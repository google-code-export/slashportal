package edu.ucsd.ccdb.slash.cory.server;
import  edu.ucsd.ccdb.slash.cory.client.*;

import java.util.*;

import java.util.*;

import java.net.*;
import java.io.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

    import org.jdom.output.*;


    import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class XMLGenerator 
{
	public String generatreXML(Vector<GPoint> vpoint,String rpath)throws java.lang.Exception
	{
		Element annot = new Element("ANNOTATION");
        Element mdate = new Element("MODIFIED_DATE");

        java.util.Date d = new java.util.Date(System.currentTimeMillis());
        mdate.setText(d.toString());
        annot.addContent(mdate);


       Element resourcePath = new Element("RESOURCE");
       resourcePath.setAttribute("filepath", rpath);
       annot.addContent(resourcePath);

       Element geometries = new Element("GEOMETRIES");
       geometries.setAttribute("coord_name","default");
       annot.addContent(geometries);
       
       long id = 1;
       for(int i=0;i<vpoint.size();i++)
       {
    	   GPoint gp = vpoint.get(i);
    	   Element geom = new Element("GEOMETRY");
           geom.setAttribute("user_name", "guest");
           geom.setAttribute("modified_time",System.currentTimeMillis()+"");
           
           Element point3d = new Element("POINT_3D");
           point3d.setAttribute("ID", id+"");
           id++;
           
           Element point = new Element("POINT");
           point.setText(gp.getX()+","+gp.getY()+","+0);
           point3d.addContent(point);
           geom.addContent(point3d);
           
           
           geometries.addContent(geom);
           
       }
       
       ByteArrayOutputStream bi = new ByteArrayOutputStream();

       XMLOutputter ouputter = new XMLOutputter();
       ouputter.output(annot, bi);

        return bi.toString();
	}

	
	public static void main(String[] args)throws java.lang.Exception
	{
		String rpath = "http://galle.crbs.ucsd.edu/test_image.png";
		Vector<GPoint> vpoint = new Vector<GPoint>();
		
		GPoint gp = new GPoint();
		gp.setX(1);
		gp.setY(1);
		vpoint.add(gp);
		
		XMLGenerator  xg = new XMLGenerator ();
		String xml =xg.generatreXML(vpoint, rpath);
		System.out.println(xml);
		
	}
}
