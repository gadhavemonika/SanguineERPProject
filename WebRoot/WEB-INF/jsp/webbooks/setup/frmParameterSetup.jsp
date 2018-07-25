<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<style type="text/css">
		
		
	.txtTextArea
	{
		width: 350px; 
		height:70px;
		resize: none;		
	}		
	
	#narrationBuilderTbl th 
	{
	    background: #f18d05 -moz-linear-gradient(center top , #0f5495, #73addd) repeat scroll 0 0;
		border: 1px solid #0f5495;
	    border-radius: 0;
	    box-shadow: 0 1px 0 rgba(90, 52, 139, 0.16), 0 1px 0 #0f5495 inset;
	    color: #fff;
	    transition: all 0.9s ease 0s;
	}
	
</style>

<script type="text/javascript">
	var fieldName;
	var accountType;		
		
	
	function setAccountCodeAndName(response)
	{
		switch(accountType)
		{		
			case "debtorControlAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtDebtorControlACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDebtorControlACCode").val(response.strAccountCode);
		        	$("#txtDebtorControlACName").val(response.strAccountName);
		        	$("#txtDebtorControlACName").focus();		
	        	}
				 break;				 
			case "debtorBillableAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtDebtorBillableACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDebtorBillableACCode").val(response.strAccountCode);
		        	$("#txtDebtorBillableACName").val(response.strAccountName);
		        	$("#txtDebtorBillableACName").focus();		
	        	}
				 break;
			case "debtorSuspenseAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtDebtorSuspenseACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDebtorSuspenseACCode").val(response.strAccountCode);
		        	$("#txtDebtorSuspenseACName").val(response.strAccountName);
		        	$("#txtDebtorSuspenseACName").focus();		
	        	}
				 break;
			case "debtorSuspenseCode": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtDebtorsSuspenseCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDebtorsSuspenseCode").val(response.strAccountCode);
		        	$("#txtDebtorsSuspenseName").val(response.strAccountName);
		        	$("#txtDebtorsSuspenseName").focus();		
	        	}
				 break;				 
			case "roundingOffAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtRoundingOffACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtRoundingOffACCode").val(response.strAccountCode);
		        	$("#txtRoundingOffACName").val(response.strAccountName);
		        	$("#txtRoundingOffACName").focus();		
	        	}
				 break;
			case "reservationAdvPartyAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtReservationAdvPartyACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtReservationAdvPartyACCode").val(response.strAccountCode);
		        	$("#txtReservationAdvPartyACName").val(response.strAccountName);
		        	$("#txtReservationAdvPartyACName").focus();		
	        	}
				 break;
			case "roomAdvAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtRoomAdvACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtRoomAdvACCode").val(response.strAccountCode);
		        	$("#txtRoomAdvACName").val(response.strAccountName);
		        	$("#txtRoomAdvACName").focus();		
	        	}
				 break;
			case "invoicerAdv": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtInvoicerAdvCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtInvoicerAdvCode").val(response.strAccountCode);
		        	$("#txtInvoicerAdvName").val(response.strAccountName);
		        	$("#txtInvoicerAdvName").focus();		
	        	}
				 break;
			case "onlineNEFTAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtOnlineNEFTACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtOnlineNEFTACCode").val(response.strAccountCode);
		        	$("#txtOnlineNEFTACName").val(response.strAccountName);
		        	$("#txtOnlineNEFTACName").focus();		
	        	}
				 break;
			case "roomAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtRoomACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtRoomACCode").val(response.strAccountCode);
		        	$("#txtRoomACName").val(response.strAccountName);
		        	$("#txtRoomACName").focus();		
	        	}
				 break;
			case "postDatedAdvAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtPostDatedChequeACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtPostDatedChequeACCode").val(response.strAccountCode);
		        	$("#txtPostDatedChequeACName").val(response.strAccountName);
		        	$("#txtPostDatedChequeACName").focus();		
	        	}
				 break;
			case "ecsBank": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtECSBankCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtECSBankCode").val(response.strAccountCode);
		        	$("#txtECSBankName").val(response.strAccountName);
		        	$("#txtECSBankName").focus();		
	        	}
				 break;	 
			case "defaultSanction": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtDefaultSanctionCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDefaultSanctionCode").val(response.strAccountCode);
		        	$("#txtDefaultSanctionName").val(response.strAccountName);
		        	$("#txtDefaultSanctionName").focus();		
	        	}
				 break;	
			case "debtorLedgerAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtDebtorLedgerACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDebtorLedgerACCode").val(response.strAccountCode);
		        	$("#txtDebtorLedgerACName").val(response.strAccountName);
		        	$("#txtDebtorLedgerACName").focus();		
	        	}
				 break;	 
			case "advAC": 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtAdvACCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtAdvACCode").val(response.strAccountCode);
		        	$("#txtAdvACName").val(response.strAccountName);
		        	$("#txtAdvACName").focus();		
	        	}
				 break;	 
		}
	}
	
	function funLoadAccountMaster(accountCode)
	{	    
		var searchurl=getContextPath()+"/loadAccountMasterData.html?accountCode="+accountCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	setAccountCodeAndName(response);
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
	
	function funSetData(code)
	{
		funLoadAccountMaster(code);
	}
	
	/* to set account type */
	function funSetACType(accountName)
	{
		accountType=accountName;
		funHelp("accountCode");
	}
	
	/* set date values */
	function funSetDate(dteRevenuePostedUpToDate,responseValue)
	{
		var id=dteRevenuePostedUpToDate;
		var value=responseValue;
		var date=responseValue.split(" ")[0];
		
		var y=date.split("-")[0];
		var m=date.split("-")[1];
		var d=date.split("-")[2];
		
		$(id).val(d+"-"+m+"-"+y);
		
	}
	
	/* To check and Set CheckBox Value To Y/N */
	function funSetCheckStatusAndValue(currentCheckBox,flag)
	{			    
		var value=flag;
	    if(value.toUpperCase()=="Y")
	    {  
	    	$(currentCheckBox).prop("checked",true);
	    	$(currentCheckBox).val("Y");
        }
	    else	    
        {         
	    	$(currentCheckBox).prop("checked",false);
	    	$(currentCheckBox).val("N");
        }	    		    	   
	}	
	
	function funLoadAndFillFormData()
	{	  
		var searchurl=getContextPath()+"/loadParameterSetupData.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {			        	
						$("#txtAccountNarrationJV").val(response.strAcctNarrJv);
						$("#txtAccountNarrationJVPay").val(response.strAcctNarrPay);
						$("#txtAccountNarrationJVReceipt").val(response.strAcctNarrRec);
					/* 	$("#").val(response.strAdAgeLimit);
						$("#").val(response.strAdultAgeLimit);
						$("#").val(response.strAdultGuest);
						$("#").val(response.strAdultMember); */
						$("#txtAdvACCode").val(response.strAdvanceACCode);
						$("#txtAdvACName").val(response.strAdvanceAcct);
						/* $("#").val(response.strAIMS);	 */																	
						funSetCheckStatusAndValue(chkAmadeusInterfaceYN,response.strAmadeusInterface);
						/* $("#").val(response.strApDebtorReceiptEntry);
						$("#").val(response.strAPECSBankCode);
						$("#").val(response.strAPJVEntry);
						$("#").val(response.strAPPaymentEntry);
						$("#").val(response.strAPReceiptEntry);
						$("#").val(response.strAutoGenCode); */
						$("#txtDebtorBillableACCode").val(response.strBillableCode);
						$("#txtDebtorBillableACName").val(response.strBillableName);
						
						$("#txtBillPrefix").val(response.strBillPrefix);
						/* $("#").val(response.strCashFlowCode);
						$("#").val(response.strCategoryCode);
						$("#").val(response.strChildGuest);
						$("#").val(response.strChildMember);
						$("#").val(response.strclientid); */
						$("#txtDebtorControlACCode").val(response.strControlCode);																
						$("#txtDebtorControlACName").val(response.strControlName);
			
						funSetCheckStatusAndValue(chkCreditLimitCtlYN,response.strCreditLimit);
						/* $("#").val(response.strCreditorControlAccount);
						$("#").val(response.strCreditorLedgerAccount);
						$("#").val(response.strCRM); */
						$("#txtDebtorCurrencyAmtUnit").val(response.strCurrencyCode);
						$("#txtDebtorCurrencyAmt").val(response.strCurrencyDesc);
					/* 	$("#").val(response.strCustImgPath);
						$("#").val(response.strDatabase);
						$("#").val(responseresponse.strDbServer()); */
						$("#txtRoomACCode").val(response.strDbtRoomACCode);
						$("#txtRoomACName").val(response.strDbtRoomACName);
						$("#txtRoomAdvACCode").val(response.strDbtRoomAdvCode);
						$("#txtRoomAdvACName").val(response.strDbtRoomAdvName);
						
						$("#txtDebtorSuspenseACCode").val(response.strDbtrSuspAcctCode);					
						$("#txtDebtorSuspenseACName").val(response.strDbtrSuspAcctName);
						
						$("#txtDebtorLedgerACCode").val(response.strDebtorLedgerACCode);
						$("#txtDebtorLedgerACName").val(response.strDebtorLedgerACName);
						/* $("#").val(response.strDebtorAck); */
						$("#txtDebtorNarrationJV").val(response.strDebtorNarrJv);
						/* $("#").val(response.strDebtorNarrPay);
						$("#").val(response.strDebtorNarrRec);
						$("#").val(response.strDebtorPreProfiling); */
						$("#txtECSBankCode").val(response.strECSBankcode);
						$("#txtECSBankName").val(response.strECSBankName);
						$("#txtECSLetterCode").val(response.strEcsLetterCode);
						$("#txtBccEmailId").val(response.strEmailBcc);
						$("#txtCcEmailId").val(response.strEmailCc);
						$("#txtFromEmailId").val(response.strEmailFrom);
						$("#txtPortNo").val(response.strEmailSMTPPort);
						$("#txtEmailSMTPServerId").val(response.strEmailSmtpServer);				
				/* 		$("#").val(response.strExportType);
						$("#").val(response.strGolfFac);
						$("#").val(response.strGroupCode);	 */								
						funSetCheckStatusAndValue(chkIntegrityCheckYN,response.strIntegrityChk);
						$("#cmbInvoiceBasedOn").val(response.strInvoiceBasedOn);					
		/* 				$("#").val(response.strInvoiceNarrRec); */
						$("#txtInvoicerAdvCode").val(response.strInvoicerAdvCode);	
						$("#txtInvoicerAdvName").val(response.strInvoicerAdvName);									
						funSetCheckStatusAndValue(chkBlockJVEntryYN,response.strjventry);
						$("#txtInvoiceHeader1").val(response.strInvoiceHeader1);
						$("#txtInvoiceHeader2").val(response.strInvoiceHeader2);
						$("#txtInvoiceHeader3").val(response.strInvoiceHeader3);
						$("#txtInvoiceFooter1").val(response.strInvoiceFooter1);
						$("#txtInvoiceFooter2").val(response.strInvoiceFooter2);
						$("#txtInvoiceFooter3").val(response.strInvoiceFooter3);	
/* 						
						$("#").val(response.strLabelsetting);
						$("#").val(response.strLastCreated); */
						$("#txtInvoiceLetterCode").val(response.strLetterCode);
						$("#txtReminderLetterCodePrefix").val(response.strLetterPrefix);
/* 						$("#").val(response.strLogo); */
						funSetCheckStatusAndValue(chkMasterDrivenNarrationYN,response.strMasterDrivenNarration);
						/* $("#").val(response.strMemberPreProfiling);		 */								
						funSetCheckStatusAndValue(chkBlockMemberReceiptYN,response.strmembrecp);			
						funSetCheckStatusAndValue(chkActivateJourVoucherNarrJVYN,response.strNarrActivateJv);			
						funSetCheckStatusAndValue(chkActivateJourVoucherNarrPayYN,response.strNarrActivatePay);			
						funSetCheckStatusAndValue(chkActivateJourVoucherNarrReceiptYN,response.strNarrActivateRec);			
						funSetCheckStatusAndValue(chkActivateJourVoucherNarrInvoiceYN,response.strNarrActivateInv);
						$("#txtPassword").val(response.strPassword);									
						funSetCheckStatusAndValue(chkBlockPaymentEntryYN,response.strpayentry);
	/* 					$("#").val(response.strPettyCashAccountCode);
						$("#").val(response.strPMS); */
						$("#txtCommonDBName").val(response.strPOSCommonDB);
						$("#txtMSDNDBName").val(response.strPOSMSDNdb);			
						$("#txtQFileDBName").val(response.strPOSQfileDB);						
						$("#txtPostDatedChequeACCode").val(response.strPostDatedChequeACCode);			
						$("#txtPostDatedChequeACName").val(response.strPostDatedChequeACName);						
					/* 	$("#").val(response.strpropertyid);
						$("#").val(response.strReceiptBcc);
						$("#").val(response.strReceiptCc); */
						$("#txtReceiptLetterCode").val(response.strReceiptLetterCode);									
						funSetCheckStatusAndValue(chkBlockReceiptEntryYN,response.strrecpentry);
						$("#txtReservationAdvPartyACCode").val(response.strReserveAccCode);
						$("#txtReservationAdvPartyACName").val(response.strReserveAccName);
						$("#txtRoundingOffACCode").val(response.strRoundingCode);
						$("#txtRoundingOffACName").val(response.strRoundingName);
/* 						$("#").val(response.strRoundOffCode); */
						$("#txtDefaultSanctionCode").val(response.strSancCode);
						$("#txtDefaultSanctionName").val(response.strSancName);
			/* 			$("#").val(response.strServiceTaxAccount);			
						$("#").val(response.strSmtpPassword);
						$("#").val(response.strSmtpUserid);	 */									
						funSetCheckStatusAndValue(chkSSLRequiredYN,response.strSSLRequiredYN);
						$("#txtDebtorsSuspenseCode").val(response.strSuspenceCode);				
						$("#txtDebtorsSuspenseName").val(response.strSuspenceName);
						
						funSetCheckStatusAndValue(chkTallyAlifTransLockYN,response.strTallyAlifTransLockYN);
	/* 					$("#").val(response.strTaxCode); */
						$("#cmbTAXIndicatorInTransYN").val(response.strTaxIndicator);
/* 						$("#").val(response.strTypeOfPOsting); */
						$("#txtUserId").val(response.strUserid);
						$("#txtVoucherNarrationJV").val(response.strVouchNarrJv);
						$("#txtVoucherNarrationPay").val(response.strVouchNarrPay);
						$("#txtVoucherNarrationReceipt").val(response.strVouchNarrRec);
						$("#txtVoucherNarrationInvoice").val(response.strVouchNarrInvoice);
/* 						$("#").val(response.strYeaOutsta);	 */											
						funSetCheckStatusAndValue(chkSingleUserYN,response.allowSingleUserLogin);						
						
						/* $("#dteLastARTransDate").val(response.DteLastAR);						
						$("#dteRevenueTransDate").val(response.DteLastRV);								
						$("#dteRevenuePostedUpToDate").val(response.DteRVPosted); */																							
						
						funSetDate(dteLastARTransDate,response.dteLastAR);
						funSetDate(dteRevenueTransDate,response.dteLastRV);
						funSetDate(dteRevenuePostedUpToDate,response.dteRVPosted);
						
		/* 				$("#").val(objGlobal.funCurrentDate("yyyy-MM-dd"));		 */									
						funSetCheckStatusAndValue(chkEmailViaOutlookYN,response.emailViaOutlook);						
						funSetCheckStatusAndValue(chkIncludeBanquetMemberYN,response.includeBanquetMember);			
						funSetCheckStatusAndValue(chkMSOfficeInstalledYN,response.isMSOfficeInstalled);		
						funSetCheckStatusAndValue(chkMultipleDebtorYN,response.isMultipleDebtor);
						$("#txtOnlineNEFTACCode").val(response.neftonlineAccountCode);
						$("#txtOnlineNEFTACName").val(response.neftonlineAccountName);
					/* 	$("#").val(response.PDCAccountCode);
						$("#").val(response.PDCAccountDesc);
						$("#").val(response.sML); */		        	
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
		
	/* To load and fill all data on the form when form open */
 	$(document).ready(function()
	{
		funLoadAndFillFormData();		
	}); 
	
	function funIconified()
	{	    
	   var cl=$("#btnIconified").attr("class"); 	   
	   
	   if(cl=="btnMaximize")
	   {
	       $("#btnIconified").removeClass("btnMaximize");		
	       $("#btnIconified").addClass("btnMinimize");	
	       $("#divBox").fadeToggle("fast","linear");
	       $("#thTittleBar").text("Reco Report Parameters (Show Details)");
	       
	   }	   
	   else
	   {
	       $("#btnIconified").removeClass("btnMinimize");		
	       $("#btnIconified").addClass("btnMaximize");
	       $("#divBox").fadeToggle("fast","linear");
	       $("#thTittleBar").text("Reco Report Parameters (Hide Details)");
	       
	   }	
	   /* $("#divBox").fadeIn(); */
	   /* $("#divBox").fadeOut(); */
	   /* $("#divBox").slideToggle("fast"); */ 
	}
	
	/* To Disable spellcheck For All TextArea Of Class .txtTextArea */
	$(document).ready(function()
	{
	    $(".txtTextArea").each(function(index,element)
	    {
	   		var textArea=element;
       		textArea.spellcheck = false;
	   	});    
	});
	
	/* To Set CheckBox Value To Y/N */
	function funSetCheckBoxValueYN(currentCheckBox)
	{		
	    var isSelected="N";
	    if($(currentCheckBox).prop("checked") == true)
	    {           
	        isSelected="Y";
        }
	    if($(currentCheckBox).prop("checked") == false)
        {         
	        isSelected="N";
        }
	    $(currentCheckBox).val(isSelected);		    	   
	}	
	
	/* For All Dates */
	$(document).ready(function()
	{
	    $("#dteLastARTransDate").datepicker(
	    {
			dateFormat : 'dd-mm-yy'
		});
		$("#dteLastARTransDate").datepicker('setDate', 'today');

		$("#dteRevenueTransDate").datepicker(
		{
			dateFormat : 'dd-mm-yy'
		});
		$("#dteRevenueTransDate").datepicker('setDate', 'today');
		
		$("#dteRevenuePostedUpToDate").datepicker(
		{
			dateFormat : 'dd-mm-yy'
		});
		$("#dteRevenuePostedUpToDate").datepicker('setDate', 'today');    		
				
	});
	
	/* For Show Tabs */
	$(document).ready(function()
	{	   
	    $(".tab_conents").hide();
	    $(".tab_conents:first").show();
	    $(".tabs li").click(function()
	    {	       
	       $("ul.tabs li").removeClass("active"); 
	       $(this).addClass("active");
	       $(".tab_conents").hide();
	       var currentTab=$(this).attr("data-state");
	       $("#"+currentTab).fadeIn();
	    });
	});
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	/**
	* Success Message After Saving Record
	**/
	 $(document).ready(function()
				{
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

		funLoadAndFillFormData();		
	});
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>ParameterSetup</label>
	</div>

<br/>
<br/>

	<s:form name="ParameterSetup" method="POST" action="saveParameterSetup.html" >
        <!-- table which holds all tabs header -->
		<table class="masterTable">
			<tr>
				<th id="tab_container" style="height: 100%;">
					<ul class="tabs" >
						<li class="active" data-state="tab1" style="width: 23.5%; ">AR Parameters</li>
						<li data-state="tab2" style="width: 23.5%; ">Invoicing Parameters</li>
						<li data-state="tab3" style="width: 23.5%; ">Narration Builder</li>
						<li data-state="tab4" style="width: 23.5%; ">Email Settings</li>						
					</ul>
				</th>
			</tr>
		</table>
        <br>
        <br>
        
        <!-- AR Parameters Tab -->
        <div id="tab1" class="tab_conents">
        	<table class="masterTable">
        		<tr>
			    	<td><label>Debtor Control A/C</label></td>
			    	<td><s:input id="txtDebtorControlACCode" path="strControlCode" readonly="true" ondblclick="funSetACType('debtorControlAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtDebtorControlACName" path="strControlName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Debtor Billable A/C</label></td>
			    	<td><s:input id="txtDebtorBillableACCode" path="strBillableCode" readonly="true" ondblclick="funSetACType('debtorBillableAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtDebtorBillableACName" path="strBillableName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Debtor Suspense A/C</label></td>
			    	<td><s:input id="txtDebtorSuspenseACCode" path="strDbtrSuspAcctCode" readonly="true" ondblclick="funSetACType('debtorSuspenseAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtDebtorSuspenseACName" path="strDbtrSuspAcctName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>															
				<tr>
			    	<td><label>Debtors Suspense Code</label></td>
			    	<td><s:input id="txtDebtorsSuspenseCode" path="strSuspenceCode" readonly="true" ondblclick="funSetACType('debtorSuspenseCode')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtDebtorsSuspenseName" path="strSuspenceName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Rounding Off A/C</label></td>
			    	<td><s:input id="txtRoundingOffACCode" path="strRoundingCode" readonly="true" ondblclick="funSetACType('roundingOffAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtRoundingOffACName" path="strRoundingName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Reservation Advance Party A/C</label></td>
			    	<td><s:input id="txtReservationAdvPartyACCode" path="strReserveAccCode" readonly="true" ondblclick="funSetACType('reservationAdvPartyAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtReservationAdvPartyACName" path="strReserveAccName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Room Advance A/C</label></td>
			    	<td><s:input id="txtRoomAdvACCode" path="strDbtRoomAdvCode" readonly="true" ondblclick="funSetACType('roomAdvAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtRoomAdvACName" path="strDbtRoomAdvName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Invoicer Advance </label></td>
			    	<td><s:input id="txtInvoicerAdvCode" path="strInvoicerAdvCode" readonly="true" ondblclick="funSetACType('invoicerAdv')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtInvoicerAdvName" path="strInvoicerAdvName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Online NEFT A/C Code</label></td>
			    	<td><s:input id="txtOnlineNEFTACCode" path="NEFTOnlineAccountCode" readonly="true" ondblclick="funSetACType('onlineNEFTAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtOnlineNEFTACName" path="NEFTOnlineAccountName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Room A/C</label></td>
			    	<td><s:input id="txtRoomACCode" path="strDbtRoomACCode" readonly="true" ondblclick="funSetACType('roomAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtRoomACName" path="strDbtRoomACName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Post Dated Cheque A/C</label></td>
			    	<td><s:input id="txtPostDatedChequeACCode" path="strPostDatedChequeACCode" readonly="true" ondblclick="funSetACType('postDatedAdvAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtPostDatedChequeACName" path="strPostDatedChequeACName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			  	    <td><label>Last A/R Transfer Date</label></td>	
			  	    <td colspan="8"><s:input type="text" id="dteLastARTransDate" path="dteLastAR"  class="calenderTextBox" style="width: 117px; background-position: 100px -2px;" /></td>			   	    		    		 
				</tr>
				<tr>
			  	    <td><label>Revenue Transfer Date</label></td>	
			   	    <td colspan="8"><s:input type="text" id="dteRevenueTransDate" path="dteLastRV" class="calenderTextBox"  style="width: 117px;background-position: 100px -2px;" /></td>		    		  
				</tr>
				<tr>
			  	    <td><label>Revenue Posted UpTo</label></td>	
			   	    <td colspan="8"><s:input type="text" id="dteRevenuePostedUpToDate" path="dteRVPosted"  class="calenderTextBox" style="width: 117px;background-position: 100px -2px;" /></td>		    		  
				</tr>
				<tr>
			    	<td><label>ECS Bank</label></td>
			    	<td><s:input id="txtECSBankCode" path="strECSBankcode" readonly="true" ondblclick="funSetACType('ecsBank')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtECSBankName" path="strECSBankName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Default Sanction</label></td>
			    	<td><s:input id="txtDefaultSanctionCode" path="strSancCode" readonly="true" ondblclick="funSetACType('defaultSanction')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtDefaultSanctionName" path="strSancName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Reminder Letter Code Prefix</label></td>
			    	<td colspan="8"><s:input id="txtReminderLetterCodePrefix" path="strLetterPrefix"  cssClass="simpleTextBox" style="width: 117px" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Credit Limit Control</label></td>
			    	<td colspan="8"><s:checkbox id="chkCreditLimitCtlYN"  path="strCreditLimit" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Block Transactions For Black Listed Members</label></td>
			    	<td><label>JV Entry</label></td>
			    	<td><s:checkbox id="chkBlockJVEntryYN"  path="strjventry" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>	
			    	<td><label>Payment Entry</label></td>
			    	<td><s:checkbox id="chkBlockPaymentEntryYN"  path="strpayentry" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>
			    	<td><label>Receipt Entry</label></td>
			    	<td><s:checkbox id="chkBlockReceiptEntryYN"  path="strrecpentry" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>
			    	<td><label>Member Receipt</label></td>
			    	<td><s:checkbox id="chkBlockMemberReceiptYN"  path="strmembrecp" value=""  onclick="funSetCheckBoxValueYN(this)"/></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Debtor Currency</label></td>
			    	<td><s:input id="txtDebtorCurrencyAmt" path="strCurrencyDesc" placeholder="Amount" cssClass="simpleTextBox" style="width: 117px" /></td>			        			        
			    	<td colspan="7"><s:input id="txtDebtorCurrencyAmtUnit" path="strCurrencyCode" placeholder="Rs. / CR / $ / etc." cssClass="longTextBox" cssStyle="width:30%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Amadeus PMS Interface</label></td>
			    	<td colspan="8"><s:checkbox id="chkAmadeusInterfaceYN"  path="strAmadeusInterface" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Debtor Ledger A/C</label></td>
			    	<td><s:input id="txtDebtorLedgerACCode" path="strDebtorLedgerACCode" readonly="true" ondblclick="funSetACType('debtorLedgerAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtDebtorLedgerACName" path="strDebtorLedgerACName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Advance A/C</label></td>
			    	<td><s:input id="txtAdvACCode" path="strAdvanceACCode" readonly="true" ondblclick="funSetACType('advAC')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="7"><s:input id="txtAdvACName" path="strAdvanceAcct" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Master Driven Narration</label></td>
			    	<td colspan="8"><s:checkbox id="chkMasterDrivenNarrationYN"  path="StrMasterDrivenNarration" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>MS-Office Installed</label></td>
			    	<td colspan="8"><s:checkbox id="chkMSOfficeInstalledYN"  path="IsMSOfficeInstalled" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Include Banquet Member</label></td>
			    	<td colspan="8"><s:checkbox id="chkIncludeBanquetMemberYN"  path="IncludeBanquetMember" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Mupltiple Debtor</label></td>
			    	<td colspan="8"><s:checkbox id="chkMultipleDebtorYN"  path="IsMultipleDebtor" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Single User Login</label></td>
			    	<td colspan="8"><s:checkbox id="chkSingleUserYN"  path="AllowSingleUserLogin" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Email Via Outlook</label></td>
			    	<td colspan="8"><s:checkbox id="chkEmailViaOutlookYN"  path="EmailViaOutlook" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
			    	<td><label>Tally/Alif Transaction Lock</label></td>
			    	<td colspan="8"><s:checkbox id="chkTallyAlifTransLockYN"  path="strTallyAlifTransLockYN" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>			        			        			    				    		        			  
				</tr>
        	</table>
        </div>
        <!-- Invoicing Parameters Tab -->
        <div id="tab2" class="tab_conents">
        	<table class="masterTable">
        		<tr>
        			<td><label>Invoice Header 1</label></td>
        			<td><s:textarea id="txtInvoiceHeader1" path="strInvoiceHeader1" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Invoice Header 2</label></td>
        			<td><s:textarea id="txtInvoiceHeader2" path="strInvoiceHeader2" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Invoice Header 3</label></td>
        			<td><s:textarea id="txtInvoiceHeader3" path="strInvoiceHeader3" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Invoice Footer 1</label></td>
        			<td><s:textarea id="txtInvoiceFooter1" path="strInvoiceFooter1" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Invoice Footer 2</label></td>
        			<td><s:textarea id="txtInvoiceFooter2" path="strInvoiceFooter2" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Invoice Footer 3</label></td>
        			<td><s:textarea id="txtInvoiceFooter3" path="strInvoiceFooter3" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Invoice Based On</label></td>
        			<td><s:select id="cmbInvoiceBasedOn" path="strInvoiceBasedOn" items="${listInvoiceBasedOn}" cssClass="BoxW124px" /></td>
        		</tr>
        		<tr>
			    	<td><label>Bill Prefix</label></td>
			    	<td><s:input id="txtBillPrefix" path="strBillPrefix" cssClass="simpleTextBox" style="width: 117px" /></td>			        			        			    				    		        			  
				</tr>
				<tr>
        			<td><label>TAX Indiactor In Transaction</label></td>
        			<td><s:select id="cmbTAXIndicatorInTransYN" path="strTaxIndicator" items="${listTAXIndicatorInTrans}" cssClass="BoxW124px" /></td>
        		</tr>
        		<tr>
        			<td><label>Integrity Check</label></td>
        			<td><s:checkbox id="chkIntegrityCheckYN"  path="strIntegrityChk" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>
        		</tr>
        		<tr>
			    	<td><label>Common DataBase Name</label></td>			    				        			       
			    	<td><s:input id="txtCommonDBName" path="strPOSCommonDB" required="true" cssClass="longTextBox" /></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>Q File DataBase Name</label></td>			    				        			       
			    	<td><s:input id="txtQFileDBName" path="strPOSQfileDB" required="true" cssClass="longTextBox" /></td>			    		        			   
				</tr>
				<tr>
			    	<td><label>MSDN DataBase Name</label></td>			    				        			       
			    	<td><s:input id="txtMSDNDBName" path="strPOSMSDNdb" required="true" cssClass="longTextBox" /></td>			    		        			   
				</tr>
        	</table>
        </div>
        <!-- Narration Builder Tab -->
        <div id="tab3" class="tab_conents">
        	<table class="masterTable" id="narrationBuilderTbl">
        		<tr>
        			<th colspan="4"><label>Journal Voucher</label></th>
        		</tr>
        		<tr>
        			<td><label>Voucher Narration</label></td>
        			<td colspan="3"><s:textarea id="txtVoucherNarrationJV" path="strVouchNarrJv" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Account Narration</label></td>
        			<td colspan="3"><s:textarea id="txtAccountNarrationJV" path="strAcctNarrJv" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Debtor Narration</label></td>
        			<td colspan="3"><s:textarea id="txtDebtorNarrationJV" path="strDebtorNarrJv" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Select Parameters</label></td>
        			<td><s:select id="cmbSelectParameters" path="" items="${listSelectParameters}" cssClass="BoxW200px" /></td>        		
        			<td><label>Activate Journal Voucher Narration</label></td>
        			<td><s:checkbox id="chkActivateJourVoucherNarrJVYN"  path="strNarrActivateJv" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>        		
        		</tr>        		
        		<tr>
        			<th colspan="4"><label>Payments</label></th>
        		</tr>
        		<tr>
        			<td><label>Voucher Narration</label></td>
        			<td colspan="3"><s:textarea id="txtVoucherNarrationPay" path="strVouchNarrPay" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Select Parameters</label></td>
        			<td><s:select id="cmbPaySelectParameters" path="" items="${listSelectParameters}" cssClass="BoxW200px" /></td>        		
        			<td><label>Activate Payments Narration</label></td>
        			<td><s:checkbox id="chkActivateJourVoucherNarrPayYN"  path="strNarrActivatePay" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>        		
        		</tr>
        		<tr>
        			<th colspan="4"><label>Receipts</label></th>
        		</tr>
        		<tr>
        			<td><label>Voucher Narration</label></td>
        			<td colspan="3"><s:textarea id="txtVoucherNarrationReceipt" path="strVouchNarrRec" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Select Parameters</label></td>
        			<td><s:select id="cmbRecptSelectParameters" path="" items="${listSelectParameters}" cssClass="BoxW200px" /></td>        		
        			<td><label>Activate Receipts Narration</label></td>
        			<td><s:checkbox id="chkActivateJourVoucherNarrReceiptYN"  path="strNarrActivateRec" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>        		
        		</tr>
        		<tr>
        			<th colspan="4"><label>Invoice</label></th>
        		</tr>
        		<tr>
        			<td><label>Voucher Narration</label></td>
        			<td colspan="3"><s:textarea id="txtVoucherNarrationInvoice" path="strVouchNarrInvoice" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        		</tr>
        		<tr>
        			<td><label>Select Parameters</label></td>
        			<td><s:select id="cmbInvSelectParameters" path="" items="${listSelectParameters}" cssClass="BoxW200px" /></td>        		
        			<td><label>Activate Invoice Narration</label></td>
        			<td><s:checkbox id="chkActivateJourVoucherNarrInvoiceYN"  path="strNarrActivateInv" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>        		
        		</tr>
        	</table>
        </div>
        <!-- Email Settings Tab -->
        <div id="tab4" class="tab_conents">
        	<table class="masterTable">
        		<tr>
        			<td><label>SMTP Server ID</label></td>        						    				        			      
			    	<td colspan="3"><s:input id="txtEmailSMTPServerId" path="strEmailSmtpServer" required="true" cssClass="longTextBox" /></td>			    		        			   				
        		</tr>
        		<tr>
        			<td><label>Is SSL Required</label></td>        						    				        			      
			    	<td colspan="3"><s:checkbox id="chkSSLRequiredYN"  path="strSSLRequiredYN" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>		        			   				
        		</tr>
        		<tr>
        			<td><label>Port No.</label></td>        						    				        			      
			    	<td colspan="3"><s:input id="txtPortNo" path="strEmailSMTPPort" required="true" cssClass="longTextBox" /></td>			    		        			   				
        		</tr>
        		<tr>
        			<td><label>User ID</label></td>        						    				        			      
			    	<td colspan="3"><s:input id="txtUserId" path="strUserid" required="true" cssClass="longTextBox" /></td>			    		        			   				
        		</tr>
        		<tr>
        			<td><label>Password</label></td>        						    				        			      
			    	<td colspan="3"><s:password id="txtPassword" path="strPassword" required="true" cssClass="longTextBox" /></td>			    		        			   				
        		</tr>
        		<tr>
        			<td><label>From</label></td>        						    				        			      
			    	<td><s:input id="txtFromEmailId" path="strEmailFrom" required="true" cssClass="longTextBox" style="width: 345px;" /></td>		
			    	<td><a href="#">(Show Details...)</a></td>
			    	<td></td>   	    		        			   				
        		</tr>
        		<tr>
        			<td><label>Cc</label></td>
        			<td><s:textarea id="txtCcEmailId" path="strEmailCc" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        			<td><label>Use ; as Seperator</label></td>   
        			<td></td>
        		</tr>
        		<tr>
        			<td><label>Bcc</label></td>
        			<td><s:textarea id="txtBccEmailId" path="strEmailBcc" class="txtTextArea" /></td><%-- cssClass="longTextBox" --%>
        			<td><label>Use ; as Seperator</label></td>
        			<td></td>
        		</tr>
        		<tr>
			    	<td style="width: 135px;"><label>Invoice Letter Code</label></td>
			    	<td><s:input id="txtInvoiceLetterCode" path="strLetterCode" readonly="true" ondblclick="funSetACType('')" cssClass="searchTextBox"/></td>			        			        
			    	<td style="float: right; width: 165%; "><s:input id="txtInvoiceLetterName" path="" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>
			    	<td></td>			    		    				    		        			 
				</tr>
				<tr>
			    	<td><label>Receipt Letter Code</label></td>
			    	<td><s:input id="txtReceiptLetterCode" path="strReceiptLetterCode" readonly="true" ondblclick="funSetACType('')" cssClass="searchTextBox"/></td>			        			        
			    	<td style="float: right; width: 165%; "><s:input id="txtReceiptLetterName" path="" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>
			    	<td></td>			    			    				    		        			 
				</tr>
				<tr>
			    	<td><label>ECS Letter Code</label></td>
			    	<td><s:input id="txtECSLetterCode" path="strEcsLetterCode" readonly="true" ondblclick="funSetACType('')" cssClass="searchTextBox"/></td>			        			        
			    	<td style="float: right; width: 165%; "><s:input id="txtECSLetterName" path="" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:96%"/></td>
			    	<td></td>	
				</tr>
				<tr>
        			<td><label>Password Protect PDF's</label></td>        						    				        			      
			    	<td><s:checkbox id="chkPassProtPDFYN"  path="" value=""  onclick="funSetCheckBoxValueYN(this)" /></td>	
			    	<td style="float: right; width: 165%; "><label>Note: Password Format(Membership No + Date Of Birth(ddmmyyyy))</label></td>
			    	<td></td>				    	      			   			
        		</tr>        		  	
        	</table>
        	<br>        	
        	<table class="masterTable">        		
        		<tr>
        			<th style="width: 15px; height: 15px;"><input type="button" id="btnIconified" class="btnMaximize" onclick='funIconified()'/></th>
        			<th id="thTittleBar" colspan="2" style="width: 15px; height: 15px;">Reco Report Parameters (Hide Details)</th>
        		</tr>
        		<tr   id="divBox" >                		    	
			    	<td colspan="4">
			    		<div style="height: 150px; " >
			    			<table class="masterTable" style="margin: 0px; width: 100%">
			    				<tr>
				        			<td style="background-color: #c0e4ff"><label>Revenue Posting &amp; JV Reco To Email</label></td>
				        			<td style="background-color: #c0e4ff"><s:textarea id="txtCcEmailId" path="" class="txtTextArea" /></td><!-- cssClass="longTextBox" -->
				        			<td style="background-color: #c0e4ff"><label>Use ; as Seperator</label></td>   				        			
				        		</tr>
				        		<tr>
				        			<td><label>Revenue Posting &amp; JV Reco Cc Email</label></td>
				        			<td><s:textarea id="txtBccEmailId" path="" class="txtTextArea" /></td><!-- cssClass="longTextBox" -->
				        			<td><label>Use ; as Seperator</label></td>				        			
				        		</tr>
			    			</table>
			    		</div>			    		
			    	</td>				    	      			   			
        		</tr>        		
        	</table>        	        
        </div>
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<br />
		<br />
	
	</s:form>	
</body>
</html>
