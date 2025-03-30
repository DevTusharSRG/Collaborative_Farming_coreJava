package com.dao;

import java.util.List;
import java.util.Map;

public interface PaymentDao {

    boolean updatePayment(String agreementType, int agreementId, String paymentMethod, String paymentMode, String paymentStatus, String transactionId,String receivedDate);

    List<Map<String,Object>> getAllPaymentsForFarmer(int id,String type);

    List<Map<String,Object>> getAllPaymentsForLandowner(int id);

    List<Map<String,Object>> getAllPaymentsForServiceProvider(int id);
    
}
