<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GRN</title>
<tab:tabConfig/>
	<script type="text/javascript">
				
		var fieldName;
		var gUOM,gTaxIndicator,gTaxOnGD,gTaxCal,gTaxType;
		var gTaxPer,gTaxAmount;
		
		function funAddRow1(prodCode,prodName,unitPrice,wtunit,qtyrecevd,code,uom) 
		{
			var qtyrecb = qtyrecevd;
		    var dcQty = qtyrecevd;
		    var totalWt = qtyrecevd*wtunit;
		    var totalPrice = qtyrecevd*unitPrice;
		    var finalWeight = 0.00;
		    var finalQty = 0.00;
		    var subTotal = 0.00;
		    var totalTaxAmt = 0.00;
		    		    
		    finalWeight=parseFloat($("#txtTotalWt").val());
		    $("#txtTotalWt").val(parseFloat(totalWt)+parseFloat(finalWeight));
		    
		    finalQty=parseFloat($("#txtTotalQty").val());
		    $("#txtTotalQty").val(parseFloat(qtyrecevd)+parseFloat(finalQty));
		    
		    subTotal=parseFloat($("#txtSubTotal").val());
		    $("#txtSubTotal").val(parseFloat(totalPrice)+parseFloat(subTotal));
		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listGRNDtl["+(rowCount-5)+"].strProdCode\" id=\"txtProdCode."+(rowCount-5)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input name=\"listGRNDtl["+(rowCount-5)+"].strProdName\" value="+prodName+" id=\"lblProdName."+(rowCount-5)+"\" >";
		    row.insertCell(2).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblQtyRbl\" id=\"txtQtyRec."+(rowCount-5)+"\" value="+qtyrecb+">";
		    row.insertCell(3).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblDCQty\" id=\"txtDCQty."+(rowCount-5)+"\" value="+dcQty+">";
		    row.insertCell(4).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblDCWt\" id=\"txtDCWt."+(rowCount-5)+"\" value=0>";
		    row.insertCell(5).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblQty\" id=\"txtQuantity."+(rowCount-5)+"\" value="+qtyrecevd+">";
		    row.insertCell(6).innerHTML= "<input name=\"listGRNDtl["+(rowCount-5)+"].dblWeight\" id=\"txtWtUnit."+(rowCount-5)+"\" value="+wtunit+">";
		    row.insertCell(7).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblTotalWt\" id=\"txtTotalWt."+(rowCount-5)+"\" value="+totalWt+">";
		    row.insertCell(8).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblRejected\" id=\"txtRejected."+(rowCount-5)+"\" value=0>";
		    row.insertCell(9).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].strUOM\" id=\"txtUOM."+(rowCount-5)+"\" value="+uom+">";
		    row.insertCell(10).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblPrice\" id=\"txtUnitPrice."+(rowCount-5)+"\" value="+unitPrice+">";
		    row.insertCell(11).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblDiscount\" id=\"txtDiscount."+(rowCount-5)+"\" value=0>";		    
		    row.insertCell(12).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblPackForw\" id=\"txtPack."+(rowCount-5)+"\" value=0>";
		    row.insertCell(13).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblTotalPrice\" id=\"txtTotalPrice."+(rowCount-5)+"\" value="+totalPrice+">";
		    row.insertCell(14).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].strRemarks\" id=\"txtRemark."+(rowCount-5)+"\" value=''>";
		    row.insertCell(15).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblPOWeight\" id=\"txtPOWt."+(rowCount-5)+"\" value=0>";
		    row.insertCell(16).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].strCode\" id=\"txtCode."+(rowCount-5)+"\" value="+code+">";
		    row.insertCell(17).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblRework\" id=\"txtRework."+(rowCount-5)+"\" value=0>";		    
		    row.insertCell(18).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    row.insertCell(19).innerHTML= "<input type=\"hidden\" id=\"txtTempTax."+(rowCount-5)+"\" value="+gTaxAmount+">";
		    funResetProductFields();
		    
		    return false;
		}
		
		
		function funAddRow() 
		{
			if(!funCheckNull($("#txtQuantity").val(),"Quantity"))
			{
				$("#txtQuantity").focus();
				return false;
			}
			else
			{
				if(!funValidateNumeric($("#txtQuantity").val()))
				{
					$("#txtQuantity").focus();
					return false;
				}
			}
			
		    var prodCode = $("#txtProdCode").val();
		    var prodName = document.getElementById("lblProdName").innerHTML;
		    var unitPrice = $("#txtUnitPrice").val();
		    var wtunit = $("#txtWtUnit").val();
		    var qtyrecevd = $("#txtQuantity").val();
		    var qtyrecb = $("#txtQtyRec").val();
		    var dcWt = $("#txtDCWt").val();
		    var dcQty = $("#txtDCQty").val();
		    var rejected = $("#txtRejected").val();
		    var poWt = $("#txtPOWt").val();
		    var rework = $("#txtRework").val();
		    var packforward = $("#txtPack").val();
		    var remarks = $("#txtRemark").val();
		    var disc = $("#txtDiscount").val();
		    var code = "";
		    var totalWt = qtyrecevd*wtunit;
		    var totalPrice = qtyrecevd*unitPrice;
		   
		    var totalTaxAmt = $("#txtTotalTaxAmt").val();
		    if(gTaxOnGD=='Discount')
		    {
				totalPrice=totalPrice-((disc/totalPrice)*100);
		    }
			gTaxAmount=funTaxCalculation(gTaxType,gTaxOnGD,gTaxCal,gTaxPer,parseFloat(totalPrice));		    
		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listGRNDtl["+(rowCount-5)+"].strProdCode\" id=\"txtProdCode."+(rowCount-5)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input name=\"listGRNDtl["+(rowCount-5)+"].strProdName\" value="+prodName+" id=\"lblProdName."+(rowCount-5)+"\" >";
		    row.insertCell(2).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblQtyRbl\" id=\"txtQtyRec."+(rowCount-5)+"\" value="+qtyrecb+">";
		    row.insertCell(3).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblDCQty\" id=\"txtDCQty."+(rowCount-5)+"\" value="+dcQty+">";
		    row.insertCell(4).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblDCWt\" id=\"txtDCWt."+(rowCount-5)+"\" value="+dcWt+">";
		    row.insertCell(5).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblQty\" id=\"txtQuantity."+(rowCount-5)+"\" value="+qtyrecevd+">";
		    row.insertCell(6).innerHTML= "<input name=\"listGRNDtl["+(rowCount-5)+"].dblWeight\" id=\"txtWtUnit."+(rowCount-5)+"\" value="+wtunit+">";
		    row.insertCell(7).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblTotalWt\" id=\"txtTotalWeight."+(rowCount-5)+"\" value="+totalWt+">";
		    row.insertCell(8).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblRejected\" id=\"txtRejected."+(rowCount-5)+"\" value="+rejected+">";
		    row.insertCell(9).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].strUOM\" id=\"txtUOM."+(rowCount-5)+"\" value="+gUOM+">";
		    row.insertCell(10).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblPrice\" id=\"txtUnitPrice."+(rowCount-5)+"\" value="+unitPrice+">";
		    row.insertCell(11).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblDiscount\" id=\"txtDiscount."+(rowCount-5)+"\" value="+disc+">";
		    row.insertCell(12).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblPackForw\" id=\"txtPack."+(rowCount-5)+"\" value="+packforward+">";
		    row.insertCell(13).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblTotalPrice\" id=\"txtTotalPrice."+(rowCount-5)+"\" value="+totalPrice+">";
		    row.insertCell(14).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].strRemarks\" id=\"txtRemark."+(rowCount-5)+"\" value="+remarks+">";
		    row.insertCell(15).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblPOWeight\" id=\"txtPOWt."+(rowCount-5)+"\" value="+poWt+">";
		    row.insertCell(16).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].strCode\" id=\"txtCode."+(rowCount-5)+"\" value="+code+">";
		    row.insertCell(17).innerHTML= "<input type=\"text\" name=\"listGRNDtl["+(rowCount-5)+"].dblRework\" id=\"txtRework."+(rowCount-5)+"\" value="+rework+">";		    
		    row.insertCell(18).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    row.insertCell(19).innerHTML= "<input type=\"hidden\" id=\"txtTempTax."+(rowCount-5)+"\" value="+gTaxAmount+">";
		    funResetProductFields();		    
		    funGetTotal();
		    return false;
		}
		 
		function funGetTotal()
		{
			var totalWeight=0.00;
			var totalPrice=0.00;
			var totalQty=0.00;
			var totalTax=0.00;
			
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			for(var i=0;i<rowCount-5;i++)
			{
				totalQty=parseFloat(document.getElementById("txtQuantity."+i).value)+totalQty;
				totalWeight=parseFloat(document.getElementById("txtTotalWeight."+i).value)+totalWeight;
				totalPrice=parseFloat(document.getElementById("txtTotalPrice."+i).value)+totalPrice;
				totalTax=parseFloat(document.getElementById("txtTempTax."+i).value)+totalTax;
				alert(document.getElementById("txtQuantity."+i).value+"   "+document.getElementById("txtTempTax."+i).value);
			}
			$("#txtSubTotal").val(totalPrice);
			$("#txtTotalWt").val(totalWeight);
			$("#txtTotalQty").val(totalQty);
			$("#txtTotalTaxAmt").val(totalTax);
		}
		
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProduct");
		    table.deleteRow(index);
		    funGetTotal();
		}
		
		function funResetFields()
		{
			$("#txtDiscount").val("0");
			funRemProdRows();
	    }
		
		function funRemProdRows()
	    {
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			for(var i=5;i<rowCount;i++)
			{
				table.deleteRow(5);
			}
	    }
		
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    document.getElementById("lblProdName").innerHTML='';
		    $("#txtUnitPrice").val('');
		    $("#txtWtUnit").val('');
		    $("#txtStock").val('');
		    $("#txtQuantity").val('');
		}
		
		function funResetFields()
		{
			funResetProductFields();
	    }
		
		$(function()
		{
			$("#txtQuantity").blur(function()
			{
				if(!$("#txtQuantity").val()=='')
				{
				}
			});
		});
		
		function funTaxCalculation(taxType,taxOnGD,taxCalc,taxPer,taxableAmount)
		{
			var finalTaxAmount=0.0000;
			if(taxType=='Percent')
		    {
		    	if(taxCalc=='Forward')
		    	{
		    		finalTaxAmount=taxableAmount*(taxPer/100);
		    	}
		    	else
		    	{
		    		finalTaxAmount=taxableAmount*(100/(taxPer+100));
		    		finalTaxAmount=taxableAmount-finalTaxAmount;
		    	}
		    }
		    else
		    {
		    }
			return finalTaxAmount;
		}
		
		$(function() 
		{
			$( "#txtGRNDate" ).datepicker();
			$( "#txtChallanDate" ).datepicker();
			$( "#txtDueDate" ).datepicker();
			$( "#txtRefDate" ).datepicker();
			 
			$("form").submit(function()
			{				
				if($("#txtGRNDate").val()=='')
				{
					alert("Please enter GRN Date");
					return false;
				}
				if($("#txtChallanDate").val()=='')
				{
					alert("Please enter Challan Date");
					return false;
				}
				else if($("#tblProduct tr").length==3)
				{
					alert("Plase Enter Product Details");
					return false;
				}
			});
		});
	
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(fieldName=='productmaster')
			{
				if($("#txtLocCode").val()=='')
				{
					alert("Please Select Location");
				}
				else
				{
			        window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
				}
			}
			else
			{
				window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
			}
	    }
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'grncode':
			    	document.grn.action="grn1.html?GRNCode="+code;
					document.grn.submit();
			        break;
			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'productmaster':
			    	funSetProduct(code);
			        break;
			        
			    case 'suppcode':
			    	funSetSupplier(code);
			        break;
			        
			    case 'POCodeAgainstGRN':
			    	funSetPurchaseOrder(code);
			        break;
			        
			    case 'PRCodeAgainstGRN':
			    	funSetPurchaseReturn(code);
			        break;
			        
			    case 'SRCodeAgainstGRN':
			    	funSetSalesReturn(code);
			        break;
			}
		}
		
		
		function funSetLocation(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtLocCode").val(response.strLocCode);
		        		$("#lblLocName").val(response.strLocName);
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
		}
		
		
		function funSetProduct(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadProductDataWithTax.html?prodCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	$("#txtProdCode").val(response.strProdCode);
		        	document.getElementById("lblProdName").innerHTML=response.strProdName;
		        	$("#txtUnitPrice").val(response.dblUnitPrice);
		        	$("#txtWtUnit").val(response.dblWeight);
		        	$("#txtBinNo").val(response.strBinNo);
		        	gUOM=response.strUOM;
		        	gTaxType=response.strTaxType;
		        	gTaxCal=response.strTaxCalculation;
		        	gTaxPer=response.dblTaxPercentage;
		        	gTaxOnGD=response.strTaxOnGD;
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		    });
		}
		
		
		function funSetSupplier(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				      	$("#txtSuppCode").val(response.strPCode);
				        $("#txtSuppName").val(response.strPName);
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
		}
		
		
		function funSetPurchaseOrder(code)
		{
			var searchUrl=getContextPath()+"/loadAgainstPO.html?POCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemProdRows();
				    	$.each(response, function(i,item)
				    	{
				    		funAddRow1(response[i].strProdCode,response[i].strProdName,response[i].dblPrice,response[i].dblTotalPrice,response[i].dblOrdQty,response[i].strPOCode,response.strUOM);				    		
				    	});
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
		}
		
		
		function funSetPurchaseReturn(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				      	$("#txtSuppCode").val(response.strPCode);
				        $("#txtSuppName").val(response.strPName);
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
		}
		
		
		function funSetSalesReturn(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				      	$("#txtSuppCode").val(response.strPCode);
				        $("#txtSuppName").val(response.strPName);
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
		}
		
		
		function funOnload()
		{
			funOnChange();
			funResetFields();
		}
		
		
		function funOpenAgainst()
		{
			if($("#cmbAgainst").val()=="Purchase Order")
			{
				funHelp("POCodeAgainstGRN");
				fieldName="POCodeAgainstGRN";
			}
			else if($("#cmbAgainst").val()=="Purchase Return")
			{
				funHelp("PRCodeAgainstGRN");
				fieldName="PRCodeAgainstGRN";
			}
			else if($("#cmbAgainst").val()=="Sales Return")
			{
				funHelp("SRCodeAgainstGRN");
				fieldName="SRCodeAgainstGRN";
			}
		}
		
		
		function funOnChange()
		{
			if($("#cmbAgainst").val()=="Direct")
			{
				$("#txtDocCode").css('visibility','hidden');
			}
			else
			{
				$("#txtDocCode").css('visibility','visible');
			}
		}
		
		
		$(function()
		{
			$("#txtGRNCode").blur(function()
			{
				if(!$("#txtGRNCode").val()=='')
				{
					funSetData($("#txtGRNCode").val());
				}					
			});
		    
			$('a#baseUrl').click(function() 
			{
				if($("#txtGRNCode").val()=="")
				{
					alert("Please Select GRN Code");
					return false;
				}
			    $(this).attr('target', '_blank');
			});
		});
		
		
		function funAddFixedAmtTax()
		{
			
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
			
	</script>
</head>

<body onload="funOnLoad()">

	<s:form name="grn" method="POST" action="saveGRN.html">
		
		<table>
			<tr></tr>			
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		    
		    <tr>
		        <td><label id="lblGRNCode" >GRN Code:</label></td>
		        <td><s:input id="txtGRNCode" path="strGRNCode" readonly="true" ondblclick="funHelp('grncode')"/></td>
		        
		        <td><label id="lblGRNNo" >GRN No:</label></td>
		        <td><s:input id="txtGRNNo" path="strGRNNo"/></td>
		        
		        <td><label id="lblGRNDate">GRN Date:</label></td>
		        <td><s:input id="txtGRNDate" path="dtGRNDate"/></td>
		        
		        <td><label id="lblChallanNo">Challan No:</label></td>
		        <td><s:input id="txtChallanNo" path="strBillNo"/></td>
		    </tr>
		    
		    <tr>
		        <td><label id="lblSuppCode" >Supplier:</label></td>
		        <td><s:input id="txtSuppCode" path="strSuppCode" readonly="true" ondblclick="funHelp('suppcode')"/></td>
		        <td><s:input id="txtSuppName" path="strSuppName" readonly="true"/></td>
		        
		        <td><label id="lblChallanDate">Challan Date:</label></td>
		        <td><s:input id="txtChallanDate" path="dtBillDate"/></td>
		        
		        <td><label id="lblDueDate">Due Date:</label></td>
		        <td><s:input id="txtDueDate" path="dtDueDate"/></td>
		    </tr>
		    
		    <tr>
		    	<td>
					<s:select id="cmbAgainst" onchange="funOnChange();" name="cmbAgainst" style="width: 65%" path="strAgainst">
					 	<option value="Direct">Direct</option>
						<option value="Purchase Order">Purchase Order</option>
						<option value="Purchase Return">Purchase Return</option>
						<option value="Sales Return">Sales Return</option>
					</s:select>
				</td>
		        <td><input type="checkbox" id="chkConsPO" >Consolidated PO:<br></td>
		        <td><input id="txtDocCode" readonly="readonly" ondblclick="funOpenAgainst()"/></td>
		    </tr>
		    
		    <tr>
			    <td><label id="lblPayMode" >Payment Mode:</label></td>
		        <td> 
			    	<s:select id="txtPayMode" path="strPayMode">
					 	<option value="Cash">Cash</option>
						<option value="Credit">Credit</option>
					</s:select>
				</td>
			</tr>
			
			<tr>
			    <td><label id="lblInwRefNo">Inward Ref No:</label></td>
		        <td><s:input id="txtInwRefNo" path="strRefNo"/></td>
		    </tr>
		    
		    <tr>
			    <td><label id="lblLocation" >Location:</label></td>
		        <td><s:input id="txtLocCode" path="strLocCode" ondblclick="funHelp('locationmaster')"/></td>
			    <td><s:input id="lblLocName" path="strLocName"/></td>
			    
			    <td><label id="lblRefDate">Inward Ref Date:</label></td>
		        <td><s:input id="txtRefDate" path="dtRefDate"/></td>
			    
			</tr>
		</table>
				
		<table id="tblProduct">
			<tr>
				<td><label>Product Code</label></td>
				<td><input id="txtProdCode" ondblclick="funHelp('productmaster')"></input></td>
				<td><label>Product Name</label></td>
				<td><label id="lblProdName"></label></td>
				<td><label>Unit Price</label></td>
				<td><input id="txtUnitPrice" value="0.00"></input></td>
				<td><label>Wt/Unit</label></td>
				<td><input id="txtWtUnit" value="0.00"></input></td>
				<td><label>Quantity Received</label></td>
				<td><input id="txtQuantity" value="0"></input></td>
				<td><label>Discount</label></td>
				<td><input id="txtDiscount" value="0"></input></td>
			</tr>
			
			<tr>
				<td><label>Rejected</label></td>
				<td><input id="txtRejected" value="0.00"></input></td>
				<td><label>DC/Wt</label></td>
				<td><input id="txtDCWt" value="0.00"></input></td>
				<td><label>DC Qtyt</label></td>
				<td><input id="txtDCQty" value="0.00"></input></td>
				<td><label>Quantity Receiveable</label></td>
				<td><input id="txtQtyRec" value="0.00"></input></td>
			</tr>
			
			<tr>
				<td><label>Bin No</label></td>
				<td><input id="txtBinNo" value=""></input></td>
				<td><label>PO Weight</label></td>
				<td><input id="txtPOWt" value="0.00"></input></td>
				<td><label>Rework</label></td>
				<td><input id="txtRework" value="0.00"></input></td>
				<td><label>Packaging and Forwording</label></td>
				<td><input id="txtPack" value="0.00"></input></td>
				<td><label>Remarks</label></td>
				<td><input id="txtRemark" value=""></input></td>
			</tr>
								
			<tr>
				<td><input id="btnAdd" type="button" value="Add" onclick="return funAddRow()"></input></td>
			</tr>
					
			<tr>
				<th><label>Product Code</label></th>
				<th><label>Product Name</label></th>
				<th><label>Qty Rec'able</label></th>
				<th><label>DC Qty</label></th>
				<th><label>DC Wt</label></th>
				<th><label>Qty Rec'd</label></th>
				<th><label>Wt/Unit</label></th>
				<th><label>Total Wt</label></th>
				<th><label>Rejected</label></th>
				<th><label>UOM</label></th>
				<th><label>Unit Price</label></th>
				<th><label>Discount</label></th>
				<th><label>Packaging and Forwarding</label></th>
				<th><label>Total Price</label></th>
				<th><label>Remarks</label></th>
				<th><label>PO Weight</label></th>
				<th><label>Code</label></th>
				<th><label>Rework</label></th>
				<th><label>Delete</label></th>
			</tr>
		
			<c:forEach items="${command.listGRNDtl}" var="grn" varStatus="status">
				<tr>
					<td><input name="listGRNDtl[${status.index}].strProdCode" value="${grn.strProdCode}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].strProdName" value="${grn.strProdName}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblQtyRbl" value="${grn.dblQtyRbl}"/></td>
					<td><input name="listGRNDtl[${status.index}].dblDCQty" value="${grn.dblDCQty}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblDCWt" value="${grn.dblDCWt}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblQty" value="${grn.dblQty}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblWeight" value="${grn.dblWeight}"/></td>
					<td><input name="listGRNDtl[${status.index}].dblTotalWt" value="${grn.dblTotalWt}"/></td>
					<td><input name="listGRNDtl[${status.index}].strUOM" value="${grn.strUOM}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblUnitPrice" value="${grn.dblUnitPrice}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblDiscount" value="${grn.dblDiscount}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblPackForw" value="${grn.dblPackForw}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblTotalPrice" value="${grn.dblTotalPrice}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].strRemarks" value="${grn.strRemarks}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblPOWeight" value="${grn.dblPOWeight}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].strCode" value="${grn.strCode}" readonly="readonly"/></td>
					<td><input name="listGRNDtl[${status.index}].dblRework" value="${grn.dblRework}" readonly="readonly"/></td>
					<td><input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)"></td>
					
				</tr>
			</c:forEach>
		</table>
			
		<table>
				<tr>
					<td colspan="1"><label id="lblTotalWt">Total Weight</label></td>
				    <td colspan="1"><input id="txtTotalWt" value="0.00" readonly="readonly"/></td>
				    
				    <td colspan="1"><label id="lblTotalQty">Total Quantity</label></td>
				    <td colspan="1"><input id="txtTotalQty" value="0.00" readonly="readonly"/></td>
			    </tr>
		</table>
			
		<br><br>
		
		<table>
				<tr>
					<td colspan="1"><label id="lblSubTotal">Sub Total</label></td>
				    <td colspan="1"><s:input id="txtSubTotal" path="dblSubTotal" readonly="true"/></td>
			    </tr>
			    
			    <tr>
					<td colspan="1"><label id="lblDiscPer">Discount %</label></td>
				    <td colspan="1"><s:input id="txtDiscPer" path="dblDisRate" /></td>
				    <td colspan="1"><label id="lblDiscAmount">Discount Amount</label></td>
				    <td colspan="1"><s:input id="txtDiscAmount" path="dblDisRate"/></td>
			    </tr>
			    
			    <tr>
					<td colspan="1"><label id="lblTaxPer">Tax %</label></td>
				    <td colspan="1"><input id="txtTaxPer"/></td>
				    <td colspan="1"><label id="lblTotalTaxAmt">Total Tax Amount</label></td>
				    <td colspan="1"><s:input id="txtTotalTaxAmt" path="dblTaxAmt"/></td>
			    </tr>
			    
			    <tr>
					<td colspan="1"><label id="lblNarration">Narration</label></td>
				    <td colspan="1"><s:input id="txtNarration" path="strNarration"/></td>
				    <td colspan="1"><label id="lblExtraCharges">Extra Charges</label></td>
				    <td colspan="1"><s:input id="txtExtraCharges" path="dblExtra"/></td>
			    </tr>
			    
			    <tr>
					<td colspan="1"><label id="lblLessAmt">Less Amount</label></td>
				    <td colspan="1"><s:input id="txtLessAmt" path="dblLessAmt"/></td>
				    <td colspan="1"><label id="lblFinalAmt">Final Amount</label></td>
				    <td colspan="1"><s:input id="txtFinalAmt" path="dblTotal" readonly="true"/></td>
			    </tr>
			    
			    <tr>
					<td colspan="1"><label id="lblVehicleNo">Vehicle No</label></td>
				    <td colspan="1"><s:input id="txtVehicleNo" path="strVehNo"/></td>
				    <td colspan="1"><label id="lblTimeIn">Time In</label></td>
				    <td colspan="1"><s:input id="txtTimeIn" path="strTimeInOut"/></td>
			    </tr>
			    
			    <tr>
					<td colspan="1"><label id="lblMInBy">Material Brought In By</label></td>
				    <td colspan="1"><s:input id="txtMInBy" path="strMInBy"/></td>
			    </tr>
		</table>
		
		<br><br>
		
		<table>
				<tr>
				    <td colspan="1"><input id="btnSaveGRN" type="submit" value="Submit" onclick="return funValidateFields()"/></td>
				    <td colspan="1"><input type="reset" value="Reset" onclick="funResetFields()"/></td>
			    </tr>
		</table>
		
		<table id="tblTaxOnAmount">
			<c:forEach items="${command.listFixedAmtTax}" var="tax" varStatus="status">
				<tr>
					<td><input name="listFixedAmtTax[${status.index}].strTaxCode" value="${tax.strTaxCode}" readonly="readonly"/></td>
					<td><input name="listFixedAmtTax[${status.index}].strTaxDesc" value="${tax.strTaxDesc}" readonly="readonly"/></td>
					<td><input name="listFixedAmtTax[${status.index}].dblQtyRbl" value="${tax.dblTaxAmt}"/></td>
					<td><input name="listFixedAmtTaxl[${status.index}].dblDCQty" value="${tax.dblTaxableAmt}"/></td>
					<td><input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)"></td>					  
				</tr>
			</c:forEach>
			<tr>
				<td><input type="button" value = "OK" onClick="Javacsript:funAddFixedAmtTax()"></td>
			</tr>
		</table>		
		
	</s:form>
</body>
</html>