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
pageEncoding="utf-8" isELIgnored="false"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<title>WebEditor</title>
	<script type="text/javascript" src="/WebEditor/jquery/jquery.min.js"></script>
	
	<script type="text/javascript" src="/WebEditor/jstree/_lib/jquery.cookie.min.js"></script>
	<script type="text/javascript" src="/WebEditor/jstree/jquery.jstree.js"></script>
	<script type="text/javascript" src="/WebEditor/jquery/jquery.blockUI.js"></script>

	<script type="text/javascript" src="/WebEditor/webeditor.js" ></script>
			
	<link type="text/css" rel="stylesheet" href="/WebEditor/css/style_tree.css"/>
	<link rel="shortcut icon" href="/WebEditor/icons/percy_128.ico">
<% 
     WebClient sessionWebclient;
	 ExpEditor sessionExpEditor;
			
			
			sessionWebclient = (WebClient)session.getAttribute("webclient");
			sessionExpEditor  = (ExpEditor)session.getAttribute("expeditor");
			String empty = "";
			session.setAttribute("jqueryIds", empty);
			
			
			ExpEditor result = sessionWebclient.getExpEditor(sessionExpEditor.getEmail(),sessionExpEditor.getPassword());

			if(result != null){	
				session.setAttribute("expeditor", result);
				sessionExpEditor  = (ExpEditor)session.getAttribute("expeditor");
			}
			
			if(request.getParameter("selectedids")!= null){
				
				String ids = request.getParameter("selectedids");
				String[]sepIds = ids.split("\\|");
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<sepIds.length;i++){
					sb.append("\'#"+sepIds[i]+"\',");
				}
				String jqueryIds = sb.toString().substring(0, sb.length()-1);
				session.setAttribute("jqueryIds", jqueryIds);
				
			}
	
