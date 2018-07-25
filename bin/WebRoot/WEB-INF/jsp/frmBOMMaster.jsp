<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RECIPE MASTER</title>
	<script type="text/javascript">
				
		var fieldName;
	
		$(function() 
		{
			 $( "#dtFromDate" ).datepicker();
		});
		
		$(function() 
		{
			 $( "#dtToDate" ).datepicker();
		});
		
		function funResetFields()
		{
			document.getElementById("parentCodeName").innerHTML="";
        	document.getElementById("posItemCode").innerHTML="";
        	document.getElementById("type").innerHTML="";
        	document.getElementById("sgCode").innerHTML="";
        	document.getElementById("sgName").innerHTML="";
        	document.getElementById("processCode").innerHTML="";
	    }
		
		function funHelp(transactionName)
		{
			fieldname=transactionName;
	        window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			var searchUrl="";
			if(fieldname=='bomcode')
			{ 
				document.recipeForm.action="recipeMaster1.html?BOMCode="+code;
				document.recipeForm.submit();
			}
			else if(fieldname=='childcode')
			{
				searchUrl=getContextPath()+"/loadProductData.html?ProductCode="+code;
			}
			else if(fieldname=='productmaster')
			{
				searchUrl=getContextPath()+"/loadProductData.html?ProductCode="+code;
			}
			if(fieldname!='bomcode')
			{
				$.ajax({
				        type: "GET",
				        url: searchUrl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(fieldname=='productmaster')
				        	{
				        		document.getElementById("parentCode").value=response.strParentCode;
					        	document.getElementById("parentCodeName").value=response.strParentName;
					        	document.getElementById("posItemCode").value=response.strPartNo;
					        	document.getElementById("type").value=response.strProdType;
					        	document.getElementById("sgCode").value=response.strSGCode;
					        	document.getElementById("sgName").value=response.strSGName;
					        	document.getElementById("processName").value=response.strProcessName;
				        	}
				        	else if(fieldname=='childcode')
				        	{
				        		document.getElementById("txtChildCode").value=response.strParentCode;
					        	document.getElementById("lblPOSItemCode").innerHTML=response.strPartNo;
					        	document.getElementById("lblItemName").innerHTML=response.strParentName;
					        	document.getElementById("lblUOM").innerHTML=response.strUOM;
				        	}				        	
						},
				        error: function(e)
				        {
				          	alert('Error:=' + e);
				        }
			      });
			}
		}
				
		function funAddRow() 
		{
			var childCode = document.getElementById("txtChildCode").value;
		    var qty = document.getElementById("txtQty").value;
		    var weight = document.getElementById("txtWeight").value;
		    var uom = document.getElementById("lblUOM").innerHTML;
		    var posItemCode = document.getElementById("lblPOSItemCode").innerHTML;
		    var itemName = document.getElementById("lblItemName").innerHTML;
		    var table = document.getElementById("tblChild");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    
		    row.insertCell(0).innerHTML= "<input name=\"listBomDtlModel["+(rowCount-2)+"].strChildCode\" id=\"txtChildCode."+(rowCount-2)+"\" value="+childCode+" ondblclick=\"funHelp('childcode')\">";		    
		    row.insertCell(1).innerHTML= "<input name=\"listBomDtlModel["+(rowCount-2)+"].strPartNo\" value="+posItemCode+" id=\"lblPOSItemCode."+(rowCount-2)+"\" >";
		    row.insertCell(2).innerHTML= "<input name=\"listBomDtlModel["+(rowCount-2)+"].strPartName\" value="+itemName+" id=\"lblItemName."+(rowCount-2)+"\" >";		    
		    row.insertCell(3).innerHTML= "<input type=\"text\" name=\"listBomDtlModel["+(rowCount-2)+"].dblQty\" id=\"txtQty."+(rowCount-2)+"\" value="+qty+">";
		    row.insertCell(4).innerHTML= "<input name=\"listBomDtlModel["+(rowCount-2)+"].strUOM\" id=\"lblUOM."+(rowCount-2)+"\" value="+uom+">";
		    row.insertCell(5).innerHTML= "<input type=\"text\" name=\"listBomDtlModel["+(rowCount-2)+"].dblWeight\" id=\"txtWeight."+(rowCount-2)+"\" value="+weight+">";
		    row.insertCell(6).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    return false;
		}
		 
		function funDeleteRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblChild");
		    table.deleteRow(index);
		}
			
	</script>
