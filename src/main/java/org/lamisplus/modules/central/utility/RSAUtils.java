package org.lamisplus.modules.central.utility;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
@RequiredArgsConstructor
@Slf4j
@Data
public class RSAUtils {
    private KeyPairGenerator keyGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private void generateSecureKeys() throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(2048);
    }

    public void createKeys() {
        KeyPair pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey readPrivateKey(RemoteAccessToken remoteAccessToken) throws GeneralSecurityException {
        PrivateKey key;
        String fileString =  remoteAccessToken.getPrKey();
        if(StringUtils.isBlank(fileString)) throw new EntityNotFoundException(RemoteAccessToken.class, "Private key", "Private key");
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(fileString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        key = kf.generatePrivate(spec);
        return key;
    }

    public PublicKey readRSAPublicKey(String facilityAppKey) throws GeneralSecurityException {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(facilityAppKey);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey key = kf.generatePublic(publicKeySpec);
        return key;
    }

    public byte[] encrypt(byte[] bytes, String facilityAppKey) throws GeneralSecurityException {
        log.info("encrypt start ---");
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, this.readRSAPublicKey(facilityAppKey));
        byte[] encryptedMessageBytes = encryptCipher.doFinal(bytes);
        log.info("encrypt end ---");
        return encryptedMessageBytes;
    }
}
