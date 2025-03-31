package com;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.service.impl.PaymentServiceImpl;

public class Payment_Main {
    private static final AtomicInteger transactionCounter = new AtomicInteger(1010);

    private static final int userId = 3;

    private static String generateTransactionId() {
        int nextId = transactionCounter.incrementAndGet();
        return "TXN" + nextId;
    }

    public static void main(String[] args) {

        PaymentServiceImpl paymentServiceImpl = new PaymentServiceImpl();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. Do Payment");
            System.out.println("2.farmer View Payments ");
            System.out.println("3.Landowner View Payments ");
            System.out.println("4.Service Provider View Payments ");
            System.out.println("5. Exit");
            System.out.println("\n********************************************************************************");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    List<String> allowedAgreementTypes = Arrays.asList("Property", "Service");
                    String agreementType;
                    while (true) {
                        System.out.print("Enter Agreement Type (Property/Service): ");
                        agreementType = scanner.nextLine();
                        if (allowedAgreementTypes.contains(agreementType)) {
                            break;
                        } else {
                            System.out.println("Invalid agreement type! Please enter either 'Property' or 'Service'.");
                        }
                    }
                    System.out.print("Enter Agreement ID: ");
                    int agreementId = scanner.nextInt();
                    scanner.nextLine();

                    List<String> allowedPaymentMethods = Arrays.asList("UPI", "Credit Card", "Debit Card",
                            "Net Banking");
                    String paymentMethod;
                    while (true) {
                        System.out.print("Enter Payment Method (UPI, Credit Card, Debit Card, Net Banking): ");
                        paymentMethod = scanner.nextLine();
                        if (allowedPaymentMethods.contains(paymentMethod)) {
                            break;
                        } else {
                            System.out.println("Invalid payment method! Please enter a valid option.");
                        }
                    }
                    // System.out.print("Enter Payment Mode (Online/Offline): ");
                    String paymentMode = "Online";

                    // System.out.print("Enter Payment Status (Paid/Pending/Failed): ");
                    String paymentStatus = "Paid";

                    String transactionId = generateTransactionId();

                    LocalDate receivedDate = LocalDate.now();

                    if (paymentServiceImpl.updatePayment(agreementType, agreementId, paymentMethod, paymentMode,
                            paymentStatus, transactionId, receivedDate.toString())) {
                        System.out.println("\nPayment Inserted Successfully:");
                        System.out.println("Agreement Type: " + agreementType);
                        System.out.println("Agreement ID: " + agreementId);
                        System.out.println("Payment Mode: " + paymentMode);
                        System.out.println("Payment Method: " + paymentMethod);
                        System.out.println("Payment Status: " + paymentStatus);
                        System.out.println("Transaction ID: " + transactionId);
                    } else {
                        System.out.println("Invalid Details");
                    }
                    break;
                case 2:
                    List<String> allTypes = Arrays.asList("Property", "Service", "All");
                    String Type;
                    while (true) {
                        System.out.print("Enter Agreement Type (Property/Service/All): ");
                        Type = scanner.nextLine();
                        if (allTypes.contains(Type)) {
                            break;
                        } else {
                            System.out.println("Invalid agreement type! Please enter either Property/Service/All.");
                        }
                    }
                    paymentServiceImpl.getAllPaymentsForFarmer(userId, Type);
                    break;
                case 3:
                    paymentServiceImpl.getAllPaymentsForLandowner(userId);
                    break;
                case 4:
                    paymentServiceImpl.getAllPaymentsForServiceProvider(userId);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
