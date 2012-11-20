package edu.ucsd.ccdb.slash.cory.client;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
public interface CoryAutoSegService  extends RemoteService
{
	String getCoryXML(Vector<GPoint> vpoint) throws java.lang.Exception;
}
