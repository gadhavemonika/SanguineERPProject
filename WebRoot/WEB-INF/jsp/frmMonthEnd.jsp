<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Month End</title>
<script type="text/javascript">

	/**
	 * Ready Function for Searching in Table
	 */
	$(document).ready(function() {
		var tablename = '';
		$('#txtLocCode').keyup(function() {
			tablename = '#tblloc';
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
	 * Ready Function for Ajax Waiting
	 */
	$(document).ready(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		funRemRows("tblloc");
		funSetAllLocationAllPrpoerty();
	});

	/**
	* Get and Set All Location All Property
	**/
	function funSetAllLocationAllPrpoerty() {
		var searchUrl = "";
		searchUrl = getContextPath() + "/loadAllLocationForAllProperty.html";
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				if (response.strLocCode == 'Invalid Code') {
					alert("Invalid Location Code");
					$("#txtFromLocCode").val('');
					$("#lblFromLocName").text("");
					$("#txtFromLocCode").focus();
				} else {
					$.each(response, function(i, item) {
						funfillLocationGrid(response[i].strLocCode,
								response[i].strLocName);
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
	
	/**
	*  Filling Location Grid 
	**/
	function funfillLocationGrid(strLocCode, strLocationName) {
		var table = document.getElementById("tblloc");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		row.insertCell(0).innerHTML = "<input id=\"cbLocSel."
				+ (rowCount)
				+ "\" name=\"Locthemes\" type=\"checkbox\" class=\"LocCheckBoxClass\"  checked=\"checked\" value='"
				+ strLocCode + "' />";
		row.insertCell(1).innerHTML = "<input readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"strLocCode."
				+ (rowCount) + "\" value='" + strLocCode + "' >";
		row.insertCell(2).innerHTML = "<input readonly=\"readonly\" class=\"Box \" size=\"50%\" id=\"strLocName."
				+ (rowCount) + "\" value='" + strLocationName + "' >";
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
	*  Select All Location When User Click On Select All Check Box
	**/
	$(document).ready(function() {
		$("#chkLocALL").click(function() {
			$(".LocCheckBoxClass").prop('checked', $(this).prop('checked'));
		});
	});
	
	/**
	*  Check Validation Before Submit The Form
	**/
	function formSubmit() {
		
		 var status = confirm("DO YOU WANT TO MONTH END?");
		   if(status == true){
			   var strLocCode = "";
				$('input[name="Locthemes"]:checked').each(function() {
					if (strLocCode.length > 0) {
						strLocCode = strLocCode + "," + this.value;
					} else {
						strLocCode = this.value;
					}
				});
				$("#hidLocCodes").val(strLocCode);

				document.forms["frmMonthEnd"].submit();
		   	
		   }
		   else{
			  	return false;
		   }
		
		
		
		 /**
		  *  Set All Selected Location In hidden TextField 
		 **/
		

	}
	
	/**
	 *Reset the form
	 */
	function funResetFields() {
		location.reload(true);
	}
	
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
</head>
<body onload="funOnload();">
	<div id="formHeading">
		<label>Month End</label>
	</div>
		<s:form name="frmMonthEnd" method="POST" action="saveMonthEnd.html?saddr=${urlHits}" >
	   		<br />
	   		
				<br>
		<table class="masterTable">
			<tr>
				<td width="49%">Location&nbsp;&nbsp;&nbsp; <input type="text"
					id="txtLocCode" style="width: 25%; background-position: 190px 2px;"
					class="searchTextBox" placeholder="Type to search">
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
									<td width="15%"><input type="checkbox" id="chkLocALL"
										checked="checked" />Select</td>
									<td width="25%">Location Code</td>
									<td width="65%">Location Name</td>
								</tr>
							</tbody>
						</table>
						<table id="tblloc" class="masterTable"
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
			<s:input type="hidden" id="hidLocCodes" path="strFromLocCode"></s:input>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>