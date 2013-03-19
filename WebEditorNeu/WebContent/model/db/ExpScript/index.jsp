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
        <script type="text/javascript" charset="utf-8">
		
        function PosChangeAlert(){ 
		    var s="<%=(String) session.getAttribute("ChangesSaved")%>";  
		    alert(s); 
		  }
     	function NegChangeAlert(){ 
		    var s="<%=(String) session.getAttribute("ChangesNotSaved")%>";  
		    alert(s); 
		  }

        </script>
<% 
     WebClient sessionWebclient;
 
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			ExpEditor expEditor = (ExpEditor) session.getAttribute("expeditor");
			//changing an ExpScript
			if(request.getParameter("author") != null){
				
				String author = UTF8Encoder.utf8Convert(request.getParameter("author"));
				String description = UTF8Encoder.utf8Convert(request.getParameter("description"));
				String maxreplay = request.getParameter("maxreplay");
				String id = request.getParameter("id");
				int resultStatus = sessionWebclient.changeExpScript(id, "-1", description, author);
					
				if(resultStatus == 204){	
					
					%><script type="text/javascript">

					PosChangeAlert();
					var id = getParameter("id");
					var url ="/WebEditor/view/ExpScript/"+id;
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
	<h2><fmt:message key="DetailsView"/></h2>
	<h3><fmt:message key="ExpScript"/>:</h3><br>
	<b><fmt:message key="Name"/>: ${it.name}</b><br><br>
	
	<form id="expChange" name="expChange">
	<label for="author"><fmt:message key="Author"/>:</label><br>
	<input id="author" name="author" type="text" value='${it.author}'><br><br>
	<label for="description"><fmt:message key="Description"/>:</label><br>
	<textarea name="description" style="resize:none" cols="45" rows="3">${it.description}</textarea><br><br>
	
	<input type="hidden" name="id" value='${it.id}'>
	<input type="submit" value="<fmt:message key="Save Changes"/>"><br><br>
	</form>
	</body>
</html>
