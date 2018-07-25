<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Non Available Items</title>


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

	$(document).ready(function() {

		funFillItemTable();
		funGetNonAvailableItems();
		$("#txtItemSearch").keyup(function()
		{
			searchTable($(this).val());
		});
	});
	
	function searchTable(inputVal)
	{
		var table = $('#tblItems');
		table.find('tr').each(function(index, row)
		{
			var allCells = $(row).find('td');
			if(allCells.length > 0)
			{
				var found = false;
				allCells.each(function(index, td)
				{
					var regExp = new RegExp("\\b(" + inputVal + ")\\b", 'i');
					
					
					var element=$(this).html();	
		        	
			       	var itemName=$(element).attr("value");
					
					 
					 if(regExp.test(itemName))//if(regExp.test($(td).text()))
					{
						found = true;
						return false;
					}  
				});
				if(found == true)
				{
					$(row).show();
				}
				else
				{
					 $(row).hide();
				}
			}
		});
	}		 
	
	function funFillItemTable() {

		var searchurl = getContextPath() + "/funFillItemTable.html";
		$.ajax({
			type : "GET",
			
			url : searchurl,
			dataType : "json",
			async : false,
			success : function(response) {
			
				var delBoystatus = "";
				$.each(response.itemList, function(j, item) {

					funFillItemTableDtl(item.strItemName);
							

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
	
	function funGetNonAvailableItems() {

		var searchurl = getContextPath() + "/funGetNonAvailableItems.html";
		$.ajax({
			type : "GET",
			
			url : searchurl,
			dataType : "json",
			async : false,
			success : function(response) {
			

				$.each(response.NonAvailableItemList, function(j, item) {

					funFillNonAvailableItems(item.strItemName);
							

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
	
	var i = 0;
	function funFillNonAvailableItems(strItemName) {
		var table = document.getElementById("tblNonAvailableItems");
		var cntIndex = 0;

		if (i == 0) {
			rowCount = table.rows.length;
			row = table.insertRow(rowCount);
		}

		
			row.insertCell(i).innerHTML = "<input type=\"text\"  id='"+ strItemName + "' name='" + strItemName + "' class=\"transForm_button \" value='" + strItemName + "' onclick=\"funDeleteSelectedItem(this,'tblNonAvailableItems')\"/>";
		
			i++;
		
		cntIndex++;
		if (i == 4) {
			i = 0;
		}
	}
	
	var k = 0;
	function funFillItemTableDtl(strItemName) {
		var table = document.getElementById("tblItems");
		var cntIndex = 0;

		if (k == 0) {
			rowCount = table.rows.length;
			row = table.insertRow(rowCount);
		}

		
			row.insertCell(k).innerHTML = "<input type=\"text\"  id='"
					+ strItemName
					+ "' name='"
					+ strItemName
					+ "' style=\"width: 490px;\" class=\"transForm_button \" value='"
					+ strItemName + "' onclick=\"funGetSelectedRowIndex(this,'tblItems')\"/>";
					
// 					
			k++;
		
		cntIndex++;
		if (k == 1) {
			k = 0;
		}
	}
	var selectedItemName="";
	var g=0;
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
		
		
		var table = document.getElementById("tblNonAvailableItems");
		var cntIndex = 0;

			if (g == 0) {
				rowCount = table.rows.length;
				row = table.insertRow(rowCount);
			}

			if(confirm( "Do You Want To Make It Not Available?"))
			{	
			funItemsMouseClicked(code);	
			row.insertCell(g).innerHTML = "<input type=\"text\"  id='"
				+ selectedItemName
				+ "' name='"
				+ selectedItemName
				+ "'  class=\"transForm_button \" value='"
				+ code + "' onclick=\"funDeleteSelectedItem(this,'tblNonAvailableItems')\"/>";
// 			alert("Do You Want To Make It Not Available");	
			
				g++;
			
			cntIndex++;
			}
			else
			{
				return;
			}	
			if (g == 4) {
				g = 0;
			}
	}
	function funItemsMouseClicked(code)
	{
		var searchurl = getContextPath() + "/funItemsMouseClicked.html";
		$.ajax({
			type : "POST",
			data : {
				code : code,
				
			},
			url : searchurl,
			dataType : "json",
			async : false,
			success : function(response) {
			
				
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
	
	function funDeleteSelectedItem(obj,tableId)
	{
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
		
// 		document.getElementById("tblNonAvailableItems").deleteCell(cellIndex);
		var firstRow = document.getElementById("tblNonAvailableItems").rows[index];
		if(confirm( "Do You Want To Make It Available?"))
		{
			funRemoveNonAvailableItem(code);
	    	firstRow.deleteCell(cellIndex);
		}
		else
		{
			return;
		}	
	}
	
	function funRemoveNonAvailableItem(code)
	{
		var searchurl = getContextPath() + "/funRemoveNonAvailableItem.html";
		$.ajax({
			type : "POST",
			data : {
				code : code,
				
			},
			url : searchurl,
			dataType : "json",
			async : false,
			success : function(response) {
			
				
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
		<label>Non Available Items</label>
	</div>
	<s:form name="NonAvailableItems" method="POST"
		action="">

		<br />
		<br />
		<s:input type="text"  id="txtItemSearch" path=""  cssStyle="width:300px; height:20px;float:right;margin-right: 229px;" cssClass="searchTextBox jQKeyboard form-control" />
		<br/>
		<br/>
		<div style="margin-top: 10px;">
			<div
				style="width: 40%; float: left; background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 624px; margin-left: 88px; overflow-x: hidden; overflow-y: scroll;">

				<table id="tblNonAvailableItems" class="transTablex" style="width: 100%;">

				</table>
			</div>

			<div
				style="width: 40%; background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 624px; margin: auto; overflow-x: hidden; overflow-y: scroll;margin-right: 36px;">

				<table id="tblItems" class="transTablex" style="width: 100%;"></table>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br>
		<p align="center">
			<input type="button" value="Close" tabindex="3" class="form_button" />
			
		</p>
	</s:form>


</body>
</html>
