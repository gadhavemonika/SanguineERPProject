<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
  #tblNotify tr:hover{
	background-color: #72BEFC;
	
}
</style>
<script type="text/javascript">
var NotificationCount="";
	var maxQuantityDecimalPlaceLimit=parseInt('<%=session.getAttribute("qtyDecPlace").toString()%>');
	var maxAmountDecimalPlaceLimit=parseInt('<%=session.getAttribute("amtDecPlace").toString()%>');
   	var NotificationTimeinterval=parseInt('<%=session.getAttribute("NotificationTimeinterval").toString()%>');
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
   	   		$("#MainDiv").hide();
   	   		
   	   	<%if(session.getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")){%>
			funGetNotification();
		<%}%>
   	   	    $("#notification").click(function(){
   	   	        $("#MainDiv").fadeToggle();
   	   	    });
   	});
   	
	<%if(session.getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")){%>
		NotificationTimeinterval=parseInt(NotificationTimeinterval)*60000;
		setInterval(function(){funGetNotification()},NotificationTimeinterval);
<%}%>
	
   	function funGetNotification()
   	{
   		var searchUrl=getContextPath()+"/getNotification.html";
   		$.ajax({
   	        type: "GET",
   	        url: searchUrl,
   	        dataType: "json",
   	        success: function(response)
   	        {
   	        	funRemoveNotification();
   	        	var count=0;
   	        	$.each(response, function(i,item)
   	        	        {
   	        				count=i;
   	        				funfillNotificationRow(response[i].strReqCode,response[i].Locationby,response[i].strNarration,
   	        						response[i].strUserCreated);
   	        	        });
   	        	if(response.length>0)
        		{
        			NotificationCount=count+1;	
        		}
   	        	$("#lblNotifyCount").text(NotificationCount);
   	        },
   	        error: function(jqXHR, exception) {
   	            if (jqXHR.status === 0) {
   	                alert('Not connect.n Verify Network.');
   	            } else if (jqXHR.status == 404) {
   	                alert('Requested page not found. [404]');
   	            } else if (jqXHR.status == 500) {
   	                alert('Internal Server Error [500].');
   	            } else if (exception === 'parsererror') {
   	                alert('Requested JSON parse failed.');
   	            } else if (exception === 'timeout') {
   	                alert('Time out error.');
   	            } else if (exception === 'abort') {
   	                alert('Ajax request aborted.');
   	            } else {
   	                alert('Uncaught Error.n' + jqXHR.responseText);
   	            }		            
   	        }
   		});
   	}
   	function funfillNotificationRow(strReqCode,Locationby,strNarration,strUserCreated) 
   	{
   	    var table = document.getElementById("tblNotify");
   	    var rowCount = table.rows.length;
   	    var row = table.insertRow(rowCount);
   	    
   	    row.insertCell(0).innerHTML= "<label>"+strReqCode+"</label>";	    
   	    row.insertCell(1).innerHTML= "<label>"+Locationby+"</label>";
   	    row.insertCell(2).innerHTML= "<label>"+strNarration+"</label>";
   	    row.insertCell(3).innerHTML= "<label>"+strUserCreated+"</label>";
   	}
   	function funRemoveNotification()
   	{
   		 $("#tblNotify").find("tr:gt(0)").remove();
   	}
   	
    function funPOSHome()
   	{
    	var posCode='<%=session.getAttribute("loginPOS").toString()%>';
    	window.location.href=getContextPath()+"/frmGetPOSSelection.html?strPosCode="+posCode;
   	}
   	
   	
	</script>
</head>
<body>
	<table id="page_top_banner">
		<thead style="">
			<tr>
				<th style="width: 50%; text-align: left;font-weight: bold;font-size: 11px;text-transform: uppercase;padding-top: 5px;padding-bottom: 5px; FONT-FAMILY: trebuchet ms, Helvetica, sans-serif;">${companyName} &nbsp;-&nbsp; ${financialYear} &nbsp;-&nbsp; ${propertyName} &nbsp;-&nbsp; ${locationName} &nbsp;-&nbsp; &nbsp;&nbsp;POS Date-&nbsp;${POSDateForDisplay} </th>
				
				<th style="width: 50%; text-align: right;font-weight: bold;font-size: 12px;FONT-FAMILY: Arial, Helvetica, sans-serif;"> 
				<a href="frmWebPOSModuleSelection.html" ><img  src="../${pageContext.request.contextPath}/resources/images/ModuleSelection.png" title="POS Module Selection"   height="20px" width="20px"></a> &nbsp;&nbsp;
					&nbsp;&nbsp;<a href="#" style="text-decoration:underline ;color: white;" onclick="funPOSHome()"><img  src="../${pageContext.request.contextPath}/resources/images/home.png" title="HOME" height="20px" width="20px"></a>&nbsp;&nbsp; <label></label>&nbsp;&nbsp; 
					<a href="logout.html" style="text-decoration:underline;color: white;"><img  src="../${pageContext.request.contextPath}/resources/images/logout.png" title="LOGOUT" height="20px" width="20px" ></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</th>
			</tr>
		</thead>
	</table>
	<div id="MainDiv"
		style="background-color: #FFFFFF; 
		border: 1px solid #ccc; height: 238px; margin: auto;
		 overflow-x: hidden; overflow-y: scroll; width: 30%;
		 position: absolute; z-index: 1; right: 3.5%;">

		<table id="tblNotify"
			style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll;"
			class="transTablex">
			<tbody id="tbodyNotifyid">
			<tr><td colspan="4">Notifications</td></tr>
			<%-- <c:forEach items="${Notifcation}" var="draw1" varStatus="status1">
			<tr>
				<td>${draw1.strReqCode}</td>
				<td>${draw1.dtReqDate}</td>
				<td>${draw1.Locationby}</td>
				<td>${draw1.LocationOn}</td>
			</tr>
		</c:forEach> --%>
		</tbody>
		</table>
	</div>
</body>
</html>