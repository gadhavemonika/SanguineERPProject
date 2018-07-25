<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName,settlementType="";
	
	$(function() 
	{
		var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		$("#txtExpiryDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtExpiryDate").datepicker('setDate', pmsDate);		
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		  var dte=pmsDate.split("-");
		  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);
		  
		  var code='${code}';
		  var dblAmt='${dblBalanceAmt}';
		  
		  if(code!=''||code!="")
		  {
		    $("#txtDocCode").val(code);
		    $("#txtSettlementCode").focus();
		    $("#flagForAdvAmt").val("Y");
		    $("#lblBalnceAmount").text(dblAmt);
		    
		  }
		  else{
			  
		  }
	});


	function funSetData(code){

		switch(fieldName){

			case 'RegistrationNo':
				//funSetPaymentData(code);
				break;
				
			case 'ReservationNo':
				funSetPaymentGuestData(code,"Reservation");
				break;
				
			case 'folioPayee':
				funSetPaymentGuestData(code,"Folio-No");
				break;
				
			case 'checkInRooms':
				funSetPaymentGuestData(code,"CheckIn");
				break;
			
			case 'BillForPayment':
				funSetPaymentGuestData(code,"Bill");
				break;
				
			case 'settlementCode':
				funSetSettlementType(code);
				break;
				
			case 'receiptNo':
				funSetReceiptData(code);
				break;	
		}
	}

	
	function funSetReceiptData(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadReceiptData.html?receiptNo=" + code,
			dataType : "json",
			success : function(response){ 
				funSetReceiptInfo(response);
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
	
	
	function funSetFolioNoData(code){

		$("#txtDocCode").val(code);
	}
	
	

	function funSetRegistrationNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadRegistrationNo.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
								
			},
			error : function(e){

			}
		});
	}

	function funSetReservationNo(code)
	{
		$("#txtDocCode").val(code);
	}
	
	
