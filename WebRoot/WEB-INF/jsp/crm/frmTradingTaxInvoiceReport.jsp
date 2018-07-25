<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invoice</title>

<script type="text/javascript">
var unitprice="";

	
	

		
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
			
			
			 $("#txtDcDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtDcDate" ).datepicker('setDate', 'today');
				$("#txtDCDate").datepicker();
				
				 $("#txtAginst").datepicker({ dateFormat: 'yy-mm-dd' });
					$("#txtAginst" ).datepicker('setDate', 'today');
					$("#txtAginst").datepicker();
					
					 $("#txtWarrPeriod").datepicker({ dateFormat: 'yy-mm-dd' });
						$("#txtWarrPeriod" ).datepicker('setDate', 'today');
						$("#txtWarrPeriod").datepicker();
						
						 $("#txtWarraValidity").datepicker({ dateFormat: 'yy-mm-dd' });
							$("#txtWarraValidity" ).datepicker('setDate', 'today');
							$("#txtWarraValidity").datepicker();		
				
			
							
							
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
							<%if(null!=session.getAttribute("rptInvCode")){%>
							code='<%=session.getAttribute("rptInvCode").toString()%>';
							
							<%if(null!=session.getAttribute("rptInvDate")){%>
							invDate='<%=session.getAttribute("rptInvDate").toString()%>';
							
							<%session.removeAttribute("rptInvCode");%>
							
							<%session.removeAttribute("rptInvDate");%>
							var isOk=confirm("Do You Want to Generate Slip?");
							if(isOk){
						window.open(getContextPath()+"/openRptInvoiceSlip.html?rptInvCode="+code,'_blank');
						window.open(getContextPath()+"/openRptInvoiceProductSlip.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
							}
// 							var isOk=confirm("Do You Want to Generate Product Detail Slip?");
// 							if(isOk){
// 								window.open(getContextPath()+"/openRptInvoiceProductSlip.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
// 									}
							
							
							<%}%><%}%>
			
							
							
		});
		
		function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
			
		}

		 	function funSetData(code)
			{
				switch (fieldName)
				{
				
				 case 'locationmaster':
				    	funSetLocation(code);
				        break;
				        
				 case 'custMaster' :
				    	funSetCuster(code);
				    	break;  
				    	
				 case 'productmaster':
				    	funSetProduct(code);
				        break;
				        
				 case 'deliveryChallan':   
					 //funSetDeliveryChallanData(code);
					 $('#txtSOCode').val(code);
					 break;
					 
					  
				 case 'invoice':
					   funSetInvoiceData(code)
					  break;
				        
				 case 'nonindicatortax':
				       funSetTax(code);
				       break;
				}
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
				$("#txtProdCode").focus();
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
						       
						
						$("#txtSAddress1").val(response.strSAdd1);
						$("#txtSAddress2").val(response.strSAdd2);
						$("#txtSCity").val(response.strSCity);
						$("#txtSState").val(response.strSState);
						$("#txtSPin").val(response.strSPin);
						$("#txtSCountry").val(response.strSCountry);
						
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
							$("#txtProdCode").val(response[0][0]);
							$("#lblProdName").text(response[0][1]);
							$("#txtPrice").val(response[0][3]);
							//$("#cmbUOM").val(response[0][2]);
							$("#txtWeight").val(response[0][7]);
							$("#hidProdType").val(response[0][6]);
							$("#txtQty").focus();
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
	
	
	function funSetDeliveryChallanData(code)
	{
		gurl=getContextPath()+"/loadDeliveryChallanHdData.html?dcCode="+code;
		$.ajax({
	        type: "GET",
	        url: gurl,
	        dataType: "json",
	        success: function(response)
	        {		        	
	        		if('Invalid Code' == response.strDCCode){
	        			alert("Invalid Delivery Challan Code");
	        			$("#txtSOCode").val('');
	        			$("#txtSOCode").focus();
	        			
	        		}else{	
	        			funRemoveAllRows();
	        			$('#txtDCCode').val(response.strInvCode);
	        			$('#txtDCDate').val(response.dteInvDate);
	        			$('#txtAginst').val(response.strAgainst);
	        			$('#cmbAgainst').val(response.strAgainst);
	        			if(response.strAgainst=="Delivery Challan")
	        			{
	        			document.all["txtSOCode"].style.display = 'block';
	        			document.all["btnFill"].style.display = 'block';
	        			
	        			}else
	        				{
	        				document.all["txtSOCode"].style.display = 'none';
	        				document.all["btnFill"].style.display = 'none';
	        				}
	        			$('#txtPONo').val(response.strPONo);
	        			$('#txtLocCode').val(response.strLocCode);
	        			$('#lblLocName').text(response.strLocName);
	        			$('#txtVehNo').val(response.strVehNo);
	        			$('#txtWarrPeriod').val(response.strWarrPeriod);
	        			$('#txtWarraValidity').val(response.strWarraValidity);
	        			$('#txtNarration').val(response.strNarration);
	        			$('#txtPackNo').val(response.strPackNo);
	        			$('#txtDktNo').val(response.strDktNo);
	        			$('#txtMInBy').val(response.strMInBy);
	        			$('#txtTimeOut').val(response.strTimeInOut);
	        			$('#txtReaCode').val(response.strReaCode);
	        			
	        			
	        			$("#txtSOCode").val(response.strSOCode);
						$("#txtSODate").val(response.dteSODate);

						$("#txtCustCode").val(response.strCustCode);
						$("#lblCustomerName").text(response.strCustName);
					
						$("#txtSAddress1").val(response.strSAdd1);
						$("#txtSAddress2").val(response.strSAdd2);
						$("#txtSCity").val(response.strSCity);
						$("#txtSState").val(response.strSState);
						$("#txtSPin").val(response.strSPin);
						$("#txtSCountry").val(response.strSCountry);

						$.each(response.listclsDeliveryChallanModelDtl, function(i,item)
		       	       	    	 {
		       	        			
		       	       	    	    funfillDCDataRow(response.listclsDeliveryChallanModelDtl[i]);
		       	       	    	                                           
		       	       	    	 });
						
						

//							$('#').val(response.);
						
//							$('#').val(response.);
//							$('#').val(response.);
//							$('#').val(response.);
						
						
						
						
						
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
 	
	
	
	
	function funSetInvoiceData(code)
	{
		gurl=getContextPath()+"/loadInvoiceHdData.html?invCode="+code;
		$.ajax({
	        type: "GET",
	        url: gurl,
	        dataType: "json",
	        success: function(response)
	        {		        	
	        		if('Invalid Code' == response.strInvCode){
	        			alert("Invalid  Invoice Code");
	        			$("#txtSOCode").val('');
	        			$("#txtSOCode").focus();
	        			
	        		}else{	
	        			funRemoveAllRows();
	        			$('#txtDCCode').val(code);
	        			$('#txtDCDate').val(response.dteInvDate);
	        			$('#txtAginst').val(response.strAgainst);
	        			$('#cmbAgainst').val(response.strAgainst);
	        			if(response.strAgainst=="Delivery Challan")
	        			{
	        			document.all["txtSOCode"].style.display = 'block';
	        			document.all["btnFill"].style.display = 'block';
	        			
	        			}else
	        				{
	        				document.all["txtSOCode"].style.display = 'none';
	        				document.all["btnFill"].style.display = 'none';
	        				}
	        			$('#txtPONo').val(response.strPONo);
	        			$('#txtLocCode').val(response.strLocCode);
	        			$('#lblLocName').text(response.strLocName);
	        			$('#txtVehNo').val(response.strVehNo);
	        			$('#txtWarrPeriod').val(response.strWarrPeriod);
	        			$('#txtWarraValidity').val(response.strWarraValidity);
	        			$('#txtNarration').val(response.strNarration);
	        			$('#txtPackNo').val(response.strPackNo);
	        			$('#txtDktNo').val(response.strDktNo);
	        			$('#txtMInBy').val(response.strMInBy);
	        			$('#txtTimeOut').val(response.strTimeInOut);
	        			$('#txtReaCode').val(response.strReaCode);
	        			
	        			
	        			$("#txtSOCode").val(response.strSOCode);
						$("#txtSODate").val(response.dteSODate);

						$("#txtCustCode").val(response.strCustCode);
						$("#lblCustomerName").text(response.strCustName);
					
						$("#txtSAddress1").val(response.strSAdd1);
						$("#txtSAddress2").val(response.strSAdd2);
						$("#txtSCity").val(response.strSCity);
						$("#txtSState").val(response.strSState);
						$("#txtSPin").val(response.strSPin);
						$("#txtSCountry").val(response.strSCountry);
						$("#txtSubTotlAmt").val(response.dblSubTotalAmt);
					
						$("#txtFinalAmt").val(response.dblTotalAmt);
						$("#txtDiscount").val(response.dblDiscount);
						
                      
						$.each(response.listclsInvoiceModelDtl, function(i,item)
		       	       	    	 {
		       	       	    	    funfillDCDataRow(response.listclsInvoiceModelDtl[i]);
		       	       	    	                                           
		       	       	    	 });
						funRemoveTaxRows();
						$.each(response.listInvTaxDtl, function(i,item)
			            {
							funAddTaxRow1(respInvTaxDtl[i].strTaxCode,respInvTaxDtl[i].strTaxDesc
								,respInvTaxDtl[i].strTaxableAmt,respInvTaxDtl[i].strTaxAmt);
			            });
					//	funSetInvoiceTaxDtl(response.listInvTaxDtl);
					
						

//							$('#').val(response.);
						
//							$('#').val(response.);
//							$('#').val(response.);
//							$('#').val(response.);
						
						
						
						
						
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
		
		if($("#txtProdCode").val().length<=0)
			{
				$("#txtProdCode").focus();
				alert("Please Enter Product Code Or Search");
				return false;
			}		
		
		if($("#txtDiscount").val().length<=0)
		{
			$("#txtDiscount").focus();
			alert("Please Enter Discount");
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
	    	 var strProdCode=$("#txtProdCode").val();
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
	    var strProdCode = $("#txtProdCode").val();
		var strProdName=$("#lblProdName").text();
		var strProdType=$("#hidProdType").val();	
	    var dblQty = $("#txtQty").val();
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=$("#txtWeight").val();
	    var dblTotalWeight=dblQty*dblWeight;
	    
	  	var packingNo= $("#txtPackingNo").val();
	    var strSerialNo = $("#txtSerialNo").val();
	    var strInvoiceable = $("#cmbInvoiceable").val();
	    var strRemarks=$("#txtRemarks").val();
	   
	     funSeProductUnitPrice(strProdCode);
	   
	     var totalPrice=unitprice*dblQty;
	    

	    row.insertCell(0).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	 
	    row.insertCell(3).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(4).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(5).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    
	    row.insertCell(6).innerHTML= "<input name=\"\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"unitprice."+(rowCount)+"\"   value='"+unitprice+"'/>";
	    row.insertCell(7).innerHTML= "<input name=\"\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" class=\"Box1 totalValueCell\" \size=\"3.9%\" id=\"totalPrice."+(rowCount)+"\"   value='"+totalPrice+"'/>";
	    
	    row.insertCell(8).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"  required = \"required\"  class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"  required = \"required\"  class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
	    
	 
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    
	    $("#txtProdCode").focus();
	    funCalculateTotalAmt();
	    funClearProduct();
	   // funGetTotal();
	    return false;
	}
	
	function funSeProductUnitPrice(code)
	{
		var strCustCode = $("#txtCustCode").val();
		var discount= $("#txtDiscount").val();
		var searchUrl="";
		searchUrl=getContextPath()+"/loadInvoiceProductDetail.html?prodCode="+code+"&strCustCode="+strCustCode+"&discount="+discount;
		$.ajax
		({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    success: function(response)
		    {
		
		    	unitprice=response.dblBillRate;
		    	
		    	
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
	
	
	
	
	function funUpdatePrice(object)
	{
		var index=object.parentNode.parentNode.rowIndex;
		var Qty=document.getElementById("txtQty."+index).value;
		var price=document.getElementById("txtPrice."+index).value;
		var discount=document.getElementById("txtDiscount."+index).value;
		var ItemPrice;
		ItemPrice=(parseFloat(Qty)*parseFloat(price))-parseFloat(discount);
		
		document.getElementById("txtAmount."+index).value=parseFloat(ItemPrice);
		funGetTotal();
	}
	
// 	function funGetTotal()
// 	{
		
// 		var subtotal=0.00;
// 		var extraChange=0.00;
// 		var finalAmt=0.00;
		
// 		$('#tblProdDet tr').each(function() {
// 		    var totalPriceCell = $(this).find(".totalValueCell").val();
// 		    subtotal=parseFloat(subtotal)+parseFloat(totalPriceCell);
// 		 });
		
// 		$("#txtSubTotal").val(subtotal);
		
// 		var disc=$("#txtDisc").val();
// 		subtotal=parseFloat(subtotal)-parseFloat(disc);
// 		extraChange=$("#txtExtraCharges").val();
// 		subtotal=parseFloat(subtotal)+parseFloat(extraChange);
// 		$("#txtFinalAmt").val(subtotal);
		  
		
// 	}
	
	
	
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblProdDet");
	    table.deleteRow(index);
	}
	
	function funfillDCDataRow(DCDtl)
	{
		var table = document.getElementById("tblProdDet");
		
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var strProdCode = DCDtl.strProdCode;
		var strProdName=DCDtl.strProdName;
		var strProdType=DCDtl.strProdType;	
	    var dblQty = DCDtl.dblQty;
	    parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	    var dblWeight=DCDtl.dblWeight;
	    var dblTotalWeight=dblQty*dblWeight;
	    
	  	var packingNo= DCDtl.strPktNo;
	    var strSerialNo = DCDtl.strSerialNo;
	    var strInvoiceable = DCDtl.strInvoiceable;
	    var strRemarks=DCDtl.strRemarks;
	    
	    funSeProductUnitPrice(strProdCode);
	    var totalPrice=unitprice*dblQty;
		
	    row.insertCell(0).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	
	    row.insertCell(3).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\"  class=\"decimal-places inputText-Auto\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(4).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	
	    row.insertCell(5).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(6).innerHTML= "<input name=\"\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" \size=\"3.9%\" id=\"unitprice."+(rowCount)+"\"   value='"+unitprice+"'/>";
	    row.insertCell(7).innerHTML= "<input name=\"\" readonly=\"readonly\" class=\"Box1 totalValueCell\" style=\"text-align: right;\" \size=\"3.9%\" id=\"totalPrice."+(rowCount)+"\"   value='"+totalPrice+"'/>";
	    
	    
	    row.insertCell(8).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"  required = \"required\"  class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"  required = \"required\"  class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
	    
	 
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	    
	    
	 	 $("#txtProdCode").focus();
		    funClearProduct();
		    funCalculateTotalAmt();
// 		    funGetTotal();
		    return false;
	    
	    return false;
	
	}
	
	

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
	
	
	function funClearProduct()
	{
		$("#txtProdCode").val("");
		$("#strUOM").val("");
		$("#lblProdName").text("");
		$("#txtQty").val("");
		$("#txtPrice").val("");
		
		$("#txtRemarks").val("");
		$("#txtWeight").val("");
// 		$("#txtDiscount").val(0);
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
		if(agianst=="Delivery Challan")
			{
			document.all["txtSOCode"].style.display = 'block';
			document.all["btnFill"].style.display = 'block';
			
			}else
				{
				document.all["txtSOCode"].style.display = 'none';
				document.all["btnFill"].style.display = 'none';
				}
	}
	
	function btnFill_onclick()
	{
		var code =$('#txtSOCode').val();
		 var  cmbAgainst=$('#cmbAgainst').val();
		if(code.toString.lenght!=0 || code==null)
			{
		
				gurl=getContextPath()+"/loadDeliveryChallanHdData.html?dcCode="+code;
				$.ajax({
			        type: "GET",
			        url: gurl,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strSOCode){
			        			alert("Invalid Customer Code");
			        			$("#txtSOCode").val('');
			        			$("#txtSOCode").focus();
			        			
			        		}else{	
			        			funRemoveAllRows();
			        		
			        		/* 	$('#txtSOCode').val(code); */
// 			        			$('#txtAginst').val(response.strAgainst);
// 			        			$('#cmbAgainst').val(cmbAgainst);
// 			        			if(response.strAgainst=="Delivery Challan")
			        			
// 			        			document.all["txtSOCode"].style.display = 'block';
// 			        			document.all["btnFill"].style.display = 'block';
			        			
			        			$('#txtPONo').val(response.strPONo);
			        			$('#txtPONo').val(response.strPONo);
			        			$('#txtLocCode').val(response.strLocCode);
			        			$('#lblLocName').text(response.strLocName);
			        			$('#txtVehNo').val(response.strVehNo);
			        			$('#txtWarrPeriod').val(response.strWarrPeriod);
			        			$('#txtWarraValidity').val(response.strWarraValidity);
			        			$('#txtNarration').val(response.strNarration);
			        			$('#txtPackNo').val(response.strPackNo);
			        			$('#txtDktNo').val(response.strDktNo);
			        			$('#txtMInBy').val(response.strMInBy);
			        			$('#txtTimeOut').val(response.strTimeInOut);
			        			$('#txtReaCode').val(response.strReaCode);
			        			
			        			
// 			        			$("#txtSOCode").val(response.strSOCode);
								$("#txtSODate").val(response.dteSODate);

								$("#txtCustCode").val(response.strCustCode);
								$("#lblCustomerName").text(response.strCustName);
							
								$("#txtSAddress1").val(response.strSAdd1);
								$("#txtSAddress2").val(response.strSAdd2);
								$("#txtSCity").val(response.strSCity);
								$("#txtSState").val(response.strSState);
								$("#txtSPin").val(response.strSPin);
								$("#txtSCountry").val(response.strSCountry);
								$("#txtSubTotlAmt").val(response.dblSubTotalAmt);
							
								$("#txtFinalAmt").val(response.dblTotalAmt);
		                      

								
			        			$.each(response.listclsDeliveryChallanModelDtl, function(i,item)
				       	       	    	 {
				       	        			
				       	       	    	    funfillDCDataRow(response.listclsDeliveryChallanModelDtl[i]);
				       	       	    	                                           
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
		
	}
	
	


	$(function()
			{
				$("#txtDCCode").blur(function() 
						{
							var code=$('#txtDCCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								funSetInvoiceData(code);
							}
						});
				
				$('#txtCustCode').blur(function () {
					var code=$('#txtCustCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){
						   funSetCuster(code);
					   }
					});
				
				$('#txtLocCode').blur(function () {
					var code=$('#txtLocCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){
						funSetLocation(code);
					   }
					});
				
				$('#txtProdCode').blur(function () {
					var code=$('#txtProdCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/"){								  
						funSetProduct(code);
					   }
					}); 
				
				
			});
	
	

	/**
	 * Calculate Total Amount
	 */
	function funCalculateTotalAmt()
	{
		var totalAmt=0;
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;
		for(var i=0;i<rowCount;i++)
		{
			totalAmt=parseFloat(document.getElementById("totalPrice."+i).value)+totalAmt;
		}
		
		$("#txtSubTotlAmt").val(totalAmt);
		$("#txtFinalAmt").val(totalAmt);
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
	    
		$('#tblProdDet tr').each(function(){
			
	    	var prodCode= $(this).find(".txtProdCode").val();
	    	var suppCode=$("#txtCustCode").val();
         	var discPer=0;
// 	    	if($("#txtDiscPer").val()!='')
// 	    	{	
// 	    		discPer=parseFloat($("#txtDiscPer").val());
// 	    	}
 	    	var taxableAmount= parseFloat($(this).find(".totalValueCell").val());
	    	var discAmt=(taxableAmount*discPer)/100;
	    	taxableAmount=taxableAmount-discAmt;
	    	prodCodeForTax=prodCodeForTax+"!"+prodCode+","+taxableAmount+","+suppCode;
	    });
		
	    prodCodeForTax=prodCodeForTax.substring(1,prodCodeForTax.length).trim();
	     forTax(prodCodeForTax);
	}
	
	
	function forTax(prodCodeForTax){
		var dteInv = $("#txtDcDate" ).val();
		var CIFAmt=0;
	    gurl=getContextPath()+"/getTaxDtlForProduct.html?prodCode="+prodCodeForTax+"&transDate="+dteInv+"&CIFAmt="+CIFAmt,
	    
	    		$.ajax({
	    		
		   		type: "GET",
		//        url: getContextPath()+"/getTaxDtlForProduct.html?prodCode="+prodCodeForTax,
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
		        			//var taxableAmt=parseFloat(spItem[0])+dblExtraCharge;
		        			var taxableAmt=parseFloat(spItem[0]);
		        			 
			        		var taxCode=spItem[1];
				        	var taxDesc=spItem[2];
				        	var taxPer1=parseFloat(spItem[4]);
// 				        	var taxAmt=(taxableAmt*taxPer1)/100;
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
	 * Calculating Tax subtotal amount
	 */
	function funCalculateTaxForSubTotal(taxableAmt,taxPercent)
	{
		var taxAmt=(taxableAmt*taxPercent/100);
		taxAmt=taxAmt.toFixed(2);
		$("#txtTaxAmt").val(taxAmt);
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
	
	/**
	 * Filling Tax in Grid
	 */
	function funAddTaxRow1(taxCode,taxDesc,taxableAmt,taxAmt) 
	{	
	    var table = document.getElementById("tblTax");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount)+"\" value='"+taxCode+"' >";
	    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+taxDesc+"'>";		    	    
	    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxableAmt\" id=\"txtTaxableAmt."+(rowCount)+"\" value="+taxableAmt+">";
	    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxAmt\" id=\"txtTaxAmt."+(rowCount)+"\" value="+taxAmt+">";		    
	    row.insertCell(4).innerHTML= '<input type="button" size=\"6%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
	    
	    funCalTaxTotal();
	    funClearFieldsOnTaxTab();
	    
	    return false;
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
	 * Calculating Total Tax 
	 */
	function funCalTaxTotal()
	{
		var totalTaxAmt=0,totalTaxableAmt=0;
		var table = document.getElementById("tblTax");
		var rowCount = table.rows.length;
		var subTotal=parseFloat($("#txtSubTotlAmt").val());
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
		$("#lblPOGrandTotal").text(grandTotal);
		var taxAmt=$("#txtPOTaxAmt").val();
		
		
	
           
		var finalAmt=parseFloat(subTotal)+parseFloat(totalTaxAmt);
		$("#txtFinalAmt").val(finalAmt);
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
		function funAddTaxRow() 
		{
			var taxCode = $("#txtTaxCode").val();
			var taxDesc=$("#lblTaxDesc").text();
		    var taxableAmt = $("#txtTaxableAmt").val();
		    var taxAmt=$("#txtTaxAmt").val();
	
		    var table = document.getElementById("tblTax");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount)+"\" value='"+taxCode+"' >";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+taxDesc+"'>";		    	    
		    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxableAmt\" id=\"txtTaxableAmt."+(rowCount)+"\" value="+taxableAmt+">";
		    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxAmt\" id=\"txtTaxAmt."+(rowCount)+"\" value="+taxAmt+">";		    
		    row.insertCell(4).innerHTML= '<input type="button" size=\"6%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
		    
		    funCalTaxTotal();
		    funClearFieldsOnTaxTab();
		    
		    return false;
		}
	
		
		/**
		 * Set Invoice tax Data
		 */
		function funSetInvoiceTaxDtl(respInvTaxDtl)
		{
			funRemoveTaxRows();
			$.each(respInvTaxDtl, function(i,item)
            {
				
				funAddTaxRow1(respInvTaxDtl[i].strTaxCode,respInvTaxDtl[i].strTaxDesc
					,respInvTaxDtl[i].strTaxableAmt,respInvTaxDtl[i].strTaxAmt);
            });
		}
</script>

</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Invoice</label>
	</div>
	<s:form name="SOForm" method="POST"
		action="saveInvoice.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table
			style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>

					<div id="tab_container" style="height: 780px">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 100px; padding-left: 55px">Invoice</li>
							<li data-state="tab2" style="width: 100px; padding-left: 55px">Address</li>
							<li data-state="tab3" style="width: 100px; padding-left: 55px">Taxes</li>
						</ul>

						<div id="tab1" class="tab_content">
							<table class="transTable">
								<tr>
									<th align="right" colspan="9"><a id="baseUrl" href="#">
											Attach Documents </a></th>
								</tr>

								<tr>
									<td width="100px"><label>Invoice Code</label></td>
									<td  colspan="3"><s:input path="strInvCode" id="txtDCCode"
											ondblclick="funHelp('invoice')"
											cssClass="searchTextBox" /></td>

									<td width="100px"><label>Invoice Date</label>
									<td><s:input path="dteInvDate" id="txtDCDate"
											required="required" cssClass="calenderTextBox" /></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><label>Against</label></td>
									<td><s:select id="cmbAgainst" path="strAgainst"
											items="${againstList}" cssClass="BoxW124px" onchange="funShowSOFieled()"/></td>
									<td><s:input id="txtSOCode" path="strSOCode"
											ondblclick="funHelp('deliveryChallan')" style="display:none" class="searchTextBox"></s:input></td>
									<td  colspan="1"><input type="Button" id="btnFill" value="Fill"
										onclick="return btnFill_onclick()" style="display:none"  class="smallButton" /></td>

									<td width="100px"><label>Date</label>
									<td><s:input path="" id="txtAginst"
											cssClass="calenderTextBox" /></td>
								</tr>

								<tr>
									<td><label>Customer Code</label></td>
									<td  colspan="1"><s:input path="strCustCode" id="txtCustCode"
											ondblclick="funHelp('custMaster')" cssClass="searchTextBox" /></td>
									<td colspan="2"><label id="lblCustomerName"
										class="namelabel"></label></td>

									<td><label>PO NO</label></td>
									<td><s:input id="txtPONo" type="text" path="strPONo"
											class="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>Location Code</label></td>
									<td><s:input type="text" id="txtLocCode" path="strLocCode"
											cssClass="searchTextBox"
											ondblclick="funHelp('locationmaster');" /></td>
									<td colspan="2"><label id="lblLocName"></label></td>

									<td><label>Vechile No</label></td>
									<td ><s:input id="txtVehNo" type="text" path="strVehNo"
											class="BoxW116px" /></td>
								</tr>
								<tr>
									<td width="100px"><label>Warrenty Start Date</label>
									<td colspan="3"><s:input path="strWarrPeriod" id="txtWarrPeriod"
											cssClass="calenderTextBox" /></td>

									<td width="100px"><label>Warranty Validity</label>
									<td><s:input path="strWarraValidity" id="txtWarraValidity"
											cssClass="calenderTextBox" /></td>

								</tr>

								<tr>
									<td width="100px"><label>Product</label></td>
									<td><input id="txtProdCode"
										ondblclick="funHelp('productmaster')" class="searchTextBox" /></td>
									<td align="left" colspan="2"><label id="lblProdName" class="namelabel"></label></td>

									<td><label>Serial No</label></td>
									<td><s:input id="txtSerialNo" path="strSerialNo" type="text" class="BoxW116px" /></td>



								</tr>
								<tr>

									<td><label>Wt/Unit</label></td>
									<td><input type="text" id="txtWeight"
										class="decimal-places numberField" /></td>
									<td style="width: 115px"><label>Quantity</label></td>
									<td><input id="txtQty" type="text"
										class="decimal-places numberField" style="width: 60%" /></td>

									<td><label>Invoiceable</label></td>
									<td><s:select id="cmbInvoiceable" name="cmbInvoiceable"
											path="" cssClass="BoxW124px">
											<option value="N">No</option>
											<option value="Y">Yes</option>
										</s:select></td>

								</tr>

								<tr>
								<td><label>Packing No</label></td>
									<td><input id="txtPackingNo" type="text" class="BoxW116px" /></td>

									<td><label>Remarks</label></td>
									<td><input id="txtRemarks" class="longTextBox"
										style="width: 100%" /></td>
											<td><label>Discount</label></td>
								    <td><s:input type="text" id="txtDiscount"
											path="dblDiscount" 
											cssClass="decimal-places-amt numberField" /></td>
									<td><input type="button" value="Add" class="smallButton"
										onclick="return btnAdd_onclick()" /></td>
								</tr>
							</table>

		<div class="dynamicTableContainer" style="height: 300px; width: 100%;">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
									<tr bgcolor="#72BEFC">
										<td width="5%">Product Code</td>
										<!--  COl1   -->
										<td width="16%">Product Name</td>
										<!--  COl2   -->
										<td width="6%">Product Type</td>
										<!--  COl3   -->
										<td width="3%">Qty</td>
										<!--  COl4   -->
										<td width="3%">Wt/Unit</td>
										<!--  COl5   -->
										<td width="3%">Total Wt</td>
										<!--  COl6   -->
										<td width="3%">Unit Price</td>
										<!--  COl7   -->
										<td width="6%">Total Amt</td>
										<!--  COl8   -->
										<td width="5%">Packing No</td>
										<!--  COl9   -->
										<td width="6%">Remarks</td>
										<!--  COl10   -->
										<td width="3%">Invoice</td>
										<!--  COl11   -->
										<td width="4%">Serial No</td>
										<!--  COl12   -->
										<td width="3%">Delete</td>
										<!--  COl13   -->
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
										<col style="width: 16%">
										<!--  COl2   -->
										<col style="width: 5.7%">
										<!--  COl3   -->
										<col style="width: 3%">
										<!--  COl4   -->
										<col style="width: 3%">
										<!--  COl5   -->
										<col style="width: 3%">
										<!--  COl6   -->
										<col style="width: 3%">
										<!--  COl7   -->
										<col style="width: 6%">
										<!--  COl8   -->
										<col style="width: 5%">
										<!--  COl9   -->
										<col style="width: 6%">
										<!--  COl10  -->
										<col style="width: 4%">
										<!--  COl11  -->
										<col style="width: 3%">
										<!--  COl12   -->
										<col style="width: 2.5%">
										<!--  COl13   -->

										</tbody>

									</table>
								</div>

							</div>



							<table class="transTable">
								<tr>
									<td><label>Narration</label></td>
									<td><s:textarea id="txtNarration" path="strNarration"
											Cols="50" rows="3" style="width:80%" /></td>
									<td><label>Pack No</label></td>
									<td><s:input id="txtPackNo" path="strPackNo" type="text"
											class="BoxW116px" /></td>
											
											
								</tr>

								<tr>
									<td><label>Docket No of Courier</label></td>
									<td><s:input id="txtDktNo" path="strDktNo" type="text"
											class="BoxW116px" /></td>

									<td><label>Material Sent Out By</label></td>
									<td><s:input id="txtMInBy" path="strMInBy" type="text"
											class="BoxW116px" /></td>
								</tr>
								<tr>
									<td><label>Time Out</label></td>
									<td><s:input id="txtTimeOut" path="strTimeInOut" type="text"
											class="BoxW116px" /></td>
											
										
								
									<td><label>Reason Code</label></td>
									<td><s:input id="txtReaCode" path="strReaCode" type="text"
											class="BoxW116px" /></td>
							</tr><tr><td colspan="2"></td>	<td><label id="lblsubTotlAmt">SubTotal Amount</label></td>
									<td><s:input type="text" id="txtSubTotlAmt"
											path="dblSubTotalAmt" readonly="true"
											cssClass="decimal-places-amt numberField" /></td></tr>
								<tr ><td colspan="2"></td><td ><label id="lblFinalAmt">Final Amount</label></td>
									<td><s:input type="text" id="txtFinalAmt"
											path="dblTotalAmt" readonly="true"
											cssClass="decimal-places-amt numberField" /></td></tr>
											
											

							</table>

						</div>
				<div id="tab2" class="tab_content">
							<table class="transTable">
								<tr>
									<th colspan="2" align="left"><label>Ship To</label></th>
								</tr>

								<tr>
									<td width="120px"><label>Address Line 1</label></td>
									<td><s:input path="strSAdd1" id="txtSAddress1"
											cssClass="longTextBox" /></td>
								</tr>

								<tr>

									<td><label>Address Line 2</label></td>
									<td><s:input path="strSAdd2" id="txtSAddress2"
											cssClass="longTextBox" /></td>
								</tr>

								<tr>

									<td><label>City</label></td>
									<td><s:input path="strSCity" id="txtSCity"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>State</label></td>
									<td><s:input path="strSState" id="txtSState"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>Country</label></td>
									<td><s:input path="strSCtry" id="txtSCountry"
											cssClass="BoxW116px" /></td>
								</tr>

								<tr>

									<td><label>Pin Code</label></td>
									<td><s:input path="strSPin" id="txtSPin"
											class="positive-integer BoxW116px" /></td>
								</tr>
							</table>
						</div>
						
						<div id="tab3" class="tab_content">
							<br>
							<br>
							<table class="transTable">
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

		<div align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="button"
				id="reset" name="reset" value="Reset" class="form_button" />
		</div>
		<br><br>
		<s:input type="hidden" id="hidProdType" path="strProdType"></s:input>
		
		
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>
	<script type="text/javascript">
		//funApplyNumberValidation();
	</script>
	
	
</body>
</html>
