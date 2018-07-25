
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Production Order</title>
<script type="text/javascript">
	var fieldName;
	$(function() {
		$("#dtOPDate").datepicker();
		$("#dtFulmtDate").datepicker();
		$("#dtfulfilled").datepicker();
	});

	
	function funHelp(transactionName) {
		
		fieldName = transactionName;
		
		window.showModalDialog("searchform.html?formname=" + transactionName,
				'window', 'width=600,height=600');
	}
	function funSetData(code)
	{
		var searchUrl="";
		if(fieldName=='locationmaster'){
			searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			
		}
		else if(fieldName=='productmaster'){
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			
		}
		else if(fieldName=='ProductionOrder'){

			document.productonorderform.action="loadOPData.html?OPCode="+code;
			document.productonorderform.submit();
		}
			
		if(fieldName !='ProductionOrder'){
			$.ajax({
			
		        type: "GET",
		        url: searchUrl,	        		
		        dataType: "json",
		        
		        success: function(response)	        
		        {
		           
		        if(fieldName=='productmaster'){
		        	$("#strProdCode").val(response.strProdCode);
		        	$("#strProductName").text(response.strProdName);
		        	$("#dblUnitPrice").val(response.dblUnitPrice);
		        	}
		       else if(fieldName=='locationmaster')
		       {
		        	$("#strLocCode").val (response.strLocCode);
		        	$('#strLocName').text(response.strLocName);	        
		        	}
		        	
				},
		        error: function(e)
		        {
		          	alert('Error:=' + e);
		        }
		  });
		}
	}
	
	function funClearProduct() {
		$("#strProdCode").val("");
		$("#strProdName").text("");
		$("#dblQty").val("");
		$("#dblWeight").val("");
		$("#dblUnitPrice").val("");
	}
	function btnAdd_onclick() 
	{		   
		if(document.all("strProdCode").value!="")
	    {
	    	if(document.all("strProdCode").value!="" && document.all("dblQty").value != 0 )
	        {
	            funAddProductRow();
	        	funClearProduct();
			} 
	        else
	        {
	        	alert("Please Enter Quantity");
	        	document.all("dblQty").focus();
	            return false;
			}
		}
		else
	    {
			alert("Please Enter Product Code or Search");
	    	document.all("strProdCode").focus() ; 
	        return false;
		}  
	}
	function funAddProductRow() 
	{
		
		var strProdCode = $("#strProdCode").val();		
		var strProdName=$("#strProductName").text();				
	    var dblQty = $("#dblQty").val();
	    
	    var dblWeight=$("#dblWeight").val();
	    
	    var dblUnitPrice=$("#dblUnitPrice").val();
	   	    
	    var amount=(dblUnitPrice*dblQty);
	    var strSpCode="NA";
	    
	   
	    
	    
	    var table = document.getElementById("tblProdDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	   
	    row.insertCell(0).innerHTML= "<input name=\"listclsProductionOrderDtlModel["+(rowCount-1)+"].strProdCode\" id=\"strProdCode."+(rowCount-1)+"\" value="+strProdCode+" >";			  		   	  
	    row.insertCell(1).innerHTML= strProdName;
	    row.insertCell(2).innerHTML= "<input name=\"listclsProductionOrderDtlModel["+(rowCount-1)+"].dblQty\" id=\"dblQty."+(rowCount-1)+"\" value="+dblQty+">";		    	    
	    row.insertCell(3).innerHTML= "<input name=\"listclsProductionOrderDtlModel["+(rowCount-1)+"].dblWeight\" id=\"dblWeight."+(rowCount-1)+"\" value="+dblWeight+">";
	    row.insertCell(4).innerHTML= "<input name=\"listclsProductionOrderDtlModel["+(rowCount-1)+"].dblUnitPrice\" id=\"dblUnitPrice."+(rowCount-1)+"\" value="+dblUnitPrice+">";	   
	    row.insertCell(5).innerHTML= amount;	
	    row.insertCell(6).innerHTML= strSpCode;
	    row.insertCell(7).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
	    return false;
	}
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblProdDet");
	    table.deleteRow(index);
	}
