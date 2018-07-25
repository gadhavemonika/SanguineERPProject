<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <style>
  #tblNotify tr:hover{
	background-color: #72BEFC;
	
}
</style>
<script type="text/javascript">

//var resFormName="";

var NotificationTimeinterval=parseInt('<%=session.getAttribute("NotificationTimeinterval").toString()%>');

var NotificationCount="";
$(document).ready(function(){
	//window.location.href=getContextPath()+"/loadPendingRequisition.html";
   		$("#MainDiv").hide();
   		  		
   	    $("#notification").click(function(){
   	        $("#MainDiv").fadeToggle();
   	    });
   	    
   	 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
	  var dte=pmsDate.split("-");
	  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);
	  funGetNotification()   
   	});
   	
<%-- <%if(session.getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")){%>
 	NotificationTimeinterval=parseInt(NotificationTimeinterval)*60000;
 	setInterval(function(){funGetNotification()},NotificationTimeinterval);
<%}%> --%>
   
   	
function funGetNotification()
{
	var searchUrl=getContextPath()+"/getCheckoutNotification.html";
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
        				funfillNotificationRow(i+1,item[0],item[1],item[2]);
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
function funfillNotificationRow(SrNo,strRoomNo,strRoomDesc,strGuestName) 
{
    var table = document.getElementById("tblNotify");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    
    row.insertCell(0).innerHTML= "<label>"+SrNo+"</label>";	    
    row.insertCell(1).innerHTML= "<label>"+strGuestName+"</label>";
    row.insertCell(2).innerHTML= "<label>"+strRoomNo+"</label>";
    row.insertCell(3).innerHTML= "<label>"+strRoomDesc+"</label>";
   
}
function funRemoveNotification()
{
	 $("#tblNotify").find("tr:gt(0)").remove();
}

function funHelpWindow(formName)
{
	var returnVal ="";
	
	
	//window.showModalDialog(getContextPath()+"/resources/jsp/WEB-INF/frmHelpModulWindow.jsp","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	//window.open(getContextPath()+"/WebRoot/WEB-INF/jsp/frmHelpModuleWindow","_blank");
	switch (formName)
	{
		case  "frmMIS" :
		
		//window.open('frmWebStockHelpMaterialIssueSlip.html',"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=900,height=400,left=400px");
		//returnVal=window.showModalDialog("frmWebStockHelpMaterialIssueSlip.html","","dialogHeight:600px;dialogWidth:500px;dialogLeft:350px;dialogTop:100px");
		window.open("frmWebStockHelpMaterialIssueSlip.html", "myhelp", "scrollbars=1,width=500,height=350");
		
		break;	
	
	
		case  "frmMaterialReq" :
			window.open("frmWebStockHelpMaterialRequisition.html", "myhelp", "scrollbars=1,width=500,height=350");
		
		//window.open('frmWebStockHelpMaterialIssueSlip.html',"mywindow","directories=yes,titlebar=yes,toolbar=yes,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=900,height=400,left=400px");
		//returnVal=window.showModalDialog("frmWebStockHelpMaterialIssueSlip.html","","dialogHeight:600px;dialogWidth:500px;dialogLeft:350px;dialogTop:100px");
		break;
		
		case "frmMaterialReturn":
			window.open("frmWebStockHelpMaterialReturn.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
				
		case "frmOpeningStock":
			window.open("frmWebStockHelpOpeningStock.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
		
		case "frmPhysicalStkPosting":
			window.open("frmWebStockHelpPhysicalStockPosting.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
			
		case "frmPurchaseIndent":
			window.open("frmWebStockHelpPurchaseIndent.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
				
		case "frmPurchaseOrder":
			window.open("frmWebStockHelpPurchaseOrder.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
			
			
		case "frmStockAdjustment":
			window.open("frmWebStockHelpStockAdjustment.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
					
		case "frmStockTransfer":
			window.open("frmWebStockHelpStockTransfer.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
			
		case "frmBillPassing":
			window.open("frmWebStockHelpBillPassing.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;

	 	case "frmWebStockHelpGRN":
			window.open("frmWebStockHelpGRN.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
			
	 	case "frmWebStockHelpGRNSlip":
			window.open("frmWebStockHelpGRNSlip.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
			
	 	case "frmWebStockHelpMaterialReturnSlip":
			window.open("frmWebStockHelpMaterialReturnSlip.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
			
	 	case "frmWebStockHelpMealPlanning":
			window.open("frmWebStockHelpMealPlanning.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
			
	 	case "frmWebStockHelpProductList":
			window.open("frmWebStockHelpProductList.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;	
			
	 	case "frmWebStockHelpProductWiseSupplierWise":
			window.open("frmWebStockHelpProductWiseSupplierWise.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;	
			
	 	case "frmWebStockHelpPurchaseOrderSlip":
			window.open("frmWebStockHelpPurchaseOrderSlip.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;		
		
	 	case "frmWebStockHelpPurchaseReturn":
			window.open("frmWebStockHelpPurchaseReturn.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;	

	 	case "frmWebStockHelpPurchaseReturnSlip":
			window.open("frmWebStockHelpPurchaseReturnSlip.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
		case "frmWebStockHelpRateContract":
			window.open("frmWebStockHelpRateContract.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
		case "frmWebStockHelpRequisitionSlip":
			window.open("frmWebStockHelpRequisitionSlip.html", "myhelp", "scrollbars=1,width=500,height=350");
			break;
	
			
			
// 		case "":
// 			window.open(".html", "myhelp", "scrollbars=1,width=500,height=350");
// 			break;
 	
	}
	
	
	
	
	
}


// function funcheck()
// {
// 	//getContextPath();
// 	//var formname = document.getElementById("lblFormHeadingName").innerHTML;
// 	funHelpWindow(formname);
// 	//alert("Help coming soon"+formname);
	
// }

function getContextPath() 
{
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}


function funGetFormName(){

	$.ajax({
		type : "GET",
		url : getContextPath()+ "/getFormName.html",
		success : function(response){ 

			if(response=='Invalid Code')
        	{
        		alert("Invalid FormName");
        		
        	}
        	else
        	{      
        		//resFormName=response;
        		funHelpWindow(response)
        		
        	}
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

	


</script>
</head>
<body>
	<table id="page_top_banner">
		<thead style="">
			<tr>
				<th style="width: 50%; text-align: left;font-weight: bold;font-size: 11px;text-transform: uppercase;padding-top: 5px;padding-bottom: 5px; FONT-FAMILY: trebuchet ms, Helvetica, sans-serif;">${companyName} &nbsp;-&nbsp; ${financialYear} &nbsp;-&nbsp; ${propertyName} &nbsp;-&nbsp;PMS Date- ${PMSDate}</th>
				<th id="notification" style="width: 2%;font-weight: bold;font-size: 11px;right: 110px;position: absolute;padding-top: 2px;">
					<div style=" background-color: #A33519; margin-left: 19px;margin-top: -5px; position: absolute;text-align: center;width: 15px;">
					<label id="lblNotifyCount"></label>
					</div>
					<img  src="../${pageContext.request.contextPath}/resources/images/Notification.png" title="Notification" height="20px" width="20px">
					
				</th>
				<th style="width: 50%; text-align: right;font-weight: bold;font-size: 12px;FONT-FAMILY: Arial, Helvetica, sans-serif;"> 
				<img s src="../${pageContext.request.contextPath}/resources/images/help.png" onclick="funGetFormName()" title="HELP" height="20px" width="20px"> &nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="frmHome.html" style="text-decoration:underline ;color: white;"><img  src="../${pageContext.request.contextPath}/resources/images/home.png" title="HOME" height="20px" width="20px"></a>
					&nbsp;&nbsp;
					<a href="frmChangeModuleSelection.html" style="text-decoration:underline ;color: white;"><img  src="../${pageContext.request.contextPath}/resources/images/ModuleSelection.png" title="Change Module" height="20px" width="20px"></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="logout.html" style="text-decoration:underline;color: white;"><img  src="../${pageContext.request.contextPath}/resources/images/logout.png" title="LOGOUT" height="20px" width="20px" ></a>
					&nbsp;&nbsp;&nbsp;
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