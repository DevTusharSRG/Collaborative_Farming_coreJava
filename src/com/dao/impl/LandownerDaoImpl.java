package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.LandownerDao;
import com.database.DBConnection;

public class LandownerDaoImpl implements LandownerDao {
    private Connection connection;

    public LandownerDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    public boolean insertProperty(int farmerId, String village, String taluka, String district, String state,
            String typeOfLand, String landImage, String documentImage, double areaAcre,
            double leasePrice, double areaGuntha, String status, String createDate) {
        String sql = "INSERT INTO property (farmer_id, village, taluka, district, state, type_of_land, land_image, " +
                "document_image, area_acre, lease_price, area_guntha, status,create_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, farmerId);
            stmt.setString(2, village);
            stmt.setString(3, taluka);
            stmt.setString(4, district);
            stmt.setString(5, state);
            stmt.setString(6, typeOfLand);
            stmt.setString(7, landImage);
            stmt.setString(8, documentImage);
            stmt.setDouble(9, areaAcre);
            stmt.setDouble(10, leasePrice);
            stmt.setDouble(11, areaGuntha);
            stmt.setString(12, status);
            stmt.setDate(13, java.sql.Date.valueOf(createDate));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> getAllProperties(int id) {
        List<Map<String, Object>> properties = new ArrayList<>();
        String sql = "SELECT * FROM property WHERE farmer_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> property = new HashMap<>();
                property.put("id", rs.getInt("property_id"));
                property.put("village", rs.getString("village"));
                property.put("taluka", rs.getString("taluka"));
                property.put("district", rs.getString("district"));
                property.put("state", rs.getString("state"));
                property.put("type_of_land", rs.getString("type_of_land"));
                property.put("land_image", rs.getString("land_image"));
                property.put("document_image", rs.getString("document_image"));
                property.put("area_acre", rs.getDouble("area_acre"));
                property.put("lease_price", rs.getDouble("lease_price"));
                property.put("area_guntha", rs.getDouble("area_guntha"));
                property.put("status", rs.getString("status"));
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // This will work on property agreements
    public List<Map<String, Object>> getAllPropertyRequests(int ownerId) {
        List<Map<String, Object>> requests = new ArrayList<>();
        String sql = "SELECT pa.agreement_id, pa.property_id, pa.start_date, pa.end_date, pa.rent_amount, " +
                "u.user_id, u.name AS tenant_name, u.address AS tenant_address, u.contact AS tenant_contact " +
                "FROM property_agreements pa " +
                "JOIN users u ON pa.tenant_id = u.user_id " +
                "WHERE pa.owner_id = ? AND pa.agreement_status = 'Pending'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> request = new HashMap<>();
                    request.put("agreement_id", rs.getInt("agreement_id"));
                    request.put("property_id", rs.getInt("property_id"));
                    request.put("tenant_name", rs.getString("tenant_name"));
                    request.put("tenant_address", rs.getString("tenant_address"));
                    request.put("tenant_contact", rs.getString("tenant_contact"));
                    request.put("start_date", rs.getDate("start_date"));
                    request.put("end_date", rs.getDate("end_date"));
                    request.put("rent_amount", rs.getDouble("rent_amount"));

                    requests.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean updateRequestForProperty(int agreementId) {
        // String sql = "UPDATE property_agreements SET agreement_status = 'Accepted'
        // WHERE agreement_id = ?";
        // try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        // stmt.setInt(1, agreementId);
        // return stmt.executeUpdate() > 0;
        // } catch (SQLException e) {
        // e.printStackTrace();
        // return false;
        // }

        String fetchPropertyIdSql = "SELECT property_id FROM property_agreements WHERE agreement_id = ?";
        String updateAgreementSql = "UPDATE property_agreements SET agreement_status = 'Accepted' WHERE agreement_id = ?";
        String updatePropertySql = "UPDATE property SET status = 'Not Available' WHERE property_id = ?";
        try (PreparedStatement fetchStmt = connection.prepareStatement(fetchPropertyIdSql);
                PreparedStatement updateAgreementStmt = connection.prepareStatement(updateAgreementSql);
                PreparedStatement updatePropertyStmt = connection.prepareStatement(updatePropertySql)) {
            fetchStmt.setInt(1, agreementId);
            ResultSet rs = fetchStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No property found for the given agreement ID.");
                connection.rollback();
                return false;
            }
            int propertyId = rs.getInt("property_id");

            // Step 2: Update agreement_status to 'Accepted'
            updateAgreementStmt.setInt(1, agreementId);
            if (updateAgreementStmt.executeUpdate() <= 0) {
                System.out.println("Failed to update agreement status.");
                connection.rollback();
                return false;
            }
            updatePropertyStmt.setInt(1, propertyId);
            if (updatePropertyStmt.executeUpdate() <= 0) {
                System.out.println("Failed to update property status.");
                connection.rollback();
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public List<Map<String, Object>> getAgreementsByUserAndStatus(int userId, String status) {
        List<Map<String, Object>> agreements = new ArrayList<>();
        String sql = "SELECT pa.agreement_id, pa.property_id, pa.tenant_id, pa.start_date, pa.end_date, pa.rent_amount, "
                +
                "pa.payment_status, pa.agreement_status, " +
                "u.name AS tenant_name, u.address AS tenant_address, u.contact AS tenant_contact " +
                "FROM property_agreements pa " +
                "JOIN users u ON pa.tenant_id = u.user_id " +
                "WHERE pa.owner_id = ? AND pa.agreement_status = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> agreement = new HashMap<>();
                    agreement.put("agreement_id", rs.getInt("agreement_id"));
                    agreement.put("property_id", rs.getInt("property_id"));
                    agreement.put("tenant_name", rs.getString("tenant_name"));
                    agreement.put("tenant_address", rs.getString("tenant_address"));
                    agreement.put("tenant_contact", rs.getString("tenant_contact"));
                    agreement.put("start_date", rs.getDate("start_date"));
                    agreement.put("end_date", rs.getDate("end_date"));
                    agreement.put("rent_amount", rs.getDouble("rent_amount"));
                    agreement.put("payment_status", rs.getString("payment_status"));
                    agreement.put("agreement_status", rs.getString("agreement_status"));

                    agreements.add(agreement);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching agreements: " + e.getMessage());
        }
        return agreements;
    }

}
