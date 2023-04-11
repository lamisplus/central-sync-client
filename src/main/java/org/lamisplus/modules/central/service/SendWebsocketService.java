package org.lamisplus.modules.central.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendWebsocketService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void broadcastProgressUpdate(String destination, int progress) {
        try {
            messagingTemplate.convertAndSend(destination, progress);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    public void broadcastProgressUpdate(String destination, String jsonPayload) {
        try {
            messagingTemplate.convertAndSend(destination, jsonPayload);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
