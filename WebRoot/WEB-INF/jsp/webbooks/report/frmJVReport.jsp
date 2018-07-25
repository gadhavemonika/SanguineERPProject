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
		 		$("#txtSOCode").val(code);
				        
				
			}
		 	
		 	
		 	

	
	
	
	function funCallFormAction(actionName,object) 
	{
			
		if ($("#txtSOCode").val()=="") 
		    {
			 alert('Invalid Date');
			 $("#txtSODate").focus();
			 return false;  
		   }
		
		
	  else
		{
			return true;
			
		}
	}
	
	$(function()
			{
				$("#txtSOCode").blur(function() 
						{
							var code=$('#txtSOCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								//funSetReqData(code);
							}
						});
				
			});
		
</script>

</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>JV Report</label>
	</div>
	<s:form name="JVReport" method="GET"
		action="rptJVReport.html" target="_blank" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
								<tr>
									<td><label>Voucher No</label></td>
									<td><s:input path="strDocCode" id="txtSOCode"
											ondblclick="funHelp('JVNoslip')"
											cssClass="searchTextBox" /></td>
																										
								</tr>
<!-- 								<tr> -->
<!-- 									<td><label>Currency </label></td> -->
									
<%-- 									<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px"> --%>
<%-- 										</s:select></td> --%>
<!-- 								</tr> -->
								<tr>
								
					
									<td><label>Report Type</label></td>
									<td ><s:select id="cmbDocType" path="strDocType"
											cssClass="BoxW124px">
											<s:option value="PDF">PDF</s:option>
											<s:option value="XLS">EXCEL</s:option>
											<s:option value="HTML">HTML</s:option>
											<s:option value="CSV">CSV</s:option>
										</s:select></td>
                                  </tr>

		</table>
		
		<p align="center">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmJVReport.html?saddr=${urlHits}"><input
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
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>