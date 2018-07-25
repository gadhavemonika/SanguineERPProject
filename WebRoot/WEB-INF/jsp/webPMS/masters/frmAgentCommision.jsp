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
				$("#txtFromDate").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				$("#txtFromDate").datepicker('setDate', pmsDate);

				$("#txtToDate").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				$("#txtToDate").datepicker('setDate', pmsDate);

				
			});

	function funSetData(code){

		switch(fieldName){

			case 'AgentCommCode' : 
				funSetAgentCommCode(code);
				break;
		}
	}


	function funSetAgentCommCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAgentCommCode.html?agentCommCode=" + code,
			dataType : "json",
			success: function(response)
	        {
				
	        	if(response.strAgentCommCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtAgentCommCode").val('');
	        	}
	        	else
	        	{					        	    	        		
	        		$("#txtAgentCommCode").val(response.strAgentCommCode);
	        		var fromDate=response.dteFromDate;
	        		var frmDate= fromDate.split(' ');
	        	    var fDate = frmDate[0];
	        	    $("#txtFromDate").val(fDate);
	        	    var toDate1=response.dteToDate;
	        		var toDate= toDate1.split(' ');
	        	    var tDate = toDate[0];
	        		$("#txtToDate").val(tDate);
	        		$("#txtDescription").val(response.strCalculatedOn);
	        		$("#txtCommisionPaid").val(response.strCommisionPaid);
	        		$("#txtCorporateCode").val(response.strCommisionOn);
	        		
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
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
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
	<label>AgentCommision</label>
	</div>

<br/>
<br/>

	<s:form name="AgentCommision" method="POST" action="saveAgentCommision.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>AgentCommCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtAgentCommCode" path="strAgentCommCode" cssClass="searchTextBox" ondblclick="funHelp('AgentCommCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>FromDate</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtFromDate" path="dteFromDate" cssClass="calenderTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>ToDate</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtToDate" path="dteToDate" cssClass="calenderTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>CalculatedOn</label>
				</td>
				<td>
					<s:select id="txtCalculatedOn" path="strCalculatedOn"   cssClass="BoxW124px">
				    <s:option value="Revenue">Revenue</s:option>
				    <s:option value="Revenue">Revenue</s:option>
				    <s:option value="Revenue">Revenue</s:option>
				    </s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>CommisionPaid</label>
				</td>
				<td>
					<s:select id="txtCommisionPaid" path="strCommisionPaid"  cssClass="BoxW124px">
				    <s:option value="Daily">Daily</s:option>
				 </s:select>
				 
				</td>
			</tr>
			<tr>
				<td>
					<label>CommisionOn</label>
				</td>
				<td>
					<s:select id="txtCommisionOn" path="strCommisionOn"   cssClass="BoxW124px">
				     <s:option value="Daily">Room</s:option>
				</s:select>
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
