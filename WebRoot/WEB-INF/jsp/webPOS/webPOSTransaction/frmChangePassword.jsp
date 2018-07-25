<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
	pageEncoding="ISO-8859-1"%> 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> Language Password </title>
<script>
	
$(document).ready(function() {

	 $('input#txtOldPass').mlKeyboard({layout: 'en_US'});
	  $('input#txtNewPass').mlKeyboard({layout: 'en_US'});
	 
	  $("form").submit(function(event){
		  if($("#txtOldPass").val().trim()=="")
			{
				alert("Please Enter Password");
				return false;
			}
 		flg = funChekPassword();
		});
	  
	 
	});

function funChekPassword() 
{
	var flg=true;
	
//		if($('#txtCounterCode').val()=='')
//		{
		var userCode = $('#txtUserCode').val();
		var oldPass = $('#txtOldPass').val();
		var newPass = $('#txtNewPass').val();
		 $.ajax({
		        type: "GET",
		        data : {
		        	userCode : userCode,
		        	oldPass : oldPass,
		        	
				},
		        url: getContextPath()+"/checkUserName.html",
		        async: false,
		        dataType: "text",
		        success: function(response)
		        {
// 		        	alert(response);
// 		        	if(response=="PasswordNotMatch")
// 		        	{
// 		        	alert("Password Doesn't Match");	
// 		        	}	
		        	
		        	if(response=="false")
	        		{
	        			alert("Password Doesn't Match!");
	        			$('#txtOldPass').focus();
	        			flg= false;
		    		}
		    		else
		    		{
		    		document.frmChangePassword.action="savrOrUpdateUserPassword.html?userCode="+ userCode+"&oldPass="+oldPass+"&newPass="+newPass;
		    		document.frmChangePassword.submit();
		    		alert("Password Saved Successfully");
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
	
	
	return flg;
} 
</script>
</head>

        <body>
<div id="formHeading">
		<label>Change Password</label>
	</div>
   
<s:form name="frmChangePassword" method="POST" action="">
<table class="masterTable">
<tr align="center">
	<td width="140px">User Code
	<s:input size="40" type="text" id="txtUserCode" path="strUserCode" cssClass="BoxW124px" value="${userCode}"/>
	</td>
</tr>	
<tr align="center">
	<td width="140px">Old Password	
	<s:input colspan="40" type="text" id="txtOldPass" name="txtOldPass" path="strOldPass" cssClass="BoxW124px"/> 
	</td>
</tr>
<tr align="center">
	<td width="140px">New Password
	<s:input size="40" type="text" name="txtNewPass" id="txtNewPass" path="strNewPass" cssClass="BoxW124px"  />
	</td>
</tr>
</table>	
<br/>
<br/>	
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="button" value="Close" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()" />
				
		</p>
		
		
	</s:form>
 

</body>

</html>