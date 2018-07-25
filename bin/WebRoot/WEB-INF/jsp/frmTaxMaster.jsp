<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
	
	<script type="text/javascript">
				
	 $(function() {
		 $( "#dtFromDate" ).datepicker();
		 });
	 
	 $(function() {
		 $( "#dtToDate" ).datepicker();
		 });
	
	
		//function funResetFields()
		//{
			//document.getElementById("taxAmt").disabled=true;
			
		//	document.getElementById("taxDesc").value="";
	      //  document.getElementById("taxCode").value="";
	        //document.getElementById("taxDesc").value="";
	        //document.getElementById("taxPercent").value="";
	       /// document.getElementById("taxAmount").value="";
	        //document.getElementById("propCode").value="";
	       // document.getElementById("taxPer").value="0.00";
	    //}		
		
		 
		function funHelp(transactionName)
		{
	        window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
	    }
		
		function funSetData(code)
		{
			document.getElementById("taxCode").value=code;
			
			 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/loadTaxMasterData.html?taxCode="+code,
				        dataType: "json",
				        success: function(response)
				        {
				        	document.getElementById("taxDesc").value=response.strTaxDesc;
				        	document.getElementById("taxPer").value=response.dblPercent;
				        	document.getElementById("taxAmt").value=response.dblAmount;
				        	document.getElementById("taxOnSP").value=response.strTaxOnSP;
				        	document.getElementById("taxOnGD").value=response.strTaxOnGD;
				        	document.getElementById("taxType").value=response.strTaxType;
				        	document.getElementById("taxIndicator").value=response.strTaxIndicator;
				        	document.getElementById("applOn").value=response.strApplOn;
				        	document.getElementById("propCode").value=response.strPropertyCode;
				        	document.getElementById("partyInd").value=response.strPartyIndicator;
				        	document.getElementById("fromDate").value=response.dtValidFrom;
				        	document.getElementById("toDate").value=response.dtValidTo;
				        	if(response.strTaxRounded=='Yes')
				        	{
				        		document.getElementById("taxRounded").checked=true;
				        	}
				        	else
				        	{
				        		document.getElementById("taxRounded").checked=false;
				        	}
				        	
				        	if(response.strTaxOnTax=='Yes')
				        	{
				        		document.getElementById("taxOnTax").checked=true;
				        	}
				        	else
				        	{
				        		document.getElementById("taxOnTax").checked=false;
				        	}
				        	
				        	if(response.strTaxOnST=='Yes')
				        	{
				        		document.getElementById("taxOnST").checked=true;
				        	}
				        	else
				        	{
				        		document.getElementById("taxOnST").checked=false;
				        	}
				        	
				        	if(response.strExcisable=='Yes')
				        	{
				        		document.getElementById("excisable").checked=true;
				        	}
				        	else
				        	{
				        		document.getElementById("excisable").checked=false;
				        	}
						},
				        error: function(e)
				        {
				          	alert('Error:=' + e);
				        }
			      });
		}
		
		function funChangeState()
		{
			if(document.getElementById("taxType").value=='Percent')
			{
				document.getElementById("taxAmt").disabled=true;
				document.getElementById("taxPer").disabled=false;
			}
			if(document.getElementById("taxType").value!='Percent')
			{
				document.getElementById("taxPer").disabled=true;
				document.getElementById("taxAmt").disabled=false;
			}
		}

	</script>

