<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
var fieldName;

	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}

		function funSetData(code)
		{
			switch (fieldName) 
			{
			   case 'CategoryCode':
			    	funSetCategotyData(code);
			        break;
			}
		}
		
		function funSetCategotyData(code)
		{
			$("#txtCategoryName").focus();
			var gurl=getContextPath()+"/loadExciseCategoryMasterData.html?categoryCode=";
			$.ajax({
		        type: "GET",				        
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {	
		        	if(response.strCategoryCode=='Invalid Code')
		        	{
		        		alert("Invalid Category Code Please Select Again");
		        		$("#txtCategoryCode").val('');
		        		$("#txtCategoryName").focus();
		        	}
		        	else
		        	{
		        		$("#txtCategoryCode").val(response.strCategoryCode);
		        		$("#txtCategoryName").val(response.strCategoryName);
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
		
		$(function(){
			
			$("#txtCategoryName").focus();
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
				alert("Category Data Save successfully\n\n"+message);
			<%
			}}%>
			
			$('#txtCategoryCode').blur(function () 
					{
					 var code=$('#txtCategoryCode').val();
					 if (code.trim().length > 0 && code !="?" && code !="/"){							   
						 funSetAttribute(code);
					   }
					});
		});
</script>

</head>
<body>
	<div id="formHeading">
	<label>Category Master</label>
	</div>

<br/>
<br/>

	<s:form name="CategoryMaster" method="POST" action="saveExciseCategoryMaster.html?saddr=${urlHits}">
	
		<table class="masterTable">
			<tr>
				<td width="120px">
					<label>Category Code</label>
				</td>
				<td>
					<s:input type="text" id="txtCategoryCode" path="strCategoryCode" cssClass="searchTextBox" ondblclick="funHelp('CategoryCode')"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Category Name</label>
				</td>
				<td>
					<s:input type="text" id="txtCategoryName" path="strCategoryName" cssClass="longTextBox" required="true"/>
				</td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button" />
		</p>

	</s:form>
</body>
</html>
