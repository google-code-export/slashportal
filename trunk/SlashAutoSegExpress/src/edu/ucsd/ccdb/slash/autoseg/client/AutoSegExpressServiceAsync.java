package edu.ucsd.ccdb.slash.autoseg.client;
import com.google.gwt.user.client.rpc.AsyncCallback;




import java.util.*;
public interface AutoSegExpressServiceAsync {
	void getSlashImages(String userName, AsyncCallback callback);
	void getDatasetModelInfo(long datasetID, AsyncCallback callback)throws java.lang.Exception;
	void getNexModelID(long datasetID, AsyncCallback callback)throws java.lang.Exception;
	void submitCytoseg(CytosegInputs cinputs, AsyncCallback callback)throws java.lang.Exception;
}
