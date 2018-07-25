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
		var sgData ;
		var prodData;
	
		var clientCode='<%=session.getAttribute("clientCode").toString()%>';
		if(clientCode=='226.001')
		{
			$("#txtSubGroup").hide();
			$("label[for=a]").hide();
		}
	
		$(".tab_content").hide();
		$(".tab_content:first").show();
	
		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();
			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
			
		var code='<%=session.getAttribute("locationCode").toString()%>';
		funSetLocation(code);
		
		$("#txtSODate").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtSODate" ).datepicker('setDate', 'today');
		$("#txtSODate").datepicker();
				
		$("#txtCPODate").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtCPODate" ).datepicker('setDate', 'today');
		$("#txtCPODate").datepicker();
		
		$("#txtFulmtDate").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtFulmtDate" ).datepicker('setDate', 'today');
		$("#txtFulmtDate").datepicker();

		$("#txtCPODate").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtCPODate" ).datepicker('setDate', 'today');
		$("#txtCPODate").datepicker();
		$("#txtLocCode").focus();

		$("#strStdOrder").click(function() {
			if (this.checked) {
				$("#hidStdValue").val('Y');
			}else
		    {
				$("#hidStdValue").val('N');
		    }
		});
		
		var currencyCode=$("#cmbCurrency").val();
		   var currValue=funGetCurrencyCode(currencyCode);
			if(currValue==null)
			{
				currValue=1.0;
			}
			$("#txtDblCurrencyConv").val(currValue);
	});
		
	$(document).ready(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "none");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
		$('a#baseUrl').click(function() 
				{
					if($("#txtSOCode").val().trim()=="")
					{
						alert("Please Select Sales Order Code");
						return false;
					}
					window.open('attachDoc.html?transName=frmSalesOrder.jsp&formName=Sales Order&code='+$('#txtSOCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
				});
		
	});
		
	
	
	
		
		function funHelp(transactionName)
		{
			fieldName = transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500;left=500;");
		}

		 	function funSetData(code)
			{
				switch (fieldName)
				{
				 	case 'locationmaster':
				    	funSetLocation(code);
				        break;
				        
				    case 'productProduced':
				    	funSetProduct(code);
				        break;
				        
				    case 'custMasterActive' :
				    	funSetCuster(code);
				    	break;
				    	
				    case 'salesorder' :
				    	funSetSalesData(code);
				    	break;
				     
				    case 'subgroup':
				    	funSetSubGroup(code);
				        break;
				        
				    case 'salesorderslip' :
				    	funSetSalesSlipData(code);
				    	break;
				}
			}
		 	
		 	
		 	function funSetSubGroup(code)
			{
				$("#hidSubGroupCode").val(code);
				gurl=getContextPath()+"/loadSubGroupMasterData.html?subGroupCode="+code;
				$.ajax({
				        type: "GET",
				        url: gurl,
				        dataType: "json",
				        success: function(response)
				        {
					        	$("#txtSubGroup").val(response.strSGName);
						},
				        error: function(e)
				        {				        	
				        	alert("Invalid SubGroup Code");
			        		$("#txtSubgroupCode").val('');
				        }
			      });
			}
		 	
		 	
		 	function funSetSalesData(code)
			{
				gurl=getContextPath()+"/SalesOrderHdData.html?soCode="+code;
				$.ajax({
			        type: "GET",
			        url: gurl,
			        dataType: "json",
			        async:false,
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strSOCode){
			        			alert("Invalid Customer Code");
			        			$("#txtSOCode").val('');
			        			$("#txtSOCode").focus();
			        			
			        		}else{	
			        			funRemoveAllRows();
			        			$("#txtSOCode").val(response.strSOCode);
								$("#txtSODate").val(response.dteSODate);
// 								funSetCuster(response.strCustCode); 
								$("#txtCustCode").val(response.strCustCode);
								$("#lblCustomerName").text(response.strcustName);
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
								$('#cmbSettlement').val(response.strSettlementCode);
								$('#txtMobileNoForSettlement').val(response.strMobileNoForSettlement);
								$("#txtCustPONo").val(response.strCustPONo);						
								$("#txtCPODate").val(response.dteCPODate);
// 								funSetLocation(response.strLocCode);
								$("#txtLocCode").val(response.strLocCode);						
								$("#lblLocName").text(response.strLocName);	
									
								$('#txtAgainst').val(response.strAgainst);
								if(response.strAgainst=="Sales Projection")
			        			{
// 			        			document.all["txtSOCode"].style.display = 'block';
			        			
			        			
// 			        			}else
// 			        				{
// 			        				document.all["txtSOCode"].style.display = 'none';
			        			
			        				}
								$('#txtCode').val(response.strCode);
								
								$('#txtFulmtDate').val(response.dteFulmtDate);
								$('#cmbCurrency').val(response.strCurrency);
								$('#txtwarmonth').val(response.intwarmonth);
								$('#cmbPayMode').val(response.strPayMode);
								$('#txtDiscPer').val(response.dblDisRate);
								$('#txtNarration').val(response.strNarration);
								$('#txtSubTotal').val(response.dblSubTotal);
								$('#txtDisc').val(response.dblDisAmt);
								$('#txtExtraCharges').val(response.dblExtra);
								$('#txtFinalAmt').val(response.dblTotal);
								$('#txtDblCurrencyConv').val(response.dblConversion);
								
								if(response.strCloseSO =="Y")
									{
									//$('#chkCloseSO').prop('checked', true);	
									$('#chkCloseSO').attr('checked', true);
									}
								
								
// 								funSetSalesOrderDtlData(response.strSOCode)
// 									var prodCodes="";
// 								$.each(response.listSODtl, function(i,item)
// 			       	       	    	 {
// 										var dtlDate = response.listSODtl[i];
// 										prodCodes=prodCodes+dtlDate.strProdCode+",";
// 			       	       	    	 });
// 									alert(prodCodes);

								
								$.each(response.listSODtl, function(i,item)
			       	       	    	 {
// 										var dtlDate = response.listSODtl[i];
										
			       	       	    	    funfillSalesOrderDataRow(response.listSODtl[i]);
			       	       	    	                                           
			       	       	    	 });
								
// 								$('#cmbUOM').val(response.);
// 								$('#').val(response.);
								
// 								$('#').val(response.);
// 								$('#').val(response.);
// 								$('#').val(response.);
								
								
								funLoadTaxDtl(response.strSOCode);	
								
								
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
		 	
		 	
		 	function funSetSalesOrderDtlData(code)
		 	{
		 		
		 		gurl=getContextPath()+"/SalesOrderDtlData.html?soCode="+code;
				$.ajax({
			        type: "GET",
			        url: gurl,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strSOCode){
			        			alert("Invalid Customer Code");
			        			
			        			
			        		}else{			   
			
			        			$.each(response, function(i,item)
			       	       	    	 {
			       	        			
			       	       	    	    funfillSalesOrderDataRow(response);
			       	       	    	                                           
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
		 	
		 	
		 	
			//Get grn taxDtl data
			function funLoadTaxDtl(code) 
			{
				var curr= $("#txtDblCurrencyConv");
				var searchUrl = "";
				searchUrl = getContextPath() + "/loadSOTaxDtl.html?strSOCode=" + code;
				$.ajax({
					type : "GET",
					url : searchUrl,
					dataType : "json",
					 async:false,
					success : function(response) 
					{
						funRemoveTaxRows();
						$.each(response, function(i,item)
			            {
							funAddTaxRow1(response[i].strTaxCode,response[i].strTaxDesc,response[i].strTaxableAmt/curr,response[i].strTaxAmt/curr);
			            });
					},
					error : function(jqXHR, exception) {
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
	
		 	function funSetCuster(code)
			{
				gurl=getContextPath()+"/loadPartyMasterData.html?partyCode=";
				$.ajax({
			        type: "GET",
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strPCode){
			        			alert("Invalid Customer Code");
			        			$("#txtPartyCode").val('');
			        			$("#txtPartyCode").focus();
			        			
			        		}else{			   
			        			$("#txtCustCode").val(response.strPCode);
								$("#lblCustomerName").text(response.strPName);
								$("#lblCreditDays").text(response.intCreditDays);
								$("#lblCreditLimits").text(response.dblCreditLimit);
								       
								
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
								$("#cmbSettlement").focus();
								
								$("#cmbCurrency").val(response.strCurrency);
						 		var currencyCode=$("#cmbCurrency").val();
				     	  		var currValue=funGetCurrencyCode(currencyCode);
				     	  		$("#txtDblCurrencyConv").val(currValue);
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
				
	function funSetLocation(code) {
	var searchUrl = "";
	searchUrl = getContextPath()
			+ "/loadLocationMasterData.html?locCode=" + code;

	$.ajax({
		type : "GET",
		url : searchUrl,
		dataType : "json",
		success : function(response) {
			if (response.strLocCode == 'Invalid Code') {
				alert("Invalid Location Code");
				$("#txtLocCode").val('');
				$("#lblLocName").text("");
				$("#txtLocCode").focus();
			} else {
				$("#txtLocCode").val(response.strLocCode);						
				$("#lblLocName").text(response.strLocName);
				$("#txtCustCode").focus();
			}
		},
		error : function(jqXHR, exception) {
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
	
	function funSetProduct(code)
	{
			var searchUrl="";
			var supp="";
			searchUrl=getContextPath()+"/loadProductDataForTrans.html?prodCode="+code+"&suppCode="+supp;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response!="Invalid Product Code")
				    	{
				    		
				    		
			     	  		var curr=$("#txtDblCurrencyConv").val();
			     	  		
							$("#hidProdCode").val(response[0][0]);
							$("#txtProdName").val(response[0][1]);
							$("#txtPrice").val(response[0][3]/curr);
							$("#cmbUOM").val(response[0][2]);
							$("#txtWeight").val(response[0][7]);
							$("#txtQty").focus();
							$("#btnAddChar").attr("disabled",false);
							var custCode = $("#txtCustCode").val();
							var soDate = $("#txtSODate").val();
							funGetSalesAvg(custCode,response[0][0],soDate); 
							funGetProdStk(response[0][0]);
							 
							 
					     }
				    	else
				    		{
				    		  alert("Invalid Product Code");
				    		  $("#txtProdName").val('') 
				    		  $("#txtSubGroup").focus();
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
	
	function btnAdd_onclick()
	{
		
		if($("#txtProdName").val().length<=0)
			{
				$("#txtProdName").focus();
				alert("Please Enter Product Name Or Search");
				return false;
			}
	
	    if($("#txtQty").val().trim().length==0 || $("#txtQty").val()== 0)
	        {		
	          alert("Please Enter Quantity");
	          $("#txtQty").focus();
	          return false;
	       } 
	    else
	    	{
	    	 var strProdCode=$("#hidProdCode").val();
	    	 if(funDuplicateProduct(strProdCode))
	    		 {
	    		 funAddProductRow();
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
	    				flag=false;
    				}
				});
			    
	    	}
	    return flag;
	}
	
	function funAddProductRow() 
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = $("#hidProdCode").val().trim();
		var strProdName=$("#txtProdName").val().trim();
		var strUOM=$("#cmbUOM").val();	
	    var dblQty = $("#txtQty").val();
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=$("#txtWeight").val();
	    var dblTotalWeight=dblQty*dblWeight;
	    var dblPrice=$("#txtPrice").val();
	    var dblDiscount=$("#txtDiscount").val();
	    var amount=(dblQty*dblPrice)-dblDiscount;
	    var strRemarks=$("#txtRemarks").val();
	    var dblAcceptQty=dblQty;
	    var custCode = $("#txtCustCode").val();
	    var soDate = $("#txtSODate").val();
	    var avgValue =$("#lblAvg").text();
// 	    var avgValue = retValue;
// 	    var avgValue = SaleDtl.dblAvgQty;
	    row.insertCell(0).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"2%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";
	    
	    row.insertCell(3).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblQty\" type=\"text\" class=\"Box txtQty \"  required = \"required\"  class=\"decimal-places inputText-Auto txtQty \"  size=\"5%\"  style=\"text-align: right;\"  id=\"txtQty."+(rowCount)+"\" value='"+dblQty+"' >";
	    row.insertCell(4).innerHTML= "<input  type=\"text\"   style=\"text-align: right;\" class=\"Box\" size=\"5%\" id=\"txtAvgQty."+(rowCount)+"\" value='"+avgValue+"' >";
	    row.insertCell(5).innerHTML= "";
	    row.insertCell(6).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAcceptQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto txtQty \" id=\"txtAcceptQty."+(rowCount)+"\" value='"+dblQty+"' onblur=\"Javacsript:funUpdatePrice(this)\">";
	    
	    row.insertCell(7).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblWeight\" type=\"text\"  size=\"5%\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto  txtWeight\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(8).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box txtTotalWeight\" style=\"text-align: right;\" \size=\"5%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(9).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblUnitPrice\" type=\"text\" class=\"Box \" required = \"required\" style=\"text-align: right;\" size=\"7%\" class=\"decimal-places-amt inputText-Auto txtPrice \" id=\"txtPrice."+(rowCount)+"\" value="+dblPrice+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(10).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblDiscount\" type=\"text\" class=\"Box \" required = \"required\"  style=\"text-align: right;\" size=\"3%\" class=\"decimal-places-amt inputText-Auto txtDiscount \" id=\"txtDiscount."+(rowCount)+"\" value="+dblDiscount+" onblur=\"Javacsript:funUpdatePrice(this)\" >";	    
	    row.insertCell(11).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box txtAmount \" style=\"text-align: right;\" size=\"9%\" id=\"txtAmount."+(rowCount)+"\" value="+amount+" >";
	    row.insertCell(12).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strRemarks\" size=\"14%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	 	row.insertCell(13).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    
	    $("#txtSubGroup").focus();
	    funClearProduct();
	    funGetTotal();
	    return false;
	}
	
	
	function funUpdatePrice(object) {

		var dblQty ="";
		var dblWeight ="";
		var dblRate ="";
		var dbldis ="";
		
		
		var $row = $(object).closest("tr");
		
		if ($row.find(".txtQty").val() != '') {
			dblQty = parseFloat($row.find(".txtQty").val());
		}
		
		if ($row.find(".txtWeight").val() != '') {
			dblWeight = parseFloat($row.find(".txtWeight").val());
		}
		
		if ($row.find(".txtPrice").val() != '') {
			dblRate = parseFloat($row.find(".txtPrice").val());
		}
		
		if ($row.find(".txtDiscount").val() != '') {
			dbldis = parseFloat($row.find(".txtDiscount").val());
		}
		
//			alert(dblQty+""+dblWeight+""+dblRate);

		 if(!(isNumber(dblQty))){
			dblQty=0;
			$row.find(".dblQty").val("0");
		}
		
		if(!(isNumber(dblWeight))){
			dblWeight=0;
			$row.find(".txtWeight").val("0");
		}
		
		if(!(isNumber(dblRate))){
			dblRate=0;
			$row.find(".txtPrice").val("0");
		} 
		
		if(!(isNumber(dbldis))){
			dbldis=0;
			$row.find(".txtDiscount").val("0");
		} 
		
		var dblTotalWeight=dblQty * dblWeight;
	    var dblTotalPrice= (dblQty * dblRate)-dbldis;	    
	    
		$row.find(".txtTotalWeight").val(dblTotalWeight);
		$row.find(".txtAmount").val(dblTotalPrice);
		
		//funCalculateTotalAmt();
}
 
	function isNumber(n) {
		  return !isNaN(parseFloat(n)) && isFinite(n);
	}
	
	function funGetTotal()
	{
		
		var subtotal=0.00;
		var extraChange=0.00;
		var finalAmt=0.00;
		
		$('#tblProdDet tr').each(function() {
			
		    var totalPriceCell = $(this).find(".txtAmount").val();
		    
		    if(!(isNumber(totalPriceCell))){
		    	totalPriceCell=0;
			} 
		    
		    subtotal=parseFloat(subtotal)+parseFloat(totalPriceCell);
		    
		    var dis = $(this).find(".txtDiscount").val();
		    
		    if(!(isNumber(dis))){
		    	dis=0;
			} 
		    
		    subtotal=parseFloat(subtotal)-parseFloat(dis);
		    
		    var finalValue = $(this).find(".txtPrice").val();
		    
		    if(!(isNumber(finalValue))){
		    	finalValue=0;
			} 
		    
		    
		 });
		
		$("#txtSubTotal").val(subtotal);

		
		
		
		var totalTaxAmt=0,totalTaxableAmt=0;
		var subTotal=parseFloat($("#txtSubTotal").val());
		var table = document.getElementById("tblTax");
		var rowCount = table.rows.length;
		for(var i=0;i<rowCount;i++)
		{
			totalTaxableAmt=parseFloat(document.getElementById("txtTaxableAmt."+i).value)+totalTaxableAmt;
			totalTaxAmt=parseFloat(document.getElementById("txtTaxAmt."+i).value)+totalTaxAmt;
		}
		totalTaxableAmt=totalTaxableAmt.toFixed(2);
		totalTaxAmt=totalTaxAmt.toFixed(2);
		
		var discPer=0.0
		if(!($("#txtDiscPer").val()=='')){
			discPer=$("#txtDiscPer").val();
		}
		
		
		var disAmt=(subtotal*discPer)/100;
		$("#txtDisc").val(disAmt);

		var disAmt = $('#txtDisc').val();
		var finalAmt=parseFloat(subTotal)+parseFloat(totalTaxAmt)-disAmt;
		$("#txtFinalAmt").val(finalAmt);
		  
	}
	
	
		
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblProdDet");
	    table.deleteRow(index);
	}
	
	function funfillSalesOrderDataRow(SaleDtl)
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode =SaleDtl.strProdCode;
		var strProdName=SaleDtl.strProdName;
		var strUOM="";	
	    var dblQty = SaleDtl.dblQty;
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=SaleDtl.dblWeight;
	    var dblTotalWeight=dblQty*dblWeight;
	    var dblPrice=SaleDtl.dblUnitPrice;
	    var dblDiscount=SaleDtl.dblDiscount;
	    var amount=(dblQty*dblPrice)-dblDiscount;
	    var strRemarks=SaleDtl.strRemarks;
	    var dblAcceptQty=SaleDtl.dblAcceptQty;
	    var custCode = $("#txtCustCode").val();
	    var soDate = $("#txtSODate").val();
// 	    alert(funGetSalesAvg(custCode,strProdCode,soDate));
// 	    alert(retValue);
		var dblStk = SaleDtl.dblAvalaibleStk;
	    var avgValue = SaleDtl.dblAvgQty;
	    row.insertCell(0).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"2%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";
	 
	    row.insertCell(3).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblQty\" type=\"text\"  class=\"Box txtQty \" required = \"required\"  class=\"decimal-places inputText-Auto txtQty \" id=\"txtQty."+(rowCount)+"\" value='"+dblQty+"' >";
	    row.insertCell(4).innerHTML= "<input  type=\"text\"   style=\"text-align: right;\" class=\"Box\" size=\"5%\" id=\"txtAvgQty."+(rowCount)+"\" value='"+avgValue+"' >";
	    row.insertCell(5).innerHTML= dblStk;
	    row.insertCell(6).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAcceptQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto txtQty \" id=\"txtAcceptQty."+(rowCount)+"\" value='"+dblAcceptQty+"' onblur=\"Javacsript:funUpdatePrice(this)\">";
	    
	    row.insertCell(7).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto txtWeight \" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(8).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box txtTotalWeight \" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(9).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblUnitPrice\" type=\"text\" class=\"Box txtPrice \" required = \"required\" style=\"text-align: right;\" size=\"5%\" class=\"decimal-places-amt inputText-Auto\" id=\"txtPrice."+(rowCount)+"\" value="+dblPrice+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(10).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblDiscount\" type=\"text\"  required = \"required\" class=\"Box txtDiscount\" style=\"text-align: right;\" size=\"3%\" class=\"decimal-places-amt inputText-Auto\" id=\"txtDiscount."+(rowCount)+"\" value="+dblDiscount+" onblur=\"Javacsript:funUpdatePrice(this)\" >";	    
	    row.insertCell(11).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box txtAmount \" style=\"text-align: right;\" size=\"9%\" id=\"txtAmount."+(rowCount)+"\" value="+amount+" >";
	    row.insertCell(12).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strRemarks\" size=\"20%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	 	row.insertCell(13).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    $("#txtSubGroup").focus();
	    funGetTotal();
	    
// 	    var soCode=SaleDtl.strSOCode;
// 	    funLoadSalesChar(soCode,strProdCode);
	    
	    return false;
	
	}

	
// 	function funLoadSalesChar(soCode,strProdCode)
// 	{
		
		
		
// 	}
	
	
	
	
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
		
		var printSOKOT='';
		<%if(null!=session.getAttribute("PrintSOKOT")){%>
		printSOKOT='<%=session.getAttribute("PrintSOKOT").toString()%>';
			<%session.removeAttribute("PrintSOKOT");%>
			if(printSOKOT=='Y')
			{
				var isOk=confirm("Do You Want to print KOT?");
				if(isOk)
				{
					window.open(getContextPath()+"/printSalesOrderKOTOnSalesOrder.html?rptSOCode="+code,'_blank');
				}
			}						
		<%}%>

		});
	
	function funCallFormAction(actionName,object) 
	{
		var flg=true;
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;	
		
		if ($("#txtSODate").val()=="") 
		    {
			 alert('Invalid Date');
			 $("#txtSODate").focus();
			 return false;  
		   }
		
		 if($("#txtCustCode").val()=="")
			{
				alert("Please Enter CustomerCode");
				$("#txtCustCode").focus();
				return false;
			}
		 if ($("#txtCPODate").val()=="") 
		    {
			 alert('Invalid Date');
			 $("#txtCPODate").focus();
			 return false;  
		   }
	  if($("#txtLocCode").val()=="")
		{
			alert("Please Enter LocationCode");
			$("#txtLocCode").focus();
			return false;
		}
	  if ($("#txtFulmtDate").val()=="") 
	    {
		 alert('Invalid Date');
		 $("#txtFulmtDate").focus();
		 return false;  
	   }
	  
	  if($("#txtSettlementType").val()=="Online Payment")
	  {
 		if($("#txtMobileNoForSettlement").val()=="")
 			{
 				alert('Mobile No. Not found ');
 				return false;
 			}
	  }
	  
	  if(rowCount<1)
		{
			alert("Please Add Product in Grid");
			return false;
		}
	  
	  else
		{
			return true;
			
		}
	  
	
	}
	
	$(function()
			{
				$("#txtSOCode").blur(function() 
						{
							var code=$('#txtSOCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								funSetSalesData(code);
							}
						});
				
								
// 				$('#txtLocCode').blur(function () {
// 					var code=$('#txtLocCode').val();
// 					if (code.trim().length > 0 && code !="?" && code !="/"){
// 						funSetLocation(code);
// 					   }
// 					});
				
				
// 				$('#txtLocCode').blur(function () {
// 					 var code=$('#txtLocCode').val();
// 					 if (code.trim().length > 0 && code !="?" && code !="/"){					  
// 						   funSetLocation(code);
// 					   }
// 					});
				
				$('#txtProdName').blur(function () {
					var code=$('#hidProdCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){								  
						funSetProduct(code);
					   }
					});
				$('#txtDiscPer').blur(function ()
				{
					
					var disPer=$('#txtDiscPer').val();
					if (disPer.trim().length > 0 && disPer !="0")
						{
						var subTotal=$('#txtSubTotal').val();
						
						if(subTotal!=0)
							{
								var discountAmt=parseFloat(disPer)*(parseFloat(subTotal)/100);
								$("#txtDisc").val(parseFloat(discountAmt).toFixed(maxAmountDecimalPlaceLimit));
								funGetTotal();
							}
						else
							{
								$("#txtDiscPer").val("0.0");
								funGetTotal();
							}
						
						}
					else
					{
						$("#txtDisc").val("0.0");
						funGetTotal();
					}
				});
				
			});
	
	function funClearProduct()
	{
		$("#txtSubGroup").val("");
		$("#txtProdName").val("");
		$("#strUOM").val("");
	//	$("#lblProdName").text("");
		$("#txtQty").val("");
		$("#txtPrice").val("");
		
		$("#txtRemarks").val("");
		$("#txtWeight").val("");
		$("#txtDiscount").val(0);
	}
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
	
	function funResetFields()
	{
		location.reload(true); 
	}
	
	function funRemoveAllRows() 
    {
		 var table = document.getElementById("tblProdDet");
		 var rowCount = table.rows.length;			   
		//alert("rowCount\t"+rowCount);
		for(var i=rowCount-1;i>=0;i--)
		{
			table.deleteRow(i);						
		} 
    }
	
	function funShowSOFieled()
	{
		var agianst = $("#cmbAgainst").val();
		if(agianst=="Sales Projection")
		{
			document.all["txtCode"].style.display = 'block';
		}else
		{
			document.all["txtCode"].style.display = 'none';
		}
	}
	
	
	function funAddChar()
	{
		var prodCode= $("#hidProdCode").val();
		window.open("frmSalesProdChar.html?prodCode=" + prodCode, "Characteristics", "scrollbars=1,width=500,height=200,top=500, left=500");		
	
// 		alert("Hii");
// 		return true;
	}
	
	
	
	function funSetCharData(value)
	{
		//alert(value);
		funLoadCharBean(value);
	}
	
	
	
// 	function funLoadCharBean(value) {
// 		var searchUrl = "";
		
// 		searchUrl = getContextPath() + "/charSalesData.html";
// 		//alert(searchUrl);
// 		$.ajax({
// 			type : "POST",
// 			url : searchUrl,
// 			data : JSON.stringify(value),
// 			//data : JSON.stringify({ "chaaar": [{ name: "Gerry", id: 20 },{ name: "Merray", id: 50 }] }),
// 			contentType: 'application/json',
// 			success : function(data) {
// 				if ('Invalid Code' == value) {
// 					alert('Hii');
// 				} else {
					
// 				}
// 			},
// 			error : function(jqXHR, exception) {
// 				if (jqXHR.status === 0) {
// 					alert('Not connect.n Verify Network.');
// 				} else if (jqXHR.status == 404) {
// 					alert('Requested page not found. [404]');
// 				} else if (jqXHR.status == 500) {
// 					alert('Internal Server Error [500].');
// 				} else if (exception === 'parsererror') {
// 					alert('Requested JSON parse failed.');
// 				} else if (exception === 'timeout') {
// 					alert('Time out error.');
// 				} else if (exception === 'abort') {
// 					alert('Ajax request aborted.');
// 				} else {
// 					alert('Uncaught Error.n' + jqXHR.responseText);
// 				}
// 			}
// 		});
// 	}
	
	
	function funLoadCharBean(response)
	{
		var obj = jQuery.parseJSON(response);
		$.each(obj, function(key,value) 
		{
			$.each(value, function(CharCode,CharName) {
				var table = document.getElementById("tblProdChar");
				var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    var strCCode =value.charCode;
				var strCharValue=value.CharName;
				var strProdCode = value.ProdCode;
 				
			    row.insertCell(0).innerHTML= "<input name=\"listSalesChar["+(rowCount)+"].strCharCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtCharCode."+(rowCount)+"\" value='"+strCCode+"' />";		  		   	  
			    row.insertCell(1).innerHTML= "<input name=\"listSalesChar["+(rowCount)+"].strCharValue\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtCharValue."+(rowCount)+"\" value='"+strCharValue+"'/>";
			    row.insertCell(2).innerHTML= "<input name=\"listSalesChar["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"'/>";
			    
			    return false;
			});
		});
	}
	
	
	
	function funGetSalesAvg(custCode,prodCode,dteSODate)
	{
		gurl=getContextPath()+"/SalesOrderCustAvg.html?custCode="+custCode+"&prodCode="+prodCode+"&dteSODate="+dteSODate;
// 		alert(gurl);
		$.ajax({
	        type: "GET",
	        url: gurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	var avgqty=response;
	        	if(avgqty=="NV")
				{
	        		$("#lblAvg").text("");
	        	}else
	        	{
	        		$("#lblAvg").text(response);
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
// 	                alert('Requested JSON parse failed.');
	            	$("#lblAvg").text("");
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
	
	function funOpenCustomer()
	{
// 		var retval = window.showModalDialog(
// 				"frmLoadMultiCustomer.html",
// 				"dialogHeight:600px;dialogWidth:500px;dialogLeft:400px;");

	var retval = window.open(
				"frmLoadMultiCustomer.html",
				"dialogHeight:600px;dialogWidth:500px;dialogLeft:400px;");
	   
		if (retval != null)
		{
			$("#txtCustCode").val(retval);

		}
	}
	
	function funPopulateProduct()
	{
		var custCodes= $("#txtCustCode").val();
		if(custCodes.length>7)
		{
			alert("Please Select only one Customer");
			return false;
		}
		var locCode=$("#txtLocCode").val();
 		var stdOrder= $("#hidStdValue").val();
 		var dteSODate = $("#txtSODate").val();
		var searchUrl="";
		searchUrl=getContextPath()+"/populateAllProductDataOfAllCustomer.html?custCodes="+custCodes+"&stdOrder="+stdOrder+
				"&dteSODate="+dteSODate+"&locCode="+locCode;
		
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	funRemoveAllRows();
			    	$.each(response, function(i,item)
					{
			    		funfillPopulateSalesOrderDataRow(response[i]);
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
	
	
	

	function funfillPopulateSalesOrderDataRow(SaleDtl)
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode =SaleDtl.strProdCode;
		var strProdName=SaleDtl.strProdName;
		var strUOM=SaleDtl.strUOM;	
	    var dblQty = SaleDtl.dblOverQty;
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=SaleDtl.dblWeight;
	    var dblTotalWeight=dblQty*dblWeight;
	    var dblPrice=SaleDtl.dblMRP;
	    var dblDiscount='0';
	    var amount=(dblQty*dblPrice)-dblDiscount;
	    var strRemarks='';
	    var dblAcceptQty='0';
	    var avgValue = '0';
	    var stdValueYN=$('#hidStdValue').val();
	    var stock=SaleDtl.dblStock;
	    parseFloat(stock).toFixed(maxQuantityDecimalPlaceLimit);
	    if(stdValueYN=='Y')
		{
	    	dblAcceptQty= dblQty;
	    }
	    else
	    {
	    	dblAcceptQty= SaleDtl.dblAcceptQty;
	    	avgValue= SaleDtl.dblAcceptQty;
	    }
	    if(stock==0)
	    {
	    	stock='';
	    }
	   
	    var custCode = $("#txtCustCode").val();
	    var soDate = $("#txtSODate").val();
// 	    alert(funGetSalesAvg(custCode,strProdCode,soDate));
// 	    alert(retValue);
	   
	    row.insertCell(0).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"2%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";
	 
	    row.insertCell(3).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblQty\" type=\"text\" class=\"Box txtQty \"  required = \"required\"  class=\"decimal-places inputText-Auto  \" id=\"txtQty."+(rowCount)+"\" value='"+dblQty+"' >";
	    row.insertCell(4).innerHTML= "<input  type=\"text\"   style=\"text-align: right;\" class=\"Box\" size=\"5%\" id=\"txtAvgQty."+(rowCount)+"\" value='"+avgValue+"' >";
	    
	    row.insertCell(5).innerHTML= "<input  type=\"text\"   style=\"text-align: right;\" class=\"Box\" size=\"5%\" id=\"txtStock."+(rowCount)+"\" value='"+stock+"' >";
	    row.insertCell(6).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAcceptQty\" type=\"text\"   required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto txtQty \" id=\"txtAcceptQty."+(rowCount)+"\" value='"+dblAcceptQty+"' onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(7).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto txtWeight \" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(8).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box txtTotalWeight \" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(9).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblPrice\" type=\"text\" class=\"Box txtPrice \" required = \"required\" style=\"text-align: right;\" size=\"5%\" class=\"decimal-places-amt inputText-Auto\" id=\"txtPrice."+(rowCount)+"\" value="+dblPrice+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(10).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblDiscount\" type=\"text\"  required = \"required\" class=\"Box txtDiscount\" style=\"text-align: right;\" size=\"3%\" class=\"decimal-places-amt inputText-Auto\" id=\"txtDiscount."+(rowCount)+"\" value="+dblDiscount+" onblur=\"Javacsript:funUpdatePrice(this)\" >";	    
	    row.insertCell(11).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box txtAmount \" style=\"text-align: right;\" size=\"9%\" id=\"txtAmount."+(rowCount)+"\" value="+amount+" >";
	    row.insertCell(12).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strRemarks\" size=\"20%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	 	row.insertCell(13).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    $("#txtSubGroup").focus();
	    funGetTotal();
	    
// 	    var soCode=SaleDtl.strSOCode;
// 	    funLoadSalesChar(soCode,strProdCode);
	    
	    return false;
	
	} 
	
	
	/**
	 * Get product stock passing value product code
	 */
	function funGetProdStk(strProdCode)
	{
		var searchUrl="";	
		var locCode=$("#txtLocCode").val();
		var dblStock="0";
		searchUrl=getContextPath()+"/getdbProdStk.html?prodCode="+strProdCode+"&locCode="+locCode;	
		//alert(searchUrl);		
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    async: false,
			    success: function(response)
			    {
			    	
			       if(response!="0")	
			       {
			    	dblStock= response;
			    	dblStock=Math.round(dblStock * 100) / 100;
			    	$("#lblStk").text(dblStock);
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
	
	
 //Open Excel Export/Impoert From
	function funOpenExportImport()			
	{
		if($('#txtCustCode').val()!='')
		{
			var transactionformName="frmSalesOrder";
	        var retval=window.open("frmExcelExportImportForSalesOrder.html","","dialogHeight:500px;dialogWidth:500px;dialogLeft:500px;");
	        var timer = setInterval(function ()
		    {
				if(retval.closed)
				{
					if (retval.returnValue != null)
					{
						var response = retval.returnValue;
						funRemoveAllRows();
						$.each(response, function(i,item)
						{
							funExcelProductPopulate(response[i].strProdCode,response[i].strProdName,response[i].dblQty,response[i].dblQty,response[i].dblUnitPrice,response[i].dblWeight,response[i].strRemarks,response[i].strProdChar,response[i].strUOM);
						});
					}
					clearInterval(timer);
				}
			}, 500);
		}
		else
		{
			alert("Please Select Customer");
			return false;
		}
	}
	
	
	
	function funExcelProductPopulate(strProdCode,strProdName,dblQty,dblAcceptQty,dblUnitPrice,dblWeight,strRemarks,strProdChar,strUOM)
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = strProdCode;
		var strProdName=strProdName;
		var strUOM=strUOM;	
	    var dblQty = dblQty;
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=dblWeight;
	    var dblTotalWeight=dblQty*dblWeight;
	    var dblPrice=dblUnitPrice;
	    var dblDiscount=0;
	    var amount=(dblQty*dblPrice)-dblDiscount;
	    var strRemarks=strRemarks;
	   // var dblAcceptQty=dblAcceptQty;
	    var custCode = $("#txtCustCode").val();
	    if(custCode.length=='')
	    	{
	    		alert("Please Select Customer");
	    		return false;
	    	}
	    
	    var soDate = $("#txtSODate").val();
	    var avgValue =0;
// 	    var avgValue = retValue;
// 	    var avgValue = SaleDtl.dblAvgQty;
	    row.insertCell(0).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strUOM\" readonly=\"readonly\" class=\"Box\" size=\"2%\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"'/>";
	    
	    row.insertCell(3).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblQty\" type=\"text\" class=\"Box txtQty \"  required = \"required\"  class=\"decimal-places inputText-Auto txtQty \" id=\"txtQty."+(rowCount)+"\" value='"+dblQty+"' >";
	    row.insertCell(4).innerHTML= "<input  type=\"text\"   style=\"text-align: right;\" class=\"Box\" size=\"5%\" id=\"txtAvgQty."+(rowCount)+"\" value='"+avgValue+"' >";
	    row.insertCell(5).innerHTML= "";
	    row.insertCell(6).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAcceptQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto txtQty \" id=\"txtAcceptQty."+(rowCount)+"\" value='"+dblQty+"' onblur=\"Javacsript:funUpdatePrice(this)\">";
	    
	    row.insertCell(7).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblWeight\" type=\"text\"  size=\"5%\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto  txtWeight\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(8).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box txtTotalWeight\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(9).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblUnitPrice\" type=\"text\" class=\"Box \" required = \"required\" style=\"text-align: right;\" size=\"5%\" class=\"decimal-places-amt inputText-Auto txtPrice \" id=\"txtPrice."+(rowCount)+"\" value="+dblPrice+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(10).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblDiscount\" type=\"text\" class=\"Box \" required = \"required\"  style=\"text-align: right;\" size=\"3%\" class=\"decimal-places-amt inputText-Auto txtDiscount \" id=\"txtDiscount."+(rowCount)+"\" value="+dblDiscount+" onblur=\"Javacsript:funUpdatePrice(this)\" >";	    
	    row.insertCell(11).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box txtAmount \" style=\"text-align: right;\" size=\"9%\" id=\"txtAmount."+(rowCount)+"\" value="+amount+" >";
	    row.insertCell(12).innerHTML= "<input name=\"listSODtl["+(rowCount)+"].strRemarks\" size=\"20%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	 	row.insertCell(13).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    
	    $("#txtSubGroup").focus();
	    funClearProduct();
	    funGetTotal();
	    return false;
	}
	 
	$(document).ready(function()
			{
				$(function() {
					
					$("#txtSubGroup").autocomplete({
					source: function(request, response)
					{
						var searchUrl=getContextPath()+"/AutoCompletGetSubgroupNameForSO.html";
						$.ajax({
						url: searchUrl,
						type: "POST",
						data: { term: request.term },
						dataType: "json",
						 
							success: function(data) 
							{
								sgData=data;
								response($.map(data, function(v,i)
								{
								//	$('#hidSubGroupCode').val(   );
									return {
										label: v.strSGName,
										value: v.strSGName
										
										};
										
										
								}));
								
							}
						});
					}
				});
					
				});
			});
		
		$(document).ready(function()
				{
					$(function() {
						
						
						$("#txtProdName").autocomplete({
						source: function(request, response)
						{
							var sgName= $("#txtSubGroup").val();
							var custCode=$("#txtCustCode").val();
							var searchUrl=getContextPath()+"/AutoCompletGetProdNamewithSubgroup.html";
							$.ajax({
							url: searchUrl,
							type: "POST",
							data: { term: request.term,sgName:sgName,custcode:custCode },
							dataType: "json",
							 
								success: function(data) 
								{
									prodData=data;
									response($.map(data, function(v,i)
									{
										return {
											label: v.strProdName,
											value: v.strProdName
											
											};
											
									}));
									
								}
							});
						}
					});
					});
				});
		
		
function funGetKeyCode(event,controller) {
		
	    var key = event.keyCode;
	    if(controller=='SubGroup' && key==13 || controller=='SubGroup' && key==9)
	    {
	    	$.each(sgData, function(i,item)
			    	{
				var sgName = $("#txtSubGroup").val();
						if(sgName==item.strSGName)
							{
							$('#hidSubGroupCode').val(item.strSGCode);
							
							}
			    	});
	    	$('#txtProdName').focus();
	    }
	  
	    if(controller=='Product' && key==13 || controller=='Product' && key==9)
	    {
	    
	 	    	$.each(prodData, function(i,item)
	 			    	{
	 				var prodName = $("#txtProdName").val();
	 						if(prodName==item.strProdName)
	 							{
	 							$('#hidProdCode').val(item.strProdCode);
	 							$('#txtPrice').val(item.dblUnitPrice);
	 							$('#txtWeight').val(item.dblWeight);
	 							$('#lblStk').text(item.dblStock);
	 							$('#cmbUOM').val(item.strUOM);
	 							$('#lblUOM').text(item.strUOM);
	 							$('#txtRemarks').val(item.strRemark);
	 							
	 							}
	 			    	});
	 	    	document.getElementById('txtQty').focus();	
	 	    	
	    }
	    if(controller=='Qty' && key==13 || controller=='Qty' && key==9)
	    {
	    	document.getElementById('btnAdd').focus();
	    }
	    
	    if(controller=='AddBtn' && key==13)
	    	{
	    	funAddProductRow();
	    	$("#txtSubGroup").focus();
	    	}
	    
	    if(controller=='changeDiscAmt' && key==13){
	    	funUpdateDiscAmt();
	    }
	}
	

	function funChangeCombo() {
		
		// Ajax call loadSettlementMasterData  --> pass settlement code
		var code=$("#cmbSettlement").val();
		var searchurl=getContextPath()+"/loadSettlementMasterData.html?code="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strSettlementCode=='Invalid Code')
			        	{
			        		alert("Settlement type not found");
			        	}
			        	else
			        	{
				        	$("#txtSettlementType").val(response.strSettlementType);
				        	//alert($("#txtSettlementType").val());
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
	
		$(document).keypress(function(e) {
			  if(e.keyCode == 120) {
				  if(funCallFormAction())
					  {
					 	 document.forms["SOForm"].submit();
					  }
				 
			  }
			});
			
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
		    var suppCode=$("#txtCustCode").val();
			var currValue='<%=session.getAttribute("currValue").toString()%>';
			if(currValue==null)
			{
			  currValue=1;
			}
		    var cnt=0;
		    for(var cnt=0;cnt<rowCount;cnt++)
		    {
		    	
		//	    	var prodCode= $(this).find(".txtProdCode").val();
		    	var prodCode=document.getElementById("txtProdCode."+cnt).value
		    	
		    	
		    	
		    	var qty=parseFloat(document.getElementById("txtQty."+cnt).value);		    	
		    	var unitPrice=parseFloat(document.getElementById("txtPrice."+cnt).value)/currValue;
		//     	var discAmt1=parseFloat(document.getElementById("txtDiscount").value);
		    
		    	prodCodeForTax=prodCodeForTax+"!"+prodCode+","+unitPrice+","+suppCode+","+qty+","+0;
		    	cnt++;
		    }
			
		    prodCodeForTax=prodCodeForTax.substring(1,prodCodeForTax.length).trim();
		     forTax(prodCodeForTax);
		}
		
		function forTax(prodCodeForTax)
		{
			var dteInv =$('#txtSODate').val();
			var CIFAmt=0;
			
		    gurl=getContextPath()+"/getTaxDtlForProduct.html?prodCode="+prodCodeForTax+"&taxType=Sales&transDate="+dteInv+"&CIFAmt="+CIFAmt,
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
			       			var dblExtraCharge="0.0";
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
		 * Filling Tax in Grid
		 */
		function funAddTaxRow1(taxCode,taxDesc,taxableAmt,taxAmt) 
		{	
		    var table = document.getElementById("tblTax");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listSalesOrderTaxDtl["+(rowCount)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount)+"\" value='"+taxCode+"' >";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listSalesOrderTaxDtl["+(rowCount)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+taxDesc+"'>";		    	    
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listSalesOrderTaxDtl["+(rowCount)+"].strTaxableAmt\" id=\"txtTaxableAmt."+(rowCount)+"\" value="+taxableAmt+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listSalesOrderTaxDtl["+(rowCount)+"].strTaxAmt\" id=\"txtTaxAmt."+(rowCount)+"\" value="+taxAmt+">";		    
		    row.insertCell(4).innerHTML= '<input type="button" size=\"6%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		    funCalTaxTotal();
		    funClearFieldsOnTaxTab();
		    
		    return false;
		}
		
		function funClearFieldsOnTaxTab()
		{
			$("#txtTaxCode").val("");
			$("#lblTaxDesc").text("");
			$("#txtTaxableAmt").val("");
			$("#txtTaxAmt").val("");
			$("#txtTaxCode").focus();
		}
		
		function funCalTaxTotal()
		{
			var totalTaxAmt=0,totalTaxableAmt=0;
			var table = document.getElementById("tblTax");
			var rowCount = table.rows.length;
			var subTotal=parseFloat($("#txtSubTotal").val());
			for(var i=0;i<rowCount;i++)
			{
				totalTaxableAmt=parseFloat(document.getElementById("txtTaxableAmt."+i).value)+totalTaxableAmt;
				totalTaxAmt=parseFloat(document.getElementById("txtTaxAmt."+i).value)+totalTaxAmt;
			}
			
			totalTaxableAmt=totalTaxableAmt.toFixed(2);
			totalTaxAmt=totalTaxAmt.toFixed(2);
			var grandTotal=parseFloat(subTotal)+parseFloat(totalTaxAmt);
			grandTotal=grandTotal.toFixed(2);
			$("#lblTaxableAmt").text(totalTaxableAmt);
			$("#lblTaxTotal").text(totalTaxAmt);			
		//		$("#lblPOGrandTotal").text(grandTotal);
		//		var taxAmt=$("#txtPOTaxAmt").val();
			var disAmt = $('#txtDisc').val();
		//	var extCharge = $('#txtExtraCharges').val();
			var finalAmt=parseFloat(subTotal)+parseFloat(totalTaxAmt)-disAmt;
			$("#txtFinalAmt").val(finalAmt);
		}
		
		function funUpdateDiscAmt()
		{
			var totalTaxAmt=0,totalTaxableAmt=0;
			var subTotal=parseFloat($("#txtSubTotal").val());
			var table = document.getElementById("tblTax");
			var rowCount = table.rows.length;
			for(var i=0;i<rowCount;i++)
			{
				totalTaxableAmt=parseFloat(document.getElementById("txtTaxableAmt."+i).value)+totalTaxableAmt;
				totalTaxAmt=parseFloat(document.getElementById("txtTaxAmt."+i).value)+totalTaxAmt;
			}
			totalTaxableAmt=totalTaxableAmt.toFixed(2);
			totalTaxAmt=totalTaxAmt.toFixed(2);
			
			
			var disAmt = $('#txtDisc').val();
			var finalAmt=parseFloat(subTotal)+parseFloat(totalTaxAmt)-disAmt;
			$("#txtFinalAmt").val(finalAmt);
		}
		
		function funOnChangeCurrency()
		{
		   var currencyCode=$("#cmbCurrency").val();
		   var currValue=funGetCurrencyCode(currencyCode);
			if(currValue==null)
			{
				currValue=1.0;
			}
			$("#txtDblCurrencyConv").val(currValue);
		
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
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Sales Order</label>
	</div>
	<s:form name="SOForm" method="POST"
		action="saveSOData.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table
			style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>
					<div id="tab_container" style="height: 710px">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 100px; padding-left: 55px">GENERAL</li>
							<li data-state="tab2" style="width: 100px; padding-left: 55px">Address</li>
<!-- 							<li data-state="tab3" style="width: 100px; padding-left: 55px">Taxes</li> -->
						</ul>
						<div id="tab1" class="tab_content" style="height: 550px">

							<table class="transTable">
								
								<tr>
								<th align="Right" colspan="8"><a onclick="return funOpenExportImport()"
								href="javascript:void(0);">Export/Import</a></th>
						
									<th align="right" ><a id="baseUrl" href="#">
											Attach Documents </a></th>
								</tr>

								<tr>
									<td width="100px"><label>SO Code</label></td>
									<td width="140px"><s:input path="strSOCode" id="txtSOCode"
											ondblclick="funHelp('salesorder')"
											cssClass="searchTextBox" /></td>
									
									<td width="100px"><label>SO Date</label>
									<td><s:input path="dteSODate" id="txtSODate"
											 required="required"
											cssClass="calenderTextBox" /></td>
											<td></td>
											<td></td>
											<td></td>
								</tr>

<tr>
									<td><label>Customer Code</label></td>
									
<%-- 									<td colspan="3"><s:input path="strCustCode" id="txtCustCode"  --%>
<%--  								   ondblclick="funOpenCustomer()" cssClass="searchTextBox" /></td>  --%>
<!--  									<td colspan="2"><label id="lblCustomerName"  -->
<!-- 										class="namelabel"></label></td> -->
									<td ><s:input path="strCustCode" id="txtCustCode"
	 								ondblclick="funHelp('custMasterActive')" cssClass="searchTextBox" /></td> 
									<td colspan="2"><label id="lblCustomerName"
										class="namelabel"></label></td> 
										
									<td><label>Customer PO NO</label></td>
									<td><s:input id="txtCustPONo" type="text" path="strCustPONo"
										class="BoxW116px" /></td>
										
									<td><label>Customer PO Date</label></td>
									<td colspan="2" align="left"><s:input path="dteCPODate"
											 id="txtCPODate"
											required="required" cssClass="calenderTextBox" /></td>
								</tr>
								
								<tr>
									<td><label>Credit Days</label></td>
									<td><label id="lblCreditDays"></label></td>
									<td><label>Credit Limit</label></td>
									<td><label id="lblCreditLimits"></label></td>
								</tr>
								
								<tr>
									<td><label>Location Code</label></td>
									<td><s:input type="text" id="txtLocCode" path="strLocCode" value="${locationCode}"
											cssClass="searchTextBox" name="locCode" ondblclick="funHelp('locationmaster');" /></td>
									<td><label id="lblLocName"></label></td>
									
									<td><label>FulmtDate</label></td>
									<td><s:input colspan="3" type="text" id="txtFulmtDate"
											path="dteFulmtDate" cssClass="calenderTextBox" /></td>
<!-- 									<td><label>fulfilled</label></td> -->
<!-- 									<td><label id="dteFulfilled"></label></td> -->
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><label>Against</label></td>
									<td><s:select id="txtAgainst" path="strAgainst"
											items="${againstList}" cssClass="BoxW124px" onchange="funShowSOFieled()" /></td>
									<td ><s:input id="txtCode" path="strCode" style="display:none" ondblclick="funHelp('')" class="searchTextBox"></s:input></td>		
									<td width="100px"><label>Settlement</label>
									<td colspan="3">
										<s:select id="cmbSettlement" path="strSettlementCode"
											items="${settlementList}" cssClass="BoxW124px" onclick="funChangeCombo()"/>
									</td>
									
									<td><label>Mobile No.</label></td>
									<td>
										<s:input  type="text"  id="txtMobileNoForSettlement" name="txtMobileNoForSettlement" path="strMobileNoForSettlement" class="numeric" cssClass="BoxW116px" />
									</td>
									
									<td>
										<input type="hidden" id="txtSettlementType"> 
									</td>
									
								</tr>
								
								<tr>
									
									<td><label>Currency</label></td>
									<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px" onclick="funOnChangeCurrency()">
										</s:select></td>
										
										
										<td><label>Conversion</label></td>
										<td>
										<s:input  type="text"  id="txtDblCurrencyConv" style="text-align: right;" name="txtDblCurrencyConv" path="dblConversion" class="decimal-places numberField" cssClass="BoxW48px" />
										</td>
									<td><label>Warranty in Month</label></td>
										<td><s:input colspan="3" type="text" class="numeric"
												id="txtwarmonth" path="intwarmonth" cssClass="BoxW124px" /></td>
									
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
							
							<table class="transTableMiddle">
								<tr>	
									<td width="100px"><label for="a" name="lblSubGroup">Sub-Group</label></td>
									<td><input id="txtSubGroup" style="width:80%;text-transform: uppercase;"  name="SubgroupName" class="searchTextBox" 
										onkeypress="funGetKeyCode(event,'SubGroup')" ondblclick="funHelp('subgroup')" /></td>
									<input type="hidden" id="hidSubGroupCode" />
																	
									<td width="100px"><label>Product</label></td>
									<td><input id="txtProdName"
										 class="searchTextBox" onkeypress="funGetKeyCode(event,'Product')" ondblclick="funHelp('productProduced')" /></td>
									 <td><label id="lblUOM"></label></td>
									 <td>
									<input type="hidden" id="hidProdCode" />		
										<label>&nbsp;&nbsp;&nbsp;&nbsp;Stock &nbsp;&nbsp;&nbsp;&nbsp;</label> 
										<label id="lblStk"></label> 
										<label>&nbsp;&nbsp;&nbsp;&nbsp;Avg Qty&nbsp;&nbsp;&nbsp;&nbsp;</label> <label id="lblAvg"></label> 
										</td>

								</tr>
								<tr>
									<td><label>Unit Price</label></td>
									<td><input id="txtPrice" type="text"
										class="decimal-places numberField" /></td>
									<td><label>Wt/Unit</label></td>
									<td width="150px"><input type="text" id="txtWeight"
										class="decimal-places numberField" /></td>
									<td width="100px"><label>Quantity</label></td>
									<td><input id="txtQty" type="text"
										class="decimal-places numberField" style="width: 60%" 
										onkeypress="funGetKeyCode(event,'AddBtn')" /></td>
									<td width="5%">UOM</td>
									<td> <s:select id="cmbUOM" name="cmbUOM"
									path="" items="${uomList}" cssStyle="width: 60%" cssClass="BoxW124px"/></td>
								</tr>

								<tr>
									
									<!-- <td><label>Stock </label></td> -->
									<td><label>Discount</label></td>
									<td><input id="txtDiscount" type="text"
										class="decimal-places-amt numberField" value="0"
										class="BoxW116px" /></td>
									<td><label>Remarks</label></td>
									<td><input id="txtRemarks" class="longTextBox" style="width:100%" /></td>
									<td><input type="button" value="Add" class="smallButton"
										onclick="return btnAdd_onclick()" /></td>
									
<!-- 									<td>Populate Standard Order</td> -->
<!-- 									<td><input type="checkbox" id="strStdOrder" value="Y"  /></td> -->
<!-- 									<td><input type="button" value="Populate Product" class="form_button" -->
<!-- 										onclick="funPopulateProduct()" /></td>	 -->
										
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
										<td width="4%">Order Qty</td>
										<!--  COl4   -->
										<td width="4%">Avg Qty</td>
										<!--  COl5   -->
										<td width="4%">Stock</td>
										<!--  COl6   -->
										<td width="4%">Accepted Qty</td>
										<!--  COl7   -->
										<td width="4%">Wt/Unit</td>
										<!--  COl8   -->
										<td width="4%">Total Wt</td>
										<!--  COl9   -->
										<td width="5%">Price</td>
										<!--  COl10   -->
										<td width="3%">Discount</td>
										<!--  COl11   -->
										<td width="6%">Amount</td>
										<!--  COl12   -->
										<td width="11%">Remarks</td>
										<!--  COl13   -->
										<td width="4%">Delete</td>
										<!--  COl14   -->
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
										<col style="width: 16.5%">
										<!--  COl2   -->
										<col style="width: 3%">										
										<!--  COl3   -->
										<col style="width: 3.8%">
										<!--  COl4   -->
										<col style="width: 4%">
										<!--  COl5   -->
										<col style="width: 4%">
										<!--  COl6   -->
										<col style="width: 4%">
										<!--  COl7   -->
										<col style="width: 4%">
										<!--  COl8  -->
										<col style="width: 4%">
										<!--  COl9  -->
										<col style="width: 5%">
									    <!--  COl10   -->
										<col style="width: 3%">
										<!--  COl11   -->
										<col style="width: 6%">
										<!--  COl12   -->
										<col style="width: 11%">
										<!--  COl13   -->
										<col style="width: 2.5%">
										<!--  COl14   -->
										
										</tbody>

									</table>
								</div>

							</div>
							<table id="tbl2" class="transTable">
								<tr>
<!-- 									<td><label id="lblPayMode">Payment Mode</label></td> -->
<!-- 									<td> -->
<%-- 									<s:select id="cmbPayMode" items="${paymentMode}"  --%>
<%-- 									path ="strPayMode" cssClass="BoxW124px"> </s:select> --%>
											
<!-- 									</td> -->
									
									<td><s:input id="cmbPayMode"  
									path ="strPayMode" cssClass="BoxW124px" type="hidden"></s:input>
									</td>
									<td></td>
									<td><label id="lblSubTotal">SubTotal</label></td>
									<td colspan="3"><s:input type="text" id="txtSubTotal"
											path="dblSubTotal" readonly="true"
											class="decimal-places-amt numberField" /></td>
									
									
								</tr>

								<tr>
									
									<td><label id="lblDiscPer">Discount %</label></td>
				    				<td><input id="txtDiscPer"  type="text"  onblur="funCalDiscountAmt();" 
				    				class="decimal-places-amt numberField"/></td>
				    				
				    				<td><label id="lblDiscount">Discount Amt</label></td>
									<td colspan="3"><s:input type="text" id="txtDisc"
									path="dblDisAmt" onblur="funGetTotal();" onkeypress="funGetKeyCode(event,'changeDiscAmt')"
									cssClass="decimal-places-amt numberField" /></td>
								</tr>


								<tr>
									<td ><label id="lblNarration">Narration</label></td>
									<td><s:textarea cssStyle="width:80%" id="txtNarration" 
									path="strNarration" /></td>
									
									<td><label id="lblExtraCharges">Extra Charges:</label></td>
									<td colspan="3"><s:input type="text" id="txtExtraCharges"
											path="dblExtra" onblur="funGetTotal();"
											cssClass="decimal-places-amt numberField" /></td>
								</tr>
								<tr>
								</tr>
								<tr>
								</tr>

								<tr>
									<td><label id="lblCloseSO">Close SO</label></td>
									<td><s:checkbox element="li" id="chkCloseSO"
											path="strCloseSO" value="Y" /></td>
									<td><label id="lblFinalAmt">Final Amount:</label></td>
									<td><s:input type="text" id="txtFinalAmt"
											path="dblTotal" readonly="true"
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
									<td><s:input path="strBAdd1" id="txtBAddress1"
											cssClass="longTextBox" /></td>
									<td width="120px"><label>Address Line 1</label></td>
									<td><s:input path="strSAdd1" id="txtSAddress1"
											cssClass="longTextBox" /></td>
								</tr>

								<tr>
									<td><label>Address Line 2</label></td>
									<td><s:input path="strBAdd2" id="txtBAddress2"
											cssClass="longTextBox" /></td>
									<td><label>Address Line 2</label></td>
									<td><s:input path="strSAdd2" id="txtSAddress2"
											cssClass="longTextBox" /></td>
								</tr>

								<tr> 
									<td><label>City</label></td>
									<td><s:input path="strBCity" id="txtBCity"
											cssClass="BoxW116px" /></td>
									<td><label>City</label></td>
									<td><s:input path="strSCity" id="txtSCity"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>
									<td><label>State</label></td>
									<td><s:input path="strBState" id="txtBState"
											cssClass="BoxW116px" /></td>
									<td><label>State</label></td>
									<td><s:input path="strSState" id="txtSState"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>
									<td><label>Country</label></td>
									<td><s:input path="strBCountry" id="txtBCountry"
											cssClass="BoxW116px" /></td>
									<td><label>Country</label></td>
									<td><s:input path="strSCountry" id="txtSCountry"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>
									<td><label>Pin Code</label></td>
									<td><s:input path="strBPin" id="txtBPin"
											class="positive-integer BoxW116px" /></td>
									<td><label>Pin Code</label></td>
									<td><s:input path="strSPin" id="txtSPin"
											class="positive-integer BoxW116px" /></td>
								</tr>
							</table>
						</div>
						
						
						<div id="tab3" class="tab_content">
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
										<input type="text" id="txtTaxCode" ondblclick="funHelp('nonindicatortax');" class="searchTextBox"/>
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
				href="frmSalesOrder.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		
		<input type="hidden" id="hidStdValue" ></input>
		<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: hidden; height: auto; margin: auto; overflow-x: hidden; overflow-y: hidden; width: 100%;">
			<table id="tblProdChar" style="display: hidden; " class="transTablex col15-center">
				<tbody>
					<col style="width: 5%">
					<!--  COl1   -->
					<col style="width: 17%">
					<!--  COl2   -->
				</tbody>

			</table>
		</div>
		
			<div id="wait"
			style="display: block; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
							
		</div>

	</s:form>
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>