package edu.ucsd.ccdb.slash.autoseg.client;
import java.util.Vector;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;



public class SelectImagePanel  extends PopupPanel
{
	SelectImagePanel self = this;
	Vector<SlashImage> imageV = null;
	ScrollPanel scrollPanel = new ScrollPanel();
	VerticalPanel imagePanel = new VerticalPanel();
	
	
	SlashAutoSegExpress  segExp = null;
	
	private TextBox modelName = new TextBox();
	public String inputType =null;
	public SelectImagePanel(Vector<SlashImage> imageV, SlashAutoSegExpress sse,String itype)
	{
		inputType = itype;
		this.segExp = sse;
		this.imageV = imageV;
		VerticalPanel root = new VerticalPanel();
		
		this.add(root);
		root.setSize("789px", "534px");
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("789px", "500px");
		scrollPanel.setHeight("463px");
		scrollPanel.add(imagePanel);
		root.add(scrollPanel);
		
		Button btnClose = new Button("Close");
		btnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				self.setVisible(false);
			}
		});
		root.setHorizontalAlignment(root.ALIGN_CENTER);
		root.add(btnClose);
		
		initImages(imageV);
		
		
		
	}
	
	public void initImages(java.util.Vector<SlashImage> imageV)
	{
		//this.flexTable.clear();
		this.imagePanel.clear();

		
		
		int row = 0;
		int col = 0;
		
		System.out.println("Image V size:"+imageV.size());
		
		for(int i=0;i<imageV.size();i++)
		{
			SlashImage image = imageV.get(i);
			

			ThumbnailPanel tpanel = new ThumbnailPanel(image,segExp,inputType);
			this.imagePanel.add(tpanel);
			
		//	flexTable.setWidget(row,col,tpanel);
			
			
			
			
			if(col == 10)
			{
				row++;
				col=0;
			}
			col++;
			
		}
		
		
	}
	
}
