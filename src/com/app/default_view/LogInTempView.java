package com.app.default_view;

import com.app.admin_view.ProfileTempView;
import com.app.attendance.Admin;
import com.app.attendance.CoAdmin;
import com.app.attendance.Student;
import com.app.attendance.UserAccount;
import com.app.student_view.StudentMenuView;
import com.app.coadmin.CoAdminProfTempView;
import com.app.student_view.StudentProfileTempView;
import java.util.Scanner;


public class LogInTempView {

    Scanner sc = new Scanner(System.in);
    UserAccount userAcc = new UserAccount();
    Admin admin = new Admin();
    Student student = new Student();
    CoAdmin coAd = new CoAdmin();

    public void logInView(String role) {
        System.out.println("---------- Log In ----------");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        boolean isTrue = userAcc.logInUserAccount(email, password);
        
        //USER ACC
        String useraccRole = userAcc.getUserRole(email);
        String useraccId = userAcc.getUserId(email);
        
        //USER ID
        String adminId = admin.getAdminId(useraccId);
        String studentId = student.getStudentId(useraccId);
        String coAdId = coAd.getCoAdminId(useraccId);
        
        String[] userInfo = {};
        
        if (!(email.isEmpty() || password.isEmpty())) {
            if ("admin".equals(role)) {
                if ("admin".equals(useraccRole)) {   
                    userInfo = new String[]{adminId, useraccId};
                    if (isTrue == true) {
                        System.out.println("Logged in Successfully!");
                        ProfileTempView ptv = new ProfileTempView();
                        ptv.profileView(userInfo[0], userInfo[1]);
                    }
                } 
                else if(!"admin".equals(useraccRole)){
                    System.out.println("Logged in Faild!");
                }
            } else if (!"admin".equals(role)) {
                if ("student".equals(useraccRole)) { 
                    userInfo = new String[]{studentId, useraccId};
                    if (isTrue == true) {
                        System.out.println("Logged in Successfully!");
                        StudentProfileTempView sptv = new StudentProfileTempView();
                        sptv.profileView(userInfo[0], userInfo[1]);
                    }
                } else if ("co-admin".equals(useraccRole)) {
                    System.out.println("Co-Admin");
                    userInfo = new String[]{coAdId, useraccId};
                    if (isTrue == true) {
                        System.out.println("Logged in Successfully!");
                        CoAdminProfTempView captv = new CoAdminProfTempView();
                        captv.profileView(userInfo[0], userInfo[1]);
                    }
                }
                else if("admin".equals(useraccRole)){
                    System.out.println("Logged in Faild!");
                }
            }
        } else {
            System.out.println("Try again");
        }
    }

    public String[] userInfo(String userId, String useraccId) {
        String[] userInfo = {userId, useraccId};
        return userInfo;
    }
}
