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
<%@page import="webclient.*" import="java.util.*" import="model.db.*" contentType="text/html"
pageEncoding="UTF-8" isELIgnored="false" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
 
		function createOptionTextfields(){
		    
			var number = $('#optNumber').val();
			var options = document.getElementById('options');
			var g = 1;
        	for(var i = number;i>0;i--){
        		var j= document.createElement('input');
        		
        		j.setAttribute("id", g);
        		j.setAttribute("name", g);
        		j.setAttribute("type", "text");
        		options.appendChild(j);
        		br =document.createElement('br');
        		br2 =document.createElement('br');
        		options.appendChild(br);
        		options.appendChild(br2);
        		g++;
        	}
		        }

        </script>
<% 
     WebClient sessionWebclient;
 
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			ExpEditor expEditor = (ExpEditor) session.getAttribute("expeditor");
			//changing an ExpScriptSection
			if(request.getParameter("id") != null){
				
				String id = request.getParameter("id");
				String position = request.getParameter("position");
				String ordering = request.getParameter("order");
				String maxreplay = request.getParameter("maxreplay");
				
				int resultStatus = sessionWebclient.changeExpScriptSection(id, position, "-1", ordering, maxreplay);
				if(resultStatus == 204){	
					
					%><script type="text/javascript">

					PosChangeAlert();
					var id = getParameter("id");
					var parentid = getParameter("parentid");
					var position = getParameter("position");
					var oldPosition = getParameter("oldPosition");
					var oldOrdering = getParameter("oldOrdering");
					var order = getParameter("order");
					if(position != oldPosition){
						$('#movedNodeId', window.parent.document).html(id);
						$('#movedNodeParentId', window.parent.document).html(parentid);
						$('#movedNodeNewPosition', window.parent.document).html(position);
						$('#movebuttonSection', window.parent.document).click();
						
					}
					if(order != oldOrdering){
						$('#'+id, window.parent.document).find("#ordering").html(order);
					}	
					var url ="/WebEditor/view/ExpScriptSection/"+<%out.print(id);%>;
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
	<h3><fmt:message key="ExpScriptSection"/>:</h3><br>
	<b><fmt:message key="Name"/>: ${it.name}</b><br><br>
	
	<form id="expChange" name="expChange">
		<label for="position"><fmt:message key="Position"/>:</label><br>
		<select id="position" name="position">
			<c:forEach items="${it.expScript.expScriptSections}" varStatus="status">
				<c:choose>
					<c:when test="${status.count == it.position}">
					<option selected>${status.count}</option>
					</c:when>
					<c:otherwise>
					<option>${status.count}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	</select><br><br>
		
		<label for="order"><fmt:message key="Ordering"/>:</label><br>
		<select name="order" id="order">
		<c:if test="${it.ordering == 'sequential'}">
			<option id="sequential" value="sequential" selected><fmt:message key="Sequential"/></option>
			<option id="random" value="random"><fmt:message key="Random"/></option>
			</c:if>
		    <c:if test="${it.ordering == 'random'}">
			<option id="sequential" value="sequential" selected><fmt:message key="Sequential"/></option>
			<option id="random" value="random" selected><fmt:message key="Random"/></option>
		    </c:if>
		</select><br><br>
		
		<label for="maxreplay"><fmt:message key="Maxreplay"/>:</label><br>
		<select id="maxreplay" name="maxreplay">
			<c:forEach var="i" begin="1" end="5">
				<c:choose>
						<c:when test="${it.maxreplay == i}">
						<option selected>${i}</option>
						</c:when>
						<c:otherwise>
						<option>${i}</option>
						</c:otherwise>
				</c:choose>
			</c:forEach>
		</select><br><br>
	
		<input type="hidden" name="id" value='${it.id}'>
		<input type="hidden" id="parentid" name="parentid" value='${it.expScript.id}'/>
		<input type="hidden" id="oldPosition" name="oldPosition" value='${it.position}'/>
		<input type="hidden" id="oldOrdering" name="oldOrdering" value='${it.ordering}'/>
		
		<input type="submit" value="<fmt:message key="Save Changes"/>"><br><br>
	</form>
	


</body>
</html>