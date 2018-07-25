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
			   case 'SubCategoryCode':
			    	funSetSubCategory(code);
			        break;
			        
			   case 'CategoryCode':
			    	funSetCategory(code);
			        break;
			}
		}
		
		function funSetSubCategory(code)
		{
			$("#txtPegSize").focus();
			var gurl=getContextPath()+"/loadExciseSubCategoryMasterData.html?subcategoryCode=";
			$.ajax({
		        type: "GET",				        
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {	
		        	if(response.strSubCategoryCode=='Invalid Code')
		        	{
		        		alert("Invalid Sub Category Code Please Select Again");
		        		$("#txtSubCategoryCode").val('');
		        		$("#txtSubCategoryName").focus();
		        		
		        	}
		        	else
		        	{
		        		$("#txtSubCategoryCode").val(response.strSubCategoryCode);
		        		$("#txtSubCategoryName").val(response.strSubCategoryName);
		        		$("#txtPegSize").val(response.intPegSize);
		        		$("#txtCategoryCode").val(response.strCategoryCode);
		        		$("#txtCategoryName").text(response.strCategoryName);
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
		
		function funSetCategory(code)
		{
			$("#txtPegSize").focus();	
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
		        		$("#txtCategoryCode").focus();
		        	}
		        	else
		        	{
		        		$("#txtCategoryCode").val(response.strCategoryCode);
		        		$("#txtCategoryName").text(response.strCategoryName);		        		
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
			
			$("#txtSubCategoryName").focus();
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
				alert("Sub Category Data Save successfully\n\n"+message);
			<%
			}}%>
			
			$('#txtSubCategoryCode').blur(function () 
					{
					 var code=$('#txtSubCategoryCode').val();
					 if (code.trim().length > 0 && code !="?" && code !="/"){							   
						 funSetSubCategory(code);
					   }
				});
			

			$('#txtCategoryCode').blur(function () 
					{
					 var code=$('#txtCategoryCode').val();
					 if (code.trim().length > 0 && code !="?" && code !="/"){							   
						 funSetCategory(code);
					   }
				});
		});	
		
</script>

</head>
<body>

	<div id="formHeading">
	<label>Sub Category Master</label>
	</div>

<br/>
<br/>

	<s:form name="SubCategoryMaster" method="POST" action="saveExciseSubCategoryMaster.html?saddr=${urlHits}">
		
		<table class="masterTable">
			<tr>
				<td width="120px">
					<label>Sub Category Code</label>
				</td>
				<td colspan="4">
					<s:input type="text" id="txtSubCategoryCode" path="strSubCategoryCode" cssClass="searchTextBox" ondblclick="funHelp('SubCategoryCode');"/>
				</td>
				</tr>
				<tr>
				<td>
					<label>Sub Category Name</label>
				</td>
				<td colspan="4">
					<s:input type="text" id="txtSubCategoryName" path="strSubCategoryName" cssClass="longTextBox" required="true" />
				</td>
			</tr>
			<tr>
				<td width="120px">
					<label>Category Code</label>
				</td>
				<td width="150px">
					<s:input type="text" id="txtCategoryCode" name="txtCategoryCode" path="strCategoryCode"  ondblclick="funHelp('CategoryCode')" required="true" cssClass="searchTextBox" />
				</td>
				<td width="150px">
					<s:label path="strCategoryName" id="txtCategoryName"/>
				</td>
				<td>
					<label>Peg Size in ML</label>
				</td>
				<td>
					<s:input type="text" required="true" id="txtPegSize" cssClass="BoxW124px numeric positive-integer " path="intPegSize"/>
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
