package edu.ucsd.ccdb.slash.cory.server;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import  edu.ucsd.ccdb.slash.cory.client.*;


import javax.servlet.*;
import javax.servlet.http.*; 
public class CoryAutoSegServlet  extends RemoteServiceServlet implements  CoryAutoSegService
{
	public String getCoryXML(Vector<GPoint> vpoint) throws java.lang.Exception
	{
		 this.init();
		String rpath = "http://galle.crbs.ucsd.edu/test_image.png";
		XMLGenerator  xg = new XMLGenerator ();
		String xml =xg.generatreXML(vpoint, rpath);
		return xml;
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
