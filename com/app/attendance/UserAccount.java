package com.app.attendance;

import com.app.db.DbConnection;
import com.app.default_view.LogInTempView;

public class UserAccount extends DbConnection {

    // ANSI escape codes for coloring text
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static String prompt = "";

    //CREATE API
    public void createUserAccount(String userEmail, String userPass, String userRole) {
        String query = "INSERT INTO tblusers_acc(user_email, user_pass, user_role)"
                + "VALUES(?,?,?)";
        String[] value = {userEmail, userPass, userRole};
        try {
            prepStatement(query, value);

            if (userEmail.isEmpty() || userPass.isEmpty() || userRole.isEmpty()) {
                System.out.println("Fields cannot be empty");
            } else {
                prep.executeUpdate();
                prompt = GREEN + "Email " + userEmail + " added successfully!" + RESET;
                System.out.println(prompt);
                con.close();
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //READ API
    public boolean logInUserAccount(String userEmail, String userPass) {
        String query = "SELECT user_email, user_pass, user_role FROM tblusers_acc "
                + "WHERE user_email = ? AND user_pass = ?";
        String[] value = {userEmail, userPass};
        boolean isTrue = true;
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            if (!result.next()) {
                prompt = RED + "Try Again!" + RESET;
                System.out.println(prompt);
                isTrue = false;
            } 
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return isTrue;
    }

    public String getUserId(String userEmail) {
        String query = "SELECT acc_id FROM tblusers_acc WHERE user_email = ?";
        String[] value = {userEmail};

        String userId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                userId = result.getString("acc_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return userId;
    }

    public String getUserRole(String userEmail) {
        String query = "SELECT user_role FROM tblusers_acc WHERE user_email = ?";
        String[] value = {userEmail};

        String userRole = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                userRole = result.getString("user_role");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return userRole;
    }

    //UPDATE API
    public void updatePassword(String userPass, String acc_id) {
        String query = "UPDATE tblusers_acc SET user_pass WHERE acc_id = ?";
        String[] value = {userPass, acc_id};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateEmail(String userEmail, String accId) {
        String query = "UPDATE tblusers_acc SET user_email = ? WHERE acc_id = ?";
        String[] value = {userEmail, accId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //DELETE API
    public void removeUsingInnerJoin() {
        String query = "Select tblstudents.student_id, tblattendances.attendance_id, tblusers_acc.acc_id "
                + "FROM tblstudents "
                + "INNER JOIN tblstudents ON tblstudents.student_id = tblstudents.student_id "
                + "INNER JOIN tblattendances ON tblattendances.attendance_id = tblattendances.attendace_id "
                + "INNER JOIN tblusers_acc ON tblusers_acc.acc_id = tblusers_acc.acc_id "
                + "where archived = 0;";

        try {
            connect();
            state = con.createStatement();
            result = state.executeQuery(query);
            System.out.println("STUDENT_ID\tATTENDANCE_ID\t\t\t\t\tACC_ID");
            while (result.next()) {
                int studentId = result.getInt("student_id");
                int attendanceId = result.getInt("attendance_id");
                int accountId = result.getInt("acc_id");
                // connection to view
                System.out.println(studentId + "\t" + attendanceId + "\t\t\t" + accountId);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void removeStudentAcc(int acc_id) {
        String query = "DELETE from tblusers_acc where acc_id = ?";

        try {
            connect();
            prep = con.prepareStatement(query);
            prep.setInt(1, acc_id);
            prep.executeUpdate();
            System.out.println("Student " + acc_id + " account remove successfully!");
            removeUsingInnerJoin();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
