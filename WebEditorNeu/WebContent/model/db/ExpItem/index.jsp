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
pageEncoding="utf-8" isELIgnored="false" %>

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
        <script type="text/javascript" src="/WebEditor/jquery/jquery.min.js"></script>
        <script type="text/javascript" charset="utf-8">
		
        var urlsAlreadyAdded = 0;
        var optionsAlreadyAdded = 0;
        var aoptionsAlreadyAdded = 0;
        
		 var s1="<%=(String) session.getAttribute("Delete")%>";
		 var s2="<%=(String) session.getAttribute("TestAudio")%>";

        function checkUrl(number){
			
        		var value = $("#url"+number).attr("value");
	        	var audio = document.getElementById("audio"+number);
	        	if(value.match("http.*")!= null){
	        	audio.src = value;
	        	$("#image"+number).attr("src","/WebEditor/icons/ok.png");
	        	audio.addEventListener('error', function() {
	        		
	        		$("#image"+number).attr("src","/WebEditor/icons/not-ok.png");

	        	}, false);
	        	} else $("#image"+number).attr("src","/WebEditor/icons/not-ok.png");
      	}
        
        $(function () {
        	
			$('#URLsDiv').bind('click',function () { 
        		
        		var count = $("#URLsDiv").children().length;
        		if(count>0){
        			$("#URLsLabel").show();
        		}
        		else{
        			$("#URLsLabel").hide();
        		}
        	
        	
        	
        	});
			$('#OptionsDiv').bind('click',function () { 
        		
        		var count = $("#OptionsDiv").children().length;
        		if(count>0){
        			$("#OptionsLabel").show();
        		}
        		else{
        			$("#OptionsLabel").hide();
        		}
        	
        	
        	
        	});
        	$('#AssessOptionsDiv').bind('click',function () { 
        		
        		var count = $("#AssessOptionsDiv").children().length;
        		if(count>0){
        			$("#AssessOptionsLabel").show();
        		}
        		else{
        			$("#AssessOptionsLabel").hide();
        		}
        	
        	
        	
        	});
			
        });
        
        function addOptions(count, select){
        
  		
        var i=1;
        var v = null;
        var p = 0;
        var nn = 0;
        var option = "";
        var sel = null;
        switch(select){
	        case 1:{
	        	v = document.getElementById("moreURLs");
		        var n = v.options[v.selectedIndex].text;
		        nn = parseInt(n);
		        p = urlsAlreadyAdded+count;
		        urlsAlreadyAdded=urlsAlreadyAdded+nn;
		        option = "url";
		        sel = document.getElementById("URLsDiv");
		        break;
	        }
        	case 2:{
	        	v = document.getElementById("moreOptions");
		        var n = v.options[v.selectedIndex].text;
		        nn = parseInt(n);
		        p = optionsAlreadyAdded+count;
		        optionsAlreadyAdded=optionsAlreadyAdded+nn;
		        option = "option";
		        sel = document.getElementById("OptionsDiv");
		        break;
	        }
	        case 3:{ 
	        	v = document.getElementById("moreAssessOptions");
		        var n = v.options[v.selectedIndex].text;
		        nn = parseInt(n);
		        p = aoptionsAlreadyAdded+count;
		        aoptionsAlreadyAdded=aoptionsAlreadyAdded+nn;
		        option = "aoption";
		        sel = document.getElementById("AssessOptionsDiv");
		        break;
	        }
        }
        
	        for(i;i <= nn;i++){
	        	
	        	var num =p+i;
	        	var span = document.createElement("span");
	        	span.setAttribute("id", ""+option+num+"span");
	        	sel.appendChild(span);
	        	if(option == "url"){
	        	$('<input id="'+option+num+'" name="'+option+num+'" title="'+option+'" size="30" type="text" onchange="checkUrl('+num+')">').appendTo('#'+option+num+'span');
	        	$('<img id="image'+num+'"src="/WebEditor/icons/not-ok.png" width="20" height="18" style="margin-left:5px;margin-bottom:-4px">').appendTo('#'+option+num+'span');
	        	} else $('<input id="'+option+num+'" name="'+option+num+'" size="33" type="text">').appendTo('#'+option+num+'span');
	        	$('<input id="'+option+num+'button" value="'+s1+'" type="button" onclick="deleteOption('+num+','+select+');" ontouch="deleteOption('+num+','+select+');" style="padding:0; margin-left:4px">').appendTo('#'+option+num+'span');
	        	if(option == "url"){
	        		$('<input type="button" value="'+s2+'" onclick="testAudio('+num+');" ontouch="testAudio('+num+');"><audio id="audio'+num+'" src=""></audio><br><br>').appendTo('#'+option+num+'span');
	        	}else $('<br><br>').appendTo('#'+option+num+'span');

	        };
	        sel.click();
 	
        }
        
        function deleteOption(id, select){
        	
        	var idEnd="";
        	
        	switch(select){
	        	case 1: {
	
	        		idEnd = "#url"+id+"span";
	        		break;
	        	}
        		case 2: {

	        		idEnd = "#option"+id+"span";
	        		break;
	        	}
	        	case 3: {

	        		idEnd = "#aoption"+id+"span";
	        		break;
	        	}

        	}
        	$(idEnd).remove();
        	
        }
        
        function PosChangeAlert(){ 
		    var s="<%=(String) session.getAttribute("ChangesSaved")%>";  
		    alert(s); 
		  }
       function NegChangeAlert(){ 
		    var s="<%=(String) session.getAttribute("ChangesNotSaved")%>";  
		    alert(s); 
		  } 
       
       function onLoad(){
    	   
    	   var count1 = $("#URLsDiv").children().length;
    	   var count2 = $("#OptionsDiv").children().length;
    	   var count3 = $("#AssessOptionsDiv").children().length;
    	   if(count1==0) $("#URLsLabel").hide();
   		   if(count2==0) $("#OptionsLabel").hide();
   		   if(count3==0) $("#AssessOptionsLabel").hide();
   		   
   			var urlInputs = $('input[title="url"]');
    		urlInputs.each(function(index, element){
    		
    		$element = $(element);
    		var number = $element.attr("id");
    		number =number.substring(3);
    		checkUrl(number);
    		$element.removeAttr('title');
    	
    		
    	});
       }
    	   
       </script>

