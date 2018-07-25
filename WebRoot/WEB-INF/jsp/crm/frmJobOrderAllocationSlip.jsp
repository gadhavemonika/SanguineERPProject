<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script type="text/javascript">

function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			
		}

		 	function funSetData(code)
			{
				switch (fieldName)
				{
					    	
				    case 'JACode' :
				    	 $('#txtJACode').val(code);
				    	break;
				    	
				    case 'JACodeslip' :
				    	 $('#txtJACode').val(code);
				    	break;
				        
				}
			}
		 	
</script>
</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Job Order Allocation Slip</label>
	</div>
	<s:form name="JobOrderAllocationCode" method="GET"
		action="rptJobOrderAllocationSlip.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
								<tr>
									<td width="170px"><label>Job Order Allocation Code</label></td>
									<td colspan="3"><s:input path="strDocCode" id="txtJACode"
											ondblclick="funHelp('JACodeslip')"
											cssClass="searchTextBox"/></td>
											<td colspan="1"><label id="lblJobOrderName"
										class="namelabel"></label></td>
																										
								</tr>
								<tr>
								

				<td><label>Type</label></td>
				<td ><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>

					</s:select></td>




			</tr>

		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="JobOrderAllocationCode.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

</body>
</html>