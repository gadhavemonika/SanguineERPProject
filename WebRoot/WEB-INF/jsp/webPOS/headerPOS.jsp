
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    
   	<%-- Started Default Script For Page  --%>
    
		<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
		<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/TreeMenu.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/main.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery.fancytree.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery.numeric.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery.ui-jalert.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery.excelexport.js"/>"></script>
<%-- 		<script type="text/javascript" src="<spring:url value="/resources/js/angular.min.js"/>"></script> --%>
<script type="text/javascript" src="<spring:url value="/resources/js/angular.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/angular-cookies.js"/>"></script>

	
	
	<%-- End Default Script For Page  --%>
	
	<%-- Started Default CSS For Page  --%>

	    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
	 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" />
	    <link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/tree.css"/>" /> 
	 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/jquery-ui.css"/>" />
	 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/main.css"/>" />
	 	<link rel="stylesheet"  href="<spring:url value="/resources/css/pagination.css"/>" />
 	
 	<%-- End Default CSS For Page  --%>
 	
 	<%--  Started Script and CSS For Select Time in textBox  --%>
	
		<script type="text/javascript" src="<spring:url value="/resources/js/jquery.timepicker.min.js"/>"></script>
	  	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/jquery.timepicker.css"/>" />
	
	<%-- End Script and CSS For Select Time in textBox  --%>
	
 	  
  	<title>Web Stocks</title>
	
	<script type="text/javascript">
	var reuqUrl='';
	var maxQuantityDecimalPlaceLimit=parseInt('<%=session.getAttribute("qtyDecPlace").toString()%>');
	var maxAmountDecimalPlaceLimit=parseInt('<%=session.getAttribute("amtDecPlace").toString()%>');
   	function getContextPath() 
   	{
	  	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
   
   	
   	var debugFlag = false;
   	function debug(value)
   	{
   		if(debugFlag)
   		{
   			alert(value);
   		}   		
   	} 
   	
    $(document).ready(function(){
    	
    	var posModule = '<%=session.getAttribute("webPOSModuleSelect").toString()%>';
    	var reuqUrl='';
    	if(posModule=='M')
    	{
    		reuqUrl = "frmWebPOSSelectionMaster.html";
    	}
    	if(posModule=='T')
		{
    		reuqUrl = "frmWebPOSSelectionReport.html";
		}
    	if(posModule=='R')
		{
    		reuqUrl = "frmWebPOSSelectionTransection.html";
		}
    	
    
   });
    
    function funRequestUel()
    {
    	window.location.href = reuqUrl;
    }
   	
   	
	</script>
<script  type="text/JavaScript">
document.onkeypress = stopRKey;
function stopRKey(evt) {
              var evt = (evt) ? evt : ((event) ? event : null);
              var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
              if (evt.keyCode == 13)  {
                           //disable form submission
                           return false;
              }
}
</script>
	
  	</head>
		<body ng-app="webPOSApp">
		 <div id="posPageTop"  style="background: white;" >		
		<table>
<!-- 		<lable style="margin-left: 40px;">Search&nbsp;&nbsp;&nbsp;</lable> -->
<!-- 		<input type="text" class="menusearchTextBox" id="txtSearch" style="margin-top: 20px; height: 28px;width: 218px;" ng-model="searchKeyword"></input> -->
		<thead style="">
			<tr>
			<th><lable style="margin-left: 40px;"></lable>
		<input type="text" class="menusearchTextBox" id="txtSearch" style="margin-top: 20px; height: 28px;width: 218px;" ng-model="searchKeyword"></input>
		</th>
				<th style="width: 34%; ">	</th>
				<th ><a href="frmWebPOSModuleSelection.html" ><img  src="../${pageContext.request.contextPath}/resources/images/imgChangeModule.png" title="POS Module Selection" ></a> &nbsp;&nbsp;
					&nbsp;&nbsp;
					<a href="frmWebPOSChangeSelection.html" style="text-decoration:underline ;color: white;" ><img  src="../${pageContext.request.contextPath}/resources/images/imgChangePOS.png" title="Change POS" ></a>&nbsp;&nbsp; <label></label>&nbsp;&nbsp; 
					<a href="logout.html" style="text-decoration:underline;color: white;"><img  src="../${pageContext.request.contextPath}/resources/images/imgLogOut.png" title="LOGOUT"  ></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</th>
			</tr>
		</thead>
		</table>
		
		</div>
		</body>
</html>