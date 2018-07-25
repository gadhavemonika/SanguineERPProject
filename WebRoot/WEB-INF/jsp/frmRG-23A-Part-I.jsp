<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html.dtd">
<html>
<head>
<script>

		$(document).ready(function(){
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
			$("#dtFromDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#dtFromDate").datepicker('setDate', Dat);	
			
			
			$("#dtToDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#dtToDate").datepicker('setDate', 'today');	
			
			var code='<%=session.getAttribute("locationCode").toString()%>';
			funSetLocation(code);			
			
		});
		
		function funSetLocation(code)
		{
			$.ajax({
			        type: "GET",
			        url: getContextPath()+"/loadLocationMasterData.html?locCode="+code,
			        dataType: "json",
			        success: function(response)
			        {
				       	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#txtLocCode").val('');
				       	}
				       	else
				       	{
				       		$("#txtLocCode").val(response.strLocCode);
					       	$("#lblLocName").text(response.strLocName);
					       
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
		
		
		function funSetData(code)
		{			
			switch (fieldName) 
			{			   
			   case 'locationmaster':
			    	funSetLocation(code);
			        break;
			}
		}	
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			
			//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		


</script>
<body>
<div id="formHeading">
		<label>FORM R.G.-23-A-Part I</label>
	</div>
	<br />
	<br />
		<s:form name="frmRG-23A-Part-I" method="GET" action="rptRG-23A-Part-I.html" target="_blank" >

			<table class="transTable">
		
			<tr>
				<td><label>From Date</label></td>
				<td><input type="text" id="dtFromDate" name="dtFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><input type="text" id="dtToDate" name="dtToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
			
			<tr>
			
				<td><label>Location Code</label></td>
				<td><s:input id="txtLocCode" name="txtLocCode" path="strLocationCode" ondblclick="funHelp('locationmaster')"  cssClass="searchTextBox"/></td>
				<td colspan="2"><label id="lblLocName"></label></td>
			
			</tr>
		</table>
			<p align="center">
				<input type="submit" value="Export"  class="form_button" onclick="return funCallFormAction('submit',this)" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
		</s:form>
</body>
</html>