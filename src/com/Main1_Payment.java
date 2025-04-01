package com;

import com.service.GovernmentSchemeService;
import com.service.LandownerService;
import com.service.PaymentService;
import com.service.PropertyLeasingService;
import com.service.ServiceProviderService;
import com.service.UserService;
import com.service.impl.GovernmentSchemeServiceImpl;
import com.service.impl.LandownerServiceImpl;
import com.service.impl.PaymentServiceImpl;
import com.service.impl.PropertyLeasingServiceImpl;
import com.service.impl.ServiceProviderServiceImpl;
import com.service.impl.UserServiceImpl;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main1_Payment {

    static PropertyLeasingService propertyLeasingService = new PropertyLeasingServiceImpl();
    private static final AtomicInteger transactionCounter = new AtomicInteger(1010);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserServiceImpl();
        LandownerService landownerService = new LandownerServiceImpl();
        GovernmentSchemeService schemeService = new GovernmentSchemeServiceImpl();
        PaymentService paymentService = new PaymentServiceImpl();
        ServiceProviderService serviceProviderService = new ServiceProviderServiceImpl();

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
                            mainMenu(scanner, userType, loggedInUserId, landownerService, schemeService, paymentService,
                                    serviceProviderService);
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
            LandownerService landownerService, GovernmentSchemeService schemeService, PaymentService paymentService,
            ServiceProviderService serviceProviderService) {
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
                        landownerPaymentMenu(scanner, paymentService, userId);
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
                System.out.println("3. Payment");
                System.out.println("4. Logout");
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
                        farmerPaymentMenu(scanner, paymentService, userId);
                        break;

                    case 4:
                        System.out.println("Logging out...");
                        return;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } else if (userType.equals("service_provider")) {
                System.out.println("1. Manage Services");
                System.out.println("2. Government Schemes");
                System.out.println("3. Payments");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");
                int choice1 = scanner.nextInt();
                scanner.nextLine();
                switch (choice1) {
                    case 1:
                        serviceProviderMenu(scanner, serviceProviderService, userId);
                        break;

                    case 2:
                        governmentSchemeMenu(scanner, schemeService, userId);
                        break;
                    case 3:
                        serviceProviderPaymentMenu(scanner, paymentService, userId);
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        return;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } else {
                // switch (choice) {
                // case 1:
                // if (userType.equals("landowner")) {
                // landownerMenu(scanner, landownerService, userId);
                // } else {
                // System.out.println("Access Denied!");
                // }
                // break;

                // case 2:
                // System.out.println("1. Government Schemes");
                // System.out.println("2. Logout");
                // System.out.print("Enter your choice: ");
                // int choice1 = scanner.nextInt();
                // scanner.nextLine();
                // governmentSchemeMenu(scanner, schemeService, userId);
                // break;

                // case 3:
                // System.out.println("Logging out...");
                // return;

                // default:
                // System.out.println("Invalid choice! Try again.");
                // }
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
            System.out.println("7. Back to Main Menu");
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
                    //System.out.println("Thank You......!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

    }

    public static void serviceProviderMenu(Scanner scanner, ServiceProviderService serviceProviderService, int userId) {
        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. View Services");
            System.out.println("2. Add Service");
            System.out.println("3. View Added Service ");
            System.out.println("4. View Service Request");
            System.out.println("5. Accept Service Request");
            System.out.println("6. View Service by Status");
            System.out.println("7. Back to Main Menu");
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

                    boolean isInserted = serviceProviderService.insertService(serviceId, userId, price, area,
                            available,
                            village, taluka, district, state, description, serviceType, createDate.toString());
                    if (isInserted) {
                        System.out.println("Service added successfully!");
                    } else {
                        System.out.println("Failed to add service.");
                    }
                    break;
                case 3:
                    serviceProviderService.getAllMyServices(userId);
                    break;

                case 4:
                    // System.out.print("Enter Service Provider ID: ");
                    // int providerId = scanner.nextInt();
                    System.out.print("Enter Service ID: ");
                    int servId = scanner.nextInt();
                    serviceProviderService.getAllServiceRequest(userId, servId);
                    break;

                case 5:
                    System.out.print("Enter User ID to Accept Request: ");
                    int requestId = scanner.nextInt();
                    boolean isUpdated = serviceProviderService.updateServiceRequestById(requestId, userId);
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
                    serviceProviderService.getAllServicesByStatus(userId, status);
                    break;
                case 7:
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");

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

    private static String generateTransactionId() {
        int nextId = transactionCounter.incrementAndGet();
        return "TXN" + nextId;
    }

    public static void landownerPaymentMenu(Scanner scanner, PaymentService paymentService, int user_id) {
        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. View Payments");
            System.out.println("2. Back to Main Menu");
            System.out.println("\n********************************************************************************");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    paymentService.getAllPaymentsForLandowner(user_id);
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public static void farmerPaymentMenu(Scanner scanner, PaymentService paymentService, int user_id) {
        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. Do Payment");
            System.out.println("2. View Payments ");
            System.out.println("3. Back to Main Menu");
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

                    if (paymentService.updatePayment(agreementType, agreementId, paymentMethod, paymentMode,
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
                    paymentService.getAllPaymentsForFarmer(user_id, Type);
                    break;
                case 3:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public static void serviceProviderPaymentMenu(Scanner scanner, PaymentService paymentService, int user_id) {
        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. View Payments");
            System.out.println("2. Back to Main Menu");
            System.out.println("\n********************************************************************************");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    paymentService.getAllPaymentsForServiceProvider(user_id);
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

}
