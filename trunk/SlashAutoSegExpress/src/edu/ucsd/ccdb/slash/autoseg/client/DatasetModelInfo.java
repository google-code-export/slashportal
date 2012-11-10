package edu.ucsd.ccdb.slash.autoseg.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatasetModelInfo implements IsSerializable
{
	private int modelID = -1;
	private int count = 0;
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

}
