<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Order</title>

<script type="text/javascript">
		
$(document).ready(function() 
		{		
			$(".tab_content").hide();
			$(".tab_content:first").show();
	
			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();
				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
			
			
		});
		
$(document).ready(function() {
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
	
	var code='';
	<%if(null!=session.getAttribute("rptSOCode")){%>
	code='<%=session.getAttribute("rptSOCode").toString()%>';
	<%session.removeAttribute("rptSOCode");%>
	var isOk=confirm("Do You Want to Generate Slip?");
	if(isOk){
		
	
		window.open(getContextPath()+"/openRptSalesOrderSlip.html?rptSOCode="+code,'_blank');
	
	}
	<%}%>

	});	
		
		
		function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
			
		}

		 	function funSetData(code)
			{
				switch (fieldName)
				{
					    	
				    case 'salesorder' :
				    	funSetSalesData(code);
				    	break;
				    	
				    case 'salesorderslip' :
				    	funSetSalesData(code);
				    	break;
				        
				}
			}
		 	
		 	function funSetSalesData(code)
			{
				gurl=getContextPath()+"/SalesOrderHdData.html?soCode="+code;
				$.ajax({
			        type: "GET",
			        url: gurl,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strSOCode){
			        			alert("Invalid Customer Code");
			        			$("#txtSOCode").val('');
			        			$("#txtSOCode").focus();
			        			
			        		}else{	
		        			
			        			$("#txtSOCode").val(response.strSOCode);
							
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
		 	

	
	
	
	function funCallFormAction(actionName,object) 
	{
			
		if ($("#txtSOCode").val()=="") 
		    {
			 alert('Invalid Date');
			 $("#txtSODate").focus();
			 return false;  
		   }
		
// 		 var against=$("#cmbDocType").val();
// 		 var SOCode=$("#txtSOCode").val();
<%-- 	     var rptSOCode='<%=session.getAttribute("rptSOCode").toString()%>'; --%>
	
// 		if(against=='40')
// 		{
// 	 		window.open(getContextPath()+"/openRptSalesOrder40Slip.html?rptSOCode="+SOCode,'_blank');
// 	 	}
// 		else{
// 			window.open(getContextPath()+"/openRptSalesOrderSlip.html?rptSOCode="+SOCode,'_blank');
// 		}
		
		
		
		else {
			var SOCode=$("#txtSOCode").val();
		    var against=$("#cmbDocType").val();
			if(against=='40')
			{
				document.SalesOrderSlip.action="openRptSalesOrder40Slip.html";
			}
// 			else{
// 				document.SalesOrderSlip.action="openRptSalesOrderSlip.html"; 
// 			}
			
			document.SalesOrderSlip.submit();
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
		<label>Sales Order Slip</label>
	</div>
	<s:form name="SalesOrderSlip" method="GET" 		action="rptSalesOrderSlip.html" target="_blank">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
								<tr>
									<td width="100px"><label>SO Code</label></td>
									<td colspan="3"><s:input path="strDocCode" id="txtSOCode"
											ondblclick="funHelp('salesorderslip')"
											cssClass="searchTextBox" /></td>
																										
								</tr>
								<tr>
								
								<td><label>BOM Show</label></td>
									<td><s:select id="cmbBOMShow" name="cmbBOMShow"
											path="strShowBOM" cssClass="BoxW124px">
											<option value="N">No</option>
											<option value="Y">Yes</option>
										</s:select></td>

				<td><label>Report Type</label></td>
				<td ><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>
						<s:option value="40">40</s:option>
					</s:select></td>




			</tr>

		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit" onclick="return funCallFormAction('submit',this)" class="form_button" />
			 &nbsp; &nbsp; &nbsp; 
				<a STYLE="text-decoration: none" href="frmSalesOrderSlip.html?saddr=${urlHits}">
		<input type="button" id="reset" name="reset" value="Reset" class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>
<%-- 	<script type="text/javascript"> --%>
<!-- // 		funApplyNumberValidation(); -->
<%-- 	</script> --%>
</body>
</html>