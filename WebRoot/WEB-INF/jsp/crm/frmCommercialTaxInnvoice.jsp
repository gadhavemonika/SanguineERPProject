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


var QtyTol=0.00;	
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
			
			var code='<%=session.getAttribute("locationCode").toString()%>';
			funSetLocation(code);
			var dayEndDate='<%=session.getAttribute("dayEndDate").toString()%>';
		//	 $("#txtDCDate").datepicker({ dateFormat: 'yy-mm-dd' });
			 $("#txtDCDate" ).val(dayEndDate);
		//		$("#txtDCDate" ).datepicker('setDate', dayEndDate);
		//		$("#txtDCDate").datepicker();
				
				
							var code='';
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
							<%session.removeAttribute("rptInvCode");%>
							var isOk=confirm("Do You Want to Generate Slip?");
							if(isOk){
								window.open(getContextPath()+"/rptComercialTaxInvSlip.html?rptInvCode="+code,'_blank');
							}
							<%}%>
							
		});
		
		
	function funGetKeyCode(event,controller) {
	    var key = event.keyCode;
	    
	    if(controller=='BillNo' && key==13)
	    {
	    	document.getElementById('txtChallanDate').focus();
	    }
	    
	    if(controller=='BillDate' && key==13)
	    {
	    	document.getElementById('txtPayMode').focus();
	    }
	}
		
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
				        
				 case 'nonindicatortax':
				    	funSetTax(code);
				    	break;
					  
				 case 'invoice':
					   funSetInvoiceData(code)
					  break;
				        
				    
				       
				}
			}
		 	
		 	//get and set Tax 
			function funSetTax(code)
			{
				$.ajax({
				   		type: "GET",
				        url: getContextPath()+"/loadTaxMasterData.html?taxCode="+code,
				        dataType: "json",
				        success: function(response)
				        {
				        	$("#txtTaxCode").val(code);
				        	$("#lblTaxDesc").text(response.strTaxDesc);
				        	$("#txtTaxableAmt").val($("#txtFinalAmt").val());
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
			
			var locCode = $("#txtLocCode").val();
			var custCode=$("#txtCustCode").val();
			searchUrl=getContextPath()+"/loadProductDataForInvoice.html?prodCode="+code+"&suppCode="+custCode+"&locCode="+locCode;
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
							$("#hidUnitPrice").val(response[0][3]);
							$("#txtUnitPrice").val(response[0][3]);
							//$("#cmbUOM").val(response[0][2]);
							$("#txtWeight").val(response[0][7]);
							$("#hidProdType").val(response[0][6]);
							$("#lblUOM").text(response[0][2]);
							
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
	
	
	
	
	
	function funSetInvoiceData(code)
	{
		gurl=getContextPath()+"/loadComericalInvoiceHdData.html?invCode="+code;
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
						
						QtyTol=0.00;
						$.each(response.listclsInvoiceModelDtl, function(i,item)
		       	       	    	 {
		       	       	    	    funfillDCDataRow(response.listclsInvoiceModelDtl[i]);
		       	       	    		$("#txtQtyTotl").val(QtyTol);
		       	       	    	 });
						
						
						funRemoveTaxRows();
						$.each(response.listInvoiceTaxDtl, function(i,item)
			            {
							
							funFillTaxRow(response.listInvoiceTaxDtl[i]);
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
 	
	
	function funFillTaxRow(taxDtl) 
	{	
		
	    var table = document.getElementById("tblTax");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    var taxCode=taxDtl.strTaxCode;
	    var taxDesc=taxDtl.strTaxDesc;
	    var taxableAmt=taxDtl.strTaxableAmt;
	    var taxAmt=taxDtl.strTaxAmt;
	    
	    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxCode\" id=\"txtTaxCode."+(rowCount)+"\" value='"+taxCode+"' >";
	    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"22%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxDesc\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+taxDesc+"'>";		    	    
	    row.insertCell(2).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxableAmt\" id=\"txtTaxableAmt."+(rowCount)+"\" value="+taxableAmt+">";
	    row.insertCell(3).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" size=\"15.5%\" name=\"listInvoiceTaxDtl["+(rowCount)+"].strTaxAmt\" id=\"txtTaxAmt."+(rowCount)+"\" value="+taxAmt+">";		    
	    row.insertCell(4).innerHTML= '<input type="button" size=\"6%\" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTaxRow(this)" >';
	    
	    funCalTaxTotal();
	    funClearFieldsOnTaxTab();
	    
	    return false;
	}
	
	
	function btnAdd_onclick()
	{
		
		if($("#txtProdCode").val().length<=0)
			{
				$("#txtProdCode").focus();
				alert("Please Enter Product Code Or Search");
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
	    	// funSeProductUnitPrice(strProdCode);
	    	// var unitprice= $("#hidbillRate").val();
	    	 
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
	    var strInvoiceable ="N";
	    var strRemarks=$("#txtRemarks").val();
	   
	    $("#txtUnitPrice").val();
        var unitprice=$("#txtUnitPrice").val();
       unitprice=parseFloat(unitprice).toFixed(maxQuantityDecimalPlaceLimit);
	   var totalPrice=unitprice*dblQty;
	    var strUOM=$("#lblUOM").text();
       var strCustCode=$("#txtCustCode").val();
       var strSOCode="";
	   row.insertCell(0).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"40%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"0%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	    row.insertCell(3).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\"  class=\"decimal-places inputText-Auto  txtQty\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(4).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(5).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(6).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblUnitPrice\" readonly=\"readonly\" class=\"Box txtUnitprice\" style=\"text-align: right;\" \size=\"3.9%\" id=\"unitprice."+(rowCount)+"\"   value='"+unitprice+"'/>";
	    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" \size=\"3.9%\" id=\"totalPrice."+(rowCount)+"\"   value='"+totalPrice+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"    class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"    class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
	 	row.insertCell(13).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strCustCode\" type=\"text\"    class=\"Box\" size=\"7%\" id=\"txtCustCode."+(rowCount)+"\" value="+strCustCode+" >";
	 	row.insertCell(14).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSOCode\" type=\"text\"    class=\"Box\" size=\"13%\" id=\"txtSOCOde."+(rowCount)+"\" value="+strSOCode+" >";
	 	row.insertCell(15).innerHTML= "<input type=\"hidden\"  name=\"listclsInvoiceModelDtl["+(rowCount)+"].strUOM\" type=\"text\"    class=\"Box\" size=\"0%\" id=\"txtUOM."+(rowCount)+"\" value="+strUOM+" >";
	 	
	 	QtyTol+=parseFloat(dblQty);
	 	$("#txtQtyTotl").val(QtyTol);
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
		     $("#hidbillRate").val(response);
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
	    
	    var strUOM=DCDtl.strUOM;
	    var unitprice=DCDtl.dblUnitPrice;
	    var totalPrice=unitprice*dblQty;
	    var CustCode=DCDtl.strCustCode;
	    var SOCode=DCDtl.strSOCode;
	    row.insertCell(0).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"40%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"0%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
	    row.insertCell(3).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\"  class=\"decimal-places inputText-Auto  txtQty\" id=\"txtQty."+(rowCount)+"\" value="+dblQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
	    row.insertCell(4).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
	    row.insertCell(5).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
	    row.insertCell(6).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblUnitPrice\" readonly=\"readonly\" class=\"Box txtUnitprice\" style=\"text-align: right;\" \size=\"3.9%\" id=\"unitprice."+(rowCount)+"\"   value='"+unitprice+"'/>";
	    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" \size=\"3.9%\" id=\"totalPrice."+(rowCount)+"\"   value='"+totalPrice+"'/>";
	    row.insertCell(8).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"    class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
	    row.insertCell(9).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
	    row.insertCell(10).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
	    row.insertCell(11).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"    class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
	 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="">';		    
	 	row.insertCell(13).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strCustCode\" type=\"text\"    class=\"Box\" size=\"7%\" id=\"txtCustCode."+(rowCount)+"\" value="+CustCode+" >";
	 	row.insertCell(14).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSOCode\" type=\"text\"    class=\"Box\" size=\"13%\" id=\"txtSOCOde."+(rowCount)+"\" value="+SOCode+" >";
	 	row.insertCell(15).innerHTML= "<input type=\"hidden\"  name=\"listclsInvoiceModelDtl["+(rowCount)+"].strUOM\" type=\"text\"    class=\"Box\" size=\"0%\" id=\"txtUOM."+(rowCount)+"\" value="+strUOM+" >"; 	
	 	$("#txtProdCode").focus();
		    funClearProduct();
		    funCalculateTotalAmt();
// 		    funGetTotal();
		
		 QtyTol+=dblQty;
		
		    return false;
	    
	    return false;
	
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
		$("#txtUnitPrice").val("");
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
		if(agianst=="Direct")
			{
			document.all["txtSOCode"].style.display = 'none';
			document.all["btnFill"].style.display = 'none'; 		
			}if(agianst=="Sales Order")
			{
				document.all["txtSOCode"].style.display = 'block';
				document.all["btnFill"].style.display = 'none'; 		
				}
				else{
					document.all["txtSOCode"].style.display = 'block';
					document.all["btnFill"].style.display = 'block';
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
		    
		    var cnt=0;
			$('#tblProdDet tr').each(function(){
				
		    	var prodCode= $(this).find(".txtProdCode").val();
		    	var custCode=$("#txtCustCode").val();
	         	var discPer=0;
//	 	    	if($("#txtDiscPer").val()!='')
//	 	    	{	
//	 	    		discPer=parseFloat($("#txtDiscPer").val());
//	 	    	}
	 	    	var vari=document.getElementById("totalPrice." + cnt).value;
	 	    	var qty=parseFloat(document.getElementById("txtQty."+cnt).value);	
	 	    	var unitPrice=parseFloat(document.getElementById("unitprice."+cnt).value);
		    	var discAmt1=0;
				var taxableAmount=parseFloat(vari);
				
		    	var discAmt=(taxableAmount*discPer)/100;
		    	taxableAmount=taxableAmount-discAmt;
		    	prodCodeForTax=prodCodeForTax+"!"+prodCode+","+unitPrice+","+custCode+","+qty+","+discAmt1;
		    	
		    	cnt++;
		    });
			
		    prodCodeForTax=prodCodeForTax.substring(1,prodCodeForTax.length).trim();
		     forTax(prodCodeForTax);
		}
		
		
		function forTax(prodCodeForTax){
			var dteInv =$('#txtDCDate').val();
			var CIFAmt=0;
		    gurl=getContextPath()+"/getTaxDtlForProduct.html?prodCode="+prodCodeForTax+"&taxType=Sales&transDate="+dteInv+"&CIFAmt="+CIFAmt,
		    
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
//	 				        	var taxAmt=(taxableAmt*taxPer1)/100;
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
	//	var disAmt = $('#txtDisc').val();
	//	var extCharge = $('#txtExtraCharges').val();
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
	
		
		function funSetVehCode(code){

			$.ajax({
				type : "GET",
				url : getContextPath()+ "/LoadVehicleMaster.html?docCode=" + code,
				dataType : "json",
				success : function(response){ 
					
					if('Invalid Code' == response.strVehCode){
	        			alert("Invalid Vehicle Code");
	        			$("#txtVehCode").val('');
	        			$("#txtVehCode").focus();
	        		}else{
	        			$("#txtVehNo").val(response.strVehNo);
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
		 * Filling Grid
		 */
		function funfillProdRow(strProdCode, strProdName, dblUnitPrice, dblAcceptQty,
				dblWeight, strSpCode,strCustCode,strSOCode) {
			var table = document.getElementById("tblProdDet");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			dblUnitPrice=parseFloat(dblUnitPrice).toFixed(maxAmountDecimalPlaceLimit);
			dblWeight=parseFloat(dblWeight).toFixed(maxQuantityDecimalPlaceLimit);
			
			var dblTotalPrice = parseFloat(dblAcceptQty).toFixed(maxQuantityDecimalPlaceLimit) * dblUnitPrice;
			dblTotalPrice=parseFloat(dblTotalPrice).toFixed(maxAmountDecimalPlaceLimit);
				var strProdType="";	
			 
				dblAcceptQty=parseFloat(dblAcceptQty).toFixed(maxQuantityDecimalPlaceLimit);
			    var dblTotalWeight=dblAcceptQty*dblWeight;
			  	var packingNo= $("#txtPackingNo").val();
			    var strSerialNo = $("#txtSerialNo").val();
			    var strInvoiceable = $("#cmbInvoiceable").val();
			    var strRemarks=$("#txtRemarks").val();

			    
			    row.insertCell(0).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdCode\" readonly=\"readonly\" class=\"Box txtProdCode\" size=\"8%\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
			    row.insertCell(1).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdName\" readonly=\"readonly\" class=\"Box\" size=\"40%\" id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
			    row.insertCell(2).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strProdType\" readonly=\"readonly\" class=\"Box\" size=\"0%\" id=\"txtProdTpye."+(rowCount)+"\" value='"+strProdType+"'/>";
			    row.insertCell(3).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblQty\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\"  class=\"decimal-places inputText-Auto  txtQty\" id=\"txtQty."+(rowCount)+"\" value="+dblAcceptQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
			    row.insertCell(4).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblWeight\" type=\"text\"  required = \"required\" style=\"text-align: right;\" size=\"3.9%\" class=\"decimal-places inputText-Auto\" id=\"txtWeight."+(rowCount)+"\" value="+dblWeight+" >";
			    row.insertCell(5).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblTotalWeight\" readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"3.9%\" id=\"dblTotalWeight."+(rowCount)+"\"   value='"+dblTotalWeight+"'/>";
			    row.insertCell(6).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].dblUnitPrice\" readonly=\"readonly\" class=\"Box txtUnitprice\" style=\"text-align: right;\" \size=\"3.9%\" id=\"unitprice."+(rowCount)+"\"   value='"+dblUnitPrice+"'/>";
			    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" \size=\"3.9%\" id=\"totalPrice."+(rowCount)+"\"   value='"+dblTotalPrice+"'/>";
			    row.insertCell(8).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strPktNo\" type=\"text\"    class=\"Box\" \size=\"5%\" id=\"txtPktNo."+(rowCount)+"\" value="+packingNo+" >";
			    row.insertCell(9).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strRemarks\" size=\"10%\" id=\"txtRemarks."+(rowCount)+"\" value='"+strRemarks+"'/>";
			    row.insertCell(10).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strInvoiceable\" readonly=\"readonly\" class=\"Box\"  size=\"6%\" id=\"txtInvoiceable."+(rowCount)+"\" value="+strInvoiceable+" >";
			    row.insertCell(11).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSerialNo\" type=\"text\"    class=\"Box\" size=\"5%\" id=\"txtSerialNo."+(rowCount)+"\" value="+strSerialNo+" >";	    
			 	row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';		    
			 	row.insertCell(13).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strCustCode\" type=\"text\"    class=\"Box\" size=\"7%\" id=\"txtCustCode."+(rowCount)+"\" value="+strCustCode+" >";
			 	row.insertCell(14).innerHTML= "<input name=\"listclsInvoiceModelDtl["+(rowCount)+"].strSOCode\" type=\"text\"    class=\"Box\" size=\"13%\" id=\"txtSOCOde."+(rowCount)+"\" value="+strSOCode+" >";
			 	
			 	
			 	QtyTol+=parseFloat(dblAcceptQty);
// 			 	var txtTotQty = $("#txtQtyTotl").val();
// 				$("#txtQtyTotl").val(parseFloat(txtTotQty)+dblAcceptQty);
				
			 	
			    $("#txtProdCode").focus();
			    funCalculateTotalAmt();
			    funClearProduct();
			   // funGetTotal();
			    return false;
		}
		
	
		
		
</script>
</head>
<body >
	<div id="formHeading">
		<label>Commercial Tax Innvoice</label>
	</div>
	<s:form name="SOForm" method="POST"  action="saveCommercialInvoice.html?saddr=${urlHits}">
<%-- 		<input type="hidden" value="${urlHits}" name="saddr"> --%>
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
									<td  colspan="2"><s:input path="strInvCode" id="txtDCCode"
											ondblclick="funHelp('invoice')"
											cssClass="searchTextBox" /></td>

									<td width="100px"><label>Invoice Date</label>
									<td><s:input path="dteInvDate" id="txtDCDate"
											 readonly="true"  required="required" cssClass="calenderTextBox" /></td>
									<td></td>
								
								</tr>


								<tr>
									<td><label>Customer Code</label></td>
									<td  colspan="1"><s:input path="strCustCode" id="txtCustCode"
											ondblclick="funHelp('custMaster')" cssClass="searchTextBox" /></td>
									<td colspan="4"><label id="lblCustomerName"
										class="namelabel"></label></td>

									<td  style="display:none"><s:input id="txtSOCode" path="strSOCode"
											 style="display:none" class="searchTextBox"></s:input></td>
									
								</tr>

								<tr>

									<td><label>Location Code</label></td>
									<td><s:input type="text" id="txtLocCode" path="strLocCode"
											cssClass="searchTextBox"
											 readonly="true" /></td>
									<td colspan="2"><label id="lblLocName"></label></td>

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
										class="decimal-places numberField" style="width: 60%" onkeypress="funGetKeyCode(event,'Qty')"/></td>
									
									<td style="width: 115px"><label>Unit Price</label></td>
									<td><input id="txtUnitPrice" type="text"
										class="decimal-places numberField" style="width: 60%" /></td>	
										


								</tr>

								<tr>
								<td><label>UOM</label></td>
								<td><label id='lblUOM'></label></td>
								<td><label>Packing No</label></td>
									<td><input id="txtPackingNo" type="text" class="BoxW116px" /></td>

									<td><label>Remarks</label></td>
									<td><input id="txtRemarks" class="longTextBox"
										style="width: 100%" /></td>
											
									<td><input type="button" value="Add" class="smallButton"
										onclick="return btnAdd_onclick()" /></td>
									
										
								</tr>
							</table>

		<div class="dynamicTableContainer" style="height: 300px; width: 100%;">
								<table
									style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
									<tr bgcolor="#72BEFC">
										<td width="6%">Product Code</td>
										<!--  COl1   -->
										<td width="16%">Product  Name</td>
										<!--  COl2   -->
										<td width="1%"></td>
										<!--  COl3   -->
										<td width="3%">Qty</td>
										<!--  COl4   -->
									  <td width="3%">Wt/Unit</td> 
										<!-- COl5   -->
										<td width="3%">Total Wt</td> 
										<!-- COl6   -->
 										<td width="3%">Unit Price</td> 
										<!--  COl7   -->
										<td width="6%">Total Amt</td>
										<!--  COl8   -->
									   <td width="5%">Packing No</td>
										<!--  COl9   -->
										<td width="5%">Remarks</td>
										<!--COl10   -->
										<td width="2.5%">Invoice</td> 
										<!--  COl11   -->
										<td width="3%">Serial No</td>
										<!-- COl12   -->
										<td width="3%">Delete</td> 
										<!--  COl13   -->
                                        <td width="6%">Customer Code</td> 
										<!-- COl14   -->
                                        <td width="10%">SOCode</td> 
											<!-- COl15   -->
									</tr>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
									<table id="tblProdDet"
										style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
										class="transTablex col15-center">
										<tbody>
										<col style="width: 8%">
										<!--  COl1   -->
										<col style="width: 22%">
										<!--  COl2   -->
										<col style="width: 0%">
										<!--  COl3   -->
										<col style="width: 4%">
										<!--  COl4   -->
										<col style="width: 5%"> 
										<!--COl5   -->
										<col style="width: 3.5%"> 
										<!--COl6   -->
 										<col style="width: 3.5%"> 
										<!-- COl7   -->
										<col style="width: 8%"> 
										<!--  COl8   -->
										<col style="width: 6%"> 
										<!--  COl9   -->
										<col style="width: 7%"> 
										<!--  COl10  -->
										<col style="width: 4.5%">
								    	<!--COl11  -->
										<col style="width: 3.5%"> 
										<!--  COl12   -->
										<col style="width: 4%"> 
										<!--COl13   -->
										<col style="width: 7%">
										<!--  COl14   -->
										<col style="width: 10">
										<!--  COl15   -->

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
							</tr>
							<tr>
							
								<td><label id="lblQtyTotl">Total Qty</label></td>
									<td><input type="text" id="txtQtyTotl" value="0.00"
											 readonly="true"
											class="BoxW116px" /></td>
							
								<td><label id="lblsubTotlAmt">SubTotal Amount</label></td>
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
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			
			
			</div>
			<input type="hidden" id="hidbillRate" ></input>	
			<input type="hidden" id="hidUnitPrice" ><input>
	
	</s:form>
	<script type="text/javascript">
		//funApplyNumberValidation();
	</script>
	
	
</body>
</html>