<% 
     WebClient sessionWebclient;
 
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			ExpEditor expEditor = (ExpEditor) session.getAttribute("expeditor");
			//changing an ExpItem
			if(request.getParameter("id") != null){

				String id = request.getParameter("id");
				String position = request.getParameter("position");
				String label = UTF8Encoder.utf8Convert(request.getParameter("label"));
				String expected = UTF8Encoder.utf8Convert(request.getParameter("expected"));
				Enumeration<String> paramNames = request.getParameterNames();
				List<String> paramNamesList = new ArrayList<String>();
				while(paramNames.hasMoreElements()){
					String s = paramNames.nextElement();
					paramNamesList.add(s);
				}
				Collections.sort(paramNamesList);
				StringBuilder sboptions = new StringBuilder();
				StringBuilder sbaoptions = new StringBuilder();
				StringBuilder sburls = new StringBuilder();

				for(String parName : paramNamesList){
					
					if(parName.matches("url.*")){
						sburls.append(request.getParameter(parName));
						sburls.append("|");
					}
					if(parName.matches("option.*")){
						sboptions.append(request.getParameter(parName));
						sboptions.append("|");
					}
					else if(parName.matches("aoption.*")){
						sbaoptions.append(request.getParameter(parName));
						sbaoptions.append("|");
					}
				}
				String url = "";
				String options = "";
				String assessmentoptions = "";
				
				if(sburls.length()!=0)	   url = UTF8Encoder.utf8Convert(sburls.substring(0, sburls.length()-1));
				if(sboptions.length()!=0)  options = UTF8Encoder.utf8Convert(sboptions.substring(0, sboptions.length()-1));
				if(sbaoptions.length()!=0) assessmentoptions = UTF8Encoder.utf8Convert(sbaoptions.substring(0, sbaoptions.length()-1));

					int resultStatus = sessionWebclient.changeExpItem(id, position, label, options, assessmentoptions, expected, url);
					if(resultStatus == 204){	
						
						%><script type="text/javascript">

						PosChangeAlert();
						var id = getParameter("id");
						var parentid = getParameter("parentid");
						var position = getParameter("position");
						
						var oldPosition = getParameter("oldPosition");
						if(position != oldPosition){
							$('#movedNodeId', window.parent.document).html(id);
							$('#movedNodeParentId', window.parent.document).html(parentid);
							$('#movedNodeNewPosition', window.parent.document).html(position);
							$('#movebuttonItem', window.parent.document).click();
							
						}
						var url ="/WebEditor/view/ExpItem/"+id;
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
    <body onload='onLoad();'>
    <fmt:setLocale value='${locale}'/>
	<h2><fmt:message key="DetailsView"/></h2>
	
	<h3><fmt:message key="ExpItem"/>:</h3><br>
	<form id="expItemChange" name="expItemChange">
	<c:if test="${it.expScriptSection.ordering=='sequential'}">
	<label for="position"><fmt:message key="Position"/>:</label><br>
	<select id="position" name="position">
		<c:forEach items="${it.expScriptSection.expItems}" varStatus="status">
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
	</c:if>
	<label for="label"><fmt:message key="Label"/>:</label><br>
	<textarea id="label" name="label" style="resize:none" cols="43" rows="2" >${it.label}</textarea><br><br>
	
	<div id="URLsDivFrame" class="optionsframe">
	<span id="URLsLabel"><label for="URLsDiv"><fmt:message key="URL"/>:</label><br></span>
	<div id="URLsDiv">
	<c:set var="UstatusinSession" value='0' scope="session"/>
	<c:forEach var="i" items="${it.urlsSep}" varStatus="Ustatus">
		<c:set var="UstatusinSession" value='${Ustatus.count}' scope="session"/>
		<span id='url${UstatusinSession}span'>
		<input id='url${UstatusinSession}' name='url${UstatusinSession}' title="url" size="30" type="text" value='${i}' onchange="checkUrl(${UstatusinSession})"/>
		<img id="image${UstatusinSession}" src="/WebEditor/icons/not-ok.png" width="20" height="18" style="margin-bottom:-4px">
		<input id='url${UstatusinSession}button' type="button" value="<fmt:message key="Delete"/>"  onclick="deleteOption(${UstatusinSession},1);" ontouch="deleteOption(${UstatusinSession},1);" style="padding:0">
		<input type="button" value="<fmt:message key="TestAudio"/>" onclick="testAudio(${UstatusinSession});" ontouch="testAudio(${UstatusinSession});"><audio id='audio${UstatusinSession}' src=""></audio><br><br>
		</span>
	</c:forEach>
	</div>

	<label for="moreURLs"><fmt:message key="moreURLs"/>:</label><br>
	<select id="moreURLs" name="moreURLs" size="1">
      <option>1</option>
      <option>2</option>
      <option>3</option>
      <option>4</option>
      <option>5</option>
    </select>
    <input type="button" id="addURLButton" onclick="addOptions(${UstatusinSession},1);" ontouch="addOptions(${UstatusinSession},1);" value="<fmt:message key="Add"/>"/><br><br>
	</div>
	<div id="OptionsDivFrame" class="optionsframe">
	<span id="OptionsLabel"><label for="OptionsDiv"><fmt:message key="Options"/>:</label><br></span>
	<div id="OptionsDiv">
	<c:set var="OstatusinSession" value='0' scope="session"/>
	<c:forEach var="i" items="${it.optionsSep}" varStatus="Ostatus">
		<c:set var="OstatusinSession" value='${Ostatus.count}' scope="session"/>
		<span id='option${Ostatus.count}span'>
		<input id='option${Ostatus.count}' name='option${Ostatus.count}' size="33" type="text" value='${i}'/>
		<input id='option${Ostatus.count}button' type="button" value="<fmt:message key="Delete"/>"  onclick="deleteOption(${OstatusinSession},2);" ontouch="deleteOption(${OstatusinSession},2);" style="padding:0"><br><br></span>
	</c:forEach>
	</div>

	<label for="moreOptions"><fmt:message key="moreOptions"/>:</label><br>
	<select id="moreOptions" name="moreOptions" size="1">
      <option>1</option>
      <option>2</option>
      <option>3</option>
      <option>4</option>
      <option>5</option>
    </select>
    <input type="button" id="addOpButton" onclick="addOptions(${OstatusinSession},2);" ontouch="addOptions(${OstatusinSession},2);" value="<fmt:message key="Add"/>"/><br><br>
    </div>
    <div id="AssessOptionsDivFrame" class="optionsframe">
    <span id="AssessOptionsLabel"><label for="AssessOptionsDiv"><fmt:message key="AssessOptions"/>:</label><br></span>
    <div id="AssessOptionsDiv">
    <c:set var="AstatusinSession" value='0' scope="session"/>
	<c:forEach var="i" items="${it.assessmentOptionsSep}" varStatus="Astatus">
		<c:set var="AstatusinSession" value='${Astatus.count}' scope="session"/>
		<span id='aoption${Astatus.count}span'>
		<input id='aoption${Astatus.count}' name='aoption${Astatus.count}' size="33" type="text" value='${i}'/>
		<input id='aoption${Astatus.count}button' type="button" value="<fmt:message key="Delete"/>"  onclick="deleteOption(${AstatusinSession},3);" ontouch="deleteOption(${AstatusinSession},3);" style="padding:0"><br><br></span>
	</c:forEach>
	</div>

	<label for="moreAssessOptions"><fmt:message key="moreAssessOptions"/>:</label><br>
	<select id="moreAssessOptions" name="moreAssessOptions" size="1">
      <option>1</option>
      <option>2</option>
      <option>3</option>
      <option>4</option>
      <option>5</option>
    </select>
    <input type="button" id="addAssessOpButton" onclick="addOptions(${AstatusinSession},3);" ontouch="addOptions(${AstatusinSession},3);" value="<fmt:message key="Add"/>"/><br><br>
	</div>
	<label for="expected"><fmt:message key="expected"/>:</label><br>
	<input id="expected" name="expected" size="35" type="text" value='${it.expected}'><br><br>
	
	<input type="hidden" id="id" name="id" value='${it.id}'/>
	<input type="hidden" id="parentid" name="parentid" value='${it.expScriptSection.id}'/>
	<input type="hidden" id="oldPosition" name="oldPosition" value='${it.position}'/>
	<input type="submit" value="<fmt:message key="Save Changes"/>"><br><br>
	
	</form>

</body>
</html>
