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
	</script>
</head>
<body>
	<table id="page_top_banner">
		<thead style="">
			<tr>
				<th style="width: 50%; text-align: left;font-weight: bold;font-size: 11px;text-transform: uppercase;padding-top: 5px;padding-bottom: 5px; FONT-FAMILY: trebuchet ms, Helvetica, sans-serif;">${companyName} &nbsp;-&nbsp; ${financialYear} &nbsp;-&nbsp; ${propertyName} &nbsp;-&nbsp; ${locationName}</th>
				 <th style="width: 34%;"></th>
				 <th id="notification" style="width: 4%;font-weight: bold;font-size: 11px; padding-left: 23px;padding-bottom: 8px;">
					<div style=" background-color: #A33519; margin-left: 18px;margin-top: -5px; position: absolute;text-align: center;width: 15px;">
					<label id="lblNotifyCount"></label>
					</div>
					<img  src="../${pageContext.request.contextPath}/resources/images/Notification.png" title="Notification" height="20px" width="20px">
					
				</th>
				<th style="width: 4%;padding-bottom: 6px;padding-left: 15px;"> 
				<img  src="../${pageContext.request.contextPath}/resources/images/help.png" onclick="funGetFormName()" title="HELP" height="20px" width="20px"> &nbsp;&nbsp;
				</th>
				
				<th style="width: 4%;padding-bottom: 8px;"><a href="frmHome.html"  style="text-decoration:underline ;color: white;"><img  src="../${pageContext.request.contextPath}/resources/images/home.png" title="HOME" height="20px" width="20px"></a>
				</th>
				<th style="width: 4%;padding-bottom: 8px;"><a href="frmChangeModuleSelection.html" style="text-decoration:underline ;color: white; padding-bottom: 16px;"><img  src="../${pageContext.request.contextPath}/resources/images/ModuleSelection.png" title="Change Module" height="20px" width="20px"></a>
				</th >
				<th style="width: 4%;padding-bottom: 8px;"><a href="logout.html" style="text-decoration:underline;color: white; padding-bottom: 16px;"><img  src="../${pageContext.request.contextPath}/resources/images/logout.png" title="LOGOUT" height="20px" width="20px" ></a>
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