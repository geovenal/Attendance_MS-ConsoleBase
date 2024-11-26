package com.app.admin_view;

import com.app.attendance.Admin;
import com.app.attendance.Attendance;
import com.app.attendance.Classes;
import com.app.attendance.Student;
import com.app.attendance.UserAccount;
import com.app.default_view.LogInTempView;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClassesTempView extends AdminMenuView {

    Scanner sc = new Scanner(System.in);
    AdminMenuView amv = new AdminMenuView();
    Admin admin = new Admin();
    Classes classes = new Classes();
    Student student = new Student();
    Attendance attendance = new Attendance();
    ProfileTempView ptv = new ProfileTempView();

    public void classesView(String adminId, String accId) {
        boolean running = true;
        try {
            amv.menuView(adminId, "Classes");
            classes.retrieveUsingInnerJoin();
            System.out.print(cornerline);
            System.out.print(borderline);
            System.out.println("");
            classesMenu(running, adminId, accId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void classesMenu(boolean running, String adminId, String accId) {
        while (running) {
            System.out.println("---------- Please select ----------");
            System.out.println("    [1] Profile");
            System.out.println("    [2] Staffs");
            System.out.println("    [3] Trainees");
            System.out.println("    [4] View Class Attendance");
            System.out.println("    [5] Add Trainee");
            System.out.println("    [6] Add Class");
            System.out.println("    [7] Update Class");
            System.out.println("    [8] Delete Class");
            System.out.println("    [9] Log out");
            System.out.print("Enter a choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        ptv.profileView(adminId, accId);
                        break;
                    case 2:
                        CoAdminTempView catv = new CoAdminTempView();
                        catv.CoAdminProfileView(adminId, accId);
                        break;
                    case 3:
                        StudentsTempView stv = new StudentsTempView();
                        stv.studentsView(adminId, accId);
                        break;
                    case 4:
                        viewClassAttd(adminId);
                        break;
                    case 5:
                        classStudentRegForm(adminId, accId);
                        classesView(adminId, accId);
                        break;
                    case 6:
                        classRegForm();
                        classesView(adminId, accId);
                        break;
                    case 7:
                        updateClass();
                        classesView(adminId, accId);
                        break;
                    case 8:
                        deleteClass(adminId, accId);
                        classesView(adminId, accId);
                    case 9:
                        System.exit(0);
                        break;
                    default:
                        sc.nextLine();
                        System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                        classesView(adminId, accId);
                        break;
                }
            } catch (Exception ex) {
                sc.nextLine();
                System.out.println(RED + "Please enter an integer!\n" + RESET);
                classesView(adminId, accId);
            }
        }
    }

    //Choice 4
    public void viewClassAttd(String adminId) {
        System.out.println("---------- View Class Attendance ----------");
        System.out.print("Enter class code: ");
        String classCode = sc.nextLine();
        System.out.print("Enter date (mm-dd-yyyy): ");
        String date = sc.nextLine();
        amv.menuView(adminId, "Classes");

        attendance.classAttdView(classCode, adminId, date);
        System.out.print(cornerline);
        System.out.print(borderline);
        System.out.println("");
    }

    //Choice 5
    public void classStudentRegForm(String adminId, String accId) {
        System.out.println("---------- Trainee List ----------");
        student.studentList(adminId);
        System.out.println("---------- Class Registration ----------");
        System.out.print("Class Code: ");
        String classCode = sc.nextLine();
        System.out.print("Student ID: ");
        String studentId = sc.nextLine();
        String classId = classes.getClassId(classCode);
        boolean isTrue = student.updateStudentClass(classId, studentId);
        if (isTrue == true) {
            System.out.println("---------- Trainee List ----------");
            student.studentsClass(adminId);
            System.out.println("");
        }
        else{
            classesView(adminId, accId);
        }
    }

    //Choice 6
    public void classRegForm() {
        System.out.println("---------- Class Registration ----------");
        System.out.print("Class Code: ");
        String classCode = sc.nextLine();
        System.out.print("Class Name: ");
        String className = sc.nextLine();
        System.out.println("---------- Class Start ----------");
        System.out.print("Enter hour (0-23): ");
        hour = sc.nextInt();
        System.out.print("Enter minute (0-59): ");
        min = sc.nextInt();

        // Choose a specific time 
        LocalTime startClass = LocalTime.of(hour, min);

        String formattedClassStart = startClass.format(formatter);
        System.out.println("Class Start (HH:mm): " + formattedClassStart);

        System.out.println("---------- Class End ----------");
        System.out.print("Enter hour (0-23): ");
        hour = sc.nextInt();
        System.out.print("Enter minute (0-59): ");
        min = sc.nextInt();

        // Choose a specific time 
        LocalTime endClass = LocalTime.of(hour, min);

        String formattedClassEnd = endClass.format(formatter);
        System.out.println("Class End (HH:mm): " + formattedClassEnd);
        classes.addClass(classCode, className, formattedClassStart, formattedClassEnd);
    }

    //Choice 7
    public void updateClass() {
        String col = "";
        String content = "";
        System.out.println("---------- Update Class ----------");
        System.out.print("Enter Class Code: ");
        String classCode = sc.nextLine();
        String classId = classes.getClassId(classCode);
        System.out.println("---------- Please Select ----------");
        System.out.println("    [1] Code");
        System.out.println("    [2] Name");
        System.out.println("    [3] Class Start");
        System.out.println("    [4] Class End");
        System.out.print("Enter a choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter a new code: ");
                content = sc.nextLine();
                col = "class_code";
                break;
            case 2:
                System.out.print("Enter a new name: ");
                content = sc.nextLine();
                col = "class_name";
                break;
            case 3:
                System.out.println("---------- Class Start ----------");
                System.out.print("Enter hour (0-23): ");
                hour = sc.nextInt();
                System.out.print("Enter minute (0-59): ");
                min = sc.nextInt();

                // Choose a specific time 
                LocalTime startClass = LocalTime.of(hour, min);

                String formattedClassStart = startClass.format(formatter);
                System.out.println("Class Start (HH:mm): " + formattedClassStart);
                content = formattedClassStart;
                col = "class_time_in";
                break;
            case 4:
                System.out.println("---------- Class End ----------");
                System.out.print("Enter hour (0-23): ");
                hour = sc.nextInt();
                System.out.print("Enter minute (0-59): ");
                min = sc.nextInt();

                // Choose a specific time 
                LocalTime endClass = LocalTime.of(hour, min);

                String formattedClassEnd = endClass.format(formatter);
                System.out.println("Class End (HH:mm): " + formattedClassEnd);
                content = formattedClassEnd;
                col = "class_time_in";
        }
        classes.updateClass(content, classId, col);
    }

    //Choice 8
    public void deleteClass(String adminId, String accId) {
        System.out.println("---------- Delete a Class ----------");
        System.out.print("Enter class code: ");
        String classCode = sc.nextLine();
        boolean isTrue = confirmation(sc, adminId, accId);
        if (isTrue == true) {
            classes.deleteClass(classCode);
        } else {
            classesView(adminId, accId);
        }
    }

}
