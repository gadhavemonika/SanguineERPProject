<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style>

#tab_container {
	width: 100%;
	margin: 1;
	
	height: 500px;
	overflow: auto;
	float: right;
	
}
ul.tab {
	margin: 0;
	padding: 0;
	
	
	
}

ul.tab li {
	width:100px;
	margin: 0;
	cursor: pointer;
	padding: 4px 4px;
	height:20px;
	line-height: 16px;
	border: 1px solid #6DA9DB;
	border-left: none;
	
	background: #52A4D4;
	overflow: hidden;
	position: relative;
	color: #fff;
	border-right: 1px solid #555;
	font-size:12px;
	font-weight: bold;
}


ul.tab li.active {
	background: -moz-linear-gradient(center top, #0F5495, #73ADDD) repeat
		scroll 0 0 #f18d05;
	border: 1px solid #0F5495;
	border-radius: 0 0px;
	box-shadow: 0 1px 0 rgba(90, 52, 139, 0.16), 0 1px 0 #0F5495 inset;
	color: #fff;
	transition: all 0.9s ease 0s;
}

</style>


<script type="text/javascript">
	$(document).ready(function() {
		
			$('input#txtTaxDesc').mlKeyboard({layout: 'en_US'});
		  	$('input#txtTaxShortName').mlKeyboard({layout: 'en_US'});
		 	$('input#txtAmount').mlKeyboard({layout: 'en_US'});
		  	$('input#txtPercent').mlKeyboard({layout: 'en_US'});
		
		  	$("#txtdteValidFrom").datepicker({ dateFormat: 'yy-mm-dd' });
			$("#txtdteValidFrom" ).datepicker('setDate', 'today');
			$("#txtdteValidFrom").datepicker();
			
	        $("#txtdteValidTo").datepicker({ dateFormat: 'yy-mm-dd' });
	        $("#txtdteValidTo" ).datepicker('setDate', 'today');
	        $("#txtdteValidTo").datepicker();
	            

		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tab li").click(function() {
			$("ul.tab li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
	
		 $("#cmbPosCode").change(function() {
			
			 funSetSaveUpdateBtn();
			 	funFillPOSWiseData();
		        	});
		  
		   $("#cmbSelectedType").change(function() {
			   funFillSelectedTypeDtlTable();
		        	});
			//$('#clientImage').attr('src', getContextPath()+"/resources/images/company_Logo.png");
		   $('#clientImage').attr('src', getContextPath()+"/resources/images/imgClientImage.jpg");
	});
	
	$(document).ready(function()
			{
				var message='';
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
						%>alert("Data Saved Successfully! \n\n"+message);<%
					}
				}%>
			});
			
	function funSetSaveUpdateBtn()
	{
		var posCode=$('#cmbPosCode').val();
		var searchurl=getContextPath()+"/funGetPos.html?posCode="+posCode;
		 $.ajax({
			        type: "GET",
			       
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.count==0)
			        		$('#submitBtn').val("Save");
			        	else
			        		$('#submitBtn').val("Update");

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
	function funFillPOSWiseData()
	{
		var code= $("#cmbPosCode").val();
		var searchurl=getContextPath()+"/loadPOSWisePropertySetupData.html?posCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	
				        	$("#cmbCity").val(response.strCity);
				        	
				        	$("#txtClientCode").val(response.strClientCode);
				        	$("#txtClientName").val(response.strClientName);
				        	$("#txtAddrLine1").val(response.strAddrLine1);
				        	$("#txtAddrLine2").val(response.strAddrLine2);
				        	$("#txtAddrLine3").val(response.strAddrLine3);
				        	$("#txtPinCode").val(response.strPinCode);
				        	$("#cmbState").val(response.strState);
				        	$("#cmbCountry").val(response.strCountry);
				        	$("#txtTelephone").val(response.strTelephone);
				        	$("#txtEmail").val(response.strEmail);
				        	$("#cmbNatureOfBussness").val(response.strNatureOfBussness);
				        	
				        	$("#txtBillFooter").val(response.strBillFooter);
				        	$("#cmbBillPaperSize").val(response.intBiilPaperSize);
				        	$("#cmbPrintMode").val(response.strBillPrintMode);
				        	$("#cmbColumnSize").val(response.intColumnSize);
				        	$("#cmbPrintType").val(response.strPrintingType);
				        	$("#cmbBillFormatType").val(response.strBillFormat);
				        	
				        	if(response.chkShowBills=='Y')
				        		{
				        		$("#chkShowBills").prop('checked',true);
				        		}
				        	else
				        		$("#chkShowBills").prop('checked',false);
				        		if(response.chkNegBilling=='Y')
				        		{
				        		$("#chkNegBilling").prop('checked',true);
				        		}
				        		else
					        		$("#chkNegBilling").prop('checked',false);
					        	
				        		if(response.chkPrintKotForDirectBiller=='Y')
				        		{
				        		$("#chkPrintKotForDirectBiller").prop('checked',true);
				        		}
				        		
				        		else
					        		$("#chkPrintKotForDirectBiller").prop('checked',false);
					        	if(response.chkEnableKOT=='Y')
				        		{
				        		$("#chkEnableKOT").prop('checked',true);
				        		}
					        	else
					        		$("#chkEnableKOT").prop('checked',false);
					        	
				        		if(response.chkMultiBillPrint=='Y')
				        		{
				        		$("#chkMultiBillPrint").prop('checked',true);
				        		}
				        		else
					        		$("#chkMultiBillPrint").prop('checked',false);
					        	
				        		if(response.chkDayEnd=='Y')
				        		{
				        		$("#chkDayEnd").prop('checked',true);
				        		}
				        		else
					        		$("#chkDayEnd").prop('checked',false);
					        	
				        		if(response.chkPrintShortNameOnKOT=='Y')
				        		{
				        		$("#chkPrintShortNameOnKOT").prop('checked',true);
				        		}
				        		else
					        		$("#chkPrintShortNameOnKOT").prop('checked',false);
					        	
				        		if(response.chkMultiKOTPrint=='Y')
				        		{
				        		$("#chkMultiKOTPrint").prop('checked',true);
				        		}
				        		else
					        		$("#chkMultiKOTPrint").prop('checked',false);
					        	
				        		if(response.chkPrintInvoiceOnBill=='Y')
				        		{
				        		$("#chkPrintInvoiceOnBill").prop('checked',true);
				        		}
				        		else
					        		$("#chkPrintInvoiceOnBill").prop('checked',false);
					        	
				        		if(response.chkPrintTDHItemsInBill=='Y')
				        		{
				        		$("#chkPrintTDHItemsInBill").prop('checked',true);
				        		}
				        		else
					        		$("#chkPrintTDHItemsInBill").prop('checked',false);
					        	
				        		if(response.chkManualBillNo=='Y')
				        		{
				        		$("#chkManualBillNo").prop('checked',true);
				        		}
				        		else
					        		$("#chkManualBillNo").prop('checked',false);
					        	
				        		if(response.chkPrintInclusiveOfAllTaxesOnBill=='Y')
				        		{
				        		$("#chkPrintInclusiveOfAllTaxesOnBill").prop('checked',true);
				        		}
				        		else
					        		$("#chkPrintInclusiveOfAllTaxesOnBill").prop('checked',false);
					        	
				        		if(response.chkEffectOnPSP=='Y')
				        		{
				        		$("#chkEffectOnPSP").prop('checked',true);
				        		}
				        		else
					        		$("#chkEffectOnPSP").prop('checked',false);
					        	
				        		if(response.chkPrintVatNo=='Y')
				        		{
				        		$("#chkPrintVatNo").prop('checked',true);
				        		}
				        		
				        		else
					        		$("#chkPrintVatNo").prop('checked',false);
					        	$("#txtVatNo").val(response.strVatNo);
				        		
				        		$("#txtNoOfLinesInKOTPrint").val(response.intNoOfLinesInKOTPrint);
				        		if(response.chkServiceTaxNo=='Y')
				        		{
				        		$("#chkServiceTaxNo").prop('checked',true);
				        		}
				        		else
					        		$("#chkServiceTaxNo").prop('checked',false);
					        	
				        	$("#txtServiceTaxNo").val(response.strServiceTaxNo);
				        	
				        	$("#cmbShowBillsDtlType").val(response.strShowBillsDtlType);
				        	$("#txtAdvRecPrintCount").val(response.strAdvRecPrintCount);
				        		
				        	$("#cmbPOSType").val(response.strPOSType);
				        	$("#cmbDataSendFrequency").val(response.strDataSendFrequency);
				        	$("#txtWebServiceLink").val(response.strWebServiceLink);
				        	$("#dteHOServerDate").val(response.dteHOServerDate);
				        	$("#txtMaxDiscount").val(response.dblMaxDiscount);
				        	$("#cmbChangeTheme").val(response.strChangeTheme);
				        	$("#cmbDirectArea").val(response.strDirectArea);
				        	
				        	if(response.chkAreaWisePricing=='Y')
				        		{
				        		$("#chkAreaWisePricing").prop('checked',true);
				        		}
				        	else
				        		$("#chkAreaWisePricing").prop('checked',false);
				        		
				        	$("#txtCustSeries").val(response.strCustSeries);
				        	
				        		if(response.chkSlabBasedHomeDelCharges=='Y')
				        		{
				        		$("#chkSlabBasedHomeDelCharges").prop('checked',true);
				        		}
				        		else
					        		$("#chkSlabBasedHomeDelCharges").prop('checked',false);
					        	
				        		if(response.chkEditHomeDelivery=='Y')
				        		{
				        		$("#chkEditHomeDelivery").prop('checked',true);
				        		}
				        		
				        		else
					        		$("#chkEditHomeDelivery").prop('checked',false);
					        	if(response.chkPrintForVoidBill=='Y')
				        		{
				        		$("#chkPrintForVoidBill").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkPrintForVoidBill").prop('checked',false);
					        	if(response.chkDirectKOTPrintMakeKOT=='Y')
				        		{
				        		$("#chkDirectKOTPrintMakeKOT").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkDirectKOTPrintMakeKOT").prop('checked',false);
					        	if(response.chkSkipPaxSelection=='Y')
				        		{
				        		$("#chkSkipPaxSelection").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkSkipPaxSelection").prop('checked',false);
					        	if(response.chkPrintInvoiceOnBill=='Y')
				        		{
				        		$("#chkPrintInvoiceOnBill").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkPrintInvoiceOnBill").prop('checked',false);
					        	if(response.chkAreaMasterCompulsory=='Y')
				        		{
				        		$("#chkAreaMasterCompulsory").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkAreaMasterCompulsory").prop('checked',false);
					        	if(response.chkPostSalesDataToMMS=='Y')
				        		{
				        		$("#chkPostSalesDataToMMS").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkPostSalesDataToMMS").prop('checked',false);
					        	$("#cmbItemType").val(response.strItemType);
				        		
				        		if(response.chkPrinterErrorMessage=='Y')
				        		{
				        		$("#chkPrinterErrorMessage").prop('checked',true);
				        		}
				        		
				        		else
					        		$("#chkPrinterErrorMessage").prop('checked',false);
					        	if(response.chkActivePromotions=='Y')
				        		{
				        		$("#chkActivePromotions").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkActivePromotions").prop('checked',false);
					        	if(response.chkPrintKOTYN=='Y')
				        		{
				        		$("#chkPrintKOTYN").prop('checked',true);
				        		}
				        		
					        	else
					        		$("#chkPrintKOTYN").prop('checked',false);
					        	if(response.chkChangeQtyForExternalCode=='Y')
				        		{
				        		$("#chkChangeQtyForExternalCode").prop('checked',true);
				        		}
					        	else
					        		$("#chkChangeQtyForExternalCode").prop('checked',false);
					        	
				        		$("#cmbStockInOption").val(response.strStockInOption);
				        		
				        		if(response.chkShowItemStkColumnInDB=='Y')
				        		{
				        		$("#chkShowItemStkColumnInDB").prop('checked',true);
				        		}
				        		else
					        		$("#chkShowItemStkColumnInDB").prop('checked',false);
					        	
				        		$("#cmbPriceFrom").val(response.strPriceFrom);
				        		
				        		if(response.chkPrintBill=='Y')
				        		{
				        		$("#chkPrintBill").prop('checked',true);
				        		}
				        		else
					        		$("#chkPrintBill").prop('checked',false);
					        	
				        	$("#cmbApplyDiscountOn").val(response.strApplyDiscountOn);
				        	
				        	if(response.chkUseVatAndServiceNoFromPos=='Y')
				        		{
				        		$("#chkUseVatAndServiceNoFromPos").prop('checked',true);
				        		}
				        	else
				        		$("#chkUseVatAndServiceNoFromPos").prop('checked',false);
				        	

				        	if(response.chkManualAdvOrderCompulsory=='Y')
				        		{
				        		$("#chkManualAdvOrderCompulsory").prop('checked',true);
				        		}
				        	else
				        		$("#chkManualAdvOrderCompulsory").prop('checked',false);
				        		
				        	
				        		if(response.chkPrintManualAdvOrderOnBill=='Y')
				        		{
				        		$("#chkPrintManualAdvOrderOnBill").prop('checked',true);
				        		}
				        		else
					        		$("#chkPrintManualAdvOrderOnBill").prop('checked',false);
					        		if(response.chkPrintModifierQtyOnKOT=='Y')
				        		{
				        		$("#chkPrintModifierQtyOnKOT").prop('checked',true);
				        		}
					        		else
						        		$("#chkPrintModifierQtyOnKOT").prop('checked',false);
						        	if(response.chkBoxAllowNewAreaMasterFromCustMaster=='Y')
				        		{
				        		$("#chkBoxAllowNewAreaMasterFromCustMaster").prop('checked',true);
				        		}
						        	else
						        		$("#chkBoxAllowNewAreaMasterFromCustMaster").prop('checked',false);
						        	
				        		$("#cmbMenuItemSortingOn").val(response.strMenuItemSortingOn);
				        		
				        		if(response.chkAllowToCalculateItemWeight=='Y')
				        		{
				        		$("#chkAllowToCalculateItemWeight").prop('checked',true);
				        		}
				        		else
					        		$("#chkAllowToCalculateItemWeight").prop('checked',false);
					        	
				        		$("#cmbMenuItemDisSeq").val(response.strMenuItemDisSeq);
				        			
				        		if(response.chkItemWiseKOTPrintYN=='Y')
				        		{
				        		$("#chkItemWiseKOTPrintYN").prop('checked',true);
				        		}
				        		else
					        		$("#chkItemWiseKOTPrintYN").prop('checked',false);
					        	if(response.chkItemQtyNumpad=='Y')
				        		{
				        		$("#chkItemQtyNumpad").prop('checked',true);
				        		}
					        	else
					        		$("#chkItemQtyNumpad").prop('checked',false);
					        	if(response.chkSlipNoForCreditCardBillYN=='Y')
				        		{
				        		$("#chkSlipNoForCreditCardBillYN").prop('checked',true);
				        		}
					        	else
					        		$("#chkSlipNoForCreditCardBillYN").prop('checked',false);
					        	if(response.chkPrintKOTToLocalPrinter=='Y')
				        		{
				        		$("#chkPrintKOTToLocalPrinter").prop('checked',true);
				        		}
					        	else
					        		$("#chkPrintKOTToLocalPrinter").prop('checked',false);
					        	
				        		
				        		if(response.chkExpDateForCreditCardBillYN=='Y')
				        		{
				        		$("#chkExpDateForCreditCardBillYN").prop('checked',true);
				        		}
				        		else
					        		$("#chkExpDateForCreditCardBillYN").prop('checked',false);
					        	if(response.chkDelBoyCompulsoryOnDirectBiller=='Y')
				        		{
				        		$("#chkDelBoyCompulsoryOnDirectBiller").prop('checked',true);
				        		}
					        	else
					        		$("#chkDelBoyCompulsoryOnDirectBiller").prop('checked',false);
					        	if(response.chkSelectWaiterFromCardSwipe=='Y')
				        		{
				        		$("#chkSelectWaiterFromCardSwipe").prop('checked',true);
				        		}
					        	else
					        		$("#chkSelectWaiterFromCardSwipe").prop('checked',false);
					        	if(response.chkEnableSettleBtnForDirectBillerBill=='Y')
				        		{
				        		$("#chkEnableSettleBtnForDirectBillerBill").prop('checked',true);
				        		}
					        	else
					        		$("#chkEnableSettleBtnForDirectBillerBill").prop('checked',false);
					        	
				        		if(response.chkMultipleWaiterSelectionOnMakeKOT=='Y')
				        		{
				        		$("#chkMultipleWaiterSelectionOnMakeKOT").prop('checked',true);
				        		}
				        		else
					        		$("#chkMultipleWaiterSelectionOnMakeKOT").prop('checked',false);
					        	
				        		if(response.chkDontShowAdvOrderInOtherPOS=='Y')
				        		{
				        		$("#chkDontShowAdvOrderInOtherPOS").prop('checked',true);
				        		}
				        		else
					        		$("#chkDontShowAdvOrderInOtherPOS").prop('checked',false);
					        	
				        	if(response.chkMoveTableToOtherPOS=='Y')
				        		{
				        		$("#chkMoveTableToOtherPOS").prop('checked',true);
				        		}
				        	else
				        		$("#chkMoveTableToOtherPOS").prop('checked',false);
				        	
				        	if(response.chkPrintZeroAmtModifierInBill=='Y')
				        		{
				        		$("#chkPrintZeroAmtModifierInBill").prop('checked',true);
				        		}
				        	else
				        		$("#chkPrintZeroAmtModifierInBill").prop('checked',false);
				        		if(response.chkMoveKOTToOtherPOS=='Y')
				        		{
				        		$("#chkMoveKOTToOtherPOS").prop('checked',true);
				        		}
				        		else
					        		$("#chkMoveKOTToOtherPOS").prop('checked',false);
					        		if(response.chkPointsOnBillPrint=='Y')
				        		{
				        		$("#chkPointsOnBillPrint").prop('checked',true);
				        		}
					        		else
						        		$("#chkPointsOnBillPrint").prop('checked',false);
						        	if(response.chkCalculateTaxOnMakeKOT=='Y')
				        		{
				        		$("#chkCalculateTaxOnMakeKOT").prop('checked',true);
				        		}
						        	else
						        		$("#chkCalculateTaxOnMakeKOT").prop('checked',false);
						        	
				        		if(response.chkCalculateDiscItemWise=='Y')
				        		{
				        		$("#chkCalculateDiscItemWise").prop('checked',true);
				        		}
				        		else
					        		$("#chkCalculateDiscItemWise").prop('checked',false);
					        	
				        		if(response.chkTakewayCustomerSelection=='Y')
				        		{
				        		$("#chkTakewayCustomerSelection").prop('checked',true);
				        		}
				        		else
					        		$("#chkTakewayCustomerSelection").prop('checked',false);
					        	if(response.chkSelectCustAddressForBill=='Y')
				        		{
				        		$("#chkSelectCustAddressForBill").prop('checked',true);
				        		}
					        	else
					        		$("#chkSelectCustAddressForBill").prop('checked',false);
					        	if(response.chkGenrateMI=='Y')
				        		{
				        		$("#chkGenrateMI").prop('checked',true);
				        		}
					        	else
					        		$("#chkGenrateMI").prop('checked',false);
					        	
				        		if(response.chkPopUpToApplyPromotionsOnBill=='Y')
				        			{
				        			$("#chkPopUpToApplyPromotionsOnBill").prop('checked',true);
				        			}
				        		else
					        		$("#chkPopUpToApplyPromotionsOnBill").prop('checked',false);
					        	$("#txtWSClientCode").val(response.strWSClientCode);
				        		
				        			if(response.chkCheckDebitCardBalOnTrans=='Y')
				        			{
				        			$("#chkCheckDebitCardBalOnTrans").prop('checked',true);
				        			}
				        			else
						        		$("#chkCheckDebitCardBalOnTrans").prop('checked',false);
						        	
				        			$("#txtDaysBeforeOrderToCancel").val(response.intDaysBeforeOrderToCancel);
				        			
				        			if(response.chkSettlementsFromPOSMaster=='Y')
				        			{
				        			$("#chkSettlementsFromPOSMaster").prop('checked',true);
				        			}
				        			else
						        		$("#chkSettlementsFromPOSMaster").prop('checked',false);
						        	
				        			$("#txtNoOfDelDaysForAdvOrder").val(response.intNoOfDelDaysForAdvOrder);
				        			
				        			if(response.chkShiftWiseDayEnd=='Y')
				        			{
				        			$("#chkShiftWiseDayEnd").prop('checked',true);
				        			}
				        			else
						        		$("#chkShiftWiseDayEnd").prop('checked',false);
						        	
				        			$("#txtNoOfDelDaysForUrgentOrder").val(response.intNoOfDelDaysForUrgentOrder);
				        			
				        			if(response.chkProductionLinkup=='Y')
				        			{
				        			$("#chkProductionLinkup").prop('checked',true);
				        			}
				        			else
						        		$("#chkProductionLinkup").prop('checked',false);
						        	
				        			if(response.chkSetUpToTimeForAdvOrder=='Y')
				        			{
				        			$("#chkSetUpToTimeForAdvOrder").prop('checked',true);
				        			}
				        			else
						        		$("#chkSetUpToTimeForAdvOrder").prop('checked',false);
						        	
				        			if(response.chkLockDataOnShift=='Y')
				        			{
				        			$("#chkLockDataOnShift").prop('checked',true);
				        			}
				        			else
						        		$("#chkLockDataOnShift").prop('checked',false);
						        	
				        			$("#cmbHH").val(response.strHours);
				        			$("#cmbMM").val(response.strMinutes);
				        			$("#cmbAMPM").val(response.strAMPM);
				        			
				        			if(response.chkEnableBillSeries=='Y')
				        			{
				        			$("#chkEnableBillSeries").prop('checked',true);
				        			}
				        			else
						        		$("#chkEnableBillSeries").prop('checked',false);
						        	
				        			if(response.chkSetUpToTimeForUrgentOrder=='Y')
				        			{
				        			$("#chkSetUpToTimeForUrgentOrder").prop('checked',true);
				        			}
				        			else
						        		$("#chkSetUpToTimeForUrgentOrder").prop('checked',false);
						        	
				        			
				        			if(response.chkEnablePMSIntegration=='Y')
				        			{
				        			$("#chkEnablePMSIntegration").prop('checked',true);
				        			}
				        			else
						        		$("#chkEnablePMSIntegration").prop('checked',false);
						        	
				        			$("#cmbUrgentOrderHH").val(response.strHoursUrgentOrder);
				        			$("#cmbToMM").val(response.strMinutesUrgentOrder);
				        			$("#cmbToAMPM").val(response.strAMPMUrgent);
				        			
				        			if(response.chkPrintTimeOnBill=='Y')
				        			{
				        			$("#chkPrintTimeOnBill").prop('checked',true);
				        			}
				        			else
						        		$("#chkPrintTimeOnBill").prop('checked',false);
						        	
				        			if(response.chkCarryForwardFloatAmtToNextDay=='Y')
				        			{
				        			$("#chkCarryForwardFloatAmtToNextDay").prop('checked',true);
				        			}
				        			else
						        		$("#chkCarryForwardFloatAmtToNextDay").prop('checked',false);
						        	
				        			if(response.chkPrintRemarkAndReasonForReprint=='Y')
				        			{
				        			$("#chkPrintRemarkAndReasonForReprint").prop('checked',true);
				        			}
				        			else
						        		$("#chkPrintRemarkAndReasonForReprint").prop('checked',false);
						        	
				        			if(response.chkShowItemDtlsForChangeCustomerOnBill=='Y')
				        			{
				        			$("#chkShowItemDtlsForChangeCustomerOnBill").prop('checked',true);
				        			}
				        			else
						        		$("#chkShowItemDtlsForChangeCustomerOnBill").prop('checked',false);
						        	
				        			if(response.chkEnableBothPrintAndSettleBtnForDB=='Y')
				        			{
				        			$("#chkEnableBothPrintAndSettleBtnForDB").prop('checked',true);
				        			}
				        			else
						        		$("#chkEnableBothPrintAndSettleBtnForDB").prop('checked',false);
						        	
				        		if(response.chkShowPopUpForNextItemQuantity=='Y')
				        			{
				        			$("#chkShowPopUpForNextItemQuantity").prop('checked',true);
				        			}
				        		else
					        		$("#chkShowPopUpForNextItemQuantity").prop('checked',false);
					        	
				        		if(response.chkOpenCashDrawerAfterBillPrint=='Y')
				        			{
				        			$("#chkOpenCashDrawerAfterBillPrint").prop('checked',true);
				        			}
				        		else
					        		$("#chkOpenCashDrawerAfterBillPrint").prop('checked',false);
					        	
				        			if(response.chkPropertyWiseSalesOrder=='Y')
				        			{
				        			$("#chkPropertyWiseSalesOrder").prop('checked',true);
				        			}
				        			else
						        		$("#chkPropertyWiseSalesOrder").prop('checked',false);
						        	
				        			
				        			
				        			
				        			$("#txtSenderEmailId").val(response.strSenderEmailId);
				        			$("#txtEmailPassword").val(response.strEmailPassword);
				        			$("#txtEmailConfirmPassword").val(response.strEmailPassword);
				        			$("#cmbEmailServerName").val(response.strEmailServerName);
				        			$("#txtReceiverEmailId").val(response.strReceiverEmailId);
				        			$("#txtBodyPart").val(response.strBodyPart);
				        			
				        			$("#cmbCardIntfType").val(response.strCardIntfType);
				        			$("#cmbRFIDSetup").val(response.strRFIDSetup);
				        			$("#txtRFIDServerName").val(response.strRFIDServerName);
				        			$("#txtRFIDUserName").val(response.strRFIDUserName);
				        			$("#txtRFIDPassword").val(response.strRFIDPassword);
				        			$("#txtRFIDDatabaseName").val(response.strRFIDDatabaseName);
				        			
				        			$("#cmbCRM").val(response.strCRM);
				        			$("#txtGetWebservice").val(response.strGetWebservice);
				        			$("#txtPostWebservice").val(response.strPostWebservice);
				        			$("#txtOutletUID").val(response.strOutletUID);
				        			$("#txtPOSID").val(response.strPOSID);
				        			
				        			$("#cmbSMSType").val(response.strSMSType);
				        			$("#txtAreaSMSApi").val(response.strAreaSMSApi);
				        			if(response.chkHomeDelSMS=='Y')
				        			{
				        			$("#chkHomeDelSMS").prop('checked',true);
				        			}
				        			else
						        		$("#chkHomeDelSMS").prop('checked',false);
						        	
				        			$("#txtAreaSendHomeDeliverySMS").val(response.strAreaSendHomeDeliverySMS);
				        			if(response.chkBillSettlementSMS=='Y')
				        			{
				        			$("#chkBillSettlementSMS").prop('checked',true);
				        			}
				        			else
						        		$("#chkBillSettlementSMS").prop('checked',false);
						        	
				        			$("#txtAreaBillSettlementSMS").val(response.strAreaBillSettlementSMS);
				        			
				        			$("#txtFTPAddress").val(response.strFTPAddress);
				        			$("#txtFTPServerUserName").val(response.strFTPServerUserName);
				        			$("#txtFTPServerPass").val(response.strFTPServerPass);
				        			
				        			$("#cmbCMSIntegrationYN").val(response.strCMSIntegrationYN);
				        			$("#txtCMSWesServiceURL").val(response.strCMSWesServiceURL);
				        			if(response.chkMemberAsTable=='Y')
				        			{
				        			$("#chkMemberAsTable").prop('checked',true);
				        			}
				        			else
						        		$("#chkMemberAsTable").prop('checked',false);
						        	
				        			if(response.chkMemberCodeForKOTJPOS=='Y')
				        			{
				        			$("#chkMemberCodeForKOTJPOS").prop('checked',true);
				        			}
				        			else
						        		$("#chkMemberCodeForKOTJPOS").prop('checked',false);
						        	
				        			if(response.chkMemberCodeForKotInMposByCardSwipe=='Y')
				        			{
				        			$("#chkMemberCodeForKotInMposByCardSwipe").prop('checked',true);
				        			}
				        			else
						        		$("#chkMemberCodeForKotInMposByCardSwipe").prop('checked',false);
						        	
				        		if(response.chkMemberCodeForMakeBillInMPOS=='Y')
				        			{
				        			$("#chkMemberCodeForMakeBillInMPOS").prop('checked',true);
				        			}
				        		else
					        		$("#chkMemberCodeForMakeBillInMPOS").prop('checked',false);
					        	
				        			if(response.chkMemberCodeForKOTMPOS=='Y')
				        			{
				        			$("#chkMemberCodeForKOTMPOS").prop('checked',true);
				        			}
				        			else
						        		$("#chkMemberCodeForKOTMPOS").prop('checked',false);
						        	
				        			if(response.chkSelectCustomerCodeFromCardSwipe=='Y')
				        			{
				        			$("#chkSelectCustomerCodeFromCardSwipe").prop('checked',true);
				        			}
				        			else
						        		$("#chkSelectCustomerCodeFromCardSwipe").prop('checked',false);
						        	
				        			$("#cmbCMSPostingType").val(response.strCMSPostingType);
				        			$("#cmbPOSForDayEnd").val(response.strPOSForDayEnd);
				        			
				        			
				        			
				        				$("#cmbInrestoPOSIntegrationYN").val(response.strInrestoPOSIntegrationYN);
				        				$("#txtInrestoPOSWesServiceURL").val(response.strInrestoPOSWesServiceURL);
				        				$("#txtInrestoPOSId").val(response.strInrestoPOSId);
				        				$("#txtInrestoPOSKey").val(response.strInrestoPOSKey);
					
				        				$("#cmbJioPOSIntegrationYN").val(response.strJioPOSIntegrationYN);
				        				$("#txtJioPOSWesServiceURL").val(response.strJioPOSWesServiceURL);
				        				
				        				
				        				if(response.chkNewBillSeriesForNewDay=='Y')
					        			{
					        			$("#chkNewBillSeriesForNewDay").prop('checked',true);
					        			}
				        				else
							        		$("#chkNewBillSeriesForNewDay").prop('checked',false);
							        	
					        			if(response.chkShowReportsPOSWise=='Y')
					        			{
					        			$("#chkShowReportsPOSWise").prop('checked',true);
					        			}
					        			else
							        		$("#chkShowReportsPOSWise").prop('checked',false);
							        	
					        			if(response.chkEnableDineIn=='Y')
					        			{
					        			$("#chkEnableDineIn").prop('checked',true);
					        			}
					        			else
							        		$("#chkEnableDineIn").prop('checked',false);
							        	
					        			if(response.chkAutoAreaSelectionInMakeKOT=='Y')
					        			{
					        			$("#chkAutoAreaSelectionInMakeKOT").prop('checked',true);
					        			}
					        			else
							        		$("#chkAutoAreaSelectionInMakeKOT").prop('checked',false);
							        	
					        			$("#txtJioMID").val(response.strJioMID);
					        			$("#txtJioTID").val(response.strJioTID);
					        			
					        			
					        			$("#txtJioActivationCode").val(response.strJioActivationCode);
					        			
					        			
					        				$("#txtJioDeviceID").val(response.strJioDeviceID);
					        				funLoadPrinterDtl();
				        				funSetSelectedBillSeries();
				        				
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
	
	function btnAdd_onclick() 
	{
		    var table = document.getElementById("tblSelectedTypeDtl");
		    var rowCount = table.rows.length;
		 	var flag=false;
		 	var code;
		 	 var name;
		    if(rowCount > 0)
	    	{
		    	 $('#tblSelectedTypeDtl tr').each(function() {
		    		
		    		  var checkbox = $(this).find("input[type='checkbox']");

		    		    if( checkbox.prop("checked") ){
		    		    	if(!flag)
		    		    		{
		    		    		  code=$(this).find("input[name='strCode']").val();
		    		    		  name=$(this).find("input[name='strName']").val();
		    		    		}
		    		    	else
		    		    		{
		    		    	code=code+","+$(this).find("input[name='strCode']").val();
		    		    	name=name+","+$(this).find("input[name='strName']").val();
		    		    	
		    		    		}
		    		    	flag=true;
 		    		   
		    		    } 
		    		   
		    			 });
		    	  $('#tblSelectedTypeDtl tr').has("input[type='checkbox']:checked").remove()
		    
		    	 if(!flag)
 		    	{
 		    	alert("Please Select The Item.");
 		    	return false;
 		    	}
		    	 else
		    		 {
		    		 funAddBillseriesDtlRow(code,name);
		    		 }
		    }
		
	}
	var cnt=0;
	function btnRemove_onclick() 
	{
		   var table = document.getElementById("tblBillseriesDtl");
		    var rowCount = table.rows.length;
		 	var flag=false;
		 
		    if(rowCount > 0)
	    	{
		    	 $('#tblBillseriesDtl tr').each(function() {
		    		 
		    		  var checkbox = $(this).find("input[id='chkRemove']");
		    		var i=parseInt($(this).find("input[name='serialNo']").val());
		    		    if( checkbox.prop("checked") ){
		    		    	var  code=$(this).find("input[name='listBillSeriesDtl["+(i-1)+"].strCodes']").val();
		    		    	var	  name=$(this).find("input[name='listBillSeriesDtl["+(i-1)+"].strNames']").val();
		    		    
		    		    	funAddSelectedTypeRow(code,name);
		    		    	flag=true;
		    		       
		    		        cnt++;
		    		    } 
		    		
		    			 });
		   	  $('#tblBillseriesDtl tr').has("input[id='chkRemove']:checked").remove()
		    	 var table = document.getElementById("tblBillseriesDtl");
			   var rowCount = table.rows.length;
			   if(rowCount==0)
				   document.getElementById("cmbSelectedType").disabled=false;
		    	 if(!flag)
 		    	{
 		    	alert("Please Select The Item.");
 		    	return false;
 		    	}
		    	 
		    }
		
	}
	function funGetSelectedRowIndex(obj)
	{
		selectedRowIndex = obj.parentNode.parentNode.rowIndex;
	}
	function funFillPOSData()
	{
		
		var searchurl=getContextPath()+"/loadPOSPropertySetupData.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        		$("#cmbPosCode").val(response.strPosCode);
				        	$("#cmbCity").val(response.strCity);
				        	
				        	$("#txtClientCode").val(response.strClientCode);
				        	$("#txtClientName").val(response.strClientName);
				        	$("#txtAddrLine1").val(response.strAddrLine1);
				        	$("#txtAddrLine2").val(response.strAddrLine2);
				        	$("#txtAddrLine3").val(response.strAddrLine3);
				        	$("#txtPinCode").val(response.strPinCode);
				        	$("#cmbState").val(response.strState);
				        	$("#cmbCountry").val(response.strCountry);
				        	$("#txtTelephone").val(response.strTelephone);
				        	$("#txtEmail").val(response.strEmail);
				        	$("#cmbNatureOfBussness").val(response.strNatureOfBussness);
				        	
				        	$("#txtBillFooter").val(response.strBillFooter);
				        	$("#cmbBillPaperSize").val(response.intBiilPaperSize);
				        	$("#cmbPrintMode").val(response.strBillPrintMode);
				        	$("#cmbColumnSize").val(response.intColumnSize);
				        	$("#cmbPrintType").val(response.strPrintingType);
				        	$("#cmbBillFormatType").val(response.strBillFormat);
				        	
				        	if(response.chkShowBills=='Y')
				        		{
				        		$("#chkShowBills").prop('checked',true);
				        		}
				        		if(response.chkNegBilling=='Y')
				        		{
				        		$("#chkNegBilling").prop('checked',true);
				        		}
				        		if(response.chkPrintKotForDirectBiller=='Y')
				        		{
				        		$("#chkPrintKotForDirectBiller").prop('checked',true);
				        		}
				        		if(response.chkEnableKOT=='Y')
				        		{
				        		$("#chkEnableKOT").prop('checked',true);
				        		}
				        		if(response.chkMultiBillPrint=='Y')
				        		{
				        		$("#chkMultiBillPrint").prop('checked',true);
				        		}
				        		if(response.chkDayEnd=='Y')
				        		{
				        		$("#chkDayEnd").prop('checked',true);
				        		}
				        		if(response.chkPrintShortNameOnKOT=='Y')
				        		{
				        		$("#chkPrintShortNameOnKOT").prop('checked',true);
				        		}
				        		if(response.chkMultiKOTPrint=='Y')
				        		{
				        		$("#chkMultiKOTPrint").prop('checked',true);
				        		}
				        		if(response.chkPrintInvoiceOnBill=='Y')
				        		{
				        		$("#chkPrintInvoiceOnBill").prop('checked',true);
				        		}
				        		
				        		if(response.chkPrintTDHItemsInBill=='Y')
				        		{
				        		$("#chkPrintTDHItemsInBill").prop('checked',true);
				        		}
				        		if(response.chkManualBillNo=='Y')
				        		{
				        		$("#chkManualBillNo").prop('checked',true);
				        		}
				        		if(response.chkPrintInclusiveOfAllTaxesOnBill=='Y')
				        		{
				        		$("#chkPrintInclusiveOfAllTaxesOnBill").prop('checked',true);
				        		}
				        		if(response.chkEffectOnPSP=='Y')
				        		{
				        		$("#chkEffectOnPSP").prop('checked',true);
				        		}
				        		if(response.chkPrintVatNo=='Y')
				        		{
				        		$("#chkPrintVatNo").prop('checked',true);
				        		}
				        		$("#txtVatNo").val(response.strVatNo);
				        		
				        		$("#txtNoOfLinesInKOTPrint").val(response.intNoOfLinesInKOTPrint);
				        		if(response.chkServiceTaxNo=='Y')
				        		{
				        		$("#chkServiceTaxNo").prop('checked',true);
				        		}
				        	
				        	$("#txtServiceTaxNo").val(response.strServiceTaxNo);
				        	
				        	$("#cmbShowBillsDtlType").val(response.strShowBillsDtlType);
				        	$("#txtAdvRecPrintCount").val(response.strAdvRecPrintCount);
				        		
				        	$("#cmbPOSType").val(response.strPOSType);
				        	$("#cmbDataSendFrequency").val(response.strDataSendFrequency);
				        	$("#txtWebServiceLink").val(response.strWebServiceLink);
				        	$("#dteHOServerDate").val(response.dteHOServerDate);
				        	$("#txtMaxDiscount").val(response.dblMaxDiscount);
				        	$("#cmbChangeTheme").val(response.strChangeTheme);
				        	$("#cmbDirectArea").val(response.strDirectArea);
				        	
				        	if(response.chkAreaWisePricing=='Y')
				        		{
				        		$("#chkAreaWisePricing").prop('checked',true);
				        		}
				        		$("#txtCustSeries").val(response.strCustSeries);
				        	
				        		if(response.chkSlabBasedHomeDelCharges=='Y')
				        		{
				        		$("#chkSlabBasedHomeDelCharges").prop('checked',true);
				        		}
				        		if(response.chkEditHomeDelivery=='Y')
				        		{
				        		$("#chkEditHomeDelivery").prop('checked',true);
				        		}
				        		if(response.chkPrintForVoidBill=='Y')
				        		{
				        		$("#chkPrintForVoidBill").prop('checked',true);
				        		}
				        		if(response.chkDirectKOTPrintMakeKOT=='Y')
				        		{
				        		$("#chkDirectKOTPrintMakeKOT").prop('checked',true);
				        		}
				        		if(response.chkSkipPaxSelection=='Y')
				        		{
				        		$("#chkSkipPaxSelection").prop('checked',true);
				        		}
				        		if(response.chkPrintInvoiceOnBill=='Y')
				        		{
				        		$("#chkPrintInvoiceOnBill").prop('checked',true);
				        		}
				        		if(response.chkAreaMasterCompulsory=='Y')
				        		{
				        		$("#chkAreaMasterCompulsory").prop('checked',true);
				        		}
				        		if(response.chkPostSalesDataToMMS=='Y')
				        		{
				        		$("#chkPostSalesDataToMMS").prop('checked',true);
				        		}
				        		$("#cmbItemType").val(response.strItemType);
				        		
				        		if(response.chkPrinterErrorMessage=='Y')
				        		{
				        		$("#chkPrinterErrorMessage").prop('checked',true);
				        		}
				        		if(response.chkActivePromotions=='Y')
				        		{
				        		$("#chkActivePromotions").prop('checked',true);
				        		}
				        		if(response.chkPrintKOTYN=='Y')
				        		{
				        		$("#chkPrintKOTYN").prop('checked',true);
				        		}
				        		if(response.chkChangeQtyForExternalCode=='Y')
				        		{
				        		$("#chkChangeQtyForExternalCode").prop('checked',true);
				        		}
				        		
				        		$("#cmbStockInOption").val(response.strStockInOption);
				        		
				        		if(response.chkShowItemStkColumnInDB=='Y')
				        		{
				        		$("#chkShowItemStkColumnInDB").prop('checked',true);
				        		}
				        		
				        		$("#cmbPriceFrom").val(response.strPriceFrom);
				        		
				        		if(response.chkPrintBill=='Y')
				        		{
				        		$("#chkPrintBill").prop('checked',true);
				        		}
				        	
				        	$("#cmbApplyDiscountOn").val(response.strApplyDiscountOn);
				        	
				        	if(response.chkUseVatAndServiceNoFromPos=='Y')
				        		{
				        		$("#chkUseVatAndServiceNoFromPos").prop('checked',true);
				        		}
				      

				        	if(response.chkManualAdvOrderCompulsory=='Y')
				        		{
				        		$("#chkManualAdvOrderCompulsory").prop('checked',true);
				        		}
				        		
				        	
				        		if(response.chkPrintManualAdvOrderOnBill=='Y')
				        		{
				        		$("#chkPrintManualAdvOrderOnBill").prop('checked',true);
				        		}
				        		if(response.chkPrintModifierQtyOnKOT=='Y')
				        		{
				        		$("#chkPrintModifierQtyOnKOT").prop('checked',true);
				        		}
				        		if(response.chkBoxAllowNewAreaMasterFromCustMaster=='Y')
				        		{
				        		$("#chkBoxAllowNewAreaMasterFromCustMaster").prop('checked',true);
				        		}
				        		$("#cmbMenuItemSortingOn").val(response.strMenuItemSortingOn);
				        		
				        		if(response.chkAllowToCalculateItemWeight=='Y')
				        		{
				        		$("#chkAllowToCalculateItemWeight").prop('checked',true);
				        		}
				        		
				        		$("#cmbMenuItemDisSeq").val(response.strMenuItemDisSeq);
				        			
				        		if(response.chkItemWiseKOTPrintYN=='Y')
				        		{
				        		$("#chkItemWiseKOTPrintYN").prop('checked',true);
				        		}
				        		if(response.chkItemQtyNumpad=='Y')
				        		{
				        		$("#chkItemQtyNumpad").prop('checked',true);
				        		}
				        		if(response.chkSlipNoForCreditCardBillYN=='Y')
				        		{
				        		$("#chkSlipNoForCreditCardBillYN").prop('checked',true);
				        		}
				        		if(response.chkPrintKOTToLocalPrinter=='Y')
				        		{
				        		$("#chkPrintKOTToLocalPrinter").prop('checked',true);
				        		}
				        		
				        		
				        		if(response.chkExpDateForCreditCardBillYN=='Y')
				        		{
				        		$("#chkExpDateForCreditCardBillYN").prop('checked',true);
				        		}
				        		if(response.chkDelBoyCompulsoryOnDirectBiller=='Y')
				        		{
				        		$("#chkDelBoyCompulsoryOnDirectBiller").prop('checked',true);
				        		}
				        		if(response.chkSelectWaiterFromCardSwipe=='Y')
				        		{
				        		$("#chkSelectWaiterFromCardSwipe").prop('checked',true);
				        		}
				        		if(response.chkEnableSettleBtnForDirectBillerBill=='Y')
				        		{
				        		$("#chkEnableSettleBtnForDirectBillerBill").prop('checked',true);
				        		}
				        		
				        		if(response.chkMultipleWaiterSelectionOnMakeKOT=='Y')
				        		{
				        		$("#chkMultipleWaiterSelectionOnMakeKOT").prop('checked',true);
				        		}
				        		
				        		if(response.chkDontShowAdvOrderInOtherPOS=='Y')
				        		{
				        		$("#chkDontShowAdvOrderInOtherPOS").prop('checked',true);
				        		}
				        	
				        	if(response.chkMoveTableToOtherPOS=='Y')
				        		{
				        		$("#chkMoveTableToOtherPOS").prop('checked',true);
				        		}
				        	
				        	if(response.chkPrintZeroAmtModifierInBill=='Y')
				        		{
				        		$("#chkPrintZeroAmtModifierInBill").prop('checked',true);
				        		}
				        		if(response.chkMoveKOTToOtherPOS=='Y')
				        		{
				        		$("#chkMoveKOTToOtherPOS").prop('checked',true);
				        		}
				        		if(response.chkPointsOnBillPrint=='Y')
				        		{
				        		$("#chkPointsOnBillPrint").prop('checked',true);
				        		}
				        		if(response.chkCalculateTaxOnMakeKOT=='Y')
				        		{
				        		$("#chkCalculateTaxOnMakeKOT").prop('checked',true);
				        		}
				        		if(response.chkCalculateDiscItemWise=='Y')
				        		{
				        		$("#chkCalculateDiscItemWise").prop('checked',true);
				        		}
				        		if(response.chkTakewayCustomerSelection=='Y')
				        		{
				        		$("#chkTakewayCustomerSelection").prop('checked',true);
				        		}
				        		if(response.chkSelectCustAddressForBill=='Y')
				        		{
				        		$("#chkSelectCustAddressForBill").prop('checked',true);
				        		}
				        		if(response.chkGenrateMI=='Y')
				        		{
				        		$("#chkGenrateMI").prop('checked',true);
				        		}
				        		
				        		if(response.chkPopUpToApplyPromotionsOnBill=='Y')
				        			{
				        			$("#chkPopUpToApplyPromotionsOnBill").prop('checked',true);
				        			}
				        			$("#txtWSClientCode").val(response.strWSClientCode);
				        		
				        			if(response.chkCheckDebitCardBalOnTrans=='Y')
				        			{
				        			$("#chkCheckDebitCardBalOnTrans").prop('checked',true);
				        			}
				        			
				        			$("#txtDaysBeforeOrderToCancel").val(response.intDaysBeforeOrderToCancel);
				        			
				        			if(response.chkSettlementsFromPOSMaster=='Y')
				        			{
				        			$("#chkSettlementsFromPOSMaster").prop('checked',true);
				        			}
				        			
				        			$("#txtNoOfDelDaysForAdvOrder").val(response.intNoOfDelDaysForAdvOrder);
				        			
				        			if(response.chkShiftWiseDayEnd=='Y')
				        			{
				        			$("#chkShiftWiseDayEnd").prop('checked',true);
				        			}
				        			$("#txtNoOfDelDaysForUrgentOrder").val(response.intNoOfDelDaysForUrgentOrder);
				        			
				        			if(response.chkProductionLinkup=='Y')
				        			{
				        			$("#chkProductionLinkup").prop('checked',true);
				        			}
				        			
				        			if(response.chkSetUpToTimeForAdvOrder=='Y')
				        			{
				        			$("#chkSetUpToTimeForAdvOrder").prop('checked',true);
				        			}
				        			if(response.chkLockDataOnShift=='Y')
				        			{
				        			$("#chkLockDataOnShift").prop('checked',true);
				        			}
				        			
				        			$("#cmbHH").val(response.strHours);
				        			$("#cmbMM").val(response.strMinutes);
				        			$("#cmbAMPM").val(response.strAMPM);
				        			
				        			if(response.chkEnableBillSeries=='Y')
				        			{
				        			$("#chkEnableBillSeries").prop('checked',true);
				        			}
				        			if(response.chkSetUpToTimeForUrgentOrder=='Y')
				        			{
				        			$("#chkSetUpToTimeForUrgentOrder").prop('checked',true);
				        			}
				        			
				        			
				        			if(response.chkEnablePMSIntegration=='Y')
				        			{
				        			$("#chkEnablePMSIntegration").prop('checked',true);
				        			}
				        			$("#cmbUrgentOrderHH").val(response.strHoursUrgentOrder);
				        			$("#cmbToMM").val(response.strMinutesUrgentOrder);
				        			$("#cmbToAMPM").val(response.strAMPMUrgent);
				        			
				        			if(response.chkPrintTimeOnBill=='Y')
				        			{
				        			$("#chkPrintTimeOnBill").prop('checked',true);
				        			}
				        			if(response.chkCarryForwardFloatAmtToNextDay=='Y')
				        			{
				        			$("#chkCarryForwardFloatAmtToNextDay").prop('checked',true);
				        			}
				        			if(response.chkPrintRemarkAndReasonForReprint=='Y')
				        			{
				        			$("#chkPrintRemarkAndReasonForReprint").prop('checked',true);
				        			}
				        			
				        			if(response.chkShowItemDtlsForChangeCustomerOnBill=='Y')
				        			{
				        			$("#chkShowItemDtlsForChangeCustomerOnBill").prop('checked',true);
				        			}
				        			
				        			if(response.chkEnableBothPrintAndSettleBtnForDB=='Y')
				        			{
				        			$("#chkEnableBothPrintAndSettleBtnForDB").prop('checked',true);
				        			}
				        		
				        		if(response.chkShowPopUpForNextItemQuantity=='Y')
				        			{
				        			$("#chkShowPopUpForNextItemQuantity").prop('checked',true);
				        			}
				        		
				        		if(response.chkOpenCashDrawerAfterBillPrint=='Y')
				        			{
				        			$("#chkOpenCashDrawerAfterBillPrint").prop('checked',true);
				        			}
				        			if(response.chkPropertyWiseSalesOrder=='Y')
				        			{
				        			$("#chkPropertyWiseSalesOrder").prop('checked',true);
				        			}
				        			
				        			
				        			
				        			
				        			$("#txtSenderEmailId").val(response.strSenderEmailId);
				        			$("#txtEmailPassword").val(response.strEmailPassword);
				        			$("#txtEmailConfirmPassword").val(response.strEmailPassword);
				        			$("#cmbEmailServerName").val(response.strEmailServerName);
				        			$("#txtReceiverEmailId").val(response.strReceiverEmailId);
				        			$("#txtBodyPart").val(response.strBodyPart);
				        			
				        			$("#cmbCardIntfType").val(response.strCardIntfType);
				        			$("#cmbRFIDSetup").val(response.strRFIDSetup);
				        			$("#txtRFIDServerName").val(response.strRFIDServerName);
				        			$("#txtRFIDUserName").val(response.strRFIDUserName);
				        			$("#txtRFIDPassword").val(response.strRFIDPassword);
				        			$("#txtRFIDDatabaseName").val(response.strRFIDDatabaseName);
				        			
				        			$("#cmbCRM").val(response.strCRM);
				        			$("#txtGetWebservice").val(response.strGetWebservice);
				        			$("#txtPostWebservice").val(response.strPostWebservice);
				        			$("#txtOutletUID").val(response.strOutletUID);
				        			$("#txtPOSID").val(response.strPOSID);
				        			
				        			$("#cmbSMSType").val(response.strSMSType);
				        			$("#txtAreaSMSApi").val(response.strAreaSMSApi);
				        			if(response.chkHomeDelSMS=='Y')
				        			{
				        			$("#chkHomeDelSMS").prop('checked',true);
				        			}
				        			
				        			$("#txtAreaSendHomeDeliverySMS").val(response.strAreaSendHomeDeliverySMS);
				        			if(response.chkBillSettlementSMS=='Y')
				        			{
				        			$("#chkBillSettlementSMS").prop('checked',true);
				        			}
				        		
				        			$("#txtAreaBillSettlementSMS").val(response.strAreaBillSettlementSMS);
				        			
				        			$("#txtFTPAddress").val(response.strFTPAddress);
				        			$("#txtFTPServerUserName").val(response.strFTPServerUserName);
				        			$("#txtFTPServerPass").val(response.strFTPServerPass);
				        			
				        			$("#cmbCMSIntegrationYN").val(response.strCMSIntegrationYN);
				        			$("#txtCMSWesServiceURL").val(response.strCMSWesServiceURL);
				        			if(response.chkMemberAsTable=='Y')
				        			{
				        			$("#chkMemberAsTable").prop('checked',true);
				        			}
				        			if(response.chkMemberCodeForKOTJPOS=='Y')
				        			{
				        			$("#chkMemberCodeForKOTJPOS").prop('checked',true);
				        			}
				        			if(response.chkMemberCodeForKotInMposByCardSwipe=='Y')
				        			{
				        			$("#chkMemberCodeForKotInMposByCardSwipe").prop('checked',true);
				        			}
				        		if(response.chkMemberCodeForMakeBillInMPOS=='Y')
				        			{
				        			$("#chkMemberCodeForMakeBillInMPOS").prop('checked',true);
				        			}
				        			if(response.chkMemberCodeForKOTMPOS=='Y')
				        			{
				        			$("#chkMemberCodeForKOTMPOS").prop('checked',true);
				        			}
				        			if(response.chkSelectCustomerCodeFromCardSwipe=='Y')
				        			{
				        			$("#chkSelectCustomerCodeFromCardSwipe").prop('checked',true);
				        			}
				        			$("#cmbCMSPostingType").val(response.strCMSPostingType);
				        			$("#cmbPOSForDayEnd").val(response.strPOSForDayEnd);
				        			
				        			
				        			
				        				$("#cmbInrestoPOSIntegrationYN").val(response.strInrestoPOSIntegrationYN);
				        				$("#txtInrestoPOSWesServiceURL").val(response.strInrestoPOSWesServiceURL);
				        				$("#txtInrestoPOSId").val(response.strInrestoPOSId);
				        				$("#txtInrestoPOSKey").val(response.strInrestoPOSKey);
					
				        				$("#cmbJioPOSIntegrationYN").val(response.strJioPOSIntegrationYN);
				        				$("#txtJioPOSWesServiceURL").val(response.strJioPOSWesServiceURL);
				        				
				        				
				        				if(response.chkNewBillSeriesForNewDay=='Y')
					        			{
					        			$("#chkNewBillSeriesForNewDay").prop('checked',true);
					        			}
					        			if(response.chkShowReportsPOSWise=='Y')
					        			{
					        			$("#chkShowReportsPOSWise").prop('checked',true);
					        			}
					        			if(response.chkEnableDineIn=='Y')
					        			{
					        			$("#chkEnableDineIn").prop('checked',true);
					        			}
					        			if(response.chkAutoAreaSelectionInMakeKOT=='Y')
					        			{
					        			$("#chkAutoAreaSelectionInMakeKOT").prop('checked',true);
					        			}
					        			$("#txtJioMID").val(response.strJioMID);
					        			$("#txtJioTID").val(response.strJioTID);
					        			
					        			
					        			$("#txtJioActivationCode").val(response.strJioActivationCode);
					        			
					        			
					        				$("#txtJioDeviceID").val(response.strJioDeviceID);
					        				funLoadPrinterDtl();
				        				funSetSelectedBillSeries();
				        				
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
	
	function funLoadPrinterDtl()
	{
		funRemoveTableRows("tblPrinterDtl");
		
		var searchurl=getContextPath()+"/loadPrinterDtl.html";
		 $.ajax({
			        type: "GET",
			       
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	
			        	$.each(response.listPrinterDtl, function(i,item)
								{			
						    		
			        		funFillPrinterDtl(item.strCostCenterCode,item.strCostCenterName,item.strPrimaryPrinterPort,item.strSecondaryPrinterPort,item.strPrintOnBothPrintersYN);
						    		
						    		
							  	});
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
	
	function funFillPrinterDtl(strCode,strName,primaryPrinter,secondaryPrinter,strPrintOnBothPrintersYN)
	{
		var table = document.getElementById("tblPrinterDtl");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		
		 row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"listPrinterDtl["+(rowCount)+"].strCostCenterCode\" readonly=\"readonly\" class=\"Box \" size=\"0%\" id=\"strCodes."+(rowCount)+"\" value='"+strCode+"'>";
		
	      row.insertCell(1).innerHTML= "<input name=\"listPrinterDtl["+(rowCount)+"].strCostCenterName\" readonly=\"readonly\" class=\"Box \" size=\"40%\" id=\"strBillSeries."+(rowCount)+"\" value='"+strName+"'>";
		  row.insertCell(2).innerHTML= "<input name=\"listPrinterDtl["+(rowCount)+"].strPrimaryPrinterPort\" readonly=\"readonly\" class=\"Box \" size=\"20%\" id=\"strNames."+(rowCount)+"\" value='"+primaryPrinter+"'>";
		  row.insertCell(3).innerHTML= "<input name=\"listPrinterDtl["+(rowCount)+"].strSecondaryPrinterPort\" readonly=\"readonly\" class=\"Box \" size=\"20%\" id=\"strNames."+(rowCount)+"\" value='"+secondaryPrinter+"'>";
          		if(strPrintOnBothPrintersYN=="Y")
	        		{
		        	  row.insertCell(4).innerHTML= "<input type=\"checkbox\" name=\"listPrinterDtl["+(rowCount)+"].strPrintOnBothPrintersYN\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
          			}
          		else
          		{
          			row.insertCell(4).innerHTML= "<input type=\"checkbox\" name=\"listPrinterDtl["+(rowCount)+"].strPrintOnBothPrintersYN\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\"value='"+true+"'>";
        		}
	}
	
	
	function funSetSelectedBillSeries()
	{
		var posCode=$('#cmbPosCode').val();
		var searchurl=getContextPath()+"/loadOldSBillSeriesSetup.html?posCode="+posCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	
		if(response.strType=="")
			{
			$("#cmbSelectedType").val("Group");
			 funFillSelectedTypeDtlTable("Group");
			}
		else
			{
			$("#cmbSelectedType").val(response.strType);
			document.getElementById("cmbSelectedType").disabled=true;
			
			funLoadOldBillSeries();
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
	function funRemoveTableRows(tblId)
	{
		
		var table = document.getElementById(tblId);
		var rowCount = table.rows.length;
		
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	function funFillSelectedTypeDtlTable()
	{
		funRemoveTableRows("tblSelectedTypeDtl");
		var strType=$('#cmbSelectedType').val();
		var searchurl=getContextPath()+"/loadSelectedTypeDtlTable.html?strType="+strType;
		 $.ajax({
			        type: "GET",
			        
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	switch(strType)
			        	{
			        	case "Group":
			        	$.each(response, function(i,item)
								{			
						    		
			        		funFillSelectedTypeDtl(strType,item.strGroupCode,item.strGroupName);
						     	});
			        	break;
			        	case "Sub Group":
				        	$.each(response, function(i,item)
									{			
				        		funFillSelectedTypeDtl(strType,item.strSubGroupCode,item.strSubGroupName);		
								  	});
				        	break;
			        	case "Menu Head":
				        	$.each(response, function(i,item)
									{			
				        		funFillSelectedTypeDtl(strType,item.strMenuCode,item.strMenuName);	
								  	});
				        	break;
			        	case "Revenue Head":
				        	$.each(response, function(i,item)
									{			
				        		funFillSelectedTypeDtl(strType,item,item);	
								  	});
				        	break;
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
	
	function funFillSelectedTypeDtl(strType,strCode,strName)
	{
		var table = document.getElementById("tblSelectedTypeDtl");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

	      row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"txtSettlementCode."+(rowCount)+"\" value='"+strType+"'>";
		  row.insertCell(1).innerHTML= "<input name=\"strName\" readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"txtSettlementDesc."+(rowCount)+"\" value='"+strName+"'>";
		  row.insertCell(2).innerHTML= "<input name=\"strCode\" readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"txtSettlementDesc."+(rowCount)+"\" value='"+strCode+"'>";
		  row.insertCell(3).innerHTML= "<input type=\"checkbox\"  size=\"10%\" id=\"chkApplicable."+(rowCount)+"\" value='"+true+"'>";

	}
	function funLoadOldBillSeries()
	{
		var posCode=$('#cmbPosCode').val();
		var searchurl=getContextPath()+"/loadOldBillSeries.html?posCode="+posCode;
		 $.ajax({
			        type: "GET",
			       
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	
			        	$.each(response.listBillSeriesDtl, function(i,item)
								{			
						    		
			        		funLoadBillSeriesDataForUpdate(item.strCodes,item.strBillSeries,item.strNames,item.strPrintGTOfOtherBills,item.strPrintInclusiveOfTaxOnBill);
						    		
						    		
							  	});
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
	function btnFetchID_onclick()
	{
	
		var searchurl=getContextPath()+"/fetchDeviceID.html";
		 $.ajax({
			        type: "GET",
			       
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	
			        	$('#txtJioDeviceID').val(response.deviceID);
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
	function funAddSelectedTypeRow(codes,names)
	{
		
		
			   var itemCode=codes.split(",");
			   var itemName=names.split(",");
			   for(var i=0; i<itemCode.length;i++)
				  { 
				   var table = document.getElementById("tblSelectedTypeDtl");
					var rowCount = table.rows.length;
					var row = table.insertRow(rowCount);
					var strType=$('#cmbSelectedType').val();
				     
				
				   row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"txtSettlementCode."+(rowCount)+"\" value='"+strType+"'>";
					  row.insertCell(1).innerHTML= "<input name=\"strName\" readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"txtSettlementDesc."+(rowCount)+"\" value='"+itemName[i]+"'>";
					  row.insertCell(2).innerHTML= "<input name=\"strCode\" readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"txtSettlementDesc."+(rowCount)+"\" value='"+itemCode[i]+"'>";
					  row.insertCell(3).innerHTML= "<input type=\"checkbox\"  size=\"10%\" id=\"chkApplicable."+(rowCount)+"\" value='"+true+"'>";
					
	       		  }
			   
			
	}  
	function funAddBillseriesDtlRow(codes,names)
	{
		
		var table = document.getElementById("tblBillseriesDtl");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var cnt=(parseInt(rowCount))+1;
		 row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"listBillSeriesDtl["+(rowCount)+"].strCodes\" readonly=\"readonly\" class=\"Box \" size=\"0%\" id=\"strCodes."+(rowCount)+"\" value='"+codes+"'>";
		 row.insertCell(1).innerHTML= "<input name=\"serialNo\" readonly=\"readonly\" class=\"Box \" size=\"10%\" value='"+cnt+"'>";
	      row.insertCell(2).innerHTML= "<input type=\"text\" name=\"listBillSeriesDtl["+(rowCount)+"].strBillSeries\"  class=\"Box \" size=\"10%\" id=\"strBillSeries."+(rowCount)+"\" >";
		  row.insertCell(3).innerHTML= "<input name=\"listBillSeriesDtl["+(rowCount)+"].strNames\" readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"strNames."+(rowCount)+"\" value='"+names+"'>";
		  row.insertCell(4).innerHTML= "<input type=\"checkbox\"  size=\"10%\" id=\"chkRemove\" value='"+true+"'>";
			row.insertCell(5).innerHTML= "<input type=\"checkbox\" name=\"listBillSeriesDtl["+(rowCount)+"].strPrintGTOfOtherBills\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\"value='"+true+"'>";
			row.insertCell(6).innerHTML= "<input type=\"checkbox\" name=\"listBillSeriesDtl["+(rowCount)+"].strPrintInclusiveOfTaxOnBill\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\"value='"+true+"'>";
	
			   
			    rowCount = table.rows.length;
			   if(rowCount>0)
				   document.getElementById("cmbSelectedType").disabled=true;
	}
	function funLoadBillSeriesDataForUpdate(codes,billSeries,names,strPrintGTOfOtherBills,strPrintInclusiveOfTaxOnBill)
	{
		
		var table = document.getElementById("tblBillseriesDtl");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var cnt=(parseInt(rowCount))+1;
		 row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"listBillSeriesDtl["+(rowCount)+"].strCodes\" readonly=\"readonly\" class=\"Box \" size=\"0%\" id=\"strCodes."+(rowCount)+"\" value='"+codes+"'>";
		 row.insertCell(1).innerHTML= "<input name=\"serialNo\" readonly=\"readonly\" class=\"Box \" size=\"10%\" value='"+cnt+"'>";
	      row.insertCell(2).innerHTML= "<input name=\"listBillSeriesDtl["+(rowCount)+"].strBillSeries\" readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"strBillSeries."+(rowCount)+"\" value='"+billSeries+"'>";
		  row.insertCell(3).innerHTML= "<input name=\"listBillSeriesDtl["+(rowCount)+"].strNames\" readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"strNames."+(rowCount)+"\" value='"+names+"'>";
		  row.insertCell(4).innerHTML= "<input type=\"checkbox\"  size=\"10%\" id=\"chkRemove\" value='"+true+"'>";
          		if(strPrintGTOfOtherBills=="Y")
	        		{
		        	  row.insertCell(5).innerHTML= "<input type=\"checkbox\" name=\"listBillSeriesDtl["+(rowCount)+"].strPrintGTOfOtherBills\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
          			}
          		else
          		{
          			row.insertCell(5).innerHTML= "<input type=\"checkbox\" name=\"listBillSeriesDtl["+(rowCount)+"].strPrintGTOfOtherBills\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\"value='"+true+"'>";
        		}

          		if(strPrintInclusiveOfTaxOnBill=="Y")
	        		{
					 row.insertCell(6).innerHTML= "<input type=\"checkbox\" name=\"listBillSeriesDtl["+(rowCount)+"].strPrintInclusiveOfTaxOnBill\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\" checked=\"checked\">";
          			}
          		else
          		{
          			row.insertCell(6).innerHTML= "<input type=\"checkbox\" name=\"listBillSeriesDtl["+(rowCount)+"].strPrintInclusiveOfTaxOnBill\" size=\"20%\" id=\"chkApplicable."+(rowCount)+"\"value='"+true+"'>";
        		}
		
	}
	
	function btnAddDelSMS_onclick()
	{
		var firstvalue="";
		  if($("#txtAreaSendHomeDeliverySMS").val().trim()=="")
			{
			  firstvalue="%%"+ $("#cmbSendHomeDelivery").val();
			  $("#txtAreaSendHomeDeliverySMS").val(firstvalue);
			}
		  else
			  {
			  firstvalue="%%"+ $("#cmbSendHomeDelivery").val();
			  var getValue=  $("#txtAreaSendHomeDeliverySMS").val();
			  $("#txtAreaSendHomeDeliverySMS").val(getValue+firstvalue);
			  }
	}
	

	function btnAddSettleSMS_onclick()
	{
		var firstvalue="";
		  if($("#txtAreaBillSettlementSMS").val().trim()=="")
			{
			  firstvalue="%%"+ $("#cmbBillSettlement").val();
			  $("#txtAreaBillSettlementSMS").val(firstvalue);
			}
		  else
			  {
			  firstvalue="%%"+ $("#cmbBillSettlement").val();
			  var getValue=  $("#txtAreaBillSettlementSMS").val();
			  $("#txtAreaBillSettlementSMS").val(getValue+firstvalue);
			  }
	}
	
	function funShowImagePreview(input)
	 {
		 
		 if (input.files && input.files[0])
		 {
			 var filerdr = new FileReader();
			 filerdr.onload = function(e) 
			 {
			 $('#clientImage').attr('src', e.target.result);
			 }
			 filerdr.readAsDataURL(input.files[0]);
			
		 }
	 }
</script>
</head>

<body onload="funFillPOSData()">
	<div id="formHeading">
		<label>Property Setup</label>
	</div>
	<s:form name="POSForm" method="POST" action="savePOSPropertySetup.html" enctype="multipart/form-data">

		<br />
		<br />
			<table class="masterTable"style="width:95%;">

			<tr>
				<td >
				<label>POS Name</label>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:select id="cmbPosCode" name="cmbPosCode" path="strPosCode" cssClass="BoxW124px" items="${posList}" >
				</s:select></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			</table>
		<table
				style="border: 0px solid black; width: 95%; height: 130%;  margin-left: auto; margin-right: auto;background-color:#C0E4FF;">
				
				<tr>
					<td>
						<div id="tab_container" >
						<div style="float:left;">
							<ul class="tab">
								<li class="active" data-state="tab1">Property Setup</li>
								<li data-state="tab2">Bill Setup</li>
								<li data-state="tab3">POS Setup 1</li>
								<li data-state="tab4">POS Setup 2</li>
								<li data-state="tab5">POS Setup 3</li>
								<li data-state="tab6">Email Setup</li>
								<li data-state="tab7">Card Interface</li>
								<li data-state="tab8">CRM Interface</li>
								<li data-state="tab9">SMS Setup</li>
								<li data-state="tab10">FTP Setup</li>
								<li data-state="tab11">CMS Integration</li>
								<li data-state="tab12">Printer Setup</li>
								<li data-state="tab13">Debit Card Setup</li>
								<li data-state="tab14">Bill Series Setup</li>
								<li data-state="tab15">Inresto Integration</li>
								<li data-state="tab16">Jio Integration</li>
				
							</ul>
						</div>

							<!--  Start of Property Setup tab-->

						<div id="tab1" class="tab_content">
						<br><br>
						<div style="float:left;">
								<table  class="masterTable" style="width:500px;">
																		
									<tr>
				<td>Client Code & Name</td>
				<td><s:input id="txtClientCode" path="strClientCode"
						cssClass="searchTextBox" readonly="true" ondblclick="funHelp('POSTaxMaster.a')" />
					<s:input colspan="" type="text" id="txtClientName" 
						name="txtClientName" path="strClientName"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  />	
		       </td>
		       </tr>
			<tr>
			<td><label>Address Line 1</label></td>
				<td><s:input colspan="" type="text" id="txtAddrLine1" 
						path="strAddrLine1" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
						</tr>
						<tr>
					<td><label>Address Line 2</label></td>
				<td><s:input colspan="" type="text" id="txtAddrLine2" 
						path="strAddrLine2" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
				</tr>
					<tr>
					<td><label>Address Line 3</label></td>
				<td><s:input colspan="" type="text" id="txtAddrLine3" 
						path="strAddrLine3" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
					
			</tr>
		
			<tr>
			<td><label>City &amp; PIN Code</label></td>
				<td><s:select id="cmbCity" path="strCity" cssClass="BoxW124px" >
				 <option value="Pune">Pune</option><option value="Agalgaon">Agalgaon</option> <option value="Agartala">Agartala</option>
				 <option value="Agra">Agra</option> <option value="Ahmedabad">Ahmedabad</option><option value="Ahmednagar">Ahmednagar</option>
				 <option value="Ajmer">Ajmer</option> <option value="Akluj">Akluj</option> <option value="Akola">Akola</option><option value="Akot">Akot</option>
				 <option value="Allahabad">Allahabad</option><option value="Allepey">Allepey</option> <option value="Amalner">Amalner</option> <option value="Ambernath">Ambernath</option>
				 <option value="Amravati">Amravati</option> <option value="Amritsar">Amritsar</option><option value="Anand">Anand</option>
				 <option value="Arvi">Arvi</option> <option value="Asansol">Asansol</option> <option value="Ashta">Ashta</option>
				 <option value="Aurangabad">Aurangabad</option> <option value="Aziwal">Aziwal</option><option value="Baddi">Baddi</option><option value="Bangalore">Bangalore</option>
				 <option value="Bansarola">Bansarola</option> <option value="Baramati">Baramati</option> <option value="Bareilly">Bareilly</option><option value="Baroda">Baroda</option>
				 <option value="Barshi">Barshi</option> <option value="Beed">Beed</option> <option value="Belgum">Belgum</option><option value="Bellary">Bellary</option> <option value="Bhandara">Bhandara</option><option value="Bhilai">Bhilai</option>
				 <option value="Bhivandi">Bhivandi</option><option value="Bhopal">Bhopal</option> <option value="Bhubaneshwar">Bhubaneshwar</option> <option value="Bhusawal">Bhusawal</option><option value="Bikaner">Bikaner</option> <option value="Bokaro">Bokaro</option>
				 <option value="Bombay">Bombay</option> <option value="Buldhana">Buldhana</option>	<option value="Burhanpur">Burhanpur</option> <option value="Chandigarh">Chandigarh</option><option value="Chattisgad">Chattisgad</option> <option value="Chennai(Madras)">Chennai(Madras)</option>
				 <option value="Cochin">Cochin</option><option value="Coimbature">Coimbature</option> <option value="Dehradun">Dehradun</option><option value="Delhi">Delhi</option>  <option value="Dhanbad">Dhanbad</option> <option value="Dhule">Dhule</option>
				 <option value="Faridabad">Faridabad</option> <option value="Goa">Goa</option> <option value="Gujrat">Gujrat</option><option value="Gurgaon">Gurgaon</option>  <option value="Guwahati">Guwahati</option><option value="Gwalior">Gwalior</option>
				 <option value="Hyderabad">Hyderabad</option>  <option value="Ichalkaranji">Ichalkaranji</option>  <option value="Indapur">Indapur</option>  <option value="Indore">Indore</option><option value="Jabalpur">Jabalpur</option> <option value="Jaipur">Jaipur</option>
				<option value="Jalandhar">Jalandhar</option><option value="Jalgaon">Jalgaon</option>  <option value="Jalna">Jalna</option><option value="Jamshedpur">Jamshedpur</option> <option value="Kalamnuri">Kalamnuri</option> <option value="Kanpur">Kanpur</option>
				<option value="Karad">Karad</option> <option value="Kochi(Cochin)">Kochi(Cochin)</option><option value="Kolhapur">Kolhapur</option><option value="Kolkata(Calcutta)">Kolkata(Calcutta)</option><option value="Kozhikode(Calicut)">Kozhikode(Calicut)</option>   <option value="Latur">Latur</option>
			 <option value="Lucknow">Lucknow</option>    <option value="Ludhiana">Ludhiana</option><option value="Mumbai">Mumbai</option> <option value="Madurai">Madurai</option><option value="Mangalvedha">Mangalvedha</option>  <option value="Manmad">Manmad</option>
			<option value="Meerut">Meerut</option> <option value="Mumbai(Bombay)">Mumbai(Bombay)</option>
			<option value="Mysore">Mysore</option>   <option value="Nagpur">Nagpur</option><option value="Nanded">Nanded</option>
			<option value="Nandurbar">Nandurbar</option> <option value="Nashik">Nashik</option><option value="Orisa">Orisa</option><option value="Osmanabad">Osmanabad</option>
			 <option value="Pachora">Pachora</option><option value="Pandharpur">Pandharpur</option><option value="Parbhani">Parbhani</option><option value="Patna">Patna</option>
		<option value="Pratapgad">Pratapgad</option><option value="Raipur">Raipur</option> <option value="Rajasthan">Rajasthan</option><option value="Rajkot">Rajkot</option>
		<option value="Ranchi">Ranchi</option><option value="Ratnagiri">Ratnagiri</option><option value="Salem">Salem</option>
				  <option value="Sangamner">Sangamner</option><option value="Sangli">Sangli</option> <option value="Satara">Satara</option>
				 <option value="Sawantwadi">Sawantwadi</option><option value="Secunderabad">Secunderabad</option> <option value="Shirdi">Shirdi</option>
				 <option value="Sindhudurga">Sindhudurga</option><option value="Solapur">Solapur</option> <option value="Srinagar">Srinagar</option>
				 <option value="Surat">Surat</option><option value="Tiruchirapalli">Tiruchirapalli</option><option value="Vadodara(Baroda)">Vadodara(Baroda)</option>
				<option value="Varanasi(Benares)">Varanasi(Benares)</option><option value="Vijayawada">Vijayawada</option><option value="Visakhapatnam">Visakhapatnam</option><option value="Yawatmal">Yawatmal</option>
				 <option value="Other">Other</option>
				 </s:select> &nbsp;&nbsp;
				<s:input colspan="" type="text" id="txtPinCode" 
						 path="strPinCode" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /></td>
				
			</tr>
			<tr>
			<td><label>State And Country</label></td>
				
				<td>  
		       <s:select id="cmbState" path="strState" cssClass="BoxW124px" >
				 <option value="Maharashtra">Maharashtra</option><option value="Delhi">Delhi</option> <option value="Karnataka">Karnataka</option>
				 <option value="Telangana">Telangana</option> <option value="TamilNadu">TamilNadu</option><option value="Gujrat">Gujrat</option>
				 <option value="Punjab">Punjab</option> <option value="Rajasthan">Rajasthan</option>
				  </s:select> &nbsp;&nbsp;
				 <s:select id="cmbCountry" path="strCountry" cssClass="BoxW124px" >
				 <option value="India">India</option><option value="China">China</option> <option value="USA">USA</option>
				 <option value="England">England</option> </s:select>
		       </td>
			</tr>
			
			<tr>
			<td><label>Telephone</label></td>
				  <td><s:input colspan="" type="text" id="txtTelephone" 
						path="strTelephone" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		       </tr>
		   
		       <tr>
			<td><label>Email Address</label></td>
				  <td><s:input colspan="" type="text" id="txtEmail" 
					 path="strEmail" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		       </tr>
		       <tr>
		       <td><label>Nature Of Bussness</label></td>
				<td>
					<s:select id="cmbNatureOfBussness" name="cmbNatureOfBussness" path="strNatureOfBussness" cssClass="BoxW124px" >
				<option value="Retail">Retail</option>
				 <option value="F&B">F &amp; B</option>
 				 </s:select> 	
		       </td>
		      </tr>
		      </table>
		      </div>
		      <table  class="masterTable" style="width:90px;">
				<tr style="width:90px;">
				<td>
		      <img id="clientImage" src=""  width="100px" height="100px" alt="Client Image" />
		      </td>
		      </tr>
		      <tr>
		      <td>
		      <div> <input style="width:80px;" id="companyLogo" name="companyLogo"  type="file" accept="image/png" onchange="funShowImagePreview(this);" /></div>
		      </td></tr>
		      </table>
		      </div>
							<!--  End of  Property Setup tab-->


						<!-- Start of Bill Setup tab -->

							<div id="tab2" class="tab_content">
							<br><br>
							<table  class="masterTable">
																		
				<tr>
				<td><label>Bill Footer</label></td>
			
				  <td colspan="5"><s:input  type="text" id="txtBillFooter" 
						name="txtBillFooter" path="strBillFooter" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		       </tr>
		       <tr>
				<td><label>Bill Paper Size</label></td>
				<td><s:select id="cmbBillPaperSize" path="intBiilPaperSize" cssClass="BoxW124px" >
				
				 <option value=2>2</option>
 				 <option value=3>3</option>
				 </s:select>
		       </td>
		       
		       <td><label>Print Mode</label></td>
				<td><s:select id="cmbPrintMode"  path="strBillPrintMode" cssClass="BoxW124px" >
				
				 <option value="Portrait">Portrait</option>
 				 <option value="Landscape">Landscape</option>
				 </s:select>
		       </td>
		       
		        <td><label>Column Size</label></td>
				<td><s:select id="cmbColumnSize" path="intColumnSize" cssClass="BoxW124px" >
				
				 <option value=40>40</option>
 				 <option value=48>48</option>
				 </s:select>
		       </td>
		       </tr>
		       
		        
		       <tr>
				<td><label>Doc Printing Type</label></td>
				<td><s:select id="cmbPrintType"  path="strPrintingType" cssClass="BoxW124px" >
				
				 <option value="Jasper">Jasper</option>
 				 <option value="Text File">Text File</option>
				 </s:select>
		       </td>
		       
		       <td><label>Bill Format</label></td>
				<td><s:select id="cmbBillFormatType" name="cmbBillFormatType" path="strBillFormat" cssClass="BoxW124px" >
				
				 <option value="Format 1">Format 1</option>
 				 <option value="Format 2">Format 2</option>
 				 <option value="Format 3">Format 3</option>
 				 <option value="Format 4">Format 4</option>
 				 <option value="Format 5">Format 5</option>
 				 <option value="Format 6">Format 6</option>
 				 <option value="Format 7">Format 7</option>
 				 <option value="Format 8">Format 8</option>
 				 <option value="Format 9">Format 9</option>
 				 <option value="Format 10">Format 10</option>
 				 <option value="Format 11">Format 11</option>
 				 <option value="Format 12">Format 12</option>
 				 <option value="Format 13">Format 13</option>
 				 <option value="Format 14">Format 14</option>
				 </s:select>
		       </td>
		       
		        <td><label>Show Docs</label></td>
				<td>
						
						<s:checkbox element="li" id="chkShowBills" 
						path="chkShowBills" value="Yes" />
						
		       </td>
		       </tr>
			<tr>
			 <td><label>Allow Negative Billing </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkNegBilling" path="chkNegBilling" value="Yes" />
		       </td>
				
				 <td><label>Enable KOT Printing For Direct Biller</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintKotForDirectBiller" path="chkPrintKotForDirectBiller" value="Yes" />
		       </td>
		        <td><label>Enable KOT Printing</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkEnableKOT" path="chkEnableKOT" value="Yes" />
		       </td>		
				
			</tr>
		
			<tr>
			<td><label>Multiple Bill Printing </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkMultiBillPrint" path="chkMultiBillPrint" value="Yes"/>
		       </td>
			<td><label>Day End (Mandatory) </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkDayEnd" path="chkDayEnd" value="Yes" />
		       </td>
		       <td><label>Print Short Name On KOT </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintShortNameOnKOT" path="chkPrintShortNameOnKOT" value="Yes"  />
		       </td>
			</tr>
			
			<tr>
			<td><label>Multiple KOT Printing </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkMultiKOTPrint" path="chkMultiKOTPrint" value="Yes"/>
		       </td>
			<td><label>Print TAX Invoice On Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintInvoiceOnBill" path="chkPrintInvoiceOnBill"  value="Yes"/>
		       </td>
		       <td><label>Print TDH Items In Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintTDHItemsInBill" path="chkPrintTDHItemsInBill" value="Yes"  />
		       </td>
			</tr>
			
			<tr>
			<td><label>Manual Bill No. </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkManualBillNo" path="chkManualBillNo" value="Yes" />
		       </td>
			<td><label>Print Inclusive Of All Taxes On Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintInclusiveOfAllTaxesOnBill" path="chkPrintInclusiveOfAllTaxesOnBill"  value="Yes"/>
		       </td>
		       <td></td><td></td>
			</tr>
			
			<tr>
			<td><label>Effect On PSP </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkEffectOnPSP" path="chkEffectOnPSP" value="Yes"/>
		       </td>
			<td><label>Print VAT No.</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintVatNo" path="chkPrintVatNo" value="Yes" />
		       </td>
		        <td colspan="2"><s:input  type="text" id="txtVatNo" 
						name="txtVatNo" path="strVatNo" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			<tr>
			<td><label>No Of Lines In KOT Print</label></td>
			<td ><s:input  type="text" id="txtNoOfLinesInKOTPrint" 
						path="intNoOfLinesInKOTPrint" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		       <td><label>Print Service Tax No</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkServiceTaxNo" path="chkServiceTaxNo" value="Yes" />
		       </td>
		        <td colspan="2"><s:input  type="text" id="txtServiceTaxNo" 
						 path="strServiceTaxNo" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			<tr>
			<td><label>Show Bills Detail Type</label></td>
			<td><s:select id="cmbShowBillsDtlType" name="cmbShowBillsDtlType" path="strShowBillsDtlType" cssClass="BoxW124px" >
				
				 <option value="Table Detail Wise">Table Detail Wise</option>
 				 <option value="Delivery Detail Wise">Delivery Detail Wise</option>
				 </s:select>
		       </td>
		     <td colspan="2"><label>No Of Advance Receipt Print  </label></td>
		         <td colspan="2"><s:input  type="text" id="txtAdvRecPrintCount" 
						 path="strAdvRecPrintCount" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
			</tr>
			</table>
			</div>
							<!-- End of Bill Setup tab -->




							<!-- Start of POS Setup 1 tab -->

							<div id="tab3" class="tab_content">
							<br><br>
							<table  class="masterTable">
																		
				
		       <tr>
				<td><label>Property Type</label></td>
				<td colspan="2"><s:select id="cmbPOSType"  path="strPOSType" cssClass="BoxW124px" >
				
				 <option value="Stand Alone-HOPOS">Stand Alone-HOPOS</option>
 				 <option value="Stand Alone">Stand Alone</option>
 				  <option value="HOPOS">HOPOS</option>
 				 <option value="Client POS">Client POS</option>
 				   <option value="DebitCard POS">DebitCard POS</option>
 				
				 </s:select>
		       </td>
		       
		       <td><label>Data Posting Frequency</label></td>
				<td colspan="2"><s:select id="cmbDataSendFrequency"  path="strDataSendFrequency" cssClass="BoxW124px" >
				
				 <option value="After Every Bill">After Every Bill</option>
 				 <option value="After Day End">After Day End</option>
 				  <option value="Manual">Manual</option>
				 </s:select>
		       </td>
		    
		       </tr>
		       
		        <tr>
				<td><label>Web Service Link</label></td>
			
				  <td colspan="5"><s:input  type="text" id="txtWebServiceLink" 
						 path="strWebServiceLink" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		       </tr>
		       
		       <tr>
				<td><label>HO Server Date</label></td>
			<td colspan="3" ><s:input id="dteHOServerDate" path="dteHOServerDate"  cssClass="calenderTextBox" /></td>
		       
		       <td><label>Max. discount</label></td>
				 <td ><s:input  type="text" id="txtMaxDiscount" 
						 path="dblMaxDiscount"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		      
		       </tr>
		       <tr>
				<td><label>Change Theme</label></td>
				<td colspan="2"><s:select id="cmbChangeTheme"  path="strChangeTheme" cssClass="BoxW124px" >
				
				 <option value="Default">Default</option>
 				 <option value="Tiles">Tiles</option>
 				  <option value="Color">Color</option>
 				 
				 </s:select>
		       </td>
		       
		       <td><label>Select Area For Direct Biller</label></td>
				<td colspan="2"><s:select id="cmbDirectArea" path="strDirectArea" items="${areaList}" cssClass="BoxW124px" />
				</td>
		    
		       </tr>
			<tr>
			 <td><label>Area Wise Pricing  </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkAreaWisePricing" path="chkAreaWisePricing" value="Yes" />
		       </td>
				 <td><label>Customer Series</label></td>
				 <td ><s:input  type="text" id="txtCustSeries" 
						 path="strCustSeries" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>
		        <td><label>Slab Based Home Delivery Charges</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkSlabBasedHomeDelCharges" path="chkSlabBasedHomeDelCharges" value="Yes" />
		       </td>		
				
			</tr>
		
			<tr>
			<td><label>Edit Home Delivery </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkEditHomeDelivery" path="chkEditHomeDelivery" value="Yes"/>
		       </td>
			<td><label>Print For Void Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintForVoidBill" path="chkPrintForVoidBill" value="Yes" />
		       </td>
		       <td><label>Direct KOT Print from Make KOT</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkDirectKOTPrintMakeKOT" path="chkDirectKOTPrintMakeKOT" value="Yes"  />
		       </td>
			</tr>
			
			<tr>
			<td><label>Skip Waiter Selection </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkSkipPaxSelection" path="chkSkipPaxSelection" value="Yes" />
		       </td>
			<td><label>Skip Pax Selection </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintInvoiceOnBill" path="chkPrintInvoiceOnBill" value="Yes" />
		       </td>
		       <td><label>Compulsory Customer Area Master</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkAreaMasterCompulsory" path="chkAreaMasterCompulsory" value="Yes"  />
		       </td>
			</tr>
			
			<tr>
			<td><label>Post Sales Data to MMS  </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPostSalesDataToMMS" path="chkPostSalesDataToMMS" value="Yes"/>
		       </td>
		    
				<td colspan="2"><s:select id="cmbItemType"  path="strItemType" cssClass="BoxW124px" >
				
				 <option value="Both">Both</option>
 				 <option value="Liquor">Liquor</option>
 				  <option value="Food">Food</option>
 				 
				 </s:select>
		       </td>
			<td><label>Show Printer Error Message</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrinterErrorMessage" path="chkPrinterErrorMessage"  value="Yes"/>
		       </td>
		     
			</tr>
			
			<tr>
			<td><label>Active Promotions </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkActivePromotions" path="chkActivePromotions" value="Yes"/>
		       </td>
			<td><label>Print KOT</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintKOTYN" path="chkPrintKOTYN" value="Yes" />
		       </td>
		      <td><label>Change Quantity For External Code</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkChangeQtyForExternalCode" path="chkChangeQtyForExternalCode" value="Yes" />
		       </td>
			</tr>
			<tr>
			<td><label>Stock In Options</label></td>
			
				<td colspan="3"><s:select id="cmbStockInOption"  path="strStockInOption" cssClass="BoxW124px" >
				
				 <option value="ItemWise">ItemWise</option>
 				 <option value="MenuHeadWise">MenuHeadWise</option>
 				 
				 </s:select>
		       </td>
		       <td><label>Show Item StK Column in DB</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkShowItemStkColumnInDB" path="chkShowItemStkColumnInDB" value="Yes" />
		       </td>
		      
			</tr>
			<tr>
			<td><label>Pick Up Price From</label></td>
			<td colspan="3"><s:select id="cmbPriceFrom"  path="strPriceFrom" cssClass="BoxW124px" >
				
				 <option value="Menu Pricing">Menu Pricing</option>
 				 <option value="Item Master">Item Master</option>
				 </s:select>
		       </td>
		      <td><label>Ask For Print Bill  Popup</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintBill" path="chkPrintBill" value="Yes" />
		       </td>
			</tr>
			<tr>
			<td><label>Discount On</label></td>
			<td colspan="3"><s:select id="cmbApplyDiscountOn"  path="strApplyDiscountOn" cssClass="BoxW124px" >
				
				 <option value="SubTotal">SubTotal</option>
 				 <option value="SubTotalTax">SubTotalTax</option>
				 </s:select>
		       </td>
		      <td><label>Use Vat And Service No From POS</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkUseVatAndServiceNoFromPos" path="chkUseVatAndServiceNoFromPos" value="Yes" />
		       </td>
			</tr>
			</table>
			</div>
							<!-- End of POS Setup 1 tab -->
							
							<!-- Start of POS Setup 2 tab -->

							<div id="tab4" class="tab_content">
							<br><br>
							<table  class="masterTable">
				
				<tr>
			 <td><label>Manual Advance Order No Compulsory </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkManualAdvOrderCompulsory" path="chkManualAdvOrderCompulsory" value="Yes" />
		       </td>
				
		        <td><label>Print Manual Advance Order No On Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintManualAdvOrderOnBill" path="chkPrintManualAdvOrderOnBill" value="Yes" />
		       </td>		
				
			</tr>														
				
				<tr>
			 <td><label>Print Modifier Quantity On KOT </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkPrintModifierQtyOnKOT" path="chkPrintModifierQtyOnKOT" value="Yes" />
		       </td>
				
		        <td><label>Allow New Area Master From Customer Master </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkBoxAllowNewAreaMasterFromCustMaster" path="chkBoxAllowNewAreaMasterFromCustMaster" value="Yes" />
		       </td>		
				
			</tr>	
		       <tr>
				<td><label>Menu Item Sorting</label></td>
				<td colspan="2"><s:select id="cmbMenuItemSortingOn"  path="strMenuItemSortingOn" cssClass="BoxW124px" >
				
				 <option value="SELECT">SELECT</option>
 				 <option value="subGroupWise">SUB GROUP WISE</option>
 				  <option value="subMenuHeadWise">SUB MENU HEAD WISE</option>
 				
				 </s:select>
		       </td>
		       
		       <td><label>Allow To Calculate Item Weight</label></td>
					<td> 
						<s:checkbox element="li" id="chkAllowToCalculateItemWeight" path="chkAllowToCalculateItemWeight" value="Yes" />
		       </td>
		    
		       </tr>
		       
		      
		       <tr>
				<td><label>Menu Item Display Sequence</label></td>
			<td colspan="2" ><s:select id="cmbMenuItemDisSeq"  path="strMenuItemDisSeq" cssClass="BoxW124px" >
				
				 <option value="Ascending">Ascending</option>
 				 <option value="As Entered">As Entered</option>
 				
				 </s:select>
		      
			 <td><label>Item Wise KOT Y/N : </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkItemWiseKOTPrintYN" path="chkItemWiseKOTPrintYN" value="Yes" />
		       </td>
		       </tr>
		      <tr>
			 <td><label>Item Quantiy Numeric Pad </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkItemQtyNumpad" path="chkItemQtyNumpad" value="Yes" />
		       </td>
				
		        <td><label>Slip No Compulsory For Credit Card Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkSlipNoForCreditCardBillYN" path="chkSlipNoForCreditCardBillYN" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Print KOT To Local Printer </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkPrintKOTToLocalPrinter" path="chkPrintKOTToLocalPrinter" value="Yes" />
		       </td>
				
		        <td><label>Expiry date Compulsory For Credit Card Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkExpDateForCreditCardBillYN" path="chkExpDateForCreditCardBillYN" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Delivery Boy Compulsory On Direct Biller </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkDelBoyCompulsoryOnDirectBiller" path="chkDelBoyCompulsoryOnDirectBiller" value="Yes" />
		       </td>
				
		        <td><label>Select Waiter From Card Swipe</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkSelectWaiterFromCardSwipe" path="chkSelectWaiterFromCardSwipe" value="Yes" />
		       </td>		
				
			</tr>	
			
			<tr>
			 <td><label>Enable Settle Button For Direct Biller Bill</label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkEnableSettleBtnForDirectBillerBill" path="chkEnableSettleBtnForDirectBillerBill" value="Yes" />
		       </td>
				
		        <td><label>Multiple Waiter Selection On Make KOT</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkMultipleWaiterSelectionOnMakeKOT" path="chkMultipleWaiterSelectionOnMakeKOT" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Don't Show Advance Order In Other POS </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkDontShowAdvOrderInOtherPOS" path="chkDontShowAdvOrderInOtherPOS" value="Yes" />
		       </td>
				
		        <td><label>Move Table From One POS To Other POS</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkMoveTableToOtherPOS" path="chkMoveTableToOtherPOS" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Print Zero Amount Modifiers In Bill </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkPrintZeroAmtModifierInBill" path="chkPrintZeroAmtModifierInBill" value="Yes" />
		       </td>
				
		        <td><label>Move KOT From One POS To Other POS</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkMoveKOTToOtherPOS" path="chkMoveKOTToOtherPOS" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Points On Bill Print </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkPointsOnBillPrint" path="chkPointsOnBillPrint" value="Yes" />
		       </td>
				
		        <td><label>Calculate Tax on Make KOT</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkCalculateTaxOnMakeKOT" path="chkCalculateTaxOnMakeKOT" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			
				
		        <td><label>Calculate Discount Item Wise </label></td>
				
				<td colspan="2"> 
						<s:checkbox element="li" id="chkCalculateDiscItemWise" path="chkCalculateDiscItemWise" value="Yes" />
		       </td>		
				 <td><label>Take Away Customer Selection </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkTakewayCustomerSelection" path="chkTakewayCustomerSelection" value="Yes" />
		       </td>
			</tr>	
			<tr>
			
				
		        <td><label>Customer Address Selection For Billing</label></td>
				
				 <td colspan="2">  
						<s:checkbox element="li" id="chkSelectCustAddressForBill" path="chkSelectCustAddressForBill" value="Yes" />
		       </td>		
				
				 <td><label>Generate MI With DayEnd</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkGenrateMI" path="chkGenrateMI" value="Yes" />
		       </td>
			</tr>	
			
			</table>
			</div>
							<!-- End of POS Setup 2 tab -->
					
						<!-- Start of POS Setup 3 tab -->

							<div id="tab5" class="tab_content">
							<br><br>
							<table  class="masterTable">
				
				<tr>
			 <td><label>Pop Up to Apply Promotions on Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPopUpToApplyPromotionsOnBill" path="chkPopUpToApplyPromotionsOnBill" value="Yes" />
		       </td>
				
		        <td><label>WebStock /HO Client Code</label></td>
				
				 <td><s:input type="text" id="txtWSClientCode" 
						name="txtWSClientCode" path="strWSClientCode" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>														
				<tr>
			 <td><label>Check Debit Card Bal on Transactions</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkCheckDebitCardBalOnTrans" path="chkCheckDebitCardBalOnTrans" value="Yes" />
		       </td>
				
		        <td><label>Days Before Order Can Be Cancelled</label></td>
				
				 <td><s:input type="text" id="txtDaysBeforeOrderToCancel" 
						path="intDaysBeforeOrderToCancel" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>														
				<tr>
			 <td><label>Pick Settlements from POS Master </label></td>
				
				<td> 
						<s:checkbox element="li" id="chkSettlementsFromPOSMaster" path="chkSettlementsFromPOSMaster" value="Yes" />
		       </td>
				
		        <td><label>Dont allow Adv Order for next how many days</label></td>
				
				 <td><s:input type="text" id="txtNoOfDelDaysForAdvOrder" 
						 path="intNoOfDelDaysForAdvOrder" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>														
				<tr>
			 <td><label>Enable Shift</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkShiftWiseDayEnd" path="chkShiftWiseDayEnd" value="Yes" />
		       </td>
				
		        <td><label>Allow Urgent Order for next how many days</label></td>
				
				 <td><s:input type="text" id="txtNoOfDelDaysForUrgentOrder" 
						 path="intNoOfDelDaysForUrgentOrder" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>														
				
				<tr>
			 <td><label>Production Link Up</label></td>
				
				<td >
						<s:checkbox element="li" id="chkProductionLinkup" path="chkProductionLinkup" value="Yes" />
		       </td>
				
		        <td><label>Set UpTo Time For Adv Order</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkSetUpToTimeForAdvOrder" path="chkSetUpToTimeForAdvOrder" value="Yes" />
		       </td>		
				
			</tr>	
		      
		       
		      
		      <tr>
			 <td><label>Lock Data On Shift</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkLockDataOnShift" path="chkLockDataOnShift" value="Yes" />
		       </td>
				
		        <td><label>UpTo Time To Punch Adv Order</label></td>
				
			<td><s:select id="cmbHH" name="cmbHH" path="strHours" cssStyle="width:25%" cssClass="BoxW124px" >
				<option value="HH">HH</option>
				<option value="00">00</option>
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="03">03</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option> 
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				 </s:select>
			
				 <s:select id="cmbMM" name="cmbMM" path="strMinutes" cssStyle="width:25%" cssClass="BoxW124px" >
				<option value="MM">MM</option><option value="00">00</option><option value="01">01</option>
				<option value="02">02</option><option value="03">03</option><option value="04">04</option>
				<option value="05">05</option><option value="06">06</option><option value="07">07</option> 
				<option value="08">08</option><option value="09">09</option><option value="10">10</option>
				<option value="11">11</option><option value="12">12</option><option value="13">13</option>
				<option value="14">14</option><option value="15">15</option><option value="16">16</option>
				<option value="17">17</option><option value="18">18</option><option value="19">19</option>
				<option value="20">20</option><option value="21">21</option><option value="22">22</option>
				<option value="23">23</option><option value="24">24</option><option value="25">25</option>
				<option value="26">26</option><option value="27">27</option><option value="28">28</option>
				<option value="29">29</option><option value="30">30</option><option value="31">31</option>
				<option value="32">32</option><option value="33">33</option><option value="34">34</option>
				<option value="35">35</option><option value="36">36</option><option value="37">37</option>
				<option value="38">38</option><option value="39">39</option><option value="41">41</option>
				<option value="42">42</option><option value="43">43</option><option value="44">44</option>
				<option value="45">45</option><option value="46">46</option><option value="47">47</option>
				<option value="48">48</option><option value="49">49</option><option value="50">50</option>
				<option value="51">51</option><option value="52">52</option><option value="53">53</option>
				<option value="54">54</option><option value="55">55</option><option value="56">56</option>
				<option value="57">57</option><option value="58">58</option><option value="59">59</option>
				 </s:select>
		
				 <s:select id="cmbAMPM" name="cmbAMPM" path="strAMPM" cssStyle="width:25%" cssClass="BoxW124px" >
				<option value="AM">AM</option>
				 <option value="PM">PM</option>
 				 
				 </s:select></td>
			</tr>
				<tr>
			 <td><label>Enable Bill Series</label></td>
				
				<td >
						<s:checkbox element="li" id="chkEnableBillSeries" path="chkEnableBillSeries" value="Yes" />
		       </td>
				
		        <td><label>Set UpTo Time For Urgent Order</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkSetUpToTimeForUrgentOrder" path="chkSetUpToTimeForUrgentOrder" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Enable PMS Integration</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkEnablePMSIntegration" path="chkEnablePMSIntegration" value="Yes" />
		       </td>
				<td><label>UpTo Time To Punch Urgent Order</label></td>
		        <td><s:select id="cmbUrgentOrderHH" path="strHoursUrgentOrder" cssStyle="width:25%" cssClass="BoxW124px" >
				<option value="HH">HH</option>
				<option value="00">00</option>
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="03">03</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option> 
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				 </s:select>
				
				 <s:select id="cmbToMM" name="cmbToMM" path="strMinutesUrgentOrder" cssStyle="width:25%" cssClass="BoxW124px" >
				<option value="MM">MM</option><option value="00">00</option><option value="01">10</option>
				<option value="02">02</option><option value="03">03</option><option value="04">04</option>
				<option value="05">05</option><option value="06">06</option><option value="07">07</option> 
				<option value="08">08</option><option value="09">09</option><option value="10">10</option>
				<option value="11">11</option><option value="12">12</option><option value="13">13</option>
				<option value="14">14</option><option value="15">15</option><option value="16">16</option>
				<option value="17">17</option><option value="18">18</option><option value="19">19</option>
				<option value="20">20</option><option value="21">21</option><option value="22">22</option>
				<option value="23">23</option><option value="24">24</option><option value="25">25</option>
				<option value="26">26</option><option value="27">27</option><option value="28">28</option>
				<option value="29">29</option><option value="30">30</option><option value="31">31</option>
				<option value="32">32</option><option value="33">33</option><option value="34">34</option>
				<option value="35">35</option><option value="36">36</option><option value="37">37</option>
				<option value="38">38</option><option value="39">39</option><option value="41">41</option>
				<option value="42">42</option><option value="43">43</option><option value="44">44</option>
				<option value="45">45</option><option value="46">46</option><option value="47">47</option>
				<option value="48">48</option><option value="49">49</option><option value="50">50</option>
				<option value="51">51</option><option value="52">52</option><option value="53">53</option>
				<option value="54">54</option><option value="55">55</option><option value="56">56</option>
				<option value="57">57</option><option value="58">58</option><option value="59">59</option>
				 </s:select>
				
				 <s:select id="cmbToAMPM" name="cmbToAMPM" path="strAMPMUrgent" cssStyle="width:25%" cssClass="BoxW124px" >
				<option value="AM">AM</option>
				 <option value="PM">PM</option>
 				 
				 </s:select></td>		
				
			</tr>	
			<tr>
			 <td><label>Print Time On Bill </label></td>
				
				<td > 
						<s:checkbox element="li" id="chkPrintTimeOnBill" path="chkPrintTimeOnBill" value="Yes" />
		       </td>
				
		        <td><label>Carry Forward Float Amt to Next Day</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkCarryForwardFloatAmtToNextDay" path="chkCarryForwardFloatAmtToNextDay" value="Yes" />
		       </td>		
				
			</tr>	
			
			<tr>
			 <td><label>Print Remark And Reason For Reprint</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPrintRemarkAndReasonForReprint" path="chkPrintRemarkAndReasonForReprint" value="Yes" />
		       </td>
				
		        <td><label>Show Item Details Grid For Change Customer On Bill</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkShowItemDtlsForChangeCustomerOnBill" path="chkShowItemDtlsForChangeCustomerOnBill" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Enable Both Print And Settle Btn For DB </label></td>
				
				<td > 
						<s:checkbox element="li" id="chkEnableBothPrintAndSettleBtnForDB" path="chkEnableBothPrintAndSettleBtnForDB" value="Yes" />
		       </td>
				
		        <td><label>Show Pop Up For Next Item Quantity</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkShowPopUpForNextItemQuantity" path="chkShowPopUpForNextItemQuantity" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>Open Cash Drawer After Bill Print</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkOpenCashDrawerAfterBillPrint" path="chkOpenCashDrawerAfterBillPrint" value="Yes" />
		       </td>
				
		        <td><label>Property Wise Sales Order</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkPropertyWiseSalesOrder" path="chkPropertyWiseSalesOrder" value="Yes" />
		       </td>		
				
			</tr>	
			<tr>
			 <td><label>New Bill Series For New Day</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkNewBillSeriesForNewDay" path="chkNewBillSeriesForNewDay" value="Yes" />
		       </td>
				
		        <td><label>Show Only Login POS Reports</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkShowReportsPOSWise" path="chkShowReportsPOSWise" value="Yes" />
		       </td>		
				
			</tr>
			<tr>
			 <td><label>Enable Dine In</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkEnableDineIn" path="chkEnableDineIn" value="Yes" />
		       </td>
				
		        <td><label>Auto Area Selection In Make KOT</label></td>
				
				<td> 
						<s:checkbox element="li" id="chkAutoAreaSelectionInMakeKOT" path="chkAutoAreaSelectionInMakeKOT" value="Yes" />
		       </td>		
				
			</tr>
			</table>
			</div>
							<!-- End of POS Setup 3 tab -->
							
							
							<!-- 	Start of Email Setup tab -->
							
					<div id="tab6" class="tab_content">
					<br><br>
								<table  class="masterTable">
																		
									<tr>
				
				<td><label>Sender Email Id</label></td>
				
				<td><s:input type="text" id="txtSenderEmailId" 
						path="strSenderEmailId" 
				 cssClass="longTextBox"  /></td>
					
			</tr>
		
		<tr>
			<td><label>Password</label></td>
				<td><s:input type="password" id="txtEmailPassword" 
						path="strEmailPassword" 
					cssClass="longTextBox"  /></td>
				
			</tr>
			
			<tr>
			<td><label>Confirm Password</label></td>
				<td><s:input type="password" id="txtEmailConfirmPassword" 
						path=""
						 cssClass="longTextBox"  /></td>
				
			</tr>
			
			
			 <tr>
			<td><label>SMTP Server Name</label></td>
				
				<td><s:select id="cmbEmailServerName" path="strEmailServerName" cssClass="BoxW124px" >
				<option value="smtp.gmail.com">Gmail</option>
				 <option value="smtp.mail.yahoo.com">Yahoo</option>
 				
				 </s:select> 
		       </td>
			</tr>
			
			<tr>
			<td><label>Receiver Email Id</label></td>
				
			 <td><s:input type="text" id="txtReceiverEmailId" 
						path="strReceiverEmailId" 
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			
			<tr>
			<td><label>Mail Body</label></td>
			<td  rowspan="3"><s:textarea  id="txtBodyPart" 
						path="strBodyPart"  style="height:100px"
						 cssClass="longTextBox"  />
		       </td>
			</tr>
			
			</table>
			
						</div>
						<!-- 	End of Email Setup tab -->
						
						
							<!-- 	Start of Card Interface tab -->
							
					<div id="tab7" class="tab_content">
					<br><br>
								<table  class="masterTable">
																		
									 <tr>
			<td><label>Card Interface Type</label></td>
				
				<td>
				<s:select id="cmbCardIntfType" path="strCardIntfType" cssClass="BoxW124px" >
				<option value="Customer Card">Customer Card</option>
				 <option value="Member Card">Member Card</option>
 				 </s:select> 
		       </td>
			</tr>
	
			
			 <tr>
			<td><label>Card Interface</label></td>
				
				<td><s:select id="cmbRFIDSetup" path="strRFIDSetup" cssClass="BoxW124px" >
				<option value="N">NO</option>
				 <option value="Y">YES</option>
 				
				 </s:select> 
		       </td>
			</tr>
			
			<tr>
			<td><label>Server Name</label></td>
				
			 <td><s:input type="text" id="txtRFIDServerName" 
						path="strRFIDServerName" 
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			
				<tr>
			<td><label>User Name</label></td>
				
			 <td><s:input type="text" id="txtRFIDUserName" 
						path="strRFIDUserName" 
				 cssClass="longTextBox"  /></td>
			</tr>
				<tr>
			<td><label>Password</label></td>
				
			 <td><s:input type="password" id="txtRFIDPassword" 
						path="strRFIDPassword" 
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
				<tr>
			<td><label>Database Name</label></td>
				
			 <td><s:input type="text" id="txtRFIDDatabaseName" 
						path="strRFIDDatabaseName" 
				 cssClass="longTextBox"  /></td>
			</tr>
			
			
			</table>
			
						</div>
						<!-- 	End of Card Interface tab -->
						
							<!-- 	Start of CRM Interface tab -->
							
					<div id="tab8" class="tab_content">
					<br><br>
								<table  class="masterTable">
			
			 <tr>
			<td><label>CRM Interface</label></td>
				
				<td><s:select id="cmbCRM" path="strCRM" cssClass="BoxW124px" >
				<option value="SQY">SQY CRM Interface</option>
				 <option value="PMAM">PMAM CRM Interface</option>
 				 <option value="JPOS">JPOS CRM Interface</option>
				 </s:select> 
		       </td>
			</tr>
			
			<tr>
			<td><label>SQY WebService URL(GET)</label></td>
				
			 <td><s:input type="text" id="txtGetWebservice" 
						path="strGetWebservice" 
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			
			<tr>
			<td><label>SQY WebService URL(POST)</label></td>
				
			 <td><s:input type="text" id="txtPostWebservice" 
						path="strPostWebservice"
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			<tr>
			<td><label>SQY Outlet UID</label></td>
				
			 <td><s:input type="text" id="txtOutletUID" 
						path="strOutletUID" 
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			<tr>
			<td><label>SQY POS ID</label></td>
				
			 <td><s:input type="text" id="txtPOSID" 
						path="strPOSID" 
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			
			</table>
			
						</div>
						<!-- 	End of CRM Interface tab -->
						
								<!-- 	Start of SMS Setup tab -->
							
					<div id="tab9" class="tab_content">
					<br><br>
								<table  class="masterTable">
																		
									<tr>
				
				<td><label>SMS Type</label></td>
				
			
				<td><s:select id="cmbSMSType" path="strSMSType" cssClass="BoxW124px" >
				<option value="SINFINI">SINFINI</option>
				 <option value="CELLX">CELLX</option>
				 	 <option value="INFYFLYER">INFYFLYER</option>
 				
				 </s:select> 
		       </td>	
			</tr>
					
				<tr>
			<td><label>SMS API</label></td>
			<td><s:textarea  id="txtAreaSMSApi" 
						path="strAreaSMSApi"  style="height:30px"
						 cssClass="longTextBox"  />
		       </td>
			</tr>
		<tr>
		<td>
		<div >
	
		<label>Home Delivery SMS</label> 
		<s:checkbox element="li" id="chkHomeDelSMS" path="chkHomeDelSMS" value="Yes" /><br>
		<s:select id="cmbSendHomeDelivery" path="" cssClass="BoxW124px" >
				<option value="BILL NO">BILL NO</option>
				 <option value="CUSTOMER NAME">CUSTOMER NAME</option>
				 	 <option value="DATE">DATE</option>
 				<option value="DELIVERY BOY">DELIVERY BOY</option>
				 <option value="ITEMS">ITEMS</option>
				 	 <option value="BILL AMT">BILL AMT</option>
 				<option value="USER">USER</option>
				 <option value="TIME">TIME</option>
				 	
				 </s:select> <br><br>
				  <input id="btnAddDelSMS" type="button" class="smallButton" value=">>" onclick="return btnAddDelSMS_onclick();"></input>
		</div>
		</td>
		
		<td>
		<s:textarea  id="txtAreaSendHomeDeliverySMS" 
						path="strAreaSendHomeDeliverySMS"  style="height:100px"
						 cssClass="longTextBox"  />
		
		</td>
		</tr>
		
			<tr>
		<td>
		<div >
	
		<label>Bill Settlement SMS</label> 
		<s:checkbox element="li" id="chkBillSettlementSMS" path="chkBillSettlementSMS" value="Yes" /><br>
		<s:select id="cmbBillSettlement" path="" cssClass="BoxW124px" >
				<option value="BILL NO">BILL NO</option>
				 <option value="CUSTOMER NAME">CUSTOMER NAME</option>
				 	 <option value="DATE">DATE</option>
 				<option value="DELIVERY BOY">DELIVERY BOY</option>
				 <option value="ITEMS">ITEMS</option>
				 	 <option value="BILL AMT">BILL AMT</option>
 				<option value="USER">USER</option>
				 <option value="TIME">TIME</option>
				 	
				 </s:select> <br><br>
				  <input id="btnAddSettleSMS" type="button" class="smallButton" value=">>" onclick="return btnAddSettleSMS_onclick();"></input>
		</div>
		</td>
		
		<td>
		<s:textarea  id="txtAreaBillSettlementSMS" 
						path="strAreaBillSettlementSMS" style="height:100px"
						 cssClass="longTextBox"  />
		
		</td>
		</tr>
			
			</table>
			
						</div>
						<!-- 	End of SMS Setup tab -->
						
						
							<!-- 	Start of FTP Setup tab -->
							
					<div id="tab10" class="tab_content">
					<br><br>
								<table  class="masterTable">
																		
			
			
			<tr>
			<td><label>FTP Server Address</label></td>
				
			 <td><s:input type="text" id="txtFTPAddress" 
						path="strFTPAddress" 
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			
				<tr>
			<td><label>FTP Server User Name</label></td>
				
			 <td><s:input type="text" id="txtFTPServerUserName" 
						path="strFTPServerUserName"
				 cssClass="longTextBox"  /></td>
			</tr>
				<tr>
			<td><label>FTP Server Password</label></td>
				
			 <td><s:input type="password" id="txtFTPServerPass" 
						path="strFTPServerPass"
				 cssClass="longTextBox"  /></td>
						
				
		     
			</tr>
			
			
			
			</table>
			
						</div>
						<!-- 	End of FTP Setup tab -->
						
						
						<!-- 	Start of CMS Integration tab -->
							
					<div id="tab11" class="tab_content">
					<br><br>
								<table  class="masterTable">
			
			 <tr>
			<td><label>CMS Integration</label></td>
				
				<td colspan="3"><s:select id="cmbCMSIntegrationYN" path="strCMSIntegrationYN" cssClass="BoxW124px" >
				<option value="N">NO</option>
				 <option value="Y">YES</option>
 			
				 </s:select> 
		       </td>
			</tr>
			
			<tr>
			<td><label>Web Service URL</label></td>
				
			 <td colspan="3"><s:input type="text" id="txtCMSWesServiceURL" 
						path="strCMSWesServiceURL" 
				 cssClass="longTextBox"  /></td>
			
			</tr>
			
			<tr>
			<td><label>Treat Member As Table</label>
			</td><td colspan="3">
			<s:checkbox element="li" id="chkMemberAsTable" path="chkMemberAsTable" value="Yes" />
			 </td>
			</tr>
			<tr>
			<td><label>Member Code For KOT In JPOS</label>
			</td><td>
			<s:checkbox element="li" id="chkMemberCodeForKOTJPOS" path="chkMemberCodeForKOTJPOS" value="Yes" />
			 </td>
			
			<td><label>Member Code For KOT In MPOS By Card Swipe</label>
			</td><td>
			<s:checkbox element="li" id="chkMemberCodeForKotInMposByCardSwipe" path="chkMemberCodeForKotInMposByCardSwipe" value="Yes" />
			 </td>
			</tr>
			
			<tr>
			<td><label>Member Code For Make Bill In MPOS</label>
			</td><td>
			<s:checkbox element="li" id="chkMemberCodeForMakeBillInMPOS" path="chkMemberCodeForMakeBillInMPOS" value="Yes" />
			 </td>
			
			<td><label>Member Code For KOT In MPOS</label>
			</td><td>
			<s:checkbox element="li" id="chkMemberCodeForKOTMPOS" path="chkMemberCodeForKOTMPOS" value="Yes" />
			 </td>
			</tr>
			
			<tr>
			<td><label>Select Customer Code From Card Swipe</label>
			</td><td  colspan="3">
			<s:checkbox element="li" id="chkSelectCustomerCodeFromCardSwipe" path="chkSelectCustomerCodeFromCardSwipe" value="Yes" />
			 </td>
			</tr>
				 <tr>
			<td><label>CMS Posting Type</label></td>
				
				<td colspan="3"><s:select id="cmbCMSPostingType" path="strCMSPostingType" cssClass="BoxW124px" >
				<option value="Sanguine CMS">Sanguine CMS</option>
				 <option value="Others">Others</option>
 			 </s:select> 
		       </td>
			</tr>
			</table>
			
						</div>
						<!-- 	End of CMS Integration tab -->
						
							
							<!-- 	Start of Printer Setup tab -->
							
					<div id="tab12" class="tab_content">
					<br>
								<table border="1" class="myTable" style="width:80%;margin: auto;"  >
										
										<tr>
										
										<td style="width:30%; border: #c0c0c0 1px solid; background: #78BEF9;">Cost Center Name</td>
										<td style="width:25%; border: #c0c0c0 1px solid; background: #78BEF9;">Primary Printer</td>
										<td style="width:25%; border: #c0c0c0 1px solid; background: #78BEF9;">Secondary Printer</td>
										<td style="width:20%; border: #c0c0c0 1px solid; background: #78BEF9;">Print On Both Printer</td>
										</tr>
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 380px;
				    				margin:auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
										<table id="tblPrinterDtl" class="transTablex col5-center" style="width:100%;">
										<tbody>   
										<col style="width:0%"><!--  COl1   --> 
												<col style="width:30%"><!--  COl1   -->
												<col style="width:25%"><!--  COl2   -->
												<col style="width:25%"><!--  COl2   -->
												<col style="width:20%"><!--  COl3   -->
																				
										</tbody>							
										</table>
								</div>
			
						</div>
						<!-- 	End of Printer Setup tab -->
						
						
							
							<!-- 	Start of Debit Card Setup tab -->
							
					<div id="tab13" class="tab_content">
					<br><br>
								<table  class="masterTable">
																		
			
			
			<tr>
		<td><label>Last POS Day For Day End</label></td>
				<td ><s:select id="cmbPOSForDayEnd" name="cmbPOSForDayEnd" path="strPOSForDayEnd" items="${posListForDayEnd}" cssClass="BoxW124px" />
				</td>
		    
		       </tr>
			
			
			
			</table>
			
						</div>
						<!-- 	End of Debit Card Setup tab -->
						
							<!-- 	Start of Bill Series Setup tab -->
							
					<div id="tab14" class="tab_content">
					
						
								<table  class="masterTable">
																		
			
			
			<tr>
		<td><label>Select Type</label></td>
				<td >	<s:select id="cmbSelectedType" path="strBillSeriesType" cssClass="BoxW124px" >
				<option value="Group">Group</option>
				 <option value="Sub Group">Sub Group</option>
				 	 <option value="Menu Head">Menu Head</option>
 				<option value="Revenue Head">Revenue Head</option>
				 </s:select></td>
				 <td>
				  <input id="btnAdd" type="button" class="smallButton" value="Add" onclick="return btnAdd_onclick();"></input>
		</td>
		    
		       </tr>
			
			
			
			</table>
					
								<table border="1" class="myTable" style="width:80%;margin: auto;"  >
										
											<tr>
										<td style="width:30%; border: #c0c0c0 1px solid; background: #78BEF9;">Type</td>
										<td style="width:25%; border: #c0c0c0 1px solid; background: #78BEF9;">Name</td>
										<td style="width:25%; border: #c0c0c0 1px solid; background: #78BEF9;">Code</td>
										<td style="width:20%; border: #c0c0c0 1px solid; background: #78BEF9;">Select</td>
										</tr>
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 150px;
				    				margin:auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
										<table id="tblSelectedTypeDtl" class="transTablex col5-center" style="width:100%;">
										<tbody>    
												<col style="width:30%"><!--  COl1   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:10%"><!--  COl3   -->
																				
										</tbody>							
										</table>
								</div>
			
			<br>
								<table border="1" class="myTable" style="width:80%;margin: auto;"  >
										
										<tr>
										
										<td style="width:10%; border: #c0c0c0 1px solid; background: #78BEF9;">Serial No.</td>
										<td style="width:10%; border: #c0c0c0 1px solid; background: #78BEF9;">Bill Series</td>
										<td style="width:30%; border: #c0c0c0 1px solid; background: #78BEF9;">Contents</td>
										<td style="width:10%; border: #c0c0c0 1px solid; background: #78BEF9;">Remove</td>
										<td style="width:20%; border: #c0c0c0 1px solid; background: #78BEF9;">Print Grand Total of<br>Other Bills</td>
										<td style="width:20%; border: #c0c0c0 1px solid; background: #78BEF9;">Print Inclusive of<br>All Taxes</td>
										
										</tr>
					</table>
					<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 130px;
				    				margin:auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
										<table id="tblBillseriesDtl" class="transTablex col5-center" style="width:100%;">
										<tbody>   
												<col style="width:0%"><!--  COl1   --> 
												<col style="width:10%"><!--  COl1   -->
												<col style="width:10%"><!--  COl2   -->
												<col style="width:30%"><!--  COl2   -->
												<col style="width:10%"><!--  COl3   -->
												<col style="width:20%"><!--  COl2   -->
												<col style="width:20%"><!--  COl3   -->
																			
										</tbody>							
										</table>
									
								</div>
									
								<table  class="masterTable">
																		
			
			
			<tr>
		<td ></td><td ></td><td ></td><td ></td><td ></td><td ></td><td ></td><td ></td>
				 <td>
				  <input id="btnRemove" type="button" class="smallButton" value="Remove" onclick="return btnRemove_onclick();"></input>
		</td>
		    
		       </tr>
			
			
			
			</table>
						</div>
						<!-- 	End of Bill Series Setup tab -->
						
							<!-- 	Start of Inresto Integration Setup tab -->
							
					<div id="tab15" class="tab_content">
					<br><br>
								<table  class="masterTable">
																		
			
			
			<tr>
		<td><label>Inresto Integration</label></td>
				<td ><s:select id="cmbInrestoPOSIntegrationYN" path="strInrestoPOSIntegrationYN" cssClass="BoxW124px" >
				<option value="N">No</option>
				 <option value="Y">Yes</option>
 			 </s:select> </td>
		    
		       </tr>
			<tr>
			<td><label>Web Service URL</label></td>
				
				 <td><s:input type="text" id="txtInrestoPOSWesServiceURL" 
						 path="strInrestoPOSWesServiceURL" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>
			
			<tr>
			<td><label>Inresto POS ID</label></td>
				
				 <td><s:input type="text" id="txtInrestoPOSId" 
						 path="strInrestoPOSId" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>
			
			<tr>
			<td><label>Inresto POS KEY</label></td>
				
				 <td><s:input type="text" id="txtInrestoPOSKey" 
						 path="strInrestoPOSKey" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>
			
			
			</table>
			
						</div>
						<!-- 	End of Inresto Integration Setup tab -->
						
							<!-- 	Start of Jio Integration Setup tab -->
							
					<div id="tab16" class="tab_content">
					<br><br>
								<table  class="masterTable">
																		
			
			
			<tr>
		<td><label>Jio Money Integration</label></td>
				<td ><s:select id="cmbJioPOSIntegrationYN" path="strJioPOSIntegrationYN" cssClass="BoxW124px" >
				<option value="N">No</option>
				 <option value="Y">Yes</option>
 			 </s:select> </td>
		    
		       </tr>
			<tr>
			<td><label>Web Service URL</label></td>
				
				 <td><s:input type="text" id="txtJioPOSWesServiceURL" 
						 path="strJioPOSWesServiceURL" 
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> 
		       </td>		
				
			</tr>
			<tr>
			<td><label>JioMoney Merchant ID</label></td>
				
			 <td><s:input type="text" id="txtJioMID" 
						path="strJioMID"
				 cssClass="longTextBox"  /></td>
			</tr>
				<tr>
			<td><label>JioMoney Terminal ID</label></td>
				
			 <td><s:input type="text" id="txtJioTID" 
						path="strJioTID"
				 cssClass="longTextBox"  /></td>
			</tr>
				<tr>
			<td><label>Activation Code </label></td>
				
			 <td><s:input type="text" id="txtJioActivationCode" 
						path="strJioActivationCode"
				 cssClass="longTextBox"  /></td>
			</tr>
				<tr>
			<td><label>Device ID </label></td>
				
			 <td><s:input type="text" id="txtJioDeviceID" 
						path="strJioDeviceID"
				 cssClass="longTextBox"  /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input id="btnFetchID" type="button" class="smallButton" value="Fetch ID" onclick="return btnFetchID_onclick();"></input>
				</td>
			</tr>
				
				
			</table>
			
						</div>
						<!-- 	End of Jio Integration Setup tab -->
						
						</div>
					</td>
				</tr>
				</table>
		<br />
		<br />
		<p align="center">
			<input id="submitBtn" type="submit" value="Update" tabindex="3" class="form_button"/> 
				</p>
	</s:form>

</body>
</html>