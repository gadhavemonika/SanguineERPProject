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
	var debtorName;

	$(function() 
	{
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() 
		{
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();
			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		
		var now = new Date();
		$("#cmbMonth").val(now.getMonth()+1);
    	
		$("#txtVouchDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtVouchDate").datepicker('setDate', 'today');
		$("#txtChequeDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtChequeDate").datepicker('setDate', 'today');
		
		$("#txtDteScFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtDteScFromDate").datepicker('setDate', 'today');
		$("#txtDteScToDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtDteScToDate").datepicker('setDate', 'today');
		document.getElementById('txtChequeNo').style.visibility='hidden';
		  
// 		$("#txtDebtorCode").prop('disabled', true);
	
	  document.all[ 'tab3' ].style.display = 'none';
	

		
		var message='';
		var retval="";
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%> alert("Data Save successfully\n\n"+message); <%
			}
		}%>
		
		var code='';
		<%
		if(null!=session.getAttribute("rptVoucherNo"))
		{%>
			code='<%=session.getAttribute("rptVoucherNo").toString()%>';
			<%session.removeAttribute("rptVoucherNo");%>
			var isOk=confirm("Do You Want to Generate Slip?");
			if(isOk)
			{
				window.open(getContextPath()+"/openRptPaymentReport.html?docCode="+code,'_blank');
			}
					
			
		<%}%>
		
	});

	
	$(document).ready(function () {
		  document.all[ 'tab3' ].style.display = 'none';
		  document.all[ 'SC Bill' ].style.display = 'none';
		 

			if($("#hidaccountCode").val()!='')
			{
				var misCode=$("#txtMISCode").val();
				
				funSetAccountDetails($("#hidaccountCode").val());
				var amt=$("#hidclosingAmt").val()*(-1);
				
				funSetcreditorMasterData($("#hidcreditorCode").val());
				$("#txtAmount").val(amt);
				$("#txtAmt").val(amt);
				
				  
			}
	});
	function funBillType()
	{

		
		var billType=$("#cmbBillType").val();
		
		if(billType=='SC Bill')
			{
				 document.all[ 'tab2' ].style.display = 'none';
				 document.all[ 'GRN' ].style.display = 'none';
				 document.all[ 'tab3' ].style.display = 'block';
				 document.all[ 'SC Bill' ].style.display = 'block';
				 document.all[ 'tab1' ].style.display = 'none';
// 				 document.all[ 'Details' ].style.display = 'none';
			
			}else{
				
				document.all[ 'tab3' ].style.display = 'none';
				document.all[ 'SC Bill' ].style.display = 'none';
				document.all[ 'tab2' ].style.display = 'block';	
				document.all[ 'GRN' ].style.display = 'block';
				document.all[ 'tab1' ].style.display = 'none';
// 			document.all[ 'Details' ].style.display = 'none';
			}
		
	}
	function funSetData(code){

		switch(fieldName){

			case 'PaymentNo' : 
				funSetVouchNo(code);
				break;
				
			case 'cashBankAccNo' : 
				funSetBankAccountDetails(code);
				break;
				
			case 'bankCode' : 
				funSetDrawnOn(code);
				break;
				
			case 'accountCode' : 
				funSetAccountDetails(code);
				break;
				
			case 'creditorCode' : 
// 				funSetMemberDetails(code);
				funSetcreditorMasterData(code)
				break;
				
			
			case 'debtorCode' : 
				//	funSetMemberDetails(code);
					funSetDebtorMasterData(code)
					break;
					
			case 'PaymentNoslip' : 
				funSetVouchNoSlip(code);
				break;
				
			case 'employeeCode' : 
				funSetEmployeeMasterData(code)
				break;	
		}
	}


	function funSetVouchNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadPayment.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strVouchNo!="Invalid")
		    	{
		    		funFillHdData(response);
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

// Function to set Bank Account Details
	function funSetBankAccountDetails(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtBankCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBankCode").val(response.strAccountCode);
		        	$("#lblBankDesc").text(response.strAccountName);
		        	$("#txtVouchDate").focus();
		        	if(response.strType=="Bank"){
		        		$("#cmbType").val("Cheque");
		        		funSetTypeLabel();
		        	}else{
		        		$("#cmbType").val("Cash");
		        		funSetTypeLabel();
		        	}
		        	funSetBankBalanceAmt(response.strType);
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



	function funSetDrawnOn(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+"/loadBankMasterData.html?bankCode="+code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Bank Code");
	        		$("#txtDrawnOn").val('');
	        	}
	        	else
	        	{
	        		$("#txtDrawnOn").val(response.strBankCode);
	        		$("#lblDrawnOnDesc").text(response.strBankName);
	        		$("#txtBranch").focus();
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
	
	
// Function to set account details from help	
	function funSetAccountDetails(code){
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtAccCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtAccCode").val(response.strAccountCode);
		        	$("#txtDescription").val(response.strAccountName);
		        	
		        	if(response.strDebtor=='Yes' || response.strCreditor=='Yes' || response.strEmployee=='Yes')
		        	{
		        			if(response.strDebtor=='Yes')
		        			{
		        			$("#lblCrdDebCode").text("Debtor Code");
		        			$("#hidSundryType").val("Debtor Code"); 
		        			
		        			}
		        			else if(response.strCreditor=='Yes')
		        			{
		        				$("#lblCrdDebCode").text("Creditor Code");
		        				$("#hidSundryType").val("Creditor Code");
		        			}
		        			else
		        			{
		        				$("#lblCrdDebCode").text("Employee Code");
			        			$("#hidSundryType").val("Employee Code"); 
		        			}	
		        		
 		        		$("#txtDebtorCode").prop('disabled', false);
		        	}
		        	else
		        	{
		        		$("#txtDebtorCode").val('');
 		        		$("#txtDebtorCode").prop('disabled', true);
 		        		$("#txtDebtorCode").focus();
		        	}
		        //	$("#txtDebtorCode").focus();
		        	
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



function funSetcreditorMasterData(creditorCode)
	{
	   
		var searchurl=getContextPath()+"/loadSundryCreditorMasterData.html?creditorCode="+creditorCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.Creditor=='Invalid Code')
			        	{
			        		alert("Invalid creditor Code");
			        		$("#txtDebtorCode").val('');
			        	}
			        	else
			        	{					        	    			        	    
			        	    /* Debtor Details */
			        	    funRemoveAccountRows("tblGRN");
			        	    $("#txtDebtorCode").val(creditorCode);
			        	    $("#txtDebtorName").val(response.strFirstName);
			        	    $("#cmbCurrency").val(response.strCurrencyType);
			        	    $("#txtDblConversion").val(response.dblConversion);
			        	    $("#txtGRNPayAmount").val('0.0');
				    		$("#txtTotalGRNAmount").val('0.0');
				    		
				    		if($("#cmbBillType").val()=="GRN")
				    			{
			        	    funGetUnPayedGRN(creditorCode,response.strClientCode);
				    			}
				    		
				    		 funSetBalanceAmt("Creditor");
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


function funSetBankBalanceAmt(accType){
	var accCode=$("#txtBankCode").val();
	var invDate=$("#txtVouchDate").val();
	
	var currencyCode=$("#cmbCurrency").val();
// 	var currValue=funGetCurrencyCode(currencyCode);
// 	if(currencyCode==null)
// 	{
// 		currencyCode="";
// 	}
		var currValue=$("#txtDblConversion").val();
   		if(currValue==null ||currValue==''||currValue==0)
   		{
   		  currValue=1;
   		}
		var gurl1=getContextPath()+"/loadBankBalanceAmt.html?accType="+accType+"&accCode="+accCode+"&toDate="+invDate+"&currency="+currencyCode;
		$.ajax({
		    type: "GET",
		    url: gurl1,
		    dataType: "json",
		    success: function(response)
		    {	
		    	var amount = (response/currValue).toFixed(maxAmountDecimalPlaceLimit);
		    	if(amount<0)
		    	{
		    		amount="("+amount*-1+")";
		    	}
		    	
		    	$("#lblBankBalAmt").text("Balance Amt:"+amount);
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

function funSetBalanceAmt(CredDebType)
{
		var debtorCode=$("#txtDebtorCode").val();
		var invDate=$("#txtVouchDate").val();
		var type1="";
		if(CredDebType=="Debtor")
		{
			type1="Debtor";
		}
		else if(CredDebType=="Creditor")
		{
			type1="Creditor";
		}

		
		var currencyCode=$("#cmbCurrency").val();
// 		var currValue=funGetCurrencyCode(currencyCode);
		if(currencyCode==null)
		{
			currencyCode="";
		}
		var gurl1=getContextPath()+"/loadBalanceAmtCreditorDetor.html?type="+type1+"&custSuppCode="+debtorCode+"&toDate="+invDate+"&currency="+currencyCode;
				
	$.ajax({
        type: "GET",
        url: gurl1,
        dataType: "json",
        success: function(response)
        {		        	
        	var amount = (response).toFixed(maxAmountDecimalPlaceLimit);
	    	if(amount<0)
	    	{
	    		amount="("+amount*-1+")";
	    	}
	    	
	    	$("#lblBalanceAmt").text("Balance Amt:"+amount);
        	
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




function funSetDebtorMasterData(debtorCode)
{
   
	var searchurl=getContextPath()+"/loadSundryDebtorMasterData.html?debtorCode="+debtorCode;
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strDebtorCode=='Invalid Code')
		        	{
		        		alert("Invalid Debtor Code");
		        		$("#txtDebtorCode").val('');
		        	}
		        	else
		        	{					        	    			        	    
		        	    /* Debtor Details */
		        	   
		        		   
			        	    $("#txtDebtorCode").val(debtorCode);
			        	    $("#txtDebtorName").val(response.strFirstName);
			        	  
			        	    funSetBalanceAmt("Debtor");
		        	
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

// Fill Header Data	
	function funFillHdData(response)
	{
		$("#txtVouchNo").val(response.strVouchNo);
		$("#txtVouchDate").val(response.dteVouchDate);
		$("#txtChequeDate").val(response.dteChequeDate);
		$("#txtBankCode").val(response.strBankCode);
    	$("#lblBankDesc").text(response.strBankAccDesc);
    	$("#cmbType").val(response.strType);
    	funSetTypeLabel();
    	if(response.strType!="Cash"){
    		$("#txtChequeNo").val(response.strChequeNo);	
    		
    	}
    	$("#txtAmt").val(response.dblAmt);
    	$("#txtDrawnOn").val(response.strDrawnOn);
    	$("#lblDrawnOnDesc").text(response.strDrawnDesc);
    	$("#txtBranch").val(response.strBranch);
    	$("#txtDrCr").val(response.strCrDr);
    	$("#txtNarration").val(response.strNarration);
    	$("#cmbMonth").val(response.intMonth);
    	$("#cmbCurrency").val(response.strCurrency);
    	$("#txtDblConversion").val(response.dblConversion);
		funFillDtlGrid(response.listPaymentDetailsBean);
		funFillGRNDtlGrid(response.listPaymentGRNDtl);
	}
	
	
// Fill Account details and Debtor Details Grid	
	function funFillDtlGrid(resListPaymentDtlBean)
	{
		funRemoveProductRows();
		$.each(resListPaymentDtlBean, function(i,item)
        {			
			debtorName=resListPaymentDtlBean[i].strDebtorName;
			funAddDetailsRow(resListPaymentDtlBean[i].strAccountCode,resListPaymentDtlBean[i].strDebtorCode
				,resListPaymentDtlBean[i].strDescription,resListPaymentDtlBean[i].strDC,''
				,resListPaymentDtlBean[i].dblDebitAmt,resListPaymentDtlBean[i].dblCreditAmt);
        });
	}
	
	// Fill Account details and Debtor Details Grid	
	function funFillGRNDtlGrid(resListPaymentGRNDtlBean)
	{
		funRemoveAccountRows("tblGRN");
		$.each(resListPaymentGRNDtlBean, function(i,item)
        {			
			funLoadGRNTable(resListPaymentGRNDtlBean[i].strGRNCode,resListPaymentGRNDtlBean[i].strGRNBIllNo,resListPaymentGRNDtlBean[i].dblGRNAmt,
					resListPaymentGRNDtlBean[i].dteGRNDate,resListPaymentGRNDtlBean[i].dteBillDate,resListPaymentGRNDtlBean[i].dteGRNDueDate,resListPaymentGRNDtlBean[i].strSelected);
        });
	}



// Get Detail Info From detail fields and pass them to function to add into detail grid
	function funGetDetailsRow() 
	{
		var accountCode =$("#txtAccCode").val().trim();
		
		var accountName =$("#lblBankDesc").text().trim();
		var bankCode =$("#txtBankCode").val().trim();
		var transAmt=parseFloat($("#txtAmount").val());
		var chequeData = $("#txtChequeNo").val();
		
		if(accountCode=='')
		{
			alert('Select Account Code!!!');
			return;
		}
		if(transAmt<1)
		{
			alert('Enter Transaction Amt!!!');
			return;
		}
		var type=$("#cmbType").val();
		switch(type)
		{
			case "Cash":
				break;
				
			case "Credit Card":
				if(chequeData=='')
			 		{
			 			alert('Fill Credit Card Number!!!');
			 			$("#txtChequeNo").focus();
			 			return;
			 		}
				break;

			case "Cheque":
				if(chequeData=='')
		 		{
		 			alert('Fill Cheque Number!!!');
		 			$("#txtChequeNo").focus();
		 			return;
		 		}
				break;
	
			case "NEFT":
				if(chequeData=='')
		 		{
		 			alert('Fill NEFT Number!!!');
		 			$("#txtChequeNo").focus();
		 			return;
		 		}
				break;
		}
		
	    var debtorCode=$("#txtDebtorCode").val();
	    var description=$("#txtDescription").val();
	    var transType=$("#cmbDrCr").val();
	    var dimension=$("#cmbDimesion").val();
	    var debitAmt=0;
	    var creditAmt=0;
	    var grn=$("#hidGrnYN").val();
	    if(grn=='Y')
	    	{
//	 	    if(transType=='Dr')
//	 	    {
		    	debitAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);	
		    	funAddDetailsRow(accountCode,debtorCode,description,'Dr',dimension,debitAmt,'0');
//	 	    }
//	 	    else
//	 	    {
//	 	    }
		   		 creditAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);	    
		    	funAddDetailsRow(bankCode,'',accountName,'Cr',dimension,'0',creditAmt);
	    	}else
	    	{
// 	    		 if(transType=='Dr')
// 	    			{ 
	    			 debitAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);	
	    			 funAddDetailsRow(accountCode,debtorCode,description,'Dr',dimension,debitAmt,'0');
// 	    		 	}else
// 	    		 	{
 	    		 		 	creditAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);	    
	    			    	funAddDetailsRow(bankCode,'',accountName,'Cr',dimension,'0',creditAmt);
// 	    		 	}
	    		
	    	}

	    
	    
	}
	
	
// Function to add detail grid rows	
	function funAddDetailsRow(accountCode,debtorCode,description,transType,dimension,debitAmt,creditAmt) 
	{
	    var table = document.getElementById("tblReceiptDetails");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" size=\"5%\" name=\"listPaymentDetailsBean["+(rowCount)+"].strAccountCode\" id=\"strAccountCode."+(rowCount)+"\" value='"+accountCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" name=\"listPaymentDetailsBean["+(rowCount)+"].strDebtorCode\" id=\"strDebtorCode."+(rowCount)+"\" value='"+debtorCode+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"18%\" name=\"listPaymentDetailsBean["+(rowCount)+"].strDescription\" id=\"strDescription."+(rowCount)+"\" value='"+description+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listPaymentDetailsBean["+(rowCount)+"].strDC\" id=\"strDC."+(rowCount)+"\" value='"+transType+"' />";
	    row.insertCell(4).innerHTML= "<input type=\"text\" class=\" debitAmt\"  onblur=\"Javacsript:funUpdateDebitAmount(this)\" size=\"10%\" name=\"listPaymentDetailsBean["+(rowCount)+"].dblDebitAmt\" style=\"text-align: right;width:98%;\" id=\"dblDebitAmt."+(rowCount)+"\" value='"+debitAmt+"'/>";
	    row.insertCell(5).innerHTML= "<input type=\"text\" class=\" creditAmt\" onblur=\"Javacsript:funUpdateCreditAmount(this)\"  size=\"10%\" name=\"listPaymentDetailsBean["+(rowCount)+"].dblCreditAmt\" style=\"text-align: right;width:98%;\" id=\"dblCreditAmt."+(rowCount)+"\" value='"+creditAmt+"'/>";
	    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listPaymentDetailsBean["+(rowCount)+"].strDimension\" id=\"strDimension."+(rowCount)+"\" value='"+dimension+"'/>";	        
	    row.insertCell(7).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"2%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>";
// 	    row.insertCell(8).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" size=\"1%\" name=\"listPaymentDetailsBean["+(rowCount)+"].strDebtorName\" id=\"strDebtorName."+(rowCount)+"\" value='"+debtorName+"' />";
	    
	    debtorName='';
// 	    $("#txtDebtorCode").prop('disabled', true);
	    funCalculateTotalAmt();
	    funResetDetailFieldsButNotAmount();
	}
	
	
	function funUpdateDebitAmount(object)
	{
		var index=object.parentNode.parentNode.rowIndex;
		document.getElementById("strDC."+index).value="Dr";
		document.getElementById("dblCreditAmt."+index).value=parseFloat(0).toFixed(maxQuantityDecimalPlaceLimit);	;
		//funGetTotal();
		funCalculateTotalAmt();
		
	}
	
	function funUpdateCreditAmount(object)
	{
		var index=object.parentNode.parentNode.rowIndex;
		
		document.getElementById("strDC."+index).value="Cr";
		document.getElementById("dblDebitAmt."+index).value=parseFloat(0).toFixed(maxQuantityDecimalPlaceLimit);	;
		//funGetTotal();
		funCalculateTotalAmt();
		
	}


//Delete a All record from a grid
	function funRemoveProductRows()
	{
		var table = document.getElementById("tblReceiptDetails");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	

//Function to Delete Selected Row From Grid
	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblReceiptDetails");
	    table.deleteRow(index);
	    funCalculateTotalAmt();
	}

	
//Calculating total amount
	function funCalculateTotalAmt()
	{
		var totalAmt=0.00;
		var totalDebitAmt=0.00;
		var totalCreditAmt=0.00;
		
		$('#tblReceiptDetails tr').each(function() 
		{
			var debitAmt=parseFloat($(this).find(".debitAmt").val());
		    totalDebitAmt = totalDebitAmt + parseFloat($(this).find(".debitAmt").val());
		    totalCreditAmt = totalCreditAmt + parseFloat($(this).find(".creditAmt").val());
		});

		totalDebitAmt=parseFloat(totalDebitAmt).toFixed(maxAmountDecimalPlaceLimit);
		totalCreditAmt=parseFloat(totalCreditAmt).toFixed(maxAmountDecimalPlaceLimit);
		
		totalAmt=totalDebitAmt-totalCreditAmt;
		totalAmt=parseFloat(totalAmt).toFixed(maxAmountDecimalPlaceLimit);
		
		$("#lblDebitAmt").text(totalDebitAmt);
		$("#lblCreditAmt").text(totalCreditAmt);
		$("#lblDiffAmt").text(totalAmt);
		$("#txtTotalAmt").val(totalAmt);
	}


// Reset Detail Fields
	function funResetDetailFields()
	{
		$("#txtAccCode").val('');
	    $("#txtDebtorCode").val('');
	    $("#txtDescription").val('');
	    $("#cmbDrCr").val('Dr');
	    $("#txtAmount").val('0.00');
	    $("#cmbDimesion").val('No');	    
	}
	
	function funResetDetailFieldsButNotAmount()
	{
		$("#txtAccCode").val('');
	    $("#txtDebtorCode").val('');
	    $("#txtDescription").val('');
	    $("#cmbDrCr").val('Dr');
	   
	    $("#cmbDimesion").val('No');	    
	}
	
	
// Reset Header Fields
	function funResetHeaderFields()
	{
		$("#txtVouchNo").val('');
		$("#txtNarration").val('');
		$("#txtVouchDate").datepicker('setDate', 'today');
		$("#txtChequeDate").datepicker('setDate', 'today');
		$("#cmbMonth").val('January');
		$("#txtBankCode").val('');
		$("#lblBankDesc").text('');
		$("#txtAmt").val('0');
		$("#cmbType").val('Cash');
		$("#lblTypeDesc").text('');
		$("#txtChequeNo").val('');
		$("#txtDrawnOn").val('');
		$("#lblDrawnOnDesc").text('');
		$("#txtBranch").val('');
		$("#txtNarration").val('');
		
// 		$("#txtDebtorCode").prop('disabled', true);
	}


	
// Function invoked on change value event on select tag.	
	function funSetTypeLabel()
	{
		var type=$("#cmbType").val();
		switch(type)
		{
			case "Cash":
				$("#lblTypeName").text("Cash");
				document.getElementById('txtChequeNo').style.visibility='hidden';
				break;
				
			case "Credit Card":
				$("#lblTypeName").text("Credit Card");	
				document.getElementById('txtChequeNo').style.visibility='visible';
				break;

			case "Cheque":
				$("#lblTypeName").text("Cheque No");
				document.getElementById('txtChequeNo').style.visibility='visible';
				break;
	
			case "NEFT":
				$("#lblTypeName").text("NEFT No");
				document.getElementById('txtChequeNo').style.visibility='visible';
				break;
		}
	}


// Function to Validate Header Fields
	function funValidateHeaderFields()
	{
		if($("#txtVouchDate").val()=='')
		{
			alert('Please Select Vouch Date!!!');
			return false;
		}
		
		if($("#txtChequeDate").val()=='')
		{
			alert('Please Select Cheque Date!!!');
			return false;
		}
		
		if($("#txtBankCode").val()=='')
		{
			alert('Please Enter Bank Account No!!!');
			return false;
		}
		
		var amt=parseFloat($("#txtAmt").val());
		if(amt<1)
		{
			alert('Please Enter Amount!!!');
			return false;
		}
		var debitAmt =parseFloat($("#lblDebitAmt").text())
		var creditAmt =parseFloat($("#lblCreditAmt").text())
		if(debitAmt>0 && creditAmt>0 && (debitAmt - creditAmt)==0  )
			{
			  return true;
			}else
				{
				  alert("Balance must be Zero");
				  return false;
				}
	}
	function funHelp(transactionName)
	{
		$("#lblBalanceAmt").text('');
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funHelp1()
	{ 
		if($("#hidSundryType").val()=="Debtor Code")
		{
			 funHelp('debtorCode');
		}
		if($("#hidSundryType").val()=="Creditor Code")
		{
			 funHelp('creditorCode');
		}
		if($("#hidSundryType").val()=="Employee Code")
		{
			 funHelp('employeeCode');
		}
		
		
	}
	
	function funGetUnPayedGRN(strDebtor,strClientCode)
	{
		var abc="";
		var strCurrency = $("#cmbCurrency").val();
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadUnPayedGrn.html?strDebtorCode="+strDebtor+"&strCurrency="+strCurrency,
			dataType : "json",
			success : function(response){ 
				$.each(response, function(i, item) {
					
						funLoadGRNTable(response[i].strGRNCode,response[i].strGRNBIllNo,response[i].dblGRNAmt,response[i].dteGRNDate,response[i].dteBillDate,response[i].dteGRNDueDate,"No");
				});	
	        
			},
			error : function(jqXHR, exception){
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
	
	function funLoadGRNTable(strGRNCode,strGRNBIllNo,dblGRNAmt,dteGRNDate,dteBillDate,dteGRNDueDate,isSelected)
	{
		var totalGrnAmt = parseFloat(dblGRNAmt);
		 var table = document.getElementById("tblGRN");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    if(isSelected=="Tick")
		    	{
		    	 row.insertCell(0).innerHTML= "<input id=\"cbGRNSel."+(rowCount)+"\" name=\"listPaymentGRNDtl["+(rowCount)+"].strSelected\" type=\"checkbox\" class=\"PropCheckBoxClass\" checked=\"checked\"   value='Tick' onclick=\"funGRNChkOnClick()\" />";
		    	}else
		    	{
		    		 row.insertCell(0).innerHTML= "<input id=\"cbGRNSel."+(rowCount)+"\" name=\"listPaymentGRNDtl["+(rowCount)+"].strSelected\" type=\"checkbox\" class=\"PropCheckBoxClass\"   value='Tick' onclick=\"funGRNChkOnClick()\" />";	
		    	}
		   
		    row.insertCell(1).innerHTML= "<input class=\"Box\" readonly = \"readonly\" size=\"21%\" name=\"listPaymentGRNDtl["+(rowCount)+"].strGRNCode\" id=\"txtGRNCode."+(rowCount)+"\" value='"+strGRNCode+"' >";
		    row.insertCell(2).innerHTML= "<input class=\"Box\" readonly = \"readonly\" size=\"7%\" name=\"listPaymentGRNDtl["+(rowCount)+"].strGRNBIllNo\"  id=\"txtGRNBIllNo."+(rowCount)+"\" value='"+strGRNBIllNo+"'>";		    	    
		    row.insertCell(3).innerHTML= "<input class=\"Box totalGRNAmtCell\" readonly = \"readonly\" size=\"7%\" style=\"text-align: right;width:98%;\" size=\"8%\" name=\"listPaymentGRNDtl["+(rowCount)+"].dblGRNAmt\" id=\"txtGRNAmt."+(rowCount)+"\" value='"+dblGRNAmt.toFixed(maxAmountDecimalPlaceLimit)+"'>";
		    row.insertCell(4).innerHTML= "<input class=\"Box\"  readonly = \"readonly\"  size=\"9%\" name=\"listPaymentGRNDtl["+(rowCount)+"].dteGRNDate\" id=\"txtGRNDate."+(rowCount)+"\" value="+dteGRNDate+">";
		    row.insertCell(5).innerHTML= "<input class=\"Box\"  readonly = \"readonly\"  size=\"9%\" name=\"listPaymentGRNDtl["+(rowCount)+"].dteBillDate\" id=\"txtBillDate."+(rowCount)+"\" value="+dteBillDate+">";		    
		    row.insertCell(6).innerHTML= "<input class=\"Box\" readonly = \"readonly\"  size=\"9%\" name=\"listPaymentGRNDtl["+(rowCount)+"].dteGRNDueDate\" id=\"txtGRNDueDate."+(rowCount)+"\" value="+dteGRNDueDate+">";
		    row.insertCell(7).innerHTML= "<input class=\"text totalGRNPayedCell\"  required = \"required\" style=\"text-align: right;width:98%;\" size=\"9%\" name=\"listPaymentGRNDtl["+(rowCount)+"].dblPayedAmt\" id=\"txtPayedAmt."+(rowCount)+"\" value='0' onblur=\"funUpdateAmount(this);\" >";
		    //   row.insertCell(7).innerHTML= '<input type="button" size=\"2%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		    
		    var grnAmt =$("#txtTotalGRNAmount").val()
		   	totalGrnAmt= parseFloat(grnAmt) + totalGrnAmt;
		     $("#txtTotalGRNAmount").val(totalGrnAmt.toFixed(maxAmountDecimalPlaceLimit) );
		   
	}
	
	
	function funLoadSCTable(strSCCode,strSCBIllNo,dblSCAmt,dteSCDate,dteBillDate,dteSCDueDate,isSelected)
	{
// 		var totalGrnAmt = parseFloat(dblGRNAmt);
		 var table = document.getElementById("tblSCBill");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    if(isSelected=="Tick")
		    	{
		    	 row.insertCell(0).innerHTML= "<input id=\"cbSCSel."+(rowCount)+"\" name=\"listPaymentSCDtl["+(rowCount)+"].strSelected\" type=\"checkbox\" class=\"PropCheckBoxClass\" checked=\"checked\"   value='Tick' onclick=\"funSCBillChkOnClick()\" />";
		    	}else
		    	{
		    		 row.insertCell(0).innerHTML= "<input id=\"cbSCSel."+(rowCount)+"\" name=\"listPaymentSCDtl["+(rowCount)+"].strSelected\" type=\"checkbox\" class=\"PropCheckBoxClass\"   value='Tick' onclick=\"funSCBillChkOnClick()\" />";	
		    	}
		   
		    row.insertCell(1).innerHTML= "<input class=\"Box\" readonly = \"readonly\" size=\"24%\" name=\"listPaymentSCDtl["+(rowCount)+"].strScCode\" id=\"textScCode."+(rowCount)+"\" value='"+strSCCode+"' >";
		    row.insertCell(2).innerHTML= "<input class=\"Box\" readonly = \"readonly\" size=\"8%\" name=\"listPaymentSCDtl["+(rowCount)+"].strScBillNo\"  id=\"txtstrScBillNo."+(rowCount)+"\" value='"+strSCBIllNo+"'>";		    	    
		    row.insertCell(3).innerHTML= "<input class=\"Box totalGRNAmtCell\" readonly = \"readonly\" size=\"8%\" style=\"text-align: right;\" size=\"8%\" name=\"listPaymentSCDtl["+(rowCount)+"].dblScBillAmt\" id=\"txtSCBillAmt."+(rowCount)+"\" value='"+dblSCAmt+"'>";
		    row.insertCell(4).innerHTML= "<input class=\"Box\"  readonly = \"readonly\"  size=\"10%\" name=\"listPaymentSCDtl["+(rowCount)+"].dteSCBillDate\" id=\"dteSCBillDate."+(rowCount)+"\" value="+dteSCDate+">";
		    row.insertCell(5).innerHTML= "<input class=\"Box\"  readonly = \"readonly\"  size=\"10%\" name=\"listPaymentSCDtl["+(rowCount)+"].dteBillDate\" id=\"txtBillDateSC."+(rowCount)+"\" value="+dteBillDate+">";		    
		    row.insertCell(6).innerHTML= "<input class=\"Box\" readonly = \"readonly\"  size=\"10%\" name=\"listPaymentSCDtl["+(rowCount)+"].dteScBillDueDate\" id=\"txtScBillDueDate."+(rowCount)+"\" value="+dteSCDueDate+">";
		    row.insertCell(7).innerHTML= "<input class=\"text totalGRNPayedCell\"  required = \"required\" style=\"text-align: right;\" size=\"8%\" name=\"listPaymentSCDtl["+(rowCount)+"].dblPayedAmt\" id=\"txtPayedAmtSC."+(rowCount)+"\" value='0' onblur=\"funUpdateAmountSC(this);\" >";
		    //   row.insertCell(7).innerHTML= '<input type="button" size=\"2%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		    
// 		    var grnAmt =$("#txtTotalGRNAmount").val()
// 		   	totalGrnAmt= parseFloat(grnAmt) + totalGrnAmt;
// 		     $("#txtTotalGRNAmount").val(totalGrnAmt );
		   
	}
	function funGRNChkOnClick()
	{
		var table = document.getElementById("tblGRN");
	    var rowCount = table.rows.length;  
	    var strGRNCodes="";
	    var grnAmt=0.0;
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbGRNSel."+no).checked==true)
	        	{
	        		if(strGRNCodes.length>0)
	        			{
	        				strGRNCodes=strGRNCodes+","+document.all("txtGRNCode."+no).value;
	        				var tempAmt = document.all("txtGRNAmt."+no).value;
	        				grnAmt=parseFloat(grnAmt)+parseFloat(tempAmt);
	        				document.all("txtPayedAmt."+no).value=tempAmt;
	        			    
	        			}
	        		else
	        			{
	        				strGRNCodes=document.all("txtGRNCode."+no).value;
	        				var tempAmt = document.all("txtGRNAmt."+no).value;
	        				document.all("txtPayedAmt."+no).value=tempAmt;
	        				grnAmt=parseFloat(tempAmt)
	        			}
	        	}
	    }
	    $("#txtAmt").val(grnAmt.toFixed(maxAmountDecimalPlaceLimit));
	    $("#txtAmount").val(grnAmt.toFixed(maxAmountDecimalPlaceLimit));
	 	   
	}
	
	function funSCBillChkOnClick()
	{
		var table = document.getElementById("tblSCBill");
	    var rowCount = table.rows.length;  
	    var strSCCodes="";
	    var scAmt=0.0;
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbSCSel."+no).checked==true)
	        	{
	        		if(strSCCodes.length>0)
	        			{
	        			strSCCodes=strSCCodes+","+document.all("textScCode."+no).value;
	        				var tempAmt = document.all("txtSCBillAmt."+no).value;
	        				scAmt=parseFloat(scAmt)+parseFloat(tempAmt);
	        				document.all("txtPayedAmtSC."+no).value=tempAmt;
	        			    
	        			}
	        		else
	        			{
	        			    strSCCodes=document.all("textScCode."+no).value;
	        				var tempAmt = document.all("txtSCBillAmt."+no).value;
	        				document.all("txtPayedAmtSC."+no).value=tempAmt;
	        				scAmt=parseFloat(tempAmt)
	        			}
	        	}else{
	        		document.all("txtPayedAmtSC."+no).value=0;
	        		
	        	}
	        
	        
	    }
	    $("#txtAmt").val(scAmt.toFixed(maxAmountDecimalPlaceLimit));
	    $("#txtAmount").val(scAmt.toFixed(maxAmountDecimalPlaceLimit));
	 	   
	}
	
	function funUpdateAmount(object)
	{
		var table = document.getElementById("tblGRN");
	    var rowCount = table.rows.length;  
	    var strGRNCodes="";
	    var grnAmt=0.0;
	    var actualpayedAmt=0.0;
	    
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbGRNSel."+no).checked==true)
	        	{
	        		if(strGRNCodes.length>0)
	        			{
	        				strGRNCodes=strGRNCodes+","+document.all("txtGRNCode."+no).value;
	        				var tempAmt = document.all("txtPayedAmt."+no).value;
		        			actualpayedAmt=parseFloat(actualpayedAmt)+parseFloat(tempAmt);
	        			}
	        		else
	        			{
	        				strGRNCodes=document.all("txtGRNCode."+no).value;
	        				var tempAmt = document.all("txtPayedAmt."+no).value;
    						actualpayedAmt=parseFloat(actualpayedAmt)+parseFloat(tempAmt);
	        			}
	        	}
	    }
	    $("#txtAmt").val(actualpayedAmt.toFixed(maxAmountDecimalPlaceLimit));
	    $("#txtAmount").val(actualpayedAmt.toFixed(maxAmountDecimalPlaceLimit));
	    $("#hidGrnYN").val("Y");
	   
	}
	
	function funUpdateAmountSC(object)
	{
		var table = document.getElementById("tblSCBill");
	    var rowCount = table.rows.length;  
	    var strSCCodes="";
	    var scAmt=0.0;
	    var actualpayedAmt=0.0;
	    
	    for(no=0;no<rowCount;no++)
	    {
	        if(document.all("cbSCSel."+no).checked==true)
	        	{
	        		if(strSCCodes.length>0)
	        			{
	        			 strSCCodes=strSCCodes+","+document.all("textScCode."+no).value;
	        				var tempAmt = document.all("txtPayedAmtSC."+no).value;
		        			actualpayedAmt=parseFloat(actualpayedAmt)+parseFloat(tempAmt);
	        			}
	        		else
	        			{
	        			strSCCodes=document.all("textScCode."+no).value;
	        				var tempAmt = document.all("txtPayedAmtSC."+no).value;
    						actualpayedAmt=parseFloat(actualpayedAmt)+parseFloat(tempAmt);
	        			}
	        	}
	    }
	    $("#txtAmt").val(actualpayedAmt.toFixed(maxAmountDecimalPlaceLimit));
	    $("#txtAmount").val(actualpayedAmt.toFixed(maxAmountDecimalPlaceLimit));
	    $("#hidGrnYN").val("Y");
	   
	}
	
	
	
	function funTotalCrGRN()
	{
		var finalCr=0.00;
		var table = document.getElementById("tblGRN");
	    var rowCount = table.rows.length; 
		var no=0;
				$('#tblGRN tr').each(function() {

					if(document.all("cbGRNSel."+no).checked==true)
			    	{
				    var totalCrCell = $(this).find(".totalGRNAmtCell").val();
				    finalCr+=parseFloat(totalCrCell);
			    	}
					no++;
				 });
	    	
		$("#txtAmt").val(finalCr.toFixed(maxAmountDecimalPlaceLimit));
		$("#txtAmount").val(finalCr.toFixed(maxAmountDecimalPlaceLimit));
	}
	
	function funRemoveAccountRows(tblID)
	{
		var table = document.getElementById(tblID);
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	
 function funSetGRNPayAmt()
	{
		var table = document.getElementById("tblGRN");
	    var rowCount = table.rows.length; 
	    var payGRNAmt = $("#txtGRNPayAmount").val();
	    var totalGRNAmt = $("#txtTotalGRNAmount").val();
	    
	    $("#txtAmt").val(payGRNAmt);
		$("#txtAmount").val(payGRNAmt);
	    
	    
	    var restAmt =parseFloat(payGRNAmt);
	      
	    for(no=0;no<rowCount;no++)
	    {
	    	var grnCrCell = document.all("txtGRNAmt."+no).value;
	    	restAmt = parseFloat(restAmt) - parseFloat(grnCrCell);
	    	
	    	if(restAmt>0)
	    		{
	    		$('#tblGRN tr').each(function() {
		    		document.getElementById("txtPayedAmt."+no).value=grnCrCell;
		    		document.getElementById("cbGRNSel."+no).checked=true;
	    		});
	    		}
	    	if(restAmt<0)
	    		{
	    		$('#tblGRN tr').each(function() {
		    		document.getElementById("txtPayedAmt."+no).value=restAmt+parseFloat(grnCrCell);
		    		document.getElementById("cbGRNSel."+no).checked=true;
	    		});
	    		break;
	    		}
	    	
	    	
	    }
		
	}

	function funSetAddSCDtl(){
		 
		 var fromDate=$("#txtDteScFromDate").val();
		 var toDate=$("#txtDteScToDate").val();
		 var debtorCode=$("#txtDebtorCode").val();
		 if(debtorCode==null)
		 {
			 alert("Please Select Creditor");
		 }
		var searchurl=getContextPath()+"/loadUnPayedSCBill.html?fromDate="+fromDate+"&toDate="+toDate+"&debtorCode="+debtorCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.Creditor=='Invalid Code')
			        	{
			        		alert("Invalid creditor Code");
			        		$("#txtDebtorCode").val('');
			        	}
			        	else
			        	{					        	    			        	    
			        	    /* Debtor Details */
			        	    funRemoveAccountRows("tblSCBill");
// 			        	    $("#txtDebtorCode").val(creditorCode);
// 			        	    $("#txtDebtorName").val(response.strCreditorFullName);
			        	    $("#txtGRNPayAmount").val('0.0');
				    		$("#txtTotalGRNAmount").val('0.0');
				    		$.each(response, function(i, item) {
				    		funLoadSCTable(response[i].strGRNCode,response[i].strGRNBIllNo,response[i].dblGRNAmt,response[i].dteGRNDate,response[i].dteBillDate,response[i].dteGRNDueDate,"No");
				    		});
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
	

	function funFillAmount(){
			
			 var hdAmt=$("#txtAmt").val();
			 if(hdAmt!=""){
				
				 $("#txtAmount").val(hdAmt); 
				 
			 }
		 }
	
	function funResetFields()
	{
		location.reload(true); 
	}
	
	function funSetEmployeeMasterData(employeeCode)
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
			        		alert("Invalid Debtor Code");
			        		$("#txtDebtorCode").val('');
			        	}
			        	else
			        	{					        	    			        	    
			        	    /* Employee Details */
			        	   
			        		   
				        	    $("#txtDebtorCode").val(response.strEmployeeCode);
				        	    $("#txtDebtorName").val(response.strEmployeeName);
				        	  
// 				        	    funSetBalanceAmt("Employee");
			        	
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
	

	function funOnChangeCurrency(){
		var cmbCurrency=$("#cmbCurrency").val();
		var currValue=funGetCurrencyCode(cmbCurrency);
		$("#txtDblConversion").val(currValue);
	}	
	
</script>

</head>
<body>

	<div id="formHeading">
		<label>Payment</label>
	</div>

	<br/>
	<br/>

	<s:form name="Payment" method="POST" action="savePayment.html">

		<table class="transTable">
		
			<tr>
				<td>
					<label>Voucher No</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtVouchNo" path="strVouchNo" cssClass="searchTextBox" ondblclick="funHelp('PaymentNo');"/>
				</td>
				
				<td>
					<label>Voucher Date</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtVouchDate" path="dteVouchDate" cssClass="calenderTextBox" />
				</td>
				
				<td>
					<label>Voucher Month</label>
				</td>
				
				<td>				
					<s:select id="cmbMonth" path="intVouchMonth" class="BoxW124px" >
						<option value="1">January</option>
						<option value="2">February</option>
						<option value="3">March</option>
						<option value="4">April</option>
						<option value="5">May</option>
						<option value="6">June</option>
						<option value="7">July</option>
						<option value="8">August</option>
						<option value="9">September</option>
						<option value="10">October</option>
						<option value="11">November</option>
						<option value="12">December</option>
					</s:select>
				
					<!-- <s:select id="cmbMonth" path="intVouchNum" items="${Months}" class="BoxW124px" ></s:select> -->
					
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Bank Code</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtBankCode" path="strBankCode" cssClass="searchTextBox" ondblclick="funHelp('cashBankAccNo');"/>
				</td>				
				<td><label id="lblBankDesc"></label></td>
				
				<td>
					<label id="lblBankBalAmt"  style="color:blue; font:bold; font-size:115%;" ></label>
					
				</td>
				<td>
					<label>Amt</label> &nbsp;&nbsp;&nbsp;&nbsp;
					<s:input  type="number" step="0.0001" id="txtAmt" path="dblAmt" class="decimal-places numberField" onChange="funFillAmount()" value="0.00"/>
				</td>
				
				<td>
					<s:input colspan="3" type="text" id="txtCrDr" value="Cr" path="strCrDr" cssClass="longTextBox" readonly="true"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Type</label>
				</td>
				<td>
					<s:select id="cmbType" path="strType" class="BoxW124px" onchange="funSetTypeLabel()">
						  <option value="Cash">Cash</option>
						  <option value="Cheque">Cheque</option>
						  <option value="Credit Card">Credit Card</option>
						  <option value="NEFT">NEFT</option>
					</s:select>
				</td>
				<td><label id="lblTypeName"></label></td>
				
				<td>
					<s:input colspan="3" type="text" id="txtChequeNo" path="strChequeNo" cssClass="longTextBox" />
				</td>
				
				<td><label id="lblChequeDate">Cheque Date</label></td>
				<td>
					<s:input colspan="3" type="text" id="txtChequeDate" path="dteChequeDate" cssClass="calenderTextBox" />
				</td>
			</tr>
			
			<tr>
				<td>
					<label>DrawnOn</label>
				</td>
				<td>
					<s:input  type="text" id="txtDrawnOn" path="strDrawnOn" cssClass="searchTextBox" ondblclick="funHelp('bankCode');"/>
				</td>
				<td colspan="2"><label id="lblDrawnOnDesc"></label></td>
				<td><label>Branch</label></td>
				<td>
					<s:input  type="text" id="txtBranch" path="strBranch" cssClass="longTextBox" />
				</td>
			</tr>
			
			<tr>
			<td><lable>Link up</lable> </td>
			<td>	<s:select id="cmbBillType" path="" cssClass="BoxW124px" onchange="funBillType()">
				    		<option value="GRN">GRN</option>
				    		<option value="SC Bill">SC Bill</option>
				    	
				    	</s:select>
			</td>
				<td><label>Narration</label></td>
				<td>
					<s:textarea  id="txtNarration" path="strNarration"/>
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>	
				<td width="10%"><label>Currency</label></td>
				<td>
				<s:select id="cmbCurrency" path="strCurrency" items="${currencyList}" cssClass="BoxW124px" onchange="funOnChangeCurrency()"></s:select></td>
				<td><s:input id="txtDblConversion" value ="1" path="dblConversion" type="text" class="decimal-places numberField" style="width:40px"></s:input><td>
				<td colspan="2"></td>
		</tr>
		</table>

		<table class="transTable">
		</table>
<br>
		<br>
	<div >
	
	<ul class="tabs" >
									<li id="Details" class="active" data-state="tab1"
										style="width: 100px; padding-left: 55px">Details</li>
									<li id="GRN" data-state="tab2" style="width: 100px; padding-left: 55px">GRN</li>
									<li id="SC Bill" data-state="tab3" style="width: 100px; padding-left: 55px">SC Bill</li>
								</ul>
		<div id="tab1" class="tab_content" style="height: 470px">
		
		<table class="transTable">
		

			<tr>
				<td width="10%"><label>Account Code</label></td>
				<td width="20%"><input id="txtAccCode" name="txtAccCode" ondblclick="funHelp('accountCode')" class="searchTextBox" /></td>
				
<!-- 				<td width="10%">Description</td> -->
				<td width="65%" colspan="2"><input id="txtDescription" name="txtDescription" Class="longTextBox"  /></td>
				<td colspan="2"></td>
					
			</tr>
			<tr>
			<td width="10%"><label id="lblCrdDebCode">Creditor Code</label></td>
				
				<td width="20%" ><input id="txtDebtorCode" name="txtDebtorCode" ondblclick="funHelp1()" class="searchTextBox" /></td>
				<td colspan="2"><input id="txtDebtorName" name="txtDebtorName" readonly="readonly" Class="longTextBox" ></input>
				</td>
				<td colspan="2">
				<label id="lblBalanceAmt"  style="color:blue; font:bold;font-size:115%;"  ></label></td>
			
			</tr>
			
			<tr>
				<td><label>Dr/Cr</label>
				<td width="5%">
					 <select id="cmbDrCr"  class="BoxW124px">
						  <option value="Dr">Dr</option>
						  <option value="Cr">Cr</option>
					</select>
				</td>
				
				<td width="10%"><label>Amount</label></td>
				<td><input type="text" id="txtAmount" value="0.00" class="decimal-places numberField"/></td>
				
				<td width="10%"><label>Dimension</label></td>
				<td width="5%" >
					 <select id="cmbDimesion" class="BoxW124px">
						  <option value="No">No</option>
						  <option value="Yes">Yes</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td colspan="6">
					<input type="Button" value="Add" onclick="return funGetDetailsRow()" class="smallButton" />
				</td>
			</tr>
		</table>
		
		
		<div class="dynamicTableContainer" style="height: 300px;">
		<table
			style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
			<tr bgcolor="#72BEFC">
				<td style="width:11%;">Account Code</td>
				<td style="width:9%;">Creditor Code</td>
				<td style="width:16%;">Description</td>
				<td style="width:3%;">D/C</td>
				<td style="width:5.3%;"  align ="right">Debit Amt</td>
				<td style="width:6%;" align ="right">Credit Amt</td>
				<td style="width:5%;">Dimension</td>
<!-- 				<td style="width:8%;">One Line Account</td> -->
<!-- 				<td style="width:13%;">Narration</td> -->
<!-- 				<td style="width:5%;">DebtorYN</td> -->
				<td style="width:1%;">Delete</td>
			</tr>
		</table>

		
		<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
			<table id="tblReceiptDetails"
				style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
				class="transTablex col8-center">
			<tbody>
						<col style="width=15%">
						<col style="width:16%">
						<col style="width:28%">
						<col style="width:5%">
						<col style="width:10%">
						<col style="width:10%">
						<col style="width:10%">
						<col style="width:2%">
<%-- 					<col style="width:2%;;"> --%>
				</tbody>
			</table>
		</div>
	</div>

	<div >
		<table class="transTable">
			<tr>
				<td width="2%"><label>Difference</label></td>
				<td><label id ="lblDiffAmt">0.00</label></td>
				<td><label>Total</label></td>
				<td><label id ="lblDebitAmt">0.00</label></td>
				<td><label id ="lblCreditAmt">0.00</label></td>
			</tr>
			
			<tr>
				<td>
					<s:input type="hidden" id="txtTotalAmt" path="dblAmt"  readonly="true" cssClass="decimal-places-amt numberField"/>
				</td>
			</tr>
		</table>
		
		</div>						
		
	</div>

		<div id="tab2" class="tab_content" style="height: 470px">
		<table class="transTable">
		<tr>
		<td><label>Total GRN Amount</label></td>
		<td><input type="text" id="txtTotalGRNAmount" readonly="readonly" value="0.00" class="decimal-places numberField"/></td>
		
		
		<td><label>Pay Amount</label></td>
		<td><input type="text" id="txtGRNPayAmount" value="0.00" class="decimal-places numberField"/></td>
		
		
		<td><input type="Button" value="Add" onclick="funSetGRNPayAmt()" class="smallButton" /></td>
		
		</tr>
		
		
			<tr><td colspan="5">
		<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 350px; overflow-x: hidden; overflow-y: scroll;">
		
									<table id="" class="masterTable"
										style="width: 100%; border-collapse: separate;">
										<thead>
											<tr bgcolor="#72BEFC">
												<td width="5%"><input type="checkbox" checked="checked" 
												id="chkGRNALL"/>Select</td>
												<td width="18%">GRN Code</td>
												<td width="8%">Bill No</td>
												<td align ="right" width="8%">Amount</td>
												<td width="10%">GRN Date</td>
												<td width="10%">Bill Date</td>
												<td width="10%">Due Date</td>
												<td align ="right" width="10%">Paying Amt</td>
		
											</tr>
										</thead>
									</table>
									<table id="tblGRN" class="masterTable"
										style="width: 100%; border-collapse: separate;">
		
										<tr bgcolor="#72BEFC">
											
		
										</tr>
									</table>
								</div></td>
								</tr>
			</table>					
		
		</div>


		<div id="tab3" class="tab_content" style="height: 470px">
		<table class="transTable">
		<tr>
		<td><label>from Date</label></td>
		<td><s:input colspan="3" path="" type="text" id="txtDteScFromDate"  cssClass="calenderTextBox" /></td>
		
		
		<td><label>ToDate</label></td>
		<td><s:input colspan="3" path="" type="text" id="txtDteScToDate"  cssClass="calenderTextBox" /></td>
		
		
		<td><input type="Button" value="Show" onclick="funSetAddSCDtl()" class="smallButton" /></td>
		
		</tr>
		
		
			<tr><td colspan="5">
		<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 350px; overflow-x: hidden; overflow-y: scroll;">
		
									<table id="" class="masterTable"
										style="width: 100%; border-collapse: separate;">
										<thead>
											<tr bgcolor="#72BEFC">
												<td width="5%"><input type="checkbox" checked="checked" 
												id="chkGRNALL"/>Select</td>
												<td width="18%">SC Code</td>
												<td width="8%">Bill No</td>
												<td width="8%">Amount</td>
												<td width="10%">SC Date</td>
												<td width="10%">Bill Date</td>
												<td width="10%">Due Date</td>
												<td width="10%">Paying Amt</td>
		
											</tr>
										</thead>
									</table>
									<table id="tblSCBill" class="masterTable"
										style="width: 100%; border-collapse: separate;">
		
										<tr bgcolor="#72BEFC">
											
		
										</tr>
									</table>
								</div></td>
								</tr>
			</table>					
		
		</div>
	
	</div>
<input type="hidden" id="hidGrnYN" value="N"></input>
<input type="hidden" id="hidSundryType" ></input>
<s:input type="hidden" id="hidaccountCode" path="accountCode"></s:input>
<s:input type="hidden" id="hidcreditorCode" path="creditorCode" ></s:input>
<s:input type="hidden" id="hidclosingAmt" path="closingAmt" ></s:input>
	<br />
	<br />
	<p align="center">
		<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateHeaderFields()" />
		<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
	</p>
<br />
	<br /><br />
	<br />
	</s:form>
</body>

</html>
