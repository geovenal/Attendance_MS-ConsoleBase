package com.app.admin_view;

import com.app.attendance.CoAdmin;
import com.app.attendance.Admin;
import com.app.attendance.Student;
import com.app.attendance.UserAccount;
import java.util.Scanner;

public class CoAdminTempView extends AdminMenuView {

    Scanner sc = new Scanner(System.in);
    AdminMenuView amv = new AdminMenuView();
    UserAccount userAcc = new UserAccount();
    Student student = new Student();
    Admin admin = new Admin();
    CoAdmin coAd = new CoAdmin();

    public void CoAdminProfileView(String adminId, String accId) {
        boolean running = true;
        try {
            amv.menuView(adminId, "Staffs");
            coAd.staffsRegInfo(adminId);
            System.out.print(cornerline);
            System.out.print(borderline);
            System.out.println("");
            staffsMenu(running, adminId, accId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void staffsMenu(boolean running, String adminId, String accId) {
        while (running) {
            System.out.println("---------- Please select ----------");
            System.out.println("    [1] Profile");
            System.out.println("    [2] Trainees");
            System.out.println("    [3] Classes");
            System.out.println("    [4] Add Staffs");
            System.out.println("    [5] Update Staffs");
            System.out.println("    [6] Archive Staffs");
            System.out.println("    [7] Restore Archive Staffs");
            System.out.println("    [8] Delete Staffs");
            System.out.println("    [9] Log out");
            System.out.print("Enter a choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        ProfileTempView ptv = new ProfileTempView();
                        ptv.profileMenu(running, adminId, accId);
                    case 2:
                        StudentsTempView stv = new StudentsTempView();
                        stv.studentsView(adminId, accId);
                        break;
                    case 3:
                        ClassesTempView ctv = new ClassesTempView();
                        ctv.classesView(adminId, accId);
                        break;
                    case 4:
                        staffRegForm(adminId);
                        CoAdminProfileView(adminId, accId);
                        break;
                    case 5:
                        updateStaffInfo(adminId, accId);
                        CoAdminProfileView(adminId, accId);
                        break;
                    case 6:
                        archiveStaff(sc, adminId, accId);
                        break;
                    case 7:
                        restoreStaff(sc, adminId, accId);
                        break;
                    case 8:
                        deleteStaff(sc, adminId, accId);
                        break;
                    case 9:
                        System.exit(0);
                        break;
                    default:
                        sc.nextLine();
                        System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                        CoAdminProfileView(adminId, accId);
                        break;
                }
            } catch (Exception ex) {
                sc.nextLine();
                System.out.println(RED + "Please enter an integer!\n" + RESET);
                CoAdminProfileView(adminId, accId);
            }
        }
    }

    //Choice 4
    public void staffRegForm(String adminId) {
        System.out.println("---------- Trainee Registration ----------");
        System.out.print("First Name: ");
        String userFName = sc.nextLine();
        System.out.print("Middle Name: ");
        String userMName = sc.nextLine();
        System.out.print("Last Name: ");
        String userLName = sc.nextLine();

        String role = "co-admin";
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        userAcc.createUserAccount(email, password, role);
        String userId = userAcc.getUserId(email);

        coAd.addCoAdmin(userId, adminId, userFName, userMName, userLName);
        System.out.println("");

    }

    //Choice 5
    public void updateStaffInfo(String adminId, String accId) {
        System.out.print("Enter Staff ID: ");
        String coAdId = sc.nextLine();
        String coAdAccId = coAd.getAccId(coAdId);
        System.out.println("----------------------------------");
        System.out.println("|        UPDATE INFORMATION      |");
        System.out.println("----------------------------------");
        System.out.println("|        [1] Name                |");
        System.out.println("|        [2] Password            |");
        System.out.println("|        [3] Back                |");
        System.out.println("----------------------------------");
        System.out.print("Enter a choice: ");
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    changeStaffName(adminId, accId, coAdId);
                    break;
                case 2:
                    changeStaffPass(adminId, accId, coAdId, coAdAccId);
                    break;
                case 3:
                    CoAdminProfileView(adminId, accId);
                    break;
                default:
                    sc.nextLine();
                    System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                    CoAdminProfileView(adminId, accId);
                    break;
            }
        } catch (Exception ex) {
            sc.nextLine();
            System.out.println(RED + "Please enter an integer!\n" + RESET);
            CoAdminProfileView(adminId, accId);
        }
    }

