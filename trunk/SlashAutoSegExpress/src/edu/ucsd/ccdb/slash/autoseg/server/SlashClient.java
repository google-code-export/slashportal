package edu.ucsd.ccdb.slash.autoseg.server;
import java.net.*;
import java.io.*;

import java.util.*;


public class SlashClient {
	public int submitCytoseg(String ipath, long datasetID, long inputDsID,String userName , String overwrite,
			Long xmin, Long xmax,
			Long ymin, Long ymax,
			Long zmin, Long zmax, String voxelWeight, String contourListWeight, String contourListThreshold,
			String modelName, String serverName, long trainingModelID)throws Exception
    {

        
        String urlPath = Constants.postUploadHost+"/SlashPostUploadService/RunCytoSegService?ipath="+ipath+"&datasetID="+datasetID
        +"&userName="+userName+"&overwrite="+overwrite+"&inputDsID="+inputDsID+
        "&xmin="+xmin+"&xmax="+xmax+
        "&ymin="+ymin+"&ymax="+ymax+
        "&zmin="+zmin+"&zmax="+zmax+
        "&voxelWeight="+voxelWeight+
        "&contourListWeight="+contourListWeight+
        "&contourListThreshold="+contourListThreshold+
        "&modelName="+modelName+
        "&cytosegServer="+serverName+
        "&trainingModelID="+trainingModelID;
        

        System.out.println(" submitImodAnnotation:"+urlPath);
         Object o = this.doPostQuery(urlPath, "");
         if(o != null && o instanceof Integer)
         {
        	 Integer count = (Integer)o;
        	 if(count.intValue() == 0)
        		 throw new Exception("Database error!");
        	 else
        		 return count;
         }
         else if(o instanceof String[])
         {
              StringBuffer buff = new StringBuffer();
              buff.append(urlPath);
              //Vector v = new Vector();
               String[] es = (String[])o;
               for(int i=0;i<es.length;i++)
               {
                   buff.append(es[i]+"\n");
               }

              throw new Exception(buff.toString());
          }
          else
              throw new Exception("Unknown response from the server:"+o);
    }
	private Object doPostQuery(String urlPath, Object objectToWrite)throws Exception
    {
           URL url = new URL(urlPath);

                  HttpURLConnection conn = (HttpURLConnection)url.openConnection();


                     conn.setDoOutput(true);
                   conn.setDoInput(true);
        conn.setRequestMethod( "POST" );
                   ObjectOutputStream outputToServlet = new ObjectOutputStream(conn.
                           getOutputStream());


                               outputToServlet.writeObject(objectToWrite);

                               outputToServlet.flush();
                   outputToServlet.close();
                   InputStream input = conn.getInputStream();
                   ObjectInputStream inputFromServlet = new ObjectInputStream(input);
                   Object o1 = inputFromServlet.readObject();

                  System.out.println( "RETURN: "+o1);      //Image Set ID



                              return o1;
    }
}
