package com.dao.impl;

import com.dao.UserDao;
import com.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao{

    private Connection connection;


    public UserDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    
    public Map<String, Object> Login(String contact, String password) {
         String sql = "SELECT user_id, name, type_of_user FROM users WHERE contact = ? AND password = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact);
            stmt.setString(2, password); 
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("user_id", rs.getInt("user_id"));
                    user.put("name", rs.getString("name"));
                    user.put("type_of_user", rs.getString("type_of_user"));
                    return user; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public int Register(String name, String contact, String email, String address, String password, String typeOfUser) {
        
        String sql = "INSERT INTO users (name, contact, email, address, password, type_of_user, create_date) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, contact);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setString(5, password); 
            stmt.setString(6, typeOfUser);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}