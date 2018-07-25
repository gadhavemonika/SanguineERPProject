<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu Item</title>
<script type="text/javascript">
	var fieldName;

	$(document).ready(function () {
		
		  $("form").submit(function(event){
			  if($("#txtItemName").val().trim()=="")
				{
					alert("Please Enter Item Name");
					return false;
				}
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		
		  $('input#txtItemCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtExternalCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtItemName').mlKeyboard({layout: 'en_US'});
		  $('input#txtShortName').mlKeyboard({layout: 'en_US'});
		  $('input#txtPurchaseRate').mlKeyboard({layout: 'en_US'});
		  $('input#txtSalePrice').mlKeyboard({layout: 'en_US'});
		  $('input#txtMinLevel').mlKeyboard({layout: 'en_US'});
		  $('input#txtItemDetails').mlKeyboard({layout: 'en_US'});
		  $('input#txtMaxLevel').mlKeyboard({layout: 'en_US'});
		  $('textarea#txtItemDetails').mlKeyboard({layout: 'en_US'});
		  
		
		}); 

	function funSetData(code){

		switch(fieldName){

			case 'POSMenuItemMaster' : 
				funSetItemCode(code);
				break;
		}
	}


	function funSetItemCode(code)
	{
		$("#txtItemCode").val(code);
		var searchurl=getContextPath()+"/loadItemCode.html?ItemCode="+code;
		 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strItemCode=='Invalid Code')
		        	{
		        		alert("Invalid Item Code ");
		        		$("#txtItemCode").val('');
		        	}
		        	else
		        	{
		        	/* 	      chkItemForSale txtItemType txtSubGroupCode
		 				txtTaxIndicator txtPurchaseRate txtRevenueHead txtSalePrice
		 				txtMinLevel txtProcTimeMin txtMaxLevel chkStockInEnable  chkOpenItem 
		 				chkItemWiseKOTYN txtItemDetails txtItemDetails */	
		        		$("#txtItemCode").val(response.strItemCode);
			        	$("#txtExternalCode").val(response.strExternalCode);
			        	$("#txtItemName").val(response.strItemName);
			        	$("#txtItemName").focus();
			        	$("#txtShortName").val(response.strShortName);
			        	//$("#chkRawMaterial").val(response.strRawMaterial);
			        	if(response.strRawMaterial=='Y')
			        	{
			        		$("#chkRawMaterial").attr('checked', true);
			        	}
			        	else
			        	{
			        		$("#chkRawMaterial").attr('unchecked', false);
			        	}
			        	
			        	//$("#chkItemForSale").val(response.strItemForSale);
			        	if(response.strItemForSale=='Y')
			        	{
			        		$("#chkItemForSale").attr('checked', true);
			        	}
			        	else
			        	{
			        		$("#chkItemForSale").attr('unchecked', false);
			        	}
			        	
			        	$("#txtItemType").val(response.strItemType);
			        	$("#txtSubGroupCode").val(response.strSubGroupCode);
			        	$("#txtTaxIndicator").val(response.strTaxIndicator);
			        	$("#txtPurchaseRate").val(response.dblPurchaseRate);
			        	$("#txtRevenueHead").val(response.strRevenueHead);
			        	$("#txtSalePrice").val(response.dblSalePrice);
			        	
			        	$("#txtMinLevel").val(response.dblMinLevel);
			        	$("#txtProcTimeMin").val(response.intProcTimeMin);
			        	$("#txtMaxLevel").val(response.dblMaxLevel);
			        	$("#txtUOM").val(response.strUOM);
			        	//$("#chkStockInEnable").val(response.strStockInEnable);
			        	if(response.strStockInEnable=='Y')
			        	{
			        		$("#chkStockInEnable").attr('checked', true);
			        	}
			        	else
			        	{
			        		$("#chkStockInEnable").attr('unchecked', false);
			        	}
			        	//$("#chkOpenItem").val(response.strOpenItem);
			         	if(response.strOpenItem=='Y')
			        	{
			        		$("#chkOpenItem").attr('checked', true);
			        	}
			        	else
			        	{
			        		$("#chkOpenItem").attr('unchecked', false);
			        	}
			        //	$("#chkItemWiseKOTYN").val(response.strItemWiseKOTYN);
			        	if(response.strItemWiseKOTYN=='Y')
			        	{
			        		$("#chkItemWiseKOTYN").attr('checked', true);
			        	}
			        	else
			        	{
			        		$("#chkItemWiseKOTYN").attr('unchecked', false);
			        	}
			        	
			        	if(response.strDiscountApply=='Y')
			        	{
			        		$("#chkDiscountApply").attr('checked', true);
			        	}
			        	else
			        	{
			        		$("#chkDiscountApply").attr('unchecked', false);
			        	}
			        	
			        	$("#txtItemDetails").val(response.strItemDetails);
			        
		        	}
		        },
		        error: function(jqXHR, exception)
		        {
		            if (jqXHR.status === 0) {
		                alert('Not connect.n Verify Network.');
		            } else if (jqXHR.status == 404) {
		                alert('Requested page not found. [404]');
		            } else if (jqXHR.status == 500) {
		                alert('Internal Server Error [500].');
		            } else if (exception === 'parsererror') {
		                alert('Requested JSON parse failed.');
		            } else if (exception === 'timeout') {
		                alert('Time out error.');
		            } else if (exception === 'abort') {
		                alert('Ajax request aborted.');
		            } else {
		                alert('Uncaught Error.n' + jqXHR.responseText);
		            }		            
		        }
	 });
	
}
	



	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%>alert("Data Saved \n\n"+message);<%
			}
		}%>
	});
		
	function funCallFormAction(actionName,object) 
	{
		var flg=true;
		
		/* if($('#txtItemCode').val()=='')
		{ */
			var name = $('#txtItemName').val();
			var code = $('#txtItemCode').val();
			
			 $.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkItemName.html?itemCode="+code+"&itemName="+name,
			        async: false,
			        dataType: "text",
			        success: function(response)
			        {
			        	if(response=="false")
			        		{
			        			alert("Item Name Already Exist!");
			        			$('#txtItemName').focus();
			        			flg= false;
				    		}
				    	else
				    		{
				    			flg=true;
				    		}
					},
					error: function(jqXHR, exception) {
			            if (jqXHR.status === 0) {
			                alert('Not connect.n Verify Network.');
			            } else if (jqXHR.status == 404) {
			                alert('Requested page not found. [404]');
			            } else if (jqXHR.status == 500) {
			                alert('Internal Server Error [500].');
			            } else if (exception === 'parsererror') {
			                alert('Requested JSON parse failed.');
			            } else if (exception === 'timeout') {
			                alert('Time out error.');
			            } else if (exception === 'abort') {
			                alert('Ajax request aborted.');
			            } else {
			                alert('Uncaught Error.n' + jqXHR.responseText);
			            }		            
			        }
					
		      });
		
		return flg;
	}

	
	
