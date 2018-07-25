<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Floor Master</title>

	<script type="text/javascript">

		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{
			window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		    //window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		
		function funSetData(code)
		{
			$("#txtFloorCode").val(code);
			var searchurl=getContextPath()+"/loadFloorMasterData.html?floorCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strFloorCode=='Invalid Code')
				        	{
				        		alert("Invalid Business Code");
				        		$("#txtFloorCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtFloorName").val(response.strFloorName);
					        	$("#txtFloorAmt").val(response.dblFloorAmt);
					        	
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
	
		 /**
		 *  Check Validation Before Saving Record
		 **/
				function funCallFormAction(actionName,object) 
				{
					var flg=true;
					if($('#txtFloorName').val()=='')
					{
						 alert('Enter Floor Name');
						 flg=false;
						  
					}
					return flg;
				}
			
		
		
	</script>	

</head>
<body>

	<div id="formHeading">
		<label>Floor Master</label>
	</div>
		<br/>
		<br/>
		<s:form name="Floor" method="GET" action="saveFloorMaster.html?" >
	
			<table class="masterTable">

				<tr>
			   		 <td><label>Floor Code</label></td>
					 <td>
					 <s:input id="txtFloorCode" type="text" path="strFloorCode" cssClass="searchTextBox" ondblclick="funHelp('floormaster')" /></td>				
				</tr>
			
				<tr>
				    <td><label>Floor Name</label></td>
					<td><s:input id="txtFloorName" path="strFloorName" cssClass="longTextBox" /></td>				
				</tr>
			
				<tr>
					 <td><label>Amount</label></td>
					 <td ><s:input id="txtFloorAmt" path="dblFloorAmt" cssClass="longTextBox" /></td>
				</tr>
			
			</table>
		
		
			<br />
			<br />
			
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funCallFormAction('submit',this);" />
            <input type="reset" value="Reset" class="form_button" />
		</p>
		
	</s:form>

</body>
</html>