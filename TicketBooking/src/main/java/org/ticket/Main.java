package org.ticket;

import org.ticket.entities.User;
import org.ticket.services.UserBookingService;
import org.ticket.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner sc = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        try{
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            System.out.println("Something went Wrong"+e);
            return;
        }
        while (option != 7){
            System.out.println("Choose Option");
            System.out.println("1. Sign up");
            System.out.println("2. login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = sc.nextInt();

            switch (option){
                case 1:
                    System.out.println("Enter the Username to SignUp : ");
                    String username = sc.next();
                    System.out.println("Enter the Password to SignUp");
                    String password = sc.next();
                    User userSignUp = new User(username,password,
                            UserServiceUtil.hashPassword(password),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userSignUp);
                    break;

                case 2:
                    System.out.println("Enter the Username to Login : ");
                    String loginUsername = sc.next();
                    System.out.println("Enter the Password to Login");
                    String loginPassword = sc.next();
                    User userLogin = new User(loginUsername, loginPassword,
                            UserServiceUtil.hashPassword(loginPassword), new ArrayList<>(),
                            UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(userLogin);
                        if (userBookingService.loginUser()) {
                            System.out.println("Login Successful");
                        } else {
                            System.out.println("Login Failed");
                        }
                    } catch (IOException e) {
                        System.out.println("Something went Wrong" + e);
                    }

                case 3:
                    System.out.println("Fetching your Bookings");
                    userBookingService.fetchBooking();
                    break;


            }

        }
    }
}