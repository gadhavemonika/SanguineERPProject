<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Opening Stock</title>
	<script type="text/javascript">
	
		var fieldName,gurl;
		
		$(function() 
		{
			 $( "#txtExpDate" ).datepicker();
		});
	
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	        window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
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
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	$("#txtLocCode").val(response.strLocCode);
		        		$("#lblLocName").val(response.strLocName);
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
			searchUrlgurl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	$("#txtProdCode").val(response.strProdCode);
			    	$("#txtProdName").val(response.strProdName);
			    },
				error: function(e)
			    {
			       	alert('Error:=' + e);
			    }
		    });
		}
		
		
	</script>
</head>

<body onload="funResetFields()">
	<s:form name="openingStk" method="POST" action="saveOpeningStk.html">
		<table>
		    <tr>
		    	<td><label>Product Code:</label></td>
		        <td>
		        	<s:input type="text" id="txtProdCode" name="prodCode" path="strProdCode" ondblclick="funHelp('productmaster')" required="true"/>
		        	<s:errors path="strProdCode"></s:errors>
		        </td>
		    	<td><label>Product Name:</label></td>
		    	<td><input id="txtProdName" name="prodName" /></td>
		    </tr>
		    	
		    <tr>
		    	<td><label>Location Code:</label></td>
		        <td>
		        	<s:input type="text" id="txtLocCode" name="locCode" path="strLocCode" ondblclick="funHelp('locationmaster')" required="true"/>
		        	<s:errors path="strLocCode"></s:errors>
		        </td>
		        <td><label>Location Name:</label></td>
		    	<td><input id="txtLocName" name="locName" /></td>
		    </tr>
			    
		    <tr>
		    	<td><label>Qty:</label></td>
			    <td>
			    	<s:input type="number" step="0.01" id="txtQuantity" name="qty" path="dblQty" required="true"/>
			    	<s:errors path="dblQty"></s:errors>
			    </td>
			    <td><label>UOM:</label></td>
			    <td><s:input id="txtUOM" name="uom" path="strUOM"/></td>
			</tr>
			    
			<tr>
		    	<td><label>Cost Per Unit:</label></td>
			    <td>
			    	<s:input id="txtCostPUnit" name="costPerUnit" path="dblCostPUnit"/>
			    	<s:errors path="dblCostPUnit"></s:errors>
			    </td>
			    <td><label>Revision Level:</label></td>
			    <td>
			    	<s:input id="txtRevLevel" name="revLevel" path="dblRevLvl"/>
			    	<s:errors path="dblRevLvl"></s:errors>
			    </td>
			</tr>
			  
			<tr>
		    	<td><label>Lot No:</label></td>
			    <td><s:input id="lotNo" name="lotNo" path="strLotNo" /></td>
			    <td><label>Expiry Date:</label></td>
			    <td>
			    	<s:input type="text" id="txtExpDate" name="expDate" path="dtExpDate" required="true"/>
			    	<s:errors path="dtExpDate"></s:errors>
			    </td>
			</tr>
			 	   
			<tr>
			    <td ><input type="submit" value="Submit" onclick="return funValidateFields()"/></td>
			    <td ><input type="reset" value="RESET" onclick="funResetFields()"/></td>
		    </tr>
		</table>
	</s:form>
</body>
</html>