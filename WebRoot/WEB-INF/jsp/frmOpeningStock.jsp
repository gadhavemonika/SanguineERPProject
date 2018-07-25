<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Opening Stock</title>
<script type="text/javascript">
	/**
	 * Ready Function for Ajax Waiting and reset form
	 */
	$(document).ready(function(){
		 resetForms('openingStk');
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
		var fieldName,gurl,listRow=0;	
		
		 /**
		 * Load Function for Initialization Text Field with default value 
		 * Set Date in date picker
		 * Getting session value
		 * Success Message after saving records
		 * Open opening stock slip 
		 */
		$(window).load(function()
				{
					$("#txtExpDate").datepicker({ dateFormat: 'dd-mm-yy' });
					$("#txtExpDate" ).datepicker('setDate', 'today');
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
					<%
					if(null!=session.getAttribute("rptOPStkCode"))
						{%>
							code='<%=session.getAttribute("rptOPStkCode").toString()%>';
							<%session.removeAttribute("rptOPStkCode");%>
							var isOk=confirm("Do You Want to Generate Slip?");
							if(isOk)
							{
								window.open(getContextPath()+"/openRptOpeningStockSlip.html?rptOPStkCode="+code,'_blank');
							}
									
						<%}%>
			    });
		 
		/**
		 * Check validation before adding product data in grid
		 */
		function btnAdd_onclick() 
		{			
			
			if(($("#txtProdCode").val().trim().length == 0) )
	        {
				 alert("Please Enter Product Code Or Search");
	             $("#txtProdCode").focus() ; 
	             return false;
	        }
			 
			if($("#txtQuantity").val().trim().length==0 || $("#txtQuantity").val()==0)
			{   alert("Please Enter Quantity");
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
		    var prodName = $("#lblProdName").text();
		    var uom = $("#lblUOM").text();
		    var qty = $("#txtQuantity").val();
		    var ReceivedconversionUOM="";
		    var issuedconversionUOM="";
		    var recipeconversionUOM="";
		    var ConversionValue=0;
		    var Displyqty="";
		    if($('#cmbConversionUOM').val()=="RecUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblRecipeConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				 recipeconversionUOM=ProductData.strRecipeUOM;
			 //  qty=parseFloat(qty)/parseFloat(ConversionValue);
			 
			 if(qty>=1)
					{
					qty= parseFloat(qty)+"";
					var tempQty=qty.split(".");
					var smallQty=parseFloat(qty)-parseFloat(tempQty[0]);
					smallQty=parseFloat(smallQty).toFixed(maxQuantityDecimalPlaceLimit);
					Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+parseFloat(smallQty)*parseFloat(ConversionValue)+" "+recipeconversionUOM;
					}
				else
					{
					Displyqty=Math.round(parseFloat(qty)*parseFloat(ConversionValue))+" "+recipeconversionUOM;
					}
			 
			}
		    if($('#cmbConversionUOM').val()=="IssueUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblIssueConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				qty=parseFloat(qty)/parseFloat(ConversionValue);
// 				var tempQty=qty.split(".");
// 				Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat(+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
				
				if(qty>=1)
					{
					qty= parseFloat(qty)+"";
					var tempQty=qty.split(".");
					var smallQty=parseFloat(qty)-parseFloat(tempQty[0]);
					smallQty=parseFloat(smallQty).toFixed(maxQuantityDecimalPlaceLimit);
					Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+parseFloat(smallQty)*parseFloat(ConversionValue)+" "+issuedconversionUOM;
					}
				else
					{
					Displyqty=Math.round(parseFloat(qty)*parseFloat(ConversionValue))+" "+issuedconversionUOM;
					}
				
				
			}
		    if($('#cmbConversionUOM').val()=="RecipeUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				 ConversionValue=ProductData.dblRecipeConversion;
				 ReceivedconversionUOM=ProductData.strReceivedUOM;
				 issuedconversionUOM=ProductData.strIssueUOM;
				 recipeconversionUOM=ProductData.strRecipeUOM;
				qty=parseFloat(qty)/parseFloat(ConversionValue);
				
				if(qty>=1)
					{
					qty= parseFloat(qty)+"";
					var tempQty=qty.split(".");
					var smallQty=parseFloat(qty)-parseFloat(tempQty[0]);
					smallQty=parseFloat(smallQty).toFixed(maxQuantityDecimalPlaceLimit);
					Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+parseFloat(smallQty)*parseFloat(ConversionValue)+" "+recipeconversionUOM;
					}
				else
					{
					Displyqty=Math.round(parseFloat(qty)*parseFloat(ConversionValue))+" "+recipeconversionUOM;
					}
				
				
				
				    
			}
		   
		    qty=parseFloat(qty).toFixed(maxQuantityDecimalPlaceLimit);
		    var costPUnit = $("#txtCostPUnit").val();
		    costPUnit=parseFloat(costPUnit).toFixed(maxAmountDecimalPlaceLimit);
		    var revLvl = $("#txtRevLevel").val();
		    var lotNo = $("#txtLotNo").val();		    
		    var totalPrice = qty*parseFloat(costPUnit);		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		   
		    var LooseQty=$("#txtQuantity").val();
		    LooseQty=parseFloat(LooseQty).toFixed(maxQuantityDecimalPlaceLimit);
		    rowCount=listRow;
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"8%\" name=\"listOpStkDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"55%\" name=\"listOpStkDtl["+(rowCount)+"].strProdName\" value='"+prodName+"' id=\"txtProdName."+(rowCount)+"\" >";
		    row.insertCell(2).innerHTML= "<input class=\"Box\" type=\"text\" name=\"listOpStkDtl["+(rowCount)+"].strDisplyQty\" size=\"9%\" style=\"text-align: right;\" id=\"txtDisplayQty."+(rowCount)+"\" value='"+Displyqty+"'/>";	
		    row.insertCell(3).innerHTML= "<input class=\"decimal-places inputText-Auto\" type=\"text\" style=\"text-align: right;\" name=\"listOpStkDtl["+(rowCount)+"].dblLooseQty\" id=\"txtLooseQty."+(rowCount)+"\"  value='"+LooseQty+"' onblur=\"funConvertQty(this);\"/>";	
		    row.insertCell(4).innerHTML= "<input class=\"Box\" size=\"4%\" name=\"listOpStkDtl["+(rowCount)+"].strUOM\" id=\"txtUOM."+(rowCount)+"\"  value='"+uom+"'>";
		    row.insertCell(5).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places-amt inputText-Auto\" name=\"listOpStkDtl["+(rowCount)+"].dblCostPUnit\" id=\"txtCostPUnit."+(rowCount)+"\" value='"+costPUnit+"' />";
		    row.insertCell(6).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places-amt inputText-Auto\"  id=\"txtTotalCost."+(rowCount)+"\" value='"+totalPrice+"' />";
		    row.insertCell(7).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto\" name=\"listOpStkDtl["+(rowCount)+"].dblRevLvl\" id=\"txtRevLevel."+(rowCount)+"\" value='"+revLvl+"' />";
		    row.insertCell(8).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto\" name=\"listOpStkDtl["+(rowCount)+"].strLotNo\" id=\"txtLotNo."+(rowCount)+"\" value='"+lotNo+"' >";		    
		    row.insertCell(9).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    row.insertCell(10).innerHTML= "<input type=\"hidden\"  name=\"listOpStkDtl["+(rowCount)+"].dblQty\" id=\"txtQuantity."+(rowCount)+"\" value='"+qty+"' >";	
		   	listRow++;
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
		 * Covert Quantity based on conversion type
		 */
		function funConvertQty(object)
		{
			var index=object.parentNode.parentNode.rowIndex;
			var prodCode=document.getElementById("txtProdCode."+index).value;
			
			var Looseqty=document.getElementById("txtLooseQty."+index).value;
			 var ReceivedconversionUOM="";
			 var issuedconversionUOM="";
			 var recipeconversionUOM="";
			 var ConversionValue=0;
			
			if($('#cmbConversionUOM').val()=="RecUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblReceiveConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				Looseqty=parseFloat(Looseqty)/parseFloat(ConversionValue);
			}
		    if($('#cmbConversionUOM').val()=="IssueUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				ConversionValue=ProductData.dblIssueConversion;
				ReceivedconversionUOM=ProductData.strReceivedUOM;
				issuedconversionUOM=ProductData.strIssueUOM;
				recipeconversionUOM=ProductData.strRecipeUOM;
				Looseqty=parseFloat(Looseqty)/parseFloat(ConversionValue);
			}
		    if($('#cmbConversionUOM').val()=="RecipeUOM")
			{
    			var ProductData=fungetConversionUOM(prodCode);
				 ConversionValue=ProductData.dblRecipeConversion;
				 ReceivedconversionUOM=ProductData.strReceivedUOM;
				 issuedconversionUOM=ProductData.strIssueUOM;
				 recipeconversionUOM=ProductData.strRecipeUOM;
				 Looseqty=parseFloat(Looseqty)/parseFloat(ConversionValue);
			}
		    Looseqty=parseFloat(Looseqty).toFixed(maxQuantityDecimalPlaceLimit);
		    var tempQty=Looseqty.split(".");
		    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
		    document.getElementById("txtDisplayQty."+index).value=Displyqty;
		    document.getElementById("txtQuantity."+index).value=Looseqty;
		}
		
		
		/**
		 * Remove all product from grid
		 */
		function funRemProdRows()
	    {
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			for(var i=rowCount;i>=0;i--)
			{
				table.deleteRow(i);
			}
	    }
		

		/**
		 * Clear textfiled after adding data in textfield
		 */
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    $("#lblProdName").text('');
		    $("#lblUOM").text('');
		    $("#txtQuantity").val('');
		    $("#txtCostPUnit").val('0');
		    $("#txtRevLevel").val('0');
		    $("#txtLotNo").val('0');
		    $("#txtProdCode").focus(); 
		}
	
		
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
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'productInUse':
			    	funSetProduct(code);
			        break;
			    
			    case 'opstock':
			    	funSetOpStk(code);
			        break;
			}
		}
		

		/**
		 * Set opening stockhd data passing value opening stock code
		 */
		function funSetOpStk(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadOpStkHdData.html?opStkCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	funRemoveProductRows();
			    	if(response.strOpStkCode == 'Invalid Code'){
			    		alert('Invalid Code');
				       	$("#txtOpStkCode").val('');
				       	$("#txtOpStkCode").focus();
			    	}else{
			    	$("#txtOpStkCode").val(response.strOpStkCode);
			    	$("#txtExpDate").val(response.dtExpDate);
			    	$("#txtLocCode").val(response.strLocCode);
			    	$("#lblLocName").text(response.strLocName);
			    	$("#cmbConversionUOM").val(response.strConversionUOM);
			    	$("#btnAdd").focus();
			    	
			    	funSetOpStkDtl(code);
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
		 * Set opening stock Dtl data passing value opening stock code
		 */
		function funSetOpStkDtl(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadOpStkDtlData.html?opStkCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	var count=0;
			    	funRemoveProductRows();
			    	$.each(response, function(i,item)
					{			
			    		count=i;
			    		funAddRow1(response[i].strProdCode,response[i].strProdName,response[i].strUOM
			    				,response[i].dblQty,response[i].dblCostPUnit,response[i].dblRevLvl
			    				,response[i].strLotNo,response[i].strDisplyQty,response[i].dblLooseQty);
			    		
			    		
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
		
		/**
		 * Set Location data passing value location code
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
				       		$("#lblLocName").text("");
				       		$("#txtLocCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtLocCode").val(response.strLocCode);
		        		$("#lblLocName").text(response.strLocName);	
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
		 * Set Product data passing value product code
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
			    	$("#txtCostPUnit").val(response.dblCostRM);
			    	//$("#lblUOM").text(response.strUOM); it is General uom
			    	
			    	if($('#cmbConversionUOM').val()=="RecUOM")
	    			{
			    		$("#lblUOM").text(response.strReceivedUOM);
	    			}
			    	if($('#cmbConversionUOM').val()=="IssueUOM")
	    			{
			    		$("#lblUOM").text(response.strIssueUOM);
	    			}
			    	if($('#cmbConversionUOM').val()=="RecipeUOM")
	    			{
			    		$("#lblUOM").text(response.strRecipeUOM);
	    			}
			    	
			    	
			    	
			    	
			    	
			    	
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
		 * Textfield on blur event
		 */
		$(function() {

			$('#txtLocCode').blur(function() {
				var code = $('#txtLocCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){						
					funSetLocation(code);
				}
			});

			$('#txtProdCode').blur(function() {
				var code = $('#txtProdCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){						
					funSetProduct(code);
				}
			});

			$('#txtOpStkCode').blur(function() {
				var code = $('#txtOpStkCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){						
					funSetOpStk(code);
				}
			});
		});
		
		/**
		 * Apply Number validation
		 */
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
		    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit , negative: false});
		}
		
		/**
		 * Open Export/Import form 
		 */
		function funOpenExportImport()			
		{
			var transactionformName="frmOpeningStock";
			var exportUOM= $('#cmbConversionUOM').val();
			var LocCode = $('#txtLocCode').val();
	       // response=window.showModalDialog("frmExcelExportImport.html?formname="+transactionformName+"&exportUOM="+exportUOM,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:500px;dialogTop:100px");
	           response=window.open("frmExcelExportImport.html?formname="+transactionformName+"&exportUOM="+exportUOM+"&strLocCode="+LocCode,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:500px;dialogTop:100px");
	      
	           var timer = setInterval(function ()
	   			    {
	   				if(response.closed)
	   					{
	   						if (response.returnValue != null)
	   						{
	   							response=response.returnValue;
	   							if(null!=response)
	   					        {
	   					        	var count=0;
	   					        	var ReceivedconversionUOM="";
	   					        	var issuedconversionUOM="";
	   					        	var recipeconversionUOM="";
	   					        	var LooseQty=0;
	   						        funRemoveProductRows();
	   						    	$.each(response, function(i,item)
	   								{		
	   						    		count=i;
	   						    		if($('#cmbConversionUOM').val()=="RecUOM")
	   						    			{
	   							    			var ProductData=fungetConversionUOM(response[i].strProdCode);
	   						    				var ConversionValue=ProductData.dblReceiveConversion;
	   						    				
	   						    				ReceivedconversionUOM=ProductData.strReceivedUOM;
	   						    				issuedconversionUOM=ProductData.strIssueUOM;
	   						    				recipeconversionUOM=ProductData.strRecipeUOM;
	   						    				
	   						    				var dblQty=parseFloat(response[i].dblQty)/parseFloat(ConversionValue);
	   						    				dblQty=parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	   						    				
	   						    				var tempQty=dblQty.split(".");
	   						    			    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
	   					    				 
	   						    			    LooseQty=parseFloat(response[i].dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	   					    				    
	   					    					funAddRow1(response[i].strProdCode,response[i].strProdName,response[i].strUOM
	   						    				,dblQty,response[i].dblCostPUnit,response[i].dblRevLvl
	   						    				,response[i].strLotNo,Displyqty,LooseQty);
	   						    			}
	   						    		if($('#cmbConversionUOM').val()=="IssueUOM")
	   					    			{
	   					    				var ProductData=fungetConversionUOM(response[i].strProdCode);
	   					    				var ConversionValue=ProductData.dblIssueConversion;
	   					    				
	   					    				ReceivedconversionUOM=ProductData.strReceivedUOM;
	   					    				issuedconversionUOM=ProductData.strIssueUOM;
	   					    				recipeconversionUOM=ProductData.strRecipeUOM;
	   					    				
	   					    				var dblQty=parseFloat(response[i].dblQty)/parseFloat(ConversionValue);
	   					    				dblQty=parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	   					    				var tempQty=dblQty.split(".");
	   					    			    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
	   				    				    LooseQty=parseFloat(response[i].dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	   				    				    
	   					    					funAddRow1(response[i].strProdCode,response[i].strProdName,response[i].strUOM
	   						    				,dblQty,response[i].dblCostPUnit,response[i].dblRevLvl
	   						    				,response[i].strLotNo,Displyqty,LooseQty);
	   					    			}
	   						    		if($('#cmbConversionUOM').val()=="RecipeUOM")
	   					    			{
	   						    			var ProductData=fungetConversionUOM(response[i].strProdCode);
	   					    				var ConversionValue=ProductData.dblRecipeConversion;
	   					    				
	   					    				ReceivedconversionUOM=ProductData.strReceivedUOM;
	   					    				issuedconversionUOM=ProductData.strIssueUOM;
	   					    				recipeconversionUOM=ProductData.strRecipeUOM;
	   					    				
	   					    				var dblQty=parseFloat(response[i].dblQty)/parseFloat(ConversionValue);
	   					    				dblQty=parseFloat(dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	   					    				var tempQty=dblQty.split(".");
	   					    			    var Displyqty=tempQty[0]+" "+ReceivedconversionUOM+"."+Math.round(parseFloat("0."+tempQty[1])*parseFloat(ConversionValue))+" "+recipeconversionUOM;
	   				    				    LooseQty=parseFloat(response[i].dblQty).toFixed(maxQuantityDecimalPlaceLimit);
	   				    				    
	   					    					funAddRow1(response[i].strProdCode,response[i].strProdName,response[i].strUOM
	   						    				,dblQty,response[i].dblCostPUnit,response[i].dblRevLvl
	   						    				,response[i].strLotNo,Displyqty,LooseQty);
	   					    			}
	   							  	}); 
	   						    	listRow=count+1; 

	   					        }
	   		
	   						}
	   						clearInterval(timer);
	   					}
	   			    }, 500);   
	           
	           
	        
		}
		
		/**
		 * Filling Record in grid
		 */
		function funAddRow1(prodCode,prodName,uom,qty,costPUnit,revLvl,lotNo,Displyqty,LooseQty)
		{		    		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var totalPrice = qty*parseFloat(costPUnit);	
		    qty=parseFloat(qty).toFixed(maxQuantityDecimalPlaceLimit);
		    costPUnit=parseFloat(costPUnit).toFixed(maxAmountDecimalPlaceLimit);
		    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"8%\" name=\"listOpStkDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+prodCode+"'>";
		    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"70%\" name=\"listOpStkDtl["+(rowCount)+"].strProdName\" value='"+prodName+"' id=\"txtProdName."+(rowCount)+"\" >";
		    row.insertCell(2).innerHTML= "<input class=\"Box\" type=\"text\" name=\"listOpStkDtl["+(rowCount)+"].strDisplyQty\" size=\"9%\" style=\"text-align: right;\" id=\"txtDisplayQty."+(rowCount)+"\" value='"+Displyqty+"'/>";	
		    row.insertCell(3).innerHTML= "<input class=\"decimal-places inputText-Auto\" type=\"text\" style=\"text-align: right;\" name=\"listOpStkDtl["+(rowCount)+"].dblLooseQty\"  id=\"txtLooseQty."+(rowCount)+"\" value='"+LooseQty+"' onblur=\"funConvertQty(this);\"/>";	
		    row.insertCell(4).innerHTML= "<input class=\"Box\" name=\"listOpStkDtl["+(rowCount)+"].strUOM\"  id=\"txtUOM."+(rowCount)+"\" value='"+uom+"'>";
		    row.insertCell(5).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places-amt inputText-Auto\" size=\"4%\" name=\"listOpStkDtl["+(rowCount)+"].dblCostPUnit\" id=\"txtCostPUnit."+(rowCount)+"\" value='"+costPUnit+"' >";
		    row.insertCell(6).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places-amt inputText-Auto\"  id=\"txtTotalCost."+(rowCount)+"\" value='"+totalPrice+"' />";
		    row.insertCell(7).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto\" name=\"listOpStkDtl["+(rowCount)+"].dblRevLvl\" id=\"txtRevLevel."+(rowCount)+"\" value='"+revLvl+"'>";
		    row.insertCell(8).innerHTML= "<input type=\"number\" step=\"any\" required = \"required\" style=\"text-align: right;\" class=\"inputText-Auto\" name=\"listOpStkDtl["+(rowCount)+"].strLotNo\" id=\"txtLotNo."+(rowCount)+"\" value='"+lotNo+"' >";		    
		    row.insertCell(9).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    row.insertCell(10).innerHTML= "<input type=\"hidden\"  name=\"listOpStkDtl["+(rowCount)+"].dblQty\" id=\"txtQuantity."+(rowCount)+"\" value='"+qty+"' >";		
		    funApplyNumberValidation();
		}
		function btnChar_onclick() 
	    {
	        document.all("tblChar").style.visibility="visible"
	        document.all("tblChar").style.position="absolute"
	        document.all("tblChar").style.top="200px"
	        document.all("tblChar").style.left="30%"
	    }
	    function btnClose1_onclick() 
	    {
	            document.all("tblChar").style.visibility="hidden"
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
	    
	    /**
		 * Checking Validation before submiting the data
		 */
	    function btnSubmit_onclick() 
	    {
	    	var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var flag=true;
		    
		    if($("#txtExpDate").val().trim().length==0)
			{
				alert("Please Enter Date or Select");
				return false;
			}
		    
			if (!fun_isDate($("#txtExpDate").val())) 
			    {
				 alert('Invalid Date');
				 $("#txtExpDate").focus();
				 return false;  
			   }
			
			if(rowCount==0)
			{
				alert("Please Add Product in Grid");
				return false;
			}
			if($("#txtLocCode").val()=='' && $("#txtLocCode").val().trim().length==0)
			{
				alert("Please Enter Location Code Or Search ");
				return false;
			}
			
	    	
	    	if($("#txtOpStkCode").val().trim().length==0)
	    	{
		    	var searchUrl="";
				searchUrl=getContextPath()+"/checkProductData.html";
	    			$.ajax({				
	    				 	type: "POST",
	    				    url: searchUrl,
	    				    data:$("#frmOpeningStock").serialize(),
	    				    async: false,
	    				    context: document.body,
	    				    dataType: "json",
	    				    success: function(response)
	    				    {	
	    				    	if(response!=null && response!="Empty")
	    				    		{
		    							alert("Duplicate Entry For Product Code('"+response[0]+"') is Allready Enterd in Opening Stock Code:-\n"+response[1]);
		    							flag=false;
	    				    		}
	    				    	else
	    				    		{
		    				    		flag=true;
	    				    		}
	    					},
			    			error: function(jqXHR, exception)
			    			{
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
	    			return flag;
	    		}
	    	
	    	
	    }
	    
	    /**
		 * Reset the form
		 */
	    function funResetField()
	    {
	    	location.reload(true); 
			listRow=0;
	    }
	</script>
	
</head>

<body onload="funOnLoad()">
<div id="formHeading">
		<label>Opening Stock</label>
	</div>
	<s:form id="frmOpeningStock" name="frmOpeningStock" method="POST" action="saveOpeningStk.html?saddr=${urlHits}">
		<br>
		<table class="transTable">
			<tr>
				<th align="right"><a onclick="funOpenExportImport()"
					href="javascript:void(0);">Export/Import</a>&nbsp; &nbsp; &nbsp;
					&nbsp;<a id="baseUrl" href="#">Attach Documents</a>&nbsp; &nbsp; 
				</th>
			</tr>
		</table>
			<table class="transTable">
			    <tr>
			    	<td width="110px"><label>OpStk Code</label></td>
			        <td width="150px"><s:input type="text" id="txtOpStkCode" path="strOpStkCode" ondblclick="funHelp('opstock')" cssClass="searchTextBox"/></td>
			        <td width="90px"><label>Expiry Date</label></td>
				    <td colspan="2"><s:input path="dtExpDate" type="text" id="txtExpDate" required="required" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>				    				            
			    </tr>
			  <tr>
			  <td>Conversion UOM </td>
			  <td  colspan="3">
			        	 <s:select id="cmbConversionUOM" Class="BoxW124px" path="strConversionUOM">
						  <option value="RecUOM">Recieved UOM</option>
						  <option value="IssueUOM">Issue UOM </option>
						  <option value="RecipeUOM">Recipe UOM</option>
						</s:select> 		
					</td>	    
			  </tr>
			    <tr>
			    	<td><label>Location Code</label></td>
			        <td><s:input type="text" id="txtLocCode" name="locCode" path="strLocCode" cssClass="searchTextBox" ondblclick="funHelp('locationmaster')" required="true"/> </td>
			        <td colspan="5"><label id="lblLocName" class="namelabel"></label></td>	
			    </tr>
			    <tr></tr>
			</table>
			
			<table class="transTableMiddle">
				
				<tr>
					<td  width="110px"><label>Product Code</label></td>
		        	<td width="150px"><input type="text" id="txtProdCode" ondblclick="funHelp('productInUse')" class="searchTextBox"/></td>
		    		<td width="110px"><label>Product Name</label></td>
		    		<td  width="150px"><label id="lblProdName"  class="namelabel" style="font-size: 12px;"></label><!-- <input id="txtProdName" name="prodName" /> --></td>
		    		<td  width="110px"><label>UOM</label></td>		    		
				    <td><label id="lblUOM"  class="namelabel"></label></td>
				</tr>
				
				<tr>
					<td><label>Qty</label></td>
				    <td><input type="text"  id="txtQuantity" class="decimal-places numberField"/></td>				    
				    <td><label>Cost Per Unit</label></td>
				    <td><input id="txtCostPUnit" type="text"   class="decimal-places-amt numberField"/></td>
				    <td><label>Revision Level</label></td>
				    <td><input id="txtRevLevel" type="text"  value="0" class="numeric numberField"/></td>
				</tr>
				
				<tr>
			    	<td><label>Lot No</label></td>
				    <td><input id="txtLotNo" name="lotNo"  value="0" class="numeric numberField"/></td>	
				    <td colspan="4"><input id="btnAdd" type="button" class="smallButton" value="Add" onclick="return btnAdd_onclick();"></input></td>			    
				</tr>

			</table>
		<div class="dynamicTableContainer" style="height: 325px">
				<table  style="height:25px;border:#0F0;width:100%;font-size:11px;
			font-weight: bold;">

				<tr bgcolor="#72BEFC">
					<td width="5%">Prod Code</td>
					<!--  COl1   -->
					<td width="27%">Product Name</td>
					<!--  COl2   -->
					<td width="7%">Qty</td>
					<!--  COl3   -->
					<td width="6%">Loose Qty</td>
					<!--  COl4   -->
					<td width="4%">UOM</td>
					<!--  COl5   -->
					<td width="5%">Cost Per Unit</td>
					<!--  COl6   -->
					<td width="5%">Total cost</td>
					<!--  COl7   -->
					<td width="5%">Revision Level</td>
					<!--  COl8   -->
					<td width="5%">Lot No</td>
					<!--  COl9   -->
					<td width="3%">Delete</td>
					<!--  COl10   -->
				</tr>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 275px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
				<table id="tblProduct"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col9-center">
					<tbody>
					<col style="width: 7.5%">
					<!--  COl1   -->
					<col style="width: 42%">
					<!--  COl2   -->
					<col style="width: 10%">
					<!--  COl3   -->
					<col style="width: 9%">
					<!--  COl4   -->
					<col style="width: 6%">
					<!--  COl5   -->
					<col style="width: 7%">
					<!--  COl6   -->
					<col style="width: 7%">
					<!--  COl7   -->
					<col style="width: 7%">
					<!--  COl8   -->
					<col style="width: 7%">
					<!--  COl9   -->
					<col style="width:5%">
					<!--  COl10   -->
					<col style="width:1%;display:none">
					<!--  COl11   -->
					</tbody>
				</table>
			</div>
		</div>
		<br>
		<p align="center">
			<input id="btnSubmit" type="submit" value="Submit" onclick="return btnSubmit_onclick()"class="form_button" />
			&nbsp; &nbsp; &nbsp;
			 <input id="btnReset" type="button" value="Reset" class="form_button" onclick="funResetField()" />
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