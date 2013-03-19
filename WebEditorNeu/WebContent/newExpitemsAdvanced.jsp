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
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>WebEditor</title>
        
        <link type="text/css" rel="stylesheet" href="/WebEditor/css/style_altern.css"/>
        
        <link type="text/css" rel="stylesheet" href="/WebEditor/css/jquery-ui-1.9.2.custom.css"/>
        <script type="text/javascript" src="/WebEditor/webeditor.js" charset="utf-8"></script>
        <script type="text/javascript" src="/WebEditor/jquery/jquery.min.js" charset="utf-8"></script>
        <script type="text/javascript" src="/WebEditor/jstree/_lib/jquery.cookie.min.js"></script>
        <script type="text/javascript" src="/WebEditor/jquery/jquery-ui-1.9.2.custom.min.js" charset="utf-8"></script>
        <script type="text/javascript" src="/WebEditor/jquery/jquery.blockUI.js"></script>
        
        <script type="text/javascript" charset="utf-8">
        
	    var selectAll="<%=(String) session.getAttribute("SelectAll")%>";
	    var notRightFormat="<%=(String) session.getAttribute("NotRightFormat")%>";
	    var noAudioFileSelected="<%=(String) session.getAttribute("NoAudioFileSelected")%>";
	    var noURLDirSelected="<%=(String) session.getAttribute("NoURLDirSelected")%>";
	    var noTemplateSelected="<%=(String) session.getAttribute("NoTemplateSelected")%>";
	    var noNumber="<%=(String) session.getAttribute("NoNumber")%>";
	    var noUrlNumber="<%=(String) session.getAttribute("NoUrlNumber")%>";
	    var expItemsCreated="<%=(String) session.getAttribute("ExpItemsCreated")%>"; 
	    var noCSVFileSelected="<%=(String) session.getAttribute("NoCSVFileSelected")%>"; 
        
        function toggleDirItems(mainItem) {
        	  checkboxes = $('.dirItem');
        	  checkboxes.each(function(index, element){
        		  var $element = $(element);
        		  $element.attr('checked', mainItem.checked);  
        	  });

        }
        
        function toggleCSVItems(mainItem) {
      	  checkboxes = $('.csvItem');
      	  checkboxes.each(function(index, element){
      		  var $element = $(element);
      		  $element.attr('checked', mainItem.checked);  
      	  });

      }
        
        $(function() {

            $("#number").spinner();
            $("#number").spinner("option", "min", 1);
            $("#number").spinner( "value",1);
            
            $("#urlNumber1").spinner();
            $("#urlNumber1").spinner("option", "min", 1);
            $("#urlNumber1").spinner( "value",1);
            
            $("#urlNumber2").spinner();
            $("#urlNumber2").spinner("option", "min", 1);
            $("#urlNumber2").spinner( "value",1);
            
            $( "#tabs" ).tabs();

        });
        
        function submitMultipleItemsForm(){
        	
        	var value = document.getElementById("number").value;
        	if(document.getElementById("selectTemplateMultiple")==null) alert(noTemplateSelected);


        	else if(!isNaN(value)){
        		$("#createContainer").hide();
        		document.multipleItemsForm.submit();
        	}else {
        		$("#number").spinner( "value",1);
        		alert(noNumber);

        	}
        }
        	function submitItemsFromCSVForm(){
            	
            	var value = document.getElementById("urlNumber2").value;
            	if(document.getElementById("selectTemplateCSV")==null) alert(noTemplateSelected);

            	
            	else if(!isNaN(value)){
            		$("#createContainer").hide();
            		document.itemsFromCSVForm.submit();
            	}else {
            		$("#urlNumber2").spinner( "value",1);
            		alert(noUrlNumber);

            	}
        	}
            function submitItemsFromDirForm(){
                	
                	var value = document.getElementById("urlNumber1").value;
                	if(document.getElementById("selectTemplateDir")==null) alert(noTemplateSelected);

                	
                	else if(!isNaN(value)){
                		$("#createContainer").hide();
                		document.selectItems.submit();
                	}else {
                		$("#urlNumber1").spinner( "value",1);
                		alert(noUrlNumber);

                	}
       		}
		
        // creating file name list of an CSV file 
        function processFile(it){
        	
        	var files = it.files;
        	
        	
        		
        		$("#itemsFromCSVEntries").empty();

                    var reader = new FileReader();
                    reader.readAsText(files[0]);
                    
                    reader.onloadend = function() {
                    	var result = reader.result;
                    	var lines = result.split(/\r\n|\r|\n/);
                    	if(lines != null){
                    		if(lines[0].substring(0,4)== "http"){
                    		$("#itemsFromCSVEntries").append("<br><input name='togglebox' type='checkbox' value='active' onclick='toggleCSVItems(this);' ontouch='toggleCSVItems(this);' style='vertical-align:middle'/>"+selectAll+"<br><br>");
		                    	for(var i=0; i< lines.length-1; i++){
		                    		if(lines[i].substring(0,4)== "http"){
		                    			var lineShort = lines[i].split("/");
		                    		$("#itemsFromCSVEntries").append("<div title='"+lines[i]+"' style='height:1.5em;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;'><input type='checkbox' name='item"+i+"' value='"+lines[i]+"' class='csvItem' style='vertical-align:middle;'/>"+lineShort[lineShort.length-1]+"</div>");
		                    		}
		                    	}
                    	    }else alert(notRightFormat);
                    	}		
                    };
        }
        
        </script>
    </head>
    <body>
    <fmt:setLocale value='${locale}'/>
	<h2><fmt:message key="ExpItemsAdvanced"/></h2>
    <div id="createContainer">
    <div id="tabs">
		<ul>
			<li><a href="#tabs-1"><fmt:message key="itemsFromDirTab"/></a></li>
			<li><a href="#tabs-2"><fmt:message key="itemsFromCSVTab"/></a></li>
			<li><a href="#tabs-3"><fmt:message key="multipleItemsTab"/></a></li>
		</ul>
	<div id="tabs-1" class="optionsframe">
	<form id="itemsFromDirForm" name="itemsFromDirForm">
	<b><fmt:message key="itemsFromDir"/>:</b><br><br>
	<label for="directoryEntry"><fmt:message key="URLDirectory"/>:</label><br>
	<input id="directoryEntry" name="directoryEntry" type="text" size="30" value="${urldirectory}"><br>
	<input type="submit" value="<fmt:message key="Submit"/>">
	</form><br><br>
	<form id="selectItems" name="selectItems">
	
