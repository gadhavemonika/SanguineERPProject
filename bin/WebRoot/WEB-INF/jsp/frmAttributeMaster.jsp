<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ATTRIBUTE VALUE MASTER</title>

	<script type="text/javascript">
	
		var fieldName1;
		
		function funResetFields()
		{		
	        document.getElementById("attCode").value="";
	        document.getElementById("attName").value="";
	        document.getElementById("attType").value="";
	        document.getElementById("attDesc").value="";
	        document.getElementById("pAttCode").value="";
	    }
		
		function funHelp(transactionName)
		{
			fieldName1=transactionName;
			
			if(fieldName1=='attributemaster')
			{
				gurl=getContextPath()+"/loadAttributeMasterData.html?attCode=";
			}
	        window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			if(fieldName1=='attributemaster')
			{				
				document.getElementById("attCode").value=code;
			}
			
			 $.ajax({
				        type: "GET",				        
				        url: gurl+code,
				        dataType: "json",
				        success: function(response)
				        {	
				        	if(fieldName1=='attributemaster')
							{
				        		document.getElementById("attName").value=response.strAttName;
								document.getElementById("attType").value=response.strAttType;
								document.getElementById("attDesc").value=response.strAttDesc;
						        document.getElementById("pAttCode").value=response.strPAttCode;
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
	<s:form name="attributemasterForm" method="POST" action="saveAttributeMaster.html">
		<table>
		<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		    <tr>
		        <td><s:label path="strAttCode" >Attribute Code:</s:label></td>
		        <td><s:input id="attCode" name="attCode" path="strAttCode" ondblclick="funHelp('attributemaster')" readonly="true"/></td>
		    </tr>
		    	
		    <tr>
		        <td>
		        	<s:label path="strAttName">Name:</s:label>
		        </td>
		        <td>
		        	<s:input type="text" id="attName" name="attName" path="strAttName" required="true" />
		        	<s:errors path="strAttName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td>
			    	<s:label path="strAttType">Attribute Type:</s:label>
			    </td>
			    
			    <td>
			    	<s:select id="attType" name="attType" path="strAttType" items="${typeList}"/>
			    	
			    	<s:errors path="strAttType"></s:errors>
			    </td>
			    
			  <tr> 
			    <td><s:label path="strAttDesc" >Description:</s:label></td>
		        <td><s:input id="attDesc" name="attDesc" path="strAttDesc" /></td>
			</tr>
			
			<tr> 
			    <td><s:label path="strPAttCode" >Parent Attribute Code:</s:label></td>
		        <td><s:input id="pAttCode" name="pAttCode" path="strPAttCode" /></td>
			</tr>
			   
			<tr>
			    <td ><input type="submit" value="Submit"/></td>
			    <td ><input type="reset"  value="RESET" /></td>
		    </tr>
		</table>
	</s:form>
</body>
</html>