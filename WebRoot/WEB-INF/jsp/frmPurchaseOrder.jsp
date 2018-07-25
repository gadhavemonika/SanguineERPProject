<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Order</title>

<script type="text/javascript">
		
	/**
	* Global variable
	**/
	var taxPer=0,listRow=0,gPIQty=0,gPICode="";
	
	/**
	* Ready function for Tab
	**/
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
		
			var clientCode='<%=session.getAttribute("clientCode").toString()%>';
			if(clientCode!='226.001')
			{}
			
			/**
			 * Ready Function for Ajax Waiting and reset form
			 */
			$(document).ajaxStart(function(){
			    $("#wait").css("display","block");
			});
			$(document).ajaxComplete(function(){
			   	$("#wait").css("display","none");
			});
			  
			$("#btnAddTC").click(function( event )
			{
				funAddTCRows();
			});
			
			if($("#txtPICode").val()!='')
			{
				funSetPurchaseIndent();
			}
			funOnChangeCurrency();
		});
		
			
		/**
		 * Ready Function for Tax
		 */
		$(function()
		{
			$("#btnAddTax").click(function()
			{
				if($("#txtTaxCode").val()=='')
			    {
					alert("Please Enter Tax Code");
			   		$("#txtTaxCode").focus();
			       	return false;
				}
				else
				{
					funAddTaxRow();
				}
			});
			
			$("#btnGenTax").click(function()
			{
				if($("#txtSuppCode").val().trim()=='')
				{
					alert('Please Select Supplier!!!');
					return false;
				}
				funCalculateIndicatorTax();
			});
			
			$('#txtTaxableAmt').blur(function () 
			{
				funCalculateTaxForSubTotal(parseFloat($("#txtTaxableAmt").val()),taxPer);
			});
			
			
		
		});
		
		/**
		 * Calculating Tax per Tax indicator 
		 */
		function funCalculateIndicatorTax()
		{
			var prodCodeForTax="";
			funRemoveTaxRows();
			var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    
			$('#tblProdDet tr').each(function(){
				
		    	var prodCode= $(this).find(".txtProdCode").val();
		    	var suppCode=$("#txtSuppCode").val();
		    	var discPer=0;
		    	if($("#txtDiscPer").val()!='')
		    	{
		    		discPer=parseFloat($("#txtDiscPer").val());
		    	}
		    	
		    	var discAmt=parseFloat($(this).find(".txtDisc").val());
		    	var qty=parseFloat($(this).find(".QtyCell").val());
		    	var unitPrice=parseFloat($(this).find(".price").val());
		    	prodCodeForTax=prodCodeForTax+"!"+prodCode+","+unitPrice+","+suppCode+","+qty+","+discAmt;
		    });
			
		    prodCodeForTax=prodCodeForTax.substring(1,prodCodeForTax.length).trim();
		    var dteOP = $("#txtPODate").val();
		    var arrdtOP=dteOP.split("-");
		    dteOP=arrdtOP[2]+"-"+arrdtOP[1]+"-"+arrdtOP[0];
			var CIFAmt=$("#txtCIF").val();
			var subTotal=$("#txtSubTotal").val();
			
		    gurl=getContextPath()+"/getTaxDtlForProduct.html?prodCode="+prodCodeForTax+"&taxType=Purchase&transDate="+dteOP+"&CIFAmt="+CIFAmt;
			
		    $.ajax({
				type: "GET",
			    url:gurl,
			    dataType: "json",
			    success: function(response)
			    {
			       	$.each(response, function(i,item)
				   	{
			        	var spItem=item.split('#');
			        	if(spItem[1]=='null')
			        	{
			        	}
			        	else
					   	{
			        		var dblExtraCharge= $("#txtExtraCharges").val();
			        		if(dblExtraCharge !=null || dblExtraCharge.length > 0){
			        			dblExtraCharge = parseFloat(dblExtraCharge);
			        		}else{
			        			dblExtraCharge="0.0";
			        		}
			        		
			        		var taxableAmt=parseFloat(spItem[0]);
				       		var taxCode=spItem[1];
					       	var taxDesc=spItem[2];
					       	var taxPer1=parseFloat(spItem[4]);
					       	var taxAmt=parseFloat(spItem[5]);
					       	taxAmt=taxAmt.toFixed(2);
					       	taxableAmt=taxableAmt.toFixed(2);
					      
					       	funAddTaxRow1(taxCode,taxDesc,taxableAmt,taxAmt);
					   	}
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
		
		/**
		 * set Tax data in grid
		 */
		function funSetTax(code)
		{
			$.ajax({
			   		type: "GET",
			        url: getContextPath()+"/loadTaxMasterData.html?taxCode="+code,
			        dataType: "json",
			        success: function(response)
			        {
			        	var finalAmt=  $("#txtFinalAmt").val();
			        	finalAmt = parseFloat(finalAmt);
			        	var extraCharges = $("#txtExtraCharges").val();
			        	extraCharges = parseFloat(extraCharges);
			        	var taxCalultionAmt = finalAmt - extraCharges;
			        	$("#txtTaxCode").val(code);
			        	$("#lblTaxDesc").text(response.strTaxDesc);
			        	$("#txtTaxableAmt").val(taxCalultionAmt);
			        	$("#txtTaxableAmt").focus();
			        	taxPer=parseFloat(response.dblPercent);
			        	funCalculateTaxForSubTotal(parseFloat($("#txtTaxableAmt").val()),taxPer);
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
		
		
		/**
		 * Calculating Tax subtotal amount
		 */
		function funCalculateTaxForSubTotal(taxableAmt,taxPercent)
		{
			var taxAmt=(taxableAmt*taxPercent/100);
			taxAmt=taxAmt.toFixed(2);
			$("#txtTaxAmt").val(taxAmt);
		}
		
		/**
		 * Adding Tax in Grid
		 */
		function funAddTaxRow() 
		{
			var taxCode = $("#txtTaxCode").val();
			var taxDesc=$("#lblTaxDesc").text();
		    var taxableAmt = $("#txtTaxableAmt").val();
		    var taxAmt=$("#txtTaxAmt").val();
	
		    var table = document.getElementById("tblTax");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount)+"\" value='"+taxCode+"' >";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+taxDesc+"'>";		    	    
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxableAmt\" id=\"txtTaxableAmt."+(rowCount)+"\" value="+taxableAmt+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxAmt\" id=\"txtTaxAmt."+(rowCount)+"\" value="+taxAmt+">";		    
		    row.insertCell(4).innerHTML= '<input type="button" size=\"6%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		    
		    funCalTaxTotal();
		    funClearFieldsOnTaxTab();
		    
		    return false;
		}
		/**
		 * Filling Tax in Grid
		 */
		function funAddTaxRow1(taxCode,taxDesc,taxableAmt,taxAmt) 
		{	
		    var table = document.getElementById("tblTax");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		 
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount)+"\" value='"+taxCode+"' >";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+taxDesc+"'>";		    	    
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxableAmt\" id=\"txtTaxableAmt."+(rowCount)+"\" value="+taxableAmt+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listPOTaxDtl["+(rowCount)+"].strTaxAmt\" id=\"txtTaxAmt."+(rowCount)+"\" value="+taxAmt+">";		    
		    row.insertCell(4).innerHTML= '<input type="button" size=\"6%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		    
		    funCalTaxTotal();
		    funClearFieldsOnTaxTab();
		    
		    return false;
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


		
		
		/**
		 * Calculating Total Tax 
		 */
		function funCalTaxTotal()
		{
			var totalTaxAmt=0,totalTaxableAmt=0;
			var table = document.getElementById("tblTax");
			var rowCount = table.rows.length;
			for(var i=0;i<rowCount;i++)
			{
				totalTaxableAmt=parseFloat(document.getElementById("txtTaxableAmt."+i).value)+totalTaxableAmt;
				totalTaxAmt=parseFloat(document.getElementById("txtTaxAmt."+i).value)+totalTaxAmt;
			}
			
			totalTaxableAmt=totalTaxableAmt.toFixed(2);
			totalTaxAmt=totalTaxAmt.toFixed(2);
			var grandTotal=parseFloat(totalTaxableAmt)+parseFloat(totalTaxAmt);
			grandTotal=grandTotal.toFixed(2);
			$("#lblTaxableAmt").text(totalTaxableAmt);
			$("#lblTaxTotal").text(totalTaxAmt);
			$("#lblPOGrandTotal").text(grandTotal);
			$("#txtPOTaxAmt").val(totalTaxAmt);
			
			var subTotal=parseFloat($("#txtSubTotal").val());
			var disAmt = $('#txtDisc').val();
			var extCharge = $('#txtExtraCharges').val();
			
			var freightAmt=$("#txtFreight").val();
			var insuranceAmt=$("#txtInsurance").val();
			var otherChargesAmt=$("#txtOtherCharges").val();
			var otherCharges=parseFloat(freightAmt)+parseFloat(insuranceAmt)+parseFloat(otherChargesAmt);
			
			var finalAmt=parseFloat(subTotal)+parseFloat(otherCharges)+parseFloat(totalTaxAmt)+parseFloat(extCharge)-parseFloat(disAmt);
			$("#txtFinalAmt").val(finalAmt.toFixed(maxAmountDecimalPlaceLimit));
			$("#lblPOGrandTotal").text(finalAmt.toFixed(maxAmountDecimalPlaceLimit));
		}
		
		/**
		 * Reset Tax field
		 */
		function funClearFieldsOnTaxTab()
		{
			$("#txtTaxCode").val("");
			$("#lblTaxDesc").text("");
			$("#txtTaxableAmt").val("");
			$("#txtTaxAmt").val("");
			$("#txtTaxCode").focus();
		}
		
		/**
		 * Delete a particular tax form grid
		 */
		function funDeleteTaxRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblTax");
			table.deleteRow(index);
			funCalTaxTotal();
		}
		/**
		 * Remove all tax form grid 
		 */
		function funRemoveTaxRows()
		{
			var table = document.getElementById("tblTax");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		var fieldName,gUOM,gSuppCode,gSuppName;
		var Supprow;
	 	
		/**
		 * open Help for purchase indent
		 */
		function funOpenHelp()
		{
			if ($("#cmbAgainst").val() == 'Purchase Indent')
				{
					var PICode=$('#cmbPIDoc').val();
					if(PICode.trim().length>0)
						{
							fieldName = "prodforPI";
							transactionName=fieldName;
						//	window.showModalDialog("searchform.html?formname="+transactionName+"&PICode="+PICode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
							window.open("searchform.html?formname="+transactionName+"&PICode="+PICode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
						}
				}
			else
				{
					funHelp("productInUse");
				}
		}
		
		/**
		 * Open help windows
		 */
		function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
		}
		
		/**
		 * Open Against windows
		 */
		function funOpenPIforPO(transactionName)
		{
			fieldName = transactionName;
			if(fieldName=='PICode')
			{
			//	var retval=window.showModalDialog("frmPIforPO.html","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
				var retval=window.open("frmPIforPO.html","","dialogHeight:950px;dialogWidth:600px;dialogLeft:400px;")
			
				var timer = setInterval(function ()
			    {
					if(retval.closed)
					{
						if (retval.returnValue != null)
						{
							strVal=retval.returnValue.split("#")
							$("#txtPICode").val(strVal[0]);
								
							var PICodes=strVal[0].split(",");
						    var html = '';
							for(var cnt=0;cnt<PICodes.length;cnt++)
							{
						 		html += '<option value="' + PICodes[cnt] + '" >' + PICodes[cnt]+ '</option>';
							}
							$('#cmbPIDoc').html(html);
						}
						clearInterval(timer);
					}
			    }, 500);
			}
		}
		
		/**
		 * Open Supplier help windows
		 */
		function funHelp1(row,transactionName)
		{
			Supprow=row;
			fieldName = transactionName;
			if(transactionName=='suppcode1')
			{
				transactionName="suppcode";
			}
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		/**
		 * Apply Number Textfield Validation
		 */
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit, negative: false });
		}
		
		/**
		 * Set Data after selecting form Help windows
		 */
		function funSetData(code)
		{
			switch (fieldName)
			{
			    case 'productInUse':
			    	funSetProduct(code);
			        break;
			        
			    case 'prodforPI':
			    	funSetProductForPI(code);
			        break;   
			        
			    case 'suppcodeActive':
			    	funSetSupplier(code);
			        break;
			        
			    case 'suppcode1':
			    	funSetSupplier1(code,Supprow);
			        break; 
			        
			    case 'purchaseorder':
			    	funSetPurchaseOrder(code);
			        break;
			        
			    case 'tcForSetup':
			    	funSetTCFields(code);
			        break;
			        
			    case 'OpenTaxesForPurchase':
			    	funSetTax(code);
			    	break;
			    	
			}
		}
		
		/**
		 * Set purchase order data
		 */
		function funSetPurchaseOrder(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadPOData.html?POCode="+code;
			$.ajax({
				 type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
					    if("Invalid Code" == response.strPOCode){
					    	alert("Invalid PO Code");
					    	$("#txtPOCode").val('');
					    }	
					    else
					    {
					    	$("#txtPOCode").val(response.strPOCode);
					    	$("#txtOurRef").val(response.strCode);
					    	$("#txtPODate").val(response.dtPODate);
					    	$("#txtSuppCode").val(response.strSuppCode);
					    	$("#txtDelDate").val(response.dtDelDate);
					    	$("#cmbAgainst").val('');
					    	$("#cmbAgainst").val(response.strAgainst);

					    	$("#txtPICode").val(response.strSOCode); 
					    	//funOnChange();
					    	$("#txtPayDate").val(response.dtPayDate);
					    	$("#cmbPayMode").val(response.strPayMode);
					    	$("#cmbCurrency").val(response.strCurrency);
					    	$("#txtYourRef").val(response.strYourRef);
					    	$("#txtSubTotal").val(response.dblTotal.toFixed(maxAmountDecimalPlaceLimit));
					    	$("#txtPermRef").val(response.strPerRef);
					    	if("No"!=response.strClosePO)
					    	{
					    		$("#chkShortClosePO").prop('checked', true);
					    	}
					    	else
					    	{
					    		$("#chkShortClosePO").prop('checked', false);
					    	}
					    	//$("#txtExtraCharges").val(response.dblExtra.toFixed(maxAmountDecimalPlaceLimit));
					    	$("#txtExtraCharges").val(response.dblExtra);
					    	$("#txtDisc").val(response.dblDiscount.toFixed(maxAmountDecimalPlaceLimit));
					    	$("#txtFinalAmt").val(response.dblFinalAmt.toFixed(maxAmountDecimalPlaceLimit));
					    	$("#txtBAddress1").val(response.strVAddress1);
					    	$("#txtBAddress2").val(response.strVAddress2);
					    	$("#txtBCity").val(response.strVCity);
					    	$("#txtBState").val(response.strVState);
					    	$("#txtBCountry").val(response.strVCountry);
					    	$("#txtBPin").val(response.strVPin);
					    	$("#txtSAddress1").val(response.strSAddress1);
					    	$("#txtSAddress2").val(response.strSAddress2);
					    	$("#txtSCity").val(response.strSCity);
					    	$("#txtSState").val(response.strSState);
					    	$("#txtSCountry").val(response.strSCountry);
					    	$("#txtSPin").val(response.strSPin);
					    	$("#txtFOB").val(response.dblFOB);
					    	$("#txtFreight").val(response.dblFreight);
					    	$("#txtInsurance").val(response.dblInsurance);
					    	$("#txtOtherCharges").val(response.dblOtherCharges);
					    	$("#txtCIF").val(response.dblCIF);
					    	$("#txtClearingAgentCharges").val(response.dblClearingAgentCharges);
					    	$("#txtVATClaim").val(response.dblVATClaim);
					    	$("#cmbCurrency").val(response.strCurrency);
					    	$("#txtDblConversion").val(response.dblConversion);
					    	
					    	funGetProdData1(response.listPODtlModel);
					    	funGetTC(response.listTCMaster);
					    	funSetPOTaxDtl(response.listPOTaxDtl);

					    	var disAmt=response.dblDiscount;
					    	var SubTotal=$("#txtSubTotal").val();
					    	var disPer=funShowDisCountPer(SubTotal,disAmt);
					    	$("#txtDiscPer").val(disPer);

					    	$("#txtProdCode").focus();
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
		
		/**
		 * Set purchase order tax Data
		 */
		function funSetPOTaxDtl(respPOTaxDtl)
		{
			funRemoveTaxRows();
			$.each(respPOTaxDtl, function(i,item)
            {
				funAddTaxRow1(respPOTaxDtl[i].strTaxCode,respPOTaxDtl[i].strTaxDesc
					,respPOTaxDtl[i].strTaxableAmt,respPOTaxDtl[i].strTaxAmt);
            });
		}
		
		function funGetProdData1(response)
		{
        	funRemoveProductRows();
        	var count=0;
			$.each(response, function(i,item)
            {	
				count=i;
                funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].strUOM,
				response[i].strSuppCode,response[i].strSuppName,response[i].dblOrdQty,response[i].dblWeight,
				response[i].dblTotalWt,response[i].dblPrice,response[i].dblDiscount,response[i].dblAmount,
				response[i].strRemarks,response[i].strPICode,response[i].strUpdate);                                                   
            });
			listRow=count+1;
		}
		
		
		/**
		 * filling purchase order data in grid
		 */
		function funfillProdRow(strProdCode,strProdName,strUOM,strSuppCode,SupplierName,dblOrdQty,dblWeight,dblTotalWeight,dblPrice,dblDiscount,dblAmount,strRemarks,PICode,strUpdate)
		{
			var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);		    
		    row.insertCell(0).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
		    row.insertCell(1).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
		    row.insertCell(2).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"2%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";		   
		    row.insertCell(3).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strSuppCode\"  required =\"required\" class=\"Box SCode\" size=\"5%\" id=\"txtSuppCode."+(rowCount)+"\" value='"+strSuppCode+"' onblur=\"funCheckSupplier(this)\"/>";
		    row.insertCell(4).innerHTML= "<input id=btnSup"+rowCount+" type=button   onclick=funHelp1("+(rowCount)+",'suppcode1') value=...>";        
		    row.insertCell(5).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strSuppName\"  readonly=\"readonly\" class=\"Box SName\" size=\"10%\" id=\"txtSuppName."+(rowCount)+"\" value='"+SupplierName+"'/>";
		    row.insertCell(6).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblOrdQty\"  step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto QtyCell\" id=\"txtOrdQty."+(rowCount)+"\" value="+dblOrdQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
		    row.insertCell(7).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblWeight\"  step=\"any\" required = \"required\" style=\"decimal-places text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
		    row.insertCell(8).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
		    row.insertCell(9).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblPrice\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto price\" id=\"txtPrice."+(rowCount)+"\" value="+dblPrice+" onblur=\"Javacsript:funUpdatePrice(this)\">";
		    row.insertCell(10).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblDiscount\"  step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto txtDisc\" id=\"txtDiscount."+(rowCount)+"\" value="+dblDiscount+" onblur=\"Javacsript:funCalDiscountItemWise(this)\">";	    
		    row.insertCell(11).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box1 totalValueCell\" size=\"5%\" id=\"txtAmount."+(rowCount)+"\" value="+dblAmount.toFixed(maxAmountDecimalPlaceLimit)+" >";
		    row.insertCell(12).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strRemarks\" size=\"15%\" id=\"txtRemarks."+(rowCount)+"\"  value='"+strRemarks+"' >";
		 	row.insertCell(13).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strPICode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtPICode."+(rowCount)+"\" value='"+PICode+"' >";
		    row.insertCell(14).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strUpdate\" readonly=\"readonly\" class=\"Box\" size=\"3%\" id=\"txtUpdate."+(rowCount)+"\" value="+strUpdate+" >";
		    row.insertCell(15).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javascript:funDeleteRow(this)">';		
		    funApplyNumberValidation();
		}
		
		function funGetTC(response)
		{
			funDeleteTCTableAllRows();
			$.each(response, function(i,item)
            {				
            	funFillTC(response[i].strTCName,response[i].strTCDesc,response[i].strTCCode);
            });
		}
		
		/**
		 * Filling terms and condition in purchase order
		 */
		function funFillTC(strTCName,strTCDesc,strTCCode)
		{
			var table = document.getElementById("tblTermsAndCond");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" name=\"listTCMaster["+(rowCount)+"].strTCName class=\"Box\" type=\"text\" size=\"50%\" value='"+strTCName+"' />";
		    row.insertCell(1).innerHTML= "<input type=\"text\" size=\"50%\"	name=\"listTCMaster["+(rowCount)+"].strTCDesc\" class=\"longTextBox\" value='"+strTCDesc+"'  />";
		    row.insertCell(2).innerHTML= "<input type=\"hidden\" name=\"listTCMaster["+(rowCount)+"].strTCCode\" value='"+strTCCode+"' />";
		    row.insertCell(3).innerHTML= '<input  class="deletebutton"	value="Delete" onClick="Javacsript:funDeleteTCRow(this)">';
		}
		
		/**
		 * Get Pending Purchase Indent data
		 */
		function funSetProductForPI(code)
		{
			var searchUrl="";
			var PICode=$('#cmbPIDoc').val();
			searchUrl=getContextPath()+"/loadProductDataForPITrans.html?prodCode="+code+"&PICode="+PICode;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response!="Invalid Product Code")
				    	{
							$("#txtProdCode").val(response[0][0]);
							$("#lblProdName").text(response[0][1]);
							$("#txtPrice").val(response[0][8]);
							$("#cmbUOM").val(response[0][2]);
							if($("#txtSuppCode").val()!="")
							{
								gSuppCode=$("#txtSuppCode").val();
								gSuppName=$("#lblSupplierName").text();
								$("#txtSuppCode").prop('disabled',true);
							}
							else
							{
								gSuppCode=response[0][3];
								gSuppName=response[0][4];
							}
							$("#txtWeight").val(response[0][6]);
							gPIQty=response[0][7];
							gPICode=response[0][9];
							
							var strProdCode=response[0][0];
							var table = document.getElementById("tblProdDet");
						    var rowCount = table.rows.length;		   
						    var totalQty=0;
						    if(rowCount > 0)
						    	{
							    	$('#tblProdDet tr').each(function() 
							    	{
								    	var prodCode = $(this).find(".prodCode").val(); 
									    var totalQtyCell = $(this).find(".QtyCell").val(); 
									    var GridPIcode = $(this).find(".PICode").val();
									    if(prodCode==strProdCode && gPICode==GridPIcode)
									    	{
									    		totalQty=parseFloat(totalQtyCell)+parseFloat(totalQty);
									    	}
									});
						    	}
						    
						    var POQty=parseFloat(gPIQty)-parseFloat(totalQty);
						    $("#txtOrderQty").val(POQty);
							$("#txtOrderQty").focus();
							
					    }
				    	else
				    	{
				    		alert("Invalid Product Code");
				    		$("#txtProdCode").val('')
				    		$("#txtProdCode").focus();
				    		return false;
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
		
		/**
		 * Get and Set Product data
		 */
		function funSetProduct(code)
		{
				var searchUrl="";
				var suppCode=$("#txtSuppCode").val();
				var currCode=$("#cmbCurrency").val();
				//var currValue=funGetCurrencyCode(currCode);
				var currValue=$("#txtDblConversion").val();
	    		if(currValue==null ||currValue==''||currValue==0)
	    		{
	    		  currValue=1;
	    		}
				searchUrl=getContextPath()+"/loadProductDataForTrans.html?prodCode="+code+"&suppCode="+suppCode;
				//alert(searchUrl);
				$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	if(response!="Invalid Product Code")
					    	{
								$("#txtProdCode").val(response[0][0]);
								$("#lblProdName").text(response[0][1]);
								$("#txtPrice").val(parseFloat(response[0][3]/currValue).toFixed(maxAmountDecimalPlaceLimit));
								$("#cmbUOM").val(response[0][2]);
								if($("#txtSuppCode").val()!="")
								{
									gSuppCode=$("#txtSuppCode").val();
									gSuppName=$("#lblSupplierName").text();
									$("#txtSuppCode").prop('disabled',true);
								}
								else
								{
									gSuppCode=response[0][4];
									gSuppName=response[0][5];
								}
								$("#txtWeight").val(response[0][7]);
								$("#txtOrderQty").focus();
								
								var searchUrl1=getContextPath()+"/loadProdRateFromRateContractor.html?suppCode="+gSuppCode+"&prodCode="+response[0][0];
								$.ajax({
								        type: "GET",
								        url: searchUrl1,
									    dataType: "json",
									    success: function(response)
									    {
									    	$("#txtPrice").val(response[0][1]/currValue);
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
					    	else
					    	{
					    		alert("Invalid Product Code");
					    		$("#txtProdCode").val('')
					    		$("#txtProdCode").focus();
					    		return false;
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
		
		  
		/**
		 * Get and set Terms and condition for supplier in purchase order
		 */
		function funSetTCForSupplier(suppCode)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadTCForSupplier.html?partyCode="+suppCode;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funDeleteTCTableAllRows();
				    	$.each(response, function(i,item)
						{
				    		//alert(response[i][1]);
							funAddTCForSupplier(response[i][0],response[i][1],response[i][2]);
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
		
		
		/**
		 * Get and Set Supplier data
		 */
		function funSetSupplier(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strPCode){
				    		alert('Invalid Code');	
					    	$("#txtSuppCode").val('');
					    	$("#lblSupplierName").text('');
					    	$("#txtSuppCode").focus();
				    	}
				    	else{
				    		
					    	$("#txtSuppCode").val(response.strPCode);
					    	$("#lblSupplierName").text(response.strPName);
					    	$("#txtProdCode").focus();
				 		    $(".SName").val(response.strPName);
					    	$(".SCode").val(response.strPCode);
					    	$("#cmbCurrency").val(response.strCurrency);
					    	funOnChangeCurrency();
				 		    funSetTCForSupplier(response.strPCode);
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
		
		/**
		 * Get and Set Supplier data
		 */
		function funSetSupplier1(code,Supprow)
		{
			var searchUrl="";
			document.all("txtSuppCode."+Supprow).value=code;
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			//alert("hi");
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.strPCode!="Invalid Code")
				    	{
				    		document.getElementById("txtSuppName."+Supprow).value=response.strPName;
				    	}
				    	else
				    	{
				    		alert("Invalid Supplier Code");
				    		return false;
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
		/**
		 * Get and Set purchase indent Details data
		 */
		function funSetPurchaseIndent()
		{
			var docCodes='';
			if($("#txtPICode").val().length!=0)
			{
				funRemoveProductRows();
				docCodes=$("#txtPICode").val();
				var count=0;
				var searchUrl=getContextPath()+"/loadPurchaseIndentDtl.html?PICode="+docCodes;
						$.ajax({
						        type: "GET",
						        url: searchUrl,
							    dataType: "json",
							    success: function(response)
							    {
							    	$.each(response, function(i,item)
									{
							    		count=i;
										funAddProductFromPI(response[i][0],response[i][5],response[i][6],response[i][7]
										,response[i][8],response[i][1],response[i][3],response[i][2],response[i][4]);
									});
							    	listRow=count+1;
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
			else
			{
				alert("Please Enter PICode");
				return false;
			}
		}
			
		/**
		 * Get and Set Terms and condition from setup
		 */
		function funSetTCFields(code)
		{
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadTCForSetup.html?tcCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	$("#txtTCCode").val(response.strTCCode);
			        $("#lblTCName").text(response.strTCName);
			        $("#txtTCDesc").focus();
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
		
		/**
		 * Add Terms and condition in grid
		 */
		function funAddTCRows()
		{
			var table = document.getElementById("tblTermsAndCond");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var tcCode=$("#txtTCCode").val();
		    var tcName=$("#lblTCName").text();
		    var tcDesc=$("#txtTCDesc").val();
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listTCMaster["+(rowCount)+"].strTCName\" id=\"txtTCName."+(rowCount)+"\" value='"+tcName+"' />";
		    row.insertCell(1).innerHTML= "<input class=\"longTextBox\" size=\"20%\" name=\"listTCMaster["+(rowCount)+"].strTCDesc\" id=\"txtTCDesc."+(rowCount)+"\" value='"+tcDesc+"' />";
		    row.insertCell(2).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"Box\" name=\"listTCMaster["+(rowCount)+"].strTCCode\" id=\"txtTCCode."+(rowCount)+"\" value='"+tcCode+"' />";
		    row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTCRow(this)">';
		    funResetTCFields();
		}
		
		/**
		 * Add Terms and condition in grid for supplier
		 */
		function funAddTCForSupplier(tcCode,tcName,tcDesc)
		{	
			var table = document.getElementById("tblTermsAndCond");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listTCMaster["+(rowCount)+"].strTCName\" id=\"txtTCName."+(rowCount)+"\" value='"+tcName+"' />";
		    row.insertCell(1).innerHTML= "<input class=\"longTextBox\" size=\"20%\" name=\"listTCMaster["+(rowCount)+"].strTCDesc\" id=\"txtTCDesc."+(rowCount)+"\" value='"+tcDesc+"' />";
		    row.insertCell(2).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"Box\" name=\"listTCMaster["+(rowCount)+"].strTCCode\" id=\"txtTCCode."+(rowCount)+"\" value='"+tcCode+"' />";
		    row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTCRow(this)">';		    
		}
		
		/**
		 * Delete a particular Terms and condition form grid 
		 */
		function funDeleteTCRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblTermsAndCond");
		    table.deleteRow(index);
		}
		/**
		 * Reset Terms and condition form Textfield 
		 */
		function funResetTCFields() 
		{
		    $("#txtTCCode").val("");
		    $("#lblTCName").text("");
		    $("#txtTCDesc").val("");
		}
		/**
		 * Delete a All Terms and condition form grid 
		 */
		function funDeleteTCTableAllRows()
		{
			var table = document.getElementById("tblTermsAndCond");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}		
		}
		
		/**
		 * Filling Purchase indent data in grid
		 */
		function funAddProductFromPI(strProdCode,strProdName,strUOM,strSuppCode	,SupplierName,dblOrdQty,dblWeight,dblPrice,PICode)
		{
			var dblDiscount=0;		    
		    var dblTotalWeight=dblWeight*dblOrdQty;
		    var amount=(dblOrdQty*dblPrice)-dblDiscount;
		    
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);		    
		   
		    row.insertCell(0).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
		    row.insertCell(1).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
		    row.insertCell(2).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"2%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";		   
		    row.insertCell(3).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strSuppCode\"  required =\"required\" class=\"Box SCode\" size=\"5%\" id=\"txtSuppCode."+(rowCount)+"\" value='"+strSuppCode+"' onblur=\"funCheckSupplier(this)\"/>";
		    row.insertCell(4).innerHTML= "<input id=btnSup"+rowCount+" type=button   onclick=funHelp1("+(rowCount)+",'suppcode1') value=...>";        
		    row.insertCell(5).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strSuppName\"  readonly=\"readonly\" class=\"Box SName\" size=\"10%\" id=\"txtSuppName."+(rowCount)+"\" value='"+SupplierName+"'/>";
		    row.insertCell(6).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblOrdQty\"  step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto QtyCell\" id=\"txtOrdQty."+(rowCount)+"\" value="+ parseFloat(dblOrdQty).toFixed(maxQuantityDecimalPlaceLimit)+" onblur=\"Javacsript:funUpdatePrice(this)\">";
		    row.insertCell(7).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblWeight\"  step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+parseFloat(dblWeight).toFixed(maxQuantityDecimalPlaceLimit)+" >";
		    row.insertCell(8).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+parseFloat(dblTotalWeight).toFixed(maxQuantityDecimalPlaceLimit)+"'/>";
		    row.insertCell(9).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblPrice\" step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto price\" id=\"txtPrice."+(rowCount)+"\" value="+parseFloat(dblPrice).toFixed(maxAmountDecimalPlaceLimit)+" >";
		    row.insertCell(10).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblDiscount\"  step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto txtDisc\" id=\"txtDiscount."+(rowCount)+"\" value="+parseFloat(dblDiscount).toFixed(maxAmountDecimalPlaceLimit)+">";	    
		    row.insertCell(11).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box1 totalValueCell\" size=\"6%\" id=\"txtAmount."+(rowCount)+"\" value="+parseFloat(amount).toFixed(maxAmountDecimalPlaceLimit)+" >";
		    row.insertCell(12).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strRemarks\" size=\"15%\" id=\"txtRemarks."+(rowCount)+"\"  value=''/>";
		 	row.insertCell(13).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strPICode\" readonly=\"readonly\" class=\"Box PICode\" size=\"8%\" id=\"txtPICode."+(rowCount)+"\" value='"+PICode+"'/>";
		    row.insertCell(14).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strUpdate\" readonly=\"readonly\" class=\"Box\" size=\"3%\" id=\"txtUpdate."+(rowCount)+"\" value='N'/>";
		    row.insertCell(15).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javascript:funDeleteRow(this)">';		
		   
		    funCalculateTotal();
		    return false;
		}
		/**
		 * Get supplier Data for changes the supplier in grid
		 */
		function funCheckSupplier(object)
		{
			
			var index=object.parentNode.parentNode.rowIndex;
			//alert(index+"\t"+object.value);
			var searchUrl="";
			code=object.value;
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {	
				    	if(response.strPCode!="Invalid Code")
				    		{
				    			document.getElementById("txtSuppName."+Supprow).value=response.strPName;	
				    		}
				    	else
				    		{
				    			alert("Invalid Supplier Code");
				    			document.all("txtSuppCode."+index).value="";
				    			
				    			return false;
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
		/**
		 * Add Product in grid Direct
		 */
		function funAddProductRow() 
		{
			var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount); 
		    var rowCount=listRow;
		    //alert(rowCount);
		    var strProdCode = $("#txtProdCode").val().trim();		    
			var strProdName=$("#lblProdName").text();
			
			var strUOM=$("#cmbUOM").val();	
			var strSuppCode=gSuppCode;
			var SupplierName=gSuppName;
		    var dblOrdQty = $("#txtOrderQty").val();
		    dblOrdQty=parseFloat(dblOrdQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var dblWeight=$("#txtWeight").val();
		    dblWeight=parseFloat(dblWeight).toFixed(maxQuantityDecimalPlaceLimit);
		    var dblTotalWeight=dblOrdQty*dblWeight;
		    dblTotalWeight=parseFloat(dblTotalWeight).toFixed(maxQuantityDecimalPlaceLimit);
		    var dblPrice=$("#txtPrice").val();
		    dblPrice=parseFloat(dblPrice).toFixed(maxAmountDecimalPlaceLimit);
		    var dblDiscount=$("#txtDiscount").val();
		    
// 		    if(dblDiscount.contains("%"))
// 		    {
// 		    	var d=dblDiscount.split("%");
// 		    	dblDiscount=dblPrice*dblOrdQty*d[0]/100;
// 		    }		    
		    
		    dblDiscount=parseFloat(dblDiscount).toFixed(maxAmountDecimalPlaceLimit);
		    var amount=(dblOrdQty*dblPrice)-dblDiscount;
		    amount=parseFloat(amount).toFixed(maxAmountDecimalPlaceLimit);
		    var strRemarks=$("#txtRemarks").val();
		    var PICode=gPICode;
		    var strUpdate=$("#txtUpdate").val();
		    var strSuppCodeTag="";
		    var strSCodeTag="";
		    var strSNameTag="";    
		    
		    row.insertCell(0).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
		    row.insertCell(1).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
		    row.insertCell(2).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"2%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";
		    if($("#txtSuppCode").val()=="")
			{
		    	strSuppCodeTag="<input type=\"text\"  name=\"listPODtlModel["+(rowCount)+"].strSuppCode\" class=\"Box suppCodeCellValue\"  required=\"required\" size=\"6%\" id=\"txtSuppCode."+(rowCount)+"\" value='"+strSuppCode+"'/>";
		    	strSCodeTag="<input id=btnSup"+rowCount+" type=button size=\"3%\"  onclick=funHelp1("+(rowCount)+",'suppcode1') value=...>";
		    	strSNameTag="<input name=\"listPODtlModel["+(rowCount)+"].strSuppName\" readonly=\"readonly\" class=\"Box\" class=\"inputText-Auto\" size=\"10%\" id=\"txtSuppName."+(rowCount)+"\" value='"+SupplierName+"'/>";
			}
		    else
		    {		    	
		    	strSuppCodeTag="<input name=\"listPODtlModel["+(rowCount)+"].strSuppCode\" readonly=\"readonly\" class=\"Box\" size=\"6%\" id=\"txtSuppCode."+(rowCount)+"\" value='"+strSuppCode+"'/>";
		    	strSCodeTag="";
		    	strSNameTag="<input name=\"listPODtlModel["+(rowCount)+"].strSuppName\" readonly=\"readonly\" class=\"Box inputText-Auto\" size=\"10%\" id=\"txtSuppName."+(rowCount)+"\" value='"+SupplierName+"'/>";
		    }
		    row.insertCell(3).innerHTML=strSuppCodeTag; 
		    row.insertCell(4).innerHTML=strSCodeTag;
		    row.insertCell(5).innerHTML=strSNameTag
		    row.insertCell(6).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblOrdQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto QtyCell\" id=\"txtOrdQty."+(rowCount)+"\" value="+dblOrdQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
		    row.insertCell(7).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
		    row.insertCell(8).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
		    row.insertCell(9).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblPrice\" type=\"text\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places-amt inputText-Auto price\" id=\"txtPrice."+(rowCount)+"\" value="+dblPrice+" onblur=\"Javacsript:funUpdatePrice(this)\">";
		    row.insertCell(10).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblDiscount\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places-amt inputText-Auto txtDisc\" id=\"txtDiscount."+(rowCount)+"\" value="+dblDiscount+" onblur=\"Javacsript:funCalDiscountItemWise(this)\" >";	    
		    row.insertCell(11).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"6%\" id=\"txtAmount."+(rowCount)+"\" value="+amount+" >";
		    row.insertCell(12).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strRemarks\" size=\"15%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
		 	row.insertCell(13).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strPICode\" readonly=\"readonly\" class=\"Box PICode\" size=\"7%\" id=\"txtPICode."+(rowCount)+"\" value='"+PICode+"'/>";
		    row.insertCell(14).innerHTML= "<input name=\"listPODtlModel["+(rowCount)+"].strUpdate\" readonly=\"readonly\" class=\"Box\" size=\"3%\" id=\"txtUpdate."+(rowCount)+"\" value='"+strUpdate+"'/>";
		    row.insertCell(15).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
		    listRow++;
		    funCalculateTotal();
		    funUpdateIssueUOM();
//  		    funCalculateBudAmt();
		    funClearProduct();
		    return false;
		}
		
		function funCalculateBudAmt()
		{
			var poDate=$("#txtPODate").val();
			var strProdCode = $("#txtProdCode").val().trim();
			var searchUrl=getContextPath()+"/calculateBudgetAmt.html?strProdCode="+strProdCode+"&poDate="+poDate;
			var  i=0;
			$.ajax({			
	        	type: "GET",
		        url: searchUrl,
		        dataType: "text",
		        success: function(response)
		        {	
		        	var  data=response.split(",");
		        
		        	$("#lblBudAmt").text(data[1].substring(1,data[0].length));
		        	$("#lblGroup").text("Budget Amount For:"+"  "+data[0].substring(0,data[1].length-1));
		
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
		
		
		/**
		 * Calculating Item wise discount
		 */
		function funCalDiscountItemWise(object)
		{
			var index=object.parentNode.parentNode.rowIndex;
			var discount=parseFloat(document.getElementById("txtDiscount."+index).value);
			var totalamt=parseFloat(document.getElementById("txtPrice."+index).value)*parseFloat(document.getElementById("txtOrdQty."+index).value);
			if(parseFloat(discount) > parseFloat(totalamt))
			{
				alert("Discount Can not be > Item Total Amount");
				document.getElementById("txtDiscount."+index).value=0.00;
				return false;
			}
					
			totalamt=parseFloat(totalamt)-parseFloat(discount);
			document.getElementById("txtAmount."+index).value=parseFloat(totalamt).toFixed(maxAmountDecimalPlaceLimit);
			funCalculateTotal();
		}
		
		/**
		 * Update total price when user change the qty 
		 */
		function funUpdatePrice(object)
		{
			var index=object.parentNode.parentNode.rowIndex;
			var price=parseFloat(document.getElementById("txtPrice."+index).value)*parseFloat(document.getElementById("txtOrdQty."+index).value);
			var discount=parseFloat(document.getElementById("txtDiscount."+index).value)
			document.getElementById("txtAmount."+index).value=parseFloat(price-discount).toFixed(maxAmountDecimalPlaceLimit);				
			funCalculateTotal();
		}		
		
		/**
		 * Calcutating total
		 */
		function funCalculateTotal()
		{
			var totalAmount=0.00;
			var discEffectOnPO='<%=session.getAttribute("DiscEffectOnPO").toString()%>';
			
			$('#tblProdDet tr').each(function() {
			    var totalvalue = $(this).find(".totalValueCell").val();
			    var prodDiscAmt=$(this).find(".txtDisc").val();
			    totalAmount=parseFloat(totalvalue)+totalAmount;
			    if(discEffectOnPO=='N')
				{
			    	totalAmount=totalAmount+parseFloat(prodDiscAmt);
				}
			});
			
			totalAmount=parseFloat(totalAmount).toFixed(maxAmountDecimalPlaceLimit);
			$("#txtSubTotal").val(totalAmount);
			var extraCharges=0;
			if($("#txtExtraCharges").val()!="")
			{
				extraCharges=parseFloat($("#txtExtraCharges").val());
			}
			extraCharges=parseFloat(extraCharges).toFixed(maxAmountDecimalPlaceLimit);
			var discAmt=0;
			if($("#txtDisc").val()!="")
			{
				discAmt=parseFloat($("#txtDisc").val());
			}
			
			discAmt=parseFloat(discAmt).toFixed(maxAmountDecimalPlaceLimit);
			var taxAmt=$("#txtPOTaxAmt").val();
			var finalAmt=(parseFloat(totalAmount)+parseFloat(extraCharges)+parseFloat(taxAmt)-parseFloat(discAmt));
			finalAmt=parseFloat(finalAmt).toFixed(maxAmountDecimalPlaceLimit);
			$("#txtFinalAmt").val(finalAmt);
			$("#lblPOGrandTotal").text(finalAmt);
			funCalculateOtherChargesTotal();
			
			funApplyNumberValidation();
		}
		
		
		function funCalculateOtherChargesTotal()
		{
			$("#txtFOB").val($("#txtSubTotal").val());
			var FOBAmt=$("#txtFOB").val();
			var freightAmt=$("#txtFreight").val();
			var insuranceAmt=$("#txtInsurance").val();
			var otherChargesAmt=$("#txtOtherCharges").val();
			var CIFAmt=(parseFloat(FOBAmt)+parseFloat(freightAmt)+parseFloat(insuranceAmt)+parseFloat(otherChargesAmt));
			$("#txtCIF").val(CIFAmt);
			var otherCharges=parseFloat(freightAmt)+parseFloat(insuranceAmt)+parseFloat(otherChargesAmt);
			
			var subTotal=$("#txtSubTotal").val();
			var extraCharges=$("#txtExtraCharges").val();
			var discAmt=$("#txtDisc").val();
			discAmt=parseFloat(discAmt).toFixed(maxAmountDecimalPlaceLimit);
			var taxAmt=$("#txtPOTaxAmt").val();
			
			var finalAmt=(parseFloat(subTotal)+parseFloat(otherCharges)+parseFloat(extraCharges)+parseFloat(taxAmt)-parseFloat(discAmt));
			finalAmt=parseFloat(finalAmt).toFixed(maxAmountDecimalPlaceLimit);
			$("#txtFinalAmt").val(finalAmt);
			$("#lblPOGrandTotal").text(finalAmt);
		}
		
		
		
		/**
		 * Check validation before adding product data in grid
		 */
		function btnAdd_onclick() 
		{
				var PICode=$("#txtPICode").val();
				if ($("#cmbAgainst").val() == 'Purchase Indent' && PICode.trim().length>0)
				{
					if($("#txtProdCode").val()!="")
				    {
						var strProdCode=$("#txtProdCode").val();
						var table = document.getElementById("tblProdDet");
					    var rowCount = table.rows.length;		   
					    var totalQty=0;
					    if(rowCount > 0)
					    	{
					    		$('#tblProdDet tr').each(function() 
							    	{
								    	var prodCode = $(this).find(".prodCode").val(); 
									    var totalQtyCell = $(this).find(".QtyCell").val(); 
									    var GridPIcode = $(this).find(".PICode").val();
									    if(prodCode==strProdCode && gPICode==GridPIcode)
									    	{
									    		totalQty=parseFloat(totalQtyCell)+parseFloat(totalQty);
									    	}
									});
							    
					    	}
					    var POQty=parseFloat(gPIQty)-parseFloat(totalQty);
					    var POOrderQty=$("#txtOrderQty").val();
					    if(parseFloat(POOrderQty)>parseFloat(POQty))
					    	{
					    		alert("PO Qty can not be > PI Qty");
					    		$("#txtOrderQty").focus();
					    		return false;
					    	}
					    else
					    	{
					    	if($("#txtOrderQty").val()==0)
					    		{
									alert("Qty Can not be Zero");
									return false;
					    		}
					    	else
					    		{
					    	
					    		
					    			funAddProductRow();
					    		
					    		}
					    	}
						
				    }
					else
						{
							alert("Please Enter Product Code or Search");
					    	$("#txtProdCode").focus();
					        return false;
						}
						
				}
			else
				{
			
			if($("#txtProdCode").val()!="")
		    {
		    	if($("#txtOrderQty").val()!="" && $("#txtOrderQty").val() != 0)
		        {	
		    	
		    			
		    		var strProdCode=$("#txtProdCode").val();
		    		//alert(funDuplicateProduct(strProdCode));
					/* if(funDuplicateProduct(strProdCode))
						{
		            		funAddProductRow();
						} */
		    		funAddProductRow();
				  
				} 
		        else
		        {
		        	alert("Please Enter Quantity");
		        	$("#txtOrderQty").focus();
		            return false;
				}		    	
			}
			else
		    {
				alert("Please Enter Product Code or Search");
		    	$("#txtProdCode").focus();
		        return false;
			}  
		  }
		}
		function funDuplicateProduct(strProdCode)
		{
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblProdDet tr').each(function()
				    {
					    if(strProdCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Already added "+ strProdCode);
					    	funClearProduct();
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		  
		}
		/**
		 * Clear textfiled after adding data in textfield
		 */
		function funClearProduct()
		{
			$("#txtProdCode").val("");
			$("#lblProdName").text("");
			$("#txtWeight").val("");
			$("#txtRemarks").val("");
			$("#txtPrice").val("");
			$("#txtOrderQty").val("");
			$("#txtDiscount").val("0");
			$("#txtProdCode").focus();
		}
		
		/**
		 * Delete a particular record from a grid
		 */
		function funDeleteRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    table.deleteRow(index);		   
			funCalculateTotal();
			
		}
	
		/**
		 * Combo box change event
		 */
		function funOnChange()
		{
			if ($("#cmbAgainst").val() == 'Purchase Indent') 
			{
				$("#txtPICode").css('visibility','visible');
				$('#txtProdCode').attr('readonly', true);
			}
			else
			{
				$("#txtPICode").css('visibility','hidden');
				$('#txtProdCode').attr('readonly', false);
			}
		}
		
		/**
		 * Update Issue UOM in product master
		 */
		function funUpdateIssueUOM()
		{
			var strProdCode = $("#txtProdCode").val();				
			var strUOM=$("#cmbUOM").val();
			if(strUOM!="")
			{
			var transaction="PO";
			var searchUrl=getContextPath()+"/updateUOMData.html?strProdCode="+strProdCode+"&strUOM="+strUOM+"&transaction="+transaction;
			$.ajax({			
	        	type: "GET",
		        url: searchUrl,
		        dataType: "text",
		        success: function(response)
		        {	
		        	//alert(response);
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
		
		/**
		 * Ready Function for Initialize textField with default value
		 * And Set date in date picker 
		 * Attached Document
		 * 
		 **/
		 
		$(function()
		{
			$("#txtPODate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		 	$("#txtDelDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		 	$("#txtPayDate").datepicker({ dateFormat: 'dd-mm-yy' });
		 	$('#txtPODate').datepicker('setDate', 'today');
		 	$('#txtDelDate').datepicker('setDate', 'today');
		 	$('#txtPayDate').datepicker('setDate', 'today');
			
		 	$('a#baseUrl').click(function() 
			{
				if($("#txtPOCode").val().trim()=="")
				{
					alert("Please Select PO No");
					return false;
				}

				 window.open('attachDoc.html?transName=frmPurchaseOrder.jsp&formName=Purchase Order&code='+$('#txtPOCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
		 	
		 	$('a#AutoUrl').click(function() 
					{
					 		if($("#txtSuppCode").val().trim()=='')
							{
								alert('Please Select Supplier!!!');
								return false;
							}else
								{
									var suppCode = $("#txtSuppCode").val();
								//	var retval = window.open('autoGeneratePO.html?transName=frmPurchaseOrder.jsp&formName=Purchase Order&suppCode='+suppCode,"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=800,height=600,left=400px");
									var dataPO= window.open('autoGeneratePO.html?transName=frmPurchaseOrder.jsp&formName=Purchase Order&suppCode='+suppCode,"","dialogHeight:800px;dialogWidth:700px;dialogLeft:400px;")
									var timer = setInterval(function ()
										    {
											if(dataPO.closed)
												{
													if (dataPO.returnValue != null)
													{
														var response=dataPO.returnValue;
														funRemoveProductRows();
														var count=0;
														$.each(response, function(i,item)
									                    {	
															count=i;
															var totWeight=parseFloat(response[i].dblOrdQty)*parseFloat(response[i].dblWeight);
															var amt=parseFloat(response[i].dblOrdQty)*parseFloat(response[i].dblPrice)-parseFloat(response[i].dblDiscount);
															
									                    	funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].strUOM,
															response[i].strSuppCode,response[i].strSuppName,response[i].dblOrdQty,response[i].dblWeight,
															totWeight,response[i].dblPrice,response[i].dblDiscount,amt,response[i].strRemarks,
															response[i].strPICode,response[i].strUpdate);      
									                    	
									                    });
													 listRow=count+1;
													
									
													}
													clearInterval(timer);
												}
										    }, 500);
								
								
								
								}	
					});
		 	
		  $('a#EmailUrl').click(function() 
					{
			 			var strPoCode= $('#txtPOCode').val();
						// window.showModalDialog('frmEmailSending.html?POCode='+strPoCode,"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			 			 window.open('frmEmailSending.html?POCode='+strPoCode,"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
						
					}); 
			
			$("#cmbAgainst").change(function() 
			{				
				if ($(this).val() == 'Purchase Indent') 
				{
					$("#txtPICode").css('visibility','visible');
				}
				else
				{
					$("#txtPICode").css('visibility','hidden');
				}
			});
			
			/** 
			 * On blur event
			 **/
			$('#txtPOCode').blur(function () {
				 var code=$('#txtPOCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){					  
					  funSetPurchaseOrder(code);
				   }
				});
			
			$('#txtSuppCode').blur(function () {
				var code=$('#txtSuppCode').val();   
				if (code.trim().length > 0 && code !="?" && code !="/"){
					   funSetSupplier(code);
				   }
				});
			
			$('#txtProdCode').blur(function () {
				var code=$('#txtProdCode').val();
				if ($("#cmbAgainst").val() == 'Purchase Indent')
				{
					var PICode=$('#cmbPIDoc').val();
					if(PICode.trim().length > 0)
						{
							if (code.trim().length > 0)
							{
								funSetProductForPI(code);
							}
						}
				}
				else
				{
					if (code.trim().length > 0 && code !="?" && code !="/")
					{
					   funSetProduct(code);
				   	}
				}
				});
			
				
		});
		
		
		/**
		 * Ready Function for Initialize textField with default value
		 * And Set date in date picker 
		 * And Getting session Value
		 * Success Message after Submit the Form
		 */
		$(document).ready(function(){
			strChangeUOM='<%=session.getAttribute("changeUOM").toString()%>';
			if(strChangeUOM!="N")
				{
					$("#cmbUOM").prop("disabled", false);
				}
			else
				{
					$("#cmbUOM").prop("disabled", true);
				}
			
			var message='';
			<%if (session.getAttribute("success") != null) {
				if (session.getAttribute("successMessage") != null) {%>
				            message='<%=session.getAttribute("successMessage").toString()%>';
				            <%session.removeAttribute("successMessage");
				}
				boolean test = ((Boolean) session.getAttribute("success"))
						.booleanValue();
				session.removeAttribute("success");
				if (test) {%>	
				alert("Data Save successfully\n\n"+message);
			<%}
			}%>
			
			var code='';
			<%if (null != session.getAttribute("rptPOCode"))
			{%>
			code='<%=session.getAttribute("rptPOCode").toString()%>';
            <%session.removeAttribute("rptPOCode");%>
            
            
	        var isOk = confirm("Do You Want to Generate Slip?");
		    if (isOk) 
		    {
				window.open(getContextPath() + "/openPOSlip.html?rptPOCode=" + code,'_blank');
		    }
		    
		    var isOk = confirm("Do You Want to Send Slip On Mail?");
		    if (isOk) 
		    {
				window.open(getContextPath() + "/sendPOEmail.html?strPOCode=" + code);
		    }
           <%}%>
		$("#txtPICode").css('visibility', 'hidden');
		$("#txtProdCode").focus();
		
		var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
		if(flagOpenFromAuthorization == 'true')
		{
			funSetPurchaseOrder("${authorizationPOCode}");
		}
		$("#cmbPayMode").val("CREDIT");
		
		
	
		funSetPropertyAddess();
			
			
	});
		
		/**
		 * Checking Validation before submiting the data
		 */
	    function funCallFormAction(actionName, object) {

		
		var spPODate=$("#txtPODate").val().split('-');
	    var poDate = new Date(spPODate[2],spPODate[1]-1,spPODate[0]);
	    var td=new Date();
	    var d2 = new Date(td.getYear()+1900,td.getMonth(),td.getDate());
	    var dateDiff=poDate-d2;
	    if(dateDiff>0)
	    {
	    	alert("Future date is not allowed for PO");
	    	$("#txtPODate").focus();
			return false;		    	
	    }
		
		if (!fun_isDate($("#txtPODate").val())) {
			alert('Invalid Date');
			$("#txtPODate").focus();
			return false;
		}

		if (!fun_isDate($("#txtDelDate").val())) {
			alert('Invalid Date');
			$("#txtDelDate").focus();
			return false;
		}

		if (!fun_isDate($("#txtPayDate").val())) {
			alert('Invalid Date');
			$("#txtPayDate").focus();
			return false;
		}
		

		if(  $("#cmbAgainst").val() == null || $("#cmbAgainst").val().trim().length == 0 )
		 {
		 		alert("Please Select Against");
				return false;
		 }
		
		if($("#cmbAgainst").val() == 'Purchase Indent') 
		{				
			if($("#txtPICode").val()=='')
			{
				alert("Please enter Purchase Indent Code");
				return false;
			}
		}
		else 
		{
			if($("#txtSuppCode").val()=="")
			{
				$('#tblProdDet tr').each(function() {
				    var suppCode = $(this).find(".suppCodeCellValue").val();
				    //alert(suppCode);
				    if(suppCode=='')
				    	{
				    		$(this).remove();
				    	}
				  
				 });
				funCalculateTotal();
				    /* var table=document.getElementById("tblProdDet");
				    var rowCount1 = t.rows.length;	 */
				    var rowCount = $('#tblProdDet tr').length;
				    //alert(rowCount1);
				    if(rowCount>0)
				    	{
							return true;
				    	}
				    else
				    	{
				    		alert("Please Fill the Grid");
				    		return false;
				    	} 
			}

		}
		
		
		 var status = confirm("DO YOU WANT CALCUTE THE TAXES?");
		   if(status == false){
		   		return true;
		   }
		   else{
			   funShowTaxTax(); 
		   		return false;
		   }
			
		
	}
		
	function funShowTaxTax(){
		
		$("li").prop("class","");
		$(".tab_content").css("display","none");
		
		$("[data-state='tab4']").prop("class","active");
		$("#tab4").css("display","block");
	}
		
	/**
	 * Remove All Product Row
	 **/
	function funRemoveProductRows() {
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;
		//alert("rowCount\t"+rowCount);
		for (var i = rowCount - 1; i >= 0; i--) {
			table.deleteRow(i);
		}
	}
	/**
	 * Calcutating discount
	 */
	function funCalDiscountAmt()
	{  
		var dicPer=0;
		if($("#txtDiscPer").val()!="")
			{
				dicPer=$("#txtDiscPer").val();
			}
		var subtotal=$("#txtSubTotal").val();
		var discountAmt=parseFloat(dicPer)*(parseFloat(subtotal)/100);
		$("#txtDisc").val(parseFloat(discountAmt).toFixed(maxAmountDecimalPlaceLimit));
		 funCalculateTotal();
		
	}
	
	function funShowDisCountPer(subtotal,disAmt)
	{
		var disPer=(parseFloat(disAmt)*100)/parseFloat(subtotal);
		return	disPer;
	}
	
	function funSetPropertyAddess()
	{
		
		var searchUrl=getContextPath()+"/loadPropertyAddress.html";
		$.ajax({			
        	type: "GET",
	        url: searchUrl,
	        dataType: "text",
	        success: function(response)
	        {	
	        	$("#txtBAddress1").val(response.strBAdd1);
	    		$("#txtBAddress2").val(response.strBAdd2);
	    		$("#txtBCity").val(response.strBCity);
	    		$("#txtBState").val(response.strBState);
	    		$("#txtBPin").val(response.strBPin);
	    		$("#txtBCountry").val(response.strBCountry);
	    		$("#txtSAddress1").val(response.strSAdd1);
	    		$("#txtSAddress2").val(response.strSAdd2);
	    		$("#txtSCity").val(response.strSCity);
	    		$("#txtSState").val(response.strSState);
	    		$("#txtSPin").val(response.strSPin);
	    		$("#txtSCountry").val(response.strSCountry);
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
	
	
	function funGetKeyCode(event,controller) {
	    var key = event.keyCode;
	    
	    if(controller=='QtyRecv' && key==13)
	    {
	    	btnAdd_onclick();
	    }
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
		<label>Purchase Order</label>
	</div>
	<s:form name="POForm" method="POST"
		action="savePOData.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table
			style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>
					<div id="tab_container" style="height: 750px">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 100px; padding-left: 55px">GENERAL</li>
							<li data-state="tab2" style="width: 100px; padding-left: 55px">Address</li>
							<li data-state="tab3" style="width: 150px; padding-left: 55px">Term
								&amp; Conditions</li>
							<li data-state="tab4" style="width: 100px; padding-left: 55px">Taxes</li>
							<li data-state="tab5" style="width: 100px; padding-left: 55px">Other Charges</li>
						</ul>
						<div id="tab1" class="tab_content" style="height: 550px">

							<table class="transTable">
								<tr>
								
								<th align="right" colspan="6" ><a id="AutoUrl" href="#">
											Auto Generate </a></th>
									<th align="right" ><a id="baseUrl" href="#">
											Attach Documents </a></th>
											 <th  ><a id="EmailUrl" href="#">
											Send Mail</a></th> 
								</tr>

								<tr>
									<td width="100px"><label>PO Code</label></td>
									<td width="140px"><s:input path="strPOCode" id="txtPOCode"
											ondblclick="funHelp('purchaseorder')"
											cssClass="searchTextBox" /></td>
									<td width="140px" align="right"><label>Our's Ref
											No.</label></td>
									<td width="140px"><s:input id="txtOurRef" class="BoxW116px" path="strCode"/></td>
									<td width="100px"><label>PO Date</label>
									<td><s:input path="dtPODate" id="txtPODate"
											pattern="\d{1,2}-\d{1,2}-\d{4}" required="required"
											cssClass="calenderTextBox" /></td>
								</tr>

								<tr>
									<td><label>Supplier</label></td>
									<td><s:input path="strSuppCode" id="txtSuppCode"
											ondblclick="funHelp('suppcodeActive')" cssClass="searchTextBox" /></td>
									<td colspan="2"><label id="lblSupplierName"
										class="namelabel"></label></td>
									<td><label>Delivery Date</label></td>
									<td colspan="2" align="left"><s:input path="dtDelDate"
											pattern="\d{1,2}-\d{1,2}-\d{4}" id="txtDelDate"
											required="required" cssClass="calenderTextBox" /></td>
								</tr>

								<tr>
									<td><label>Against</label></td>
									<td><s:select path="strAgainst" id="cmbAgainst"
									items="${strProcessList}" onchange="funOnChange();" cssClass="BoxW124px">						
										</s:select></td>
									<td><s:input id="txtPICode" path="strSOCode" ondblclick="funOpenPIforPO('PICode')" class="searchTextBox"></s:input></td>
									<td><input type="button" value="Fill" class="smallButton"
										onclick="funSetPurchaseIndent();" id=btnFill /></td>
									<td><label>Payment Due Date </label></td>
									<td colspan="2" align="left"><s:input path="dtPayDate"
											required="required" pattern="\d{1,2}-\d{1,2}-\d{4}"
											id="txtPayDate" cssClass="calenderTextBox" /></td>
								</tr>
								<tr>
									<td><label>Pay Mode</label></td>
									<td><s:select path="strPayMode" id="cmbPayMode"
											cssClass="BoxW124px">
											<s:option value="CASH">CASH</s:option>
											<s:option value="CREDIT" >CREDIT</s:option>
										</s:select></td>
									<td align="right"><label>Currency </label></td>
									
									<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px" onchange="funOnChangeCurrency()">
										</s:select></td>
										<td><s:input id="txtDblConversion" value ="1" path="dblConversion" type="text" class="decimal-places numberField"></s:input>
									</td>
									
								</tr>
							</table>
							<table class="transTableMiddle">
							<tr>
								<td><label>PI Code</label></td>
								<td colspan="8">
									<select id="cmbPIDoc"  Class="BoxW124px">
									</select>
								</td>
								</tr>
								<tr>
									<td width="100px"><label>Product</label></td>
									<td width="150px"><input id="txtProdCode"
										ondblclick="funOpenHelp();" class="searchTextBox" /></td>
									<td align="left" colspan="5"><label id="lblProdName"
										class="namelabel"></label></td>

								</tr>
								<tr>
									<td><label>Unit Price</label></td>
									<td><input id="txtPrice" type="text"
										class="decimal-places-amt numberField" /></td>
									<td><label>Wt/Unit</label></td>
									<td width="150px"><input type="text" id="txtWeight"
										class="decimal-places numberField" /></td>
									<td width="100px"><label>Quantity</label></td>
									<td width="150px"><input id="txtOrderQty"
										type="text" class="decimal-places numberField" style="width:60%" onkeypress="funGetKeyCode(event,'QtyRecv')"/></td>
									<td width="5%">UOM</td>
									<td> <s:select id="cmbUOM" name="cmbUOM"
									path="" items="${uomList}" cssStyle="width: 60%" cssClass="BoxW124px"/></td>
								</tr>

								<tr>
									<td><label>Highlight</label></td>
									<td><select id="txtUpdate" class="BoxW62px">
											<option value="N">No</option>
											<option value="Y">Yes</option>
									</select></td>
									<!-- <td><label>Stock </label></td> -->
									<td><label>Discount</label></td>
									
<!-- 									<td><input id="txtDiscount" type="text" class="decimal-places-amt numberField" value="0" class="BoxW116px" /></td> -->
									<td><input id="txtDiscount" value="0" type="text" class="BoxW124px" ></input></td>
									<td><label>Remarks</label></td>
									<td><input id="txtRemarks" class="longTextBox" style="width:100%" /></td>
									<td><input type="button" value="Add" class="smallButton"
										onclick="return btnAdd_onclick()" /></td>
								</tr>
							</table>

							<div class="dynamicTableContainer" style="height: 300px;">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
									<tr bgcolor="#72BEFC">
										<td width="5%">Product Code</td>
										<!--  COl1   -->
										<td width="18%">Product Name</td>
										<!--  COl2   -->
										<td width="3%">UOM</td>
										<!--  COl3   -->
										<td width="6%">Supplier Code</td>
										<!--  COl4   -->
										<td width="6%">S Code</td>
										<!--  COl4   -->
										<td width="8%">Supplier Name</td>
										<!--  COl5   -->
										<td width="5%">Order Qty</td>
										<!--  COl6   -->
										<td width="4%">Wt/Unit</td>
										<!--  COl7   -->
										<td width="4%">Total Wt</td>
										<!--  COl8   -->
										<td width="5%">Price</td>
										<!--  COl9   -->
										<td width="3%">Discount</td>
										<!--  COl10   -->
										<td width="6%">Amount</td>
										<!--  COl11   -->
										<td width="11%">Remarks</td>
										<!--  COl12   -->
										<td width="7%">PI Code</td>
										<!--  COl13  -->
										<td width="4%">Update</td>
										<!--  COl14  -->
										<td width="4%">Delete</td>
										<!--  COl15   -->
									</tr>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
									<table id="tblProdDet"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col15-center">
										<tbody>
										<col style="width: 5%">
										<!--  COl1   -->
										<col style="width: 18%">
										<!--  COl2   -->
										<col style="width: 3%">
										<!--  COl3   -->
										<col style="width: 6%">
										<!--  COl4   -->
										<col style="width: 6%">
										<!--  COl4   -->
										<col style="width: 8%">
										<!--  COl5   -->
										<col style="width: 5%">
										<!--  COl6   -->
										<col style="width: 4%">
										<!--  COl7   -->
										<col style="width: 4%">
										<!--  COl8   -->
										<col style="width: 5%">
										<!--  COl9   -->
										<col style="width: 5%">
										<!--  COl10   -->
										<col style="width: 6%">
										<!--  COl11  -->
										<col style="width: 11%">
										<!--  COl12  -->
										<col style="width: 7%">
										<!--  COl13  -->
										<col style="width: 4%">
										<!--  COl14  -->
										<col style="width: 2.5%">
										<!--  COl15   -->
										
										</tbody>

									</table>
								</div>

							</div>
							<table id="tbl2" class="transTable">
								<tr>
									<td><label id="lblGroup"></label></td>
									<td colspan="4"><label id="lblBudAmt"></label></td>
		                        </tr>
		                        
		                        <tr>
									<td><label id="lblYourRef">Your Ref</label></td>
									<td><s:input type="text" id="txtYourRef" path="strYourRef" cssClass="simpleTextBox" /></td>
									<td><label id="lblSubTotal">SubTotal</label></td>
									<td colspan="3"><s:input type="text" id="txtSubTotal" path="dblTotal" readonly="true" class="decimal-places-amt numberField" /></td>
								</tr>

								<tr>
									<td><label id="lblPermRef">Permission Ref. in LUT</label></td>
									<td><s:input type="text" id="txtPermRef"
											path="strPerRef" cssClass="simpleTextBox" /></td>
									<td><label id="lblDiscPer">Discount %</label></td>
				    				<td><input id="txtDiscPer"  type="text" value="0" onblur="funCalDiscountAmt();" 
				    				class="decimal-places-amt numberField"/></td>
								</tr>

								<tr>
									<td><label id="lblPOItems">PO Items Title:</label></td>
									<td><input type="text" id="txtPOItems"
										class="simpleTextBox" /></td>
									<td><label id="lblDiscount">Discount</label></td>
									<td colspan="3"><s:input type="text" id="txtDisc"
											path="dblDiscount" onblur="funCalculateTotal();"
											cssClass="decimal-places-amt numberField" />
								   </td>
								</tr>

								<tr>
									<td><label id="lblPOFooter">PO Footer</label></td>
									<td><input type="button" id="btnPOFooter" /></td>
									<td><label id="lblExtraCharges">Extra Charges:</label></td>
									<td colspan="3"><s:input type="text" value="0" id="txtExtraCharges"
											path="dblExtra" onblur="funCalculateTotal();"
											cssClass="decimal-places-amt numberField" />
									</td>
								</tr>

								<tr>
									<td><label id="lblShortClosePO">Short Close PO</label></td>
									<td><s:checkbox element="li" id="chkShortClosePO"
											path="strClosePO" value="Yes" /></td>


									<td><label id="lblFinalAmt">Final Amount:</label></td>
									<td><s:input type="text" id="txtFinalAmt"
											path="dblFinalAmt" readonly="true"
											cssClass="decimal-places-amt numberField" /></td>
								</tr>

								<tr>
									<td><label id="lblDelSchedule">Delivery Schedule</label></td>
									<td colspan="5"><input type="button" id="btnDelSchedule" /></td>
								</tr>

							</table>
						</div>
						<div id="tab2" class="tab_content">
							<table class="transTable">
								<tr>
									<th colspan="2" align="left"><label>Bill To</label></th>
									<th colspan="2" align="left"><label>Ship To </label></th>
								</tr>

								<tr>
									<td width="120px"><label>Address Line 1</label></td>
									<td><s:input path="strVAddress1" id="txtBAddress1"
											cssClass="longTextBox" /></td>
									<td width="120px"><label>Address Line 1</label></td>
									<td><s:input path="strSAddress1" id="txtSAddress1"
											cssClass="longTextBox" /></td>
								</tr>

								<tr>
									<td><label>Address Line 2</label></td>
									<td><s:input path="strVAddress2" id="txtBAddress2"
											cssClass="longTextBox" /></td>
									<td><label>Address Line 2</label></td>
									<td><s:input path="strSAddress2" id="txtSAddress2"
											cssClass="longTextBox" /></td>
								</tr>

								<tr>
									<td><label>City</label></td>
									<td><s:input path="strVCity" id="txtBCity"
											cssClass="BoxW116px" /></td>
									<td><label>City</label></td>
									<td><s:input path="strSCity" id="txtSCity"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>
									<td><label>State</label></td>
									<td><s:input path="strVState" id="txtBState"
											cssClass="BoxW116px" /></td>
									<td><label>State</label></td>
									<td><s:input path="strSState" id="txtSState"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>
									<td><label>Country</label></td>
									<td><s:input path="strVCountry" id="txtBCountry"
											cssClass="BoxW116px" /></td>
									<td><label>Country</label></td>
									<td><s:input path="strSCountry" id="txtSCountry"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>
									<td><label>Pin Code</label></td>
									<td><s:input path="strVPin" id="txtBPin"
											class="positive-integer BoxW116px" /></td>
									<td><label>Pin Code</label></td>
									<td><s:input path="strSPin" id="txtSPin"
											class="positive-integer BoxW116px" /></td>
								</tr>
							</table>
						</div>
						<div id="tab3" class="tab_content">
							<br>
							<table class="transTableMiddle">
							
								<tr>
									<td><label class="namelabel">TC Code</label></td>
									<td><input id="txtTCCode"
										ondblclick="funHelp('tcForSetup')" class="searchTextBox" /></td>
									<td><label id="lblTCName" class="namelabel"></label></td>
									<td><label class="namelabel">TC Description</label></td>
									<td><input id="txtTCDesc" class="longTextBox" /></td>
									<td colspan="3"><input type="Button" value="Add"
										id="btnAddTC" class="smallButton" /></td>
								</tr>
							</table>

							<table class="transTable" id="tblTermsAndCondColumns">
								<tr>
									<td width="40%">TC Name</td>
									<td width="80%">TC Description</td>
								</tr>
							</table>

							<table
								style="width: 100%; height: 100%; text-align: center; border: 1px solid black; font-size: 11px; font-weight: bold;">
								<tr>
									<td>
										<table class="myTable" id="tblTermsAndCond">

										</table>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="tab4" class="tab_content">
							<br>
							<br>
							<table class="masterTable">
								<tr><th colspan="5"></th></tr>
								<tr>
									<td><input type="button" id="btnGenTax" value="Calculate Tax" class="form_button"></td>
									<td><label id="tx"></label></td>
								</tr>
								
								<tr>									
									<td><label>Tax Code</label></td>
									<td>
										<input type="text" id="txtTaxCode" ondblclick="funHelp('OpenTaxesForPurchase');" class="searchTextBox"/>
									</td>
									
									<td><label>Tax Description</label></td>
									<td colspan="2">
										<label id="lblTaxDesc"></label>
									</td>
									</tr><tr>
									<td><label>Taxable Amount</label></td>
									<td>
										<input type="number" style="text-align: right;" step="any" id="txtTaxableAmt" class="BoxW116px"/>
									</td>
									
									<td><label>Tax Amount</label></td>
									<td>
										<input type="number" style="text-align: right;" step="any" id="txtTaxAmt" class="BoxW116px"/>
									</td>
															
									<td>
										<input type="button" id="btnAddTax" value="Add" class="smallButton"/>
									</td>
								</tr>
							</table>
							<br>
							<table style="width: 80%;" class="transTablex col5-center">
								<tr>
									<td style="width:10%">Tax Code</td>
									<td style="width:10%">Description</td>
									<td style="width:10%">Taxable Amount</td>
									<td style="width:10%">Tax Amount</td>
									<td style="width:5%">Delete</td>
								</tr>							
							</table>
							<div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 150px;
			    				margin: auto;overflow-x: hidden; overflow-y: scroll;width: 80%;">
									<table id="tblTax" class="transTablex col5-center" style="width: 100%;">
									<tbody>    
											<col style="width:10%"><!--  COl1   -->
											<col style="width:10%"><!--  COl2   -->
											<col style="width:10%"><!--  COl3   -->
											<col style="width:10%"><!--  COl4   -->
											<col style="width:6%"><!--  COl5   -->									
									</tbody>							
									</table>
							</div>			
						<br>
						<table id="tblTaxTotal" class="masterTable">
							<tr>
								<td width="130px"><label>Taxable Amt Total</label></td>
								<td><label id="lblTaxableAmt"></label></td>
								
								<td  width="130px"><label>Tax</label></td>
								<td><label id="lblTaxTotal"></label></td>
								<td><s:input type="hidden" id="txtPOTaxAmt" path="dblTaxAmt"/></td>
							</tr>
							
							<tr>
								<td><label>Grand Total</label></td>
								<td colspan="3"><label id="lblPOGrandTotal"></label></td>
							</tr>
						</table>
							
						</div>
						
						<div id="tab5" class="tab_content">
							<br>
							<br>
							<table class="masterTable" id="tblOtherCharges">
								<tr>
									<td><label id="lblFOB">FOB</label></td>
									<td colspan="2"><s:input type="text" id="txtFOB" path="dblFOB" 
										onblur="funCalculateOtherChargesTotal();" class="decimal-places-amt numberField" /></td>
									
									<td><label id="lblFreight">Freight</label></td>
									<td colspan="2"><s:input type="text" id="txtFreight" path="dblFreight" 
									 	onblur="funCalculateOtherChargesTotal();" class="decimal-places-amt numberField" /></td>
								</tr>
								
								<tr>	
									<td><label id="lblInsurance">Insurance</label></td>
									<td colspan="2"><s:input type="text" id="txtInsurance" path="dblInsurance" 
										onblur="funCalculateOtherChargesTotal();" class="decimal-places-amt numberField" /></td>
									
									<td><label id="lblOtherCharges">Other Charges</label></td>
									<td colspan="2"><s:input type="text" id="txtOtherCharges" path="dblOtherCharges" 
										onblur="funCalculateOtherChargesTotal();" class="decimal-places-amt numberField" /></td>
								</tr>
								
								<tr>
									<td><label id="lblCIF">CIF</label></td>
									<td colspan="1"><s:input type="text" id="txtCIF" path="dblCIF" readonly="true" 
										onblur="funCalculateOtherChargesTotal();" class="decimal-places-amt numberField" /></td>
								</tr>
								
								<tr>
									<td><label id="lblClearingAgentCharges">Clearing Agent Charges</label></td>
									<td colspan="2"><s:input type="text" id="txtClearingAgentCharges" path="dblClearingAgentCharges"  
										class="decimal-places-amt numberField" /></td>
								
									<td><label id="lblVATClaim">VAT Claim</label></td>
									<td colspan="2"><s:input type="text" id="txtVATClaim" path="dblVATClaim" 
										class="decimal-places-amt numberField" /></td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmPurchaseOrder.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		
	</s:form>
	<script type="text/javascript">
		funApplyNumberValidation();
		funOnChange();
	</script>
</body>
</html>