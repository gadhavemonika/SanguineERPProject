<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script>


	function funValidateFields()
	{
		var flag=false;
			flag=true;
			
			var fromDate=$("#dteFromDate").val();
			var toDate=$("#dteToDate").val();
			
			var fd=fromDate.split("-")[0];
			var fm=fromDate.split("-")[1];
			var fy=fromDate.split("-")[2];
			
			var td=toDate.split("-")[0];
			var tm=toDate.split("-")[1];
			var ty=toDate.split("-")[2];
			
			$("#dteFromDate").val(fy+"-"+fm+"-"+fd);
			$("#dteToDate").val(ty+"-"+tm+"-"+td);
			
		
			
			window.open(getContextPath()+"/frmNoShowReportList.html?fromDate="+fy+"-"+fm+"-"+fd+"&toDate="+ty+"-"+tm+"-"+td+" ");
		
		return flag;
	}


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
			$("#btnExecute").click(function( event )
			{
			funCalculateStockFlashForSummary();
			});
		});
		
		function funCalculateStockFlashForSummary()
		{
			var fromDate=$("#dteFromDate").val();
			var toDate=$("#dteToDate").val();
			
			var fd=fromDate.split("-")[0];
			var fm=fromDate.split("-")[1];
			var fy=fromDate.split("-")[2];
			
			var td=toDate.split("-")[0];
			var tm=toDate.split("-")[1];
			var ty=toDate.split("-")[2];
			
// 			$("#dteFromDate").val(fy+"-"+fm+"-"+fd);
// 			$("#dteToDate").val(ty+"-"+tm+"-"+td);
			var searchUrl=getContextPath()+"/frmNoShowReportList.html?fromDate="+fy+"-"+fm+"-"+fd+"&toDate="+ty+"-"+tm+"-"+td;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {				    	
				    	StkFlashData=response[0];
				    	showTable();
// 				    	funGetTotalValue(response[1]/currValue);
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
		
		function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(StkFlashData.length, optInit);	
		    $("#divValueTotal").show();
		    
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
		    var max_elem = Math.min((page_index+1) * items_per_page, StkFlashData.length);
		    var newcontent="";
			var currValue='<%=session.getAttribute("currValue").toString()%>';
    		if(currValue==null)
    			{
    				currValue=1;
    			}	
		
		   
			   	newcontent = '<table id="tblNoShow" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labls1">Guest Name</td><td id="labls2">Reservation No</td><td id="labls3">No Of Rooms</td><td id="labls4">Payment</td></tr>';
					   
			    // Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<tr><td>'+StkFlashData[i].strGuestName+'</td>';
			        
			        newcontent += '<td>'+StkFlashData[i].strReservationNo+'</td>';
			        newcontent += '<td>'+StkFlashData[i].strNoOfRooms+'</td>';
			       newcontent += '<td align="right">'+parseFloat((StkFlashData[i].dblPayment/currValue)).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				   
			      
			    }
		    
		   
		    
		    newcontent += '</table>';
		    // Replace old content with new content
		   
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
		
		$(document).ready(function () 
				{			 
					$("#btnExport").click(function (e)
					{
						
						var fromDate=$("#dteFromDate").val();
						var toDate=$("#dteToDate").val();
						
						var rowCount = document.getElementById('tblNoShow').rows.length;
						if(rowCount>1)
						{
						window.location.href=getContextPath()+"/downloadNoShowFlashExcel.html?fromDate="+fromDate+"&toDate="+toDate;
						}
						else
						{
						alert("No Data Present For Selected Date");
						}	
				});
					
				});			
				
		
		
</script>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>No Show Report </label>
	</div>
	<s:form name="frmNoShowReport" method="GET" action="" >
		
	<table class="masterTable">
		<br/><br/>
			<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
			<tr>
					<td><input id="btnExecute" type="button" class="form_button1" value="EXECUTE"/></td>
					
					<td>
						<s:select path="strExportType" id="cmbExportType"  cssClass="BoxW124px">
							<option value="Excel">Excel</option>
							
						</s:select>
					</td>
					<td colspan="7">						
						<input id="btnExport" type="button" value="EXPORT"  class="form_button1"/>
					</td>
				</tr>
		</table>
		<br>
		<br>
		<dl id="Searchresult" style="width: 95%; margin-left: 26px; overflow:auto;"></dl>
		<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
		
		</div>
			<div id="divNoShow">
		<table id="tblNoShow" class="transTablex" style="width: 95%;font-size:11px;font-weight: bold;">
		<tr style="margin-left: 28px">
			<td id="labld26" width="80%" align="right">Total Value</td>
			<td id="tdTotValue" width="10%" align="right">
			<input id="txtTotValue" style="width: 100%;text-align: right;" class="Box"></input></td>
			<td width="10%" align="right"></td>
			</tr>
		</table>
		</div>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	
	</s:form>

</body>
</html>