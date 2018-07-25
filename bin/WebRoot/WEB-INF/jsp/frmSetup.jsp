<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

	$(function() 
	{
		 $( "#dtStart" ).datepicker();
		 $( "#dtEnd" ).datepicker();
		 $( "#dtLastTransDate" ).datepicker();
		 $( "#dtFromTime" ).datepicker();
		 $( "#dtToTime" ).datepicker();
	});

</script>
<title>Insert title here</title>
<tab:tabConfig />
</head>
<body>
	<s:form action="saveData.html" method="POST">
	<table>
	<tr>
<td><label>Property</label></td>
<td>
<s:select   path="strProperty" items="${propertyList}" id="strProperty">
</s:select></td>
</tr>
	
	</table>
	<br><br>
		<!--  Start of tabContainer-->
		<tab:tabContainer id="Setup">

			<!--  Start of Company Tab-->
			<tab:tabPane id="Company" tabTitle="Company">
			<table >
			<tr>
			<td><label>Industry Type</label></td>
			<td>
			<s:select path="strIndustryType">
			<s:option value="Manufacturing">Manufacturing</s:option>
			<s:option value="Hospitality">Hospitality</s:option>
			<s:option value="Pharmaceutical">Pharmaceutical</s:option>
			<s:option value="Trading">Trading</s:option>
			<s:option value="Corporate">Corporate</s:option>
			<s:option value="Retail">Retail</s:option>
			</s:select> 
			</td>
			</tr>
			
			<tr>
			<td><label>Company Code </label></td>
			<td><s:input path="strCompanyCode" readonly="true"/></td>
			<td><label>Name </label></td>
			<td><s:input path="strCompanyName"/></td>
			</tr>
			
			<tr>
			<td><label>Financial Year</label></td>
			<td><s:input path="strFinYear"/></td>						
			</tr>
			
			<tr>
			<td><label>Start Date</label></td>
			<td><s:input path="dtStart" id="dtStart"/></td>
			<td><label>End Date</label></td>
			<td><s:input path="dtEnd" id="dtEnd"/></td>
			</tr>
			
			<tr>
			<td><label>Last Transaction Date</label>
			<td><s:input path="dtLastTransDate" id="dtLastTransDate"/>
			</tr>
			
			<tr style="background: none repeat scroll 0% 0% blue;">
			<td><label>Main Address</label></td>
			</tr>
			
			<tr>
			<td><label>Address Line 1</label></td>
			<td><s:input path="strAdd1"/></td>
			</tr>
			
			<tr>
			<td><label>Address Line 2</label></td>
			<td><s:input path="strAdd2"/>
			</tr>
			
			<tr>
			<td><label>City</label></td>
			<td><s:input path="strCity"/></td>
			<td><label>State</label></td>
			<td><s:input path="strState"/></td>
			</tr>
			
			<tr>
			<td><label>Country</label></td>
			<td><s:input path="strCountry"/></td>
			<td><label>Pin</label></td>
			<td><s:input path="strPin"/></td>
			</tr>
			
			<tr  style="background: none repeat scroll 0% 0% blue;">
			<td><label>Billing Address</label></td>
			</tr>					
			<tr>
			<td><label>Address Line 1</label></td>
			<td><s:input path="strBAdd1"/></td>
			</tr>
			
			<tr>
			<td><label>Address Line 2</label></td>
			<td><s:input path="strBAdd2"/>
			</tr>
			
			<tr>
			<td><label>City</label></td>
			<td><s:input path="strBCity"/></td>
			<td><label>State</label></td>
			<td><s:input path="strBState"/></td>
			</tr>
			
			<tr>
			<td><label>Country</label></td>
			<td><s:input path="strBCountry"/></td>
			<td><label>Pin</label></td>
			<td><s:input path="strBPin"/></td>
			</tr>
			
			
			<tr   style="background: none repeat scroll 0% 0% blue;">
			<td><label>Shipping Address</label></td>
			</tr>					
			<tr>
			<td><label>Address Line 1</label></td>
			<td><s:input path="strSAdd1"/></td>
			</tr>
			
			<tr>
			<td><label>Address Line 2</label></td>
			<td><s:input path="strSAdd2"/>
			</tr>
			
			<tr>
			<td><label>City</label></td>
			<td><s:input path="strSCity"/></td>
			<td><label>State</label></td>
			<td><s:input path="strSState"/></td>
			</tr>
			
			<tr>
			<td><label>Country</label></td>
			<td><s:input path="strSCountry"/></td>
			<td><label>Pin</label></td>
			<td><s:input path="strSPin"/></td>
			</tr>
			
			<tr   style="background: none repeat scroll 0% 0% blue;">
			<td><label>Others</label>
			</td>
			</tr>
			<tr>
			<td><label>Phone No</label></td>
			<td><s:input path="strPhone"/></td>
			<td><label>Fax</label></td>
			<td><s:input path="strFax"/></td>
			</tr>
			
			
			<tr>
			<td><label>Email id</label></td>
			<td><s:input path="strEmail"/></td>
			<td><label>Website</label></td>
			<td><s:input path="strWebsite"/></td>
			</tr>
			
			
			<tr>
			<td><label>Due Days</label></td>
			<td><s:input path="intDueDays"/></td>
			<td><label>P.L.A. No.</label></td>
		<%-- 	<td><s:input path="strFax"/></td> --%>
			</tr>
			
			
			<tr>
			<td><label>CST No</label></td>
			<td><s:input path="strCST"/></td>
			<td><label>VAT No</label></td>
			<td><s:input path="strVAT"/></td>
			</tr>
			
			
			<tr>
			<td><label>Service Tax No</label></td>
			<td><s:input path="strSerTax"/></td>
			<td><label>Pan No </label></td>
			<td><s:input path="strPanNo"/></td>
			</tr>
			
			
			<tr>
			<td><label>Location Code No </label></td>
			<td><s:input path="strLocCode"/></td>
			<td><label>Asseese Code No  </label></td>
			<td><s:input path="strAsseeCode"/></td>
			</tr>
			
			
			<tr>
			<td><label>Purchase Email  </label></td>
			<td><s:input path="strPurEmail"/></td>
			<td><label>Sales Email</label></td>
			<td><s:input path="strSaleEmail"/></td>
			</tr>
			
			
			
			<tr>
			<td><label>Range Address</label></td>
			<%-- <td><s:input path="strPhone"/></td> --%>
			
			</tr>
			
			
			<tr>
			<td><label>Range</label></td>
			<td><s:input path="strRangeDiv"/></td>
			<td><label>Commisionerate </label></td>
			<td><s:input path="strCommi"/></td>
			</tr>
			
			
			
			<tr>
			<td><label>C.Ex Reg No</label></td>
			<td><s:input path="strRegNo"/></td>
			<td><label>Division   </label></td>
			<td><s:input path="strDivision"/></td>
			</tr>
			
			
			
			<tr>
			<td><label>Bond Amount</label></td>
			<td><s:input path="dblBondAmt"/></td>
			<td><label>Acceptance No.</label></td>
			<td><s:input path="strAcceptanceNo"/></td>
			</tr>
			
			</table>
			<!--  End of Company Tab-->
			</tab:tabPane>

			<!-- ------------------------------------------------------------------------- -->

			<!--  Start of General Tab-->
			<tab:tabPane id="General" tabTitle="General">
			<table>
			<tr>
			<td><label>Allow Negative Stock</label></td>
			<td><s:select path="strNegStock">
			<s:option value="Y">YES</s:option>
			<s:option value="N">NO</s:option>
			</s:select></td>
			</tr>
			
			<tr>
			<td><label>Production Order BOM</label></td>
			<td><s:select path="strPOBOM">
			<s:option value="FIRST">FIRST LEVEL</s:option>
			<s:option value="LAST">LAST LEVEL</s:option>
			</s:select></td>						
			<td><label>Sales Order BOM</label></td>
			<td><s:select path="strSOBOM">
			<s:option value="FIRST">FIRST LEVEL</s:option>
			<s:option value="LAST">LAST LEVEL</s:option>
			</s:select></td>			
			</tr>
			
			<tr>
			<td><label>Total Working Time(min)</label></td>
			<td><s:input path="strTotalWorkhour"/></td>			
			</tr>
			
			
			<tr>
			<td><label>From Time </label></td>
			<td><s:input path="dtFromTime" id="dtFromTime"/></td>
			<td><label>To Time</label></td>
			<td><s:input path="dtToTime" id="dtToTime"/></td>			
			</tr>
			
			
			<tr>
			<td><label>Workflowbased Authorisation </label></td>
			<td><s:select path="strWorkFlowbasedAuth">
			<s:option value="Y">YES</s:option>
			<s:option value="N">NO</s:option>
			<s:option value="S">SLAB BASED</s:option>
			</s:select></td>
			<td><label>Decimal Places</label></td>
			</tr>
			
			
			<tr>
			<td><label>List Price in PO</label></td>
			<td><label>Finance Module</label></td>						
			</tr>
			
			<tr>
			<td><label>Batch Method</label></td>
			<td><label>Tally Posting Type</label></td>
			</tr>
			
			
			<tr>
			<td><label>Auto DC for TaxInvoice</label></td>
			<td><label>Audit</label></td>					
			</tr>
			</table>

			<!--  End of General Tab-->
			<!-- ------------------------------------------------------------------------- -->
			</tab:tabPane>

			

			<!--  Start of Purchase Order Tab-->
			<tab:tabPane id="PurchaseOrder" tabTitle="Purchase Order">


				<!--  End of  Purchase Order Tab-->
			<!-- ------------------------------------------------------------------------- -->
			</tab:tabPane>
			


			<!--  Start of Authorise Tab-->
			<tab:tabPane id="Authorise" tabTitle="Authorise">

		<table>
		<tr  style="background: none repeat scroll 0% 0% blue;">
		<td><label>Document Authorization </label></td>
		</tr>
		
		<tr>

		<td><label>Bill Passing </label></td>

		<td><input type="checkbox" name="chkBillPassing" value="true" ></td>

		<td><select>
		<option value=""></option>
		<option value="">1</option>
		<option value="">2</option>
		<option value="">3</option>
		<option value="">4</option>
		<option value="">5</option>				
		</select></td>

		<td>
		
		<s:select path="strUserCode"  items= "${userList}" id="strUser1">
		</s:select></td>
