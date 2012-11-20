package edu.ucsd.ccdb.slash.cory.client;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextArea;
public class XMLPopupPanel extends  PopupPanel 
{
	XMLPopupPanel self = this;
	
	public XMLPopupPanel(String xml)
	{
		VerticalPanel vpanel = new VerticalPanel();
		this.setWidget(vpanel);
		vpanel.setSize("839px", "482px");
		
		TextArea textArea = new TextArea();
		textArea.setText(xml);
		vpanel.add(textArea);
		textArea.setSize("831px", "446px");
		
		Button closeBtn = new Button("Close");
		closeBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				self.setVisible(false);
			}
		});
		vpanel.add(closeBtn);
		
	}

}
