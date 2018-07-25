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

			case 'AgentCode' : 
				funSetAgentCode(code);
				break;
			case 'AgentCommCode' : 
				$("#txtAgentCommCode").val(code);
// 				funSetAgentCommCode(code);
				break;
			case 'CorporateCode' :
				$("#txtCorporateCode").val(code);
// 				funSetCorporateCode(code);
				break;
		}
	}

	
	




	function funSetAgentCode(code){
	 
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAgentCode.html?agentCode=" + code,
			dataType : "json",
		
			success: function(response)
	        {
				
	        	if(response.strAgentCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtAgentCode").val('');
	        	}
	        	else
	        	{					        	    	        		
	        		$("#txtAgentCode").val(response.strAgentCode);
	        		var fromDate=response.dteFromDate;
	        		var frmDate= fromDate.split(' ');
	        	    var fDate = frmDate[0];
	        	    $("#txtFromDate").val(fDate);
	        	    var toDate1=response.dteToDate;
	        		var toDate= toDate1.split(' ');
	        	    var tDate = toDate[0];
	        		$("#txtToDate").val(tDate);
	        		$("#txtDescription").val(response.strDescription);
	        		$("#txtAgentCommCode").val(response.strAgentCommCode);
	        		$("#txtCorporateCode").val(response.strCorporateCode);
	        		$("#txtAdvToReceive").val(response.dblAdvToReceive);
	        		$("#txtAddress").val(response.strAddress);
	        		$("#txtCity").val(response.strCity);
	        		$("#txtState").val(response.strState);
	        		$("#txtCountry").val(response.strCountry);
	        		$("#txtTelphoneNo").val(response.lngTelphoneNo);
	        		$("#txtMobileNo").val(response.lngMobileNo);
	        		$("#txtFaxNo").val(response.lngFaxNo);
	        		$("#txtEmailId").val(response.lngMobileNo);
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
	<label>AgentMaster</label>
	</div>

<br/>
<br/>

	<s:form name="AgentMaster" method="POST" action="saveAgentMaster.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>AgentCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtAgentCode" path="strAgentCode" cssClass="searchTextBox" ondblclick="funHelp('AgentCode');"/>
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
					<label>Description</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtDescription" path="strDescription" cssClass="longTextBox" />
				</td>
			</tr>
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
					<label>CorporateCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtCorporateCode" path="strCorporateCode" cssClass="searchTextBox" ondblclick="funHelp('CorporateCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>AdvToReceive</label>
				</td>
				<td>
					<s:input colspan="3" type="number" step="0.01" id="txtAdvToReceive" path="dblAdvToReceive" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Address</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtAddress" path="strAddress" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>City</label>
				</td>
				<td>
					<s:select id="txtCity" path="strCity" items="${cityArrLsit}" cssClass="BoxW124px"/>
									
				</td>
			</tr>
			<tr>
				<td>
					<label>State</label>
				</td>
				<td>
					<s:select id="txtState" path="strState"  items="${stateArrLsit}" cssClass="BoxW124px"/>
				
				</td>
			</tr>
			<tr>
				<td>
					<label>Country</label>
				</td>
				<td>
					<s:select id="txtCountry" path="strCountry" items="${countryArrLsit}" cssClass="BoxW124px"/>
					
					
				</td>
			</tr>
			<tr>
				<td>
					<label>TelphoneNo</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtTelphoneNo" path="lngTelphoneNo" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>MobileNo</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtMobileNo" path="lngMobileNo" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>FaxNo</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtFaxNo" path="lngFaxNo" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>EmailId</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtEmailId" path="strEmailId" cssClass="longTextBox" />
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
