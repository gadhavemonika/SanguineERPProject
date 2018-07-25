<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>Reason Master</title>
<script type="text/javascript">
function funHelp(transactionName){
	window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
}


function funSetData(code){
	document.getElementById("reasonCode").value=code;
	
	$.ajax({
        type: "GET",
       
        url: getContextPath()+"/loadReasonMasterData.html?reasonCode="+code,
        dataType: "json",
        success: function(response)
        {				        	
        	document.getElementById("strReasName").value=response.strReasName;
        	document.getElementById("strReasDesc").value=response.strReasDesc;
        	
        	document.getElementById("strStockAdj").checked = response.strStockAdj=='Yes' ? true : false;
        	document.getElementById("strstocktra").checked = response.strstocktra=='Yes' ? true : false;
        	document.getElementById("strnonconf").checked = response.strnonconf=='Yes' ? true : false;
        	document.getElementById("strfolloups").checked = response.strfolloups=='Yes' ? true : false;
        	document.getElementById("strcorract").checked = response.strcorract=='Yes' ? true : false;
        	document.getElementById("strprevact").checked = response.strprevact=='Yes' ? true : false;
        	document.getElementById("strResAlloc").checked = response.strResAlloc=='Yes' ? true : false;
        	document.getElementById("strDelcha").checked = response.strDelcha=='Yes' ? true : false;
        	document.getElementById("strLeadMaster").checked = response.strLeadMaster=='Yes' ? true : false;
        	
        	
        	/* 
        	if(response.strstocktra=='Yes')
        	{
        		document.getElementById("strstocktra").checked=true;
        	}
        	else
        	{
        		document.getElementById("strstocktra").checked=false;
        	}
        	
        	if(response.strnonconf=='Yes')
        	{
        		document.getElementById("strnonconf").checked=true;	
        	}
        	else
        	{
        		document.getElementById("strnonconf").checked=false;
        	}
        	
        	if(response.strfolloups=='Yes')
        	{
        		document.getElementById("strfolloups").checked=true;	
        	}
        	else
        	{
        		document.getElementById("strfolloups").checked=false;
        	}
        	
        	if(response.strcorract=="Yes"){
        		document.getElementById("strcorract").checked=true;	
        	}
        	else{
        		document.getElementById("strcorract").checked=false;	
        	}
        	if(response.strprevact=="Yes"){
        		document.getElementById("strprevact").checked=true;	
        	}
        	else
        	{
        		document.getElementById("strprevact").checked=false;	
        	}
        	if(response.strResAlloc=="Yes"){
        		document.getElementById("strResAlloc").checked=true;	
        	}
        	else
        	{
        		document.getElementById("strResAlloc").checked=false;	
        	}
        	if(response.strDelcha=="Yes"){
        		document.getElementById("strDelcha").checked=true;	
        	}
        	else
        	{
        		document.getElementById("strDelcha").checked=false;	
        	}
        	if(response.strLeadMaster=="Yes"){
        		document.getElementById("strLeadMaster").checked=true;	
        	}
        	else
        	{
        		document.getElementById("strLeadMaster").checked=false;	
        	} */
		},
        error: function(e)
        {
          	alert('Error:=' + e);
        }
  });
}




</script>
</head>
<body>
<s:form method="POST"  name ="reasonForm" action="savereasonmaster.html">
<table>
<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
<tr>
<td><s:label path="">Reason Code </s:label></td>
<td><s:input path="strReasCode" readonly="true" ondblclick="funHelp('reason')" id="reasonCode"/></td>
</tr>
<tr>
<td><s:label path="">Reason Name</s:label></td>
<td><s:input path="strReasName" id="strReasName"/></td>
</tr>
<tr>
<td><s:label path="">Description</s:label></td>
<td><s:input path="strReasDesc" id="strReasDesc"/></td>
</tr>
<tr>
<td colspan="2"><s:label path="">APPLICABLE FOR</s:label></td>
</tr>
 
<tr>
<td><s:label path="strStockAdj">Stock Adjustment</s:label></td>
<td><s:checkbox element="li" id="strStockAdj" path="strStockAdj" value="true"/></td>
</tr>

<tr>
<td><s:label path="strstocktra">Stock Transfer</s:label></td>
<td><s:checkbox element="li" id="strstocktra" path="strstocktra" value="true"/></td>
</tr>

<tr>
<td><s:label path="strnonconf">Non Conference</s:label></td>
<td><s:checkbox element="li" id="strnonconf" path="strnonconf" value="true"/></td>
</tr>

<tr>
<td><s:label path="strfolloups">Follow ups</s:label></td>
<td><s:checkbox element="li" id="strfolloups" path="strfolloups" value="true"/></td>
</tr>

<tr>
<td><s:label path="strcorract">Corrective Active</s:label></td>
<td><s:checkbox element="li" id="strcorract" path="strcorract" value="true"/></td>
</tr>

<tr>
<td><s:label path="strprevact">Preventive Action</s:label></td>
<td><s:checkbox element="li" id="strprevact" path="strprevact" value="true"/></td>
</tr>

<tr>
<td><s:label path="strResAlloc">Resource Blocking</s:label></td>
<td><s:checkbox element="li" id="strResAlloc" path="strResAlloc" value="true"/></td>
</tr>

<tr>
<td><s:label path="strDelcha">Delivery Challan</s:label></td>
<td><s:checkbox element="li" id="strDelcha" path="strDelcha" value="true"/></td>
</tr>


<tr>
<td><s:label path="strLeadMaster">Lead Master</s:label></td>
<td><s:checkbox element="li" id="strLeadMaster" path="strLeadMaster" value="true"/></td>
</tr>


			  

<tr>
  <td colspan="2"><input type="submit" value="Submit"/></td>
   <td colspan="1"><input type="reset" value="Reset" /></td>
  </tr>
</table>


</s:form>
</body>
</html>