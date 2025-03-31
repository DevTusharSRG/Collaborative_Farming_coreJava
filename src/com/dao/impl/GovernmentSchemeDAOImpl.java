package com.dao.impl;

import com.dao.GovernmentSchemeDAO;
import com.database.DBConnection;
import java.sql.*;
import java.util.*;

public class GovernmentSchemeDAOImpl implements GovernmentSchemeDAO {

    @Override
    public void insertScheme(String title, double benefitAmount, String startDate, String lastDate, String description) {
        String query = "INSERT INTO government_schemes (title, benefit_amount, start_date, last_date, description, created_date) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, title);
            pst.setDouble(2, benefitAmount);
            pst.setDate(3, java.sql.Date.valueOf(startDate));
            pst.setDate(4, java.sql.Date.valueOf(lastDate));
            pst.setString(5, description);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertApplication(int farmerId, int schemeId) {
        String query = "INSERT INTO government_scheme_application (farmer_id, scheme_id, status, applied_date) VALUES (?, ?, 'Pending', CURRENT_DATE)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, farmerId);
            pst.setInt(2, schemeId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateApplicationStatus(int applicationId, String status) {
        String query = "UPDATE government_scheme_application SET status = ? WHERE application_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, status);
            pst.setInt(2, applicationId);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Application status updated successfully.");
            } else {
                System.out.println("Application ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> getApplicationsByStatus(String status) {
        List<Map<String, Object>> applications = new ArrayList<>();
        String query = "SELECT * FROM government_scheme_application WHERE status = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, status);
    
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> app = new HashMap<>();
                    app.put("application_id", rs.getInt("application_id"));  //  Corrected Key Names
                    app.put("scheme_id", rs.getInt("scheme_id"));
                    app.put("farmer_id", rs.getInt("farmer_id"));
                    app.put("register_date", rs.getDate("register_date"));
                    app.put("status", rs.getString("status"));
                    applications.add(app);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        System.out.println("Total Applications Found: " + applications.size());
        return applications;
    }
    
    public List<Map<String, Object>> getAllSchemes() {
        List<Map<String, Object>> schemes = new ArrayList<>();
        String query = "SELECT scheme_id, title, benefit_amount, start_date, last_date, description FROM government_schemes";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> scheme = new HashMap<>();
                scheme.put("scheme_id", rs.getInt("scheme_id"));
                scheme.put("title", rs.getString("title"));
                scheme.put("benefitAmount", rs.getDouble("benefit_amount"));
                scheme.put("startDate", rs.getString("start_date"));
                scheme.put("lastDate", rs.getString("last_date"));
                scheme.put("description", rs.getString("description"));

                schemes.add(scheme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schemes;
    }

}
