package edu.ucsd.ccdb.slash.cory.server;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import  java.io.*;
import  java.net.*;
import  java.sql.*;
import  java.util.*;

import  javax.sql.*;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
/**
 *
 * @author wawong
 */
public class SlashDBService {
   // setup connection values to the database
   public static final String DB_DRIVER = "org.postgresql.Driver";


  
  public static  String URL = null;
  public  static String USERNAME = null;
  public  static  String PASSWORD = null;



    // Load the driver when this class is first loaded
    static {
        try {
            Class.forName(DB_DRIVER).newInstance();
        } catch (ClassNotFoundException cnfx) {
            cnfx.printStackTrace();
        } catch (IllegalAccessException iaex) {
            iaex.printStackTrace();
        } catch (InstantiationException iex) {
            iex.printStackTrace();
        }
    }

    /**
     * Returns a normal connection to the database
     */
    public static Connection getConnection () {
         try {
            return  DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    public boolean readConfig(String path) throws Exception
    {
      try
      {
        //Now, go get the connection info from resource files
        ResourceBundle rb = 
        //Then get the real connection info from the real resource
        rb = new PropertyResourceBundle(new FileInputStream(path));


        USERNAME = rb.getString("postgres_user");
        PASSWORD = rb.getString("postgres_password");
        URL=rb.getString("postgres_jdbc_url");

        System.out.println("---------------URL------------"+URL);
        System.out.println("--------PASSWORD:"+PASSWORD+"-------USERNAME:"+USERNAME);
        
        return true;
      }
      catch(Exception e)
      {
        e.printStackTrace();
        throw new Exception("Problem reading database parameters: "+e.getMessage());

      }



    }
    /**
     * Static method that releases a connection
     * @param con the connection
     */
    public static void closeConnection (Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Static method that return a result set
     * @param query
     * @return
     */
    public static ResultSet getResultSet (String query) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        // grab a connection to the database
        try {

            con = getConnection();
            if (con == null)
                return  null;
            stmt = con.createStatement();
            // run the sql query to obtain a result set

            rs = stmt.executeQuery(query);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (RuntimeException rex) {
            rex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

         return rs;

    }

      /**
     * Static method that return a result set
     * @param query
     * @return
     */
    public static java.util.Map getTypeMap () {
        Connection con = null;
        Statement stmt = null;
        java.util.Map map = null;
        // grab a connection to the database
        try {
            con = getConnection();
            if (con == null)
                return  null;
           map = con.getTypeMap();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (RuntimeException rex) {
            rex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  map;
    }


    public static void  main(String[] args)
    {
      try
      {
        SlashDBService db = new SlashDBService();
        db.readConfig("E:\\slash-db-config.properties");




        Connection c = db.getConnection();
        Statement s = c.createStatement();
        String sql = "SELECT * from  project";

        ResultSet rs = s.executeQuery(sql);
        if(rs.next())
            System.out.println(rs.getString(1));

      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    }
}

