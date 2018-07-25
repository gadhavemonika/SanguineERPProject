<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PHYSICAL STOCK POSTING</title>

<script type="text/javascript">

var fieldName,listRow=0;
var intBrandSize=0,intPegSize=0,strOpStk="0.0";

$(document).ready(function() {

		$("#txtStkPostCode").focus();

		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
		$("#txtStkPostDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtStkPostDate").datepicker('setDate', 'today');

		var message = '';
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
				alert("Data Save successfully\n\n"+message);
			<%
			}}%>
			
		$('a#baseUrl').click(function() {
			if($("#txtStkPostCode").val().trim()=="")
			{
				alert("Please Select Stock Posting Code");
				return false;
			}
		 	window.open('attachDoc.html?transName=frmPhysicalStkPosting.jsp&formName=Physical Stock Posting&code='+$('#txtStkPostCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		});
					
		$('#txtBrandCode').blur(function () {
			var code=$('#txtBrandCode').val();
			if (code.trim().length > 0 && code !="?" && code !="/"){	
				   funSetBrandData(code);
			   }
			});
					
		$('#txtLocCode').blur(function () {
			var code=$('#txtLocCode').val();
			if (code.trim().length > 0 && code !="?" && code !="/"){						  
				   funSetExciseLocation(code);
			   }
			});
					
		$('#txtStkPostCode').blur(function () {
			 var code=$('#txtStkPostCode').val();	
				 if (code.trim().length > 0 && code !="?" && code !="/"){						  				  
				 funGetExcisePhyStkPostingData(code);
			   }
			});
					
		$("form").submit(function(){
			var table = document.getElementById("tblBrandTable");
			var rowCount = table.rows.length;
				if (!fun_isDate($("#txtStkPostDate").val())) 
				    {
						 alert('Invalid Date');
						 $("#txtQuantity").focus();
						 return false;  
					} 
				if($("#txtStkPostDate").val()==''){
						alert("Please Enter Or Select Date");
						return false;
					} else if($("#txtLocCode").val()==''){
						alert("Please Enter Location Or Search");
						$("#txtLocCode").focus();
						return false;
					} else if(rowCount==0){
						alert("Please Add Brand in Grid");
						return false;
					}   
						
			});
					
		$("[type='reset']").click(function(){
			location.reload();
		});
					
	});
	
	function funSetData(code) {
		
		switch (fieldName) {
		
		case 'excisestkpostcode':
			funGetExcisePhyStkPostingData(code);
			break;

		case 'LicenceCode':
			funSetExciseLicence(code);
			break;

		case 'BrandCode':
			funSetBrandCode(code);
			break;

		}
	}

	function funHelp(transactionName) {
		fieldName = transactionName;
	//	window.showModalDialog("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funGetExcisePhyStkPostingData(code) {
		
		$("#txtBrandCode").focus();
		funClearBrandRowData();
		searchUrl = getContextPath()+"/loadExcisePhysicalStk.html?strPSPCode="+code;
		$.ajax({
			type : "GET",
			url : searchUrl,
			success : function(response) {
				if (response.strPSCode == 'Invalid Code') {
					alert('Invalid Code');
					$("#txtStkPostCode").val('');
					$("#txtStkPostCode").focus();
				} else {
					
					$("#txtStkPostCode").val(response.strPSPCode);
					$("#txtStkPostDate").val(response.dtePostingDate);
					$("#txtLicencCode").val(response.strLicenceCode);
					$("#txtLicenceNo").text(response.strLicenceNo);
					
					funGetBrandData1(response.phyStocklist);
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
	
	function funSetExciseLicence(code) {
		
		$('#txtBrandCode').focus();
		var searchUrl = getContextPath()+ "/loadExciseLicenceMasterData.html?licenceCode=" + code;
		$.ajax({
			type : "GET",
			url : searchUrl,
			success : function(response) {
				if (response.strLicenceCode == 'Invalid Code') {
					alert("Please Select Licence Data Properly");
					$("#txtLicencCode").val('');
					$("#txtLicenceNo").text("");
				} else {
					$("#txtLicencCode").val(response.strLicenceCode);
					$("#txtLicenceNo").text(response.strLicenceNo);
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

	function funSetBrandCode(code) {
		$("#txtBtls").focus();
		var gurl = getContextPath()+ "/loadExciseBrandMasterData.html?brandCode=";
		$.ajax({
			type : "GET",
			url : gurl + code,
			dataType : "json",
			success : function(response) {
				if (response.strBrandCode == 'Invalid Code') {
					alert("Invalid Brand Code Please Select Again");
					$("#txtBrandCode").val('');
					$("#txtBrandCode").focus();
				} else {
					$("#txtBrandCode").val(response.strBrandCode);
					$("#txtBrandName").text(response.strBrandName);
					$("#txtBrandSize").text(response.strSizeName);
					$("#txtAvailabelStk").text("Available Stk In ML:- "+response.strAvailableStk);
					$("#txtPegSize").text("peg Size:- "+response.intPegSize);
					$("#txtBrandMRP").val(response.dblRate);
					intPegSize = response.intPegSize;
					intBrandSize = response.intSizeQty;
					strOpStk = response.strAvailableStk;
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

	
	function btnAdd_onclick(){
		if ($("#txtBrandCode").val().trim().length == 0 && $("#txtBrandCode").val().trim() == "") {
				alert("Please Enter Brand Code Or Search");
				$("#txtBrandCode").focus();
				return false;
		}

		var strBrandCode = $("#txtBrandCode").val();
		if (funDuplicateBrand(strBrandCode)) {
				funAddRow();
			}
	}

	
	function funDuplicateBrand(BrandCode) {
		var table = document.getElementById("tblBrandTable");
		var rowCount = table.rows.length;
		var flag = true;
		if (rowCount > 0) {
			$('#tblBrandTable tr').each(function() {
				if (BrandCode == $(this).find('input').val()){
					alert("Already added " + BrandCode);
					flag = false;
				}
			});

		}
		return flag;
	}

	
	function funAddRow() {
		var strBrandCode = $("#txtBrandCode").val();
		var strBrandName = $("#txtBrandName").text();
		var dblBrandMRP = $("#txtBrandMRP").val();
		
		var intPhyBtl = 0;
		var intPhyML = 0;
		var intPhyPeg = 0;
		
		if ($("#txtBtls").val().toString().trim() != undefined && $("#txtBtls").val().toString().trim().length > 0) {
			intPhyBtl = $("#txtBtls").val().toString().trim();
		}
		
		if ($("#txtML").val().toString().trim() != undefined && $("#txtML").val().toString().trim().length > 0) {
			intPhyML = $("#txtML").val().toString().trim();
		}

		if ($("#txtPegs").val().toString().trim() != undefined && $("#txtPegs").val().toString().trim().length > 0) {
			intPhyPeg = $("#txtPegs").val().toString().trim();
		}

	
		var arr = strOpStk.split('.');
		var intSysBtl = arr[0];
		var intSysML = arr[1];
		var intSysPeg = 0;

		var systemStockInML = funStkInML(intSysBtl, intSysPeg, intSysML);
		var physStockInML = funStkInML(intPhyBtl, intPhyPeg, intPhyML);
		
		var intVarianceInML = systemStockInML-physStockInML;
			
		funFillBrandData(strBrandCode, strBrandName, dblBrandMRP,intSysBtl, intSysML, intSysPeg, intPhyBtl,
						intPhyML,intPhyPeg,intVarianceInML,intBrandSize,intPegSize,strOpStk);

		funApplyNumberValidation();
		funResetBrandFields();
		$("#txtBrandCode").focus();
		return false;
	}

	function funStkInML(bottals, pegs, mls) {

		if (intPegSize <= 0) {
			intPegSize = intBrandSize;
		}

		var totMls = parseInt(intBrandSize * (bottals.toString().trim())) + parseInt(mls.toString().trim())
					+ (parseInt(pegs.toString().trim()) * intPegSize);
		return totMls;
	}


	function funCalculateVariation(object) {

		var intPhyBtl = 0;
		var intPhyML = 0;
		var intPhyPeg = 0;

		var $row = $(object).closest("tr");
		
		if ($row.find(".intPhyBtl").val().trim() != undefined && $row.find(".intPhyBtl").val().trim().length > 0) {
			intPhyBtl = parseInt($row.find(".intPhyBtl").val());
		}
		if ($row.find(".intPhyML").val().trim() != undefined && $row.find(".intPhyML").val().trim().length > 0) {
			intPhyML = parseInt($row.find(".intPhyML").val());
		}
		if ($row.find(".intPhyPeg").val().trim() != undefined && $row.find(".intPhyPeg").val().trim().length > 0) {
			intPhyPeg = parseInt($row.find(".intPhyPeg").val());
		}
		
		var intSysBtl = 0;
		var intSysML = 0;
		var intSysPeg = 0;
		
		if ($row.find(".intSysBtl").val().trim() != undefined && $row.find(".intSysBtl").val().trim().length > 0) {
			intSysBtl = parseInt($row.find(".intSysBtl").val());
		}
		if ($row.find(".intSysML").val().trim() != undefined && $row.find(".intSysML").val().trim().length > 0) {
			intSysML = parseInt($row.find(".intSysML").val());
		}
		if ($row.find(".intSysPeg").val().trim() != undefined && $row.find(".intSysPeg").val().trim().length > 0) {
			intSysPeg = parseInt($row.find(".intSysPeg").val());
		}
		
		// Global Values Initialisation
		
		if ($row.find(".intBrandSize").val().trim() != undefined && $row.find(".intBrandSize").val().trim().length > 0) {
			intBrandSize = parseInt($row.find(".intBrandSize").val());
		}
		if ($row.find(".intPegSize").val().trim() != undefined && $row.find(".intPegSize").val().trim().length > 0) {
			intPegSize = parseInt($row.find(".intPegSize").val());
		}

		var systemStockInML = funStkInML(intSysBtl, intSysPeg, intSysML);
		var physStockInML = funStkInML(intPhyBtl, intPhyPeg, intPhyML);
		
		var intVarianceInML = systemStockInML-physStockInML;

		$row.find(".intVarianceInML").val(intVarianceInML);
	}

	
	function funDeleteRow(obj) {
		var index = obj.parentNode.parentNode.rowIndex;
		var table = document.getElementById("tblBrandTable");
		table.deleteRow(index);
	}

	
	function funClearBrandRowData() {
		$("#tblBrandTable tr").remove();
	}

	function funResetBrandFields() {
		
		$("#txtBrandCode").val('');
		document.getElementById("txtBrandName").innerHTML = '';
		document.getElementById("txtBrandSize").innerHTML = '';
		document.getElementById("txtAvailabelStk").innerHTML = '';
		document.getElementById("txtPegSize").innerHTML = '';
		
		$("#txtBtls").val('0');
		$("#txtPegs").val('0');
		$("#txtML").val('0');
		$("#txtBrandMRP").val('0');
	}

	
	function funGetBrandData1(response) {
		var count = 0;
		$.each(response, function(i, item) {
			
			funFillBrandData(response[i].strBrandCode,
					response[i].strBrandName, response[i].dblBrandMRP,
					response[i].intSysBtl, response[i].intSysML,
					response[i].intSysPeg, response[i].intPhyBtl,
					response[i].intPhyML, response[i].intPhyPeg,
					response[i].intVarianceInML,response[i].intBrandSize,
					response[i].intPegSize,response[i].strOpStk);
				count = i;
			});
		listRow = count + 1;
	}

	function funOpenExportImport() {
		var transactionformName = "frmExcisePhyStkPosting";
	//	response = window.showModalDialog("frmExciseExcelExportImport.html?formname="+ transactionformName,"","dialogHeight:500px;dialogWidth:500px;");
		response = window.open("frmExciseExcelExportImport.html?formname="+ transactionformName,"","dialogHeight:500px;dialogWidth:500px;");

		if (null != response) {
			funClearBrandRowData();
			$.each(response, function(i, item) {
				funFillBrandData(response[i].strBrandCode,
						response[i].strBrandName, response[i].dblBrandMRP,
						response[i].intSysBtl, response[i].intSysML,
						response[i].intSysPeg, response[i].intPhyBtl,
						response[i].intPhyML, response[i].intPhyPeg,
						response[i].intVarianceInML,response[i].intBrandSize,
						response[i].intPegSize,response[i].strOpStk);
			});
		}
	}

	function funFillBrandData(strBrandCode, strBrandName, dblBrandMRP,intSysBtl, intSysML, intSysPeg, intPhyBtl, intPhyML, 
					intPhyPeg,intVarianceInML,intBrandSize,intPegSize,strOpStk) {
		
		var table = document.getElementById("tblBrandTable");
		var rowCount1 = table.rows.length;
		var row = table.insertRow(rowCount1);
		var rowCount = listRow;
		
		row.insertCell(0).innerHTML = "<input readonly=\"readonly\"  size=\"10%\" class=\"Box\" class=\"inputText-Auto\" name=\"phyStocklist["
				+ (rowCount)+ "].strBrandCode\" id=\"txtBrandCode."+ (rowCount) + "\" value=" + strBrandCode + ">";
				
		row.insertCell(1).innerHTML = "<input readonly=\"readonly\"  size=\"35%\" class=\"Box\" class=\"inputText-Auto\" name=\"phyStocklist["
				+ (rowCount)+ "].strBrandName\" id=\"lblBrandName."+ (rowCount) + "\" value='" + strBrandName + "'>";

		row.insertCell(2).innerHTML = "<input readonly=\"readonly\" size=\"10%\" class=\"Box dblBrandMRP\" class=\"inputText-Auto\" name=\"phyStocklist["
				+ (rowCount)+ "].dblBrandMRP\" id=\"dblBrandMRP."+ (rowCount)+ "\" value=" + dblBrandMRP + ">";
				
		row.insertCell(3).innerHTML = "<input readonly=\"readonly\" size=\"8%\" class=\"Box intBrandSize\" name=\"phyStocklist["
			+ (rowCount)+ "].intBrandSize\" id=\"intBrandSize."+ (rowCount) + "\"  value=" + intBrandSize + ">";
			
		row.insertCell(4).innerHTML = "<input readonly=\"readonly\" size=\"8%\" class=\"Box intPegSize\" name=\"phyStocklist["
						+ (rowCount)+ "].intPegSize\" id=\"intPegSize."+ (rowCount) + "\"  value=" + intPegSize + ">";
						
		row.insertCell(5).innerHTML = "<input readonly=\"readonly\" size=\"8%\" class=\"Box intSysBtl\" class=\"inputText-Auto\" name=\"phyStocklist["
				+ (rowCount)+ "].intSysBtl\" id=\"intSysBtl."+ (rowCount)+ "\"  value=" + intSysBtl + " >";
				
		row.insertCell(6).innerHTML = "<input readonly=\"readonly\" size=\"8%\" class=\"Box intSysML\" class=\"inputText-Auto\" name=\"phyStocklist["
				+ (rowCount)+ "].intSysML\" id=\"intSysML."+ (rowCount)+ "\" value=" + intSysML + ">";
				
		row.insertCell(7).innerHTML = "<input readonly=\"readonly\" size=\"8%\" class=\"Box intSysPeg\" name=\"phyStocklist["
				+ (rowCount)+ "].intSysPeg\" id=\"intSysPeg."+ (rowCount)+ "\" value=" + intSysPeg + " >";
				
		row.insertCell(8).innerHTML = "<input size=\"8%\" class=\"Box integer numberField inputText-Auto intPhyBtl\" name=\"phyStocklist["
				+ (rowCount)+ "].intPhyBtl\" id=\"intPhyBtl."+ (rowCount)+ "\" value=" + intPhyBtl +" onchange=\"funCalculateVariation(this)\" >";
				
		row.insertCell(9).innerHTML = "<input size=\"8%\" class=\"Box integer numberField inputText-Auto intPhyML\" name=\"phyStocklist["
				+ (rowCount)+ "].intPhyML\" id=\"intPhyML."+ (rowCount)+ "\" value=" + intPhyML + " onchange=\"funCalculateVariation(this)\" >";
				
		row.insertCell(10).innerHTML = "<input size=\"8%\" class=\"Box integer numberField inputText-Auto intPhyPeg\" name=\"phyStocklist["
				+ (rowCount)+ "].intPhyPeg\" id=\"intPhyPeg."+ (rowCount)+ "\"  value=" + intPhyPeg + " onchange=\"funCalculateVariation(this)\" >";
				
		row.insertCell(11).innerHTML = "<input readonly=\"readonly\" size=\"8%\" class=\"Box intVarianceInML\" name=\"phyStocklist["
				+ (rowCount)+ "].intVarianceInML\" id=\"intVarianceInML."+ (rowCount) + "\"  value=" + intVarianceInML + ">";
				
		row.insertCell(12).innerHTML = "<input type=\"button\" size=\"4%\" value = \"Delete\" class=\"deletebutton\" onClick=\"Javacsript:funDeleteRow(this)\">";
		
// 		row.insertCell(13).innerHTML = "<input type=\"hidden\" readonly=\"readonly\" class=\"Box strOpStk\" name=\"phyStocklist["
// 					+ (rowCount)+ "].strOpStk\" id=\"strOpStk."+ (rowCount) + "\"  value=" + strOpStk + ">";
				
		listRow++;
		funApplyNumberValidation();
		funResetBrandFields();
		return false;
	}

	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit,negative: false});
	}
	
	function funResetFields() {
		location.reload(true);
	}
	
</script>
	
</head>

	<body>
		<div id="formHeading">
			<label>Physical Stock Posting</label>
		</div>
			<s:form name="stkPosting" method="POST" action="saveExcisePhyStkPosting.html?saddr=${urlHits}">	
		<br/>
		<table class="transTable">
	    	<tr>
			    <th align="right">
			    	<a onclick="funOpenExportImport()" href="javascript:void(0);">Export/Import</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    </th>
			</tr>
		</table>
		
		<table class="transTable">
			<tr>
		        <td>
		        	<label id="lblStkPostCode" >Stock Posting Code</label>
	        	</td>
		        <td>
		        	<s:input id="txtStkPostCode" path="strPSPCode"  ondblclick="funHelp('excisestkpostcode')" cssClass="searchTextBox"/>
	        	</td>				        
		        <td>
		        	<label id="lblStkPostDate">Stock Posting Date</label>
	        	</td>
		        <td>
		        	<s:input id="txtStkPostDate" type="text" path="dtePostingDate" required="required" cssClass="calenderTextBox"/>
	        	</td>
		        <td></td>
		    </tr>
		     <tr>
			    <td>
			    	<label id="lblLicence" >Licence</label>
		    	</td>
		        <td>
		        	<s:input id="txtLicencCode" required="required" path="strLicenceCode" ondblclick="funHelp('LicenceCode')" cssClass="searchTextBox"/></td>
			    <td colspan="3" >
			    	<s:label id="txtLicenceNo" path="strLicenceNo" readonly="true"></s:label>
		    	</td>
			</tr>
		</table>
		<br/>	
		
	  <table  class="transTableMiddle">
		<tr>
			<td><label>Brand Code</label></td>
			<td>
				<input id="txtBrandCode" ondblclick="funHelp('BrandCode');" class="searchTextBox" />
			</td>
			<td colspan="1"><label id="txtBrandName"></label></td>
			<td><label id="txtBrandSize"></label></td>	
			<td><label id="txtAvailabelStk"></label></td>	
			<td><label id="txtPegSize"></label></td>		
		</tr>
		
		<tr>
			<td><label>Bottles</label></td>
			<td>
				<input id="txtBtls" type="text" class=" BoxW124px numeric positive-integer " />
			</td>
				
			<td><label>Peg / Glass</label></td>
			<td><input id="txtPegs" type="text" class=" BoxW124px numeric positive-integer " />
			</td>
	
			<td><label>ML</label></td>
			<td><input id="txtML" type="text" class=" BoxW124px numeric positive-integer " />
			</td>
		</tr>
		<tr>
			<td><input type="hidden" id="txtBrandMRP" /></td>
			<td></td>	
			<td colspan="2">
				<input id="btnAdd" type="button" value="Add" class="smallButton" onclick="return btnAdd_onclick();" />
			</td>
			<td></td>
			<td></td>	
		</tr>
	  </table>
	  <br>
	  
	<div class="dynamicTableContainer" style="height: 300px;">			 
		<table  style="height:28px;border:#0F0;width:100%;font-size:11px; font-weight: bold;">		
			<tr bgcolor="#72BEFC" >
			<td width="8%">Brand Code</td><!--  COl1   -->
			<td width="22%">Brand Name</td><!--  COl2   -->
			<td width="8%">MRP</td><!--  COl3   -->
			<td  width="8%">Brand Size</td><!--  COl12   -->
			<td  width="8%">Peg Size</td><!--  COl13   -->
			<td width="6%">Sys Stk Btl</td><!--  COl4   -->
			<td width="6%">Sys Stk ML</td><!--  COl5   -->
			<td width="6%">Sys Stk Peg</td><!--  COl6   -->
			<td width="6%">Phy Stk Btl</td><!--  COl7   -->
			<td width="6%">Phy Stk ML</td><!--  COl8   -->
			<td width="6%">Phy Stk Peg</td><!--  COl9   -->
			<td width="8%">Variance In ML</td><!--  COl10   -->
			<td width="5%">Delete</td><!--  COl11   -->
			</tr>
		</table>
			
	<div style="background-color:  	#a4d7ff;border: 1px solid #ccc;display: block;height: 250px;
	   				 margin: auto;overflow-x: hidden;overflow-y: scroll;width: 100%;">
		    
		 <table id="tblBrandTable"  style="width:100%;border: #0F0;table-layout:fixed;overflow:scroll" 
		    	class="transTablex col2-left col3-center col4-center col5-center col7-center col8-center col9-center  col10-center">
			<tbody>    
				<col style="width:8%"><!--  COl1   -->
				<col style="width:23%"><!--  COl2   -->
				<col style="width:9%"><!--  COl3   -->
				<col style="width:9%"><!--  COl12   -->
				<col style="width:9%"><!--  COl13   -->
				<col style="width:7%"><!--  COl4   -->
				<col style="width:6%"><!--  COl5   -->
				<col style="width:6%"><!--  COl6   -->
				<col style="width:6%"><!--  COl7   -->
				<col style="width:6%"><!--  COl8   -->
				<col style="width:6%"><!--  COl9   -->
				<col style="width:8%"><!--  COl10   -->
				<col style="width:5%"><!--  COl11   -->
		    </tbody>
		</table>
    </div>  
</div>
			  
	<br />
	<br />
		<p align="center">
			<input id="btnStkPost" type="submit" value="Submit" class="form_button"/>
			<input id="btnstkexport" type="submit" value="Export" class="form_button" 
					onclick="stkPosting.action='exportExcisePhyStkPosting.html?saddr=${urlHits}';"/>
		 	<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>	
			
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
			<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
		</div>
	</s:form>
	
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>