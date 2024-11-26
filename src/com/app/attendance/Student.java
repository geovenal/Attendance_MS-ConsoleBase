package com.app.attendance;

import com.app.db.DbConnection;

public class Student extends DbConnection {

    //CREATE
    public void addStudent(String accId, String adminId, String studentFName, String studentMName, String studentLName) {
        String query = "INSERT INTO tblstudents(acc_id, admin_id, student_fname, student_mname, student_lname)"
                + "VALUE(?,?,?,?,?)";
        String[] value = {accId, adminId, studentFName, studentMName, studentLName};

        try {
            prepStatement(query, value);
            prep.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //READ
    public void studentList(String adminId) {
        String query = "SELECT student_id, student_fname, student_mname, student_lname FROM tblstudents "
                + "where admin_id = ?";
        String[] value = {adminId};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();

            System.out.printf("+---------------------------------------------+%n");
            System.out.printf("| %-5s | %-35s |%n", "ID", "NAME");
            while (result.next()) {
                String studentId = result.getString("student_id");
                String studentFName = result.getString("student_fname");
                String studentMName = result.getString("student_mname");
                String studentLName = result.getString("student_lname");

                String studentName = String.format("%-10s %-5s %-10s", studentFName, studentMName.charAt(0) + ".", studentLName);
                System.out.printf("+---------------------------------------------+%n");
                System.out.printf("| %-5s | %-35s |%n", studentId, studentName);
            }
            System.out.printf("+---------------------------------------------+%n");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void studentsRegInfo(String adminId) {
        String query = "SELECT tblstudents.student_id, tblusers_acc.user_email, tblusers_acc.user_pass, tblstudents.student_fname, tblstudents.student_mname,"
                + "tblstudents.student_lname FROM tblstudents "
                + "INNER JOIN tblusers_acc ON tblstudents.acc_id = tblusers_acc.acc_id "
                + "where admin_id = ?";
        String[] value = {adminId};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", "ID", "NAME", "EMAIL", "PASSWORD");
            while (result.next()) {
                String studentId = result.getString("student_id");
                String studentPass = result.getString("user_pass");
                String studentEmail = result.getString("user_email");
                String studentFName = result.getString("student_fname");
                String studentMName = result.getString("student_mname");
                String studentLName = result.getString("student_lname");

                String studentName = String.format("%-10s %-5s %-10s", studentFName, studentMName.charAt(0) + ".", studentLName);

                System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
                System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", studentId, studentName, studentEmail, studentPass);
            }
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void studentsClass(String adminId) {
        String query = "SELECT tblstudents.student_id, tblusers_acc.user_email, tblstudents.student_fname, tblstudents.student_mname,"
                + "tblstudents.student_lname, tblclasses.class_code, tblclasses.class_name FROM tblstudents "
                + "INNER JOIN tblusers_acc ON tblstudents.acc_id = tblusers_acc.acc_id "
                + "INNER JOIN tblclasses ON tblstudents.class_id = tblclasses.class_id "
                + "where admin_id = ?";
        String[] value = {adminId};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", "ID", "NAME", "EMAIL", "CLASS");
            while (result.next()) {
                String studentId = result.getString("student_id");
                String studentEmail = result.getString("user_email");
                String studentFName = result.getString("student_fname");
                String studentMName = result.getString("student_mname");
                String studentLName = result.getString("student_lname");
                String classCode = result.getString("class_code");
                String className = result.getString("class_name");

                String studentName = String.format("%-10s %-5s %-10s", studentFName, studentMName.charAt(0) + ".", studentLName);
                String studentClass = String.format("%-3s - %-10s", classCode, className);

                System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
                System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", studentId, studentName, studentEmail, studentClass);
            }
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //GET StudentId 
    public String getStudentId(String accId) {
        String query = "SELECT student_id FROM tblstudents WHERE acc_id = ?";
        String[] value = {accId};

        String studentId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                studentId = result.getString("student_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return studentId;
    }
    //GET StudentId Using tbladmins.admin_id
    public String getStudent_Id(String adminId) {
        String query = "SELECT student_id FROM tblstudents WHERE admin_id = ?";
        String[] value = {adminId};

        String studentId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                studentId = result.getString("student_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return studentId;
    }
    
    public String getStudClassId(String studentId) {
        String query = "SELECT class_id FROM tblstudents WHERE student_id = ?";
        String[] value = {studentId};

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

    //GET StudentAcc FROM tblusers_acc.acc_id
    public String[] getStudentAcc(String studentAcc) {
        String query = "SELECT user_email, user_pass FROM tblusers_acc WHERE acc_id = ?";
        String[] value = {studentAcc};
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

    //GET StudentsName USING tblstudents.student_id
    public String[] getStudent_Name(String studentId) {
        String query = "SELECT * FROM tblstudents WHERE student_id = ?";

        String[] value = {studentId};
        String[] name = {String.format("%-29s", " ")};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                String studentFname = result.getString("student_fname");
                String studentMname = result.getString("student_mname");
                String studentLname = result.getString("student_lname");
                name = new String[]{studentFname, studentMname, studentLname};
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Name");
        }
        return name;
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

    //GET AdminId USING tblstudents.student_id
    public String getStudAdminId(String studentId) {
        String query = "SELECT admin_id FROM tblstudents WHERE student_id = ?";

        String[] value = {studentId};
        String adminId = "";
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                adminId = result.getString("admin_id");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Trainer");
        }
        return adminId;
    }
    //GET StudentAccId USING tblstudents.student_id
    public String getAccId(String studentId){
        String query = "SELECT acc_id FROM tblstudents WHERE student_id = ?";

        String[] value = {studentId};
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
    
    //UPDATE
    public void updateStudentName(String name, String studentId, String student_name) {
        String query = "UPDATE tblstudents SET " + student_name + " = ? WHERE student_id = ?";
        String[] value = {name, studentId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateStudentPass(String studPass, String accId) {
        String query = "UPDATE tblusers_acc SET user_pass = ? WHERE acc_id = ?";
        String[] value = {studPass, accId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public boolean updateStudentClass(String classId, String studentId) {
        boolean isTrue = false;
        String query = "UPDATE tblstudents SET class_id = ? WHERE student_id = ?";
        String[] value = {classId, studentId};
        try {
            prepStatement(query, value);
            while(result.next()){
                prompt = GREEN + "Added student successfully!" + RESET;
                System.out.println(prompt);
                isTrue = true;
            }
            prep.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println(RED + "Try Again" + RESET);
        }
        return isTrue;
    }

    //DELETE
    //SOFT DELETE
    //HARD DELETE
    public void deleteTrainee(String studentId) {
        String query = "DELETE FROM tblstudents WHERE student_id = ?";
        String[] value = {studentId};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
            System.out.println(GREEN + "Deleted Successfully!" + RESET);
            con.close();
        } catch (Exception ex) {
            System.out.println(RED + "Delete not successful!" + RESET);
        }
    }
}
