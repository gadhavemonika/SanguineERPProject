<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script>
 	
	var ExportData;
	var dblRate=0.0;
		$(function ()
		{
			$("#btnSubmit").click(function( event )
			{
				$("#t2").trigger('click');
				funGetChildNodes();
			});
		});
		
		$(document).ready(function() {
			$(".tab_content").hide();
			$(".tab_content:first").show();
			$("ul.tabs li").click(function() 
			{
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();

				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
		});
		
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
		function funSetData(code)
		{
			switch (fieldName)
			{
			    case 'ProductRecipe':
			    	funSetProduct(code);
			    	break;
			    	
			    case 'ProductionOrder':
			    	$("#txtOPCode").val(code);
					funSetOPData(code);
					break;
			}
		}
		
		
		function funRemProdRows() {
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;

			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		
		function funRemChildProdRows() {
			var table = document.getElementById("tblChildNodes");
			var rowCount = table.rows.length;

			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		
		function funGetChildNodes()
		{
			funRemChildProdRows();
			var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var prodList="",prodQtyList="";
		    for(var cnt=0;cnt<rowCount;cnt++)
		    {
		    	var prodCode=document.getElementById("txtProdCode."+cnt).value;		    	
		    	var qty=document.getElementById("txtQuantity."+cnt).value;
		    	prodList+=","+prodCode+"!"+qty;
		    }
		    	var searchUrl=getContextPath()+"/getChildNodes1.html?prodCode="+prodList;
		    	
		    	$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {				    	
				    	$.each(response, function(i,itemDtl)
						{				    		
							funAddChildProducts(itemDtl[0],itemDtl[1],itemDtl[2],itemDtl[3].toFixed(maxQuantityDecimalPlaceLimit),itemDtl[4].toFixed(maxAmountDecimalPlaceLimit),itemDtl[5]
							,itemDtl[6],itemDtl[7],itemDtl[8],itemDtl[9],itemDtl[10].toFixed(maxAmountDecimalPlaceLimit),itemDtl[11],itemDtl[12]);
						});
				    	
				    	funAddTotal();
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
		    	
				/*$.ajax({
				        type: "GET",
				        url: searchUrl,
					    dataType: "json",
					    success: function(response)
					    {
					    	for(var cnt=0;cnt<response.length;cnt++)
					    	{
					    		funAddChildProducts(response[cnt][0],response[cnt][1],response[cnt][2],response[cnt][3]
					    		,response[cnt][4],response[cnt][5],response[cnt][6],response[cnt][7],response[cnt][8],response[cnt][9]
					    		,response[cnt][10],response[cnt][11],response[cnt][12]);
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
				      });*/
		    
		}
		
		
		function funAddChildProducts(childCode,childName,partNo,reqQty,currentStk,openPO,orderQty,suppCode
				,suppName,leadTime,rate,expectedDate,value) 
		{   
		    var table = document.getElementById("tblChildNodes");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtProdCode."+(rowCount)+"\" value="+childCode+">";
		    row.insertCell(1).innerHTML= "<input width=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtProdName."+(rowCount)+"\" value='"+childName+"'>";
		    row.insertCell(2).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPartNo."+(rowCount)+"\" value="+partNo+">";
		    row.insertCell(3).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtQuantity."+(rowCount)+"\" value="+reqQty+">";
		    row.insertCell(4).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+currentStk+">";
		    row.insertCell(5).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+openPO+">";
		    row.insertCell(6).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+orderQty+">";
		    row.insertCell(7).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+suppCode+">";
		    row.insertCell(8).innerHTML= "<input width=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+suppName+">";
		    row.insertCell(9).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+leadTime+">";
		    row.insertCell(10).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+rate+">";
		    row.insertCell(11).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+expectedDate+">";
		    row.insertCell(12).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+value+">";		    
		    row.insertCell(13).innerHTML= '<input width=\"5%\" type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';		    
		    dblRate=parseFloat(dblRate)+parseFloat(rate);
		    return false;
		}
		function funAddTotal()
		{
			var table = document.getElementById("tblChildNodes");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    dblRate=dblRate.toFixed(maxAmountDecimalPlaceLimit)
		    
		    row.insertCell(0).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtProdCode."+(rowCount)+"\" value=>";
		    row.insertCell(1).innerHTML= "<input width=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtProdName."+(rowCount)+"\" value=>";
		    row.insertCell(2).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPartNo."+(rowCount)+"\" value=>";
		    row.insertCell(3).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtQuantity."+(rowCount)+"\" value=>";
		    row.insertCell(4).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value=>";
		    row.insertCell(5).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value=>";
		    row.insertCell(6).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value=>";
		    row.insertCell(7).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value=>";
		    row.insertCell(8).innerHTML= "<input width=\"10%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value=>";
		    row.insertCell(9).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value='Total'>";
		    row.insertCell(10).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+dblRate+">";
		    row.insertCell(11).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value=>";
		    row.insertCell(12).innerHTML= "<input width=\"5%\" readonly=\"readonly\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value=>";		    
		    row.insertCell(13).innerHTML= '<input width=\"5%\" readonly=\"readonly\" class=\"Box\"  value =  >';		    
		}
		
		function funSetOPData(code) 
		{
			funRemProdRows();
			searchUrl = getContextPath() + "/loadOPDtlData.html?OPCode=" + code;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					$.each(response, function(i, item) {
						funAddProdDetailsForProdOrder(response[i].strProdCode,response[i].strProdName,''
								,response[i].dblQty,response[i].dblUnitPrice);
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
		
		
		function funSetProduct(code)
		{	
			var searchUrl=getContextPath()+"/loadProductFromRecipe.html?prodCode="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$.each(response, function(i,item)
						{
				    		$("#txtProdCode").val(response[i][0]);
				    		$("#lblProdName").text(response[i][2]);
				    		$("#lblPartNo").text(response[i][1]);
				    		$("#txtQuantity").focus();
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
		
		
		function funAddProdDetailsRow() 
		{
		    var prodCode = $("#txtProdCode").val();
		    var prodName = $("#lblProdName").text();
		    var partNo = $("#lblPartNo").text();
		    var qty = $("#txtQuantity").val();
		    var price = $("#txtPrice").val();
		    //arrParentProd=arrParentProd+","+prodCode;
		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+ ">";
		    row.insertCell(1).innerHTML= "<input width=\"15%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtProdName."+(rowCount)+"\" value='"+prodName+"'>";
		    row.insertCell(2).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtPartNo."+(rowCount)+"\" value="+partNo+">";
		    row.insertCell(3).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtQuantity."+(rowCount)+"\" value="+qty+">";
		    row.insertCell(4).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+price+">";
		    row.insertCell(5).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    funResetFields();
		    return false;
		}
		
		function funAddProdDetailsForProdOrder(prodCode,prodName,partNo,qty,price) 
		{
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    //arrParentProd=arrParentProd+","+prodCode;
		    
		    row.insertCell(0).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input width=\"15%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtProdName."+(rowCount)+"\" value='"+prodName+"' \> ";
		    row.insertCell(2).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtPartNo."+(rowCount)+"\" value="+partNo+">";
		    row.insertCell(3).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtQuantity."+(rowCount)+"\" value="+qty+">";
		    row.insertCell(4).innerHTML= "<input width=\"4%\" readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+price+">";
		    row.insertCell(5).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    
		    return false;
		}
		
		function funResetFields()
		{
			$("#txtProdCode").val('');
			$("#lblProdName").text('');
			$("#lblPartNo").text('');
			$("#txtQuantity").val('0');
			$("#txtPrice").val('0');
		}
		
		 $(document).ready(function () {
		 $("#btnExport").click(function() {  
			  var dtltbl = $('#dvWhatIf').html(); 
			  window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#dvWhatIf').html()));
			});
		 });
		 
		 function funDeleteRow(obj)  
			{
			    var index = obj.parentNode.parentNode.rowIndex;
			    var table = document.getElementById("tblProduct");
			    table.deleteRow(index);
			    
			}
		 
	</script>
</head>
<body>
<div id="formHeading">
		<label>What If Analysis</label>
	</div>
		<s:form action="saveWhatIfAnanlysis.html" method="POST" name="whatIfAnanlysis">
		<br>
	
		<div id="tab_container" style="height: 550px">
			<ul class="tabs">
				<li class="active" id="t1" data-state="tab1" style="width: 100px;padding-left: 55px">General</li>
				<li data-state="tab2" id="t2" style="width: 150px;padding-left: 55px">What If Analysis</li>
			</ul>
	
			<div id="tab1" class="tab_content" style="height: 330px" >
				<table class="transTable">
				<tr>
							<th colspan="5" align="center">Product Details</th>	
							</tr>
							<tr>					
							<td>Product</td>
							<td colspan="5"><input id="txtProdCode" type="text" ondblclick="funHelp('ProductRecipe');" Class="searchTextBox"/>
							<label id="lblProdName" ></label>
							<label id="lblPartNo" ></label></td>
						</tr>
						<tr>
							<td width="10%">Quantity</td>
							<td width="20%"><input id="txtQuantity" type="text" Class="decimal-places-amt numberField"/></td>
							<td width="5%">Price</td>
							<td><input id="txtPrice" type="text" Class="decimal-places-amt numberField"/></td>
							<td width="50%"><input type="button" value="ADD" onclick="funAddProdDetailsRow();" class="form_button"/></td>
						</tr>
				</table>
						
				<br>		
				<table class="transTable">
					<tr>
						<td width="10%"><label>Production Order Code</label></td>
						<td width="50%"><input id="txtOPCode" type="text" ondblclick="funHelp('ProductionOrder');" Class="searchTextBox"/></td>
					</tr>
				</table>
				
				<div class="dynamicTableContainer" style="height: 150px;">
					<table style="height:20px;border:#0F0;width:90%;font-size:11px;font-weight: bold;">
						<tr bgcolor="#72BEFC">
							<td width="4%">Product Code</td>
							<td width="15%">Product Name</td>
							<td width="4%">Part No</td>
							<td width="4%">Quantity</td>
							<td width="4%">Price</td>
						</tr>
					</table>
					<div style="background-color:#a4d7ff;
					   border: 1px solid #ccc;
					   display: block;
					   height: 250px;
					   margin: auto;
					   overflow-x: hidden;
					   overflow-y: scroll;
					   width: 100%;">
						   
						<table id="tblProduct" style="width:100%;border:
							#0F0;table-layout:fixed;overflow:scroll" class="transTablex ">		
							
							<tbody>
					<col style="width: 3%">
					<!-- col1   -->
					
					<col style="width: 11%">
					<!-- col3   -->
					<col style="width: 3%">
					<!-- col4   -->
					<col style="width: 3%">
					<!-- col5   -->
					<col style="width: 3%">
					<!-- col6   -->
					<col style="width: 2%">
					<!-- col7   -->
				
							</tbody>
							
						</table>
					</div>
				</div>
	
				<br><br>
			</div>
			
			<div id="tab2" class="tab_content"> 
<!-- 				<table class="transTable"> -->
<!-- 					<tr><th colspan="6" align="left">  </th></tr> -->
<!-- 				</table> -->
							
<!-- 				<table class="transTable" id="tblWhatIFFlashColumns"> -->
<!-- 					<tr> -->
<!-- 						<td width="5%">Product Code</td> -->
<!-- 						<td width="7%">Product Name</td> -->
<!-- 						<td width="5%">UOM</td> -->
<!-- 						<td width="5%">Qty Required</td> -->
<!-- 						<td width="5%">Current Stock</td> -->
<!-- 						<td width="5%">Open PO</td> -->
<!-- 						<td width="5%">Order Qty</td> -->
<!-- 						<td width="5%">Supplier Code</td> -->
<!-- 						<td width="7%">Supplier Name</td> -->
<!-- 						<td width="5%">Lead Time</td> -->
<!-- 						<td width="5%">Rate</td> -->
<!-- 						<td width="5%">Expected Date</td>						 -->
<!-- 					</tr> -->
<!-- 				</table> -->
						
<!-- 				<div id="dvWhatIf" style="width: 100% ;height: 100% ;padding-left: 26px;"> -->
<!-- 					<table class="transTablex col14-center" id="tblChildNodes"> -->
<!-- 						<tbody> -->
<%-- 					<col style="width: 4%"> --%>
<!-- 					col1   -->
					
<%-- 					<col style="width: 10%"> --%>
<!-- 					col3   -->
<%-- 					<col style="width: 2%"> --%>
<!-- 					col4   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col5   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col6   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col7   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col8   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col9   -->
<%-- 					<col style="width: 10%"> --%>
<!-- 					col10   -->
					
<%-- 					<col style="width: 5%"> --%>
<!-- 					col11   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col12   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col13   -->
<%-- 					<col style="width: 5%"> --%>
<!-- 					col14   -->
<%-- 					<col style="width: 2%"> --%>
<!-- 					</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
				
<!-- 				<p align="center"> -->
<!-- 					<input id="btnExport" type="button" value="EXPORT" id="export" class="form_button" /> -->
<!-- 				</p> -->
<!-- 			</div> -->

		<div class="dynamicTableContainer" id="dvWhatIf" style="height: 330px;">
			<table style="height: 20px; border: #0F0;width: 100%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
<!-- 					<td width="6%">Prod Code</td>					 -->
<!-- 					<td width="22%">Product Name</td>	 -->
<!-- 					<td width="3%">UOM</td>	 -->
<!-- 					<td width="3%">Stock</td>				 -->
<!-- 					<td width="5%">Issue Qty</td> -->
<!--                     <td width="5%">Pending Qty</td>  -->
<!--                     <td width="5%">Unit Price</td> -->
<!--                     <td width="5%">Total Price</td>  -->
<!--                     <td width="8%">MR Code</td>         -->
<!-- 					<td width="14%">Remarks</td> -->
<!-- 					<td width="5%">Delete</td> -->

					<td width="3%">Product Code</td> 
 						<td width="7%">Product Name</td>
 						<td width="2.3%">UOM</td> 
 						<td width="4%">Qty Required</td> 
						<td width="4%">Current Stock</td> 
						<td width="3%">Open PO</td>
						<td width="3%">Order Qty</td>
 						<td width="3%">Supplier Code</td>
						<td width="7%">Supplier Name</td> 
 						<td width="3%">Lead Time</td> 
 						<td width="3%">Amt</td> 
 						<td width="3%">Expected Date</td>
 						<td width="3%"></td>
 						<td width="2%"></td>	

				</tr>
			</table>
			<div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 230px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblChildNodes"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width: 3.2%">
<!-- 					col1   -->
					
					<col style="width: 7%">
<!-- 					col3   -->
					<col style="width: 2.3%">
<!-- 					col4   -->
					<col style="width: 4%">
<!-- 					col5   -->
					<col style="width: 4%">
<!-- 					col6   -->
					<col style="width: 3%">
<!-- 					col7   -->
					<col style="width: 3%">
<!-- 					col8   -->
					<col style="width: 3.5%">
<!-- 					col9   -->
					<col style="width: 7%">
<!-- 					col10   -->
					
					<col style="width: 2.8%">
<!-- 					col11   -->
					<col style="width: 3%">
<!-- 					col12   -->
					<col style="width: 3%">
<!-- 					col13   -->
					<col style="width: 3%">
<!-- 					col14   -->
					<col style="width: 1%">
					</tbody>
				</table>
			</div>
			
			<p align="center">
					<input id="btnExport" type="button" value="EXPORT" id="export" class="form_button" />
				</p>
				
			
		</div>
</div>



				<p align="center">
			<input id="btnSubmit" type="button" value="Submit" id="formsubmit" class="form_button" /> 
			<input type="reset" value="Reset" class="form_button" onclick=" return funResetField()" />
		</p>


			<br></br>
		
		
		</div>
		
		
	</s:form>
	
</body>
</html>