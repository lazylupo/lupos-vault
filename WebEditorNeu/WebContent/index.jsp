<%@page language="java" import="webclient.*" import="model.db.*" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="/WebEditor/icons/percy_128.ico">
<title>WebEditor</title>
<link type="text/css" rel="stylesheet" href="/WebEditor/css/style_tree.css"/>
<script type="text/javascript" src="/WebEditor/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/WebEditor/jstree/_lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="webeditor.js" charset="utf-8"></script>
<script type="text/javascript">


	$(function() {
		
		$.cookie("jstree_open", null);
		$.cookie("jstree_load", null);
		$.cookie("jstree_select", null);
		
	});
		
</script>

<% 
     WebClient sessionWebclient;
 
	   		session.invalidate();
	   		session = request.getSession(true);
	   		String serverName = request.getServerName();
	   		int serverPort = request.getServerPort();
	   		String serverAdress = "http://"+serverName+":"+serverPort;
	   		WebClient webclient = new WebClient(serverAdress);
	   		
			session.setAttribute("webclient", webclient);
			session.setAttribute("urldirectory", "http://");
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			
			// setting the session attribute containing the locale
			if(request.getParameter("locale")!= null){
				
				String locale = request.getParameter("locale");
				session.setAttribute("locale", locale);
				%>
				<script type="text/javascript">
				var url ="/WebEditor/login.jsp";
				location.href=url;
				</script><% 
			}
			
%>


</head>
<body>
<div id="buttons">
<img src="/WebEditor/icons/percy_128.png" align="left" width="50px" height="50px" style="margin-top:5px">
<div align="center" style="position: absolute; width:200px; height:60px; left:50%;margin-left:-100px;">
<h4>Percy Web-Editor</h4>
</div>
</div>
<div id="login">
<div align="center" style="position:absolute; height:200px; width:900px; left:50%; margin-left:-450px; top:50%; margin-top:-200px;">
<form>
Choose your Language:<br><br>
<button name="locale" type=submit value="de_DE">
<img src="/WebEditor/icons/flags/de.png">
</button>
<button name="locale" type=submit value="en_GB">
<img src="/WebEditor/icons/flags/uk.png">
</button>
</form>
</div>
</div>
</body>
</html>