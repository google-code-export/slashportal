/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ucsd.ccdb.slash.results.portlet;
import java.util.*;
/**
 *
 * @author wawong
 */
public class HTMLGenerator
{
    public String imagePrefix ="http://ccdb-portal.crbs.ucsd.edu:8081/Data_browser_image_servlet/GetIRODIMageServlet?filePath=";


    public  String generateModelOnlyHTML( Vector<SlashImage> vimage, HashMap<String, Vector> imageMap)
    {
        StringBuffer buff = new StringBuffer();
        buff.append("<center><table border=\"1\" width=\"1000\">\n");
        buff.append("<tr>\n");
        buff.append("<td align=\"center\" width=\"15%\">\n");
        buff.append("Model ID");
        buff.append("</td>\n");
        buff.append("<td align=\"center\" width=\"25%\">\n");
        buff.append("Model name");
        buff.append("</td>\n");
        buff.append("<td align=\"center\" width=\"60%\">\n");
        buff.append("Options");
        buff.append("</td>\n");
        buff.append("</tr>\n");

        for(int i=0;i<vimage.size();i++)
        {
            SlashImage si = vimage.get(i);

            long datasetID =  si.getDatasetID();
            Vector<DatasetModelInfo> v = imageMap.get(datasetID+"");
            if(v == null)
                continue;
            for(int j=0;j<v.size();j++)
            {
                 buff.append("<tr>\n");
                 DatasetModelInfo info = v.get(j);
                 buff.append("<td align=\"center\">\n");
                 buff.append(datasetID+"-"+info.getModelID());
                 buff.append("</td>\n");
                 buff.append("<td align=\"center\">\n");
                 buff.append("<br/>&nbsp;&nbsp;");
                 if(info.getModelName() != null)
                     buff.append(info.getModelName()+" (annotation count:"+info.getCount()+")");
                 else
                      buff.append("Model#"+info.getModelID()+" (annotation count:"+info.getCount()+")");
                 buff.append("<br/>&nbsp;&nbsp;");
                
                 buff.append("</td>\n");
                 buff.append("<td>\n");
                 String infoHTML = getSingleAnnotationHML(info, datasetID);
                 buff.append(infoHTML);
                 buff.append("</td>\n");

                
                  
                 buff.append("</tr>\n");
            }

        }

        buff.append("</table></center>\n");
        return buff.toString();
    }


    public String generateHTML( Vector<SlashImage> vimage, HashMap<String, Vector> imageMap)
    {
        StringBuffer buff = new StringBuffer();
        buff.append("<center><table border=\"1\">\n");

        buff.append("<tr>\n");
        buff.append("<td align=\"center\">\n");
        buff.append("Display");
        buff.append("</td>\n");
        buff.append("<td align=\"center\">\n");
        buff.append("ID");
        buff.append("</td>\n");
        buff.append("<td align=\"center\">\n");
        buff.append("Name");
        buff.append("</td>\n");
        buff.append("<td align=\"center\">\n");
        buff.append("Options");
        buff.append("</td>\n");
        buff.append("</tr>\n");


        for(int i=0;i<vimage.size();i++)
        {
            SlashImage si = vimage.get(i);
            buff.append("<tr>\n");


            String display = "<img src=\""+imagePrefix+si.getActualLocation()+".512.jpg"+"\" width=\"100\" height=\"100\">";
            buff.append("<td>\n");
            buff.append(display);
            buff.append("</td>\n");

            buff.append("<td>\n");
            buff.append(si.getDatasetID());
            buff.append("</td>\n");



            String name = this.getName(si.getActualLocation());
            buff.append("<td align=\"center\">\n");
            buff.append(name);
            buff.append("</td>\n");

            long datasetID =  si.getDatasetID();
            Vector<DatasetModelInfo> v = imageMap.get(datasetID+"");
            if(v == null)
                v = new Vector<DatasetModelInfo>();

            String ahtml = this.getAnnotationHML(v, datasetID);
            buff.append("<td>\n");
            buff.append(ahtml);
            buff.append("</td>\n");


  
            buff.append("</tr>\n");

        }
        buff.append("</table></center>\n");

        return buff.toString();
    }

