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
		$(function(){
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
				alert(message);
			<%
			}}%>
			
			funSetExcisePropertyData()
		});
		
		function funSetExcisePropertyData()
		{
			var gurl=getContextPath()+"/loadExcisePropertySetUp.html?";
			$.ajax({
		        type: "GET",				        
		        url: gurl,
		        dataType: "json",
		        success: function(response)
		        {	
		        	if(response.strBrandMaster=="All"){
		        		$("#chkBrandMaster").prop( "checked", true );
		        	}
		        	if(response.strSizeMaster=="All"){
		        		$("#chkSizeMaster").prop( "checked", true );
		        	}
		        	if(response.strSubCategory=="All"){
		        		$("#chkSubCategoryMaster").prop( "checked", true );
		        	}
		        	if(response.strCategory=="All"){
		        		$("#chkCategoryMaster").prop( "checked", true );
		        	}
		        	if(response.strSupplier=="All"){
		        		$("#chkSupplierMaster").prop( "checked", true );
		        	}
		        	if(response.strRecipe=="All"){
		        		$("#chkRecipeMaster").prop( "checked", true );
		        	}
		        	if(response.strCity=="All"){
		        		$("#chkCityMaster").prop( "checked", true );
		        	}
		        	if(response.strPermit=="All"){
		        		$("#chkPermitMaster").prop( "checked", true );
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
		
</script>

</head>
<body>
	<div id="formHeading">
	<label>Excise Property Set Up</label>
	</div>

<br/>
<br/>
	<s:form name="PropertyMaster" method="POST" action="saveExcisePropertySetUp.html?saddr=${urlHits}">
		<table class="masterTable"  style="border-collapse: separate;">
		<tr>
			<td>
				<label> Use Global Brand Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkBrandMaster" path="strBrandMaster" value="All" />
			</td>
		</tr>
		<tr>
			<td>
				<label> Use Global Size Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkSizeMaster" path="strSizeMaster" value="All" />
			</td>
		</tr>
		<tr>
			<td>
				<label> Use Global Sub Category Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkSubCategoryMaster" path="strSubCategory" value="All" />
			</td>
		</tr>
		<tr>
			<td>
				<label> Use Global Category Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkCategoryMaster" path="strCategory" value="All" />
			</td>
		</tr>
		<tr>
			<td>
				<label> Use Global Supplier Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkSupplierMaster" path="strSupplier" value="All" />
			</td>
		</tr>
		<tr>
			<td>
				<label> Use Global Recipe Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkRecipeMaster" path="strRecipe" value="All" />
			</td>
		</tr>
		<tr>
			<td>
				<label> Use Global City Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkCityMaster" path="strCity" value="All" />
			</td>
		</tr>
		<tr>
			<td>
				<label> Use Global Permit Master</label>
			</td>
			<td>
				<s:input type="checkbox" id="chkPermitMaster" path="strPermit" value="All" />
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
