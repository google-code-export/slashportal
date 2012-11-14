package edu.ucsd.ccdb.slash.autoseg.client;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;



public class AutoSegExpressExc {
	Vector<SlashImage> imageV=  null;
	public static String entry = "http://127.0.0.1:8888/AutoSegExpressServlet";
	//public static String entry = "http://galle.crbs.ucsd.edu:8081/SlashAutoSegExpress/AutoSegExpressServlet";
	
	SlashAutoSegExpress segExp = null;
	CreateNewModelPanel cModelPanel = null;
	
	public AutoSegExpressExc(CreateNewModelPanel cnp)
	{
		cModelPanel = cnp;
	}
	
	public AutoSegExpressExc(SlashAutoSegExpress sse)
	{
		segExp = sse;
	}
	
	
	public void getNexModelID(long datasetID)throws java.lang.Exception
	{
		System.out.println("---------------getNexModelID----------------");
		 AutoSegExpressServiceAsync galleryService =
		       (AutoSegExpressServiceAsync)GWT.create(AutoSegExpressService.class);
		
		 ServiceDefTarget endpoint = (ServiceDefTarget)galleryService;
		    endpoint.setServiceEntryPoint( entry);
		    
		    
		    AsyncCallback callback = new AsyncCallback() {
		          public void onSuccess(Object o) {
		           System.out.println("Callback: "+o+"!!!!!!!!!!!!!"+o.getClass());
		           
			           if(o instanceof Long)
			           {
			        	   Long mID = (Long)o;
			        	   System.out.println("----------------------mID-----------------"+mID);
			        	   cModelPanel.setModelID(mID);
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
		       
		      
		galleryService.getNexModelID(datasetID, callback);
	}
	
	public void getDatasetModelInfo(long datasetID)throws java.lang.Exception
	{
		System.out.println("---------------Start getDatasetModelInfo----------------");
		 AutoSegExpressServiceAsync galleryService =
		       (AutoSegExpressServiceAsync)GWT.create(AutoSegExpressService.class);
		
		 ServiceDefTarget endpoint = (ServiceDefTarget)galleryService;
		    endpoint.setServiceEntryPoint( entry);
		    
		    
		    AsyncCallback callback = new AsyncCallback() {
		          public void onSuccess(Object o) {
		           System.out.println("Callback: "+o+"!!!!!!!!!!!!!"+o.getClass());
		           
			           if(o instanceof Vector)
			           {
			        	   Vector<DatasetModelInfo> vmodel = (Vector<DatasetModelInfo>)o;
			        	   System.out.println("-------------------getDatasetModelInfo:"+vmodel);
			        	   segExp.udpateModelInfo(vmodel);
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
		       
		      
		    	   galleryService.getDatasetModelInfo(datasetID, callback);
	}
	
	public Vector<SlashImage> getSlashImages(String userName) throws java.lang.Exception
	 {
		 System.out.println("entry:"+entry);
		 imageV=  new Vector<SlashImage>();
		 
		 AutoSegExpressServiceAsync galleryService =
		       (AutoSegExpressServiceAsync)GWT.create(AutoSegExpressService.class);
		
		 ServiceDefTarget endpoint = (ServiceDefTarget)galleryService;
		    endpoint.setServiceEntryPoint( entry);
		    
		    
		    AsyncCallback callback = new AsyncCallback() {
		          public void onSuccess(Object o) {
		           System.out.println("Callback: "+o+"!!!!!!!!!!!!!"+o.getClass());
		           
		           if(o instanceof Vector)
		           {
		        	   imageV = (Vector<SlashImage>)o;
		        	   System.out.println("It is a Vector and its size:"+imageV.size());
		        	   segExp.setSlashImages(imageV);
		        	   
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
		       
		      
		    	   galleryService.getSlashImages(userName, callback);
		     
		       return imageV;
		 
		 
		 
	 }
}