</head>

<body onload="funResetFields()">

	<s:form name="recipeForm" method="POST" action="saveRecipeMaster.html">
		<table>
		<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
			<tr></tr>
			<tr>
			<!-- 
		        <td align="right"> <a id="baseUrl" href="attatchdoc.html?groupCode=" target="_blank" onclick="return funCheckGroupCode()"> Attatch Documents </a> </td>
			-->
		    </tr>
				
		    <tr>
		        <td><s:label path="strBOMCode" >Recipe Code:</s:label></td>
		        <td><s:input id="bomCode" path="strBOMCode" readonly="true" value="${command.strBOMCode}" ondblclick="funHelp('bomcode')"/></td>
		        
		        <td><s:label path="strParentCode">Parent Product:</s:label></td>
		        <td>
		            <s:input id="parentCode" path="strParentCode" ondblclick="funHelp('productmaster')"/>
		        	<s:errors path="strParentCode"></s:errors>
		        </td>
		        <td><s:input id="parentCodeName" path="strParentName" readonly="true"/></td>
		    </tr>
			    
		    <tr>
			    <td><label>POS Item Code:</label></td>
			    <td><s:input id="posItemCode" path="strPosItemCode" readonly="true"/></td>
			    <td><label>Type:</label></td>
			    <td><s:input path="strType" id="type"></s:input></td>
			</tr>
			
			<tr>
			    <td><label>Sub Group Code:</label></td>
			    <td><s:input id="sgCode" path="strSGCode" readonly="true"/></td>
			    <td><label>Name:</label></td>
			    <td><s:input id="sgName" path="strSGName" readonly="true"/></td>
			</tr>
			
			<tr>
				<td><label>Process Name:</label></td>
				<td><s:input type="hidden" id="processCode" path="strProcessCode"/></td>
			    <td><s:select id="processName" path="strProcessName" items="${processList}"/></td>
			</tr>
			
			<tr>
			    <td><s:label path="dtValidFrom">Date Valid From:</s:label></td>
			    <td><s:input id="dtFromDate" name="fromDate" path="dtValidFrom"/></td>
			</tr>
				
			<tr>
			    <td><s:label path="dtValidTo">Date Valid To:</s:label></td>
			    <td><s:input id="dtToDate" name="toDate" path="dtValidTo" /></td>
			</tr>
		</table>
			
		<table id="tblChild">
			<tr>
				<th><label>Child Code:</label></th>
				<th><label>POS Item Code:</label></th>
				<th><label>Name</label></th>
				<th><label>Qty</label></th>
				<th><label>UOM</label></th>
				<th><label>Weight</label></th>
			</tr>
				
			<tr>
				<td><input id="txtChildCode" ondblclick="funHelp('childcode')"></input></td>
				<td><label id="lblPOSItemCode"></label></td>
				<td><label id="lblItemName"></label></td>
				<td><input id="txtQty"></input></td>
				<td><label id="lblUOM"></label></td>
				<td><input id="txtWeight"></input></td>
				<td><input id="btnAdd" type="submit" value="Add" onclick="return funAddRow()"></input></td>
			</tr>
			<c:forEach items="${command.listBomDtlModel}" var="recipe" varStatus="status">
				<tr>
					<td><input name="listBomDtlModel[${status.index}].strChildCode" value="${recipe.strChildCode}" readonly="readonly"/></td>
					<td><input name="listBomDtlModel[${status.index}].strPartNo" value="${recipe.strPartNo}" readonly="readonly"/></td>
					<td><input name="listBomDtlModel[${status.index}].strPartName" value="${recipe.strPartName}" readonly="readonly"/></td>
					<td><input name="listBomDtlModel[${status.index}].dblQty" value="${recipe.dblQty}"/></td>
					<td><input name="listBomDtlModel[${status.index}].strUOM" value="${recipe.strUOM}" readonly="readonly"/></td>
					<td><input name="listBomDtlModel[${status.index}].dblWeight" value="${recipe.dblWeight}"/></td>
				</tr>
			</c:forEach>
		</table>
		<br><br>
		<table>
			<tr>
			    <td colspan="1"><input id="formsubmit" type="submit" value="Submit" /></td>
			    <td colspan="1"><input type="reset" value="Reset" onclick="funResetFields()"/></td>
		    </tr>
		</table>			
	</s:form>
</body>
</html>