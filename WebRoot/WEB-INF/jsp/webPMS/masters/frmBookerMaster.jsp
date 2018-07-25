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

			case 'BookerCode' : 
				funSetBookerCode(code);
				break;
		}
	}


	function funSetBookerCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadBookerCode.html?bookerCode=" + code,
			dataType : "json",
			success: function(response)
	        {
				
	        	if(response.strBookerCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtBookerCode").val('');
	        	}
	        	else
	        	{					        	    	        		
	        		$("#txtBookerCode").val(response.strBookerCode);
	        		$("#txtBookerName").val(response.strBookerName);
	        		$("#txtAddress").val(response.strCommisionPaid);
	        		$("#txtAddress").val(response.strAddress);
	        		$("#txtCity").val(response.strCity);
	        		$("#txtState").val(response.strState);
	        		$("#txtCountry").val(response.strCountry);
	        		$("#txtMobileNo").val(response.lngMobileNo);
	        		$("#txtTelephoneNo").val(response.lngTelephoneNo);
	        		$("#txtEmailId").val(response.strEmailId);
	        		
	        		if(response.strBlackList=='Y')
			    	{
			    		document.getElementById("chkBlackList").checked=true;
			    	}
			    	else
			    	{
			    		document.getElementById("chkBlackList").checked=false;
			    	}
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
	<label>BookerMaster</label>
	</div>

<br/>
<br/>

	<s:form name="BookerMaster" method="POST" action="saveBookerMaster.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>BookerCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtBookerCode" path="strBookerCode" cssClass="searchTextBox" ondblclick="funHelp('BookerCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>BookerName</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtBookerName" path="strBookerName" cssClass="longTextBox" />
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
					<label>MobileNo</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtMobileNo" path="lngMobileNo" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>TelephoneNo</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtTelephoneNo" path="lngTelephoneNo" cssClass="longTextBox" />
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
			<tr>
				<td><s:checkbox label="Black List this corporate"  id="chkBlackList" path="strBlackList" value="" /></td>
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
