<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javascript">
	var fDate, tDate,colspan;
	var inPeg;
	$(document).ready(function() {

		$("#txtFromDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtFromDate").datepicker('setDate', 'today');

		$("#txtToDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtToDate").datepicker('setDate', 'today');

		$("#btnSubmit").click(function(event) {
			var fromDate = $("#txtFromDate").val();
			var toDate = $("#txtToDate").val();
			inPeg=$('#txtInPeg').val();
			if (fromDate.trim() == '' && fromDate.trim().length == 0) {
				alert("Please Enter From Date");
				return false;
			}
			if (toDate.trim() == '' && toDate.trim().length == 0) {
				alert("Please Enter To Date");
				return false;
			}

			var strBrandCode = $("#txtBrandCode").val();

			if (strBrandCode.trim() == '' && strBrandCode.trim().length == 0) {
				alert("Please Select Brand");
				return false;
			}

			if (funDeleteTableAllRows()) {
 				if (CalculateDateDiff(fromDate, toDate)) {
					fDate = fromDate;
					tDate = toDate;
					funAdd(strBrandCode);
				}
			}
		});

	});

	function CalculateDateDiff(fromDate,toDate) {

		var frmDate= fromDate.split('-');
	    var fDate = new Date(frmDate[2],frmDate[1],frmDate[0]);
	    
	    var tDate= toDate.split('-');
	    var t1Date = new Date(tDate[2],tDate[1],tDate[0]);

		var dateDiff=t1Date-fDate;
			 if (dateDiff >= 0 ) 
			 {
	     	return true;
	     }else{
	    	 alert("Please Check From Date And To Date");
	    	 return false
	     }
	}

	function funDeleteTableAllRows() {
		$('#tblExciseBrandWiseInquiry tbody').empty();

		var table = document.getElementById("tblExciseBrandWiseInquiry");
		var rowCount1 = table.rows.length;
		if (rowCount1 == 0) {
			return true;
		} else {
			return false;
		}
	}

	function funHelp(transactionName) {
		fieldName = transactionName;
// 		window.showModalDialog("searchform.html?formname=" + transactionName
// 				+ "&searchText=", "",
// 				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")

		window.open("searchform.html?formname=" + transactionName
				+ "&searchText=", "",
				"dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")

	}

	function funSetData(code) {
		switch (fieldName) {

		case 'BrandCode':
			$('#txtBrandCode').val(code);
			break;

		}
	}

	function funAdd(strBrandCode) {
		var searchUrl = "";
		searchUrl = getContextPath() + "/loadExciseBrandWiseInquiryExport.html";

		$.ajax({
			type : "GET",
			data : "fromDate=" + fDate + "&toDate=" + tDate + "&strBrandCode="
					+ strBrandCode +"&inPeg="+inPeg,
			url : searchUrl,
			dataType : "json",

			success : function(response) {
				
				totalCol = 4;
				funAddHeders(totalCol);
				funAddFullRow(response);
				

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

	function funAddHeders(colspan) {
		var $row = $('<tr id="headerRow">' +

				'<th ><h4>Date</h4></th>' +'<th ><h4>OPening Stock</h4></th>' +'<th><h4>Purchase</h4></th>'
				+ '<th><h4>SALES</h4></th>' + '<th><h4>CLOSING STOCK</h4></th>'
				+ '</tr>');

		$('#tblExciseBrandWiseInquiry').prepend($row);
	}

	function funAddFullRow(data) {
		if (data != null) {
			var table = document.getElementById("tblExciseBrandWiseInquiry");
			var rowCount = table.rows.length;
			var row, rowData;
			
			for (var j = 0; j < data.length; j++) {
				row = table.insertRow(rowCount);
				rowData = data[j]
				
	for (var i = 0; i < rowData.length; i++) {
					if (i == 0) {
						row.insertCell(i).innerHTML= "<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].strOpeningStk\" value="+rowData[i]+">";
						
					} else if (i == 1) {
						row.insertCell(i).innerHTML= "<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].dteDate\" value="+rowData[i]+">";
						
					} else if (i == 2) {
						row.insertCell(i).innerHTML =  "<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].strSale\" value="+rowData[i]+">";
						
					} else if(i == 3) {
						row.insertCell(i).innerHTML ="<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].strPurchase\" value="+rowData[i]+">";
					}else{
						row.insertCell(i).innerHTML = "<input class=\"Box\"  name=\"objBrandWiseInquiry["+(rowCount)+"].strClosingStk\" value="+rowData[i]+">";
					}
				}
				rowCount++;
			}
		}
	}
</script>



<body>
	<div id="formHeading">
		<label>Brand Wise Inquiry</label>
	</div>

	<br />
	<br />

	<s:form name="frmExciseBrandWiseInquiry" method="POST"
		action="saveBrandWise.html?saddr=${urlHits}">
		<div>
			<table class="masterTable">
				<tr>
					<td><label>From Date</label></td>
					<td><s:input type="text" id="txtFromDate"
							class="calenderTextBox" path="dteFromDate" required="required" />
					</td>
				
					<td><label>To Date</label></td>
					<td><s:input type="text" id="txtToDate"
							class="calenderTextBox" path="dteToDate" required="required" /></td>
				</tr>
				<tr>
					<td><label>Brand Code</label></td>
					<td><s:input type="text" id="txtBrandCode" path="strBrandCode"
							cssClass="searchTextBox" ondblclick="funHelp('BrandCode')" /></td>
							<td><label></label></td>
							<td>
				<s:select id="txtInPeg" path="strInPeg" cssClass="BoxW124px">
					<s:option value="Peg">PegWise</s:option>
					<s:option value="ML">MLWise</s:option>
				</s:select>
				</td>
				</tr>
			</table>
		</div>
		<br />
		<br />
		<br />
		<br />
		<br />
		<div
			style="width: 100%; overflow-x: auto !important; overflow-y: auto !important;">
			<table id="tblExciseBrandWiseInquiry" class="transTablex"
				style="width: 100%; text-align: center !important;">
			</table>
		</div>
		<p align="center">
			<input type="button" value="Display" class="form_button"
				id="btnSubmit" /> <input type="submit" value="Export"
				class="form_button" /> <input type="reset" value="Reset"
				class="form_button" />
		</p>
	</s:form>

</body>
</html>