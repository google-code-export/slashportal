/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ucsd.ccdb.slash.results.portlet;

/**
 *
 * @author wawong
 */
public class DatasetModelInfo
{
    private long datasetID = -1;
    private int modelID = -1;
    private int count = -1;
    private String modelName = null;

    /**
     * @return the datasetID
     */
    public long getDatasetID() {
        return datasetID;
    }

    /**
     * @param datasetID the datasetID to set
     */
    public void setDatasetID(long datasetID) {
        this.datasetID = datasetID;
    }

    /**
     * @return the modelID
     */
    public int getModelID() {
        return modelID;
    }

    /**
     * @param modelID the modelID to set
     */
    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    

}
