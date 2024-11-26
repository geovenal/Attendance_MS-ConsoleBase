package com.app.student_view;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class StudentAttdTempView extends StudentMenuView {

    StudentMenuView smv = new StudentMenuView();
    
    Scanner sc = new Scanner(System.in);
    
    LocalTime timeIn = LocalTime.parse(currentTime);

    public void attendanceView(String studentId, String accId) {
        boolean running = true;
        //Classes
        String classId = smv.classId(studentId);
        String[] classInfo = smv.getClassInfo(classId);
        
        //Attendace
        String attdId = smv.getAttdId(studentId, dateNow[0]);
        String time_in = attendance.getTime(attdId, dateNow[0], "attd_time_in");
        String time_out = attendance.getTime(attdId, dateNow[0], "attd_time_out");
        LocalTime classEnd = LocalTime.parse(classInfo[3]);

        try {
            smv.menuView(studentId, "Attendance");
            System.out.printf("| %-10s  %-72s               |%n", "Class Code :", classInfo[0]);
            System.out.printf("| %-10s  %-72s               |%n", "Class Name :", classInfo[1]);
            System.out.printf("| %-10s :  %-10s %-61s               |%n", "Date", dateNow[0], dateNow[1]);
            attendance.attendanceClass(attdId, dateNow[0]);
            System.out.print(smv.cornerline);
            System.out.print(smv.borderline);
            System.out.println("");
            if (time_in.isEmpty()) {
                attendanceMenu(running, studentId, accId, "Time In", attdId);
            } else if (!time_in.isEmpty() && time_out.isEmpty()) {
                attendanceMenu(running, studentId, accId, "Time Out", attdId);
            } else {
                attendanceMenu(running, studentId, accId, RED + "Time In" + RESET, attdId);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void attendanceMenu(boolean running, String studentId, String accId, String time, String attdId) {
         
        //Classes
        String classId = smv.classId(studentId);
        String[] classInfo = smv.getClassInfo(classId);

        //Attendace
        LocalTime classStart = LocalTime.parse(classInfo[2]);
        LocalTime classEnd = LocalTime.parse(classInfo[3]);
        while (running) {
            System.out.println("---------- Please select ----------");
            System.out.println("    [1] Profile");
            System.out.println("    [2] View Record");
            System.out.println("    [3] " + time);
            System.out.println("    [4] Log out");
            System.out.print("Enter a choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        StudentProfileTempView sptv = new StudentProfileTempView();
                        sptv.profileView(studentId, accId);
                        break;
                    case 2:
                        StudentAttdRecTempView viewRec = new StudentAttdRecTempView();
                        viewRec.attdRecordView(studentId, accId);
                        break;
                    case 3:
                        prompt = GREEN + "You successfully " + time + RESET;
                        if (time.equals("Time In")) {
                            attendance.updateTime(currentTime, attdId, dateNow[0], "attd_time_in");
                            if (timeIn.isBefore(classStart)) {
                                status = "EARLY";
                            } else if (timeIn.isAfter(classStart) && timeIn.isBefore(classEnd)) {
                                status = "LATE";
                            } else if (classStart.equals(timeIn)) {
                                status = "ON TIME";
                            }
                            attendance.updateStatus(status, attdId, dateNow[0]);

                            System.out.println(prompt);
                        } else if (time.equals("Time Out")) {
                            String currentTime = smv.getCurrentTime();
                            attendance.updateTime(currentTime, attdId, dateNow[0], "attd_time_out");
                            System.out.println(prompt);

                        } else {
                            prompt = RED + "Try another day" + RESET;
                            System.out.println(prompt);
                            running = false;
                        }
                        attendanceView(studentId, accId);
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        sc.nextLine();
                        System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                        attendanceView(studentId, accId);
                        break;
                }
            } catch (Exception ex) {
                sc.nextLine();
                System.out.println(RED + "Please enter an integer!\n" + RESET);
                attendanceView(studentId, accId);
            }
        }
    }
}
