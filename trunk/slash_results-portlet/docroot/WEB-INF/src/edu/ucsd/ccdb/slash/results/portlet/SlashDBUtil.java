/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ucsd.ccdb.slash.results.portlet;
import java.util.*;
import java.sql.*;
/**
 *
 * @author wawong
 */
public class SlashDBUtil {


    SlashDBService db = new SlashDBService();


    public int getNextVersionNumber(long datasetID)throws Exception
    {
        int version = -1;
        String sql = "select max(a.version_number) from slash_dataset ds, slash_annotation a, slash_annot_geom_map map, slash_geometry g "+
" where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id and a.version_number is not null "+
" and ds.dataset_id = ?";

        Connection c = db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setLong(1, datasetID);
         ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            version =rs.getInt(1);
        }
        c.close();

        return version+1;
    }

    public int getVersionNumberByName(long datasetID, String name)throws Exception
    {
        int version = -1;
        String sql = "select distinct a.version_number from slash_dataset ds, slash_annotation a, slash_annot_geom_map map, slash_geometry g "+
" where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id and a.version_number is not null "+
" and ds.dataset_id = ? and a.version_nickname = ?";

        Connection c = db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setLong(1, datasetID);
        ps.setString(2, name);
        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            version =rs.getInt(1);
        }
        c.close();

        return version;
    }


public Vector<SlashImage> getSlashImages(String userName) throws java.lang.Exception
	{
		System.out.println("------------------server-------getSlashImages:"+userName);
		Vector<SlashImage> v = new Vector<SlashImage>();


		/*String sql = "select distinct image_id, ipath, attribution, contact, description, email, license, portal_user, modified_time, actual_location, wib_url "+
				     "\n , x_max, y_max, z_max"+
					 "\n from  slash_image_box where deleted= false and portal_user =?";*/


		String sql = "(select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
"\n from  slash_image_box i, slash_dataset ds where deleted= false and (portal_user = ? or is_public = true) and ds.resource_path = 'file:'|| i.actual_location) "+
"\n union "+
"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
"\n from  slash_image_box i, slash_dataset ds where deleted= false and (portal_user = ? or is_public = true)  and ds.resource_path = 'file:'|| i.actual_location || '.ppm' )"+
"\n union "+
"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
"\n from  slash_image_box i, slash_dataset ds where deleted= false and (portal_user = ? or is_public = true)  and ds.actual_location = i.actual_location) order by dataset_id  asc";

		System.out.println(sql);

		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setString(2, userName);
		ps.setString(3, userName);

		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			SlashImage image = new SlashImage();
			long imageID = rs.getLong("image_id");
			String ipath = rs.getString("ipath");
			String attr = rs.getString("attribution");
			String contact = rs.getString("contact");
			String description =rs.getString("description");
			String email = rs.getString("email");
			String license = rs.getString("license");

			java.sql.Timestamp ts = rs.getTimestamp("modified_time");

			String actual_location = rs.getString("actual_location");
			String wib_url = rs.getString("wib_url");
			String portal_user = rs.getString("portal_user");

			Long xmax= rs.getLong("x_max");
			Long ymax= rs.getLong("y_max");
			Long zmax= rs.getLong("z_max");
			Long dsID = rs.getLong("dataset_id");

			image.setImageID(imageID);
			image.setIpath(ipath);
			image.setAttribution(attr);
			image.setContact(contact);
			image.setDescription(description);
			image.setEmail(email);
			image.setLicense(license);
			image.setActualLocation(actual_location);
			image.setModified_time(ts.toString());
			image.setWibURL(wib_url);
			image.setPortal_user(portal_user);

			image.setXmax(xmax);
			image.setYmax(ymax);
			image.setZmax(zmax);
			image.setDatasetID(dsID);

			v.addElement(image);
		}

		return v;

	}


       public  HashMap<String, Vector> getDataModelInfo() throws Exception
       {
           HashMap<String, Vector> map = new HashMap<String, Vector>();
           //Vector<DatasetModelInfo> v = new Vector<DatasetModelInfo>();
           //String sql = "select distinct dataset_id, version_number, count(annotation_id),version_nickname from slash_annotation  group by dataset_id, version_number, version_nickname";
           String sql = "select dataset_id, version_number, count, version_nickname from slash_model_count_cache";

           Connection c = db.getConnection();
           PreparedStatement ps = c.prepareStatement(sql);

           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
               long datasetID = rs.getLong("dataset_id");
               int version = rs.getInt("version_number");
               String version_nickname = rs.getString("version_nickname");
               int count = rs.getInt(3);
               Vector<DatasetModelInfo> v = null;
               if(!map.containsKey(datasetID+""))
               {
                   v = new Vector<DatasetModelInfo>();
               }
               else
               {
                   v = map.get(datasetID+"");
               }

               DatasetModelInfo model = new DatasetModelInfo();
               model.setDatasetID(datasetID);
               model.setModelID(version);
               model.setCount(count);
               model.setModelName(version_nickname);

               v.addElement(model);
               map.put(datasetID+"", v);
           }
           c.close();
           return map;
       }


       public static void main(String[] args)throws Exception
      {
            SlashDBUtil util = new SlashDBUtil();
            int version = util.getVersionNumberByName(281729, "test");//util.getNextVersionNumber(-1);
           System.out.println(version);

     }
}
