package com.gmail.projectCrypt.authentication;

import com.gmail.projectCrypt.backend.cryptData.SQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    //Constructor which initializes the values of the user's attributes
    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
        //empty for when we dont want to initialize in the constructor
    }
    //getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
    //Extracting data from the database and loading it into the object when called.
    public void loadData() throws SQLException {

        SQLConnector connector = new SQLConnector();
        Statement statement = connector.SQLConnection();
        String query = "SELECT * FROM users WHERE username = " + "'" + CurrentUser.get() + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            setFirstName(rs.getString("firstName"));
            setLastName(rs.getString("lastName"));
            setEmail(rs.getString("email"));
            setUsername(rs.getString("username"));
            setPassword(rs.getString("password"));
        }
    }
}
