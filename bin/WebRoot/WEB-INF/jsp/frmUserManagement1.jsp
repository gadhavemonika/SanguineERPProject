<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>User Management</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">


function funHelp(transactionName)
{
    window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
}
function funSetData(code)
{
	document.getElementById("strUserCode").value=code;
	$.ajax({
        type: "GET",
        url: getContextPath()+"/loadUserMasterData.html?userCode="+code,
        dataType: "json",
        success: function(response)
        
        {			
        	document.getElementById("strUserName").value=response.strUserName;
        	document.getElementById("strPassword").value=response.strPassword;
        	document.getElementById("strSuperUser").value=response.strSuperUser;
        	document.getElementById("strRetire").value=response.strRetire;
        	document.getElementById("strProperty").value=response.strProperty;
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
<s:form action="saveusermaster.html" method="POST" name="userForm">

<table>
<tr>

<td><s:label path="strUserCode">User Code </s:label></td>
<td><s:input path="strUserCode" id="strUserCode" ondblclick="funHelp('usermaster')"/></td>
</tr>

<tr>
<td><s:label path="strUserName">User Name</s:label></td>
<td><s:input path="strUserName" id="strUserName"/></td>
</tr>

<tr>
<td><s:label path="strPassword">Password</s:label></td>
<td><s:input path="strPassword" id="strPassword"/></td>
</tr>


<tr>
<td><s:label path="strSuperUser">Super User</s:label></td>
<td><s:select path="strSuperUser" id="strSuperUser">
<s:option value="No">No</s:option>
<s:option value="YES">YES</s:option>
</s:select></td>
</tr>

<tr>
<td><s:label path="strRetire">Retire</s:label></td>
<td>
<s:select path="strRetire" id="strRetire">
<s:option value="No">No</s:option>
<s:option value="YES">YES</s:option>
</s:select></td>
</tr>
<tr>
<td><label>Properties</label></td>
<td>
<s:select   path="strProperty" items="${propertyList}" id="strProperty">
</s:select></td>
</tr>

<tr>
  <td colspan="2"><input type="submit" value="Submit"/></td>
   <td colspan="1"><input type="reset" value="Reset" /></td>
  </tr>

</table>



</s:form>
</body>
</html>