<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%-- <link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>"> --%>
<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
        <!-- Load data to paginate -->
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Guest Report</title>
</head>

<script type="text/javascript">
var fieldName;
var guestdata;
//set date
$(document).ready(function(){
	
	var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
	
	$("#dteFromDate").datepicker({
		dateFormat : 'dd-mm-yy'
	});
	$("#dteFromDate").datepicker('setDate', pmsDate);
	
	
	$("#dteToDate").datepicker({
		dateFormat : 'dd-mm-yy'
	});
	$("#dteToDate").datepicker('setDate', pmsDate);

});

function funHelp(transactionName)
{
	fieldName=transactionName;
	window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
}
	
function funSetData(code){
	$("#txtGuestCode").val(code);
}

function showTable(response)
{
	guestdata=response;
	var optInit = getOptionsFromForm();
   $("#Pagination").pagination(guestdata.length, optInit);	
    
    
}
var items_per_page = 10;
function getOptionsFromForm()
{
    var opt = {callback: pageselectCallback};
	opt['items_per_page'] = items_per_page;
	opt['num_display_entries'] = 10;
	opt['num_edge_entries'] = 3;
	opt['prev_text'] = "Prev";
	opt['next_text'] = "Next";
    return opt;
}
function pageselectCallback(page_index, jq)
{
    // Get number of elements per pagionation page from form
    var max_elem = Math.min((page_index+1) * items_per_page, guestdata.length);
    var newcontent="";
	
    		    	
	   	newcontent = '<table id="tblGuestHistory" class="transTablex" style="width: 100%;font-width:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" width="12%">Guest Name</td><td id="labld2" width="7%"> Bill No</td><td id="labld3" width="7%"> Type</td>	<td id="labld4" width="8%">Check In Date</td><td id="labld5"  width="8%"> Check Out Date</td><td id="labld6" width="5%"> Room No</td><td id="labld7" width="2%">Extra Bed</td><td id="labld7" width="2%">No of Days Stayed</td><td id="labld7" width="2%">No Of Adults</td><td id="labld7" width="2%">No Of Child</td><td id="labld8" width="7%"> Bill Amt</td><td id="labld9" width="5%">Payment Type</td></tr>';
	   	// Iterate through a selection of the content and build an HTML string
	    for(var i=page_index*items_per_page;i<max_elem;i++)
	    {
	        newcontent += '<tr><td>'+guestdata[i].strGuestName+'</td>';
//Room No<td id="labld7" width="2%">No Of Child</td><td id="labld8" width="7%"> Bill Amt</td><td id="labld9" width="5%">Payment Type</td></tr>	        
	        newcontent += '<td><a id="strBillNo.'+i+'" href="#" onclick="funClick(this);">'+guestdata[i].strBillNo+'</a></td>';
	        newcontent += '<td>'+guestdata[i].strEntryType+'</td>';
	        newcontent += '<td>'+guestdata[i].dteCheckIn+'</td>';
	        newcontent += '<td>'+guestdata[i].dteCheckOut+'</td>';
	        newcontent += '<td>'+guestdata[i].strRoomNo+'</td>';
	        newcontent += '<td>'+guestdata[i].strExtraBed+'</td>';
	        newcontent += '<td align="right">'+guestdata[i].dblNoOfdaysStayed+'</td>';
	        newcontent += '<td align="right">'+guestdata[i].intNoOfAdults+'</td>';
	        newcontent += '<td align="right">'+guestdata[i].intNoOfChild+'</td>';
	        newcontent += '<td align="right">'+guestdata[i].dblbillAmt+'</td>';
	    	newcontent += '<td>'+guestdata[i].strPaymentType+'</td></tr>';
		    
	    	
	    }			   
     
    newcontent += '</table>';
    // Replace old content with new content
   
    $('#Searchresult').html(newcontent);
   
    // Prevent click eventpropagation
    return false;
}
		
/* */

function funExecuteReport()
{
	var guestCode=$("#txtGuestCode").val();
	var dteFromDate=document.getElementById("dteFromDate").value;
	var dteToDate=document.getElementById("dteToDate").value;
	
	var searchurl=getContextPath()+"/loadGuestHistoryReport.html?guestCode="+guestCode+"&dteFromDate="+dteFromDate+"&dteToDate="+dteToDate;
	$.ajax({
        type: "GET",
	    url: searchurl,
    	dataType: "json",
    
    	success: function (response) {
    		showTable(response);
    	},
        error: function(jqXHR, exception)
   		 {
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

	function funClick(obj)
	{
		var billNo=document.getElementById(""+obj.id+"").innerHTML;
		var dteFromDate=document.getElementById("dteFromDate").value;
		var dteToDate=document.getElementById("dteToDate").value;
		var against='Bill';
		if(against=='Bill')
		{
			window.open(getContextPath()+"/rptBillPrinting.html?fromDate="+dteFromDate+"&toDate="+dteToDate+"&billNo="+billNo+"");
		}else{
			window.open(getContextPath()+"/rptBillPrintingForCheckIn.html?fromDate="+dteFromDate+"&toDate="+dteToDate+"&checkInNo="+billNo+"&against="+against+ "");
	    } 
	}	
	
	function funExportData(){
		var guestCode=$("#txtGuestCode").val();
		var dteFromDate=document.getElementById("dteFromDate").value;
		var dteToDate=document.getElementById("dteToDate").value;
		
		window.location.href=getContextPath()+"/exportGuestHistoryExcel.html?guestCode="+guestCode+"&dteFromDate="+dteFromDate+"&dteToDate="+dteToDate;
		
		
	}
	
		    
</script>

<body>
	<div id="formHeading">
		<label>Guest History Report </label>
	</div>
			<br/>
			<br/>
			<br/>
	<s:form name="GuestReport" method="GET" action="rptGuestHistoryReport.html" target="_blank"> 
	

		<table class="transTable">
			<tr>
				<td><label> Guest Code</label></td>
				<td><input id="txtGuestCode" path="strGuestCode" ondblclick="funHelp('guestCode');" class="searchTextBox" />
				 <td colspan="2"></td>
			</tr>
				
			<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteToDate" path="dteTodate" required="true" class="calenderTextBox" /></td>	
					
			</tr>
			<tr>
			<td colspan="4"></td>
			</tr>
			<tr>
			<td colspan="3">
				</td>	
				<td><input type="button" value="EXECUTE" tabindex="3" class="form_button" onclick=" funExecuteReport()" />
				&nbsp; &nbsp; &nbsp; 
				 <input type="button" value="EXPORT"
					class="form_button" onclick="funExportData();" /> 
				</td>
			</tr>
						
			</table>
			<dl id="Searchresult" style="width: 95%; margin-left: 26px; overflow:auto;"></dl>
			<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
			
			</div>
			<br/>
			<br/>
			<br/>
			<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
			</s:form>
			
			

</body>
</html>