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
public class DataUtil {


    public static Vector<SlashImage> reorderImageByName(Vector<SlashImage> vimage)
    {
        TreeSet<String> ts = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        HashMap<String,SlashImage> map = new HashMap<String,SlashImage>();
        for(int i=0;i<vimage.size();i++)
        {
            SlashImage si = vimage.get(i);
            String name = HTMLGenerator.getName(si.getActualLocation())+"--"+si.getImageID();
            ts.add(name);
            map.put(name,si);
        }

        Vector<SlashImage> vimage2 = new Vector<SlashImage>();
        Iterator<String> itr = ts.iterator();
        while(itr.hasNext())
        {
            String name = itr.next();
            SlashImage si = map.get(name);
            if(si != null);
                vimage2.addElement(si);
        }
        return vimage2;
    }
}
