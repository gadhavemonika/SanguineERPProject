<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Indent</title>

<script type="text/javascript">
/**
 * Ready Function for Ajax Waiting and reset form
 */
$(document).ready(function(){
	 resetForms('PIForm');
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
		var fieldName,productName,listRow=0;
		
		 /**
		 * Ready Function for Initialization Text Field with default value 
		 */
		 $(document).ready(function() 
			{
					  $('#dtPIDate').datepicker({ dateFormat: 'dd-mm-yy' });
					  $('#dtPIDate').datepicker('setDate', 'today');
					  var dates = $("#txtReqDate").datepicker({ 
					    	minDate: "0",
					    	maxDate: "+2Y", 
					    	defaultDate: "+1w", 
					    	dateFormat: 'dd-mm-yy', 
					    	numberOfMonths: 1, 
					    	onSelect: function(date) 
					    	{
					    		for(var i = 0; i < dates.length; ++i) 
					    		{	 
					    			if(dates[i].id < this.id)
					    				$(dates[i]).datepicker('option', 'maxDate', date);
					    			else if(dates[i].id > this.id) $(dates[i]).datepicker('option', 'minDate', date); 
					    			}
					    		}
					       });
			    		$("#txtReqDate" ).datepicker('setDate', 'today');
					 
			});
			
		 /**
			 * Apply date format date picker 
			 */
			function funApplyDatePicker()
		 		{
		 			$(".datePicker").datepicker({ dateFormat: 'dd-mm-yy' });
		 		}
		 		
			/**
			 * Open help windows
			 */
		function funHelp(transactionName)	
		{
			fieldName=transactionName;
			if(fieldName=="productInUse")
				{
					if($("#strLocCode").val()=="")
		    		{
						alert("Please Select Location");
		    			$("#strLocCode").focus();
		    			return false;
		    		}
					else
					{
						
					//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
						 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
					}
				}
			else
				{
					
				//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:800px;dialogLeft:400px;")
					 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:800px;dialogLeft:400px;")
				}
		}
		
		
		/**
		 * Filling Record in grid
		 */
		function funfillProdRow(strProdCode,strProdName,dblOrdQty,strPurpose,dtReqDate,dblAvailStock,Minimumlevel,strAgainst,dblUnitPrice,dblAmount)
		{	
			dblOrdQty=parseFloat(dblOrdQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    if(strAgainst==null)
		    	{
		    	strAgainst="";
		    	}
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"55%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />";
		    row.insertCell(2).innerHTML= "<input type=\"text\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblQty\" id=\"txtProdQty."+(rowCount)+"\" value="+dblOrdQty+" onblur=\"Javacsript:funUpdatePrice(this)\">";
		    row.insertCell(3).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto Box\"  name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblRate\" id=\"txtRate."+(rowCount)+"\" value='"+parseFloat(dblUnitPrice).toFixed(maxAmountDecimalPlaceLimit)+"'/>";
		    row.insertCell(4).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto Box totalValueCell\"  name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblAmount\" id=\"txtAmount."+(rowCount)+"\" value='"+parseFloat(dblAmount).toFixed(maxAmountDecimalPlaceLimit)+"'/>";
		    row.insertCell(5).innerHTML= "<input size=\"15%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strPurpose\" id=\"strPurpose."+(rowCount)+"\" value="+strPurpose+" >";
		    row.insertCell(6).innerHTML= "<input type=\"text\" size=\"8%\"  required = \"required\"  class=\"datePicker calenderTextBox\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dtReqDate\" id=\"dtReqDate."+(rowCount)+"\" value='"+dtReqDate+"' />";		   		    
		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\"  style=\"text-align: right;\" class=\"Box\" size=\"7%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strInStock\" id=\"strInStock."+(rowCount)+"\" value='"+dblAvailStock+"'/>";		    
		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\"  style=\"text-align: right;\" class=\"Box\" size=\"4%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblMinLevel\" id=\"dblReOrderQty."+(rowCount)+"\" value='"+Minimumlevel+"' />";
		    row.insertCell(9).innerHTML= "<input size=\"6%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strAgainst\" id=\"strAgainst."+(rowCount)+"\" value='"+strAgainst+"' />";		    
		    row.insertCell(10).innerHTML= '<input size=\"8%\" type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    funApplyDatePicker();
		    funApplyNumberValidation();
		    funCalculateTotal();

		}
		/**
		 * Remove all product from grid
		 */
		function funRemoveProductRows() 
	    {
			 
			 var table = document.getElementById("tblProdDet");
			 var rowCount = table.rows.length;			   
			//alert("rowCount\t"+rowCount);
			for(var i=rowCount-1;i>=0;i--)
			{
				table.deleteRow(i);						
			} 
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
			        
			    case 'PICode':			    	

					  funSetPIData(code);
			        break;
			}
		}
	
		/**
		 * Set Purchase Indent Data
		 */
		function funSetPIData(code)
		{
			var searchUrl=getContextPath()+"/frmPurchaseIndent1.html?PICode="+code;	
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funFillData(response);
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
		 * Set Purchase Indent Hd Data
		 */
		function funFillData(response)
		{
							          				
	    				$("#strPICode").val(response.strPICode);
	    				$("#dtPIDate").val(response.dtPIDate); 
	    				$("#strLocCode").val(response.strLocCode);	    				
	    				$("#strLocName").text(response.strLocName);  	    										        				
	    				$("#txtNarration").val(response.strNarration);
	    				$("#hidDocCode").val(response.strDocCode);  	    										        				
	    				$("#hidDocType").val(response.strAgainst);
	    				if(response.strClosePI=="Yes")
	    					{
	    					$("#cbClosePI").attr("checked",true);
	    					}
	    			
	    				$("#txtProdCode").focus();
	    				funGetProdData(response.clsPurchaseIndentDtlModel);
	    				
	    			
		}
		function funGetProdData(response)
		{		
			//var searchURL=getContextPath()+"/loadPIDtlData.html?PICode="+code+"&strLocCode="+strLocCode;	
			//alert(searchURL);
				     var count=0; 
		        	funRemoveProductRows();
					$.each(response, function(i,item)
	                {
						count=i;
						funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].dblQty,response[i].strPurpose,response[i].dtReqDate,response[i].strInStock,response[i].dblReOrderQty,response[i].strAgainst,response[i].dblRate,response[i].dblAmount);
	                });
					listRow=count+1;
					
				
		}
		/**
		 * Get and Set Product Master Data after selecting Records from Help windows
		 */
		function funSetProduct(code)
		{
			var searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;			
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strProdCode){
				    		alert('Invalid Product Code');
					    	$("#txtProdCode").val('');
					    	$("#spProdName").text('');
					    	$("#txtUnitPrice").val('');
					    	$("#txtProdCode").focus();
				    	}else{
				    	$("#txtProdCode").val(response.strProdCode);
			        	$("#spProdName").text(response.strProdName);
			        	$("#txtUnitPrice").val(response.dblCostRM);
			        	productName=response.strProdName;			        	
			        	$("#txtProdQty").focus();
			        	funGetMinLevel(code);
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
		 * Get and Set Minimum Level of Product
		 */
		function funGetMinLevel(code)
		{
			var strLocCode=$("#strLocCode").val();
			var searchUrl=getContextPath()+"/loadReorderLevel.html?prodCode="+code+"&strLocCode="+strLocCode;	
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtMinlvl").text(response.dblReOrderLevel);
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
		 * Get and Set Location Master Data after selecting Records from Help windows
		 */
		function funSetLocation(code)
		{
			var searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {

				    	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#strLocCode").val('');
				       		$("#strLocName").text("");
				       		$("#strLocCode").focus();
				       	}
				       	else
				       	{
				    	$("#strLocCode").val (response.strLocCode);
			        	$('#strLocName').text(response.strLocName);
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
		 * Check validation before adding product data in grid
		 */
		function btnAdd_onclick() 
		{		   
			if(document.all("txtProdCode").value!="" && $("#spProdName").text().trim()!="")
		    {
				var spPIDate=$("#dtPIDate").val().split('-');
				var spRequiredDate=$("#txtReqDate").val().split('-');
				var PIDate= new Date(spPIDate[2],spPIDate[1]-1,spPIDate[0]);
				var RequiredDate = new Date(spRequiredDate[2],spRequiredDate[1]-1,spRequiredDate[0]);
				if(RequiredDate<PIDate)
				{
				    	alert("Required Date Should Not Be Less Than Purchase Indent Date");
				    	$("#txtReqDate").focus();
						return false;		    	
				}
		    	if(document.all("txtProdQty").value!="" && document.all("txtProdQty").value != 0 )
		        {
		    		var strProdCode=document.all("txtProdCode").value;
// 		    		if(funDuplicateProduct(strProdCode))
		    		if(true)	
		    		{
				      funAddProductRow();				        	
		    		}
		    	}
				else
		        {
		        	alert("Please Enter Quantity");
		        	document.all("txtProdQty").focus();
		            return false;
				}
		   }else
		    {
				alert("Please Enter Product Code Or Search");
		    	document.all("txtProdCode").focus();
		        return false;
			}  
		}
	
	 
		/**
		 * Check duplicate record in grid
		 */
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
		 * Adding Product Data in grid 
		 */
		function funAddProductRow() 
		{
			var strProdCode = $("#txtProdCode").val().trim();
			var strProdName=productName;
		    var dblProdQty = $("#txtProdQty").val();
		    var dblUnitPrice=$("#txtUnitPrice").val();
		    dblProdQty=parseFloat(dblProdQty).toFixed(maxQuantityDecimalPlaceLimit);
		    var dblAmount=parseFloat(dblProdQty)*parseFloat(dblUnitPrice).toFixed(maxQuantityDecimalPlaceLimit);
		    var strPurpose=$("#txtPurpose").val();
		    var dtReqDate=$("#txtReqDate").val();
		    var strInStock=funGetProductStock(strProdCode);
		    
		    var strAgainst='';
		    var Minimumlevel=$("#txtMinlvl").text();
		    
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    rowCount=listRow;
		   // row.insertCell(1).innerHTML= "<input type=\"hidden\" name=\"listMISDtl["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />"+strProdName+"</label>";
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"55%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />";
		    row.insertCell(2).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\"  name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblQty\" id=\"txtProdQty."+(rowCount)+"\" value='"+dblProdQty+"' onblur=\"Javacsript:funUpdatePrice(this)\">";
		    row.insertCell(3).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto Box\"  name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblRate\" id=\"txtRate."+(rowCount)+"\" value='"+parseFloat(dblUnitPrice).toFixed(maxAmountDecimalPlaceLimit)+"'/>";
		    row.insertCell(4).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto Box totalValueCell\"  name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblAmount\" id=\"txtAmount."+(rowCount)+"\" value='"+parseFloat(dblAmount).toFixed(maxAmountDecimalPlaceLimit)+"'/>";
		    row.insertCell(5).innerHTML= "<input size=\"18%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strPurpose\" id=\"strPurpose."+(rowCount)+"\" value='"+strPurpose+"' >";
		    row.insertCell(6).innerHTML= "<input size=\"8%\"  required = \"required\" class=\"datePicker calenderTextBox\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dtReqDate\" id=\"dtReqDate."+(rowCount)+"\" value='"+dtReqDate+"' >";	   
		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\"  style=\"text-align: right;\" class=\"Box\" size=\"7%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strInStock\" id=\"strInStock."+(rowCount)+"\" value='"+strInStock+"' />";		    
		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\"  style=\"text-align: right;\" class=\"Box\" size=\"4%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].dblReOrderQty\" id=\"dblReOrderQty."+(rowCount)+"\"  value='"+Minimumlevel+"'/>";
		    row.insertCell(9).innerHTML= "<input size=\"8%\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strAgainst\" id=\"strAgainst."+(rowCount)+"\" value='"+strAgainst+"'/>";		    
		    row.insertCell(10).innerHTML= '<input size=\"8%\" type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    listRow++;
		    funApplyDatePicker();
		    funApplyNumberValidation();
		    funCalculateTotal();
		    funClearProduct();
		    return false;
		}
		 
		/**
		 * Update total price when user change the qty 
		 */
		function funUpdatePrice(object)
		{
			var index=object.parentNode.parentNode.rowIndex;	   		
			var price=parseFloat(document.getElementById("txtRate."+index).value)*parseFloat(object.value);
			document.getElementById("txtAmount."+index).value=parseFloat(price).toFixed(maxAmountDecimalPlaceLimit);				
			funCalculateTotal();
		}
		/**
		 * Calcutating Total
		 */
		function funCalculateTotal()
		{
			var totalAmount=0.00;
			
			$('#tblProdDet tr').each(function() {
			    var totalvalue = $(this).find(".totalValueCell").val();
			    totalAmount=parseFloat(totalvalue)+totalAmount;
			  
			 });
			
			totalAmount=parseFloat(totalAmount).toFixed(maxAmountDecimalPlaceLimit);		
			$("#txtSubTotal").val(totalAmount);
		}
		/**
		 * Delete a particular record from a grid
		 */
		function funDeleteRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdDet");
		    table.deleteRow(index);
		    funCalculateTotal();
		}
		
		/**
		 * Clear Textfiled after adding data in textfield
		 */
		function funClearProduct()
		{
			$("#txtProdCode").val('');
			$("#spProdName").text('');
			$("#spPosItemCode").text('');
			$("#txtProdQty").val('');
			$("#txtRemarks").val('');
			$("#txtPurpose").val('');
			$("#txtUnitPrice").val('');
			$("#txtMinlvl").text('');
			$("#txtProdCode").focus();
		}
		
		/**
		 * Get product stock passing value product code
		 */
		function funGetProductStock(strProdCode)
		{
			var searchUrl="";	
			var locCode=$("#strLocCode").val();
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
		
		$(function()
		{
			/**
			 * Attached document Link
			 */
			$('a#baseUrl').click(function() 
			{
				if($("#strPICode").val().trim()=="")
				{
					alert("Please Select PI No");
					return false;
				}

				 window.open('attachDoc.html?transName=frmPurchaseIndent.jsp&formName=Purchase Indent&code='+$('#strPICode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			/**
			 *  Ready function for Textfield on blur event
			 */
			$('#strPICode').blur(function () {
				var code=$('#strPICode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){					   
					   funSetPIData(code);
				   }
				});
			
			$('#strLocCode').blur(function () {
				 var code=$('#strLocCode').val();
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
		 *  Open Auto Generate Purchase Indent window
		 */
		function funHelpAutoGeneratedPI()	
		{
		//	response= window.showModalDialog("frmAutoPIHelp.html","","dialogHeight:800px;dialogWidth:700px;dialogLeft:400px;")
			response= window.open("frmAutoPIHelp.html","","dialogHeight:800px;dialogWidth:700px;dialogLeft:400px;")
			
			var timer = setInterval(function ()
				    {
					if(response.closed)
						{
							if (response.returnValue != null)
							{
								funRemoveProductRows();
								var count=0;
								var retValue =response.returnValue;
								dtReqDate=$("#txtReqDate").val();
								$.each(retValue, function(i,item)
					               { 
									count=i;
										funfillProdRow(retValue[i].strProdCode,retValue[i].strProdName,retValue[i].dblQty,"",dtReqDate,retValue[i].strInStock,retValue[i].dblMinLevel,"",retValue[i].dblRate,parseFloat(retValue[i].dblRate)*parseFloat(retValue[i].dblQty));                 
										$('#hidDocCode').val(retValue[i].strDocCode);
										$('#hidDocType').val(retValue[i].strDocType);
					               
					               });
								listRow=count+1;
			
							}
							clearInterval(timer);
						}
				    }, 500);
			
// 			if(response[0].length!=0 )
// 	        {
// 				funRemoveProductRows();
// 				var count=0;
// 				dtReqDate=$("#txtReqDate").val();
// 				$.each(response, function(i,item)
// 	               { 
// 					count=i;
// 						funfillProdRow(response[i].strProdCode,response[i].strProdName,response[i].dblQty,"",dtReqDate,response[i].strInStock,response[i].dblMinLevel,"",response[i].dblRate,parseFloat(response[i].dblRate)*parseFloat(response[i].dblQty));                 
// 				   });
// 				listRow=count+1;
// 	        }
		}
		
		/**
		 * Apply Number field validation
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
		 * Ready Function 
		 * Getting session Value
		 * Success Message after Submit the Form
		 * Open PI Slip
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
		   
		var code='';
		<%if(null!=session.getAttribute("rptPICode")){%>
		code='<%=session.getAttribute("rptPICode").toString()%>';
		<%session.removeAttribute("rptPICode");%>
		var isOk=confirm("Do You Want to Generate Slip?");
		if(isOk){
		window.open(getContextPath()+"/openRptPISlip.html?rptPICode="+code,'_blank');
		}
		
		var isOk = confirm("Do You Want to Send Slip On Mail?");
	    if (isOk) 
	    {
			window.open(getContextPath() + "/sendPIEmail.html?strPICode=" + code);
	    }
	    
		<%}%>
		
		/**
		 * Checking Authorization 
		 */
		var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
		if(flagOpenFromAuthorization == 'true')
		{
			funSetMRHdData("${authorizationPICode}");
		}
			 
    });
	
		/**
		 * Reset field After adding record in grid 
		 */
		function funResetFields()
		{ 
		     	/* funRemoveProductRows();
				$("#strPICode").val("");		
				$("#txtProdCode").val("");		
				$("#spProdName").text("");		
				$("#txtProdQty").val("");		
				$("#txtMinlvl").text("");		
				$("#txtPurpose").val("");
			    $('#dtPIDate').datepicker({ dateFormat: 'dd-mm-yy' });
			    $('#dtPIDate').datepicker('setDate', 'today');
			    $("#txtReqDate").datepicker({ dateFormat: 'dd-mm-yy' });
			   $('#txtReqDate').datepicker('setDate', 'today');	
			   $("#cbClosePI").removeAttr("checked"); */
			   
			   location.reload(true);
			   
		 
	    }
	
	/**
	 * Checking Validation before submiting the data
	 */
	function funCallFormAction(actionName,object) 
	{	
		var table = document.getElementById("tblProdDet");
		var rowCount = table.rows.length;		 
		
		if(!fun_isDate($("#dtPIDate").val()))
		{
			 alert('Invalid Date');
	            $("#dtPIDate").focus();
	            return false;
		}
		if($("#strLocCode").val().trim()=="")
		{
			alert("Please Enter Purchase Indent Code Or Search");
			$("#txtLocBy").focus();
			return false;
		}
		if(rowCount==0)
		{
			alert("Please Add Product in Grid");
			return false;
		}	
		else
		{
			return true;
		
		}
	}
	
	function funGetKeyCode(event,controller) {
	    var key = event.keyCode;
	    
	    if(controller=='Qty' && key==13)
	    {
	    	btnAdd_onclick();
	    }
	}
	

</script>

</head>
<body>
<div id="formHeading">
		<label>Purchase Indent</label>
	</div>
	<s:form method="POST" action="savepurchaseIndent.html?saddr=${urlHits}" name="PIForm" data-ajax="false">
	<br>
		<table class="transTable">
			<tr >
			 <th align="right" colspan="10"> <a onclick="funHelpAutoGeneratedPI();" href="javascript:void(0);">Auto Generate</a>&nbsp; &nbsp;| &nbsp;  &nbsp;
			 <a id="baseUrl" href="#">Attach Documents</a>&nbsp; &nbsp; &nbsp;  &nbsp;</th>			   
		    </tr>
			<tr>
				<td width="110px"><label>PI Code</label> </td>
				<td  width="150px">
					<s:input path="strPIcode" ondblclick="funHelp('PICode')" id="strPICode" cssClass="searchTextBox"/>
				</td>
				
				<td width="100px" align="right"><label>Date</label></td>
			    <td colspan="8"><s:input path="dtPIDate" id="dtPIDate" required="required" cssClass="calenderTextBox" /></td>
			</tr>

			<tr>
				<td>
					<label>Location</label></td><td> 
					<s:input id="strLocCode" path="strLocCode" value="${locationCode}"  required="required"  ondblclick="funHelp('locationmaster')" cssClass="searchTextBox" />
				</td>
				<td colspan="8"><label id="strLocName"  class="namelabel">${locationName} </label></td>
			</tr>
			
			<tr>
				<td><label>Product Code</label></td>
				<td>
					<input id="txtProdCode" name="txtProdCode" ondblclick="funHelp('productInUse')" class="searchTextBox"/>
				</td>
				<!-- <td><label>Product Name</label></td> -->
				<td width="30%"><label id="spProdName"  class="namelabel" style="font-size: 12px;"></label></td>
				<td width="5%">Qty</td>
				<td width="5%"><input id="txtProdQty" style="width: 90%" name="txtProdQty" value="" type="text"  class="decimal-places numberField"  onkeypress="funGetKeyCode(event,'Qty')"/></td>
				<td width="5%">Unit Price</td>
				<td width="10%"><input id="txtUnitPrice" readonly="readonly" style="width: 90%" name="txtUnitPrice" value="" type="text"  class="decimal-places-amt numberField"  /></td>
				<td width="6%"><label>Min Level</label></td>
				<td><label id="txtMinlvl" ></label></td>
			</tr>		
			
			<tr>
				<td><label>Purpose</label></td>
				<td colspan="2"><input id="txtPurpose" class="longTextBox" >
				<td><label>Required Date</label></td>
				<td><input id="txtReqDate" class="calenderTextBox" ></input></td>
				<td colspan="8"><input type="button" id="addbtn" value="Add" class="smallButton" onclick="return btnAdd_onclick()" />
				</td>
			</tr>			
		</table>
<div class="dynamicTableContainer" style="height: 330px">
	<table  style="height:28px;border:#0F0;width:100%;font-size:11px;
			font-weight: bold;">	
		<tr bgcolor="#72BEFC" >
				<td width="4%">Prod Code</td><!--  COl1   -->
				<td width="20%">Prod name</td><!--  COl2   -->
				<td width="3%">Qty</td><!--  COl3   -->
				<td width="3%">Unit Price</td><!--  COl4   -->
				<td width="3%">Amount</td><!--  COl5   -->
				<td width="10%">Purpose</td><!--  COl6   -->
				<td width="7%">Required Date</td><!--  COl7   -->
				<td width="4%">Avail Stock </td><!--  COl8   -->
				<td width="3%">Min level</td><!--  COl9   -->
				<td width="5%">Against</td><!--  COl10   -->		
				<td width="4%">Delete</td><!--  COl11   -->
		
		</tr>
				</table>
				<div style="background-color:  	#a4d7ff;
    border: 1px solid #ccc;
    display: block;
    height: 280px;
    margin: auto;
    overflow-x: hidden;
    overflow-y: scroll;
    width: 100%;">
		<table id="tblProdDet" style="width:100%;border:
#0F0;table-layout:fixed;overflow:scroll" class="transTablex  col11-center ">
	<tbody>    
	<col style="width:4%"><!--  COl1   -->
	<col style="width:20%"><!--  COl2   -->
	<col style="width:3%"><!--  COl3   -->
	<col style="width:3%"><!--  COl4   -->
	<col style="width:4%"><!--  COl5   -->
	<col style="width:10%"><!--  COl6   -->
	<col style="width:7%"><!--  COl7   -->
	<col style="width:4%"><!--  COl8   -->
	<col style="width:3%"><!--  COl9   -->	
	<col style="width:5%"><!--  COl10   -->	
	<col style="width:3%"><!--  COl11  -->
	
	<c:forEach items="${command.listPurchaseIndentDtlModel}" var="pi" varStatus="status">
					<tr>
						<td>
<!-- 						row.insertCell(0).innerHTML= "<input type=\"hidden\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"' />"+"<label>"+strProdCode+"</label>"; -->
<!-- 		   				 row.insertCell(1).innerHTML= "<input type=\"hidden\" name=\"listPurchaseIndentDtlModel["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />"+strProdName+"</label>"; -->
							<input type="hidden" name="listPurchaseIndentDtlModel[${status.index}].strProdCode" value="${pi.strProdCode}" /><label>${pi.strProdCode}</label>
						</td>
						<td>
							<input type="hidden" name="listPurchaseIndentDtlModel[${status.index}].strProdName" value="${pi.strProdName}" /><label>${pi.strProdName}</label>
						</td>
						<td>
							<input name="listPurchaseIndentDtlModel[${status.index}].dblQty" value="${pi.dblQty}" />
						</td>
						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].strPurpose"
							value="${pi.strPurpose}" /></td>
						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].dtReqDate"
							value="${pi.dtReqDate}" /></td>
						<td><input type="hidden"
							name="listPurchaseIndentDtlModel[${status.index}].strInStock"
							value="${pi.strInStock}" /><label>${pi.strInStock}</label></td>

						<td><input type="hidden"
						name="listPurchaseIndentDtlModel[${status.index}].dblMinLevel"
							value="${pi.dblMinLevel}" /><label>${pi.dblMinLevel}</label></td>
						<td><input type="hidden"
							name="listPurchaseIndentDtlModel[${status.index}].strAgainst"
							value="${pi.strAgainst}" /><label>${pi.strAgainst}</label></td>

						<td><input type="Button" value="Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)"/></td>
					</tr>
				</c:forEach>
				
	<tbody>
	</table>
	</div>
				
</div>

		<table class="transTableMiddle">
			<tr>
				<td>Close Purchase Indent</td>
				<td>
					<s:input id="cbClosePI" type="checkbox" value="Yes" path="strClosePI" />
				</td>
				<td width="5%"><label>Narration </label></td>
				<td width="27%"><s:textarea cssStyle="width:100%" path="strNarration" id="txtNarration"/></td>
				
				<td width="6%" align="right">Total</td>
				<td><s:input type="text" id="txtSubTotal"
				path="dblTotal" readonly="true"
				class="decimal-places" /></td>
			</tr>
		</table>
		<br>
		<p align="center">
			<input type="submit" id="formsubmit" value="Submit" class="form_button" onclick="return funCallFormAction('submit',this)" />
			&nbsp; &nbsp; &nbsp; <input type="button" value="Reset"
				class="form_button" onclick="funResetFields();" />
		</p>
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
			<s:input type="hidden" id="hidDocCode" path="strDocCode"></s:input>
			<s:input type="hidden" id="hidDocType" path="strDocType"></s:input>
			
	</s:form>
	<script type="text/javascript">
	funApplyNumberValidation();
	</script>
</body>
</html>