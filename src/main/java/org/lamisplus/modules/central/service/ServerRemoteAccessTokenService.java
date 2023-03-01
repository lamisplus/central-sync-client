package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.lamisplus.modules.base.controller.UserJWTController;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.base.domain.dto.UserDTO;
import org.lamisplus.modules.base.domain.entities.User;
import org.lamisplus.modules.base.service.UserService;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.utility.AESUtil;
import org.lamisplus.modules.central.utility.RSAUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerRemoteAccessTokenService {
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;
    private final UserService userService;
    private final UserJWTController userJWTController;
    private final RSAUtils rsaUtils;
    //@SneakyThrows
    public RemoteAccessToken save(byte[] bytes) throws GeneralSecurityException, IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);
        RemoteAccessToken remoteAccessToken = (RemoteAccessToken) in.readObject();

        //RemoteAccessToken remoteAccessToken = (RemoteAccessToken)SerializationUtils.deserialize(bytes);
        Optional<RemoteAccessToken> optionalRemoteAccessToken = remoteAccessTokenRepository.findByUsername(remoteAccessToken.getUsername());
        optionalRemoteAccessToken.ifPresent(remoteAccessToken1 -> {
            throw new RecordExistException(RemoteAccessToken.class, "username", ""+remoteAccessToken1.getUsername());
        });
        String key = this.generateAESKey(remoteAccessToken);

        //Encrypt Server generated AESKey with client public key
        byte[] aesKey = this.rsaUtils.encrypt(key.getBytes(StandardCharsets.UTF_8), remoteAccessToken);
        remoteAccessToken.setAnyByteKey(aesKey);

        this.createUserOnServer(remoteAccessToken); // save to user table on the server

        remoteAccessToken.setToken(this.authenticateToGetToken(remoteAccessToken));
        remoteAccessToken = this.rsaUtils.keyGenerateAndReturnKey(remoteAccessToken); // Server public & private key

        remoteAccessToken.setPrKey(key);
        remoteAccessTokenRepository.save(remoteAccessToken);
        remoteAccessToken.setPrKey("x");
        remoteAccessToken.setPassword("x");
        remoteAccessToken.setStatus(0L);
        return remoteAccessToken;
    }

    //@SneakyThrows
    public User createUserOnServer(RemoteAccessToken remoteAccessToken) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(remoteAccessToken.getUsername());
        userDTO.setLastName(remoteAccessToken.getUsername());
        userDTO.setUserName(remoteAccessToken.getUsername());

        if(null != remoteAccessToken.getOrganisationUnitId())userDTO.setCurrentOrganisationUnitId(remoteAccessToken.getOrganisationUnitId());
        else userDTO.setCurrentOrganisationUnitId(0L);

        return userService.registerOrUpdateUser(userDTO, remoteAccessToken.getPassword(), true);
    }

    private String authenticateToGetToken(RemoteAccessToken remoteAccessToken){
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(remoteAccessToken.getUsername());
        loginVM.setPassword(remoteAccessToken.getPassword());
        //Long sevenDays = 168L;
        //return userJWTController.authorize(loginVM, sevenDays).getBody().getIdToken();

        String jwt = userJWTController.authorization(loginVM);
        return jwt;
    }

    public String generateAESKey(RemoteAccessToken remoteAccessToken) throws GeneralSecurityException, IOException {
        return DatatypeConverter.printBase64Binary(AESUtil.getKeyFromPassword(remoteAccessToken.getPassword(), UUID.randomUUID().toString()).getEncoded());
    }
}
