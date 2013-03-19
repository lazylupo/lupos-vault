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
<link type="text/css" rel="stylesheet" href="/WebEditor/css/style_tree.css"/>
<link rel="shortcut icon" href="/WebEditor/icons/percy_128.ico">
<title>WebEditor</title>

	<script type="text/javascript" src="/WebEditor/webeditor.js"></script>
	<script type="text/javascript" src="/WebEditor/jquery/jquery.js"></script>

	<script language="javascript">  
	
			function loginAlert(){ 

			    var s="<%=(String) session.getAttribute("LoginAlert")%>";  
			    alert(s); 
			  }
			
			function mandatoryAlert(){

				    var s="<%=(String) session.getAttribute("Mandatory")%>"; 
					alert(s);
					

				}
				
			
     </script>  


<% 
     WebClient sessionWebclient;
	 ExpEditor sessionExpEditor;
 			
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			
			// log in
			if(request.getParameter("email") != null){
				String email = UTF8Encoder.utf8Convert(request.getParameter("email"));	
				String password = UTF8Encoder.utf8Convert(request.getParameter("password"));
				if(email.equals("") || password.equals("")){
					
					%><script type="text/javascript">
					mandatoryAlert();
					</script><%

				}else{
			    ExpEditor result = sessionWebclient.getExpEditor(email, password);

				if(result != null){	
					session.setAttribute("expeditor", result);
					sessionExpEditor  = (ExpEditor)session.getAttribute("expeditor");
					
					if(sessionExpEditor.getExpProjects()!= null && sessionExpEditor.getExpProjects().size()>0){
						%><script type="text/javascript">
						var url = "/WebEditor/overview.jsp";
						location.href=url;
		
						</script><%
					}else{
						%><script type="text/javascript">
						var url = "/WebEditor/expeditor.jsp";
						location.href=url;
		
						</script><% 
						
					}

				} else {
				 
					%>
					<script type="text/javascript">
					loginAlert();
					</script><%
				}
			  }	
			}
%>

</head>
<body>

<c:set var='scope' value='${sessionScope}'/>
<c:forEach items='${scope}' var='p'>
<c:if test="${p.key == 'locale'}">
<c:set var='locale' value='${p.value}' scope="session"/>
</c:if>
</c:forEach>
<fmt:setLocale value='${locale}'/>
<fmt:message key="New" var="New" scope="session"/> 
<fmt:message key="CopyNotAllowed" var="CopyNotAllowed" scope="session"/>
<fmt:message key="PasteNotAllowed" var="PasteNotAllowed" scope="session"/>      
<fmt:message key="ConfirmDeleteSelection" var="ConfirmDeleteSelection" scope="session"/>
<fmt:message key="New Experiment" var="New Experiment" scope="session"/>
<fmt:message key="New Experiment-Script" var="New Experiment-Script" scope="session"/>
<fmt:message key="New Experiment-Script-Section" var="New Experiment-Script-Section" scope="session"/>
<fmt:message key="New Experiment-Item" var="New Experiment-Item" scope="session"/>
<fmt:message key="Rename" var="Rename" scope="session"/>
<fmt:message key="Delete" var="Delete" scope="session"/>
<fmt:message key="Copy" var="Copy" scope="session"/>
<fmt:message key="Paste" var="Paste" scope="session"/>
<fmt:message key="CheckAllUrls" var="CheckAllUrls" scope="session"/>   
<fmt:message key="Cant Change ExpProject" var="Cant Change ExpProject" scope="session"/>
<fmt:message key="ExpItem" var="ExpItem" scope="session"/>
<fmt:message key="ChangesSaved" var="ChangesSaved" scope="session"/>
<fmt:message key="ChangesNotSaved" var="ChangesNotSaved" scope="session"/>
<fmt:message key="Advanced" var="Advanced" scope="session"/>
<fmt:message key="ExpScripts" var="ExpScripts" scope="session"/>
<fmt:message key="ExpScriptSections" var="ExpScriptSections" scope="session"/>
<fmt:message key="ExpItems" var="ExpItems" scope="session"/>
<fmt:message key="TestAudio" var="TestAudio" scope="session"/>
<fmt:message key="Submit" var="Submit" scope="session"/>
<fmt:message key="Mandatory" var="Mandatory" scope="session"/>
<fmt:message key="SelectAll" var="SelectAll" scope="session"/>
<fmt:message key="URLDirfailed" var="URLDirfailed" scope="session"/>
<fmt:message key="NotRightFormat" var="NotRightFormat" scope="session"/>
<fmt:message key="NoURLDirSelected" var="NoURLDirSelected" scope="session"/>
<fmt:message key="NoCSVFileSelected" var="NoCSVFileSelected" scope="session"/>
<fmt:message key="NoAudioFileSelected" var="NoAudioFileSelected" scope="session"/>
<fmt:message key="NoTemplateSelected" var="NoTemplateSelected" scope="session"/>
<fmt:message key="NoNumber" var="NoNumber" scope="session"/>
<fmt:message key="NoUrlNumber" var="NoUrlNumber" scope="session"/>
<fmt:message key="ExpItemsCreated" var="ExpItemsCreated" scope="session"/>
<fmt:message key="DeleteNotAllowed" var="DeleteNotAllowed" scope="session"/>
<fmt:message key="Login failed" var="LoginAlert" scope="session"/>
<fmt:message key="Registration failed" var="RegAlert" scope="session"/>





<div id="buttons">
<img src="/WebEditor/icons/percy_128.png" align="left" width="50px" height="50px" style="margin-top:5px">
<div align="center" style="position: absolute; width:200px; height:60px; left:50%;margin-left:-100px;">
<h4>Percy Web-Editor</h4>
</div>
</div>
<div id="login">
<div style="position:absolute; height:400px; width:296px; left:50%; margin-left:-148px; top:50%; margin-top:-200px;">
&nbsp;<label for="editor"><fmt:message key="Loginform"/>:</label><br><br>
<form name="editor" id="editor" accept-charset="utf-8">
&nbsp;<label for="email"><fmt:message key="Email"/>:</label><br>
&nbsp;<input id="email" name="email" type="text" size="28">*&nbsp;<br>
&nbsp;<label for="password"><fmt:message key="Password"/>:</label><br>
&nbsp;<input id="password" name="password" type="password" size="28">*&nbsp;<br><br>
&nbsp;<input id="submitButton" type="submit"  value="<fmt:message key="Login"/>"><br>
</form>
<br>
&nbsp;<a href="registration.jsp"><fmt:message key="Registration"/></a>
</div>
</div>
</body>
<script type="text/javascript">

	if(history.length > 2){

	writeBackParams();
	}

</script>
</html>