</head>
<body onload="funResetFields()">
	<s:form name="taxForm" method="POST" action="saveTaxMaster.html">
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
		        <td><s:label path="strTaxCode" >TaxCode:</s:label></td>
		        <td><s:input id="taxCode" name="taxCode" path="strTaxCode" value="" readonly="true" ondblclick="funHelp('taxmaster')"/></td>
		    </tr>
		    	
		    <tr>
		        <td><s:label path="strTaxDesc">Description:</s:label></td>
		        <td>
		        	<s:input type="text" id="taxDesc" name="taxDesc" path="strTaxDesc" tabindex="1" required="true"/>
		        	<s:errors path="strTaxDesc"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td><s:label path="strTaxOnSP">Tax On S/P:</s:label></td>
			    <td>
			    	<s:select id="taxOnSP" path="strTaxOnSP" >
			    		<s:options items="${taxOnSPList}"/>
			    	</s:select>
			    </td>
			</tr>
			
			<tr>
			    <td><s:label path="strTaxType">Tax Type:</s:label></td>
			    <td><s:select id="taxType" path="strTaxType" items="${taxTypeList}"  onchange="funChangeState()"/></td>
			</tr>			
			    
			<tr>
		        <td><s:label path="dblPercent">Percent:</s:label></td>
		        <td>
		        	<s:input type="number" min="0" max="100" id="taxPer" name="taxPer" path="dblPercent" tabindex="1" required="true"/>
		        	
		        	<s:errors path="dblPercent"></s:errors>
		        </td>
		    </tr>
			    
			<tr>
		        <td><s:label path="dblAmount">Amount:</s:label></td>
		        <td>
		        	<s:input id="taxAmt" name="taxAmt" path="dblAmount" tabindex="1"/>
		        	<s:errors path="dblAmount"></s:errors>
		        </td>
		    </tr>
			    
			<tr>
			    <td><s:label path="strTaxOnGD">Tax On G/D:</s:label></td>
			    <td><s:select id="taxOnGD" path="strTaxOnGD" items="${taxOnGDList}"/></td>
			</tr>
			
			<tr>
			    <td><s:label path="dtValidFrom">Date Valid From:</s:label></td>
			    <td><s:input id="dtFromDate" name="fromDate" path="dtValidFrom"/></td>
			</tr>
				
			<tr>
			    <td><s:label path="dtValidTo">Date Valid To:</s:label></td>
			    <td><s:input id="dtToDate" name="toDate" path="dtValidTo" /></td>
			</tr>
				
			<tr>
			    <td><s:label path="strTaxIndicator">Tax Indicator:</s:label></td>
			    <td><s:select id="taxIndicator" path="strTaxIndicator" items="${taxIndicatorList}"/></td>
			</tr>
				
			<tr>
			    <td><s:label path="strTaxRounded">Tax Rounded:</s:label></td>
			    <td><s:checkbox element="li" id="taxRounded" path="strTaxRounded" value="Yes"/></td>
			</tr>
				
			<tr>
			    <td><s:label path="strPropertyCode">Property Code:</s:label></td>
			    <td><s:input id="propCode" name="propCode" path="strPropertyCode" tabindex="1"/></td>
			</tr>
				
			<tr>
			    <td><s:label path="strTaxOnST">Tax On SubTotal:</s:label></td>
			    <td><s:checkbox element="li" id="taxOnST" path="strTaxOnST" value=""/></td>
			</tr>
				
			<tr>
			    <td><s:label path="strTaxOnTax">Tax On Tax:</s:label></td>
			    <td><s:checkbox element="li" id="taxOnTax" path="strTaxOnTax" value=""/></td>
			</tr>
				
			<tr>
			    <td><s:label path="strExcisable">Excisable:</s:label></td>
			    <td><s:checkbox element="li" id="excisable" path="strExcisable" value=""/></td>
			</tr>
				
			<tr>
			    <td><s:label path="strApplOn">Applicable On:</s:label></td>
			    <td><s:select id="applOn" path="strApplOn" items="${applicableOnList}" /></td>
			</tr>
				
			<tr>
			    <td><s:label path="strPartyIndicator">Party Indicator:</s:label></td>
			    <td><s:select id="partyInd" path="strPartyIndicator" items="${partyIndicatorList}" /></td>
			</tr>
			
			<!--
			<c:forEach items="${settlementGrid.listSettlement}" var="settlement" varStatus="status">
				<tr>
					<td>
						<input type="hidden" name="listSettlement[${status.index}].strSettlementCode" value="${settlement.strSettlementCode}"/>
					</td>
					
					<td>
						<input name="listSettlement[${status.index}].strSettlementDesc" value="${settlement.strSettlementDesc}"/>
					</td>
					
					<td>
						<input name="listSettlement[${status.index}].strSettlementType" value="${settlement.strSettlementType}"/>
					</td>
					
					<td>
					    <input type="checkbox" name="listSettlement[${status.index}].strApplicable" value=""/>
					</td>
				</tr>
			</c:forEach>
			     -->
			<tr>
			    <td colspan="1"><input type="submit" value="Submit" tabindex="3" /></td>
			    <td colspan="1"><input type="reset" value="Reset"/></td>
		    </tr>
		</table>
	</s:form>
</body>
</html>