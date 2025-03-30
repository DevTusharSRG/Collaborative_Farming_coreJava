package com;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.service.impl.UserServiceImpl;

public class Login_Main {

    public static void main(String[] args) {
        UserServiceImpl userServiceImpl=new UserServiceImpl();
        
        Scanner scanner=new Scanner(System.in);
        while (true) {
			System.out.println("********************************************************************************");
			System.out.println("\n1. Register");
            System.out.println("2. Login");
			// System.out.println("3. View Service Provider By Location");
            // System.out.println("4. Request Service");  
			// System.out.println("5. View All Service Requests");
			System.out.println("3. Exit");
			System.out.println("\n ********************************************************************************");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1: 
                // Enum validation for type_of_user
                List<String> allowedUserTypes = Arrays.asList("admin", "farmer", "service_provider", "landowner");
                String typeOfUser;
                while (true) {
                    System.out.println("Enter Type of User (admin, farmer, service_provider, landowner): ");

                    typeOfUser = scanner.nextLine().trim().toLowerCase();
                    if (allowedUserTypes.contains(typeOfUser)) {
                        break;
                    } else {
                        System.out.println("Invalid user type! Please enter a valid type.");
                    }
                
                }
                System.out.print("Enter Name : ");
                String name = scanner.nextLine();

                System.out.print("Enter Contact Number: ");
                String contact = scanner.nextLine();

                System.out.print("Enter Email: ");
                String email = scanner.nextLine();

                System.out.print("Enter Address: ");
                String address = scanner.nextLine();

                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                // System.out.println("Name: " + name);
                // System.out.println("Contact: " + contact);
                // System.out.println("Email: " + email);
                // System.out.println("Address: " + address);
                // System.out.println("Password: " + "*".repeat(password.length())); // Mask password display
                // System.out.println("User Type: " + typeOfUser);
                if(userServiceImpl.userRegister(name, contact, email, address, password, typeOfUser)){
                    System.out.println("Register Succesfully");
                }else{
                    System.out.println("User Exist with this credentials");
                }


                break;
                case 2:
                System.out.print("Enter Contact Number: ");
                String contact1 = scanner.next();
                
                System.out.print("Enter Password: ");
                String password1 = scanner.next();
                

                // System.out.println("Contact: " + contact1);
                // System.out.println("Password: " +password1);
                Map<String,Object> user=userServiceImpl.Login(contact1, password1);
                if(user!=null){
                    System.out.println("Welcome "+user.get("name"));
                }else{
                    System.out.println("User Not Exist with this credentials");
                }

                break;
                case 3:
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
