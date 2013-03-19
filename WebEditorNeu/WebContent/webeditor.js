function addUserInfo() {
	"use strict";
	var maillink, subject, username;
	username = document.getElementById("username").value;

	maillink = document.getElementById("bugmail");
	subject = maillink.getAttribute("href");
	maillink.setAttribute("href", subject + username );
}


function getParameter(paramName) {
	  var searchString = window.location.search.substring(1),
	      i, val, params = searchString.split("&");

	  for (i=0;i<params.length;i++) {
	    val = params[i].split("=");
	    if (val[0] == paramName) {
	      var str = unescape(val[1]);
	      var nStr = str.replace("+", " ");
	      return nStr;
	      
	    }
	  }
	  return null;
}

function writeBackParams(){

	var i;
	var val; 
	var searchString = window.location.search.substring(1);
	
	searchString = searchString.replace("%FC","%C3%BC");
	searchString = searchString.replace("%E4","%C3%A4");
	searchString = searchString.replace("%F6","%C3%B6");
	searchString = searchString.replace("%DF","%C3%9F");
	searchString = searchString.replace("%DC","%C3%9C");
	searchString = searchString.replace("%C4","%C3%84");
	searchString = searchString.replace("%D6","%C3%96");
	
	searchString = decodeURIComponent(searchString);

	var params = searchString.split("&");
	
		for (i=0;i<params.length;i++) {
	  	val = params[i].split("=");
	  	var str = val[1];
		var element = document.getElementById(val[0]); 
		if(element != null)element.setAttribute("value", str);
		
	  
	}
}

function testAudio(number) {
	"use strict";
	
	var audio = document.getElementById("audio"+number);
		audio.play();
}