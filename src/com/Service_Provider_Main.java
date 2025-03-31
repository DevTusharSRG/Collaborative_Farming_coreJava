package com;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.service.ServiceProviderService;
import com.service.impl.ServiceProviderServiceImpl;

public class Service_Provider_Main {
    private final static int serviceProviderId = 2;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ServiceProviderService serviceProviderService = new ServiceProviderServiceImpl();

        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. View Services");
            System.out.println("2. Add Service");
            System.out.println("3. View Added Service ");
            System.out.println("4. View Service Request");
            System.out.println("5. Accept Service Request");
            System.out.println("6. View Service by Status");
            System.out.println("7. Exit");
            System.out.println("\n********************************************************************************");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    serviceProviderService.getServices();
                    break;

                case 2:
                List<String> allowedServiceTypes = Arrays.asList("Equipment", "Testing", "Labour", "Development");
                    String serviceType;

                    while (true) {
                        System.out.println("Enter Service Type (Equipment, Testing, Labour, Development): ");
                        serviceType = scanner.nextLine().trim();

                        if (allowedServiceTypes.contains(serviceType)) {
                            break;
                        } else {
                            System.out.println("Invalid service type! Please enter a valid type.");
                        }
                    }
                    System.out.print("Enter Service ID: ");
                    int serviceId = scanner.nextInt();
                    // System.out.print("Enter User ID: ");
                    // int userId = scanner.nextInt();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Area: ");
                    double area = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    // System.out.print("Enter Availability (Yes/No): ");
                    String available = "true";
                    System.out.print("Enter Village: ");
                    String village = scanner.nextLine();
                    System.out.print("Enter Taluka: ");
                    String taluka = scanner.nextLine();
                    System.out.print("Enter District: ");
                    String district = scanner.nextLine();
                    System.out.print("Enter State: ");
                    String state = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    
                    // System.out.print("Enter Creation Date (YYYY-MM-DD): ");
                    LocalDate createDate = LocalDate.now();

                    boolean isInserted = serviceProviderService.insertService(serviceId, serviceProviderId, price, area,
                            available,
                            village, taluka, district, state, description, serviceType, createDate.toString());
                    if (isInserted) {
                        System.out.println("Service added successfully!");
                    } else {
                        System.out.println("Failed to add service.");
                    }
                    break;
                case 3:
                    serviceProviderService.getAllMyServices(serviceProviderId);
                    break;

                case 4:
                    // System.out.print("Enter Service Provider ID: ");
                    // int providerId = scanner.nextInt();
                    System.out.print("Enter Service ID: ");
                    int servId = scanner.nextInt();
                    serviceProviderService.getAllServiceRequest(serviceProviderId, servId);
                    break;

                case 5:
                    System.out.print("Enter User ID to Accept Request: ");
                    int requestId = scanner.nextInt();
                    boolean isUpdated = serviceProviderService.updateServiceRequestById(requestId, serviceProviderId);
                    if (isUpdated) {
                        System.out.println("Service request accepted successfully!");
                    } else {
                        System.out.println("Failed to accept service request No Service Request Found.");
                    }
                    break;

                case 6:
                    // System.out.print("Enter Service Provider ID: ");
                    // int spId = scanner.nextInt();
                    // scanner.nextLine();
                    List<String> allowedStatuses = Arrays.asList("pending", "accepted", "rejected", "completed");
                    String status;

                    while (true) {
                        System.out.print("Enter Status (Pending/Accepted/Rejected/Completed): ");
                        status = scanner.nextLine().trim().toLowerCase();

                        if (allowedStatuses.contains(status)) {
                            break;
                        } else {
                            System.out.println("Invalid status! Please enter a valid status.");
                        }
                    }
                    serviceProviderService.getAllServicesByStatus(serviceProviderId, status);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }

    }
}
