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

			case 'BookingTypeCode' : 
				funSetBookingTypeCode(code);
				break;
		}
	}


	function funSetBookingTypeCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadBookingTypeCode.html?bookingType=" + code,
			dataType : "json",
			success: function(response)
	        {
				
	        	if(response.strBookingTypeCode=='Invalid Code')
	        	{
	        		alert("Invalid Agent Code");
	        		$("#txtBookingTypeCode").val('');
	        	}
	        	else
	        	{					        	    	        		
	        		$("#txtBookingTypeCode").val(response.strBookingTypeCode);
	        	    $("#txtBookingTypeDesc").val(response.strBookingTypeDesc);
	        
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
			if (test) {
			%>	
				alert("Data Save successfully\n\n"+message);
			<%
			}
		}%>
	});



	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
	/**
	 *  Check Validation Before Saving Record
	 **/
	function funCallFormAction(actionName,object) 
	{
		var flg=true;
		
		if($('#txtBookingTypeDesc').val()=='')
		{
			 alert('Enter Booking Type Description');
			 flg=false;
		}			
		return flg;
	}
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Booking Type</label>
	</div>

<br/>
<br/>

	<s:form name="BookingType" method="POST" action="saveBookingType.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>Booking Type Code</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtBookingTypeCode" path="strBookingTypeCode" cssClass="searchTextBox" ondblclick="funHelp('BookingTypeCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Booking Type Desc</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtBookingTypeDesc" path="strBookingTypeDesc" cssClass="longTextBox" />
				</td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funCallFormAction('submit',this);"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
