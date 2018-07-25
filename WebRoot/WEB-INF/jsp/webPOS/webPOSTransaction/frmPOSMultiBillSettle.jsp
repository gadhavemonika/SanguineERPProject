<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Multiple Bill Settle</title>
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
</head>
<script type="text/javascript">


/**
* Success Message After Saving Record
**/
$(document).ready(function()
{
// 	$("#select").click(function ()
// 			{
// 				$(".Box").prop('checked', $(this).prop('checked'));
// 				funOnClickAllChecked();
				$("#select").click(function () {
		            $(".deletebutton").attr('checked', this.checked);
		        });
			//});
	
	
	var message='';
	<%if (session.getAttribute("success") != null) {if (session.getAttribute("successMessage") != null) {%>message='<%=session.getAttribute("successMessage").toString()%>';
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
	function funGetRowIndex(x) {
		//var table = document.getElementById("tblBillSettle");
		// 	$('#tblBillSettle tr').each(function() {
		//     if (!this.rowIndex) return; // skip first row
		//     var customerId = this.cells[6].innerHTML;

		var rowIndex = x.rowIndex
		alert('Row index: ' + table.row(this).index());
		$(
				'input[name="listUnsettleBillDtl["' + rowIndex
						+ '"].strSelectedData"]:checked').each(function() {
			alert();
		});

	}

	function btnSubmit_Onclick() {

		var col = "";
		var count = 0;
		var totAmt = 0;
		var totAmt1 = 0;

		$(".deletebutton:checked").each(function() {
			var table = document.getElementById("tblBillSettle");
			var row = table.rows[table.rows.length - 1];
			var cell = row.cells[6];
			var column = cell.cellIndex;
			var rowIndex = this.parentNode.parentNode.rowIndex;
			//$("txtamt"+column).val();
			totAmt = $("#txtamt" + rowIndex).val();
			totAmt1 += parseInt(totAmt);
			count++;
		});

		$("#totAmount").text(parseInt(totAmt1));
		$("#noOfBills").text(parseInt(count));

		if (totAmt1 == "") {
			alert("Please Select To Location");
			return false;
		}

	}

	function funLoadSettleBillDtlData() {

		var searchurl = getContextPath() + "/LoadUnsettleBillDtlData.html";
		$.ajax({
			type : "GET",
			url : searchurl,
			dataType : "json",
			async : false,

			success : function(response) {
				    	
				$.each(response.UnsettleBillDtl, function(i, item) {
					funfillSettleBillDtlData(item.strBillNo, item.strTableName,
							item.strCustomerName, item.strBuildingName,
							item.strDPName, item.dteBillDate,
							item.dblGrandTotal);

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

	function funfillSettleBillDtlData(strBillNo, strTableName, strCustomerName,
			strBuildingName, strDPName, dteBillDate, dblGrandTotal) {
		var table = document.getElementById("tblBillSettle");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		row.insertCell(0).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strBillNo\" readonly=\"readonly\" class=\"unsettleCheckBoxClass\" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + strBillNo + "'>";
		row.insertCell(1).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strTableName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadName."
				+ (rowCount) + "\" value='" + strTableName + "'>";
		row.insertCell(2).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strCustomerName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + strCustomerName + "'>";
		row.insertCell(3).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strBuildingName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + strBuildingName + "'>";
		row.insertCell(4).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strDPName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + strDPName + "'>";
		row.insertCell(5).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].dteBillDate\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + dteBillDate + "'>";
		row.insertCell(6).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].dblGrandTotal\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + dblGrandTotal + "'>";

		row.insertCell(7).innerHTML = "<input type=\"checkbox\"  size=\"30%\" id=\"chkSelect."
				+ (rowCount) + "\" value=\"Tick\" >";

	}
	function funfillSettleBillDtlData1(strBillNo, strTableName, strWShortName,
			strCustomerName, dteBillDate, dblGrandTotal) {
		var table = document.getElementById("tblBillSettle1");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		row.insertCell(0).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strBillNo\" readonly=\"readonly\" class=\"unsettleCheckBoxClass\" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + strBillNo + "'>";
		row.insertCell(1).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strTableName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadName."
				+ (rowCount) + "\" value='" + strTableName + "'>";
		row.insertCell(2).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strWShortName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + strWShortName + "'>";
		row.insertCell(3).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].strCustomerName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + strCustomerName + "'>";
		row.insertCell(5).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].dteBillDate\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + dteBillDate + "'>";
		row.insertCell(6).innerHTML = "<input name=\"UnsettleBillDtl["
				+ (rowCount)
				+ "].dblGrandTotal\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtMenuHeadCode."
				+ (rowCount) + "\" value='" + dblGrandTotal + "'>";

		row.insertCell(7).innerHTML = "<input type=\"checkbox\" size=\"30%\" id=\"chkSelect."
				+ (rowCount) + "\" value=\"Tick\" >";

	}

	function funRemoveTableRows(tableId) {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;
		while (rowCount > 0) {
			table.deleteRow(0);
			rowCount--;
		}
	}
</script>
<body>
	<!-- onload="funLoadSettleBillDtlData()" -->

	<div id="formHeading">
		<label>Multiple Bill Settle In Cash</label>
	</div>
	<s:form name="MultiBillSettle" method="POST"
		action="settlePOSMultiBill.html?saddr=${urlHits}">

		<table border="1" class="myTable" style="width: 80%; margin: auto;">
			<thead>
				<tr>

					<c:forEach items="${tblheader}" var="heder">
						<th style="width: 13%;">${heder}
					</c:forEach>
					<input type="checkbox" id="select" value="" />
					</th>
				</tr>


			</thead>
		</table>
		<div
			style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 300px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">

			<table id="tblBillSettle" class="transTablex col5-center"
				style="width: 100%;">

				<c:forEach items="${details}" var="SB" varStatus="status">
					<tr>
						<td style="width: 13%; height: 12px;"><input size="12%"
							class="Box" readonly="readonly" id="txtBillNo.[${status.index}]"
							name="listUnsettleBillDtl[${status.index}].strBillNo"
							value="${SB.strBillNo}" /></td>

						<td style="width: 13.5%; height: 12px;"><input size="12%"
							class="Box" readonly="readonly"
							name="listUnsettleBillDtl[${status.index}].strTableName"
							value="${SB.strTableName}" /></td>

						<td style="width: 13.2%; height: 12px;"><input size="12%"
							class="Box" readonly="readonly"
							name="listUnsettleBillDtl[${status.index}].strCustomerName"
							value="${SB.strCustomerName}" /></td>

						<td style="width: 13.2%; height: 12px;"><input size="12%"
							class="Box" readonly="readonly"
							name="listUnsettleBillDtl[${status.index}].strBuildingName"
							value="${SB.strBuildingName}" /></td>
						<td style="width: 13.2%; height: 12px;"><input size="12%"
							class="Box" readonly="readonly"
							name="listUnsettleBillDtl[${status.index}].strDPName"
							value="${SB.strDPName}" /></td>
						<td style="width: 13.2%; height: 12px;"><input size="12%"
							class="Box" readonly="readonly"
							name="listUnsettleBillDtl[${status.index}].dteBillDate"
							value="${SB.dteBillDate}" /></td>
						<td style="width: 13.2%; height: 12px; align: right"><input
							size="12%" class="Box" id="txtamt${status.index}"
							readonly="readonly"
							name="listUnsettleBillDtl[${status.index}].dblGrandTotal"
							value="${SB.dblGrandTotal}" style="align: right" /></td>
						<td style="width: 12.5%; height: 12px;"><input
							type="checkbox" id="cmbSettle.[${status.index}]" value="Tick"
							size="5%"
							name="listUnsettleBillDtl[${status.index}].strSelectedData"
							class="deletebutton" onclick="btnSubmit_Onclick()" /> <%--  											  <c:if test="${SB.name == 'FIELD7'}">checked="checked"</c:if>  --%>
						</td>
						<!-- 										 onClick="Javacsript:funDeleteTCRow(this)"  -->
					</tr>
				</c:forEach>
			</table>


		</div>

		<div>
			<br> <br>
			<table style="margin: auto">
				<tr>
					<td style="padding-right: 20px;"><label>No. Of Bills:</label>
					</td>
					<td style="padding-right: 20px;"><label id="noOfBills"></label>
					</td>

					<td style="padding-right: 20px;"><label>Total Amount:</label>
					</td>
					<td><label id="totAmount"></label></td>
				</tr>
			</table>
		</div>
		<br />
		<br />

		<p align="center">
			<input type="submit" value="Settle" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />
		</p>

	</s:form>
</body>
</html>