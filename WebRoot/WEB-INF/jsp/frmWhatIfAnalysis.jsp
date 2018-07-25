<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="<c:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.steps.js"/>"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/steps.css"/>" />

<script>

		var fieldName,parentNodes="";
		
		$(function ()
		{
			$("#wizard").steps({
				headerTag: "h2",
				bodyTag: "section",
				transitionEffect: "slide",
				onStepChanging: function (event, currentIndex, newIndex)
			    {
			        if(currentIndex == 0) // Product Details Page
			        {			       		
			        }
			        if(currentIndex == 1) // MRP Page
			        {			        	
			        }
			        return true;
			    },
			    
			    onStepChanged: function (event, currentIndex, priorIndex)
			    {
					if (currentIndex == 1)
			        {
						var table1 = document.getElementById("tblChildNodes");
					    var rowCount1 = table1.rows.length;
					    for(var cnt=0;cnt<rowCount1;cnt++)
					    {
					    	table1.deleteRow(cnt);
					    }
						funGetChildNodes();
			        }
			    	else if(currentIndex == 2)
			    	{
			        }
					else if(currentIndex == 3)
					{
			        }
					else if(currentIndex == 4)
					{
			        }
			    },
			    onFinishing: function (event, currentIndex)
			    {
			    	return true;
			    },
			    onFinished: function (event, currentIndex)
			    {
			    }
			});
		});
		
		
		function funGetChildNodes()
		{
			var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    for(var cnt=0;cnt<rowCount;cnt++)
		    {
		    	var prodCode=document.getElementById("txtProdCode."+cnt).value;
		    	var qty=document.getElementById("txtQuantity."+cnt).value;
		    	var searchUrl=getContextPath()+"/getChildNodes.html?prodCode="+prodCode+","+qty;
				$.ajax({
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
				      });
		    }
		}
		
		
		function funAddChildProducts(childCode,childName,partNo,reqQty,currentStk,openPO,orderQty,suppCode
				,suppName,leadTime,rate,expectedDate,value) 
		{   
		    var table = document.getElementById("tblChildNodes");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtProdCode."+(rowCount)+"\" value="+childCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" size=\"10%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtProdName."+(rowCount)+"\" value="+childName+">";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPartNo."+(rowCount)+"\" value="+partNo+">";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtQuantity."+(rowCount)+"\" value="+reqQty+">";
		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+currentStk+">";
		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+openPO+">";
		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+orderQty+">";
		    row.insertCell(7).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+suppCode+">";
		    row.insertCell(8).innerHTML= "<input readonly=\"readonly\" size=\"10%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+suppName+">";
		    row.insertCell(9).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+leadTime+">";
		    row.insertCell(10).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+rate+">";
		    row.insertCell(11).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+expectedDate+">";
		    row.insertCell(12).innerHTML= "<input readonly=\"readonly\" size=\"5%\" class=\"Box\" name=\"listChild["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+value+">";
		    
		    row.insertCell(13).innerHTML= '<input type="button" size=\"2%\" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    
		    return false;
		}
		
		
		
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
			}
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
		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtProdCode."+(rowCount)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtProdName."+(rowCount)+"\" value="+prodName+">";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtPartNo."+(rowCount)+"\" value="+partNo+">";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtQuantity."+(rowCount)+"\" value="+qty+">";
		    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" name=\"listProduct["+(rowCount)+"]\" id=\"txtPrice."+(rowCount)+"\" value="+price+">";
		    row.insertCell(5).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    funResetSupplierFields();
		    return false;
		}
		
		
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProduct");
		    table.deleteRow(index);
		}
			
</script>
</head>

<body>
	<s:form action="saveWhatIfAnanlysis.html" method="POST" name="whatIfAnanlysis">
	
		<div id="wizard">
			<h2>Product Details</h2>
			<section>
				<div align="center">
					<table>
						<tr>
						<th colspan="8" align="center">Product Details</th>
							<td align="left">Product </td>
							<td><input id="txtProdCode" type="text" ondblclick="funHelp('ProductRecipe');" Class="searchTextBox"/></td>
							<td><label id="lblProdName" ></label></td>
							<td><label id="lblPartNo" ></label></td>
							<td>Quantity</td>
							<td><input id="txtQuantity" type="text"/></td>
							
							<td>Price</td>
							<td><input id="txtPrice" type="text"/></td>
							
							<td><input type="button" value="ADD" onclick="funAddProdDetailsRow();"/></td>
						</tr>
					</table>
					<table style="width:80%">
						<tr>
							<td>Product Code</td>
							<td>Product Name</td>
							<td>Part No</td>
							<td>Quantity</td>
							<td>Price</td>
						</tr>
					</table>
					
					<table style="width:80%" id="tblProduct">
						
					</table>
				</div>
			</section>
			
			
			<h2>M.R.P.</h2>
			<section>
				<div>
					<table>
					<tr>
						<th colspan="2" align="center">Material Requirement Planning</th>
							<td>Export Type :</td>
							<td>
								<select id="cmbExport" style="width:50px;">
									<option id="opExcel">Excel</option>									
								</select>
							</td>
							
							<td><input type="button" id="btnExport" value="EXPORT"/></td>
						</tr>
					</table>
					
					<table style="width:80%">
						<tr>
							<td>Product Code</td>
							<td>Product Name</td>
							<td>UOM</td>
							<td>Qty Required</td>
							<td>Current Stock</td>
							<td>Open PO</td>
							<td>Order Qty</td>
							<td>Supplier Code</td>
							<td>Supplier Name</td>
							<td>Lead Time</td>
							<td>Rate</td>
							<td>Expected Date</td>
						</tr>
					</table>
					
					<table id="tblChildNodes">		
					<tbody>
					<col style="width: 5%">
					<!-- col1   -->
					
					<col style="width: 10%">
					<!-- col3   -->
					<col style="width: 2%">
					<!-- col4   -->
					<col style="width: 5%">
					<!-- col5   -->
					<col style="width: 5%">
					<!-- col6   -->
					<col style="width: 5%">
					<!-- col7   -->
					<col style="width: 5%">
					<!-- col8   -->
					<col style="width: 5%">
					<!-- col9   -->
					<col style="width: 10%">
					<!-- col10   -->
					
					<col style="width: 5%">
					<!-- col11   -->
					<col style="width: 5%">
					<!-- col12   -->
					<col style="width: 5%">
					<!-- col13   -->
					<col style="width: 5%">
					<!-- col14   -->
					<col style="width: 2%">

					</tbody>
					
					
					
								
					</table>
					
				</div>
			</section>
			
			<!-- 
			<h2>Production Details</h2>
			<section>
				<div>					
				</div>
			</section>
			
			<h2>Status</h2>
			
			<section>
				<div>				
				</div>
			</section> 			
		
			<h2>Page Setting</h2>
			<section>
				<div>
				</div>
			</section>-->
			
 		</div>
	</s:form>

</body>
</html>