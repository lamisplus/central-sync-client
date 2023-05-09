package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.OrganisationUnit;
import org.lamisplus.modules.base.domain.repositories.OrganisationUnitRepository;
import org.lamisplus.modules.central.domain.dto.UploadConfirmationDTO;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncQueue;
import org.lamisplus.modules.central.domain.entity.Tables;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.utility.AESUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportService {
    private final OrganisationUnitRepository facility;
    private final QueueManager queueManager;
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;



    public UploadConfirmationDTO importPersonData(Long facilityId, String tableName, String userName, MultipartFile file) throws IOException{
        UploadConfirmationDTO uploadConfirmationDTO = new UploadConfirmationDTO();
        facility.findById(facilityId)
                .orElseThrow(()-> new EntityNotFoundException(OrganisationUnit.class, "facility", "not available"));

        RemoteAccessToken remoteAccessToken = remoteAccessTokenRepository
                .findByUsername(userName)
                .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "User", "does not exist"));

        try {
            byte[] bytes = file.getBytes();
            //Get user key
            SecretKey secretKey = AESUtil.getPrivateAESKeyFromDB(remoteAccessToken);
            //AESUtil aesUtil = new AESUtil();
            //Decrypt file
            bytes = AESUtil.decrypt(bytes, secretKey);
            //Queue files
            SyncQueue syncQueue = queueManager.setQueue(bytes, tableName, facilityId);
            //String data = new String(bytes, StandardCharsets.UTF_8);
            if (syncQueue != null) {
                uploadConfirmationDTO.setSyncQueueId(syncQueue.getId());
                uploadConfirmationDTO.setRemoteAccessTokenId(remoteAccessToken.getRemoteId());
            }
            throw new EntityNotFoundException(SyncQueue.class, "SyncQueue", "is null");
        }catch (IOException | GeneralSecurityException ex) {
            ex.printStackTrace();
            uploadConfirmationDTO.setError(ex.getMessage());
        }
        return uploadConfirmationDTO;
    }
}
