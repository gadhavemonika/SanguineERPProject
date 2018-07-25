<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Card Type Master</title>
<style>
.ui-autocomplete {
	max-height: 200px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	/* add padding to account for vertical scrollbar */
	padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
	height: 200px;
}
</style>
<script type="text/javascript">
	var fromdate="";
	$(document).ready(function() {
		
		
		var POSDate="${POSDate}"
		var startDate="${POSDate}";
		var Date = startDate.split(" ");
		var arr = Date[0].split("-");
		Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
		$("#txtdteToDate").datepicker({ dateFormat: 'dd-mm-yy'  });
		$("#txtdteToDate" ).datepicker('setDate', Dat);
			
		
		fromdate =$("#txtdteToDate").val();
		
		 $('input#txtCardTypeCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtCardName').mlKeyboard({layout: 'en_US'});
		  $('input#txtValidityDays').mlKeyboard({layout: 'en_US'});
		  $('input#txtCardValueFixed').mlKeyboard({layout: 'en_US'});
		  $('input#txtDepositAmt').mlKeyboard({layout: 'en_US'});
		  $('input#txtMaxVal').mlKeyboard({layout: 'en_US'});
		  $('input#txtMinVal').mlKeyboard({layout: 'en_US'});
		  $('input#txtMaxRefundAmt').mlKeyboard({layout: 'en_US'});
		  $('input#txtMinCharge').mlKeyboard({layout: 'en_US'});
		  $('input#txtRedemptionLimitType').mlKeyboard({layout: 'en_US'});
		  $('input#txtRedemptionLimitType').mlKeyboard({layout: 'en_US'});
		
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		
		$("form").submit(function(event)
		{
			
			
			  if($("#txtMaximum").val() < $("#txtMinimum").val())
			  {
				  alert("Please Enter Valid Value");
			  }	
			  
			  if($("#txtCardName").val().trim()=="")
				{
					alert("Please Enter Card Name");
					return false;
				}
			  if($("#txtValidityDays").val().trim()=="")
				{
					alert("Please Enter Validity Days");
					return false;
				}
			var todate = $("#txtdteToDate").val();
			
			 var frmDate= fromdate.split('-');
			 Dat=frmDate[2]+frmDate[1]+frmDate[0];	
			 fDate = Dat;

			    var tDate= todate.split('-');
				 Dat=tDate[2]+tDate[1]+tDate[0];	
				t1Date =  Dat;

	    	var dateDiff=t1Date-fDate;
			
		    	var dateDiff=t1Date-fDate;
				
	            
				  if(dateDiff<0)
				  {
						alert("Please Enter Valid Date");
						return false;
				  }

			  else{
				  
				 
				 flg=funChekTable();
					 
				 
				  return flg;
			  }
			});
	});
