package edu.ucsd.ccdb.slash.autoseg.client;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;


public class ThumbnailPanel extends com.google.gwt.user.client.ui.HorizontalPanel//com.google.gwt.user.client.ui.VerticalPanel
{
	public String durl2 = null;
	public String description2 = null;
	
	SlashImage simage2 = null;
	String ipath  = null;

	ThumbnailPanel tpanel;
	
	int nameMax = 50;
	ThumbnailPanel self = this;
	public SlashAutoSegExpress segExp = null;
	
	public String inputType =null;

	/**
	 * @wbp.parser.constructor
	 */
	public ThumbnailPanel(SlashImage simage, SlashAutoSegExpress sse, String itype) 
	{
		this.segExp = sse;
		tpanel = this;
		simage2 = simage;
		inputType = itype;
		
		ipath = simage.getIpath();
		String thumbnailURL = Constants.imagePrefix+simage.getActualLocation()+".jpg";
		System.out.println("thumbnailURL:");
		String name = this.getName(simage.getActualLocation());
		
		String displayURL = Constants.imagePrefix+simage.getActualLocation()+".512.jpg";
		System.out.println("displayURL:"+displayURL);
		
		this.description2 = simage.getDescription();
		//setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		//setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Image image = new Image();

		image.addClickHandler(new ClickHandler() {
	

			public void onClick(ClickEvent event) 
			{
				
					segExp.setTrainingImage(simage2,inputType);

				
			}
			
			
		});
		
		
		image.setUrl(thumbnailURL);
		add(image);
		image.setSize("100px", "100px");
		
		HTML lblTest = new HTML();
		lblTest.setWidth("350px");
		lblTest.setHTML(name);
		add(lblTest);
		
		Image image2 = new Image();
		image2.addDoubleClickHandler(new DoubleClickHandler(){
			public void onDoubleClick(DoubleClickEvent event) 
			{
				
			}
			
			
		});
		
		image2.addClickHandler(new ClickHandler() {
			

			public void onClick(ClickEvent event) {

				segExp.setTrainingImage(simage2,inputType);
			}
			
			
		});
		String vicon ="http://ccdb-portal.crbs.ucsd.edu/view-details_icon.png";
		
		
		image2.setUrl(vicon);
		//add(image2);
		
		VerticalPanel vpanel = new VerticalPanel();
		vpanel.add(image2);
		Button btnSelect = new Button("Select");
		btnSelect.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				segExp.setTrainingImage(simage2,inputType);
				
			}
		});
		vpanel.add(btnSelect);
		btnSelect.setWidth("100px");
		add(vpanel);
		
		image2.setSize("100px", "30px");

		
	}
	
	private String getName(String path)
	{
		String[] items = path.split("/");
		if(items == null)
			return null;
		
		if(items.length > 0)
		{
			String name = items[items.length-1];
			if(name.length() > this.nameMax)
				return name.substring(0,this.nameMax);
			else
				return name;
				//return name+this.createSpaces(name)+name.length();
			
		}
		else
			return null;
		
			
	}
	
	private String createSpaces(String name) 
	{
		int diff = this.nameMax - name.length();
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<diff;i++)
		{
			buff.append("&nbsp;");
		}
		return buff.toString();
	}
	
	public ThumbnailPanel(String turl, String label, String durl, String description, String attribution, String email, String wibURL) 
	{
		this.durl2 = durl;
		
		this.description2 = description;
		setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Image image = new Image();
		image.addDoubleClickHandler(new DoubleClickHandler() {
			public void onDoubleClick(DoubleClickEvent event) 
			{

			}
		});
		image.setUrl(turl);
		add(image);
		image.setSize("100px", "100px");
		
		Label lblTest = new Label("test");
		lblTest.setText(label);
		add(lblTest);
	}


}
