 package com;

// import com.service.GovernmentSchemeService;
// import com.service.impl.GovernmentSchemeServiceImpl;
// import java.util.InputMismatchException;
// import java.util.List;
// import java.util.Map;
// import java.util.Scanner;

// public class GovernmentApplication_Main {
//     public static void main(String[] args) {
//         GovernmentSchemeService schemeService = new GovernmentSchemeServiceImpl();
//         Scanner scanner = new Scanner(System.in);

//         while (true) {
//             System.out.println("\n===== Government Scheme Management =====");
//             System.out.println("1. Add Government Scheme");
//             System.out.println("2. Apply for Scheme");
//             System.out.println("3. View Applications by Status");
//             System.out.println("4. Update Application Status");
//             System.out.println("5. Exit");
//             System.out.print("Enter your choice: ");

//             int choice;
//             try {
//                 choice = scanner.nextInt();
//                 scanner.nextLine(); // Consume newline
//             } catch (InputMismatchException e) {
//                 System.out.println("Invalid input! Please enter a number between 1 and 5.");
//                 scanner.nextLine(); // Clear invalid input
//                 continue;
//             }

//             switch (choice) {
//                 case 1:
//                     addGovernmentScheme(schemeService, scanner);
//                     break;
//                 case 2:
//                     applyForScheme(schemeService, scanner);
//                     break;
//                 case 3:
//                     viewApplicationsByStatus(schemeService, scanner);
//                     break;
//                 case 4:
//                     updateApplicationStatus(schemeService, scanner);
//                     break;
//                 case 5:
//                     System.out.println("Exiting... Thank you!");
//                     scanner.close();
//                     return;
//                 default:
//                     System.out.println("Invalid choice! Please select a valid option.");
//             }
//         }
//     }

//     private static void addGovernmentScheme(GovernmentSchemeService schemeService, Scanner scanner) {
//         System.out.println("\n--- Add Government Scheme ---");

//         System.out.print("Enter Title: ");
//         String title = scanner.nextLine().trim();
//         if (title.isEmpty()) {
//             System.out.println("Title cannot be empty!");
//             return;
//         }

//         double benefitAmount;
//         while (true) {
//             System.out.print("Enter Benefit Amount: ");
//             try {
//                 benefitAmount = scanner.nextDouble();
//                 scanner.nextLine(); // Consume newline
//                 if (benefitAmount <= 0) {
//                     System.out.println("Benefit amount must be greater than 0.");
//                     continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 System.out.println("Invalid input! Please enter a valid amount.");
//                 scanner.nextLine(); // Clear invalid input
//             }
//         }

//         System.out.print("Enter Start Date (YYYY-MM-DD): ");
//         String startDate = scanner.nextLine().trim();
//         if (!isValidDate(startDate)) {
//             System.out.println("Invalid date format! Please use YYYY-MM-DD.");
//             return;
//         }

//         System.out.print("Enter Last Date (YYYY-MM-DD): ");
//         String lastDate = scanner.nextLine().trim();
//         if (!isValidDate(lastDate)) {
//             System.out.println("Invalid date format! Please use YYYY-MM-DD.");
//             return;
//         }

//         System.out.print("Enter Description: ");
//         String description = scanner.nextLine().trim();
//         if (description.isEmpty()) {
//             System.out.println("Description cannot be empty!");
//             return;
//         }

//         schemeService.addGovernmentScheme(title, benefitAmount, startDate, lastDate, description);
//     }

//     private static void applyForScheme(GovernmentSchemeService schemeService, Scanner scanner) {
//         System.out.println("\n--- Apply for Government Scheme ---");

//         int farmerId;
//         while (true) {
//             System.out.print("Enter Farmer ID: ");
//             try {
//                 farmerId = scanner.nextInt();
//                 if (farmerId <= 0) {
//                     System.out.println("Farmer ID must be a positive number.");
//                     continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 System.out.println("Invalid input! Please enter a valid numeric Farmer ID.");
//                 scanner.nextLine();
//             }
//         }

//         int schemeId;
//         while (true) {
//             System.out.print("Enter Scheme ID: ");
//             try {
//                 schemeId = scanner.nextInt();
//                 if (schemeId <= 0) {
//                     System.out.println("Scheme ID must be a positive number.");
//                     continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 System.out.println("Invalid input! Please enter a valid numeric Scheme ID.");
//                 scanner.nextLine();
//             }
//         }

//         schemeService.applyForScheme(farmerId, schemeId);
//     }

//     private static void viewApplicationsByStatus(GovernmentSchemeService schemeService, Scanner scanner) {
//         System.out.println("\n--- View Applications by Status ---");
//         System.out.print("Enter Status (Pending/Approved/Rejected): ");
//         String status = scanner.nextLine().trim();
//         if (!status.equalsIgnoreCase("Pending") && !status.equalsIgnoreCase("Approved") && !status.equalsIgnoreCase("Rejected")) {
//             System.out.println("Invalid status! Please enter Pending, Approved, or Rejected.");
//             return;
//         }

