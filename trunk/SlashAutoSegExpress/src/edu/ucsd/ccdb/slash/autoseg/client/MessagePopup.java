package edu.ucsd.ccdb.slash.autoseg.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MessagePopup extends  PopupPanel 
{
	VerticalPanel panel = new VerticalPanel();
	Button button = new Button();
	MessagePopup self = this;
	public  MessagePopup(String message)
	{
		
		this.add(panel);
		panel.setHorizontalAlignment(panel.ALIGN_CENTER);
		panel.setVerticalAlignment(panel.ALIGN_MIDDLE);
		this.setSize("500px", "150px");
		HTML html = new HTML();
		html.setHeight("109px");
		html.setHTML("<center>"+message+"</center>");
		
		panel.add(html);
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				self.setVisible(false);
			}
		});
		button.setText("Close");
		panel.add(button);
		button.setWidth("60px");
	}

}
