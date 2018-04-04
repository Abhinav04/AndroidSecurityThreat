package com.rapidbox.myapplication;

import javax.crypto.*;

import java.security.*;

import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;


/**
 * Created by abhinav_rapidbox on 14/3/18.
 */

public class AESencrp {

    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',

                    'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };


    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue;
        byte[] encodedBytes = Base64.getEncoder().encode(encVal);
        encryptedValue = new String(encodedBytes);
        return encryptedValue;
    }

    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }


}
