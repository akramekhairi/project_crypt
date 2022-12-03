package com.gmail.projectCrypt.authentication;

import com.gmail.projectCrypt.backend.cryptData.SQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.*;
import java.io.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encryption {



    public static char[] charset1 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static char[] charset2 = {'ا','ب','ت','ث','ج','ح','خ','د','ذ','ر','ز','س','ش','ص','ض','ط','ظ','ع','غ','ف','ق','ك','ل','م','ن','ه','و','ي'};




    public String encryptmessage(String decodedString) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException {



        //the default charset we will be using :)

        //getting the day of the month and storing it into day
        int day;
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now(zone);
        day = localDate.getDayOfMonth();

        //now to convert the day into a binary string
        String binaryDay;
        binaryDay = Integer.toString(day, 2);
        char[] daylist = binaryDay.toCharArray();

        char[] plaintext = decodedString.toCharArray();

        String newstr = "";

        String charsetstr = new String(charset1);
        int a = 0;
        //Using a loop where if the binary day value is 1 change the character's language to the arabic equivalent and if it's 0 keep it in english
        for(int i = 0; i < decodedString.length();i++){
            if (daylist[a] == '1') {
                int x = charsetstr.indexOf(plaintext[i]);
                newstr += charset2[x];


            }

            if (daylist[a] == '0')  {
                newstr += plaintext[i];
            }

            a++;
            //Wrap binary value as it might be shorter than the password
            if (a == binaryDay.length()-1){
                a = 0;
            }

        }


        RSAUtil rsa = new RSAUtil();
        //Encrypt the already encrypted message with RSA as well
        String encryptedString = Base64.getEncoder().encodeToString(rsa.encrypt(newstr, rsa.publicKey));



        return encryptedString;
















    }


    public String decryptmessage(String entry,String binaryDay) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException {
        RSAUtil rsa = new RSAUtil();
        //Decrypt the message using RSA
        String decryptedString = RSAUtil.decrypt(entry, rsa.privateKey);

        char[] daylist = binaryDay.toCharArray();
        char[] ciphertext = decryptedString.toCharArray();
        //Opposite of encryption process

        int a = 0;

        String charsetstr = new String(charset2);

        String decryptstr = "";
        //Loop used so where there's a 1 in the binary day value change the character to english and if it's 0 use the same english character
        for(int i=0;i<decryptedString.length();i++){
            if (daylist[a] == '1') {
                int x = charsetstr.indexOf(ciphertext[i]);
                decryptstr += charset1[x];

            }

            if (daylist[a] == '0') {
                decryptstr += ciphertext[i];
            }

            a++;
            //Wrap binary value as it might be shorter than the password
            if (a == binaryDay.length()-1){
                a = 0;
            }
        }

        return decryptstr;

    }


    public double hashingalgorithm(String username, String password) throws SQLException {
        SQLConnector connect = new SQLConnector();
        Statement stmt = connect.SQLConnection();

        int[] charnumbers = new int[password.length()];
        //Converting the username into an array with all the character's UTF-8 values
        for (int i = 0; i < password.length(); i++){
            charnumbers[i] = password.toCharArray()[i];


        }
        double hashvalue;
        //Generating the hashvalue
        hashvalue = Math.E * charnumbers[0] + charnumbers[1]%charnumbers[2];
        String query = "SELECT hashvalue FROM users WHERE hashvalue = '"+hashvalue+"'";
        //Check if the hashvalue is in the DB
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()){
            boolean differentcheck = false;
            while (!differentcheck){
                //Generate a random number and check if it's in the DB if it is then generate another one
                hashvalue = Math.random();
                query = "SELECT hashvalue FROM users WHERE hashvalue = '"+hashvalue+"'";
                rs = stmt.executeQuery(query);
                if(!rs.next()){
                    differentcheck = true;
                }

            }
        }
        return hashvalue;
    }

}



class RSAUtil {

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";
    public static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";

    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }





    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

}


