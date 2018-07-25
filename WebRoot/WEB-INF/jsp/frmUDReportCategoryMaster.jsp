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

	$(function() 
	{
	});

	function funSetData(code)
	{

		switch(fieldName)
		{

			case 'UDCCode' : 
				funSetUDCCode(code);
				break;
		}
	}


	function funSetUDCCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadUDCCode.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				$("#txtUDCCode").val(response.strUDCCode);
	        	$("#txtUDCName").val(response.strUDCName);
	        	$("#txtUDCDesc").val(response.strUDCDesc);	        	
			},
			error : function(e){

			}
		});
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>UDReportCategoryMaster</label>
	</div>

	<s:form name="UDReportCategoryMaster" method="POST" action="saveUDReportCategoryMaster.html">

		<br />
		<br />
		
		<table class="masterTable">
		<tr>
		        <th align="right" colspan="2"> </th>
		    </tr>
			<tr>
				<td width="120px">
					<label>UDC Code</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtUDCCode" path="strUDCCode" cssClass="searchTextBox" ondblclick="funHelp('UDCCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>UDC Name</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtUDCName" path="strUDCName" cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
				<td>
					<label>UDC Desc</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtUDCDesc" path="strUDCDesc" cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
				</td>
			</tr>
			<tr>
			    <td ></td>
			    <td ></td>
		    </tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
