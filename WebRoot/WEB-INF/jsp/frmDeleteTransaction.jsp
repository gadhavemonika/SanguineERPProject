<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<script>
	
		var transactionName,fieldName,reportName;
	
		$(function ()
		{
			funSetTransCodeHelp($("#cmbFormName").val());
			
			$("#frmDelTrans").submit(function( event )
			{
				if($("#txtTransactionCode").val()=='')
				{
					alert("Please Select Transaction Code To Delete");
					return false;
				}
				
				if($("#txtReason").val()=='')
				{
					alert("Enter Reason For Delete");
					return false;
				}
			});
			
			
			$("#btnShow").click(function( event )
			{
				funSetDocCode($("#txtTransactionCode").val());
			});
			
			
			$("#cmbFormName").change(function() 
			{
				var value=$("#cmbFormName").val();
				funSetTransCodeHelp(value);
			});
			
			$('a#baseUrl').click(function() 
			{
				var doc=$("#txtTransactionCode").val();
				var value=$("#cmbFormName").val();
				if(reportName=='')
				{
					
				}else{
					window.open("setReportFormName.html?docCode="+doc+","+value,"_blank");
					reportName='';
				}
			});
		});
		
		function funSetTransCodeHelp(value)
		{
			reportName=''
			switch (value) 
			{
			   case 'frmGRN':
				   reportName="openRptGRNSlip.html";
				   transactionName='grncode';
			       break;
			       
			   case 'frmMaterialReturn':
				   reportName="openRptMRetSlip.html";
				   transactionName='MaterialReturn';
			       break;
			       
			   case 'frmOpeningStock':
				   reportName="openRptOpStkSlip.html";
				   transactionName='opstock';
			       break;
			       
			   case 'frmPhysicalStkPosting':
				   reportName="openRptPhyStkSlip.html";
				   transactionName='stkpostcode';
			       break;
			       
			   case 'frmProductionOrder':
				   reportName="openRptOPSlip.html";
				   transactionName='ProductionOrder';
			       break;
			       
			   case 'frmPurchaseIndent':
				   reportName="openRptPISlip.html";
				   transactionName='PICode';
			       break;
			       
			   case 'frmPurchaseOrder':
				   reportName="openRptPOSlip.html";
				   transactionName='purchaseorder';
			       break;
			       
			   case 'frmPurchaseReturn':
				   reportName="openRptPRSlip.html";
				   transactionName='PurchaseReturn';
			       break;
			       
			   case 'frmStockAdjustment':
				   reportName="openRptStockAdjustmentSlip.html";
				   transactionName='stkadjcode';
			       break;
			       
			   case 'frmWorkOrder':
				   reportName="openRptWorkOrderSlip.html";
				   transactionName='WorkOrder';
			       break;
			   
			   case 'frmStockTransfer':
				   transactionName='stktransfercode';
			       break;
			       
			   case "frmMIS":
				   reportName="openRptMISSlip.html";
				   transactionName='MIS';
			       break;
			       
			   case "frmMaterialReq":
				   reportName="openRptMReqSlip.html";
				   transactionName='MaterialReqDelete';
			       break;
			       
			   case "frmProduction":
				   transactionName='Production';
			       break;
			       
			   case "frmBillPassing":
				   transactionName='BillPassing';
			       break;
			       
			   case "frmInvoice":
				   reportName="";
				   transactionName='invoice';
			       break;
		       
			   case "frmSalesReturn":
				   transactionName='salesReturn';
			       break;
				
			   case "frmSalesOrder":
				   transactionName='salesorder';
			       break;
			}
		}
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'reason':
			    	funSetReason(code);
			        break;
			    
			    case 'transaction':
			    	funSetTrasactionCode(code);
			        break;
			}
		}
		
		
		function funHelp1(field)
		{
			fieldName=field;
	        //window.open("searchform.html?formname="+field+"&searchText=", 'window', 'width=600,height=600');
	     //   window.showModalDialog("searchform.html?formname="+field+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	        window.open("searchform.html?formname="+field+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	    }
		
		function funHelp()
		{
			fieldName='transaction';
	       // window.open("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	    }
		
		function funSetTrasactionCode(code)
		{			
			$("#txtTransactionCode").val(code);
			$("#lblDocCode").text(code);
		}
		
		
		function funSetDocCode(code)
		{
			var value=$("#cmbFormName").val();
			var searchUrl=getContextPath()+"/setReportFormName.html?docCode="+code+","+value;
			$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
					window.open(getContextPath()+"/"+reportName,'_blank');
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
		
		
		function funSetReason(code)
		{
			$("#txtReason").val(code);
			$.ajax({
		        type: "GET",		       
		        url: getContextPath()+"/loadReasonMasterData.html?reasonCode="+code,
		        dataType: "json",
		        success: function(response)
		        {				        	
		        	$("#lblReasonName").text(response.strReasonName);
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
		
		$(function ()
				{
					 var message='';
					 var retval="";
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
						alert("Delete Transaction successfully\n\n"+message);
						
					<%
					}}%>
				});
	</script>

</head>
	<body>
	<div id="formHeading">
		<label>Delete Transaction</label>
	</div>
		<s:form id="frmDelTrans" method="POST" action="deleteTransaction.html">
		    <br><br>
		    <table class="masterTable">
		    <tr><th colspan="8"></th></tr>
				<tr>	
						
					<td>Form Name</td>
					<td>
						<s:select id="cmbFormName" path="strFormName" cssClass="longTextBox">
							<s:options items="${listFormName}"/>
						</s:select>
					</td>
					
					<td>Code</td>
					
					<td width="15%">
						<s:input type="text" name="code" id="txtTransactionCode" cssClass="searchTextBox" path="strTransCode" ondblclick="funHelp();"/>						
					</td>
					
					<td  colspan="4">
						<a id="baseUrl" href="#"> <label id="lblDocCode"></label></a>
						<!-- <input type="button" id="btnShow" value="SHOW" class="form_button"/> -->
					</td>
				</tr>
			
			    <tr>
			    	<td><label>Delete</label></td>
			        <td colspan="7" >
						<select id="cmbDelete" class="BoxW62px">
							<option value="Y">Yes</option>
							<option value="N">No</option>
						</select>
					</td>
			    </tr>
			    
			    <tr>
			        <td><label>Reason</label></td>
			        <td colspan="7">
			        	<s:input type="text" id="txtReason" path="strReasonCode" cssClass="searchTextBox" ondblclick="funHelp1('reason');"/>
			        <label id="lblReasonName"></label>
			        </td>
			        
			    </tr>
			    
			    <tr>
			    	<td><label>Narration</label></td>
			        <td colspan="7">
			        	<s:input type="text" id="txtNarration" cssClass="longTextBox" path="strNarration"/>
			        </td>
			    </tr>
			    
			    <tr>
			    	<td>
			    		<!-- <input type="submit" id="btnSubmit" value="SUBMIT"/> -->
			    	</td>
			    	<td>
			    		<!-- <input type="reset" id="btnReset" value="RESET"/> -->
			    	</td>
			    	<td></td>
			    	<td></td>
			    	<td></td>
			    	<td></td>
			    	<td></td>
			    	<td></td>
			    </tr>
			</table>
				<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit"
				id="btnSubmit"
				class="form_button" /> <input type="reset" value="Reset"
				id="btnReset" class="form_button" />
		</p>
<br><br>
		</s:form>
	</body>
</html>