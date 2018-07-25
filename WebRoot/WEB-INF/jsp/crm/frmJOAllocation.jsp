<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var listRow = 0;
	var remainingQty=0;

	$(document).ready(function() {
		
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
		$( "#txtJADate" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#txtJADate" ).datepicker('setDate','today');
		
		$( "#txtRefDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#txtRefDate" ).datepicker('setDate','today');

	});
	
	
	function funResetFields()
	{
		location.reload(true); 	
    }

	function funHelp(transactionName) 
	{
		fieldName = transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	
	}

	function funSetData(code) {

		switch (fieldName) {
		
		case 'AllJobOrder':
			funSetJOData(code);
			break;
		case 'JACode':
			funSetJAData(code);
			break;
		case 'subContractor':
			funSetSubContractorData(code);
			break;
		}
	}
	
	function funSetJAData(code) {

		funClearJAHdData();
		funClearJARowData();
		$("#txtJANo").focus();
		var searchURL = getContextPath()+ "/loadJAData.html?JACode=" + code;
		$.ajax({
			type : "GET",
			url : searchURL,
			dataType : "json",
			success : function(response) {
				if (response.strJACode == 'Invalid Code') {
					alert("Invalid Job Allocation Code Please Select Again");
					$("#txtJACode").val('');
					$("#txtJACode").focus();
				} else {
					$("#txtJACode").val(response.strJACode);
					$("#txtJANo").val(response.strJANo);
					$("#txtJADate").val(response.dteJADate);
					$("#txtSCCode").val(response.strSCCode);
					$("#txtSCName").text(response.strSCName);
	        		$("#txtRef").val(response.strRef);		
	        		$("#txtRefDate").val(response.dteRefDate)
					$("#txtDispatchMode").val(response.strDispatchMode);
					$("#txtPayment").val(response.strPayment);
					$("#txtTaxes").val(response.strTaxes);
					$("#txtTotQty").val(response.dbltotQty);

					$.each(response.objJOList, function(i,item){
						var objModel = response.objJOList[i];
						funAddJORow(objModel.strJOCode,objModel.strProdCode,objModel.strProdName,
								objModel.strNatureOfProcessing,objModel.strProdNo,objModel.dblQty,
								objModel.dblRate,objModel.dblTotalPrice,objModel.strRemarks);
 	       	    	 });
					
				}
			},
			error : function(jqXHR, exception) {
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

	function funClearJARowData(){
		$("#tblJODet tr").remove();
	}
	
	function funClearJAHdData(){
		$("#txtJACode").val('');
		$("#txtJANo").val('');
		$("#txtSCCode").val('');
		$("#txtSCName").text('');
		$("#txtRef").val('');		
		$("#txtDispatchMode").val('');
		$("#txtPayment").val('');
		$("#txtTaxes").val('');
		$("#txtTotQty").val('');
	}

	function funSetSubContractorData(code) {
		
		$("#txtRef").focus();
		gurl=getContextPath()+"/loadSubContractorMasterData.html?partyCode=";
		$.ajax({
	        type: "GET",
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {
	        	
	        		if('Invalid Code' == response.strPCode){
	        			alert("Invalid Sub Contractor Code");
	        			$("#txtSCCode").val('');
	        			$("#txtSCCode").focus();
	        			
	        		}else{
	        			
	        			$("#txtSCCode").val(response.strPCode);
						$("#txtSCName").text(response.strPName);
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
		
		
	}
	
	function funSetJOData(code) {
		
		$("#txtPrice").focus();
			gurl=getContextPath()+"/loadJobOrderData.html?JOcode="+code;
			 $.ajax({
				type : "GET",
				url : gurl,
				dataType : "json",
				success : function(response){ 
					
					
					if(response.strJOCode == "Invalid Code"){
						alert("Job Order Code Invalid Please select Again");
		       			$("#txtJOCode").val('');
		       			$("#txtJOCode").focus();
					}else{
						$("#txtJOCode").val(response.strJOCode);
						$("#txtProductCode").val(response.strProdCode);
						$("#txtProductName").text(response.strProdName);
						$("#txtQty").val(response.dblQty);
						remainingQty=response.dblQty;
					}
					
				},
				error : function(e){
	
				}
			});  
	}

	
	function btnAdd_onclick() {

		if ($("#txtJOCode").val().trim().length == 0) {
			alert("Please Enter Job Order Code Or Search");
			$('#txtJOCode').focus();
			return false;
		}

		if (($("#txtQty").val() == 0) || ($("#txtQty").val().trim().length == 0)) {
			alert("Please Enter Quantity ");
			$('#txtQty').focus();
			return false;
		}
		
		var txtJOCode = $("#txtJOCode").val();
		if (funDuplicateJO(txtJOCode)) {
			funFetchJORow();
		}
	}

	function funDuplicateJO(txtJOCode) {
		var table = document.getElementById("tblJODet");
		var rowCount = table.rows.length;
		var flag = true;
		if (rowCount > 0) {
			$('#tblJODet tr').each(function() {
				if (txtJOCode == $(this).find('input').val())// `this` is TR DOM element
				{
					alert("Already added " + txtJOCode);
					flag = false;
				}
			});

		}
		return flag;
	}

	
	function funFetchJORow() {
		
		var strJOCode = $("#txtJOCode").val().trim();
		var strProductCode = $("#txtProductCode").val();
		var strProductName = $("#txtProductName").text();
		var strNature = $("#txtNature").val();
		var strProdNo = $("#txtProdNo").val();
		var dblQty = $("#txtQty").val();
		var dblPrice = $("#txtPrice").val();
		var strRemark = $("#txtRemark").val();
		
		if(!(isNumber(dblQty))){
			dblQty=0;
			$("#txtQty").val("0");
		}
		
		if(!(isNumber(dblPrice))){
			dblPrice=0;
			$("#txtPrice").val("0");
		}
		
		dblQty = parseFloat(dblQty);
		dblPrice = parseFloat(dblPrice);
		var dblTotalPrice = dblPrice * dblQty;
		dblTotalPrice = parseFloat(dblTotalPrice);
		
		funAddJORow(strJOCode,strProductCode,strProductName,strNature,strProdNo,dblQty,dblPrice,dblTotalPrice,strRemark);
		
		funClearProductDetails();
		funApplyNumberValidation();
		funCalculateTotalQty();

		$('#txtJOCode').focus();
		return false;
		
	}
	
	function isNumber(n) {
		  return !isNaN(parseFloat(n)) && isFinite(n);
	}
	
	function funAddJORow(strJOCode,strProductCode,strProductName,strNature,strProdNo,dblQty,dblPrice,dblTotalPrice,strRemark) {
		
		var table = document.getElementById("tblJODet");
		var rowCount1 = table.rows.length;
		var row = table.insertRow(rowCount1);
		var rowCount = listRow;

		row.insertCell(0).innerHTML = "<input  readonly=\"readonly\" size=\"9%\" class=\"Box\" name=\"objJOList["+(rowCount)+"].strJOCode\" id=\"txtJOCode."
											+(rowCount) + "\" value='" + strJOCode + "' />";

		row.insertCell(1).innerHTML = "<input size=\"30%\"  readonly=\"readonly\"  name=\"objJOList["+(rowCount)+"].strProdCode\" class=\"Box\" id=\"txtProductCode."
											+ (rowCount) + "\" value='" + strProductCode + "' />";

		row.insertCell(2).innerHTML = "<input size=\"60%\" readonly=\"readonly\"  name=\"objJOList["+(rowCount)+"].strProdName\" class=\"Box\" id=\"txtProductName."
											+ (rowCount) + "\" value='" + strProductName + "' />";

		row.insertCell(3).innerHTML = "<input size=\"60%\" readonly=\"readonly\" name=\"objJOList["+(rowCount)+"].strNatureOfProcessing\" class=\"Box\" id=\"txtNature."
											+ (rowCount) + "\" value='" + strNature + "' />";

		row.insertCell(4).innerHTML = "<input size=\"20%\" readonly=\"readonly\" name=\"objJOList["+ (rowCount)+"].strProdNo\" class=\"Box\"  id=\"txtProdNo."
											+ (rowCount)+ "\" value='"+ strProdNo+ "' />";

		row.insertCell(5).innerHTML = "<input size=\"20%\" name=\"objJOList["+ (rowCount)+ "].dblQty\" class=\"decimal-places inputText-Auto dblQty\" id=\"dblQty."
											+ (rowCount)+ "\" value='"+ dblQty + "' onchange=\"funUpdatePrice(this)\" />";

		row.insertCell(6).innerHTML = "<input size=\"20%\" name=\"objJOList["+ (rowCount)+ "].dblRate\" class=\"decimal-places inputText-Auto dblPrice\" id=\"dblPrice."
											+ (rowCount)+ "\" value='"+ dblPrice + "' onchange=\"funUpdatePrice(this)\" />";

		row.insertCell(7).innerHTML = "<input size=\"10%\" readonly=\"readonly\" name=\"objJOList["+ (rowCount)+ "].dblTotalPrice\" class=\"Box dblTotalPrice\" id=\"dblTotalPrice."
											+ (rowCount)+ "\" value='"+ dblTotalPrice+ "' />";

		row.insertCell(8).innerHTML = "<input size=\"5%\" readonly=\"readonly\" name=\"objJOList["+ (rowCount)+ "].strRemarks\" class=\"Box\" id=\"strRemark."
											+ (rowCount) + "\" value='" + strRemark + "' />";

		row.insertCell(9).innerHTML = '<input size=\"3%\" type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';

		listRow++;

	}
	
	function funCalculateTotalQty() {
		var totalQty = 0;

		$('#tblJODet tr').each(function() {
			var qty=0;
			if ($(this).find(".dblQty").val() != '') {
				qty = parseFloat($(this).find(".dblQty").val());
			}
			totalQty = qty + totalQty;
		});

		$("#txtTotQty").val(totalQty);
	}

	function funUpdatePrice(object) {

		var dblQty ="";
		var dblPrice ="";
		var $row = $(object).closest("tr");
		
		if ($row.find(".dblQty").val() != '') {
			dblQty = parseFloat($row.find(".dblQty").val());
		}
		
		if ($row.find(".dblPrice").val() != '') {
			dblPrice = parseFloat($row.find(".dblPrice").val());
		}

		if(!(isNumber(dblQty))){
			dblQty=0;
			$row.find(".dblQty").val("0");
		}
		
		if(!(isNumber(dblPrice))){
			dblPrice=0;
			$row.find(".dblPrice").val("0");
		}
		
		var dblTotalPrice = dblPrice * dblQty;
		dblTotalPrice = parseFloat(dblTotalPrice);
		$row.find(".dblTotalPrice").val(dblTotalPrice);
		
		funCalculateTotalQty();
	}

	function funDeleteRow(obj) {
		var index = obj.parentNode.parentNode.rowIndex;
		var table = document.getElementById("tblJODet");
		table.deleteRow(index);
		
		funCalculateTotalQty();
	}

	function funClearProductDetails() {
		
		$("#txtJOCode").val("");
		$("#txtProductName").text("");
		$("#txtProductCode").val("");
		$("#txtNature").val("");
		$("#txtProdNo").val("");
		$("#txtQty").val("");
		$("#txtPrice").val("");
		$("#txtRemark").val("");
	}

	$(function() {
		
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
				alert(message+"\n Saved Successfully");
		<%
		}}%>
		
		$('#txtJACode').blur(function() {
			var code = $('#txtJACode').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funSetJAData(code);(code);
			}
		});

		$('#txtJOCode').blur(function() {
			var code = $('#txtJOCode').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funSetJOData(code);
			}
		});

		$('#txtSCCode').blur(function() {
			var code = $('#txtSCCode').val();
			if (code.trim().length > 0 && code != "?" && code != "/") {
				funSetSubContractorData(code);
			}
		});

		
		$("#reset").click(function(){
			location.reload();
		});

		$("form").submit(function() {

			if ($("#txtSCCode").val() == '') {
				alert("Please Enter Sub Contractor Or Search");
				return false;
			} 
			
			var table = document.getElementById("tblJODet");
			var rowCount = table.rows.length;
			if (rowCount == 0) {
				alert("Please Add Job Order in Grid");
				return false;
			}

		});

	});
	
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
		<label>Job Order Allocation</label>
	</div>
	<br />
	<s:form name="JOAllocation" method="POST" action="saveJOAllocation.html?saddr=${urlHits}">
		<br>
		<table class="transTable">
			<tr>
				<td>
					<label>JA Code</label>
				</td>
				<td>
					<s:input id="txtJACode" ondblclick="funHelp('JACode')" type="text" path="strJACode" cssClass="searchTextBox" />
				</td>
				<td></td>
				<td>JA NO</td>
				<td>
					<s:input id="txtJANo" type="text" path="strJANo" cssClass="BoxW116px" required="true" />
				</td>
				<td>Date</td>
				<td>
					<s:input type="text" id="txtJADate" class="calenderTextBox"	path="dteJADate" />
				</td>
			</tr>
			
			<tr>
				<td>Sub Contractor</td>
				<td>
					<s:input id="txtSCCode" type="text" required="true" ondblclick="funHelp('subContractor')" path="strSCCode" cssClass="searchTextBox" />
				</td>
				<td><label id="txtSCName"></label></td>
				<td>
					<label>Ref</label>
				</td>
				<td>
					<s:input id="txtRef" type="text" path="strRef" cssClass="BoxW116px" />
				</td>
				<td>Ref Date</td>
				<td>
					<s:input type="text" id="txtRefDate" class="calenderTextBox"	path="dteRefDate" />
				</td>
			</tr>
			
			<tr>
				<td>Dispatch Mode</td>
				<td>
					<s:input id="txtDispatchMode" type="text" required="true" path="strDispatchMode" cssClass="BoxW116px" />
				</td>
				<td></td>
				<td>Payment</td>
				<td>
					<s:input id="txtPayment" type="text" required="true" path="strPayment" cssClass="BoxW116px" />
				</td>
				<td>Taxes</td>
				<td>
					<s:input id="txtTaxes" type="text" required="true" path="strTaxes" cssClass="BoxW116px" />
				</td>
			</tr>
			
		</table>
		
		<br>
		<table class="transTableMiddle">
			<tr>
				<td><label>JO Code</label></td>
				<td><input id="txtJOCode" ondblclick="funHelp('AllJobOrder')" class="searchTextBox"  /></td>
				<td colspan="2">Product &nbsp;<label id="txtProductName"></label></td>
				<td><input id="txtProductCode" type="hidden"></td>
				
				<td><label>Quantity</label></td>
				<td><input type="text" id="txtQty" class="BoxW116px decimal-places"/></td>
				
				<td><label>Unit Price</label></td>
				<td><input type="text" id="txtPrice" class="BoxW116px decimal-places" /></td>
				
			</tr>
			<tr>
				<td><label>Nature Of Processing</label></td>
				<td><input id="txtNature"  class="BoxW116px"  /></td>
				
				<td><label>product No</label></td>
				<td><input type="text" id="txtProdNo" class="BoxW116px decimal-places" /></td>
				
				<td><label>Remark</label></td>
				<td colspan="5"><input type="text" id="txtRemark" class="longTextBox" /></td>
			</tr>
			<tr>
				<td colspan="4"></td>
				<td><input type="Button" value="Add" onclick="return btnAdd_onclick()" class="smallButton" /></td>
				<td colspan="4"></td>
			</tr>
		</table>
		<br>
		<div class="dynamicTableContainer">
			<table style="height: 20px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">

				<tr bgcolor="#72BEFC">
					<td width="8%">JO Code</td>
					<td width="8%">Product Code</td>
					<td width="20%">Product Name</td>
					<td width="20%">Nature Of Processing</td>
					<td width="8%">product No.</td>
					<td width="8%">Quantity</td>
					<td width="8%">Unit Price</td>
					<td width="8%">Total Price</td>
					<td width="8%">Reamark</td>
					<td width="2%">Delete</td>
				</tr>
			</table>

			<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">

				<table id="tblJODet" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll" class="transTablex col13-center">
					<tbody>
						<col style="width: 8%">
						<col style="width: 8%">
						<col style="width: 20%">
						<col style="width: 20%">
						<col style="width: 8%">
						<col style="width: 8%">
						<col style="width: 8%">
						<col style="width: 8%">
						<col style="width: 8%">
						<col style="width: 2%">
					</tbody>
				</table>
			</div>
		</div>

		<br/>	
			<table class="transTable">
				<tr>
				<td></td>
					<td>
						<label>Total Quantity</label>
					</td>
					<td>
						<s:input id="txtTotQty" type="text" required="true" readonly="true" path="dbltotQty" cssClass="BoxW116px" />
					</td>
					<td></td>
				</tr>
			</table>
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" /> 
			<input type="button" id="reset" value="Reset" class="form_button" />
		</p>
		<br />
		<br />

		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>