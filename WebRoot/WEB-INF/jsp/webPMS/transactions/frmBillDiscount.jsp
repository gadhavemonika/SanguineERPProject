<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName;

	$(function() 
	{
		var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		$("#txtBillDate").timepicker();
		$("#txtBillDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtBillDate").datepicker('setDate', pmsDate);
	});

	function funSetData(code){

		switch(fieldName){

			case 'billNo' : 
				funSetBillNo(code);
				break;
			case 'CheckInNo' : 
				funSetCheckInNo(code);
				break;
			case 'FolioNo' : 
				funSetFolioNo(code);
				break;
		}
	}


	function funSetBillNo(code){

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/getBillData.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

				if(response.strBillNo=="Invalid Bill No")
		    	{
					alert("Invalid Bill No");
			    	$("#txtBillNo").val("");
			    	$("#txtBillNo").focus();
			    	return false;
		    	}
		    	else
			    {
		    		$("#txtBillNo").val(response.strBillNo);
		    		$("#txtBillDate").val(response.dteBillDate);
		    		$("#txtCheckInNo").val(response.strCheckInNo);
		    		$("#txtFolioNo").val(response.strFolioNo);
		    		
		    		funSetRoomMasterData(response.strRoomNo)
		    		$("#txtDiscAmt").val(response.dblDiscAmt);
		    		$("#txtGrandTotal").val(response.dblGrandTotal);
		    		$("#txtTotal").val(response.dblGrandTotal);
		    		
		    		$("#txtRemark").val('');
		    		$("#cmbReason").val('');
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


	function funSetCheckInNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadCheckInNo.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

			},
			error : function(e){

			}
		});
	}

	function funSetFolioNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadFolioNo.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

			},
			error : function(e){

			}
		});
	}

	function funSetRoomMasterData(roomCode)
	{
		var searchUrl=getContextPath()+"/loadRoomMasterData.html?roomCode="+roomCode;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	if(response.strRoomCode=='Invalid Code')
	        	{
	        		alert("Invalid Room Code");
	        		$("#txtRoomNo").val('');
	        	}
	        	else
	        	{
	        		$("#txtRoomNo").val(response.strRoomCode);
	        		$("#lblRoomDesc").text(response.strRoomDesc);
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



	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funDiscountSelection(){
		if($("#cmbDiscountType").val()=="Amount"){
			document.getElementById("txtDiscPer").readOnly = true; 
			document.getElementById("txtDiscAmt").readOnly = false; 
			$("#txtDiscPer").val("");
			$("#txtGrandTotal").val($("#txtTotal").val());
		}
		if($("#cmbDiscountType").val()=="Per"){
			document.getElementById("txtDiscAmt").readOnly = true;
			document.getElementById("txtDiscPer").readOnly = false; 
			$("#txtDiscAmt").val("");
			$("#txtGrandTotal").val($("#txtTotal").val());
		}
	}
	function funCalculateDiscount()
	{
		if($("#cmbDiscountType").val()=="Amount"){
			var discAmt=$("#txtDiscAmt").val();
			$("#txtGrandTotal").val($("#txtTotal").val()-discAmt);
		}else{
			var discPer=$("#txtDiscPer").val();
			var grandTotCal=$("#txtTotal").val()-($("#txtTotal").val()*(discPer/100));
			
			$("#txtGrandTotal").val(grandTotCal);
		}
	}
	
	function funBtnSubmit(){
		
		if($("#txtBillNo").val()==''){
			alert("Select Bill No");
			return false;
		}
		if($("#txtGrandTotal").val()==''){
			alert("No Full Void");
			return false;
		}
		if($("#txtGrandTotal").val()==''){
			alert("No Full Void");
			return false;
		}
		
		if($("#txtRemark").val()==''){
			alert("Enter Valied Remark");
			return false;
		}
		
	}

	/**
	* Success Message After Saving Record
	**/
	 $(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) {
			            if(session.getAttribute("successMessage") != null){%>
			            message='<%=session.getAttribute("successMessage").toString()%>';
			            <%
			            session.removeAttribute("successMessage");
			            }
						boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
						session.removeAttribute("success");
						if (test) {
						%>	
			alert("Data Save successfully\n\n"+message);
		<%
		}}%>

	});
</script>

</head>
<body>

	<div id="formHeading">
	<label>Modify Bill</label>
	</div>

<br/>
<br/>

	<s:form name="BillDiscount" method="POST" action="saveBillDiscount.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>Bill No</label>
				</td>
				<td>
					<s:input colspan="3" type="text" path="strBillNo" id="txtBillNo" cssClass="searchTextBox" ondblclick="funHelp('billNo')" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Bill Date</label>
				</td>
				<td>
					<s:input colspan="3" type="text" readonly="true" path="dteBillDate" id="txtBillDate" cssClass="calenderTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>CheckIn No</label>
				</td>
				<td>
					<s:input colspan="3" type="text" readonly="true"  path="strCheckInNo" id="txtCheckInNo" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Folio No</label>
				</td>
				<td>
					<s:input colspan="3" type="text" readonly="true"  path="strFolioNo"  id="txtFolioNo" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Room No</label>
				</td>
				<td>
					<s:input type="text" readonly="true"  path="strRoomNo"  id="txtRoomNo" cssClass="BoxW124px" />
				&nbsp;&nbsp;&nbsp;<label id="lblRoomDesc"></label>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Disc Type</label>
				</td>
				<td>
    				<s:select id="cmbDiscountType" path="strDiscountType" cssClass="BoxW124px"
    				onchange="funDiscountSelection();">
 					    <s:option value="Amount">Amount</s:option>
				    	<s:option value="Per">Per</s:option>
				   </s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>Disc Amt</label>
				</td>
				<td>
					<s:input colspan="3" type="number" step="0.01"  path="dblDiscAmt"  id="txtDiscAmt" onblur="funCalculateDiscount();" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Disc Per</label>
				</td>
				<td>
					<s:input type="number" step="0.01"  path="dblDiscPer"  id="txtDiscPer" onblur="funCalculateDiscount();" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Total</label>
				</td>
				<td>
					<s:input colspan="3" type="number" step="0.01" readonly="true"   path="dblTotal"  id="txtTotal" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Grand Total</label>
				</td>
				<td>
					<s:input colspan="3" type="number" step="0.01" readonly="true"   path="dblGrandTotal"  id="txtGrandTotal" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
			<td>
				<label>Reason</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
   			<td>
				 <s:select id="cmbReason" path="strReason" cssClass="BoxW124px">
    			 <s:options items="${listReason}"/>
    			</s:select>
   			</td>
   			</tr>
   			<tr>
   			<td>
    			<label>Remark</label>
    			</td>
   			<td>
    			<s:input id="txtRemark" path="strRemark"  cssClass="longTextBox"/>
			</td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funBtnSubmit()" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