</script>
</head>
<body>
	<s:form method="POST" action="saveProductionOrderData.html" name="productonorderform">

		<table>

			<tr>
				<td><label>Production Order Code</label></td>
				<td><s:input path="strOPCode" id="strOPCode" ondblclick="funHelp('ProductionOrder')" /></td>
				<td><label>Date</label></td>
				<td><s:input path="dtOPDate" id="dtOPDate" autocomplete="false" /></td>
				<td><label>Status </label></td>
				<td><s:label path="strStatus"></s:label></td>
			</tr>


			<tr>
				<td><label>Against</label></td>
				<td><s:select path="strAgainst">
				<s:option value="Direct">Direct</s:option>
				<s:option value="Sales Projection">Sales Projection</s:option>
				</s:select></td>
			</tr>

			<tr>
				<td><label>Fullfillment Date</label></td>
				<td><s:input path="dtFulmtDate" id="dtFulmtDate" /></td>
				<td><label>Fullfilled Date</label></td>
				<td><s:input path="dtfulfilled" id="dtfulfilled" /></td>
			</tr>


			<tr>
				<td><label>Location </label></td>
				<td><s:input path="strLocCode" id="strLocCode" readonly="true"
						ondblclick="funHelp('locationmaster')" /></td>
				<td><label id="strLocName"></label>
			</tr>

			<tr><td></td></tr>
			<tr><td></td></tr>

			<tr>
				<td><label>Product</label></td>
				<td><input id="strProdCode" ondblclick="funHelp('productmaster')" /></td>
				<td><label id="strProductName"></label></td>
				<td><label>Quantity</label></td>
				<td><input id="dblQty" type="number" ></td>
				<td><label>Unit Price</label></td>
				<td><input id="dblUnitPrice"></td>
				<td><label>Wt/Unit</label></td>
				<td><input id="dblWeight"></td>
				<td><input type="button" value="Add"
					onclick="return btnAdd_onclick()" /></td>
			</tr>
			

		</table>
		
		<table>
			<tr bgcolor="#75c0ff">
				<td style="width: 16%; height: 16px;" align="left">Product Code</td>
				<td style="width: 16%; height: 16px;" align="left">Prod Name</td>
				<td style="width: 16%; height: 16px;" align="right">Quantity</td>
				<td style="width: 16%; height: 16px;" align="left">Wt/Unit</td>
				<td style="width: 16%; height: 16px;" align="left">Unit Price</td>
				<td style="width: 16%; height: 16px;" align="left">Total Price</td>
				<td style="width: 16%; height: 16px;" align="left">SP Code</td>
				<td style="width: 16%; height: 16px;" align="center">Delete</td>
			</tr>
		</table>
		<div id="divProduct"
			style="width: 100%; bgcolor: #d8edff; overflow: scroll;">
			<table id="tblProdDet"
				style="overflow: scroll;">
				<tr>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="right"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					<td style="width: 16%" align="left"></td>
					
				</tr>
				<c:forEach items="${command.listclsProductionOrderDtlModel}" var="op"
					varStatus="status">
					<tr>
						<td><input
							name="listclsProductionOrderDtlModel[${status.index}].strProdCode"
							value="${op.strProdCode}" /></td>

						<td><input
							name="listclsProductionOrderDtlModel[${status.index}].dblQty"
							value="${op.dblQty}" /></td>

						<td><input
							name="listclsProductionOrderDtlModel[${status.index}].dblWeight"
							value="${op.dblWeight}" /></td>

						<td><input
							name="listclsProductionOrderDtlModel[${status.index}].dblUnitPrice"
							value="${op.dblUnitPrice}" /></td>


						<td><input
							name="listclsProductionOrderDtlModel[${status.index}].strSpCode"
							value="${op.strSpCode}" /></td>

						<td><input type="Button" value="Delete" /></td>
					</tr>
				</c:forEach>


			</table>
		</div>
		<table>
		<tr>
		<td><label>Narration</label></td>
		<td><s:input path="strNarration" id="strNarration"/></td>
		</tr>
		
		</table>
		<input type="submit" value="Submit" />
		<input type="reset" value="Reset" />

	</s:form>
</body>
</html>