//set PaymentGuestDetails Data
	function funSetPaymentGuestData(code,type)
	{
		var searchUrl=getContextPath()+"/loadPaymentGuestDetails.html?docCode="+code+ "&docName=" + type;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	if(response.strRoomCode=='Invalid Code')
	        	{
	        		alert("Invalid Doc Code");
	        		$("#txtDocCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDocCode").val(code);
	        		$("#lblGuestName").text("Guest Name");
	        		$("#lblGuestFullName").text(response[0].strFirstName+" "+response[0].strMiddleName+" "+response[0].strLastName);
	        		$( "#txtReceiptAmt" ).focus();
	        		$( "#lblBalnceAmount").text(response[0].dblBalanceAmount);
	        	}
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

	
	function funSetBillNo(code)
	{
	  	$("#txtDocCode").val(code);
	}
	
	
	function funSetCheckinNo(code)
	{
		$("#txtDocCode").val(code);
	}
	
	
	function funSetSettlementType(code)
	{
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadSettlementCode.html?settlementCode=" + code,
			dataType : "json",
			success : function(response)
			{
				if(response.strService=='Invalid Code')
	        	{
	        		alert("Invalid Service Code");
	        		$("#txtSettlement").val('');
	        	}
	        	else
	        	{
		        	$("#txtSettlementCode").val(response.strSettlementCode);
		        	$("#txtSettlementDesc").val(response.strSettlementDesc);
		        	settlementType=response.strSettlementType;
	        	}

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
	}
	
	
	//Open Against Form
	function funOpenAgainst() {
		if ($("#cmbAgainst").val() == "Reservation")
		{
			funHelp("ReservationNo");
			fieldName = "ReservationNo";
		}

		else if ($("#cmbAgainst").val() == "Folio-No") 
		{
			funHelp("folioPayee");
			fieldName = "folioPayee";
		}
		/* else if ($("#cmbAgainst").val() == "Check-In") 
		{
			funHelp("checkIn");
			fieldName = "checkInRooms";
		} */
		else if ($("#cmbAgainst").val() == "Bill")
		{
			funHelp("BillForPayment");
			fieldName = "BillForPayment";
		}
	}
	

	function funSetReceiptInfo(response)
	{
		$("#txtReceiptNo").val(response.strReceiptNo);
		$("#cmbAgainst").val(response.strAgainst);
		$("#txtDocCode").val(response.strDocNo);
		$("#txtReceiptAmt").val(response.dblReceiptAmt);
		
		if(response.strAgainst=='Reservation')
		{
			funSetPaymentGuestData(response.strDocNo,"Reservation");
		}
		else if(response.strAgainst=='Check-In')
		{
			funSetPaymentGuestData(response.strDocNo,"CheckIn");
		}
		else if(response.strAgainst=='Bill')
		{
			funSetPaymentGuestData(response.strDocNo,"Bill");
		}
		
		$("#txtSettlementCode").val(response.strSettlementCode);
		funSetSettlementType(response.strSettlementCode);
		$("#txtCardNo").val(response.strCardNo);
		$("#txtExpiryDate").val(response.dteExpiryDate);
		$("#txtRemarks").val(response.strRemarks);
		$("#flagForAdvAmt").val(response.strFlagOfAdvAmt);
		
	}
	
	

	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=900,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	


/**
* Success Message After Saving Record
**/
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
			{%>
				alert("Data Save successfully\n\n"+message);
				var reciptNo='';
				var isOk=confirm("Do You Want to Generate Slip?");
				if(isOk)
 				{
	 				reciptNo='<%=session.getAttribute("GenerateSlip").toString()%>';
	 				checkAgainst='<%=session.getAttribute("Against").toString()%>';
	    			window.open(getContextPath()+"/rptReservationPaymentRecipt.html?reciptNo="+reciptNo+"&checkAgainst="+checkAgainst,'_blank');
					session.removeAttribute("GenerateSlip");
					session.removeAttribute("Against");
				}<%
			}
		}%>
	});
	
 	/**
	*   Attached document Link
	**/
	$(function()
	{
	
		$('a#baseUrl').click(function() 
		{
			if($("#txtReceiptNo").val().trim()=="")
			{
				alert("Please Select Receipt Number");
				return false;
			}
		   	window.open('attachDoc.html?transName=frmPMSPayment.jsp&formName=Payment&code='+$('#txtReceiptNo').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		});
		
		/**
		* On Blur Event on Reservation Code Textfield
		**/
		$('#txtReceiptNo').blur(function() 
		{
			var code = $('#txtReceiptNo').val();
			if (code.trim().length > 0 && code !="?" && code !="/")
			{
				funSetData(code);
			}
		});
		
	});
 	
 	
 	
 	
	function funValidateFields(actionName,object)
	{
		var flg=true;
		
		if($("#txtSettlementCode").val().trim().length==0)
		{
			alert("Please Select Payment mode!!");
			 flg=false;
		}
		
		if(settlementType=="Credit Card")
		{
			if($("#txtCardNo").val().trim().length==0)
			{
				alert("Please Enter cardNo!!");
				 flg=false;
			}
		}
		if(parseFloat($("#lblBalnceAmount").text())>='0.0')
		{
			if($("#txtReceiptAmt").val()=='0'||$("#txtReceiptAmt").val()=='0.0' )
			{
				alert("Please Enter Amount");
				 flg=false;
			}
			else if(parseFloat($("#txtReceiptAmt").val())>parseFloat($("#lblBalnceAmount").text()))
			{
				alert("Amount should not be greatet than balance amount");
				 flg=false;
			}
		}
		else
		{
			alert("Please Enter Amount");
			 flg=false;
		}	
		
		
		return flg;
	}
	
	

</script>

</head>
<body>

	<div id="formHeading">
	<label>Payment</label>
	</div>

<br/>
<br/>

<s:form name="Payment" method="GET" action="savePMSPayment.html">
	
	<table class="masterTable">
	
		<tr>
			<td>Payment Receipt No</td>
		
			<td>
				<s:input id="txtReceiptNo" path="strReceiptNo" readonly="readonly" ondblclick="funHelp('receiptNo');" class="searchTextBox"></s:input>
			</td>
			<td colspan="3"></td>
		</tr>
		
	   	<tr>
			<td>Against</td>
			
			<td>
				<s:select id="cmbAgainst" items="${listAgainst}" name="cmbAgainst"  cssClass="BoxW124px" path="strAgainst"></s:select>
			</td>
			
			<td><s:input id="txtDocCode" path="strDocNo" readonly="readonly" ondblclick="funOpenAgainst()" class="searchTextBox"></s:input></td>
		    	<td colspan="2"></td>
		</tr>
		  
	  	<tr>
	      	<td colspan="1"><label id="lblGuestName"></label></td>
		   	<td colspan="2" ><label id="lblGuestFullName"></label></td>	
		   	<td colspan="2"></td>
	  	</tr>

		<tr>
			<td><label>Amount</label></td>
			
			<td><s:input colspan="3" type="number" style="text-align: right;width: 118px;" id="txtReceiptAmt" path="dblReceiptAmt" cssClass="longTextBox" /></td>
			<td><label>Balance Amount</label></td>
			<td><label id="lblBalnceAmount" readonly="readonly"></label></td>
			
		</tr>
						
		<tr>
			<td width="15%"><label>Settlement Code</label></td>
			
			<td>
				<s:input colspan="3" type="text" id="txtSettlementCode" path="strSettlementCode" cssClass="searchTextBox" ondblclick="funHelp('settlementCode');"/>
			</td>
				  
			<td><label>Settlement Desc</label></td>
			
			<td><s:input colspan="3" type="text" id="txtSettlementDesc" path="strSettlementDesc" cssClass="longTextBox" /></td>
		</tr>
			
		<tr>
			<td><label>Card No</label></td>
			
			<td><s:input colspan="3" type="text" id="txtCardNo" path="strCardNo" cssClass="longTextBox" /></td>
			
			<td><label>Expiry Date</label></td>
			
			<td><s:input colspan="3" type="text" id="txtExpiryDate" path="dteExpiryDate" cssClass="calenderTextBox" /></td>
		</tr>
		
		<tr>
			<td><label>Remark</label></td>
			    
			<td><s:input colspan="3" type="text" id="txtRemarks" path="strRemarks" cssClass="longTextBox" /></td>
			<td colspan="3"></td>
		</tr>
		
		
		
	</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields('submit',this)" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
       <s:input type="hidden"  id="flagForAdvAmt" path="strFlagOfAdvAmt" value="N" name="saddr" />
	</s:form>
</body>
</html>
