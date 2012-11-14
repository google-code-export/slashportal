package edu.ucsd.ccdb.slash.autoseg.server;
import  edu.ucsd.ccdb.slash.autoseg.client.*;


import java.util.*;
import java.sql.*;
public class SlashDBUtil 
{
	SlashDBService db = new SlashDBService();
	
	
	public Long getNexModelID(long datasetID)throws Exception
	{
		Long mID = null;
		String sql  = " select max(version_number)+1 from slash_annotation where dataset_id = ?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setLong(1, datasetID);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next())
		{
			mID = rs.getLong(1);
		}
		
		c.close();
		
		return mID;
	
		
	}
	
	public Vector<DatasetModelInfo> getDatasetModelInfo(long datasetID)throws Exception
	{
		Vector<DatasetModelInfo> v = new Vector<DatasetModelInfo>();
		String sql = " select distinct version_number, count(annotation_id), version_nickname from slash_annotation where dataset_id = "+datasetID+" group by version_number,version_nickname";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		//ps.setLong(1, datasetID);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			DatasetModelInfo info = new DatasetModelInfo();
			Integer modelID = rs.getInt(1);
			Integer count = rs.getInt(2);
			String modelName = rs.getString("version_nickname");
			
			
			info.setModelID(modelID);
			info.setCount(count);
			info.setModelName(modelName);
			info.setDatasetID(datasetID);
			
			v.addElement(info);
		}
		c.close();
		return v;
		
	}
	
	public Long getDatasetIDBySlashImageID(long imageID)throws Exception
	{
		long dsID = -1;
		String sql = "select dataset_id from slash_dataset where resource_path in "+
					"(select 'file:'||actual_location from slash_image_box where image_id =?)";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setLong(1, imageID);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			dsID= rs.getLong(1);
		}
		c.close();
		
		if(dsID == -1)
		{
			sql = "select dataset_id from slash_dataset where resource_path in "+
			"(select 'file:'||actual_location || '.ppm' from slash_image_box where image_id =?)";
				c = db.getConnection();
				ps = c.prepareStatement(sql);
				ps.setLong(1, imageID);

				rs = ps.executeQuery();
		if(rs.next())
			{
				dsID= rs.getLong(1);
			}
			c.close();
		
		}
		
		return dsID;
		
	}
	
	public Vector<UserWithPermission> getUsersWithPermission(long imageID)throws java.lang.Exception
	{
		Vector<UserWithPermission>  v = new Vector<UserWithPermission> ();
		String sql = "select access_id, image_id, portal_user, owner,modified_date from slash_image_box_access where image_id =?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		long id = this.getNextSequence();
		ps.setLong(1, imageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			UserWithPermission userp = new UserWithPermission();
			long access_id = rs.getLong("access_id");
			long image_id = rs.getLong("image_id");
			String portal_user = rs.getString("portal_user");
			String owner = rs.getString("owner");
			String modified_date = rs.getString("modified_date");
			
			userp.setAccess_id(access_id);
			userp.setImage_id(image_id);
			userp.setPortal_user(portal_user);
			userp.setOwner(owner);
			userp.setModified_time(modified_date);
			
			v.addElement(userp);
			
		}
		c.close();
		return v;
		
	}
	
	public boolean unshareImage(long imageID, String owner, String recipient)throws java.lang.Exception
	{
		String sql = "delete from slash_image_box_access where image_id =? and portal_user = ?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setLong(1, imageID);
		ps.setString(2, recipient);
		int count = ps.executeUpdate();
		c.close();
		if(count > 0)
			return true;
		else
			return false;
		
	}
	
	public boolean shareImage(long imageID, String owner, String recipient)throws java.lang.Exception
	{
		String sql = "insert into slash_image_box_access (access_id, image_id, portal_user, owner, modified_date) \n"+
		" values(?,?,?,?,?)";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		long id = this.getNextSequence();
		ps.setLong(1, id);
		ps.setLong(2, imageID);
		ps.setString(3, recipient);
		ps.setString(4, owner);
		java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
		ps.setTimestamp(5, ts);
		int count = ps.executeUpdate();
		c.close();
		if(count > 0)
			return true;
		else
			return false;
		
	}
	
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
	
	public Boolean removeImage(String ipath, String portalUser)throws Exception
	{
		System.out.println("Server: ipath:"+ipath+"-----portalUser:"+portalUser);
		boolean result = true;
		String sql = "update slash_image_box set deleted=true where ipath = ? and portal_user = ?";
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, ipath);
		ps.setString(2, portalUser);
		
		int count = ps.executeUpdate();
		if(count == 0)
			result = false;
		
		
		sql = "update image_info set deleted=true where file_path = ?";
		ps = c.prepareStatement(sql);
		ps.setString(1, ipath);
		ps.executeUpdate();
		
		return result;
	}
	
	public Vector<SlashImage> getSlashImages(String userName) throws java.lang.Exception
	{
		System.out.println("------------------server-------getSlashImages:"+userName);
		Vector<SlashImage> v = new Vector<SlashImage>();
		
		
		/*String sql = "select distinct image_id, ipath, attribution, contact, description, email, license, portal_user, modified_time, actual_location, wib_url "+
				     "\n , x_max, y_max, z_max"+
					 "\n from  slash_image_box where deleted= false and portal_user =?";*/
		
		
	/*	String sql = "(select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
"\n from  slash_image_box i, slash_dataset ds where deleted= false and portal_user = ?  and ds.resource_path = 'file:'|| i.actual_location) "+
"\n union "+
"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
"\n from  slash_image_box i, slash_dataset ds where deleted= false and portal_user = ?  and ds.resource_path = 'file:'|| i.actual_location || '.ppm' )"+
"\n union "+
"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
"\n from  slash_image_box i, slash_dataset ds where deleted= false and portal_user = ?  and ds.actual_location = i.actual_location) ";
		*/
		
		
		String sql =  "(select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
		"\n from  slash_image_box i, slash_dataset ds where deleted= false and portal_user = ?  and ds.resource_path = 'file:'|| i.actual_location) "+
		"\n union "+
		"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
		"\n from  slash_image_box i, slash_dataset ds where deleted= false and portal_user = ?  and ds.resource_path = 'file:'|| i.actual_location || '.ppm' )"+
		"\n union "+
		"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
		"\n from  slash_image_box i, slash_dataset ds where deleted= false and portal_user = ?  and ds.actual_location = i.actual_location) union "+
				"(select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
			"\n from  slash_image_box i, slash_dataset ds, slash_image_box_access acc where  i.image_id = acc.image_id and   deleted= false and (i.portal_user = ? or acc.portal_user = ?) and ds.resource_path = 'file:'|| i.actual_location) "+
			"\n union  "+
			"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
			"\n from  slash_image_box i, slash_dataset ds, slash_image_box_access acc where i.image_id = acc.image_id and  deleted= false and (i.portal_user = ? or acc.portal_user = ?) and ds.resource_path = 'file:'|| i.actual_location || '.ppm' )"+
			"\n union "+
			"\n (select distinct i.image_id,  i.ipath,  i.attribution,  i.contact,  i.description,  i.email,  i.license,  i.portal_user,  i.modified_time,  i.actual_location,  i.wib_url ,  i.x_max,  i.y_max,  i.z_max, ds.dataset_id "+
			"\n from  slash_image_box i, slash_dataset ds, slash_image_box_access acc where i.image_id = acc.image_id and  deleted= false and (i.portal_user = ? or acc.portal_user = ?)  and ds.actual_location = i.actual_location) ";


		
		
		
		
		System.out.println(sql);
		
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setString(2, userName);
		ps.setString(3, userName);
		ps.setString(4, userName);
		ps.setString(5, userName);
		ps.setString(6, userName);
		ps.setString(7, userName);
		ps.setString(8, userName);
		ps.setString(9, userName);

		
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
	
	/* public Vector<SlashImage> getSlashImages(String userName) throws java.lang.Exception
	{
		Vector<SlashImage> v = new Vector<SlashImage>();
	//	String sql = "select distinct image_id, ipath, attribution, contact, description, email, license, portal_user, modified_time, actual_location, wib_url "+
	//			     " from  slash_image_box where deleted= false and portal_user =?";
		
		String sql = "select distinct image_id, ipath, attribution, contact, description, email, license, portal_user, modified_time, actual_location, wib_url , true as is_owner "+
                     "\n from  slash_image_box where deleted= false and portal_user = ?  "+
                     "\n union "+
                     "\n select distinct ibox.image_id, ibox.ipath, ibox.attribution, ibox.contact, ibox.description, ibox.email, ibox.license, ibox.portal_user, ibox.modified_time, ibox.actual_location, ibox.wib_url , false as is_owner "+
                     "\n from  slash_image_box ibox, slash_image_box_access acc where ibox.image_id = acc.image_id and acc.portal_user = ? ";		
		
		
		
		System.out.println(sql);
		
		Connection c = db.getConnection();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setString(2, userName);
		
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
			boolean isOwner = rs.getBoolean("is_owner");
			
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
			image.setOwner(isOwner);
			
			v.addElement(image);
		}
		
		return v;
		
	}*/
	
	public static void main(String[] args)throws Exception
	{
		SlashDBUtil util = new SlashDBUtil();
		//Vector v = util.getSlashImages("ccdbuser");
		SlashDBService db = new SlashDBService();
        db.readConfig("E:\\slash-db-config.properties");
		//System.out.println(v.size());
		util.getDatasetModelInfo(504052);
	}
	

}