%>
	
			<script type="text/javascript">
			
				
		 		var expEditorId="<%=String.valueOf(sessionExpEditor.getId())%>";
			 	var confirmDelete="<%=(String) session.getAttribute("ConfirmDeleteSelection")%>";
			 	var copyNotAllowed="<%=(String) session.getAttribute("CopyNotAllowed")%>";
			 	var newElement="<%=(String) session.getAttribute("New")%>";
			 	var checkAllUrls="<%=(String) session.getAttribute("CheckAllUrls")%>";
				var s12="<%=(String) session.getAttribute("ExpItem")%>";
				var s15="<%=(String) session.getAttribute("Advanced")%>";
				var s16="<%=(String) session.getAttribute("ExpScripts")%>";
				var s17="<%=(String) session.getAttribute("ExpScriptSections")%>";
				var s18="<%=(String) session.getAttribute("ExpItems")%>";
				var initselect= "<%=(String) session.getAttribute("jqueryIds")%>";
				var deleteNotAllowed= "<%=(String) session.getAttribute("DeleteNotAllowed")%>";
				var pasteNotAllowed="<%=(String) session.getAttribute("PasteNotAllowed")%>";

			 	var idNew;
			 	var s1="";
				var option = null;	
				var selectedParent;
				var selected_node;
				var selected_node_id;

				function movingNodeItem(){
					var idNode =$('#movedNodeId').html();
					var idParent =$('#movedNodeParentId').html();
					var movedNodeNewPosition =$('#movedNodeNewPosition').html();
					$idNode=$('#'+idNode);
					$idParent=$('#ExpItems'+idParent).children("ul");
					$("#userTree").jstree("move_node", $idNode,$idParent,movedNodeNewPosition-1,false, false, true);
					setIndexesExpItemList(idParent);
				};
				
				function movingNodeSection(){
					var idNode =$('#movedNodeId').html();
					var idParent =$('#movedNodeParentId').html();
					var movedNodeNewPosition =$('#movedNodeNewPosition').html();
					$idNode=$('#'+idNode);
					$idParent=$('#ExpScriptSections'+idParent).children("ul");
					$("#userTree").jstree("move_node", $idNode,$idParent,movedNodeNewPosition-1,false, false, true);
					setIndexesExpScriptSectionList(idParent);
				};
				
				function setIndexes(){

						var expItemLists = $(".ExpItem");
						expItemLists.each(function(index, list){
							var $list = $(list);
							$list.find('li').each(function(index, element){
								var $element = $(element);
								$element.find("#position").html(index+1);
							});	
						});
						var expScriptSectionLists = $(".ExpScriptSection");
						
						expScriptSectionLists.each(function(index, list){
							var $list = $(list);
							$list.children('li').each(function(index, element){
								var $element = $(element);
								$element.find("#sectPosition").html(index+1+". ");
							});	
						});
				}
				
				function setIndexesExpItemList(id){

					var items = $('#ExpItems'+id).children("ul").children("li");

					items.each(function(index, element){

						var $element = $(element);
						$element.find("#position").html(index+1);
							
					});
				}
				
				function setIndexesExpScriptSectionList(id){

					var items = $('#ExpScriptSections'+id).children("ul").children("li");

					var i = 1;
					items.each(function(index, element){
				
						var $element = $(element);
						$element.find("#sectPosition").html(i+". ");
						i++;
											
					});
				}

				function onLoad(){
					
					$("#loadingProcess").hide();
					$("#container").css("height", "90%");
					$("#container").css("border", "2px solid black");
					$("#ifr").css("height", "90%");
					$("#buttons").css("visibility", "visible");
					$("#ifr").css("border", "2px solid black");
					$("#userTree").append("<p>&nbsp;</p>");
					setIndexes();
				}
				
				//creating the context menu
				function customMenu(node) {
					
					var parentId = node.parent().attr("class");
					
					if(parentId=="Experiment")s1="<%=(String) session.getAttribute("New Experiment")%>";
					else if(parentId=="ExpScript") s1="<%=(String) session.getAttribute("New Experiment-Script")%>";
					else if(parentId=="ExpScriptSection") s1="<%=(String) session.getAttribute("New Experiment-Script-Section")%>";
					else if(parentId=="ExpItem") s1="<%=(String) session.getAttribute("New Experiment-Item")%>";
						  

					 var s2="<%=(String) session.getAttribute("Rename")%>";
					 var s3="<%=(String) session.getAttribute("Delete")%>";
					 var s4="<%=(String) session.getAttribute("Copy")%>";
					 var s5="<%=(String) session.getAttribute("Paste")%>";
					 var s6="<%=(String) session.getAttribute("Cant Change ExpProject")%>";
					
				    var items = {
				    	newItem: {
					        label: s1,
					        separator_after	: true,
					        action: function (obj) {
					        	
					        	var newObj= null;
					        	var idParentSql = obj.val();
								var parentId = obj.parent().attr("class");
					        	
					        	newObj = this.create(obj,"last",s1,null,true);


					        	$.get(
				            		    "formHandler1.jsp",
				            		    {createparent: parentId,
				            		     idParentSql : idParentSql,
				            		     expEditorId : expEditorId,	
				            		    },
				            		    function(data) {
				            		    	  
				            		    	  $("#response").empty();
				            		    	  $(data).appendTo("#response");
				            		    	  idNew = $("#response").find("p").html();
				            		    	  
				            		    	  newObj.attr("id", idNew); 
				            		    	  if(parentId=="Experiment" || parentId=="ExpScript" || parentId=="ExpScriptSection"){
				                		          if(parentId=="Experiment"){
				                		          newObj.attr("value", "Experiment");  
				                		    	  newObj.append("<ul class='ExpScript'><li value='"+idNew+"' id='ExpScripts"+idNew+"' class='ExpScripts jstree-last jstree-leaf'><ins class='jstree-icon'>&nbsp;</ins><a href='#'><ins class='jstree-icon'>&nbsp;</ins>"+s16+"</a></li></ul>");
				            		          	  }
				                		          if(parentId=="ExpScript"){
				                		        	  newObj.attr("value", "ExpScript");
				                    		    	  newObj.append("<ul class='ExpScriptSection'><li value='"+idNew+"' id='ExpScriptSections"+idNew+"' class='ExpScriptSections jstree-last jstree-leaf'><ins class='jstree-icon'>&nbsp;</ins><a href='#'><ins class='jstree-icon'>&nbsp;</ins>"+s17+"</a></li></ul>");
				                		          	  }
				                		          if(parentId=="ExpScriptSection"){
				                		        	  newObj.attr("value", "ExpScriptSection");
				                    		    	  newObj.append("<ul class='ExpItem'><li value='"+idNew+"' id='ExpItems"+idNew+"' class='ExpItems jstree-last jstree-leaf'><ins class='jstree-icon'>&nbsp;</ins><a href='#'><ins class='jstree-icon'>&nbsp;</ins>"+s18+"</a><span id='ordering' style='visibility:hidden'>sequential</span></li></ul>");
				                		          	  }
				            		          }
				            		    	  if(parentId!="ExpItem"){
				            		    		  if(parentId=="ExpScriptSection")newObj.attr("class", "jstree-open jstree-drop jstree-last");
				            		    		  else newObj.attr("class", "jstree-open jstree-last");  
				            		    	  }else{
				            		    		 newObj.attr("value", "ExpItem"); 
				            		    		 newObj.attr("class", "jstree-leaf jstree-last jstree-drop"); 
				                		         newObj.find("a").html("<ins class='jstree-icon'>&nbsp;</ins>"+s12+" <span id='position'></span><span id='urlCheck'></span>");
				            		    	  }

				            		    	  var url = "/WebEditor/view/"+parentId+"/"+idNew;
				          					  $("iframe#ifr").attr("src", url);
  
				            		    	}
				            		);
					        	
					        	this.deselect_all();
					        	this.select_node(newObj);
					        	
					        	if(parentId != "ExpItem"){
					        	obj = this._get_node(newObj);
					        	this.__rollback();
								var f = this.__callback;
								
								this._show_input(obj, function (obj, new_name, old_name) {
									f.call(this, { "obj" : obj, "new_name" : new_name, "old_name" : old_name });
									
									$.get(
					            		    "formHandler1.jsp",
					            		    {renameID : idNew,
					            		     renameparent   : parentId,
					            		     newName  : new_name,	
					            		    },
						            		    function() {
					            		    	if(parentId == "ExpScriptSection"){
					            		    		obj.children("a").html("<ins class='jstree-icon'>&nbsp;</ins><span id='sectPosition'></span>"+new_name+"");
					          			            setIndexesExpScriptSectionList(idParentSql);
 
												}
					            		    	
					            		    	 var url = "/WebEditor/view/"+parentId+"/"+idNew;
												  $("iframe#ifr").attr("src", url);
												  
		
												  
												 
					            		    }
					            		);
								});
					           }
					           else{
					        	   setTimeout(function(){
					        		   setIndexesExpItemList(idParentSql);
										}, 300);
					           } 
					        }
					    },
					    advancedItem: {
				            label: s15,
				            separator_after: true,
				            action: function (obj) { 
								 
				            	 var parentIdSql = obj.attr("value");
				            	 var url = "/WebEditor/newExpitemsAdvanced.jsp?parentIdSql="+parentIdSql;
								  $("iframe#ifr").attr("src", url);
				            	
				            	}
				        },
				        copyItem: {
				            label: s4,
				            action: function (obj) { 
				            	
				            	var selected =  this.data.ui.selected;
				            	var firstParent = selected[0].getAttribute("value");

				            	for(var i=0;i<selected.length;i++){
									
				            		if (selected[i].getAttribute("value")!=firstParent){
				            		alert(copyNotAllowed);
				            		return false;
				            		}
				            	}
				            	this.copy(); 
				            	}
				        },
				        pasteItem: { 
				            label: s5,
				            _disabled: true,
				            action: function (obj) { 
				            	
				            	var copied_nodes = this.data.crrm.cp_nodes;
				            	firstParent = copied_nodes[0].getAttribute("value");
				            	var target = obj.parent().attr("class");
				            	var targetID = obj.parent().parent().attr("id");
				            	
				            	if(firstParent != target) alert(pasteNotAllowed);
				            	else{
				            		$("#multiIDs").empty();
				            		for(var i=0;i<copied_nodes.length;i++){

					            		$.get(
						            		    "formHandler1.jsp",
						            		    {pasteID : copied_nodes[i].getAttribute("id"),
						            		     targettype   :	target,
						            		     targetID : targetID,
						            		     expEditorId : expEditorId
						            		    },
						            		    function(data){
						            		 		
						            		    	$("#response").empty();
						            		    	$(data).appendTo("#response");
						            		    	var idString = $("#response").find("p").html();
						            		    	$("#multiIDs").append(idString);
						            		    }
						            		);
					            	}
				            		
				            	}
				            }
				        },
				        deleteItem: { 
				            label: s3,
				            action: function (obj) {
				            	
				            	var selected =  this.data.ui.selected;
				            	var firstParent = selected[0].getAttribute("value");

					            	for(var i=0;i<selected.length;i++){
					
					            		if (selected[i].getAttribute("value")!=firstParent){
					            		alert(deleteNotAllowed);
					            		return false;
					            		}
					            	}  
			            		    
					            	if(confirm(confirmDelete)){
			            		
					            	var parent = selected[0].getAttribute("value");
					            	var	parentSqlId = selected[0].parentNode.parentNode.getAttribute("value");

						            	
						            	for(var i=0;i<selected.length;i++){

						            		$.get(
							            		    "formHandler1.jsp",
							            		    {deleteID : selected[i].getAttribute("id"),
							            		     parent   :	selected[i].parentNode.parentNode.parentNode.getAttribute("class")
							            		    },
							            		    function(){
							            		    	var url = $("#ifr").attr("src");
							            		    	$("iframe#ifr").attr("src", url);
							            		    }
							            		);
						            	}
					       				this.remove(); 

						            if(parent == "ExpItem"){
						            	setIndexesExpItemList(parentSqlId); 
						            }
						            
						            if(parent == "ExpScriptSection"){
						            	setIndexesExpScriptSectionList(parentSqlId); 
						            }     
					    	  }else return false;
							}

				        },
				        renameItem: { 
				        	label: s2,
				            action: function (obj) {
				            	
				            	
				            	obj = this._get_node(obj);
				            	var parent = obj.parent().parent().parent().attr("class");
				            	var id = obj.attr("id");
								this.__rollback();
								var f = this.__callback;
								this._show_input(obj, function (obj, new_name, old_name) {
									f.call(this, { "obj" : obj, "new_name" : new_name, "old_name" : old_name });
									$.get(
					            		    "formHandler1.jsp",
					            		    {renameID : id,
					            		     renameparent   : parent,
					            		     newName  : new_name,	
					            		    },
						            		    function() {
					            		    	
					            		    	 var url = "/WebEditor/view/"+parent+"/"+id;
												  $("iframe#ifr").attr("src", url);
					            		    }
					            		);
								});
	
				            }
				        },
				        disableItem: {
				            label: s6,
				            action: function () {}
				        },
				        checkUrlItem: {
				        	separator_before: true,
				            label: checkAllUrls,
				            action: function (obj) {
				            	
				            	obj = this._get_node(obj);
				            	parent = this._get_parent(obj);
				            	var id = parent.attr("id");
				            	$.get(
				            		    "formHandler1.jsp",
				            		    {checkUrlID : id
				            		    },
				            		    function(data) {
				            		    	  
				            		    	  $("#response").empty();
				            		    	  $(data).appendTo("#response");
				            		    	  var idString = $("#response").find("p").html();
				            		    	  if(idString != ""){
				            		    	  var idList = idString.split("|");
				            		    	  var children = obj.find("ul").children();
				            		    	  
					            		    	  children.each(function(index, child){
					          					    var id = $(child).attr("id");
					          						var element = obj.find("#"+id);
					          						element.find("#urlCheck").html("<img src='/WebEditor/icons/ok.png' width='10' height='9'>");
					          						 for (var i=0;i<idList.length;i++) {
					            		    		  		
					          							  if(id == idList[i]){
					          								  
					          								element.find("#urlCheck").html("<img src='/WebEditor/icons/not-ok.png' width='10' height='9'>");
					          								break;  
					          							  }
						            		    	  }
					          					  });
				            		    	  } 
				            		    } 	  
				            	 );
				              }
				          }
				    };
				    

				    if ($(node).hasClass("ExpProjects") || $(node).parent().hasClass("ExpProject")) {
				       
				        delete items.newItem;
				        delete items.renameItem;
				        delete items.deleteItem;
				        delete items.copyItem;
				        delete items.pasteItem;
				        
				    }
				    else delete items.disableItem;
				    
				    if ($(node).parent().hasClass("ExpItem")){
				    	
				    	 delete items.renameItem;

				    }
				    
					if ($(node).hasClass("Experiments")|| $(node).hasClass("ExpScripts") || $(node).hasClass("ExpScriptSections") || $(node).hasClass("ExpItems")){
				    	
				    	delete items.renameItem;
				        delete items.deleteItem;
				        delete items.copyItem;
	 	
				    }else{
				    	delete items.pasteItem;
				    	delete items.newItem;
				    }
					
					if(!$(node).hasClass("ExpItems")){
						
						delete items.checkUrlItem;
						delete items.advancedItem;
					}else{
						if(items.newItem != null)items.newItem.separator_after=false;
					}
				    
				    if(this.data.ui.selected.length > 1){
				    	
				    	delete items.renameItem;
				    }
				    if(this.data.crrm.cp_nodes != null){
				    	
					  if(items.pasteItem != null) items.pasteItem._disabled=false;
				    }
				    return items;
				}

				$(function () {

					$("#userTree")
					.bind("ajaxStart", function(){
						$.blockUI({ message: '<h1></h1>', 
									css: { backgroundColor: 'transparent',  border: '0px solid'}, 
									overlayCSS:  {backgroundColor:	'transparent'}
								  });
						})
					.bind("ajaxStop", function(){
						$.unblockUI();
						setTimeout(function(){
							var url = $("iframe#ifr").attr("src");
							$("iframe#ifr").attr("src", url);
							}, 300);
						
						
						if($("#multiIDs").html()!=""){
							$.blockUI({ message: '<h1></h1>', 
								css: { backgroundColor: 'transparent',  border: '0px solid'}, 
								overlayCSS:  {backgroundColor:	'transparent'}
							  });
							
							var ids = $("#multiIDs").html();
							$.cookie("jstree_select", null);
							window.location = "/WebEditor/overview.jsp?selectedids="+ids;
							
						}

					})
						//creating the jsTree
						.jstree({
					    "contextmenu": {items: customMenu,
					    				select_node : true,
					    },
						"crrm" : { 
							"move" : {
								"check_move" : function (m) { 
									
									moved_node = this._get_node(m.o);
									var ordering = this._get_parent(m.op).find("#ordering").html();
									var parent = m.op.parent().attr("class");
									if(!(parent=="ExpItem" || parent=="ExpScriptSection")) return false;
									if(ordering == "random") return false;
									
									var selected =  this.data.ui.selected;
					            	
					            	if(selected.length > 1){
					            		
					            	moved_node = $(selected[0]);
					            	var firstParent = selected[0].parentNode.parentNode.parentNode.getAttribute("class");
									
						            	for(var i=0;i<selected.length;i++){
											
						            		if (selected[i].parentNode.parentNode.parentNode.getAttribute("class")!=firstParent){
						            		return false;
						            		}
						            	}
										for(var i=0;i<selected.length;i++){
											
						            		if($(selected[i]).index()<m.r.index()){
						            			for(var j=0;j<selected.length;j++){
						            				if($(selected[j]).index()>m.r.index()) return false;
						            			}
						            		}
						            	}
					            	}
					            	if(m.p == "inside") return false;
									if(m.op[0] == m.np[0]) return true;
									return false;
								}
							}
						},
						"core" : {
							"strings" : {
								new_node : newElement
							}
							
						},
						"ui" : {
							initially_select : [initselect]
							
						},
						"cookies" : {
							save_loaded		: false,
							
						},
						"dnd" : {
							"open_timeout": "60000",
							"drop_finish" : function (){
								
								var parent = moved_node.parent();
								var parentclass = parent.parent().parent().attr("class");
								var parentid = parent.parent().parent().parent().attr("id");
								var positionMoved  = moved_node.index()+1;

								var selected =  this.data.ui.selected;
								if(selected.length > 1){
									var selItems = "";
									for(var i=0;i<selected.length;i++){
										selItems =selItems+selected[i].getAttribute("id")+"|";
										
									}
									selItems = selItems.substring(0,selItems.length-1);

											$.get(
							            		    "formHandler1.jsp",
							            		    {
							            		     selItems : selItems,
							            		     parentclass : parentclass,
							            		     positionMoved : positionMoved,
							            		    },
							            		    function (){
							            		    	if(parentclass=="ExpScriptSection"){
							            		    		setIndexesExpScriptSectionList(parentid);
							            		    		}
							            		    	
														else if(parentclass=="ExpItem")setIndexesExpItemList(parentid);

							            		    } 
							            	);
								}
								else {
								
								var id = moved_node.attr("id");
							
									$.get(
					            		    "formHandler1.jsp",
					            		    {moved_itemID : id, 
					            		     parentclass : parentclass,
					            		     positionMoved : positionMoved,
					            		    },
					            		    function (){
					            		    	if(parentclass=="ExpScriptSection")setIndexesExpScriptSectionList(parentid);
												else if(parentclass=="ExpItem")setIndexesExpItemList(parentid);

					            		    	

					            		    } 
					            	);
							    }	
							}
						},
						"plugins" : ["core", "themes", "html_data", "ui", "crrm", "dnd", "contextmenu", "cookies"]
					})
						
						.bind("select_node.jstree", function (event, data) {

							selected_node = data.rslt.obj;
							selected_node_id = selected_node.attr("id");
							var element = document.getElementById(selected_node_id);
							if(element != null){
							selectedParent = element.getAttribute("value");
							}
					    


								if(!isNaN(selected_node_id)){
									var url = "/WebEditor/view/"+selectedParent+"/"+selected_node_id;
									$("iframe#ifr").attr("src", url);
								} else{
									var url = "/WebEditor/detailEmpty.jsp";
									$("iframe#ifr").attr("src", url);
								}
					    })
					    .bind("close_node.jstree", function (event, data) {
					    	var closed_node = data.rslt.obj;
					    	 var closedChildren = closed_node.find(".jstree-open");
					    	 closedChildren.each(function(index, element){
					    		 var $element = $(element);
					    		 $element.removeClass("jstree-open").addClass("jstree-closed");
					    	 });
					    })
					    
					    .delegate("a", "click", function (event, data) { 
					    	
					    	event.preventDefault();

					    });
				}); 

			</script>



