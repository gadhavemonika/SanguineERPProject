<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Excise Master</title>
</head>
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
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funSetData(code)
	{
		switch (fieldName) 
		{
		   
		   case 'subgroupExcisable':
			   $("#txtsubGroup").val(code);
		        break;
		        
		   case 'exciseDuty':
			   funSetExciseData(code);
		        break;
		   
		}
		
	}
			
		
		


function funSetExciseData(code)
{
	$("#txtGroupCode").val(code);
	var searchurl=getContextPath()+"/loadExciseMasterData.html?exciseCode="+code;
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strExciseCode=='Invalid Code')
		        	{
		        		alert("Invalid Group Code");
		        		$("#txtExciseCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtExciseCode").val(response.strExciseCode);
		        		$("#txtDescription").val(response.strDesc);
		        		$("#txtExciseChapterNo").val(response.strExciseChapterNo);
		        		$("#txtsubGroup").val(response.strSGCode);
		        		$("#txtExcisePer").val(response.dblExcisePercent);
		        		
		        	if(response.strCessTax=='Y')
			    	{
			    		document.getElementById("chkCessTax").checked=true;
			    	}
			    	else
			    	{
			    		document.getElementById("chkCessTax").checked=false;
			    	}
		        		
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
<body >
	<div id="formHeading">
		<label>Excise Master</label>
	</div>
	<s:form name="frmExciseMaster" method="GET" action="saveExciseMaster.html?saddr=${urlHits}">

		<br />
		<br />
		
		
			
		<table class="masterTable">

			
			<tr>
				<td width="140px">ExciseCode Code</td>
				<td><s:input id="txtExciseCode" path="strExciseCode"
						cssClass="searchTextBox" ondblclick="funHelp('exciseDuty')" /></td>
			</tr>
			
			<tr>
				<td><label>Description</label></td>
				<td><s:input type="text" id="txtDesc" 
						name="txtDescription" path="strDesc" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
			</tr>
			<tr>
				<td><label>Excise Chapter No.</label></td>
				<td><s:input  type="text" id="txtGroupName" 
						name="txtExciseChapterNo" path="strExciseChapterNo" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>
			</tr>
			<tr>
				<td><label>Sub Group code</label></td>
				<td><s:input  id="txtsubGroup" cssClass="searchTextBox" path="strSGCode"   ondblclick="funHelp('subgroupExcisable')" /> </td>
			</tr>
			
			<tr>
				<td><label>Excise Percentage</label></td>
				<td><s:input colspan="3" id="txtExcisePer" name="txtExcisePer"
						cssStyle="text-transform: uppercase;" path="dblExcisePercent" cssClass="longTextBox"  /> </td>
			</tr>
			<tr>
			
			<td><s:checkbox label="Cess Tax"  id="chkCessTax" path="strCessTax" value="" /></td>
			</tr>
			
			<tr>
				<td><label>Category Sequence</label></td>
				<td><s:input colspan="3" id="txtRank" name="txtRank"
						cssStyle="text-transform: uppercase;" path="strRank" cssClass="longTextBox"  /> </td>
			</tr>
			<tr>
			
				<td></td>
			</tr>
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"
				onclick="return funCallFormAction('submit',this);" /> <input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>