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
<title>Batch Generation</title>
<script type="text/javascript">
	//Set date in date Picker
	$(function()
	{
		$(".datePicker").datepicker({ dateFormat: 'dd-mm-yy' });
		$('.datePicker').datepicker('setDate', 'today');
	});
	
	//After Clicking Submit Button Getting the data
	function btnSubmit_onclick() 
	{
	
				$.ajax({				
					 	type: "POST",
					    url: $("#frmBatchProcess").attr("action"),
					    data:$("#frmBatchProcess").serialize(),
					    async: false,
					    context: document.body,
					    dataType: "text",
					    success: function(response)
					    {	
								str=response;
								window.close();
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
<div style="width: 100%; height: 40px; background-color: #458CCA">
		<p align="center" style="padding-top: 5px;color: white">Batch Generation</p>
	</div>

	<s:form id="frmBatchProcess" name="frmBatchProcess" method="POST" action="saveBatchProcessing.html" modelAttribute="setBatchAttribute" target="_self">
	<br>
	
							<table  style="height:25px;border:#0F0;width:100%;font-size:11px;
								font-weight: bold;">	
								<tr bgcolor="#79BAF2">
									<td style="width: 13%; height: 16px;" align="left">GRN Code</td>
									<td style="width: 12%; height: 16px;" align="left">Product Code</td>
									<td style="width: 34%; height: 16px;" align="left">Product Name</td>
									<td style="width: 10%; height: 16px;" align="left">Quantity</td>
									<td style="width: 12%; height: 16px;" align="left">Expiry Date</td>
									<td style="width: 20%; height: 16px;" align="left">Manufacture Batch Code</td>
								</tr>
							</table>
					<div style="background-color:  	#a4d7ff;
					    border: 1px solid #ccc;
					    display: block;
					    height: 300px;
					    margin: auto;
					    overflow-x: hidden;
					    overflow-y: scroll;
					    width: 100%;">
					    
							<table id="tblBatch" class="transTablex col6-center" style="width: 100%;">
								<tbody>    
<%-- 										<col style="width:15%"><!--  COl1   --> --%>
<%-- 										<col style="width:10%"><!--  COl2   --> --%>
<%-- 										<col style="width:30%"><!--  COl3   --> --%>
<%-- 										<col style="width:10%"><!--  COl4   --> --%>
<%-- 										<col style="width:15%"><!--  COl5   --> --%>
<%-- 										<col style="width:20%"><!--  COl6   --> --%>
										<c:forEach items="${BatchList}" var="GRNBatch" varStatus="status">

								<tr>
									<td style="width: 13%; height: 18px;" align="left"><input readonly="readonly" class="Box" size="12%" name="listBatchDtl[${status.index}].strTransCode" value="${GRNBatch.strGRNCode}" /></td>
									<td style="width: 13%; height: 18px;" align="left"><input readonly="readonly" class="Box" size="10%" name="listBatchDtl[${status.index}].strProdCode" value="${GRNBatch.strProdCode}" /></td>
									<td style="width: 35%; height: 18px;" align="left"><input readonly="readonly" class="Box" size="40%" name="listBatchDtl[${status.index}].strProdName" value="${GRNBatch.strProdName}"/></td>
									<td style="width: 10%; height: 18px;" align="Right"><input readonly="readonly" class="Box" size="8%" name="listBatchDtl[${status.index}].dblQty" value="${GRNBatch.dblQty}" /></td>
									<td style="width: 13%; height: 18px;" align="left"><input class="datePicker calenderTextBox" size="14%" name="listBatchDtl[${status.index}].dtExpiryDate" value=""/></td>
									<td style="width: 18%; height: 18px;" align="left"><input size="18%" name="listBatchDtl[${status.index}].strManuBatchCode" value="" /></td>
									
								</tr>
			</c:forEach> 
								</tbody>							
							</table>
					</div>	
				
					<p align="center">
			<input type="submit" value="Submit"
							onclick="return btnSubmit_onclick()"
				class="form_button" /> <input type="button" value="Reset"
				onclick="funResetFields();" class="form_button" />
		</p>		
	</s:form>
</body>
</html>