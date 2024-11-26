package com.app.student_view;

import com.app.attendance.Admin;
import com.app.attendance.Attendance;
import com.app.attendance.Classes;
import com.app.attendance.Student;
import com.app.attendance.UserAccount;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class StudentMenuView {

    // ANSI escape codes for coloring text
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";

    //MenuProfile
    String prompt = "";
    String borderline = "+------------------------------------------------------------------------------------------------------+";
    String menuline = "+------------+------------+-------------+--------------------------------------------------------------+";
    String cornerline = String.format("| %-80s                     |%n", " ");
    String txtlogOut = RED + " Log out" + RESET;
    String[] menuBar = new String[]{"Profile", "Attendance", "View Record"};

    //Attendance
    String[] dateNow = getCurrentDate();
    String status = "";
    String currentTime = getCurrentTime();
 
    Admin admin = new Admin();
    Student student = new Student();
    UserAccount userAcc = new UserAccount();
    Classes classes = new Classes();
    Attendance attendance = new Attendance();
    
    
    public void menuView(String studentId, String menubar) {
        String[] student_name = this.getName(studentId);
        if (menubar.equals(menuBar[0])) {
            menuBar[0] = BLUE + menubar + "   " + RESET;
        } else if (menubar.equals(menuBar[1])) {
            menuBar[1] = BLUE + menubar + RESET;
        } else if (menubar.equals(menuBar[2])) {
            menuBar[2] = BLUE + menubar + RESET;
        }

        try {
            System.out.println(borderline);
            System.out.printf("| %-58s     | %-25s| %-10s |%n", " ", student_name[0], txtlogOut);
            System.out.println(menuline);
            System.out.printf("| %-10s | %-10s | %-10s | %-60s |%n", menuBar[0], menuBar[1], menuBar[2], " ");
            System.out.println(menuline);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public String classId(String studentId) {
        String classId = student.getStudClassId(studentId);
        return classId;
    }

    public String[] getName(String studentId) {
        String[] student_name = student.getStudent_Name(studentId);
        return student_name;
    }

    public String[] getAcc(String accId) {
        String[] studentAcc = student.getStudentAcc(accId);
        return studentAcc;
    }

    public String[] getClassInfo(String classId) {
        String[] classInfo = classes.classInfo(classId);
        return classInfo;
    }
    public String getAdminId(String studentId){
        String adminId = student.getStudAdminId(studentId);
        return adminId;
    }
    public String[] getCurrentDate() {
        //Get the current Date
        LocalDate currentDate = LocalDate.now();

        //Format the Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String date = currentDate.format(formatter);

        //Get the Day of the Week
        String day = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String[] dateNow = {date, day};

        return dateNow;
    }

    public String getCurrentTime() {
        // Define a formatter to display time in HH:mm format 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Get the current time 
        LocalTime currentTime = LocalTime.now();
        //Format the current time 
        String timeNow = currentTime.format(formatter);

        return timeNow;
    }

    public String getAttdId(String studentId, String date) {
        String attdId = attendance.getAttdId(studentId, date);
        return attdId;
    }

    public void addAttd(String adminId, String studentId, String date, String day) {
        String attdId = getAttdId(studentId, date);
        String getDate = attendance.getDate(attdId);
        String classId = student.getStudClassId(studentId);
        if (!date.equals(getDate) && getDate.isEmpty()) {
            attendance.addAttendace(date, day ,studentId, classId, adminId);
        }
    }

}
