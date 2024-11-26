package com.app.coadmin;

import com.app.student_view.StudentProfileTempView;
import com.app.student_view.StudentAttdRecTempView;
import java.util.Scanner;

public class CoAdminProfTempView extends CoAdminMenuView {
    private final CoAdminMenuView ca = new CoAdminMenuView();
    private final Scanner sc = new Scanner(System.in);

    // Method to display the CoAdmin profile
    public void profileView(String coAdminId, String accId) {
        String[] coAdminData = ca.getCoAdminById(coAdminId);
        String email = ca.getCoAdminEmailByAccId(accId);

        try {
            menuView(coAdminId, "Profile");
            System.out.printf("| %-5s  %-78s               |%n", "Name :",String.join(" ", coAdminData));
            System.out.printf("| %-5s  %-78s               |%n", "Email :", email);
            System.out.println(cornerline);
            System.out.println(borderline);
            profileMenu(coAdminId, accId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Method to show the menu bar
    public void menuView(String coAdminId, String menubar) {
        String[] coAdminName = this.getCoAdminById(coAdminId);

        // Highlight the selected menu
        for (int i = 0; i < menuBar.length; i++) {
            if (menuBar[i].equals(menubar)) {
                menuBar[i] = BLUE + menubar + RESET;
            } else {
                menuBar[i] = menuBar[i].replace(BLUE, "").replace(RESET, "");
            }
        }

        try {
            System.out.println(borderline);
            System.out.printf("| %-58s     | %-25s| %-10s |%n", " ", coAdminName[0], txtlogOut);
            System.out.println(menuline);
            System.out.printf("| %-10s | %-10s | %-10s | %-60s |%n", menuBar[0], menuBar[1], menuBar[2], " ");
            System.out.println(menuline);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void profileMenu(String coAdminId, String accId) {
        while (true) {
            System.out.println("---------- Please select ----------");
            System.out.println("    [1] Trainees");
            System.out.println("    [2] View Records");
            System.out.println("    [3] Change Name");
            System.out.println("    [4] Change Password");
            System.out.println("    [5] Log out");
            System.out.print("Enter a choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine(); 
                switch (choice) {
                    case 1:
                        StudentProfileTempView sptv = new StudentProfileTempView();
                        sptv.profileView(coAdminId, accId);
                        break;
                    case 2:
                        StudentAttdRecTempView sartv = new StudentAttdRecTempView();
                        sartv.attdRecordView(coAdminId, accId);
                    case 3:
                        changeName(coAdminId, accId);
                        break;
                    case 4:
                        changePass(coAdminId, accId);
                        break;
                    case 5:
                        System.exit(0);
                        return;
                    default:
                        System.out.println(RED + "Invalid choice! Please select a valid option.\n" + RESET);
                }
            } catch (Exception ex) {
                sc.nextLine(); // Clear the scanner buffer
                System.out.println(RED + "Please enter a valid number!\n" + RESET);
            }
        }
    }
    
    public void changeName(String coAdminId, String accId) {
        String[] coAdminData = ca.getCoAdminById(coAdminId);
        String newName = "";
        String column = "";

        System.out.println("---------- Your Name ----------");
        System.out.println("First Name: " + coAdminData[0]);
        System.out.println("Middle Name: " + coAdminData[1]);
        System.out.println("Last Name: " + coAdminData[2]);
        System.out.println("------- Change Your Name -------");
        System.out.println("    [1] First Name");
        System.out.println("    [2] Middle Name");
        System.out.println("    [3] Last Name");
        System.out.print("Enter a choice: ");

        try {
            int choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.print("Enter First Name: ");
                    newName = sc.nextLine();
                    column = "co_admin_fname";
                    break;
                case 2:
                    System.out.print("Enter Middle Name: ");
                    newName = sc.nextLine();
                    column = "co_admin_mname";
                    break;
                case 3:
                    System.out.print("Enter Last Name: ");
                    newName = sc.nextLine();
                    column = "co_admin_lname";
                    break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }
            ca.updateCoAdminName(coAdminId, column, newName);
            System.out.println(GREEN + "Name changed successfully to " + newName + "!" + RESET);
            profileView(coAdminId, accId);
        } catch (Exception ex) {
            sc.nextLine();
            System.out.println(RED + "Please enter a valid option!\n" + RESET);
        }
    }

    public void changePass(String coAdminId, String accId) {
        System.out.println("------- Change Your Password -------");
        System.out.print("Enter New Password: ");
        String password = sc.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPass = sc.nextLine();

        if (password.isEmpty() || confirmPass.isEmpty()) {
            System.out.println(RED + "Password cannot be empty!" + RESET);
        } else if (password.equals(confirmPass)) {
            ca.updateCoAdminPassword(accId, password);
            System.out.println(GREEN + "Password changed successfully!" + RESET);
            profileView(coAdminId, accId);
        } else {
            System.out.println(RED + "Passwords do not match!" + RESET);
        }
    }
}