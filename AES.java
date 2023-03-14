/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.draft;


import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;


public class AES {

    Cipher toCipher;
    String IV; 
    
    
    
    //this method to encrypt the plain text for the sender.
    public String encrypt (String Text, SecretKey key) throws Exception{
        // convert text to byte[]
        byte [] TextByte =Text.getBytes();
        // to select encryption mode 
        toCipher = Cipher.getInstance("AES/GCM/NoPadding");
        // Initialize cipher with secret key passed from parameter 
        toCipher.init(Cipher.ENCRYPT_MODE, key);
        // store IV as String to share it through socket 
        IV=Base64.getEncoder().encodeToString(toCipher.getIV());
        // start encryption 
        byte [] cipherByte = toCipher.doFinal(TextByte);
        // convert cipher text to String 
        return encode(cipherByte);
    }
    
    //this method to decrypt the chipher text for the reciver.
    public String decrypt (String cipherText ,SecretKey key, byte[] iv)throws Exception{
        // convert text to byte[]
        byte [] cipherByte = decode(cipherText);
        // to select decryption mode 
        Cipher toPlain = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128,iv);
        toPlain.init(Cipher.DECRYPT_MODE, key,spec);
        byte [] TextByte = toPlain.doFinal(cipherByte);
        return new String(TextByte);    
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
