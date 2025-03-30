package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.PaymentDao;
import com.database.DBConnection;

public class PaymentDaoImpl implements PaymentDao {

    private Connection connection;

    public PaymentDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    public boolean updatePayment(String agreementType, int agreementId, String paymentMethod, String paymentMode,
        String paymentStatus, String transactionId, String receivedDate) {
    
    String sql = "UPDATE Payment SET payment_method = ?, payment_mode = ?, payment_status = ?, transaction_id = ?, received_date = ? " +
                 "WHERE agreement_id = ? AND agreement_type = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

        pstmt.setString(1, paymentMethod);
        pstmt.setString(2, paymentMode);
        pstmt.setString(3, paymentStatus);
        pstmt.setString(4, transactionId);
        pstmt.setDate(5, java.sql.Date.valueOf(receivedDate)); // Convert String to SQL Date
        pstmt.setInt(6, agreementId);
        pstmt.setString(7, agreementType);

        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0; // Return true if update is successful

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    public List<Map<String, Object>> getAllPaymentsForFarmer(int id, String type) {

        List<Map<String, Object>> paymentList = new ArrayList<>();
        List<Integer> agreementIds = new ArrayList<>();
        List<Integer> propertyAgreementIds = new ArrayList<>();

        if ("Property".equals(type)) {
            String fetchAgreementIdSql = "SELECT agreement_id FROM property_agreements WHERE tenant_id = ?";
            String fetchPropertyPaymentsSql = "SELECT * FROM Payment WHERE agreement_id = ? AND agreement_type = 'Property'";

            try (PreparedStatement fetchStmt = connection.prepareStatement(fetchAgreementIdSql)) {
                fetchStmt.setInt(1, id);
                ResultSet rs = fetchStmt.executeQuery();

                while (rs.next()) {
                    agreementIds.add(rs.getInt("agreement_id"));
                }

                if (agreementIds.isEmpty()) {
                    System.out.println("No Agreement found.");
                    return paymentList;
                }

                try (PreparedStatement paymentStmt = connection.prepareStatement(fetchPropertyPaymentsSql)) {
                    for (int agreementId : agreementIds) {
                        paymentStmt.setInt(1, agreementId);
                        ResultSet payments = paymentStmt.executeQuery();

                        while (payments.next()) {
                            Map<String, Object> payment = new HashMap<>();
                            payment.put("payment_id", payments.getInt("payment_id"));
                            payment.put("agreement_type", payments.getString("agreement_type"));
                            payment.put("agreement_id", payments.getInt("agreement_id"));
                            payment.put("payment_status", payments.getString("payment_status"));
                            payment.put("total_amount", payments.getDouble("total_amount"));
                            payment.put("amount", payments.getDouble("tenant_payment"));
                            payment.put("brokerage", payments.getDouble("brokerage"));
                            paymentList.add(payment);
                        }
                    }
                    agreementIds.clear();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if ("Service".equals(type)) {
            String fetchAgreementIdSql = "SELECT agreement_id FROM agreement WHERE user_id = ?";
            String fetchServicePaymentsSql = "SELECT * FROM Payment WHERE agreement_id = ? AND agreement_type = 'Service'";

            try (PreparedStatement fetchStmt = connection.prepareStatement(fetchAgreementIdSql)) {
                fetchStmt.setInt(1, id);
                ResultSet rs = fetchStmt.executeQuery();

                while (rs.next()) {
                    agreementIds.add(rs.getInt("agreement_id"));
                }

                if (agreementIds.isEmpty()) {
                    System.out.println("No Service Agreement found.");
                    return paymentList;
                }

                try (PreparedStatement paymentStmt = connection.prepareStatement(fetchServicePaymentsSql)) {
                    for (int agreementId : agreementIds) {
                        paymentStmt.setInt(1, agreementId);
                        ResultSet payments = paymentStmt.executeQuery();

                        while (payments.next()) {
                            Map<String, Object> payment = new HashMap<>();
                            payment.put("payment_id", payments.getInt("payment_id"));
                            payment.put("agreement_type", payments.getString("agreement_type"));
                            payment.put("agreement_id", payments.getInt("agreement_id"));
                            payment.put("payment_status", payments.getString("payment_status"));
                            payment.put("total_amount", payments.getDouble("total_amount"));
                            payment.put("amount", payments.getDouble("tenant_payment"));
                            payment.put("brokerage", payments.getDouble("brokerage"));
                            paymentList.add(payment);
                        }
                    }
                    agreementIds.clear();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
                String fetchAgreementIdSql = "SELECT agreement_id FROM agreement WHERE user_id = ?";
                String fetchPropertyAgreementIdSql = "SELECT agreement_id FROM property_agreements WHERE tenant_id = ?";
                String fetchServicePaymentsSql = "SELECT * FROM Payment WHERE agreement_id = ? AND agreement_type = 'Service'";
                String fetchPropertyPaymentsSql = "SELECT * FROM Payment WHERE agreement_id = ? AND agreement_type = 'Property'";
            
                try {
                    
                    
            
                    // Fetch agreement IDs from `agreement` table (for Service agreements)
                    try (PreparedStatement fetchStmt = connection.prepareStatement(fetchAgreementIdSql)) {
                        fetchStmt.setInt(1, id);
                        ResultSet rs = fetchStmt.executeQuery();
            
                        while (rs.next()) {
                            agreementIds.add(rs.getInt("agreement_id"));
                        }
                    }
            
                    // Fetch agreement IDs from `property_agreements` table (for Property agreements)
                    try (PreparedStatement fetchStmt = connection.prepareStatement(fetchPropertyAgreementIdSql)) {
                        fetchStmt.setInt(1, id);
                        ResultSet rs = fetchStmt.executeQuery();
            
                        while (rs.next()) {
                            propertyAgreementIds.add(rs.getInt("agreement_id"));
                        }
                    }
            
                    if (agreementIds.isEmpty() && propertyAgreementIds.isEmpty()) {
                        System.out.println("No Agreements found.");
                        return paymentList;
                    }
            
                    // Fetch payments for Service agreements
                    try (PreparedStatement paymentStmt = connection.prepareStatement(fetchServicePaymentsSql)) {
                        for (int agreementId : agreementIds) {
                            paymentStmt.setInt(1, agreementId);
                            ResultSet payments = paymentStmt.executeQuery();
            
                            while (payments.next()) {
                                Map<String, Object> payment = new HashMap<>();
                                payment.put("payment_id", payments.getInt("payment_id"));
                                payment.put("agreement_type", payments.getString("agreement_type"));
                                payment.put("agreement_id", payments.getInt("agreement_id"));
                                payment.put("payment_status", payments.getString("payment_status"));
                                payment.put("total_amount", payments.getDouble("total_amount"));
                                payment.put("amount", payments.getDouble("tenant_payment"));
                                payment.put("brokerage", payments.getDouble("brokerage"));
                                paymentList.add(payment);
                            }
                        }
                    }
            
                    // Fetch payments for Property agreements
                    try (PreparedStatement paymentStmt = connection.prepareStatement(fetchPropertyPaymentsSql)) {
                        for (int agreementId : propertyAgreementIds) {
                            paymentStmt.setInt(1, agreementId);
                            ResultSet payments = paymentStmt.executeQuery();
            
                            while (payments.next()) {
                                Map<String, Object> payment = new HashMap<>();
                                payment.put("payment_id", payments.getInt("payment_id"));
                                payment.put("agreement_type", payments.getString("agreement_type"));
                                payment.put("agreement_id", payments.getInt("agreement_id"));
                                payment.put("payment_status", payments.getString("payment_status"));
                                payment.put("total_amount", payments.getDouble("total_amount"));
                                payment.put("amount", payments.getDouble("tenant_payment"));
                                payment.put("brokerage", payments.getDouble("brokerage"));
                                paymentList.add(payment);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
        
        
        return paymentList;
    }

    /// need to see data from property_agreements and property 
    public List<Map<String, Object>> getAllPaymentsForLandowner(int id) {
        List<Map<String, Object>> paymentList = new ArrayList<>();
        List<Integer> agreementIds = new ArrayList<>();
        String fetchAgreementIdSql = "SELECT agreement_id FROM property_agreements WHERE owner_id = ?";
        String fetchServicePaymentsSql = "SELECT * FROM Payment WHERE agreement_id = ? AND agreement_type = 'Property'";

        try (PreparedStatement fetchStmt = connection.prepareStatement(fetchAgreementIdSql)) {
            fetchStmt.setInt(1, id);
            ResultSet rs = fetchStmt.executeQuery();

            while (rs.next()) {
                agreementIds.add(rs.getInt("agreement_id"));
            }

            if (agreementIds.isEmpty()) {
                System.out.println("No Service Agreement found.");
                return paymentList;
            }

            try (PreparedStatement paymentStmt = connection.prepareStatement(fetchServicePaymentsSql)) {
                for (int agreementId : agreementIds) {
                    paymentStmt.setInt(1, agreementId);
                    ResultSet payments = paymentStmt.executeQuery();

                    while (payments.next()) {
                        Map<String, Object> payment = new HashMap<>();
                        payment.put("payment_id", payments.getInt("payment_id"));
                        payment.put("agreement_type", payments.getString("agreement_type"));
                        payment.put("agreement_id", payments.getInt("agreement_id"));
                        payment.put("payment_status", payments.getString("payment_status"));
                        payment.put("total_amount", payments.getDouble("total_amount"));
                        payment.put("amount", payments.getDouble("owner_payment"));
                        payment.put("brokerage", payments.getDouble("brokerage"));
                        paymentList.add(payment);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentList;
    }

    /// need to see data from agreements and property 
    public List<Map<String, Object>> getAllPaymentsForServiceProvider(int id) {
        List<Map<String, Object>> paymentList = new ArrayList<>();
        List<Integer> agreementIds = new ArrayList<>();
        String fetchAgreementIdSql = "SELECT agreement_id FROM agreement WHERE service_provider_id = ?";
        String fetchServicePaymentsSql = "SELECT * FROM Payment WHERE agreement_id = ? AND agreement_type = 'Service'";

        try (PreparedStatement fetchStmt = connection.prepareStatement(fetchAgreementIdSql)) {
            fetchStmt.setInt(1, id);
            ResultSet rs = fetchStmt.executeQuery();

            while (rs.next()) {
                agreementIds.add(rs.getInt("agreement_id"));
            }

            if (agreementIds.isEmpty()) {
                System.out.println("No Service Agreement found.");
                return paymentList;
            }

            try (PreparedStatement paymentStmt = connection.prepareStatement(fetchServicePaymentsSql)) {
                for (int agreementId : agreementIds) {
                    paymentStmt.setInt(1, agreementId);
                    ResultSet payments = paymentStmt.executeQuery();

                    while (payments.next()) {
                        Map<String, Object> payment = new HashMap<>();
                        payment.put("payment_id", payments.getInt("payment_id"));
                        payment.put("agreement_type", payments.getString("agreement_type"));
                        payment.put("agreement_id", payments.getInt("agreement_id"));
                        payment.put("payment_status", payments.getString("payment_status"));
                        payment.put("total_amount", payments.getDouble("total_amount"));
                        payment.put("amount", payments.getDouble("owner_payment"));
                        payment.put("brokerage", payments.getDouble("brokerage"));
                        paymentList.add(payment);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentList;
    }

}
