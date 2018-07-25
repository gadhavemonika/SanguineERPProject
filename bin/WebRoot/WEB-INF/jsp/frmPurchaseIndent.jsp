<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Indent</title>

<script type="text/javascript">
var fieldName;

	$(function() 
	{
	 	$( "#dtPIDate" ).datepicker();	
	 	$( "#dtReqDate" ).datepicker();	
	});
	
	function funHelp(transactionName)
	
	{
		fieldName=transactionName;
		window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
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
		else if(fieldName=='PICode'){
			//searchUrl=getContextPath()+"/loadPIData.html?PICode="+code;
			
			document.PIForm.action="loadPIDtlData.html?PICode="+code;
			document.PIForm.submit();
		}
				
		if(fieldName !='PICode'){
		$.ajax({
		
	        type: "GET",
	        url: searchUrl,	        		
	        dataType: "json",
	        
	        success: function(response)	        
	        {
	           
	        if(fieldName=='productmaster'){
	        	$("#txtProdCode").val(response.strProdCode);
	        	$("#spProdName").text(response.strProdName);
	        	
	        	}
	       else if(fieldName=='locationmaster'){
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
	
	
	function btnAdd_onclick() 
	{		   
		if(document.all("txtProdCode").value!="")
	    {
	    	if(document.all("txtProdQty").value!="" && document.all("txtProdQty").value != 0 )
	        {
	            funAddProductRow();
	        	funClearProduct();
			} 
	        else
	        {
	        	alert("Please Enter Quantity");
	        	document.all("txtProdQty").focus();
	            return false;
			}
		}
		else
	    {
			alert("Please Enter Product Code or Search");
	    	document.all("txtProdCode").focus() ; 
	        return false;
		}  
	}
	 

		function funAddProductRow() 
		{
			
			var strProdCode = document.getElementById("txtProdCode").value;			
			//var strProdName=document.getElementById("spProdName").innerHTML;
			var strProdName='NOT FOUND';
		    var dblProdQty = document.getElementById("txtProdQty").value;
		    var strPurpose=document.getElementById("strPurpose").value;
		    var dtReqDate=document.getElementById("dtReqDate").value;
		    var strInStock='0.00';
		    var strAgainst='NA';
		    var Minimumlevel='NA';
		    
		    var table = document.getElementById("tblProdDet");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		   
		    row.insertCell(0).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].strProdCode\" id=\"txtProdCode."+(rowCount-1)+"\" value="+strProdCode+" >";			  		   
		    //row.insertCell(1).innerHTML= strProdName;
		    row.insertCell(1).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].strProdName\" id=\"strProdName."+(rowCount-1)+"\" value="+strProdName+">";
		    row.insertCell(2).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].dblQty\" id=\"txtProdQty."+(rowCount-1)+"\" value="+dblProdQty+">";
		    row.insertCell(3).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].strPurpose\" id=\"strPurpose."+(rowCount-1)+"\" value="+strPurpose+">";
		    row.insertCell(4).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].dtReqDate\" id=\"dtReqDate."+(rowCount-1)+"\" value="+dtReqDate+">";		   		   
		    row.insertCell(5).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].strInStock\" id=\"strInStock."+(rowCount-1)+"\" value="+strInStock+">";		    
		    row.insertCell(6).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].strAgainst\" id=\"strAgainst."+(rowCount-1)+"\" value="+strAgainst+">";
		    row.insertCell(7).innerHTML= "<input name=\"listPurchaseIndentDtlModel["+(rowCount-1)+"].Minimumlevel\" id=\"Minimumlevel."+(rowCount-1)+"\" value="+Minimumlevel+">";
		    row.insertCell(8).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    return false;
		}
		 
		function funDeleteRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdDet");
		    table.deleteRow(index);
		}
		
		function funClearProduct()
		{
			document.getElementById("txtProdCode").value="";
			document.getElementById("spPosItemCode").innerHTML="";
			document.getElementById("spProdName").innerHTML="";
			document.getElementById("txtProdQty").value="";
			document.getElementById("txtRemarks").value="";
		}
</script>
</head>
<body>

	<s:form method="POST" action="savepurchaseIndent.html" name="PIForm">
		<table>
			<tr>
				<td><label>PI Code</label> <s:input path="strPIcode"
						ondblclick="funHelp('PICode')" /></td>
				<td><label>Date</label>
				<s:input path="dtPIDate" id="dtPIDate" /></td>
			</tr>

			<tr>
				<td><label>Location</label> <s:input path="strLocCode"
						readonly="true" ondblclick="funHelp('locationmaster')"
						id="strLocCode" /></td>
				<td><label id="strLocName"></label></td>
			</tr>
			<tr>
				<td><label>Product Code:</label></td>
				<td><input id="txtProdCode" name="txtProdCode"
					ondblclick="funHelp('productmaster')" /></td>
				<td><label>Product Name:</label></td>
				<td><label id="spProdName"></label></td>
				<%-- <td><span id="spProdName"></span></td> --%>
			</tr>
			<tr>

				<td><label>Qty:</label></td>
				<td><input id="txtProdQty" name="txtProdQty" value="" /></td>
			<tr>
			<tr>
				<td><label>Purpose:</label></td>
				<td><input id="strPurpose" name="strPurpose">
				<td><label>Required Date</label></td>
				<td><input id="dtReqDate" name="dtReqDate" /></td>
			</tr>

			<tr>
				<td><label>Narration </label></td>
				<td><s:input path="strNarration" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" value="Add"
					onclick="return btnAdd_onclick()" /></td>
			</tr>
		</table>

		<table>
			<tr bgcolor="#75c0ff">
				<td style="width: 16%; height: 16px;" align="left">Product&nbsp;Code</td>
				<td style="width: 16%; height: 16px;" align="left">Prod name</td>
				<td style="width: 16%; height: 16px;" align="right">Qty</td>
				<td style="width: 16%; height: 16px;" align="left">Purpose</td>
				<td style="width: 16%; height: 16px;" align="left">Required
					Date</td>
				<td style="width: 16%; height: 16px;" align="left">Available
					Stock</td>
				<td style="width: 16%; height: 16px;" align="left">Minimum
					level</td>
				<td style="width: 16%; height: 16px;" align="left">Against</td>
				<td style="width: 16%; height: 16px;" align="center">Delete</td>
			</tr>

		</table>
		<div id="divProduct"
			style="width: 100%; bgcolor: #d8edff; overflow: scroll;">
			<table width="100%" bgcolor="#d8edff" id="tblProdDet"
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
				</tr>

				<c:forEach items="${command.listPurchaseIndentDtlModel}" var="pi"
					varStatus="status">
					<tr>
						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].strProdCode"
							value="${pi.strProdCode}" /></td>

						<%-- <td><input name="listPurchaseIndentDtlModel[${status.index}].strProdCode"
							value="${pi.strProdCode}" /></td>			 --%>

						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].dblQty"
							value="${pi.dblQty}" /></td>

						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].strPurpose"
							value="${pi.strPurpose}" /></td>

						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].dtReqDate"
							value="${pi.dtReqDate}" /></td>


						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].strInStock"
							value="${pi.strInStock}" /></td>

						<td><input
							name="listPurchaseIndentDtlModel[${status.index}].strAgainst"
							value="${pi.strAgainst}" /></td>

						<td><input type="Button" value="Delete" /></td>
					</tr>
				</c:forEach>


			</table>
		</div>







		<input type="submit" value="Submit" />
		<input type="reset" value="Reset" />
	</s:form>
</body>
</html>