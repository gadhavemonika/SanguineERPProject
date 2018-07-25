<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PHYSICAL STOCK POSTING</title>
<script type="text/javascript">
	/**
	 * Ready Function for Ajax Waiting
	 */
	$(document).ready(function(){
// 		 resetForms('stkPosting');
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
	     * Global Variable
	    **/		
		var fieldName,listRow=0;
		var ReceivedconversionUOM="";
		var issuedconversionUOM="";
		var recipeconversionUOM="";
		var ConversionValue=0;
		
		/**
		 * Check validation before adding product data in grid
		 */
		function btnAdd_onclick() 
		{			
			
			if($("#txtProdCode").val().trim().length ==0)
	        {
				 alert("Please Enter Product Code Or Search");
	             $("#txtProdCode").focus() ; 
	             return false;
	        }
			if($("#txtQuantity").val().trim()=="" || parseInt($("#txtQuantity").val())<0)
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
		    var unitPrice = $("#txtCostRM").val();
		    unitPrice=parseFloat(unitPrice).toFixed(maxAmountDecimalPlaceLimit);
		    var wtunit = $("#txtWtUnit").val();
		    
		    var actualRate = $("#txtActualRate").val();
		    actualRate=parseFloat(actualRate).toFixed(maxAmountDecimalPlaceLimit);
		     
		    wtunit=parseFloat(wtunit).toFixed(maxAmountDecimalPlaceLimit);
		    var currentStkQty = $("#txtStock").val();
		   
		    var phyStkQty = $("#txtQuantity").val();
		    if($('#cmbConversionUOM').val()=="RecUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblReceiveConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				phyStkQty=parseFloat(phyStkQty)/parseFloat(ConversionValue);
			}
		    if($('#cmbConversionUOM').val()=="RecipeUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblRecipeConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				phyStkQty=parseFloat(phyStkQty)/parseFloat(ConversionValue);
			}
		    if($('#cmbConversionUOM').val()=="IssueUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblIssueConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				phyStkQty=parseFloat(phyStkQty)/parseFloat(ConversionValue);
			}
		   
		    var tempphyStkQty=parseFloat(phyStkQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var variance=tempphyStkQty-currentStkQty;
		    variance=parseFloat(variance).toFixed(maxQuantityDecimalPlaceLimit);
		    var adjValue = unitPrice*variance;
		    adjValue=parseFloat(adjValue).toFixed(maxQuantityDecimalPlaceLimit);
		    
		    var actulAdjValue = actualRate*variance;
		    actulAdjValue=parseFloat(actulAdjValue).toFixed(maxQuantityDecimalPlaceLimit);
		    
		    var adjWeight = wtunit*variance;
		    adjWeight=parseFloat(adjWeight).toFixed(maxQuantityDecimalPlaceLimit);		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    
		    var DiscurrentStkQty=$("#txtStock").val();
		    DiscurrentStkQty=parseFloat(DiscurrentStkQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var tempStkQty=DiscurrentStkQty.split(".");
		    DiscurrentStkQty=tempStkQty[0]+" "+ReceivedconversionUOM+"."+parseFloat("0."+tempStkQty[1])*parseFloat(ConversionValue)+" "+recipeconversionUOM;
		    var tempQty=tempphyStkQty.split(".");
		    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
		    var LooseQty=$("#txtQuantity").val();
		    LooseQty=parseFloat(LooseQty).toFixed(maxQuantityDecimalPlaceLimit);
		    
		    var tempvariance=variance.split(".");
		    var DisplayVariance=tempvariance[0]+" "+ReceivedconversionUOM+"."+parseFloat("0."+tempvariance[1])*parseFloat(ConversionValue)+" "+recipeconversionUOM;
		    rowCount=listRow;
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listStkPostDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"100%\" name=\"listStkPostDtl["+(rowCount)+"].strProdName\" id=\"lblProdName."+(rowCount)+"\" value='"+prodName+"'>";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"4%\"  name=\"listStkPostDtl["+(rowCount)+"].dblPrice\" id=\"txtCostRM."+(rowCount)+"\" value="+unitPrice+">";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  style=\"text-align: right;\" size=\"4%\" name=\"listStkPostDtl["+(rowCount)+"].dblWeight\" id=\"txtWtUnit."+(rowCount)+"\"  value="+wtunit+">";
		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"8%\"  id=\"txtDisplayStock."+(rowCount)+"\" value='"+DiscurrentStkQty+"'>";
		   
		    row.insertCell(5).innerHTML= "<input class=\"Box\" type=\"text\" name=\"listStkPostDtl["+(rowCount)+"].strDisplyQty\" size=\"9%\" style=\"text-align: right;\" id=\"txtDisplyQty."+(rowCount)+"\"  value='"+Displyqty+"'/>";	
		    row.insertCell(6).innerHTML= "<input class=\"decimal-places inputText-Auto\" type=\"text\" style=\"text-align: right;\" name=\"listStkPostDtl["+(rowCount)+"].dblLooseQty\" id=\"txtLooseQty."+(rowCount)+"\"  value='"+LooseQty+"'onblur=\"funUpdatePrice(this);\" />";	
		   
		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"8%\" name=\"listStkPostDtl["+(rowCount)+"].strDisplyVariance\" id=\"txtDisplayVariance."+(rowCount)+"\" value='"+DisplayVariance+"'>";	
		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"6%\"  name=\"listStkPostDtl["+(rowCount)+"].dblAdjWt\" id=\"lblAdjWeight."+(rowCount)+"\" value="+adjWeight+">";
		    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"8%\"  name=\"listStkPostDtl["+(rowCount)+"].dblAdjValue\" id=\"lblAdjValue."+(rowCount)+"\"  value="+adjValue+">";	
		    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box totalActualValueCell\" style=\"text-align: right;\" size=\"8%\"  name=\"listStkPostDtl["+(rowCount)+"].dblActualValue\" id=\"lblActualAdjValue."+(rowCount)+"\"  value="+actulAdjValue+">";	
		    row.insertCell(11).innerHTML= "<input type=\"button\" value = \"Delete\" class=\"deletebutton\" onClick=\"Javacsript:funDeleteRow(this)\">";
		    row.insertCell(12).innerHTML= "<input type=\"hidden\"  class=\"decimal-places inputText-Auto\" size=\"6%\" name=\"listStkPostDtl["+(rowCount)+"].dblActualRate\" id=\"txtActualRate."+(rowCount)+"\" value="+actualRate+" >";
		    row.insertCell(13).innerHTML= "<input type=\"hidden\"  class=\"decimal-places inputText-Auto\" size=\"6%\" name=\"listStkPostDtl["+(rowCount)+"].dblPStock\" id=\"txtQuantity."+(rowCount)+"\" value="+phyStkQty+" >";
		    row.insertCell(14).innerHTML= "<input type=\"hidden\" name=\"listStkPostDtl["+(rowCount)+"].dblVariance\" id=\"lblVariance."+(rowCount)+"\" value="+variance+">";	
		    row.insertCell(15).innerHTML= "<input name=\"listStkPostDtl["+(rowCount)+"].dblCStock\" id=\"txtStock."+(rowCount)+"\" value="+currentStkQty+">";
		    listRow++;
		    funApplyNumberValidation();
		    funCalSubTotal();
		    funResetProductFields();
		    
		    $("#txtLocCode").attr("readonly", true); 
		    $("#txtStkPostDate").attr("readonly", true) .datepicker("destroy");
		    $("#hidConversionUOM").val($("#cmbConversionUOM").val());
		    $("#cmbConversionUOM").attr("disabled", true);
		    $("#txtProdCode").focus() ; 
		    return false;
		}
		
		/**
		 * Update total price when user change the qty 
		 */
		function funUpdatePrice(object)
		{
			var index=object.parentNode.parentNode.rowIndex;
			var cStock=document.getElementById("txtStock."+index).value;
			var looseQty=document.getElementById("txtLooseQty."+index).value;
			document.getElementById("txtQuantity."+index).value=looseQty;
			var PhyQty=document.getElementById("txtQuantity."+index).value;
			var unitPrice=document.getElementById("txtCostRM."+index).value;
			
			var actualRate=document.getElementById("txtActualRate."+index).value; 
			actualRate=parseFloat(actualRate).toFixed(maxAmountDecimalPlaceLimit);
			var variance=PhyQty-cStock;
			document.getElementById("lblVariance."+index).value=variance;
			
			var prodCode = document.getElementById("txtProdCode."+index).value;
			 if($('#cmbConversionUOM').val()=="RecUOM")
				{
	    			var ProductData=fungetConversionUOM(prodCode);
					ConversionValue=ProductData.dblReceiveConversion;
					ReceivedconversionUOM=ProductData.strReceivedUOM;
					issuedconversionUOM=ProductData.strIssueUOM;
					recipeconversionUOM=ProductData.strRecipeUOM;
					PhyQty=parseFloat(PhyQty)/parseFloat(ConversionValue);
				}
			    if($('#cmbConversionUOM').val()=="RecipeUOM")
				{
	    			var ProductData=fungetConversionUOM(prodCode);
					ConversionValue=ProductData.dblRecipeConversion;
					ReceivedconversionUOM=ProductData.strReceivedUOM;
					issuedconversionUOM=ProductData.strIssueUOM;
					recipeconversionUOM=ProductData.strRecipeUOM;
					PhyQty=parseFloat(PhyQty)/parseFloat(ConversionValue);
				}
			    if($('#cmbConversionUOM').val()=="IssueUOM")
				{
	    			var ProductData=fungetConversionUOM(prodCode);
					ConversionValue=ProductData.dblIssueConversion;
					ReceivedconversionUOM=ProductData.strReceivedUOM;
					issuedconversionUOM=ProductData.strIssueUOM;
					recipeconversionUOM=ProductData.strRecipeUOM;
					PhyQty=parseFloat(PhyQty)/parseFloat(ConversionValue);
				}
			    PhyQty=parseFloat(PhyQty).toFixed(maxQuantityDecimalPlaceLimit);
			    
			    
			    var adjValue = unitPrice*variance;
				document.getElementById("lblAdjValue."+index).value=parseFloat(adjValue).toFixed(maxQuantityDecimalPlaceLimit);
				
				var actualAdjValue = actualRate*variance;
				document.getElementById("lblActualAdjValue."+index).value=parseFloat(actualAdjValue).toFixed(maxQuantityDecimalPlaceLimit);
			    
				var tempQty=PhyQty.split(".");
				var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
				document.getElementById("txtDisplyQty."+index).value=Displyqty;
				variance=parseFloat(variance).toFixed(maxQuantityDecimalPlaceLimit);
				var tempvariance=variance.split(".");
				var DisplayVariance=tempvariance[0]+" "+ReceivedconversionUOM+"."+parseFloat("0."+tempvariance[1])*parseFloat(ConversionValue)+" "+recipeconversionUOM;
				document.getElementById("txtDisplayVariance."+index).value=DisplayVariance;
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
		}
		
		/**
		 * Ready function
		 * Checking Autorization
		 * Success Message after Submit the Form
		 * Open slip
		 */
		$(document).ready(function(){
			
			var message='';
			var SAmessage='';
			<%if (session.getAttribute("success") != null) {
				            if(session.getAttribute("successMessage") != null){%>
				            message='<%=session.getAttribute("successMessage").toString()%>';
				            alert("Data Save successfully\n\n"+message);
				            <%
				            session.removeAttribute("successMessage");
				            }
				            if(session.getAttribute("successMessageSA") != null){%>
				            SAmessage='<%=session.getAttribute("successMessageSA").toString()%>';
				            alert("Data Save successfully\n\n"+SAmessage);
				            <%
				            session.removeAttribute("successMessageSA");
				            }
							boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
							session.removeAttribute("success");
							if (test) {
							%>	
			<%
			}}%>
			
			var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
			if(flagOpenFromAuthorization == 'true')
			{
				funGetPhyStkPostingData("${authorizationPhyStkCode}");
			}
			
			var code='';
			<%if(null!=session.getAttribute("rptStockPostingCode")){%>
			code='<%=session.getAttribute("rptStockPostingCode").toString()%>';
			<%session.removeAttribute("rptStockPostingCode");%>
			var isOk=confirm("Do You Want to Generate Slip?");
			if(isOk){
			window.open(getContextPath()+"/openRptPhysicalStockPostingSlip.html?rptStockPostingCode="+code,'_blank');
			}
			<%}%>
			});
		
		/**
		 * Reset form
		 */
		function funResetFields()
		{
			location.reload(true); 
		} 
		
		/**
		 * Remove all product from grid
		 */
		function funRemProdRows()
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
		 * Reset textfield
		 */
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    document.getElementById("lblProdName").innerHTML='';
		    $("#txtCostRM").val('');
		    $("#txtWtUnit").val('');
		    $("#txtStock").val('');
		    $("#txtQuantity").val('');
		    $("#spStockUOM").text('');
		}
		
		
				
		/**
		 * Checking Validation before submiting the data
		 */
		$(function() 
		{
			$( "#txtStkPostDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
			$( "#txtStkPostDate" ).datepicker('setDate', 'today');
			
			$("form").submit(function()
			{
				var table = document.getElementById("tblProduct");
			    var rowCount = table.rows.length;
			    
			    if (!fun_isDate($("#txtStkPostDate").val())) 
			    {
				 alert('Invalid Date');
				 $("#txtStkPostDate").focus();
				 return false;  
			   }
				if($("#txtStkPostDate").val()=='')
				{
					alert("Please Enter Or Select Date");
					return false;
				}
				else if($("#txtLocCode").val()=='')
				{
					alert("Please Enter Location Or Search");
					return false;
				}
				else if(rowCount==0)
				{
					alert("Please Add Product in Grid");
					return false;
				}   
				
			});
		});
	
		/**
		 * Open Help Form 
		 **/
		 function funHelp1(transactionName,loc)
			{ 
				if ($('#txtLocCode').attr("readonly")!='readonly')
				{
					fieldName=transactionName;
					window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
				}
			}
		 
		 
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(fieldName=='productInUse')
			{
				if($("#txtLocCode").val()=='')
				{
					alert("Please Select Location");
				}
				else
				{
			        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
				}
			}
			else
			{
			//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:800px;dialogLeft:200px;")
				 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:800px;top=500,left=500")
			}
	    }
		
		/**
		 * Set Data after selecting form Help windows
		 */
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'stkpostcode':
					funGetPhyStkPostingData(code);
			        break;
			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'RawProduct':
			    	funSetProduct(code);
			        break;
			   
			}
		}
		
		/**
		 * Get Physical stock Data after selecting form Help windows
		 */
		function funGetPhyStkPostingData(code)
		{
			$("#txtProdCode").focus();
			var searchUrl="";
			searchUrl=getContextPath()+"/frmPhysicalStkPosting1.html?PSCode="+code;	
			//alert(searchUrl);
			$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if(response.strPSCode=='Invalid Code')
			       	{
			       		alert('Invalid Code');
			       		$("#txtStkPostCode").val('');
			       		$("#txtStkPostCode").focus();
			       		return false;
			       	}
			       	else
			       	{
			       		$("#txtStkPostCode").val(response.strPSCode);
			       		$("#txtStkPostDate").val(response.dtPSDate);
			       		$("#txtLocCode").val(response.strLocCode);
			       		$("#txtLocName").text(response.strLocName);
			       		$("#txtTotalAmount").val(response.dblTotalAmt);
			       		$("#txtAreaNarration").val(response.strNarration);
			       		$("#cmbConversionUOM").val(response.strConversionUOM);
			       		
			       		
			       	 	$("#txtLocCode").attr("readonly", true); 
					    $("#txtStkPostDate").attr("readonly", true) .datepicker("destroy");
					    $("#hidConversionUOM").val(response.strConversionUOM);
					    $("#cmbConversionUOM").attr("disabled", true);
			       		
			       	 	/* $("#txtLocCode").attr("disabled", "disabled"); 
					    $("#txtStkPostDate").attr("disabled", "disabled"); 
					    $("#cmbConversionUOM").attr("disabled", "disabled"); */
					    
			       		var saCode = response.strSACode ;
			       		$('#hidSACode').val(saCode);
			       		if(!(saCode.length==0))
			       			{
			       				$("#txtAreaNarration").val("Stock Adjustment Code :" + response.strSACode);
			       			}
			       		
		        		$("#txtProdCode").focus();
		        		funGetProdData1(response.listStkPostDtl);
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
			var count=0;
					funRemProdRows();
					$.each(response, function(i,item)
                    {		//alert(i);
                    	count=i;
						funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].dblPrice,response[i].dblWeight,
						response[i].dblCStock,response[i].dblPStock,response[i].dblVariance,response[i].dblAdjValue,response[i].dblAdjWt,
						response[i].strDisplyQty,response[i].dblLooseQty,response[i].strDisplyVariance,response[i].dblActualRate,response[i].dblActualValue);
                                                   
                    });
				listRow=count+1;
				funCalSubTotal();
		}
		
		/**
		 * Filling Data in grid
		 */
		function funfillProdRow(prodCode,prodName,unitPrice,wtunit,currentStkQty,
				phyStkQty,variance,adjValue,adjWeight,Displyqty,LooseQty,DisplayVariance,dblActualRate,dblActualValue)
		{
			
			if($('#cmbConversionUOM').val()=="RecUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblReceiveConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				
			}
		    if($('#cmbConversionUOM').val()=="RecipeUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblRecipeConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
			}
		    if($('#cmbConversionUOM').val()=="IssueUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblIssueConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
			}
		    currentStkQty=parseFloat(currentStkQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var tempStkQty=currentStkQty.split(".");
		    DiscurrentStkQty=tempStkQty[0]+" "+ReceivedconversionUOM+"."+parseFloat("0."+tempStkQty[1])*parseFloat(ConversionValue)+" "+recipeconversionUOM;
		    var table = document.getElementById("tblProduct");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);		   
			    
			    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listStkPostDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"100%\" name=\"listStkPostDtl["+(rowCount)+"].strProdName\" id=\"lblProdName."+(rowCount)+"\" value='"+prodName+"'>";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"4%\"  name=\"listStkPostDtl["+(rowCount)+"].dblPrice\" id=\"txtCostRM."+(rowCount)+"\" value="+unitPrice+">";
			    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  style=\"text-align: right;\" size=\"4%\" name=\"listStkPostDtl["+(rowCount)+"].dblWeight\" id=\"txtWtUnit."+(rowCount)+"\"  value="+wtunit+">";
			    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"8%\"  id=\"txtDisplayStock."+(rowCount)+"\" value='"+DiscurrentStkQty+"'>";
			    
			    row.insertCell(5).innerHTML= "<input class=\"Box\" type=\"text\" name=\"listStkPostDtl["+(rowCount)+"].strDisplyQty\" size=\"9%\" style=\"text-align: right;\" id=\"txtDisplyQty."+(rowCount)+"\" value='"+Displyqty+"'/>";	
			    row.insertCell(6).innerHTML= "<input class=\"decimal-places inputText-Auto\" type=\"text\" style=\"text-align: right;\" name=\"listStkPostDtl["+(rowCount)+"].dblLooseQty\"  id=\"txtLooseQty."+(rowCount)+"\"  value='"+LooseQty+"'onblur=\"funUpdatePrice(this);\" />";	
			    
			    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  size=\"8%\" name=\"listStkPostDtl["+(rowCount)+"].strDisplyVariance\" id=\"txtDisplayVariance."+(rowCount)+"\" value='"+DisplayVariance+"'>";
			    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"6%\"  name=\"listStkPostDtl["+(rowCount)+"].dblAdjWt\" id=\"lblAdjWeight."+(rowCount)+"\" value="+adjWeight+">";
			    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"8%\"  name=\"listStkPostDtl["+(rowCount)+"].dblAdjValue\" id=\"lblAdjValue."+(rowCount)+"\"  value="+adjValue+">";			    					
			    
			    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box totalActualValueCell\" style=\"text-align: right;\" size=\"8%\"  name=\"listStkPostDtl["+(rowCount)+"].dblActualValue\" id=\"lblActualAdjValue."+(rowCount)+"\"  value="+dblActualValue+">";
			    
			    row.insertCell(11).innerHTML= "<input type=\"button\" value = \"Delete\" class=\"deletebutton\" onClick=\"Javacsript:funDeleteRow(this)\">";
			    
			    row.insertCell(12).innerHTML= "<input type=\"hidden\"  class=\"decimal-places inputText-Auto\" size=\"6%\" name=\"listStkPostDtl["+(rowCount)+"].dblActualRate\" id=\"txtActualRate."+(rowCount)+"\" value="+dblActualRate+" >";
			    row.insertCell(13).innerHTML= "<input type=\"hidden\" required = \"required\"  class=\"decimal-places inputText-Auto\" size=\"6%\" name=\"listStkPostDtl["+(rowCount)+"].dblPStock\" id=\"txtQuantity."+(rowCount)+"\" value="+phyStkQty+" >";
			    row.insertCell(14).innerHTML= "<input type=\"hidden\" name=\"listStkPostDtl["+(rowCount)+"].dblVariance\" id=\"lblVariance."+(rowCount)+"\" value="+variance+">";
			    row.insertCell(15).innerHTML= "<input type=\"hidden\" name=\"listStkPostDtl["+(rowCount)+"].dblCStock\" id=\"txtStock."+(rowCount)+"\" value="+currentStkQty+">";
		}
		
		/**
		 * Set and Get Location Data after selecting form Help windows
		 */
		function funSetLocation(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
			//alert("sdf");
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
		 * Set and Get Product Data after selecting form Help windows
		 */
		function funSetProduct(code)
		{
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
			    	$("#txtProdCode").val(response.strProdCode);
		        	$("#lblProdName").text(response.strProdName);
		        	var dblStock=funGetProductStock(response.strProdCode);
		        	$("#txtStock").val(dblStock);
		        	$("#spStockUOM").text(response.strReceivedUOM);
		        	$("#txtCostRM").val(response.dblCostRM);
		        	$("#txtWtUnit").val(response.dblWeight);
		        	
		        	funLatestGRNProductRate(response.strProdCode,response.dblCostRM);
		        	
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
		 * Get stock for product 
		 */
		function funGetProductStock(strProdCode)
		{
			var searchUrl="";	
			var locCode=$("#txtLocCode").val();
			var dtPhydate=$("#txtStkPostDate").val();
			var dblStock="0";
			searchUrl=getContextPath()+"/getProductStockInUOM.html?prodCode="+strProdCode+"&locCode="+locCode+"&strTransDate="+dtPhydate+"&strUOM=RecUOM";	
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
			return Math.round(dblStock * 100) / 100;
		}
		
		/**
		 * Calculating Subtotal
		 */
		function funCalSubTotal()
	    {
			funApplyNumberValidation();
	        var dblQtyTot=0;		        
	        var subtot=0;
	        var actTotal=0;
	        $('#tblProduct tr').each(function() {
			    var totalvalue = $(this).find(".totalValueCell").val();
			    subtot = parseFloat(subtot + parseFloat(totalvalue));
			    var totalActvalue = $(this).find(".totalActualValueCell").val();
			    actTotal = parseFloat(actTotal + parseFloat(totalActvalue));
			  
			 });	
	        
	        
			$("#txtTotalAmount").val(parseFloat(subtot).toFixed(parseInt(maxAmountDecimalPlaceLimit)));  
			$("#txtTotalActualAmount").val(parseFloat(actTotal).toFixed(parseInt(maxAmountDecimalPlaceLimit))); 
			
	    }	
		
		/**
		 * Ready function 
		 * Attached document Link
		 * Text Field on blur event
		 */
		$(function()
		{
			$('a#baseUrl').click(function() 
			{
				if($("#txtStkPostCode").val().trim()=="")
				{
					alert("Please Select Stock Posting Code");
					return false;
				}

				 window.open('attachDoc.html?transName=frmPhysicalStkPosting.jsp&formName=Physical Stock Posting&code='+$('#txtStkPostCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			$('#txtProdCode').blur(function () {
				var code=$('#txtProdCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){	
					   funSetProduct(code);
				   }
				});
			
			$('#txtLocCode').blur(function () {
				var code=$('#txtLocCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){						  
					   funSetLocation(code);
				   }
				});
			
			$('#txtStkPostCode').blur(function () {
				 var code=$('#txtStkPostCode').val();	
				 if (code.trim().length > 0 && code !="?" && code !="/"){						  				  
					 funGetPhyStkPostingData(code);
				   }
				});
		});
			
		/**
		 * Apply Number Textfield validation
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
		 * Open Export/Import Excel 
		 **/
		function funOpenExportImport()			
		{
			var transactionformName="frmPhysicalStkPosting";
			var locCode=$('#txtLocCode').val();
			
		//	response=window.showModalDialog("frmExcelExportImport.html?formname="+transactionformName+"&strLocCode="+locCode,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:550px;");
			response=window.open("frmExcelExportImport.html?formname="+transactionformName+"&strLocCode="+locCode,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:550px;");
	        
			var timer = setInterval(function ()
				    {
					if(response.closed)
						{
							if (response.returnValue != null)
							{
								if(null!=response)
						        {
									response=response.returnValue;
						        	var count=0;
						        	funResetProductFields();
						        	funRemProdRows();
						        	 $("#txtLocCode").attr("readonly", true); 
						 		    $("#txtStkPostDate").attr("readonly", true) .datepicker("destroy");
						 		    $("#hidConversionUOM").val($("#cmbConversionUOM").val());
						 		    $("#cmbConversionUOM").attr("disabled", true);
						        	var LooseQty=0;
							    	$.each(response, function(i,item)
									{		
							    		count=i;
							    		var dblStock="";
							    		if(response[i].strProdCode!=null)
							    			{
							    				dblStock=funGetProductStock(response[i].strProdCode);
							    			}
							    		
							    		
							    		if($('#cmbConversionUOM').val()=="RecUOM")
							    		{
							    			var ProductData=fungetConversionUOM(response[i].strProdCode);
							    			
							    			ReceivedconversionUOM=ProductData.strReceivedUOM;
						    				issuedconversionUOM=ProductData.strIssueUOM;
						    				recipeconversionUOM=ProductData.strRecipeUOM;
						    				
						    				var ConversionValue=ProductData.dblReceiveConversion;
						    				var dblQty=parseFloat(response[i].dblPStock)/parseFloat(ConversionValue);
						    				dblQty=parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
						    				var tempQty=dblQty.split(".");
						    			    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
						    			    LooseQty=parseFloat(response[i].dblPStock).toFixed(maxQuantityDecimalPlaceLimit);
					 		    			
						    			    funFillFromExcelData(response[i].strProdCode,response[i].strProdName,response[i].dblPrice,
					 		    				response[i].dblWeight,dblStock,dblQty,Displyqty,LooseQty,ReceivedconversionUOM,recipeconversionUOM,ConversionValue,response[i].dblActualRate);
							    		}
							    		if($('#cmbConversionUOM').val()=="IssueUOM")
						    			{
							    			var ProductData=fungetConversionUOM(response[i].strProdCode);

							    			ReceivedconversionUOM=ProductData.strReceivedUOM;
						    				issuedconversionUOM=ProductData.strIssueUOM;
						    				recipeconversionUOM=ProductData.strRecipeUOM;
						    				
						    				var ConversionValue=ProductData.dblIssueConversion;
						    				var dblQty=parseFloat(response[i].dblPStock)/parseFloat(ConversionValue);
						    				dblQty=parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
						    				var tempQty=dblQty.split(".");
						    			    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
						    			    LooseQty=parseFloat(response[i].dblPStock).toFixed(maxQuantityDecimalPlaceLimit);	
						    			   
						    			    funFillFromExcelData(response[i].strProdCode,response[i].strProdName,response[i].dblPrice,
							    				response[i].dblWeight,dblStock,dblQty,Displyqty,LooseQty,ReceivedconversionUOM,recipeconversionUOM,ConversionValue,response[i].dblActualRate);
						    			}
							    		if($('#cmbConversionUOM').val()=="RecipeUOM")
						    			{
							    			var ProductData=fungetConversionUOM(response[i].strProdCode);

							    			ReceivedconversionUOM=ProductData.strReceivedUOM;
						    				issuedconversionUOM=ProductData.strIssueUOM;
						    				recipeconversionUOM=ProductData.strRecipeUOM;
						    				
						    				var ConversionValue=ProductData.dblRecipeConversion;
						    				var dblQty=parseFloat(response[i].dblPStock)/parseFloat(ConversionValue);
						    				dblQty=parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
						    				var tempQty=dblQty.split(".");
						    			    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
						    			    LooseQty=parseFloat(response[i].dblPStock).toFixed(maxQuantityDecimalPlaceLimit);
						    			   
						    			    funFillFromExcelData(response[i].strProdCode,response[i].strProdName,response[i].dblPrice,
							    				response[i].dblWeight,dblStock,dblQty,Displyqty,LooseQty,ReceivedconversionUOM,recipeconversionUOM,ConversionValue,response[i].dblActualRate);
						    			}
								  	}); 
							    	funCalSubTotal();
							    	listRow=count+1;
							    	 
						        }
			
							}
							clearInterval(timer);
						}
				    }, 500);
			
			
			
		}
		/**
		 * Filling Grid
		 */
		function funFillFromExcelData(strProdCode,strProdName,dblCostPUnit,dblWeight,dblStock,dblQty,Displyqty,LooseQty,ReceivedconversionUOM,recipeconversionUOM,ConversionValue, dblActualRate) 
		{	
		    var prodCode = strProdCode;
		    var prodName = strProdName;
		    var unitPrice = dblCostPUnit;
		    unitPrice=parseFloat(unitPrice).toFixed(maxAmountDecimalPlaceLimit);
		    var wtunit = dblWeight;
		    dblActualRate=parseFloat(dblActualRate).toFixed(maxAmountDecimalPlaceLimit);
		    
		     
		    wtunit=parseFloat(wtunit).toFixed(maxAmountDecimalPlaceLimit);
		    var currentStkQty = dblStock;
		    var phyStkQty = dblQty;
		    phyStkQty=parseFloat(phyStkQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var variance=phyStkQty-currentStkQty;
		    variance=parseFloat(variance).toFixed(maxQuantityDecimalPlaceLimit);
		    var adjValue = unitPrice*variance;
		    adjValue=parseFloat(adjValue).toFixed(maxQuantityDecimalPlaceLimit);
		    
		    var actualAdjValue = dblActualRate*variance;
		    actualAdjValue=parseFloat(actualAdjValue).toFixed(maxQuantityDecimalPlaceLimit);
		    
		    var adjWeight = wtunit*variance;
		    adjWeight=parseFloat(adjWeight).toFixed(maxQuantityDecimalPlaceLimit);		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		  
		    var DiscurrentStkQty=dblStock;
		    DiscurrentStkQty=parseFloat(DiscurrentStkQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var tempStkQty=DiscurrentStkQty.split(".");
		    DiscurrentStkQty=tempStkQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempStkQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
		    
		    var tempvariance=variance.split(".");
		    var DisplayVariance=tempvariance[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempvariance[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
		   
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"10%\" name=\"listStkPostDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"100%\" name=\"listStkPostDtl["+(rowCount)+"].strProdName\" id=\"lblProdName."+(rowCount)+"\" value='"+prodName+"'>";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"4%\"  name=\"listStkPostDtl["+(rowCount)+"].dblPrice\" id=\"txtCostRM."+(rowCount)+"\" value="+unitPrice+">";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\"  style=\"text-align: right;\" size=\"5%\" name=\"listStkPostDtl["+(rowCount)+"].dblWeight\" id=\"txtWtUnit."+(rowCount)+"\"  value="+wtunit+">";
		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"8%\"   id=\"txtDisplayStock."+(rowCount)+"\" value='"+DiscurrentStkQty+"'>";
		    
		    row.insertCell(5).innerHTML= "<input class=\"Box\" type=\"text\" name=\"listStkPostDtl["+(rowCount)+"].strDisplyQty\" size=\"9%\" style=\"text-align: right;\" id=\"txtDisplyQty."+(rowCount)+"\" value='"+Displyqty+"'/>";	
		    row.insertCell(6).innerHTML= "<input class=\"decimal-places inputText-Auto\" type=\"text\" style=\"text-align: right;\" name=\"listStkPostDtl["+(rowCount)+"].dblLooseQty\" id=\"txtLooseQty."+(rowCount)+"\"  value='"+LooseQty+"'onblur=\"funUpdatePrice(this);\" />";	
		   
		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"7%\" name=\"listStkPostDtl["+(rowCount)+"].strDisplyVariance\"  id=\"txtDisplayVariance."+(rowCount)+"\" value='"+DisplayVariance+"'>";	
		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" class=\"Box\" style=\"text-align: right;\" size=\"6%\"  name=\"listStkPostDtl["+(rowCount)+"].dblAdjWt\" id=\"lblAdjWeight."+(rowCount)+"\" value="+adjWeight+">";
		    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" class=\"Box totalValueCell\" style=\"text-align: right;\" size=\"8%\"  name=\"listStkPostDtl["+(rowCount)+"].dblAdjValue\" id=\"lblAdjValue."+(rowCount)+"\"  value="+adjValue+">";	
		    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" class=\"Box totalActualValueCell\" style=\"text-align: right;\" size=\"8%\"  name=\"listStkPostDtl["+(rowCount)+"].dblActualValue\" id=\"lblActualAdjValue."+(rowCount)+"\"  value="+actualAdjValue+">";
		    row.insertCell(11).innerHTML= "<input type=\"button\" value = \"Delete\" class=\"deletebutton\" onClick=\"Javacsript:funDeleteRow(this)\">";
		    row.insertCell(12).innerHTML= "<input type=\"hidden\"  class=\"decimal-places inputText-Auto\" size=\"6%\" name=\"listStkPostDtl["+(rowCount)+"].dblActualRate\" id=\"txtActualRate."+(rowCount)+"\" value="+dblActualRate+" >";
		    row.insertCell(13).innerHTML= "<input type=\"hidden\" required = \"required\"  class=\"decimal-places inputText-Auto\" size=\"6%\" name=\"listStkPostDtl["+(rowCount)+"].dblPStock\" id=\"txtQuantity."+(rowCount)+"\" value="+phyStkQty+" >";
		    row.insertCell(14).innerHTML= "<input type=\"hidden\" name=\"listStkPostDtl["+(rowCount)+"].dblVariance\" id=\"lblVariance."+(rowCount)+"\" value="+variance+">";	
		    row.insertCell(15).innerHTML= "<input type=\"hidden\" name=\"listStkPostDtl["+(rowCount)+"].dblCStock\" id=\"txtStock."+(rowCount)+"\" value="+currentStkQty+">";
		    funApplyNumberValidation();
		    return false;
		}
		

	    /**
		 * Get conversion Ratio form product master
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
				return ProductData;
			}
	    
	/*     function funLatestGRNProductRate(code,dblCostRM)
	    {
	    	var searchUrl="";
			searchUrl=getContextPath()+"/loadGRNProductRate.html?prodCode="+code;
			$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if('Invalid Code ' == response[0]){
			    	
			    		$("#txtActualRate").val(dblCostRM);
			    	}else{
			    		
			    		$("#txtActualRate").val(response[1]);
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
	     */
	    function funLatestGRNProductRate(code,dblCostRM)
		{
	    	 var searchUrl=getContextPath()+"/loadGRNProductRate.html?prodCode="+code;	
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    async: false,
				    success: function(response)
				    {
				    	if('Invalid Code ' == response[0]){
				    	
				    		$("#txtActualRate").val(dblCostRM);
				    	}else{
				    		
				    		$("#txtActualRate").val(response[1]);
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
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	</script>
	
</head>

<body onload="funOnload();">
<div id="formHeading">
		<label>Physical Stock Posting</label>
	</div>
	<s:form name="stkPosting" method="POST" action="savePhyStkPosting.html?saddr=${urlHits}">	
	
	<s:input type="hidden" id="hidSACode" path="strSACode"></s:input>	
	<s:input type="hidden" id="hidConversionUOM" path="strConversionUOM"></s:input>
	<br/>
		<table class="transTable">
			<tr>
				<th align="right"><a onclick="funOpenExportImport()"
					href="javascript:void(0);">Export/Import</a>&nbsp; &nbsp; &nbsp;
					&nbsp;<a id="baseUrl" href="#"> Attach Documents</a>&nbsp; &nbsp;
					&nbsp; &nbsp;</th>
			</tr>
		</table>
		<table class="transTable">

			<tr>
				<td width="120px"><label id="lblStkPostCode">Stock
						Posting Code</label></td>
				<td width="150px"><s:input id="txtStkPostCode" path="strPSCode"
						ondblclick="funHelp('stkpostcode')" cssClass="searchTextBox" /></td>
				<td width="120px"><label id="lblStkPostDate">Stock
						Posting Date</label></td>
				<td><s:input id="txtStkPostDate" type="text" path="dtPSDate"
						required="required" pattern="\d{1,2}-\d{1,2}-\d{4}"
						cssClass="calenderTextBox" /></td>

			</tr>
			<tr>
				<td>Conversion UOM</td>
				<td colspan="3"><select id="cmbConversionUOM" Class="BoxW124px" >
						<option value="RecUOM">Recieved UOM</option>
						<option value="IssueUOM">Issue UOM</option>
						<option value="RecipeUOM">Recipe UOM</option>
				</select></td>
			</tr>
			<tr>
				<td><label id="lblLocation">Location</label></td>
				<td><s:input id="txtLocCode" path="strLocCode"
						ondblclick="funHelp1('locationmaster','loc')" value="${locationCode}"
						cssClass="searchTextBox" /></td>
				<td colspan="3"><s:label id="txtLocName" path="strLocName"
						readonly="true">${locationName}</s:label></td>

			</tr>

		</table>
		<table  class="transTableMiddle1">
			  <tr>
			  <td  width="120px"><label>Product Code</label></td>
			  <td width="150px"><input id="txtProdCode" ondblclick="funHelp('RawProduct')" class="searchTextBox"></input></td>
			  <td><label>Product Name</label></td>
			  <td colspan="4"><label id="lblProdName" style="font-size: 12px;"></label></td>
			 </tr>
			 <tr>
			  <td><label>Weighted Avg Price</label></td>
			  <td ><input id="txtCostRM" readonly="readonly"   type="text" class="decimal-places-amt numberField" ></input></td>
			 <td><label>Actual Rate</label></td>
			 <td colspan="4"><input id="txtActualRate" readonly="readonly"   type="text" class="decimal-places-amt numberField" ></input></td>
			 
			 
			  </tr>
			  <tr>
			  <td><label>Stock</label></td>
			  <td><input id="txtStock" readonly="readonly" style="width:50%" class="BoxW116px right-Align"></input>
			  <span id="spStockUOM"></span></td>
			  
			  <!-- <td><label>Stock</label></td>
			  <td><input id="txtStock" readonly="readonly" class="BoxW116px right-Align"></input></td> -->
			  <td><label>Wt/Unit</label></td>
			  <td><input id="txtWtUnit"   type="text"  class="decimal-places numberField" ></input></td>
			  <td><label>Quantity</label></td>
			  <td><input id="txtQuantity"  type="text"  class="decimal-places numberField" ></input></td>
			  </tr>
			  
			 
			  <td><input id="btnAdd" type="button" value="Add"   class="smallButton" onclick="return btnAdd_onclick();"></input></td>
			  </tr>			  
			  </table>
		<div class="dynamicTableContainer" style="height: 300px;">
			<table
				style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Prod Code</td>
					<!--  COl1   -->
					<td width="22%">Product Name</td>
					<!--  COl2   -->
					<td width="4%">Unit Price</td>
					<!--  COl3   -->
					<td width="4%">Wt/Unit</td>
					<!--  COl4   -->
					<td width="6%">Current Stock</td>
					<!--  COl5   -->
					<td width="6%">Qty</td>
					<!--  COl6   -->
					<td width="6%">Loose Qty</td>
					<!--  COl7   -->
					<td width="5%">Variance</td>
					<!--  COl8   -->
					<td width="6%">Adjusted Wt</td>
					<!--  COl9   -->
					<td width="6%">Weighted Adj. Value</td>
					<!--  COl10   -->
					<td width="6%">Actual Value</td>
					<!--  COl11   -->
					<td width="5%">Delete</td>
					<!--  COl12   -->
				</tr>
			</table>

			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">


				<table id="tblProduct"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width: 8%">
					<!--  COl1   -->
					<col style="width: 30%">
					<!--  COl2   -->
					<col style="width: 5.5%">
					<!--  COl3   -->
					<col style="width: 5.5%">
					<!--  COl4   -->
					<col style="width: 8%">
					<!--  COl5   -->
					<col style="width: 8%">
					<!--  COl6   -->
					<col style="width: 8%">
					<!--  COl7   -->
					<col style="width: 7%">
					<!--  COl8   -->
					<col style="width: 8%">
					<!--  COl9   -->
					<col style="width: 8%">
					<!--  COl10   -->
					<col style="width: 8%">
					<!--  COl11   -->
					<col style="width: 5%">
					<!--  COl12   -->
					<col style="width:1%;display:none">
					<!--  COl13   -->
					<col style="width:1%;display:none">
					<!--  COl14   -->
					<col style="width:1%;display:none">
					<!--  COl15  -->

					<c:forEach items="${command.listStkPostDtl}" var="stkPost"
						varStatus="status">
						<tr>
							<td><input readonly="readonly" class="Box" size="10%"
								name="listStkPostDtl[${status.index}].strProdCode"
								value="${stkPost.strProdCode}" /></td>
							<td><input readonly="readonly" class="Box" size="100%"
								name="listStkPostDtl[${status.index}].strProdName"
								value="${stkPost.strProdName}" /></td>
							<td><input readonly="readonly" class="Box"
								style="text-align: right;" size="6%"
								name="listStkPostDtl[${status.index}].dblPrice"
								value="${stkPost.dblPrice}" /></td>
							<td><input readonly="readonly" class="Box"
								style="text-align: right;" size="6%"
								name="listStkPostDtl[${status.index}].dblWeight"
								value="${stkPost.dblWeight}" /></td>
							<td><input readonly="readonly" class="Box"
								style="text-align: right;" size="6%"
								name="listStkPostDtl[${status.index}].dblCStock"
								value="${stkPost.dblCStock}" /></td>
							<td><input type="text" required="required"
								class="decimal-places inputText-Auto" size="6%"
								name="listStkPostDtl[${status.index}].dblPStock"
								value="${stkPost.dblPStock}" /></td>
							<td><input readonly="readonly" class="Box"
								style="text-align: right;" size="6%"
								name="listStkPostDtl[${status.index}].dblVariance"
								value="${stkPost.dblVariance}" /></td>
							<td><input readonly="readonly" class="Box"
								style="text-align: right;" size="6%"
								name="listStkPostDtl[${status.index}].dblAdjValue"
								value="${stkPost.dblAdjWt}" /></td>
							<td><input readonly="readonly" class="Box"
								style="text-align: right;" size="6%"
								name="listStkPostDtl[${status.index}].dblAdjWt"
								value="${stkPost.dblAdjValue}" /></td>
							<td><input type="button" value="Delete" class="deletebutton"
								onClick="Javacsript:funDeleteRow(this)"></td>

						</tr>
					</c:forEach>

					</tbody>

				</table>
			</div>
		</div>

		<table class="transTable">
			<tr>
				<td></td>
				<td  width="80%"></td>
				<td width="10%">Total Amount</td>
				<td><s:input id="txtTotalAmount" type="text" value=""
						path="dblTotalAmt" readonly="true" class="numberField" /></td>
				<td></td>
				<td style="width: 6%"></td>
			</tr>
			<tr>
				<td></td>
				<td  width="80%"></td>
				<td width="10%">Total Actual Amount</td>
				<td><input id="txtTotalActualAmount" type="text" value="0.0"
						 readonly="readonly" class="numberField" /></td>
				<td></td>
				<td style="width: 6%"></td>
			</tr>
			<tr>
				<td><label id="lblAreaNarration">Narration:</label></td>
				<td><s:textarea id="txtAreaNarration" path="strNarration" Style="width: 400px " /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<br><br>		
		<p align="center">
			<input id="btnStkPost" type="submit" value="Submit"  class="form_button"/>
				 <a STYLE="text-decoration:none"  href="frmPhysicalStkPosting.html?saddr=${urlHits}"> <input type="button"
				value="Reset" class="form_button"/></a>
		</p>
		
			
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	<script type="text/javascript">
	funApplyNumberValidation();
	</script>
</body>
</html>