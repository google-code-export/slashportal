package edu.ucsd.ccdb.slash.cory.client;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Image;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.shape.Circle;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.widgetideas.client.SliderBar;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CoryAutoSegGWT implements EntryPoint {

	boolean mousedown = false;
	private DrawingArea canvas;
	Vector<Circle> vcircle = new Vector<Circle>();
	Vector<Line> polygon = new Vector<Line>();
	HashMap<String, Vector> canvasMap = new HashMap<String, Vector>();
	SliderBar slider = new SliderBar(0,0);
	int cindex = 0;
	private Label label =  new Label("Index: 0");
	public static int defaultWidth = 700;
	public static  int defaultHeight = 700;
	Image image = 
		new Image(0,0, defaultWidth,defaultHeight,
		"http://galle.crbs.ucsd.edu/test_image.png");
	private HashMap<Vector,String> polygonToIDMap = new HashMap<Vector,String>();
	private HashMap<String, String> paramMap = new HashMap<String, String>();
	Button drawBtn = new Button("Draw");
	Button delBtn = new Button("Delete");
	
	HTML xlabel = new HTML("X: 0");
	HTML ylabel = new HTML("Y: 0");
	
	CoryAutoSegExc exc=  new CoryAutoSegExc(this);
	String gridLineColor = "blue";
	
	private int gridSize = 100;
	
	private Vector<Integer> vgridx  = new Vector<Integer>();
	private Vector<Integer> vgridy = new Vector<Integer>();
	private int gridRange = 10;
	/**
	* Gets the string with the parameters from the current URL.
	*
	* @return String containing the list of parameters from the URL
	* in format: ?aaa=bbbb&vvvv=ccc
	*/
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
	
	
	public void drawGrid()
	{
		this.vgridx.clear();
		this.vgridy.clear();
		//int count = this.defaultWidth/100;
		int count = this.defaultWidth/this.gridSize;
		int step = 0;
		for(int i=0; i<count;i++)
		{
			
			Line l1 = new Line((int)(step)
					, (int)(0)
					, (int)(step)
					, (int)(this.defaultHeight));
			
			
			//l1.setStrokeColor("blue");
			
			l1.setStrokeColor(this.gridLineColor);
			step=step+this.gridSize;
			canvas.add(l1);
		}
		
		
		//count = this.defaultHeight/100;
		count = this.defaultHeight/this.gridSize;
		step = 0;
		for(int i=0; i<count;i++)
		{
			
			Line l1 = new Line((int)(0)
					, (int)(step)
					, (int)(this.defaultWidth)
					, (int)(step));
			
			//l1.setStrokeColor("blue");
			l1.setStrokeColor(this.gridLineColor);
			
			
			step=step+this.gridSize;
			canvas.add(l1);
		}
		
	}
	
	private void updateGridSizeFromURL()
	{
		String sgridSize = this.paramMap.get("gridSize");
		try
		{
			this.gridSize = Integer.parseInt(sgridSize);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	private void updateGridLineColor()
	{
		String temp = this.paramMap.get("gridLineColor");
		if(temp != null)
		{
			this.gridLineColor = temp;
		}
		
	}
	
	private void updateGuideRange()
	{
		String guideRange = this.paramMap.get("guideRange");
		try
		{
			if(guideRange != null)
			{
				this.gridRange = Integer.parseInt(guideRange);
				System.out.println("--------------------gridRange:"+this.gridRange);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private int getClosestGridLine(int position, String axis)
	{
		/*int newPosition = 0;
		int base = position/this.gridSize;
		newPosition = base*this.gridSize;
		
		int leftover = position%this.gridSize;
		
		
		int diff = this.gridSize - leftover;
		System.out.println("position:"+position+"---Axis:"+axis+"---Base-----------"+base+"----------Leftover--------------"+leftover+"-----diff:"+diff);
		if(diff < this.gridRange)
		{
			
			newPosition  = newPosition+this.gridSize;
		}
		else
		{
			newPosition  = newPosition+leftover;
		}*/
		int newPosition = position;
		if(axis.equals("x"))
		{
			for(int i=0;i<= this.defaultWidth;i=i+this.gridSize)
			{
				int diff = i-position;
				if(diff < 0)
					diff=diff*-1;
				
				if(diff <= this.gridRange)
					newPosition = i;
			}
		}
		if(axis.equals("y"))
		{
			for(int i=0;i<= this.defaultHeight;i=i+this.gridSize)
			{
				int diff = i-position;
				if(diff < 0)
					diff=diff*-1;
				
				if(diff <= this.gridRange)
					newPosition = i;
			}
		}
		
		return newPosition;
		
		
	}
	public void onModuleLoad() {
		
		this.initParamMap(this.getParamString());
		this.updateGridSizeFromURL();
		this.updateGridLineColor();
		this.updateGuideRange();
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setStyleName("mainPanel");
		
		//mainPanel.setVerticalAlignment(mainPanel.ALIGN_MIDDLE);
		//mainPanel.setHorizontalAlignment(mainPanel.ALIGN_CENTER);
		
		RootPanel.get("sendButtonContainer").add(mainPanel);
		RootPanel.get("sendButtonContainer").setStyleName("sendButtonContainer");
		
		
	//	mainPanel.setSize("1000px", "700px");
		slider.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) 
			{
				
				
			}
		});

		slider.setStepSize(1);
		//slider.setStyleName("slider");

		canvas = new DrawingArea(700, 700);
		
		canvas.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) 
			{
				
				
				

			}
		});
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				
				
				xlabel.setHTML("X: "+event.getX());
				ylabel.setHTML("Y :"+event.getY());
				//System.out.println("mouse move");
				//System.out.println("Mouse x, y "+event.getX()+","+event.getY());
				if( mousedown ) 
				{
					
			      /*   //Do Something
					Circle c0 = vcircle.lastElement();
					
					Circle c1 = new Circle(event.getX(),event.getY(),2);
					vcircle.add(c1);
					
					Line l1 = new Line(c0.getX(), c0.getY(), c1.getX(), c1.getY());
					l1.setStrokeColor("blue");
					canvas.add(l1);
					
					polygon.addElement(l1);    */
				}	
			
			}
		});
		canvas.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) 
			{
				
				
				
			/*	if(mousedown)
				{
					mousedown = false;
					closePolygon(event.getX(), event.getY());
				}
				else
				{ */
					mousedown = true;
					polygon.clear(); //Willy test
				
				
					//System.out.println("Client x, y "+event.getClientX()+","+event.getClientY());
					//System.out.println("Get x, y "+event.getX()+","+event.getY());
					
					if(!drawBtn.isEnabled())
					{
						//Circle c1 = new Circle(event.getX(),event.getY(),4);
						int x = getClosestGridLine(event.getX(),"x");
						int y = getClosestGridLine(event.getY(),"y");
						Circle c1 = new Circle(x,y,4);
						c1.setFillColor("green");
						
						
						//c1.setStyleName("line");
						vcircle.add(c1);
						canvas.add(c1);
						
					}
					else 
					{
						for(int i=0;i<vcircle.size();i++)
						{
							Circle c1 = vcircle.get(i);
							int x1 = c1.getX();
							int y1 = c1.getY();
							
							
							int x2 = event.getX();
							int y2 = event.getY();
							
							int diffx = x1-x2;
							int diffy = y1-y2;
							
							if(diffx < 0)
								diffx = diffx*-1;
							
							if(diffy < 0)
								diffy = diffy*-1;
							
							if(diffy <=2 && diffx <= 2)
							{
								canvas.remove(c1);
								try
								{
									boolean deleteOK = vcircle.remove(c1);
									System.out.println("-----------DeleteOK:"+deleteOK);
								}
								catch(Exception e2)
								{
									e2.printStackTrace();
								}
							}

						}
						

					}
				//}
				
			}
		});
		canvas.setStyleName("drawing-area");
		mainPanel.add(canvas);
		slider.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				
				System.out.println("Slider:"+slider.getCurrentValue());
			}
		});
		mainPanel.add(label);
		
		HorizontalPanel hpanel = new HorizontalPanel();
		Button leftButton = new Button();
		leftButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				System.out.println("------Left click----");
				int svalue = (int)slider.getCurrentValue();
				if(svalue >= 1)
				{
					svalue = svalue-1;
					slider.setCurrentValue(svalue);
					handleSliderUpdate();
				}
				
			}
		});
		leftButton.setText("<");
		
		Button rightButton = new Button();
		rightButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				System.out.println("------Right click----");
				int svalue = (int)slider.getCurrentValue();
				int max = (int)slider.getMaxValue();
				if(svalue +1 <= max)
				{
					svalue = svalue+1;
					slider.setCurrentValue(svalue);
					handleSliderUpdate();
				}
			}
		});
		rightButton.setText(">");
		hpanel.add(leftButton);
		hpanel.add(slider);
		hpanel.add(rightButton);
		hpanel.setHorizontalAlignment(hpanel.ALIGN_CENTER);
		mainPanel.add(hpanel);
		

		HorizontalPanel hpanel2 = new HorizontalPanel();

		drawBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				drawBtn.setEnabled(false);
				delBtn.setEnabled(true);
				
			}
		});
		drawBtn.setEnabled(false);
		hpanel2.add(drawBtn);
		HTML space = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		hpanel2.add(space);
		delBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				drawBtn.setEnabled(true);
				delBtn.setEnabled(false);
			}
		});
		
		hpanel2.add(delBtn);
		HTML space2 = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		hpanel2.add(space2);
		

		hpanel2.add(xlabel);
		HTML space3 = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		hpanel2.add(space3);
		
		hpanel2.add(ylabel);
		HTML space4 = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");	
		hpanel2.add(space4);
		
		Button showXMLBtn = new Button("Show XML");
		showXMLBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				Vector<GPoint> vpoint = new Vector<GPoint>();
				
				for(int i=0;i<vcircle.size();i++)
				{
					Circle c = vcircle.get(i);
					GPoint gp = new GPoint();
					gp.setX(c.getX());
					gp.setY(c.getY());
					vpoint.addElement(gp);
				}
				
				try
				{
					System.out.println("------------vpoint:"+vpoint);
					exc.getCoryXML(vpoint);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		});
		hpanel2.add(showXMLBtn);
		
		//Button undoBtn = new Button("Undo");
		//hpanel2.add(undoBtn);
		
		HTML newline = new  HTML("<p>");
		mainPanel.add(newline);
		mainPanel.add(hpanel2);
		
	
		slider.setSize("650px", "20px");
		slider.setNumTicks(1);
		canvas.setSize("700px", "700px");
		
		
		canvas.add(image); 
		

		
	
		this.drawGrid();
		

	}
	
	
	public void showXML(String xml)
	{
		
		XMLPopupPanel xmlPanel = new XMLPopupPanel(xml);
		xmlPanel.setPopupPosition(canvas.getAbsoluteLeft(), canvas.getAbsoluteTop());
		xmlPanel.show();
	}
	private void closePolygon(int x , int y)
	{
		Circle c0 = this.vcircle.elementAt(0);
		Circle c1 = new Circle(x,y,2);
		
		Line l1 = new Line(c0.getX(), c0.getY(), c1.getX(), c1.getY());
		polygon.add(l1);
		l1.setStrokeColor("blue");
		canvas.add(l1);
		
		
		try
		{
			//this.insertPolygonToDB(polygon);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		java.util.Vector<Vector> vPolygon = new java.util.Vector<Vector>();
		Object o =canvasMap.get(cindex+"");
		if(o != null)
		{
			vPolygon = (Vector<Vector>) o;
		}
		
		vPolygon.add(this.polygon);
		System.out.println("before putting"+cindex+"----"+vPolygon);
		canvasMap.put(cindex+"", vPolygon);
		polygon = new Vector<Line>();
		vcircle.clear();
	}
	public void handleSliderUpdate()
	{
		
	
	}
	
	private static native JavaScriptObject newWindow(String url, String name, String features)/*-{
    var window = $wnd.open(url, name, features);
    return window;
}-*/;

private static native void setWindowTarget(JavaScriptObject window, String target)/*-{
    window.location = target;
}-*/;
}
