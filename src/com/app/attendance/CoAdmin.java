package com.app.attendance;

import com.app.db.DbConnection;

public class CoAdmin extends DbConnection {

    //CREATE API
    public void addCoAdmin(String accId, String adminId, String coAdFName, String coAdMName, String coAdLName) {
        String query = "INSERT INTO tblco_admins(acc_id, admin_id, co_admin_fname, co_admin_mname, co_admin_lname)"
                + "VALUE(?,?,?,?,?)";
        String[] value = {accId, adminId, coAdFName, coAdMName, coAdLName};

        try {
            prepStatement(query, value);
            prep.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //READ API
    public void staffsRegInfo(String adminId) {
        String query = "SELECT tblco_admins.co_admin_id, tblusers_acc.user_email, tblusers_acc.user_pass, tblco_admins.co_admin_fname, tblco_admins.co_admin_mname,"
                + "tblco_admins.co_admin_lname FROM tblco_admins "
                + "INNER JOIN tblusers_acc ON tblco_admins.acc_id = tblusers_acc.acc_id "
                + "WHERE tblco_admins.archived = 0 AND admin_id = ? ";
        String[] value = {adminId};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", "ID", "NAME", "EMAIL", "PASSWORD");
            while (result.next()) {
                String coAdId = result.getString("co_admin_id");
                String coAdPass = result.getString("user_pass");
                String coAdEmail = result.getString("user_email");
                String coAdFName = result.getString("co_admin_fname");
                String coAdMName = result.getString("co_admin_mname");
                String coAdLName = result.getString("co_admin_lname");

                String studentName = String.format("%-10s %-5s %-10s", coAdFName, coAdMName.charAt(0) + ".", coAdLName);

                System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
                System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", coAdId, studentName, coAdEmail, coAdPass);
            }
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void archivedstaffsInfo(String adminId) {
        String query = "SELECT tblco_admins.co_admin_id, tblusers_acc.user_email, tblusers_acc.user_pass, tblco_admins.co_admin_fname, tblco_admins.co_admin_mname,"
                + "tblco_admins.co_admin_lname FROM tblco_admins "
                + "INNER JOIN tblusers_acc ON tblco_admins.acc_id = tblusers_acc.acc_id "
                + "WHERE tblco_admins.archived = 1 AND admin_id = ? ";
        String[] value = {adminId};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", "ID", "NAME", "EMAIL", "PASSWORD");
            while (result.next()) {
                String coAdId = result.getString("co_admin_id");
                String coAdPass = result.getString("user_pass");
                String coAdEmail = result.getString("user_email");
                String coAdFName = result.getString("co_admin_fname");
                String coAdMName = result.getString("co_admin_mname");
                String coAdLName = result.getString("co_admin_lname");

                String studentName = String.format("%-10s %-5s %-10s", coAdFName, coAdMName.charAt(0) + ".", coAdLName);

                System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
                System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", coAdId, studentName, coAdEmail, coAdPass);
            }
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String getCoAdminId(String accId) {
        String query = "SELECT co_admin_id FROM tblco_admins WHERE acc_id = ?";
        String[] value = {accId};

        String co_adminId = "";
        try {
            connect();
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                co_adminId = result.getString("co_admin_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return co_adminId;
    }

    //GET StaffAccId USING tblco_admins.co_admin_id
    public String getAccId(String coAdId) {
        String query = "SELECT acc_id FROM tblco_admins WHERE co_admin_id = ?";

        String[] value = {coAdId};
        String accId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                accId = result.getString("acc_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Account");
        }
        return accId;
    }

    //UPDATE API
    public void updateStaffName(String name, String coAdId, String coAd_name) {
        String query = "UPDATE tblco_admins SET " + coAd_name + " = ? WHERE co_admin_id = ?";
        String[] value = {name, coAdId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateStaffPass(String studPass, String accId) {
        String query = "UPDATE tblusers_acc SET user_pass = ? WHERE acc_id = ?";
        String[] value = {studPass, accId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void restoreStaffInfo(String coAdId) {
        String query = "UPDATE tblco_admins SET archived = 0 WHERE co_admin_id = ?";
        String[] value = {coAdId};
        try {
            prepStatement(query, value);    
            prep.executeUpdate();
            System.out.println(GREEN + "Restored Successfully!" + RESET);
            con.close();
        } catch (Exception ex) {
            System.out.println(RED + "Restored not successful!" + RESET);
        }
    }

    //DELETE API
    //SOFT DELETE API
    public void archiveStaff(String coAdId) {
        String query = "UPDATE tblco_admins SET archived = 1 WHERE co_admin_id = ?";
        String[] value = {coAdId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
            System.out.println(GREEN + "Archived Successfully!" + RESET);
            con.close();
        } catch (Exception ex) {
            System.out.println(RED + "Archived not successful!" + RESET);
        }
    }

    //HARD DELETE API
    public void deleteStaff(String coAdId) {
        String query = "DELETE FROM tblco_admins WHERE co_admin_id = ?";
        String[] value = {coAdId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
            System.out.println(GREEN + "Deleted Successfully!" + RESET);
            con.close();
        } catch (Exception ex) {
            System.out.println(RED + "Delete not successful!" + RESET);
        }
    }

    public String[] getCoAdminAcc(String CoAdminAcc) {
        String query = "SELECT user_email, user_pass FROM tblusers_acc WHERE acc_id = ?";
        String[] value = {CoAdminAcc};
        String[] account = {String.format("%-29s", " ")};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
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

    public void scanAttendance() {
        String query = "SELECT * FROM tblattendances";

        try {
            connect();
            state = con.createStatement();
            result = state.executeQuery(query);
            System.out.println("ATTENDANCE_ID\tDATE\t\t\tATTD_TIME_IN\t\tATTD_TIME_OUT\t\tSTATUS");

            while (result.next()) {
                int attdId = result.getInt("attendance_id");
                String attdDate = result.getString("attd_date");
                String attdIn = result.getString("attd_time_in");
                String attdOut = result.getString("attd_time_out");
                String attdStatus = result.getString("attd_status");
                System.out.println(attdId + "\t" + attdDate + "\t\t" + attdIn + "\t\t" + attdOut + "\t\t" + attdStatus);
            }
            result.close();
            state.close();
            con.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void scanStudents() {
        String query = "SELECT * FROM tblstudents";

        try {
            connect();
            state = con.createStatement();
            result = state.executeQuery(query);
            System.out.println("STUDENT_ID\t\t\tSTUDENT_FNAME\t\tSTUDENT_MNAME\t\tSTUDENT_LNAME");

            while (result.next()) {
                int studId = result.getInt("student_id");
                String studFname = result.getString("student_fname");
                String studMname = result.getString("student_mname");
                String studLname = result.getString("student_lname");
                System.out.println(studId + "\t" + studFname + "\t\t" + studMname + "\t\t" + studLname);
            }
            result.close();
            state.close();
            con.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void removeStudent() {
        String query = "Select tblstudents.student_id, tblattendances.attendance_id, tblusers_acc.acc_id "
                + "FROM tblstudent "
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

    //DELETE API
    public void removeStudent(int student_id) {
        String query = "DELETE from tblstudents where id = ?";

        try {
            connect();
            prep = con.prepareStatement(query);
            prep.setInt(1, student_id);
            prep.executeUpdate();
            System.out.println("Student " + student_id + " remove from the class successfully!");
            removeStudent();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String[] getCoAdminName(String coAdminId) {
        String query = "SELECT * FROM tblco_admins WHERE co_admin_id = ?";

        String[] value = {coAdminId};
        String[] name = {String.format("%-29s", " ")};
        try {
            connect();
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                String co_adminFname = result.getString("co_admin_fname");
                String co_adminMname = result.getString("co_admin_mname");
                String co_adminLname = result.getString("co_admin_lname");
                name = new String[]{co_adminFname, co_adminMname, co_adminLname};
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Name");
        }
        return name;
    }

    public void addStaffs(String coMail, String coPass, String coFName, String coMName, String coLName) {
        String query = "INSERT INTO tblco_admins (co_admin_mail, co_admin_pass, co_admin_fname, co_admin_mname, co_admin_lname)"
                + "VALUE = ?,?,?,?,?";
        String[] value = {coMail, coPass, coFName, coMName, coLName};

        try {
            connect();
            prepStatement(query, value);
            prep.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println("Failed to add staffs");
        }
    }

    public String[] getCoAdminPass(String coAdminId) {
        String query = "SELECT * FROM tblco_admins WHERE co_admin_id = ?";

        String[] value = {coAdminId};
        String[] name = {String.format("%-29s", " ")};
        try {
            connect();
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                String co_admin_pass = result.getString("co_admin_pass");
                name = new String[]{co_admin_pass};
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Name");
        }
        return name;
    }

    public void updateCoAdminPass(String coPass, String coId) {
        String query = "UPDATE tblco_admins SET co_admin_pass = ? WHERE co_admin_id = ?";
        String[] value = {coPass, coId};
        try {
            connect();
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Cannot be update");
        }
    }

    public String[] getCoAdminEmail(String coAdminId) {
        String query = "SELECT * FROM tblco_admins WHERE co_admin_id = ?";

        String[] value = {coAdminId};
        String[] name = {String.format("%-29s", " ")};
        try {
            connect();
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                String co_adminMail = result.getString("co_admin_mail");
                name = new String[]{co_adminMail};
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Name");
        }
        return name;
    }

    public void updateCoAdminEmail(String coMail, String coId) {
        String query = "UPDATE tblco_admins SET co_admin_mail = ? WHERE co_admin_id = ?";
        String[] value = {coMail, coId};
        try {
            connect();
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Cannot be update");
        }
    }

    public void removeCoAdmin(int coAdminId) {
        String query = "DELETE from tblco_admins where co_admin_id = ?";

        try {
            connect();
            prep = con.prepareStatement(query);
            prep.setInt(1, coAdminId);
            prep.executeUpdate();
            System.out.println("Co admin " + coAdminId + " removed successfully!");
            con.close();
        } catch (Exception ex) {
            System.out.println("Unable to remove the co admin");
        }
    }
}
