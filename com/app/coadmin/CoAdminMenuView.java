package com.app.coadmin;

import com.app.db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoAdminMenuView {
    
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    
    String borderline = "+------------------------------------------------------------------------------------------------------+";
    String cornerline = String.format("| %-80s                     |%n", " ");
    String menuline = "+---------------------------------------------------------------------------------------------+";
    String txtlogOut = RED + "Log out" + RESET;
    String[] menuBar = new String[]{"Profile", "CoAdmins", "Settings"};

    public String[] getCoAdminById(String coAdminId) {
        String[] coAdminData = new String[3];
        String query = "SELECT co_admin_fname, co_admin_mname, co_admin_lname FROM tblco_admins WHERE co_admin_id = ?";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, coAdminId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                coAdminData[0] = rs.getString("co_admin_fname");
                coAdminData[1] = rs.getString("co_admin_mname");
                coAdminData[2] = rs.getString("co_admin_lname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coAdminData;
    }

    public String getCoAdminEmailByAccId(String accId) {
        String email = null;
        String query = "SELECT user_email FROM tblusers_acc WHERE acc_id = ?";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("user_email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    public void updateCoAdminName(String coAdminId, String column, String newName) {
        String query = "UPDATE tblco_admins SET " + column + " = ? WHERE co_admin_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, coAdminId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCoAdminPassword(String accId, String newPassword) {
        String query = "UPDATE tblusers_acc SET password = ? WHERE acc_id = ?";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, accId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