//         List<Map<String, Object>> applications = schemeService.viewApplicationsByStatus(status);
//         if (applications.isEmpty()) {
//             System.out.println("No applications found for the given status.");
//         } else {
//             System.out.println("\n--- Applications with Status: " + status + " ---");
//             for (Map<String, Object> application : applications) {
//                 System.out.println("Application ID: " + application.get("application_id"));
//                 System.out.println("Scheme ID: " + application.get("scheme_id"));
//                 System.out.println("Farmer ID: " + application.get("farmer_id"));
//                 System.out.println("Register Date: " + application.get("register_date"));
//                 System.out.println("Status: " + application.get("status"));
//                 System.out.println("---------------------------");
//             }
            
//         }
//     }

//     private static void updateApplicationStatus(GovernmentSchemeService schemeService, Scanner scanner) {
//         System.out.println("\n--- Update Application Status ---");

//         int applicationId;
//         while (true) {
//             System.out.print("Enter Application ID: ");
//             try {
//                 applicationId = scanner.nextInt();
//                 scanner.nextLine(); // Consume newline
//                 if (applicationId <= 0) {
//                     System.out.println("Application ID must be a positive number.");
//                     continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 System.out.println("Invalid input! Please enter a valid numeric Application ID.");
//                 scanner.nextLine();
//             }
//         }

//         System.out.print("Enter New Status (Accepted/Rejected): ");
//         String status = scanner.nextLine().trim();
//         if (!status.equalsIgnoreCase("Accepted") && !status.equalsIgnoreCase("Rejected")) {
//             System.out.println("Invalid status! Please enter Approved or Rejected.");
//             return;
//         }

//         schemeService.updateApplicationStatus(applicationId, status);
//     }

//     private static boolean isValidDate(String date) {
//         return date.matches("\\d{4}-\\d{2}-\\d{2}");
//     }
// }


import com.service.GovernmentSchemeService;
import com.service.impl.GovernmentSchemeServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GovernmentApplication_Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GovernmentSchemeService schemeService = new GovernmentSchemeServiceImpl();

        //System.out.print("Enter User ID: "); 
        int userId = 2; // User ID is now predefined and passed in function calls

        while (true) {
            System.out.println("\nGovernment Scheme Management System");
            System.out.println("1. View All Schemes");
            System.out.println("2. Add New Government Scheme");
            System.out.println("3. Apply for a Government Scheme");
            System.out.println("4. Update Application Status");
            System.out.println("5. View Applications by Status");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<Map<String, Object>> schemes = schemeService.viewAllSchemes();
                    if (schemes.isEmpty()) {
                        System.out.println("No government schemes available.");
                    } else {
                        System.out.println("Available Government Schemes:");
                        for (Map<String, Object> scheme : schemes) {
                            System.out.println("ID: " + scheme.get("scheme_id") +
                                    ", Title: " + scheme.get("title") +
                                    ", Benefit: " + scheme.get("benefitAmount") +
                                    ", Start Date: " + scheme.get("startDate") +
                                    ", Last Date: " + scheme.get("lastDate") +
                                    ", Description: " + scheme.get("description"));
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter Scheme Title: ");
                    scanner.nextLine(); // Consume newline
                    String title = scanner.nextLine();
                    System.out.print("Enter Benefit Amount: ");
                    double benefitAmount = scanner.nextDouble();
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String startDate = scanner.next();
                    System.out.print("Enter Last Date (YYYY-MM-DD): ");
                    String lastDate = scanner.next();
                    System.out.print("Enter Description: ");
                    scanner.nextLine(); // Consume newline
                    String description = scanner.nextLine();

                    schemeService.addGovernmentScheme(title, benefitAmount, startDate, lastDate, description);
                    break;

                case 3: //  Apply for Scheme using userId
                    System.out.print("Enter Scheme ID to Apply: ");
                    int schemeId = scanner.nextInt();
                    schemeService.applyForScheme(userId, schemeId);
                    break;

                case 4: //  Update Application Status using userId
                    System.out.print("Enter Application ID: ");
                    int applicationId = scanner.nextInt();
                    System.out.print("Enter New Status (Approved/Rejected/Pending): ");
                    String status = scanner.next();
                    schemeService.updateApplicationStatus(applicationId, status);
                    break;

                case 5: //  View Applications by Status using userId
                    System.out.print("Enter Application Status to Filter: ");
                    String filterStatus = scanner.next();
                    List<Map<String, Object>> applications = schemeService.viewApplicationsByStatus(filterStatus);
                    if (applications.isEmpty()) {
                        System.out.println("No applications found with status: " + filterStatus);
                    } else {
                        System.out.println("Applications with status " + filterStatus + ":");
                        for (Map<String, Object> app : applications) {
                            System.out.println("Application ID: " + app.get("application_id") +
                                    ", Scheme ID: " + app.get("scheme_id") +
                                    ", User ID: " + app.get("user_id") +
                                    ", Status: " + app.get("status"));
                        }
                    }
                    break;

                case 6:
                    System.out.println("Exiting... Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }

    }
}
