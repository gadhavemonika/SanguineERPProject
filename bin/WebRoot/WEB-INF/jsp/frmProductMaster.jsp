<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PRODUCT MASTER</title>
<tab:tabConfig/>
	<script type="text/javascript">	 

	$(function() {
		 $("#lastModified").datepicker();
		 
		 });	 
	
		var fieldName1;
		
		function funResetFields()
		{   
			document.getElementById("forSale").checked=false;
			document.getElementById("expDate").checked=false;
			document.getElementById("lotNo").checked=false;
			document.getElementById("revLevel").checked=false;
			document.getElementById("notInUse").checked=false;
			document.getElementById("exceedPO").checked=false;
			document.getElementById("stagDel").checked=false;
			document.getElementById("slNo").checked=false;
			document.getElementById("prodCode").value="";
	        document.getElementById("prodName").value="";
	        document.getElementById("partNo").value="";
	        document.getElementById("prodType").value="";
	        document.getElementById("calAmtOn").value="";
	        document.getElementById("locCode").value="";
	        document.getElementById("uom").value="";
	        document.getElementById("wtUOM").value=""; 
	        document.getElementById("binNo").value=""; 
	        document.getElementById("class").value=""; 
	        document.getElementById("tariffNo").value=""; 
	        document.getElementById("subGroupCode").value="";
	        document.getElementById("taxIndicator").value=""; 
	        document.getElementById("bomCal").value=""; 
	        document.getElementById("weight").value="";
	        document.getElementById("costManu").value="0.0";
	        document.getElementById("batchQty").value="0.0";
	        document.getElementById("delPeriod").value="0"; 
			document.getElementById("listPrice").value="0.0";
	        document.getElementById("maxLvl").value="0.0";
	        document.getElementById("orduptoLvl").value="0.0";
	        document.getElementById("reorderLvl").value="0.0";
	        document.getElementById("costRM").value="0.0";
	        document.getElementById("unitPrice").value="0.0";
	        document.getElementById("reorderLvl").value="0.0";	       
		}
	
		function funHelp(transactionName)
		{
			fieldName1=transactionName;
			
			if(fieldName1=='productmaster')
			{
				gurl=getContextPath()+"/loadProductMasterData.html?prodCode=";
			}
			else if(fieldName1=='subgroup')
			{
				gurl=getContextPath()+"/loadSubGroupMasterData.html?subGroupCode=";
			}
			
			else if(fieldName1=='locationmaster')
			{
				gurl=getContextPath()+"/loadLocationMasterData.html?LocCode=";
			}
	        window.open("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		
		function funSetData(code)
		{
			if(fieldName1=='productmaster')
			{
				document.getElementById("prodCode").value=code;
				gurl=getContextPath()+"/loadProductMasterData.html?prodCode=";
			}
			
			else if(fieldName1=='subgroup')
			{
				document.getElementById("subGroupCode").value=code;
				gurl=getContextPath()+"/loadSubGroupMasterData.html?subGroupCode=";
			}			
			if(fieldName1=='locationmaster')
			{
				document.getElementById("locCode").value=code;
			}
			
			 $.ajax({
			        type: "GET",			        
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {			        	
			        	if(fieldName1=='productmaster')
						{
			        		document.getElementById("prodName").value=response.strProdName;							
						}			        	
					},					
					error: function(e)
					{				        	
				    	alert('Error121212: ' + e);
					}
		      });
		}
	</script>

</head>
<body onload="funResetFields()">
	<s:form name="productmasterForm" method="POST" action="saveProductMaster.html" enctype="multipart/form-data">
		<tab:tabContainer id="Master">


				<tab:tabPane id="general" tabTitle="GENERAL">
		<table>
			<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
		    	<tr>
		        <td><s:label path="strProdCode" >Code :</s:label></td>
		        <td><s:input  id="prodCode" name="prodCode" path="strProdCode" value="" ondblclick="funHelp('productmaster')" /></td>
		    
		    	<td><s:label path="strProdName" >Name :</s:label></td>
		        <td><s:input type="text" id="prodName" name="prodName" path="strProdName" required="true"/></td>
		    
		   		</tr>
		    	
		    	<tr>
		        <td><s:label path="strPartNo" >POS Item Code  :</s:label></td>
		        <td><s:input type="text" id="partNo" name="partNo" path="strPartNo"  required="true"/></td>
		 
		    	<td><s:label path="strUOM" >UOM:</s:label></td>
		        <td><s:input id="uom" name="uom" path="strUOM"/></td>
		    
		   		</tr>
		    
		    	<tr>
		        <td><s:label path="strSGCode" >Sub-Group Code:</s:label></td>
		        <td><s:input id="subGroupCode" name="subGroupCode" path="strSGCode" ondblclick="funHelp('subgroup')" readonly="true"/></td>
		   		 </tr>
			    
		    	<tr>
		        <td><s:label path="dblCostRM" >Purchase Price  :</s:label></td>
		        <td><s:input id="costRM" name="costRM" path="dblCostRM"/></td>
		 
		    	<td><s:label path="dblCostManu" >Cost of Manf/Unit:</s:label></td>
		        <td><s:input id="costManu" name="costManu" path="dblCostManu"/></td>
		    
		  		 </tr>
		   
		   		<tr>
		        <td><s:label path="strLocCode" >Location Code :</s:label></td>
		        <td><s:input id="locCode" name="locCode" path="strLocCode" ondblclick="funHelp('locationmaster')" readonly="true"/></td>
		    	</tr>
			    
			 	<tr>
		        <td><s:label path="dblOrduptoLvl" >Reorder Qty  :</s:label></td>
		        <td><s:input id="orduptoLvl" name="orduptoLvl" path="dblOrduptoLvl"/></td>
		 
		    	<td><s:label path="dblReorderLvl" >Minimum Level:</s:label></td>
		        <td><s:input id="reorderLvl" name="reorderLvl" path="dblReorderLvl"/></td>
		    
		  		 </tr>
			  
			  	<tr>
		        <td><s:label path="strType" >Item Type:</s:label></td>
		        <td><s:select id="prodType" name="prodType" path="strProdType" items="${typeList}"/></td>
		 
		    	<td><s:label path="strCalAmtOn" >Calulation of amount On:</s:label></td>
		        <td><s:select id="calAmtOn" name="calAmtOn" path="strCalAmtOn" items="${calAmtOnList}"/></td>
		    
		   		</tr>
		   
		  		 <tr>
		        <td><s:label path="dblWeight" >Weight:</s:label></td>
		        <td><s:input id="weight" name="weight" path="dblWeight"/></td>
		 
		    	<td><s:label path="strWtUOM" >Wt UOM:</s:label></td>
		        <td><s:input id="wtUOM" name="wtUOM" path="strWtUOM"/></td>
		    
		  		 </tr>
		   
		  		 <tr>
		        <td><s:label path="dblBatchQty" >Quatity in a Batch:</s:label></td>
		        <td><s:input id="batchQty" name="batchQty" path="dblBatchQty"/></td>
		 
		    	<td><s:label path="dblMaxLvl" >Maximum Level:</s:label></td>
		        <td><s:input id="maxLvl" name="maxLvl" path="dblMaxLvl"/></td>
		    
		  		 </tr>
		   
		   		<tr>
		        <td><s:label path="strBinNo" >bin No.:</s:label></td>
		        <td><s:input id="binNo" name="binNo" path="strBinNo"/></td>
		 
		    	<td><s:label path="strClass" >Class:</s:label></td>
		        <td><s:select id="class" name="class" path="strClass" items="${classList}"/></td>
		    
		   		</tr>
		   
		  		 <tr>
		        <td><s:label path="strTariffNo" >Tariff No.:</s:label></td>
		        <td><s:input id="tariffNo" name="tariffNo" path="strTariffNo"/></td>
		 
		    	<td><s:label path="dblListPrice" >List Price:</s:label></td>
		        <td><s:input id="listPrice" name="listPrice" path="dblListPrice"/></td>
		    
		  		 </tr>
			  
			   
			
		</table>
	</tab:tabPane>

	<tab:tabPane id="tracking" tabTitle="TRACKING">
		<table>
			
			<tr>
				<td>
			    	<s:checkbox id="notInUse" name="notInUse" path="strNotInUse" value=""/>
			    	<s:errors path="strNotInUse"></s:errors>
				</td>
				<td>
			    	<s:label path="strNotInUse">Item Not In Use</s:label>
			    </td>
				
				<td>
			    	<s:checkbox id="expDate" name="expDate" path="strExpDate" value="" />
			    	<s:errors path="strExpDate"></s:errors>
				</td>
				<td>
			    	<s:label path="strExpDate">Expiry Date</s:label>
			    </td>
			
				<td>
			    	<s:checkbox id="lotNo" name="lotNo" path="strLotNo" value="" />
			    	<s:errors path="strLotNo"></s:errors>
				</td>
				<td>
			    	<s:label path="strLotNo">Batch No</s:label>
			    </td>
			    
			    <td>
			    	<s:checkbox id="revLevel" name="revLevel" path="strRevLevel" value="" />
			    	<s:errors path="strRevLevel"></s:errors>
				</td>
				<td>
			    	<s:label path="strRevLevel">Revision Level</s:label>
			    </td>
				
			</tr>
			
			<!-- new row -->
			<tr>
				<td>
			    	<s:checkbox id="slNo" name="slno" path="strSlNo" value="" />
			    	<s:errors path="strSlNo"></s:errors>
				</td>
				<td>
			    	<s:label path="strSlNo">Serial No.</s:label>
			    </td>
				
				<td>
			    	<s:checkbox id="exceedPO" name="exceedPO" path="strExceedPO" value="" />
			    	<s:errors path="strExceedPO"></s:errors>
				</td>
				<td>
			    	<s:label path="strExceedPO">Allowed Exceed PO</s:label>
			    </td>
			
				<td>
			    	<s:checkbox id="stagDel" name="stagDel" path="strStagDel" value="" />
			    	<s:errors path="strStagDel"></s:errors>
				</td>
				<td>
			    	<s:label path="strStagDel">Stagger Delivery</s:label>
			    </td>
			    
			    
				
			</tr>
			
			<!-- new row -->
			<tr>
				
				<td>
			    	<s:label path="strTaxIndicator">Tax Indicator</s:label>
			    </td>
				<td>
			    	<s:select id="taxIndicator" name="taxIndicator" path="strTaxIndicator" items="${taxIndicatorList}" />
			    	<s:errors path="strTaxIndicator"></s:errors>
				</td>
				
				<td>
			    	<s:label path="intDelPeriod">Delivery Period</s:label>
			    </td>
				<td>
			    	<s:input id="delPeriod" name="delPeriod" path="intDelPeriod" />
			    	<s:errors path="intDelPeriod"></s:errors>
				</td>
					
			</tr>		
		
			<!-- new row -->
			<tr>
				<td>
			    	<s:checkbox id="forSale" name="forSale" path="strForSale" value="" />
			    	<s:errors path="strForSale"></s:errors>
				</td>
				<td>
			    	<s:label path="strForSale">Item For Sale</s:label>
			    </td>
				
				<td>
			    	<s:label path="strBomCal">BOM Calculation Upto</s:label>
			    </td>
				<td>
			    	<s:select id="bomCal" name="bomCal" path="strBomCal" items="${bomCalList}" />
			    	<s:errors path="strBomCal"></s:errors>
				</td>
			
			</tr>	
			
			<!-- new row -->
			<tr>
				
				<td><!-- dataTypeNotFound -->
			    	<s:label path="">Customer Item Code</s:label>
			    </td>
			    <td>
			    	<s:input id="" name="" path="" />
			    	<s:errors path=""></s:errors>
				</td>
				
				<td>
			    	<s:label path="dblUnitPrice">Unit Price</s:label>
			    </td>
				<td>
			    	<s:input id="unitPrice" name="unitPrice" path="dblUnitPrice" />
			    	<s:errors path="dblUnitPrice"></s:errors>
				</td>
			
			</tr>	
				
			<tr>
				<td>
			    	<s:label path="strDesc">Description</s:label>
			    </td>
				<td>
			    	<s:input id="Desc" name="Desc" path="strDesc" />
			    	<s:errors path="strDesc"></s:errors>
				</td>
			</tr>		
		
		
		</table>
	
	
	
	
	
	</tab:tabPane>
				
	<tab:tabPane id="specification" tabTitle="SPECIFICATION">
		<table>
		<!-- new row -->
		<tr>
				<td>
			    	<s:label path="strSpecification">Specification</s:label>
			    </td>
		</tr>
		<tr>		
				<td>
			    	<s:input id="Specification" name="Specification" path="strSpecification" />
			    	<s:errors path="strSpecification"></s:errors>
				</td>
		</tr>
		</table>	
	</tab:tabPane>
	
	<tab:tabPane id="supplier" tabTitle="SUPPLIER">
		<table>
		<!-- new row -->
		<tr>
				<td>
			    	<label >Supplier ID:</label>
			    </td>
				
				<td>
			    	<s:input id="supplierId" name="supplierId" path="strSuppCode" />
			    	<s:errors path="strSuppCode"></s:errors>
				</td>
	
				<td>
			    	<s:label path="">Supplier Name:</s:label>
			    </td>
				
				<td>
			    	<s:input id="supplierName" name="supplierName" path="" />
			    	<s:errors path=""></s:errors>
				</td>
		</tr>
		
		<!-- new row -->
		<tr>
				<td>
			    	<s:label path="">Last Cost:</s:label>
			    </td>
				
				<td>
			    	<s:input id="lastCost" name="lastCost" path="dblLastCost" />
			    	<s:errors path="dblLastCost"></s:errors>
				</td>
	
				<td>
			    	<s:label path="strSuppUOM">UOM:</s:label>
			    </td>
				
				<td>
			    	<s:input id="suppUOM" name="suppUOM" path="strSuppUOM" />
			    	<s:errors path="strSuppUOM"></s:errors>
				</td>
		</tr>
		<!-- new row -->
		<tr>
				
				<td><s:label path="dtLastDate">Last Date:</s:label></td>
			    <td><s:input id="lastModified" name="lastModified" path="dtLastDate"/></td>
				
				
				
				<td>
			    	<s:label path="strLeadTime">Lead Time in Days:</s:label>
			    </td>
				<td>
			    	<s:input id="leadTimeInDays" name="LeadTimeInDays" path="strLeadTime" />
			    	<s:errors path="strLeadTime"></s:errors>
				</td>
		</tr>
		
		<!-- new row -->
		<tr>
				<td>
			    	<s:label path="strSuppId">Supplier Item Code :</s:label>
			    </td>
				
				<td>
			    	<s:input id="supplierItemCode" name="supplierItemCode" path="strSuppId" />
			    	<s:errors path="strSuppId"></s:errors>
				</td>
	
				<td>
			    	<s:label path="strSuppPartDesc">Description:</s:label>
			    </td>
				
				<td>
			    	<s:input id="description" name="description" path="strSuppPartDesc" />
			    	<s:errors path="strSuppPartDesc"></s:errors>
				</td>
		</tr>
		<!-- new row -->
		<tr>
				<td>
			    	<s:label path="dblMaxQty">Minimum Quantity :</s:label>
			    </td>
				
				<td>
			    	<s:input id="minimumQuantity" name="minimumQuantity" path="dblMaxQty" />
			    	<s:errors path="dblMaxQty"></s:errors>
				</td>
	
				
				
				
				<td>
			    	<s:label path="strDefault">Default:</s:label>
			    </td>
			    <td>
			    	<s:checkbox id="default" value="" name="default" path="strDefault" />
			    	<s:errors path="strDefault"></s:errors>
				</td>
		</tr>
		
		
		</table>	
	</tab:tabPane>
	
	<tab:tabPane id="custom" tabTitle="CUSTOM">
	<table>
	<!-- new row -->
	<tr>
				<td>
			    	<s:label path="">Attribute  :</s:label>
			    </td>
				<td>
			    	<s:input id="atribute" name="attribute" path="strAttCode" />
			    	<s:errors path="strAttCode"></s:errors>
				</td>
					<td>
			    	<s:label path="">Value:</s:label>
			    </td>
				<td>
			    	<s:input id="value" name="value" path="dblAttValue" />
			    	<s:errors path="dblAttValue"></s:errors>
				</td>
				
		</tr>
		
	</table>		
	</tab:tabPane>
	
	<tab:tabPane id="process" tabTitle="PROCESS">
		<table>
	<!-- new row -->
	<tr>
				<td>
			    	<s:label path="">Process  :</s:label>
			    </td>
				<td>
			    	<s:input id="process1" name="process1" path="strProcessCode" />
			    	<s:errors path="strProcessCode"></s:errors>
				</td>
					<td>
			    	<s:label path="">Weight:</s:label>
			    </td>
				<td>
			    	<s:input id="processweight" name="processweight" path="dblProcessWeight" />
			    	<s:errors path="dblProcessWeight"></s:errors>
				</td>
				
					<td>
			    	<s:label path="">Cycle Time:</s:label>
			    </td>
				<td>
			    	<s:input id="cycleTime" name="cycleTime" path="dblCycleTime" />
			    	<s:errors path="dblCycleTime"></s:errors>
				</td>
				
		</tr>
		
	</table>	
	</tab:tabPane>
	
	<tab:tabPane id="characteristics" tabTitle="CHARACTERISTICS">
			<table>
	<!-- new row -->
	<tr>
				<td>
			    	<s:label path="">Process  :</s:label>
			    </td>
				<td>
			    	<s:input id="process2" name="process2" path="" />
			    	<s:errors path=""></s:errors>
				</td>
					<td>
			    	<s:label path="">Characteristics:</s:label>
			    </td>
				<td>
			    	<s:input id="characteristics" name="characteristics" path="" />
			    	<s:errors path=""></s:errors>
				</td>
				
					<td>
			    	<s:label path="">Tolerance/Method of Ispection</s:label>
			    </td>
				<td>
			    	<s:input id="tolerance" name="tolerance" path="" />
			    	<s:errors path=""></s:errors>
				</td>
				
		</tr>
		<tr>
				<td>
			    	<s:label path="">Specification  :</s:label>
			    </td>
				<td>
			    	<s:input id="specification" name="specification" path="" />
			    	<s:errors path=""></s:errors>
				</td>
					<td>
			    	<s:label path="">Guage No.:</s:label>
			    </td>
				<td>
			    	<s:input id="guageNo" name="guageNo" path="" />
			    	<s:errors path=""></s:errors>
				</td>
		</tr>
		
	</table>
	</tab:tabPane>
	
	<tab:tabPane id="conversion" tabTitle="CONVERTION">
	<table>
	<!-- new row -->
	<tr>
				<td>
			    	<s:label path="">Recieved UOM  :</s:label>
			    </td>
				<td>
			    	<s:input id="recievedUOM" name="recievedUOM" path="strReceivedUOM" />
			    	<s:errors path="strReceivedUOM"></s:errors>
				</td>
					<td>
			    	<s:label path="">Recieved Conversion Ratio:</s:label>
			    </td>
				<td>
			    	<s:input id="recievedConversionRatio" name="recievedConversionRatio" path="dblReceiveConversion" />
			    	<s:errors path="dblReceiveConversion"></s:errors>
				</td>
		</tr>
		<tr>
				<td>
			    	<s:label path="">Issue UOM  :</s:label>
			    </td>
				<td>
			    	<s:input id="issueUOM" name="issueUOM" path="strIssueUOM" />
			    	<s:errors path="strIssueUOM"></s:errors>
				</td>
					<td>
			    	<s:label path="">Issue Conversion Ratio:</s:label>
			    </td>
				<td>
			    	<s:input id="issueConversionRatio" name="issueConversionRatio" path="dblIssueConversion" />
			    	<s:errors path="dblIssueConversion"></s:errors>
				</td>
		</tr>
		<tr>
				<td>
			    	<s:label path="">Recipe UOM  :</s:label>
			    </td>
				<td>
			    	<s:input id="recipeUOM" name="recipeUOM" path="strRecipeUOM" />
			    	<s:errors path="strRecipeUOM"></s:errors>
				</td>
					<td>
			    	<s:label path="">Recipe Conversion Ratio:</s:label>
			    </td>
				<td>
			    	<s:input id="recipeConversionRatio" name="recipeConversionRatio" path="dblRecipeConversion" />
			    	<s:errors path="dblRecipeConversion"></s:errors>
				</td>
		</tr>
		
		
	</table>
	</tab:tabPane>



	</tab:tabContainer>
		<table>
		<tr>
			    <td ><input type="submit" value="Submit" onclick="return funValidateFields()"/></td>
			    <td ><input type="button" value="RESET" onclick="funResetFields()"/></td>
		</tr>
	</table>
	
</s:form>
</body>
</html>