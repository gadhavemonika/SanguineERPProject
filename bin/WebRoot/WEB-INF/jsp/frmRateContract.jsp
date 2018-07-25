<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RECIPE MASTER</title>
<tab:tabConfig/>
	<script type="text/javascript">
				
		var fieldName;
		
		$(function()
		{
			$( "#txtFromDate" ).datepicker();
			$( "#txtToDate" ).datepicker();
			$( "#txtRateContDate" ).datepicker();
		});
		
		function funResetFields()
		{			
	    }
		
		function funValidateFields()
		{
			if(!isNaN(document.getElementById("txtRate").value))
			{
				alert("Enter only numbers in Rate field "+document.getElementById("txtRate").value);
				return false;
			}
			else
			{
				alert("OK");
			}
			return true;
	    }
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	        window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'ratecontno':
			    	document.rateContract.action="rateContract1.html?rateContNo="+code;
					document.rateContract.submit();
			        break;
			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'productmaster':
			    	funSetProduct(code);
			        break;			    
			}
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
				    	$("#txtSupplierCode").val(response.strPCode);
			        	document.getElementById("lblSuppName").innerHTML=response.strPName;
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
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	$("#txtProdCode").val(response.strProdCode);
		        	document.getElementById("lblPOSItemCode").innerHTML=response.strPartNo;
		        	document.getElementById("lblProdName").innerHTML=response.strProdName;
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		    });
		}
		
		$(function()
		{
			$("#txtRateContractNo").blur(function()
			{
				if(!$("#txtRateContractNo").val()=='')
				{
					funSetData($("#txtRateContractNo").val());
				}					
			});
		    
			$('a#baseUrl').click(function() 
			{
				if($("#txtRateContractNo").val()=="")
				{
					alert("Please Select Rate Contract Code");
					return false;
				}
			    $(this).attr('target', '_blank');
			});
		});
		
		
		function funAddRow() 
		{
			var prodCode = document.getElementById("txtProdCode").value;
			var posItemCode = document.getElementById("lblPOSItemCode").innerHTML;
		    var prodName = document.getElementById("lblProdName").innerHTML;
		    var uom = document.getElementById("txtUOM").value;
		    var rate = document.getElementById("txtRate").value;
		    var discount = document.getElementById("txtDiscount").value;
		    var narration = document.getElementById("txtNarration").value;
		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listRateContDtl["+(rowCount-2)+"].strProductCode\" id=\"txtProdCode."+(rowCount-2)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input name=\"listRateContDtl["+(rowCount-2)+"].strProductName\" value="+prodName+" id=\"lblProdName."+(rowCount-2)+"\" >";
		    row.insertCell(2).innerHTML= "<input name=\"listRateContDtl["+(rowCount-2)+"].strPartNo\" value="+posItemCode+" id=\"lblPOSItemCode."+(rowCount-2)+"\" >";
		    row.insertCell(3).innerHTML= "<input type=\"text\" name=\"listRateContDtl["+(rowCount-2)+"].strUnit\" id=\"txtUOM."+(rowCount-2)+"\" value="+uom+">";
		    row.insertCell(4).innerHTML= "<input name=\"listRateContDtl["+(rowCount-2)+"].dblRate\" id=\"txtRate."+(rowCount-2)+"\" value="+rate+">";
		    row.insertCell(5).innerHTML= "<input type=\"text\" name=\"listRateContDtl["+(rowCount-2)+"].dblDiscount\" id=\"txtDiscount."+(rowCount-2)+"\" value="+discount+">";
		    row.insertCell(6).innerHTML= "<input type=\"text\" name=\"listRateContDtl["+(rowCount-2)+"].strNarration\" id=\"txtNarrtion."+(rowCount-2)+"\" value="+narration+">";
		    row.insertCell(7).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
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

<body onload="funResetFields()">

	<s:form name="rateContract" method="POST" action="saveRateCotract.html">
		<tab:tabContainer id="Master">
			<tab:tabPane id="general" tabTitle="GENERAL">
				<table>
					<tr></tr>
					<tr>				    
				        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attach Documents </a> </td>
				    </tr>
						
				    <tr>
				        <td><label id="lblRateContNo" >Rate Contract Code:</label></td>
				        <td><s:input id="txtRateContractNo" path="strRateContNo" readonly="true" ondblclick="funHelp('ratecontno')"/></td>
				        
				        <td><label id="lblRateContDate">Rate Contract Date:</label></td>
				        <td>
				            <s:input id="txtRateContDate" path="dtRateContDate"/>
				        	<s:errors path="dtRateContDate"></s:errors>
				        </td>
				    </tr>
					    
				    <tr>
					    <td><label id="lblSuppCode" >Supplier Code:</label></td>
				        <td><s:input id="txtSupplierCode" path="strSuppCode" ondblclick="funHelp('suppcode')"/></td>
					    <td><s:input id="lblSupplierName" path="strSuppName"/></td>
					</tr>
					
					<tr>
					    <td><label id="lblFromDate">From Date:</label></td>
				        <td>
				            <s:input id="txtFromDate" path="dtFromDate"/>
				        	<s:errors path="dtFromDate"></s:errors>
				        </td>
				        
				        <td><label id="lblToDate">To Date:</label></td>
				        <td>
				            <s:input id="txtToDate" path="dtToDate"/>
				        	<s:errors path="dtToDate"></s:errors>
				        </td>
					</tr>
					
					<tr>
					    <td><label id="lblDateChange">Date Change:</label></td>
				        <td>
				            <s:select id="cmbDateChange" path="strDateChg" items="${dateChgList}" />
				        </td>
				        
				        <td><label id="lblCurrency">Currency:</label></td>
				        <td>
				        	<s:select id="cmbCurrency" path="strCurrency" items="${currencyList}" />
				        </td>
					</tr>
					
					<tr>
					    <td><label id="lblProduct">All Products:</label></td>
				        <td>
				            <s:select id="cmbAllProduct" path="strProdFlag" items="${allProdList}" />
				        </td>
					</tr>					
				</table>
					
				<table id="tblProduct">
					<tr>
						<th><label>Product Code:</label></th>
						<th><label>Product Name:</label></th>
						<th><label>POS Item Code</label></th>
						<th><label>UOM</label></th>
						<th><label>Rate</label></th>
						<th><label>Discount</label></th>
						<th><label>Narration</label></th>
					</tr>
						
					<tr>
						<td><input id="txtProdCode" ondblclick="funHelp('productmaster')"></input></td>
						<td><label id="lblProdName"></label></td>
						<td><label id="lblPOSItemCode"></label></td>
						<td><input id="txtUOM"></input></td>
						<td><input id="txtRate"></input></td>
						<td><input id="txtDiscount"></input></td>
						<td><input id="txtNarration"></input></td>
						<td><input id="btnAdd" type="submit" value="Add" onclick="return funAddRow()"></input></td>
					</tr>
					
					<c:forEach items="${command.listRateContDtl}" var="ratecont" varStatus="status">
						<tr>
							<td><input name="listRateContDtl[${status.index}].strProductCode" value="${ratecont.strProductCode}" readonly="readonly"/></td>
							<td><input name="listRateContDtl[${status.index}].strProductName" value="${ratecont.strProductName}" readonly="readonly"/></td>
							<td><input name="listRateContDtl[${status.index}].strPartNo" value="${ratecont.strPartNo}" readonly="readonly"/></td>
							<td><input name="listRateContDtl[${status.index}].strUnit" value="${ratecont.strUnit}"/></td>
							<td><input name="listRateContDtl[${status.index}].dblRate" value="${ratecont.dblRate}"/></td>
							<td><input name="listRateContDtl[${status.index}].dblDiscount" value="${ratecont.dblDiscount}"/></td>
							<td><input name="listRateContDtl[${status.index}].strNarration" value="${ratecont.strNarration}"/></td>
						</tr>
					</c:forEach>
				</table>
				<br><br>
				
			</tab:tabPane>
			
			<tab:tabPane id="tc" tabTitle="TERMS AND CONDITIONS">
				<table>
					<tr></tr>
				    <tr>
				        <td><label id="lblDelSchedule" >Delivery Schedule:</label></td>
				        <td><s:input id="txtDelSchedule" path="strTC1"/></td>
				    </tr>
					    
				    <tr>
				        <td><label id="lblShipment" >Mode Of Shipment:</label></td>
				        <td><s:input id="txtShipment" path="strTC2"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblExcise" >Excise:</label></td>
				        <td><s:input id="txtExcise" path="strTC3"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblSalesTax" >Sales Tax:</label></td>
				        <td><s:input id="txtSalesTax" path="strTC4"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblFreight" >Freight:</label></td>
				        <td><s:input id="txtFreight" path="strTC5"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblPF" >P and F:</label></td>
				        <td><s:input id="txtPF" path="strTC6"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblOctroi" >Octroi:</label></td>
				        <td><s:input id="txtOctroi" path="strTC7"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblTestCert" >Test Certificates:</label></td>
				        <td><s:input id="txtTestCert" path="strTC8"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblPaymentTemrs" >Payments Terms:</label></td>
				        <td><s:input id="txtPaymentTerms" path="strTC9"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblValidity" >Validity:</label></td>
				        <td><s:input id="txtValidity" path="strTC10"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblOthers" >Others:</label></td>
				        <td><s:input id="txtOthers" path="strTC11"/></td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblOthers2" >Others2:</label></td>
				        <td><s:input id="txtOthers2" path="strTC12"/></td>
				    </tr>
				    					
				</table>
			</tab:tabPane>
			
		</tab:tabContainer>
			<table>
				<tr>
				    <td colspan="1"><input id="formsubmit" type="submit" value="Submit" /></td>
				    <td colspan="1"><input type="reset" value="Reset" onclick="funResetFields()"/></td>
			    </tr>
			</table>
		
	</s:form>
</body>
</html>