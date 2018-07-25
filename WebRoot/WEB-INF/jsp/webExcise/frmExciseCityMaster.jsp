<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
var fieldName;

function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		
	}

		function funSetData(code)
		{
			switch (fieldName) 
			{
			   case 'CityCode':
			    	funSetCityData(code);
			        break;
			}
		}
		
		function funSetCityData(code)
		{
			$("#txtCityName").focus();
			var gurl=getContextPath()+"/loadExciseCityMasterData.html?cityCode=";
			$.ajax({
		        type: "GET",				        
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {	
		        	if(response.strCityCode=='Invalid Code')
		        	{
		        		alert("Invalid City Code Please Select Again");
		        			$("#txtCityCode").val('');
		        			$("#txtCityName").focus();
		        	}
		        	else
		        	{
		        		$("#txtCityCode").val(response.strCityCode);
		        		$("#txtCityName").val(response.strCityName);
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
		$(function(){
			
			$("#txtCityName").focus();
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
		alert("City Data Save successfully\n\n"+message);
	<%
	}}%>
			
			$('#txtCityCode').blur(function () 
					{
					 var code=$('#txtCityCode').val();
					 if (code.trim().length > 0 && code !="?" && code !="/"){							   
						 funSetCityData(code);
					   }
					});
		});
</script>

</head>
<body>



	<div id="formHeading">
		<label>City Master</label>
	</div>

	<br />
	<br />

	<s:form name="CityMaster" method="POST" action="saveExciseCityMaster.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td><label>City Code</label></td>
				<td><s:input type="text" id="txtCityCode" path="strCityCode" cssClass="searchTextBox" ondblclick="funHelp('CityCode');" /></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><label>City Name</label></td>
				<td colspan="3">
				<s:input type="text" id="txtCityName" path="strCityName" cssClass="longTextBox" required="true" /></td>
			</tr>
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button"  />
		</p>
	</s:form>
</body>
</html>