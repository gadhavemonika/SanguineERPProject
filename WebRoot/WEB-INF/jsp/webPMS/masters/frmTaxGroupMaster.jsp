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
	
	function funSetTaxGroupData(taxGroupCode)
	{
		var loadTaxGroupUrl=getContextPath()+"/loadTaxGroupMasterData.html?taxGroupCode="+taxGroupCode;
		$.ajax({
			
			url:loadTaxGroupUrl,
			type:"GET",
			dataType:"json",
			 success: function(response)
		        {
		        	if(response.strTaxGroupCode=='Invalid Code')
		        	{
		        		alert("Invalid Tax Group Code");
		        		$("#strTaxGroupCode").val('');
		        	}
		        	else
		        	{					        	    		        		
		        		$("#strTaxGroupCode").val(response.strTaxGroupCode);
		        		$("#strTaxGroupDesc").val(response.strTaxGroupDesc);
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

	function funSetData(code)
	{
		switch(fieldName)
		{
			case "taxGroupCode":
				 funSetTaxGroupData(code);
				 break;
		}
	}
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
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
	<label>Tax Group Master</label>
	</div>

<br/>
<br/>

	<s:form name="TaxGroupMaster" method="POST" action="saveTaxGroupMaster.html">

		<table class="masterTable">
			<tr>
			    <td style="width: 85px"><label>Tax Group Code</label></td>
			    <td style="width: 85px"><s:input id="strTaxGroupCode" path="strTaxGroupCode"  ondblclick="funHelp('taxGroupCode')" cssClass="searchTextBox"/></td>			        			        
			    <td><s:input id="strTaxGroupDesc" path="strTaxGroupDesc" required="true" cssClass="longTextBox" /></td>			    		        			   
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
