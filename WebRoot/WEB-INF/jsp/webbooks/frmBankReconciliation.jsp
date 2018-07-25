<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	$(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		
		$("#txtFromDate" ).datepicker('setDate', 'today');
		$("#txtToDate" ).datepicker('setDate', 'today');
		var bankCode = $("#txtGLCode").val();
		if(bankCode!='')
		{
			funSetBankCode(bankCode);
		}
	});
	

		function funHelp(transactionName)
	    {
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	     }
	 
		function funSetData(code){

		switch(fieldName){

			case 'bankAccNo' : 
				funSetBankCode(code);
				break;
				
		
		}
	}
	
		
	function funSetBankCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strVouchNo!="Invalid")
		    	{
					$("#txtGLCode").val(response.strAccountCode);
					$("#lblGLCode").text(response.strAccountName);
						
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
	

	
	function funFillData()
	{
		
		var accCode=$("#txtGLCode").val();
		var frmDate=$("#txtFromDate").val();
		var toDate=$("#txtToDate").val();
		var currency=$("#cmbCurrency").val();
		$('#tblDetails').empty();
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadPaymentReceiptBankData.html?accountCode="+accCode+"&frmDate="+frmDate+"&toDate="+toDate+"&currency="+currency,
			dataType : "json",
			success : function(response){ 
				if(response.strVouchNo=="Invalid")
		    	{
					$("#txtGLCode").val(response.strAccountCode);
					$("#lblGLCode").text(response.strAccountName);
						
		    	}
		    	else
			    {
		    		
		    		 $.each(response,function(i,item)
		   				  {
		   	  
		    			 funAddDetailsRow(response[i].strTransMode,response[i].strVouchNo,response[i].strChequeNo,response[i].dteChequeDate,response[i].dblAmt,response[i].dteClearence,response[i].strDrawnOn,response[i].dteVouchDate);
		               	  });
			    	
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
	
	function funAddDetailsRow(strTransMode,strVouchNo,strChequeNo,dteChequeDate,dblAmt,chequeCleared,strDrawnOn,dteVouchDate) 
	{
	    var table = document.getElementById("tblDetails");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	   var date='';
	   if(parseFloat(dblAmt) <0)
		 {
		   dblAmt="("+dblAmt*(-1)+")";
		 }
	  
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" name=\"listBankReconciliationDtl["+(rowCount)+"].strTransType\" id=\"strTransType."+(rowCount)+"\" value='"+strTransMode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" name=\"listBankReconciliationDtl["+(rowCount)+"].strVouchNo\" id=\"strDebtorCode."+(rowCount)+"\" value='"+strVouchNo+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listBankReconciliationDtl["+(rowCount)+"].strChequeNo\" id=\"strChequeNo."+(rowCount)+"\" value='"+strChequeNo+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"9%\" name=\"listBankReconciliationDtl["+(rowCount)+"].dteCheque\" id=\"dteCheque."+(rowCount)+"\"style=\"text-align: center;\" value='"+dteChequeDate+"' />";
	    
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\"    id=\"strDrawnOn."+(rowCount)+"\" value='"+strDrawnOn+"'  />";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\"    id=\"dteVouchDate."+(rowCount)+"\" value='"+dteVouchDate+"'  />";
	    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"17%\" name=\"listBankReconciliationDtl["+(rowCount)+"].dblAmount\" style=\"text-align: right;\"  id=\"dblAmount."+(rowCount)+"\" value='"+dblAmt+"'  />";
// 		input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"
// 		row.insertCell(4).innerHTML= "<input  class=\"calenderTextBox\" size=\"23%\" name=\"listBankReconciliationDtl["+(rowCount)+"].dblAmount\" style=\"text-align: right;\"   />";
		if(chequeCleared=='01/01/1990')
			{
			row.insertCell(7).innerHTML= "<input type=\"text\"   class=\"calenderTextBox\"  size=\"18%\" name=\"listBankReconciliationDtl["+(rowCount)+"].dteClearing\" id=\"dteClearing."+(rowCount)+"\"   />";
			}else{
		    row.insertCell(7).innerHTML= "<input type=\"text\"   class=\"calenderTextBox\"  size=\"18%\" name=\"listBankReconciliationDtl["+(rowCount)+"].dteClearing\" id=\"dteClearing."+(rowCount)+"\"  value='"+chequeCleared+"' />";
			}
		/* row.insertCell(6).innerHTML= "<input id=\"chequeCleared."+(rowCount)+"\" size=\"10%\" name=\"chequeCleared\" type=\"checkbox\" class=\"CustCheckBoxClass\" name=\"listBankReconciliationDtl["+(rowCount)+"].chequeCleared\" id=\"chequeCleared."+(rowCount)+"\" checked=\"unchecked\" value='"+chequeCleared+"' />"; */
		//chequeCleared
		$("#dteClearing.1").datepicker();
		$( ".calenderTextBox" ).datepicker();
	}

	function funsetDate(row)
	{
		var index=row.parentNode.parentNode.rowIndex;
		//var Qty=document.getElementById("txtQty."+index).value;
		var d=new Date();
		d.setFullYear(2020, 11, 3);
		document.getElementById("dteClearing."+index).innerHTML = d;
		/* $("#dteClearing."+index).datepicker({ dateFormat: 'dd-mm-yy' });
		 $("#dteClearing."+index).datepicker('setDate', '10-06-2018'); */
	}
</script>
<body>
	<div id="formHeading">
		<label>Bank Reconciliation</label>
	</div>

	<br />
	<br />

	<s:form name="BankReconciliation" method="GET" action="rptBankReconciliation.html" >
		<div>
			<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
					<td colspan="4"></td>
					
				</tr>
				<tr>
					
				<td>
					<label>Bank Code</label>
				</td>
				<td>
					<s:input type="text" id="txtGLCode" path="strGLCode" class="searchTextBox" ondblclick="funHelp('bankAccNo');"/>
				</td>
				<td  colspan="3"><label id="lblGLCode"></label></td>
		
<!-- 					<td><label>Currency </label></td> -->
<%-- 					<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px"> --%>
<%-- 						</s:select></td> --%>
					<td colspan="2"></td>
				</tr>
				<tr><td><input type="button" id="btnExcecute" value="Excecute"  class="form_button" onclick="funFillData()" /></td>
				<td colspan="7"></td>
				</tr>
				
			</table>
		</div>
		<br/>
		<br/>
		<div class="dynamicTableContainer" style="height: 300px;width: 99.80%;">
		<table
			style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
			<tr bgcolor="#72BEFC">
				<td style="width:6.5%;">Trans Type</td>
				<td style="width:6.1%;">Vouch No </td>
				<td style="width:6.2%;">Cheque No</td>
				<td style="width:6.2%;">Cheque Date</td>
				<td style="width:6.3%;">Drawn On </td>
				<td style="width:6.0%;">Voucher Date</td>
				<td style="width:6.5%;">Amount</td>
				<td style="width:7.0%;">Clearing Date</td>
				
				
			</tr>
		</table>
		
		<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
			<table id="tblDetails"
				style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
				class="transTablex col8-center">
				<tbody>
				
				 	<col style="width:8%;">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:8%">
					
			
				</tbody>
			</table>
		</div>
		</div>
		<p align="center">
				<input type="submit" value="Submit"  class="form_button" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
	</s:form>

</body>
</html>