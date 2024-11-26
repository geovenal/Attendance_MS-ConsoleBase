package com.app.attendance;

import com.app.db.DbConnection;

public class Classes extends DbConnection{
    
    //CREATE API
    public void addClass(String classCode, String className, String classTimeIn, String classTimeOut) {
        String query = "INSERT INTO tblclasses(class_code, class_name, class_time_in, class_time_out)"
                + "VALUE(?,?,?,?)";
        String[] value = {classCode, className, classTimeIn, classTimeOut};

        try {
            prepStatement(query, value);
            prep.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println(RED + "Class already exist!" + RESET);
        }
    }
    
    
    //READ API
    public void retrieveUsingInnerJoin() {
        String query = "SELECT * FROM tblclasses";

        try {
            creStatement(query);
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", "CODE", "NAME", "START", "END");
            while (result.next()) {
                String classCode = result.getString("class_code");
                String className = result.getString("class_name");
                String classStart = result.getString("class_time_in");
                String classEnd = result.getString("class_time_out");
                System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
                System.out.printf("| | %-5s | %-35s | %-25s | %-22s | |%n", classCode, className, classStart, classEnd);
            }
            System.out.printf("| +--------------------------------------------------------------------------------------------------+ |%n");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    //GET ClassId USING tblclasses.class_code
    public String getClassId(String classCode) {
        String query = "SELECT class_id FROM tblclasses WHERE class_code = ?";
        String[] value = {classCode};

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
    
    
    public String[] classInfo(String classId){
        String query = "SELECT * FROM tblclasses WHERE class_id = ?";

        String[] value = {classId};
        String[] info = {String.format("%-29s", " ")};
        try {
            prepStatement(query, value);
            result = prep.executeQuery();
            while (result.next()) {
                String classCode = result.getString("class_code");
                String className = result.getString("class_name");
                String classStart = result.getString("class_time_in");
                String classEnd = result.getString("class_time_out");
                info = new String[]{classCode, className, classStart,classEnd};
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("No Info");
        }
        return info;
    }
    
    //UPDATE API
    public void updateClass(String content, String classId, String col){
        String query = "UPDATE tblclasses SET " + col + " = ? WHERE class_id = ?";
        String[] value = {content, classId};
        try {
            prepStatement(query, value);
            System.out.println(GREEN+ "Updated Successfully!" + RESET);
            prep.executeUpdate();
        } catch (Exception ex) {
            System.out.println(RED + "Try Again!" + RESET);
        }
    }
    
    
    //DELETE API
    //SOFT DELETE
    
    //HARD DELETE
    public void deleteClass(String classCode){
        String query = "DELETE FROM tblclasses WHERE class_code = ?";
        String [] value = {classCode};
        try {
            prepStatement(query, value);
            prep.executeUpdate();
            while(result.next()){
                System.out.println(GREEN + "Deleted Successfully!" + RESET);
            }
            con.close();  
        } catch (Exception ex) {
            System.out.println(RED + "Delete not successful!" + RESET);
        }
    }
    
}