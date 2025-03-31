package com;

import com.service.ServiceRequestService;
import com.service.impl.ServiceRequestServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // User Id
    public static final int userId = 2;

    public static void main(String[] args) {
        ServiceRequestService serviceRequestService = new ServiceRequestServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. View Properties");
            System.out.println("2. View all Services");
            System.out.println("3. View Service Provider By Location");
            System.out.println("4. Request Service");
            System.out.println("5. View All Service Requests");
            System.out.println("6. Exit");
            System.out.println("\n ********************************************************************************");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    serviceRequestService.getPropertiesById(userId);
                    break;

                case 2:
                    serviceRequestService.viewAllServices();
                    break;

                case 3:
                    System.out.print("Enter Service ID: ");
                    int serviceId1 = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter State Name: ");
                    String state = scanner.nextLine();
                    System.out.print("Enter the District: ");
                    String district = scanner.nextLine();
                    System.out.print("Enter the Taluka: ");
                    String taluka = scanner.nextLine();
                    System.out.print("Enter the Village: ");
                    String village = scanner.nextLine();

                    if (!isValidId(serviceId1, "Service ID") || !isValidLocation(state, "State") ||
                            !isValidLocation(district, "District") || !isValidLocation(taluka, "Taluka") ||
                            !isValidLocation(village, "Village")) {
                        break;
                    }

                    serviceRequestService.getServiceProviderByLocation(serviceId1, state, district, taluka, village);
                    break;

                case 4:
                    System.out.print("Enter Service ID: ");
                    int serviceId = scanner.nextInt();
                    System.out.print("Enter Service Provider ID: ");
                    int serviceProviderId = scanner.nextInt();
                    System.out.print("Enter Property ID: ");
                    int propertyId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Enter Time Duration (Days): ");
                    int duration = scanner.nextInt();

                    // Validate inputs
                    if (!isValidId(serviceId, "Service ID") || !isValidId(serviceProviderId, "Service Provider ID") ||
                            !isValidId(propertyId, "Property ID") || !isValidDate(startDate) ||
                            !isValidDuration(duration)) {
                        break;
                    }

                    // Calculate cost
                    double cost = 0.0;
                    for (Map<String, Object> provider : ServiceRequestServiceImpl.serviceProviders) {
                        if (provider.get("serviceProviderId").equals(serviceProviderId)) {
                            cost = (double) provider.get("price");
                        }
                    }

                    // Format the start and end dates
                    LocalDate startDateFormat = LocalDate.parse(startDate);
                    LocalDate endDate = startDateFormat.plusDays(duration);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedEndDate = endDate.format(formatter);

                    System.out.println("\nService Request Details:");
                    System.out.println("User ID: " + userId);
                    System.out.println("Service ID: " + serviceId);
                    System.out.println("Service Provider ID: " + serviceProviderId);
                    System.out.println("Property ID: " + propertyId);
                    System.out.println("Start Date: " + startDate);
                    System.out.println("Duration (Days): " + duration);
                    System.out.println("End Date: " + formattedEndDate);
                    System.out.println("Cost: " + cost);

                    // Call the service method if all inputs are valid
                    boolean success = serviceRequestService.requestService(userId, serviceId, serviceProviderId,
                            propertyId, startDate, formattedEndDate, duration, cost);
                    if (success) {
                        System.out.println("Service request submitted successfully.");
                    } else {
                        System.out.println("Failed to submit service request.");
                    }
                    break;

                case 5:
                    serviceRequestService.viewServiceRequests(userId);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Validation Methods

    private static boolean isValidId(int id, String fieldName) {
        if (id <= 0) {
            System.out.println(fieldName + " must be a positive number.");
            return false;
        }
        return true;
    }

    private static boolean isValidDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            if (date.isBefore(LocalDate.now())) {
                System.out.println("Start date must be a future date.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
            return false;
        }
    }

    private static boolean isValidDuration(int duration) {
        if (duration <= 0) {
            System.out.println("Duration must be a positive number.");
            return false;
        }
        return true;
    }

    private static boolean isValidLocation(String location, String fieldName) {
        if (location == null || location.trim().isEmpty()) {
            System.out.println(fieldName + " cannot be empty.");
            return false;
        }
        return true;
    }
}
