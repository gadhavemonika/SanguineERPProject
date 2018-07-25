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

	$(document).ready(function() 
	{
		
		$("#txtPermitExp").datepicker({
				dateFormat : 'dd-mm-yy'
			});
		$("#txtPermitExp").datepicker('setDate', 'today');
		
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
					alert("Permit Data Save successfully\n\n"+message);
		<%
		}}%>
			
			$('#txtPermitCode').blur(function () 
				{
				 var code=$('#txtPermitCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){							   
					 funSetPermitData(code);
				   }
				});

	});

	function funSetData(code) {

		switch (fieldName) {

		case 'excisePermitCode':
			funSetPermitData(code);
			break;
		}
	}

	function funSetPermitData(code)
	{
		$("#txtPermitName").focus();
		var gurl=getContextPath()+"/loadExcisePermitMaster.html?permitCode=";
		$.ajax({
	        type: "POST",				        
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {	
	        	if(response.strPermitCode=='Invalid Code')
	        	{
	        		alert("Invalid Permit Code Please Select Again");
	        			$("#txtPermitCode").val('');
	        			$("#txtPermitCode").focus();
	        	}
	        	else
	        	{
	        		$("#txtPermitCode").val(response.strPermitCode);
	        		$("#txtPermitName").val(response.strPermitName);
	        		$("#txtPermitNo").val(response.strPermitNo);
	        		
	        		if(response.dtePermitExp=='Life Time'){
	        			$("#txtLifeTime").prop('checked','true')
	        		}else{
	        			$("#txtPermitExp").val(response.dtePermitExp);
	        		}
	        		
	        		$("#txtPermitType").val(response.strPermitType);
	        		
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
	function funHelp(transactionName) {
		fieldName = transactionName;
// 		window.showModalDialog("searchform.html?formname=" + transactionName
// 				+ "&searchText=", "",
// 				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	
		window.open("searchform.html?formname=" + transactionName
				+ "&searchText=", "",
				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");

	
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Excise Permit Master</label>
	</div>

<br/>
<br/>

	<s:form name="excisePermitMaster" method="POST" action="saveExcisePermitMaster.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>Permit Code</label>
				</td>
				<td>
					<s:input type="text" id="txtPermitCode" path="strPermitCode" cssClass="searchTextBox" ondblclick="funHelp('excisePermitCode');"/>
				</td>
				<td colspan="3"></td>
			</tr>
			<tr>
				<td>
					<label>Permit Name</label>
				</td>
				<td colspan="3">
					<s:input  type="text" id="txtPermitName" required="true" path="strPermitName" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Permit No</label>
				</td>
				<td>
					<s:input type="text" id="txtPermitNo" required="true" path="strPermitNo" cssClass="longTextBox" />
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td>
					<label>Permit Exp Date</label>
				</td>
				<td>
					<s:input  type="text" id="txtPermitExp" path="dtePermitExp" cssClass="calenderTextBox" />
				</td>
				
				<td>Is Lift Time</td>
				<td colspan="3">
					<s:input  type="checkbox" id="txtLifeTime" path="isLifeTime" value="Life Time" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Permit Type</label>
				</td>
				<td>
					<s:select id="txtPermitType" items="${permitType}" path="strPermitType" cssClass="BoxW124px" >
					</s:select>
				</td>
				<td colspan="3"></td>
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
