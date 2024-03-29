package org.lamisplus.modules.central.utility;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
/*import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;*/
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.*;


@Component
@Slf4j
public class FileUtility {

    Set<String> filesListInDir = new HashSet<>();
    static final Field zipNamesField = getZipNameField();

    private static Field getZipNameField()
    {
        try
        {
            Field res = ZipOutputStream.class.getDeclaredField("names");
            res.setAccessible(true);
            return res;
        }
        catch (NoSuchFieldException | SecurityException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void zipDirectory(File dir, String zipFileName, String folder) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)){
            //loadDirectoryFileToList(dir, folder);
            //log.info("filesListInDir {}", filesListInDir);
            for (File file : dir.listFiles()) {
                String filePath = file.getPath();
                if (!filePath.contains(".zip") && filePath.contains(".json")) {
                    //File file = new File(filePath);
                    ((HashSet<String>) zipNamesField.get(zipOutputStream)).clear();
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
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    /*public void zipDirectoryLocked(File dir, String fileName, String password) {
        List<File> fileList = Arrays.asList(dir.listFiles())
                .stream()
                .filter(file -> file.getName().contains(".json"))
                .collect(Collectors.toList());
        try {
            String filePath = dir.getPath() +File.separator + fileName;
            log.info("file path {}", filePath);
            lockZipFile(dir.getPath() +File.separator + password, password, fileList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void loadDirectoryFileToList(File dir, String folder) throws IOException {
        File[] files = dir.listFiles();
        for (File file : files) {
            String absolutePath = file.getAbsolutePath();
            if (file.isFile() && (file.getParentFile().equals(folder) || absolutePath.contains(folder))){
                log.info("absolutePath {}", absolutePath);
                filesListInDir.add(absolutePath);
            } else loadDirectoryFileToList(file, folder);
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

//    public long bytesToMegaBytes(long bytes) {
//        long megabytes = 1024L * 1024L;
//
//        return bytes / megabytes;
//    }

    /*public byte[] convertZipToByteArray(String zipFilePath) {
        try (FileInputStream fis = new FileInputStream(zipFilePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public byte[] convertFileToByteArray(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void convertByteToZip(byte[] byteArray, String outputFilePath) {
        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            bos.write(byteArray);
            bos.flush();

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(outputFilePath))) {
                ZipEntry entry = zis.getNextEntry();
                while (entry != null) {
                    String entryName = entry.getName();
                    String entryPath = outputFilePath + File.separator + entryName;
                    File entryFile = new File(entryPath);

                    if (entry.isDirectory()) {
                        entryFile.mkdirs();
                    } else {
                        entryFile.getParentFile().mkdirs();
                        try (FileOutputStream entryOutputStream = new FileOutputStream(entryFile);
                             BufferedOutputStream entryBos = new BufferedOutputStream(entryOutputStream)) {

                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = zis.read(buffer)) != -1) {
                                entryBos.write(buffer, 0, bytesRead);
                            }
                        }
                    }

                    zis.closeEntry();
                    entry = zis.getNextEntry();
                }
            }

            System.out.println("ZIP file extracted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*public void lockZipFile(String zipFilePath, String password, List<File> fileList) throws IOException {
        try(ZipFile zipFile = new ZipFile(zipFilePath, password.toCharArray())) {
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
            zipParameters.setCompressionLevel(CompressionLevel.HIGHER);
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_128);

            zipFile.addFiles(fileList, zipParameters);
        } catch (ZipException exception) {
            exception.printStackTrace();
            log.error("An errorr occurred while creating zip file: {}", exception.getMessage());
        }
    }*/

    /*public void unlockZipFile(String lockedZipFilePath, String unlockedZipFilePath, String password) throws IOException {
        try(ZipFile zipFile = new ZipFile(lockedZipFilePath, password.toCharArray());) {
            if (Files.exists(Paths.get(lockedZipFilePath))) {
                zipFile.extractAll(unlockedZipFilePath);
            }
        } catch (ZipException exception) {
            log.error("An errorr occurred while creating zip file: {}", exception.getMessage());
        }
    }*/


//    @SneakyThrows
//    public ByteArrayOutputStream downloadFile(String directory, String fileName) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        final Optional<String> fileToDownload = listFilesUsingDirectoryStream(directory).stream()
//                .filter(f -> f.equals(fileName))
//                .findFirst();
//        fileToDownload.ifPresent(s -> {
//            try (InputStream is = new FileInputStream(directory + s)) {
//                IOUtils.copy(is, baos);
//            } catch (IOException ignored) {
//            }
//        });
//
//        return baos;
//    }

    public long getFileSize(String filePath) throws IOException {
       return Files.size(Paths.get(filePath));
    }
//
//    public Set<String> listFilesUsingDirectoryStream(String directory) throws IOException {
//        final Set<String> fileList = new HashSet<>();
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory))) {
//            for (Path path : stream) {
//                if (!Files.isDirectory(path)) {
//                    fileList.add(path.getFileName()
//                            .toString());
//                }
//            }
//        }
//
//        return fileList;
//    }

    public long bytesToMegaBytes(long bytes) {
        long megabytes = 1024L * 1024L;

        return bytes / megabytes;
    }


}
