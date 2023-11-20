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
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

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

    /**
     * read Private Key.
     * @Param prKey
     * @return PrivateKey
     */
    public PrivateKey readPrivateKey(String prKey) throws GeneralSecurityException {
        PrivateKey key;
        String fileString =  prKey;
        if(StringUtils.isBlank(fileString)) throw new EntityNotFoundException(RemoteAccessToken.class, "Private key", "Private key");
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(fileString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        key = kf.generatePrivate(spec);
        return key;
    }

    /**
     * read RSA Public Key.
     * @return Public Key
     */
    public PublicKey readRSAPublicKey(String facilityAppKey) throws GeneralSecurityException {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(facilityAppKey);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey key = kf.generatePublic(publicKeySpec);
        return key;
    }

    /**
     * encrypt message with facility key.
     * @param bytes
     * @param facilityAppKey
     * @return HashMap<String, String>
     */
    public byte[] encrypt(byte[] bytes, String facilityAppKey) throws GeneralSecurityException {
        log.info("encrypt start ---");
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, this.readRSAPublicKey(facilityAppKey));
        byte[] encryptedMessageBytes = encryptCipher.doFinal(bytes);
        log.info("encrypt end ---");
        return encryptedMessageBytes;
    }

    /**
     * generate and return Key.
     * @return HashMap<String, String>
     */
    public HashMap<String, String> keyGenerateAndReturnKey() {
        String publicKeyPEM;
        String privateKeyPEM;
        HashMap<String, String> keys = null;
        //log.info("main method of generator");
        try {
            this.generateSecureKeys();
            this.createKeys();

            // THIS IS PEM:
            publicKeyPEM = DatatypeConverter.printBase64Binary(this.getPublicKey().getEncoded());
            privateKeyPEM = DatatypeConverter.printBase64Binary(this.getPrivateKey().getEncoded());
            keys = new HashMap<>();
            keys.put("privateKeyPEM", privateKeyPEM);
            keys.put("publicKeyPEM", publicKeyPEM);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    /**
     * decrypting message with Private Key.
     * @param encryptedMessageBytes
     * @param prKey
     * @return String
     */
    public String decryptWithPrivateKey(byte[] encryptedMessageBytes, String prKey) throws GeneralSecurityException {
        PrivateKey privateKey = this.readPrivateKey(prKey);
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        return decryptedMessage;
    }
}
