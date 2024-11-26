package com.app.admin_view;

import com.app.attendance.Admin;
import com.app.default_view.LogInTempView;
import java.util.Scanner;

public class ProfileTempView extends AdminMenuView {

    Scanner sc = new Scanner(System.in);

    AdminMenuView amv = new AdminMenuView();

    public void profileView(String adminId, String accId) {
        boolean running = true;
        String[] admin_name = amv.getName(adminId);
        String adminName = String.format("%-6s %-6s %-6s", admin_name[0], admin_name[1], admin_name[2]);
        String[] admin_acc = amv.getAcc(accId);

        try {
            amv.menuView(adminId, "Profile");
            System.out.printf("| %-5s  %-78s               |%n", "Name :", adminName);
            System.out.printf("| %-5s  %-78s               |%n", "Eail :", admin_acc[0]);
            System.out.print(cornerline);
            System.out.print(borderline);
            System.out.println("");
            profileMenu(running, adminId, accId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void profileMenu(boolean running, String adminId, String accId) {
        while (running) {
            System.out.println("---------- Please select ----------");
            System.out.println("    [1] Staffs");
            System.out.println("    [2] Trainees");
            System.out.println("    [3] Classes");
            System.out.println("    [4] Change Name");
            System.out.println("    [5] Change Email");
            System.out.println("    [6] Change Password");
            System.out.println("    [7] Log out");
            System.out.print("Enter a choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        CoAdminTempView catv = new CoAdminTempView();
                        catv.CoAdminProfileView(adminId, accId);
                        break;
                    case 2:
                        StudentsTempView stv = new StudentsTempView();
                        stv.studentsView(adminId, accId);
                        break;
                    case 3:
                        ClassesTempView ctv = new ClassesTempView();
                        ctv.classesView(adminId, accId);
                        break;
                    case 4:
                        changeName(adminId, accId);
                        break;
                    case 5:
                        changeEmail(adminId, accId);
                        break;
                    case 6:
                        changePass(adminId, accId);
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        sc.nextLine();
                        System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                        profileView(adminId, accId);
                        break;
                }
            } catch (Exception ex) {
                sc.nextLine();
                System.out.println(RED + "Please enter an integer!\n" + RESET);
                profileView(adminId, accId);
            }
        }
    }
    
    
    //choice 4
    public void changeName(String adminId, String accId) {
        String[] adminName = admin.getAdminName(adminId);
        String admin_name = " ";
        String name = " ";

        System.out.println("---------- Your Name ----------");
        System.out.println("First Name: " + adminName[0]);
        System.out.println("Middle Name: " + adminName[1]);
        System.out.println("Last Name: " + adminName[2]);
        System.out.println("------- Change Your Name -------");
        System.out.println("---------- Please select ----------");
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
                    name = sc.nextLine();
                    admin_name = "admin_fname";
                    admin.updateAdminName(name, adminId, admin_name);
                    break;
                case 2:
                    System.out.print("Enter Middle Name: ");
                    name = sc.nextLine();
                    admin_name = "admin_mname";
                    admin.updateAdminName(name, adminId, admin_name);
                    break;
                case 3:
                    System.out.print("Enter Last Name: ");
                    name = sc.nextLine();
                    admin_name = "admin_lname";
                    admin.updateAdminName(name, adminId, admin_name);
                    break;
            }
            prompt = GREEN + "Change to " + name + " successfully!" + RESET;
            System.out.println(prompt);
            profileView(adminId, accId);
        } catch (Exception ex) {
            sc.nextLine();
            System.out.println(RED + "Please enter an integer!\n" + RESET);
            profileView(adminId, accId);
        }
    }
    
    //choice 5
    public void changeEmail(String adminId, String accId) {
        String[] admin_acc = admin.getAdminAcc(accId);

        System.out.println("---------- Your Email ----------");
        System.out.println("Email : " + admin_acc[0]);
        System.out.println("------- Change Your Email -------");
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        if (email.isEmpty()) {
            prompt = RED + "Input an Email Address" + RESET;
            System.out.println(prompt);
            changeEmail(adminId, accId);
        } else {
            admin.updateAdminEmail(email, accId);
            prompt = GREEN + "Change Email to " + email + " successfully!" + RESET;
            System.out.println(prompt);
            profileView(adminId, accId);
        }
    }
    
    //choice 6
    public void changePass(String adminId, String accId) {
        System.out.println("------- Change Your Password -------");
        System.out.print("Enter New Password: ");
        String password = sc.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPass = sc.nextLine();

        if (password.isEmpty() || confirmPass.isEmpty()) {
            prompt = RED + "Input a password" + RESET;
            System.out.println(prompt);
            changePass(adminId, accId);
        } else if (password.equals(confirmPass)) {
            admin.updateAdminPass(password, accId);
            prompt = GREEN + "Change password successfully!" + RESET;
            System.out.println(prompt);
            profileView(adminId, accId);
        }
    }
}
