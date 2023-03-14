/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.draft;

import java.util.ArrayList;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class userInfo {
    
    

    String userName ;
    String password ;
    private PublicKey pubKey ;
    private PrivateKey prKey;
    
    // to create instance from the class
    public userInfo(String userName, String password) throws Exception {
        this.userName = userName;
        // send password to save hashed password
        this.password = generateHash(password);
        // generate private and public keys 
        KeyPairGenerator kgenerator = KeyPairGenerator.getInstance("RSA");
        kgenerator.initialize(1024);
        KeyPair pair=kgenerator.generateKeyPair();
        prKey=pair.getPrivate();
        pubKey=pair.getPublic();
        
        // Store Public Key.
	X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey.getEncoded());
        FileOutputStream keysFile = new FileOutputStream(this.userName + "public.key");
	keysFile.write(x509EncodedKeySpec.getEncoded());
	keysFile.close();
 
        // Store Private Key.
	PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(prKey.getEncoded());
	keysFile = new FileOutputStream(this.userName + "private.key");
	keysFile.write(pkcs8EncodedKeySpec.getEncoded());
	keysFile.close();        
        
        
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    public PublicKey getPubKey() {
        return pubKey;
    }
    public PrivateKey getPrKey() {
        return prKey;
    }
    
    // method to hash the password using SHA-512
    public String generateHash(String password_text) throws Exception { 
    MessageDigest md = MessageDigest.getInstance ("SHA-512") ;
    byte[] hashedPassword = md.digest(password_text.getBytes(StandardCharsets.UTF_8));
    // convert hashed password to String
    return Base64.getEncoder().encodeToString(hashedPassword); 
    }
    
    
    }