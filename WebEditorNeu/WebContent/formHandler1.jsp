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

<%@ page language="java" import="java.util.*" import="webclient.*" import="model.db.*" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FormHandler1</title>


</head>
<body>
<%
	WebClient sessionWebclient;
	 ExpEditor sessionExpEditor;
	 int itemPosEnd;
	
	
	sessionWebclient = (WebClient)session.getAttribute("webclient");
	sessionExpEditor = (ExpEditor)session.getAttribute("expeditor");
	int resultStatus;

	// moving an item
	if(request.getParameter("moved_itemID")!= null){
		
		String moved_itemID = request.getParameter("moved_itemID");
		String parentclass = request.getParameter("parentclass");
		String positionMoved= request.getParameter("positionMoved");
		
		if(parentclass.equals("ExpScriptSection")){
		sessionWebclient.changeExpScriptSection(moved_itemID, positionMoved, "-1", "-1", "-1");
		}
		if(parentclass.equals("ExpItem")){
			sessionWebclient.changeExpItem(moved_itemID, positionMoved, "-1", "-1", "-1", "-1", "-1");
			}
		
	}
	//moving multiple items
	if(request.getParameter("selItems")!= null){
		
		String parentclass = request.getParameter("parentclass");
		String positionMoved = request.getParameter("positionMoved");
		String selItems = request.getParameter("selItems");
		
		if(parentclass.equals("ExpScriptSection")){
		sessionWebclient.moveMultipleExpScriptSections(selItems, positionMoved);
		}
		if(parentclass.equals("ExpItem")){
			sessionWebclient.moveMultipleExpItems(selItems, positionMoved);	
			}
		
	}
	//deleting an item
	if(request.getParameter("deleteID")!= null){
		String id = request.getParameter("deleteID");
		String parent = request.getParameter("parent");
		
		if(parent.equals("Experiment")){
		resultStatus = sessionWebclient.deleteExperiment(id);

		}
		else if(parent.equals("ExpScript")){
			resultStatus = sessionWebclient.deleteExpScript(id);
		}
		else if(parent.equals("ExpScriptSection")){
			resultStatus = sessionWebclient.deleteExpScriptSection(id);
		}
		else if(parent.equals("ExpItem")){
			resultStatus = sessionWebclient.deleteExpItem(id);
		}
	}
	//renaming an item
	if(request.getParameter("renameID")!= null){
		String id = request.getParameter("renameID");
		String parent = request.getParameter("renameparent");
		String newName = UTF8Encoder.utf8Convert(request.getParameter("newName"));
		
		if(parent.equals("Experiment")){
			resultStatus = sessionWebclient.changeExperiment(id, newName, "-1", "-1");
		}
		else if(parent.equals("ExpScript")){
			resultStatus = sessionWebclient.changeExpScript(id, newName, "-1", "-1");
		}
		else if(parent.equals("ExpScriptSection")){
			resultStatus = sessionWebclient.changeExpScriptSection(id, "-1", newName, "-1", "-1");
		}
		
	}
	// creating an item
	if(request.getParameter("createparent")!= null){
		String parent = request.getParameter("createparent");
		String idParentSql = request.getParameter("idParentSql");
		String expEditorId = request.getParameter("expEditorId");
		
		String idNew="";
		
		if(parent.equals("Experiment")){
			
			idNew = sessionWebclient.createExperiment("", "", expEditorId, idParentSql);
		}
		if(parent.equals("ExpScript")){
			
			ExpEditor expEditor = ExpEditor.getExpEditorStatic(expEditorId);
			String name = expEditor.getFirstName()+" "+expEditor.getName();
			idNew = sessionWebclient.createExpScript("", "", name, idParentSql);
		}
		if(parent.equals("ExpScriptSection")){

			idNew = sessionWebclient.createExpScriptSection("", "", "sequential", "1", idParentSql);
		}
		if(parent.equals("ExpItem")){

			idNew = sessionWebclient.createExpItem("-1", "", "", "", "", "", idParentSql);
		}
		

		out.print("<p>"+idNew+"</p>");
		
	}
	// checking all urls of an ExpScriptSection
	if(request.getParameter("checkUrlID")!= null){
		
		String id = request.getParameter("checkUrlID");
		StringBuilder itemFailed = new StringBuilder();
		ExpScriptSection expScriptSection = ExpScriptSection.getExpScriptSectionStatic(Integer.parseInt(id));
		List<ExpItem> expItems = expScriptSection.getExpItems();
		
		for(ExpItem expItem : expItems){
			
			if(!expItem.getUrl().equals("")){

			List<String> urlsSep = expItem.getUrlsSep();
			
			
				for(String url : urlsSep){

					if(!ExpItem.isValid(url)){
						
						itemFailed.append(expItem.getId());
						itemFailed.append("|");
						break;
						
					}
				}
			}else{
				itemFailed.append(expItem.getId());
				itemFailed.append("|");
			}
		}
		String itemsFailedEnd="";
		if(itemFailed.length()!=0){
		itemsFailedEnd = itemFailed.toString();
		itemsFailedEnd = itemsFailedEnd.substring(0, itemFailed.length()-1);
		}
		
		
		out.print("<p>"+itemsFailedEnd+"</p>");
	}
	//pasting an item
	if(request.getParameter("pasteID")!= null){
		
		String pasteID = request.getParameter("pasteID");
		String targettype = request.getParameter("targettype");
		String targetID = request.getParameter("targetID");
		String expEditorId = request.getParameter("expEditorId");
		
		if(targettype.equals("Experiment")){
			
			String idNew = sessionWebclient.pasteExperiment(pasteID, targetID,expEditorId);
			
			out.print("<p>"+idNew+"|</p>");
		}
		if(targettype.equals("ExpScript")){
			
			String idNew = sessionWebclient.pasteExpScript(pasteID, targetID);
			
			out.print("<p>"+idNew+"|</p>");
		}
		if(targettype.equals("ExpScriptSection")){

			String idNew = sessionWebclient.pasteExpScriptSection(pasteID, targetID);
			
			out.print("<p>"+idNew+"|</p>");
		}
		if(targettype.equals("ExpItem")){

			String idNew = sessionWebclient.pasteExpItem(pasteID, targetID);
			
			out.print("<p>"+idNew+"|</p>");
		}
		
		
	}
%>

</body>
</html>