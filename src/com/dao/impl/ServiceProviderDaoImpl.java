package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.ServiceProviderDao;
import com.database.DBConnection;

public class ServiceProviderDaoImpl implements ServiceProviderDao {
    private Connection connection;

    public ServiceProviderDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<Map<String, Object>> getServices() {
        List<Map<String, Object>> services = new ArrayList<>();
        String sql = "SELECT * FROM services";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> service = new HashMap<>();
                service.put("service_id", rs.getInt("service_id"));
                service.put("service_name", rs.getString("service_name"));
                services.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public List<Map<String, Object>> getAllMyServices(int serviceProviderId) {
        List<Map<String, Object>> services = new ArrayList<>();
        String sql = "SELECT sp.price AS price,s.service_id AS service_id, s.service_name AS service_name, sp.area AS area,sp.available AS available " +
        "FROM service_provider sp " +
        "JOIN Services s ON sp.service_id = s.service_id " +
        "WHERE sp.user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, serviceProviderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> service = new HashMap<>();
                service.put("service_id", rs.getString("service_id"));
                service.put("service_name", rs.getString("service_name"));
                service.put("price", rs.getString("price"));
                service.put("area", rs.getString("area"));
                service.put("available", rs.getString("available"));
                services.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public boolean insertService(int serviceId, int userId, double price, double area, String available, String village,
            String taluka, String district, String state, String description, String serviceType, String createDate) {
        String sql = "INSERT INTO service_provider (service_id, user_id, price, area, available, village, taluka, district, state, description, service_type, create_date) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, serviceId);
            pstmt.setInt(2, userId);
            pstmt.setDouble(3, price);
            pstmt.setDouble(4, area);
            pstmt.setBoolean(5, Boolean.parseBoolean(available));
            pstmt.setString(6, village);
            pstmt.setString(7, taluka);
            pstmt.setString(8, district);
            pstmt.setString(9, state);
            pstmt.setString(10, description);
            pstmt.setString(11, serviceType);
            pstmt.setDate(12, java.sql.Date.valueOf(createDate));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
public List<Map<String, Object>> getAllServiceRequest(int serviceProviderId, int serviceId) {
    List<Map<String, Object>> serviceRequests = new ArrayList<>();

    String sql = "SELECT a.agreement_id, a.user_id, u.name AS user_name, u.contact, u.email, " +
                 "       a.service_id, s.service_name,  " +
                 "       a.status, a.start_date, a.end_date, " +
                 "       a.time_duration AS time_duration, a.cost, a.create_date " +
                 "FROM agreement a " +
                 "JOIN users u ON a.user_id = u.user_id " +
                 "JOIN Services s ON a.service_id = s.service_id " +
                 "LEFT JOIN Property p ON a.property_id = p.property_id " +
                 "WHERE a.service_id = ? AND a.service_provider_id = ? AND a.status='pending'";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, serviceId);
        pstmt.setInt(2, serviceProviderId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Map<String, Object> request = new HashMap<>();
            request.put("agreement_id", rs.getInt("agreement_id"));
            request.put("user_id", rs.getInt("user_id"));
            request.put("user_name", rs.getString("user_name"));
            request.put("contact", rs.getString("contact"));
            //request.put("email", rs.getString("email"));
            //request.put("service_id", rs.getInt("service_id"));
            request.put("service_name", rs.getString("service_name"));
            //request.put("property_id", rs.getInt("property_id"));
            //request.put("property_name", rs.getString("property_name"));
            request.put("status", rs.getString("status"));
            request.put("start_date", rs.getDate("start_date"));
            request.put("end_date", rs.getDate("end_date"));
            request.put("time_duration", rs.getInt("time_duration")); // Duration in days
            request.put("cost", rs.getDouble("cost"));
            //request.put("create_date", rs.getDate("create_date"));

            serviceRequests.add(request);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return serviceRequests;
}


public boolean updateServiceRequestById(int userId, int serviceProviderId) {
    String fetchServiceIdSql = "SELECT service_id FROM agreement WHERE user_id = ? AND service_provider_id = ?";
    String updateAgreementSql = "UPDATE agreement SET status = 'accepted' WHERE user_id = ? AND service_provider_id = ?";
    String rejectOtherRequestsSql = "UPDATE agreement SET status = 'rejected' WHERE service_id = ? AND NOT (user_id = ? AND service_provider_id = ?)";
    String updateServiceProviderSql = "UPDATE service_provider SET available = 'false' WHERE service_id = ?";

    try (PreparedStatement fetchStmt = connection.prepareStatement(fetchServiceIdSql);
         PreparedStatement updateAgreementStmt = connection.prepareStatement(updateAgreementSql);
         PreparedStatement rejectOtherStmt = connection.prepareStatement(rejectOtherRequestsSql);
         PreparedStatement updateServiceProviderStmt = connection.prepareStatement(updateServiceProviderSql)) {

        // Fetch service_id from agreement table
        fetchStmt.setInt(1, userId);
        fetchStmt.setInt(2, serviceProviderId);
        ResultSet rs = fetchStmt.executeQuery();

        if (!rs.next()) {
            System.out.println("No service found for the given user ID and service provider ID.");
            connection.rollback();
            return false;
        }
        int serviceId = rs.getInt("service_id");

        // Update agreement status to 'Accepted' for the selected user and provider
        updateAgreementStmt.setInt(1, userId);
        updateAgreementStmt.setInt(2, serviceProviderId);
        if (updateAgreementStmt.executeUpdate() <= 0) {
            System.out.println("Failed to update agreement status.");
            connection.rollback();
            return false;
        }

        // Reject other requests related to the same service_id
        rejectOtherStmt.setInt(1, serviceId);
        rejectOtherStmt.setInt(2, userId);
        rejectOtherStmt.setInt(3, serviceProviderId);
        rejectOtherStmt.executeUpdate(); // No rollback needed as rejection is optional

        // Update service availability in service_provider table
        updateServiceProviderStmt.setInt(1, serviceId);
        if (updateServiceProviderStmt.executeUpdate() <= 0) {
            System.out.println("Failed to update service availability.");
            connection.rollback();
            return false;
        }

        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    @Override
public List<Map<String, Object>> getAllServicesByStatus(int serviceProviderId, String status) {
    List<Map<String, Object>> services = new ArrayList<>();

    String sql = "SELECT a.agreement_id, a.user_id, u.name AS user_name, u.contact, u.email, " +
                 "       a.service_id, s.service_name," +
                 "       a.status, a.start_date, a.end_date, " +
                 "       a.time_duration AS time_duration, a.cost, a.create_date " +
                 "FROM agreement a " +
                 "JOIN users u ON a.user_id = u.user_id " +
                 "JOIN Services s ON a.service_id = s.service_id " +
                 "LEFT JOIN Property p ON a.property_id = p.property_id " +
                 "WHERE a.service_provider_id = ? AND a.status = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, serviceProviderId);
        pstmt.setString(2, status);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Map<String, Object> service = new HashMap<>();
            //service.put("user_id", rs.getInt("user_id"));
            //service.put("email", rs.getString("email"));
            //service.put("service_id", rs.getInt("service_id"));
            //service.put("property_id", rs.getInt("property_id"));
            // service.put("property_name", rs.getString("property_name"));
            // service.put("create_date", rs.getDate("create_date"));
            service.put("agreement_id", rs.getInt("agreement_id"));
            service.put("user_name", rs.getString("user_name"));
            service.put("contact", rs.getString("contact"));
            service.put("service_name", rs.getString("service_name"));
            service.put("status", rs.getString("status"));
            service.put("start_date", rs.getDate("start_date"));
            service.put("end_date", rs.getDate("end_date"));
            service.put("time_duration", rs.getInt("time_duration")); // Duration in days
            service.put("cost", rs.getDouble("cost"));

            services.add(service);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return services;
}


}
