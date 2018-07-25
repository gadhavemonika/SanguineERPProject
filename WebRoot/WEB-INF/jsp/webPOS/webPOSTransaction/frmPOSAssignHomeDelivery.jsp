<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assign Home Delivery</title>

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

var arrSelectedBills=new Array();
var arrSelectedDelBoys=new Array();
	$(document).ready(function() {

		funCustomerArea($("#cmbZone").val());
		
// 		alert($("#cmbZone").val());
// 		alert($("#cmbArea").val());

		funFetchDeliveryBoyData();
		funFetchBillNoData();
		
		$("#cmbZone").change(function() {
			funFetchBillNoData();
		});
		$("#cmbArea").change(function() {
			funFetchBillNoData();
		});

		$("#cmbZone").click(function() {


			funCustomerArea($("#cmbZone").val());

		});
		
		
		/**
		* Success Message After Saving Record
		**/
		$(document).ready(function()
		{
			var message='';
			<%if (session.getAttribute("success") != null) {
				if (session.getAttribute("successMessage") != null) {%>
			message='<%=session.getAttribute("successMessage").toString()%>';
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

						var g = 0;
						var BillNo = "";
						var DeliveryBoyName = "";
						var arrTableNo = [];
						var arrTableName = [];
						var cntDelBoy = 0;
						var cntBill = 0;

					});
	var prevRow, prevCell;
	var previousKOT = "";
	var prevCellIndex = "";
	var prevKOTIndex = "";
	var prevKOTCellIndex = "";
	var selectedIndx = 0;
	var prevIndex = "";
	var i = 0;
	function funGetSelectedRowIndex(obj, tableId) {
		var tableName = document.getElementById(tableId);
		var index = obj.parentNode.parentNode.rowIndex;
		var cellIndex = obj.parentNode.cellIndex;
		var tblname = tableName.rows[index].cells[cellIndex].innerHTML;
		var btnClassName = tblname.split('class="');
		var btnBackground = btnClassName[1].split('value=');
		var btnType = btnBackground[0].substring(0,
				(btnBackground[0].length - 2));
		var data = btnBackground[1].split('onclick=');
		var code = data[0].substring(1, (data[0].length - 2));
		var selectedTableName = data[1].split('this,');

		if (tableId == "tblBillDtl") 
		{
			var billNo = code;
			
			if (arrSelectedBills.includes(billNo)) 
			{
				
				var a = arrSelectedBills.indexOf(billNo);
				arrSelectedBills.splice(a, 1);
				i = i - 1;
				selectedRowIndex = index;
				row = tableName.rows[selectedRowIndex];
				
// 				document.getElementById(billNo).style.backgroundColor = "#6FB9F6";
				tableName.rows[index].cells[cellIndex].innerHTML= "<input  name=\"listBillNoDtl["+selectedRowIndex+"].strBillNo\"  readonly=\"readonly\" class=\"transForm_button\" id=\"+billNo+\" value='"+billNo+"'  onclick=\"funGetSelectedRowIndex(this,'tblBillDtl')\"/>";
				$("#billNo").text(arrSelectedBills);
			} 
			else 
			{
				
				$("#billNo").append(billNo).append(',');
				arrSelectedBills[i++] = billNo;
				selectedRowIndex = index;
				row = tableName.rows[selectedRowIndex];
				
// 				document.getElementById(billNo).style.backgroundColor = 'gray';
				tableName.rows[index].cells[cellIndex].innerHTML= "<input  name=\"listBillNoDtl["+selectedRowIndex+"].strBillNo\"  readonly=\"readonly\" class=\"transFormDisable_button\" id=\"+billNo+\" value='"+billNo+"'  onclick=\"funGetSelectedRowIndex(this,'tblBillDtl')\"/>";
			}

		}
		var values = "";
		if (tableId == "tblDelBoyDtl") 
		{

			if (arrSelectedDelBoys.includes(code)) 
			{
				var a = arrSelectedDelBoys.indexOf(code);
				arrSelectedDelBoys.splice(a, 1);
				i = i - 1;
				selectedRowIndex = index;
				row = tableName.rows[selectedRowIndex];
				
// 				document.getElementById(code).style.backgroundColor = "#6FB9F6";

				
				tableName.rows[index].cells[cellIndex].innerHTML= "<input  name=\"listDelBoyDtl["+selectedRowIndex+"].strDPCode\"  readonly=\"readonly\" class=\"transForm_button\" id=\"+code+\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,'tblDelBoyDtl')\"/>";
				$("#boyname").text(arrSelectedDelBoys);

			} else 
			{
				$("#boyname").append(code).append(',');
				arrSelectedDelBoys[i++] = code;

				selectedRowIndex = index;
				row = tableName.rows[selectedRowIndex];
// 				document.getElementById(code).style.backgroundColor = 'gray';
				tableName.rows[index].cells[cellIndex].innerHTML= "<input  name=\"listDelBoyDtl["+selectedRowIndex+"].strDPCode\"  readonly=\"readonly\" class=\"transForm_SelectedBtn\" id=\"+code+\" value='"+code+"' onclick=\"funGetSelectedRowIndex(this,'tblDelBoyDtl')\"/>";
			}
		}
	}

	function getval(cel, tblId) {
		var tableNo = cel.innerHTML;

		var codeArr = tableNo.split('value=');
		var code = codeArr[1].split('type=');
		var menuCode = code[0].substring(1, (code[0].length - 2));

		cel.innerHTML = "<input type=\"button\"  class=\"transForm_SelectedBtn \" value='"+menuCode+"'>";

		if (tblId = "tblBillDtl") {
			BillNo = menuCode;

		}
		if (tblId = "tblBillAndDelBoy") {
			var delBoyNameArr = menuCode.split("<br/>");
			DeliveryBoyName = tblNameArr[0];

		}
	}

	function funCustomerArea(code) {
		$("#cmbZone").val(code);
		var searchurl = getContextPath()
				+ "/fillCustomerAreaCombo.html?zoneCode=" + code;
		$
				.ajax({
					type : "GET",
					url : searchurl,
					dataType : "json",
					success : function(response) {

						var $select = $('#cmbArea');
						$select.find('option').remove();
						$
								.each(
										response,
										function(i, obj) {
											//alert(obj);
											var div_data;
											if (obj == "All") {
												div_data = "<option value="+obj+" selected='selected' >"
														+ obj + "</option>";
											} else {
												div_data = "<option value="+obj+">"
														+ obj + "</option>";
											}

											$(div_data).appendTo('#cmbArea');
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

	function funFetchDeliveryBoyData() {
		funRemoveTableRows("tblDelBoyDtl");

		var zoneCode = $("#cmbZone").val();
		var areaCode = $("#cmbArea").val();
		var searchurl = getContextPath() + "/loadBillAndDelBoyData.html";
		$.ajax({
			type : "GET",
			data : {
				zoneCode : zoneCode,
				areaCode : areaCode,
			},
			url : searchurl,
			dataType : "json",
			async : false,
			success : function(response) {
				var cnt = 0;
				var item1, item2, item3, item4;

				var delBoystatus = "";

				$.each(response.delBoyDtl, function(j, obj) {

					delBoystatus = delBoystatus + "," + obj.statusDelBoyBillNo;

				});

				var dpCode = delBoystatus.split(",");

				$.each(response.DeliveryBoyDtl, function(i, item) {
					funFillDeliveyBoyTable(item.strDPName, item.strDPCode,
							dpCode);

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

	function funFetchBillNoData() {
		funRemoveTableRows("tblBillDtl");
		g = 0;
		var zoneCode = $("#cmbZone").val();
		var areaCode = $("#cmbArea").val();
		var cnt = 0;

		var searchurl = getContextPath() + "/loadBillAndDelBoyData.html";
		$.ajax({
			type : "GET",
			data : {
				zoneCode : zoneCode,
				areaCode : areaCode,
			},
			url : searchurl,
			dataType : "json",
			async : false,

			success : function(response) {

				cntBill = response.countBill;

				$.each(response.BillNoDtl, function(i, item) {

					funFillBillNOTable(item.strBillNo, item.zoneWithDelTime,
							cntBill);

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

	function funRemoveTableRows() {
		var table = document.getElementById("tblDelBoyDtl");
		var rowCount = table.rows.length;
		while (rowCount > 0) {
			table.deleteRow(0);
			rowCount--;
		}
	}
	function funRemoveTableRows() {
		var table = document.getElementById("tblBillDtl");
		var rowCount = table.rows.length;
		while (rowCount > 0) {
			table.deleteRow(0);
			rowCount--;
		}
	}

	var rowCount;
	var row;
	function funFillBillNOTable(strBillNo, zoneWithDelTime, cnt) {
		var table = document.getElementById("tblBillDtl");

		if (g == 0) {
			rowCount = table.rows.length;
			row = table.insertRow(rowCount);
		}

		{

			row.insertCell(g).innerHTML = "<input type=\"text\"  id='"
					+ strBillNo
					+ "' class=\"transForm_button \" value='"
					+ strBillNo
					+ "' onclick=\"funGetSelectedRowIndex(this,'tblBillDtl')\"/>";

			g++;
		}
		if (g == 4) {
			g = 0;
		}
	}
	var k = 0;

	function funFillDeliveyBoyTable(strDPName, strDPCode, delBoystatus) {
		var table = document.getElementById("tblDelBoyDtl");
		var cntIndex = 0;

		if (k == 0) {
			rowCount = table.rows.length;
			row = table.insertRow(rowCount);
		}

		if (delBoystatus.indexOf(strDPCode) > -1) {
			row.insertCell(k).innerHTML = "<input type=\"button\"  class=\"transForm_redButton \" value='"+strDPName+"'/>";
			k++;
		} else {
			row.insertCell(k).innerHTML = "<input type=\"text\"  id='"
					+ strDPName
					+ "' name='"
					+ strDPName
					+ "' class=\"transForm_button \" value='"
					+ strDPName
					+ "'onclick=\"funGetSelectedRowIndex(this,'tblDelBoyDtl')\"/>";
		
			k++;
		}
		cntIndex++;
		if (k == 4) {
			k = 0;
		}
	}

	function funCheckDeliveryBoyAndBillData() {

		var searchurl = getContextPath() + "/checkBillAndDelBoyData.html";
		$.ajax({
			type : "GET",

			url : searchurl,
			dataType : "json",
			async : false,
			success : function(response) {
				var cnt = 0;
				var item1, item2, item3, item4;

				$.each(response.DeliveryBoyDtl, function(i, item) {

					funFillDeliveyBoyTable(item.strDPName);

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
</script>


</head>

<body>
	<div id="formHeading">
		<label>Area Master</label>
	</div>
	<s:form name="AssignHomeDeliveryForm" method="POST"
		action="saveDelBoyBillDtl.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable" style="margin-left: auto; width: 87%;">

			<tr style="float: right; width: 46%">

				<td style="padding-right: 10px"><s:select id="cmbZone"
						name="cmbZone" path="strZone" items="${mapZoneCode}"
						cssClass="BoxW124px" /> <s:select id="cmbArea" name="cmbArea"
						path="strArea" items="${customerAreaList1}" cssClass="BoxW124px" /></td>

			</tr>
		</table>
		<div style="margin-top: 10px;">
			<div
				style="width: 40%; float: left; background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 300px; margin-left: 88px; overflow-x: hidden; overflow-y: scroll;">

				<table id="tblDelBoyDtl" class="transTablex" style="width: 100%;">

				</table>
			</div>

			<div
				style="width: 40%; background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 300px; margin: auto; overflow-x: hidden; overflow-y: scroll;">

				<table id="tblBillDtl" class="transTablex" style="width: 100%;"></table>
			</div>
		</div>
		<div style="width: 100%; margin-left: 95px">
			<table>
				<tr>
					<td><label for="DeliveryBoys" id="delBoys">Delivery
							Boy:</label> <label for="DeliveryBoys" id="boyname"></label></td>
				</tr>
				<tr>
					<td><label for="BillNos" id="billNos">Bill Nos:</label> <label
						for="BillNos" id="billNo"></label></td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<br>
		<br>
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" />

		</p>
	</s:form>


</body>
</html>
