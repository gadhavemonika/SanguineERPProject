<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	
	var fieldName;
	var debtorName='';
	
	$(function() 
	{
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', Dat);
		$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtToDate").datepicker('setDate', 'today');
		var glCode = $("#txtGLCode").val();
		if(glCode!='')
		{
			funSetGLCode(glCode);
		}
		
		
		$("#btnExecute").click(function( event )
				{
			 		var propCode='<%=session.getAttribute("propertyCode").toString()%>';
					if($("#txtGLCode").val()=='')
					{
						alert("Enter GL Code!");
						return false;
					}
					if($("#txtFromEmployeeCode").val()=='')
					{
						alert("Enter Debtor Code!");
						return false;
					}
					
					var fromDate=$("#txtFromDate").val();
					var toDate=$("#txtToDate").val();
					var table = document.getElementById("tblEmployeeLedgerBill");
					var rowCount = table.rows.length;
					while(rowCount>0)
					{
						table.deleteRow(0);
						rowCount--;
					}
					
					var table1 = document.getElementById("tblEmployeeLedgerBillTot");
					var rowCount1 = table1.rows.length;
					while(rowCount1>0)
					{
						table1.deleteRow(0);
						rowCount1--;
					}
					var glCode = $("#txtGLCode").val();
					var employeeCode =$("#txtFromEmployeeCode").val()
					funGetEmployeeLedger(fromDate,toDate,glCode,employeeCode,propCode);
					
				});

		$("#btnExport").click(
				function() {
					var currency=$("#cmbCurrency").val();
					var fromDate = $("#txtFromDate").val();
					var toDate = $("#txtToDate").val();
					var glCode = $("#txtGLCode").val();
					var employeeCode = $("#txtFromEmployeeCode").val();
					
					var param1=glCode+","+employeeCode;
					var reportType = $("#cmbReportType").val();
					var ledgerName = "employeeLedger";
					var glName = $("#lblGLCode").text();
					var employeeName = $("#lblFromEmployeeName").text();
					if(reportType=="EXCEL")
					{
					window.location.href = getContextPath()
							+ "/frmExportLedger.html?param1="
							+ param1 + "&fDate=" + fromDate
							+ "&tDate=" + toDate+"&currency="+currency;
					}
					else
					{
						window.open(getContextPath()+"/rptEmployeeReport.html?employeeCode="+employeeCode+"&fromDate="+fromDate+"&toDate="+toDate+"&ledgerName="+ledgerName+"&glCode="+glCode+"&glName="+glName+"&employeeName="+employeeName+"&currency="+currency,'_blank');
					}	
				});
		
	});
	
	
	function funGetEmployeeLedger(fromDate,toDate,glCode,employeeCode,propCode)
	{
		var currency=$("#cmbCurrency").val();
	
// 		var currValue=funGetCurrencyCode(currency);
		var currValue=1;
		var param1=glCode+","+employeeCode+","+propCode;
		var searchUrl=getContextPath()+"/getEmployeeLedger.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate+"&currency="+currency;
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    async:false,
			    success: function(response)
			    {
			    	funShowEmployeeLedger(response,currValue);
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		      });
	}
	
	function funShowEmployeeLedger(response,currValue)
	{
		var table = document.getElementById("tblEmployeeLedgerBill");
		var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
		row.insertCell(0).innerHTML= "<label>Transaction Date</label>";
		row.insertCell(1).innerHTML= "<label>Transaction Type</label>";
		row.insertCell(2).innerHTML= "<label>Ref No</label>";
		row.insertCell(3).innerHTML= "<label>Chq/BillNo</label>";
		row.insertCell(4).innerHTML= "<label>BillDate</label>";
		row.insertCell(5).innerHTML= "<label>Dr</label>";
		row.insertCell(6).innerHTML= "<label>Cr</label>";
		row.insertCell(7).innerHTML= "<label>Bal</label>";
		
		rowCount=rowCount+1;
		//var records = [];
		var bal=0.00;
		var cr=0.00;
		var dr=0.00;
		var opBal=0.00;
		$.each(response, function(i,item)
		{
			var row1 = table.insertRow(rowCount);
			if(item[2]!='')
			{
				/* cr=cr+parseFloat(item[5]);
				dr=dr+parseFloat(item[6]);
				bal=bal+parseFloat(item[5])-parseFloat(item[6]);
				
				row1.insertCell(0).innerHTML= "<label>"+item[0]+"</label>";
				row1.insertCell(1).innerHTML= "<label>"+item[1]+"</label>";
				row1.insertCell(2).innerHTML= "<a id=\"urlDocCode\" href=\"openSlipLedger.html?docCode="+item[2]+","+item[1]+"\" target=\"_blank\" >"+item[2]+"</a>";
				row1.insertCell(3).innerHTML= "<label>"+item[3]+"</label>";
				row1.insertCell(4).innerHTML= "<label>"+item[4]+"</label>";
				row1.insertCell(5).innerHTML= "<label>"+item[5].toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
				row1.insertCell(6).innerHTML= "<label>"+item[6].toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
				row1.insertCell(7).innerHTML= "<label>"+bal.toFixed(maxQuantityDecimalPlaceLimit)+"</label>"; */
				
				
				

				var vochDate=item.dteVochDate.split("-");
				vochDate=vochDate[2].split(" ")[0]+"-"+vochDate[1]+"-"+vochDate[0];
				
				var dteBillDate=item.dteBillDate.split("-");
				dteBillDate=dteBillDate[2].split(" ")[0]+"-"+dteBillDate[1]+"-"+dteBillDate[0];
				if(item.strTransType=='Opening')
				{
					dteBillDate='';
					opBal=(parseFloat(item.dblDebitAmt)/currValue)-(parseFloat(item.dblCreditAmt)/currValue);
				}else{
					cr=cr+(parseFloat(item.dblCreditAmt)/currValue);
					dr=dr+(parseFloat(item.dblDebitAmt)/currValue);
					
					
				}
				bal=bal+(parseFloat(item.dblDebitAmt)/currValue)-(parseFloat(item.dblCreditAmt)/currValue);
				var transType="";
				row1.insertCell(0).innerHTML= "<label>"+vochDate+"</label>";
				row1.insertCell(1).innerHTML= "<label>"+item.strTransType+"</label>";
				row1.insertCell(2).innerHTML= "<a id=\"urlDocCode\" href=\"openSlipLedger.html?docCode="+item.strVoucherNo+","+item.strTransType+"\" target=\"_blank\" >"+item.strVoucherNo+"</a>";
				row1.insertCell(3).innerHTML= "<label>"+item.strChequeBillNo+"</label>";
				row1.insertCell(4).innerHTML= "<label>"+dteBillDate+"</label>";
// 				if(item.strTransType=="Opening")
// 				{
					 
// 					row1.insertCell(5).innerHTML= "<label>0</label>";
// 					row1.insertCell(6).innerHTML= "<label>0</label>";
// 					if(bal<0){
// 						row1.insertCell(7).innerHTML= "<label>("+bal.toFixed(maxQuantityDecimalPlaceLimit)*(-1)+")</label>";
// 						}else{
// 							row1.insertCell(7).innerHTML= "<label>"+bal.toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
// 						}
					
// 				}else{
				if(item.dblDebitAmt<0){
				row1.insertCell(5).innerHTML= "<label>("+(item.dblDebitAmt/currValue).toFixed(maxQuantityDecimalPlaceLimit)*(-1)+")</label>";
				}else{
					row1.insertCell(5).innerHTML= "<label>"+(item.dblDebitAmt/currValue).toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
				}
				if(item.dblCreditAmt<0){
					row1.insertCell(6).innerHTML= "<label>("+(item.dblCreditAmt/currValue).toFixed(maxQuantityDecimalPlaceLimit)*(-1)+")</label>";
				}else{
					row1.insertCell(6).innerHTML= "<label>"+(item.dblCreditAmt/currValue).toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
				}
				if(bal<0){
				row1.insertCell(7).innerHTML= "<label>("+bal.toFixed(maxQuantityDecimalPlaceLimit)*(-1)+")</label>";
				}else{
					row1.insertCell(7).innerHTML= "<label>"+bal.toFixed(maxQuantityDecimalPlaceLimit)+"</label>";
				}
// 				}
				rowCount=rowCount+1;
				
			}	
		});
		
		var table = document.getElementById("tblEmployeeLedgerBillTot");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML = "<label>Transaction Type</label>";
		row.insertCell(1).innerHTML = "<label>Amount</label>";
		rowCount = rowCount + 1;
	
	    var row1 = table.insertRow(rowCount);
		
		
		row1.insertCell(0).innerHTML = "<label>Opening Balance</label>";
		if(opBal<0)
		{
		row1.insertCell(1).innerHTML = "<label>("+ parseFloat(opBal).toFixed(maxQuantityDecimalPlaceLimit)*(-1) + ")</label>";
		
		}else{
			row1.insertCell(1).innerHTML = "<label>"+ parseFloat(opBal).toFixed(maxQuantityDecimalPlaceLimit) + "</label>";
		}
	

		rowCount = rowCount + 1;
		
		row1 = table.insertRow(rowCount);
		row1.insertCell(0).innerHTML = "<label>Total Debit</label>";
		row1.insertCell(1).innerHTML = "<label>"+ parseFloat(dr).toFixed(maxQuantityDecimalPlaceLimit) + "</label>";
		

		rowCount = rowCount + 1;
		row1 = table.insertRow(rowCount);
		row1.insertCell(0).innerHTML = "<label>Total Credit</label>";
		row1.insertCell(1).innerHTML = "<label>"+ parseFloat(cr).toFixed(maxQuantityDecimalPlaceLimit)+ "</label>";

		rowCount = rowCount + 1;
		row1 = table.insertRow(rowCount);
		row1.insertCell(0).innerHTML = "<label>Closing Balance</label>";
		if(bal<0){
		row1.insertCell(1).innerHTML = "<label>("+ parseFloat(bal).toFixed(maxQuantityDecimalPlaceLimit)*(-1) + ")</label>";
	    }else{
		row1.insertCell(1).innerHTML = "<label>"+ parseFloat(bal).toFixed(maxQuantityDecimalPlaceLimit) + "</label>";
	    }
		
		closingAmt=bal;

	}
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
	function funSetData(code){

		switch(fieldName){

			case 'EmployeeAccountCode' : 
				funSetGLCode(code);
				break;
				
			case 'employeeCode' : 
				funSetEmployeeCode(code);
				break;
				
			
		}
	}
	
	function funSetGLCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strVouchNo!="Invalid")
		    	{
					$("#txtGLCode").val(response.strAccountCode);
					$("#lblGLCode").text(response.strAccountName);
					$("#txtFromEmployeeCode").focus();					
		    	}
		    	else
			    {
			    	alert("Invalid Receipt No");
			    	$("#txtVouchNo").val("");
			    	$("#txtVouchNo").focus();
			    	return false;
			    }
			},
			error : function(e){
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
	
	function funSetEmployeeCode(employeeCode)
	{
	   
		var searchurl=getContextPath()+"/loadEmployeeMasterData.html?employeeCode="+employeeCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strEmployeeCode=='Invalid Code')
			        	{
			        		alert("Invalid Employee Code");
			        		$("#txtFromEmployeeCode").val('');
			        	}
			        	else
			        	{					        	    			        	    
			        	   
			        	    $("#txtFromEmployeeCode").val(employeeCode);
			        	    $("#lblFromEmployeeName").text(response.strEmployeeName);
			        	
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

	
	function funGetCurrencyCode(code){

		var amt=1;
		$.ajax({
			type : "POST",
			url : getContextPath()+ "/loadCurrencyCode.html?docCode=" + code,
			dataType : "json",
			async:false,
			success : function(response){ 
				if(response.strCurrencyCode=='Invalid Code')
	        	{
	        		
	        	}
	        	else
	        	{        
	        		amt=response.dblConvToBaseCurr;
		        	
		        	
		        
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
		return amt;
	}
	
</script>

</head>
<body>

	<div id="formHeading">
		<label>Employee Ledger</label>
	</div>

	<br/>
	<br/>

	<s:form name="EmployeeLedger" method="POST" action="">

		<table class="masterTable">
			<tr>
				<td>
					<label>GL Code</label>
				</td>
				<td>
					<s:input type="text" id="txtGLCode" path="strGLCode" class="searchTextBox" ondblclick="funHelp('EmployeeAccountCode');"/>
				</td>
				<td  colspan="3"><label id="lblGLCode"></label></td>
			</tr>
			
			<tr>
				<td>
					<label>Employee Code</label>
				</td>
				<td>
					<s:input type="text" id="txtFromEmployeeCode" path="strFromEmployeeCode" class="searchTextBox" ondblclick="funHelp('employeeCode');"/>
				</td>
				<td>
					<label id="lblFromEmployeeName"></label>
				</td>
				<td colspan="2"></td>
				
				
			</tr>

			<tr>
				<td>
					<label>From Date</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtFromDate" path="dteFromDate" cssClass="calenderTextBox" />
				</td>
				
				<td>
					<label>To Date</label>
				</td>
				<td>
					<s:input type="text" id="txtToDate" path="dteToDate" cssClass="calenderTextBox" />
				</td>
				<td></td>
			</tr>
			<tr>
			<td width="10%"><label>Currency</label></td>
				<td><s:select id="cmbCurrency" path="currency" items="${currencyList}" cssClass="BoxW124px"></s:select></td>
				<td width="10%"><label>Report Type</label></td>
				<td>
					<s:select id="cmbReportType" path="strReportType" class="BoxW124px" >
						<option value="EXCEL">EXCEL</option>
						<option value="PDF">PDF</option>
					</s:select>
				</td>
				<td colspan="1"></td>
			</tr>
			
			<tr>
			<td ><p align="center">
			<input type="button" value="Execute" id="btnExecute"   class="form_button" /></p><td>
			<td ><p align="center"><input type="button" value="Export" id="btnExport" class="form_button" /></p></td>
			
			<td colspan="4"></td>
			</tr>
			
		</table>

		<br>
		<br>
	
			
			<div id="dvEmployeeLedgerBill" style="width: 100% ;height: 100% ;">
<!-- 				<table id="tblCreditorLedgerBill"  class="transTable col2-right col3-right">					 -->
		<table id="tblEmployeeLedgerBill"  class="transTable col5-right col6-right col7-right col8-right col9-right"></table>
		</div>
		<br> <br>
		<div id="dvEmployeeLedgerBillTot" style="width: 30% ;height: 100% ;">
				<table id="tblEmployeeLedgerBillTot" class="transTable col2-right"></table>
			</div>
			

	</s:form>
</body>
</html>
