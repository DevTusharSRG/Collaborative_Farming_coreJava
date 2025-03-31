package com;

import com.service.LandownerService;
import com.service.impl.LandownerServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Landowner_Main {

    public static final int userId = 3;

    public static void main(String[] args) {

        LandownerService landownerService = new LandownerServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("********************************************************************************");
            System.out.println("\n1. View My Properties");
            System.out.println("2. Add New Property");
            System.out.println("3. View Property Requests");
            System.out.println("4. Accept Property Agreement");
            System.out.println("5. View Agreements by Status");
            System.out.println("6. Exit");
            System.out.println("\n********************************************************************************");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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

                    // Enum validation for Type of Land
                    List<String> allowedLandTypes = Arrays.asList("Irrigated Land", "Rainfed Land");
                    String typeOfLand;
                    while (true) {
                        System.out.println("Enter Type of Land (Irrigated Land,Rainfed Land): ");

                        typeOfLand = scanner.nextLine();
                        if (allowedLandTypes.contains(typeOfLand)) {
                            break;
                        } else {
                            System.out.println("Invalid user type! Please enter a valid type.");
                        }

                    }
                    //System.out.print("Enter Type of Land: ");
                    //String typeOfLand = scanner.nextLine();
                    System.out.print("Enter Land Image Path: ");
                    String landImage = scanner.nextLine();
                    System.out.print("Enter Document Image Path: ");
                    String documentImage = scanner.nextLine();
                    System.out.print("Enter Area in Acres: ");
                    double areaAcre = scanner.nextDouble();

                    System.out.print("Enter Area in Guntha: ");
                    double areaGuntha = scanner.nextDouble();
                    System.out.print("Enter Lease Price: ");
                    double leasePrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    // System.out.print("Enter Status (Available/Leased): ");
                    // String status = scanner.nextLine();
                    // System.out.print("Enter Create Date (YYYY-MM-DD): ");
                    // String createDate = scanner.nextLine();
                    String createDate = LocalDate.now().toString();

                    boolean added = landownerService.insertProperty(userId, village, taluka, district, state,
                            typeOfLand, landImage, documentImage,
                            areaAcre, leasePrice, areaGuntha,
                            "Available", createDate);
                    if (added) {
                        System.out.println("Property added successfully.");
                    } else {
                        System.out.println("Failed to add property.");
                    }
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
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
