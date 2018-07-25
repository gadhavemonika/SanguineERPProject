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
	});

	function funSetData(code){

		switch(fieldName){

			case 'CurrencyCode' : 
				funSetCurrencyCode(code);
				break;
		}
	}


	function funSetCurrencyCode(code){

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/loadCurrencyCode.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strCurrencyCode=='Invalid Code')
	        	{
	        		alert("Invalid Code");
	        		$("#txtCurrencyCode").val('');
	        	}
	        	else
	        	{        
		        	$("#txtCurrencyCode").val(response.strCurrencyCode);
		        	$("#txtCurrencyName").val(response.strCurrencyName);
		        	$("#txtShortName").val(response.strShortName);
		        	$("#txtBankName").val(response.strBankName);
		        	$("#txtSwiftCode").val(response.strSwiftCode);
		        	$("#txtIbanNo").val(response.strIbanNo);
		        	$("#txtRouting").val(response.strRouting);
		        	$("#txtAccountNo").val(response.strAccountNo);
		        	$("#txtConvToBaseCurr").val(response.dblConvToBaseCurr);
		        	$("#txtSubUnit").val(response.strSubUnit);
		        
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
			{
				%>alert("Data Saved \n\n"+message);<%
			}
		}%>
	});













	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Currency Master</label>
	</div>

<br/>
<br/>

	<s:form name="CurrencyMaster" method="POST" action="saveCurrencyMaster.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>Currency Code</label>
				</td>
				<td>
					<s:input type="text" id="txtCurrencyCode" path="strCurrencyCode" cssClass="searchTextBox" ondblclick="funHelp('CurrencyCode');"/>
				</td>
				<td>
					<label>Currency Name</label>
				</td>
				<td>
					<s:input type="text" id="txtCurrencyName" path="strCurrencyName" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Short Name</label>
				</td>
				<td>
				<s:input type="text" id="txtShortName" path="strShortName" cssClass="longTextBox" />
				</td>
			<td>
					<label>Bank Name</label>
				</td>
				<td>
				<s:input type="text" id="txtBankName" path="strBankName" cssClass="longTextBox" />
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Swift Code</label>
				</td>
				<td>
				<s:input type="text" id="txtSwiftCode" path="strSwiftCode" cssClass="longTextBox" />
				</td>
			<td>
					<label>Iban No</label>
				</td>
				<td>
				<s:input type="text" id="txtIbanNo" path="strIbanNo" cssClass="longTextBox" />
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Routing</label>
				</td>
				<td>
				<s:input type="text" id="txtRouting" path="strRouting" cssClass="longTextBox" />
				</td>
			<td>
					<label>Account No</label>
				</td>
				<td>
				<s:input type="text" id="txtAccountNo" path="strAccountNo" cssClass="longTextBox" />
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Conversion To Base</label>
				</td>
				<td>
				<s:input type="text" id="txtConvToBaseCurr" path="dblConvToBaseCurr" cssClass="longTextBox" />
				</td>
			<td>
					<label>Sub Unit</label>
				</td>
				<td>
				<s:input type="text" id="txtSubUnit" path="strSubUnit" cssClass="longTextBox" />
				</td>
			</tr>
			
			
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
