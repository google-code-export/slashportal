package edu.ucsd.ccdb.slash.autoseg.client;
import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.client.IntegerRenderer;

public class SelectModelPanel  extends PopupPanel
{
	
	
	
	
	VerticalPanel vpanel = new VerticalPanel();
	public Vector<DatasetModelInfo> vmodel =null;
	ListBox listBox = new ListBox();
	SelectModelPanel self = this;
	
	SlashAutoSegExpress  segExp = null;
	
	public long datasetID = -1;
	public HashMap<String, DatasetModelInfo> modelMap = new HashMap<String,DatasetModelInfo>();
	
	private static native JavaScriptObject newWindow(String url, String name, String features)/*-{
    var window = $wnd.open(url, name, features);
    return window;
}-*/;

private static native void setWindowTarget(JavaScriptObject window, String target)/*-{
    window.location = target;
}-*/;
	
	
	private void initList()
	{
		modelMap.clear();
		for(int i=0;i<vmodel.size();i++)
		{
			DatasetModelInfo info = vmodel.get(i);
			
			if(this.datasetID < 0)
			{
				this.datasetID = info.getDatasetID();
			}
			
			modelMap.put(info.getModelID()+"", info);
			
			if(info.getModelName() == null)
			{
				listBox.addItem(""+info.getDatasetID()+"-"+info.getModelID()+" (count:"+info.getCount()+")" , info.getModelID()+"");
				
			}
			else
			{
				listBox.addItem(""+info.getModelName()+" (count:"+info.getCount()+")" , info.getModelID()+"");
			}
		}
	}
	
	
	public SelectModelPanel(Vector<DatasetModelInfo> vModel, SlashAutoSegExpress sse)
	{
		this.segExp = sse;
		this.vmodel = vModel;
		this.setWidget(vpanel);
		vpanel.setSize("665px", "215px");
		

		
		HorizontalPanel hpanel = new HorizontalPanel();
		vpanel.add(hpanel);
		
		HTML selctModelLabel = new HTML("Select a segmentation model:");
		hpanel.add(selctModelLabel);
		
		
		hpanel.add(listBox);
		listBox.setWidth("474px");
		
		HorizontalPanel hpanel2 = new HorizontalPanel();
		vpanel.add(hpanel2);
		hpanel2.setWidth("656px");
		
		Button selectModelBtn = new Button("Select this model");
		selectModelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				int index = listBox.getSelectedIndex();
				String val = listBox.getValue(index);
				System.out.println("-----------Select:"+val);
				
				DatasetModelInfo info = modelMap.get(val);
				if(info != null)
				{
					segExp.setTrainingModel(info);
					self.setVisible(false);
				}
				
			}
		});
		hpanel2.add(selectModelBtn);
		
		Button viewModelBtn = new Button("View this model");
		viewModelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = listBox.getSelectedIndex();
				String val = listBox.getValue(index);
				System.out.println("-----------Select:"+val);
				
				if(vmodel.size()>0)
				{
					DatasetModelInfo info = vmodel.get(0);
					
					
					String url = "http://galle.crbs.ucsd.edu:8081/Canvas_GWT_Test3/Canvas_GWT_Test2.html?datasetID="+info.getDatasetID()+"&userName=ccdbuser&modelID="+val;
					 JavaScriptObject window = newWindow("", "", "");
					 setWindowTarget(window, url);
				}
				

				
				
			}
		});
		hpanel2.add(viewModelBtn);
		
		Button btnCreateANew = new Button("Create a new model");
		btnCreateANew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = listBox.getSelectedIndex();
				String val = listBox.getValue(index);
				System.out.println("-----------Select:"+val);
				
				DatasetModelInfo info =modelMap.get(val);
				CreateNewModelPanel cmp = new CreateNewModelPanel(self, info);
				cmp.show();
				self.setVisible(false);
			}
		});
		hpanel2.add(btnCreateANew);
		
		Button cancelBtn = new Button("Cancel");
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				self.setVisible(false);
			}
		});
		hpanel2.add(cancelBtn);
		
		initList();
		
	}
	
	
}
