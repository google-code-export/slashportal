<%@ page import="edu.ucsd.ccdb.slash.results.portlet.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>
</head>

<body>
testing


<%
	String modelName = request.getParameter("modelName");
	String dataset = request.getParameter("dataset");
	
	//out.println("ModelName="+modelName);
	//out.println("dataset:"+dataset);
	
	int index = dataset.indexOf("----");
	String datasetID = dataset.substring(index+4, dataset.length());
	//out.println("datasetID:"+datasetID);
	
	
	int modelID = -1;
	SlashDBUtil dbutil = new SlashDBUtil();
	try
	{
		long dsID = Long.parseLong(datasetID);
		modelName = modelName.replace(" ", "_");
		modelID = dbutil.getVersionNumberByName(dsID, modelName);
		if(modelID < 0)
			modelID = dbutil.getNextVersionNumber(dsID);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	if(modelID > 0 && modelName !=  null)
	{
		String url = "http://galle.crbs.ucsd.edu:8081/Canvas_GWT_Test3/Canvas_GWT_Test2.html?datasetID="+datasetID+"&userName=ccdbuser&modelID="+modelID+"&modelName="+modelName;
		out.println("<meta http-equiv=\"REFRESH\" content=\"0;url="+url+"\">");
	}
	else
	{
		out.println("Cannot redirect to SLASH drawing board with the following information:<br/>");
		out.println("ModelName="+modelName+"<br/>");
		out.println("dataset:"+dataset+"<br/>");
	}
	

%>
</body>
</html>
