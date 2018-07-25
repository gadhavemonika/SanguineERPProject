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
		var fieldName,gUOM,gProdType;
		
		function funAddRow() 
		{
			if($("#txtQuantity").val()=='')
			{
				alert("Please Enter Quantity");
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
		    var quantity = $("#txtQuantity").val();
		    var wtUnit = $("#txtWtUnit").val();
		    var strType = $("#cmbType").val();
		    var remark = $("#txtRemark").val();
		    var totalWt=quantity*wtUnit;
		    var uom=gUOM;
		    var prodType=gProdType;
		    		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listStkAdjDtl["+(rowCount-3)+"].strProdCode\" id=\"txtProdCode."+(rowCount-3)+"\" value="+prodCode+">";
		    row.insertCell(1).innerHTML= "<input name=\"listStkAdjDtl["+(rowCount-3)+"].strProdName\" value="+prodName+" id=\"lblProdName."+(rowCount-3)+"\" >";
		    row.insertCell(2).innerHTML= "<input name=\"listStkAdjDtl["+(rowCount-3)+"].strProdType\" value="+prodType+" id=\"lblProdType."+(rowCount-3)+"\" >";
		    row.insertCell(3).innerHTML= "<input type=\"text\" name=\"listStkAdjDtl["+(rowCount-3)+"].strUOM\" id=\"lblUOM."+(rowCount-3)+"\" value="+uom+">";
		    row.insertCell(4).innerHTML= "<input type=\"text\" name=\"listStkAdjDtl["+(rowCount-3)+"].dblQty\" id=\"txtQuantity."+(rowCount-3)+"\" value="+quantity+">";
		    row.insertCell(5).innerHTML= "<input type=\"text\" name=\"listStkAdjDtl["+(rowCount-3)+"].dblWeight\" id=\"txtWtUnit."+(rowCount-3)+"\" value="+wtUnit+">";
		    row.insertCell(6).innerHTML= "<input name=\"listStkAdjDtl["+(rowCount-3)+"].dblTotalWt\" id=\"lblTotalWt."+(rowCount-3)+"\" value="+totalWt+">";
		    row.insertCell(7).innerHTML= "<input name=\"listStkAdjDtl["+(rowCount-3)+"].strType\" id=\"txtType."+(rowCount-3)+"\" value="+strType+">";
		    row.insertCell(8).innerHTML= "<input name=\"listStkAdjDtl["+(rowCount-3)+"].strRemark\" id=\"txtRemark."+(rowCount-3)+"\" value="+remark+">";		    
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
		
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    document.getElementById("lblProdName").innerHTML='';
		    $("#txtQuantity").val('');
		    $("#txtWtUnit").val('');
		    $("#cmbType").val('IN');
		    $("#txtRemark").val('');
		}
		
		function funResetFields()
		{
			funRemoveRows();
	    }
		
		function funRemoveRows()
	    {
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			for(var i=rowCount-3;i>=1;i--)
			{
				table.deleteRow(i);
			}
	    }
		
		
		$(function() 
		{
			$( "#txtSADate" ).datepicker();
			 
			$("form").submit(function()
			{
				if($("#txtSADate").val=='')
				{
					alert("Please enter Stock Posting Date");
					return false;
				}
				else if($("#tblProduct tr").length==3)
				{
					alert("Plase Select Product");
					return true;
				}
			});
		});
		
		
		function funValidateFields()
		{
			if($("#txtLocCode").val().length==0)
			{
				$("#txtLocCode").focus();
				alert("Please select Location");
				return false;
			}
			else
			{
				if($("#txtSADate").val().length==0)
				{
					alert("Please enter Stock Posting Date");
					$("#txtSADate").focus();
					return false;
				}
				else if(document.getElementById("tblProduct").rows.length==3)
				{
					alert("Plase Select Product");
					$("#txtProdCode").focus();
					return false;
				}
			}
			return true;
		}
		
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			if(fieldName=='productmaster')
			{
				if($("#txtLocCode").val()=='')
				{
					$("#txtLocCode").focus();
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
			    case 'stkadjcode':
			    	document.stkAdjustment.action="stkAdj1.html?SACode="+code;
					document.stkAdjustment.submit();
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
				    	document.getElementById("lblLocName").innerHTML=response.strLocName;
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
		        	$("#txtWtUnit").val(response.dblWeight);
		        	gUOM=response.strUOM;
		        	gProdType=response.strProdType;
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		    });
		}
		
		$(function()
		{
			$("#txtSACode").blur(function()
			{
				if(!$("#txtSACode").val()=='')
				{
					funSetData($("#txtSACode").val());
				}					
			});
			    
			$('a#baseUrl').click(function() 
			{
				if($("#txtSACode").val()=="")
				{
					alert("Please Select SA Code");
					return false;
				}
			    $(this).attr('target', '_blank');
			});
		});
			
	</script>
