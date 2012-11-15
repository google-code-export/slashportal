package edu.ucsd.ccdb.slash.autoseg.client;
import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.client.IntegerRenderer;

public class CreateNewModelPanel extends PopupPanel
{
	CreateNewModelPanel self = this;
	AutoSegExpressExc exc = new AutoSegExpressExc(this);
	public SelectModelPanel smodelPanel = null;
	TextBox modelName = new TextBox();
	
	long modelID = -1;
	
	private static native JavaScriptObject newWindow(String url, String name, String features)/*-{
    var window = $wnd.open(url, name, features);
    return window;
}-*/;

private static native void setWindowTarget(JavaScriptObject window, String target)/*-{
    window.location = target;
}-*/;

	public DatasetModelInfo info = null;
	
	public void setModelID(long modelID)
	{
		this.modelID = modelID;
	}
	
	public CreateNewModelPanel(SelectModelPanel sp, DatasetModelInfo info2)
	{
		this.info = info2;
		this.smodelPanel = sp;
		
		VerticalPanel vpanel = new VerticalPanel();
		this.setWidget(vpanel);
		vpanel.setSize("509px", "104px");
		
		HorizontalPanel hpanel = new HorizontalPanel();
		vpanel.add(hpanel);
		hpanel.setSize("509px", "53px");
		
		HTML htmlenterANew = new HTML("<b>Enter a new model name:</b>", true);
		hpanel.add(htmlenterANew);
		
		
		hpanel.add(modelName);
		modelName.setSize("277px", "27px");
		
		
		HorizontalPanel hpanel2 = new HorizontalPanel();
		vpanel.add(hpanel2);
		hpanel2.setWidth("504px");
		hpanel2.setHorizontalAlignment(hpanel2.ALIGN_CENTER);
		
		Button createBtn = new Button("Create");
		createBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String mname = modelName.getText();
				mname = mname.replace(" ", "_");
				if(modelID  > 0)
				{
					String url = "http://galle.crbs.ucsd.edu:8081/Canvas_GWT_Test3/Canvas_GWT_Test2.html?datasetID="+info.getDatasetID()+"&userName=ccdbuser&modelID="+modelID+"&modelName="+mname;
					JavaScriptObject window = newWindow("", "", "");
				    setWindowTarget(window, url);
				}
			}
		});
		hpanel2.add(createBtn);
		
		Button cancelBtn = new Button("Cancel");
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				smodelPanel.setVisible(true);
				self.setVisible(false);
			}
		});
		hpanel2.add(cancelBtn);
		
		try
		{
			exc.getNexModelID(this.info.getDatasetID());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
