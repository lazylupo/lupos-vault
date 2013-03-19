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
<script type="text/javascript" src="webeditor.js" charset="utf-8"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script language="javascript">

			function RegAlert(){ 

			    var s="<%=(String) session.getAttribute("RegAlert")%>";  
			    alert(s); 
			  }
			
			function submitForm(){
				
				var name = document.getElementById("name").value;
				var firstname = document.getElementById("firstname").value;
				var email = document.getElementById("email").value;
				var password = document.getElementById("password").value;
				
				if(name != "" && firstname != "" && email != "" && password != ""){
					document.editor.submit();
				}else {
				    var s="<%=(String) session.getAttribute("Mandatory")%>"; 
					alert(s);
				}
				
			}
     </script>  

<% 
     WebClient sessionWebclient;
	 ExpEditor sessionExpEditor;
 
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			//creating an ExpEditor
			if(request.getParameter("name") != null){
				

					
				String name = UTF8Encoder.utf8Convert(request.getParameter("name"));
				String firstname = UTF8Encoder.utf8Convert(request.getParameter("firstname"));
				String email = UTF8Encoder.utf8Convert(request.getParameter("email"));
				String password = UTF8Encoder.utf8Convert(request.getParameter("password"));
				String phone = UTF8Encoder.utf8Convert(request.getParameter("phone"));
				String organisation = UTF8Encoder.utf8Convert(request.getParameter("organisation"));
			    String id = sessionWebclient.createExpEditor(name, firstname, email, password, phone, organisation);
		
				if(id != null){	
					
					ExpEditor result = sessionWebclient.getExpEditor(email, password);

					if(result != null){	
						session.setAttribute("expeditor", result);
						
						%><script type="text/javascript">
						var url = "/WebEditor/expeditor.jsp";
						location.href=url;
		
						</script><% 
					}else {
						 
						%><script type="text/javascript">
					    RegAlert();
						</script><%
					}	
 
					
					
				} else {
				 
					%><script type="text/javascript">
				    RegAlert();
					</script><%
				}
				
			}
%>


</head>
<body>
<fmt:setLocale value='${locale}'/>

<div id="buttons">
<img src="/WebEditor/icons/percy_128.png" align="left" width="50px" height="50px" style="margin-top:5px">
<div align="center" style="position: absolute; width:200px; height:60px; left:50%;margin-left:-100px;">
<h4>Percy Web-Editor</h4>
</div>
</div>
<div id="login">
<div style="position:absolute; height:400px; width:296x; left:50%; margin-left:-148px; top:40%; margin-top:-200px;">
&nbsp;<label for="editor"><fmt:message key="Registration"/>:</label><br><br>
<form name="editor" id="editor">
&nbsp;<label for="firstname"><fmt:message key="Firstname"/></label><br>
&nbsp;<input id="firstname" name="firstname" type="text" size="28">*&nbsp;<br>
&nbsp;<label for="name"><fmt:message key="Name"/></label><br>
&nbsp;<input id="name" name="name" type="text" size="28">*&nbsp;<br>
&nbsp;<label for="email"><fmt:message key="Email"/></label><br>
&nbsp;<input id="email" name="email" type="text" size="28">*&nbsp;<br>
&nbsp;<label for="password"><fmt:message key="Password"/></label><br>
&nbsp;<input id="password" name="password" type="password" size="28">*&nbsp;<br>
&nbsp;<label for="phone"><fmt:message key="Phone"/></label><br>
&nbsp;<input id="phone" name="phone" type="text" size="28"><br>
&nbsp;<label for="phone"><fmt:message key="Organisation"/></label><br>
&nbsp;<input id="organisation" name="organisation" type="text" size="28"><br><br>
&nbsp;<input type="button"  value="<fmt:message key="Submit"/>" onclick="submitForm()" ontouch="submitForm()"><br>
</form>
</div>
</div>

</body>
<script type="text/javascript">
	if(history.length >= 2){
	writeBackParams();
	}
</script>
</html>