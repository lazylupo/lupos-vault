<%--

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright (c) 2010-2011 Oracle and/or its affiliates. All rights reserved.

    The contents of this file are subject to the terms of either the GNU
    General Public License Version 2 only ("GPL") or the Common Development
    and Distribution License("CDDL") (collectively, the "License").  You
    may not use this file except in compliance with the License.  You can
    obtain a copy of the License at
    http://glassfish.java.net/public/CDDL+GPL_1_1.html
    or packager/legal/LICENSE.txt.  See the License for the specific
    language governing permissions and limitations under the License.

    When distributing the software, include this License Header Notice in each
    file and include the License file at packager/legal/LICENSE.txt.

    GPL Classpath Exception:
    Oracle designates this particular file as subject to the "Classpath"
    exception as provided by Oracle in the GPL Version 2 section of the License
    file that accompanied this code.

    Modifications:
    If applicable, add the following below the License Header, with the fields
    enclosed by brackets [] replaced by your own identifying information:
    "Portions Copyright [year] [name of copyright owner]"

    Contributor(s):
    If you wish your version of this file to be governed by only the CDDL or
    only the GPL Version 2, indicate your decision by adding "[Contributor]
    elects to include this software in this distribution under the [CDDL or GPL
    Version 2] license."  If you don't indicate a single choice of license, a
    recipient has the option to distribute your version of this file under
    either the CDDL, the GPL Version 2 or to extend the choice of license to
    its licensees as provided above.  However, if you add GPL Version 2 code
    and therefore, elected the GPL Version 2 license, then the option applies
    only if the new code is made subject to such option by the copyright
    holder.

--%>
<%@page import="webclient.*" import="model.db.*" contentType="text/html"
pageEncoding="UTF-8" isELIgnored="false" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>WebEditor</title>
        
        <link type="text/css" rel="stylesheet" href="/WebEditor/css/style.css"/>
        <script type="text/javascript" src="/WebEditor/webeditor.js" charset="utf-8"></script>
        <script type="text/javascript" src="/WebEditor/jquery/jquery.min.js" charset="utf-8"></script>
        <script type="text/javascript">
        
        function PosChangeAlert(){ 
		    var s="<%=(String) session.getAttribute("ChangesSaved")%>";  
		    alert(s); 
		  }
       function NegChangeAlert(){ 
		    var s="<%=(String) session.getAttribute("ChangesNotSaved")%>";  
		    alert(s); 
		  } 
        	 
        function goToOverview(){
        	
        	window.parent.location = "/WebEditor/overview.jsp";
        	
        }
        
		function logout(){
        	
        	window.parent.location = "/WebEditor/index.jsp";
        	
        }
		
		function submitForm(){
			
			var name = document.getElementById("name").value;
			var firstname = document.getElementById("firstname").value;
			var email = document.getElementById("email").value;
			var password = document.getElementById("password").value;
			
			if(name != "" && firstname != "" && email != "" && password != ""){
				document.expEditorChange.submit();
			}else {
			    var s="<%=(String) session.getAttribute("Mandatory")%>"; 
				alert(s);
			}
		}

        </script>

<% 
     WebClient sessionWebclient;	
			
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			//changing an ExpEditor
			if(request.getParameter("id")!= null){
				
				String id= request.getParameter("id");
				String firstname= UTF8Encoder.utf8Convert(request.getParameter("firstname"));
				String name= UTF8Encoder.utf8Convert(request.getParameter("name"));
				String email= UTF8Encoder.utf8Convert(request.getParameter("email"));
				String password= UTF8Encoder.utf8Convert(request.getParameter("password"));
				String phone= UTF8Encoder.utf8Convert(request.getParameter("phone"));
				String organisation= UTF8Encoder.utf8Convert(request.getParameter("organisation"));
				int resultStatus = sessionWebclient.changeExpEditor(id, firstname, name, email, password, phone, organisation);
				if(resultStatus == 204){	
					
					%><script type="text/javascript">

					PosChangeAlert();
					var id = getParameter("id");
					var url ="/WebEditor/view/ExpEditor/"+id;
					location.href=url;
	
					</script><% 
					
					
				} else {
				 
					%><script type="text/javascript">
	
					NegChangeAlert();
	
					</script><%
				}		
			}

%>
    </head>
    <body>
	<fmt:setLocale value='${locale}'/>
	<h2><fmt:message key="AccountSettings"/></h2><br>
	
    <div style="position: absolute; width:296px; left:50%;margin-left:-148px;">
    <form id="expEditorChange" name="expEditorChange">
	&nbsp;<label for="firstname"><fmt:message key="Firstname"/>:</label><br>
	&nbsp;<input id="firstname" name="firstname" size="28" type="text" value='${it.firstName}'>*&nbsp;<br><br>
	&nbsp;<label for="name"><fmt:message key="Name"/>:</label><br>
	&nbsp;<input id="name" name="name" size="28" type="text" value='${it.name}'>*&nbsp;<br><br>
	&nbsp;<label for="email"><fmt:message key="Email"/>:</label><br>
	&nbsp;<input id="email" name="email" size="28" type="text" value='${it.email}'>*&nbsp;<br><br>
	&nbsp;<label for="password"><fmt:message key="Password"/>:</label><br>
	&nbsp;<input id="password" name="password" size="28" type="password" value='${it.password}'>*&nbsp;<br><br>
	&nbsp;<label for="phone"><fmt:message key="Phone"/>:</label><br>
	&nbsp;<input id="phone" name="phone" size="28" type="text" value='${it.phone}'><br><br>
	&nbsp;<label for="organisation"><fmt:message key="Organisation"/>:</label><br>
	&nbsp;<input id="organisation" name="organisation" size="28" type="text" value='${it.organisation}'><br><br>
	<input id="id" name="id" type="hidden" value='${it.id}'>
	&nbsp;<input type="button" value="<fmt:message key="Save Changes"/>" onclick="submitForm();" ontouch="submitForm();">
	</form><br><br>
	<c:choose>
		<c:when test="${fn:length(it.expProjects)!=0}">
		&nbsp;<input type="button" value="<fmt:message key="To Overview"/>" onclick="goToOverview();" ontouch="goToOverview();">
		</c:when>
		<c:otherwise>
		&nbsp;<p><fmt:message key="NoEnabledProjects"/></p>
		&nbsp;<a id="bugmail" href="MAILTO:draxler@phonetik.uni-muenchen.de"><fmt:message key="ContactAdministrator"/></a>
		</c:otherwise>
	</c:choose><br><br>
	&nbsp;<input type="button" value="<fmt:message key="Logout"/>" onclick="logout();" ontouch="logout();">
	</div>
    </body>
    <script type="text/javascript">

	writeBackParams();

</script>
    
    
</html>
