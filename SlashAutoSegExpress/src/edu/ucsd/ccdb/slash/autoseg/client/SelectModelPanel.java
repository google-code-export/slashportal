package edu.ucsd.ccdb.slash.autoseg.client;
import java.util.Vector;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.client.IntegerRenderer;

public class SelectModelPanel  extends PopupPanel
{
	VerticalPanel vpanel = new VerticalPanel();
	
	public SelectModelPanel()
	{
		this.setWidget(vpanel);
		vpanel.setSize("665px", "215px");
		

		
		HorizontalPanel hpanel = new HorizontalPanel();
		vpanel.add(hpanel);
		
		HTML selctModelLabel = new HTML("Select a segmentation model:");
		hpanel.add(selctModelLabel);
		
		ValueListBox valueListBox = new ValueListBox(IntegerRenderer.instance());
		hpanel.add(valueListBox);
		valueListBox.setWidth("474px");
		
		HorizontalPanel hpanel2 = new HorizontalPanel();
		vpanel.add(hpanel2);
		hpanel2.setWidth("656px");
		
		Button selectModelBtn = new Button("Select this model");
		hpanel2.add(selectModelBtn);
		
		Button viewModelBtn = new Button("View this model");
		hpanel2.add(viewModelBtn);
		
		Button btnCreateANew = new Button("Create a new model");
		hpanel2.add(btnCreateANew);
		
		Button cancelBtn = new Button("Cancel");
		hpanel2.add(cancelBtn);
		
	}
	
	
}
