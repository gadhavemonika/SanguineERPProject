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
	
	
	function funSetRemarkData(remarkCode)
	 {
	     $("#txtRemarkCode").val(remarkCode);
			var searchurl=getContextPath()+"/loadNarrationMasterData.html?remarkCode="+remarkCode;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strRemarkCode=='Invalid Code')
				        	{
				        		alert("Invalid Remark Code");
				        		$("#txtRemarkCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtDescription").val(response.strDescription);
					        	$("#txtDescription").focus();		
					        	if(response.strActiveYN=="Yes")
					            {
					        	    $("#chkActiveYN").prop('checked',true);	
					        	    $("#chkActiveYN").val("Yes");
					        	}
					        	else
					        	{
					        	    $("#chkActiveYN").prop('checked',false);
					        	    $("#chkActiveYN").val("No");
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

	function funSetData(code){

		switch(fieldName)
		{		
			case "remarkCode":
			    funSetRemarkData(code);			    
				 break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funSetActiveYN()
	{
	    var activeYN="No";
	    if($("#chkActiveYN").prop("checked") == true)
	    {           
	        activeYN="Yes";
        }
	    if($("#chkActiveYN").prop("checked") == false)
        {         
	        activeYN="No";
        }
	    $("#chkActiveYN").val(activeYN);	
	    	   
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Remark Master</label>
	</div>

<br/>
<br/>

	<s:form name="RemarkMaster" method="POST" action="saveNarrationMaster.html">

		<table class="masterTable">
			<tr>
			    <td><label >Remark Code</label></td>
			    <td><s:input id="txtRemarkCode" path="strRemarkCode" readonly="true" ondblclick="funHelp('remarkCode')" cssClass="searchTextBox"/></td>			        			        			    			    		        			  
			</tr>
			<tr>
				<td><label >Description</label></td>
				<td ><s:input id="txtDescription" path="strDescription" required="true" cssClass="longTextBox"/></td>
			</tr>
			<tr>
				<td><label >Active</label></td>
				<td><s:checkbox id="chkActiveYN"  path="strActiveYN" value=""  onclick="funSetActiveYN()"  /></td>
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
