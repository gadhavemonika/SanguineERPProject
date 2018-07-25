<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName;

	$(document).ready(function() 
	{
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
		$('#txtDispatchTime').timepicker({ 'step': 15 });
		
		$( "#txtDispatchDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#txtDispatchDate" ).datepicker('setDate','today');
		
		$( "#txtECDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#txtECDate" ).datepicker('setDate','today');
		
		$( "#txtGRChallanDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#txtGRChallanDate" ).datepicker('setDate','today');
		
		var message='';
		<%if (session.getAttribute("success") != null) {
			            if(session.getAttribute("successMessage") != null){%>
			            message='<%=session.getAttribute("successMessage").toString()%>';
			            <%
			            session.removeAttribute("successMessage");
			            }
						boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
						session.removeAttribute("success");
						if (test) {
						%>	
			alert("Data Save successfully For \n\n"+message);
		<%
		}}%>
		
	});

	function funSetData(code){

		switch(fieldName){

			case 'ECCode' : 
				funSetECCode(code);
				break;
			case 'ProdCode' : 
				funSetProdCode(code);
				break;
			case 'ProcessCode' : 
				funSetProcessCode(code);
				break;
		}
	}


	function funSetECCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadExciseChallanData.html?ecCode=" + code,
			dataType : "json",
			success : function(response){ 

			},
			error : function(e){

			}
		});
	}

	function funSetId(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadId.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

			},
			error : function(e){

			}
		});
	}



	function funSetProdCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadProdCode.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

			},
			error : function(e){

			}
		});
	}

	function funSetProcessCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadProcessCode.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

			},
			error : function(e){

			}
		});
	}



	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Excise Challan</label>
	</div>

<br/>
<br/>

	<s:form name="ExciseChallan" method="POST" action="saveExciseChallan.html?saddr=${urlHits}">

		<table class="masterTable">
			<tr>
				<td>
					<label>EC Code</label>
				</td>
				<td>
					<s:input type="text" id="txtECCode" path="strECCode" cssClass="searchTextBox" ondblclick="funHelp('ecCode');"/>
				</td>
				<td>
					<label>EC Date</label>
				</td>
				<td>
					<s:input type="text" id="txtECDate" path="dteECDate" cssClass="calenderTextBox" />
				</td>
				<td colspan="2"></td>
				</tr>
				
				<tr>
				<td>
					<label>Against</label>
				</td>
				<td>
					<s:select id="txtAgainst" path="strAgainst"  cssClass="BoxW124px">
					<s:option value="deliveryNote">Delivery Note</s:option>
					<s:option value="purchaseReturn">Purchase Return</s:option>
					</s:select>
				</td>
				
				<td>
					<label>GR Challan Code</label>
				</td>
		<%-- 		<td>
					<s:input type="text" id="txtGRChallanCode" path="strGRChallanCode" cssClass="searchTextBox" ondblclick="funHelp('GRChallanCode');"/>
				</td> --%>
				
				<td>
					<s:input type="text" id="txtGRChallanCode" path="strGRChallanCode" cssClass="BoxW124px" />
				</td>
				
				<td>
					<label>GRN Date</label>
				</td>
				<td>
					<s:input type="text" id="txtGRChallanDate" path="dteGRChallanDate" cssClass="calenderTextBox" />
				</td>
				</tr>
				
				<tr>
				<td>
					<label>S Code</label>
				</td>
				<td>
					<s:input type="text" id="txtScode" path="strScode" cssClass="BoxW124px" />
				</td>
				<td colspan="4">
			</tr>
			
			<tr>
				
				<td>
					<label>Product Code</label>
				</td>
				<%-- <td>
					<s:input type="text" id="txtProdCode" path="strProdCode" cssClass="searchTextBox" ondblclick="funHelp('ProdCode');"/>
				</td> --%>
				
				<td>
					<s:input type="text" id="txtProdCode" path="strProdCode" cssClass="BoxW124px" />
				</td>
				<td colspan="4"></td>
			</tr>
			
			<tr>
				<td>
					<label>Process Code</label>
				</td>
<!-- 				<td> -->
<%-- 					<s:input type="text" id="txtProcessCode" path="strProcessCode" cssClass="searchTextBox" ondblclick="funHelp('ProcessCode');"/> --%>
<!-- 				</td> -->
				
				<td>
					<s:input type="text" id="txtProcessCode" path="strProcessCode" cssClass="BoxW124px" />
				</td>
				<td colspan="4"></td>
			</tr>
			
			<tr>
				
				<td>
					<label>Challan Type</label>
				</td>
				<td>
					<s:select id="txtChallanType" path="strChallanType" cssClass="BoxW124px">
						<s:option value="jobOrder">Job Order</s:option>
						<s:option value="thirdParty">Third party</s:option>
						<s:option value="return">Return</s:option>
					</s:select>
				</td>
				<td>
					<label>Tariff</label>
				</td>
				<td>
					<s:input type="text" id="txtTariff" path="strTariff" cssClass="BoxW124px" />
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td>
					<label>Dispatch Date</label>
				</td>
				<td>
					<s:input type="text" id="txtDispatchDate" path="dteDispatchDate" cssClass="calenderTextBox" />
				</td>
				<td>
					<label>Dispatch Time</label>
				</td>
				<td>
					<s:input type="datetime-local " id="txtDispatchTime" path="dteDispatchTime" class="BoxW124px" />
				</td>					
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td>
					<label>Quantity</label>
				</td>
				<td>
					<s:input type="text"  id="txtQty" path="dblQty" class="BoxW124px decimal-places-amt numeric" />
				</td>
				<td>
					<label>Unit Price</label>
				</td>
				<td>
					<s:input type="text" id="txtUnitPrice" path="dblUnitPrice" class="BoxW124px decimal-places-amt numeric" />
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td>
					<label>Identity Marks</label>
				</td>
				<td>
					<s:input type="text" id="txtIdentityMarks" path="strIdentityMarks" cssClass="longTextBox" />
				</td>
				<td>
					<label>Duration</label>
				</td>
				<td>
					<s:input type="text" id="txtDuration" path="strDuration" cssClass="BoxW124px" />
				</td>
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td>
					<label>Currency</label>
				</td>
				<td>
					<s:input type="text" id="txtCurrency" path="strCurrency" cssClass="longTextBox" />
				</td>
				<td colspan="4"></td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
	
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>
