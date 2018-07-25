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
	var debtorYN='N';
	var debtorName='';
	var oneLineAccDesc='';

	$(function() 
	{
		$("#txtVouchDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtVouchDate").datepicker('setDate', 'today');
		
		$("#txtCreatedDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtCreatedDate").datepicker('setDate', 'today');
		
		
		$("#txtEditedDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtEditedDate").datepicker('setDate', 'today');
		   
	//	$("#txtDebtorCode").prop('disabled', true);
		
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
				%> alert("Data Save successfully\n\n"+message);<%
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
				window.open(getContextPath()+"/rptJVReportFromTrans.html?docCode="+code,'_blank');
			}
					
			
		<%}%>
		
	});
	
	

	function funSetData(code){

		switch(fieldName){

			case 'JVNo' : 
				funSetVouchNo(code);
				break;
				
			case 'accountCode' : 
				funSetAccountDetails(code);
				break;
				
			case 'oneLineAcc' : 
				funSetOneLineAccDetails(code);
				break;
			
			case 'debtorCode' : 
				funSetDebtorMasterData(code)
				break;
				
			case 'creditorCode' : 
				funSetcreditorMasterData(code)
				break;
				
			case 'JVNoslip' : 
				funSetVouchNoSlip(code);
				break;
				
			case 'employeeCode' : 
				funSetEmployeeMasterData(code)
				break;		
		}
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
			        	  
			        	    $("#txtDebtorCode").val(creditorCode);
			        	    $("#lblCrdDebName").text(response.strCreditorFullName);
			        	  
				    		
				    		
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

// Function to set JV Data from help	
	function funSetVouchNo(code){
		var currencyCode=$("#cmbCurrency").val();
// 		var currValue=funGetCurrencyCode(currencyCode);
// 		if(currencyCode==null)
// 		{
// 			currencyCode="";
// 		}
		var currValue=$("#txtDblConversion").val();
   		if(currValue==null ||currValue==''||currValue==0)
   		{
   		  currValue=1;
   		}
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadJV.html?docCode=" + code+"&strCurrency="+currencyCode,
			dataType : "json",
			async: false,
			success : function(response){

				if(response.strVouchNo!="Invalid")
		    	{
		    		funFillHdData(response);
		    	}
		    	else
			    {
			    	alert("Invalid JV No");
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
	
	
	function funFillHdData(response)
	{
		$("#txtVouchNo").val(response.strVouchNo);
		$("#txtVouchDate").val(response.dteVouchDate);
		$("#txtNarration").val(response.strNarration);
		
		$("#txtUserCreated").val(response.strUserCreated);
		$("#txtUserCreated").prop('disabled', true);
		
		$("#txtUserEdited").val(response.strUserEdited);
		$("#txtUserEdited").prop('disabled', true);
		
		$("#txtCreatedDate").val(response.dteDateCreated);
		$("#txtCreatedDate").prop('disabled', true);
		
		$("#txtEditedDate").val(response.dteDateEdited);
		$("#txtEditedDate").prop('disabled', true);
		$("#cmbCurrency").val(response.strCurrency);
		$("#txtDblConversion").val(response.dblConversion);
		$("#lblInitialtedby").text(response.strSource);
		  
		funFillDtlGrid(response.listJVDtlBean);
	}
	
	
	function funFillDtlGrid(resListJVDtlBean)
	{
		funRemoveProductRows();
		$.each(resListJVDtlBean, function(i,item)
        {
			debtorYN=resListJVDtlBean[i].strDebtorYN;
			debtorName=resListJVDtlBean[i].strDebtorName;
			
			funAddDetailsRow(resListJVDtlBean[i].strAccountCode,resListJVDtlBean[i].strDebtorCode
				,resListJVDtlBean[i].strDescription,resListJVDtlBean[i].strDC,''
				,resListJVDtlBean[i].strOneLineAcc,resListJVDtlBean[i].strNarration
				,resListJVDtlBean[i].dblDebitAmt,resListJVDtlBean[i].dblCreditAmt,resListJVDtlBean[i].strDebtorName);
        });
	}
	
	
// Function to set account details from help	
	function funSetAccountDetails(code){
		
		 $.ajax({
		        type: "GET",
		        url: getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
		        dataType: "json",
		        success: function(response)
		        {
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
			        		$("#txtDebtorCode").focus();
	 		        		$("#txtDebtorCode").prop('disabled', false);
			        	}
			        	else{
			        		$("#txtDebtorCode").val('');
 		        			$("#txtDebtorCode").prop('disabled', true);
 		        			$("#txtAmount").focus();
			        	}
			        	
			        	funDifferenceAmt();
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
	
	
	
// Function to set account details from help
	function funSetOneLineAccDetails(code){
	
		var searchurl=getContextPath()+ "/loadAccForNonDebtor.html?acCode=" + code
	
		$.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtOneLineAcc").val('');
	        	}
				if($("#txtAccCode").val()==response.strAccountCode)
				{
					alert("This account in not valid!!!");
				}
	        	else
	        	{
	        		$("#txtOneLineAcc").val(response.strAccountCode);
	        		oneLineAccDesc=response.strAccountName;
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
	


// Function to set debtor details from help	
	function funSetMemberDetails(code){
		//alert(getContextPath()+ "/loadMemberDetails.html?memberCode=" + code);
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadMemberDetails.html?debtorCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Debtor Code");
	        		$("#txtDebtorCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDebtorCode").val(response.strDebtorCode);
	        		debtorName=response.strDebtorName;
	        		$("#cmbDrCr").focus();
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


// Get Detail Info From detail fields and pass them to function to add into detail grid
	
function funGetDetailsRow() 
	{
		var accountCode =$("#txtAccCode").val().trim();
		var transAmt=parseFloat($("#txtAmount").val());
		
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
		
	    var debtorCode=$("#txtDebtorCode").val();
	    var description=$("#txtDescription").val();
	    var transType=$("#cmbDrCr").val();
	    var dimension=$("#cmbDimesion").val();
	    var oneLineAcc=$("#txtOneLineAcc").val();
	    var narration=$("#txtNarrationDtl").val();
	    var debtorName=$("#lblCrdDebName").text();
	    
	    
	    var debitAmt=0;
	    var creditAmt=0;
	    
	    if(transType=='Dr')
	    {
	    	debitAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);	
	    }
	    else
	    {
	    	creditAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);
	    }
	    
	    funAddDetailsRow(accountCode,debtorCode,description,transType,dimension,oneLineAcc,narration,debitAmt,creditAmt,debtorName);
	    
	    if(oneLineAcc.trim()!='')
	    {
	    	var transTypeForOneLineAcc=transType;
	    	if(transType=='Dr')
		    {
	    		transTypeForOneLineAcc='Cr';
		    }
		    else
		    {
		    	transTypeForOneLineAcc='Dr';
		    }
	    	funAddDetailsRow(oneLineAcc,'',oneLineAccDesc,transTypeForOneLineAcc,dimension,'',narration,creditAmt,debitAmt,debtorName);
	    	 oneLineAccDesc='';
	    }
	}

	
	
// Function to add detail grid rows	
	function funAddDetailsRow(accountCode,debtorCode,description,transType,dimension,oneLineAcc,narration,debitAmt,creditAmt,debtorName) 
	{
	    var table = document.getElementById("tblJVDetails");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    debitAmt=parseFloat(debitAmt).toFixed(maxAmountDecimalPlaceLimit);
	    creditAmt=parseFloat(creditAmt).toFixed(maxAmountDecimalPlaceLimit);
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" name=\"listJVDtlBean["+(rowCount)+"].strAccountCode\" id=\"strAccountCode."+(rowCount)+"\" value='"+accountCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" name=\"listJVDtlBean["+(rowCount)+"].strDebtorCode\" id=\"strDebtorCode."+(rowCount)+"\" value='"+debtorCode+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listJVDtlBean["+(rowCount)+"].strDescription\" id=\"strDescription."+(rowCount)+"\" value='"+description+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listJVDtlBean["+(rowCount)+"].strDC\" id=\"strDC."+(rowCount)+"\"style=\"text-align: center;\" value='"+transType+"' />";
	    row.insertCell(4).innerHTML= "<input type=\"text\"  size=\"7%\" name=\"listJVDtlBean["+(rowCount)+"].dblDebitAmt\" style=\"text-align: right;\"  id=\"dblDebitAmt."+(rowCount)+"\" value='"+debitAmt+"'  onblur=\"Javacsript:funUpdateDebitAmount(this)\" />";
	    row.insertCell(5).innerHTML= "<input type=\"text\"  size=\"7%\" name=\"listJVDtlBean["+(rowCount)+"].dblCreditAmt\" style=\"text-align: right;\"  id=\"dblCreditAmt."+(rowCount)+"\" value='"+creditAmt+"' onblur=\"Javacsript:funUpdateCreditAmount(this)\" />";
	    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listJVDtlBean["+(rowCount)+"].strDimension\" id=\"strDimension."+(rowCount)+"\" value='"+dimension+"'/>";
	   
	    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\" name=\"listJVDtlBean["+(rowCount)+"].strNarration\" id=\"strNarration."+(rowCount)+"\" value='"+narration+"'/>";
	    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"2%\" name=\"listJVDtlBean["+(rowCount)+"].strDebtorYN\" id=\"strDebtorYN."+(rowCount)+"\" value='"+debtorYN+"'/>";
	    row.insertCell(9).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"2%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>";
	    row.insertCell(10).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" size=\"0%\" name=\"listJVDtlBean["+(rowCount)+"].strDebtorName\" id=\"strDebtorName."+(rowCount)+"\" value='"+debtorName+"' />";
	    row.insertCell(11).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"Box\" size=\"0%\" name=\"listJVDtlBean["+(rowCount)+"].strOneLineAcc\" id=\"strOneLineAcc."+(rowCount)+"\" value='"+oneLineAcc+"'/>";
	    debtorYN='N';
	    debtorName='';
	   
//	    $("#txtDebtorCode").prop('disabled', true);
	    funCalculateTotalAmt();
	    funResetDetailFields();
	    
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
	function funDifferenceAmt()
	{
		
		 var hdAmt=$("#lblDiffAmt").text();
		 if(hdAmt!=""){
			 if(hdAmt!=0)
			 {
			 $("#txtAmount").val(hdAmt); 
			 } if(hdAmt<0)
			 {
				 hdAmt=hdAmt*(-1);
				 $("#txtAmount").val(hdAmt);
			 }else{
				 
				 $("#txtAmount").val(hdAmt);
			 }
		 }
	}

//Delete a All record from a grid
	function funRemoveProductRows()
	{
		var table = document.getElementById("tblJVDetails");
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
	    var table = document.getElementById("tblJVDetails");
	    table.deleteRow(index);
	    funCalculateTotalAmt();
	    
	}

	
//Calculating total amount
	function funCalculateTotalAmt()
	{
		var totalAmt=0.00;
		var totalDebitAmt=0.00;
		var totalCreditAmt=0.00;

			var table = document.getElementById("tblJVDetails");
			var rowCount = table.rows.length;
			
			for(var i=0;i<rowCount;i++)
			{
				totalDebitAmt=parseFloat(document.getElementById("dblDebitAmt."+i).value)+totalDebitAmt;
				totalCreditAmt=parseFloat(document.getElementById("dblCreditAmt."+i).value)+totalCreditAmt;
			}


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
	    $("#txtOneLineAcc").val('');
	    $("#txtNarrationDtl").val('');
	}
	
	
// Reset Header Fields
	function funResetHeaderFields()
	{
		$("#txtVouchNo").val('');
		$("#txtVouchDate").val('');
		$("#txtNarration").val('');
		$("#txtUserCreated").val('');
		$("#txtUserEdited").val('');
		$("#txtCreatedDate").val('');
		$("#txtEditedDate").val('');
	}


// Function to Validate Header Fields
	function funValidateHeaderFields(object)
	{
		if($("#txtVouchDate").val()=='')
		{
			alert('Please Select Vouch Date!!!');
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
			        	    $("#lblCrdDebName").text(response.strDebtorFullName);
			        	
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
				        	    $("#lblCrdDebName").text(response.strEmployeeName);
				        	  
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
		<label>JV</label>
	</div>

<br/>
<br/>

	<s:form name="JV" method="POST" action="saveJV.html">

		<table class="transTable">
			<tr>
				<td>
					<label>VouchNo</label>
				</td>
				<td >
					<s:input  type="text" id="txtVouchNo" path="strVouchNo" cssClass="searchTextBox" ondblclick="funHelp('JVNo');"/>
				</td>
				
				<td>
					<label>VouchDate</label>
				</td>
				<td>
					<s:input  type="text" id="txtVouchDate" path="dteVouchDate" cssClass="calenderTextBox" />
				</td>
				<td>
					<label>Initiated By </label>
				</td>
				<td colspan="4">
					<label id="lblInitialtedby" >USER</label>
				</td>
			</tr>
			<tr>
				<td><label>Currency</label></td>
				<td>
				<s:select id="cmbCurrency" path="strCurrency" items="${currencyList}" cssClass="BoxW124px" onchange="funOnChangeCurrency()"></s:select>
				<s:input id="txtDblConversion" value ="1" path="dblConversion" type="text" class="decimal-places numberField" style="width:40px"></s:input></td>
				<td>
					<label>Narration</label>
				</td>
				<td colspan="5" >
					<s:input type="text" id="txtNarration" path="strNarration" cssClass="remarkTextBox" />
				</td>
			</tr>
			
			<tr>
				<td><label>User Created</label></td>
				<td>
					<s:input type="text" id="txtUserCreated" path="strUserCreated" cssClass="longTextBox" />
				</td>
				
				<td><label>User Edited</label></td>
				<td>
					<s:input  type="text" id="txtUserEdited" path="strUserEdited" cssClass="longTextBox" />
				</td>
				
				<td><label>Created Date</label></td>
				<td>
					<s:input  type="text" id="txtCreatedDate" path="dteDateCreated" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" />
				</td>
				
				<td><label>Edited Date</label></td>
				<td>
					<s:input  type="text" id="txtEditedDate" path="dteDateEdited" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" />
				</td>
			</tr>
			<tr></tr>
			<tr></tr>
		</table>

		<br>
		<br>
		
		<table class="transTable">
		</table>

	<div >
		<table class="transTable">
				
			<tr>
				<td width="10%"><label>Details</label></td>
			</tr>
				
			<tr>
				<td width="10%"><label>Account Code</label></td>
				<td width="20%"><input id="txtAccCode" name="txtAccCode" ondblclick="funHelp('accountCode')" class="searchTextBox" /></td>
				
				<td width="10%">Description</td>
				<td width="30%" colspan="1"><input id="txtDescription" name="txtDescription" style="width: 100%;"  class="remarkTextBox" /></td>
				<td></td>
				<td></td>			
			</tr>
			<tr>
				<td width="10%"><label id="lblCrdDebCode">Debtor Code</label></td>
				<td width="30%"><input id="txtDebtorCode"  name="txtDebtorCode" ondblclick="funHelp1()" class="searchTextBox" /></td>
				<td><label id="lblCrdDebName"></label></td>
				<td></td>
				<td></td>
				<td></td>	
				
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
				<td><input type="text" id="txtAmount" value="" class="decimal-places numberField"/></td>
				
				<td width="10%"><label>Dimension</label></td>
				<td width="5%">
					 <select id="cmbDimesion" class="BoxW124px">
						  <option value=""></option>
						  <option value=""></option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label>One Line Account</label></td>
				<td><input id="txtOneLineAcc" name="txtOneLineAcc" ondblclick="funHelp('oneLineAcc')" class="searchTextBox" /></td>
			
				<td><label>Narration</label></td>
				<td>
					<input type="text" id="txtNarrationDtl" class="remarkTextBox" />
				</td>
				
				<td colspan="3">
					<input type="Button" value="Add" onclick="return funGetDetailsRow()" class="smallButton" />
				</td>
			</tr>
		</table>
	</div>


	
<div class="dynamicTableContainer" style="height: 300px;width: 99.80%;">
		<table
			style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
			<tr bgcolor="#72BEFC">
				<td style="width:8.9%;">Account Code</td>
				<td style="width:12%;">Debtor Code</td>
				<td style="width:22%;">Description</td>
				<td style="width:4%;">D/C</td>
				<td style="width:8%;">Debit Amt</td>
				<td style="width:8%;">Credit Amt</td>
				<td style="width:8%;">Dimension</td>
<!-- 				<td style="width:8%;">One Line Account</td> -->
				<td style="width:13%;">Narration</td>
				<td style="width:3%;">Debtor YN</td>
				<td style="width:2%;">Delete</td>
				
			</tr>
		</table>
		
		<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
			<table id="tblJVDetails"
				style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
				class="transTablex col8-center">
				<tbody>
					<col style="width:9%">
					<col style="width:12%">
					<col style="width:22%">
					<col style="width:4%">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:8%">
					<col style="width:13%">
					<col style="width:3%">
					<col style="width:2%">
					<col style="width:0%">
					<col style="width:0%;">
				</tbody>
			</table>
		</div>
	</div>
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
<input type="hidden" id="hidSundryType" ></input>
	<p align="center">
		<input type="submit" value="Submit" onclick="return funValidateHeaderFields(this)" tabindex="3" class="form_button" />
		<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
	</p>
	
<br />
	<br /><br />
	<br />
	</s:form>
</body>
</html>
