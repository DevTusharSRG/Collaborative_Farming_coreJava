package com.dao.impl;

import com.dao.PropertyLeasingDao;
import com.database.DBConnection;
import java.sql.*;
import java.util.*;

public class PropertyLeasingDaoImpl implements PropertyLeasingDao {
    private Connection connection;

    public PropertyLeasingDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    
    public List<Object> getAllProperties() {
        List<Object> properties = new ArrayList<>();
        String query = "SELECT * FROM property where status='Available'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                properties.add("| PropertyId - "+rs.getInt("property_id") + "\t| OwnerId: " 
                    + rs.getString("farmer_id") + "\t|Land Type: "
                    + rs.getString("type_of_land") + "\t|Village: " 
                    + rs.getString("village") + "\t|Area: " 
                    + rs.getString("area_acre") + "\t|Guntha: " 
                    + rs.getString("area_guntha") + "\t|Rent: " 
                    + rs.getDouble("lease_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    // public Object getPropertyById(int propertyId) {
    //     String query = "SELECT * FROM property WHERE property_id = ?";
    //     try (PreparedStatement pstmt = connection.prepareStatement(query)) {
    //         pstmt.setInt(1, propertyId);
    //         ResultSet rs = pstmt.executeQuery();
    //         if (rs.next()) {
    //             return rs.getInt("property_id") + " - " 
    //                 + rs.getString("village") + ", " 
    //                 + rs.getString("taluka") + ", " 
    //                 + rs.getString("district") + " - Rent: " 
    //                 + rs.getDouble("lease_price");
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
    
    public Map<String, Object> getPropertyById(int propertyId) {
        String query = "SELECT * FROM property WHERE property_id = ?";
        Map<String, Object> propertyData = new HashMap<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, propertyId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    propertyData.put("owner_id", rs.getInt("farmer_id"));
                    propertyData.put("lease_price", rs.getDouble("lease_price"));
                    propertyData.put("village", rs.getString("village"));
                    propertyData.put("taluka", rs.getString("taluka"));
                    propertyData.put("district", rs.getString("district"));
                    return propertyData; // Return property data if found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no data is found
    }

    
    public boolean requestProperty(int propertyId, int ownerId, int userId, String startDate, String endDate, double rentAmount) {
        String query = "INSERT INTO property_agreements (property_id, owner_id, tenant_id, start_date, end_date, rent_amount, payment_status, agreement_status, created_at, updated_at) " +
                       "VALUES (?, ?, ?, ?, ?, ?, 'Pending', 'Pending', NOW(), NOW())";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, propertyId);
            pstmt.setInt(2, ownerId);
            pstmt.setInt(3, userId);
            pstmt.setDate(4, java.sql.Date.valueOf(startDate)); 
            pstmt.setDate(5, java.sql.Date.valueOf(endDate)); 
            pstmt.setDouble(6, rentAmount);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

   
    public List<Object> getAgreementDetails(String status,int userID) {
        List<Object> agreements = new ArrayList<>();
        String query = "SELECT * FROM property_agreements WHERE agreement_status = ? and tenant_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                agreements.add("Agreement ID: " + rs.getInt("agreement_id") + ", Property ID: " + rs.getInt("property_id") + ", User ID: " + rs.getInt("owner_id") + ", Status: " + rs.getString("agreement_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agreements;
    }
}
