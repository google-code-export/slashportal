package edu.ucsd.ccdb.slash.autoseg.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserWithPermission  implements IsSerializable
{
	private long access_id = -1;
	private long image_id = -1;
	private String portal_user = null;
	private String owner = null;
	private String modified_time = null;
	/**
	 * @param access_id the access_id to set
	 */
	public void setAccess_id(long access_id) {
		this.access_id = access_id;
	}
	/**
	 * @return the access_id
	 */
	public long getAccess_id() {
		return access_id;
	}
	/**
	 * @param image_id the image_id to set
	 */
	public void setImage_id(long image_id) {
		this.image_id = image_id;
	}
	/**
	 * @return the image_id
	 */
	public long getImage_id() {
		return image_id;
	}
	/**
	 * @param portal_user the portal_user to set
	 */
	public void setPortal_user(String portal_user) {
		this.portal_user = portal_user;
	}
	/**
	 * @return the portal_user
	 */
	public String getPortal_user() {
		return portal_user;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param modified_time the modified_time to set
	 */
	public void setModified_time(String modified_time) {
		this.modified_time = modified_time;
	}
	/**
	 * @return the modified_time
	 */
	public String getModified_time() {
		return modified_time;
	}

}
