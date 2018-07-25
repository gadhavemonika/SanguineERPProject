<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>


<style type="text/css">

.searchTextBox {
    background: #FFF url(../images/textboxsearchimage.png) no-repeat 192px 2px;
    background-color: inherit;
    border: 1px solid #060006;
    outline: 0;
    padding-left: 5px;
    height: 25px;
    width: 203px;       
}

.menuItemBtn {
 width: 100px;
 height: 100px; 
 white-space: normal; 
}

.controlgroup-textinput{
				padding-top: .22em;
				padding-bottom: .22em;
	
</style>

<script type="text/javascript">
	var gMobileNo="";
	var fieldName;
	var selectedRowIndex=0;
	var gDebitCardPayment="";
	var tblMenuItemDtl_MAX_ROW_SIZE=100;
	var tblMenuItemDtl_MAX_COL_SIZE=4;
	var itemPriceDtlList=new Array();	
	var hmHappyHourItems = new Map();
	var gCustomerCode="",gCustomerName="";
	var currentDate="";
	var currentTime="";
	var dayForPricing="",flagPopular="",menucode="",homeDeliveryForTax="N",gTakeAway="No",cmsMemCode="",cmsMemName="";
	var arrListHomeDelDetails= new Array();
	var arrKOTItemDtlList=new Array();
	var gCustAddressSelectionForBill="${gCustAddressSelectionForBill}";
	var gCMSIntegrationYN="${gCMSIntegrationYN}";
	var gCRMInterface="${gCRMInterface}";
	var gDelBoyCompulsoryOnDirectBiller="${gDelBoyCompulsoryOnDirectBiller}";
	var gRemarksOnTakeAway="{$gRemarksOnTakeAway}";
	var gNewCustomerForHomeDel=false;
	var gTotalBillAmount,gNewCustomerMobileNo;
    var gBuildingCodeForHD="",gDeliveryBoyCode="";
	//start PLU Search funactionality
	$(document).ready(function()
	{
		$("#txtSearch").keyup(function()
		{
			searchTable($(this).val());
		});
		
	});
	function searchTable(inputVal)
	{
		var table = $('#tblMenuItemDtl');
		table.find('tr').each(function(index, row)
		{
			var allCells = $(row).find('td');
			if(allCells.length > 0)
			{
				var found = false;
				allCells.each(function(index, td)
				{
					var regExp = new RegExp("\\b(" + inputVal + ")\\b", 'i');
					
					
					var element=$(this).html();	
		        	
			       	var itemName=$(element).attr("value");
					 if (td)
					 {
						 if (td.innerHTML.toUpperCase().indexOf(inputVal.toUpperCase()) > -1)
							{
									found = true;
									return false;
							} 
					 }									
					
				});
				if(found == true)
				{
					$(row).show();
				}
				else
				{
					 $(row).hide();
				}
			}
		});
	}		 
	//end PLU Search funactionality		
	
	//Footer btn click
	
	function funFooterButtonClicked(objFooterButton)
	{
		switch(objFooterButton.id)
		{
		case "Done":
			funValidate();
			break;
			
		case "Home Delivery":
			funHomeDeliveryBtnClicked();
			break;
			
		case "Customer":
			funCustomerBtnClicked();
			break;
			
		case "Delivery Boy":
			funHelp('POSDeliveryBoyMaster');
			break;
			
		case "Take Away":
			funTakeAwayBtnClicked();
			break;
			
		case "Home":
			funHomeBtnclicked();
			break;
			
		case "Customer History" :
			funCustomerHistoryBtnClicked();
			break;
		}
	}
	
	function funCustomerHistoryBtnClicked()
	{
		 if (gCustomerCode.trim().length==0)
         {
        	 alert("Select Customer!");
             return;
         }
		 else
		 {
				window.open("frmCustomerHistory.html?strCustCode="+gCustomerCode+"","","dialogHeight:450px;dialogWidth:500px;dialogLeft:400px;");
		 }
	}
	function funAddCustomerHistory(arr,total)
	{
		$("#txtTotal").val(total);		
		for(var j=0;j<arr.length;j++)
		{
		var itmDtl=arr[j].split("#");
		
		var tblBillItemDtl=document.getElementById('tblBillItemDtl');
		
		var rowCount = tblBillItemDtl.rows.length;
		var insertRow = tblBillItemDtl.insertRow(rowCount);
				
	    var col1=insertRow.insertCell(0);
	    var col2=insertRow.insertCell(1);
	    var col3=insertRow.insertCell(2);
	    var col4=insertRow.insertCell(3);
	    var col5=insertRow.insertCell(4);
	   
	    
	    col1.innerHTML = "<input readonly=\"readonly\" size=\"32px\"  class=\"itemName\"    style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemName\" id=\"strItemName."+(rowCount)+"\" value='"+itmDtl[1]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    col2.innerHTML = "<input readonly=\"readonly\" size=\"3.5px\"   class=\"itemQty\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblQuantity\" id=\"dblQuantity."+(rowCount)+"\" value='"+parseFloat(itmDtl[2])+"' onclick=\"funChangeQty(this)\"/>";
	    col3.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemAmt\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblAmount\" id=\"dblAmount."+(rowCount)+"\" value='"+itmDtl[3]+"'/>";
	    col4.innerHTML = "<input readonly=\"readonly\" size=\"4.3px\" class=\"itemCode\"     style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemCode\" id=\"strItemCode."+(rowCount)+"\" value='"+itmDtl[0]+"' />";
	    col5.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemDiscAmt\"  style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strSerialNo\" id=\"strSerialNo."+(rowCount-1)+"\" value='"+rowCount+"' />";
	    	
		}
	}
	function funValidate()
	{
		 if (gDebitCardPayment=="Yes")
	        {
	            if (gCheckDebitCardBalanceOnTrans=="Y")
	            {
	                if (!flgCheckNCKOTButtonColor)
	                {
	                    if ($("#txtCardBalance").text().length==0)
	                    {
	                        var debitCardBalance = parseFloat($("#txtCardBalance").text());
	                        if (parseFloat($("#txtTotal").val()) > debitCardBalance)
	                        {
	                            alert("Insufficient Balance on Card!!!");
	                            return;
	                        }
	                    }
	                }
	            }
	        }
		 if (homeDeliveryForTax=="Y")
         {
             if (gCustomerCode.trim().length==0)
             {
            	 alert("Select Customer for Home Delivery!!!");
                 return;
             }
         }
		 if (gCMSIntegrationYN=="Y")
	        {
	            if (gClientCode!="074.001")
	            {
	                if (cmsMemberCode.trim().length == 0)
	                {
                        alert("Please Select Member!!!");
                        return;
	                }
	            }
	        }
		 else if (!((gCustomerCode.trim().length==0) && homeDeliveryForTax=="Y"))
         {
            // billTransType = "Home Delivery";
            var totalBillAmount = 0.00;
		 	 if ($("#txtTotal").val().trim().length > 0)
       		 {
			 	 totalBillAmount = parseFloat($("#txtTotal").val());
     		 }
             if (!gNewCustomerForHomeDel)
             {
                 funGetDeliveryCharges(gBuildingCodeForHD, totalBillAmount, gCustomerCode);
             }
             if (gDelBoyCompulsoryOnDirectBiller=="Y")
             {
                 if (gDeliveryBoyCode == "")
                 {
                     alert( "Please Assign Delivery Boy");
                     return;
                 }
             }
         }
		 else if (gRemarksOnTakeAway=="Y")
         {
             if (gTakeAway=="Yes" && gCustomerCode.trim().length==0 )
             {
                 alert("Select Customer For Take Away!!!");
                 return;
             }
         }
		 var tblBillItemDtl=document.getElementById('tblBillItemDtl');
			
			var rowCount = tblBillItemDtl.rows.length;
			if(rowCount==0)
			{
				alert("Please Select item!");
				return;
			}
			
			/* Write code Redirect on bill Settlement form */
			
			
	}
	function funGetDeliveryCharges(buildingCode, totalBillAmount, gCustomerCode)
	{
		
	}
	function funTakeAwayBtnClicked()
	{
		 homeDeliveryForTax = "N";
			
		 if(gTakeAway=="No")
			{
			    gTakeAway="Yes";
			}
		else
        {
			gTakeAway = "No";
        }
		
	}
	function funHomeBtnclicked()
	{
		var loginPOS="${loginPOS}";
	
		if (arrKOTItemDtlList.length > 0)
        {
           if(confirm( "Do you want to end Transaction?"))
        	   window.location ="frmGetPOSSelection.html?strPosCode="+loginPOS;
		else
            return;
        } 
        else
        {
        	window.location ="frmGetPOSSelection.html?strPosCode="+loginPOS;
        }	
    }
	function funHomeDeliveryBtnClicked()
	{
			
		if (gCustomerCode.trim().length == 0)
        {

           alert("Please Enter Customer Mobile No!");
            return;
        }
		else
        {
			funCheckHomeDelStatus();
        }
		
	}
	function funCheckHomeDelStatus()
	{
		homeDeliveryForTax = "Y";
		if (arrListHomeDelDetails.length == 0)
        {
            arrListHomeDelDetails[0]=gCustomerCode;
            arrListHomeDelDetails[1]=gCustomerName;
            arrListHomeDelDetails[2]=gBuildingCodeForHD;
            arrListHomeDelDetails[3]="HomeDelivery";
            arrListHomeDelDetails[4]="";
            arrListHomeDelDetails[5]="";
        }
		if(gTakeAway=="Yes")
		{
		    gTakeAway="No";
		}
		if(gCustAddressSelectionForBill=="Y")
		{
		 	window.open("frmHomeDeliveryAddress.html?strMobNo="+gMobileNo+"","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		} 
	}
	
	function funCustomerBtnClicked()
	{
		if(gCMSIntegrationYN=="Y")
			{
			funChekCMSCustomerDtl();
			}
		else
        {
            funNewCustomerButtonPressed();
        }
	}
	
	function funNewCustomerButtonPressed()
	{
		if (gCRMInterface=="SQY")
        {
			 var strMobNo = prompt("Enter Mobile number", "");
			 if(strMobNo.trim().length>0)
				 funCallWebService(strMobNo);
        }
		else if (gCRMInterface=="PMAM")
        {
			 var strMobNo = prompt("Enter Mobile number", "");
			 if(strMobNo.trim().length>0)
				 funSetCustMobileNo(strMobNo);
			 
       	}
		else
        {
			 var strMobNo = prompt("Enter Mobile number", "");
			 if(strMobNo.trim().length>0)
			 {
				 funSetCustMobileNo(strMobNo);
			 }
       	}
	}

	function  funSetCustMobileNo(strMobNo)
	{
		gMobileNo=strMobNo;
	
		 if (strMobNo.trim().length == 0)
         {
			 funHelp('POSCustomerMaster');
         }
		 else
			 funCheckCustomer(strMobNo);
	}
	function funCheckCustomer(strMobNo)
	{
		var totalBillAmount = 0.00;
		  if ($("#txtTotal").val().trim().length > 0)
        {
			  totalBillAmount = parseFloat($("#txtTotal").val());
        }	
		var searchurl=getContextPath()+"/funCheckCustomer.html?strMobNo="+strMobNo;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	 if (response.flag)
			             {
			        		 gCustomerCode=response.strCustomerCode;
			        		 gBuildingCodeForHD= response.strBuldingCode;
			        		 $("#Customer").val(response.strCustomerName);
			             }	
			        	 else
			        		 {
			        		 gNewCustomerForHomeDel = true;
		                     gTotalBillAmount = totalBillAmount;
		                     gNewCustomerMobileNo =gMobileNo;
			        		 funCustomerMaster(strMobNo);
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
	function funCallWebService(strMobNo)
	{		
		var searchurl=getContextPath()+"/funCallWebService.html?strMobNo="+strMobNo;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	 if (null != response.code)
			             {
			                 if (parseInt(response.code) == 323)
			                 {
			                     alert("Discount Request Expired! Please ask the customer to regenerate discount request!");
			                     return 0;
			                 }
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
	function funChekCMSCustomerDtl()
	{		
		var searchurl=getContextPath()+"/funChekCMSCustomerDtl.html?strTableNo="+globalTableNo;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.flag)
			        	{
			        	if(response.dblAmount<1)
			        		funGetCMSMemberCode();
			        	else
			        	{
			        		cmsMemCode=response.strCustomerCode;
			        		cmsMemName=response.strCustomerName;
			        	}
			        	
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
	
	function funGetCMSMemberCode()
	{
		 var strCustomerCode = prompt("Enter Member Code", "");
		 if(strCustomerCode.trim().length>0)
			 {
			 var searchurl=getContextPath()+"/funCheckMemeberBalance.html?strCustomerCode="+strCustomerCode;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.flag)
				        	{
				        		 if (response.memberInfo.split("#")[4].trim().equals("Y"))
				                 {
				                     alert("Member is blocked");
				                     return;
				                 }
				                 else
				                 {
				                     cmsMemCode = response.memberInfo.split("#")[0];
				                     cmsMemName = response.memberInfo.split("#")[1];
				                     $("#Customer").val(cmsMemName);
				                     var creditLimit = parseFloat(response.memberInfo.split("#")[2]);
									var totalAmt;
				                     if ($("#txtTotal").val().trim().length > 0)
				                     {
				                         totalAmt = $("#txtTotal").val();
				                     }
				                     if (creditLimit < totalAmt)
				                     {
				                      alert("Credit Limit is " + creditLimit);
				                     }
				                 }
				        	
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
			
	}
	
	
	//Apply Validation on Number TextFiled
	function funApplyNumberValidation() {
		$(".numeric").numeric();
		$(".integer").numeric(false, function() {
			alert("Integers only");
			this.value = "";
			this.focus();
		});
		$(".positive").numeric({
			negative : false
		}, function() {
			alert("No negative values");
			this.value = "";
			this.focus();
		});
		$(".positive-integer").numeric({
			decimal : false,
			negative : false
		}, function() {
			alert("Positive integers only");
			this.value = "";
			this.focus();
		});
		$(".decimal-places").numeric({
			decimalPlaces : maxQuantityDecimalPlaceLimit,
			negative : false
		});
		$(".decimal-places-amt").numeric({
			decimalPlaces : maxAmountDecimalPlaceLimit,
			negative : false
		});
	}
	
	
	//function to update item which is alredy order item (duplicate)
		function funUpdateTableBillItemDtlFor(objMenuItemPricingDtl,price,qty)
	{
			var itemCode=''
	
		$('#tblBillItemDtl tbody tr').each(function () 
		{
			//'td:nth-child(4)' for itemcode 
		    $('td:nth-child(4)', this).each(function () 
		    {	     
		     	var element=$(this).html();	
		        	
		       	var itemCode=$(element).attr("value");
		       	if(itemCode==objMenuItemPricingDtl.strItemCode)
		       	{
// 		       		$('td:nth-child(3)', this).each(function () 
// 		       			    {	     
// 		       			     	var element=$(this).html();	
		       			        	
// 		       			       	var itemPrice=$(element).attr("value");
// 		       			       	if(itemPrice==price)
// 		       			       	{
// 		       			       	 	var rowIndex = this.parentNode.rowIndex;					
// 		       			    	 	var colIndex = 2; /* this.cellIndex; *///for qty
		       			    	 	
// 		       			    	 	var oldQty = $(this.parentNode).find(".itemQty").val(); 
// 		       			    	 	var oldAmt= $(this.parentNode).find(".itemAmt").val(); 
		       					
// 		       			    	 	var rate=parseFloat(oldAmt)/parseFloat(oldQty);
		       			    	 	
		       			    	 	    	 
// 		       			    	 	var newQty=parseFloat(oldQty)+parseFloat(qty);
		       			    	 	
// 		       			    	 	var newAmt=parseFloat(rate)*parseFloat(newQty);
		       			    	 	
// 		       			    	 	$(this.parentNode).find(".itemQty").val(parseFloat(newQty)); 
// 		       			    	 	$(this.parentNode).find(".itemAmt").val(parseFloat(newAmt));
		       			    	 			       		
// 		       			       	}else
// 		       			       		{
// 		       			       			funFillTableBillItemDtl(objMenuItemPricingDtl,price,qty);
// 		       			       		}
// 		       			     });
		       		
		       	 	var rowIndex = this.parentNode.rowIndex;					
		    	 	var colIndex = 2; /* this.cellIndex; *///for qty
		    	 	
		    	 	var oldQty = $(this.parentNode).find(".itemQty").val(); 
		    	 	var oldAmt= $(this.parentNode).find(".itemAmt").val(); 
				
		    	 	var rate=parseFloat(oldAmt)/parseFloat(oldQty);
		    	 	
		    	 	    	 
		    	 	var newQty=parseFloat(oldQty)+parseFloat(qty);
		    	 	
		    	 	var newAmt=parseFloat(rate)*parseFloat(newQty);
		    	 	
		    	 	$(this.parentNode).find(".itemQty").val(parseFloat(newQty)); 
		    	 	$(this.parentNode).find(".itemAmt").val(parseFloat(newAmt));
		    	 			       		
		       	}		        			        
		     });		
		});	
	}
	
	
	//function to to check is alredy order item (duplicate)
	function funIsAlreadyOrderedItem(objMenuItemPricingDtl)
	{		
		var isOrdered=false;
		$('#tblBillItemDtl tbody tr').each(function () 
		{
			//'td:nth-child(4)' for itemcode 
		    $('td:nth-child(4)', this).each(function () 
		    {	     
		     	var element=$(this).html();	
		        	
		       	var itemCode=$(element).attr("value");
		       	if(itemCode==objMenuItemPricingDtl.strItemCode)
		       	{		       	 
		       		isOrdered=true;
		       	}		        			        
		     });						
		});	
		
		return isOrdered;
		
	}
	function funSetData(code)
	{

		switch(fieldName)
		{
		
		case "POSCustomerMaster":
			funSetCustomerDataForHD(code);
			break;
		case "NewCustomer":
			funSetCustomerDataForHD(code);
			break;
		case "POSDeliveryBoyMaster":
			funSetDeliveryBoy(code);
			break;
		}
	}
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	 function funCustomerMaster(strMobNo)
	{
		 fieldName="NewCustomer";
		 <%session.setAttribute("frmName", "frmPOSRestaurantBill");%>

		
		window.open("frmPOSCustomerMaster.html","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	 function funSetCustomerDataForHD(code)
		{
			code=code.trim();
			var searchurl=getContextPath()+"/loadPOSCustomerMasterData.html?POSCustomerCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	$("#Customer").val(response.strCustomerName);
				        	gCustomerCode=response.strCustomerCode;
					        	funSetHomeDeliveryData(response.strCustomerCode,response.strCustomerName,response.strBuldingCode,"","");
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

		function funSetDeliveryBoy(code)
		{
			code=code.trim();
			var searchurl=getContextPath()+"/loadPOSDeliveryBoyMasterData.html?dpCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	arrListHomeDelDetails[4]=code;//4 del person code
				            arrListHomeDelDetails[5]=response.strDPName;//5 del person name
				            gDeliveryBoyCode=code;
				        	document.all["lblDpName"].style.display = 'block';
				        	$("#dpName").text(response.strDPName);
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

		function funSetHomeDeliveryData(custCode, custName, buildingCode, delPersonCode, delPersonName)
	    {
	        arrListHomeDelDetails.length=0;
	        arrListHomeDelDetails[0]=custCode;//0 cust code
	        arrListHomeDelDetails[1]=custName;//1 cust name
	        arrListHomeDelDetails[2]=buildingCode;//2 building code
	        arrListHomeDelDetails[3]="HomeDelivery";//3 home delivery
	        arrListHomeDelDetails[4]=delPersonCode;//4 del person code
	        arrListHomeDelDetails[5]=delPersonName;//5 del person name
	    }
	function funFillMapWithHappyHourItems()
	{		
		var searchurl=getContextPath()+"/funFillMapWithHappyHourItems.html";
		$.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        async:	false,
			        success: function(response)
			        {
			        	for(var i=0;i<response.ItemPriceDtl.length;i++)
		        		{
			        		hmHappyHourItems.put(response.ItemCode[i],response.ItemPriceDtl[i]);
		        		}
			        	 gDebitCardPayment=response.gDebitCardPayment;
			        	currentDate=response.CurrentDate;
			        	currentTime=response.CurrentTime;
			        	dayForPricing=response.DayForPricing; 
			        	
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
	
	
	//function to add items to bill item table
	
	function funFillTableBillItemDtl(objMenuItemPricingDtl,price,qty)
	{	
		var itemName=objMenuItemPricingDtl.strItemName.replace(/&#x00A;/g," ");
		var tblBillItemDtl=document.getElementById('tblBillItemDtl');
		
		var rowCount = tblBillItemDtl.rows.length;
		var insertRow = tblBillItemDtl.insertRow(rowCount);
				
	    var col1=insertRow.insertCell(0);
	    var col2=insertRow.insertCell(1);
	    var col3=insertRow.insertCell(2);
	    var col4=insertRow.insertCell(3);
	    var col5=insertRow.insertCell(4);
	   
	    
	    col1.innerHTML = "<input readonly=\"readonly\" size=\"32px\"  class=\"itemName\"    style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemName\" id=\"strItemName."+(rowCount)+"\" value='"+itemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    col2.innerHTML = "<input readonly=\"readonly\" size=\"3.5px\"   class=\"itemQty\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblQuantity\" id=\"dblQuantity."+(rowCount)+"\" value='"+parseFloat(qty)+"' onclick=\"funChangeQty(this)\"/>";
	    col3.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemAmt\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblAmount\" id=\"dblAmount."+(rowCount)+"\" value='"+price+"'/>";
	    col4.innerHTML = "<input readonly=\"readonly\" size=\"4.3px\" class=\"itemCode\"     style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemCode\" id=\"strItemCode."+(rowCount)+"\" value='"+objMenuItemPricingDtl.strItemCode+"' />";
	    col5.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemDiscAmt\"  style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strSerialNo\" id=\"strSerialNo."+(rowCount-1)+"\" value='"+rowCount+"' />";
	    	    
		
		
		
	}
	function funAddModifierTableBillItemDtl(objMenuItemPricingDtl)
	{	
		funFillKOTList();
		deleteTableRows();
		var tblBillItemDtl=document.getElementById('tblBillItemDtl');
		var rowCount = tblBillItemDtl.rows.length;
		var insertRow = tblBillItemDtl.insertRow(rowCount);
		
	    var col1=insertRow.insertCell(0);
	    var col2=insertRow.insertCell(1);
	    var col3=insertRow.insertCell(2);
	    var col4=insertRow.insertCell(3);
	    var col5=insertRow.insertCell(4);
	    
	    col1.innerHTML = "<input readonly=\"readonly\" size=\"32px\"  class=\"itemName\"    style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemName\" id=\"strItemName."+(rowCount)+"\" value='"+objMenuItemPricingDtl.strModifierName+"' />";
	    col2.innerHTML = "<input readonly=\"readonly\" size=\"3.5px\"   class=\"itemQty\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblQuantity\" id=\"dblQuantity."+(rowCount)+"\" value='"+1.00+"' />";
	    col3.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemAmt\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblAmount\" id=\"dblAmount."+(rowCount)+"\" value='"+objMenuItemPricingDtl.dblRate+"' />";
	    col4.innerHTML = "<input readonly=\"readonly\" size=\"4.3px\" class=\"itemCode\"     style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemCode\" id=\"strItemCode."+(rowCount)+"\" value='"+objMenuItemPricingDtl.strModifierCode+"' />";
	    col5.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemDiscAmt\"  style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strSerialNo\" id=\"strSerialNo."+(rowCount-1)+"\" value='"+rowCount+"' />";
	 
		for(var i=selectedRowIndex+1;i<kotListForModifier.length;i++)
			{
			
		var rowCount = tblBillItemDtl.rows.length;
		var insertRow = tblBillItemDtl.insertRow(rowCount);
		var data=kotListForModifier[i];
		
	    var col1=insertRow.insertCell(0);
	    var col2=insertRow.insertCell(1);
	    var col3=insertRow.insertCell(2);
	    var col4=insertRow.insertCell(3);
	    var col5=insertRow.insertCell(4);
	   
	    
	    col1.innerHTML = "<input readonly=\"readonly\" size=\"32px\"  class=\"itemName\"    style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemName\" id=\"strItemName."+(rowCount)+"\" value='"+data[0]+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	    col2.innerHTML = "<input readonly=\"readonly\" size=\"3.5px\"   class=\"itemQty\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblQuantity\" id=\"dblQuantity."+(rowCount)+"\" value='"+data[1]+"' onclick=\"funChangeQty(this)\"/>";
	    col3.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemAmt\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].dblAmount\" id=\"dblAmount."+(rowCount)+"\" value='"+data[2]+"' />";
	    col4.innerHTML = "<input readonly=\"readonly\" size=\"4.3px\" class=\"itemCode\"     style=\"text-align: left; color:blue;\"   name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strItemCode\" id=\"strItemCode."+(rowCount)+"\" value='"+data[3]+"' />";
	    col5.innerHTML = "<input readonly=\"readonly\" size=\"4px\"   class=\"itemDiscAmt\"  style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(rowCount)+"].strSerialNo\" id=\"strSerialNo."+(rowCount-1)+"\" value='"+rowCount+"' />";
	    	    
			}
		
		
	}
	
	function funMenuItemClicked(objMenuItemButton,objIndex)
	{	
		funFillMapWithHappyHourItems();
	
		var objMenuItemPricingDtl=itemPriceDtlList[objIndex];
		
		var   price = funGetFinalPrice(objMenuItemPricingDtl);
		
		var isOrdered=funIsAlreadyOrderedItem(objMenuItemPricingDtl);
		var qty=prompt("Enter Quantity", 1);
		 if(price==0.00)
			{
			 price = prompt("Enter Price", 0);
			} 
		 
		 if(qty==null || price==null)
			 {
			 	return false;
			 }
		if(isOrdered)
		{
			funUpdateTableBillItemDtlFor(objMenuItemPricingDtl,price,qty);	
		}
		else
		{
			funFillTableBillItemDtl(objMenuItemPricingDtl,price,qty);	
		} 
		
		funFillKOTList();
		funCalculateTax();
	}
	//function on popular item button click
	function funPopularItemButtonClicked(objButton)
	{
		var $rows = $('#tblMenuItemDtl').empty();
		var tblMenuItemDtl=document.getElementById('tblMenuItemDtl');
		var selctedCode=objButton.id;
		flagPopular="Popular";
		funFillTopButtonList(flagPopular);
		var jsonArrForPopularItems=${command.jsonArrForPopularItems};	
		var rowCount = tblMenuItemDtl.rows.length;	
		itemPriceDtlList=new Array();
		var insertCol=0;
		var insertTR=tblMenuItemDtl.insertRow();
		var index=0;
		$.each(jsonArrForPopularItems, function(i, obj) 
		{									
												
				if(insertCol<tblMenuItemDtl_MAX_COL_SIZE)
				{
					index=rowCount*4+insertCol;
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space:normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					insertCol++;
				}
				else
				{		
					rowCount++;
					insertTR=tblMenuItemDtl.insertRow();									
					insertCol=0;
					index=rowCount*4+insertCol;				
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space: normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					insertCol++;
				}	
				itemPriceDtlList[index]=obj;
			
		});
	}
	
	//function on menu Head cliked
	
	function funMenuHeadButtonClicked(objMenuHeadButton)
	{
		var $rows = $('#tblMenuItemDtl').empty();
		var tblMenuItemDtl=document.getElementById('tblMenuItemDtl');
		var selctedMenuHeadCode=objMenuHeadButton.id;
		flagPopular="menuhead";
		funFillTopButtonList(selctedMenuHeadCode);
		var jsonArrForMenuItemPricing=${command.jsonArrForDirectBillerMenuItemPricing};	
		var rowCount = tblMenuItemDtl.rows.length;	
		itemPriceDtlList=new Array();
		var insertCol=0;
		var insertTR=tblMenuItemDtl.insertRow();
		var index=0;
		$.each(jsonArrForMenuItemPricing, function(i, obj) 
		{									
			if(obj.strMenuCode==selctedMenuHeadCode)
			{									
				if(insertCol<tblMenuItemDtl_MAX_COL_SIZE)
				{
					index=rowCount*4+insertCol;
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space:normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					insertCol++;
				}
				else
				{		
					rowCount++;
					insertTR=tblMenuItemDtl.insertRow();									
					insertCol=0;
					index=rowCount*4+insertCol;				
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space: normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					insertCol++;
				}	
				itemPriceDtlList[index]=obj;
			}
		});
	}
	
	
	
	function funFillTopButtonList(menuHeadCode)
	{		
		menucode=menuHeadCode;
		var searchurl=getContextPath()+"/funFillTopButtonList.html?menuHeadCode="+menuHeadCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {	
			        	funAddTopButtonData(response.topButtonList);
			        	
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
	
	function funAddTopButtonData(topButtonList)
	{
		var $rows = $('#tblTopButtonDtl').empty();
		var tblTopButtonDtl=document.getElementById('tblTopButtonDtl');
		var insertCol=0;
		var insertTR=tblTopButtonDtl.insertRow();
		
		$.each(topButtonList, function(i, obj) 
		{		
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strCode+" value="+obj.strName+"    style=\"width: 90px;height: 30px; white-space: normal;\"  onclick=\"funTopButtonClicked(this)\" /></td>";
					insertCol++; 
		});
	}
	
	function funTopButtonClicked(objMenuHeadButton)
	{
		
		var $rows = $('#tblMenuItemDtl').empty();
		var tblMenuItemDtl=document.getElementById('tblMenuItemDtl');
		
		var selctedMenuHeadCode=objMenuHeadButton.id;
	
		itemPriceDtlList=new Array();
		var searchurl=getContextPath()+"/funFillitemsSubMenuWise.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        data:{ strMenuCode:menucode,
			        	flag:flagPopular,
			        	selectedButtonCode:selctedMenuHeadCode,
					},
			        dataType: "json",
			        success: function(response)
			        {	
			        
			        var rowCount = tblMenuItemDtl.rows.length;	
					
					var insertCol=0;
					var insertTR=tblMenuItemDtl.insertRow();
					var index=0;
			    		$.each(response.SubMenuWiseItemList, function(i, obj) 
			    		{									
			    												
			    				if(insertCol<tblMenuItemDtl_MAX_COL_SIZE)
			    				{
			    					index=rowCount*4+insertCol;
			    					var col=insertTR.insertCell(insertCol);
			    					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space:normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
			    					
			    					insertCol++;
			    				}
			    				else
			    				{		rowCount++;	 		
			    					insertTR=tblMenuItemDtl.insertRow();									
			    					insertCol=0;
			    					index=rowCount*4+insertCol;				
			    					var col=insertTR.insertCell(insertCol);
			    					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space: normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
			    					
			    					insertCol++;
			    				}							
			    			
			    				itemPriceDtlList[index]=obj;
			    			  
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
		
	
	function funAddPopularItemsData()
	{
		funFillTopButtonList("Popular");
		var jsonArrForPopularItems=${command.jsonArrForPopularItems};	
		itemPriceDtlList=new Array();
		
		$.each(jsonArrForPopularItems, function(i, obj) 
		{									
				itemPriceDtlList[i]=obj;
		});
	}
	
	function deleteTableRows()
	{
		var table = document.getElementById("tblBillItemDtl");
		var rowCount = table.rows.length;
		while(rowCount>selectedRowIndex+1)
		{
			table.deleteRow(selectedRowIndex+1);
			rowCount--;
		}
	}
	var kotListForModifier = new Array();
	 function funFillKOTList()
	{
		var i=0;
		
		kotListForModifier = new Array();
		arrKOTItemDtlList = new Array();
		 $('#tblBillItemDtl tr').each(function() {
			 var code=$(this).find("input[class='itemCode']").val();
			
			 var itemName=$(this).find("input[class='itemName']").val();
			 var itemQty=$(this).find("input[class='itemQty']").val();
			 var itemAmt=$(this).find("input[class='itemAmt']").val();
			 var itemCode=$(this).find("input[class='itemCode']").val();
			 var itemDiscAmt=$(this).find("input[class='itemDiscAmt']").val();
			    	code=code+"_"+itemName+"_"+itemAmt;
			    	var data=new Array();
			    	 data[0]=itemName;
			    	data[1]=itemQty;
			    	data[2]=itemAmt;
			    	data[3]=itemCode;
			    	data[4]=itemDiscAmt;
			    	kotListForModifier[i]=data; 
			    	
			    	if(i > 0)
		    		{
		    	arrKOTItemDtlList[i-1]=code;
		    	
		    		}
					i++;
			   
				 });
	} 
	
		function funCalculateTax()
		{		
			
			var searchurl=getContextPath()+"/funCalculateTax.html?arrKOTItemDtlList="+arrKOTItemDtlList;
			 $.ajax({
				        type: "POST",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {	
				        	$("#txtTotal").val(response);
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
		
		function CalculateDateDiff(fromDate,toDate) {
			
			var frmDate= fromDate.split('-');
		    var fDate = new Date(frmDate[0],frmDate[1],frmDate[2]);
		    
		    var tDate= toDate.split('-');
		    var t1Date = new Date(tDate[0],tDate[1],tDate[2]);

	    	var dateDiff=t1Date-fDate;
	  		
	        	 return dateDiff;
	        
		}
		
	function  funGetFinalPrice( ob)
    {
        var Price = 0.00;
        var fromTime = ob.tmeTimeFrom;
        var toTime = ob.tmeTimeTo;
        var fromAMPM = ob.strAMPMFrom;
        var toAMPM = ob.strAMPMTo;
        var hourlyPricing = ob.strHourlyPricing;
	var strItemCode=ob.strItemCode;
        if (hmHappyHourItems.has(strItemCode))
        {
            var obHappyHourItem = hmHappyHourItems.get(ob.strItemCode);
            fromTime = obHappyHourItem.tmeTimeFrom;
            toTime = obHappyHourItem.tmeTimeTo;
            fromAMPM = obHappyHourItem.strAMPMFrom;
            toAMPM = obHappyHourItem.strAMPMTo;

            var spFromTime = fromTime.split(":");
            var spToTime = toTime.split(":");
            var fromHour = parseInt(spFromTime[0]);
            var fromMin = parseInt(spFromTime[1]);
            if (fromAMPM=="PM")
            {
                fromHour += 12;
            }
            var toHour = parseInt(spToTime[0]);
            var toMin = parseInt(spToTime[1]);
            if (toAMPM=="PM")
            {
                toHour += 12;
            }
			var spCurrTime = currentTime.split(" ");
            var spCurrentTime = spCurrTime[0].split(":");

            var currHour = parseInt(spCurrentTime[0]);
            var currMin = parseInt(spCurrentTime[1]);
            var currDate = currentDate;
            currDate = currDate + " " + currHour + ":" + currMin + ":00";

            //2014-09-09 23:35:00
            var fromDate = currentDate;
            var toDate = currentDate();
            fromDate = fromDate + " " + fromHour + ":" + fromMin + ":00";
            toDate = toDate + " " + toHour + ":" + toMin + ":00";

            var diff1 = CalculateDateDiff(fromDate, currDate);
            var diff2 = CalculateDateDiff(currDate, toDate);
            if (diff1 > 0 && diff2 > 0)
            {
                switch (dayForPricing)
                {
                    case "strPriceMonday":
                        Price = obHappyHourItem.strPriceMonday;
                        break;

                    case "strPriceTuesday":
                        Price = obHappyHourItem.strPriceTuesday;
                        break;

                    case "strPriceWednesday":
                        Price = obHappyHourItem.strPriceWednesday;
                        break;

                    case "strPriceThursday":
                        Price = obHappyHourItem.strPriceThursday;
                        break;

                    case "strPriceFriday":
                        Price = obHappyHourItem.strPriceFriday;
                        break;

                    case "strPriceSaturday":
                        Price = obHappyHourItem.strPriceSaturday;
                        break;

                    case "strPriceSunday":
                        Price = obHappyHourItem.strPriceSunday;
                        break;
                }
            }
            else
            {
                switch (dayForPricing)
                {
                    case "strPriceMonday":
                        Price = ob.strPriceMonday;
                        break;

                    case "strPriceTuesday":
                        Price = ob.strPriceTuesday;
                        break;

                    case "strPriceWednesday":
                        Price = ob.strPriceWednesday;
                        break;

                    case "strPriceThursday":
                        Price = ob.strPriceThursday;
                        break;

                    case "strPriceFriday":
                        Price = ob.strPriceFriday;
                        break;

                    case "strPriceSaturday":
                        Price = ob.strPriceSaturday;
                        break;

                    case "strPriceSunday":
                        Price = ob.strPriceSunday;
                        break;
                }
            }
        }
        else
        {
            switch (dayForPricing)
            {
                case "strPriceMonday":
                    Price = ob.strPriceMonday;
                    break;

                case "strPriceTuesday":
                    Price = ob.strPriceTuesday;
                    break;

                case "strPriceWednesday":
                    Price = ob.strPriceWednesday;
                    break;

                case "strPriceThursday":
                    Price = ob.strPriceThursday;
                    break;

                case "strPriceFriday":
                    Price = ob.strPriceFriday;
                    break;

                case "strPriceSaturday":
                    Price = ob.strPriceSaturday;
                    break;

                case "strPriceSunday":
                    Price = ob.strPriceSunday;
                    break;
            }
        }

        return Price;
    }
	/**
	* Success Message After Saving Record
	**/
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
			{%>	
				alert("Data Save successfully\n\n"+message);
			<%}
		}%>

	});
	
	
	function funDeleteBtnClicked()
	{
		var table = document.getElementById("tblBillItemDtl");
		
			table.deleteRow(selectedRowIndex);
			funFillKOTList();
		
	}
	function funChgQtyBtnClicked()
	{
		if(selectedRowIndex>0)
		{
		 	var table = document.getElementById("tblBillItemDtl");
			var rowCount = table.rows.length;
			var iteCode=table.rows[selectedRowIndex].cells[1].innerHTML;
		  
		 	var codeArr = iteCode.split('value=');
		    var code=codeArr[1].split('onclick=');
		    var oldQty=code[0].substring(1, (code[0].length-2));
		    
		    var qty=parseFloat(prompt("Enter Quantity", oldQty));
		  //$("#dblQuantity."+selectedRowIndex).val(qty);
		    table.rows[selectedRowIndex].cells[1].innerHTML = "<input readonly=\"readonly\" size=\"3.5px\"   class=\"itemQty\"      style=\"text-align: right; color:blue;\"  name=\"listOfMakeKOTBillItemDtl["+(selectedRowIndex)+"].dblQuantity\" id=\"dblQuantity."+(selectedRowIndex)+"\" value='"+qty+"' onclick=\"funChangeQty(this)\"/>";
		}
		  
	}
	

	function funGetSelectedRowIndex(obj)
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		 var table = document.getElementById("tblBillItemDtl");
		 if((selectedRowIndex>0) && (index!=selectedRowIndex))
		 {
			
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#C0E4FF';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
	         
			
		 }
		 else
		 {
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
		 }
		 
		  var iteCode=table.rows[selectedRowIndex].cells[3].innerHTML;
		  
		  var codeArr = iteCode.split('value=');
		  var code=codeArr[1].split('onclick=');
		  var itemCode=code[0].substring(1, (code[0].length-2));
			funFillTopModifierButtonList(itemCode);
			funLoadModifiers(itemCode);
	}
	
	function funChangeQty(obj)
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		 var table = document.getElementById("tblBillItemDtl");
		 if((selectedRowIndex>0) && (index!=selectedRowIndex))
		 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#C0E4FF';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
		 }
		 else
		 {
			 selectedRowIndex=index;
			 row = table.rows[selectedRowIndex];
			 row.style.backgroundColor='#ffd966';
			 row.hilite = true;
		 }
		 
		  var iteCode=table.rows[selectedRowIndex].cells[3].innerHTML;
		  
		  var codeArr = iteCode.split('value=');
		  var code=codeArr[1].split('onclick=');
		  var itemCode=code[0].substring(1, (code[0].length-2));
		  
		 
		  var qty=prompt("Enter Quantity", obj.value);
		  obj.value=qty;
	}

	function funLoadModifiers(itemCode)
	{		
		var searchurl=getContextPath()+"/funLoadModifiers.html?itemCode="+itemCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {	
			        	funAddModifiersData(response.Modifiers);
			        
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
	
	function funAddModifiersData(Modifiers)
	{
		var $rows = $('#tblMenuItemDtl').empty();
		var tblMenuItemDtl=document.getElementById('tblMenuItemDtl');
		var rowCount = tblMenuItemDtl.rows.length;	
		
		var insertCol=0;
		var insertTR=tblMenuItemDtl.insertRow();
		var colmn=insertTR.insertCell(insertCol);
		
			colmn.innerHTML = "<td><input type=\"button\" id=\"M99\" value='Free Flow Modofier' style=\"width: 100px;height: 100px; white-space:normal;\"  onclick=\"funFreeFlowModifierClicked(this,"+index+")\" /></td>";
			insertCol++;
		var index=0;
		itemPriceDtlList=new Array();
		$.each(Modifiers, function(i, obj) 
		{					
			var name=obj.strModifierName.split(">");
				if(insertCol<tblMenuItemDtl_MAX_COL_SIZE)
				{
					index=rowCount*4+insertCol;
					var col=insertTR.insertCell(insertCol);
					
				//	col.innerHTML = "<td><input type=\"button\" id="+obj.strModifierCode+" value="+obj.strModifierName+"    style=\"width: 100px;height: 100px; white-space:normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					col.innerHTML = "<td><input type=\"button\" id="+obj.strModifierCode+" value='-->"+name[1]+"' style=\"width: 100px;height: 100px; white-space:normal;\"  onclick=\"funModifierClicked(this,"+index+")\" /></td>";
					
					insertCol++;
				}
				else
				{		rowCount++;	 		
					insertTR=tblMenuItemDtl.insertRow();									
					insertCol=0;
					index=rowCount*4+insertCol;				
					var col=insertTR.insertCell(insertCol);
					//col.innerHTML = "<td><input type=\"button\" id="+obj.strModifierCode+" value="+obj.strModifierName+"    style=\"width: 100px;height: 100px; white-space: normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					
					col.innerHTML = "<td><input type=\"button\" id="+obj.strModifierCode+" value='-->"+name[1]+"'    style=\"width: 100px;height: 100px; white-space: normal;\"  onclick=\"funModifierClicked(this,"+index+")\" /></td>";
					
					insertCol++;
				}							
				itemPriceDtlList[index]=obj;
		});
	}
	function funModifierClicked(objMenuItemButton,objIndex)
	{							

		var objMenuItemPricingDtl=itemPriceDtlList[objIndex];
		
	
			funAddModifierTableBillItemDtl(objMenuItemPricingDtl);	
		
		funFillKOTList();
		funCalculateTax();
	}
	
	function funFreeFlowModifierClicked(objMenuItemButton,objIndex)
	{
		var itmName=prompt("Enter Name", "");
		if(itmName.trim().length>0)
			{
			itmName="-->"+itmName;
			var amt=parseFloat(prompt("Enter Amount", 0));
			var jObj={
					strModifierName:itmName,
					dblRate:amt,
					strModifierCode:"M99",
					};
			funAddModifierTableBillItemDtl(jObj);
			funFillKOTList();
			funCalculateTax();
			}
	}
	function funFillTopModifierButtonList(itemCode)
	{		
		
		var searchurl=getContextPath()+"/funFillTopModifierButtonList.html?itemCode="+itemCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {	
			        	funAddTopModifierButtonData(response.topButtonModifier);
			        	
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
	
	function funAddTopModifierButtonData(topButtonList)
	{
		var $rows = $('#tblTopButtonDtl').empty();
		var tblTopButtonDtl=document.getElementById('tblTopButtonDtl');
		var insertCol=0;
		var insertTR=tblTopButtonDtl.insertRow();
		
		$.each(topButtonList, function(i, obj) 
		{		
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strModifierGroupCode+" value="+obj.strModifierGroupShortName+"    style=\"width: 90px;height: 30px; white-space: normal;\"  onclick=\"funTopButtonClicked(this)\" /></td>";
					insertCol++; 
		});
	}
	
	
 /* 	$(document).ready(function()
			{
		 		$(function() {
					
					var items = ${command.jsonArrForDirectBillerMenuItemPricing};
					var jobj='';
					$.each(items, function(i, obj) 
							{
								var itemname = obj.strItemName;
								itemname = itemname.replace(/&#x00A;/gi,' ');
								jobj = jobj + '{"label":"' + itemname + '","value":"' + itemname + '"},';
								
							//	'{"dblMRP":"' + blank + '","intProductCode":"' + blank + '"}'; '{"label:"'+itemname+'","value:"'+obj.strItemCode+'"},';
							});
					jobj=jobj.substring(0, jobj.length - 1);
					var dataItems =$.parseJSON('[' + jobj + ']');
					
				//	[{label:"INDIA",value:"IND"},{label:"INDONESIA",value:"INDO"},{label:"NEPAL",value:"NEP"},{label:"BANGLADESH",value:"BAN"}];
					
					$("#txtSearch").autocomplete({
					source: dataItems,
					
					minLength: 0,
				    minChars: 0,
				    max: 12,
				    autoFill: true,
				    mustMatch: true,
				    matchContains: false,
				    scrollHeight: 220,
					
				}).on('focus', function(event) {
				    var self = this;
					  //  var sgName= $("#txtSubGroup").val();
					    $(self).autocomplete( "search", '');
					});
					 
					 
				});
			}); */
	
 	
/* 	
 	function funGetKeyCode(event,controller) {
 			
 		  var key = event.keyCode;
 		    if(controller=='PLUADD' && key==13 || controller=='PLUADD' && key==9)
 		    {
 		    	if($("#txtSearch").val().trim().length==0 || $("#txtSearch").val()== 0)
 		        {		
 			          alert("Please Select Customer");
 			          $("#txtSearch").focus();
 		          return false;
 		       	}else
 		       		{
 		       			var itemName = $('#txtSearch').val();
 		       			itemName = itemName.replace(new RegExp(' ','g'),'&#x00A;');
 		       			funPLU(itemName);
 		       			$('#txtSearch').focus();
 		       		}
 		    	
 		    } 
 	
 	}
 	
	
 	 var getMenuItemPricingObject = function(itemName) {
 	
 	 	var menuItem='';
 		var itemPriceDtlList=${command.jsonArrForDirectBillerMenuItemPricing};	
 		$.each(itemPriceDtlList, function(i, obj) 
				{
 					if(obj.strItemName==itemName)
 						{
 							menuItem = obj;
 							
 						}
				}); 
 		return menuItem;
			//	return 'abc';
 	};
 	 
 	
	
	function funPLU(itemName)
	{
		funFillMapWithHappyHourItems();
		
		var objMenuItemPricingDtl=getMenuItemPricingObject(itemName);
		
		var   price = funGetFinalPrice(objMenuItemPricingDtl);
		
		var isOrdered=funIsAlreadyOrderedItem(objMenuItemPricingDtl);
		var qty=prompt("Enter Quantity", 1);
		if(isOrdered)
		{
			funUpdateTableBillItemDtlFor(objMenuItemPricingDtl,qty);	
		}
		else
		{
			funFillTableBillItemDtl(objMenuItemPricingDtl,price,qty);	
		} 
		
		funFillKOTList();
		funCalculateTax();
	} */
	
	
	function funSetPLUFoucs()
	{
// 		if(($("#txtSearch").is(':focus')))
// 			{
// 				alert('Hello jio');
				 funPLU();
// 			}
	}
	
	function funPLU()
	{	
		var $rows = $('#tblMenuItemDtl').empty();
		var tblMenuItemDtl=document.getElementById('tblMenuItemDtl');
		var jsonArrForMenuItemPricing=${command.jsonArrForDirectBillerMenuItemPricing};	
		var rowCount = tblMenuItemDtl.rows.length;	
		itemPriceDtlList=new Array();
		var insertCol=0;
		var insertTR=tblMenuItemDtl.insertRow();
		var index=0;
		$.each(jsonArrForMenuItemPricing, function(i, obj) 
		{									
				if(insertCol<tblMenuItemDtl_MAX_COL_SIZE)
				{
					index=rowCount*4+insertCol;
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space:normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					insertCol++;
				}
				else
				{		
					rowCount++;
					insertTR=tblMenuItemDtl.insertRow();									
					insertCol=0;
					index=rowCount*4+insertCol;				
					var col=insertTR.insertCell(insertCol);
					col.innerHTML = "<td><input type=\"button\" id="+obj.strItemCode+" value="+obj.strItemName+"    style=\"width: 100px;height: 100px; white-space: normal;\"  onclick=\"funMenuItemClicked(this,"+index+")\" /></td>";
					insertCol++;
				}	
				itemPriceDtlList[index]=obj;
			
		});
	}

	
</script>

</head>
<body onload="funAddPopularItemsData()">

	<div id="formHeading">
	<label>Direct Biller</label>
	</div>
	<s:form name="DirectBiller" method="POST" commandName="command" action="actionDirectBiller.html?saddr=${urlHits}" >			
			
			<div id="divMain" style=" margin-left: 50px; " >				
				<table  >
					<tr>
						<td>
<%-- 							<label style=" display: inline-block;width: 175px;text-align: left;">Bill No: ${billNo}</label> --%>
							<label style=" display: inline-block;width: 130px;text-align: left;">Date: ${billDate}</label>							
						</td>
						<td><input type="button"  id="Customer" value="Customer"    style="width: 150px;height: 30px; white-space: normal;"   onclick="funFooterButtonClicked(this)"/>																						
						<s:input id="txtSearch" path="" value="" cssClass="searchTextBoxW120px jQKeyboard form-control"  style="width: 32%; "   onkeypress="funGetKeyCode(event,'PLUADD')"  onclick="funSetPLUFoucs()" /></td>
					</tr>					
					<tr>
						<td>
							<!-- <div id="divBillItemDtl" style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px;  overflow-x: scroll; overflow-y: scroll; width: 30%;"> -->
							<div id="divBillItemDtl" style=" border: 1px solid #ccc; height: 450px;  overflow-x: auto; overflow-y: auto; width: 325px;">
								
								<table id="tblBillItemDtl"  cellpadding="0" cellspacing="0" ><!-- class="transTablex" -->
									<tr>
										  <th><input type="button" value="Description" style="width: 220px;" class="tblBillItemDtlColBtnGrp" ></input></th>
										  <th><input type="button" value="Qty" style="width: 47px;" class="tblBillItemDtlColBtnGrp" ></input></th>
										  <th><input type="button" value="Amount" style="width: 55px;" class="tblBillItemDtlColBtnGrp"></input></th>
										  <th><input type="button" value="Item Code" class="tblBillItemDtlColBtnGrp"></input></th>
										  <th><input type="button" value="Sequence No" class="tblBillItemDtlColBtnGrp"></input></th>
									</tr>																	
								</table>
								
							</div>
							<div id="divTotalDtl" style=" border: 1px solid #ccc; height: 50px;  width: 325px;">									
									<table>
								<tr>
				<td><input type="button" id="chgQty" value="CHG QTY" style="width: 60px;height: 35px; " onclick="funChgQtyBtnClicked()"/></td>
				<td><input type="button" id="delete" value="Delete" style="width: 80px;height: 35px; " onclick="funDeleteBtnClicked()"/></td>
				<td>
					&nbsp;&nbsp;&nbsp;<label >TOTAL</label>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>
					<s:input  type="text"  id="txtTotal" path="" cssStyle="width:70px;" cssClass="longTextBox jQKeyboard form-control"  /> 
				</td>
								</tr>
								
								</table>
								</div>
						</td>
						
						<td>
							<div id="divTopButtonDtl" style=" border: 1px solid #ccc; height: 50px;  overflow-x: auto; overflow-y: auto; width: 445px;">									
								
									<table id="tblTopButtonDtl"   cellpadding="0" cellspacing="2">
									</table>
									</div>
						<div id="divItemDtl" style=" border: 1px solid #ccc; height: 425px;  overflow-x: auto; overflow-y: auto; width: 445px;">									
								
									<table id="tblMenuItemDtl"   cellpadding="0" cellspacing="5">
									 <c:set var="sizeOfMenuItems" value="${fn:length(command.jsonArrForPopularItems)}"></c:set>									   
									   <c:set var="itemCounter" value="${0}"></c:set>									   									   					
										<%-- ${varMenuItemStatus.getIndex() ${varMenuItemStatus.count} ${sizeOfMenuItems} --%>																			   									  
									   <c:forEach var="objItemPriceDtl" items="${command.jsonArrForPopularItems}"  varStatus="varMenuItemStatus">																																		
												<tr>
												<%
													for(int x=0; x<4; x++)
													{
												%>														
														<c:if test="${itemCounter lt sizeOfMenuItems}">	
																
																	
																<td><input type="button" id="${command.jsonArrForPopularItems[itemCounter].strItemCode}"  value="${command.jsonArrForPopularItems[itemCounter].strItemName}" style="width: 100px; height: 100px; "  onclick="funMenuItemClicked(this,${itemCounter})" /></td>																				
															
																
															<c:set var="itemCounter" value="${itemCounter +1}"></c:set>
															</c:if>	
												
												<%  }
												%>
											   </tr>																																
										</c:forEach>	
									</table>
								</div>
								<div style="width: 445px; height: 25px;">
								<table><tr><td><label style="display:none;" id="lblDpName">Delivery Boy : </label></td><td><label id="dpName"></label></td></tr></table>
								</div> 
								
								
						</td>
						<td>								
								<div id="divMenuHeadDtl" style=" border: 1px solid #ccc; height: 500px;  overflow-x: auto; overflow-y: auto; width: 130px;">									
									<table id="tblMenuHeadDtl"  cellpadding="0" cellspacing="5"  > <!-- class="table table-striped table-bordered table-hover" -->
									 <tr>
									 <td><input type="button" id="PopularItem" value="POPULAR ITEM"  style="width: 100px;height: 50px; white-space: normal;"  onclick="funPopularItemButtonClicked(this)" /></td>
									 </tr>
									  <c:forEach var="objMenuHeadDtl" items="${command.jsonArrForDirectBillerMenuHeads}"  varStatus="varMenuHeadStatus">																																		
												<tr>																							 													
													<td><input type="button"  id="${objMenuHeadDtl.strMenuCode}" value="${objMenuHeadDtl.strMenuName}"    style="width: 100px;height: 50px; white-space: normal;"   onclick="funMenuHeadButtonClicked(this)"/></td>														
											   </tr>																																
										</c:forEach>									   				   									   									   							
									</table>
								</div>
						</td>
					</tr>
				</table>
		
				<div style="text-align: right;">
				<!-- <div id="divBottomButtonsNavigator" style="border: 1px solid #ccc; height: 40px;  overflow-x: auto; overflow-y:; width: 615px; "> -->
				 	<table id="tblFooterButtons"  cellpadding="0" cellspacing="2"  > <!-- class="table table-striped table-bordered table-hover" -->				 																																	
							<tr>							
								<c:forEach var="objFooterButtons" items="${command.jsonArrForDirectBillerFooterButtons}"  varStatus="varFooterButtons">								
										<td><input  type="button" id="${objFooterButtons}"  value="${objFooterButtons}" tabindex="${varFooterButtons.getIndex()}"  style="width: 100px;height: 35px; white-space: normal;"   onclick="funFooterButtonClicked(this)"/></td>																									   																															
								</c:forEach>																						
						    </tr>																																				 									   				   									   									   						
					</table>			
		 		<!-- </div> -->		 				 																						
			</div>
			</div>
		
	</s:form>
</body>
</html>
