package com.company;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.util.Base64;

public class Encryption{
    private final static int YEAR = LocalDate.now().getYear();
    private final static char[] keyStorePassword = "meteor16".toCharArray();


    KeyStore keyStore;
    Encryption() throws KeyStoreException {
        keyStore = KeyStore.getInstance("PKCS12");

        try(InputStream keyStoreData = new FileInputStream("KeyStore" + YEAR + ".ks")){
            keyStore.load(keyStoreData, keyStorePassword);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }

    }
    public String Cipher (String plainText, String date) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keyStore.getKey(date, keyStorePassword));
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public String Decipher (String cipherText, String date) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keyStore.getKey(date, keyStorePassword));
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText, StandardCharsets.UTF_8);
    }

}
