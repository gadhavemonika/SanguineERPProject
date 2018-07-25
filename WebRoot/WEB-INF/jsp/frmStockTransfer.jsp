<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>STOCK TRANSFER</title>

	<script type="text/javascript">
	/**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	 */
	var ProductCodeArray=new Array();
	var ProductCodeVar=-1;
		var fieldName;
		var gProdType,gUOM;
		var urlHits;
		var listRow=0;
		$(document).ready(function () {
			$("#txtToLocCode").focus();
			
			 $("#txtSTDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
			 if($('#txtSTDate').val().trim().length==0)
			 {
				 
				 $('#txtSTDate').datepicker('setDate', 'today'); 
			 }
			 /**
			  * Ready Function for Ajax Waiting and reset form
			  */
			 	$(document).ajaxStart(function(){
				    $("#wait").css("display","block");
				  });
				  $(document).ajaxComplete(function(){
				    $("#wait").css("display","none");
				  });
			}); 
				
		/**
		 * Check validation before adding product data in grid
		 */
		function btnAdd_onclick() 
		{
			 var strProdCode = $("#txtProdCode").val();
			 
			    if(strProdCode.trim().length<=0)
				{
					$("#txtProdCode").focus();
					alert("Please Enter Product Code or Search");
					return false;
				}	
			    if($("#txtQuantity").val().length<=0 || $("#txtQuantity").val()==0)
				{
			    	alert("Please Enter Quantity");
					$("#txtQuantity").focus();
					return false;
				}
		    	else
		    	{
		    		
		    	 	if(funDuplicateProduct(strProdCode))
		    		 {
		    	 		funAddRow();
		    		 }
		    		
		    	}
		}
		/**
		 * Check duplicate record in grid
		 */
		function funDuplicateProduct(strProdCode)
		{
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;	
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblProduct tr').each(function()
				    {
					    if(strProdCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Already added "+ strProdCode);
					    	 funResetProductFields();	
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		}
		
		/**
		 * Adding Product Data in grid 
		 */
		function funAddRow() 
		{ 
		    var prodCode = $("#txtProdCode").val();		  
		    var prodName = document.getElementById("lblProdName").innerHTML;
		    var unitPrice = $("#txtUnitPrice").val();
		    var stock = $("#txtStock").text();
		    var quantity = $("#txtQuantity").val();
		    quantity=parseFloat(quantity).toFixed(maxQuantityDecimalPlaceLimit);
		    var totalPrice=parseFloat(unitPrice)*parseFloat(quantity);
		    unitPrice=parseFloat(unitPrice).toFixed(maxAmountDecimalPlaceLimit);
		    totalPrice=parseFloat(totalPrice).toFixed(maxAmountDecimalPlaceLimit);
		    var wtUnit = $("#txtWtUnit").val();
		    var remarks = $("#txtRemarks").val();
		    var totalWt=quantity*wtUnit;	
		    var fromLoc=$('#txtFromLocCode').val();
		    var toLoc=$('#txtToLocCode').val();
		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);		   
		    rowCount=listRow;
		  	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listStkTransDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+prodCode+"' />";		      
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"55%\" name=\"listStkTransDtl["+(rowCount)+"].strProdName\" id=\"lblProdName."+(rowCount)+"\" value='"+prodName+"' />";		   
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"16%\" name=\"listStkTransDtl["+(rowCount)+"].strProdType\" id=\"lblProdType."+(rowCount)+"\" value='"+gProdType+"' />";		   
		    row.insertCell(3).innerHTML= "<input class=\"Box\" size=\"4%\" name=\"listStkTransDtl["+(rowCount)+"].strUOM\" id=\"lblUMO."+(rowCount)+"\" value='"+gUOM+"' >";		   		   
		    row.insertCell(4).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblQty\" id=\"txtQuantity."+(rowCount)+"\" value="+quantity+" class=\"decimal-places inputText-Auto QtyCell\" onblur=\"funUpdatePrice(this);\">";
		    row.insertCell(5).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblPrice\" id=\"txtUnitPrice."+(rowCount)+"\" value="+unitPrice+" class=\"decimal-places inputText-Auto Box\">";
		    row.insertCell(6).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblTotalPrice\" id=\"txtTotalPrice."+(rowCount)+"\" value="+totalPrice+" class=\"decimal-places inputText-Auto Box totalValueCell\">";
		    row.insertCell(7).innerHTML= "<input type=\"number\" readonly=\"readonly\" step=\"any\" required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblWeight\" id=\"txtWtUnit."+(rowCount)+"\" value='"+wtUnit+"' class=\"inputText-Auto\" >";		   
		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblTotalWt\" id=\"txtTotalWt."+(rowCount)+"\" value='"+totalWt+"' />";		   		    
		    row.insertCell(9).innerHTML= "<input size=\"18%\" name=\"listStkTransDtl["+(rowCount)+"].strRemark\" id=\"txtRemarks."+(rowCount)+"\" value='"+remarks+"'/>";
		    //row.insertCell(10).innerHTML= "<input type=\"hidden\" size=\"0%\" name=\"listStkTransDtl["+(rowCount)+"].dblPrice\" id=\"txtUnitPrice."+(rowCount)+"\" value='"+unitPrice+"' />";
		    row.insertCell(10).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    listRow++;
		    funCalSubTotal();
		    funApplyNumberValidation();
		    funResetProductFields();		
		    return false;
		}
		
		/**
		 * Delete a particular record from a grid
		 */
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProduct");
		    table.deleteRow(index);
		}
		
		/**
		 * Reset Textfield after adding data in grid
		 */
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    $("#lblProdName").text('');
		    $("#txtUnitPrice").val('');
		    $("#txtWtUnit").val('');
		    $("#txtStock").text('');
		    $("#txtQuantity").val('');
		    $("#txtRemarks").val('');
		    $("#txtProdCode").focus();
		}
		
		/**
		 * Remove all product from grid
		 */
		function funRemoveProductRows()
		{
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		/**
		 * Open help windows
		 */
		function funHelp(transactionName)
		{
			fieldName=transactionName;
		//    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	    }
		
		/**
		 * Set Data after selecting form Help windows
		 */
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'stktransfercode':
			    	funGetStockTransferData(code);
			        break;
			        
			    case 'locby':
			    	funSetLocationBy(code);
			        break;
			        
			    case 'locon':
			    	funSetLocationOn(code);
			        break;
			        
			    case 'productInUse':
			    	funSetProduct(code);
			        break;
			}
		}
		
		/**
		 * Get Stock Transfer Data
		 */
		function funGetStockTransferData(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/frmStockTransfer1.html?STCode="+code;	
			$.ajax({
		        type: "POST",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strSTCode=='Invalid Code')
			       	{
			       		alert('Invalid Code');
			       		$("#txtSTCode").val('');
			       		$("#txtSTCode").focus();
			       		return false;
			       	}
			       	else
			       	{
			       		$("#txtSTCode").val(response.strSTCode);
			       		$('#txtSTDate').val(response.dtSTDate);			       		
		        		$("#txtFromLocCode").val(response.strFromLocCode);
		        		$("#lblFromLocName").text(response.strFromLocName);
		        		$("#txtToLocCode").val(response.strToLocCode);
		        		$("#lblToLocName").text(response.strToLocName);
		        		$("#txtNarration").text(response.strNarration);
		        		$("#cmbMaterialIssue").val(response.strMaterialIssue);	
		        		$("#txtTotalAmount").val(response.dblTotalAmt);
		        		//$("#cmbAgainst").val(response.strAgainst);
		            	//$("#txtReqCode").val(response.strReqCode);		
		        		//funOnChange();
		        		$("#txtProdCode").focus();
		        		funGetProdData1(response.listStkTransDtl);
			       		
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
		function funGetProdData1(response)
		{
		        	funRemoveProductRows();
		        	var count=0;
					$.each(response, function(i,item)
                    {		count=i;		
						funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].strProdType,
						response[i].strUOM,response[i].dblQty,response[i].dblWeight,response[i].dblTotalWt
						,response[i].strRemark,response[i].dblPrice,response[i].dblTotalPrice);
                                                   
                    });
				listRow=count+1;
			//	funOnChange();
		}
		/**
		 * Fill product Data in grid
		 */
		function funfillProdRow(prodCode,prodName,ProdType,UOM,quantity ,wtUnit,totalWt ,remarks,unitPrice,totalPrice )
		{
			    var table = document.getElementById("tblProduct");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);		
			    quantity=parseFloat(quantity).toFixed(maxQuantityDecimalPlaceLimit);
			    unitPrice=parseFloat(unitPrice).toFixed(maxAmountDecimalPlaceLimit);
			    totalPrice=parseFloat(totalPrice).toFixed(maxAmountDecimalPlaceLimit);
			  	row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listStkTransDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+prodCode+"' />";		      
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"55%\" name=\"listStkTransDtl["+(rowCount)+"].strProdName\" id=\"lblProdName."+(rowCount)+"\" value='"+prodName+"' />";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"16%\" name=\"listStkTransDtl["+(rowCount)+"].strProdType\" id=\"lblProdType."+(rowCount)+"\" value='"+ProdType+"' />";		   
			    row.insertCell(3).innerHTML= "<input class=\"Box\" size=\"4%\" name=\"listStkTransDtl["+(rowCount)+"].strUOM\" id=\"lblUMO."+(rowCount)+"\" value='"+UOM+"' >";		   		   
			    row.insertCell(4).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblQty\" id=\"txtQuantity."+(rowCount)+"\" value="+quantity+" class=\"decimal-places inputText-Auto QtyCell\" onblur=\"funUpdatePrice(this);\">";
			    row.insertCell(5).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblPrice\" id=\"txtUnitPrice."+(rowCount)+"\" value="+unitPrice+" class=\"decimal-places inputText-Auto Box\">";
			    row.insertCell(6).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblTotalPrice\" id=\"txtTotalPrice."+(rowCount)+"\" value="+totalPrice+" class=\"decimal-places inputText-Auto Box totalValueCell\">";
			    row.insertCell(7).innerHTML= "<input type=\"number\" readonly=\"readonly\" step=\"any\" required = \"required\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblWeight\" id=\"txtWtUnit."+(rowCount)+"\" value='"+wtUnit+"' class=\"inputText-Auto\" >";		   
			    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" style=\"text-align: right;\" name=\"listStkTransDtl["+(rowCount)+"].dblTotalWt\" id=\"txtTotalWt."+(rowCount)+"\" value='"+totalWt+"' />";		   		    
			    row.insertCell(9).innerHTML= "<input size=\"18%\" name=\"listStkTransDtl["+(rowCount)+"].strRemark\" id=\"txtRemarks."+(rowCount)+"\" value='"+remarks+"'/>";
			    row.insertCell(10).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
				
		}
		/**
		 * Update total price when user change the qty 
		 */
		function funUpdatePrice(object)
		{
			var index=object.parentNode.parentNode.rowIndex;
			var price=parseFloat(document.getElementById("txtUnitPrice."+index).value)*parseFloat(object.value);
			
			document.getElementById("txtTotalPrice."+index).value=price.toFixed(parseInt(maxAmountDecimalPlaceLimit));				
			funCalSubTotal();
		}
		
		/**
		 * Calculate subtotal
		 */
		function funCalSubTotal()
	    {
	        var dblQtyTot=0;		        
	        var subtot=0;
	        $('#tblProduct tr').each(function() {
			    var totalvalue = $(this).find(".totalValueCell").val();
			    subtot = parseFloat(subtot + parseFloat(totalvalue));
			 });
							
			$("#txtTotalAmount").val(parseFloat(subtot).toFixed(parseInt(maxAmountDecimalPlaceLimit)));  	
			
	    }	
		/**
		 * Set Location On Data after selecting form Help windows
		 */
		function funSetLocationOn(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#txtToLocCode").val('');
				       		$("#lblToLocName").text("");
				       		$("#txtToLocCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtToLocCode").val(response.strLocCode);
		        		$("#lblToLocName").text(response.strLocName);		        		
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
		 * Set Location By Data after selecting form Help windows
		 */
		function funSetLocationBy(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#txtFromLocCode").val('');
				       		$("#lblFromLocName").text("");
				       		$("#txtFromLocCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtFromLocCode").val(response.strLocCode);
		        		$("#lblFromLocName").text(response.strLocName);
		        		$("#txtToLocCode").focus();
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
		 * Set Product Data after selecting form Help windows
		 */
		function funSetProduct(code)
		{
			
			var searchUrl="";
			var loccode = $('#txtFromLocCode').val();
			var prodCode = code;
			searchUrl=getContextPath()+"/loadLocCustomerLinkedProductData.html?frmLocCode="+loccode+"&prodCode="+prodCode;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if('Invalid Code' == response.strProdCode){
			    		alert('Invalid Product Code');
				    	$("#txtProdCode").val('');
				    	$("#lblProdName").text('');
				    	$("#txtProdCode").focus();
			    	}else{
			    		var dblStock=funGetProductStock(response.strProdCode);
			    	$("#txtProdCode").val(response.strProdCode);
		        	document.getElementById("lblProdName").innerHTML=response.strProdName;
		        	$("#txtStock").text(dblStock);
		        	$("#txtWtUnit").val(response.dblWeight);
		        	var dblCostRM =(response.dblCostRM).toFixed(maxAmountDecimalPlaceLimit);
		        	$("#txtUnitPrice").val(dblCostRM);
		        	gProdType=response.strProdType;
		        	gUOM=response.strUOM;
		        	$("#txtQuantity").focus();
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
			
			/* 
					var searchUrl="";
					searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
					$.ajax
					({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	if('Invalid Code' == response.strProdCode){
					    		alert('Invalid Product Code');
						    	$("#txtProdCode").val('');
						    	$("#lblProdName").text('');
						    	$("#txtProdCode").focus();
					    	}else{
					    		var dblStock=funGetProductStock(response.strProdCode);
					    	$("#txtProdCode").val(response.strProdCode);
				        	document.getElementById("lblProdName").innerHTML=response.strProdName;
				        	$("#txtStock").text(dblStock);
				        	$("#txtWtUnit").val(response.dblWeight);
				        	$("#txtUnitPrice").val(response.dblCostRM);
				        	gProdType=response.strProdType;
				        	gUOM=response.strUOM;
				        	$("#txtQuantity").focus();
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
				    }); */
				
			
		}
		/**
		 * Get product stock 
		 */
		function funGetProductStock(strProdCode)
		{
			var searchUrl="";	
			var locCode=$("#txtFromLocCode").val();
			var dblStock="0";
			searchUrl=getContextPath()+"/getProductStock.html?prodCode="+strProdCode+"&locCode="+locCode;	
			//alert(searchUrl);		
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    async: false,
				    success: function(response)
				    {
				    	dblStock= response;
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
			return dblStock;
		}
		/**
		 * Ready function
		 */
		$(function()
		{
			/**
			 * Document Attach Link 
			 */
			$('a#baseUrl').click(function() 
			{
				if($("#txtSTCode").val().trim()=="")
				{
					alert("Please Select Stock Transfer Code");
					return false;
				}
				window.open('attachDoc.html?transName=frmStockTransfer.jsp&formName=Stock Transfer&code='+$("#txtSTCode").val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			/**
			 *On blur event
			 */
			$('#txtSTCode').blur(function () {
				 var code=$('#txtSTCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){					  
					 funGetStockTransferData(code);
				   }
				}); 
			
			$('#txtFromLocCode').blur(function () {
				 var code=$('#txtFromLocCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){					  
					   funSetLocationBy(code);
				   }
				});
			
			$('#txtToLocCode').blur(function () {
				var code=$('#txtToLocCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){	
					   funSetLocationOn(code);
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
		 * Apply number text filed validation
		 */
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit , negative: false});
		}
		/**
		 * Ready Function 
		 * And Getting session Value
		 * Success Message after Submit the Form
		 * open slip
		 */
		$(document).ready(function(){
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
							}
							else
							{
							%>	
								alert(message);
							<%
							}	
			}%>
			
			/**
			 * Checking Authorization
			 */
			var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
			if(flagOpenFromAuthorization == 'true')
			{
				funGetStockTransferData("${authorizationSTCode}");
			}
			
			var code='';
			<%if(null!=session.getAttribute("rptStockTranferCode")){%>
			code='<%=session.getAttribute("rptStockTranferCode").toString()%>';
			<%session.removeAttribute("rptStockTranferCode");%>
			var isOk=confirm("Do You Want to Generate Slip?");
			if(isOk){
			window.open(getContextPath()+"/openRptStockTransferSlip.html?rptStockTranferCode="+code,'_blank');
			}
			<%}%>
	    });
		
		/**
		 * Checking Validation before submiting the data
		 */
		function funCallFormAction() 
		{
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;	
			if ($("#txtSTDate").val().trim().length==0) 
				{
					alert('Invalid Date');
					$("#txtSTDate").focus();
					return false;
				}			
			if(!fun_isDate($("#txtSTDate").val()))
				{
					 alert('Invalid Date');
			         $("#txtSTDate").focus();
			         return false;
				}
			if($("#txtFromLocCode").val()=='')
				{
					alert("Please Enter Location From or Search");
					$("#txtFromLocCode").focus();
					return false;
				}
			 if($("#txtToLocCode").val()=='')
				{
					alert("Please Enter Location To or Search");
					$("#txtToLocCode").focus();
					return false;
				}
			 if($("#txtFromLocCode").val()==$("#txtToLocCode").val())
				{
					alert("Location From and Location To cannot be same");
					return false;
				}
			 if(rowCount<1)
				{
					alert("Please Add Product in Grid");
					$('#txtProdCode').focus();
					return false;
				}
			/*  if(  $("#cmbAgainst").val() == null || $("#cmbAgainst").val().trim().length == 0 )
			 {
			 		alert("Please Select Against");
					return false;
			 } */
		 
			else
				{
					 return true;
				}
		}
		
		function funLocCustomerLinkedProductData()
		{
			
		}

		function funOnChange()
		{
					 if($("#cmbAgainst").val()=="Direct")
						 {
							document.all[ 'txtReqCode' ].style.display = 'none';
							document.all[ 'spQty' ].style.display = 'none';
							
						 	$("#txtProdCode").attr("disabled", false);
						 }
					
					 else if($("#cmbAgainst").val()=="Requisition")
						 {
						 	document.all[ 'txtReqCode' ].style.display = 'block';
							document.all[ 'spQty' ].style.display = 'none';
						$("#txtProdCode").attr("disabled", true);
						 } 
		}
		
		
		function funOpenAgainst()
		{
			
					if($("#txtLocFrom").val()=="")
			        {
			            alert("Please Enter From Location Or Search")
			            return false
			        }
			        if($("#txtLocTo").val()=="")
			        {
			            alert("Please Enter To Location Or Search")
			            return false
			        }
			        else
			        {
						strLocFrom=$("#txtLocFrom").val();
			            strLocTo=$("#txtLocTo").val();
			          //  var retval=window.showModalDialog("frmMISforMR.html?strLocFrom="+strLocFrom+"&strLocTo="+strLocTo,"","dialogHeight:700px;dialogWidth:800px;dialogLeft:300px;")
					  	  var retval=window.open("frmStockTransferforSR.html?strLocFrom="+strLocFrom+"&strLocTo="+strLocTo,"","dialogHeight:700px;dialogWidth:800px;dialogLeft:300px;")     
//	 		          if(retval != null)
//	 			            {
//	 			                strVal=retval.split("#")
//	 			                $("#txtReqCode").val(strVal[0]);
//	 			            }
			          
			          
			          
					  	var timer = setInterval(function ()
							    {
								if(retval.closed)
									{
										if (retval.returnValue != null)
										{
											strVal=retval.returnValue.split("#")
							                $("#txtReqCode").val(strVal[0]);
						
										}
										clearInterval(timer);
									}
							    }, 500);
			          
			          
			        }
				}
		
		
		
		
		
		
		
		
		
		
		
		
	</script>
</head>

<body>
<div id="formHeading">
		<label>Stock Transfer</label>
	</div>
	<s:form name="stkTransfer" method="POST" action="saveStkTransfer.html?saddr=${urlHits}">				
	<br/>
		<input type="hidden" value="${urlHits}" name="saddr">
			<table class="transTable">
		    	<tr>
				    <th align="right"> <a id="baseUrl" href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp; </th>
				</tr>
			</table>
			<table class="transTable">																			    
				    <tr>
				        <td width="100px"><label id="lblSTCode" >ST Code</label></td>
				        <td><s:input id="txtSTCode" path="strSTCode"  ondblclick="funHelp('stktransfercode')" cssClass="searchTextBox"/></td>
				        
				        <td><label id="lblNo" >No</label> &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; <s:input id="txtNo" path="strNo" cssClass="BoxW116px"/></td>
				       <%--  <td><s:input id="txtNo" path="strNo" /></td> --%>
				        <td><label id="lblSTDate">Date       </label>&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; <s:input id="txtSTDate" pattern="\d{1,2}-\d{1,2}-\d{4}" path="dtSTDate" cssClass="calenderTextBox"/></td>
				        <td></td>
				       <td></td>
				        <td></td>
				        <td></td>
				    </tr>
				    
				    <tr>
					    <td><label id="lblFromLoc" >From</label></td>
				        <td width="10%"><s:input id="txtFromLocCode" path="strFromLocCode" value="${locationCode}"  ondblclick="funHelp('locby')" cssClass="searchTextBox"/></td>
					    <td><s:label id="lblFromLocName" path="strFromLocName" cssClass="namelabel">${locationName}</s:label></td>					    
					    <td><label id="lblToLoc" >To</label> &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;  <s:input id="txtToLocCode" path="strToLocCode" ondblclick="funHelp('locon')"   cssClass="searchTextBox"/></td>
				        <%-- <td><s:input id="txtToLocCode" path="strToLocCode" ondblclick="funHelp('locon')"   cssClass="searchTextBox"/></td> --%>
					    <td><s:label id="lblToLocName" path="strToLocName" cssClass="namelabel"/></td>
					    <td></td>  
				      <td></td>
				      <td></td>
				      
				     
				      
					</tr>
					
					 <tr >
						<%-- <td><label id="lblMI" >Material Issue</label></td>
						<td>
						   	<s:select id="cmbMaterialIssue" path="strMaterialIssue" cssClass="BoxW62px">
						   		<option value="No">No</option>
						   		<option value="Yes">Yes</option>
						   	</s:select>
						</td> --%>
						
						<td ><label id="lblMI" >Type</label>
						</td>
						<td colspan="4">
						   	<select id="cmbType" class="BoxW124px">
						   		<option value="Product">Product</option>
						   		<option value="Assembly">Assembly</option>
						   	</select>
						</td>
						<%-- <td><label>Against</label></td>
					<td><s:select id="cmbAgainst" items="${strProcessList}"  onchange="funOnChange();"	name="cmbAgainst" cssClass="BoxW124px" path="strAgainst">
					</s:select>
					</td>  
					<td ><s:input id="txtReqCode" name="txtReqCode" path="strReqCode" ondblclick="return funOpenAgainst();"   cssClass="searchTextBox " cssStyle="width:90%;background-position: 165px 2px;"/></td>
					<td></td> --%>
				     
					</tr>				
				</table>
				<table  class="transTableMiddle">
				<tr>
				<td width="100px"><label>Product Code</label></td>
				<td width="205px"><input id="txtProdCode" ondblclick="funHelp('productInUse')" class="searchTextBox"></input></td>
				<td width="85px"><label>Product Name</label></td>
				<td><label id="lblProdName" class="namelabel" style="font-size: 12px;"></label></td>
				<td width="85px"><label>Unit Price</label></td>
				<td><input readonly="readonly" id="txtUnitPrice" type="text"  class="decimal-places numberField" style="width: 100px"></input></td>
				</tr>
				
				<tr>
				<td><label>Stock</label></td>
				<td><label id="txtStock"></label></td>
				<td><span id="spQty" style="display:none"> Quantity </span></td>
				<td align="left"><input id="txtQuantity" type="text" class="decimal-places numberField" style="width: 100px;"></input></td>
				<td><label>Wt/Unit</label></td>
				<td><input id="txtWtUnit" readonly="readonly" class="decimal-places numberField" type="text"  style="width: 100px"></input></td>
				
				</tr>
				<tr>
				<td><label>Remarks</label></td>
				<td><input id="txtRemarks" class="remarkTextBox"></input></td>
				<td><input id="btnAdd" type="button" value="Add" onclick="return btnAdd_onclick();"  class="smallButton"></input></td>
				
				<td></td>
				<td></td> 
				<td></td>
				</tr>
				</table>
		<div class="dynamicTableContainer" style="height: 300px;">
			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px;
	                      font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="8%">Product Code</td><!-- col1   -->
					<td width="20%">Product Name</td><!-- col2   -->
					<td width="10%">Product Type</td><!-- col3   -->
					<td width="4%">UOM</td><!-- col4   -->
					<td width="5%">Quantity</td><!-- col5   -->
					<td width="6%">Unit Price</td><!-- col6   -->
					<td width="8%">Total Price</td><!-- col7   -->
					<td width="5%">Wt/Unit</td><!-- col8  -->
					<td width="6%">Total Wt</td><!-- col9   -->
					<td width="15%">Remarks</td><!-- col10   -->
					<td width="5%">Delete</td><!-- col11   -->
				</tr>
			</table>

			<div style="background-color: #a4d7ff; border: 1px solid #ccc;
			 display: block; height: 250px; margin: auto; overflow-x: hidden; 
			 overflow-y: scroll; width: 100%;">

				<table id="tblProduct"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col10-center">
					<tbody>
					<col style="width: 8%"><!-- col1   -->
					<col style="width: 20.2%"><!-- col2   -->
					<col style="width: 10%"><!-- col3   -->
					<col style="width: 4%"><!-- col4   -->
					<col style="width: 5%"><!-- col5   -->
					<col style="width: 6%"><!-- col6   -->
					<col style="width: 8%"><!-- col7   -->
					<col style="width: 5%"><!-- col8   -->
					<col style="width: 6%"><!-- col9   -->
					<col style="width: 15%"><!-- col10   -->
					<col style="width: 3.7%">	<!-- col11   -->				
<%-- 					<c:forEach items="${command.listStkTransDtl}" var="stkTrans" varStatus="status"> --%>
<!-- 						<tr> -->
<%-- 							<td><input readonly="readonly" class="Box" size="8%"  name="listStkTransDtl[${status.index}].strProdCode" value="${stkTrans.strProdCode}" /></td> --%>
<%-- 							<td><input readonly="readonly" class="Box" size="55%"name="listStkTransDtl[${status.index}].strProdName" value="${stkTrans.strProdName}" /></td> --%>
<%-- 							<td><input readonly="readonly" class="Box" size="16%"name="listStkTransDtl[${status.index}].strProdType" value="${stkTrans.strProdType}" /></td> --%>
<%-- 							<td><input readonly="readonly" class="Box" size="4%" name="listStkTransDtl[${status.index}].strUOM" value="${stkTrans.strUOM}" /></td> --%>
<%-- 							<td><input type="text"  required = "required" class="decimal-places inputText-Auto" style="text-align: right;" name="listStkTransDtl[${status.index}].dblQty" value="${stkTrans.dblQty}"/></td> --%>
<%-- 							<td><input  type="number" readonly="readonly" step="any" required = "required" class="inputText-Auto" style="text-align: right;" name="listStkTransDtl[${status.index}].dblWeight" value="${stkTrans.dblWeight}"  /></td> --%>
<%-- 							<td><input readonly="readonly" class="Box" size="8%"style="text-align: right;"  name="listStkTransDtl[${status.index}].dblTotalWt" value="${stkTrans.dblTotalWt}" /></td> --%>
<%-- 							<td><input size="25%" name="listStkTransDtl[${status.index}].strRemark" value="${stkTrans.strRemark}"/></td> --%>
<%-- 							<td><input type="hidden" name="listStkTransDtl[${status.index}].dblPrice" value="${stkTrans.dblPrice}"/></td> --%>
<!-- 							<td><input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)"></td> -->
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
					
					</tbody>
				</table>
				<script type="text/javascript">
					funApplyNumberValidation();
				</script>
			</div>

		</div>
			<table class="transTableMiddle">
			<tr>
			<td></td>
			<td width="34%"></td>
				<td width="10%">Total Amount</td>
				<td><s:input id="txtTotalAmount" type="text"
						value="${command.dblTotalAmt}" path="dblTotalAmt" readonly="true" class="numberField"/>
				</td>
				<td style="height: 4px;"></td>
			</tr>
				<tr>
					<td style="width: 10%">Narration</td>
		            <td colspan="4"><s:textarea id="txtNarration" path="strNarration" style="width: 50%"></s:textarea></td>					
				</tr>	
				<tr>
				<td></td>
				<td colspan="4"></td>
				</tr>
				
			</table>
		
				
		<br />
		<br />
		<p align="center">
			<input id="btnStkTransfer" type="submit" value="Submit" onclick="return funCallFormAction()" class="form_button"/>
				 <a STYLE="text-decoration:none"  href="frmStockTransfer.html?saddr=${urlHits}">  <input type="button"
				value="Reset" class="form_button" /></a>
		</p>		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	<script type="text/javascript">
	funApplyNumberValidation();
	</script>
</body>
</html>