package edu.ucsd.ccdb.slash.autoseg.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CytosegInputs implements java.io.Serializable,IsSerializable
{
	private Long trainingDatasetID = null;
	private Long inputDatasetID = null;
	private Long minX = null;
	private Long minY = null;
	private Long minZ = null;
	private Long maxX = null;
	private Long maxY = null;
	private Long maxZ = null;
	
	private Double p_voxel_w = null;
	private Double n_voxel_w = null;
	private Integer p_contour_m = null;
	private Integer n_contour_m = null;
	private Double threshold = null;
	
	private String userName= null;
	
	private String modelName = null;
	private String serverName = null;
	
	private Integer trainModelID = null;
	/**
	 * @param trainingDatasetID the trainingDatasetID to set
	 */
	public void setTrainingDatasetID(Long trainingDatasetID) {
		this.trainingDatasetID = trainingDatasetID;
	}
	/**
	 * @return the trainingDatasetID
	 */
	public Long getTrainingDatasetID() {
		return trainingDatasetID;
	}
	/**
	 * @param inputDatasetID the inputDatasetID to set
	 */
	public void setInputDatasetID(Long inputDatasetID) {
		this.inputDatasetID = inputDatasetID;
	}
	/**
	 * @return the inputDatasetID
	 */
	public Long getInputDatasetID() {
		return inputDatasetID;
	}
	/**
	 * @param minX the minX to set
	 */
	public void setMinX(Long minX) {
		this.minX = minX;
	}
	/**
	 * @return the minX
	 */
	public Long getMinX() {
		return minX;
	}
	/**
	 * @param minY the minY to set
	 */
	public void setMinY(Long minY) {
		this.minY = minY;
	}
	/**
	 * @return the minY
	 */
	public Long getMinY() {
		return minY;
	}
	/**
	 * @param minZ the minZ to set
	 */
	public void setMinZ(Long minZ) {
		this.minZ = minZ;
	}
	/**
	 * @return the minZ
	 */
	public Long getMinZ() {
		return minZ;
	}
	/**
	 * @param maxX the maxX to set
	 */
	public void setMaxX(Long maxX) {
		this.maxX = maxX;
	}
	/**
	 * @return the maxX
	 */
	public Long getMaxX() {
		return maxX;
	}
	/**
	 * @param maxY the maxY to set
	 */
	public void setMaxY(Long maxY) {
		this.maxY = maxY;
	}
	/**
	 * @return the maxY
	 */
	public Long getMaxY() {
		return maxY;
	}
	/**
	 * @param maxZ the maxZ to set
	 */
	public void setMaxZ(Long maxZ) {
		this.maxZ = maxZ;
	}
	/**
	 * @return the maxZ
	 */
	public Long getMaxZ() {
		return maxZ;
	}
	/**
	 * @param p_voxel_w the p_voxel_w to set
	 */
	public void setP_voxel_w(Double p_voxel_w) {
		this.p_voxel_w = p_voxel_w;
	}
	/**
	 * @return the p_voxel_w
	 */
	public Double getP_voxel_w() {
		return p_voxel_w;
	}
	/**
	 * @param n_voxel_w the n_voxel_w to set
	 */
	public void setN_voxel_w(Double n_voxel_w) {
		this.n_voxel_w = n_voxel_w;
	}
	/**
	 * @return the n_voxel_w
	 */
	public Double getN_voxel_w() {
		return n_voxel_w;
	}
	/**
	 * @param p_contour_m the p_contour_m to set
	 */
	public void setP_contour_m(Integer p_contour_m) {
		this.p_contour_m = p_contour_m;
	}
	/**
	 * @return the p_contour_m
	 */
	public Integer getP_contour_m() {
		return p_contour_m;
	}
	/**
	 * @param n_contour_m the n_contour_m to set
	 */
	public void setN_contour_m(Integer n_contour_m) {
		this.n_contour_m = n_contour_m;
	}
	/**
	 * @return the n_contour_m
	 */
	public Integer getN_contour_m() {
		return n_contour_m;
	}
	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
	/**
	 * @return the threshold
	 */
	public Double getThreshold() {
		return threshold;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	
	public String toString()
	{
		String s = "\n----------trainingDatasetID:"+trainingDatasetID;
		s = s+"\n----------inputDatasetID:"+inputDatasetID;
		s = s+"\n----------minX:"+minX;
		s = s+"\n----------minY:"+minY;
		s = s+"\n----------minZ:"+minZ;
		s = s+"\n----------maxX:"+maxX;
		s = s+"\n----------maxY:"+maxY;
		s = s+"\n----------maxZ:"+maxZ;
		s = s+"\n----------p_voxel_w:"+p_voxel_w;
		s = s+"\n----------n_voxel_w:"+n_voxel_w;
		s = s+"\n----------p_contour_m:"+p_contour_m;
		s = s+"\n----------n_contour_m:"+n_contour_m;
		s = s+"\n----------threshold:"+threshold;
		s = s+"\n----------userName:"+userName;
		
		return s;
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
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}
	/**
	 * @param trainModelID the trainModelID to set
	 */
	public void setTrainModelID(Integer trainModelID) {
		this.trainModelID = trainModelID;
	}
	/**
	 * @return the trainModelID
	 */
	public Integer getTrainModelID() {
		return trainModelID;
	}
}
