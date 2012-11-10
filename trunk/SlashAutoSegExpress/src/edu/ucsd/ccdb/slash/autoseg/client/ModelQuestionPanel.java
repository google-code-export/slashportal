package edu.ucsd.ccdb.slash.autoseg.client;
import java.util.Vector;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
public class ModelQuestionPanel  extends PopupPanel 
{
	VerticalPanel vpanel = new VerticalPanel();
	public ModelQuestionPanel()
	{
		this.setWidget(vpanel);
		vpanel.setSize("634px", "198px");
		
		HTML html = new HTML("New HTML", true);
		html.setHeight("117px");
		vpanel.add(html);
		
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.setHorizontalAlignment(hpanel.ALIGN_CENTER);
		vpanel.add(hpanel);
		hpanel.setWidth("626px");
		
		Button yesBtn = new Button("Yes");
		hpanel.add(yesBtn);
		
		Button noBtn = new Button("No");
		hpanel.add(noBtn);
		
		
	}

}
