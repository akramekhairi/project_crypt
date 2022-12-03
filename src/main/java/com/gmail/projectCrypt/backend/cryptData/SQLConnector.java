package com.gmail.projectCrypt.backend.cryptData;

import java.sql.*;

public class SQLConnector {
    public Statement SQLConnection() throws SQLException {
        //Creating a connection between the program and the database and returning a statement that can be used throughout the program.
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connect = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/crypto?user=root&password=toor");
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        return statement;

    }
}
