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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<title>WebEditor</title>
	<script type="text/javascript" src="/WebEditor/jquery/jquery.min.js"></script>
	
	<script type="text/javascript" src="/WebEditor/jstree/_lib/jquery.cookie.min.js"></script>
	<script type="text/javascript" src="/WebEditor/jstree/_lib/jquery.hotkeys.js"></script>
	<script type="text/javascript" src="/WebEditor/jstree/jquery.jstree.min.js"></script>
	<script type="text/javascript" src="/WebEditor/jquery/jquery.blockUI.js"></script>

	<script type="text/javascript" src="/WebEditor/webeditor.js" ></script>
			
	<link type="text/css" rel="stylesheet" href="/WebEditor/css/style_tree.css"/>
	<link rel="shortcut icon" href="/WebEditor/icons/percy_128.ico">
<script type="text/javascript">

	<% 
	ExpEditor sessionExpEditor;
	sessionExpEditor = (ExpEditor)session.getAttribute("expeditor");
	%>;
	var expeditorId="<%=(String) String.valueOf(sessionExpEditor.getId())%>";
	
	function onLoad(){
		
		var url = "/WebEditor/view/ExpEditor/"+expeditorId;
		  $("iframe#expifr").attr("src", url);
	}




</script>
	
<% 
     WebClient sessionWebclient;

			sessionWebclient = (WebClient)session.getAttribute("webclient");
	
%>

</head>
<fmt:setLocale value='${locale}'/>
<body onload='onLoad();'>

<div id="buttons">
<img src="/WebEditor/icons/percy_128.png" align="left" width="50px" height="50px" style="margin-top:5px">
<div align="center" style="position: absolute; width:200px; height:60px; left:50%;margin-left:-100px;">
<h4>Percy Web-Editor</h4>
</div>
</div>
<iframe id="expifr" name="expifr" src="" scrolling="auto" ></iframe>
</body> 
</html>