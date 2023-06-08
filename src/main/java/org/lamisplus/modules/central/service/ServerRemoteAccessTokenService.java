package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

        String key = this.generateAESKey(remoteAccessToken);

        //Encrypt Server generated AESKey with client public key
        byte[] aesKey = this.rsaUtils.encrypt(key.getBytes(StandardCharsets.UTF_8), remoteAccessToken);
        remoteAccessToken.setAnyByteKey(aesKey);

        //Authenticate user & get token
        remoteAccessToken.setToken(this.authenticateToGetToken(remoteAccessToken));
        // Server public & private key
        remoteAccessToken = this.rsaUtils.keyGenerateAndReturnKey(remoteAccessToken);

        remoteAccessToken.setPrKey(key);
        remoteAccessToken.setPassword("x");

        //Check if user exist
        Optional<RemoteAccessToken> accessTokenOptional = remoteAccessTokenRepository
                .findByUsername(remoteAccessToken.getUsername());
        //set id
        if(accessTokenOptional.isPresent())remoteAccessToken.setId(accessTokenOptional.get().getId());

        remoteAccessTokenRepository.save(remoteAccessToken);
        remoteAccessToken.setStatus(0L);
        log.info("token generated");
        return remoteAccessToken;
    }

    //@SneakyThrows
    //create token
    /*public User createUserOnServer(RemoteAccessToken remoteAccessToken) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(remoteAccessToken.getUsername());
        userDTO.setLastName(remoteAccessToken.getUsername());
        userDTO.setUserName(remoteAccessToken.getUsername());

        Set<String> set = new HashSet<>();
        set.add("Data Clerk");
        //set as Data Clerk but change to another user
        userDTO.setRoles(set);

        if(null != remoteAccessToken.getOrganisationUnitId())userDTO.setCurrentOrganisationUnitId(remoteAccessToken.getOrganisationUnitId());
        else userDTO.setCurrentOrganisationUnitId(0L);

        return userService.registerOrUpdateUser(userDTO, remoteAccessToken.getPassword(), true);
    }*/

    private String authenticateToGetToken(RemoteAccessToken remoteAccessToken){
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(remoteAccessToken.getUsername());
        loginVM.setPassword(remoteAccessToken.getPassword());
        //Long sevenDays = 168L;
        //return userJWTController.authorize(loginVM, sevenDays).getBody().getIdToken();

        return userJWTController.authorize(loginVM).getBody().getIdToken();
    }

    public String generateAESKey(RemoteAccessToken remoteAccessToken) throws GeneralSecurityException, IOException {
        return DatatypeConverter.printBase64Binary(AESUtil.getKeyFromPassword(remoteAccessToken.getPassword(), UUID.randomUUID().toString()).getEncoded());
    }
}
