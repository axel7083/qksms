package com.moez.QKSMS.common.models;

import android.util.Base64;
import android.util.Log;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyPairData {
    String myPrivateKey;
    String myPublicKey;

    transient PublicKey publicKey;
    transient PrivateKey privateKey;

    public KeyPairData(String myPrivateKey, String myPublicKey) {
        this.myPrivateKey = myPrivateKey;
        this.myPublicKey = myPublicKey;
    }

    public String getMyPrivateKey() {
        return myPrivateKey;
    }

    public void setMyPrivateKey(String myPrivateKey) {
        this.myPrivateKey = myPrivateKey;
    }

    public String getMyPublicKey() {
        return myPublicKey;
    }

    public void setMyPublicKey(String myPublicKey) {
        this.myPublicKey = myPublicKey;
    }

    public PublicKey getPublicKey(){

        if(publicKey != null)
            return publicKey;

        try{
            byte[] byteKey = Base64.decode(myPublicKey.getBytes(), Base64.DEFAULT);
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

    public PrivateKey getPrivateKey(){

        if(privateKey != null)
            return privateKey;

        try{
            byte[] byteKey = Base64.decode(myPrivateKey.getBytes(), Base64.DEFAULT);
            PKCS8EncodedKeySpec PKCS8privateKey = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            privateKey =  kf.generatePrivate(PKCS8privateKey);
            return privateKey;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Log.d("getPrivateKey","NULL");
        return null;
    }
}
