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

	$(function() 
	{
	});

	function funSetData(code){

		switch(fieldName){

			case 'CityCode' : 
				funSetCityCode(code);
				break;
			case 'CountryCode' : 
				funSetCountryCode(code);
				break;
			case 'StateCode' : 
				funSetStateCode(code);
				break;
		}
	}


	function funSetCityCode(code){

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/loadWSCityCode.html?docCode=" + code,
			dataType : "json",
			success : function(response)
			{
	        	if(response.strCityCode=='Invalid Code')
	        	{
	        		alert("Invalid City Code");
	        		$("#txtCityCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCityCode").val(code);
		        	$("#txtCityName").val(response.strCityName);
		        	funSetStateCode(response.strStateCode);
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


	function funSetCountryCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadWSCountryCode.html?docCode=" + code,
			dataType : "json",
			success : function(response)
			{
	        	if(response.strCountryCode=='Invalid Code')
	        	{
	        		alert("Invalid State Code");
	        		$("#txtCountryCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCountryCode").val(code);
		        	$("#lblCountryName").text(response.strCountryName);
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


	function funSetStateCode(code){

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/loadWSStateCode.html?docCode=" + code,
			dataType : "json",
			success : function(response)
			{
	        	if(response.strStateCode=='Invalid Code')
	        	{
	        		alert("Invalid State Code");
	        		$("#txtStateCode").val('');
	        	}
	        	else
	        	{
		        	$("#txtStateCode").val(response.strStateCode);
		        	$("#lblStateName").text(response.strStateName);
		        	//$("#txtStateDesc").val(response.strStateDesc);
		        	funSetCountryCode(response.strCountryCode);
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







	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>City Master</label>
	</div>

<br/>
<br/>

	<s:form name="frmWSCityMaster" method="POST" action="saveWSCityMaster.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>City Code</label>
				</td>
				<td>
					<s:input colspan="4" type="text" id="txtCityCode" path="strCityCode" cssClass="searchTextBox" ondblclick="funHelp('CityCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>City Name</label>
				</td>
				<td>
					<s:input colspan="4" type="text" id="txtCityName" path="strCityName" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>State Code</label>
				</td>
				<td>
					<s:input  type="text" id="txtStateCode" path="strStateCode" cssClass="searchTextBox" ondblclick="funHelp('StateCode');"/>
				</td>
				<td colspan="3"><label id="lblStateName"></label></td>
				
			</tr>
			
			<tr>
				<td>
					<label>Country Code</label>
				</td>
				<td>
					<s:input  type="text" id="txtCountryCode" path="strCountryCode" cssClass="searchTextBox" ondblclick="funHelp('CountryCode');"/>
				</td>
				<td colspan="3"><label id="lblCountryName"></label></td>
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
