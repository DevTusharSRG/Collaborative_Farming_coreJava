package com;

import com.database.*;
import com.service.PropertyLeasingService;
import com.service.impl.PropertyLeasingServiceImpl;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class LandLease_Main {
    public static final int userId = 2;
    private static PropertyLeasingService propertyLeasingService;

    public static void main(String[] args) {
        try {
            // Establish database connection 
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                
            } else {
                System.out.println("Failed to connect to the database.");
            }
            propertyLeasingService = new PropertyLeasingServiceImpl();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n************************************************************************************\n");
                System.out.println("1. View available Properties for the land lease");
                System.out.println("2. Request for property");
                System.out.println("3. View Applied Agreements");
                System.out.println("4. Accept Agreement");
                System.out.println("5. View ongoing agreements");
                System.out.println("6. View completed Agreements");
                System.out.println("7. Exit");
                System.out.println("\n************************************************************************************\n");

                System.out.println("Enter Your Choice");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewAllProperties();
                        break;

                    case 2:
                    System.out.println("Enter Property ID: ");
                    int pId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Fetch property details
                    Map<String, Object> propertyDetails = propertyLeasingService.getPropertyDetails(pId);

                    if (propertyDetails == null) {
                        System.out.println("Invalid Property ID. Please try again.");
                        return;
                    }

                    // Extract values safely
                    int ownerId = (int) propertyDetails.get("owner_id");
                    double rentAmount = (double) propertyDetails.get("lease_price");

                    System.out.println("Property Details: ");
                    System.out.println("Owner ID: " + ownerId);
                    System.out.println("Rent Amount: " + rentAmount);
                    System.out.println("Location: " + propertyDetails.get("village") + ", "
                            + propertyDetails.get("taluka") + ", " + propertyDetails.get("district"));

                    System.out.println("Enter Start Date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();

                    System.out.println("Enter Lease Duration (in months): ");
                    int duration = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Calculate End Date
                    LocalDate start = LocalDate.parse(startDate);
                    LocalDate end = start.plusMonths(duration);
                    String endDate = end.toString();

                    // Apply for property
                    boolean requestSuccess = propertyLeasingService.applyForProperty(pId, ownerId, userId, startDate, endDate, rentAmount);
                    if (requestSuccess) {
                        System.out.println("Property request submitted successfully.");
                    } else {
                        System.out.println("Failed to request property.");
                    }
                    
                    
                        break;


                    case 3:
                        getAgreementDetails("Pending",userId);
                        break;

                    case 4:
                        getAgreementDetails("Accepted",userId);
                        break;

                    case 5:
                        getAgreementDetails("Active",userId);
                        break;

                    case 6:
                        getAgreementDetails("Completed",userId);
                        break;

                    case 7:
                        System.out.println("Thank You......!");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewAllProperties() {
        List<Object> properties = propertyLeasingService.viewAllProperties();
        if (properties.isEmpty()) {
            System.out.println("No available properties.");
        } else {
            properties.forEach(System.out::println);
        }
    }

    private static void getAgreementDetails(String status,int userId) {
        List<Object> agreements = propertyLeasingService.fetchAgreementsByStatus(status,userId);
        if (agreements.isEmpty()) {
            System.out.println("No agreements found for status: " + status);
        } else {
            agreements.forEach(System.out::println);
        }
    }
}
