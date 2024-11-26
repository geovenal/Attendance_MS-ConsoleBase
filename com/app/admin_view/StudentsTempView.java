package com.app.admin_view;

import com.app.attendance.Student;
import com.app.attendance.UserAccount;
import com.app.attendance.Admin;
import com.app.default_view.LogInTempView;
import java.util.Scanner;

public class StudentsTempView extends AdminMenuView {

    Scanner sc = new Scanner(System.in);
    AdminMenuView amv = new AdminMenuView();
    UserAccount userAcc = new UserAccount();
    Student student = new Student();
    Admin admin = new Admin();

    public void studentsView(String adminId, String accId) {
        boolean running = true;
        try {
            amv.menuView(adminId, "Trainee");
            student.studentsRegInfo(adminId);
            System.out.print(cornerline);
            System.out.print(borderline);
            System.out.println("");
            studentsMenu(running, adminId, accId);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void studentsMenu(boolean running, String adminId, String accId) {       
        while (running) {
            System.out.println("---------- Please select ----------");
            System.out.println("    [1] Profile");
            System.out.println("    [2] Staffs");
            System.out.println("    [3] Classes");
            System.out.println("    [4] Add Trainees");
            System.out.println("    [5] Update Trainees");
            System.out.println("    [6] Delete Trainees");
            System.out.println("    [7] Log out");
            System.out.print("Enter a choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        ProfileTempView ptv = new ProfileTempView();
                        ptv.profileView(adminId, accId);
                        break;
                    case 2:
                        CoAdminTempView catv = new CoAdminTempView();
                        catv.CoAdminProfileView(adminId, accId);
                        break;
                    case 3:
                        ClassesTempView ctv = new ClassesTempView();
                        ctv.classesView(adminId, accId);
                        break;
                    case 4:
                        studentRegForm(adminId);
                        studentsView(adminId, accId);
                        break;
                    case 5:
                        updateTraineeInfo(adminId, accId);
                        studentsView(adminId, accId);
                        break;
                    case 6:
                        deleteTrainee();
                        studentsView(adminId, accId);
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        sc.nextLine();
                        System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                        studentsView(adminId, accId);
                        break;
                }
            } catch (Exception ex) {
                sc.nextLine();
                System.out.println(RED + "Please enter an integer!\n" + RESET);
                studentsView(adminId, accId);
            }
        }
    }

    //Choice 4
    public void studentRegForm(String adminId) {
        System.out.println("---------- Trainee Registration ----------");
        System.out.print("First Name: ");
        String userFName = sc.nextLine();
        System.out.print("Middle Name: ");
        String userMName = sc.nextLine();
        System.out.print("Last Name: ");
        String userLName = sc.nextLine();

        String role = "student";
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        userAcc.createUserAccount(email, password, role);
        String userId = userAcc.getUserId(email);

        student.addStudent(userId, adminId, userFName, userMName, userLName);
        System.out.println("");

    }

    //Choice 5
    public void updateTraineeInfo(String adminId, String accId) {
        System.out.print("Enter Trainee ID: ");
        String studentId = sc.nextLine();
        String studAccId = student.getAccId(studentId);
        System.out.println("---------- Update Trainee Information ----------");
        System.out.println("    [1] Name");
        System.out.println("    [2] Password");
        System.out.println("    [3] Back");
        System.out.print("Enter a choice: ");
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    changeTraineeName(adminId, accId, studentId);
                    break;
                case 2:
                    changeTraineePass(adminId, accId, studentId, studAccId);
                    break;
                case 3:
                    studentsView(adminId, accId);
                    break;
                default:
                    sc.nextLine();
                    System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                    studentsView(adminId, accId);
                    break;
            }
        } catch (Exception ex) {
            sc.nextLine();
            System.out.println(RED + "Please enter an integer!\n" + RESET);
            studentsView(adminId, accId);
        }
    }

    public void changeTraineeName(String adminId, String accId, String studentId) {
        String name = " ";
        String student_name = " ";
        System.out.println("---------- Change Trainee Name ----------");
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
                    student_name = "student_fname";
                    student.updateStudentName(name, studentId, student_name);
                    break;
                case 2:
                    System.out.print("Enter Middle Name: ");
                    name = sc.nextLine();
                    student_name = "student_mname";
                    student.updateStudentName(name, studentId, student_name);
                    break;
                case 3:
                    System.out.print("Enter Last Name: ");
                    name = sc.nextLine();
                    student_name = "student_lname";
                    student.updateStudentName(name, studentId, student_name);
                    break;
            }
            prompt = GREEN + "Change to " + name + " successfully!" + RESET;
            System.out.println(prompt);
            studentsView(adminId, accId);
        } catch (Exception ex) {
            sc.nextLine();
            System.out.println(RED + "Please enter an integer!\n" + RESET);
            studentsView(adminId, accId);
        }
    }
    public void changeTraineePass(String adminId, String accId, String studentId, String studAccId) {
        System.out.println("------- Change Trainee Password -------");
        System.out.print("Enter New Password: ");
        String password = sc.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPass = sc.nextLine();
        if (password.isEmpty() || confirmPass.isEmpty()) {
            prompt = RED + "Input a password" + RESET;
            System.out.println(prompt);
            studentsView(adminId, accId);
        } else if (password.equals(confirmPass)) {
            student.updateStudentPass(password, studAccId);
            prompt = GREEN + "Change password successfully!" + RESET;
            System.out.println(prompt);
            studentsView(adminId, accId);
        }
    }

    //Choice 6
    public void deleteTrainee() {
        System.out.println("---------- Delete Trainee ----------");
        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine();
        student.deleteTrainee(studentId);
    }
}
