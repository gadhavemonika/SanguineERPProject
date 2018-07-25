<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">

/**
 * Ready Function for Searching in Table
 */
$(document).ready(function() {
	var tablename = '';
	$('#txtLicenceCode').keyup(function() {
		tablename = '#tbllicence';
		searchTable($(this).val(), tablename);
	});
});

/**
 * Function for Searching in Table Passing value(inputvalue and Table Id) 
 */
function searchTable(inputVal, tablename) {
	var table = $(tablename);
	table.find('tr').each(function(index, row) {
		var allCells = $(row).find('td');
		if (allCells.length > 0) {
			var found = false;
			allCells.each(function(index, td) {
				var regExp = new RegExp(inputVal, 'i');
				if (regExp.test($(td).find('input').val())) {
					found = true;
					return false;
				}
			});
			if (found == true)
				$(row).show();
			else
				$(row).hide();
		}
	});
}

/**
*  Remove All Row From Grid 
**/
function funRemRows(tableName) {
	var table = document.getElementById(tableName);
	var rowCount = table.rows.length;
	while (rowCount > 0) {
		table.deleteRow(0);
		rowCount--;
	}
}
/**
 * Ready Function for Ajax Waiting
 */
$(document).ready(function() {
	$(document).ajaxStart(function() {
		$("#wait").css("display", "block");
	});
	$(document).ajaxComplete(function() {
		$("#wait").css("display", "none");
	});
	funRemRows("tbllicence");
	funSetAllLicenceAllPrpoerty();
});


/**
* Get and Set All Licence All Property
**/
function funSetAllLicenceAllPrpoerty() {
	var searchUrl = "";
	searchUrl = getContextPath() + "/loadAllLicenceForAllProperty.html";
	
	$.ajax({
		type : "GET",
		url : searchUrl,
		dataType : "json",
		success : function(response) {
				$.each(response, function(i, item) {
					funfillLicenceGrid(response[i].strLicenceCode,
							response[i].strLicenceNo);
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
*  Filling Licence Grid 
**/
function funfillLicenceGrid(strLicenceCode, strLicenceNo) {
	var table = document.getElementById("tbllicence");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML = "<input id=\"cbLicSel."
			+ (rowCount)
			+ "\" name=\"Licthemes\" type=\"checkbox\" class=\"LicCheckBoxClass\"  checked=\"checked\" value='"
			+ strLicenceCode + "' />";
	row.insertCell(1).innerHTML = "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strLicenceCode."
			+ (rowCount) + "\" value='" + strLicenceCode + "' >";
	row.insertCell(2).innerHTML = "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strLicenceNo."
			+ (rowCount) + "\" value='" + strLicenceNo + "' >";
}

/**
*  Select All Licence When User Click On Select All Check Box
**/
$(document).ready(function() {
	$("#chkLicenceAll").click(function() {
		$(".LicCheckBoxClass").prop('checked', $(this).prop('checked'));
	});
});



/**
*  Check Validation Before Submit The Form
**/
function formSubmit() {
	var strLicenceCode = "";
	$('input[name="Licthemes"]:checked').each(function() {
		if (strLicenceCode.length > 0) {
			strLicenceCode = strLicenceCode + "," + this.value;
		} else {
			strLicenceCode = this.value;
		}
	});
	
	 /**
	  *  Set All Selected Licence In hidden TextField 
	 **/
	$("#hidLicenceCodes").val(strLicenceCode);

	document.forms["frmExciseMonthEnd"].submit();

}

/**
 *Reset the form
 */
// function funResetFields() {
// 	location.reload(true);
// }

/**
 * Ready Function 
 * Success Message after Submit the Form
 */
$(function() {
	var message='';
	<%if (session.getAttribute("success") != null) 
	{
		 if(session.getAttribute("successMessage") != null)
		 {%>
		  	message='<%=session.getAttribute("successMessage").toString()%>';
		    <% session.removeAttribute("successMessage");
		 }
		 boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			if (test)
			{%>	
				alert(message);
			<%}
			 session.removeAttribute("success");	
	}%>
	
});

</script>
<body onload="funOnload();">
	<div id="formHeading">
		<label>Month End</label>
	</div>
		<s:form name="frmExciseMonthEnd" method="POST" action="saveExciseMonthEnd.html?saddr=${urlHits}" >
	   		<br />
	   		
				<br>
		<table class="masterTable">
			<tr>
				<td width="49%">Licence&nbsp;&nbsp;&nbsp; <input type="text"
					id="txtLicenceCode" style="width: 25%; background-position: 190px 2px;"
					class="searchTextBox" placeholder="Type to search"  >
				</td>
			</tr>
			<tr>
				<td style="padding: 0 !important;">
					<div
						style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;">
						<table id="" class="masterTable"
							style="width: 100%; border-collapse: separate;">
							<tbody>
								<tr bgcolor="#72BEFC">
									<td width="15%"><input type="checkbox" id="chkLicenceAll"
										checked="checked" />Select</td>
									<td width="25%">Licence Code</td>
									<td width="65%">Licence  No</td>
								</tr>
							</tbody>
						</table>
						<table id="tbllicence" class="masterTable"
							style="width: 100%; border-collapse: separate;">

							<tr bgcolor="#72BEFC">
								<td width="15%"></td>
								<td width="25%"></td>
								<td width="65%"></td>

							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<br>
			<br>
			<p align="center">
				 <input type="button" value="Submit" onclick="return formSubmit();" class="form_button" />
				 <input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>			     
			</p>
			<s:input type="hidden" id="hidLicenceCodes" path="strFromLocCode"></s:input>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>