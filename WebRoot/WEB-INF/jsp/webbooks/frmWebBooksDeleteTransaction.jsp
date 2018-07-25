<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	
	var fieldName,transactionName;

	$(function ()
	{
		funSetTransCodeHelp($("#cmbFormName").val());
				
		$("#DeleteTransaction").submit(function( event )
		{
			if($("#txtTransCode").val()=='')
			{
				alert("Please Select Transaction Code To Delete");
				return false;
			}
			
			if($("#txtReason").val()=='')
			{
				alert("Enter Reason For Delete");
				return false;
			}
		});
		
		$("#btnShow").click(function(event)
		{
			funSetDocCode($("#txtTransCode").val());
		});
		
		$("#cmbFormName").change(function() 
		{
			var value=$("#cmbFormName").val();
			funSetTransCodeHelp(value);
		});
		
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
				alert("Data Deleted successfully\n\n"+message);
				<%
			}else{
				%>	
				alert("Data Not Deleted .Other trasaction entry are present\n\n"+message);
				<%
			}
		}%>
	});

		
	
	
	function funSetTransCodeHelp(value)
	{
		switch (value) 
		{
		   case 'frmJV':
			   transactionName='JVNo';
		       break;
		       
		   case 'frmReceipt':
			   transactionName='ReceiptNo';
		       break;
		       
		   case 'frmPayment':
			   transactionName='PaymentNo';
		       break;
		}
	}
	
	
	
	function funSetData(code){

		switch(fieldName){

			case 'reason':
	    		funSetReason(code);
	        	break;
		
			case 'TransCode' : 
				$("#txtTransCode").val(code);
				break;
		}
	}
	

	function funSetReason(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadWebStockReasonMasterData.html?reasonCode=" + code,
			dataType : "json",
			success : function(response){
				
				$("#txtReasonCode").val(response.strReasonCode);
				$("#lblReasonDesc").text(response.strReasonName);
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

	
// Validations before submitting form values. 
	function funValidateForm() 
	{
		if($("#txtVouchNo").val()=='')
		{
			alert("Select Vouch No!!!");
			return false;
		}
		return true;
	}
		


	function funHelp(transactionName)
	{		
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
	
	function funTransCodeHelp(transactionName)
	{
		switch ($("#cmbFormName").val()) 
		{
		   case 'frmJV':
			   transactionName='JVNo';
		       break;
		       
		   case 'frmReceipt':
			   transactionName='ReceiptNo';
		       break;
		       
		   case 'frmPayment':
			   transactionName='PaymentNo';
		       break;
		}
		
		fieldName="TransCode";
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>DeleteTransaction</label>
	</div>

	<br/>
	<br/>

	<s:form name="DeleteTransaction" method="POST" action="saveWebBooksDeleteTrans.html">

		<table class="masterTable">
		
			<tr>
				<td>
					<label>FormName</label>
				</td>
				<td>
					<s:select id="cmbFormName" path="strFormName" cssClass="longTextBox">
						<s:options items="${listFormName}"/>
					</s:select>
				</td>
			
				<td>
					<label>Trans Code</label>
				</td>
				<td>
					<s:input  type="text" id="txtTransCode" path="strTransCode" cssClass="searchTextBox" ondblclick="funTransCodeHelp('TransCode');"/>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
					<select id="cmbDeleteYN" class="BoxW124px">
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Reason Code</label>
				</td>
				<td colspan="2">
					<s:input  type="text" id="txtReasonCode" path="strReasonCode" cssClass="searchTextBox" ondblclick="funHelp('reason');"/>
				</td>
				
				<td>
					<label id="lblReasonDesc"></label>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Narration</label>
				</td>
				<td colspan="3">
					<s:textarea type="text" id="txtNarration" path="strNarration" />
				</td>
			</tr>
			
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"  onclick="return funValidateHeaderFields()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
