<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SUPPLIER MASTER</title>
<tab:tabConfig/>
	<script type="text/javascript">
	
	var fieldName1;
	var posItemCode;
	
	function funValidateFields()
	{		
		 if(!funCheckNull($("#partyName").val(),"Party Name"))
		{
			$("#partyName").focus();
			return false;
		}
		return true;
    }	
	
	
	
	function funSetAdd()
	{		
		if(document.getElementById("chkShipAdd").checked==true)
		{
			document.getElementById("shipAdd1").value=document.getElementById("billAdd1").value;
			document.getElementById("shipAdd2").value=document.getElementById("billAdd2").value;
			document.getElementById("shipCity").value=document.getElementById("billCity").value;
			document.getElementById("shipState").value=document.getElementById("billState").value;
			document.getElementById("shipPin").value=document.getElementById("billPin").value;
			document.getElementById("shipCountry").value=document.getElementById("billCountry").value;
		}
		else
		{			
			document.getElementById("shipAdd1").value="";
			document.getElementById("shipAdd2").value="";
			document.getElementById("shipCity").value="";
			document.getElementById("shipState").value="";
			document.getElementById("shipPin").value="";
			document.getElementById("shipCountry").value="";
		}
	}	
	
	function funResetFields()
	{
		document.getElementById("partyCode").value=""; 
		document.getElementById("partyName").value="";
		document.getElementById("manualCode").value="";
	    document.getElementById("phone").value="";	    
	    document.getElementById("mobile").value="";
        document.getElementById("fax").value="";
        document.getElementById("contact").value="";
        document.getElementById("email").value=""; 
        document.getElementById("bankName").value=""; 
        document.getElementById("bankAdd1").value=""; 
        document.getElementById("bankAdd2").value="";
        document.getElementById("bankAccountNo").value="";
        document.getElementById("bankABANo").value="";
        document.getElementById("ibanNo").value="";
        document.getElementById("swiftCode").value="";
        document.getElementById("taxNo1").value="";
        document.getElementById("taxNo2").value="";
        document.getElementById("cst").value="";
        document.getElementById("vat").value="";
        document.getElementById("excise").value="";
        document.getElementById("serviceTax").value=""; 
        document.getElementById("partyType").value=""; 
        document.getElementById("acCrCode").value=""; 
        document.getElementById("creditDays").value="0"; 
        document.getElementById("creditLimit").value="0.0";
        document.getElementById("registration").value="";
        document.getElementById("range").value="";
        document.getElementById("division").value="";
        document.getElementById("commissionerate").value="";
        document.getElementById("category").value="";
        document.getElementById("excisable").value="";
        document.getElementById("mainAdd1").value="";
        document.getElementById("mainAdd2").value="";
        document.getElementById("mainCity").value="";
        document.getElementById("mainState").value="";
        document.getElementById("mainCountry").value="";
        document.getElementById("mainPin").value="";
        document.getElementById("billAdd1").value="";
        document.getElementById("billAdd2").value="";
        document.getElementById("billCity").value="";
        document.getElementById("billState").value="";
        document.getElementById("billCountry").value="";
        document.getElementById("billPin").value="";
        document.getElementById("shipAdd1").value="";
        document.getElementById("shipAdd2").value="";
        document.getElementById("shipCity").value="";
        document.getElementById("shipState").value="";
        document.getElementById("shipCountry").value="";
        document.getElementById("shipPin").value="";
    }
	
	function funHelp(transactionName)
	{
		fieldName1=transactionName;
        window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
    }

	$(function()
	{
		$("#partyCode").blur(function()
		{
		    fieldName1='subgroup';
		    gurl=getContextPath()+"/loadSupplierMasterData.html?partyCode=";
			funSetData($('#partyCode').val());
		});				
	});	
	
	function funSetData(code)
	{
		if(fieldName1=='suppliermaster')
		{
			document.getElementById("partyCode").value=code;
			gurl=getContextPath()+"/loadSupplierMasterData.html?partyCode=";
		}
		if(fieldName1=='productmaster')
		{
			gurl=getContextPath()+"/loadProductMasterData.html?prodCode=";
		}
		
		$.ajax({
			        type: "GET",			        
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(fieldName1=='suppliermaster')
						{
							document.getElementById("partyName").value=response.strPName;
							document.getElementById("manualCode").value=response.strManualCode;
							document.getElementById("phone").value=response.strPhone;
							document.getElementById("mobile").value=response.strMobile;
							document.getElementById("fax").value=response.strFax;
							document.getElementById("contact").value=response.strContact;
							document.getElementById("email").value=response.strEmail;
							document.getElementById("bankName").value=response.strBankName;
							document.getElementById("bankAdd1").value=response.strBankAdd1;
							document.getElementById("bankAdd2").value=response.strBankAdd2;
							document.getElementById("bankAccountNo").value=response.strBankAccountNo;
							document.getElementById("bankABANo").value=response.strBankABANo;
							document.getElementById("ibanNo").value=response.strIBANNo;
							document.getElementById("swiftCode").value=response.strSwiftCode;
							document.getElementById("taxNo1").value=response.strTaxNo1;
							document.getElementById("taxNo2").value=response.strTaxNo2;
							document.getElementById("vat").value=response.strVAT;
							document.getElementById("cst").value=response.strCST;
							document.getElementById("excise").value=response.strExcise;
							document.getElementById("serviceTax").value=response.strServiceTax;
							document.getElementById("partyType").value=response.strPartyType;
							document.getElementById("acCrCode").value=response.strAcCrCode;
							document.getElementById("creditDays").value=response.intCreditDays;
							document.getElementById("creditLimit").value=response.dblCreditLimit;
							document.getElementById("registration").value=response.strRegistration;
							document.getElementById("range").value=response.strRange;
							document.getElementById("division").value=response.strDivision;
							document.getElementById("commissionerate").value=response.strCommissionerate;
							document.getElementById("category").value=response.strCategory;
							document.getElementById("excisable").value=response.strExcisable;
							document.getElementById("mainAdd1").value=response.strMAdd1;
							document.getElementById("mainAdd2").value=response.strMAdd2;
							document.getElementById("mainCity").value=response.strMCity;
							document.getElementById("mainState").value=response.strMState;
							document.getElementById("mainPin").value=response.strMPin;
							document.getElementById("mainCountry").value=response.strMCountry;
							document.getElementById("billAdd1").value=response.strBAdd1;
							document.getElementById("billAdd2").value=response.strBAdd2;
							document.getElementById("billCity").value=response.strBCity;
							document.getElementById("billState").value=response.strBState;
							document.getElementById("billPin").value=response.strBPin;
							document.getElementById("billCountry").value=response.strBCountry;
							document.getElementById("shipAdd1").value=response.strSAdd1;
							document.getElementById("shipAdd2").value=response.strSAdd2;
							document.getElementById("shipCity").value=response.strSCity;
							document.getElementById("shipState").value=response.strSState;
							document.getElementById("shipPin").value=response.strSPin;
							document.getElementById("shipCountry").value=response.strSCountry;							
						}
			        	else if(fieldName1=='productmaster')
			        	{
			        		document.getElementById("txtProdCode").value=response.strProdCode;
			        		document.getElementById("lblProdName").innerHTML=response.strProdName;
				        	posItemCode=response.strPartNo;
			        	}
					},
			        error: function(e)
			        {				        	
			          	alert('Error121212: ' + e);
			        }
		      });
		 
		 
		 	}
	
	function funAddRow() 
	{
		 if(!funCheckNull($("#txtProdCode").val(),"Product Code"))
			{
				$("#txtProdCode").focus();
				return false;
			}
		
		 if(!funValidateNumeric($("#txtAmount").val()))
			{
				$("#txtAmount").focus();
				return false;
			}
		 
		var prodCode = document.getElementById("txtProdCode").value;
	    var amount = document.getElementById("txtAmount").value;
	    var itemName = document.getElementById("lblProdName").value;
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);			    
	    
	    
	    row.insertCell(0).innerHTML= "<input name=\"listBomDtlModel["+(rowCount-2)+"].strProdCode\" id=\"txtProdCode."+(rowCount-2)+"\" value="+prodCode+" ondblclick=\"funHelp('prodcode')\">";		    
	    row.insertCell(1).innerHTML= posItemCode;
	    row.insertCell(2).innerHTML= itemName;
	    row.insertCell(3).innerHTML= "<input name=\"listBomDtlModel["+(rowCount-2)+"].dblAmount\" id=\"txtAmount."+(rowCount-2)+"\" value="+amount+">";			    
	    row.insertCell(4).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
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
	<s:form name="suppliermasterForm" method="POST" action="saveSupplierMaster.html">
	
		<tab:tabContainer id="Master">
		<tab:tabPane id="general" tabTitle="GENERAL">
		<table>
			<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		    	<tr>
		        <td><s:label path="strPCode" >Supplier Code :</s:label></td>
		        <td><s:input id="partyCode" name="partyCode"  path="strPCode" ondblclick="funHelp('suppliermaster')"  /></td>
		    
		    	<td><s:label path="strManualCode" >Manual Code :</s:label></td>
		        <td><s:input id="manualCode" name="manualCode" path="strManualCode"/></td>
		    
		   		</tr>
		    	
		    	<tr>
		        <td><s:label path="strPName" >Name  :</s:label></td>
		        <td colspan="3"><s:input type="text" id="partyName" name="partyName" path="strPName" required="true"/></td>
		   		</tr>
		    
		    	<tr>
		        <td><s:label path="strPhone" >Tel No.:</s:label></td>
		        <td><s:input  pattern="[789][0-9]{9}"  id="phone" name="phone" path="strPhone"  /></td>
		   		 
		        <td><s:label path="strMobile" > Mobile No.  :</s:label></td>
		        <td><s:input pattern="[789][0-9]{9}" id="mobile" name="mobile" path="strMobile"/></td>
		 		</tr>
		    
		    	<tr>
		    	<td><s:label path="strFax" >Fax:</s:label></td>
		        <td><s:input id="fax" name="fax" path="strFax"/></td>
		    	<td><s:label path="strContact" >Contact Pesrson:</s:label></td>
		        <td><s:input id="contact" name="contact" path="strContact"/></td>
		    
		  		 </tr>
		   
		   		<tr>
		        <td><s:label path="strEmail" >Email  :</s:label></td>
		        <td colspan="3"><s:input type="email" id="email" name="email" path="strEmail"/></td>
		   		</tr>
		   		
		   		<tr>
		    	<td><s:label path="strBankName" >Bank Name  :</s:label></td>
		        <td colspan="3"><s:input id="bankName" name="bankName" path="strBankName"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strBankAdd1" >Bank Address Line 1  :</s:label></td>
		        <td colspan="3"><s:input id="bankAdd1" name="bankAdd1" path="strBankAdd1"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strBankAdd2" >Bank Address Line 2  :</s:label></td>
		
       <td colspan="3"><s:input id="bankAdd2" name="bankAdd2" path="strBankAdd2"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strBankAccountNo" >Bank Account No.:</s:label></td>
		        <td><s:input id="bankAccountNo" name="bankAccountNo" path="strBankAccountNo"/></td>
		    	<td><s:label path="strBankABANo" >ABA No.:</s:label></td>
		        <td><s:input id="bankABANo" name="bankABANo" path="strBankABANo"/></td>
		  		 </tr>
		  		 
		    	<tr>
		    	<td><s:label path="strIBANNo" >IBAN No:</s:label></td>
		        <td><s:input id="ibanNo" name="ibanNo" path="strIBANNo"/></td>
		    	<td><s:label path="strSwiftCode" >Bank Swift Code:</s:label></td>
		        <td><s:input id="swiftCode" name="swiftCode" path="strSwiftCode"/></td>
		  		 </tr>
		  		 
		  		 <tr>
		    	<td><s:label path="strTaxNo1" >Tax No. 1:</s:label></td>
		        <td><s:input id="taxNo1" name="taxNo1" path="strTaxNo1"/></td>
		    	<td><s:label path="strTaxNo2" >Tax No. 2:</s:label></td>
		        <td><s:input id="taxNo2" name="taxNo2" path="strTaxNo2"/></td>
		  		 </tr>
		  		 
		  		 <tr>
		    	<td><s:label path="strCST" >CST No.:</s:label></td>
		        <td><s:input id="cst" name="cst" path="strCST"/></td>
		    	<td><s:label path="strVAT" >VAT:</s:label></td>
		        <td><s:input id="vat" name="vat" path="strVAT"/></td>
		  		 </tr>
		  		 
		  		 
			     <tr>
		    	<td><s:label path="strExcise" >Excise No.:</s:label></td>
		        <td><s:input id="excise" name="excise" path="strExcise"/></td>
		    	<td><s:label path="strServiceTax" >Service Tax No.:</s:label></td>
		        <td><s:input id="serviceTax" name="serviceTax" path="strServiceTax"/></td>
		  		 </tr>
		  		 
			    <tr>
		    	<td><s:label path="strPartyType" >Supplier Type:</s:label></td>
		        <td><s:select id="partyType" name="partyType" path="strPartyType" items="${typeList}"/></td>
		    	
		    	<td><s:label path="strAcCrCode" >A/C Creditors Code:</s:label></td>
		        <td><s:input id="acCrCode" name="acCrCode" path="strAcCrCode"/></td>
		  		 </tr>
		  		 
		  		  <tr>
		    	<td><s:label path="intCreditDays" >Credit Days:</s:label></td>
		        <td><s:input id="creditDays" name="creditDays" path="intCreditDays"/></td>
		    	<td><s:label path="dblCreditLimit" >Credit Limit:</s:label></td>
		        <td><s:input id="creditLimit" name="creditLimit" path="dblCreditLimit"/></td>
		  		 </tr>
		  		 
		  		  <tr>
		    	<td><s:label path="strRegistration" >Registration No.:</s:label></td>
		        <td><s:input id="registration" name="registration" path="strRegistration"/></td>
		    	<td><s:label path="strRange" >Range:</s:label></td>
		        <td><s:input id="range" name="range" path="strRange"/></td>
		  		 </tr>
		  		 
		  		  <tr>
		    	<td><s:label path="strDivision" >Division:</s:label></td>
		        <td><s:input id="division" name="division" path="strDivision"/></td>
		    	<td><s:label path="strCommissionerate" >Commissionerate:</s:label></td>
		        <td><s:input id="commissionerate" name="commissionerate" path="strCommissionerate"/></td>
		  		 </tr>
		  		 <!-- problem -->
		  		 <tr>
		    	<td><s:label path="strCategory" >Category:</s:label></td>
		        <td><s:select id="category" name="category" path="strCategory" items="${categoryList}"/></td>
		    	<td><s:label path="strExcisable" >Party Indicator:</s:label></td>
		        <td><s:select id="excisable" name="excisable" path="strExcisable" items="${partyIndicatorList}"/></td>
		  		 </tr>
		  		 
		  		 
			
		</table>
	</tab:tabPane>

	<tab:tabPane id="address" tabTitle="ADDRESS">
		<table>
			
		
				<tr><th>Main Address</th></tr>
				<tr>
		    	<td><s:label path="strMAdd1">  Address Line 1  :</s:label></td>
		        <td colspan="3"><s:input id="mainAdd1" name="mainAdd1" path="strMAdd1"/></td>
		    	</tr>
		    	<tr>
		    	<td><s:label path="strMAdd2">  Address Line 2  :</s:label></td>
		        <td colspan="3"><s:input id="mainAdd2" name="mainAdd2" path="strMAdd2"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strMCity"> City:</s:label></td>
		        <td ><s:input id="mainCity" name="mainCity" path="strMCity"/></td>
		        <td><s:label path="strMState"> State:</s:label></td>
		        <td ><s:input id="mainState" name="mainState" path="strMState"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strMCountry"> Country:</s:label></td>
		        <td ><s:input id="mainCountry" name="mainCountry" path="strMCountry"/></td>
		        <td><s:label path="strMPin"> Pin:</s:label></td>
		        <td ><s:input pattern="[0-9]{6}" id="mainPin" name="mainPin" path="strMPin"/></td>
		    	</tr>
		    	
		    	
		
				<tr><th>Billing Address</th></tr>
				<tr>
		    	<td><s:label path="strBAdd1">  Address Line 1  :</s:label></td>
		        <td colspan="3"><s:input id="billAdd1" name="billAdd1" path="strBAdd1"/></td>
		    	</tr>
		    	<tr>
		    	<td><s:label path="strBAdd2">  Address Line 2  :</s:label></td>
		        <td colspan="3"><s:input id="billAdd2" name="billAdd2" path="strBAdd2"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strBCity"> City:</s:label></td>
		        <td ><s:input id="billCity" name="billCity" path="strBCity"/></td>
		        <td><s:label path="strBState"> State:</s:label></td>
		        <td ><s:input id="billState" name="billState" path="strBState"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strBCountry"> Country:</s:label></td>
		        <td ><s:input id="billCountry" name="billCountry" path="strBCountry"/></td>
		        <td><s:label path="strBPin"> Pin:</s:label></td>
		        <td ><s:input pattern="[0-9]{6}" id="billPin" name="billPin" path="strBPin"/></td>
		    	</tr>
		    	
		    	
				<tr><th>Shipping Address</th>
				<td><s:label path="">  same as billing address  :</s:label></td>
		        <td ><s:checkbox id="chkShipAdd" name="chkShipAdd" path="" value="" onclick="funSetAdd()"/></td>
				</tr>
				<tr>
		    	<td><s:label path="strSAdd1">  Address Line 1  :</s:label></td>
		        <td colspan="3"><s:input id="shipAdd1" name="shipAdd1" path="strSAdd1"/></td>
		    	</tr>
		    	<tr>
		    	<td><s:label path="strSAdd2">  Address Line 2  :</s:label></td>
		        <td colspan="3"><s:input id="shipAdd2" name="shipAdd2" path="strSAdd2"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strSCity"> City:</s:label></td>
		        <td ><s:input id="shipCity" name="shipCity" path="strSCity"/></td>
		        <td><s:label path="strSState"> State:</s:label></td>
		        <td ><s:input id="shipState" name="shipState" path="strSState"/></td>
		    	</tr>
		    	
		    	<tr>
		    	<td><s:label path="strSCountry"> Country:</s:label></td>
		        <td ><s:input id="shipCountry" name="shipCountry" path="strSCountry"/></td>
		        <td><s:label path="strSPin"> Pin:</s:label></td>
		        <td ><s:input pattern="[0-9]{6}" id="shipPin" name="shipPin" path="strSPin"/></td>
		    	</tr>		    	
		</table>
	
	</tab:tabPane>
				
	<tab:tabPane id="product" tabTitle="PRODUCT">
					
		<table id="tblProduct">
			<tr>
				<th><label>Product Code:</label></th>
				<th><label>Amount:</label></th>
			</tr>
				
			<tr>
				<td><input id="txtProdCode" ondblclick="funHelp('productmaster')" ></input></td>
				<td><label id="lblProdName"></label></td>
				<td><input id="txtAmount"></input></td>
				<td><input id="btnAdd" type="submit" value="Add" onclick="return funAddRow()"></input></td>
			</tr>
				
			<c:forEach items="${productdtl.listBomDtlModel}" var="recipe" varStatus="status">
				<tr>
					<sp:bind path="productdtl.listBomDtlModel[${status.index}].strProdCode">
						<td><input name="listBomDtlModel[${status.index}].strProdCode" value="${recipe.strProdCode}"/></td>
					</sp:bind>
					
					<sp:bind path="productdtl.listBomDtlModel[${status.index}].dblAmount">
						<td><input name="listBomDtlModel[${status.index}].dblAmount" value="${recipe.dblAmount}"/></td>
					</sp:bind>
					
					<td><input type="submit" value="Add" /></td>
				</tr>
			</c:forEach>
		</table>	
	</tab:tabPane>
	</tab:tabContainer>
		<table>
		<tr>
			    <td ><input type="submit" value="Submit" onclick="return funValidateFields()"/></td>
			    <td ><input type="reset" value="RESET" onclick="funResetFields()"/></td>
		</tr>
	</table>
	
</s:form>
</body>
</html>