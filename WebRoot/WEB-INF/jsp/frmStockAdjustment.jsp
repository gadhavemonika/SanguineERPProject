<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script type="text/javascript">
/**
 * Ready Function for Ajax Waiting and reset form
 */
$(document).ready(function(){
	 resetForms('stkAdjustment');
	   $("#txtProdCode").focus();	
	   $(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
		  $(document).ajaxComplete(function(){
		    $("#wait").css("display","none");
		  });
});
</script>
	<script type="text/javascript">
	 /**
	 * Global variable
	 */
	var ReceivedconversionUOM="";
	var issuedconversionUOM="";
	var recipeconversionUOM="";
	var ConversionValue=0;
	var fieldName,gUOM,gProdType,listRow=0;
		
	/**
	 * Check validation before adding product data in grid
	 */
		function btnAdd_onclick() 
		{			
			if($("#txtProdCode").val().trim().length ==0 && $("#lblProdName").text().trim().length==0)
	        {
				 alert("Please Enter Product Code Or Search");
	             $("#txtProdCode").focus() ; 
	             return false;
	        }
			
			if($("#txtQuantity").val().trim().length==0 || $("#txtQuantity").val()==0)
			{
				alert("Please Enter Quantity");
				$("#txtQuantity").focus();
				return false;
			}			 	
			else
		    {
				var strProdCode=$("#txtProdCode").val();
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
		    var quantity = $("#txtQuantity").val();
		    var LooseQty=quantity;
		    var unitPrice=$("#txtUnitPrice").val();
		    
		    var totalPrice=parseFloat(quantity)*parseFloat(unitPrice);
		    unitPrice=parseFloat(unitPrice).toFixed(maxQuantityDecimalPlaceLimit);
		   
		    var wtUnit = $("#txtWtUnit").val();
		    wtUnit=parseFloat(wtUnit).toFixed(maxQuantityDecimalPlaceLimit);
		    var strType = $("#cmbType").val();
		    var remark = $("#txtRemark").val();
		    var totalWt=quantity*wtUnit;
		    totalWt=parseFloat(totalWt).toFixed(maxQuantityDecimalPlaceLimit);
		    var uom=gUOM;
		    var prodType=gProdType;
		    if(prodType.trim()=='')
		    {
		    	
		    	prodType="NA";
		    }
		    
		    if($('#cmbConversionUOM').val()=="RecUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblReceiveConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				quantity=parseFloat(quantity)/parseFloat(ConversionValue);
			}
		    if($('#cmbConversionUOM').val()=="RecipeUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblRecipeConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				quantity=parseFloat(quantity)/parseFloat(ConversionValue);
			}
		    if($('#cmbConversionUOM').val()=="IssueUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblIssueConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				quantity=parseFloat(quantity)/parseFloat(ConversionValue);
			} 
		    
		    LooseQty=parseFloat(LooseQty).toFixed(maxQuantityDecimalPlaceLimit);
		    
		    quantity=parseFloat(quantity).toFixed(maxQuantityDecimalPlaceLimit);
			var tempQty=quantity.split(".");
			var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
		
			var Price=parseFloat(unitPrice) / parseFloat(ConversionValue);
			totalPrice=(parseFloat(Price)*parseFloat(LooseQty));				
			totalPrice=parseFloat(totalPrice).toFixed(maxQuantityDecimalPlaceLimit);    
			
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    rowCount=listRow;
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listStkAdjDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"48%\"  name=\"listStkAdjDtl["+(rowCount)+"].strProdName\" value='"+prodName+"' id=\"lblProdName."+(rowCount)+"\" >";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" name=\"listStkAdjDtl["+(rowCount)+"].strProdType\" value="+prodType+" id=\"lblProdType."+(rowCount)+"\" >";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" type=\"text\" name=\"listStkAdjDtl["+(rowCount)+"].strUOM\" id=\"lblUOM."+(rowCount)+"\" value="+uom+">";
		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" type=\"text\"  class=\"inputText-Auto\"  name=\"listStkAdjDtl["+(rowCount)+"].strDisplayQty\" id=\"txtDisplayQty."+(rowCount)+"\" value='"+Displyqty+"' \">";
		    row.insertCell(5).innerHTML= "<input style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto\"  name=\"listStkAdjDtl["+(rowCount)+"].dblQty\" id=\"txtQuantity."+(rowCount)+"\" value="+LooseQty+" onblur=\"funUpdatePrice(this);\">";
		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto\"  name=\"listStkAdjDtl["+(rowCount)+"].dblRate\" id=\"txtUnitPrice."+(rowCount)+"\" value="+unitPrice+">";
		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto totalValueCell\"  name=\"listStkAdjDtl["+(rowCount)+"].dblPrice\" id=\"txtTotalPrice."+(rowCount)+"\" value="+totalPrice+">";
		    row.insertCell(8).innerHTML= "<input style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto\" name=\"listStkAdjDtl["+(rowCount)+"].dblWeight\" id=\"txtWtUnit."+(rowCount)+"\" value="+wtUnit+">";
		    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" style=\"text-align: right;\" name=\"listStkAdjDtl["+(rowCount)+"].dblTotalWt\" id=\"lblTotalWt."+(rowCount)+"\" value="+totalWt+">";
		    if(strType=="IN")
		    	{
		    		row.insertCell(10).innerHTML= "<select id=\"txtType."+(rowCount)+"\" style=\"background-color:#a4d7ff;width:100%\" name=\"listStkAdjDtl["+(rowCount)+"].strType\" ><option selected=\"selected\" value=\"IN\">IN</option><option value=\"OUT\">OUT</option></select>";
		    	}
		    else
		    	{
		    		row.insertCell(10).innerHTML= "<select id=\"txtType."+(rowCount)+"\" style=\"background-color:#a4d7ff;width:100%\" name=\"listStkAdjDtl["+(rowCount)+"].strType\" ><option value=\"IN\">IN</option><option selected=\"selected\" value=\"OUT\">OUT</option></select>";
		    	}
		    
		    row.insertCell(11).innerHTML= "<input size=\"22%\" name=\"listStkAdjDtl["+(rowCount)+"].strRemark\" id=\"txtRemark."+(rowCount)+"\" value="+remark+">";		    
		    row.insertCell(12).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    row.insertCell(13).innerHTML= "<input size=\"0%\" style=\"display:none;\" name=\"listStkAdjDtl["+(rowCount)+"].strWSLinkedProdCode\" id=\"strWSLinkedProdCode."+(rowCount)+"\" value=''>";
		    listRow++;
		    funApplyNumberValidation();
		    funCalSubTotal();
		    funResetProductFields();
		    
		  
		    $("#txtLocCode").attr("readonly", true);  
		    $("#txtSADate").attr("readonly", true).datepicker("destroy") ; 
		    $("#hidConversionUOM").val($("#cmbConversionUOM").val());
		    $("#cmbConversionUOM").attr("disabled", true);
		    
		    return false;
		}
		 
		/**
		 * Calculate subtotal
		 */
		function funCalSubTotal()
	    {
			funApplyNumberValidation();
	        var dblQtyTot=0;		        
	        var subtot=0;
	        $('#tblProduct tr').each(function() {
			    var totalvalue = $(this).find(".totalValueCell").val();
			    subtot = parseFloat(subtot + parseFloat(totalvalue));
			  
			 });							
			$("#txtTotalAmount").val(parseFloat(subtot).toFixed(parseInt(maxAmountDecimalPlaceLimit)));  	
			
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
		 * Update total price when user change the qty 
		 */
		function funUpdatePrice(object)
		{
			var index=object.parentNode.parentNode.rowIndex;
			var price=parseFloat(document.getElementById("txtUnitPrice."+index).value);
			var LooseQty=object.value;
			var prodCode = document.getElementById("txtProdCode."+index).value;
			//alert(prodCode);
			 	if($('#cmbConversionUOM').val()=="RecUOM")
				{
	    			var ProductData=fungetConversionUOM(prodCode);
					ConversionValue=ProductData.dblReceiveConversion;
					ReceivedconversionUOM=ProductData.strReceivedUOM;
					issuedconversionUOM=ProductData.strIssueUOM;
					recipeconversionUOM=ProductData.strRecipeUOM;
					LooseQty=parseFloat(LooseQty)/parseFloat(ConversionValue);
				}
			    if($('#cmbConversionUOM').val()=="RecipeUOM")
				{
	    			var ProductData=fungetConversionUOM(prodCode);
					ConversionValue=ProductData.dblRecipeConversion;
					ReceivedconversionUOM=ProductData.strReceivedUOM;
					issuedconversionUOM=ProductData.strIssueUOM;
					recipeconversionUOM=ProductData.strRecipeUOM;
					LooseQty=parseFloat(LooseQty)/parseFloat(ConversionValue);
				}
			    if($('#cmbConversionUOM').val()=="IssueUOM")
				{
	    			var ProductData=fungetConversionUOM(prodCode);
					ConversionValue=ProductData.dblIssueConversion;
					ReceivedconversionUOM=ProductData.strReceivedUOM;
					issuedconversionUOM=ProductData.strIssueUOM;
					recipeconversionUOM=ProductData.strRecipeUOM;
					LooseQty=parseFloat(LooseQty)/parseFloat(ConversionValue);
				}
			    LooseQty=parseFloat(LooseQty).toFixed(maxQuantityDecimalPlaceLimit);
				var tempQty=LooseQty.split(".");
				var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
				document.getElementById("txtDisplayQty."+index).value=Displyqty;
				price=parseFloat(price).toFixed(maxAmountDecimalPlaceLimit);
				price=parseFloat(price) / parseFloat(ConversionValue);
				document.getElementById("txtTotalPrice."+index).value=(parseFloat(price)*parseFloat(object.value)).toFixed(maxQuantityDecimalPlaceLimit);				
				funCalSubTotal();
		}
		
		/**
		 * Delete a particular record from a grid
		 */
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProduct");
		    table.deleteRow(index);
		    funCalSubTotal();
		}
		
		/**
		 * Clear textfiled after adding data in textfield
		 */
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    document.getElementById("lblProdName").innerHTML='';
		    $("#txtQuantity").val('');
		    $("#txtWtUnit").val('');
		    $("#cmbType").val('IN');
		    $("#txtRemark").val('');
		    $("#txtStock").val('');
		    $("#txtProdCode").focus() ;
		}
		
		/**
		 * Reset the form
		 */
		function funResetFields()
		{
			funResetProductFields();
	    }
		
		/**
		 * Remove all product from grid
		 */
		function funRemoveRows()
	    {
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			for(var i=rowCount;i>=1;i--)
			{
				table.deleteRow(i);
			}
	    }
		
		/**
		 * Checking Validation before submiting the data
		 */
		$(function() 
		{
			$("#txtSADate" ).datepicker({ dateFormat: 'dd-mm-yy' });			
			$('#txtSADate').datepicker('setDate', 'today');
			$("form").submit(function()
			{
				var table = document.getElementById("tblProduct");
			    var rowCount = table.rows.length;
				if($("#txtSADate").val()=='')
				{
					alert("Please enter Stock Adjustment Date");
					return false;
				}
				else if($("#txtLocCode").val()=='')
				{
					alert("Please Select Location");
					return false;
				}					
				else if(rowCount==0)
				{
					alert("Plase Select Product");
					return false;
				}
			});
			
			/**
			 * Reset Field
			 */
			$("#btnReset").click(function()
			{
				$("#txtSACode").val('');
				$("#txtSADate").val('');
				$("#txtLocCode").val('');
				$("#txtAreaNarration").val('');
				$("#txtLocName").text('');
				
				funRemoveProductRows();
			});

			/**
			 * Attached document Link
			 */
			$('a#baseUrl').click(function() 
			{
				if($("#txtSACode").val().trim()=="")
				{
					alert("Please Select SA Code");
					return false;
				}

				 window.open('attachDoc.html?transName=frmStockAdjustment.jsp&formName=Stock Adjustment&code='+$('#txtSACode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");  
			});
			
			/**
			 *  Ready function for Textfield on blur event
			 */
			$('#txtSACode').blur(function () {
				var code=$('#txtSACode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){						   
					funSetStockAdjustmentData(code);
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
			$('#txtReasonCode').blur(function () {
				 var code=$('#txtReasonCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){					  
					 funGetReasonData(code);
				   }
				});
			
			
		});
		
		/**
		 * Checking Validation before submiting the data
		 */
		function funValidateFields()
		{
				if($("#txtLocCode").val().length==0)
				{
					$("#txtLocCode").focus();
					alert("Please select Location");
					return false;
				}
				else if($("#txtSADate").val().length==0)
				{
					alert("Please enter Stock Posting Date");
					$("#txtSADate").focus();
					return false;
				}
				else if($("#txtReasonCode").val()=='')
				{
					alert("Please Select Reason Code");
					$("#txtReasonCode").focus();
					return false;
				}
				else if(document.getElementById("tblProduct").rows.length=0)
				{
						alert("Plase Select Product");
						$("#txtProdCode").focus();
						return false;
				}
				else
				{
					return true;
				}
		
			
			
		}
		function funHelp1(transactionName,loc)
		{ 
			if ($('#txtLocCode').attr("readonly")!='readonly')
			{
				fieldName=transactionName;
				window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
			}
		}
	 
		/**
		 * Open help windows
		 */
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(fieldName=='productInUse')
			{
				if($("#txtLocCode").val()=='')
				{
					$("#txtLocCode").focus();
					alert("Please Select Location");
				}
				else
				{
			     
			      //  window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
				}
			}
			else
			{
				
			//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:700px;dialogLeft:300px;")
				window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:700px;dialogLeft:300px;")
			}
	    }
		
		/**
		 * Set Data after selecting form Help windows
		 */
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'stkadjcode':
			    	funSetStockAdjustmentData(code);
			        break;
			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'productInUse':
			    	funSetProduct(code);
			        break;
			        
			    case 'reason':
			    	funGetReasonData(code)
			    	break;
			}
		}
		/**
		 * Set Stock adjustement Data after selecting form Help windows
		 */
		function funSetStockAdjustmentData(code)
		{
			$("#txtProdCode").focus();
			var searchUrl="";
			searchUrl=getContextPath()+"/frmStockAdjustment1.html?SACode="+code;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.strSACode=='Invalid Code')
				       	{
				    		$("#txtSACode").val('');
				    		$("#txtSACode").focus();
				    		return false;
				       	}
				       	else
				       	{
				       		$("#txtSACode").val(response.strSACode);
				       		$("#txtSADate").val(response.dtSADate);
				       		$("#txtLocCode").val(response.strLocCode);
				       		$("#txtLocName").text(response.strLocName);
				       		$("#txtReasonCode").val(response.strReasonCode);
				       		$("#txtTotalAmount").val(response.dblTotalAmt);
				       		$("#txtAreaNarration").val(response.strNarration); 
				       		$("#cmbConversionUOM").val(response.strConversionUOM); 
				       		
				       	 	$("#txtLocCode").attr("readonly", true);  
						    $("#txtSADate").attr("readonly", true).datepicker("destroy") ; 
						    $("#hidConversionUOM").val($("#cmbConversionUOM").val());
						    $("#cmbConversionUOM").attr("disabled", true);
				       		
				       		/* $("#txtLocCode").attr("disabled", "disabled"); 
						    $("#txtSADate").attr("disabled", "disabled"); 
						    $("#cmbConversionUOM").attr("disabled", "disabled"); */
				       		
				       		
				       		funGetReasonData(response.strReasonCode);
				       		$("#txtProdCode").focus();
				       		funGetProdData1(response.listStkAdjDtl);
				       		
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
                    {				
						count=i;
						funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].strProdType,
						response[i].strUOM,response[i].dblQty,response[i].dblRate,response[i].dblPrice,response[i].dblWeight,response[i].dblTotalWt
						,response[i].strRemark,response[i].strType,response[i].strWSLinkedProdCode);
                                                   
                    });
					listRow=count+1;
					funApplyNumberValidation();
				
		}
		/**
		 * filling Product grid
		 */
		function funfillProdRow(prodCode,prodName,prodType,uom,quantity,dblRate,dblPrice,wtUnit,totalWt ,remark,strType,strWSLinkedProdCode )
		{
				var ProductData=fungetConversionUOM(prodCode);
				var ConversionValue=ProductData.dblRecipeConversion;
				var ReceivedconversionUOM=ProductData.strReceivedUOM;
				var issuedconversionUOM=ProductData.strIssueUOM;
				var recipeconversionUOM=ProductData.strRecipeUOM;
				quantity=parseFloat(quantity).toFixed(maxQuantityDecimalPlaceLimit);
				var tempQty=quantity.split(".");
				var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
			
			 	var table = document.getElementById("tblProduct");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);		   
			    
			    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listStkAdjDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"48%\"  name=\"listStkAdjDtl["+(rowCount)+"].strProdName\" id=\"lblProdName."+(rowCount)+"\"  value='"+prodName+"' >";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"14%\" name=\"listStkAdjDtl["+(rowCount)+"].strProdType\"  id=\"lblProdType."+(rowCount)+"\" value="+prodType+">";
			    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" type=\"text\" name=\"listStkAdjDtl["+(rowCount)+"].strUOM\" id=\"lblUOM."+(rowCount)+"\" value="+uom+">";
			    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" type=\"text\"  class=\"inputText-Auto\"  name=\"listStkAdjDtl["+(rowCount)+"].strDisplayQty\" id=\"txtDisplayQty."+(rowCount)+"\" value='"+Displyqty+"' \">";
			    row.insertCell(5).innerHTML= "<input style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto\"  name=\"listStkAdjDtl["+(rowCount)+"].dblQty\" id=\"txtQuantity."+(rowCount)+"\" value="+quantity+" onblur=\"funUpdatePrice(this);\">";
			    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto\"  name=\"listStkAdjDtl["+(rowCount)+"].dblRate\" id=\"txtUnitPrice."+(rowCount)+"\" value="+dblRate.toFixed(maxQuantityDecimalPlaceLimit)+">";
			    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto totalValueCell\"  name=\"listStkAdjDtl["+(rowCount)+"].dblPrice\" id=\"txtTotalPrice."+(rowCount)+"\" value="+dblPrice.toFixed(maxQuantityDecimalPlaceLimit)+">";
			    row.insertCell(8).innerHTML= "<input style=\"text-align: right;\" type=\"text\" required = \"required\" class=\"decimal-places inputText-Auto\" name=\"listStkAdjDtl["+(rowCount)+"].dblWeight\" id=\"txtWtUnit."+(rowCount)+"\" value="+wtUnit.toFixed(maxQuantityDecimalPlaceLimit)+">";
			    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"6%\" style=\"text-align: right;\" name=\"listStkAdjDtl["+(rowCount)+"].dblTotalWt\" id=\"lblTotalWt."+(rowCount)+"\" value="+totalWt.toFixed(maxQuantityDecimalPlaceLimit)+">";
			    if(strType=="IN")
			    	{
			    		row.insertCell(10).innerHTML= "<select id=\"txtType."+(rowCount)+"\" style=\"background-color:#a4d7ff;width:100%\" name=\"listStkAdjDtl["+(rowCount)+"].strType\" ><option selected=\"selected\" value=\"IN\">IN</option><option value=\"OUT\">OUT</option></select>";
			    	}
			    else
			    	{
			    		row.insertCell(10).innerHTML= "<select id=\"txtType."+(rowCount)+"\" style=\"background-color:#a4d7ff;width:100%\" name=\"listStkAdjDtl["+(rowCount)+"].strType\" ><option value=\"IN\">IN</option><option selected=\"selected\" value=\"OUT\">OUT</option></select>";
			    	}
			    
			    row.insertCell(11).innerHTML= "<input size=\"21%\" name=\"listStkAdjDtl["+(rowCount)+"].strRemark\" id=\"txtRemark."+(rowCount)+"\" value='"+remark+"'>";		    
			    row.insertCell(12).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
			    row.insertCell(13).innerHTML= "<input size=\"0%\" style=\"display:none;\" name=\"listStkAdjDtl["+(rowCount)+"].strWSLinkedProdCode\" id=\"strWSLinkedProdCode."+(rowCount)+"\" value='"+strWSLinkedProdCode+"' >";
		}
		/**
		 * Set Location Data after selecting form Help windows
		 */
		function funSetLocation(code)
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
				       		$("#txtLocCode").val('');
				       		$("#txtLocName").text("");
				       		$("#txtLocCode").focus();
				       	}
				       	else
				       	{
				    		$("#txtLocCode").val(response.strLocCode);
				    		$("#txtLocName").text(response.strLocName);
				    		$('#txtProdCode').focus();
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
			//var qty=funGetProductStock(code);
			//alert("Stock="+qty);
			var searchUrl="";
			
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			//alert(searchUrl);
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
		        	$("#txtStock").val(dblStock);
		        	$("#txtUnitPrice").val(response.dblCostRM);
		        	$("#txtWtUnit").val(response.dblWeight);
		        	gUOM=response.strUOM;
		        	gProdType=response.strProdType;
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
		}
		
		/**
		 * Set Reason Data after selecting form Help windows
		 */
		function funGetReasonData(code) {
			$.ajax({
					type : "GET",
					url : getContextPath()
							+ "/loadReasonMasterData.html?reasonCode=" + code,
					dataType : "json",
					success : function(response) {
						if('Invalid Code' == response.strReasonCode)
						{
							alert("Invalid Reason Code");
							$("#txtReasonCode").val('');
							$("#txtReasonName").text('');
							$("#txtReasonCode").focus();
						}else{
							$("#txtReasonCode").val(response.strReasonCode);
							//alert(response.strReasonName);
							$("#txtReasonName").text(response.strReasonName);
							
							 
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
		 * Get product stock 
		 */
		function funGetProductStock(strProdCode)
		{
			var searchUrl="";	
			var locCode=$("#txtLocCode").val();
			var dblStock="0";
			var strSADate = $("#txtSADate").val();
		//	searchUrl=getContextPath()+"/getProductStock.html?prodCode="+strProdCode+"&locCode="+locCode;	
		searchUrl=getContextPath()+"/getProductStockInUOM.html?prodCode="+strProdCode+"&locCode="+locCode+"&strTransDate="+strSADate+"&strUOM=RecUOM";	
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
	 		//alert("dblStock\t"+dblStock);
			return dblStock;
		}
			
		
	
		/**
		 * Ready Function for Initialize textField with default value
		 * And Set date in date picker 
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
			}}%>
			/**
			 * Checking Authorization
			 */
			var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
			if(flagOpenFromAuthorization == 'true')
			{
				funSetStockAdjustmentData("${authorizationSACode}");
			}
			
			var code='';
			<%if(null!=session.getAttribute("rptSACode")){%>
			code='<%=session.getAttribute("rptSACode").toString()%>';
			<%session.removeAttribute("rptSACode");%>
			var isOk=confirm("Do You Want to Generate Slip?");
			if(isOk){
			window.open(getContextPath()+"/openRptStockAdjustmentSlip.html?rptSACode="+code,'_blank');
			}
			<%}%>
	
		});
		
		/**
		 * Get Conversion Ratio Data
		 */
		function fungetConversionUOM(code)
		{
			var searchUrl="";
			var ProductData;
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    async: false,
			    success: function(response)
			    {
			    	ProductData=response;
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
			//alert(ProductData);
			return ProductData;
		}
		/**
		 * Apply number text filed validation
		 */
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit, negative: false });
		}
		
	</script>
</head>

<body onload="funOnload();">
	<div id="formHeading">
		<label>Stock Adjustment</label>
	</div>

	<s:form name="stkAdjustment" method="POST" action="saveStkAdjustment.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
			<br>
				<table class="transTable">
			<tr>
				<th align="right"><a id="baseUrl" href="#"> Attach
						Documents</a>&nbsp; &nbsp; &nbsp; &nbsp;</th>
			</tr>
		</table>
		<table class="transTable">
			<tr>
				<td width="100px"><label id="lblSACode">SA Code</label></td>
				<td width="120px"><s:input id="txtSACode" path="strSACode"
						ondblclick="funHelp('stkadjcode')" cssClass="searchTextBox" /></td>
				<td width="300px"></td>
				<td width="50px">SA Date</td>
				<td colspan="2"><s:input id="txtSADate" path="dtSADate"
						required="required" pattern="\d{1,2}-\d{1,2}-\d{4}"
						cssClass="calenderTextBox" /> <s:errors path="dtSADate"></s:errors></td>
			</tr>
			<tr>
				<td><label id="lblLocation">Location</label></td>
				<td><s:input id="txtLocCode" path="strLocCode"
						ondblclick="funHelp1('locationmaster','loc')" value="${locationCode}"
						required="required" cssClass="searchTextBox" /></td>
				<td><s:label id="txtLocName" path="strLocName">${locationName}</s:label></td>

				<td>Reason</td>
				<td width="50px"><s:input id="txtReasonCode"
						path="strReasonCode" ondblclick="funHelp('reason')"
						required="required" cssClass="searchTextBox" /></td>
				<td id="txtReasonName"></td>

			</tr>
			<tr>
				<td>Conversion UOM</td>
				<td colspan="5"><select id="cmbConversionUOM"
						Class="BoxW124px" >
						<option value="RecUOM">Recieved UOM</option>
						<option value="IssueUOM">Issue UOM</option>
						<option value="RecipeUOM">Recipe UOM</option>
					</select></td>
			</tr>
		</table>

		<table class="transTableMiddle1">
				<tr>
					<td width="100px">Product Code</td>
					<td width="120px"><input id="txtProdCode" ondblclick="funHelp('productInUse')" class="searchTextBox"></input></td>
					<td width="90px">Product Name </td>
					<td width="200px"><label id="lblProdName" class="namelabel" style="font-size: 12px;"></label></td>
					<td width="50px">Stock </td>
					<td width="50px" colspan="3"><input id="txtStock" readonly="readonly" class="BoxW116px right-Align" style="width: 15%;padding-right: 4px;" ></input></td>
				</tr>
				<tr>
					<td>Quantity </td>
					<td><input id="txtQuantity" type="text"  class="decimal-places numberField"></input></td>
					<td>Unit Price</td>
					<td><input id="txtUnitPrice"
						name="txtUnitPrice" disabled="disabled" type="number" step="any"
						class="BoxW116px right-Align decimal-2-places" /></td>
					<td>Wt/Unit </td>
					<td width="10%"><input id="txtWtUnit" type="text" style="width: 80%"  class="decimal-places numberField" ></input></td>
					<td width="25px">Type</td>
					<td>
						<select id="cmbType" class="BoxW48px" style="width:20%">
						   <option value="IN">IN</option>
						   <option value="OUT">OUT</option>
						 </select>
					</td>
				</tr>
				<tr>
				<td>Remark </td>
				<td colspan="3"><input id="txtRemark"  class="longTextBox" /> </td>
				<td colspan="5"><input id="btnAdd" type="button" value="Add" class="smallButton" onclick="return btnAdd_onclick();"></input></td>
				
				</tr>
				</table>

		<div class="dynamicTableContainer" style="height: 300px;">
			<table
				style="height: 28px; border: #0F0; width: 110%; font-size: 11px; font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="5%">Product Code</td>
					<!--  COl1   -->
					<td width="18%">Product Name</td>
					<!--  COl2   -->
					<td width="5%">Product Type</td>
					<!--  COl3   -->
					<td width="2%">UOM</td>
					<!--  COl4   -->
					<td width="6%">Quantity</td>
					<!--  COl5   -->
					<td width="4%">Loose Qty</td>
					<!--  COl6   -->
					<td width="4%">Unit Price</td>
					<!--  COl7   -->
					<td width="4%">Total Price</td>
					<!--  COl8   -->
					<td width="3%">Wt/Unit</td>
					<!--  COl9   -->
					<td width="4%">Total Wt</td>
					<!--  COl10   -->
					<td width="4%">Type</td>
					<!--  COl11   -->
					<td width="10%">Remark</td>
					<!--  COl12   -->
					<td width="2%">Delete</td>
					<!--  COl13   -->
				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 255px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 110%;">
				<table id="tblProduct"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col12-center ">
					<tbody>
					<col style="width: 5%">
					<!--  COl1   -->
					<col style="width: 18%">
					<!--  COl2   -->
					<col style="width: 5%">
					<!--  COl3   -->
					<col style="width: 2.5%">
					<!--  COl4   -->
					<col style="width: 6%">
					<!--  COl5   -->
					<col style="width: 4%">
					<!--  COl6   -->
					<col style="width: 4%">
					<!--  COl7   -->
					<col style="width: 4%">
					<!--  COl8   -->
					<col style="width: 3.5%">
					<!--  COl9   -->
					<col style="width: 3.7%">
					<!--  COl10   -->
					<col style="width: 4%">
					<!--  COl11   -->
					<col style="width: 10%">
					<!--  COl12   -->
					<col style="width: 2%">
					<!--  COl13   -->
					<col style="width: 0%">
					<!--  COl13   -->

					<%-- <c:forEach items="${command.listStkAdjDtl}" var="stkAdj" varStatus="status">
	<tr>
							<td><input name="listStkAdjDtl[${status.index}].strProdCode" value="${stkAdj.strProdCode}" readonly="readonly" class="Box" size="10%"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strProdName" value="${stkAdj.strProdName}" readonly="readonly" class="Box" size="35%"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strProdType" value="${stkAdj.strProdType}" readonly="readonly" class="Box" size="6%"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strUOM" value="${stkAdj.strUOM}" readonly="readonly" class="Box" size="6%"/></td>
							<td><input name="listStkAdjDtl[${status.index}].dblQty" value="${stkAdj.dblQty}" style="text-align: right" type="text" required="required" class="decimal-places inputText-Auto"/></td>
							<td><input name="listStkAdjDtl[${status.index}].dblWeight" value="${stkAdj.dblWeight}" style="text-align: right;" type="text" class="decimal-places inputText-Auto"/></td>
							<td><input name="listStkAdjDtl[${status.index}].dblTotalWt" value="${stkAdj.dblTotalWt}" readonly="readonly" style="text-align: right;" class="Box" size="6%" /></td>
							<c:if test="${stkAdj.strType eq 'IN'}">
							<td><select name="listStkAdjDtl[${status.index}].strType" style="background-color:#a4d7ff;width:100%"><option selected="selected" value="IN">IN</option><option value="OUT">OUT</option></select></td>
							</c:if>
							<c:if test="${stkAdj.strType eq 'OUT'}">
							<td><select name="listStkAdjDtl[${status.index}].strType" style="background-color:#a4d7ff;width:100%"  ><option selected="selected" value="OUT">OUT</option><option value="IN">IN</option></select></td>
							</c:if>							
							<td><input name="listStkAdjDtl[${status.index}].strType" value="${stkAdj.strType}" readonly="readonly"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strRemark" value="${stkAdj.strRemark}" size="25%"/></td>
							<td><input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)"></td>
	</tr>
	</c:forEach>
	 --%>
					</tbody>
				</table>
			</div>
		</div>
		<table class="transTable">
			<tr>
				<td></td>
				<td style="width: 31%"></td>
				<td width="10%">Total Amount</td>
				<td><s:input id="txtTotalAmount" type="text" value=""
						path="dblTotalAmt" readonly="true" class="numberField" style="width: 25%"/></td>
				<td></td>
				<td style="height: 4px;"></td>
			</tr>
			<tr>
				<td><label id="lblAreaNarration">Narration:</label></td>
				<td><s:textarea id="txtAreaNarration" path="strNarration" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>&nbsp; &nbsp; &nbsp;</td>
				<td>&nbsp; &nbsp; &nbsp;</td>

			</tr>
		</table>
		<br>
		<p align="center">
		<input type="submit" value="Submit"
				onclick="return funValidateFields()"
				class="form_button" id="btnStkPost"/>&nbsp; &nbsp; &nbsp;
				 <a STYLE="text-decoration:none"  href="frmStockAdjustment.html?saddr=${urlHits}"> <input id="btnReset" type="button" value="Reset"
				class="form_button"  /></a>
		</p>
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
				<s:input type="hidden" id="hidConversionUOM" path="strConversionUOM"></s:input>
			
	</s:form>
	<script type="text/javascript">
	funApplyNumberValidation();
	</script>
</body>
</html>