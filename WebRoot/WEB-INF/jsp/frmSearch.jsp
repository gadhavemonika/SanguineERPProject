<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/validations.js"/>"></script>

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/design.css"/>" />
 <link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/jquery-ui.css"/>" />
	
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/dataTables/jquery.dataTables.css"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/dataTables/shCore.css"/>" />
<%-- 		<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:url value="/resources/css/dataTables/demo.css"/>" /> --%>
	
	<script type="text/javascript"
	src="<spring:url value="/resources/js/dataTables/jquery.dataTables.min.js"/>"></script>
	<script type="text/javascript"
	src="<spring:url value="/resources/js/dataTables/shCore.js"/>"></script>
	<script type="text/javascript"
	src="<spring:url value="/resources/js/dataTables/demo.js"/>"></script>
	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Stocks</title>

 <script type="text/javascript">
 $(document).ready(function(){
		// this is hide for warning maessge code for datatable when column data is null
		// 	 $.fn.dataTable.ext.errMode = 'none'; 
	 
	     $(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
		  $(document).ajaxComplete(function(){
		    $("#wait").css("display","none");
		  });
		  
});
	function funPreviousForm(value) {
		window.opener.funSetData(value);
		window.close();
	}
	
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	$(document).ready(function() {
		var columns = [];
		var colData = [];

		$.ajax({
			url : getContextPath()+"/loadSearchColumnNames.html?",
			type : "GET",
			success : function(response) {
				$.each(response, function(i, value) {
					var obj = {
						sTitle : value
					};
					columns.push(obj);
				});
				
			$('#example').dataTable({
			 	"ajax": getContextPath()+"/searchData.html?",
	 			"columns" : columns,
	 			 "scrollY": "320px",
	 	        "scrollCollapse": true,
	 		});
			
			 $('#example tbody').on('click', 'tr', function () {
			        var code = $('td', this).eq(0).text();
			        funPreviousForm(code);
			    } );
		 	
	 		$('#example tr').eq(1).addClass("selected");
	 		$('div.dataTables_filter input').focus();
		}
		
	});
		
	 	$("body").on("keydown", function(e){
	 	    var thisIndex = $(".selected").index()+1;
	 	    var newIndex = null;
	 	    if(e.keyCode === 38) {
	 	        // up
	 	       newIndex = thisIndex - 1;
	 	    }
	 	    else if(e.keyCode === 40) {
	 	        // down
	 	        newIndex = thisIndex + 1;       
	 	    }else if(e.keyCode == 13){
	 	    		var index = $(".selected").index()+1;
    				var code = $('#example tr:eq('+index+') td:eq(0)').text();
    				funPreviousForm(code);
	 	    }
	 	    
	 		if (newIndex !== null) {
	 			if(newIndex !=0){
		 			$("#example tr").eq(thisIndex).removeClass("selected");
		 			$("#example tr").eq(newIndex).addClass("selected");
	 			}
	 		}
	 		
 		});
	 	
	});

	
	
</script> 
<script type="text/javascript">
	window.onkeyup = function (event) {
		if (event.keyCode == 27) {
			window.close ();
		}
	}
</script>
</head>


<body style="font-family: trebuchet ms,Helvetica,sans-serif !important; font-size: 11px !important; font-weight: bold !important;">

<div style="width: 100%; height: 30px; background-color: #458CCA">
		<p align="center" style="padding-top: 5px;color: white; font-size: 14px;">${searchFormTitle}</p>
	</div>
<br/>
<div id="demo">

<table class="display" id="example">

</table>
</div>
<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
		
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
</body>
</html>
