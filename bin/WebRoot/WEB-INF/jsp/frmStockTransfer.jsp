<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PHYSICAL STOCK POSTING</title>
<tab:tabConfig/>
	<script type="text/javascript">
				
		var fieldName;
		var gProdType,gUOM;
		
		function funAddRow() 
		{
			if(!funCheckNull($("#txtQuantity").val(),"Quantity"))
			{
				$("#txtQuantity").focus();
				return false;
			}
			else
			{
				if(!funValidateNumeric($("#txtQuantity").val()))
				{
					$("#txtQuantity").focus();
					return false;
				}
			}
		    var prodCode = $("#txtProdCode").val();
		    var prodName = document.getElementById("lblProdName").innerHTML;
		    var unitPrice = $("#txtUnitPrice").val();
		    var stock = $("#txtStock").val();
		    var quantity = $("#txtQuantity").val();
		    var wtUnit = $("#txtWtUnit").val();
		    var remarks = $("#txtRemarks").val();
		    var totalWt=quantity*wtUnit;
		    		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listStkTransDtl["+(rowCount-3)+"].strProdCode\" id=\"txtProdCode."+(rowCount-3)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input name=\"listStkTransDtl["+(rowCount-3)+"].strProdName\" value="+prodName+" id=\"lblProdName."+(rowCount-3)+"\" >";
		    row.insertCell(2).innerHTML= "<input name=\"listStkTransDtl["+(rowCount-3)+"].strProdType\" id=\"lblProdType."+(rowCount-3)+"\" value="+gProdType+">";
		    row.insertCell(3).innerHTML= "<input name=\"listStkTransDtl["+(rowCount-3)+"].strUMO\" id=\"lblUMO."+(rowCount-3)+"\" value="+gUOM+">";
		    row.insertCell(4).innerHTML= "<input type=\"text\" name=\"listStkTransDtl["+(rowCount-3)+"].dblQty\" id=\"txtQuantity."+(rowCount-3)+"\" value="+quantity+">";
		    row.insertCell(5).innerHTML= "<input name=\"listStkTransDtl["+(rowCount-3)+"].dblWeight\" id=\"txtWtUnit."+(rowCount-3)+"\" value="+wtUnit+">";
		    row.insertCell(6).innerHTML= "<input name=\"listStkTransDtl["+(rowCount-3)+"].dblTotalWt\" id=\"txtTotalWt."+(rowCount-3)+"\" value="+totalWt+">";
		    row.insertCell(7).innerHTML= "<input name=\"listStkTransDtl["+(rowCount-3)+"].strRemark\" id=\"txtRemarks."+(rowCount-3)+"\" value="+remarks+">";
		    row.insertCell(8).innerHTML= "<input type=\"hidden\" name=\"listStkTransDtl["+(rowCount-3)+"].dblPrice\" id=\"txtUnitPrice."+(rowCount-3)+"\" value="+unitPrice+">";
		    row.insertCell(9).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    funResetProductFields();
		    
		    return false;
		}
		 
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProduct");
		    table.deleteRow(index);
		}
		
		function funResetFields()
		{
			funRemProdRows();
	    }
		
		function funRemProdRows()
	    {
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			for(var i=rowCount-3;i>=1;i--)
			{
				table.deleteRow(i);
			}
	    }
		
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    document.getElementById("lblProdName").innerHTML='';
		    $("#txtUnitPrice").val('');
		    $("#txtWtUnit").val('');
		    $("#txtStock").val('');
		    $("#txtQuantity").val('');
		    $("#txtRemarks").val('');
		}
		
		function funResetFields()
		{
			funResetProductFields();
	    }
				
		$(function() 
		{
			$( "#txtSTDate" ).datepicker();
			 
			$("form").submit(function()
			{
				if($("#txtSTDate").val()=='')
				{
					alert("Please enter Stock Transfer Date");
					return false;
				}
				else if($("#tblProduct tr").length==3)
				{
					alert("Plase Select Product");
					return false;
				}
			});
		});
	
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(fieldName=='productmaster')
			{
				if($("#txtFromLocCode").val()=='')
				{
					alert("Please Select Location From");
				}
				else if($("#txtToLocCode").val()=='')
				{
					alert("Please Select Location To");
				}
				else
				{
			        window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
				}
			}
			else
			{
				window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
			}
	    }
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'stktransfercode':
			    	document.stkTransfer.action="stkTrans1.html?STCode="+code;
					document.stkTransfer.submit();
			        break;
			        
			    case 'locby':
			    	funSetLocationBy(code);
			        break;
			        
			    case 'locon':
			    	funSetLocationOn(code);
			        break;
			        
			    case 'productmaster':
			    	funSetProduct(code);
			        break;			    
			}			
		}
		
		
		function funSetLocationOn(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtToLocCode").val(response.strLocCode);
		        		$("#lblToLocName").val(response.strLocName);
				    },
					error: function(e)
				    {
				       	alert('Error:=' + e);
				    }
			      });
		}
		
		
		function funSetLocationBy(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtFromLocCode").val(response.strLocCode);
		        		$("#lblFromLocName").val(response.strLocName);
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
		        	document.getElementById("lblProdName").innerHTML=response.strProdName;
		        	$("#txtStock").val('0');
		        	$("#txtWtUnit").val(response.dblWeight);
		        	$("#txtUnitPrice").val(response.dblUnitPrice);
		        	gProdType=response.strProdType;
		        	gUOM=response.strUOM;
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		    });
		}
		
		
		$(function()
		{
			$("#txtSTCode").blur(function()
			{
				if(!$("#txtSTCode").val()=='')
				{
					funSetData($("#txtSTCode").val());
				}
			});
		    
			$('a#baseUrl').click(function() 
			{
				if($("#txtSTCode").val()=="")
				{
					alert("Please Select Stock Transfer Code");
					return false;
				}
			    $(this).attr('target', '_blank');
			});
		});
			
	</script>