    public void changeStaffName(String adminId, String accId, String coAdId) {
        String name = " ";
        String coAd_name = " ";
        System.out.println("---------- Change Staff Name ----------");
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
                    coAd_name = "co_admin_fname";
                    coAd.updateStaffName(name, coAdId, coAd_name);
                    break;
                case 2:
                    System.out.print("Enter Middle Name: ");
                    name = sc.nextLine();
                    coAd_name = "co_admin_mname";
                    coAd.updateStaffName(name, coAdId, coAd_name);
                    break;
                case 3:
                    System.out.print("Enter Last Name: ");
                    name = sc.nextLine();
                    coAd_name = "co_admin_lname";
                    coAd.updateStaffName(name, coAdId, coAd_name);
                    break;
            }
            prompt = GREEN + "Change to " + name + " successfully!" + RESET;
            System.out.println(prompt);
            CoAdminProfileView(adminId, accId);
        } catch (Exception ex) {
            sc.nextLine();
            System.out.println(RED + "Please enter an integer!\n" + RESET);
            CoAdminProfileView(adminId, accId);
        }
    }

    public void changeStaffPass(String adminId, String accId, String coAdId, String coAdAccId) {
        System.out.println("------- Change Staff Password -------");
        System.out.print("Enter New Password: ");
        String password = sc.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPass = sc.nextLine();
        if (password.isEmpty() || confirmPass.isEmpty()) {
            prompt = RED + "Input a password" + RESET;
            System.out.println(prompt);
            CoAdminProfileView(adminId, accId);
        } else if (password.equals(confirmPass)) {
            coAd.updateStaffPass(password, coAdAccId);
            prompt = GREEN + "Change password successfully!" + RESET;
            System.out.println(prompt);
            CoAdminProfileView(adminId, accId);
        }
    }

    //Choice 6
    public void archiveStaff(Scanner sc, String adminId, String accId) {
        System.out.println("---------- Archive Staff ----------");
        System.out.print("Enter Staff ID: ");
        String coAdId = sc.nextLine();
        boolean isTrue = confirmation(sc, adminId, accId);
        if (isTrue == true) {
            coAd.archiveStaff(coAdId);
            CoAdminProfileView(adminId, accId);
        } else {
            CoAdminProfileView(adminId, accId);
        }
    }

    //Choice 7
    public void restoreStaff(Scanner sc, String adminId, String accId) {
        String coAdId = coAd.getCoAdminId(accId);
        amv.menuView(adminId, "Staffs");
        coAd.archivedstaffsInfo(adminId);
        System.out.print(cornerline);
        System.out.print(borderline);
        System.out.println("");
        System.out.println("---------- Restore Staff ----------");
        System.out.print("Enter Staff ID: ");
        coAdId = sc.nextLine();
        boolean isTrue = confirmation(sc, adminId, accId);
        if (isTrue == true) {
            coAd.restoreStaffInfo(coAdId);
            amv.menuView(adminId, "Staffs");
            coAd.staffsRegInfo(adminId);
            System.out.print(cornerline);
            System.out.print(borderline);
            System.out.println("");
        } else {
            CoAdminProfileView(adminId, accId);
        }

    }

    //Choice 8
    public void deleteStaff(Scanner sc, String adminId, String accId) {
        System.out.println("---------- Delete Staff ----------");
        System.out.print("Enter Staff ID: ");
        String coAdId = sc.nextLine();
        boolean isTrue = confirmation(sc, adminId, accId);
        if (isTrue == true) {
            coAd.deleteStaff(coAdId);
        } else {
            CoAdminProfileView(adminId, accId);
        }
    }
}
