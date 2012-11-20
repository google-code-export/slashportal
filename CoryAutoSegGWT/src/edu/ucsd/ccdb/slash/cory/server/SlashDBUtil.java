package edu.ucsd.ccdb.slash.cory.server;


import java.util.*;
import java.sql.*;
public class SlashDBUtil 
{
	SlashDBService db = new SlashDBService();
	 public long getNextSequence()throws Exception
	    {
	        long id = -1;
	        String sql = "select nextval('general_sequence')";
	        Connection c = db.getConnection();
	        PreparedStatement ps = c.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();

	        if(rs.next());
	            id = rs.getLong(1);

	            c.close();
	            return id;

	    }
	

}
