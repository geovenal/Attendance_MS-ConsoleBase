package com.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance_ms";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    // ANSI escape codes for coloring text
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    
    public static String prompt = "";
    
    public Connection con;
    public Statement state;
    public ResultSet result;
    public PreparedStatement prep;
    
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
    
    public void connect(){
        try{
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void creStatement(String query) {
        try {
            connect();
            state = con.createStatement();
            result = state.executeQuery(query);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void prepStatement(String query, String[] value){
        try {
            connect();
            prep = con.prepareStatement(query); 
            for(int i = 0; i < value.length; i++){
                prep.setString(i+1, value[i]);
            }    
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}