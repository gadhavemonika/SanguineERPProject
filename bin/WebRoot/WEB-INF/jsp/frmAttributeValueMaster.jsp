<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ATTRIBUTE VALUE MASTER</title>
	
	<script type="text/javascript">
	
		var fieldName1;
		
		function funResetFields()
		{		
	        document.getElementById("attValueCode").value="";
	        document.getElementById("attValueName").value="";
	        document.getElementById("attCode").value="";
	        document.getElementById("attValueDesc").value="";
	    }
		
		
	
		function funHelp(transactionName)
		{
			fieldName1=transactionName;
			
			if(fieldName1=='attributevaluemaster')
			{
				gurl=getContextPath()+"/loadAttributeValueMasterData.html?attValueCode=";
			}
			else
			{
				gurl=getContextPath()+"/loadAttributeMasterData.html?attCode=";				
			}
			
	        window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			if(fieldName1=='attributevaluemaster')
			{
				document.getElementById("attValueCode").value=code;
			}
			else
			{
				document.getElementById("attCode").value=code;
			}			
			
			 $.ajax({
				        type: "GET",
				        url: gurl+code,
				        dataType: "json",
				        success: function(response)
				        {				        	
				        	if(fieldName1=='attributevaluemaster')
							{
								document.getElementById("attValueName").value=response.strAVName;
								document.getElementById("attValueDesc").value=response.strAVDesc;
						        document.getElementById("attCode").value=response.strACode;
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
	<s:form name="attributevaluemasterForm" method="POST" action="saveAttributeValueMaster.html">
		<table>
		<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		    <tr>
		        <td><s:label path="strAVCode" >Attribute Value Code:</s:label></td>
		        <td><s:input id="attValueCode" name="attValueCode" path="strAVCode" ondblclick="funHelp('attributevaluemaster')" readonly="true"/></td>
		    </tr>
		    	
		    <tr>
		        <td>
		        	<s:label path="strAVName">Name:</s:label>
		        </td>
		        <td>
		        	<s:input type="text" id="attValueName" name="attValueName" path="strAVName" required="true"/>
		        	<s:errors path="strAVName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td>
			    	<s:label path="strAVCode">Attribute code:</s:label>
			    </td>
			    
			    <td>
			    	<s:input typr="text" id="attCode" name="attCode" path="strACode"  ondblclick="funHelp('attributemaster')" required="true"/>
			    	<s:errors path="strACode"></s:errors>
			    </td>
			    
			  <tr> 
			    <td><s:label path="strAVDesc" >Description :</s:label></td>
		        <td><s:input id="attValueDesc" name="attValueDesc" path="strAVDesc" /></td>
			</tr>
			   
			<tr>
			    <td ><input type="submit" value="Submit" onclick="return funValidateFields()"/></td>
			    <td ><input type="reset" value="RESET" onclick="funResetFields()"/></td>
		    </tr>
		</table>
	</s:form>
</body>
</html>