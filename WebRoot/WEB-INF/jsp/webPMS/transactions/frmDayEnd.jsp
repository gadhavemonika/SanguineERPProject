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
		var startDay='<%=session.getAttribute("PMSStartDay").toString()%>';
		if(startDay=='N')
		{
			//$("#btnDayEnd").prop("disabled", true);
		}
		
		$("#btnStartDay").click(function()
		{
			funStartPMSDay();
		});
	});
		
	
	function funStartPMSDay()
	{
		var searchUrl=getContextPath()+"/startPMSDay.html?PMSDate="+$("#txtPMSDate").val();
		
		$.ajax({
	        type: "GET",
	        url: searchUrl,
	        dataType: "json",
	        success: function(response)
	        {
				if(response==1)
				{
					$("#btnDayEnd").prop("disabled", false);
					$("#btnStartDay").prop("disabled", true);
					alert("Day Started");
				}
			},
			error: function(jqXHR, exception) {
	            if (jqXHR.status === 0) {
	                $.jAlert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                $.jAlert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                $.jAlert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                $.jAlert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                $.jAlert('Time out error.');
	            } else if (exception === 'abort') {
	                $.jAlert('Ajax request aborted.');
	            } else {
	                $.jAlert('Uncaught Error.n' + jqXHR.responseText);
	            }		            
	        }
	  	});
	}
	
	
	
	function funCheckStartDay()
	{
		var startDay='<%=session.getAttribute("PMSStartDay").toString()%>';
		if(startDay=='N')
		{
			alert('Please Start Day!!!');
			return false;
		}
		else
		{
			return true;
		}
	}

	


	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
		<label>DayEnd</label>
	</div>
<br/>
<br/>

	<s:form name="DayEnd" method="POST" action="dayEndProcess.html">

		<table class="masterTable">
			<tr>
				<td><label id="lblDate">PMS Date</label>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><s:input style="width: 17%;" id="txtPMSDate" path="dtePMSDate" readonly="true" value="${PMSDate}" cssClass="longTextBox"/></td>
				<!-- <td><s:input type="text" id="txtPMSDate" path="dtePMSDate" required="true" readonly="true" class="calenderTextBox" /></td> -->
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			
			<!--  
			<input type="button" value="Start Day" id="btnStartDay" tabindex="3" class="form_button" />
			-->
			<input type="submit" value="Day End" id="btnDayEnd" tabindex="3" class="form_button"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>
</body>
</html>
