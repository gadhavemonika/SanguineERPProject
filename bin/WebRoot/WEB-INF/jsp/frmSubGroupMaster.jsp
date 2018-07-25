<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SUB GROUP MASTER</title>	
	<script type="text/javascript">
	
		var fieldName1;
		
		function funResetFields()
		{		
	        document.getElementById("subgroupCode").value="";
	        document.getElementById("subgroupName").value="";
	        document.getElementById("subgroupDesc").value="";
	        document.getElementById("groupCode").value="";
	    }
	
		
	
		function funHelp(transactionName)
		{
			fieldName1=transactionName;			
			if(fieldName1=='subgroup')
			{
				gurl=getContextPath()+"/loadSubGroupMasterData.html?subGroupCode=";
			}
			else
			{
				gurl=getContextPath()+"/loadGroupMasterData.html?groupCode=";				
			}			
	        window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			if(fieldName1=='subgroup')
			{
				document.getElementById("subgroupCode").value=code;
			}
			else
			{
				document.getElementById("groupCode").value=code;
			}
			
			 $.ajax({
				        type: "GET",
				        url: gurl+code,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(fieldName1=='subgroup')
							{
								document.getElementById("subgroupName").value=response.strSGName;
								document.getElementById("subgroupDesc").value=response.strSGDesc;
						        document.getElementById("groupCode").value=response.strGCode;
							}
				        	
						},
				        error: function(e)
				        {				        	
				          	alert('Error121212: ' + e);
				        }
			      });
		}
	</script>

</head>
<body onload="funResetFields()">
	<s:form name="subgrpForm" method="POST" action="saveSubGroupMaster.html">
		<table>
		<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		    <tr>
		        <td><s:label path="strSGCode" >Sub-Group Code:</s:label></td>
		        <td><s:input id="subgroupCode" name="subgroupCode" path="strSGCode" ondblclick="funHelp('subgroup')" readonly="true"/></td>
		    </tr>
		    	
		    <tr>
		        <td>
		        	<s:label path="strSGName">Sub-Group Name:</s:label>
		        </td>
		        <td>
		        	<s:input id="subgroupName" name="subgroupName" path="strSGName" required="true" />
		        	<s:errors path="strSGName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td>
			    	<s:label path="strSGDesc">Description:</s:label>
			    </td>
			    
			    <td>
			    	<s:input id="subgroupDesc" name="subgroupDesc" path="strSGDesc" />
			    	<s:errors path="strSGDesc"></s:errors>
			    </td>
			    
			  <tr> 
			    <td><s:label path="strGCode" >Group Code:</s:label></td>
		        <td><s:input type="text" id="groupCode" name="groupCode" path="strGCode"   ondblclick="funHelp('group')" required="true"/></td>
			</tr>
			   
			<tr>
			    <td ><input type="submit" value="Submit" onclick="Return funValidateFields()()"/></td>
			    <td ><input type="button" value="RESET" onclick="funResetFields()"/></td>
		    </tr>
		</table>
	</s:form>
</body>
</html>