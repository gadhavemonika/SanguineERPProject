<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">

$(document).ready(function () {
	  $('input#txtUserName').mlKeyboard({layout: 'en_US'});
	  $('input#txtPassword').mlKeyboard({layout: 'en_US'});
	  $('input#txtDataBase').mlKeyboard({layout: 'en_US'});	
	  $('input#txtBackupPath').mlKeyboard({layout: 'en_US'});
	  
	  $("#txtUserName").val("root");
	  $("#txtPassword").val("root");
	  $("#txtDataBase").val("jpos");
	 
	  
	  
	  $("form").submit(function(event){
		
		});

		
	
	});  



	$(document).ready(function()
			{
				var message='';
				<%if (session.getAttribute("success") != null) 
				{
					if(session.getAttribute("successMessage") != null)
					{%>
						message='<%=session.getAttribute("successMessage").toString()%>';
					    <%
					    session.removeAttribute("successMessage");
					}
					boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
					session.removeAttribute("success");
					if (test) 
					{
						%>alert("Data "+message);
						window.returnValue = "saved";
						window.close();
						<%
					}
				}%>
			});


	function funBackupBtnClick()
	{
		var userName = $("#txtUserName").val();
		var password = $("#txtPassword").val();
		var dataBase = $("#txtDataBase").val();
		var backupPath = $("#txtBackupPath").val();
		var searchurl=getContextPath()+"/loadDBBackupData.html?userName="+userName+"&password="+password+"&dataBase="+dataBase+"&backupPath="+backupPath;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        
			        success: function (response) {
			        	
			        	
			            },
			            
			      
			        error: function(jqXHR, exception)
			        {
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


</script>

</head>
<body>

	<div id="formHeading">
	<label>POS Database Backup</label>
	</div>

<br/>
<br/>

	<s:form name="POSDataBaseBackup" method="POST" action="">

		<table class="masterTable">
			<tr>
				<td style="width: 10%;">
					<label>User Name:</label>
				</td>
				<td>
					<s:input  type="text" id="txtUserName" path="" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>Password:</label>
				</td>
				<td>
					<s:input  type="text" id="txtPassword" path="" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>DataBase:</label>
				</td>
				<td>
					<s:input  type="text" id="txtDataBase" path="" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			
			<tr>
				<td style="width: 10%;">
					<label>Backup To:</label>
				</td>
				<td>
					<s:input value="${backupPath}" type="text" id="txtBackupPath" path="" cssClass="BoxW124px" style="width: 40%;" />
				</td>
				
			</tr>
			
			
		</table>

		<br />
		<br />
		<p align="center">
			<input type="button" value="Backup" tabindex="3" class="form_button" onclick="funBackupBtnClick()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