</head>

<body>

	<s:form name="stkAdjustment" method="POST" action="saveStkAdjustment.html">
		<tab:tabContainer id="Master">
			<tab:tabPane id="direct" tabTitle="DIRECT">
				<table>
					<tr></tr>
					
					<tr>
		        		<td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    		</tr>
					
				    <tr>
				        <td><label id="lblSACode" >SA Code:</label></td>
				        <td><s:input id="txtSACode" path="strSACode" readonly="true" ondblclick="funHelp('stkadjcode')"/></td>
				        
				        <td><label id="lblSADate">Rate Contract Date:</label></td>
				        <td>
				            <s:input id="txtSADate" path="dtSADate"/>
				        	<s:errors path="dtSADate"></s:errors>
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
						<th><label>Stock</label></th>
						<th><label>Quantity</label></th>
						<th><label>Wt/Unit</label></th>
						<th><label>Type</label></th>
						<th><label>Remark</label></th>
					</tr>
					
					<tr>
						<td><input id="txtProdCode" ondblclick="funHelp('productmaster')"></input></td>
						<td><label id="lblProdName"></label></td>
						<td><input id="txtStock" readonly="readonly"></input></td>
						<td><input id="txtQuantity"></input></td>
						<td><input id="txtWtUnit"></input></td>
						<td>
					    	<select id="cmbType" >
					    		<option value="IN">IN</option>
					    		<option value="OUT">OUT</option>
					    	</select>
					    </td>
					    <td><input id="txtRemark"></input></td>
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
						<th><label>Type</label></th>
						<th><label>Remark</label></th>
						<th><label>Delete</label></th>
					</tr>
					
					<c:forEach items="${command.listStkAdjDtl}" var="stkAdj" varStatus="status">
						<tr>
							<td><input name="listStkAdjDtl[${status.index}].strProdCode" value="${stkAdj.strProdCode}" readonly="readonly"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strProdName" value="${stkAdj.strProdName}" readonly="readonly"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strProdType" value="${stkAdj.strProdType}" readonly="readonly"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strUOM" value="${stkAdj.strUOM}" readonly="readonly"/></td>
							<td><input name="listStkAdjDtl[${status.index}].dblQty" value="${stkAdj.dblQty}"/></td>
							<td><input name="listStkAdjDtl[${status.index}].dblWeight" value="${stkAdj.dblWeight}"/></td>
							<td><input name="listStkAdjDtl[${status.index}].dblTotalWt" value="${stkAdj.dblTotalWt}" readonly="readonly"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strType" value="${stkAdj.strType}" readonly="readonly"/></td>
							<td><input name="listStkAdjDtl[${status.index}].strRemark" value="${stkAdj.strRemark}"/></td>
							<td><input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)"></td>
							
						</tr>
					</c:forEach>
				</table>
				
				<table>
					<tr>
						<td><label id="lblAreaNarration">Narration:</label></td>
						<td><s:textarea id="txtAreaNarration" path="strNarration" /></td>
					</tr>
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