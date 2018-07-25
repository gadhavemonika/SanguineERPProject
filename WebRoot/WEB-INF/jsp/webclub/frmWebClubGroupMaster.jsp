<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
	
	<script type="text/javascript">
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
	 
	 function funHelp(transactionName)
		{	       
	     //   window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
	 
	 function funResetFields()
		{
			location.reload(true); 
		}
	 
	 function funSetData(code)
		{
			$("#txtGroupCode").val(code);
			var searchurl=getContextPath()+"/loadWebClubGroupMasterData.html?groupCode="+code;
			//alert(searchurl);
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strGCode=='Invalid Code')
				        	{
				        		alert("Invalid Group Code");
				        		$("#txtGroupCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtGroupCode").val(code);
					        	$("#txtGroupName").val(response.strGroupName);
					        	$("#txtShortName").val(response.strShortName);
					        	$("#cmbCategory").val(response.strCategory);
					        	$("#cmbDefaultType").val(response.strCrDr);
					        	$("#txtGroupName").focus();
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
		
	 
	 
	 
	 
	 
</script>
</head>
<body >
<div id="formHeading">
	<label>Group Master</label>
	</div>
	<div>
	<s:form name="frmGroupMaster" action="savefrmWebClubGroupMaster.html?saddr=${urlHits}" method="POST">
		<br>
		<table class="masterTable">
			<tr>
				<td width="16%">Group Code</td>
				<td width="17%"><s:input id="txtGroupCode"   path="strGroupCode"	cssClass="searchTextBox" ondblclick="funHelp('WCgroup')"/></td>
			
				<td><label>Group Name</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:input type="text" id="txtGroupName" 
						name="txtGroupName" path="strGroupName" required="true"
						cssStyle="width:53% ; text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			
			<tr>
					<td><label>Short Name</label></td>
					<td colspan="2"><s:input  type="text" id="txtShortName" 
						name="txtChangeDependentCode" path="strShortName" required="true"
						cssStyle="searchTextBox; width:16% " cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			<tr>
					<td><label>Category</label></td>
					<td colspan="2"><s:select id="cmbCategory" name="cmbCategory" path="strCategory" cssClass="BoxW124px" >
					 <option value="Income">Income</option>
		 				 <option value="cash Balance">cash Balance</option>
				 </s:select></td>
			</tr>
			<tr>
					<td><label>Default Type</label></td>
					<td colspan="2"><s:select id="cmbDefaultType" name="cmbDefaultType" path="strCrDr" cssClass="BoxW124px" >
					 <option value="Cash">Cash</option>
		 				 <option value="Credit">Credit</option>
				 </s:select></td>
			</tr>
		 
		 
		 </table>
		 
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="reset"
				value="Reset" class="form_button" onclick="funResetField()" />
		</p>
		<br><br>
	
	</s:form>
</div>

	
</body>




</html>