/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ucsd.ccdb.slash.results.portlet;

public class SlashImage implements java.io.Serializable
{
	private long imageID = -1;
	private String ipath = null;
	private String attribution = null;
	private String contact = null;
	private String description = null;
	private String email = null;
	private String license = null;
	private String portal_user = null;
	private String actualLocation = null;
	private String modified_time = null;
	private String wibURL = null;

	private Long xmax = null;
	private Long ymax = null;
	private Long zmax = null;
	private Long datasetID = null;

	public String getWibURL() {
		return wibURL;
	}
	public void setWibURL(String wibURL) {
		this.wibURL = wibURL;
	}
	public String getModified_time() {
		return modified_time;
	}
	public void setModified_time(String modified_time) {
		this.modified_time = modified_time;
	}
	public long getImageID() {
		return imageID;
	}
	public void setImageID(long imageID) {
		this.imageID = imageID;
	}
	public String getIpath() {
		return ipath;
	}
	public void setIpath(String ipath) {
		this.ipath = ipath;
	}
	public String getAttribution() {
		return attribution;
	}
	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getPortal_user() {
		return portal_user;
	}
	public void setPortal_user(String portal_user) {
		this.portal_user = portal_user;
	}
	public String getActualLocation() {
		return actualLocation;
	}
	public void setActualLocation(String actualLocation) {
		this.actualLocation = actualLocation;
	}
	/**
	 * @param xmax the xmax to set
	 */
	public void setXmax(Long xmax) {
		this.xmax = xmax;
	}
	/**
	 * @return the xmax
	 */
	public Long getXmax() {
		return xmax;
	}
	/**
	 * @param ymax the ymax to set
	 */
	public void setYmax(Long ymax) {
		this.ymax = ymax;
	}
	/**
	 * @return the ymax
	 */
	public Long getYmax() {
		return ymax;
	}
	/**
	 * @param zmax the zmax to set
	 */
	public void setZmax(Long zmax) {
		this.zmax = zmax;
	}
	/**
	 * @return the zmax
	 */
	public Long getZmax() {
		return zmax;
	}
	/**
	 * @param datasetID the datasetID to set
	 */
	public void setDatasetID(Long datasetID) {
		this.datasetID = datasetID;
	}
	/**
	 * @return the datasetID
	 */
	public Long getDatasetID() {
		return datasetID;
	}


}