</script>
<script type="text/javascript">
var field;


	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
	location.reload(true);
		
    }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	 
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
		

		/**
		* Success Message After Saving Record
		**/
		$(document).ready(function()
		{
			var message='';
			<%if (session.getAttribute("success") != null) {
				if (session.getAttribute("successMessage") != null) {%>
					message='<%=session.getAttribute("successMessage").toString()%>
	';
<%session.removeAttribute("successMessage");
				}
				boolean test = ((Boolean) session.getAttribute("success"))
						.booleanValue();
				session.removeAttribute("success");
				if (test) {%>
	alert("Data Saved \n\n"
								+ message);
<%}
			}%>
	});

	function funSetData(code) {

		$("#txtCardTypeCode").val(code);
		var searchurl = getContextPath()
				+ "/loadPOSDebitCardMasterData.html?cardTypeCode=" + code;
		$
				.ajax({
					type : "GET",
					url : searchurl,
					dataType : "json",
					async : false,
					success : function(response) {
						if (response.strCardTypeCode == 'Invalid Code') {
							alert("Invalid Group Code");
							$("#txtCardTypeCode").val('');
						} else {

							$("#txtCardName").val(response.strCardName);
							$("#txtCardName").focus();

							if (response.strDebitOnCredit == 'Y') {
								$("#chkDebitOnCredit").prop('checked', true);
							}

							if (response.strRoomCard == 'Y') {
								$("#chkRoomCard").prop('checked', true);
							}

							if (response.strComplementary == 'Y') {
								$("#chkComplementary").prop('checked', true);
							}

							if (response.strAutoTopUp == 'Y') {
								$("#chkAutoTopUp").prop('checked', true);
							}

							if (response.strRedeemableCard == 'Y') {
								$("#chkRedeemableCard").prop('checked', true);
							}

							if (response.strCardInUse == 'Y') {
								$("#chkCardInUse").prop('checked', true);
							}

							if (response.strEntryCharge == 'Y') {
								$("#chkEntryCharge").prop('checked', true);
							}

							if (response.strCoverCharge == 'Y') {
								$("#chkCoverCharge").prop('checked', true);
							}

							if (response.strDiplomate == 'Y') {
								$("#chkDiplomate").prop('checked', true);
							}

							if (response.strAllowTopUp == 'Y') {
								$("#chkAllowTopUp").prop('checked', true);
							}

							if (response.strExValOnTopUp == 'Y') {
								$("#chkExValOnTopUp").prop('checked', true);
							}

							if (response.strCustomerCompulsory == 'Y') {
								$("#chkCustomerCompulsory").prop('checked',
										true);
							}

							if (response.chkSetExpiryDt == 'Y') {
								$("#strSetExpiryDt").prop('checked', true);
							}

							$("#txtdteToDate").val(response.dteExpiryDt);
							if (response.strCurrentFinacialYr == 'Y') {
								$("#chkCurrentFinacialYr")
										.prop('checked', true);
							}

							if (response.strCashCard == 'Y') {
								$("#chkCashCard").prop('checked', true);
							}

							$("#txtValidityDays").val(response.strValidityDays);
							if (response.strAuthorizeMemberCard == 'Y') {
								$("#chkAuthorizeMemberCard").prop('checked',
										true);
							}

							$("#txtCardValueFixed").val(
									response.strCardValueFixed);
							$("#txtDepositAmt").val(response.dblDepositAmt);
							$("#txtMaxVal").val(response.dblMaxVal);
							$("#txtMinVal").val(response.dblMinVal);
							$("#txtMaxRefundAmt").val(response.dblMaxRefundAmt);
							$("#txtMinCharge").val(response.dblMinCharge);
							$("#txtRedemptionLimitType").val(
									response.strRedemptionLimitType);

							if (response.strRedemptionLimitType == 'NA') {
								document.getElementById("txtRedumptionValue").disabled = true;

							} else {
								$("#txtRedumptionValue").val(
										response.strRedemptionLimitValue);
							}

							//fill Settle Table
							funRemoveTableRows("tblSettlement");
							//funLoadSettlementData(code);
							funLoadSettlementDataForUpdate(response.listSettlementDtl);
							//funLoadSettlementData(response.listSettlementDtl);

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

	function funLoadSettlementDataForUpdate(list) {

		var searchurl = getContextPath() + "/LoadSettlmentData.html";
		$.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",

			success : function(response) {
				funRemoveTableRows("tblSettlement");
				// for (var i in response){		            	
				$.each(response,
						function(i, item) {

							funfillDebitCardSettlementDtlGrid(
									item.strSettlementCode,
									item.strSettlementDesc,
									item.strApplicableYN, list);

						});

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

	function funfillDebitCardSettlementDtlGrid(strSettlementCode,
			strSettlementDesc, strApplicableYN, list) {
		var flag = false;

		var table = document.getElementById("tblSettlement");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		row.insertCell(0).innerHTML = "<input type=\"hidden\"  name=\"listSettlementDtl["
				+ (rowCount)
				+ "].strSettlementCode\" readonly=\"readonly\" class=\"Box \" size=\"35%\" id=\"txtSettlementCode."
				+ (rowCount) + "\" value='" + strSettlementCode + "'>";
		row.insertCell(1).innerHTML = "<input name=\"listSettlementDtl["
				+ (rowCount)
				+ "].strSettlementDesc\" readonly=\"readonly\" class=\"Box \" size=\"35%\" id=\"txtSettlementDesc."
				+ (rowCount) + "\" value='" + strSettlementDesc + "'>";
		$
				.each(
						list,
						function(i, item) {
							if (item.strSettlementCode == strSettlementCode) {
								flag = true;
								row.insertCell(2).innerHTML = "<input type=\"checkbox\" name=\"listSettlementDtl["
										+ (rowCount)
										+ "].strApplicableYN\" size=\"30%\" id=\"chkApplicable."
										+ (rowCount)
										+ "\" checked=\"checked\">";

							}

						});
		if (!flag) {
			row.insertCell(2).innerHTML = "<input type=\"checkbox\" name=\"listSettlementDtl["
					+ (rowCount)
					+ "].strApplicableYN\" size=\"30%\" id=\"chkApplicable."
					+ (rowCount) + "\" value='" + true + "'>";
		}

	}

	function funLoadTableData() {

		document.getElementById("txtRedumptionValue").disabled = true;
		funLoadDebitCardSettlementData();

		$('#txtValidityDays').val("");
	}

	function funRemoveTableRows(tableId) {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;
		while (rowCount > 0) {
			table.deleteRow(0);
			rowCount--;
		}
	}

	function funCombo() {
		if (document.getElementById("txtRedemptionLimitType").value == "NA") {
			document.getElementById("txtRedumptionValue").disabled = true;

		}
		if (document.getElementById("txtRedemptionLimitType").value == "Daily") {
			document.getElementById("txtRedumptionValue").disabled = false;

		}
		if (document.getElementById("txtRedemptionLimitType").value == "Monthly") {
			document.getElementById("txtRedumptionValue").disabled = false;

		}

	}

	function funLoadDebitCardSettlementData() {

		var searchurl = getContextPath() + "/LoadSettlmentData.html";
		$.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",

			success : function(response) {
				funRemoveTableRows("tblSettlement");

				$.each(response, function(i, item) {

					funfillDebitCardSettlementDtlGrid(item.strSettlementCode,
							item.strSettlementDesc, null);

				});

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

	/**
	 *  Check Validation Before Saving Record
	 **/
	function funCallFormAction() {
		var flg = true;

		// 			if($('#txtCardTypeCode').val()=='')
		// 			{
		var cardname = $('#txtCardName').val();
		var cardcode = $('#txtCardTypeCode').val();
		$.ajax({
			type : "GET",

			url : getContextPath() + "/checkCardName.html?strCardName=" + code
					+ "&strCardTypeCode=" + cardcode,
			async : false,
			dataType : "text",
			success : function(response) {
				if (response == "false") {
					alert("This Card Name is already present!");
					$('#txtCardName').focus();
					flg = false;
				}

				else {
					flg = true;
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

		return flg;
	}

	function funChekTable() {
		var flag = false;

		$('#tblSettlement tr').each(function() {
			var checkbox = $(this).find("input[type='checkbox']");

			if (checkbox.prop("checked")) {
				flag = true;
			}
		});

		if (!flag) {
			alert("Please select atleast one Settlement Mode");
			return flag;
		}

		flag = funCallFormAction();
		return flag;
	}
</script>


</head>

<body onload="funLoadTableData()">
	<div id="formHeading">
		<label>Card Type Master</label>
	</div>
	<s:form name="Card Type Master" method="POST"
		action="savePOSDebitCardMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table
			style="border: 0px solid black; width: 85%; height: 130%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>
					<div id="tab_container">
						<ul class="tabs">
							<li class="active" data-state="tab1">Card Details</li>
							<li data-state="tab2">Card Values</li>


						</ul>
						<br /> <br />

						<!--  Start of Generals tab-->

						<div id="tab1" class="tab_content">
							<table class="masterTable">

								<tr>
									<td><label>Card Type Code</label></td>
									<td><s:input colspan="3" type="text" id="txtCardTypeCode"
											path="strCardTypeCode" cssClass="searchTextBox"
											readonly="true" ondblclick="funHelp('POSDebitCardMaster')" />
									</td>
									<td></td>
									<td></td>
								</tr>

								<tr>
									<td><label>Card Name</label></td>
									<td><s:input type="text" id="txtCardName"
											name="txtCardName" path="strCardName"
											cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
									</td>
								</tr>
								<tr>
									<td><s:checkbox element="li" id="chkDebitOnCredit"
											path="strDebitOnCredit" value="Yes" /> <label>Debit
											On Credit</label></td>

									<td><s:checkbox element="li" id="chkRoomCard"
											path="strRoomCard" value="Yes" /> <label>Room Card</label></td>

									<td><s:checkbox element="li" id="chkComplementary"
											path="strComplementary" value="Yes" /> <label>Complimentary</label>
									</td>

									<td><s:checkbox element="li" id="chkAutoTopUp"
											path="strAutoTopUp" value="Yes" /> <label>Auto Top
											Up</label></td>

								</tr>
								<tr>
									<td><s:checkbox element="li" id="chkRedeemableCard"
											path="strRedeemableCard" value="Yes" /> <label>Redeemable
											Card</label></td>

									<td><s:checkbox element="li" id="chkCardInUse"
											path="strCardInUse" value="Yes" /> <label>Card Type
											In Use</label></td>

									<td><s:checkbox element="li" id="chkEntryCharge"
											path="strEntryCharge" value="Yes" /> <label>Entry
											Charge</label></td>

									<td><s:checkbox element="li" id="chkCoverCharge"
											path="strCoverCharge" value="Yes" /> <label>Cover
											Charge</label></td>

								</tr>
								<tr>

									<td><s:checkbox element="li" id="chkDiplomate"
											path="strDiplomate" value="Yes" /> <label>Diplomate</label>
									</td>

									<td><s:checkbox element="li" id="chkAllowTopUp"
											path="strAllowTopUp" value="Yes" /> <label>Allow Top
											Up</label></td>

									<td><s:checkbox element="li" id="chkExValOnTopUp"
											path="strExValOnTopUp" value="Yes" /> <label>Extended
											Validity On Top Up</label></td>

									<td><s:checkbox element="li" id="chkCustomerCompulsory"
											path="strCustomerCompulsory" value="Yes" /> <label>Customer
											Compulsory</label></td>

								</tr>
								<tr>

									<td><s:checkbox element="li" id="chkSetExpiryDt"
											path="strSetExpiryDt" value="Yes" /> <label>Set
											Expiry Date</label></td>

									<td><s:input id="txtdteToDate" name="txtdteToDate"
											path="dteExpiryDt" cssClass="calenderTextBox" required="true" />
									</td>
									<td><s:checkbox element="li" id="chkCurrentFinacialYr"
											path="strCurrentFinacialYr" value="Yes" /> <label>For
											Current Financial Year</label></td>

									<td><s:checkbox element="li" id="chkCashCard"
											path="strCashCard" value="Yes" /> <label>Cash Card</label></td>

								</tr>
								<tr>
									<td><label>Validity Days</label></td>
									<td><s:input type="text" id="txtValidityDays"
											name="txtValidityDays" path="strValidityDays"
											cssStyle="text-transform: uppercase;" cssClass="longTextBox"
											value="" /></td>
									<td><s:checkbox element="li" id="chkAuthorizeMemberCard"
											path="strAuthorizeMemberCard" value="Yes" /> <label>Authorize
											Member Card</label></td>
									<td></td>

								</tr>


							</table>
						</div>
						<!--  End of  First  tab-->


						<!-- Start of Settlement tab -->

						<div id="tab2" class="tab_content">
							<table class="masterTable">
								<tr>
									<td><label>Card Value Fixed</label></td>
									<td><s:input type="text" id="txtCardValueFixed"
											name="txtCardValueFixed" path="strCardValueFixed"
											required="true" cssStyle="text-transform: uppercase;"
											cssClass="longTextBox" /></td>

									<td><label>Deposit Amount</label></td>
									<td><s:input type="text" id="txtDepositAmount"
											name="txtDepositAmt" path="dblDepositAmt" required="true"
											cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
									</td>
								</tr>
								<tr>
									<td><label>Maximum</label></td>
									<td><s:input type="text" id="txtMaximum" name="txtMaxVal"
											path="dblMaxVal" required="true"
											cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
									</td>
									<td><label>Minimum</label></td>
									<td><s:input type="text" id="txtMinimum" name="txtMinVal"
											path="dblMinVal" required="true"
											cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
									</td>
								</tr>
								<tr>
									<td><label>Maximum Refundable Amount</label></td>
									<td><s:input type="text" id="txtMaximumRefundableAmount"
											name="txtMaxRefundAmt" path="dblMaxRefundAmt" required="true"
											cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
									</td>
									<td><label>Minimum Charges</label></td>
									<td><s:input type="text" id="txtMinCharge"
											name="txtMinCharge" path="dblMinCharge" required="true"
											cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
									</td>
								</tr>
								<tr>
									<td><label>Redemption Limit Type</label></td>
									<td><s:select id="txtRedemptionLimitType"
											name="txtRedemptionLimitType" path="strRedemptionLimitType"
											cssClass="BoxW124px" onclick="funCombo()">

											<option value="NA">NA</option>
											<option value="Daily">Daily</option>
											<option value="Monthly">Monthly</option>
										</s:select></td>

									<td><label>Value</label></td>
									<td><s:input type="text" id="txtRedumptionValue"
											name="txtRedumptionValue" path="strRedemptionLimitValue"
											required="true" cssStyle="text-transform: uppercase;"
											cssClass="longTextBox" /></td>
								</tr>
								<tr>
									<td><label>Recharge Modes</label></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>

							<table border="1" class="myTable"
								style="width: 80%; margin: auto;">
								<thead>
									<tr>

										<th style="width: 40%">SettleMent Name</th>
										<th style="width: 40%">Select</th>

									</tr>

								</thead>
							</table>
							<div
								style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
								<table id="tblSettlement" class="transTablex col5-center"
									style="width: 100%;">
									<tbody>
									<col style="width: 0%;">
									<!--  COl1   -->
									<col style="width: 50%">
									<!--  COl2   -->
									<col style="width: 50%">
									<!--  COl3   -->
									</tbody>
								</table>


							</div>
						</div>

						<!-- 									
							
							<!-- End of Settlement tab -->

					</div>
		</table>
		<br />
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />
		</p>

	</s:form>

</body>
</html>