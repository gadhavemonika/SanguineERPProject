<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PHYSICAL STOCK POSTING</title>
<tab:tabConfig/>
	<script type="text/javascript">
				
		var fieldName;
		
		function funAddRow() 
		{			
			if(funCheckNull($("#txtQuantity").val(),"Quantity"))
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
		    var wtunit = $("#txtWtUnit").val();
		    var currentStkQty = $("#txtStock").val();
		    var phyStkQty = $("#txtQuantity").val();
		    var variance=phyStkQty-currentStkQty;
		    var adjValue = unitPrice*variance;
		    var adjWeight = wtunit*variance;
		    		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listStkPostDtl["+(rowCount-3)+"].strProdCode\" id=\"txtProdCode."+(rowCount-3)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input name=\"listStkPostDtl["+(rowCount-3)+"].strProdName\" value="+prodName+" id=\"lblProdName."+(rowCount-3)+"\" >";		    
		    row.insertCell(2).innerHTML= "<input type=\"text\" name=\"listStkPostDtl["+(rowCount-3)+"].dblPrice\" id=\"txtUnitPrice."+(rowCount-3)+"\" value="+unitPrice+">";
		    row.insertCell(3).innerHTML= "<input name=\"listStkPostDtl["+(rowCount-3)+"].dblWeight\" id=\"txtWtUnit."+(rowCount-3)+"\" value="+wtunit+">";
		    row.insertCell(4).innerHTML= "<input type=\"text\" name=\"listStkPostDtl["+(rowCount-3)+"].dblCStock\" id=\"txtStock."+(rowCount-3)+"\" value="+currentStkQty+">";
		    row.insertCell(5).innerHTML= "<input type=\"text\" name=\"listStkPostDtl["+(rowCount-3)+"].dblPStock\" id=\"txtQuantity."+(rowCount-3)+"\" value="+phyStkQty+">";
		    row.insertCell(6).innerHTML= "<input name=\"listStkPostDtl["+(rowCount-3)+"].dblVariance\" id=\"lblVariance."+(rowCount-3)+"\" value="+variance+">";
		    row.insertCell(7).innerHTML= "<input name=\"listStkPostDtl["+(rowCount-3)+"].dblAdjValue\" id=\"lblAdjValue."+(rowCount-3)+"\" value="+adjValue+">";
		    row.insertCell(8).innerHTML= "<input name=\"listStkPostDtl["+(rowCount-3)+"].dblAdjWt\" id=\"lblAdjWeight."+(rowCount-3)+"\" value="+adjWeight+">";
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
		}
		
		function funResetFields()
		{
			funResetProductFields();
	    }
				
		$(function() 
		{
			$( "#txtStkPostDate" ).datepicker();
			 
			$("form").submit(function()
			{
				if($("#txtStkPostDate").val()=='')
				{
					alert("Please enter Stock Posting Date");
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
				if($("#txtLocCode").val()=='')
				{
					alert("Please Select Location");
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
			    case 'stkpostcode':
			    	document.stkPosting.action="stkPost1.html?PSCode="+code;
					document.stkPosting.submit();
			        break;
			        
			    case 'locationmaster':
			    	funSetLocation(code);
			        break;
			        
			    case 'productmaster':
			    	funSetProduct(code);
			        break;			    
			}
		}
		
		function funSetLocation(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?LocCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtLocCode").val(response.strLocCode);		        		
		        		document.getElementById("lblLocName").innerHTML=response.strProdName;
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
			searchUrl=getContextPath()+"/loadProductDataWithTax.html?prodCode="+code;
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
		        	$("#txtUnitPrice").val(response.dblUnitPrice);
		        	$("#txtWtUnit").val(response.dblWeight);
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		    });
		}
		
		$(function()
		{
			$("#txtStkPostCode").blur(function()
			{
				if(!$("#txtStkPostCode").val()=='')
				{
					funSetData($("#txtStkPostCode").val());
				}					
			});
		    
			$('a#baseUrl').click(function() 
			{
				if($("#txtStkPostCode").val()=="")
				{
					alert("Please Select Stock Posting Code");
					return false;
				}
			    $(this).attr('target', '_blank');
			});
		});
			
	</script>
</head>

<body onload="funResetFields()">

	<s:form name="stkPosting" method="POST" action="savePhyStkPosting.html">
		<tab:tabContainer id="Master">
			<tab:tabPane id="direct" tabTitle="DIRECT">
				<table>
					<tr></tr>
					
					<tr>
				        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attach Documents </a> </td>
				    </tr>
					
				    <tr>
				        <td><label id="lblStkPostCode" >Stock Posting Code:</label></td>
				        <td><s:input id="txtStkPostCode" path="strPSCode" readonly="true" ondblclick="funHelp('stkpostcode')"/></td>
				        
				        <td><label id="lblStkPostDate">Rate Contract Date:</label></td>
				        <td>
				            <s:input id="txtStkPostDate" path="dtPSDate"/>
				        	<s:errors path="dtPSDate"></s:errors>
				        </td>
				    </tr>
				    <tr>
					    <td><label id="lblLocation" >Location:</label></td>
				        <td><s:input id="txtLocCode" path="strLocCode" ondblclick="funHelp('locationmaster')"/></td>
					    <td><s:input id="lblLocName" path="strLocName"/></td>
					</tr>
				</table>
				
				<table id="tblProduct">
					<tr>
						<th><label>Product Code</label></th>
						<th><label>Product Name</label></th>
						<th><label>Unit Price</label></th>
						<th><label>Stock</label></th>
						<th><label>Wt/Unit</label></th>
						<th><label>Quantity</label></th>
					</tr>
					
					<tr>
						<td><input id="txtProdCode" ondblclick="funHelp('productmaster')"></input></td>
						<td><label id="lblProdName"></label></td>
						<td><input id="txtUnitPrice"></input></td>
						<td><input id="txtStock" readonly="readonly"></input></td>
						<td><input id="txtWtUnit"></input></td>
						<td><input id="txtQuantity"></input></td>
						<td><input id="btnAdd" type="button" value="Add" onclick="return funAddRow()"></input></td>
					</tr>
					
					<tr>
						<th><label>Product Code</label></th>
						<th><label>Product Name</label></th>
						<th><label>Unit Price</label></th>
						<th><label>Wt/Unit</label></th>
						<th><label>Current Stock</label></th>
						<th><label>Physical Stock</label></th>
						<th><label>Variance</label></th>
						<th><label>Adjusted Wt</label></th>
						<th><label>Adjusted Value</label></th>
						<th><label>Delete</label></th>
					</tr>
					
					<c:forEach items="${command.listStkPostDtl}" var="stkPost" varStatus="status">
						<tr>
							<td><input name="listStkPostDtl[${status.index}].strProdCode" value="${stkPost.strProdCode}" readonly="readonly"/></td>
							<td><input name="listStkPostDtl[${status.index}].strProdName" value="${stkPost.strProdName}" readonly="readonly"/></td>
							<td><input name="listStkPostDtl[${status.index}].dblPrice" value="${stkPost.dblPrice}"/></td>
							<td><input name="listStkPostDtl[${status.index}].dblWeight" value="${stkPost.dblWeight}"/></td>
							<td><input name="listStkPostDtl[${status.index}].dblCStock" value="${stkPost.dblCStock}" readonly="readonly"/></td>
							<td><input name="listStkPostDtl[${status.index}].dblPStock" value="${stkPost.dblPStock}"/></td>
							<td><input name="listStkPostDtl[${status.index}].dblVariance" value="${stkPost.dblVariance}" readonly="readonly"/></td>
							<td><input name="listStkPostDtl[${status.index}].dblAdjWt" value="${stkPost.dblAdjWt}" readonly="readonly"/></td>
							<td><input name="listStkPostDtl[${status.index}].dblAdjValue" value="${stkPost.dblAdjValue}" readonly="readonly"/></td>							
							<td><input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)"></td>
							
						</tr>
					</c:forEach>
				</table>
				<br><br>
			</tab:tabPane>
				
		</tab:tabContainer>
			<table>
				<tr>
				    <td colspan="1"><input id="btnStkPost" type="submit" value="Submit" onclick="return funValidateFields()"/></td>
				    <td colspan="1"><input type="reset" value="Reset" onclick="funResetFields()"/></td>
			    </tr>
			</table>
		
	</s:form>
</body>
</html>