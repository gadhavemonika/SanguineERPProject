<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Order</title>


<script type="text/javascript">
var fieldName;
$(function() 
		{
		 	$( "#dtPODate" ).datepicker();	
		 	$( "#dtDelDate" ).datepicker();	
		 	$("#dtPayDate").datepicker();
		});
		


/* TO hide PICode input fields when Direct is Selected */
 
	$(document).ready(function() {
		$("#strAgainst").change(function() {
			if ($(this).val() == "Purchase Indent") {
				$("#PICode").show();
			} else {
				$("#PICode").hide();
			}
		});
		$("#PICode").hide();
	});
/*   End of Hide Function  */
	
	function fillPIData(code){
		var code=$("#PICode").val();
		if(code!=""){
		document.POForm.action="loadPIDtlDataForPO.html?PICode="+code;
		document.POForm.submit();
		}
		else{
			alert("Please Enter PI Code");
			$("#PICode").focus();
			return false;
		}
	}
	function funHelp(transactionName)
	{
		fieldName = transactionName;		
		window.showModalDialog("searchform.html?formname=" + transactionName,
				'window', 'width=600,height=600');		
	}
	function funSetData(code) {
		var searchUrl="";
		if(fieldName=='PICode'){
			$("#PICode").val(code);			
		} else if(fieldName=='productmaster'){
			$("#strProdCode").val(code);
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
		}else if(fieldName=='suppcode'){
			$("#strSuppCode").val(code);
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
		}else if(fieldName=='purchaseorder'){
			document.POForm.action="loadPOData.html?POCode="+code;
			document.POForm.submit();
		}
		if(fieldName !='purchaseorder' && fieldName !='PICode' ){
			$.ajax({
			
		        type: "GET",
		        url: searchUrl,	        		
		        dataType: "json",
		        
		        success: function(response)	        
		        {
		           
		        if(fieldName=='productmaster'){
		        	$("#strProdName").text(response.strProdName);
		        	$("#dblPrice").val(response.dblUnitPrice);
		        	}
		       else if(fieldName=='suppcode'){
		    	   $("#SupplierName").text(response.strPName);    
		        	}
		        	
				},
		        error: function(e)
		        {
		          	alert('Error:=' + e);
		        }
		  });
		}

	}
	
	function funAddProductRow() 
	{
		
		var strProdCode = $("#strProdCode").val();		
		var strProdName=$("#strProdName").text();
		var strProcess="NA";
		var strUOM="NA";
		var strSuppCode=$("#strSuppCode").val();
		var SupplierName=$("#SupplierName").text();
	    var dblOrdQty = $("#dblOrdQty").val();
	    var dblWeight=$("#dblWeight").val();
	    var dblTotalWeight="0.00";
	    var dblPrice=$("#dblPrice").val();
	    var dblDiscount=$("#dblDiscount").val();	    
	    var amount=(dblOrdQty*dblPrice)-dblDiscount;
	    var strRemarks=$("#strRemarks").val();
	    var PICode=$("#PICode").val();
	    var strUpdate=$("#strUpdate").val();
	    
	    
	    var table = document.getElementById("tblProdDet");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	   
	    row.insertCell(0).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].strProdCode\" id=\"strProdCode."+(rowCount-1)+"\" value="+strProdCode+" >";			  		   	  
	    row.insertCell(1).innerHTML= strProdName;
	    row.insertCell(2).innerHTML= strProcess;
	    row.insertCell(3).innerHTML= strUOM;
	    row.insertCell(4).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].strSuppCode\" id=\"strSuppCode."+(rowCount-1)+"\" value="+strSuppCode+">";
	    row.insertCell(5).innerHTML= SupplierName;		   		   
	    row.insertCell(6).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].dblOrdQty\" id=\"dblOrdQty."+(rowCount-1)+"\" value="+dblOrdQty+">";		    	    
	    row.insertCell(7).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].dblWeight\" id=\"dblWeight."+(rowCount-1)+"\" value="+dblWeight+">";	    
	    row.insertCell(8).innerHTML= dblTotalWeight;	    
	    row.insertCell(9).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].dblPrice\" id=\"dblPrice."+(rowCount-1)+"\" value="+dblPrice+">";	   
	    row.insertCell(10).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].dblDiscount\" id=\"dblDiscount."+(rowCount-1)+"\" value="+dblDiscount+">";	    
	    row.insertCell(11).innerHTML= amount;	    
	    row.insertCell(12).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].strRemarks\" id=\"strRemarks."+(rowCount-1)+"\" value="+strRemarks+">";	    
	 	 row.insertCell(13).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].strPICode\" id=\"PICode."+(rowCount-1)+"\" value="+PICode+">";	    	   
	    row.insertCell(14).innerHTML= "<input name=\"listclsPurchaseOrderDtlModel["+(rowCount-1)+"].strUpdate\" id=\"strUpdate."+(rowCount-1)+"\" value="+strUpdate+">";	    
	    row.insertCell(15).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
	    return false;
	}
	
	function btnAdd_onclick() 
	{		   
		if(document.all("strProdCode").value!="")
	    {
	    	if(document.all("dblOrdQty").value!="" && document.all("dblOrdQty").value != 0 )
	        {
	            funAddProductRow();
	        	funClearProduct();
			} 
	        else
	        {
	        	alert("Please Enter Quantity");
	        	document.all("dblOrdQty").focus();
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
	
	function funClearProduct() {
		$("#strProdCode").val("");
		$("#strProdName").text("");
		$("#dblOrdQty").val("");
		$("#dblWeight").val("");
		$("#strRemarks").val("");
		$("#dblPrice").val("");

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
<s:form name="POForm" method="POST" action="savePOData.html" >
<table >


<tr>
<td><label>PO Code</label></td>
<td><s:input path="strPOCode" id="strPOCode" ondblclick="funHelp('purchaseorder')"/></td>
<td><label>Our's Ref No.</label></td>
<td><s:input path="strYourRef" id="strYourRef"/></td>
<td><label>PO Date</label>
<td><s:input path="dtPODate" id="dtPODate"/></td>
</tr>


<tr>
<td><label>Supplier</label></td>
<td><s:input path="strSuppCode" id="strSuppCode" readonly="true" ondblclick="funHelp('suppcode')"/></td>
<td><label  id="SupplierName"></label></td>
<td><label>Delivery Date</label></td>
<td><s:input path="dtDelDate" id="dtDelDate"/></td>
</tr>


<tr>
<td><label>Against</label></td>
<td><s:select path="strAgainst" id="strAgainst">
<s:option value="Direct">Direct</s:option>
<s:option value="Purchase Indent">Purchase Indent</s:option>
</s:select></td>
<td><input id="PICode" ondblclick="funHelp('PICode')"></td>

<td><input type="button" value="Fill" onclick="return fillPIData()" /></td>
<td><label>Payment Due Date </label></td>
<td><s:input path="dtPayDate" id="dtPayDate"/></td>
</tr>


<tr>
<td><label>Pay Mode</label></td>
<td><s:select path="strPayMode">
<s:option value="CASH">CASH</s:option>
<s:option value="CREDIT">CREDIT</s:option>
</s:select></td>
<td><label>Currency </label></td>
<td><s:select path="strCurrency">
<s:option value="Rs">Rs</s:option>
<s:option value="$">$</s:option>
</s:select></td>
</tr>


<tr>
<td><label>Bill To</label></td>
<td><label>Ship To  </label></td>
</tr>

<tr>
<td><label>Address Line 1</label></td>
<td><s:input path="strVAddress1"/></td>
<td><label>Address Line 1</label></td>
<td><s:input path="strSAddress1"/></td>
</tr>



<tr>
<td><label>Address Line 2</label></td>
<td><s:input path="strVAddress2" /></td>
<td><label>Address Line 2</label></td>
<td><s:input path="strSAddress2"/></td>
</tr>




<tr>
<td><label>City</label></td>
<td><s:input path="strVCity"/></td>
<td><label>City</label></td>
<td><s:input path="strSCity"/></td>
</tr>



<tr>
<td><label>State</label></td>
<td><s:input path="strVState"/></td>
<td><label>State</label></td>
<td><s:input path="strSState"/></td>
</tr>



<tr>
<td><label>Country</label></td>
<td><s:input path="strVCountry"/></td>
<td><label>Country</label></td>
<td><s:input path="strSCountry"/></td>
</tr>




<tr>
<td><label>Pin Code</label></td>
<td><s:input path="strVPin"/></td>
<td><label>Pin Code</label></td>
<td><s:input path="strSPin"/></td>
</tr>


<tr>
<td><label>Product</label></td>
<td><input  id="strProdCode" ondblclick="funHelp('productmaster')"/></td>
<td><label id="strProdName"></label></td>
<td><label>Unit Price</label></td>
<td><input id="dblPrice"/></td>
<td><label>Wt/ Unit</label></td>
<td><input  id="dblWeight"/></td>
<td><label>Quantity</label></td>
<td><input  id="dblOrdQty"/></td>
</tr>


<tr>
<td><label>Highlight</label></td>
<td><select id="strUpdate">
<option value="N">YES</option>
<option value="Y">NO</option>
</select></td>
<td><label>Stock </label></td>
<td><label>Discount</label></td>
<td><input  id="dblDiscount"/></td>
<td><label> Remarks</label></td>
<td><input id="strRemarks"/></td>
<td><input type="button" value="Add" onclick="return btnAdd_onclick()" /></td>
</tr>
</table>
<!--  -->

			<table  width="100%" >
			<tr bgcolor="#75c0ff">
				<td style="width: 16%; height: 16px;" align="left">Product&nbsp;Code</td>
				<td style="width: 16%; height: 16px;" align="left">Prod name</td>
				<td style="width: 16%; height: 16px;" align="right">Process</td>
				<td style="width: 16%; height: 16px;" align="left">UOM</td>
				<td style="width: 16%; height: 16px;" align="left">Supplier Code</td>
				<td style="width: 16%; height: 16px;" align="left">Supplier Name</td>
				<td style="width: 16%; height: 16px;" align="left">Order Qty</td>
				<td style="width: 16%; height: 16px;" align="left">Wt/Unit</td>
				<td style="width: 16%; height: 16px;" align="center">Total Wt</td>
				<td style="width: 16%; height: 16px;" align="center">Price</td>
				<td style="width: 16%; height: 16px;" align="center">Discount</td>
				<td style="width: 16%; height: 16px;" align="center">Amount</td>
				<td style="width: 16%; height: 16px;" align="center">Remarks</td>
				<td style="width: 16%; height: 16px;" align="center">PI Code</td>
				<td style="width: 16%; height: 16px;" align="center">Update</td>
				<td style="width: 16%; height: 16px;" align="center">Delete</td>
			</tr>
			</table>
			
<!--  -->			


<div id="divProduct"
			style="width: 100%; bgcolor: #d8edff; overflow: scroll;">
			<table  width="100%" bgcolor="#d8edff" id="tblProdDet" 
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
					<td style="width: 16%" align="center"></td>
					<td style="width: 16%" align="center"></td>
					<td style="width: 16%" align="center"></td>
					<td style="width: 16%" align="center"></td>
					<td style="width: 16%" align="center"></td>
					<td style="width: 16%" align="center"></td>
					<td style="width: 16%" align="center"></td>
					<td style="width: 16%" align="center"></td>
				</tr>

				<c:forEach items="${command.listclsPurchaseOrderDtlModel}" var="po"
					varStatus="status">
					<tr>
						<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].strProdCode"
							value="${po.strProdCode}" /></td>

						<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].dblOrdQty"
							value="${po.dblOrdQty}" /></td>
							
							<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].dblWeight"
							value="${po.dblWeight}" /></td>
							
							
							<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].dblPrice"
							value="${po.dblPrice}" /></td>
							
							
							<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].dblDiscount"
							value="${po.dblDiscount}" /></td>
							
										<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].strRemarks"
							value="${po.strRemarks}" /></td>
							
							
										<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].strPICode"
							value="${po.strPICode}" /></td>
							
							
									
										<td><input
							name="listclsPurchaseOrderDtlModel[${status.index}].strUpdate"
							value="${po.strUpdate}" /></td>
							
							

						<td><input type="Button" value="Delete" /></td>
					</tr>
				</c:forEach>


			</table>
		</div>

<!--  -->
<input type="submit" value="Submit" />
		<input type="reset" value="Reset" />
</s:form>
</body>
</html>