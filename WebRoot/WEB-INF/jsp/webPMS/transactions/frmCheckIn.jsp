<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	
	var fieldName,gridHelpRow;

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
	});		
	
	$(function() 
	{
		var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		$("#txtArrivalTime").timepicker();
		$("#txtDepartureTime").timepicker();
		
		$("#txtArrivalDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtArrivalDate").datepicker('setDate', pmsDate);
		
		$("#txtDepartureDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtDepartureDate").datepicker('setDate', pmsDate);
		$("#hidPayee").val('N')
		//$("#txtArrivalTime").prop('disabled',true);
		//$("#txtDepartureTime").prop('disabled',true);
		//$("#txtArrivalDate").prop('disabled',true);
		//$("#txtDepartureDate").prop('disabled',true);
		 
		$('a#baseUrl').click(function() 
		{
			if($("#txtCheckInNo").val().trim()=="")
			{
				alert("Please Select CheckIn No ");
				return false;
			}
			window.open('attachDoc.html?transName=frmCheckIN.jsp&formName=CheckIn &code='+$('#txtCheckInNo').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		});
				
		var checkInNo='';
		<%if (session.getAttribute("checkInNo") != null) 
		{
			%>
			checkInNo='<%=session.getAttribute("checkInNo").toString()%>';
			funSetCheckInData(checkInNo);
		    <%
   		 	session.removeAttribute("checkInNo");
		}%>
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		  var dte=pmsDate.split("-");

		  
		  
	});

	
	function funSetData(code){

		switch(fieldName){

			case 'RegistrationNo' : 
				funSetRegistrationNo(code);
				break;
				
			case 'ReservationNo' : 
				funSetReservationNo(code);
				break;
				
			case 'WalkinNo' : 
				funSetWalkinNo(code);
				break;
				
			case 'checkIn' : 
				funSetCheckInData(code);
				break;
				
			case 'roomByRoomType' : 
				funSetRoomNo(code,gridHelpRow);
				break;
			
			case 'extraBed' : 
				funSetExtraBed(code,gridHelpRow);
				break;
				
			case "incomeHead":
				funSetIncomeHead(code);
			break;
			
			case "package":
				funSetPackageNo(code);
			break;
				
				
		}
	}

	
	
	function funSetCheckInData(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadCheckInData.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strCheckInNo!="Invalid")
		    	{
					funFillCheckInHdData(response);
					
		    	}
		    	else
			    {
			    	alert("Invalid Check In No");
			    	$("#txtCheckInNo").val("");
			    	$("#txtCheckInNo").focus();
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
	
	
	function funFillCheckInHdData(response)
	{
		$("#txtCheckInNo").val(response.strCheckInNo);
		$("#txtRegistrationNo").val(response.strRegistrationNo);
		$("#txtDocNo").val(response.strAgainstDocNo);
		$("#cmbAgainst").val(response.strType);
		if(response.strType=="Reservation")
		{
			$("#lblAgainst").text("Reservation");
		}
		else
		{
			$("#lblAgainst").text("Walk In No");
		}
				
		$("#txtArrivalDate").val(response.dteArrivalDate);
		$("#txtDepartureDate").val(response.dteDepartureDate);

		$("#txtArrivalTime").val(response.tmeArrivalTime);
		$("#txtDepartureTime").val(response.tmeDepartureTime);
		
		$("#txtRoomNo").val(response.strRoomNo);
		$("#lblRoomNo").text(response.strRoomDesc);
	    $("#txtExtraBed").val(response.strExtraBedCode);
	    $("#lblExtraBed").text(response.strExtraBedDesc);	    
	    $("#txtNoOfAdults").val(response.intNoOfAdults);
	    $("#txtNoOfChild").val(response.intNoOfChild);
	    
	    $("#txtPackageCode").val(response.strPackageCode);
		$("#txtPackageName").val(response.strPackageName);
	   
	    if(response.strNoPostFolio=='Y')
    	{
    		document.getElementById("txtNoPostFolio").checked=true;
    	}
    	else
    	{
    		document.getElementById("txtNoPostFolio").checked=false;
    	}
	    
	    funRemoveProductRowsForIncomeHead();
		funRemoveTariffRows();
		funFillDtlGrid(response.listCheckInDetailsBean);
		if(response.strType=="Reservation")
		{
			funAddRommRateDtlOnReservationSelect(response.listReservationRoomRateDtl);
		}
		else
		{
			funAddRommRateDtlOnWalkinSelect(response.listWalkinRoomRateDtl);
		}
		funGetPreviouslyLoadedPkgList(response.listRoomPackageDtl);	
		
	}
		
	
	function funFillDtlGrid(resListDtlBean)
	{
		funRemoveProductRows();
		$.each(resListDtlBean, function(i,item)
		{
			
			funAddDetailsRow(resListDtlBean[i].strGuestName,resListDtlBean[i].strGuestCode,resListDtlBean[i].lngMobileNo
				,resListDtlBean[i].strRoomNo,resListDtlBean[i].strRoomDesc,resListDtlBean[i].strExtraBedCode
				,resListDtlBean[i].strExtraBedDesc,resListDtlBean[i].strPayee,resListDtlBean[i].strRoomType);
		});
	}
	
	
	

	function funSetReservationNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadReservation.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strReservationNo!="Invalid")
		    	{
					funFillHdDataAgainstRes(response);
					//funFillRoomRate();
		    	}
		    	else
			    {
			    	alert("Invalid Reservation No");
			    	$("#txtDocNo").val("");
			    	$("#txtDocNo").focus();
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
	
	
	function funFillHdDataAgainstRes(response)
	{
		$("#txtDocNo").val(response.strReservationNo);
		
		$("#txtArrivalDate").val(response.dteArrivalDate);
		$("#txtDepartureDate").val(response.dteDepartureDate);

		$("#txtArrivalTime").val(response.tmeArrivalTime);
		$("#txtDepartureTime").val(response.tmeDepartureTime);
		
		$("#txtRoomNo").val(response.strRoomNo);
		$("#lblRoomNo").text(response.strRoomDesc);
	    $("#txtExtraBed").val(response.strExtraBedCode);
	    $("#lblExtraBed").text(response.strExtraBedDesc);
	    $("#txtNoOfAdults").val(response.intNoOfAdults);
	    $("#txtNoOfChild").val(response.intNoOfChild);
	    
	    $("#txtPackageCode").val(response.strPackageCode);
		$("#txtPackageName").val(response.strPackageName);

	    funRemoveProductRowsForIncomeHead();
		funRemoveTariffRows();
		funFillDtlGridAgainstRes(response.listReservationDetailsBean);
		funAddRommRateDtlOnReservationSelect(response.listReservationRoomRateDtl);
		funGetPreviouslyLoadedPkgList(response.listRoomPackageDtl);
	}
		
	
	function funFillDtlGridAgainstRes(resListResDtlBean)
	{
		funRemoveProductRows();
		$.each(resListResDtlBean, function(i,item)
		{
			funAddDetailsRow(resListResDtlBean[i].strGuestName,resListResDtlBean[i].strGuestCode,resListResDtlBean[i].lngMobileNo
				,resListResDtlBean[i].strRoomNo,resListResDtlBean[i].strRoomDesc
				,resListResDtlBean[i].strExtraBedCode,resListResDtlBean[i].strExtraBedDesc,resListResDtlBean[i].strPayee,resListResDtlBean[i].strRoomType);
		});
	}
	
	
	
	function funSetRoomType(code){
		var retVal="";
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadRoomTypeMasterData.html?roomCode=" + code,
			dataType : "json",
		    async:false,
			success : function(response){ 
				if(response.strAgentCode=='Invalid Code')
	        	{
					retVal= "Invalid Room Type" ;
// 	        		$("#lblRoomType").text('');
	        	}
	        	else
	        	{					        	    	        		
	        		retVal= response.strRoomTypeDesc;
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
		return retVal;
	}
	
	function funSetWalkinNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadWalkinData.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strWalkinNo!="Invalid")
		    	{
					funFillHdDataAgainstWalkIn(response);
					//funFillRoomRate();
		    	}
		    	else
			    {
			    	alert("Invalid Walk In No");
			    	$("#txtDocNo").val("");
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
	
	
	function funFillHdDataAgainstWalkIn(response)
	{
		$("#txtDocNo").val(response.strWalkinNo);		
		$("#txtArrivalDate").val(response.dteWalkinDate);
		$("#txtDepartureDate").val(response.dteCheckOutDate);
		$("#txtArrivalTime").val(response.tmeWalkinTime);
		$("#txtDepartureTime").val(response.tmeCheckOutTime);
		$("#txtRoomNo").val(response.strRoomNo);
		$("#lblRoomNo").text(response.strRoomDesc);
	    $("#txtExtraBed").val(response.strExtraBedCode);
	    $("#lblExtraBed").text(response.strExtraBedDesc);
	    $("#txtNoOfAdults").val(response.intNoOfAdults);
	    $("#txtNoOfChild").val(response.intNoOfChild);
	    $("#txtPackageCode").val(response.strPackageCode);
		$("#txtPackageName").val(response.strPackageName); 

	    funRemoveProductRowsForIncomeHead();
		funRemoveTariffRows();
		funFillDtlGridAgainstWalkIn(response.listWalkinDetailsBean);
		funAddRommRateDtlOnWalkinSelect(response.listWalkinRoomRateDtl);
		funGetPreviouslyLoadedPkgList(response.listRoomPackageDtl);
	}
		
	
	function funFillDtlGridAgainstWalkIn(resListWalkInDtlBean)
	{
		funRemoveProductRows();
		$.each(resListWalkInDtlBean, function(i,item)
		{
			var geustName=resListWalkInDtlBean[i].strGuestFirstName+' '+resListWalkInDtlBean[i].strGuestMiddleName+' '+resListWalkInDtlBean[i].strGuestLastName
			funAddDetailsRow(geustName,resListWalkInDtlBean[i].strGuestCode,resListWalkInDtlBean[i].lngMobileNo
				,resListWalkInDtlBean[i].strRoomNo,resListWalkInDtlBean[i].strRoomDesc,resListWalkInDtlBean[i].strExtraBedCode
				,resListWalkInDtlBean[i].strExtraBedDesc,"N",resListWalkInDtlBean[i].strRoomType);
		});
	}
	
	

	// Get Detail Info From detail fields and pass them to function to add into detail grid
		function funGetDetailsRow() 
		{
			var guestCode=$("#txtGuestCode").val().trim();
			var mobileNo=$("#txtMobileNo").val().trim();
			var guestName=$("#txtGFirstName").val().trim()+" "+$("#txtGMiddleName").val().trim()+" "+$("#txtGLastName").val().trim();
			
			var roomNo =$("#txtRoomNo").val().trim();
			var roomDesc =$("#lblRoomNo").text().trim();
			var extraBedCode=$("#txtExtraBed").val();
			var extraBedDesc=$("#lblExtraBed").text();
			
		    funAddDetailsRow(guestName,guestCode,mobileNo,roomNo,roomDesc,extraBedCode,extraBedDesc,"Y");
		}
	
	
	//Function to add detail grid rows	
		function funAddDetailsRow(guestName,guestCode,mobileNo,roomNo,roomDesc,extraBedCode,extraBedDesc,payee,roomTypeCode) 
		{
		    var table = document.getElementById("tblCheckInDetails");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var roomtypeDesc=funSetRoomType(roomTypeCode);
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14.5%\" name=\"listCheckInDetailsBean["+(rowCount)+"].strGuestName\" id=\"strGuestName."+(rowCount)+"\" value='"+guestName+"' />";	    
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10.5%\" name=\"listCheckInDetailsBean["+(rowCount)+"].lngMobileNo\" id=\"lngMobileNo."+(rowCount)+"\" value='"+mobileNo+"' />";	   
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10.5%\" id=\"strRoomTypeDesc."+(rowCount)+"\" value='"+roomtypeDesc+"' />";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"searchTextBox\" size=\"9.5%\" name=\"listCheckInDetailsBean["+(rowCount)+"].strRoomNo\" id=\"strRoomNo."+(rowCount)+"\" value='"+roomNo+"'  ondblclick=\"Javacsript:funHelp1('roomByRoomType',"+(rowCount)+",'"+roomTypeCode+"' )\"/>";
		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10.5%\" id=\"strRoomDesc."+(rowCount)+"\" value='"+roomDesc+"' />";
		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"searchTextBox\" size=\"9.5%\" name=\"listCheckInDetailsBean["+(rowCount)+"].strExtraBedCode\" id=\"strExtraBedCode."+(rowCount)+"\" value='"+extraBedCode+"' ondblclick=\"Javacsript:funHelp1('extraBed',"+(rowCount)+",'')\" />";
		    
		     
		    if(payee=='Y')
		    {
		    	row.insertCell(6).innerHTML= "<input id=\"cbItemCodeSel."+(rowCount)+"\" type=\"checkbox\" checked=\"checked\" class=\"Box payeeSel\" name=\"listCheckInDetailsBean["+(rowCount)+"].strPayee\" size=\"3%\" value=\"Y\" onClick=\"Javacsript:funCheckBoxRow(this)\"  />";
		    }
		    else
		    {
		    	row.insertCell(6).innerHTML= "<input id=\"cbItemCodeSel."+(rowCount)+"\" type=\"checkbox\" class=\"Box payeeSel\" name=\"listCheckInDetailsBean["+(rowCount)+"].strPayee\" size=\"3%\" value=\"N\" onClick=\"Javacsript:funCheckBoxRow(this)\" />";	
		    } 
		    
		    
		  /*   if(payee=='Y')
		    {
		    	row.insertCell(4).innerHTML= "<input id=\"cbItemCodeSel."+(rowCount)+"\" type=\"radio\" checked=\"checked\" class=\"Box payeeSel\" name=\"strPayeeRoomNo."+roomNo+"\" size=\"2%\" value='"+roomNo+"'  onClick=\"Javacsript:funRadioRow(this)\" />";
		    	row.insertCell(5).innerHTML= "<input readonly=\"readonly\"  style=\"display:none\" class=\"searchTextBox\" size=\"1%\" name=\"listCheckInDetailsBean["+(rowCount)+"].strPayee\" id=\"strPayee."+(rowCount)+"\" value=\"Y\" />";
		    }
		    else
		    {
		    	row.insertCell(4).innerHTML= "<input id=\"cbItemCodeSel."+(rowCount)+"\" type=\"radio\" class=\"Box payeeSel\" name=\"strPayeeRoomNo."+roomNo+"\" size=\"2%\" value='"+roomNo+"'  />";	
		    	row.insertCell(5).innerHTML= "<input readonly=\"readonly\"  style=\"display:none\" class=\"searchTextBox\" size=\"1%\" name=\"listCheckInDetailsBean["+(rowCount)+"].strPayee\" id=\"strPayee."+(rowCount)+"\" value=\"N\" />";	
		    } */
		    
		    row.insertCell(7).innerHTML= "<input class=\"Box\" size=\"4%\" name=\"listCheckInDetailsBean["+(rowCount)+"].intNoOfFolios\" id=\"intNoOfFolios."+(rowCount)+"\" value='1' />";
		    row.insertCell(8).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"6%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>";
		    
		    row.insertCell(9).innerHTML= "<input size=\"1%\" name=\"listCheckInDetailsBean["+(rowCount)+"].strGuestCode\" id=\"strGuestCode."+(rowCount)+"\" value='"+guestCode+"' type='hidden' />";
		    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" id=\"strExtraBedDesc."+(rowCount)+"\" value='"+extraBedDesc+"' />";
		    row.insertCell(11).innerHTML= "<input type=\"hidden\" class=\"Box \" name=\"listCheckInDetailsBean["+(rowCount)+"].strRoomType\" id=\"strRoomType."+(rowCount)+"\" value='"+roomTypeCode+"' >";
		    funResetDetailFields();
		    
		    if(payee=='Y')
		    {
		    	$("#hidPayee").val(guestCode);
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
						
						
						var table = document.getElementById("tblCheckInDetails");
						var rowCount = table.rows.length;
						var roomFound='N';
						if(rowCount>0)
						{
							for(var i=0;i<rowCount;i++)
							{
								 if(document.getElementById("strRoomNo."+i).value=='')
								 {
									roomFound='N';
								 }
								 else
								 {
									 roomFound='Y'; 
								 }	 
							}
							
							if(roomFound=='N')
							{
								alert('Select Room!!');
							}
							else
							{
								$("#txtPackageName").val(response.strPackageName);
								$.each(response.listPackageDtl, function(i,item)
								{
									funAddIncomeHeadRow(item.strIncomeHeadCode.split('#')[0],item.strIncomeHeadCode.split('#')[1],item.dblAmt,'');		
								});
							}
						}
						else
						{
							alert('Guest List Not Found!!');
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
		
		
		function funfillIncomHeadGrid(data)
		{
			
			$("#txtIncomeHead").val(data.strIncomeHeadCode);
			$("#txtIncomeHeadName").val(data.strIncomeHeadDesc);
		}
		
		
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
					var table = document.getElementById("tblCheckInDetails");
					var rowCount = table.rows.length;
					var roomFound='N';
					if(rowCount>0)
					{
						for(var i=0;i<rowCount;i++)
						{
							 if(document.getElementById("strRoomNo."+i).value=='')
							 {
								roomFound='N';
							 }
							 else
							 {
								 roomFound='Y'; 
							 }	 
						}
						
						if(roomFound=='N')
						{
							alert('Select Room!!');
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
					else
					{
						flag=false;
						alert('Guest List Not Found!!');
					}
				}
			}		
			return flag;
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
		
		
		function funFillRoomRate()
		{
			
			 var arrivalDate= $("#txtArrivalDate").val();
			 var departureDate=$("#txtDepartureDate").val();
			 var roomDescList = new Array();
			 var table = document.getElementById("tblCheckInDetails");
			 var rowCount = table.rows.length;
			 for (i = 0; i < rowCount; i++){
				 
				 var oCells = table.rows.item(i).cells;
				 
				 if(roomDescList!='')
				 {
						 roomDescList = roomDescList + ","+table.rows[i].cells[11].children[0].value;
					
				 }
				
				 else
				 {
					 roomDescList = table.rows[i].cells[11].children[0].value;	 
				 }	 

			}
			 $.ajax({  
					type : "GET",
					url : getContextPath()+ "/loadRoomRate.html?arrivalDate="+arrivalDate+"&departureDate="+departureDate+"&roomDescList="+roomDescList+"&noOfNights=0",
					dataType : "json",
					success : function(response){ 
					funAddRommRateDtl(response);
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
		
		
		function funAddRommRateDtl(dataList)
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
				 date=day+"-"+month+"-"+dateSplit[0];
				 row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-center: 5px;width:50%;\" name=\"listReservationRoomRateDtl["+(rowCount)+"].dtDate\"  id=\"dtDate."+(rowCount)+"\" value='"+date+"' >";
		 	     row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" id=\"strTypeRoomDesc."+(rowCount)+"\" value='"+list[2]+"' />";
		 	     row.insertCell(2).innerHTML= "<input type=\"text\"    style=\"text-align:right;\"  name=\"listReservationRoomRateDtl["+(rowCount)+"].dblRoomRate\" id=\"dblRoomRate."+(rowCount)+"\" onchange =\"Javacsript:funCalculateTotals()\" value='"+list[1]+"' >";
		 	     row.insertCell(3).innerHTML= "<input type=\"hidden\" class=\"Box \"  name=\"listReservationRoomRateDtl["+(rowCount)+"].strRoomType\" id=\"strRoomType."+(rowCount)+"\" value='"+list[3]+"' >";
			}
		}
		
		function funAddRommRateDtlOnReservationSelect(dataList)
		{
			
			
			 var table=document.getElementById("tblRommRate");
			 
			 for(var i=0;i<dataList.length;i++ )
		     {
				 var rowCount=table.rows.length;
				 var row=table.insertRow();
				 var list=dataList[i];
				 var date,roomtypeDesc,roomRate,roomType;
				 date=list.dtDate;
				 roomtypeDesc=funSetRoomType(list.strRoomType);
				 //roomtypeDesc=$("#lblRoomType").text();
				 roomRate=list.dblRoomRate;
				 roomType=list.strRoomType;
				 $("#lblRoomType").text("");
				 var dateSplit = date.split("-");
				 date=dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
				 row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-center: 5px;width:50%;\" name=\"listReservationRoomRateDtl["+(rowCount)+"].dtDate\"  id=\"dtDate."+(rowCount)+"\" value='"+date+"' >";
		 	     row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" id=\"strRoomTypeDesc."+(rowCount)+"\" value='"+roomtypeDesc+"' />";
		 	     row.insertCell(2).innerHTML= "<input type=\"text\" style=\"text-align:right;\"  name=\"listReservationRoomRateDtl["+(rowCount)+"].dblRoomRate\" id=\"dblRoomRate."+(rowCount)+"\" onchange =\"Javacsript:funCalculateTotals()\" value='"+roomRate+"' >";
		 	     row.insertCell(3).innerHTML= "<input type=\"hidden\" class=\"Box \" name=\"listReservationRoomRateDtl["+(rowCount)+"].strRoomType\" id=\"strRoomType."+(rowCount)+"\" value='"+roomType+"' >";
				 
		     }
		}
		
		function funGetPreviouslyLoadedPkgList(resPackageIncomeHeadList)
		{
			$.each(resPackageIncomeHeadList, function(i,item)
			 {
		 		funAddIncomeHeadRow(item.strIncomeHeadCode,item.strIncomeHeadName,item.dblIncomeHeadAmt);
		 	 });
			
		}
		
		function funAddRommRateDtlOnWalkinSelect(dataList)
		{
			
			 var table=document.getElementById("tblRommRate");
			 
			 for(var i=0;i<dataList.length;i++ )
		     {
				 var rowCount=table.rows.length;
				 var row=table.insertRow();
				 var list=dataList[i];
				 var date,roomtypeDesc,roomRate,roomType;
				 date=list.dtDate;
				 roomtypeDesc=funSetRoomType(list.strRoomType);
				// roomtypeDesc=$("#lblRoomType").text();
				 roomRate=list.dblRoomRate;
				 roomType=list.strRoomType;
			 	$("#lblRoomType").text("");
				 var dateSplit = date.split("-");
				 date=dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
				 var dateSplit = date.split("-");
				 date=dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
				 row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box \"  style=\"padding-center: 5px;width:34%;\" name=\"listWalkinRoomRateDtl["+(rowCount)+"].dtDate\"  id=\"dtDate."+(rowCount)+"\" value='"+date+"' >";
		 	     row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"34%\" id=\"strRoomTypeDesc."+(rowCount)+"\" value='"+roomtypeDesc+"' />";
		 	     row.insertCell(2).innerHTML= "<input type=\"text\" style=\"text-align:right;width:34%;\"  name=\"listWalkinRoomRateDtl["+(rowCount)+"].dblRoomRate\" id=\"dblRoomRate."+(rowCount)+"\" onchange =\"Javacsript:funCalculateTotals()\" value='"+roomRate+"' >";
		 	     row.insertCell(3).innerHTML= "<input type=\"hidden\" class=\"Box \" name=\"listWalkinRoomRateDtl["+(rowCount)+"].strRoomType\" id=\"strRoomType."+(rowCount)+"\" value='"+roomType+"' >";
				 }
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
		
		function funRemoveRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblIncomeHeadDtl");
		    table.deleteRow(index);
		    funCalculateTotals();
		}


	// Reset Detail Fields
		function funResetDetailFields()
		{
			$("#txtGuestCode").val('');
			$("#txtMobileNo").val('');
			$("#txtGFirstName").val('');
			$("#txtGMiddleName").val('');
			$("#txtGLastName").val('');
		}
	
		function funCheckBoxRow(rowObj)
		{
			/* $( "input[type='radio']" ).prop({
				checked: false
				}); */
				var no=0;
				$('#tblCheckInDetails tr').each(function() {
					
					if(document.getElementById("cbItemCodeSel."+no).checked == true)
						{
						  document.getElementById("cbItemCodeSel."+no).value='Y';
						  $("#hidPayee").val('Y');
						  
						}else
							{
								document.getElementById("cbItemCodeSel."+no).value='N';
								$("#hidPayee").val('N');
							}
					no++;
				});
		}


	//Delete a All record from a grid
		function funRemoveProductRows()
		{
			var table = document.getElementById("tblCheckInDetails");
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
		    var table = document.getElementById("tblCheckInDetails");
		    table.deleteRow(index);
		}


		function funHelp(transactionName)
		{
			fieldName=transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		}
		
		function funHelp1(transactionName,row,condition)
		{
			gridHelpRow=row;
			fieldName=transactionName;
			if(transactionName=="roomByRoomType")
			{
				window.open("searchform.html?formname="+fieldName+"&strRoomTypeCode="+condition+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			
			}
			else
			{
				window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			}
		}
	
		
		function funHelpAgainst()
		{
			if ($("#cmbAgainst").val() == "Reservation")
			{
				fieldName="ReservationNo";
			}
			else if ($("#cmbAgainst").val() == "Walk In")
			{
				fieldName="WalkinNo";
			}
			
			window.open("searchform.html?formname="+fieldName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			//window.showModalDialog("searchform.html?formname="+fieldName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		}
		
	
		$(document).ready(function()
		{
			var message='';
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
					%>
					alert("Data Save successfully\n\n"+message);
					var AdvAmount='';
// 					var isOk=confirm("Do You Want to pay Advance Amount ?");
// 					if(isOk)
// 						{
// 						var checkAgainst="checkIN";
<%-- 						AdvAmount='<%=session.getAttribute("AdvanceAmount").toString()%>'; --%>
// // 						window.location.href=getContextPath()+"/frmPMSPaymentAdvanceAmount.html?AdvAmount="+AdvAmount ;
// 		    			session.removeAttribute("AdvanceAmount");
// //							session.removeAttribute("Against");
					
// 						}
					
					
					<%
				}
			}%>
			
			
		});
		
		
		function funOnChange() {
			if ($("#cmbAgainst").val() == "Reservation")
			{
				$("#lblAgainst").text("Reservation");
			}
			else if ($("#cmbAgainst").val() == "Walk In")
			{
				$("#lblAgainst").text("Walk In No");
			}
		}
		

		 /**
			*   Attached document Link
			**/
			$(function()
			{
			
				$('a#baseUrl').click(function() 
				{
					if($("#txtCheckInNo").val().trim()=="")
					{
						alert("Please Select CheckIN No ");
						return false;
					}
				   window.open('attachDoc.html?transName=frmCheckIN.jsp&formName=CheckIn &code='+$('#txtCheckInNo').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
				});
				
				
				var walkinNo='${walkinNo}';
				  
				  if(walkinNo!=''||walkinNo!="")
				  {
					  alert("Walkin No Is"+walkinNo);
					  $("#lblAgainst").text("Walk In No");
					  fieldName="WalkinNo";
					   $("#cmbAgainst").val("Walk In");
					   funSetWalkinNo(walkinNo);
				  }
			});
		 
		 
		 function funValidateForm()
		 {
				var table = document.getElementById("tblCheckInDetails");
			    var rowCount = table.rows.length;
				if(rowCount==0)
				{
					alert("Please Select Guest.");
					return false;
				}
				
				
				var table = document.getElementById("tblCheckInDetails");
				var rowCount = table.rows.length;
				var payee='N',roomFound='Y';
				for(var i=0;i<rowCount;i++)
				{
		    		if(rowCount==1)
		    		{
		    			
		    			 document.getElementById("cbItemCodeSel."+i).checked = true;
				    	 document.getElementById("cbItemCodeSel."+i).value='Y';
						 $("#hidPayee").val('Y');
						 payee='Y';
						 
						 if(document.getElementById("strRoomNo."+i).value=='')
						 {
							roomFound='N';
						 }
		    		}
		    		else
		    		{
		    			if(document.getElementById("cbItemCodeSel."+i).value=='Y')
						{
							payee='Y';
						}
		    			if(document.getElementById("strRoomNo."+i).value=='')
						{
							roomFound='N';
						}
		    		}	
				}	
				
				if(payee=='N')
				{
					alert("Please Select One Payee");
					return false;
				}
				/*rowCount = table.rows.length;
				for(var i=0;i<rowCount;i++)
				{
					if(document.getElementById("txtRoomNo."+i).value=='')
					{
						roomFound='N';
					}
				}
				*/
				
				if(roomFound=='N')
				{
					alert("Please Select Room No!!");
					return false;
				}
				
				return true;	 
		 }
		 
		 
		 function funSetRoomNo(code,gridHelpRow){

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
			        		document.getElementById("strRoomNo."+gridHelpRow).value=response.strRoomCode;						
			    			document.getElementById("strRoomDesc."+gridHelpRow).value=response.strRoomDesc;
			    			document.getElementById("strRoomTypeDesc."+gridHelpRow).value=response.strRoomTypeDesc;						
			    			document.getElementById("strRoomType."+gridHelpRow).value=response.strRoomTypeCode;
			    			 $( "#tblCheckInDetails" ).load( "your-current-page.html #tblCheckInDetails" );
			    		//	document.getElementById("strPayee."+gridHelpRow).value=response.strRoomDesc; 
			    			  
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
			
		 function funSetExtraBed(code,gridHelpRow)
			{
			//	$("#txtExtraBed").val(code);
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
					        	document.getElementById("strExtraBedCode."+gridHelpRow).value=code;						
				    			document.getElementById("strExtraBedDesc."+gridHelpRow).value=response.strExtraBedTypeDesc; 
						       
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

	<div id="formHeading">
	<label>CheckIn</label>
	</div>

	<br/>
	<br/>

	<s:form name="frmCheckIn" method="POST" action="saveCheckIn.html">
		<div id="tab_container" style="height: 900px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 6%; padding-left: 2%; class="active" >Check In</li>
					<li data-state="tab2" style="width: 8%; padding-left: 1%">Tariff</li>
					<li data-state="tab3" style="width: 8%; padding-left: 1%">Package</li>
					</ul>
							
<!-- Check In Tab Start -->

				<div id="tab1" class="tab_content" style="height: 800px">

		<table class="transTable">
			
			<tr>
				
				<th align="right" colspan="4"><a id="baseUrl" href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;&nbsp;</th>
			    <th colspan="1">
			</tr>
			<tr>
			<th colspan="6">
			</tr>
			
		
			<tr>
				<td>
					<label>Check In No</label>
				</td>
				<td>
					<s:input  type="text" id="txtCheckInNo" path="strCheckInNo" cssClass="searchTextBox" ondblclick="funHelp('checkIn');"/>
				</td>
							
				<td>
					<label>Registration No</label>
				</td>
				<td>
					<s:input type="text" id="txtRegistrationNo" path="strRegistrationNo" cssClass="searchTextBox" ondblclick="funHelp('RegistrationNo');"/>
				</td>
			</tr>
			
		 	<tr>
				<td><label>Type</label></td>
				<td>
					<s:select id="cmbAgainst" path="strType" cssClass="BoxW124px" onchange="funOnChange();">
					    <option selected="selected" value="Reservation">Reservation</option>
				        <option value="Walk In">Walk In</option>
			         </s:select>
				</td>
							
				<td>
					<label id="lblAgainst">Reservation No</label>
				</td>
				<td>
					<s:input  type="text" id="txtDocNo" path="strAgainstDocNo" cssClass="searchTextBox" ondblclick="funHelpAgainst();"/>
				</td>
			</tr>
			
			<tr>
				<td><label>Arrival Date</label></td>
				<td><s:input  type="text" id="txtArrivalDate" path="dteArrivalDate" cssClass="calenderTextBox" /></td>
			
				<td><label>Departure Date</label></td>
				<td><s:input  type="text" id="txtDepartureDate" path="dteDepartureDate" cssClass="calenderTextBox" /></td>
			</tr>
		
			<tr>
				<td><label>Arrival Time</label></td>
				<td><s:input  type="text" id="txtArrivalTime" path="tmeArrivalTime" cssClass="calenderTextBox" /></td>
			
				<td><label>Departure Time</label></td>
				<td><s:input  type="text" id="txtDepartureTime" path="tmeDepartureTime" cssClass="calenderTextBox" /></td>
			</tr>
			
			
				
			<tr>
				<td ><label>#Adult</label></td>
				<td><s:input id="txtNoOfAdults" name="txtNoOfAdults" path="intNoOfAdults" type="number" min="0" step="1" class="longTextBox" style="width: 38%;text-align: right;"/></td>
				<td><label>#Child</label></td>
				<td><s:input id="txtNoOfChild" path="intNoOfChild" type="number" min="0" step="1" name="txtNoOfChild" class="longTextBox" style="width: 38%;text-align: right;"/></td>				
			</tr> 
			<tr>
			<td>NO POST Folio</td>
			<td><s:checkbox id="txtNoPostFolio" path="strNoPostFolio" value="Y" />
			</tr>
		</table>
		
		<br>
		<br>
		

		<div class="dynamicTableContainer" style="height: 300px;">
			<table style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
				<tr bgcolor="#72BEFC">
				
					<td style="width:14%;">Name</td>
					<td style="width:7%;">Mb No</td>
					<td style="width:10%;">Room Type</td>
					<td style="width:10%;">Room Code</td>
					<td style="width:10%;">Room No</td>
					<td style="width:10%;">Extra Bed</td>					
					<td style="width:1%;">Payee</td>
					<td style="width:3.5%;">No Of Folios</td>
					<td style="width:1%;">Delete</td>
					
					
				</tr>
			</table>
		
			<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblCheckInDetails"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">
					<tbody>
					
						<col style="width: 16%;">
						<col style="width: 8.5%;">
						<col style="width: 12%;">
						<col style="width: 11.5%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 2%;">
						<col style="width: 4%;">
						<col style="width: 2%;">
						<col style="width: 0%;">
						<col style="width: 0%;">
						<col style="width: 0%;">
					
					<!-- 
						<col style="width=40%">
						<col style="width=10%">
						<col style="width=10%">
						<col style="width=10%">
						<col style="width:10%">
						<col style="width:5%">
						<col style="width:5%">
						<col style="width:5%">
						<col style="width:5%">
						<col style="width:5%">-->
						
						<col style="display:none;">
					</tbody>
				</table>
			</div>
		</div>
	</div>	
		<!-- End of Check In Tab -->
		
		
		<!-- Start of Tarif Tab -->

	<div id="tab2" class="tab_content" style="height: 400px">
	<!-- Generate Dynamic Table   -->		
	<br/><br/><br/>
		<div class="dynamicTableContainer" style="height: 200px; width: 80%">
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
			<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 200px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
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
		<br>
		<br>
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateForm();"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
 		<s:input type="hidden" id="txtTotalPackageAmt" path="strTotalPackageAmt"></s:input>
		<br><br>

	</s:form>
</body>
</html>
