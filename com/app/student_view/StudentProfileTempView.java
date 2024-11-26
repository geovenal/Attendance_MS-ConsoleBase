package com.app.student_view;

import java.time.LocalTime;
import java.util.Scanner;

public class StudentProfileTempView extends StudentMenuView {

    StudentMenuView smv = new StudentMenuView();
    Scanner sc = new Scanner(System.in);

    public void profileView(String studentId, String accId) {
        boolean running = true;
        String adminId = smv.getAdminId(studentId);
        String[] student_name = smv.getName(studentId);
        String studentName = String.format("%-6s %-6s %-6s", student_name[0], student_name[1], student_name[2]);
        String[] student_acc = smv.getAcc(accId);

        try {
            smv.addAttd(adminId, studentId, dateNow[0], dateNow[1]);
            smv.menuView(studentId, "Profile");
            System.out.printf("| %-5s  %-78s               |%n", "Name :", studentName);
            System.out.printf("| %-5s  %-78s               |%n", "Eail :", student_acc[0]);
            System.out.print(smv.cornerline);
            System.out.print(smv.borderline);
            System.out.println("");
            profileMenu(running, studentId, accId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void profileMenu(boolean running, String studentId, String accId) {
        //Classes
        String classId = smv.classId(studentId);
        String[] classInfo = smv.getClassInfo(classId);
        //Attendace
        LocalTime timeIn = LocalTime.parse(currentTime);
        String attdId = getAttdId(studentId, dateNow[0]);
        LocalTime classEnd = LocalTime.parse(classInfo[3]);
        String time_in = attendance.getTime(attdId, dateNow[0], "attd_time_in");
        String time_out = attendance.getTime(attdId, dateNow[0], "attd_time_out");

        while (running) {
            System.out.println("---------- Please select ----------");
            System.out.println("    [1] Attendance");
            System.out.println("    [2] View Record");
            System.out.println("    [3] Change Name");
            System.out.println("    [4] Change Password");
            System.out.println("    [5] Log out");
            System.out.print("Enter a choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        if (timeIn.isAfter(classEnd) && (time_in.isEmpty() && time_out.isEmpty())) {
                            attendance.updateTime("--:--", attdId, dateNow[0], "attd_time_in");
                            attendance.updateTime("--:--", attdId, dateNow[0], "attd_time_out");
                            status = "ABSENT";
                            attendance.updateStatus(status, attdId, dateNow[0]);
                        }
                        StudentAttdTempView satv = new StudentAttdTempView();
                        satv.attendanceView(studentId, accId);
                        break;
                    case 2:
                        StudentAttdRecTempView viewRec = new StudentAttdRecTempView();
                        viewRec.attdRecordView(studentId, accId);
                        break;
                    case 3:
                        changeName(studentId, accId);
                        break;
                    case 4:
                        changePass(studentId, accId);
                        break;
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        sc.nextLine();
                        System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                        profileView(studentId, accId);
                        break;
                }
            } catch (Exception ex) {
                sc.nextLine();
                System.out.println(RED + "Please enter an integer!\n" + RESET);
                profileView(studentId, accId);
            }
        }
    }

    public void changeName(String studentId, String accId) {
        String[] studentName = smv.getName(studentId);
        String student_name = " ";
        String name = " ";

        System.out.println("---------- Your Name ----------");
        System.out.println("First Name: " + studentName[0]);
        System.out.println("Middle Name: " + studentName[1]);
        System.out.println("Last Name: " + studentName[2]);
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
            profileView(studentId, accId);
        } catch (Exception ex) {
            sc.nextLine();
            System.out.println(RED + "Please enter an integer!\n" + RESET);
            profileView(studentId, accId);
        }
    }

    public void changePass(String studentId, String accId) {
        System.out.println("------- Change Your Password -------");
        System.out.print("Enter New Password: ");
        String password = sc.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPass = sc.nextLine();

        if (password.isEmpty() || confirmPass.isEmpty()) {
            prompt = RED + "Input a password" + RESET;
            System.out.println(prompt);
            changePass(studentId, accId);
        } else if (password.equals(confirmPass)) {
            student.updateStudentPass(password, accId);
            prompt = GREEN + "Change password successfully!" + RESET;
            System.out.println(prompt);
            profileView(studentId, accId);
        }
    }
}
