package edu.ucsd.ccdb.slash.cory.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CoryAutoSegServiceAsync {
	
	
	void getCoryXML(Vector<GPoint> vpoint, AsyncCallback callback);

}
