package com.database;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/colabDB";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    // Private constructor to prevent object creation
    private DBConnection() {}

    // Static method to get database connection
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database Connected Successfully");
        } catch (Exception e) {
            System.out.println("Database Connection Error");
            e.printStackTrace();
        }
        return con;
    }
}