<td>
		<s:select path="strUserCode"  items= "${userList}" id="strUser2">
		</s:select></td>

		<td>
		<s:select   path="strUserCode" items="${userList}" id="strUser3">
		</s:select></td>

		<td>
		<s:select   path="strUserCode" items="${userList}" id="strUser4">
		</s:select></td>

		<td>
		<s:select   path="strUserCode" items="${userList}" id="strUser5">
		</s:select></td>

		<td></td>
		</tr>
		</table>


			<!--  End of Authorise Tab-->
				
			</tab:tabPane>
			<!-- ------------------------------------------------------------------------- -->



			<!--  Start of Process  Tab-->
			<tab:tabPane id="Process" tabTitle="Process">
		
				<table id="tblprocess">
				
				<tr>
				<td><label>Module Flow</label></td>
				</tr>
				<tr><td><hr></td></tr>
				<c:forEach items="${command.listProcessSetupForm}" var="processsetupform" varStatus="status">
				<tr>
					<td>
						<input name="listProcessSetupForm[${status.index}].strFormDesc" value="${processsetupform.strFormDesc}"/>
						<%-- <label>${processsetupform.strFormDesc}</label> --%>
						</td>	
						<c:choose>
						
						<c:when  test="${processsetupform.strFormDesc == 'Purchase Indend'}">	
								
						<td><input type="checkbox" name="listProcessSetupForm[${status.index}].strSalesOrder" value="Sales Order">Sales Order<br></td>
						<td><input type="checkbox" name="listProcessSetupForm[${status.index}].strProductionOrder" value="Production Order">Production Order<br></td>
						<td><input type="checkbox" name="listProcessSetupForm[${status.index}].strMinimumLevel" value="Minimum Level">Minimum Level <br></td>	
						<td><input type="checkbox" name="listProcessSetupForm[${status.index}].strRequisition"  value="Requisition">Requisition<br></td>		
						<%-- <td><input type="checkbox" name="Sales Order" value="${processsetupform.strSalesOrder}">Sales Order<br></td> --%>
						<%-- <td><input type="checkbox" name="Production Order" value="${processsetupform.strProductionOrder}">Production Order<br></td> --%>
						<%-- <td><input type="checkbox" name="Minimum Level " value="${processsetupform.strMinimumLevel}">Minimum Level <br></td> --%>
						<%-- <td><input type="checkbox" name="Requisition" value="${processsetupform.strRequisition}">Requisition<br></td> --%>		
						</c:when>
						
						<c:when test="${processsetupform.strFormDesc == 'Purchase Order'}">
						<td><input type="checkbox" name="Direct" value="${processsetupform.strDirect}"> Direct <br></td>
						<td><input type="checkbox" name="Purchase Indent" value="${processsetupform.strPurchaseIndent}">Production Order<br></td>	
						</c:when>
						
						<c:when test="${processsetupform.strFormDesc == 'Job Order'}">
						<td><input type="checkbox" name="Sales Order" value="${processsetupform.strSalesOrder}">Sales Order<br></td>
						<td><input type="checkbox" name="Production Order" value="${processsetupform.strProductionOrder}">Production Order<br></td>
						<td><input type="checkbox" name="Direct" value="${processsetupform.strDirect}"> Direct <br></td>
						</c:when>
						
						<c:when  test="${processsetupform.strFormDesc == 'Sales Direct Billing'}">
						<td><input type="checkbox" name="Sales Order" value="Sales Order">Sales Order<br></td>
						<td><input type="checkbox" name="Service Order" value="Service Order"> Service Order <br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						</c:when>
						
						<c:when test="${processsetupform.strFormDesc == 'Requisition'}">
						<td><input type="checkbox" name="Work Order" value="Work Order"> Work Order  <br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'Material Issue'}">
						<td><input type="checkbox" name="Requisition" value="Requisition">Requisition<br></td>
						<td><input type="checkbox" name="Work Order" value="Work Order"> Work Order  <br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						<td><input type="checkbox" name="Project" value="Project"> Project <br></td>						
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'GRN'}">
						<td><input type="checkbox" name="Production Order" value="Production Order">Production Order<br></td>
						<td><input type="checkbox" name="Purchase  Return" value="Purchase  Return">Purchase  Return<br></td>
						<td><input type="checkbox" name="Service Order" value="Service Order">Service Order<br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						<td><input type="checkbox" name="Sales Return" value="Sales Return"> Sales Return  <br></td>
						<td><input type="checkbox" name="Rate Contractor" value="Rate Contractor"> Rate Contractor <br></td>
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'Purchase Return'}">
						<td><input type="checkbox" name="GRN" value="GRN"> GRN <br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'Sub Contractor GRN'}">
						<td><input type="checkbox" name="Delivery Note" value="Delivery Note">Delivery Note<br></td>
						<td><input type="checkbox" name="Opening Stock" value="Opening Stock">Opening Stock<br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'Delivery Note'}">
						<td><input type="checkbox" name="GRN" value="GRN"> GRN <br></td>
						<td><input type="checkbox" name="Sub Contractor GRN" value="Sub Contractor GRN"> Sub Contractor GRN  <br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'Excise Challan'}">
						<td><input type="checkbox" name="Purchase  Return" value="Purchase  Return">Purchase  Return<br></td>
						<td><input type="checkbox" name="Delivery Note" value="Delivery Note">  Delivery Note  <br></td>
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'Production Order'}">
						<td><input type="checkbox" name="Sales Projection" value="Sales Projection">Sales Projection<br></td>
						<td><input type="checkbox" name="Delivery Note" value="Delivery Note">  Delivery Note  <br></td>
						</c:when>
						
						
						<c:when test="${processsetupform.strFormDesc == 'Work Order'}">
						<td><input type="checkbox" name="Sales Order" value="Sales Order">Sales Order<br></td>
						<td><input type="checkbox" name="Production Order" value="Production Order">Production Order<br></td>
						<td><input type="checkbox" name="Sales Order" value="Sales Order">Sales Order<br></td>
						<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
						</c:when>
						
						</c:choose>
						<%-- <label name="listProcessSetupForm[${status.index}].strFormDesc" value="${processsetupform.strFormDesc}"/> --%>
										
				</tr>
			</c:forEach>	
			
			
				<%-- <tr>				
				<td><label>Purchase Indend</label></td>
				<td><s:checkbox path="selectedCheckBox" value="Sales Order"/>Sales Order</td>
				<td><input type="checkbox" name="Sales Order" value="Sales Order" >Sales Order<br></td>
				<td><input type="checkbox" name="Production Order" value="Production Order ">Production Order<br></td>
				<td><input type="checkbox" name="Minimum Level " value="Minimum Level ">Minimum Level <br></td>
				<td><input type="checkbox" name="Requisition" value="Requisition">Requisition<br></td>				
				</tr>
				
				<tr>
				<td><label>Purchase Order</label></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				<td><input type="checkbox" name="Purchase Indent" value="Purchase Indent">Production Order<br></td>				
				</tr>
				
				<tr>
				<td><label>Job Order</label></td>
				<td><input type="checkbox" name="Sales Order" value="Sales Order">Sales Order<br></td>
				<td><input type="checkbox" name="Production Order" value="Production Order">Production Order<br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				</tr>
				
				<tr>
				<td><label>Sales Direct Billing</label></td>
				<td><input type="checkbox" name="Sales Order" value="Sales Order">Sales Order<br></td>
				<td><input type="checkbox" name="Service Order" value="Service Order"> Service Order <br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				</tr>
				
				<tr>
				<td><label>Requisition</label></td>
				<td><input type="checkbox" name="Work Order" value="Work Order"> Work Order  <br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				</tr>
				
				
				<tr>
				<td><label>Material Issue</label></td>
				<td><input type="checkbox" name="Requisition" value="Requisition">Requisition<br></td>
				<td><input type="checkbox" name="Work Order" value="Work Order"> Work Order  <br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				<td><input type="checkbox" name="Project" value="Project"> Project <br></td>
				</tr>

				<tr>
				<td><label>GRN</label></td>
				<td><input type="checkbox" name="Production Order" value="Production Order">Production Order<br></td>
				<td><input type="checkbox" name="Purchase  Return" value="Purchase  Return">Purchase  Return<br></td>
				<td><input type="checkbox" name="Service Order" value="Service Order">Service Order<br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				<td><input type="checkbox" name="Sales Return" value="Sales Return"> Sales Return  <br></td>
				<td><input type="checkbox" name="Rate Contractor" value="Rate Contractor"> Rate Contractor <br></td>
				</tr>
				
				<tr>
				<td><label>Purchase Return</label></td>
				<td><input type="checkbox" name="GRN" value="GRN"> GRN <br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				</tr>	
				
				
				<tr>
				<td><label>Sub Contractor GRN</label></td>
				<td><input type="checkbox" name="Delivery Note" value="Delivery Note">Delivery Note<br></td>
				<td><input type="checkbox" name="Opening Stock" value="Opening Stock">Opening Stock<br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				</tr>
				
				
				<tr>
				<td><label>Delivery Note</label></td>
				<td><input type="checkbox" name="GRN" value="GRN"> GRN <br></td>
				<td><input type="checkbox" name="Sub Contractor GRN" value="Sub Contractor GRN"> Sub Contractor GRN  <br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				</tr>		
				
				
				
				<tr>
				<td><label>Excise Challan</label></td>
				<td><input type="checkbox" name="Purchase  Return" value="Purchase  Return">Purchase  Return<br></td>
				<td><input type="checkbox" name="Delivery Note" value="Delivery Note">  Delivery Note  <br></td>
				</tr>
				
				
				
				<tr>
				<td><label>Production Order</label></td>
				<td><input type="checkbox" name="Sales Projection" value="Sales Projection">Sales Projection<br></td>
				<td><input type="checkbox" name="Delivery Note" value="Delivery Note">  Delivery Note  <br></td>
				</tr>
				
				<tr>
				<td><label>Work Order</label></td>
				<td><input type="checkbox" name="Sales Order" value="Sales Order">Sales Order<br></td>
				<td><input type="checkbox" name="Production Order" value="Production Order">Production Order<br></td>
				<td><input type="checkbox" name="Sales Order" value="Sales Order">Sales Order<br></td>
				<td><input type="checkbox" name="Direct" value="Direct"> Direct <br></td>
				</tr>
				
				<c:forEach items="${command.listProcessSetupModel}" var="processsetup"
					varStatus="status">
					<tr>
						<td><input
							name="listProcessSetupModel[${status.index}].strForm"
							value="${processsetup.strForm}" /></td>

						<td><input
							name="listProcessSetupModel[${status.index}].strProcess"
							value="${processsetup.strProcess}" /></td>
						
					</tr>
				</c:forEach> --%>
				
				</table>


				<!--  End of Process  Tab-->
			</tab:tabPane>
			<!-- ------------------------------------------------------------------------- -->


			<!--  Start of Bank Tab-->
			<tab:tabPane id="Bank" tabTitle="Bank">
				<table>
				<tr>
				<td><label>Bank Name</label></td>
				<td><s:input path="strBankName"/></td>
				</tr>
				
				
				<tr>
				<td><label>Branch Name</label></td>
				<td><s:input path="strBranchName"/></td>
				</tr>
				
				<tr>
				<td><label>AddressLine1</label></td>
				<td><s:input path="strBankAdd1"/></td>
				</tr>
				
				<tr>
				<td><label>AddressLine2</label></td>
				<td><s:input path="strBankAdd2"/></td>
				</tr>
				
				<tr>
				<td><label>City</label></td>
				<td><s:input path="strBankCity"/></td>
				</tr>
				
				
				<tr>
				<td><label>Account Number </label></td>
				<td><s:input path="strBankAccountNo"/></td>
				</tr>
				
				
				<tr>
				<td><label>SwiftCode </label></td>
				<td><s:input path="strSwiftCode"/></td>
				</tr>
				</table>

				<!--  End of Bank Tab-->
			</tab:tabPane>

			<!-- ------------------------------------------------------------------------- -->

			<!--  Start of Supplier Performance  Tab-->
			<tab:tabPane id="Supplier Performance"
				tabTitle="Supplier Performance">


				<!--  End of Supplier Performance  Tab-->
			</tab:tabPane>
			<!-- ------------------------------------------------------------------------- -->


			<!--  Start of Authorization(Slab Based)  Tab-->
			<tab:tabPane id="Authorization" tabTitle="Authorization(Slab Based) ">


				<!--  End of Authorization(Slab Based)  Tab-->
			</tab:tabPane>

			<!-- ------------------------------------------------------------------------- -->
			<!--  End of tabContainer-->
		</tab:tabContainer>
<table>
		<tr>
  <td colspan="2"><input type="submit" value="Submit"/></td>
   <td colspan="1"><input type="reset" value="Reset" /></td>
  </tr>

</table>
	</s:form>
</body>
</html>