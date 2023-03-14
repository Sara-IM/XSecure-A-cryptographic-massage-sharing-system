/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.draft;


import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;


/**
 *
 * @author soso-
 */
public class RSA {

    // method to encrypt massage by public key
    public String encrypt ( String text , PublicKey pub) throws Exception {
        byte [] TextByte =text.getBytes();
        Cipher toCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        toCipher.init(Cipher.ENCRYPT_MODE, pub);
        byte [] cipherByte = toCipher.doFinal(TextByte);
        return encode(cipherByte);  
    }
    // method to decrypt cipher by private key
    public String decrypt (String m , PrivateKey pr )throws Exception{
        byte [] cipherByte = decode(m);
        Cipher toPlain = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        toPlain.init(Cipher.DECRYPT_MODE,pr);
        byte [] TextByte = toPlain.doFinal(cipherByte);
        return new String(TextByte, "UTF8");
    }    
    // method to convert byte[] to String
    private String encode (byte [] s){
        return Base64.getEncoder().encodeToString(s);
    }
    // method to convert String to Byte[]
    private byte [] decode (String s){
        return Base64.getDecoder().decode(s);
    }
    
    
}