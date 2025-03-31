package com;

import com.service.GovernmentSchemeService;
import com.service.LandownerService;
import com.service.PropertyLeasingService;
import com.service.UserService;
import com.service.impl.GovernmentSchemeServiceImpl;
import com.service.impl.LandownerServiceImpl;
import com.service.impl.PropertyLeasingServiceImpl;
import com.service.impl.UserServiceImpl;
import java.time.LocalDate;
import java.util.*;

public class Main1 {

    static PropertyLeasingService propertyLeasingService = new PropertyLeasingServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserServiceImpl();
        LandownerService landownerService = new LandownerServiceImpl();
        GovernmentSchemeService schemeService = new GovernmentSchemeServiceImpl();

        int loggedInUserId = -1;
        String userType = "";

        while (true) {
            System.out.println("\n***** Collaborative Farming System *****");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Type of User (admin, farmer, service_provider, landowner): ");
                    List<String> allowedUserTypes = Arrays.asList("admin", "farmer", "service_provider", "landowner");
                    while (true) {
                        userType = scanner.nextLine().trim().toLowerCase();
                        if (allowedUserTypes.contains(userType))
                            break;
                        System.out.println("Invalid user type! Try again.");
                    }
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Contact Number: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    if (userService.userRegister(name, contact, email, address, password, userType)) {
                        System.out.println("Registered Successfully!");
                    } else {
                        System.out.println("User already exists!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Contact Number: ");
                    String loginContact = scanner.next();

                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.next();

                    Map<String, Object> user = userService.Login(loginContact, loginPassword);

                    if (user != null) {
                        System.out.println("Welcome, " + user.get("name") + "!");

                        loggedInUserId = (user.get("user_id") != null) ? (int) user.get("user_id") : -1;
                        userType = (user.get("type_of_user") != null) ? user.get("type_of_user").toString() : "";

                        if (loggedInUserId > 0) {
                            mainMenu(scanner, userType, loggedInUserId, landownerService, schemeService);
                        } else {
                            System.out.println("Login failed. Please check your credentials.");
                        }
                    } else {
                        System.out.println("User does not exist with these credentials.");
                    }

                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    public static void mainMenu(Scanner scanner, String userType, int userId,
            LandownerService landownerService, GovernmentSchemeService schemeService) {
        while (true) {

            int choice = 0;

            System.out.println("\n***** Main Menu *****");
            if (userType.equals("landowner")) {
                System.out.println("1. Manage Properties");
                System.out.println("2. Government Schemes");
                System.out.println("3. Payments");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");
                int choice1 = scanner.nextInt();
                scanner.nextLine();
                switch (choice1) {
                    case 1:
                        landownerMenu(scanner, landownerService, userId);
                        break;
                    case 2:
                        governmentSchemeMenu(scanner, schemeService, userId);
                        break;
                    case 3:

                        break;
                    case 4:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }

            } else if (userType.equals("farmer")) {
                System.out.println("1. Property Lease");
                System.out.println("2. Government Schemes");
                System.out.println("3. Logout");
                System.out.print("Enter your choice: ");
                int choice1 = scanner.nextInt();
                scanner.nextLine();

                switch (choice1) {
                    case 1:
                        landLeaseMenu(scanner, propertyLeasingService, userId);
                        break;

                    case 2:
                        governmentSchemeMenu(scanner, schemeService, userId);
                        break;

                    case 3:
                        System.out.println("Logging out...");
                        return;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } else if (userType.equals("service_provider")) {
                switch (choice) {
                    case 1:
                        if (userType.equals("landowner")) {
                            landownerMenu(scanner, landownerService, userId);
                        } else {
                            System.out.println("Access Denied!");
                        }
                        break;

                    case 2:
                        System.out.println("1. Government Schemes");
                        System.out.println("2. Logout");
                        System.out.print("Enter your choice: ");
                        int choice1 = scanner.nextInt();
                        scanner.nextLine();
                        governmentSchemeMenu(scanner, schemeService, userId);
                        break;

                    case 3:
                        System.out.println("Logging out...");
                        return;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } else {
                switch (choice) {
                    case 1:
                        if (userType.equals("landowner")) {
                            landownerMenu(scanner, landownerService, userId);
                        } else {
                            System.out.println("Access Denied!");
                        }
                        break;

                    case 2:
                        System.out.println("1. Government Schemes");
                        System.out.println("2. Logout");
                        System.out.print("Enter your choice: ");
                        int choice1 = scanner.nextInt();
                        scanner.nextLine();
                        governmentSchemeMenu(scanner, schemeService, userId);
                        break;

                    case 3:
                        System.out.println("Logging out...");
                        return;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }

        }
    }

    public static void landownerMenu(Scanner scanner, LandownerService landownerService, int userId) {
        while (true) {
            System.out.println("\n***** Landowner Menu *****");
            System.out.println("1. View My Properties");
            System.out.println("2. Add New Property");
            System.out.println("3. View Property Requests");
            System.out.println("4. Accept Property Agreement");
            System.out.println("5. View Agreements by Status");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    landownerService.getAllProperties(userId);
                    break;

                case 2:
                    System.out.print("Enter Village: ");
                    String village = scanner.nextLine();
                    System.out.print("Enter Taluka: ");
                    String taluka = scanner.nextLine();
                    System.out.print("Enter District: ");
                    String district = scanner.nextLine();
                    System.out.print("Enter State: ");
                    String state = scanner.nextLine();

                    List<String> allowedLandTypes = Arrays.asList("Irrigated Land", "Rainfed Land");
                    String typeOfLand;
                    while (true) {
                        System.out.print("Enter Type of Land (Irrigated Land, Rainfed Land): ");
                        typeOfLand = scanner.nextLine();
                        if (allowedLandTypes.contains(typeOfLand))
                            break;
                        System.out.println("Invalid type! Try again.");
                    }
                    System.out.print("Enter Area in Acres: ");
                    double areaAcre = scanner.nextDouble();
                    System.out.print("Enter Area in Guntha: ");
                    double areaGuntha = scanner.nextDouble();
                    System.out.print("Enter Lease Price: ");
                    double leasePrice = scanner.nextDouble();
                    scanner.nextLine();

                    boolean added = landownerService.insertProperty(userId, village, taluka, district, state,
                            typeOfLand, "",
                            "", areaAcre, leasePrice, areaGuntha, "Available", LocalDate.now().toString());
                    System.out.println(added ? "Property added successfully." : "Failed to add property.");
                    break;

                case 3:
                    landownerService.getAllPropertyRequests(userId);
                    break;

                case 4:
                    System.out.print("Enter Agreement ID to Accept: ");
                    int agreementId = scanner.nextInt();
                    landownerService.updateRequestForProperty(agreementId);
                    break;

                case 5:
                    System.out.print("Enter Agreement Status (Pending/Accepted/Rejected): ");
                    String agreementStatus = scanner.nextLine();
                    landownerService.getAgreementsByUserAndStatus(userId, agreementStatus);
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void governmentSchemeMenu(Scanner scanner, GovernmentSchemeService schemeService, int userId) {

        while (true) {
            System.out.println("\n***** Government Schemes Menu *****");
            System.out.println("1. View All Schemes");
            System.out.println("2. Apply for a Scheme");
            System.out.println("3. View My Applications");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<Map<String, Object>> schemes = schemeService.viewAllSchemes();
                    if (schemes.isEmpty()) {
                        System.out.println("No schemes available.");
                    } else {
                        for (Map<String, Object> scheme : schemes) {
                            System.out.println("ID: " + scheme.get("scheme_id") + ", Title: " + scheme.get("title"));
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter Scheme ID to Apply: ");
                    int schemeId = scanner.nextInt();
                    schemeService.applyForScheme(userId, schemeId);
                    break;

                case 3:
                    System.out.print("Enter Application Status: ");
                    String status = scanner.next();
                    schemeService.viewApplicationsByStatus(status);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void landLeaseMenu(Scanner scanner, PropertyLeasingService propertyLeasingService, int userId) {

        while (true) {
            System.out.println(
                    "\n************************************************************************************\n");
            System.out.println("1. View available Properties for the land lease");
            System.out.println("2. Request for property");
            System.out.println("3. View Applied Agreements");
            System.out.println("4. Accept Agreement");
            System.out.println("5. View ongoing agreements");
            System.out.println("6. View completed Agreements");
            System.out.println("7. Exit");
            System.out.println(
                    "\n************************************************************************************\n");

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
                    boolean requestSuccess = propertyLeasingService.applyForProperty(pId, ownerId, userId, startDate,
                            endDate, rentAmount);
                    if (requestSuccess) {
                        System.out.println("Property request submitted successfully.");
                    } else {
                        System.out.println("Failed to request property.");
                    }

                    break;
                case 3:
                    getAgreementDetails("Pending", userId);
                    break;

                case 4:
                    getAgreementDetails("Accepted", userId);
                    break;

                case 5:
                    getAgreementDetails("Active", userId);
                    break;

                case 6:
                    getAgreementDetails("Completed", userId);
                    break;

                case 7:
                    System.out.println("Thank You......!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

    }

    public static void viewAllProperties() {
        List<Object> properties = propertyLeasingService.viewAllProperties();
        if (properties.isEmpty()) {
            System.out.println("No available properties.");
        } else {
            properties.forEach(System.out::println);
        }
    }

    public static void getAgreementDetails(String status, int userId) {
        List<Object> agreements = propertyLeasingService.fetchAgreementsByStatus(status, userId);
        if (agreements.isEmpty()) {
            System.out.println("No agreements found for status: " + status);
        } else {
            agreements.forEach(System.out::println);
        }
    }
    
    //public static void landownerPaymentMenu(Scanner scanner,)
}
