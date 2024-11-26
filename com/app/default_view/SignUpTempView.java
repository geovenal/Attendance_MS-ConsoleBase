package com.app.default_view;

import com.app.attendance.Admin;
import com.app.attendance.UserAccount;
import java.util.Scanner;

public class SignUpTempView {
    
    
    Scanner sc = new Scanner(System.in);
    UserAccount userAcc = new UserAccount();
    Admin admin = new Admin();
    
    
    public void signUptView() {
        System.out.println("---------- Sign Up ----------");
        System.out.print("First Name: ");
        String userFName = sc.nextLine();
        System.out.print("Middle Name: ");
        String userMName = sc.nextLine();
        System.out.print("Last Name: ");
        String userLName = sc.nextLine();
        
        String role = "admin";
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        
        userAcc.createUserAccount(email, password, role);
        String userId = userAcc.getUserId(email);
        
        admin.addAdmin(userId, userFName, userMName, userLName);
        System.out.println("");
    }
}