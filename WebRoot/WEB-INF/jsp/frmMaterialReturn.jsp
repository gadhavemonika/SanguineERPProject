<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Material Return</title>
<script type="text/javascript">

/**
 * Global variable
 */
var ProductCodeArray=new Array();
var ProductCodeVar=-1;
var date;
var listRow=0;

	/**
	 * Ready Function for Ajax Waiting
	 */
	$(function()
		{	

   			$("#txtMRDate").datepicker({ dateFormat: 'dd-mm-yy' });		
			$("#txtMRDate" ).datepicker('setDate', 'today');		
			$(document).ajaxStart(function(){
			    $("#wait").css("display","block");
			  });
			  $(document).ajaxComplete(function(){
			    $("#wait").css("display","none");
			  });
			  date=$("#txtMRDate").val();	 
			  
		});
		
		/**
		 * Checking validation before adding the data in grid
		 */
		function btnAdd_onclick() 
		{
			 var strProdCode = $("#txtProdCode").val();
			 var strProdName = $("#txtProdName").text();
			 if(strProdCode.trim().length<=0)
			    {  
			    	alert("Please Enter Product Code Or Search");
			    	$("#txtProdCode").focus();
			    	return false;
			    }	    
				if($("#txtQty").val().trim().length==0 || $("#txtQty").val() == 0)
				{  
					alert("Please Enter Quantity");
					$("#txtQuantity").focus();
					return false;
				}	
			    if(strProdName.length<=0) 
			    {   alert("Please Select Product From  Search");
			        $("#txtProdCode").focus();
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
		 * Checking duplicate record in grid
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
	* Adding record in grid
	*/
	function funAddRow() 
	{
	    var prodCode = $("#txtProdCode").val();
	    var prodName = $("#txtProdName").text();
	    var POSItemCode = $("#txtPOSItemCode").text();
	    var qty = $("#txtQty").val();
	    qty=parseFloat(qty).toFixed(maxQuantityDecimalPlaceLimit);
	    var remarks = $("#txtRemarks").val();	    		    
	    var locationfrom=$('#txtLocFromCode').val();
	    var locationTo=$('#txtLocToCode').val();
	    
	    var table = document.getElementById("tblProduct");
	    var rowCount = table.rows.length;	   
	    var row = table.insertRow(rowCount);
	    rowCount=listRow;
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\"  name=\"listMaterialRetDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+prodCode+"'/>";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"65%\" name=\"listMaterialRetDtl["+(rowCount)+"].strProdName\" value='"+prodName+"'id=\"txtProdName."+(rowCount)+"\" />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"9%\" name=\"listMaterialRetDtl["+(rowCount)+"].strPartNo\" value='"+POSItemCode+"' id=\"txtPOSItemCode."+(rowCount)+"\" />";
	    row.insertCell(3).innerHTML= "<input type=\"text\" class=\"decimal-places inputText-Auto\" name=\"listMaterialRetDtl["+(rowCount)+"].dblQty\" id=\"txtQty."+(rowCount)+"\" value="+qty+" />";	    
	    row.insertCell(4).innerHTML= "<input size=\"32%\" name=\"listMaterialRetDtl["+(rowCount)+"].strRemarks\" id=\"txtRemarks."+(rowCount)+"\" value='"+remarks+"' />";
	    row.insertCell(5).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)"  class="deletebutton">';
	    listRow++;
	    funApplyNumberValidation();
	    funResetProductFields();
	    return false;
	      
	}
	 
	/**
	 * Filling grid for Against
	 */
	function funFillTableForAgainst(prodCode,prodName,POSItemCode,quantity,remarks) 
	{
		var table = document.getElementById("tblProduct");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    if(POSItemCode==null)
	    	{
	    	   POSItemCode="";
	    	}
	    quantity=parseFloat(quantity).toFixed(maxQuantityDecimalPlaceLimit);
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listMaterialRetDtl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+prodCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"65%\" name=\"listMaterialRetDtl["+(rowCount)+"].strProdName\" value='"+prodName+"' id=\"txtProdName."+(rowCount)+"\"  />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"9%\" name=\"listMaterialRetDtl["+(rowCount)+"].strPartNo\" value='"+POSItemCode+"' id=\"txtPOSItemCode."+(rowCount)+"\" />";
	    row.insertCell(3).innerHTML= "<input type=\"text\" class=\"decimal-places inputText-Auto\"  name=\"listMaterialRetDtl["+(rowCount)+"].dblQty\" id=\"txtQty."+(rowCount)+"\" value="+quantity+" />";	    
	    row.insertCell(4).innerHTML= "<input size=\"32%\" type=\"text\" name=\"listMaterialRetDtl["+(rowCount)+"].strRemarks\" id=\"txtRemarks."+(rowCount)+"\" value='"+remarks+"' />";
	    row.insertCell(5).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)" class="deletebutton" >';
	    funApplyNumberValidation();
	}
	
	/**
	 * Delete a particular record form grid
	 */
	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblProduct");
	    table.deleteRow(index);
	}
	
	/**
	 * Remove all rows form grid
	 */
	function funRemProdRows()
    {
		$('#tblProduct tbody > tr').remove();  
    }
	
	function funResetProductFields()
	{
		$("#txtProdCode").val('');
	    $("#txtProdName").text('');
	    $("#txtPOSItemCode").text('');
	    $("#txtStock").text('');
	    $("#txtQty").val('');
	    $("#txtRemarks").val('');
	    $("#txtProdCode").focus();
	}
	
	/**
	 * Open help window
	 */
	var fieldName="";	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		
		// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
		 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
	}	
	
	/**
	 * Set data after selecting record form help window
	 */
	function funSetData(code)
	{
		switch (fieldName) 
		{
		    case 'MaterialReturn':
		    	funSetMRHdData(code);
		    	
		        break;
		        
		    case 'locby':
		    	funSetLocationBy(code);
		        break;
		        
		    case 'locon':
		    	funSetLocationTo(code);
		        break;
		        
		    case 'productInUse':
		    	funSetProduct(code);
		        break;
		        
		    case 'MIS':
		    	funSetMIS(code);
		        break;
		}		
	}
	
	/**
	 * Get and set location by data passing value location code
	 */
		function funSetLocationBy(code)
		{
			var searchUrl="";
			$("#txtLocFromCode").val(code);
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
				       		$("#txtLocFromCode").val('');
				       		$("#txtLocFromName").text("");
				       		$("#txtLocFromCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtLocFromCode").val(response.strLocCode);
				    	$("#txtLocFromName").text(response.strLocName);
				    	$("#txtLocToCode").focus();
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
		 * Get and set Location to data passing value location code
		 */
		function funSetLocationTo(code)
		{
			var searchUrl="";
			$("#txtLocToCode").val(code);
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
				       		$("#txtLocToCode").val('');
				       		$("#txtLocToName").text("");
				       		$("#txtLocToCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtLocToCode").val(response.strLocCode);
				    	$("#txtLocToName").text(response.strLocName);
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
		 * Get and set product data passing value product code
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
				    	$("#txtProdName").text('');
				    	$("#txtProdCode").focus();
			    	}else{
			    		var dblStock=funGetProductStock(response.strProdCode);
			    	$("#txtProdCode").val(response.strProdCode);
		        	$("#txtProdName").text(response.strProdName);
					$("#txtPOSItemCode").text(response.strPartNo);
		        	$("#txtStock").text(dblStock);
		        	$("#txtQty").focus();
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
		 * Get stock for a product passing value product code
		 */
		function funGetProductStock(strProdCode)
		{
			var searchUrl="";	
			var locCode=$("#txtLocFromCode").val();
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
		 * Get and set MIS Data 
		 */
		function funSetMIS(code)
		{			
			searchUrl=getContextPath()+"/loadMISHdData.html?MISCode="+code;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	funRemProdRows();
				    	$("#txtMISCode").val(code);
				    	funGetProdData1(response.clsMISDtlModel);
				    	
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
		 * Fill a MIS product data 
		 */
		function funGetProdData1(response)
		{	
			var count=0;
		    funRemProdRows();
			$.each(response, function(i,item)
			{
				count=i;
			     funFillTableForAgainst(response[i].strProdCode,response[i].strProdName,response[i].strPartNo,response[i].dblQty,response[i].strRemarks);				    		
			});
			listRow=count+1;
	    }
		
		/**
		 * Get and set Material Return Data passing value(Material Return code)
		 */
		function funSetMRHdData(code)
		{			
			searchUrl=getContextPath()+"/loadMRHdData.html?MRCode="+code;
			//alert(searchUrl);
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {		
				    	if(response.strMRetCode=="Invalid Code")
				    		{
				    			alert("Invalid Code");
				    			$("#txtMRCode").val('');
				    			$("#txtMRCode").focus();
				    			return false;
				    		}
				    	else{
						    	$("#txtMRCode").val(response.strMRetCode);
						    	$("#txtMRDate").val(response.dtMRetDate);
						    	$("#txtLocFromCode").val(response.strLocFrom);
						    	$("#txtLocFromName").text(response.strLocFromName);
						    	$("#txtLocToCode").val(response.strLocTo);
						    	$("#txtLocToName").text(response.strLocToName);
						    	$("#txtNarration").val(response.strNarration);
						    	$("#cmbAgainst").val(response.strAgainst);
						    	funOnChange();
						    	$('#txtMISCode').val(response.strMISCode);
						    	$('#txtProdCode').focus();
						    	funSetMRDtlData(code);
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
		 * Get and set Material Return Dtl(Product Data) passing value(Material Return Code)
		 */
		function funSetMRDtlData(code)
		{			
			searchUrl=getContextPath()+"/loadMRDtlData.html?MRCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	var count=0;
				    	funRemProdRows();
				    	$.each(response, function(i,item)
				    	{
				    		count=i;
				        	funFillTableForAgainst(response[i].strProdCode,response[i].strProdName,response[i].strPartNo,response[i].dblQty,response[i].strRemarks);				    		
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
		 * Combo box on change 
		 */
		function funOnChange()
		{
			if($("#cmbAgainst").val()=="Direct")
			{
			 	$('#txtMISCode').css('visibility','hidden');
			 	$("#txtProdCode").attr("disabled", false);
			}
			else if($("#cmbAgainst").val()=="MIS")
			{
			    $('#txtMISCode').css('visibility','visible');
			    $("#txtProdCode").attr("disabled", true);
			}			 
		}
				

		/**
		 * Ready Function for Initialize textField with default value
		 * And Getting session Value
		 * Success Message after Submit the Form
		 */
		$(function() 
		{  
			//alert("dcv");
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
							<%}
							else
							{
							%>	
								alert(message);
							<%}	
							
			}%>
			
			var code='';
			<%if(null!=session.getAttribute("rptMRCode")){%>
			code='<%=session.getAttribute("rptMRCode").toString()%>';
			<%session.removeAttribute("rptMRCode");%>
			var isOk=confirm("Do You Want to Generate Slip?");
			if(isOk){
				//alert("/openRptMISSlip.html?rptMISCode="+code);
				window.open(getContextPath()+"/invokeMaterialReturnDetail.html?docCode="+code,'_blank');
			}
			<%}%>
			
			var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
			if(flagOpenFromAuthorization == 'true')
			{
				funSetMRHdData("${authorizationMatRetCode}");
			}
			
			$("#txtLocFromCode").val("${locationCode}");
	    	$("#txtLocFromName").text("${locationName}");  
	    	$("#txtLocToCode").focus();	
	    	$("#txtProdCode").val('');	
	    	$("#txtProdName").text('');	
	    	$("#txtQty").val('');	
			funOnChange();
		});
		

		/**
		 *set Field name for Open Help
		 */
		function funOpenAgainst()
		{
			if($("#cmbAgainst").val()=="Direct")
			{				
			}
			else
			{
				funHelp("MIS");
				fieldName="MIS";
			}
		}
		


		/**
		 * On blur event in Textfiled
		 */
		$(function()
		{
			$('#txtLocFromCode').blur(function () {
				var code=$('#txtLocFromCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){					   
					   funSetLocationBy(code);
				   }
				});
			
			$('#txtLocToCode').blur(function () {
				var code=$('#txtLocToCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){					   
					   funSetLocationTo(code);
				   }
				});
			
			
			$('#txtProdCode').blur(function () {
				var code=$('#txtProdCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){	
					   funSetProduct(code);
					   
				   }
				});
			
			$('#txtMRCode').blur(function () {
				var code=$('#txtMRCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){	
					   funSetMRHdData(code);
				   }
				});
			$('a#baseUrl').click(function() 
					{
						if($("#txtMRCode").val().trim() == "")
						{
							alert("Please Select Material Return Code");
							return false;
						}
						window.open('attachDoc.html?transName=frmMaterialReturn.jsp&formName=Material Return&code='+$("#txtMRCode").val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
					});
		});
		
		/**
		 * Checking Validation before submiting the data
		 */
		function funCallFormAction(actionName,object) 
		{	
	    
			if(!fun_isDate($("#txtMRDate").val())){
				 alert('Invalid Date');
		            $("#txtMRDate").focus();
		            return false;
			}
			var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
			if($("#txtLocFromCode").val()=='')
			{
				alert("Please Enter Location From or Search");
				$("#txtLocBy").focus();
				return false;
			}
			else if($("#txtLocToCode").val()=='')
			{
				alert("Please Enter Location To or Search");
				$("#txtLocOn").focus();
				return false;
			}
			else if($("#txtLocFromCode").val()==$("#txtLocToCode").val())
			{
				alert("Location From and Location To cannot be same");
				return false;
			}
			else if(rowCount==0)
			{
				alert("Please Add Product in Grid");
				return false;
			}
			 if(  $("#cmbAgainst").val() == null || $("#cmbAgainst").val().trim().length == 0 )
			 {
			 		alert("Please Select Against");
					return false;
			 }
			
			
			else
			{
				ProductCodeArray.length=0;	 
				ProductCodeVar=-1;			 
				return true;
				/* if (actionName == 'submit') 
				{
						document.forms[0].action = "saveMaterialReturn.html";
				} */
			}
		}
		 
		/**
		 * Number Textfiled Validation   
		 */
		function funApplyNumberValidation(){
			$(".numeric").numeric();
			$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
			$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
			$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
		    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit,negative: false});
		}
		/**
		 * Reset function  
		 */
		function funResetFields()
		{
			location.reload(true); 
		} 
</script>

	
</head>
		<body>
	<div id="formHeading">
		<label>Material Return</label>
	</div>
		<s:form name="MaterialReturn" action="saveMaterialReturn.html?saddr=${urlHits}" method="POST">
		<br/>
	
			<table class="transTable">
		    	<tr>
				    <th align="right"> <a id="baseUrl" href="#">Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp; </th>
				</tr>
			</table>
		
			<table class="transTable"> 
			
		 		<tr>
		 		    <td width="10%">MR Code</td>
			        <td width="10%"><s:input id="txtMRCode" name="txtMRCode" cssClass="searchTextBox" path="strMRetCode" ondblclick="funHelp('MaterialReturn')"/></td>
			      	 <td width="20%"></td>			      
			        <td width="8%">MR Date</td>
			        <td colspan="5"><s:input id="txtMRDate" name="txtMRDate" type="text" required="required" path="dtMRetDate"  pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
			        
			    </tr>
				    	
			    <tr>
			        <td><label id="lblLocFrom" >Location By</label></td>
			        <td><s:input id="txtLocFromCode" name="txtLocFrom" path="strLocFrom" type="text" required="required"  ondblclick="funHelp('locby')" cssClass="searchTextBox"/></td>
			        <td><label id="txtLocFromName"></label>
			        <td><label id="lblLocTo" >Location To</label></td>
			        <td width="10%"><s:input id="txtLocToCode" name="txtLocTo" type="text" required="required" path="strLocTo" ondblclick="funHelp('locon')" cssClass="searchTextBox"/></td>
			        <td><label id="txtLocToName"></label></td>
				</tr>                   
				  
			    <tr>
				    <td><label>Against</label></td>
				    <td>
				    	<s:select id="cmbAgainst" path="strAgainst" items="${strProcessList}" 
				    	 onchange="funOnChange();" cssClass="BoxW124px">
						</s:select>
					</td>
					<td colspan="4"><s:input id="txtMISCode" name="txtMISCode" path="strMISCode" cssClass="searchTextBox" ondblclick="funOpenAgainst();"/></td>
					
				</tr>
				    </table>
				    <table class="transTableMiddle">
				<tr>
					<td width="10%"><label>Product Code</label></td>
				    <td width="10%"><input id="txtProdCode" name="txtProdCode" ondblclick="funHelp('productInUse')" class="searchTextBox"/></td>
				    <td width="10%"><label>Product Name</label></td>
				    <td width="20%"><label id="txtProdName" style="font-size: 12px;"></label></td>
				    <td width="10%"><label>POS Item Code</label></td>
				    <td width="10%"><label id="txtPOSItemCode"></label></td>
				    <td width="5%"><label>Stock</label></td>
				    <td colspan="2"><label id="txtStock"></label></td>				    
				</tr>
								    
				<tr>
				    <td><label>Qty</label></td>
				    <td><input id="txtQty" name="txtQty" class="decimal-places numberField" style="width: 100px" /></td>
				    
				    <td ><label >Remarks</label></td>
				    <td width="10%"><input id="txtRemarks" name="txtRemarks" class="remarkTextBox"/></td>
				    <td colspan="4"><input type="Button" value="Add"  class="smallButton" onclick="return btnAdd_onclick();"/></td>
				    <td></td>
				</tr>
				<tr></tr> 
				<tr></tr>				
			</table>
			<!--<br/>
		 <div class="codebox_bar">
			<p class="tab">Products</p>
		</div> -->
		<div class="dynamicTableContainer"  style="height:300px;">
		
		<table  style="height:28px;border:#0F0;font-size:11px;
	font-weight: bold;width: 100%;" >
		
		<tr bgcolor="#72BEFC" >
		<td width="6%">Product Code</td>
		<td width="30%">Product Name</td>
		<td width="6%">POS Item Code</td>
		<td width="5%">Quantity</td>
		<td width="20%">Remarks</td>
		<td width="5%">Delete</td>
		</tr>
		</table>
		<div style="background-color:  	#C0E2FE;
    border: 1px solid #ccc;
    display: block;
    height: 255px;
    margin: auto;
    overflow-x: hidden;
    overflow-y: scroll;
    width: 100%;">
		<table id="tblProduct"  style="width:100%;border:
#0F0;table-layout:fixed;overflow:scroll" class="transTablex col6-center">
	<tbody>    
	 <col style="width:6%">
        <col style="width:30%">
        <col style="width:6%">
        <col style="width:5%">
        <col style="width:20%">
        <col style="width:4%">
	
    </tbody>
</table>
		</div>
		</div>												
			<table class="transTableMiddle">
				<tr>
					<td>Narration</td>
		            <td><s:textarea id="txtNarration" path="strNarration"></s:textarea></td>
					
				</tr>	
			</table>
			<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit"  class="form_button" onclick="return funCallFormAction('submit',this)"/>
				 <input type="button"   value="Reset" class="form_button" onclick="funResetFields()" />
		</p>	
		<br><br>	
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
			
		</s:form>
		
		<script type="text/javascript">
    		funApplyNumberValidation();
    		funOnChange();
    	</script>
	</body>
</html>