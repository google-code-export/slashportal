package edu.ucsd.ccdb.slash.autoseg.client;

import com.google.gwt.user.client.rpc.IsSerializable;


public class DatasetModelInfo implements IsSerializable
{
	private int modelID = -1;
	private int count = 0;
	private String modelName = null;
	private long modifiedDate = 0;
	private long datasetID = -1;
	/**
	 * @param modelID the modelID to set
	 */
	public void setModelID(int modelID) {
		this.modelID = modelID;
	}
	/**
	 * @return the modelID
	 */
	public int getModelID() {
		return modelID;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public long getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param datasetID the datasetID to set
	 */
	public void setDatasetID(long datasetID) {
		this.datasetID = datasetID;
	}
	/**
	 * @return the datasetID
	 */
	public long getDatasetID() {
		return datasetID;
	}

}
