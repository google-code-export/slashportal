package edu.ucsd.ccdb.slash.cory.client;
import com.google.gwt.user.client.rpc.IsSerializable;
public class GPoint implements IsSerializable
{
	private int x = -1;
	private int y = -1;
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

}