</head>
<fmt:setLocale value='${locale}'/>
<body id="thebody" onload='onLoad();'>


<table id="loadingProcess" style="position:absolute; height:200px; width:400px; top:50%; margin-top:-150px; left:50%; margin-left:-200px; z-index:3">
	<tr>
		<td width="100%" height="100%" align="center" valign="middle">
			<h2><fmt:message key="Loading"/></h2>
		</td>
	</tr>
</table>

	<c:set var='scope' value='${sessionScope}'/>
	<c:forEach items='${scope}' var='p'>
		<c:if test="${p.key == 'expeditor'}">
		<c:set var='expeditor' value='${p.value}' scope="session"/>
		</c:if>
	</c:forEach>

<div id="buttons" style="visibility:hidden;">
	<img src="/WebEditor/icons/percy_128.png"  align="left" width="50px" height="50px" style="margin-top:5px">
	<div align="center" style="vertical-align:middle;position: absolute; width:200px; height:60px; left:50%;margin-left:-100px;">
		<h4>Percy Web-Editor</h4>
	</div>
	<div align="right" style="vertical-align:middle;position: absolute; width:300px; height:60px; left:50%;margin-left:185px;">
		<p><a href=expeditor.jsp><fmt:message key="AccountSettings"/></a><br>
		   <a href=index.jsp><fmt:message key="Logout"/></a>
		</p>
	</div>
