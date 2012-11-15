package edu.ucsd.ccdb.slash.autoseg.client;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;




public interface AutoSegExpressService extends RemoteService
{
	Vector<SlashImage> getSlashImages(String userName) throws java.lang.Exception;
	Vector<DatasetModelInfo> getDatasetModelInfo(long datasetID)throws java.lang.Exception;
	Long getNexModelID(long datasetID)throws java.lang.Exception;
	boolean submitCytoseg(CytosegInputs cinputs)throws java.lang.Exception;
}
