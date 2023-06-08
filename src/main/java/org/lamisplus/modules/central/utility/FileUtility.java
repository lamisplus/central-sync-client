package org.lamisplus.modules.central.utility;

import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


@Component
public class FileUtility {

    Set<String> filesListInDir = new HashSet<>();

    public void zipDirectory(File dir, String zipFileName) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)){
            loadDirectoryFileToList(dir);
            for (String filePath : filesListInDir) {
                if (!filePath.contains(".zip")) {
                    File file = new File(filePath);
                    ZipEntry zipEntry =  new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(zipEntry);

                    try(FileInputStream fileInputStream = new FileInputStream(filePath)){
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fileInputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, length);
                        }
                        zipOutputStream.closeEntry();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void loadDirectoryFileToList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else loadDirectoryFileToList(file);
        }
    }

    public void zipSingleFile(File file, String zipFileName) {
        try ( FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
              ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
              FileInputStream fileInputStream = new FileInputStream(file)){

            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }

            zipOutputStream.closeEntry();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void unzipFile(Path source, Path target) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                boolean isDirectory = false;
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }
                Path newPath = zipSlipProtect(zipEntry, target);
                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null){
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Path zipSlipProtect(ZipEntry zipEntry, Path targetDir) throws IOException {
        Path targetDirectoryResolver = targetDir.resolve(zipEntry.getName());
        Path normalizePath = targetDirectoryResolver.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip file: " + zipEntry.getName());
        }
        return normalizePath;

    }

    @SneakyThrows
    public ByteArrayOutputStream downloadFile(String directory, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final Optional<String> fileToDownload = listFilesUsingDirectoryStream(directory).stream()
                .filter(f -> f.equals(fileName))
                .findFirst();
        fileToDownload.ifPresent(s -> {
            try (InputStream is = new FileInputStream(directory + s)) {
                IOUtils.copy(is, baos);
            } catch (IOException ignored) {
            }
        });

        return baos;
    }

    public Set<String> listFilesUsingDirectoryStream(String directory) throws IOException {
        final Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }

        return fileList;
    }

    public long bytesToMegaBytes(long bytes) {
        long megabytes = 1024L * 1024L;

        return bytes / megabytes;
    }


}
