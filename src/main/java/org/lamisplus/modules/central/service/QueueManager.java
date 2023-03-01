package org.lamisplus.modules.central.service;

import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lamisplus.modules.central.domain.entity.SyncQueue;
import org.lamisplus.modules.central.repository.SyncQueueRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueManager {

    private final ObjectDeserializer objectDeserializer;
    private final SyncQueueRepository syncQueueRepository;


    public SyncQueue queue(byte[] bytes, String table, Long facilityId) throws Exception {

        this.setQueue(bytes, table, facilityId);

        Optional<SyncQueue> optionalSyncQueue = syncQueueRepository.getLastSaved();
        if(optionalSyncQueue.isPresent()) return optionalSyncQueue.get();

        return null;
    }

    public SyncQueue setQueue(byte[] bytes, String table, Long facilityId) throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss.ms");
        String folder = ("sync/").concat(Long.toString(facilityId).concat("/")).concat(table).concat("/");
        String fileName = dateFormat.format(date) + "_" + timeFormat.format(date) + ".json";
        File file = new File(folder.concat(fileName));
        FileUtils.writeByteArrayToFile(file, bytes);
        SyncQueue syncQueue = new SyncQueue();
        syncQueue.setFileName(fileName);
        syncQueue.setOrganisationUnitId(facilityId);
        syncQueue.setTableName(table);
        syncQueue.setDateCreated(LocalDateTime.now());
        syncQueue.setProcessed(0);
        syncQueue.setProcessedSize(0);
        return syncQueue;
    }


    /*Client processing...*/
    @Scheduled(fixedDelay = 30000)
    public void process() throws Exception {
        List<SyncQueue> filesNotProcessed = syncQueueRepository.getAllSyncQueueByFacilitiesNotProcessed();
        LOG.info("available file for processing are : {}", filesNotProcessed.size());
        InputStream targetStream=null;
        for (SyncQueue syncQueue : filesNotProcessed) {
            String folder = ("sync/").concat(Long.toString(syncQueue.getOrganisationUnitId())
                    .concat("/")).concat(syncQueue.getTableName()).concat("/");
            File file = FileUtils.getFile(folder, syncQueue.getFileName());
            try {
                targetStream = new FileInputStream(file);
                byte[] bytes = ByteStreams.toByteArray(Objects.requireNonNull(targetStream));
                List<?> list = objectDeserializer.deserialize(bytes, syncQueue.getTableName());
                if (!list.isEmpty()) {
                    syncQueue.setProcessed(1);
                    syncQueue.setProcessedSize(list.size());
                    syncQueueRepository.save(syncQueue);
                    FileUtils.deleteQuietly(file);
                    //LOG.info("deleting file : {}", file.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(targetStream != null) targetStream.close();
            }
        }
    }
}