<%
	WebClient sessionWebclient;

	sessionWebclient = (WebClient)session.getAttribute("webclient");
	ExpEditor expEditor = (ExpEditor) session.getAttribute("expeditor");
	
	if(request.getParameter("parentIdSql")!= null){
		
		String parentIdSql = request.getParameter("parentIdSql");
		ExpScriptSection expscrs = sessionWebclient.getExpScriptSection(parentIdSql);
		session.setAttribute("currentExpScriptSection", expscrs);
		
	}
	
	//creating multiple items using an url directory
	if(request.getParameter("selectTemplateDir")!= null){
		
		if(request.getParameter("urldirectory")==null){
			
			%><script type="text/javascript">
			$("#createContainer").hide();
			alert(noURLDirSelected);
			history.go(-1);

			</script><%
		}else{
			Enumeration<String> paramNames = request.getParameterNames();
			List<String> paramNamesList = new ArrayList<String>();
			while(paramNames.hasMoreElements()){
				String s = paramNames.nextElement();
				paramNamesList.add(s);
			}
			Collections.sort(paramNamesList);
			int i=0;
			for(String parName : paramNamesList){
				i++;
				if(parName.matches("item.*"))break;
				
				else if(i == paramNamesList.size());
				{
					
					%><script type="text/javascript">
					$("#createContainer").hide();
					alert(noAudioFileSelected);
					history.go(-1);

					</script><%
					return;
				}
				
			}
			%><script type="text/javascript">
			$.blockUI({ message: '<h1></h1>', 
				css: { backgroundColor: 'transparent',  border: '0px solid'}, 
				overlayCSS:  {backgroundColor:	'transparent'}
			  });
			</script><%
			String urlDirectory = UTF8Encoder.utf8Convert(request.getParameter("urldirectory"));
			String urlNumber = request.getParameter("urlNumber1");
			String selectTemplateDir = request.getParameter("selectTemplateDir");
			String sectionId = request.getParameter("sectionId");
			int urlNumberInt = Integer.parseInt(urlNumber);
			List<String> items = new ArrayList<String>();
			for(String parName : paramNamesList){
				
				if(parName.matches("item.*")){
				String itemFull = urlDirectory+request.getParameter(parName);
				items.add(itemFull);

				}
				
			}
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			Collections.sort(items);
			int j = 0;
			for(String item: items){
				j++;
				sb1.append(item);
				if(j%urlNumberInt==0 ||j==items.size()){
				
					String url = sb1.toString();
					ExpItem expiTemplate = sessionWebclient.getExpItem(selectTemplateDir);
					String idNewItem = sessionWebclient.createExpItem("-1", expiTemplate.getLabel(), expiTemplate.getOptions(), expiTemplate.getAssessmentoptions(), expiTemplate.getExpected(), url, sectionId);
					if(idNewItem != null){
					sb2.append(idNewItem);
					sb2.append("|");
					}
					sb1.delete(0, sb1.length());
				}
				else sb1.append("|");
			}
			
			if(sb2.length()>0){
				sb2.deleteCharAt(sb2.length()-1);
				String allNewIds = sb2.toString();
				session.setAttribute("allnewIds", allNewIds);
				
				%><script type="text/javascript">

			 	var ids="<%=(String) session.getAttribute("allnewIds")%>";
			 	$("#createContainer").hide();
			 	alert(expItemsCreated);
			 	$.cookie("jstree_select", null);
				window.parent.location = "/WebEditor/overview.jsp?selectedids="+ids;

				</script><%
			}
			

		}
	}
	//creating multiple items using a CSV file
	if(request.getParameter("selectTemplateCSV")!= null){

			Enumeration<String> paramNames = request.getParameterNames();
			List<String> paramNamesList = new ArrayList<String>();
			while(paramNames.hasMoreElements()){
				String s = paramNames.nextElement();
				paramNamesList.add(s);
			}
			Collections.sort(paramNamesList);
			int i=0;
			for(String parName : paramNamesList){
				i++;
				if(parName.matches("item.*"))break;
				
				else if(i == paramNamesList.size());
				{

					%><script type="text/javascript">
					$("#createContainer").hide();
					alert(noCSVFileSelected);
					history.go(-1);

					</script><%
					return;
				}
				
			}
			
			%><script type="text/javascript">
			$.blockUI({ message: '<h1></h1>', 
				css: { backgroundColor: 'transparent',  border: '0px solid'}, 
				overlayCSS:  {backgroundColor:	'transparent'}
			  });
			</script><%
			String urlNumber = request.getParameter("urlNumber2");
			String selectTemplateCSV = request.getParameter("selectTemplateCSV");
			String sectionId = request.getParameter("sectionId");
			int urlNumberInt = Integer.parseInt(urlNumber);

			List<String> items = new ArrayList<String>();
			for(String parName : paramNamesList){
				
				if(parName.matches("item.*")){
				String itemFull = request.getParameter(parName);
				items.add(itemFull);

				}
				
			}
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			Collections.sort(items);
			int j = 0;
			for(String item: items){
				j++;
				sb1.append(item);
				if(j%urlNumberInt==0 ||j==items.size()){
				
					String url = sb1.toString();
					ExpItem expiTemplate = sessionWebclient.getExpItem(selectTemplateCSV);
					String idNewItem = sessionWebclient.createExpItem("-1", expiTemplate.getLabel(), expiTemplate.getOptions(), expiTemplate.getAssessmentoptions(), expiTemplate.getExpected(), url, sectionId);
					if(idNewItem != null){
					sb2.append(idNewItem);
					sb2.append("|");
					}
					sb1.delete(0, sb1.length());
				}
				else sb1.append("|");
			}
			
			if(sb2.length()>0){
				sb2.deleteCharAt(sb2.length()-1);
				String allNewIds = sb2.toString();
				session.setAttribute("allnewIds", allNewIds);
				
				%><script type="text/javascript">

			 	var ids="<%=(String) session.getAttribute("allnewIds")%>";
			 	$("#createContainer").hide();
			 	alert(expItemsCreated);
			 	$.cookie("jstree_select", null);
				window.parent.location = "/WebEditor/overview.jsp?selectedids="+ids;

				</script><%
			}
			

		}
	
    // creating the file name list of an url directory
	if(request.getParameter("directoryEntry")!= null){
		
		String urldirectory = UTF8Encoder.utf8Convert(request.getParameter("directoryEntry"));
		if(!urldirectory.endsWith("/")){
			
			urldirectory=urldirectory+"/";
			System.out.println(urldirectory);
		}
		session.setAttribute("urldirectory", urldirectory);
		URLDirectoryLister lister = new URLDirectoryLister(urldirectory);
		String submit = (String) session.getAttribute("Submit");
		List<String> items = lister.getListing();
		
		if(items.size()>0){
			String slctAll =(String)session.getAttribute("SelectAll");
			out.print("<input name=\"togglebox\" type=\"checkbox\" value=\"active\" onclick=\"toggleDirItems(this);\" ontouch=\"toggleDirItems(this);\" style=\"vertical-align:middle\"/>"+slctAll+"<br><br>");
			int i=1;
			for(String item:items){
				item= UTF8Encoder.utf8Convert(item);
				item=item.replace("%20", " ");
				out.print("<div title='"+item+"' style='height:1.5em;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;'><input type=\"checkbox\" name=\"item"+i+"\" value=\""+item+"\" class=\"dirItem\" style=\"vertical-align:middle\"/>"+item+"<br></div>");
			i++;
			}
		out.print("<input type=\"hidden\" name=\"urldirectory\" value=\""+urldirectory+"\"><br><br>");

		}else{ 
		String failed = (String) session.getAttribute("URLDirfailed");
		out.print("<p>"+failed+"</p>");
		}
	}
	// creating multiple items
	if(request.getParameter("selectTemplateMultiple")!= null){
		
		%><script type="text/javascript">
		$.blockUI({ message: '<h1></h1>', 
			css: { backgroundColor: 'transparent',  border: '0px solid'}, 
			overlayCSS:  {backgroundColor:	'transparent'}
		  });
		</script><%
		int number = Integer.parseInt(request.getParameter("number"));
		String selectTemplateMultiple = request.getParameter("selectTemplateMultiple");
		String sectionId = request.getParameter("sectionId");
		
		ExpItem expiTemplate = sessionWebclient.getExpItem(selectTemplateMultiple);
		StringBuilder sb1 = new StringBuilder();
		
		for(int i=0;i<number;i++){
			
			String idNewItem = sessionWebclient.createExpItem("-1", expiTemplate.getLabel(), expiTemplate.getOptions(), expiTemplate.getAssessmentoptions(), expiTemplate.getExpected(), "", sectionId);
			sb1.append(idNewItem);
			sb1.append("|");
		}
		
		if(sb1.length()>0){
			sb1.deleteCharAt(sb1.length()-1);
			String allNewIds = sb1.toString();
			session.setAttribute("allnewIds", allNewIds);
			
			%><script type="text/javascript">

		 	var ids="<%=(String) session.getAttribute("allnewIds")%>";
		 	$("#createContainer").hide();
		 	alert(expItemsCreated);
		 	$.cookie("jstree_select", null);
			window.parent.location = "/WebEditor/overview.jsp?selectedids="+ids;

			</script><%
		}
		
		
	}
