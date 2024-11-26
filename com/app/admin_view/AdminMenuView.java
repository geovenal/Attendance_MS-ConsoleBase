
package com.app.admin_view;

import com.app.attendance.Admin;
import com.app.attendance.Attendance;
import com.app.attendance.Classes;
import com.app.attendance.Student;
import com.app.attendance.UserAccount;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AdminMenuView {
     // ANSI escape codes for coloring text
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    
    
     //MenuProfile
    String prompt = "";
    String borderline = "+------------------------------------------------------------------------------------------------------+";
    String menuline = "+----------+----------+----------+----------+----------------------------------------------------------+";
    String cornerline = String.format("| %-80s                     |%n", " ");
    String txtlogOut = RED + " Log out" + RESET;
    String[] menuBar = new String[]{"Profile", "Staffs", "Trainee", "Classes"};
    
    Admin admin = new Admin();
    Student student = new Student();
    UserAccount userAcc = new UserAccount();
    Classes classes = new Classes();
    Attendance attendance = new Attendance();
    
    Scanner sc = new Scanner(System.in); 
     // Define a formatter to display time in HH:mm format 
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
     int hour = 0, min = 0;
     
    public void menuView(String adminId, String menubar) {
        String[] admin_name = this.getName(adminId);
        if (menubar.equals(menuBar[0])) {
            menuBar[0] = BLUE + menubar + " " + RESET;
        } else if (menubar.equals(menuBar[1])) {
            menuBar[1] = BLUE + menubar + "  " +RESET;
        } else if (menubar.equals(menuBar[2])) {
            menuBar[2] = BLUE + menubar + " " +RESET;
        }
        else if (menubar.equals(menuBar[3])) {
            menuBar[3] = BLUE + menubar + " " + RESET;
        }

        try {
            System.out.println(borderline);
            System.out.printf("| %-58s     | %-25s| %-10s |%n", " ", admin_name[0], txtlogOut);
            System.out.println(menuline);
            System.out.printf("| %-8s | %-8s | %-8s | %-8s | %-56s |%n", menuBar[0], menuBar[1], menuBar[2], menuBar[3]," ");
            System.out.println(menuline);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public String[] getName(String adminId) {
        String[] admin_name = admin.getAdminName(adminId);
        return admin_name;
    }
    public String[] getAcc(String accId) {
        String[] adminAcc = admin.getAdminAcc(accId);
        return adminAcc;
    }
    public boolean confirmation(Scanner sc, String adminId, String accId) {
        //sc.nextLine();
        boolean isTrue = false;
        System.out.print("Are you sure? (Y/N): ");
        String confirm = sc.nextLine();
        if ('Y' == confirm.toUpperCase().charAt(0)) {
            isTrue = true;
        } else if ('N' == confirm.toUpperCase().charAt(0)) {
            isTrue = false;
        } else {
            System.out.println("> Invalid Input! Please Try Again");
            confirmation(sc, adminId, accId);
        }
        return isTrue;
    }
}
