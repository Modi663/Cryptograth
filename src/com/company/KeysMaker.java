package com.company;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.time.LocalDate;

class KeysMaker {
    private static final char[] keyStorePassword = "meteor16".toCharArray();
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException {
        int YEAR = LocalDate.now().getYear();
        // Создание базы данных ключей
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        KeyStore.ProtectionParameter entryPassword =
                new KeyStore.PasswordProtection(keyStorePassword);
        try(InputStream keyStoreData = new FileInputStream("keystore.ks")){
           keyStore.load(keyStoreData, keyStorePassword);
        } catch (IOException | CertificateException e) {
            e.printStackTrace();
        }
        // Генерация ключей
        for (int day = 1; day <= 365; day++) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            int keyBitSize = 128;
            keyGenerator.init(keyBitSize, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();

            // Запись ключей
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
            keyStore.setEntry(String.valueOf(LocalDate.ofYearDay(YEAR, day)), secretKeyEntry, entryPassword);
            }
        // Сохранение ключей
        try (FileOutputStream keyStoreOutputStream = new FileOutputStream("KeyStore"+ YEAR +".ks")) {
                keyStore.store(keyStoreOutputStream, keyStorePassword);

        } catch (CertificateException | IOException e) {
            e.printStackTrace();
        }
    }
}