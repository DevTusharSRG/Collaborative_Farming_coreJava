package com.service.impl;

import com.dao.PaymentDao;
import com.dao.impl.PaymentDaoImpl;
import com.service.PaymentService;
import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDao paymentDao;

    public PaymentServiceImpl() {
        this.paymentDao = new PaymentDaoImpl();
    }

    
    public boolean updatePayment(String agreementType, int agreementId, String paymentMethod, String paymentMode,
            String paymentStatus, String transactionId, String receivedDate) {
        return paymentDao.updatePayment(agreementType, agreementId, paymentMethod, paymentMode, paymentStatus,
                transactionId, receivedDate);
    }

    
    public void getAllPaymentsForFarmer(int id, String type) {
        List<Map<String, Object>> payments = paymentDao.getAllPaymentsForFarmer(id, type);
        printPayments(payments);
    }

    
    public void getAllPaymentsForLandowner(int id) {
        List<Map<String, Object>> payments = paymentDao.getAllPaymentsForLandowner(id);
        printPayments(payments);
    }

    
    public void getAllPaymentsForServiceProvider(int id) {
        List<Map<String, Object>> payments = paymentDao.getAllPaymentsForServiceProvider(id);
        printPayments(payments);
    }

    private void printPayments(List<Map<String, Object>> payments) {
        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }

        for (Map<String, Object> payment : payments) {
            System.out.println("Payment ID: " + payment.get("payment_id") +
                    ", Agreement Type: " + payment.get("agreement_type") +
                    ", Agreement ID: " + payment.get("agreement_id") +
                    ", Payment Status: " + payment.get("payment_status") +
                    ", Total Amount: " + payment.get("total_amount") +
                    ", Amount: " + payment.get("amount") +
                    ", Brokerage: " + payment.get("brokerage"));

        }
    }
}