</div>
<div id="container" style="height:0px;">
	<div id="userTree" class="tree" style="height:99%;">
		<ul>
			<li id="ExpProjects" class="ExpProjects">
				<a href="#"><fmt:message key="ExpProjects"/></a>
				<ul id="ExpProject" class="ExpProject">
					<c:forEach var="expProject" items="${expeditor.expProjects}">
					<li id='${expProject.id}'  value="ExpProject">
						<a href="#">${expProject.name}</a>
							<ul class="Experiment">
								<li value='${expProject.id}' id="Experiments${experiment.id}" class="Experiments">
									<a href="#"><fmt:message key="Experiments"/></a>
									<ul id="Experiment" class="Experiment">
										<c:forEach var="experiment" items="${expProject.experiments}">
										<c:forEach var="editorExps" items="${expeditor.experiments}">
										<c:if test="${experiment.id == editorExps.id }">
										<li id='${experiment.id}' value="Experiment">
											<a href="#">${experiment.name}</a>
												<ul class="ExpScript">
													<li value='${experiment.id}' id="ExpScripts${experiment.id}" class="ExpScripts">
														<a href="#"><fmt:message key="ExpScripts"/></a>
														<ul id="ExpScript" class="ExpScript">
															<c:forEach var="expScript" items="${experiment.expScripts}">
															<li id='${expScript.id}' value="ExpScript">
																<a href="#">${expScript.name}</a>
																<ul class="ExpScriptSection">
																	<li value='${expScript.id}' id="ExpScriptSections${expScript.id}" class="ExpScriptSections">
																		<a href="#"><fmt:message key="ExpScriptSections"/></a>
																		<ul id="ExpScriptSection${expScript.id}" class="ExpScriptSection">
																			<c:forEach var="expScriptSection" items="${expScript.expScriptSections}">
																			<li id='${expScriptSection.id}' value="ExpScriptSection" class="jstree-drop">
																				<a id='anchor' href="#"><span id="sectPosition"></span>${expScriptSection.name}</a>
																				<ul class="ExpItem">
																					<li value='${expScriptSection.id}' id="ExpItems${expScriptSection.id}" class="ExpItems">
																						<a href="#"><fmt:message key="ExpItems"/></a>
																						<span id="ordering" style="visibility:hidden">${expScriptSection.ordering}</span>
																						<ul id="ExpItem${expScriptSection.id}" class="ExpItem">
																							<c:forEach var="expItem" items="${expScriptSection.expItems}" varStatus="expItemStatus">
																							<li id='${expItem.id}' value="ExpItem" class="jstree-drop">	
																								<a id='anchor' href="#"><fmt:message key="ExpItem"/> <span id="position"></span><span id="urlCheck"></span></a>
																							</li>
																							</c:forEach>
																						</ul>
																					</li>
																				</ul>			
																			</li>
																			</c:forEach>
																		</ul>
																	</li>
																</ul>	
															</li>
															</c:forEach>
														</ul>
													</li>
												</ul>		
										</li>
										</c:if>
										</c:forEach>
										</c:forEach>
									</ul>
								</li>
							</ul>
					</li>
					</c:forEach>
				</ul>
			</li>
		</ul>
	</div>
</div>
<iframe id="ifr" name="ifr" src="/WebEditor/detailEmpty.jsp" scrolling="auto" style="height:0px"></iframe>

<div class="hiddenItems">
<div id="response"></div>

<div id="multiIDs"></div>

<input type="button" id="movebuttonItem" value="movebuttonItem" onclick="movingNodeItem()">
<input type="button" id="movebuttonSection" value="movebuttonSection" onclick="movingNodeSection()">
<div id="movedNodeId"></div>
<div id="movedNodeParentId"></div>
<div id="movedNodeNewPosition"></div>
</div>
</body> 
</html>