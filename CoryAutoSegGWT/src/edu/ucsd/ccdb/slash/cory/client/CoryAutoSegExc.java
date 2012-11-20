package edu.ucsd.ccdb.slash.cory.client;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;



public class CoryAutoSegExc 
{
	//public static String entry = "http://127.0.0.1:8888/CoryAutoSegServlet";
	public static String entry = "http://galle.crbs.ucsd.edu:8081/CoryAutoSegGWT/CoryAutoSegServlet";
	public CoryAutoSegGWT cag = null;
	
	
	public CoryAutoSegExc(CoryAutoSegGWT parent)
	{
		cag = parent;
	}
	
	public void getCoryXML(Vector<GPoint> vpoint) throws java.lang.Exception
	{
		CoryAutoSegServiceAsync coryService = (CoryAutoSegServiceAsync)GWT.create(CoryAutoSegService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget)coryService;
	    endpoint.setServiceEntryPoint( entry);
	    
	    AsyncCallback callback = new AsyncCallback() {
	          public void onSuccess(Object o) {
	           System.out.println("------------------------getCoryXML: "+o+"!!!!!!!!!!!!!"+o.getClass());
	           
	           if(o instanceof String)
	           {
	        	   System.out.println(o);
	        	   cag.showXML(o+"");
	        	
	           }
	           else
	           {
	        	   System.out.println("submitCytoseg--------------Returned:"+o);
	           }
	           

	          }

	          public void onFailure(Throwable caught) {

	        	 

	            StackTraceElement[] el = caught.getStackTrace();

	            StringBuffer buff = new StringBuffer();
	            for(int i=0;i<el.length;i++)
	            {
	              buff.append("\nService Execute : " +
	                                 el[i]);
	            }
	            System.out.println(buff.toString());
	            com.google.gwt.user.client.Window.alert("Error: "+caught.toString());
	            
	          
	          }
	       };
	       
	       coryService.getCoryXML(vpoint, callback);
		
	}

}
