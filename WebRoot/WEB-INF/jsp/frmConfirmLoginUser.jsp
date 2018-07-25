<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	
<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" /> 	
 	<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/jquery-ui.css"/>" />
<title>Confirmation User</title>
<script type="text/javascript">

	//Getting Project Path
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	//Load Data wen Form is Open
	function funOnLoad()
	{
	  
		strHeadingType='<%=request.getParameter("strHeadingType") %>'
		if(strHeadingType=="Transaction")
		{
			$("#strHeadingType").text("Clear All Transaction");	
		}
		if(strHeadingType=="Master")
		{
			$("#strHeadingType").text("Clear All Master");
		}
	}
	
	//After Clicking Submit Button Checking User Id 
	function btnSubmit_onclick() 
	{
		var strUserName,strpassword;
		
		if($("#txtusername").val().trim().length==0)
		{
				alert("Please Enter User Id");
				return false;
		}
		else
		{
				strUserName=$("#txtusername").val().trim();
		}
	
		if($("#txtPassword").val().trim().length==0)
		{
			alert("Please Enter Password");
			return false;
		}
		else
		{
			strpassword=$("#txtPassword").val().trim();
		}
		var strurl= getContextPath()+"/confirmUser.html?userName="+strUserName+"&password="+strpassword;
		
				$.ajax({				
					 	type: "POST",
					    url:strurl,
					    async: false,
					    dataType: "text",
					    success: function(response)
					    {	
								str=response;
								if(str=="Invalid Login")
									{
										alert(str);
										return false;
									}
								else
									{
										window.returnValue = str;
										window.close();
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
	
	//Reset Filed after Clicking Reset Button
	function funResetFields()
	{
		$("#txtusername").val("");
		$("#txtPassword").val("");
	}
	
</script>
</head>
<body onload="funOnLoad();">
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center"  style="padding-top: 5px;color: white"><label id="strHeadingType"></label></p>
	</div>
	<s:form id="frmConfirmLoginUser" name="frmConfirmLoginUser"  action="confirmUser.html" >
	<div
		style="width: 100%; min-height:450px; height:100%;  overflow-y: auto; padding-bottom: auto;">
		<table  class="masterTable" style="width: 100%">
			<tr>
								<td width="100px" style="padding-left: 30px">Username</td>
								<td><input Class="loginInput" name="usercode"
										 autocomplete="off" id="txtusername" 
										placeholder="User name" required="required"
										Style="text-transform: uppercase;" /> </td>
							</tr>
							<tr>
								<td style="padding-left: 30px">Password</td>
								<td><input type="password" placeholder="PASSWORD"
										required="required" Class="loginInput" name="pass"
										id="txtPassword" /> </td>
							</tr>
							<tr>
		</table>
		<br>
						<p align="center"><input type="button" value="Submit"
							onclick="return btnSubmit_onclick()"
				class="form_button" /> <input type="button" value="Reset"
				onclick="funResetFields();" class="form_button" />
	
		

		<div id="wait" style="display:none;width:80px;height:80px;border:0px solid black;position:absolute;top:45%;left:45%;padding:2px;">
		<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="80px" height="80px" /></div>
		</div>
	</s:form>
</body>
</html>