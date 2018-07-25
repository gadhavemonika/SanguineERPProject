<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script type="text/javascript">

	var fieldName,listRow=0;
	
	  $(document).ready(function(){
		    
		  $(".tab_content").hide();
			$(".tab_content:first").show();

			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();
				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
			
		  $("#txtNoOfAdults").val(1);
		  $("#txtNoOfBookingRoom").val(1);
		  
<%-- 		  var pmsDate='<%=session.getAttribute("PMSDate").toString()%>'; --%>
// 		  var dte=pmsDate.split("-");
// 		  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);
	   });

	$(function() 
	{
		//var arrivalTime=session.getAttribute("PMSCheckInTime");
		//var departureTime=session.getAttribute("PMSCheckOutTime");
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		$('#txtArrivalTime').timepicker();
		$('#txtDepartureTime').timepicker();
		
		$('#txtArrivalTime').timepicker({
		        'timeFormat':'H:i:s'
		});
		$('#txtDepartureTime').timepicker({
		        'timeFormat':'H:i:s'
		});
		
		
		
		$('#txtArrivalTime').timepicker('setTime', new Date());
		$('#txtDepartureTime').timepicker('setTime', new Date());
		
		
		$('#txtPickUpTime').timepicker();
		$('#txtDropTime').timepicker();
		
		$('#txtPickUpTime').timepicker({
		        'timeFormat':'H:i:s'
		});
		$('#txtDropTime').timepicker({
		        'timeFormat':'H:i:s'
		});
		
		$('#txtPickUpTime').timepicker('setTime', new Date());
		$('#txtDropTime').timepicker('setTime', new Date());
		
		  
		
// 		$( "#txtArrivalDate" ).datepicker({
// 			minDate: 0,
// 			 dateFormat: 'dd-mm-yy' 
// 		});
		
	
		$("#txtArrivalDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtArrivalDate").datepicker('setDate', pmsDate);
		
// 		$( "#txtDepartureDate" ).datepicker({
// 			minDate: 0,
// 			 dateFormat: 'dd-mm-yy' 
// 		});
		
		$("#txtDepartureDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtDepartureDate").datepicker('setDate', pmsDate);
	

		
// 		$("#txtArrivalDate").datepicker({ dateFormat: 'dd-mm-yy' });
// 		$("#txtArrivalDate").datepicker('setDate', pmsDate);
		
// 		$("#txtDepartureDate").datepicker({ dateFormat: 'dd-mm-yy' });
// 		$("#txtDepartureDate").datepicker('setDate', pmsDate);
		
		$("#txtCancelDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtCancelDate").datepicker('setDate', pmsDate);
		
		$("#txtConfirmDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtConfirmDate").datepicker('setDate', pmsDate);
		
		
		$('a#baseUrl').click(function() 
		{
			if($("#txtReservationNo").val().trim()=="")
			{
				alert("Please Select Reservation No ");
				return false;
			}
			window.open('attachDoc.html?transName=frmReservation.jsp&formName=Reservation &code='+$('#txtReservationNo').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		});	
		
		
		
		var message='';
		var retval="";
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%> alert("Data Save successfully\n\n"+message);
				var AdvAmount='';
				var isOk=confirm("Do You Want to pay Advance Amount ?");
				if(isOk)
 				{
					var checkAgainst="Reservation";
					AdvAmount='<%=session.getAttribute("AdvanceAmount").toString()%>';
	    			window.location.href=getContextPath()+"/frmPMSPaymentAdvanceAmount.html?AdvAmount="+AdvAmount ;
	    			session.removeAttribute("AdvanceAmount");
	    			
 				}<%
			}
		}%>
		
		var resNo='';
		<%if (session.getAttribute("ResNo") != null) 
		{
			%>
			resNo='<%=session.getAttribute("ResNo").toString()%>';
			funSetReservationNo(resNo);
		    <%
		    session.removeAttribute("ResNo");
		}%>
	});

	
	function funSetData(code){

		switch(fieldName){

			case 'ReservationNo' : 
				funSetReservationNo(code);
				break;
				
			case 'PropertyCode' : 
				funSetPropertyCode(code);
				break;
				
			case 'guestCode' : 
				funSetGuestCode(code);
				break;
				
			case 'CorporateCode' : 
				funSetCorporateCode(code);
				break;
				
			case 'BookingTypeCode' : 
				funSetBookingTypeCode(code);
				break;
				
			case 'BillingInstCode' : 
				funSetBillingInstCode(code);
				break;
				
			case 'BookerCode' : 
				funSetBookerCode(code);
				break;
				
			case 'business' : 
				funSetBusinessSourceCode(code);
				break;
				
			case 'AgentCode' : 
				funSetAgentCode(code);
				break;
				
			case 'roomCode' : 
				funSetRoomNo(code);
				break;
			
			case 'extraBed' : 
				funSetExtraBed(code);
				break;
				
			case 'marketsource' : 
				funSetMarketSourceCode(code);
			break;
			
			case "incomeHead":
				funSetIncomeHead(code);
			break;
			
			case "roomType":
				funSetRoomType(code);
			break;
			
			case "package":
				funSetPackageNo(code);
			break;
			
		}
	}

	
	
	
	function funSetReservationNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadReservation.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strReservationNo!="Invalid")
		    	{
		    		funFillHdData(response);
		    	}
		    	else
			    {
			    	alert("Invalid Reservation No");
			    	$("#txtReservationNo").val("");
			    	$("#txtReservationNo").focus();
			    	return false;
			    }
			},
			error : function(e){
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

	
	function funSetPropertyCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadPropertyCode.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				
			},
			error : function(e){
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

	
	function funSetGuestCode(code){
			
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadGuestCode.html?guestCode=" + code,
			dataType : "json",
			success : function(response){ 
				funSetGuestInfo(response);
			},
			error : function(e){
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

	
	function funSetCorporateCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadCorporateCode.html?corpcode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strCorporateCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtCorporateCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCorporateCode").val(response.strCorporateCode);
	        		$("#lblCorporateDesc").text(response.strCorporateDesc);
	        	}
			},
			error : function(e){
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

	
	function funSetBookingTypeCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadBookingTypeCode.html?bookingType=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strBookingTypeCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtBookingTypeCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBookingTypeCode").val(response.strBookingTypeCode);
	        	    $("#lblBookingTypeDesc").text(response.strBookingTypeDesc);
	        	}
			},
			error : function(e){
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


	function funSetBillingInstCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadBillingInstCode.html?billingInstructions=" + code,
			dataType : "json",
			success : function(response){
				$("#txtBillingInstCode").val(response.strBillingInstCode);
        	    $("#lblBillingInstDesc").text(response.strBillingInstDesc);
			},
			error : function(e){
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

	
	function funSetBookerCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadBookerCode.html?bookerCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strBookerCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtBookerCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBookerCode").val(response.strBookerCode);
	        		$("#lblBookerName").text(response.strBookerName);
	        	}
			},
			error : function(e){
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

	
	function funSetBusinessSourceCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadBusinessMasterData.html?businessCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strBusinessSourceCode=='Invalid Code')
	        	{
	        		alert("Invalid Business Code");
	        		$("#txtBusinessSourceCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBusinessSourceCode").val(response.strBusinessSourceCode);
		        	$("#lblBusinessSourceName").text(response.strDescription);
	        	}
			},
			error : function(e){
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

	
	function funSetAgentCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAgentCode.html?agentCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAgentCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtAgentCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtAgentCode").val(response.strAgentCode);
	        		$("#lblAgentName").text(response.strDescription);
	        	}
			},
			error : function(e){
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

	
	
	function funSetRoomType(code){
		$("#txtRoomTypeCode").val(code);
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadRoomTypeMasterData.html?roomCode=" + code,
			dataType : "json",
		    async:false,
			success : function(response){ 
				if(response.strAgentCode=='Invalid Code')
	        	{
	        		alert("Invalid Room Type");
	        		$("#lblRoomType").text('');
	        	}
	        	else
	        	{					        	    	        		
	        		$("#lblRoomType").text(response.strRoomTypeDesc);
	        	}
			},
			error : function(e){
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
	
	
	
	function funSetRoomNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadRoomMasterData.html?roomCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAgentCode=='Invalid Code')
	        	{
	        		alert("Invalid Room No");
	        		$("#txtRoomNo").val('');
	        	}
	        	else
	        	{
	        		$("#txtRoomNo").val(response.strRoomCode);
	        		$("#lblRoomNo").text(response.strRoomDesc);
	        		funSetRoomType(response.strRoomTypeCode);
	        	}
			},
			error : function(e){
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
	
	
	
	function funSetExtraBed(code)
	{
		$("#txtExtraBed").val(code);
		var searchurl=getContextPath()+"/loadExtraBedMasterData.html?extraBedCode="+code;
		 $.ajax({
			    type: "GET",
			    url: searchurl,
			    dataType: "json",
			    success: function(response)
			    {
			        if(response.strExtraBedTypeCode=='Invalid Code')
			        {
			        	alert("Invalid ExtraBed Code");
			        	$("#txtExtraBed").val('');
			        }
			        else
			        {
				        $("#lblExtraBed").text(response.strExtraBedTypeDesc);
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
	
	function funSetPackageNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadPackageMaster.html?docCode=" + code,
			dataType : "json",
			success : function(response)
			{ 
				if(response.strPackageCode!="Invalid")
		    	{
					$("#txtPackageCode").val(response.strPackageCode);
					var table = document.getElementById("tblResDetails");
				    var rowCount = table.rows.length;
					if(rowCount==0)
					{
						alert("Please Enter Guest For Reservation");
					}
					else
					{
						$("#txtPackageName").val(response.strPackageName);
						$.each(response.listPackageDtl, function(i,item)
						{
							funAddIncomeHeadRow(item.strIncomeHeadCode.split('#')[0],item.strIncomeHeadCode.split('#')[1],item.dblAmt);		
						});
					}
		    	}
		    	else
			    {
			    	alert("Invalid Package No");
			    	$("#txtPackageCode").val(code);
			    	$("#txtPackageCode").focus();
			    	return false;
			    }
				
			},
			error : function(e){
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
	
	
	
	function funSetGuestInfo(obj)
	{
		$("#txtGuestCode").val(obj.strGuestCode);
		$("#txtMobileNo").val(obj.lngMobileNo);
		$("#txtGFirstName").val(obj.strFirstName);
		$("#txtGMiddleName").val(obj.strMiddleName);
		$("#txtGLastName").val(obj.strLastName);
		$("#txtAddress").val(obj.strAddress);
		
	}
	
	
	
	function funFillHdData(response)
	{
		$("#txtReservationNo").val(response.strReservationNo);
		$("#txtPropertyCode").val(response.strPropertyCode);
		
		$("#txtGuestCode").val(response.strGuestCode);
		$("#txtGuestPrefix").val(response.strGuestPrefix);
		$("#txtGFirstName").val(response.strFirstName);
		$("#txtGMiddleName").val(response.strMiddleName);
		$("#txtGLastName").val(response.strLastName);
		$("#txtAddress").val(response.strAddress);
		
		
		if(response.strCorporateCode!='')
		{
			$("#txtCorporateCode").val(response.strCorporateCode);
			funSetCorporateCode(response.strCorporateCode);
		}
		
		if(response.strBookingTypeCode!='')
		{
			$("#txtBookingTypeCode").val(response.strBookingTypeCode);
			funSetBookingTypeCode(response.strBookingTypeCode);
		}
		
		if(response.strBillingInstCode!='')
		{
			$("#txtBillingInstCode").val(response.strBillingInstCode);
			funSetBillingInstCode(response.strBillingInstCode);
		}
		
		if(response.strBookerCode!='')
		{
			$("#txtBookerCode").val(response.strBookerCode);
			funSetBookerCode(response.strBookerCode);
		}
		
		if(response.strBusinessSourceCode!='')
		{
			$("#txtBusinessSourceCode").val(response.strBusinessSourceCode);
			funSetBusinessSourceCode(response.strBusinessSourceCode);
		}
		
		if(response.strAgentCode!='')
		{
			$("#txtAgentCode").val(response.strAgentCode);
			funSetAgentCode(response.strAgentCode);
		}
		
		$("#txtRoomNo").val(response.strRoomNo);
		$("#lblRoomNo").text(response.strRoomDesc);
	    $("#txtExtraBed").val(response.strExtraBedCode);
	    $("#lblExtraBed").text(response.strExtraBedDesc);	    
	    $("#txtNoOfAdults").val(response.intNoOfAdults);
	    $("#txtNoOfChild").val(response.intNoOfChild);
				
		$("#txtArrivalDate").val(response.dteArrivalDate);
		$("#txtDepartureDate").val(response.dteDepartureDate);

		$("#txtArrivalTime").val(response.tmeArrivalTime);
		$("#txtDepartureTime").val(response.tmeDepartureTime);
		$("#txtNoOfNights").val(response.intNoOfNights);
		$("#txtNoOfBookingRoom").val(response.intNoRoomsBooked);
		$("#txtContactPerson").val(response.strContactPerson);
		$("#txtEmailId").val(response.strEmailId);
		$("#txtMobileNo").val(response.lngMobileNo);
		
		$("#txtRemarks").val(response.strRemarks);
		$("#txtCancelDate").val(response.dteCancelDate);
		$("#txtConfirmDate").val(response.dteConfirmDate);
		$("#txtOTANo").text(response.strOTANo);
		
		$("#txtPickUpTime").val(response.tmePickUpTime);
		$("#txtDropTime").text(response.tmeDropTime);
		
		$("#txtPackageCode").val(response.strPackageCode);
		$("#txtPackageName").val(response.strPackageName);
		      
		$("#hidIncomeHead").val("");
		funRemoveProductRowsForIncomeHead();
		funRemoveTariffRows();
		funFillDtlGrid(response.listReservationDetailsBean);
		funAddRommRateDtlOnReservationSelect(response.listReservationRoomRateDtl);
		funGetPreviouslyLoadedPkgList(response.listRoomPackageDtl);
		/*var incomeHeadCode =response.strIncomeHeadCode;
		if(incomeHeadCode!='')
		{
		var incomeHead = incomeHeadCode.split(',');
		for(var i=0; i<incomeHead.length; i++)
		{
		funSetIncomeHead(incomeHead[i]);
		}
		}
		*/
	}
	
	function funAddRommRateDtlOnReservationSelect(dataList)
	{
		 var table=document.getElementById("tblRommRate");
		 
		 for(var i=0;i<dataList.length;i++ )
	     {
			 var rowCount=table.rows.length;
			 var row=table.insertRow();
			 var list=dataList[i];
			 var date=list.dtDate;
	// 		 var roomDesc = funGetRoomDescOnLoad(list.strRoomNo);
		     var roomtypeDesc=funSetRoomType(list.strRoomType);
			 var roomtypeDesc=$("#lblRoomType").text();
			 $("#lblRoomType").text("");
			 var dateSplit = date.split("-");
			 date=dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
			 row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-center: 5px;width:50%;\" name=\"listReservationRoomRateDtl["+(rowCount)+"].dtDate\"  id=\"dtDate."+(rowCount)+"\" value='"+date+"' >";
	 	     row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strRoomTypeDesc."+(rowCount)+"\" value='"+roomtypeDesc+"' />";
	 	     row.insertCell(2).innerHTML= "<input type=\"text\" style=\"text-align:right;\"  name=\"listReservationRoomRateDtl["+(rowCount)+"].dblRoomRate\" id=\"dblRoomRate."+(rowCount)+"\" onblur =\"Javacsript:funCalculateTotals()\" value='"+list.dblRoomRate+"' >";
	 	     row.insertCell(3).innerHTML= "<input type=\"hidden\" class=\"Box \" name=\"listReservationRoomRateDtl["+(rowCount)+"].strRoomType\" id=\"strRoomType."+(rowCount)+"\" value='"+list.strRoomType+"' >";
	     }
	}

	function funSetIncomeHead(code)
	{
		$("#txtIncomeHeadCode").val(code);
		var searchurl=getContextPath()+"/loadIncomeHeadMasterData.html?incomeCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strIncomeHeadCode=='Invalid Code')
			        	{
			        		alert("Invalid Income Head Code");
			        		$("#txtIncomeHeadCode").val('');
			        	}
			        	else
			        	{
			        		funfillIncomHeadGrid(response);
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
	
	function funfillIncomHeadGrid(data){
		
		$("#txtIncomeHead").val(data.strIncomeHeadCode);
		$("#txtIncomeHeadName").val(data.strIncomeHeadDesc);
	 
		//funAddIncomeHeadRow();
		
		//$("#txtIncomeHead").val('');
		//$("#txtIncomeHeadName").text('');
	
	}
	
	

	function funFillDtlGrid(resListResDtlBean)
		{
			funRemoveProductRows();
			var count=0;
			$.each(resListResDtlBean, function(i,item)
			{
				count=i;
				var roomDesc="";
				var roomtypeDesc=funSetRoomType(resListResDtlBean[i].strRoomType);
				var roomtypeDesc=$("#lblRoomType").text();
				$("#lblRoomType").text("");
				funAddDetailsRow(resListResDtlBean[i].strGuestName,resListResDtlBean[i].strGuestCode,resListResDtlBean[i].lngMobileNo
					,resListResDtlBean[i].strRoomType,"",resListResDtlBean[i].strRoomNo,roomDesc
					,resListResDtlBean[i].strExtraBedCode,resListResDtlBean[i].strExtraBedDesc,resListResDtlBean[i].strPayee,"",roomtypeDesc);
			});
			listRow=count+1;
		}


	function funGetRoomDescOnLoad(roomNo) 
		{
			var returnVal =0;
			var searchurl=getContextPath()+"/loadRoomDesc.html?roomNo="+roomNo;
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        async:false,
				success: function(response)
		        {
		        	returnVal = response;
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
			
			return returnVal;
		}
	
	
	
//Delete a All record from a grid
	function funRemoveProductRows()
	{
		var table = document.getElementById("tblResDetails");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	
//Function to Delete Selected Row From Grid
	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblResDetails");
	    table.deleteRow(index);
	}
	
	
	
// Reset Detail Fields
	function funResetDetailFields()
	{
		$("#txtGuestCode").val('');
		$("#txtMobileNo").val('');
		$("#txtGFirstName").val('');
		$("#txtGMiddleName").val('');
		$("#txtGLastName").val('');
		$("#txtAddress").val('');
		
		$("#lblRoomType").text('');
	    $("#txtRoomNo").val('');
	    $("#lblRoomNo").text('');
	    $("#txtExtraBed").val('');
	    $("#lblExtraBed").text('');	
	    $("#txtRemark").text('');	
	    $("#txtRoomTypeCode").val('');
	    
	    
	}
	
	//Delete a All record from a grid
	function funRemoveTariffRows()
	{
		var table = document.getElementById("tblRommRate");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	
	

// Get Detail Info From detail fields and pass them to function to add into detail grid
	function funGetDetailsRow() 
	{
	/*
		var table = document.getElementById("tblResDetails");
		var rowCount = table.rows.length;
		if(rowCount>0)
		{
			alert('Only One Guest is Allowed at a time.');
			return;
		}*/
		
		
		if($("#txtRoomTypeCode").val()=='')
		{
			alert('Select RoomType!!');
		}
		else
		{
			var gCodeval=$("#txtGuestCode").val().trim();
			
			if(gCodeval==''){
				
				var gCode = funGetGuestCode(gCodeval);
				$("#txtGuestCode").val(gCode.strGuestCode);
				
			}
		
			var guestCode=$("#txtGuestCode").val().trim();
			var mobileNo=$("#txtMobileNo").val().trim();
			var guestName=$("#txtGFirstName").val().trim()+" "+$("#txtGMiddleName").val().trim()+" "+$("#txtGLastName").val().trim();
			var roomType =$("#txtRoomTypeCode").val();
			var roomNo =$("#txtRoomNo").val().trim();
			var roomDesc =$("#lblRoomNo").text().trim();
			var extraBedCode=$("#txtExtraBed").val();
			var extraBedDesc=$("#lblExtraBed").text();
			var remark=$("#txtRemark").val();
			var address=$("#txtAddress").val();
			var roomTypeDesc=$("#lblRoomType").text();
			if(roomTypeDesc=='')
			{
				funSetRoomType($("#txtRoomTypeCode").val().trim());
				roomTypeDesc=$("#lblRoomType").text();
			}
			
			if(mobileNo=='')
			{
				alert('Enter Mobile No!!!');
				$("#txtMobileNo").focus();
				return;
			}
			else
			{
				/*var phoneno = /^\d{10}$/;
				if((mobileNo.match(phoneno)))
				{
				}
				else
				{
					alert("Invalid Mobile No");
				    return;
				}
				*/
				
				var pattern = /^[\s()+-]*([0-9][\s()+-]*){6,20}$/;
				if (pattern.test(mobileNo)) 
				{
				}
				else
				{
					alert("Invalid Mobile No");
				    return;
				}	
			}
			
			
			
			
			if($("#txtGFirstName").val().trim()=='')
			{
				alert('Enter Guest First Name!!!');
				$("#txtGFirstName").focus();
				return;
			}
			
			funAddDetailsRow(guestName,guestCode,mobileNo,roomType,remark,roomNo,roomDesc,extraBedCode,extraBedDesc,"N",address,roomTypeDesc);

			funFillRoomRate(roomType,roomDesc);
		}	
	}
	
	
	function funGetDetailsRowinGrid(ArrivalDate, DepartureDate) 
	{
		var returnVal =0;
		var searchurl=getContextPath()+"/loadRoomLimit.html?ArrivalDate="+ArrivalDate+"&DepartureDate="+DepartureDate;
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        async:false,
			success: function(response)
	        {
	        	returnVal = response;
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
		
		return returnVal;
	}
	
	
// Function to add detail grid rows	
	function funAddDetailsRow(guestName,guestCode,mobileNo,roomType,remark,roomNo,roomDesc,extraBedCode,extraBedDesc,payee,address,roomTypeDesc)
	{
	    var table = document.getElementById("tblResDetails");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    rowCount=listRow;

	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"45%\" name=\"listReservationDetailsBean["+(rowCount)+"].strGuestName\" id=\"strGuestName."+(rowCount)+"\" value='"+guestName+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listReservationDetailsBean["+(rowCount)+"].lngMobileNo\" id=\"lngMobileNo."+(rowCount)+"\" value='"+mobileNo+"' />";
	    
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\"  id=\"strRoomType."+(rowCount)+"\" value='"+roomTypeDesc+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listReservationDetailsBean["+(rowCount)+"].strRemark\" id=\"strRemark."+(rowCount)+"\" value='"+remark+"' />";
	    
	    if(payee=='Y')
	    	{
	    		row.insertCell(4).innerHTML= "<input id=\"cbItemCodeSel."+(rowCount)+"\" type=\"radio\" checked=\"checked\" class=\"Box payeeSel\" name=\"listReservationDetailsBean.strPayee\" size=\"2%\"   value=\"Y\" onClick=\"Javacsript:funRadioRow(this)\"  />";
	    	}else
	    	{
	    		row.insertCell(4).innerHTML= "<input id=\"cbItemCodeSel."+(rowCount)+"\" type=\"radio\" class=\"Box payeeSel\" name=\"listReservationDetailsBean.strPayee\" size=\"2%\" value=\"N\" onClick=\"Javacsript:funRadioRow(this)\"  />";
	    	}
	    
	    row.insertCell(5).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"2%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>";
	    
	    row.insertCell(6).innerHTML= "<input type=\"hidden\"  name=\"listReservationDetailsBean["+(rowCount)+"].strGuestCode\" id=\"strGuestCode."+(rowCount)+"\" value='"+guestCode+"' />";
	    row.insertCell(7).innerHTML= "<input type=\"hidden\" size=\"0%\" name=\"listReservationDetailsBean["+(rowCount)+"].strRoomNo\" id=\"strRoomNo."+(rowCount)+"\" value='' />";
	    row.insertCell(8).innerHTML= "<input type=\"hidden\" size=\"0%\" name=\"listReservationDetailsBean["+(rowCount)+"].strExtraBedCode\" id=\"strExtraBedCode."+(rowCount)+"\" value='' />";
	    row.insertCell(9).innerHTML= "<input type=\"hidden\" size=\"0%\" name=\"listReservationDetailsBean["+(rowCount)+"].strAddress\" id=\"strAddress."+(rowCount)+"\" value='"+address+"' />";
	    row.insertCell(10).innerHTML= "<input type=\"hidden\" size=\"0%\" id=\"strRoomDesc."+(rowCount)+"\" value='' />";
	    row.insertCell(11).innerHTML= "<input type=\"hidden\"  size=\"0%\" id=\"strExtraBedDesc."+(rowCount)+"\" value='' />";
	    row.insertCell(12).innerHTML= "<input type=\"hidden\" size=\"0%\" name=\"listReservationDetailsBean["+(rowCount)+"].strRoomType\" id=\"strRoomType."+(rowCount)+"\" value='"+roomType+"' />";
	    funResetDetailFields();
	    
	    if(payee=='Y')
	    	{
	    	 	$("#hidPayee").val(guestCode);
	    	}
	    
	    listRow++;
	    
	   
	}

	function funRadioRow(rowObj)
	{
		/* $( "input[type='radio']" ).prop({
			checked: false
			}); */
			var no=0;
			$('#tblResDetails tr').each(function() {
				
				if(document.getElementById("cbItemCodeSel."+no).checked == true)
					{
					  var gustcode = document.getElementById("strGuestCode."+no).value;
					  $("#hidPayee").val(gustcode);
					}
				no++;
			});
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
		
	
	function funValidateForm()
	{
		var flg=false;
		if($("#txtBookingTypeCode").val()=='')
		{
			alert("Please Select Booking Type");
			flg=false;
		}
		else
		{
			if($('#txtEmailId').val()=='')
			{
				flg=true;
			}
			else
			{
				var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

		        if (reg.test($('#txtEmailId').val()) == false) 
		        {
		            alert('Invalid Email Address');
		            flg=false;
		        }	
			}
			
			
			if($("#txtArrivalTime").val()=='')
			{
				alert("Please Enter Arrival Time");
				flg=false;
			}
			
			if($("#txtDepartureTime").val()=='')
			{
				alert("Please Enter Departure Time");
				flg=false;
			}
			
//	 		if($("#hidPayee").val()=='')
//	 		{
//	 			alert("Please Select One Payee");
//	 			return false;
//	 		}
		/* 	if($("#txtRoomNo").val()=='')
			{
				alert('Select Room No!!!');
				$("#txtRoomNo").focus();
				return false;
			}
			 */
			if($("#txtNoOfAdults").val()=='')
			{
				alert('Enter No of Adults!!!');
				$("#txtNoOfAdults").focus();
				flg=false;
			}
			
			if($("#txtNoOfChild").val()=='')
			{
				alert('Enter No of Child!!!');
				$("#txtNoOfChild").focus();
				flg=false;
			}
			
			var table = document.getElementById("tblResDetails");
		    var rowCount = table.rows.length;
			if(rowCount==0)
			{
				alert("Please Enter Guest For Reservation");
				flg=false;
			}
			
			var ArrivalDate = $("#txtArrivalDate").val();
			var DepartureDate = $("#txtDepartureDate").val();
			var roomLimitCount = funGetDetailsRowinGrid(ArrivalDate, DepartureDate)

			if(roomLimitCount == "0"){
				alert("Room Limit exceed for today");
				flg=false;
			}

			var ArrivalDate = new Date($("#txtArrivalDate").val()); //Year, Month, Date
	        var DepartureDate = new Date($("#txtDepartureDate").val()); //Year, Month, Date
	        if (ArrivalDate > DepartureDate) {
			    	alert("Departure Date Should not be come before Arrival Date");
			    	flg=false;
	        }
	        
	        if(document.getElementById("tblIncomeHeadDtl").rows.length>0)
	        {
	        	var table = document.getElementById("tblTotalPackageDtl");
			    var rowCount = table.rows.length;
				if(rowCount>0)
				{
					if($("#txtPackageName").val()=='')
					 {
						alert("Please Enter Package Name");
						flg=false;
					 }
				}
	        }
	        
	        
			
		}	
		
		
				
		return flg;
	}
	

	function funSetMarketSourceCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadMarketMasterData.html?marketCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strMarketSourceCode=='Invalid Code')
	        	{
	        		alert("Invalid Business Code");
	        		$("#txtMarketSourceCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtMarketSourceCode").val(response.strMarketSourceCode);
		        	$("#lblMarketSourceName").text(response.strDescription);
	        	}
			},
			error : function(e){
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

// 	//set Reservation Data
// 	function funSetIncomeHeadData(incomeCode)
// 	{
// 		$("#txtIncomeHead").val(incomeCode);
// 		var searchUrl=getContextPath()+"/loadIncomeHeadMasterData.html?incomeCode="+incomeCode;
// 		$.ajax({
			
// 			url:searchUrl,
// 			type :"GET",
// 			dataType: "json",
// 	        success: function(response)
// 	        {
// 	        	if(response.strIncomeHeadCode=='Invalid Code')
// 	        	{
// 	        		alert("Invalid Reservation No.");
// 	        		$("#txtIncomeHead").val('');
// 	        	}
// 	        	else
// 	        	{
// 	        		$("#txtIncomeHead").val(response.strIncomeHeadCode);
// 	        		$("#lblIncomeHeadName").text(response.strIncomeHeadDesc);
// 	        	}
// 			},
// 			error: function(jqXHR, exception) 
// 			{
// 	            if (jqXHR.status === 0) {
// 	                alert('Not connect.n Verify Network.');
// 	            } else if (jqXHR.status == 404) {
// 	                alert('Requested page not found. [404]');
// 	            } else if (jqXHR.status == 500) {
// 	                alert('Internal Server Error [500].');
// 	            } else if (exception === 'parsererror') {
// 	                alert('Requested JSON parse failed.');
// 	            } else if (exception === 'timeout') {
// 	                alert('Time out error.');
// 	            } else if (exception === 'abort') {
// 	                alert('Ajax request aborted.');
// 	            } else {
// 	                alert('Uncaught Error.n' + jqXHR.responseText);
// 	            }
// 	        }
// 		});
// 	}
	
	function funAddRow()
	{
		var flag=false;
		
		
		if($("#txtIncomeHead").val().trim().length==0)
		{
			alert("Please Select Income Head.");
		}
		
		if($("#txtIncomeHead").val().trim().length==0)
		{
			alert("Please Select Income Head.");
		}
		else
		{
			if($("#txtIncomeHeadAmt").val().trim().length==0)
			{
				alert("Please Enter Amount For Income Head.");
			}
			else
			{
				flag=true;
				var table = document.getElementById("tblResDetails");
			    var rowCount = table.rows.length;
				if(rowCount==0)
				{
					alert("Please Enter Guest For Reservation");
					
				}
				else
				{
					flag=true;
					funAddIncomeHeadRow($("#txtIncomeHead").val(),$("#txtIncomeHeadName").val(),$("#txtIncomeHeadAmt").val());
					$("#txtIncomeHead").val("");
					$("#txtIncomeHeadName").val("");
					$("#txtIncomeHeadAmt").val("");
				}
				
			}
		}		
		return flag;
	}
	
	function funGetPreviouslyLoadedPkgList(resPackageIncomeHeadList)
	{
		$.each(resPackageIncomeHeadList, function(i,item)
		 {
	 		funAddIncomeHeadRow(item.strIncomeHeadCode,item.strIncomeHeadName,item.dblIncomeHeadAmt);
	 	 });
		
	}
	
	
	
	
	//add income head
	function funAddIncomeHeadRow(incomeHeadCode,incomeHeadName,incomeHeadAmt)
	{
		/*var incomeHeadCode=$("#txtIncomeHead").val();
		var incomeHeadName=$("#txtIncomeHeadName").val();
		var incomeHeadAmt=$("#txtIncomeHeadAmt").val(); 
		*/var amount="0.0";
		
		var flag=false;
		flag=funChechDuplicate(incomeHeadCode);
		if(flag)
		{
			alert("Already Added.");
		}
		else
		{
			var table=document.getElementById("tblIncomeHeadDtl");
			var rowCount=table.rows.length;
			var row=table.insertRow();
			
			var incomehead = $("#hidIncomeHead").val();
			
			if(!incomehead==''){
				incomehead=incomehead+","+incomeHeadCode;
				 $("#hidIncomeHead").val(incomehead);
			}
			else{
				$("#hidIncomeHead").val(incomeHeadCode);
				
			}
			
			
			 
	 	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width:50%;\"  name=\"listRoomPackageDtl["+(rowCount)+"].strIncomeHeadCode\"    id=\"strIncomeHeadCode."+(rowCount)+"\" value='"+incomeHeadCode+"' >";
	 	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 5px;width:50%;\" name=\"listRoomPackageDtl["+(rowCount)+"].strIncomeHeadName\"   id=\"strIncomeHeadDesc."+(rowCount)+"\" value='"+incomeHeadName+"' >";
	 	    row.insertCell(2).innerHTML= "<input type=\"readonly\"   class=\"Box \"  style=\"text-align:right;\" name=\"listRoomPackageDtl["+(rowCount)+"].dblIncomeHeadAmt\"   id=\"dblIncomeRate."+(rowCount)+"\" value='"+incomeHeadAmt+"' >";
	 	    row.insertCell(3).innerHTML= "<input type=\"button\" value=\"Delete\" style=\"padding-right: 5px;width:80%;text-align: right;\" class=\"deletebutton\" onclick=\"funRemoveRow(this)\" />";
					
		//calculate totals
			funCalculateTotals();
		
			//$("#txtIncomeHead").val('');
			//$("#dblIncomeHeadAmt").val('');
		}
	}
	
	//check duplicate value
	function funChechDuplicate(incomeHeadCode)
	{
		var flag=false;
		var table=document.getElementById("tblIncomeHeadDtl");
		var rowCount=table.rows.length;
		if(rowCount>0)
		{
		    for(var i=0;i<rowCount;i++)
		    {
		       var containsAccountCode=table.rows[i].cells[0].innerHTML;
		       var addedIHCode=$(containsAccountCode).val();
		       if(addedIHCode==incomeHeadCode)
		       {
		    	   flag=true;
		    	   break;
		       }
		    }
		}
		return flag;
	}
	
	//calculate totals
	function funCalculateTotals()
	{
		var totalAmt=0.00;
		var table=document.getElementById("tblIncomeHeadDtl");
		var rowCount=table.rows.length;
		if(rowCount>0)
		{
		    for(var i=0;i<rowCount;i++)
		    {
		    	var containsAccountCode=table.rows[i].cells[2].innerHTML;
		       	totalAmt=totalAmt+parseFloat($(containsAccountCode).val());
		    }
		   	totalAmt=parseFloat(totalAmt).toFixed(maxAmountDecimalPlaceLimit);
		}
		//$("#dblTotalAmt").text(totalAmt);
		
		//For tarrif
		var totalTarriff=0;
		if(document.getElementById("tblRommRate").rows.length>0)
		{
			for(var i=0;i<document.getElementById("tblRommRate").rows.length;i++)
		    {
				var objName =document.getElementById("dblRoomRate."+i);
		        totalTarriff=totalTarriff+parseFloat(objName.value);
		    }
			totalTarriff=parseFloat(totalTarriff).toFixed(maxAmountDecimalPlaceLimit);
		}
		
		
		var table=document.getElementById("tblTotalPackageDtl");
		var rowCount=table.rows.length;
		while(rowCount>0)
		{table.deleteRow(0);
		   rowCount--;
		}
		var row=table.insertRow();
		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 56%;width:100%; font-size:13px; font-weight: bold;text-align:right; \"  id=\"strPackageDesc."+(rowCount)+"\" value='Total' >";
 	    row.insertCell(1).innerHTML= "<input type=\"text\" class=\"Box \"  style=\"text-align:right; font-size:13px; font-weight: bold;width:95%;\"  id=\"dblIncomeRate."+(rowCount)+"\" value='"+totalAmt+"' >";
 	    
 	    row=table.insertRow();
		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 67%;width:100%; font-size:13px; font-weight: bold;text-align:right; \"  id=\"strPackageDesc."+(rowCount)+"\" value='Room Tarrif' >";
	    row.insertCell(1).innerHTML= "<input type=\"readonly\"   class=\"Box \" style=\"text-align:right; font-size:13px; font-weight: bold;width:95%;\"  id=\"dblIncomeRate."+(rowCount)+"\" value='"+totalTarriff+"' >";
	    var totalPkgAmt=0;
	    var rowCount=table.rows.length;
	    if(rowCount>0)
		{
		    for(var i=0;i<rowCount;i++)
		    {
		    	var containsAccountCode=table.rows[i].cells[1].innerHTML;
		    	totalPkgAmt=totalPkgAmt+parseFloat($(containsAccountCode).val());
		    }
		    totalPkgAmt=parseFloat(totalPkgAmt).toFixed(maxAmountDecimalPlaceLimit);
		}
	    row=table.insertRow();
		row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-left: 70%;width:100%; font-size:13px; font-weight: bold;text-align:right; \"  id=\"strPackageDesc."+(rowCount)+"\" value='Total Package' >";
	    row.insertCell(1).innerHTML= "<input type=\"text\" class=\"Box \"  style=\"text-align:right; font-size:13px; font-weight: bold;width:95%;\"  id=\"dblIncomeRate."+(rowCount)+"\" value='"+totalPkgAmt+"' >";
	    $("#txtTotalPackageAmt").val(totalPkgAmt);
	}
	
	function funFillRoomRate(roomNo,roomDesc)
	{
		
		 var arrivalDate= $("#txtArrivalDate").val();
		 var departureDate=$("#txtDepartureDate").val();
		 var roomDescList = new Array();
		 var table = document.getElementById("tblResDetails");
		 var rowCount = table.rows.length;
		 for (i = 0; i < rowCount; i++){
			 
			 var oCells = table.rows.item(i).cells;
			 
			 if(roomDescList!='')
			 {
					 roomDescList = roomDescList + ","+table.rows[i].cells[12].children[0].value;
				
			 }
			
			 else
			 {
				 roomDescList = table.rows[i].cells[12].children[0].value;	 
			 }	 

		}
		 $.ajax({  
				type : "GET",
				url : getContextPath()+ "/loadRoomRate.html?arrivalDate="+arrivalDate+"&departureDate="+departureDate+"&roomDescList="+roomDescList+"&noOfNights="+$("#txtNoOfNights").val(),
				dataType : "json",
				success : function(response){ 
				funAddRommRateDtl(response,roomNo,roomDesc);
				},
				error : function(e){
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

	

	function funAddRommRateDtl(dataList,roomNo,roomDesc)
	{
		$('#tblRommRate tbody').empty()
		 var table=document.getElementById("tblRommRate");
		 
		 for(var i=0;i<dataList.length;i++ )
	     {
			 var rowCount=table.rows.length;
			 var row=table.insertRow();
		 var list=dataList[i];
		 var date=list[0];
		 var dateSplit= date.split("-");
		 var month=dateSplit[1];
		 var day = dateSplit[2];
// 		 if(day<10) 
// 		 {
// 			 day='0'+day;
// 		 } 

// 		 if(month<10) 	
// 		 {
// 			 month='0'+month;
// 		 } 
		 date=day+"-"+month+"-"+dateSplit[0];
		 row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-center: 5px;width:50%;\" name=\"listReservationRoomRateDtl["+(rowCount)+"].dtDate\"  id=\"dtDate."+(rowCount)+"\" value='"+date+"' >";
 	     row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"strTypeRoomDesc."+(rowCount)+"\" value='"+list[2]+"' />";
 	     row.insertCell(2).innerHTML= "<input type=\"text\"    style=\"text-align:right;\"  name=\"listReservationRoomRateDtl["+(rowCount)+"].dblRoomRate\" id=\"dblRoomRate."+(rowCount)+"\" onchange =\"Javacsript:funCalculateTotals()\" value='"+list[1]+"' >";
 	     row.insertCell(3).innerHTML= "<input type=\"hidden\" class=\"Box \"  name=\"listReservationRoomRateDtl["+(rowCount)+"].strRoomType\" id=\"strRoomType."+(rowCount)+"\" value='"+list[3]+"' >";
		}
		 
	}
	
	function CalculateDateDiff() 
	{

		var fromDate=$("#txtArrivalDate").val();
		var toDate=$("#txtDepartureDate").val()
		var frmDate= fromDate.split('-');
	    var fDate = new Date(frmDate[2],frmDate[1],frmDate[0]);
	    
	    var tDate= toDate.split('-');
	    var t1Date = new Date(tDate[2],tDate[1],tDate[0]);

    	var dateDiff=t1Date-fDate;
  		 if (dateDiff >= 0 ) 
  		 {
  			var total_days = (dateDiff) / (1000 * 60 * 60 * 24);
  			$("#txtNoOfNights").val(total_days);
         	
         }else{
        	 alert("Please Check Departure Date");
        	 $("#txtDepartureDate").datepicker({ dateFormat: 'dd-mm-yy' });
			 $("#txtDepartureDate").datepicker('setDate', pmsDate);
        	 return false
         }
  		funFillRoomRate('','');
  		
		
	}
	
	function funChangeArrivalDate()
	{
		var arrivalDate=$("#txtArrivalDate").val();
	    var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';

    	if (arrivalDate < pmsDate) 
  		 {
		    	alert("Arrival Date Should not be come before PMS Date");
		    	$("#txtArrivalDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtArrivalDate").datepicker('setDate', pmsDate);
				return false
         }
    	funFillRoomRate('','');
    	
	}

	
	function validateEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test(emailField.value) == false) 
        {
            alert('Invalid Email Address');
            return false;
        }

        return true;

	}
	
	function funRemoveRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblIncomeHeadDtl");
	    table.deleteRow(index);
	    funCalculateTotals();
	}
	
	function funRemoveProductRowsForIncomeHead()
	{
		var table = document.getElementById("tblIncomeHeadDtl");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	function funGetGuestCode(guestCode) 
	{	
		var returnVal =0;
		var searchurl=getContextPath()+"/getGuestCode.html?guestCode="+guestCode;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        async:false,
		        success: function(response)
		        {
		        	returnVal = response;
				},
			error : function(e)
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
		
		 return returnVal;
	}
	function funCreateNewGuest(){
		
		window.open("frmGuestMaster.html", "myhelp", "scrollbars=1,width=500,height=350");
<%-- 		var GuestDetails='<%=session.getAttribute("GuestDetails").toString()%>'; --%>
// 		var guest=GuestDetails.split("#");
		
	}
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Reservation</label>
	</div>

	<br/>
	<br/>

	<s:form name="Reservation" method="POST" action="saveReservation.html">
	
		<div id="tab_container" style="height: 900px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 6%; padding-left: 2%; class="active" >Reservation</li>
					<li data-state="tab2" style="width: 8%; padding-left: 1%">Tariff</li>
					<li data-state="tab3" style="width: 8%; padding-left: 1%">Package</li>
				</ul>
							
<!-- Reservation Tab Start -->

				<div id="tab1" class="tab_content" style="height: 800px">

		<table class="transTable">
		

			<tr>
			<th colspan="6">
			</tr>
		
			<tr>
				<td><label>Reservation No</label></td>
				<td>
					<s:input type="text" id="txtReservationNo" path="strReservationNo" cssClass="searchTextBox" ondblclick="funHelp('ReservationNo');"/>
				</td>
				
				<td><label>Property</label></td>
				<td><s:select id="strPropertyCode" path="strPropertyCode" items="${listOfProperty}" required="true" cssClass="BoxW200px"></s:select></td>
				
				<td><label id="lblPropName"></label></td>
			</tr>
			
			<tr>
				<td><label>Corporate</label></td>
				<td>
					<s:input type="text" id="txtCorporateCode" path="strCorporateCode" cssClass="searchTextBox" ondblclick="funHelp('CorporateCode');"/>
					<label id="lblCorporateDesc"></label>
				</td>
			
				<td ><label>Booking Type</label></td>
				<td>
					<s:input type="text" id="txtBookingTypeCode" path="strBookingTypeCode" cssClass="searchTextBox" ondblclick="funHelp('BookingTypeCode');"/>
					<label id="lblBookingTypeDesc"></label>
				</td>
				
				
			</tr>
			
			<tr>
				<td><label>Arrival Date</label></td>
				<td><s:input type="text" id="txtArrivalDate" path="dteArrivalDate" cssClass="calenderTextBox" onchange="funChangeArrivalDate();" /></td>
			
				<td><label>Departure Date</label></td>
				<td><s:input type="text" id="txtDepartureDate" path="dteDepartureDate" cssClass="calenderTextBox" onchange="CalculateDateDiff();" /></td>
			    <td colspan="2"></td>
			</tr>
			
			<tr>
				<td><label>Arrival Time</label></td>
				<td><s:input type="text" id="txtArrivalTime" path="tmeArrivalTime" cssClass="calenderTextBox" /></td>
			
				<td><label>Departure Time</label></td>
				<td><s:input type="text" id="txtDepartureTime" path="tmeDepartureTime" cssClass="calenderTextBox"  /></td>
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td><label>Pick Up Time</label></td>
				<td><s:input type="text" id="txtPickUpTime" path="tmePickUpTime" cssClass="calenderTextBox" /></td>
			
				<td><label>Drop Time</label></td>
				<td><s:input type="text" id="txtDropTime" path="tmeDropTime" cssClass="calenderTextBox"  /></td>
				<td colspan="2"></td>
			</tr>
						
			<tr>
				<td><label>#Adult</label></td>
				<td><s:input id="txtNoOfAdults" name="txtNoOfAdults" path="intNoOfAdults" style="width: 20%;text-align: right;" type="number" min="1" step="1"   class="longTextBox" /></td>
				<td><label>#Child</label></td>
				<td><s:input id="txtNoOfChild" path="intNoOfChild" style="text-align: right; width: 20%;" type="number" min="0" step="1" name="txtNoOfChild" class="longTextBox" /></td>				
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td><label>No Of Nights</label></td>
				<td>
					<s:input type="text" class="numeric" id="txtNoOfNights" path="intNoOfNights" cssClass="longTextBox" style="width: 20%;text-align: right;"/>
				</td>
				
				<td><label>Booking Rooms</label></td>
				<td>
					<s:input type="number" min="1" step="1" class="longTextBox" id="txtNoOfBookingRoom" path="intNoRoomsBooked" cssClass="longTextBox" style="text-align: right;width: 20%;" />
				</td>
				<td colspan="2"></td>
			
			</tr>
			
			<tr>
				<td><label>Email Id</label></td>
				<td><s:input type="text" id="txtEmailId" path="strEmailId" cssClass="longTextBox" /></td>
				<td><label>Contact Person</label></td>
				<td><s:input type="text" id="txtContactPerson" path="strContactPerson" cssClass="longTextBox" /></td>
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td><label>Billing Instructions</label></td>
				<td>
					<s:input type="text" id="txtBillingInstCode" path="strBillingInstCode" cssClass="searchTextBox" ondblclick="funHelp('BillingInstCode');"/>
					<label id="lblBillingInstDesc"></label></td>
			
				<td><label>Booker</label></td>
				<td colspan="3">
					<s:input type="text" id="txtBookerCode" path="strBookerCode" cssClass="searchTextBox" ondblclick="funHelp('BookerCode');"/>
					<label id="lblBookerName"></label>
				</td>
			</tr>
			
			<tr>
				<td><label>Business Source</label></td>
				<td>
					<s:input type="text" id="txtBusinessSourceCode" path="strBusinessSourceCode" cssClass="searchTextBox" ondblclick="funHelp('business');"/>
					<label id="lblBusinessSourceName"></label></td>
			
				<td><label>Agent</label></td>
				<td colspan="3">
					<s:input type="text" id="txtAgentCode" path="strAgentCode" cssClass="searchTextBox" ondblclick="funHelp('AgentCode');"/>
					<label id="lblAgentName"></label></td>
			</tr>
			
			<tr>
				<td><label>Remarks</label></td>
				<td><s:input colspan="1" type="text" id="txtRemarks" path="strRemarks" cssClass="longTextBox" /></td>
				<td><label>OTA No</label></td>
				<td><s:input type="text" id="txtOTANo" path="strOTANo" class="longTextBox" style="width: 34%"/></td>
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td><label>Cancel Date</label></td>
				<td><s:input type="text" id="txtCancelDate" path="dteCancelDate" cssClass="calenderTextBox" /></td>
			
				<td><label>Confirm Date</label></td>
				<td><s:input type="text" id="txtConfirmDate" path="dteConfirmDate" cssClass="calenderTextBox" /></td>
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td><label>Market Source</label></td>
				<td>
					<s:input  type="text" id="txtMarketSourceCode" path="strMarketSourceCode" cssClass="searchTextBox" ondblclick="funHelp('marketsource');"/>
						&nbsp;&nbsp;&nbsp;<label id="lblMarketSourceName"></label>
				</td>
			<td colspan="3"></td>
				
			</tr>
			
		</table>

			<br>
			<br>
		
			<div >
			<table class="transTable">
				
				<tr>
					<td><label>Mobile No</label></td>
					<td><input type="text" id="txtMobileNo" class="longTextBox" /></td>
					
					<td><label>Guest Code</label></td>
					<td><input id="txtGuestCode" ondblclick="funHelp('guestCode');" class="searchTextBox" /></td>
					<td>
					<input type="Button" value="New Guest" onclick="return funCreateNewGuest()" class="form_button" />
					</td>
					<td colspan="2"></td>
				</tr>
				
				<tr>
					<td><label id="lblGFirstName">First Name</label></td>
					<td><input type="text" id="txtGFirstName" class="longTextBox" /></td>
					
					<td><label id="lblGMiddleName">Middle Name</label></td>
					<td><input type="text" id="txtGMiddleName" class="longTextBox" /></td>
					
					<td><label id="lblGLastName">Last Name</label></td>
					<td><input type="text" id="txtGLastName" class="longTextBox" /></td>
					<td colspan="1"></td>
				</tr>
				<tr>
				<td><label>Room Type</label></td>
				<td>
                  <input type="text" id="txtRoomTypeCode" name="txtRoomTypeCode" Class="searchTextBox" ondblclick="funHelp('roomType')" /></td>
				<td><label id="lblRoomType"></label></td>
			    <td colspan="1" ></td>
				<td><label>Extra Bed</label></td>
				<td><input type="text" id="txtExtraBed" name="txtExtraBed" Class="searchTextBox" ondblclick="funHelp('extraBed')" /></td>
				<td><label id="lblExtraBed"></label></td>
				</tr>
				
				<tr>
					<td><label>Address</label></td>
					<td><input type="text" id="txtAddress" Class="longTextBox" /></td>
					<td><label>Remarks</label></td>
					<td><input type="text" id="txtRemark" path="strRemark" Class="longTextBox"  /></td>
					<td colspan="3"></td>
     			</tr>
				
				<tr>
					<td></td>
					<td>
						<input type="Button" value="Add" onclick="return funGetDetailsRow()" class="smallButton" />
					</td>
					<td colspan="5"></td>
				</tr>
			
			</table>
		</div>

	
		<div class="dynamicTableContainer" style="height: 300px;">
			<table style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
				<tr bgcolor="#72BEFC">				
					<td style="width:22%;">Name</td>
					<td style="width:10%;">Mobile No</td>
<!-- 					<td style="width:7%;">Room No</td> -->
<!-- 					<td style="width:9%;">Extra Bed</td> -->
					<td style="width:6%;">Room Type</td>
					<td style="width:04%;">Remarks</td>
					<td style="width:5%;">Payee</td>
					<td style="width:25%;">Delete</td>
				</tr>
			</table>
		
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblResDetails"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">
					<tbody>
						<col style="width: 37%;">
						<col style="width: 16% ;">
<%-- 						<col style="width: 80px;"> --%>
<%-- 						<col style="width: 100px;"> --%>
						<col style="width: 10%;">
						<col style="width: 7%;">
						<col style="width: 8%;">
						<col style="width: 38%;">
						<col style="width: 0px;">
						<col style="width: 0px;">
						<col style="width: 0px;">
						<col style="width: 0px;">
						<col style="width: 0px;">
						<col style="width: 0px;">
						<col style="width: 0px;">
							
					</tbody>
				</table>
			</div>
		</div>
</div>

<!-- End of Reservation Tab -->

<!-- Start of Tarif Tab -->

	<div id="tab2" class="tab_content" style="height: 400px">
	<!-- Generate Dynamic Table   -->		
	<br/><br/><br/>
		<div class="dynamicTableContainer" style="height: 400px; width: 80%">
			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px; font-weight: bold;">
				<tr bgcolor="#72BEFC" style="height: 24px;">
					<!-- col1   -->
					<td align="center" style="width: 30.6%">Date</td>
					<!-- col1   -->
					
					<!-- col2   -->
					<td align="center" style="width: 30.6%">Room Type.</td>
					<!-- col2   -->
					
					<!-- col3   -->
					<td align="center" style="width: 30.6%">Rate.</td>
					<!-- col3   -->
					
													
				</tr>
			</table>
			<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblRommRate" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll" class="transTablex col3-center">
					<tbody>
						<!-- col1   -->
						<col width="100%">
						<!-- col1   -->
						
						<!-- col2   -->
						<col width="100%" >
						<!-- col2   -->
						
						<!-- col2   -->
						<col width="100%" >
						<!-- col2   -->
						
					</tbody>
				</table>
			</div>			
	
	</div>
	</div>
	
<!-- End of Tarif Tab -->

<!-- Start of Package Tab -->

	 <div id="tab3" class="tab_content" style="height: 400px">
	 <br><br>
	 
	 	<table class="transTable">
	 	    <tr>
			    <td><label>Package Code</label></td>
			    <td><s:input id="txtPackageCode" path="strPackageCode"  readonly="true"  ondblclick="funHelp('package')" cssClass="searchTextBox"/></td>
			    <td><label>Package Name</label></td>
			    <td><s:input id="txtPackageName" path="strPackageName" class="longTextBox" /></td>
			</tr>
			<tr>
			    <td><label>Income Head</label></td>
			    <td><s:input id="txtIncomeHead" path=""  readonly="true"  ondblclick="funHelp('incomeHead')" cssClass="searchTextBox"/></td>
			    <td><label>Income Head Name</label></td>
				<td><input type="text" id="txtIncomeHeadName" path="" Class="longTextBox"  /></td> 
			</tr>
			<tr>
			    <td><label>Amount</label></td>
			    <td><input type="text" id="txtIncomeHeadAmt" path="" Class="BoxW124px"  /></td>
			    <td><input type="button" value="Add"  class="smallButton" onclick='return funAddRow()'/></td>
	
			</tr>
		</table>
		
		<br/>
		<!-- Generate Dynamic Table   -->		
		<div class="dynamicTableContainer" style="height: 320px; width: 80%">
			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px; font-weight: bold;">
				<tr bgcolor="#72BEFC" style="height: 24px;">
					<!-- col1   -->
					<td align="left" style="width: 30.6%">Income Head Code</td>
					<!-- col1   -->
					
					<!-- col2   -->
					<td align="left" style="width: 30.6%">Income Head Name</td>
					<!-- col2   -->
					
					<!-- col3   -->
					<td align="right" style="width: 30.6%">Amount</td>
					<!-- col3   -->
					
					<!-- col4   -->
					<td align="center">Delete</td>
					<!-- col4  -->									
				</tr>
			</table>
			<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 200px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblIncomeHeadDtl" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll" class="transTablex col3-center">
					<tbody>
						<!-- col1   -->
						<col width="100%">
						<!-- col1   -->
						
						<!-- col2   -->
						<col width="100%" >
						<!-- col2   -->
						
						<!-- col3   -->
						<col width="100%" >
						<!-- col3   -->
						
						<!-- col4   -->
						<col  width="10%">
						<!-- col4   -->
					</tbody>
				</table>
			</div>	
			
			<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 120px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblTotalPackageDtl" style="width: 100%; border: #0F0; font-size:15px; font-weight: bold; table-layout: fixed; overflow: scroll" class="display dataTable no-footer">
					<tbody>
						<!-- col1   -->
						<col width="100%" >
						<!-- col1   -->
						
						<!-- col2   -->
						<col width="100%" >
						<!-- col2   -->
						
						<!-- col3   -->
						<col  width="10%">
						<!-- col3   -->
					</tbody>
				</table>
			</div>		
		</div>		
	 
	 </div>
	 
<!-- End of Package Tab -->	 
				
</div>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateForm();" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetDetailFields()"/>
		</p>
		<s:input type="hidden" id="hidPayee" path="strPayeeGuestCode"></s:input>
		<s:input type="hidden" id="hidIncomeHead" path="strIncomeHeadCode"></s:input>
		<s:input type="hidden" id="txtTotalPackageAmt" path="strTotalPackageAmt"></s:input>
		
		
			<input type="hidden" id="txtRoomNo" name="txtRoomNo" ondblclick="funHelp('roomCode')" Class="searchTextBox"/> 
				<td><label type="hidden" id="lblRoomNo"></label></td>
		<br />
		<br />

	</s:form>
</body>
</html>
							