</head>

<body onload="funResetFields()">

	<s:form name="stkTransfer" method="POST" action="saveStkTransfer.html">
		<tab:tabContainer id="Master">
			<tab:tabPane id="direct" tabTitle="DIRECT">
				<table>
					<tr></tr>
					
					<tr>
				        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attach Documents </a> </td>
				    </tr>
				    
				    <tr>
				        <td><label id="lblSTCode" >ST Code:</label></td>
				        <td><s:input id="txtSTCode" path="strSTCode" readonly="true" ondblclick="funHelp('stktransfercode')"/></td>
				        
				        <td><label id="lblNo" >No:</label></td>
				        <td><s:input id="txtNo" path="strNo" /></td>
				        
				        <td><label id="lblSTDate">Date:</label></td>
				        <td>
				            <s:input id="txtSTDate" path="dtSTDate"/>
				        	<s:errors path="dtSTDate"></s:errors>
				        </td>
				    </tr>
				    
				    <tr>
					    <td><label id="lblFromLoc" >From:</label></td>
				        <td><s:input id="txtFromLocCode" path="strFromLocCode" ondblclick="funHelp('locby')"/></td>
					    <td><s:input id="lblFromLocName" path="strFromLocName"/></td>
					    
					    <td><label id="lblToLoc" >To:</label></td>
				        <td><s:input id="txtToLocCode" path="strToLocCode" ondblclick="funHelp('locon')"/></td>
					    <td><s:input id="lblToLocName" path="strToLocName"/></td>
					</tr>
					
					<tr>
						<td><label id="lblMI" >To:</label></td>
						<td>
						   	<s:select id="cmbMaterialIssue" path="strMaterialIssue">
						   		<option value="No">No</option>
						   		<option value="Yes">Yes</option>
						   	</s:select>
						</td>
					</tr>
					
					<tr>
						<td><label id="lblMI" >To:</label></td>
						<td>
						   	<select id="cmbType">
						   		<option value="Product">Product</option>
						   		<option value="Assembly">Assembly</option>
						   	</select>
						</td>
					</tr>
					
				</table>
				
				<table id="tblProduct">
					<tr>
						<th><label>Product Code</label></th>
						<th><label>Product Name</label></th>
						<th><label>Unit Price</label></th>
						<th><label>Stock</label></th>
						<th><label>Quantity</label></th>
						<th><label>Wt/Unit</label></th>
						<th><label>Remarks</label></th>
					</tr>
					
					<tr>
						<td><input id="txtProdCode" ondblclick="funHelp('productmaster')"></input></td>
						<td><label id="lblProdName"></label></td>
						<td><input id="txtUnitPrice"></input></td>
						<td><input id="txtStock" readonly="readonly"></input></td>
						<td><input id="txtQuantity"></input></td>
						<td><input id="txtWtUnit"></input></td>
						<td><input id="txtRemarks"></input></td>
						<td><input id="btnAdd" type="button" value="Add" onclick="return funAddRow()"></input></td>
					</tr>
					
					<tr>
						<th><label>Product Code</label></th>
						<th><label>Product Name</label></th>
						<th><label>Product Type</label></th>
						<th><label>UOM</label></th>
						<th><label>Quantity</label></th>
						<th><label>Wt/Unit</label></th>
						<th><label>Total Wt</label></th>
						<th><label>Remarks</label></th>
						<th><label>Delete</label></th>
					</tr>
					
					<c:forEach items="${command.listStkTransDtl}" var="stkTrans" varStatus="status">
						<tr>
							<td><input name="listStkTransDtl[${status.index}].strProdCode" value="${stkTrans.strProdCode}" readonly="readonly"/></td>
							<td><input name="listStkTransDtl[${status.index}].strProdName" value="${stkTrans.strProdName}" readonly="readonly"/></td>
							<td><input name="listStkTransDtl[${status.index}].strProdType" value="${stkTrans.strProdType}" readonly="readonly"/></td>
							<td><input name="listStkTransDtl[${status.index}].strUOM" value="${stkTrans.strUOM}" readonly="readonly"/></td>
							<td><input name="listStkTransDtl[${status.index}].dblWeight" value="${stkTrans.dblWeight}"/></td>
							<td><input name="listStkTransDtl[${status.index}].dblQty" value="${stkTrans.dblQty}"/></td>
							<td><input name="listStkTransDtl[${status.index}].dblTotalWt" value="${stkTrans.dblTotalWt}"/></td>
							<td><input name="listStkTransDtl[${status.index}].strRemark" value="${stkTrans.strRemark}"/></td>
							<td><input type="hidden" name="listStkTransDtl[${status.index}].dblPrice" value="${stkTrans.dblPrice}"/></td>
						</tr>
					</c:forEach>
				</table>
				
				<table>
					<tr></tr>
				    <tr>
				        <td><label id="lblNarration" >ST Code:</label></td>
				        <td><s:input id="txtNarration" path="strNarration"/></td>
				    </tr>
			  </table>
				
				<br><br>
			</tab:tabPane>
				
		</tab:tabContainer>
			<table>
				<tr>
				    <td colspan="1"><input id="btnStkTransfer" type="submit" value="Submit" onclick="return funValidateFields()"/></td>
				    <td colspan="1"><input type="reset" value="Reset" onclick="funResetFields()"/></td>
			    </tr>
			</table>		
	</s:form>
	
</body>
</html>