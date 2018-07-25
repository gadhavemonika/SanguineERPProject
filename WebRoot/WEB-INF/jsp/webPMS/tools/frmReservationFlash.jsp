<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
        <!-- Load data to paginate -->
	<link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>" />

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript">

 		var reservFlashData;
 		var loggedInProperty="";
 		var loggedInLocation="";
 		$(document).ready(function() 
 		{ 
 			  
 			var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
 			
 			$( "#txtArriveFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
 			$("#txtArriveFromDate" ).datepicker('setDate', pmsDate);
 			
 			$( "#txtArriveToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
 			$("#txtArriveToDate" ).datepicker('setDate', pmsDate);
 			
 			$( "#txtDepFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
 			$("#txtDepFromDate" ).datepicker('setDate', pmsDate);
 			
 			$("#txtdepToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
 			$("#txtdepToDate" ).datepicker('setDate', pmsDate);
 			
		});	
 		

	
 
	
	 	function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(reservFlashData.length, optInit);	
		   // $("#divValueTotal").show();
		    
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
		
		$(document).ready(function() 
		{
			$(document).ajaxStart(function()
			{
			    $("#wait").css("display","block");
			});
			$(document).ajaxComplete(function()
			{
				$("#wait").css("display","none");
			});
		});
		
		
		
		function funClick(obj)
		{
			/*  var prodCode=document.getElementById(""+obj.id+"").innerHTML;
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var param1=prodCode+","+locCode+","+propCode;
			window.open(getContextPath()+"/frmStockLedgerFromStockFlash.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate);  */
		}
		
		function pageselectCallback(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, reservFlashData.length);
		    var newcontent='<table id="tblResevFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labls2">Reservation</td><td id="labls3">Arrive</td><td id="labls13">Departure</td><td id="labls14">Guest</td><td id="labls14">RoomNo</td><td id="labls14">Source</td><td id="labls14">Corporate</td><td id="labls14">Deposit</td><td id="labls14">User</td><td id="labls14">Res.Type</td><td style="width: 10%;">Room Type</td></tr>';
				   
			    // Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<td><a id="stkLedgerUrl.'+i+'"  onclick="funClick(this);">'+reservFlashData[i].strResvervationCode+'</a></td>';
			        newcontent += '<td>'+reservFlashData[i].dteArriveFromDate+'</td>';
			        newcontent += '<td>'+reservFlashData[i].dteDepFromDate+'</td>';
			        newcontent += '<td>'+reservFlashData[i].strGuestName+'</td>';
			        newcontent += '<td>'+reservFlashData[i].strRoomNo+'</td>';
			        newcontent += '<td>'+reservFlashData[i].strSource+'</td>';
			        newcontent += '<td>'+reservFlashData[i].strCoparate+'</td>';
			        newcontent += '<td align="right">'+reservFlashData[i].dblPaidAmt+'</td>';
			        newcontent += '<td>'+reservFlashData[i].struser+'</td>';
			        newcontent += '<td>'+reservFlashData[i].strReservationTypeCode+'</td>';

			        newcontent += '<td>'+reservFlashData[i].strRoomTypeDesc+'</td><tr>';
			    }
	
		    
		    newcontent += '</table>';
		    // Replace old content with new content
		   
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
				
		
		function funGetRevservationFlash()
		{
			var propCode = $("#cmbPropertyCode").val();
			var bookingType = $("#cmbBookingCode").val();
			var roomType = $("#cmbRoomsType").val();
			
			var fArrDate =$( "#txtArriveFromDate" ).val();
			var tArrDate =$( "#txtArriveToDate" ).val();
 			
 			var fDepDate =$( "#txtDepFromDate" ).val();
 			var tDepDate =$("#txtdepToDate" ).val();
 		
			var param1=propCode+","+bookingType+","+roomType+","+fArrDate+","+tArrDate+","+fDepDate+","+tDepDate;
			var searchUrl=getContextPath()+"/funGetRevservationFlash.html?param1="+param1;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {				    	
				    	reservFlashData=response[0];
				    	showTable();
				    	
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
		
		
		
	function funExportData()
	{
				var propCode = $("#cmbPropertyCode").val();
				var bookingType = $("#cmbBookingCode").val();
				var roomType = $("#cmbRoomsType").val();
				
				var fArrDate =$( "#txtArriveFromDate" ).val();
				var tArrDate =$( "#txtArriveToDate" ).val();
	 			
	 			var fDepDate =$( "#txtDepFromDate" ).val();
	 			var tDepDate =$("#txtdepToDate" ).val();
	 		
				var param1=propCode+","+bookingType+","+roomType+","+fArrDate+","+tArrDate+","+fDepDate+","+tDepDate;
				window.location.href=getContextPath()+"/funExpoetReverationSheet.html?param1="+param1;
			
	}
		  
		 
		 
	</script>
</head>
<body onload="funOnLoad();">
<div id="formHeading">
		<label>Reservation Flash</label>
	</div>
	<s:form action="frmReservationFlashReport.html" method="GET" name="frmRevFlash" target="_blank">
		<br>
	
			<table class="transTable">
			<tr><th colspan="10"></th></tr>
				<tr>
					<td width="10%">Property </td>
					<td width="20%">
						<s:select id="cmbPropertyCode" path="strPropertyCode" items="${hmProperty}" cssClass="longTextBox" cssStyle="width:100%" >
			    			</s:select>
					</td>
						
					<td width="15%"><label>Booking Type</label></td>
					<td>
						<s:select id="cmbBookingCode" path="strBookingCode" items="${hmBooking}"  cssClass="longTextBox" cssStyle="width:180px;" >
			    			</s:select>
			    	
					</td>
					<td><label>Rooms Type</label></td>
					<td><s:select id="cmbRoomsType" path="strRoomTypeCode"  items="${hmRoomType}"  cssClass="BoxW124px">
					</s:select></td>
					
				</tr>
				
				<tr>
				    <td><label id="lblFromArrDate">From Arrive Date</label></td>
			        <td>
			            <s:input id="txtArriveFromDate" name="dteArriveFromDate" path="dteArriveFromDate" cssClass="calenderTextBox"/>
			        	
			        </td>
				       
			        <td><label id="lblToArrDate">To Arrive Date</label></td>
			        <td colspan="3">
			            <s:input id="txtArriveToDate" name="dteArriveToDate" path="dteArriveToDate" cssClass="calenderTextBox"/>
			        
			        </td>
			       
				</tr>
				
				<tr>
				    <td><label id="lblFromDepDate">From Depature Date</label></td>
			        <td>
			            <s:input id="txtDepFromDate" name="dteDepFromDate" path="dteDepFromDate" cssClass="calenderTextBox"/>
			        	
			        </td> 
				       
			        <td><label id="lblToDepDate">To Depature Date</label></td>
			        <td  colspan="3">
			            <s:input id="txtdepToDate" name="dtedepToDate" path="dtedepToDate" cssClass="calenderTextBox"/>
			        	
			        </td>
			       
				</tr>
				<tr>
				</tr>
				
				<tr>
			        <td><input id="btnExecute" type="button" class="form_button" value="EXECUTE" onclick="funGetRevservationFlash()"/></td>
			        <td><input id="btnExport" type="button" class="form_button" value="EXPORT" onclick="funExportData()"/>
			        </td>
			        
			    </tr>    
			
			</table>
			
			<dl id="Searchresult" style="width: 95%; margin-left: 26px; overflow:auto;"></dl>
			<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
		
		</div>
		<br>
		<br>
	
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	
</body>
</html>