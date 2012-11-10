package edu.ucsd.ccdb.slash.autoseg.client;

import java.util.Vector;

import edu.ucsd.ccdb.slash.autoseg.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusListenerAdapter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.FastTree;
import com.google.gwt.widgetideas.client.FastTreeItem;
import com.google.gwt.user.client.ui.Image;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SlashAutoSegExpress implements EntryPoint {

	SlashAutoSegExpress self = this;
	AutoSegExpressExc exc  = new AutoSegExpressExc(this);
	Vector<SlashImage> imageV = new Vector<SlashImage>();
	
	

	private final TextBox xmin = new TextBox();
	private final Label lblXMax = new Label("  X max:");
	private final TextBox xmax = new TextBox();
	
	
	private final TextBox ymin = new TextBox();
	private final TextBox ymax = new TextBox();
	
	private final TextBox zmin = new TextBox();
	private final TextBox zmax = new TextBox();
	
	
	//private TextBox voxelWeightBox = new TextBox();
	private TextBox pvoxelWeightBox = new TextBox();
	private TextBox nvoxelWeightBox = new TextBox();
	
	//private TextBox contourListWeightBox = new TextBox();
	private TextBox pcontourListWeightBox = new TextBox();
	private TextBox ncontourListWeightBox = new TextBox();
	
	
	private TextBox contourListThresholdBox = new TextBox();
	com.google.gwt.widgetideas.client.FastTree ft = new
		com.google.gwt.widgetideas.client.FastTree();
	ListBox serverList = new ListBox();
	Button timageBtn = new Button("Training Image");
	SelectImagePanel  tpanel = null;
	HTML trainImageHtml = new HTML("New HTML", true);
	HTML inputImageHtml = new HTML("New HTML", true);
	int nameMax = 50;
	Vector<DatasetModelInfo> vmodel = new Vector<DatasetModelInfo>();
	Button modelBtn = new Button("Training Model");
	
	SelectModelPanel smodelPanel = null;
	public void setSlashImages(Vector<SlashImage> imageV)
	{
		this.imageV = imageV;
		System.out.println("Client---------------------------"+imageV);
	}
	
	
	public void udpateModelInfo(Vector<DatasetModelInfo> vmodel)
	{
		this.vmodel = vmodel;
		 smodelPanel = new SelectModelPanel(this.vmodel);
		smodelPanel.show();
	}
	
	public void setTrainingImage(SlashImage timage, String itype)
	{
		System.out.println("Training image-----------------:"+timage);
		Constants.trainImage = timage;
		tpanel.setVisible(false);
		String thumbnailURL = Constants.imagePrefix+timage.getActualLocation()+".jpg";
		
		String name = this.getName(timage.getActualLocation());
		String html = "<img src=\""+thumbnailURL+"\" width=\"50\" height=\"50\"/>&nbsp;&nbsp;&nbsp;"+name;
		
		if(itype.equals(Constants.trainImageType))
		{
			trainImageHtml.setHTML(html);
			
		}
		else if(itype.equals(Constants.inputImageType))
		{
			inputImageHtml.setHTML(html);
		}
		
		
	}
	
	public void onModuleLoad() 
	{
	

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel root =RootPanel.get("sendButtonContainer");
		
		HTML htmlSelect = new HTML("Select", true);
		root.add(htmlSelect, 98, 43);
		
		
		root.add(timageBtn, 164, 43);
		timageBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				tpanel = new SelectImagePanel (imageV, self, Constants.trainImageType);
				tpanel.setPopupPosition(timageBtn.getAbsoluteLeft(), timageBtn.getAbsoluteTop());
				tpanel.show();
				
			}
		});
		timageBtn.setSize("223px", "28px");
		
		HTML htmlSelect_1 = new HTML("Select", true);
		root.add(htmlSelect_1, 98, 129);
		
		
		modelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				if(Constants.trainImage != null)
				{
					try
					{
						exc.getDatasetModelInfo(Constants.trainImage.getDatasetID());
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					MessagePopup messagePanel = new MessagePopup("You need to select a training image first!");
					messagePanel.setPopupPosition(modelBtn.getAbsoluteLeft(), modelBtn.getAbsoluteTop());
					messagePanel.show();
				}
			}
		});
		root.add(modelBtn, 164, 129);
		modelBtn.setSize("223px", "28px");
		
		HTML htmlSelect_2 = new HTML("Select", true);
		root.add(htmlSelect_2, 98, 220);
		
		Button iimageBtn = new Button("Input Image");
		iimageBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				tpanel = new SelectImagePanel (imageV, self, Constants.inputImageType);
				tpanel.setPopupPosition(timageBtn.getAbsoluteLeft(), timageBtn.getAbsoluteTop());
				tpanel.show();				
				
			}
		});
		root.add(iimageBtn, 164, 210);
		iimageBtn.setSize("223px", "28px");
		
		Button btnRunProcess = new Button("Run process");
		root.add(btnRunProcess, 499, 471);
		btnRunProcess.setSize("165px", "28px");
		
		VerticalPanel vpanel = new VerticalPanel();
		root.add(vpanel, 98, 287);
		vpanel.setSize("557px", "166px");


		try
		{
			exc.getSlashImages(Constants.userName);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		
		VerticalPanel paramPanel =  new VerticalPanel();
		HTML advOpt = new HTML("Advanced options");
		FastTreeItem treeItem= ft.addItem(advOpt);
		FastTree.addDefaultCSS();
	
		ft.addMouseListener(new MouseListenerAdapter() {
			
			public void onMouseDown(Widget sender, int x, int y) {
				System.out.println("------------onMouseDown----------------");
				//ft.setSelectedItem(null);
				//ft.setFocus(false);
			}
			
			public void onMouseEnter(Widget sender) {
				System.out.println("-------------onMouseEnter---------------");
				//ft.setSelectedItem(null);
				//ft.setFocus(false);
			}
			
			public void onMouseLeave(Widget sender) {
				System.out.println("-------------onMouseLeave---------------");
				//ft.setSelectedItem(null);
				//ft.setFocus(false);
			}
			
			public void onMouseMove(Widget sender, int x, int y) {
				
				//ft.setFocus(false);
			}
			
			public void onMouseUp(Widget sender, int x, int y) {
				
				//ft.setFocus(false);
			}
		});
		
		ft.addFocusListener(new FocusListenerAdapter() {
			@Override
			public void onFocus(Widget sender) {
				
				//ft.setFocus(false);
			}
		});
		
		vpanel.add(ft);
		
		
		root.add(trainImageHtml, 419, 43);
		trainImageHtml.setSize("259px", "18px");
		
		
		root.add(inputImageHtml, 419, 210);
		inputImageHtml.setSize("259px", "18px");
		
		VerticalPanel advPanel= new VerticalPanel();
		

		
		
		HorizontalPanel voxelWeightPanel = new HorizontalPanel();
		
		HTML vwlbl = new HTML("Positive voxel weight:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		voxelWeightPanel.add(vwlbl);
		pvoxelWeightBox.setWidth("150px");
		pvoxelWeightBox.setText("0.0130");
		voxelWeightPanel.add(pvoxelWeightBox);
		
		HTML spacevw = new HTML("&nbsp;&nbsp;&nbsp;");
		voxelWeightPanel.add(spacevw);
		
		HTML vw2lbl = new HTML("Negative voxel weight:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		voxelWeightPanel.add(vw2lbl);
		nvoxelWeightBox.setWidth("150px");
		nvoxelWeightBox.setText("0.00064");
		voxelWeightPanel.add(nvoxelWeightBox);
		advPanel.add(voxelWeightPanel);
		
		
		
		HorizontalPanel contourListWeightPanel = new HorizontalPanel();
		
		
		/*HTML cllbl = new HTML("Contour list weight:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		contourListWeightPanel.add(cllbl);
		this.contourListWeightBox.setWidth("300px");
		this.contourListWeightBox.setText("7,1");
		 contourListWeightPanel.add(contourListWeightBox);
		 contourListWeightPanel.setBorderWidth(0);
		 advPanel.add(contourListWeightPanel); */
		
		
		HTML cllbl = new HTML("Positive contour multiplier:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		contourListWeightPanel.add(cllbl);
		this.pcontourListWeightBox.setWidth("150px");
		this.pcontourListWeightBox.setText("7");
		contourListWeightPanel.add(pcontourListWeightBox);
		contourListWeightPanel.setBorderWidth(0);
		
		
		HTML cl2lbl = new HTML("Negative contour multiplier:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		contourListWeightPanel.add(cl2lbl);
		this.ncontourListWeightBox.setWidth("150px");
		this.ncontourListWeightBox.setText("1");
		contourListWeightPanel.add(ncontourListWeightBox);
		contourListWeightPanel.setBorderWidth(0);
		advPanel.add(contourListWeightPanel);
		
		
		 
		 HorizontalPanel contourListThresholdPanel = new HorizontalPanel();
		 HTML ctlbl = new HTML("Contour detection cut off threshold:&nbsp;");
		 contourListThresholdPanel .add(ctlbl);
		 this.contourListThresholdBox.setWidth("300px");
		 this.contourListThresholdBox.setText("0.8");
		 contourListThresholdPanel.add(contourListThresholdBox);
		 advPanel.add(contourListThresholdPanel);
		
		 HorizontalPanel serverPanel = new HorizontalPanel();
		 HTML serverlbl = new HTML("Server:&nbsp;");
		 serverPanel.add(serverlbl);
		
		 serverList.addItem("jane.crbs.ucsd.edu");
		 serverList.addItem("ccdb-stage-portal.crbs.ucsd.edu");
		 serverPanel.add(serverList);
		 advPanel.add(serverPanel);
		 
		 
		
		advPanel.setBorderWidth(1);		 
		treeItem.addItem(advPanel);
		
		
		
		
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
}
