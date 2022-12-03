package com.gmail.projectCrypt.authentication;

import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a user if the password is the same string, and
 * considers the user "admin" as the only administrator.
 */
public class BasicAccessControl implements AccessControl {

    @Override
    public boolean signIn(String username, String password) throws SQLException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        String querpass = "";
        String encryptedDay = "";
        //if the username value is empty then false is returned
        if (username == null || username.isEmpty()) {
            return false;
        }
        SQLConnector connect = new SQLConnector();
        Statement stmt = connect.SQLConnection();
        String query = "SELECT password,encryptedDay FROM users WHERE username = '"+username+"'";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            querpass = rs.getString("password");
            encryptedDay = rs.getString("encryptedDay");
        }
        //Extracting the encrpyted password and encrypted day value

        //decrypting day value first
        DecimalFormat df2 = new DecimalFormat("###");
        int day = Integer.valueOf(df2.format(Math.pow(10, Double.valueOf(encryptedDay) / Math.pow(Math.E, 44))));

        String binaryDay = Integer.toString(day, 2);

        //Decrypting the password

        Encryption encrypt = new Encryption();

        String decryptedString = encrypt.decryptmessage(querpass,binaryDay);

        //Checking if the password entered is equal to the value in the database.
        if (!password.equals(decryptedString)){
            return false;
        }

        CurrentUser.set(username);
        return true;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

    @Override
    public void signOut() {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().navigate("Login");
    }



}