%>

	<label for="selectTemplateDir"><fmt:message key="SelectTemplate"/>:</label><br>
	<c:choose>
		<c:when test="${fn:length(currentExpScriptSection.expItems)!=0}">
		<select id="selectTemplateDir" name="selectTemplateDir">
		<c:forEach var="expItem" items="${currentExpScriptSection.expItems}" varStatus="expItemStatus">
			<option value='${expItem.id}'><fmt:message key="ExpItem"/> ${expItemStatus.count}</option>
		</c:forEach>
		</select><br>
		</c:when>
		<c:otherwise><b><fmt:message key="CreateTemplate"/></b></c:otherwise>
	</c:choose><br><br>
	<label for="urlNumber1"><fmt:message key="audioPerItem"/>:</label><br>
    <input id="urlNumber1" name="urlNumber1"/><br>
    <input id="sectionId" name="sectionId" type="hidden" value='${currentExpScriptSection.id}'>
    <br><input type="button" value="<fmt:message key="Submit"/>" onclick="submitItemsFromDirForm();" ontouch="submitItemsFromDirForm();">
    </form>
	</div>
	
	<div id="tabs-2" class="optionsframe">
	<b><fmt:message key="itemsFromCSV"/>:</b><br><br>
	<label for="file"><fmt:message key="SelectCSV"/>:</label><br>
	<input type="file" id="file" name="file" onchange="processFile(this);"/><br><br>
	
	<form id="itemsFromCSVForm" name="itemsFromCSVForm">
	<div id="itemsFromCSVEntries"></div><br>
	<label for="selectTemplateCSV"><fmt:message key="SelectTemplate"/>:</label><br>
	<c:choose>
		<c:when test="${fn:length(currentExpScriptSection.expItems)!=0}">
		<select id="selectTemplateCSV" name="selectTemplateCSV">
		<c:forEach var="expItem" items="${currentExpScriptSection.expItems}" varStatus="expItemStatus">
			<option value='${expItem.id}'><fmt:message key="ExpItem"/> ${expItemStatus.count}</option>
		</c:forEach>
		</select><br>
		</c:when>
		<c:otherwise><b><fmt:message key="CreateTemplate"/></b></c:otherwise>
	</c:choose><br><br>
	<label for="urlNumber2"><fmt:message key="audioPerItem"/>:</label><br>
    <input id="urlNumber2" name="urlNumber2"/><br><br>
    <input id="sectionId" name="sectionId" type="hidden" value='${currentExpScriptSection.id}'>
	<input type="button" value="<fmt:message key="Submit"/>" onclick="submitItemsFromCSVForm();" ontouch="submitItemsFromCSVForm();" >
	</form>
	</div>
	
	<div id="tabs-3" class="optionsframe">
	<b><fmt:message key="multipleItems"/>:</b><br><br>
	<form id="multipleItemsForm" name="multipleItemsForm" >
    <label for="number"><fmt:message key="Number"/>:</label><br>
    <input id="number" name="number"/><br><br>
    <label for="selectTemplateMultiple"><fmt:message key="SelectTemplate"/>:</label><br>
	<c:choose>
		<c:when test="${fn:length(currentExpScriptSection.expItems)!=0}">
		<select id="selectTemplateMultiple" name="selectTemplateMultiple">
		<c:forEach var="expItem" items="${currentExpScriptSection.expItems}" varStatus="expItemStatus">
			<option value='${expItem.id}'><fmt:message key="ExpItem"/> ${expItemStatus.count}</option>
		</c:forEach>
		</select><br>
		</c:when>
		<c:otherwise><b><fmt:message key="CreateTemplate"/></b></c:otherwise>
	</c:choose><br><br>
	<input id="sectionId" name="sectionId" type="hidden" value='${currentExpScriptSection.id}'>
    <input type="button" value="<fmt:message key="Submit"/>" onclick="submitMultipleItemsForm();" ontouch="submitMultipleItemsForm();">
	</form>
	</div>
</div>
</div>
</body>
<script type="text/javascript">

	if(history.length > 1){

	writeBackParams();
	$("#file").change();

	}
</script>
</html>
