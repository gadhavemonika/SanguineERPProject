<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	
<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" /> 	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Import</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>

<script type="text/javascript">

function funShowImagePreview(input)
{
	 if (input.files && input.files[0])
	 {
		 var filerdr = new FileReader();
		 filerdr.onload = function(e) 
		 {
		 $('#itemImage').attr('src', e.target.result);
		 }
		 filerdr.readAsDataURL(input.files[0]);
	 }
	 
	
}
</script>

</head>
<body >

<div id="formHeading">
	<label>Database Data Import</label>
	</div>

<br/>
<br/>

<s:form name="DataImport" method="POST" action="saveDataImport.html?saddr=${urlHits}">
		<table class="masterTable">
		<tr>
		 
				<td width="200px"><label> DataBase Path </label>
				</td>
				<td>
				<s:input type="text" id="txtDBPath" placeholder="Enter full Database path with file name"  name="DbPath" path="strDBName" cssClass="longTextBox" style="width:300px;"/>
				</td>
				<!-- <td></td> -->
				<!-- <td ><input id="BrowseDB" name="BrowseDB"  type="file" accept=".mdb,.accda,.accdb" onchange="funShowDataBasePreview(this);" /></td> -->
		</tr>
		<tr>
				<td></td> <td></td> 
		</tr>
		<tr>
		  
				<td width="200px">
				<input type="submit" value="Excute" tabindex="3" class="form_button" />
				</td>
				<td></td>
		</tr>
		</table>
</s:form>

</body>
</html>