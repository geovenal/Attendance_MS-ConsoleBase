package com.app.attendance;

import com.app.db.DbConnection;

public class Attendance extends DbConnection {

    //CREATE API
    public void addAttendace(String date, String day, String studentId, String classId, String adminId) {
        String query = "INSERT INTO tblattendances(attd_date, attd_day, student_id, class_id, admin_id)"
                + "VALUE(?,?,?,?,?)";
        String[] value = {date, day, studentId, classId, adminId};

        try {
            prepStatement(query, value);
            prep.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println(RED + "Contact your Trainer to add you in a class" + RESET);
            System.exit(0);
        }
    }

    //READ API
    public void attendanceClass(String attendanceId, String date) {
        String query = "SELECT DISTINCT tblattendances.attd_time_in, tblattendances.attd_time_out, tblattendances.attd_status, "
                + "tblclasses.class_time_in, tblclasses.class_time_out FROM tblattendances "
                + "INNER JOIN tblclasses ON tblattendances.class_id = tblclasses.class_id "
                + "WHERE attendance_id = ? AND attd_date = ?";
        String[] value = {attendanceId, date};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            System.out.printf("| +--------------------------------------------------------------------------+ %-23s |%n", " ");
            System.out.printf("| | %-15s | %-10s | %-15s | %-10s | %-10s | %-23s |%n", "CLASS START", "TIME IN", "CLASS END", "TIME OUT", "STATUS", " ");
            while (result.next()) {
                String classStart = result.getString("class_time_in");
                String timeIn = result.getString("attd_time_in");
                String classEnd = result.getString("class_time_out");
                String timeOut = result.getString("attd_time_out");
                String status = result.getString("attd_status");
                status = getStatus(status);

                System.out.printf("| +--------------------------------------------------------------------------+ %-23s |%n", " ");
                System.out.printf("| | %-15s | %-10s | %-15s | %-10s | %-19s | %-23s |%n", classStart, timeIn, classEnd, timeOut, status + " ", " ");
            }
            System.out.printf("| +--------------------------------------------------------------------------+ %-23s |%n", " ");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void classAttdView(String classCode, String adminId, String date) {
        String query = "SELECT tblattendances.attd_time_in, tblattendances.attd_time_out, tblattendances.attd_status, "
                + "tblattendances.attd_date, tblattendances.attd_day, tblattendances.student_id, "
                + "tblstudents.student_fname, tblstudents.student_mname, tblstudents.student_lname "
                + "FROM tblattendances "
                + "INNER JOIN tblstudents ON tblattendances.student_id = tblstudents.student_id "
                + "INNER JOIN tblclasses ON tblattendances.class_id = tblclasses.class_id "
                + "WHERE tblclasses.class_code = ? AND tblattendances.admin_id = ? AND tblattendances.attd_date = ? "
                + "ORDER BY  tblattendances.attd_date DESC, tblstudents.student_lname ASC ";

        String[] value = {classCode, adminId, date};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
      
            System.out.printf("| +-------------------------------------------------------------------------------------------------+  |%n");
            System.out.printf("| |%-5s | %-25s | %-10s | %-10s | %-10s | %-10s | %-8s |  |%n", "ID", "NAME", "DATE", "DAY", "TIME IN", "TIME OUT", "STATUS");
            while (result.next()) {
                String studentId = result.getString("student_id");
                String studentFname = result.getString("student_fname");
                String studentMname = result.getString("student_mname");
                String studentLname = result.getString("student_lname");
                String attdDate = result.getString("attd_date");
                String attdDay = result.getString("attd_day");
                String timeIn = result.getString("attd_time_in");
                String timeOut = result.getString("attd_time_out");
                String status = result.getString("attd_status");
                status = getStatus(status);
                String studentName = studentLname + ", " + studentFname + " " + studentMname.charAt(0) + ".";
                System.out.printf("| +-------------------------------------------------------------------------------------------------+  |%n");
                System.out.printf("| |%-5s | %-25s | %-10s | %-10s | %-10s | %-10s | %-17s |  |%n", studentId, studentName, attdDate, attdDay, timeIn, timeOut, status + " ");
            }
            System.out.printf("| +-------------------------------------------------------------------------------------------------+  |%n");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    //GET ClassId USING tblattendances.attendance_id
    public String getClassId(String attdId) {
        String query = "SELECT class_id FROM tblattendances WHERE class_id = ?";
        String[] value = {attdId};

        String classId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                classId = result.getString("class_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return classId;
    }

    public void attendanceRecord(String studentId) {
        String query = "SELECT DISTINCT tblattendances.attd_time_in, tblattendances.attd_time_out, tblattendances.attd_status, "
                + "tblattendances.attd_date,tblattendances.attd_day, tblclasses.class_time_in, tblclasses.class_time_out FROM tblattendances "
                + "INNER JOIN tblclasses ON tblattendances.class_id = tblclasses.class_id "
                + "WHERE student_id = ?"
                + "ORDER BY  tblattendances.attd_date DESC";
        String[] value = {studentId};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            System.out.printf("| +----------------------------------------------------------------------------------------+ %-9s |%n", " ");
            System.out.printf("| |%-13s | %-8s | %-12s | %-8s | %-12s | %-8s | %-8s | %-9s |%n", "DATE", "DAY", "CLASS START", "TIME IN", "CLASS END", "TIME OUT", "STATUS", " ");
            while (result.next()) {
                String attdDate = result.getString("attd_date");
                String attdDay = result.getString("attd_day");
                String classStart = result.getString("class_time_in");
                String timeIn = result.getString("attd_time_in");
                String classEnd = result.getString("class_time_out");
                String timeOut = result.getString("attd_time_out");
                String status = result.getString("attd_status");
                status = getStatus(status);
                System.out.printf("| +----------------------------------------------------------------------------------------+ %-9s |%n", " ");
                System.out.printf("| |%-13s | %-8s | %-12s | %-8s | %-12s | %-8s | %-17s | %-9s |%n", attdDate, attdDay, classStart, timeIn, classEnd, timeOut, status, " ");
            }
            System.out.printf("| +----------------------------------------------------------------------------------------+ %-9s |%n", " ");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String getTime(String attendanceId, String date, String time) {
        String query = "SELECT DISTINCT " + time + " FROM tblattendances WHERE attendance_id = ? AND attd_date = ?";
        String[] value = {attendanceId, date};

        String getTime = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                getTime = result.getString(time);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return getTime;
    }

    public String getDate(String attdId) {
        String query = "SELECT DISTINCT attd_date FROM tblattendances WHERE attendance_id = ?";
        String[] value = {attdId};

        String getDate = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                getDate = result.getString("attd_date");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return getDate;
    }

    //GET AdminId USING tbladmins.acc_id
    public String getAttdId(String studentId, String date) {
        String query = "SELECT attendance_id FROM tblattendances WHERE student_id = ? AND attd_date = ?";
        String[] value = {studentId, date};

        String attdId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                attdId = result.getString("attendance_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return attdId;
    }

    //GET attendace_id USING tbl.attendances.student_id
    public String getAttdId(String studentId) {
        String query = "SELECT attendance_id FROM tblattendances WHERE student_id = ?";
        String[] value = {studentId};

        String attdId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                attdId = result.getString("attendance_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return attdId;
    }

    public String getStatus(String status) {
        if ("EARLY".equals(status)) {
            status = GREEN + status + "  " + RESET;
        } else if ("LATE".equals(status)) {
            status = BLUE + status + " " + RESET;
        } else if ("ABSENT".equals(status)) {
            status = RED + status + " " + RESET;
        }
        return status;
    }

    //UPDATE API
    public void updateTime(String time, String attdId, String date, String timeCheck) {
        String query = "UPDATE tblattendances SET " + timeCheck + " = ? WHERE attendance_id = ? AND attd_date = ?";
        String[] value = {time, attdId, date};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateStatus(String status, String attdId, String date) {
        String query = "UPDATE tblattendances SET attd_status = ? WHERE attendance_id = ? AND attd_date = ?";
        String[] value = {status, attdId, date};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //DELETE API 
}
