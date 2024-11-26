package com.app.student_view;

import java.util.Scanner;

public class StudentAttdRecTempView extends StudentMenuView {

    StudentMenuView smv = new StudentMenuView();
    Scanner sc = new Scanner(System.in);

    public void attdRecordView(String studentId, String accId) {
        boolean running = true;
        try {
            smv.menuView(studentId, "View Record");
            attendance.attendanceRecord(studentId);
            System.out.print(smv.cornerline);
            System.out.print(smv.borderline);
            System.out.println(""); 
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
