<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOCATION MASTER</title>	
	<script type="text/javascript">
	
		var fieldName1;
		
		function funResetFields()
		{
	        document.getElementById("locCode").value="";
	        document.getElementById("locName").value="";
	        document.getElementById("locDesc").value="";
	        document.getElementById("avlForSale").value="";
	        document.getElementById("active").value="";
	        document.getElementById("pickable").value="";
	        document.getElementById("receivable").value="";
	        document.getElementById("exciseNo").value="";
	        document.getElementById("type").value="";
	        document.getElementById("propertyCode").value="";
	        document.getElementById("externalCode").value="";
	    }
		
		
		
		function funHelp(transactionName)
		{
			fieldName1=transactionName;
			
			if(fieldName1=='locationmaster')
			{
				gurl=getContextPath()+"/loadLocationMasterData.html?locCode=";
			}
	        window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			if(fieldName1=='locationmaster')
			{
				document.getElementById("locCode").value=code;
			}
			
			 $.ajax({
				        type: "GET",
				        url: gurl+code,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(fieldName1=='locationmaster')
							{
								document.getElementById("locName").value=response.strLocName;
								document.getElementById("locDesc").value=response.strLocDesc;
						        document.getElementById("type").value=response.strType;
						        document.getElementById("propertyCode").value=response.strPropertyCode;
						        document.getElementById("exciseNo").value=response.strExciseNo;
						        document.getElementById("externalCode").value=response.strExternalCode;
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
	<s:form name="locationForm" method="POST" action="saveLocationMaster.html">
		<table>
		<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		    <tr>
		        <td><s:label path="strLocCode" >Location Code :</s:label></td>
		        <td><s:input id="locCode" name="locCode" path="strLocCode" ondblclick="funHelp('locationmaster')" readonly="true"/></td>
		    </tr>
		    	
		    <tr>
		        <td>
		        	<s:label path="strLocName">Location Name:</s:label>
		        </td>
		        <td>
		        	<s:input type="text" id="locName" name="locName" path="strLocName" required="true"/>
		        	<s:errors path="strLocName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td>
			    	<s:label path="strLocDesc">Description:</s:label>
			    </td>
			    
			    <td>
			    	<s:input id="locDesc" name="locDesc" path="strLocDesc"  />
			    	<s:errors path="strLocDesc"></s:errors>
			    </td>
			 </tr>
			    
			  <tr>
			    <td>
			    	<s:label path="strPropertyCode">Property Code:</s:label>
			    </td>
			    
			    <td>
			    	<s:input id="propertyCode" name="propertyCode" path="strPropertyCode"  />
			    	<s:errors path="strPropertyCode"></s:errors>
			    </td>
			   </tr> 
			  
			   <tr>
			    <td>
			    	<s:label path="strExciseNo">Excise No:</s:label>
			    </td>
			    
			    <td>
			    	<s:input id="exciseNo" name="exciseNo" path="strExciseNo"  />
			    	<s:errors path="strExciseNo"></s:errors>
			    </td>
			   </tr> 
			  
			   
			   <tr>
			    <td>
			    	<s:label path="strExternalCode">External Code:</s:label>
			    </td>
			    
			    <td>
			    	<s:input id="externalCode" name="externalCode" path="strExternalCode"  />
			    	<s:errors path="strExternalCode"></s:errors>
			    </td>
			   </tr> 
			   
			   	   <tr>
			    <td>
			    	<s:label path="strAvlForSale">Available for Sale:</s:label>
			    </td>
			    
			    <td>
			    	<s:checkbox id="avlForSale" name="avlForSale" path="strAvlForSale" value="" />
			    	<s:errors path="strAvlForSale"></s:errors>
			    </td>
			   </tr> 
			  
			   	   <tr>
			    <td>
			    	<s:label path="strActive">Active:</s:label>
			    </td>
			    
			    <td>
			    	<s:checkbox id="active" name="active" path="strActive" value="" />
			    	<s:errors path="strActive"></s:errors>
			    </td>
			   </tr> 
			  
			   	   <tr>
			    <td>
			    	<s:label path="strPickable">Pickable:</s:label>
			    </td>
			    
			    <td>
			    	<s:checkbox id="pickable" name="pickable" path="strPickable" value=""  />
			    	<s:errors path="strPickable"></s:errors>
			    </td>
			   </tr> 
			   
			    	   <tr>
			    <td>
			    	<s:label path="strReceivable">Receivable:</s:label>
			    </td>
			    
			    <td>
			    	<s:checkbox id="receivable" name="receivable" path="strReceivable" value="" />
			    	<s:errors path="strReceivable"></s:errors>
			    </td>
			   </tr> 
			   
			  
			  
			  <tr> 
			    <td><s:label path="strType" >Type :</s:label></td>
		        <td><s:select id="type" name="type" path="strType" items="${typeList}"/></td>
			</tr>
			   
			   
			<tr>
			    <td ><input type="submit" value="Submit" onclick="return funValidateFields()"/></td>
			    <td ><input type="button" value="RESET" onclick="funResetFields()"/></td>
		    </tr>
		</table>
	</s:form>
</body>
</html>