package edu.ucsd.ccdb.slash.autoseg.client;

import java.util.*;

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
	HTML trainImageHtml = new HTML("...", true);
	HTML trainingModelHtml = new HTML("...", true);
	HTML inputImageHtml = new HTML("...", true);
	int nameMax = 50;
	Vector<DatasetModelInfo> vmodel = new Vector<DatasetModelInfo>();
	int traingModelID = 0;
	Button modelBtn = new Button("Training Model");
	
	SelectModelPanel smodelPanel = null;
	
	
	TextBox minx = new TextBox();
	TextBox maxx = new TextBox();
	TextBox miny = new TextBox();
	TextBox maxy = new TextBox();
	TextBox minz = new TextBox();
	TextBox maxz = new TextBox();
	TextBox modelName = new TextBox();
	
	private HashMap<String, String> paramMap = new HashMap<String, String>();
	
	public static native String getParamString() /*-{
    return $wnd.location.search;
}-*/;
	
	private void initParamMap(String line)
	{
		line = line.substring(1, line.length());
		
		String[] items = line.split("&");
		
		if(items == null)
			return ;
		
		for(int i=0;i<items.length;i++)
		{
			String args = items[i];
			
			String[] values = args.split("=");
			if(values == null || values.length != 2)
				return;
			
			this.paramMap.put(values[0], values[1]);
			
		}
		
	}
	
	public void setSlashImages(Vector<SlashImage> imageV)
	{
		this.imageV = imageV;
		System.out.println("Client---------------------------"+imageV);
	}
	
	public void setTrainingModel(DatasetModelInfo info)
	{
		Constants.modelInfo = info;
		String modelText = "";
		if(info.getModelName() != null && info.getModelName().trim().length() > 0)
			modelText = "Model: "+info.getModelName()+" ("+info.getDatasetID()+"-"+info.getModelID()+")";
		else
			modelText = "Model: "+"("+info.getDatasetID()+"-"+info.getModelID()+")";
		
		this.trainingModelHtml.setHTML(modelText);
	}
	
	
	public void udpateModelInfo(Vector<DatasetModelInfo> vmodel)
	{
		this.vmodel = vmodel;
		 smodelPanel = new SelectModelPanel(this.vmodel,self);
		smodelPanel.show();
	}
	
	public void setTrainingImage(SlashImage timage, String itype)
	{
		System.out.println("Training image-----------------:"+timage);
		
		tpanel.setVisible(false);
		String thumbnailURL = Constants.imagePrefix+timage.getActualLocation()+".jpg";
		
		String name = this.getName(timage.getActualLocation());
		String html = "<img src=\""+thumbnailURL+"\" width=\"50\" height=\"50\"/>&nbsp;&nbsp;&nbsp;"+name;
		
		if(itype.equals(Constants.trainImageType))
		{
			Constants.trainImage = timage;
			trainImageHtml.setHTML(html);
			
			
		}
		else if(itype.equals(Constants.inputImageType))
		{
			Constants.inputImage = timage;
			inputImageHtml.setHTML(html);
			
			minx.setText("0");
			maxx.setText(timage.getXmax()+"");
			
			miny.setText("0");
			maxy.setText(timage.getYmax()+"");
			
			minz.setText("0");
			maxz.setText(timage.getZmax()+"");
			
			
			
		}
		
		
	}
	
	public void onModuleLoad() 
	{
	
		String params = this.getParamString();
		this.initParamMap(params);
		if(this.paramMap.containsKey("userName"))
		{
			String userName = this.paramMap.get("userName");
			System.out.println("-----------------UserName:"+userName);
			Constants.userName = userName;
			
		}
		
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel root =RootPanel.get();//("sendButtonContainer");
	
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
		btnRunProcess.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				
				if(Constants.trainImage == null || Constants.modelInfo == null || Constants.inputImage == null)
				{
					MessagePopup mpopup = new MessagePopup("You have to select the training image, the segmentation model and the input image!");
					mpopup.center();
					mpopup.show();
					return;
				}
				
				
				if(minx.getText().trim().length() ==0 ||  
				   maxx.getText().trim().length() ==0 ||
				   miny.getText().trim().length() ==0 ||
				   maxy.getText().trim().length() ==0 ||
				   minz.getText().trim().length() ==0 ||
				   maxz.getText().trim().length() ==0)
				{
					MessagePopup mpopup = new MessagePopup("Auto segmentation range cannot be empty!");
					mpopup.center();
					mpopup.show();
					return;
				}
				
				if(modelName.getText().trim().length() == 0)
				{
					MessagePopup mpopup = new MessagePopup("Model name cannot be empty!");
					mpopup.center();
					mpopup.show();
					return;
				}
				
				
				try
				{
					CytosegInputs cinput = new CytosegInputs();
					cinput.setUserName(Constants.userName);
					
					cinput.setInputDatasetID(Constants.inputImage.getDatasetID());
					cinput.setTrainingDatasetID(Constants.trainImage.getDatasetID());
				
					long xmin = Long.parseLong(minx.getText());
					cinput.setMinX(xmin);
					
					long xmax = Long.parseLong(maxx.getText());
					cinput.setMaxX(xmax);
					
					long ymin = Long.parseLong(miny.getText());
					cinput.setMinY(ymin);
					
					long ymax = Long.parseLong(maxy.getText());
					cinput.setMaxY(ymax);
					
					long zmin = Long.parseLong(minz.getText());
					cinput.setMinZ(zmin);
					
					long zmax = Long.parseLong(maxz.getText());
					cinput.setMaxZ(zmax);
					
					String mname = modelName.getText();
					mname = mname.trim().replace(" ", "_");
					cinput.setModelName(modelName.getText());
					
					double p_voxel_w = Double.parseDouble(pvoxelWeightBox.getText());
					cinput.setP_voxel_w(p_voxel_w);
					double n_voxel_w = Double.parseDouble(nvoxelWeightBox.getText());
					cinput.setN_voxel_w(n_voxel_w);
					
					Integer p_contour_m =  Integer.parseInt(pcontourListWeightBox.getText());
					cinput.setP_contour_m(p_contour_m);				
					Integer n_contour_m =  Integer.parseInt(ncontourListWeightBox.getText());
					cinput.setN_contour_m(n_contour_m);
					
					double threshold = Double.parseDouble(contourListThresholdBox.getText());
					cinput.setThreshold(threshold);
					int sindex = serverList.getSelectedIndex();
					String serverName = serverList.getItemText(sindex);
					cinput.setServerName(serverName);
					
					
					cinput.setTrainModelID(Constants.modelInfo.getModelID());
					exc.submitCytoseg(cinput);
					
					MessagePopup mpp = new MessagePopup("You will receive a notification email about this process.");
					mpp.center();
					mpp.show();
					
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
			}
		});
		root.add(btnRunProcess, 494, 698);
		btnRunProcess.setSize("165px", "28px");
		
		VerticalPanel vpanel = new VerticalPanel();
		root.add(vpanel, 98, 480);
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
		trainImageHtml.setSize("301px", "18px");
		
		
		root.add(inputImageHtml, 419, 210);
		inputImageHtml.setSize("301px", "18px");
		
		
		root.add(trainingModelHtml, 419, 139);
		trainingModelHtml.setSize("301px", "18px");
		
		HTML htmlAutosegmentationRegion = new HTML("<b>Auto-segmentation range:</b>", true);
		root.add(htmlAutosegmentationRegion, 98, 281);
		htmlAutosegmentationRegion.setSize("216px", "18px");
		
		HTML htmlXMin = new HTML("X min:", true);
		root.add(htmlXMin, 98, 305);
		

		
		
		root.add(minx, 138, 305);
		minx.setSize("63px", "16px");
		
		Label lblYMin = new Label("X max:");
		root.add(lblYMin, 235, 305);
		
		
		root.add(maxx, 279, 305);
		maxx.setSize("63px", "16px");
		
		HTML htmlYMin = new HTML("Y min:", true);
		root.add(htmlYMin, 98, 344);
		htmlYMin.setSize("38px", "18px");
		
		
		root.add(miny, 138, 344);
		miny.setSize("63px", "16px");
		
		Label lblYMax = new Label("Y max:");
		root.add(lblYMax, 235, 344);
		lblYMax.setSize("42px", "18px");
		
		
		root.add(maxy, 279, 344);
		maxy.setSize("63px", "16px");
		
		HTML htmlZMin = new HTML("Z min:", true);
		root.add(htmlZMin, 98, 385);
		htmlZMin.setSize("38px", "18px");
		
		
		root.add(minz, 138, 385);
		minz.setSize("63px", "16px");
		
		Label lblZMax = new Label("Z max:");
		root.add(lblZMax, 235, 385);
		lblZMax.setSize("42px", "18px");
		
		
		root.add(maxz, 279, 385);
		maxz.setSize("63px", "16px");
		
		HTML htmlModelName = new HTML("Model name:", true);
		root.add(htmlModelName, 98, 427);
		htmlModelName.setSize("81px", "18px");
		
		
		root.add(modelName, 181, 427);
		modelName.setSize("164px", "16px");
		
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