</script>

</head>
<body>
<br>
	<div id="formHeading">
	<label>Menu Item Master</label>
	</div>

<br/>
<br/>

	<s:form name="menuItemMaster" method="POST" action="saveMenuItemMaster.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td>
					<label>Item Code</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtItemCode" path="strItemCode" cssClass="searchTextBox" ondblclick="funHelp('POSMenuItemMaster');"/>
				</td>
				<td>
					<label>External Code</label>
					<s:input type="text" id="txtExternalCode" path="strExternalCode" cssClass="longTextBox" />
				</td>
				<td></td>
				
			</tr>
			<tr>
				<td>
					<label>Item Name</label>
				</td>
				<td>
					<s:input type="text" id="txtItemName" path="strItemName" cssClass="longTextBox" />
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Short Name</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtShortName" path="strShortName" cssClass="longTextBox" />
				</td>
				<td>
					<label>Raw Material</label>
					<s:input type="checkbox"  id="chkRawMaterial" path="strRawMaterial"  style="width: 3%"></s:input>
					&emsp;&ensp;
					<label>Item For Sale</label>
					<s:input type="checkbox"  id="chkItemForSale" path="strItemForSale"  style="width: 3%"></s:input>
					
				</td><td></td>
			</tr>
			<tr>
				<td>
					<label>Item Type</label>
				</td>
				<td>
					<s:select id="txtItemType" path="strItemType" cssClass="BoxW124px">
					<option selected="selected" value="">Select</option>
					<option value="Food">Food</option>
					<option value="Liquor">Liquor</option>
					<option value="Retail">Retail</option>
					
					</s:select>
				</td>
				<td>
					<label>Sub Group Code </label>
					<s:select id="txtSubGroupCode" path="strSubGroupCode" items="${subGroup}"  cssClass="BoxW124px"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Tax Indicator</label>
				</td>
				<td>
					<s:select id="txtTaxIndicator" path="strTaxIndicator" cssClass="BoxW124px">
					<option selected="selected" value=""></option>
					<option value="A">A</option>
					<option value="B">B</option>
					<option value="C">C</option>
					<option value="D">D</option>
					<option value="E">E</option>
					<option value="F">F</option>
					<option value="G">G</option>
					<option value="H">H</option>
					<option value="I">I</option>
					<option value="J">J</option>
					<option value="K">K</option>
					<option value="L">L</option>
					<option value="M">M</option>
					<option value="N">N</option>
					<option value="O">O</option>
					<option value="P">P</option>
					<option value="Q">Q</option>
					<option value="R">R</option>
					<option value="S">S</option>
					<option value="T">T</option>
					<option value="U">U</option>
					<option value="V">V</option>
					<option value="W">W</option>
					<option value="X">X</option>
					<option value="Y">Y</option>
					<option value="Z">Z</option>
					</s:select>
					
				</td>
				<td>
					<label>Purchase Rate</label>
					<s:input colspan="3" id="txtPurchaseRate" path="dblPurchaseRate" type="number" min="0" step="1" class="longTextBox" style="width: 38%;text-align: right;" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Revenue Head</label>
				</td>
				<td>
					<s:select id="txtRevenueHead" path="strRevenueHead" cssClass="BoxW124px">
					<option selected="selected" value=""></option>
					<option value="FOOD">FOOD</option>
					<option value="BEVERAGES">BEVERAGES</option>
					<option value="TOBBACO">TOBBACO</option>
					<option value="CONFECTIONARY">CONFECTIONARY</option>
					<option value="MILD">MILD</option>
					<option value="LIQUORS">LIQUORS</option>
					<option value="FERMENTATED">FERMENTATED</option>
					<option value="LIQUOR">DMFL</option>
					<option value="TOBBACO">IMPORTED</option>
					<option value="CONFECTIONARY">BITTER/LIQUOR</option>
					<option value="MILD">OTHER/MISC</option>
					<option value=WINES>WINES</option>
					
					</s:select>
					
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Sale Price</label>
				</td>
				<td>
				<s:input colspan="3" id="txtSalePrice" path="dblSalePrice" type="number" min="0" step="1" class="longTextBox" style="width: 38%;text-align: right;" />
				</td>
				<td>
					<label>Processing Day</label>
					<s:select id="txtProcDay" path="intProcDay" cssClass="BoxW124px">
					<option selected="selected" value=""></option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					
					</s:select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td> 
					<label>Minimum Level</label>
				</td>
				<td>
				<s:input colspan="3" id="txtMinLevel" path="dblMinLevel" type="number" min="0" step="1" class="longTextBox" style="width: 38%;text-align: right;" />					
				</td>
				<td> 
					<label>Processing Time</label>
					<s:select colspan="3" type="text" items="${ProcessTime}" id="txtProcTimeMin" path="intProcTimeMin" cssClass="BoxW124px" />
				<label> Minutes</label>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Maximum Level</label>
				</td>
				<td>
					<s:input colspan="3" id="txtMaxLevel" path="dblMaxLevel" type="number" min="0" step="1" class="longTextBox" style="width: 38%;text-align: right;" />
				</td>
				<td>
					<label>Stock In Enable</label>
					<s:input type="checkbox"  id="chkStockInEnable" path="strOpenItem"  style="width: 3%"></s:input>
				</td>
				<td></td>
				</tr>
				<tr>	
				<td>
					<label>Open Item</label>
				</td>
				<td>
				<s:input type="checkbox"  id="chkOpenItem" path="strOpenItem"  style="width: 3%"></s:input>
				</td>
				<td>	
					<label>Item Wise KOT YN</label>
					<s:input type="checkbox"  id="chkItemWiseKOTYN" path="strItemWiseKOTYN"  style="width: 3%"></s:input>
					&emsp;&ensp;
					<label> No Discount</label>
					<s:input type="checkbox"  id="chkDiscountApply" path="strDiscountApply" value="Y" style="width: 3%"></s:input>
				</td>
				<td>	
				</td>
			</tr>
			
			<tr>
				<td><label>Unit Of Measurement</label>
				</td>
				<td>
				<s:select id="txtUOM" path="strUOM" cssClass="BoxW124px">
					<option selected="selected" value=""></option>
					<option value="Gram">Gram</option>
					<option value="Kg">Kg</option>
					<option value="Unit">Unit</option>
				</s:select>
				</td>
				<td>
					<label>Item Details &emsp;&ensp;&emsp;&ensp;</label>
					<s:textarea id="txtItemDetails" path="strItemDetails" class="txtTextArea" />
				</td>
				<td></td>
			</tr>
			
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
