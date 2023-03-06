package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.entity.SyncQueue;
import org.lamisplus.modules.central.repository.SyncQueueRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncQueueService {

    private final SyncQueueRepository syncQueueRepository;

    public  void save(SyncQueue syncQueue) {
        syncQueueRepository.save(syncQueue);
    }



    public SyncQueue getAllSyncQueueById(Long id) {
        Optional<SyncQueue> optionalSyncQueue = syncQueueRepository.findTopByIdOrderByIdDesc(id);
        if(optionalSyncQueue.isPresent())return optionalSyncQueue.get();
        return new SyncQueue();
    }

    @Scheduled(fixedDelay = 300000)
    public void deleteProcessed(){
        syncQueueRepository.getAllByProcessed(1).stream().map(syncQueue -> {
            File file = new File(syncQueue.getFileName());
            if (file.exists()) {
                file.delete();
            }
            return syncQueue;
        }).collect(Collectors.toList());
    }
}
