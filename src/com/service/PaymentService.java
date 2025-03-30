package com.service;


public interface PaymentService {
    boolean updatePayment(String agreementType, int agreementId, String paymentMethod, String paymentMode, String paymentStatus, String transactionId,String receivedDate);

    void getAllPaymentsForFarmer(int id,String type);

    void getAllPaymentsForLandowner(int id);

    void getAllPaymentsForServiceProvider(int id);
}
