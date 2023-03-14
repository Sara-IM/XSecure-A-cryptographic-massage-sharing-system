/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.draft;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class DecryptJFrame extends javax.swing.JFrame {

    //static ServerSocket server;
    static Socket socket;
    static DataInputStream inputkey;
    static DataInputStream inputtext;
    static DataInputStream inputiv;
    static byte[] iv;
    static String  key;
    static String CipherText;
    SecretKey secretKey ;
    PrivateKey PrivateKey;
    
    
    public DecryptJFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        DecryptLable = new javax.swing.JLabel();
        DecryptButton = new javax.swing.JButton();
        ErrorLable = new javax.swing.JLabel();
        BackButton = new javax.swing.JButton();
        CipherTextField = new javax.swing.JTextField();
        PlainTextField = new javax.swing.JTextField();
        prKeyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        DecryptLable.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        DecryptLable.setText("Decrypt");
        DecryptLable.setPreferredSize(new java.awt.Dimension(101, 37));

        DecryptButton.setBackground(new java.awt.Color(204, 204, 255));
        DecryptButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DecryptButton.setText("Decrypte Text");
        DecryptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptButton(evt);
            }
        });

        ErrorLable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        BackButton.setBackground(new java.awt.Color(204, 204, 255));
        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButton(evt);
            }
        });

        CipherTextField.setToolTipText("                                    ");
        CipherTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        PlainTextField.setForeground(new java.awt.Color(153, 153, 153));
        PlainTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        prKeyButton.setBackground(new java.awt.Color(204, 204, 255));
        prKeyButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        prKeyButton.setText("Private Key");
        prKeyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectPtivateKButton(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(DecryptLable, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(278, 278, 278)
                        .addComponent(ErrorLable))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(BackButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CipherTextField)
                            .addComponent(PlainTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(DecryptButton)
                        .addGap(26, 26, 26)
                        .addComponent(prKeyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(DecryptLable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CipherTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DecryptButton)
                    .addComponent(prKeyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(PlainTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ErrorLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BackButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        
// This button recieve cipher text and decrypt it
    private void decryptButton(java.awt.event.ActionEvent evt) {                                         
        try{
        // initiate RSA object to decrypt Symetric Key
        RSA rsa= new RSA();
        //Store secret key in byte String to conver it from String To SecretKey
        byte[] decodedKey = Base64.getDecoder().decode(rsa.decrypt(key, PrivateKey));
        // Rebuild key using SecretKeySpec
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");  
        System.out.println("Symetric key afte decrypt  " +Base64.getEncoder().encodeToString(secretKey.getEncoded())); 
        
        // initiate AES object to decrypt file using SecretKey
        AES aes = new AES();
        String plain =aes.decrypt(CipherText, secretKey,iv);
        // present decrypted text in PlainTextField
        PlainTextField.setText(PlainTextField.getText().trim()+"\n"+plain);
       }
       catch(Exception error){  
        System.err.println("Error! No Key OR Text "+ error.toString()); 
       }

    }                                        

    
    // method to go to main page
    private void BackButton(java.awt.event.ActionEvent evt) {                                         
        SelectJFrame select = new SelectJFrame();
        select.show();
  
        dispose();
    }                                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // this text filed well recive the cipher text from the sender.

    }                                           

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // present plain text after decryption
          
    }                                           
    
    // method to selectPtivateKButton and decrypt the symetricKey
    private void selectPtivateKButton(java.awt.event.ActionEvent evt) {                                         
        try {
        // to select private key file and read it as String    
        String path="";
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
        int r = j.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getName();
            
            //Store private key in byte String to convert it from String To PrivateKey 
            byte[] keyBytes2 = Files.readAllBytes(Paths.get(path));
            PKCS8EncodedKeySpec spec2 =new PKCS8EncodedKeySpec(keyBytes2);
            KeyFactory kf2 = KeyFactory.getInstance("RSA");
            PrivateKey= kf2.generatePrivate(spec2);
            System.out.println(path);
            System.out.println("private key "+Base64.getEncoder().encodeToString(PrivateKey.getEncoded()));
        } 

        }
        catch(Exception ex){
            System.err.println("No Private  File"+ex.toString());
        }
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
 
        /* Set the Nimbus look and feel */
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DecryptJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DecryptJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DecryptJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DecryptJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DecryptJFrame().setVisible(true);
            }
        });
        
        
        try{
            // to establish connection 
            socket = new Socket("localhost", 5000);
            // object to read from socket
            inputkey = new DataInputStream(socket.getInputStream());
            inputtext = new DataInputStream(socket.getInputStream());
            inputiv = new DataInputStream(socket.getInputStream());
            // read key and use it in decryptButton method
            key=inputkey.readUTF();
            // read text and display it in cipher text field 
            CipherText= inputtext.readUTF();
            // read IV as string then convert it as byte [] and use it in decryptButton method
            iv= Base64.getDecoder().decode(inputiv.readUTF());
            // display cipher text
            CipherTextField.setText(CipherTextField.getText().trim()+"\n"+CipherText);   


        }
        catch(Exception e){  
        System.err.println("no text recive"+e.toString()); 
       }
        
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton prKeyButton;
    private javax.swing.JButton BackButton;
    private static javax.swing.JButton DecryptButton;
    private javax.swing.JLabel DecryptLable;
    private javax.swing.JLabel ErrorLable;
    static javax.swing.JTextField CipherTextField;
    private javax.swing.JTextField PlainTextField;
    // End of variables declaration                   
}