<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<%@ page import="edu.ucsd.ccdb.slash.results.portlet.*" %>
<%@ page import="java.util.*" %>
<%@page import="com.liferay.portal.model.*"%>
<%@page import="com.liferay.portal.service.*"%>
<%@page import="javax.portlet.*"%>
<!-- <link href="http://ccdb-portal.crbs.ucsd.edu:80/css/table.css" rel="stylesheet" type="text/css"> -->
<style type="text/css">
/*margin and padding on body element
  can introduce errors in determining
  element position and are not recommended;
  we turn them off as a foundation for YUI
  CSS treatments. */
body {
	margin:0;
	padding:0;
}
</style>
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.9.0/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.9.0/build/tabview/assets/skins/sam/tabview.css" />
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/element/element-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.9.0/build/tabview/tabview-min.js"></script>
<!--begin custom header content for this example-->

<SCRIPT LANGUAGE="javascript">
<!--
function ListOnChange(dropdown)
{
    var myindex  = dropdown.selectedIndex;
    var SelValue = dropdown.options[myindex].value;
	var SelName = dropdown.options[myindex].text;
   
   //alert("index:"+myindex+"---------SelValue"+SelValue+"-------------SelName:"+SelName);
   
   var previewImage = document.getElementById('previewImage');
   if(previewImage != null)
   	previewImage.src =  SelValue ;
    
    return true;
}
//-->
</SCRIPT>


<SCRIPT LANGUAGE="javascript">
<!--
function createNewModel(dropdown)
{
    var myindex  = dropdown.selectedIndex;
    var SelValue = dropdown.options[myindex].value;
	var SelName = dropdown.options[myindex].text;
   
   
   var modelName = document.getElementById('modelName').value;
   
   //alert("ModelName:"+modelName+"-----------------index:"+myindex+"---------SelValue"+SelValue+"-------------SelName:"+SelName);
    if(modelName == null || modelName.length == 0)
		alert('Model name cannot be empty!');
	else
		window.open("http://<%    out.print(request.getServerName());    %>:<%  
		
		int port = request.getServerPort();
		if(port == 80)
			port = 8080;
			
		out.print(port);
		
		  %>/slash_results-portlet/createNewModel.jsp?modelName="+modelName+"&dataset="+SelName);
    
    return true;
}
//-->
</SCRIPT>


<style type="text/css">
.yui-navset button {
    position:absolute;
    top:0;
    right:0;
}
</style>
<%
	String userName = null;
	Object ouserName = request.getAttribute("userName");
	if(ouserName != null)
	{
		userName = ouserName+"";
	}

	//out.println("User name:"+userName);
	
	Object oImages = request.getAttribute(Constants.slashImageType);
	Vector<SlashImage> vimage = new Vector<SlashImage>();
	if(oImages != null)
		vimage = (Vector<SlashImage>)oImages;
	//out.println("<br/>SLASH images:"+oImages);
	
	
	Object oImageMap = request.getAttribute(Constants.imageMap);
	HashMap<String, Vector> imageMap = new HashMap<String, Vector>();
	if(oImageMap != null)
		imageMap = (HashMap<String, Vector>)oImageMap;
	//out.println("<br/>Image Map:"+oImageMap);
	
	
	HTMLGenerator gen = new HTMLGenerator();
	String html0 = "";
	String html1 = "";
	try
	{
		html0 = gen.generateModelOnlyHTML(vimage, imageMap);
	//out.println("<center>"+html0+"</center><p><p>");
	
		html1 = gen.generateHTML(vimage, imageMap);
	//out.println("<center>"+html1+"</center>");
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
%>

<portlet:defineObjects />

<%
	
	PortletURL removeURL = null;
	Object oPURL = request.getAttribute(JSPConstants.portletURL);
	if(oPURL != null)
		removeURL = (PortletURL)oPURL;
		
	removeURL.setParameter(ActionRequest.ACTION_NAME, "removeModelAction");	
		
	
%>
<div class="yui-skin-sam">
<div id="demo" class="yui-navset">
    <ul class="yui-nav">
        <li class="selected"><a href="#tab1"><em>Model List</em></a></li>
        <li><a href="#tab2"><em>Images and Model List</em></a></li>
		<li><a href="#tab3"><em>Create a new model</em></a></li>
    </ul>            
    <div class="yui-content">
		
        <div id="tab1">
		 <form action="<% out.println(removeURL.toString());  %>" id="removeAction" name="removeAction" method="post" action="removeAction">
		<% out.println(html0); %>
		</form>
		</div>
        <div id="tab2">
		<form action="<% out.println(removeURL.toString());  %>" id="removeAction" name="removeAction" method="post" action="removeAction">
		<% out.println(html1); %>
		</div>
		</form>
		<div id="tab3">
		 
		  
		  <form id="form1" name="form1" method="post" action="">
		    <label>Model name:
		      <input type="text" name="modelName" id="modelName"/>
	        </label>
			<br /><br /><label>Select an image:<select id="imageList" name="imageList" onchange="ListOnChange(document.getElementById('imageList'));">
			
			  <!-- <option value="test">test</option>  -->
			   <%
			   		String imagePrefix ="http://ccdb-portal.crbs.ucsd.edu:8081/Data_browser_image_servlet/GetIRODIMageServlet?filePath=";
					vimage = DataUtil.reorderImageByName(vimage);
			   		 for(int i=0;i<vimage.size();i++)
        			{
               				SlashImage si = vimage.get(i);
							out.println("<option value=\""+imagePrefix+si.getActualLocation()+".512.jpg"+"\">"+HTMLGenerator.getName(si.getActualLocation())+"----"+si.getDatasetID()+"</option> ");
							
					}
			   
			   %>
			</select></label>
			<p><br/>
			
			<%
				String firstImage = "../../..test";
				if(vimage.size()> 0)
				{
					SlashImage si = vimage.get(0);
					firstImage= imagePrefix+si.getActualLocation()+".512.jpg";
				}
				
			%>
			
		      <img  id="previewImage" src="<% out.print(firstImage); %>" width="100" height="100" />		    </p>
			<p>
			 <input name="newModelBtn" type="button" id="newModelBtn" value="Create a new model" onclick="createNewModel(document.getElementById('imageList'))" />
</p>
		  </form>
		  <p>&nbsp;</p>
		</div>
    </div>
</div>
</div>

<script>
(function() {
    var tabView = new YAHOO.widget.TabView('demo');
    
    var addTab = function() {
        var labelText = YAHOO.lang.escapeHTML(window.prompt('enter the tab label'));
        var content = YAHOO.lang.escapeHTML(window.prompt('enter the tab content'));
        if (labelText && content) {
            tabView.addTab( new YAHOO.widget.Tab({ label: labelText, content: content }) );
        }
    };

   // var button = document.createElement('button');
  //  button.innerHTML = 'add tab';

 //   YAHOO.util.Event.on(button, 'click', addTab);
  //  tabView.appendChild(button);

    YAHOO.log("The example has finished loading; as you interact with it, you'll see log messages appearing here.", "info", "example");
})();
</script>