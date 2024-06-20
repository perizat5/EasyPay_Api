package com.hp.utilMetods;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataBase_Utils {
    // declaring at class level so all methods can access
    private static Connection con ;
    private static Statement stm ;
    private static ResultSet rs ;
    private static ResultSetMetaData rsmd ;

    public static void createConnection(String url , String username, String password){
        try {
            con = DriverManager.getConnection(url, username, password) ;
            System.out.println("CONNECTION SUCCESSFUL");
        } catch (Exception e) {
            System.out.println("CONNECTION HAS FAILED " + e.getMessage() );
        }
    }
    /**
     * Create connection method , just checking one connection successful or not
     */
    public static void createConnection(){
        String url      = Config_Reader.getProperty("hp.db.url") ;
        String username = Config_Reader.getProperty("hp.db.username") ;
        String password = Config_Reader.getProperty("hp.db.password") ;

        createConnection(url, username, password);
    }

    public static ResultSet runQuery(String sql){
        try {
            stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,     ResultSet.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);
            rsmd = rs.getMetaData() ;  // setting the value of ResultSetMetaData for reuse
        }catch(Exception e){
            System.out.println("ERROR OCCURRED WHILE RUNNING QUERY "+ e.getMessage() );
        }
        return rs ;
    }

    public static void destroy(){
        // WE HAVE TO CHECK IF WE HAVE THE VALID OBJECT FIRST BEFORE CLOSING THE RESOURCE
        // BECAUSE WE CAN NOT TAKE ACTION ON AN OBJECT THAT DOES NOT EXIST
        try {
            if( rs!=null)  rs.close();
            if( stm!=null)  stm.close();
            if( con!=null)  con.close();
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE CLOSING RESOURCES " + e.getMessage() );
        }
    }

    private static void resetCursor(){
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int getColumnCount(){
        int columnCount = 0 ;
        try {
            columnCount = rsmd.getColumnCount();
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE GETTING COLUMN COUNT " + e.getMessage() );
        }
        return columnCount ;
    }
    /**
     * // Get all the Column names into a list object
     * @return  List<String>
     */

    public static Map<String, Object> getRowMap(int rowNum){
        Map<String,Object> rowMap = new LinkedHashMap<>();
        int columnCount = getColumnCount() ;
        try{
            rs.absolute(rowNum) ;
            for (int colIndex = 1; colIndex <= columnCount ; colIndex++) {
                String columnName = rsmd.getColumnName(colIndex) ;
                String cellValue  = rs.getString(colIndex) ;
                rowMap.put(columnName, cellValue) ;
            }
        }catch(Exception e){
            System.out.println("ERROR OCCURRED WHILE getRowMap " + e.getMessage() );
        }finally {
            resetCursor();
        }
        return rowMap ;
    }


}
