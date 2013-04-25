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
public class DeleteUtil {

SlashDBService db = new SlashDBService();


    public boolean deleteAnnotations(long datasetID, int modelID)throws Exception
    {
        long count = this.getGeometryCount(datasetID);

        if(count > 0)
        {
            this.deleteFullAnnotationData(datasetID, modelID);
        }
        else
        {
            this.deletePartialAnnotationDate(datasetID, modelID);
        }

        return true;


    }


    public long getGeometryCount(long datasetID)throws Exception
    {
        long count = 0;
        String sql = "(select count(a.*)  from slash_dataset ds, slash_annotation a, slash_annot_geom_map map, slash_geometry g "+
                     " where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id "+
                     " and ds.dataset_id = ?)";

        System.out.println("\n\n\n"+sql+"\n\n\n");

        Connection c = db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setLong(1, datasetID);

        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            count = rs.getLong(1);
        }

        c.close();
        return count;
    }


    public boolean deleteFullAnnotationData(long datasetID, int modelID)throws Exception
    {

        System.out.println("-----------------------------deleteFullAnnotationData------------------------------");
        String insert0 = "insert into slash_annotation_deleted \n"+
                        " (select distinct  a.*  from slash_dataset ds, slash_annotation a, slash_annot_geom_map map, slash_geometry g \n"+
                        " where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id \n"+
                        " and ds.dataset_id = ? \n"+
                        " and a.version_number = ?)\n";


         System.out.println("\n\n\n"+insert0+"\n\n\n");

        Connection c = db.getConnection();
        PreparedStatement ps = c.prepareStatement(insert0);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        int count = ps.executeUpdate();


        String insert1 = "insert into slash_annot_geom_map_deleted \n"+
" (select distinct map.*  from slash_dataset ds, slash_annotation a, slash_annot_geom_map map, slash_geometry g \n"+
" where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id \n"+
" and ds.dataset_id = ? \n"+
" and a.version_number = ?)\n";
         System.out.println("\n\n\n"+insert1+"\n\n\n");


         ps = c.prepareStatement(insert1);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();


        String insert2 = "insert into slash_geometry_deleted \n"+
" (select distinct g.*  from slash_dataset ds, slash_annotation a, slash_annot_geom_map map, slash_geometry g \n"+
" where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id \n"+
" and ds.dataset_id = ? \n"+
" and a.version_number = ?) \n";
         System.out.println("\n\n\n"+insert2+"\n\n\n");

        ps = c.prepareStatement(insert2);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();

        String delete0 = "delete from slash_annot_geom_map where map_id in "+
            " (select map.map_id from slash_dataset ds, slash_annotation a, slash_annot_geom_map map, slash_geometry g "+
            " where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id "+
            " and ds.dataset_id = ? \n"+
            " and a.version_number = ?) \n";

         System.out.println("\n\n\n"+delete0+"\n\n\n");
        
        ps = c.prepareStatement(delete0);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();


        String delete1 = "delete from slash_geometry where geom_id in \n"+
                        " (select g.geom_id from slash_dataset ds, slash_annotation a, slash_annot_geom_map_deleted map, slash_geometry g \n"+
                        " where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id \n"+
                        " and ds.dataset_id = ? \n"+
                        " and a.version_number = ?)\n";
        System.out.println("\n\n\n"+delete1+"\n\n\n");

        ps = c.prepareStatement(delete1);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();

        String delete2 = "delete from slash_applicatoin_data where annotation_id in \n"+
                        " (select distinct a.annotation_id  from slash_dataset ds, slash_annotation_deleted a, slash_annot_geom_map_deleted map, slash_geometry_deleted g \n"+
                        " where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id  "+
                        " and ds.dataset_id = ? \n"+
                        " and a.version_number = ?)\n";
        System.out.println("\n\n\n"+delete2+"\n\n\n");
        ps = c.prepareStatement(delete2);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();


        String delete3 = "delete from slash_annotation where annotation_id in \n"+
                         " (select distinct a.annotation_id  from slash_dataset ds, slash_annotation_deleted a, slash_annot_geom_map_deleted map, slash_geometry_deleted g \n"+
                         " where ds.dataset_id = a.dataset_id and map.annotation_id = a.annotation_id and map.geometry_id = g.geom_id \n"+
                         " and ds.dataset_id = ? \n"+
                         " and a.version_number = ?)\n";
        System.out.println("\n\n\n"+delete3+"\n\n\n");
        ps = c.prepareStatement(delete3);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();

        String delete4 = "delete from  slash_model_count_cache where dataset_id = ? and version_number = ?";
        System.out.println("\n\n\n"+delete4+"\n\n\n");
        ps = c.prepareStatement(delete4);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();

        c.close();
        return true;
        
    }


    public boolean deletePartialAnnotationDate(long datasetID, int modelID)throws Exception
    {
        System.out.println("----------------------------deletePartialAnnotationDate----------------------------");

        String delete0 = "delete from slash_applicatoin_data where annotation_id in  "+
                            " (select annotation_id from slash_annotation where dataset_id = ?)";
          System.out.println("\n\n\n"+delete0+"\n\n\n");
         Connection c = db.getConnection();
        PreparedStatement ps = c.prepareStatement(delete0);
        ps.setLong(1, datasetID);
        int count = ps.executeUpdate();


        String delete1 = "delete from slash_annotation where dataset_id = ?";
         System.out.println("\n\n\n"+delete1+"\n\n\n");
         c = db.getConnection();
        ps = c.prepareStatement(delete1);
        ps.setLong(1, datasetID);
        count = ps.executeUpdate();

         String delete2 = "delete from  slash_model_count_cache where dataset_id = ? and version_number = ?";
          System.out.println("\n\n\n"+delete2+"\n\n\n");
        ps = c.prepareStatement(delete2);
        ps.setLong(1, datasetID);
        ps.setInt(2, modelID);
        count = ps.executeUpdate();

         c.close();
        return true;

        
    }
}
