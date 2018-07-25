<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Person</title>
<script type="text/javascript">
	var fieldName;

	$(function() 
	{
	});

	function funSetData(code){

	funSetSalesPersonCode(code);
				
		
	}


	function funSetSalesPersonCode(code){

		$.ajax({
			type : "POST",
			url : getContextPath()+ "/loadSalesPerson.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strSalesPersonCode=='Invalid Code')
	        	{
	        		alert("Invalid Code");
	        		$("#txtSalesPersonCode").val('');
	        	}
	        	else
	        	{        
		        	$("#txtSalesPersonCode").val(response.strSalesPersonCode);
		        	$("#txtSalesPersonName").val(response.strSalesPersonName);
		        
		        
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
	function funResetFields()
	{
		location.reload(true); 
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Sales Person Master</label>
	</div>

<br/>
<br/>

	<s:form name="SalesPerson" method="POST" action="saveSalesPerson.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>Sales Person Code</label>
				</td>
				<td>
					<s:input type="text" id="txtSalesPersonCode" path="strSalesPersonCode" cssClass="searchTextBox" ondblclick="funHelp('salesPersonCode');"/>
				</td>
				<td>
					<label>Sales Person Name</label>
				</td>
				<td>
					<s:input type="text" id="txtSalesPersonName" path="strSalesPersonName" cssClass="longTextBox" />
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