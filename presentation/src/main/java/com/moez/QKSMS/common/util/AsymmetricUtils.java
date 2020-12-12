package com.moez.QKSMS.common.util;

import android.os.Build;

import com.moez.QKSMS.common.models.KeyPairData;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class AsymmetricUtils {

    private static final String RSA
            = "RSA";

    // Generating public & private keys
    // using RSA algorithm.
    public static KeyPair generateRSAKkeyPair()
            throws Exception
    {
        SecureRandom secureRandom
                = new SecureRandom();
        KeyPairGenerator keyPairGenerator
                = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(
                2048, secureRandom);
        return keyPairGenerator
                .generateKeyPair();
    }

    public static KeyPairData generateKeyPair()
    {
        KeyPair keyPair = null;
        try {
            keyPair = generateRSAKkeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        String publicKey;
        String privateKey;

        if (Build.VERSION.SDK_INT >= 26) {
            publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        } else {
            publicKey = android.util.Base64.encodeToString(keyPair.getPublic().getEncoded(), 0);
            privateKey = android.util.Base64.encodeToString(keyPair.getPrivate().getEncoded(), 0);
        }

        return new KeyPairData(privateKey,publicKey);
    }

    // Encryption function which converts
    // the plainText into a cipherText
    // using private Key.
    public static byte[] do_RSAEncryption(
            String plainText,
            PublicKey publicKey)
            throws Exception
    {
        Cipher cipher
                = Cipher.getInstance(RSA);

        cipher.init(
                Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(
                plainText.getBytes());
    }

    // Decryption function which converts
    // the ciphertext back to the
    // orginal plaintext.
    public static String do_RSADecryption(
            byte[] cipherText,
            PrivateKey privateKey)
            throws Exception
    {
        Cipher cipher
                = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE,
                privateKey);
        byte[] result
                = cipher.doFinal(cipherText);

        return new String(result);
    }

    public static String BAtoString(byte[] bArr) { //Byte array to String
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X", Byte.valueOf(bArr[i])));
        }
        return sb.toString();
    }

    public static PublicKey getPublicKey(String key){
        PublicKey publicKey;

        try{
            byte[] byteKey = android.util.Base64.decode(key.getBytes(), android.util.Base64.DEFAULT);
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            publicKey =  kf.generatePublic(X509publicKey);
            return publicKey;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
