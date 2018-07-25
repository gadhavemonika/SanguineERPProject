<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName,intBrandSize=0,intPegSize=0,dblOpStk=0.0,dblClosingStk="0.0";
	var txtOldBtls=0,txtOldMls=0,txtOldPegs=0;
	var listRow=0;

	$(function() 
	{
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});

		$("#txtBillDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		
		$("#txtBillDate").datepicker('setDate', 'today');
		
		$('#txtBrandCode').blur(function () 
		{
		 	var code=$('#txtBrandCode').val();
		 	if (code.trim().length > 0 && code !="?" && code !="/"){							   
		 		funSetBrandCode(code);
	   			}
		});
		
		$('#txtLicenceCode').blur(function () 
				{
				 	var code=$('#txtLicenceCode').val();
				 	if (code.trim().length > 0 && code !="?" && code !="/"){							   
				 		funSetLicenceCode(code);
		   			}
				});
		
		$('#txtSalesId').blur(function () 
			{
			 	var code=$('#txtSalesId').val();
			 	if (code.trim().length > 0 && code !="?" && code !="/"){							   
			 		funSetSalesData(code);
	   			}
		});
		
		$("form").submit(function(){
			
			var table = document.getElementById("tblData");
		    var rowCount = table.rows.length;
		    
			if($("#strBrandCode").val()=='')
			{
				alert("Please Enter Brand Code");
				return false;
			} else if(rowCount==0)
			{
				alert("Please Add Brand in Grid");
				return false;
			}   
					
		}); 
		
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
				alert(message);
			<%
			}}%>
			
			$('#reset').click(function(){
				location.reload();
			})
	});
	

	function funHelp(transactionName)
	{
		fieldName = transactionName;
		var licenceCode=$("#txtLicenceCode").val();
		if(transactionName=="SalesId")
		{
			if($("#txtLicenceCode").val().trim().length == 0){
				
				alert("Please Select Licence ");
			}else{
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
				window.open("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		}
	   }else{
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}
	}
	

	function funSetData(code){

		switch(fieldName){
		
			case 'SalesId' : 
				funSetSalesData(code);
				break;
			case 'LicenceCode' : 
				funSetLicenceCode(code);
				break;
			case 'TransactionalBrands' : 
				funSetBrandCode(code);
				break;
		}
	}
	
	function funSetSalesData(code){
		funClearBrandRowData();
		var licenceCode= $("#txtLicenceCode").val();
		$("#txtBrandCode").focus();
		var gurl=getContextPath()+"/loadExciseSalesMasterData.html?saleId="+code+"&licenceCode="+licenceCode;
		$.ajax({
	        type: "GET",				        
	        url: gurl,
			dataType : "json",
			success : function(response){ 
				if(response.intId==0)
	        	{
	        		alert("Invalid Sales Details Please Select Again");
	        		$("#txtSalesId").val(0);
	        		$("#txtSalesId").focus();
	        	}
	        	else
	        	{
	        		$("#txtSalesId").val(response.intId);
	        		$("#txtBillDate").val(response.dteBillDate);
	        		$("#txtLicenceCode").val(response.strLicenceCode);
	        		$("#txtLicenceNo").text(response.strLicenceNo);
	        		$("#txtBillDate").text(response.strBrandName);
	        		$("#strSourceEntry").val(response.strSourceEntry);
	        		
	        		funSetSaleDtlData(response.objSalesDtlList)
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
	
	function funSetSaleDtlData(response){
		var count=0;
		$.each(response, function(i,item){
				funFillSaleData(response[i]);
			       count=i;	    	   			       	    	                                           
   	    	 });
		listRow=count+1;
	}
	
	function funFillSaleData(data){
	
	 var table = document.getElementById("tblData");
	 var rowCount = table.rows.length;
	 var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML= "<input type=\"text\" size=\"30%\" readonly=\"readonly\" class=\"Box id\" name=\"objSalesDtlList["+(rowCount)+"].strBrandCode\" id=\"strBrandCode."+(rowCount)+"\" value="+data.strBrandCode+" />";		    
	    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"90%\" readonly=\"readonly\" id=\"strBrandName."+(rowCount)+"\" name=\"objSalesDtlList["+(rowCount)+"].strBrandName\" value='"+data.strBrandName+"' />";
	    row.insertCell(2).innerHTML= "<input type=\"text\" size=\"12%\" style=\"text-align: right;\" class=\"integer numberField inputText-Auto txtBtls \" name=\"objSalesDtlList["+(rowCount)+"].intBtl\" id=\"intBtl."+(rowCount)+"\" value="+data.intBtl+" onblur=\"Javacsript:funUpdateStk(this)\" onFocus='Javacsript:funsetOldValues(this)'  />";
	    row.insertCell(3).innerHTML= "<input type=\"text\" size=\"12%\" style=\"text-align: right;\" class=\"integer numberField inputText-Auto txtPegs \" name=\"objSalesDtlList["+(rowCount)+"].intPeg\" id=\"intPeg."+(rowCount)+"\" value="+data.intPeg+" onblur=\"Javacsript:funUpdateStk(this)\" onFocus='Javacsript:funsetOldValues(this)'  />";
	    row.insertCell(4).innerHTML= "<input type=\"text\" size=\"12%\" style=\"text-align: right;\" class=\"integer numberField inputText-Auto txtML \" name=\"objSalesDtlList["+(rowCount)+"].intML\" id=\"intML."+(rowCount)+"\" value="+data.intML+" onblur=\"Javacsript:funUpdateStk(this)\" onFocus='Javacsript:funsetOldValues(this)' />";
	    row.insertCell(5).innerHTML= "<input class=\"Box txtClosing \" size=\"12%\" readonly=\"readonly\" style=\"text-align: left;\" type=\"text\" id=\"dblClosing"+(rowCount)+"\" value=\""+dblClosingStk+"\" />";
	    row.insertCell(6).innerHTML= "<input class=\"Box\" size=\"10%\" readonly=\"readonly\" type=\"text\" style=\"text-align: center;\" required = \"required\" name=\"objSalesDtlList["+(rowCount)+"].strBillGenFlag\" id=\"strBillGenFlag."+(rowCount)+"\" value="+data.strBillGenFlag+" />";
	    row.insertCell(7).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
	   
		row.insertCell(8).innerHTML= '<input type=\"hidden\"  class=\"strBrandOPStk\"  value=\"'+data.strOpStk+'\" >';
	    row.insertCell(9).innerHTML= '<input type=\"hidden\"  class=\"strBrandSize\"  value=\"'+data.intBrandSize+'\" >';
	    row.insertCell(10).innerHTML= '<input type=\"hidden\" class=\"strBrandPegSize\" value=\"'+data.intPegSize+'\" >';

	    funResetBrandFields();
	    funApplyNumberValidation();
		
	}

	function funSetLicenceCode(code){
		$("#txtBillDate").focus();
		var gurl=getContextPath()+"/loadExciseLicenceMasterData.html?licenceCode=";
		$.ajax({
	        type: "GET",				        
	        url: gurl+code,
			dataType : "json",
			success : function(response){ 
				if(response.strLicenceNo=='Invalid Code')
	        	{
	        		alert("Invalid Licence Code Please Select Again");
	        		$("#txtLicenceCode").val('');
	        		$("#txtLicenceName").focus();
	        	}
	        	else
	        	{
	        		$("#txtLicenceCode").val(response.strLicenceCode);
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

	function funSetBrandCode(code){
		$("#txtPegs").focus();
		var licenceCode=$("#txtLicenceCode").val();
		var gurl=getContextPath()+"/loadBrandData.html?brandCode="+code+"&licencecode="+licenceCode;
		$.ajax({
	        type: "GET",				        
	        url: gurl,
			dataType : "json",
			success : function(response){ 
				if(response.strBrandCode=='Invalid Code')
	        	{
	        		alert("Invalid Brand Code Please Select Again");
	        		$("#txtBrandCode").val('');
	        		$("#txtBrandCode").focus();
	        	}
	        	else
	        	{
	        		$("#txtBrandCode").val(response.strBrandCode);
	        		$("#txtBrandName").text(response.strBrandName);
	        		$("#txtBrandSize").text(response.strSizeName);
	        		$("#txtPegSize").html("peg Size :&nbsp;&nbsp;&nbsp;&nbsp;"+response.intPegSize);
	        		intPegSize=response.intPegSize;
	        		intBrandSize=response.intSizeQty;
	        		dblOpStk=response.strAvailableStk;
	        		
	        		var arr = dblOpStk.toString().split('.');
	       			var opBts=0;
	       			var opMls=0
	       			if(arr.length>1){
	       				opBts=arr[0];
	       				opMls=arr[1];
	       			}else{
	       				opBts=arr[0];
	       			}
	       			
	       			opMls=parseInt(opMls)/parseInt(intPegSize);
	       			if(parseFloat(opBts+"."+opMls) >0){
	       				$("#txtAvailabelStk").html("<span style='color:#00802b'><b>Available Stock :&nbsp;&nbsp;&nbsp;&nbsp;"+opBts+"."+opMls+"</b></span>");
	       			}else{
	       				$("#txtAvailabelStk").html("<span style='color:#ff3300'><b>Available Stock :&nbsp;&nbsp;&nbsp;&nbsp;"+opBts+"."+opMls+"</b></span>");
	       			}	       			
	       			
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
	

	function btnAdd_onclick() {
		if ($("#txtBrandCode").val().trim().length == 0) {
			alert("Please Enter Brand Code Or Search");
			$('#txtBrandCode').focus();
			return false;
		} else {
			var strBrandCode = $("#txtBrandCode").val();

			var txtBtls = $("#txtBtls").val();
			var txtPegs = $("#txtPegs").val();
			var txtML = $("#txtML").val();

			if (funDuplicateBrand(strBrandCode)) {
				if (parseInt(txtBtls) > 0 || parseInt(txtPegs) > 0
						|| parseInt(txtML) > 0) {
					funAddRow();
					$("#txtBrandCode").focus();
				} else {
					alert("Please Enter Brand Quantity");
					$("#txtBtls").focus();
				}
			}
		}

	}

	function funDuplicateBrand(strBrandCode) {
		var table = document.getElementById("tblData");
		var rowCount = table.rows.length;
		var flag = true;
		if (rowCount > 0) {
			$('#tblData tr').each(function() {
				if (strBrandCode == $(this).find('input').val())// `this` is TR DOM element
				{
					alert("Already added " + strBrandCode);
					flag = false;
				}
			});

		}
		return flag;

	}

	function funAddRow() {
		var brandCode = $("#txtBrandCode").val();
		var brandName = $("#txtBrandName").text();
		var bottals = $("#txtBtls").val().toString().trim();
		var pegs = $("#txtPegs").val().toString().trim();
		var mls = $("#txtML").val().toString().trim();

		if (bottals.trim() == '' && bottals.trim().length == 0) {
			bottals = 0;
		}

		if (pegs.trim() == '' && pegs.trim().length == 0) {
			pegs = 0;
		}

		if (mls.trim() == '' && mls.trim().length == 0) {
			mls = 0;
		}

		if (dblOpStk > 0) {
			var dblClosingStk = funIsStkValid(bottals, mls, pegs);
			var tmpdblClosingStk = new Number(dblClosingStk);
			if (tmpdblClosingStk < 0 || !($.isNumeric(tmpdblClosingStk))) {
				funResetBrandFields();
				alert("Sale Amount Not Valid");
			} else {
				var table = document.getElementById("tblData");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				rowCount = listRow;
// 				
				
				row.insertCell(0).innerHTML = "<input type=\"text\" size=\"15%\" readonly=\"readonly\" class=\"Box id\" name=\"objSalesDtlList["
						+ (rowCount) + "].strBrandCode\" id=\"strBrandCode." + (rowCount) + "\" value=" + brandCode + " />";
				row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"60%\" name=\"objSalesDtlList[" + (rowCount)+"].strBrandName\" id=\"strBrandName."
							+ (rowCount) + "\" value='"+brandName+"' />";		
				row.insertCell(2).innerHTML = "<input type=\"text\" size=\"8%\" style=\"text-align: right;\" class=\"integer numberField inputText-Auto txtBtls \" name=\"objSalesDtlList["
						+ (rowCount) + "].intBtl\" id=\"intBtl." + (rowCount) + "\" value=" + bottals + " onblur=\"Javacsript:funUpdateStk(this)\"  onFocus='Javacsript:funsetOldValues(this)' />";
			
				row.insertCell(3).innerHTML = "<input type=\"text\" size=\"8%\" style=\"text-align: right;\" class=\"integer numberField inputText-Auto txtPegs \" name=\"objSalesDtlList["
						+ (rowCount) + "].intPeg\" id=\"intPeg." + (rowCount) + "\" value=" + pegs + " onblur=\"Javacsript:funUpdateStk(this)\" onFocus='Javacsript:funsetOldValues(this)' />";
						
				row.insertCell(4).innerHTML = "<input type=\"text\" size=\"8%\" style=\"text-align: right;\" class=\"integer numberField inputText-Auto txtML \" name=\"objSalesDtlList["
						+ (rowCount) + "].intML\" id=\"intML." + (rowCount) + "\" value=" + mls + " onblur=\"Javacsript:funUpdateStk(this)\" onFocus='Javacsript:funsetOldValues(this)' />";
				
				row.insertCell(5).innerHTML = "<input class=\"Box txtClosing \" size=\"8%\" style=\"text-align: center;\" type=\"text\"  id=\"dblClosing"
						+ (rowCount) + "\" readonly=\"readonly\" value=\"" + dblClosingStk + "\" />";
				
				row.insertCell(6).innerHTML = "<input class=\"Box\" size=\"8%\" style=\"text-align: center;\"  type=\"text\" required = \"required\" readonly=\"readonly\" name=\"objSalesDtlList["
						+ (rowCount) + "].strBillGenFlag\" id=\"strBillGenFlag." + (rowCount) + "\" value=\"N\" />";
			
				row.insertCell(7).innerHTML = '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';

				row.insertCell(8).innerHTML = '<input type=\"hidden\"  class=\"strBrandOPStk\"  value=\"'+dblOpStk+'\" >';
				row.insertCell(9).innerHTML = '<input type=\"hidden\"  class=\"strBrandSize\"  value=\"'+intBrandSize+'\" >';
				row.insertCell(10).innerHTML = '<input type=\"hidden\" class=\"strBrandPegSize\" value=\"'+intPegSize+'\" >';

				listRow++;
				funResetBrandFields();
				funApplyNumberValidation();
				return false;
			}
		} else {
			funResetBrandFields();
			alert("Available Stock is Low Than Sale Qty");
		}

	}
	
	//Logic For Save old Values
	function funsetOldValues(obj){
		
		var $row = $(obj).closest("tr");
		if ($row.find(".txtBtls ").val() != '') {
			txtOldBtls = $row.find(".txtBtls ").val();
		}
		if ($row.find(".txtPegs").val() != '') {
			txtOldPegs = parseInt($row.find(".txtPegs").val());
		}
		if ($row.find(".txtML").val() != '') {
			txtOldMls = parseInt($row.find(".txtML").val());
		}
		
	}

	function funUpdateStk(obj) {

		funResetBrandFields();
		var bottals = '0';
		var mls = '0';
		var pegs = '0';

		var opStk = '0.0';
		var brandSize = '0';
		var pegSize = '0';
		
		var $row = $(obj).closest("tr");
		
		//Logic For Calculate Stock is Available
		
		if ($row.find(".strBrandOPStk").val() != '') {
			opStk = $row.find(".strBrandOPStk").val();
		}

		if ($row.find(".strBrandSize").val() != '') {
			brandSize = parseInt($row.find(".strBrandSize").val());
		}

		if ($row.find(".strBrandPegSize").val() != '') {
			pegSize = parseInt($row.find(".strBrandPegSize").val());
		}

		//Logic For Save New values
		
		if ($row.find(".txtBtls").val() != '') {
			bottals = parseInt($row.find(".txtBtls").val());
		}

		if ($row.find(".txtML").val() != '') {
			mls = parseInt($row.find(".txtML").val());
		}

		if ($row.find(".txtPegs").val() != '') {
			pegs = parseInt($row.find(".txtPegs").val());
		}

		if (opStk >= 0) {
			var dblClosingStk = "0.0";

			if (pegSize <= 0) {
				pegSize = brandSize;
			}

			var arr = opStk.split('.');
			var opBts = arr[0];
			var opMls = arr[1];
			
			
			//Adding old Stock In Current Stock
			
			var opPegs = txtOldPegs;			
			opBts = parseInt(opBts.toString().trim()) + parseInt(txtOldBtls)
			opMls = parseInt(opMls.toString().trim())
			var totOpMls = parseInt(brandSize * opBts) +  opMls + (pegSize * opPegs);
			
// 			var totOpMls = parseInt(brandSize * (opBts.toString().trim()))
// 					+ parseInt(opMls.toString().trim());

			
			var totSaleMls = parseInt(brandSize * (bottals.toString().trim()))
					+ parseInt(mls.toString().trim())
					+ parseInt(pegSize * pegs);
			var totClosingMls = parseInt(totOpMls - totSaleMls);

			if (totClosingMls >= brandSize) {
				var totCls = totClosingMls / brandSize;
				var arr1 = totCls.toString().split('.');
				var opBts1 = 0;
				var opMls1 = 0
				if (arr1.length > 1) {
					opBts1 = arr1[0];
					opMls1 = arr1[1];
				} else {
					opBts1 = arr1[0];
				}
				var tempMls = parseFloat(0 + "." + opMls1) * brandSize;
				tempMls = parseInt(tempMls);
				dblClosingStk = opBts1 + "." + tempMls;

			} else {
				dblClosingStk = 0 + "." + totClosingMls;
			}

			var tmpdblClosingStk = new Number(dblClosingStk);
			if (tmpdblClosingStk < 0 || !($.isNumeric(tmpdblClosingStk))) {
				
					$row.find(".txtBtls ").val(txtOldBtls);
					$row.find(".txtPegs").val(txtOldPegs);
					$row.find(".txtML").val(txtOldMls);
// 					$row.find(".txtClosing").val(opStk);

					this.focus();
				
					alert("Sale Amount Not Valid");
					
			} else {

				$row.find(".txtClosing").val(dblClosingStk);
				$row.find(".strBrandOPStk").val(dblClosingStk);
				funApplyNumberValidation();
			}

		} else {
			funResetBrandFields();
			alert("Available Stock is Low Than Sale Qty");
		}

	}

	function funIsStkValid(bottals, mls, pegs) {

		var dblClosingStk = "0.0";

		if (intPegSize <= 0) {
			intPegSize = intBrandSize;
		}
		var arr = dblOpStk.split('.');
		var opBts = arr[0];
		var opMls = arr[1];

		var totOpMls = parseInt(intBrandSize * (opBts.toString().trim()))
				+ parseInt(opMls.toString().trim());
		var totSaleMls = parseInt(intBrandSize * (bottals.toString().trim()))
				+ parseInt(mls.toString().trim()) + parseInt(intPegSize * pegs);
		var totClosingMls = parseInt(totOpMls - totSaleMls);

		if (totClosingMls >= intBrandSize) {
			var totCls = totClosingMls / intBrandSize;
			var arr1 = totCls.toString().split('.');
			var opBts1 = 0;
			var opMls1 = 0
			if (arr1.length > 1) {
				opBts1 = arr1[0];
				opMls1 = arr1[1];
			} else {
				opBts1 = arr1[0];
			}

			var tempMls = parseFloat(0 + "." + opMls1) * intBrandSize;
			tempMls = parseInt(tempMls);
			dblClosingStk = opBts1 + "." + tempMls;

		} else {
			dblClosingStk = 0 + "." + totClosingMls;
		}

		return dblClosingStk;
	}

	function funDeleteRow(obj) {
		var index = $(obj).closest('tr').index();
		var icode = $(obj).closest("tr").find("input[type=text]").val();
		var table = document.getElementById("tblData");
		table.deleteRow(index);
	}

	function funResetBrandFields() {
		$("#txtBrandCode").val('');
		$("#txtBrandName").text(' ');
		$("#txtBrandSize").text(' ');
		$("#txtAvailabelStk").text(' ');
		$("#txtPegSize").text(' ');
		$("#txtQty").val(' ');
		$("#txtBtls").val(0);
		$("#txtPegs").val(0);
		$("#txtML").val(0);
	}

	function funClearBrandRowData() {
		$("#tblData tr").remove();
	}

	function funApplyNumberValidation() {
		$(".numeric").numeric();
		$(".integer").numeric(false, function() {
			alert("Integers only");
			this.value = "";
			this.focus();
		});
		$(".positive").numeric({
			negative : false
		}, function() {
			alert("No negative values");
			this.value = "";
			this.focus();
		});
		$(".positive-integer").numeric({
			decimal : false,
			negative : false
		}, function() {
			alert("Positive integers only");
			this.value = "";
			this.focus();
		});
		$(".decimal-places").numeric({
			decimalPlaces : maxQuantityDecimalPlaceLimit,
			negative : false
		});
	}

	function funOpenExportImport() {
		var transactionformName = "ExcisePOSSaleDataExcelExportImport";
// 		response = window.showModalDialog(
// 				"frmExciseExcelExportImport.html?formname="
// 						+ transactionformName, "",
// 				"dialogHeight:500px;dialogWidth:500px;");

	response = window.open(
				"frmExciseExcelExportImport.html?formname="
						+ transactionformName, "",
				"dialogHeight:500px;dialogWidth:500px;");

		if (null != response) {
			funClearBrandRowData();
			funSetSaleDtlData(response[0]);
			$("#strSourceEntry").val("Excel");
			if (response[1].length > 0) {
				var str = "";
				var res = response[1];
				$.each(res, function(i, item) {
					str = res[i] + " , " + str;
				});
				alert("Available Stock is Low Than Sale Qty For Brand:-" + str);
			}
		}
	}
	
	
	function funValidateFields() 
	{
		
		if(funGetMonthEnd(document.all("txtLicenceCode").value,document.all("txtBillDate").value)!=true)
        {
            alert("Month End Not Done");
            return false
              
        }
		else
		{
			return true;
		}
	}
	
	
	//Check Month End done or not
	function funGetMonthEnd(licenceCode,date) {
		var strMonthEnd="";
		var searchUrl = "";
		searchUrl = getContextPath()
				+ "/checkExciseMonthEnd.html?licenceCode=" + licenceCode+"&date="+date+"&formName=Sale ";

		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			async: false,
			success : function(response) {
				strMonthEnd=response;
				//alert(strMonthEnd);
				
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
		if(strMonthEnd=="1")
			return false;
		if(strMonthEnd=="0")
			return true;
	}
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Excise Sale Master</label>
	</div>

<br/>
<br/>

	<s:form name="ExciseSalesMaster" method="POST" action="saveExciseSalesMaster.html?saddr=${urlHits}">
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
					<label> Sr. No</label>
				</td>
				<td colspan="6">
					<s:input type="text" id="txtSalesId" path="intId" cssClass="searchTextBox numeric integer" ondblclick="funHelp('SalesId');"/>
				</td>
				<td>
					<s:input type="hidden" required="true" id="strSourceEntry" path="strSourceEntry" value="Manual" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Licence Code</label>
				</td>
				<td>
					<s:input type="text" required="true" id="txtLicenceCode" path="strLicenceCode" cssClass="searchTextBox" value="${LicenceCode}" ondblclick="funHelp('LicenceCode');"/>
				</td>
				<td colspan="3"><label id="txtLicenceNo">${LicenceNo}</label></td>
				<td></td>
				<td>
					<label>Date</label>
				</td>
				<td>
					<s:input type="text" required="true" id="txtBillDate" class="calenderTextBox" path="dteBillDate" />
				</td>
			</tr>
		</table>
		
			<br>
			<table class="transTableMiddle">
				<tr>
					<td><label>Brand Code</label></td>
					<td>
						<input id="txtBrandCode" ondblclick="funHelp('TransactionalBrands');" class="searchTextBox" />
					</td>
					<td colspan="1"><label id="txtBrandName"></label></td>
					<td><label id="txtBrandSize"></label></td>	
					<td><label id="txtAvailabelStk"></label></td>	
					<td><label id="txtPegSize"></label></td>		
				</tr>
				<tr>

				<td><label>Peg / Glass</label></td>
				<td><input id="txtPegs" type="text" class=" BoxW124px numeric positive-integer " /></td>

				<td><label>Bottles</label></td>
				<td><input id="txtBtls" type="text" class=" BoxW124px numeric positive-integer " /></td>

				<td><label>ML</label></td>
				<td><input id="txtML" type="text" class=" BoxW124px numeric positive-integer " /></td>
			</tr>
				<tr>
					<td></td>
					<td></td>					
					<td colspan="2"><input id="btnAdd" type="button" value="Add" class="smallButton" onclick="return btnAdd_onclick();" /></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<br/>
			<div class="dynamicTableContainer" style=" overflow: visible !important;">
			<table style="height:28px;border:#0F0;width:100%;font-size:11px; font-weight: bold;">
				<tr bgcolor="#72BEFC" >
				<td width="13%">Brand Code</td><!--  COl1   -->				
				<td width="44%">Name</td><!--  COl2   -->
				<td width="8%">Btls</td><!--  COl3   -->
				<td width="8%">Peg</td><!--  COl3   -->
				<td width="8%">ML</td><!--  COl3   -->
				<td width="8%">Closing In ML</td><!--  COl3   -->
				<td width="10%">Bill Ganerated</td><!--  COl4   -->
				<td width="5%">Delete</td><!--  COl5   -->
				
				<td></td><!--  COl5   -->
				<td></td><!--  COl5   -->
				<td></td><!--  COl5   -->
			</tr>
			</table>
		<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 190px; margin: auto;	width: 100%; overflow-y: scroll;">
			<table id="tblData" style="width:100%;border:#0F0;table-layout:fixed;" class="transTablex col7-center">
				<tbody>    
					<col style="width:16%"><!--  COl0   -->		
					<col style="width:51%"><!--  COl1   -->
					<col style="width:10%"><!--  COl2  -->
					<col style="width:9%"><!--  COl3   -->
					<col style="width:10%"><!--  COl4   -->
					<col style="width:10%"><!--  COl5   -->
					<col style="width:11%"><!--  COl6   -->
					<col style="width:3%"><!--  COl7  -->
					
					<col><!--  COl7  -->
					<col><!--  COl7  -->
					<col><!--  COl7  -->
					
				</tbody>
			</table>
		</div>	
	</div>			
		

		<br />
		<br />
		<p align="center">
			<input type="submit" id="submit" value="Submit" class="form_button"  onclick="return funValidateFields();" />
			<input type="button" id="reset" value="Reset" class="form_button" />
		</p>

	</s:form>
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>
