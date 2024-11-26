package com.app.attendance;

import com.app.db.DbConnection;

public class Admin extends DbConnection {

    //CREATE API
    public void addAdmin(String accId, String adminFName, String adminMName, String adminLName) {
        String query = "INSERT INTO tbladmins(acc_id, admin_fname, admin_mname, admin_lname)"
                + "VALUE(?,?,?,?)";
        String[] value = {accId, adminFName, adminMName, adminLName};

        try {
            prepStatement(query, value);
            prep.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    
    
    //READ API    
    //GET AdminAcc FROM tblusers_acc.acc_id
    public String[] getAdminAcc(String adminAcc) {
        String query = "SELECT user_email, user_pass FROM tblusers_acc WHERE acc_id = ?";
        String[] value = {adminAcc};
        String[] account = {String.format("%-29s", " ")};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while(result.next()){
               String adminEmail = result.getString("user_email");
               String adminPass = result.getString("user_pass");
               account = new String[]{adminEmail, adminPass};
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Account");
        }
        return account;
    }
    //GET AdminName USING tbladmins.admin_id
    public String[] getAdminName(String adminId) {
        String query = "SELECT * FROM tbladmins WHERE admin_id = ?";

        String[] value = {adminId};
        String[] name = {String.format("%-29s", " ")};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                String adminFname = result.getString("admin_fname");
                String adminMname = result.getString("admin_mname");
                String adminLname = result.getString("admin_lname");
                name = new String[]{adminFname, adminMname, adminLname};
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Name");
        }
        return name;
    }
    //GET AdminId USING tbladmins.acc_id
    public String getAdminId(String accId) {
        String query = "SELECT admin_id FROM tbladmins WHERE acc_id = ?";
        String[] value = {accId};

        String adminId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                adminId = result.getString("admin_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return adminId;
    }
    
    
    //UPDATE API
    public void updateAttendance(String attd_status, int attendance_id) {
        String query = "UPDATE tblattendances SET " + attd_status + " = ? WHERE attendance_id = ?";

        try {
            prep = con.prepareStatement(query);  
            prep.setBoolean(1, true);
            prep.setInt(2, attendance_id);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void updateAdminName(String name,  String adminId, String admin_name){
        String query = "UPDATE tbladmins SET " + admin_name + " = ? WHERE admin_id = ?";
        String[] value ={name, adminId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
        }
    }
    public void updateAdminEmail(String adminEmail, String accId){
        String query = "UPDATE tblusers_acc SET user_email = ? WHERE acc_id = ?";
        String[] value = {adminEmail, accId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void updateAdminPass(String adminPass, String accId){
        String query = "UPDATE tblusers_acc SET user_pass = ? WHERE acc_id = ?";
        String[] value = {adminPass, accId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    //DELETE API
    public void removeUsingInnerJoin(){
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
            while(result.next()){
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
    
    public void removeStudent(int student_id){
        String query = "DELETE from tblstudents where student_id = ?";
        
        try {
            connect();
            prep = con.prepareStatement(query);
            prep.setInt(1, student_id);
            prep.executeUpdate();
            System.out.println("Student " + student_id + " remove from the class successfully!");
            removeUsingInnerJoin();
            con.close();  
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void archiveStudentInnerJoin(){
        String query = "Select tblstudents.student_id, tblattendances.attendance_id, tblusers_acc.acc_id "
                + "FROM tblstudents "
                + "INNER JOIN tblstudents ON tblstudents.student_id = tblstudents.student_id "
                + "INNER JOIN tblattendances ON tblattendances.attendance_id = tblattendances.attendace_id "
                + "INNER JOIN tblusers_acc ON tblusers_acc.acc_id = tblusers_acc.acc_id "
                + "where archived = 1;";
        
        try {
           connect();
           state = con.createStatement();
           result = state.executeQuery(query); 
           System.out.println("STUDENT_ID\tATTENDANCE_ID\t\t\t\t\tACC_ID");
            while(result.next()){
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
    
    public void archiveStudent(int student_id){
        String query = "UPDATE tblstudents SET archived = 1 where student_id = ?";
        
        try {
            connect();
            prep = con.prepareStatement(query);
            prep.setInt(1, student_id);
            prep.executeUpdate();
            System.out.println("Student " + student_id + " suspended succesful!");
            archiveStudentInnerJoin();
            con.close();  
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void restoreStudent(int student_id){
        String query = "UPDATE tblstudents SET archived = 0 where student_id = ?";
        
        try {
            connect();
            prep = con.prepareStatement(query);
            prep.setInt(1, student_id);
            prep.executeUpdate();
            System.out.println("Student " + student_id + " restored successfully!");
            archiveStudentInnerJoin();
            con.close();  
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}