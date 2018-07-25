<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>User Management</title>	
<script type="text/javascript">
$(document).ready(function(){
	resetForms("userForm");
	$("#txtUserName").focus();
});

</script>

	<script type="text/javascript">

	   function funResetFields(){
		   $("#txtUserName").focus();
	   }
		function funHelp(transactionName)
		{
		 
		//    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		function funSetData(code)
		{
			
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadExciseUserMasterData.html?userCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	if('Invalid Code' == response.strUserCode1){
		        	//alert("Invalid User Code");
		        	//resetForms("userForm");
		        	//$("#txtUserName").focus();
		        	}else{
		        	$("#txtUserCode").val(response.strUserCode1);
		        	$("#txtUserName").val(response.strUserName1);
		        	//$("#txtPassword").val(response.strPassword1);
		        	$("#cmbSuperUser").val(response.strSuperUser);
		        	if(response.strRetire=='Y')
		        	{
		        		$("#cmbRetire").val('Yes');
		        	}
		        	else		        	
		        	{
		        		$("#cmbRetire").val('No');	
		        	}		        	
		        	$("#cmbProperty").val(response.strProperty);
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
		
		
		$(function()
		{
			 
			$('a#baseUrl').click(function() 
			{
				 ;
				if($("#txtUserCode").val().trim()=="")
				{
					alert("Please Select User Code");
					return false;
				}
            window.open('attachDoc.html?transName=frmUserManagement1.jsp&formName=User Management&code='+$('#txtUserCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
   });
			
			
		$('#txtUserCode').keydown(function(e) {
				if (e.which == 9) {
					
					var code = $('#txtUserCode').val();
					if (code.trim().length > 0) {
						funSetData(code);
					}
				}
			});
		});
// 		function funOnLoad(){
// 			var message='';
<%-- 			<%if (session.getAttribute("success") != null) { --%>
<%-- 				            if(session.getAttribute("successMessage") != null){%> --%>
<%-- 				            message='<%=session.getAttribute("successMessage").toString()%>'; --%>
<%-- 				            <% --%>
// 				            session.removeAttribute("successMessage");
// 				            }
// 							boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
// 							session.removeAttribute("success");
// 							if (test) {
<%-- 							%>	 --%>
// 				alert("Data Save successfully\n\n"+message);
<%-- 			<% --%>
<%-- 			}}%> --%>
// 		}
		
		
		 $(document).ready(function()
					{
			var message='';
			<%if (session.getAttribute("success") != null) {
				            if(session.getAttribute("successMessage") != null){%>
				            message='<%=session.getAttribute("successMessage").toString()%>';
				            <%
				            session.removeAttribute("successMessage");
				            }
							boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
							session.removeAttribute("success");
							if (test) {
							%>	
				alert("Data Save successfully\n\n"+message);
			<%
			}}%>

		});
		
		
		
		/* function funValidateFields()
		{
			code=$("#txtUserCode").val();
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadUserMasterData.html?userCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	if('Invalid Code' == response.strUserCode1){
		        	alert("Invalid User Code");
		        	$("#txtUserCode").focus();
		        	return false;
		        	}else{
		        		return true;
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
		} */
		
		
	</script>
</head>

	<body onload="funOnload();">
	<div id="formHeading">
		<label>Excise User Management</label>
	</div>
		<s:form action="saveExciseUserMaster.html?saddr=${urlHits}" method="POST" name="userForm">			
			<br />
			<br />
				<table class="masterTable" >
				   <tr>
		        		<th  align="right" colspan="2"> <a id="baseUrl" href="#"> Attach Documents</a> &nbsp; &nbsp; &nbsp;
						&nbsp;</th>
		    		</tr>
					<tr>
						<td width="100px"><label>User Code </label></td>
						<td><s:input path="strUserCode1" id="txtUserCode" ondblclick="funHelp('usermaster')" required="true" cssStyle="text-transform: uppercase;" cssClass="searchTextBox"/></td>
					</tr>
			
					<tr>
						<td><label>User Name</label></td>
						<td><s:input path="strUserName1" id="txtUserName" required="true" cssStyle="text-transform: uppercase;"   cssClass="longTextBox" /></td>
					</tr>
			
					<tr>
						<td><label>Password</label></td>
						<td><s:input type="password" path="strPassword1" id="txtPassword" required="true"   cssClass="longTextBox"/></td>
					</tr>			
			
					<tr>
						<td><label>Super User</label></td>
						<td>
							<s:select path="strSuperUser" id="cmbSuperUser" cssClass="BoxW62px">
								<s:option value="No">No</s:option>
								<s:option value="YES">YES</s:option>
							</s:select>
						</td>
					</tr>
			
					<tr>
						<td><label>Retire</label></td>
						<td>
							<s:select path="strRetire" id="cmbRetire" cssClass="BoxW62px">
								<s:option value="No">No</s:option>
								<s:option value="YES">YES</s:option>
							</s:select>
						</td>
					</tr>
					
					<tr>
						<td><label>Properties</label></td>
						<td>
							<s:select path="strProperty" items="${propertyList}" id="cmbProperty" cssClass="BoxW62px" cssStyle="width:30%">
							</s:select>
						</td>
					</tr>
			
					<tr>
			  			<td colspan="2" >
			  				
			  			</td>
			  		</tr>			
				</table>
			<br /><br />
		
		<p align="center">
			<input type="submit" value="Submit"  class="form_button"
				onclick="return funValidateFields()" /> 
				<input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		</s:form>
	</body>
</html>