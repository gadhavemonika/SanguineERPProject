<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
	<script type="text/javascript">
				
		function funResetFields()
		{
			$("#txtGroupName").focus();
	    }
		
		function funHelp(transactionName)
		{
	        window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			$("#txtGroupCode").val(code);
			
			 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/loadGroupMasterData.html?groupCode="+code,
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
					        	$("#txtGroupName").val(response.strGName);
					        	$("#txtGroupDesc").val(response.strGDesc);
				        	}
						},
				        error: function(e)
				        {
				          	alert('Error121212: ' + e);
				        }
			      });
		}
		
		$(function()
		{
			$("#txtGroupCode").blur(function()
			{
				if(!$("#txtGroupCode").val()=='')
				{
					funSetData($("#txtGroupCode").val());
				}					
			});
		    
			$('a#baseUrl').click(function() 
			{
				if($("#txtGroupCode").val()=="")
				{
					alert("Please Select Group Code");
					return false;
				}
			    $(this).attr('target', '_blank');
			});
		});

	</script>
</head>

<body onload="funResetFields()">
	<s:form name="grpForm" method="POST" action="saveGroupMaster.html">
		<table>
			<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		
		    <tr>
		        <td><s:label path="strGCode" >GroupCode:</s:label></td>
		        <td><s:input id="txtGroupCode" name="txtGroupCode" path="strGCode" value="" ondblclick="funHelp('group')"/></td>
		    </tr>
		    	
		    <tr>
		        <td>
		        	<s:label path="strGName">Group Name:</s:label>
		        </td>
		        <td>
		        	<s:input type="text" id="txtGroupName" name="txtGroupName" path="strGName" tabindex="1"  required="true"/>
		        	<s:errors path="strGName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td>
			    	<s:label path="strGDesc">Description:</s:label>
			    </td>
			    <td>
			    	<s:input id="txtGroupDesc" name="txtGroupDesc" path="strGDesc" tabindex="2"/>
			    	<s:errors path="strGDesc"></s:errors>
			    </td>
			</tr>
			    
			<tr>
			    <td colspan="1"><input type="submit" value="Submit" tabindex="3" onclick="return funValidateFields()"/></td>
			    <td colspan="1"><input type="reset" value="Reset" /></td>
		    </tr>
		</table>
	</s:form>
</body>
</html>