<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Process Master</title>
<script type="text/javascript">
	var fieldName;


	
	/**
	* Open Help
	**/
	function funHelp(transactionName)
	{	       
       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }

	/**
	* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
	**/
	function funSetData(code)
	{
		$("#txtProcessCode").val(code);
		var searchurl=getContextPath()+"/loadProcessData1.html?processCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strProcessCode=='Invalid Code')
			        	{
			        		alert("Invalid Process Code");
			        		$("#txtProcessCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtProcessName").val(response.strProcessName);
				        	$("#txtDesc").val(response.strDesc);
				        	$("#txtProcessName").focus();
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
	* Reset The Process Name TextField
	**/
	function funResetFields()
	{
		$("#txtProcessCode").focus();
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
	
	
	 $(function()
				{
	
		/**
		* On Blur Event on TextField
		**/
		$('#txtProcessCode').blur(function() 
		{
				var code = $('#txtProcessCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/")
				{				
					funSetData(code);							
				}
		});
		$('#txtProcessName').blur(function () {
			 var strProcessName=$('#txtProcessName').val();
		      var st = strProcessName.replace(/\s{2,}/g, ' ');
		      $('#txtProcessName').val(st);
			});
		
		
	
	 });
	
	 
</script>

</head>
<body>

	<div id="formHeading">
	<label>ProcessMaster</label>
	</div>

<br/>
<br/>

	<s:form name="ProcessMaster" method="POST" action="saveProcessMaster.html">

		<table class="masterTable">
		
		 
			<tr>
				<td width="140px">Process Code</td>
				<td><s:input id="txtProcessCode" path="strProcessCode"
						cssClass="searchTextBox" ondblclick="funHelp('processcode')" /></td>
			</tr>
			<tr>
				<td><label>Name</label></td>
				<td><s:input colspan="3" type="text" id="txtProcessName" 
						name="txtProcessName" path="strProcessName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>
			</tr>
			<tr>
				<td><label>Description</label></td>
				<td><s:input colspan="3" id="txtDesc" name="txtDesc"
						cssStyle="text-transform: uppercase;" path="strDesc" cssClass="longTextBox" autocomplete="off" /> </td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
	</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" 
			onclick="return funCallFormAction('submit',this);"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