	public static String getName(String path)
	{
		String[] items = path.split("/");
		if(items == null)
			return null;

		if(items.length > 0)
			return items[items.length-1];
		else
			return null;


	}


        public  String getSingleAnnotationHML(DatasetModelInfo info, long datasetID)
        {
            /* String s = "<table width=\"600\">";
				s = s+"<tr >";
				s = s+"<td width=\"33%\">";
				s = s+"<a href=\"http://ccdb-dev-portal.crbs.ucsd.edu/WebImageBrowser/cgi-bin/start.pl?plugin=SLASH&datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">WIB</a>";
				s = s+"</td>";
				s = s+"<td width=\"33%\">";
				s = s+"<a href=\"http://galle.crbs.ucsd.edu:8081/Canvas_GWT_Test3/Canvas_GWT_Test2.html?datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">Slash Drawing Board</a>";
				s = s+"</td>";
				s = s+"<td width=\"33%\">";
				s = s+"<a href=\" http://webgl-tests.crbs.ucsd.edu/model-viewer/?datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">3D Visualizer</a>";
				s = s+"</td>";
				s = s+"</tr>";
              s= s+"</table>"; */
            String space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            String wib = "<a href=\"http://ccdb-dev-portal.crbs.ucsd.edu/WebImageBrowser/cgi-bin/start.pl?plugin=SLASH&datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">WIB</a>";
            String slb = "<a href=\"http://galle.crbs.ucsd.edu:8081/Canvas_GWT_Test3/Canvas_GWT_Test2.html?datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">Slash Drawing Board</a>";
            String webgl = "<a href=\" http://webgl-tests.crbs.ucsd.edu/model-viewer/?datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">3D Visualizer</a>";




             String button = "<input name=\"removeBtn\" type=\"submit\" value=\"Remove:"+datasetID+"-"+info.getModelID()+"\" />";

             String s = "<center>"+wib+space+slb+space+webgl+space+button+"</center>";

            return s;


        }

        private String getAnnotationHML(Vector<DatasetModelInfo> v, long datasetID)
        {
            		String s = "<table width=\"500\" border=\"1\">";
			for(int i=0;i<v.size();i++)
			{
				DatasetModelInfo info = v.get(i);
				s = s+"<tr>";
				s = s+"<td>";

                                if(info.getModelName() != null)
                                    s = s+info.getModelName()+" (annotation count:"+info.getCount()+")";
                                else
                                    s = s+"Model#"+info.getModelID()+" (annotation count:"+info.getCount()+")";
				s = s+"</td>";
				s = s+"<td>";
				s = s+"<a href=\"http://ccdb-dev-portal.crbs.ucsd.edu/WebImageBrowser/cgi-bin/start.pl?plugin=SLASH&datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">WIB</a>";
				s = s+"</td>";
				s = s+"<td>";
				s = s+"<a href=\"http://galle.crbs.ucsd.edu:8081/Canvas_GWT_Test3/Canvas_GWT_Test2.html?datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">Slash Drawing Board</a>";
				s = s+"</td>";
				s = s+"<td>";
				s = s+"<a href=\" http://webgl-tests.crbs.ucsd.edu/model-viewer/?datasetID="+datasetID+"&modelID="+info.getModelID()+"\" target=\"_blank\">3D Visualizer</a>";
				s = s+"</td>";
                                s = s+"<td>";
                                s = s+"<input name=\"removeBtn\" type=\"submit\" value=\"Remove:"+datasetID+"-"+info.getModelID()+"\" />";
                                s = s+"</td>";



				s = s+"</tr>";


                               

			}


			s= s+"</table>";

                        return s;
        }

}
