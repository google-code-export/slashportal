package edu.ucsd.ccdb.slash.autoseg.server;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import  edu.ucsd.ccdb.slash.autoseg.client.*;
import javax.servlet.*;
import javax.servlet.http.*; 

public class AutoSegExpressServlet extends RemoteServiceServlet implements AutoSegExpressService
{
	SlashDBUtil dbutil = new SlashDBUtil();
	
	
	
	//private ServletConfig config = null;
	
	public Long getNexModelID(long datasetID)throws java.lang.Exception
	{
		Long mID = null;
		
		try
		{
			mID = dbutil.getNexModelID(datasetID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mID;
	}
	
	
	
	public Vector<DatasetModelInfo> getDatasetModelInfo(long datasetID)throws java.lang.Exception
	{
		Vector<DatasetModelInfo> v = new Vector<DatasetModelInfo>();
		try
		{
			this.init();
			
			 v = dbutil.getDatasetModelInfo(datasetID);
			 System.out.println("Server----------------"+v);
			 
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return v;
	}
	
	public Vector<SlashImage> getSlashImages(String userName) throws java.lang.Exception
	{
		this.init();
		System.out.println("-----------getSlashImages-------------");
		Vector<SlashImage> v = new Vector<SlashImage>();
		try
		{
			 v = dbutil.getSlashImages(userName);
			 System.out.println("Server----------------"+v);
			 
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			return v;
			
			
	}
		
	public void init()
    {
        
    
      try
      {
          if(SlashDBService.URL == null)
          {
	          String configFile = this.getServletConfig().getServletContext().getInitParameter("configFile");
	          System.out.println("-----------configFile-----------"+configFile);
	          SlashDBService db = new SlashDBService();
	          db.readConfig(configFile);
	          
          }


      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    }    
}
