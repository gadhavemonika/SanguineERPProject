<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rate Contractor</title>
<script type="text/javascript">

	$(document).ready(function() 
	{
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
		$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$('#txtFromDate').datepicker('setDate', 'today');
		$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$('#txtToDate').datepicker('setDate', 'today');
		$("#txtRateContDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$('#txtRateContDate').datepicker('setDate', 'today');
		$(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		  });
		  $(document).ajaxComplete(function(){
		    $("#wait").css("display","none");
		  });
	});
</script>
	<script type="text/javascript">
				
		var fieldName,listRow=0;
		
		$(function()
		{
			$( "#txtFromDate" ).datepicker();
			$( "#txtToDate" ).datepicker();
			$( "#txtRateContDate" ).datepicker();
		});
		
		function funResetProductFields()
		{
			$("#txtProdCode").val('');
		    document.getElementById("lblPOSItemCode").innerHTML='';
		    document.getElementById("lblProdName").innerHTML='';
		    $("#txtUOM").text('');
		    $("#txtRate").val('');
		    $("#txtDiscount").val('0');
		    $("#txtNarration").val('');
		}
		
		function funRemProdRows()
	    {
			var table = document.getElementById("tblProduct");
			var rowCount = table.rows.length;
			for (var i = rowCount - 1; i >= 0; i--) {
				table.deleteRow(i);
			}
	    }
		
		function funValidateFields()
		{
			if(!isNaN(document.getElementById("txtRate").value))
			{
				alert("Enter only numbers in Rate field "+document.getElementById("txtRate").value);
				return false;
			}
			else
			{
				alert("OK");
			}
			return true;
	    }
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	     //   window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'ratecontno':
			    	funSetRateConData(code);
			        break;
			        
			    case 'suppcodeActive':
			    	funSetSupplier(code);
			        break;
			        
			    case 'productmaster':
			    	funSetProduct(code);
			        break;
			        
			    case 'tcForSetup':
			    	funSetTCFields(code);
			        break;
			}
		}
		
		
		function funSetTCFields(code)
		{
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadTCForSetup.html?tcCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	$("#txtTCCode").val(response.strTCCode);
			        $("#lblTCName").text(response.strTCName);
			        $("#txtTCDesc").focus();
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
		
		
		function funAddTCRows()
		{
			var table = document.getElementById("tblTermsAndCond");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var tcCode=$("#txtTCCode").val();
		    var tcName=$("#lblTCName").text();
		    var tcDesc=$("#txtTCDesc").val();
		    
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listTCMaster["+(rowCount)+"].strTCName\" id=\"txtTCName."+(rowCount)+"\" value='"+tcName+"' />";
		    row.insertCell(1).innerHTML= "<input class=\"longTextBox\" size=\"20%\" name=\"listTCMaster["+(rowCount)+"].strTCDesc\" id=\"txtTCDesc."+(rowCount)+"\" value='"+tcDesc+"' />";
		    row.insertCell(2).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"Box\" name=\"listTCMaster["+(rowCount)+"].strTCCode\" id=\"txtTCCode."+(rowCount)+"\" value='"+tcCode+"' />";
		    row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTCRow(this)">';
		    funResetTCFields();
		}
		
		
		function funDeleteTCRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblTermsAndCond");
		    table.deleteRow(index);
		}
		
		function funResetTCFields() 
		{
		    $("#txtTCCode").val("");
		    $("#lblTCName").text("");
		    $("#txtTCDesc").val("");
		}
		function funDeleteTCTableAllRows()
		{
			var table = document.getElementById("tblTermsAndCond");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}		
		}
		
		function funSetRateConData(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/frmRateContract1.html?rateContNo="+code;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strRateContNo){
				    		alert('Invalid Code');	
					    	$("#txtRateContractNo").val('');					    	
					    	$("#txtRateContractNo").focus();
				    	}
				    	else
				    	{
					    	$("#txtRateContractNo").val(response.strRateContNo);
					    	$("#txtSupplierCode").val(response.strSuppCode);
					    	$("#txtSupplierName").text(response.strSuppName);
					    	$("#txtRateContDate").val(response.dtRateContDate);
					    	$("#txtFromDate").val(response.dtFromDate);
					    	$("#txtToDate").val(response.dtToDate);
					    	$("#cmbDateChange").val(response.strDateChg);
					    	$("#cmbCurrency").val(response.strCurrency);
					    	$("#cmbAllProduct").val(response.strProdFlag);
					    	funGetProdData1(response.listRateContDtl);
					    	funGetTC(response.listTCMaster);
					    	$("#txtProdCode").focus();
					    	
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
		function funGetProdData1(response)
		{
			funRemProdRows();
			var count=0;
			$.each(response, function(i,item)
            {	
				count=i;
                funfillProdRow(response[i].strProductCode,response[i].strProductName,response[i].strPartNo,
				response[i].strUnit,response[i].dblRate,response[i].dblDiscount,response[i].strNarration);
                                                   
            });
			listRow=count+1;
		}
		function funGetTC(response)
		{
			funDeleteTCTableAllRows();
			$.each(response, function(i,item)
            {	
				funfillTC(response[i].strTCName,response[i].strTCDesc,response[i].strTCCode);
                                                   
            });
		}
		function funfillProdRow(strProdCode,strProdName,strPOSCode,strUOM,strRate,dblDiscount,strNarration)
		{
				var table = document.getElementById("tblProduct");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			   
			    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\"  name=\"listRateContDtl["+(rowCount)+"].strProductCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"'/>";
			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listRateContDtl["+(rowCount)+"].strProductName\" id=\"lblProdName."+(rowCount)+"\" value='"+strProdName+"'  />";
			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"11%\" name=\"listRateContDtl["+(rowCount)+"].strPartNo\" id=\"lblPOSItemCode."+(rowCount)+"\" value='"+strPOSCode+"' />";
			    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listRateContDtl["+(rowCount)+"].strUnit\" id=\"txtUOM."+(rowCount)+"\" value='"+strUOM+"' />";
			    row.insertCell(4).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"4%\" class=\"decimal-places-amt\" name=\"listRateContDtl["+(rowCount)+"].dblRate\" id=\"txtRate."+(rowCount)+"\" value='"+strRate.toFixed(maxAmountDecimalPlaceLimit)	+"' >";
			    row.insertCell(5).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"4%\"class=\"decimal-places-amt\"  name=\"listRateContDtl["+(rowCount)+"].dblDiscount\" id=\"txtDiscount."+(rowCount)+"\" value='"+dblDiscount.toFixed(maxAmountDecimalPlaceLimit)	+"' >";
			    row.insertCell(6).innerHTML= "<input size=\"32%\" type=\"text\" name=\"listRateContDtl["+(rowCount)+"].strNarration\" id=\"txtNarrtion."+(rowCount)+"\" value='"+strNarration+"' >";
			    row.insertCell(7).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		}
		function funfillTC(strTCName,strTCDesc,strTCCode)
		{
			var table = document.getElementById("tblTermsAndCond");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" type=\"text\" name=\"listTCMaster["+(rowCount)+"].strTCName\" size=\"50%\" value='"+strTCName+"' >";
		    row.insertCell(1).innerHTML= "<input type=\"text\" size=\"50%\"	class=\"longTextBox\" name=\"listTCMaster["+(rowCount)+"].strTCDesc\" value='"+strTCDesc+"' >";
			row.insertCell(2).innerHTML= "<input type=\"hidden\" name=\"listTCMaster["+(rowCount)+"].strTCCode\" value='"+strTCCode+"' >";
			row.insertCell(3).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteTCRow(this)">';											
		
		}
		function funSetSupplier(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadSupplierMasterData.html?partyCode="+code;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if('Invalid Code' == response.strPCode){
				    		alert('Invalid Code');	
					    	$("#txtSupplierCode").val('');
					    	$("#txtSupplierName").text('');
					    	$("#txtSupplierCode").focus();
				    	}else{
				    	$("#txtSupplierCode").val(response.strPCode);
				    	$("#txtSupplierName").text(response.strPName);
				    	$("#txtFromDate").focus();
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
		
		
		function funSetProduct(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	if('Invalid Code' == response.strProdCode){
			    		alert('Invalid Product Code');
				    	$("#txtProdCode").val('');
				    	$("#lblPOSItemCode").text('');
				    	$("#txtProdCode").focus();
			    	}else{
			    	$("#txtProdCode").val(response.strProdCode);
		        	document.getElementById("lblPOSItemCode").innerHTML=response.strPartNo;
		        	document.getElementById("lblProdName").innerHTML=response.strProdName;
		        	$("#txtUOM").text(response.strUOM);
		        	$("#txtRate").focus();
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
		
		
		$(function()
		{
			$('a#baseUrl').click(function() 
			{
				if($("#txtRateContractNo").val().trim()=="")
				{
					alert("Please Select Rate Contract Code");
					return false;
				}

				 window.open('attachDoc.html?transName=frmRateContract.jsp&formName= Rate Contract &code='+$('#txtRateContractNo').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
						
			$('#txtRateContractNo').blur(function () {
				var code=$('#txtRateContractNo').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){								  
			   		funSetRateConData(code);
				}
			});
						
			$('#txtSupplierCode').blur(function () {
				var code=$('#txtSupplierCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/"){
				   funSetSupplier(code);
				}
			});
						
			$('#txtProdCode').blur(function () {
			 	var code=$('#txtProdCode').val();   
				if (code.trim().length > 0 && code !="?" && code !="/"){
				   funSetProduct(code);
				}
			});
					
			$("#btnAddTC").click(function( event )
			{
				funAddTCRows();
			});
			
			$("#frmRateCont").submit(function( event ) 
			{				
				 if(!fun_isDate($("#txtRateContDate").val())){
					 alert('Invalid Date');
			            $("#txtRateContDate").focus();
			            return false;
					}
				 if(!fun_isDate($("#txtFromDate").val())){
					 alert('Invalid Date');
			            $("#txtFromDate").focus();
			            return false;
					}
				 if(!fun_isDate($("#txtToDate").val())){
					 alert('Invalid Date');
			            $("#txtToDate").focus();
			            return false;
					}
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				if(funCompareDate(fromDate,toDate)==0)
				{
					alert("Invalid From and To Date");
					return false;
				}
			});
		});
		
		
		function btnAdd_onclick() 
	    {
			var strProdCode=$("#txtProdCode").val();
			var strProdName = $("#lblProdName").text();	
			
			if(strProdCode.trim().length == 0 && strProdName.trim().length==0){
				alert("Please Enter Product Code Or Search");
				$("#txtProdCode").focus()
				 return false;
			}
			if($("#txtRate").val().trim().length==0 || $("#txtRate").val()==0)
				{
					alert("Please Enter Rate");
					$("#txtRate").focus();
					return false;
				}
			else
				{
					if(funDuplicateProduct(strProdCode))
						{
							funAddRow() ;
						}
					
				}
		}
		function funDuplicateProduct(strProdCode)
		{
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblProduct tr').each(function()
				    {
					    if(strProdCode==$(this).find('input').val())// `this` is TR DOM element
	    				{
					    	alert("Already added "+ strProdCode);
		    				flag=false;
	    				}
					});
				    
		    	}
		    return flag;
		  
		}
		
		function funAddRow() 
		{
			var prodCode = $("#txtProdCode").val();
			var posItemCode = $("#lblPOSItemCode").text();
		    var prodName = $("#lblProdName").text();	
		    var uom = $("#txtUOM").text();
		    var rate = document.getElementById("txtRate").value;
		    rate=parseFloat(rate).toFixed(maxAmountDecimalPlaceLimit);
		    var discount = document.getElementById("txtDiscount").value;
		    if(discount=='')
		    {
		    	discount=0;
		    }
		    discount=parseFloat(discount).toFixed(maxAmountDecimalPlaceLimit);
		    var narration = document.getElementById("txtNarration").value;
		    
		    var table = document.getElementById("tblProduct");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    rowCount=listRow;
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\"  name=\"listRateContDtl["+(rowCount)+"].strProductCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+prodCode+"'/>";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listRateContDtl["+(rowCount)+"].strProductName\" value='"+prodName+"' id=\"lblProdName."+(rowCount)+"\" />";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"11%\" name=\"listRateContDtl["+(rowCount)+"].strPartNo\" value='"+posItemCode+"' id=\"lblPOSItemCode."+(rowCount)+"\" />";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listRateContDtl["+(rowCount)+"].strUnit\" id=\"txtUOM."+(rowCount)+"\" value='"+uom+"' />";
		    row.insertCell(4).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"4%\" class=\"decimal-places-amt\" name=\"listRateContDtl["+(rowCount)+"].dblRate\" id=\"txtRate."+(rowCount)+"\" value='"+rate+"' >";
		    row.insertCell(5).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"4%\"class=\"decimal-places-amt\"  name=\"listRateContDtl["+(rowCount)+"].dblDiscount\" id=\"txtDiscount."+(rowCount)+"\" value='"+discount+"' >";
		    row.insertCell(6).innerHTML= "<input size=\"32%\" type=\"text\" name=\"listRateContDtl["+(rowCount)+"].strNarration\" id=\"txtNarrtion."+(rowCount)+"\" value='"+narration+"' >";
		    row.insertCell(7).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
		    listRow++;
		    funResetProductFields();
		    funApplyNumberValidation();
		    $("#txtProdCode").focus();
		    return false;
			
		}
		 
		function funDeleteRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProduct");
		    table.deleteRow(index);
		}
		
		 function funApplyNumberValidation(){
				$(".numeric").numeric();
				$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
				$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
				$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
			    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
			    $(".decimal-places-amt").numeric({ decimalPlaces: maxAmountDecimalPlaceLimit, negative: false });
			}
		 function funOnload() {
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
					alert("Data Save successfully\n\n"+message);
				<%
				}}%>
				
				var flagOpenFromAuthorization="${flagOpenFromAuthorization}";
				if(flagOpenFromAuthorization == 'true')
				{
					funSetMRHdData("${authorizationRateContractCode}");
				}
		 }
		 function funCallFormAction(actionName,object) 
			{
				var table = document.getElementById("tblProduct");
			    var rowCount = table.rows.length;
			   
				if(!fun_isDate($("#txtRateContDate").val()) || $("#txtRateContDate").val().trim().lenght==0)
				{
					alert('Invalid Date');
			        $("#txtRateContDate").focus();
			        return false;
				}
			  if(!fun_isDate($("#txtFromDate").val()) || $("#txtFromDate").val().trim().lenght==0)
				{
					alert('Invalid From Date');
			        $("#txtFromDate").focus();
			        return false;
				}
			  if(!fun_isDate($("#txtToDate").val()) || $("#txtToDate").val().trim().lenght==0)
				{
					alert('Invalid To Date');
			        $("#txtToDate").focus();
			        return false;
				}						 
			if($("#txtSupplierCode").val()=="")
				{
				 	alert("Please Enter Supplier Or Search");
					return false;
				}
			if(rowCount==0)
				{
				  	alert("Please fill the Product");
				  	return false;
				}
				else
				{
					return true;
				}
			}
		 function funResetFields()
			{
				location.reload(true); 
			}
	</script>
</head>

<body onload="funOnload();">

<div id="formHeading">
		<label>Rate Contract</label>
	</div>
	<s:form id="frmRateCont" name="rateContract" method="POST" action="saveRateCotract.html?saddr=${urlHits}">
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
				<tr>
				<td>	
		<div id="tab_container" style="height: 550px">
							<ul class="tabs">
								<li class="active" data-state="tab1" style="width: 100px;padding-left: 55px">General</li>
								<li data-state="tab2"  style="width: 150px;padding-left: 55px">Terms & Conditions</li>
							</ul>
							
				<div id="tab1" class="tab_content" style="height: 450px" >
				<table class="transTable">
					
					<tr>				    
				        <th align="right" colspan="4"> <a id="baseUrl" href="#">Attach Documents</a>&nbsp; &nbsp; &nbsp; &nbsp; </th>
				    </tr>
						
				    <tr>
				        <td width="16%"><label id="lblRateContNo" >Rate Contract Code</label></td>
				        <td width="16%"><s:input id="txtRateContractNo" path="strRateContNo" ondblclick="funHelp('ratecontno')"  cssClass="searchTextBox"/></td>
				        
				        <td  width="14%"><label id="lblRateContDate">Rate Contract Date</label></td>
				        <td>
				            <s:input id="txtRateContDate" required="required" path="dtRateContDate" pattern="\d{1,2}-\d{1,2}-\d{4}"  cssClass="calenderTextBox"/>
				        	<s:errors path="dtRateContDate"></s:errors>
				        </td>
				    </tr>
					    
				    <tr>
					    <td><label id="lblSuppCode" >Supplier Code</label></td>
				        <td><s:input id="txtSupplierCode" required="required" path="strSuppCode" ondblclick="funHelp('suppcodeActive')"  cssClass="searchTextBox"/></td>
					    <td colspan="4"><label for="strSuppName" id="txtSupplierName" class="namelabel" style="font-size: 12px;"></label></td>
					</tr>
					
					<tr>
					    <td><label id="lblFromDate">From Date</label></td>
				        <td>
				            <s:input id="txtFromDate" required="required" path="dtFromDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/>
				        	<s:errors path="dtFromDate"></s:errors>
				        </td>
				        
				        <td><label id="lblToDate">To Date</label></td>
				        <td>
				            <s:input id="txtToDate" required="required" path="dtToDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/>
				        	<s:errors path="dtToDate"></s:errors>
				        </td>
					</tr>
					
					<tr>
					    <td><label id="lblDateChange">Date Change</label></td>
				        <td>
				            <s:select id="cmbDateChange" required="required" path="strDateChg" items="${dateChgList}"  cssClass="simpleTextBox"/>
				        </td>
				        
				        <td><label id="lblCurrency">Currency</label></td>
				        <td>
				        	<s:select id="cmbCurrency" path="strCurrency" items="${currencyList}"  cssClass="simpleTextBox"/>
				        </td>
					</tr>
					
					<tr>
					    <td><label id="lblProduct">All Products</label></td>
				        <td colspan="4" align="left">
				            <s:select id="cmbAllProduct" path="strProdFlag" items="${allProdList}"  cssClass="simpleTextBox"/>
				        </td>
					</tr>					
				</table>
					
					<table class="transTableMiddle1">
					<tr>
					<td width="16%">Product Code</td>
					<td  width="16%"><input id="txtProdCode"  ondblclick="funHelp('productmaster')" class="searchTextBox"></input></td>
					<td  width="12%">Product Name</td>
					<td width="28%"><label id="lblProdName" style="font-size: 12px;"></label></td>
					<td  width="10%">POS Item Code</td>
					<td  width="8%"><label id="lblPOSItemCode" class="namelabel"></label></td>
					<td  width="5%">UOM</td>
					<td width="5%"><label id="txtUOM" class="namelabel"></label></td>
					</tr>
					
					<tr>
					<td>Rate</td>
					<td><input id="txtRate" type="text"  class="decimal-places-amt numberField"></input></td>
					<td>Discount</td>
					<td colspan="5"><input id="txtDiscount" type="text" value="0" class="decimal-places numberField"></input></td>
					</tr>
					
					<tr>
					<td>Narration</td>
					<td><input id="txtNarration" style="width: 100%" class="simpleTextBox"></input></td>
					<td colspan="6" align="left"><input id="btnAdd" type="button" value="Add"  class="smallButton" onclick="return  btnAdd_onclick();"></input></td>
					</tr>
					</table>
					
						<div class="dynamicTableContainer" style="height: 260px">
						<table  style="height:20px;border:#0F0;width:100%;font-size:11px;
			font-weight: bold;">	
		
							<tr bgcolor="#72BEFC" >
							<td width="4%">Product Code</td><!--  COl1   -->
							<td width="18%">Product Name</td><!--  COl2   -->
							<td width="5%">POS Item Code</td><!--  COl3   -->
							<td width="3%">UOM</td><!--  COl4   -->
							<td width="3%">Rate</td><!--  COl5   -->
							<td width="2%">Discount</td><!--  COl6   -->
							<td width="15%">Narration</td><!--  COl7   -->		
							<td width="5%">Delete</td><!--  COl9   -->
		
							</tr>
						</table>
							<div style="background-color:  	#a4d7ff;
					    border: 1px solid #ccc;
					    display: block;
					    height: 210px;
					    margin: auto;
					    overflow-x: hidden;
					    overflow-y: scroll;
					    width: 99.8%;">
					   <table id="tblProduct" style="width:100%;border:
						#0F0;table-layout:fixed;overflow:scroll" class="transTablex col8-center">
							<tbody>    
							<col style="width:4%"><!--  COl1   -->
							<col style="width:18%"><!--  COl2   -->
							<col style="width:5%"><!--  COl3   -->
							<col style="width:3%"><!--  COl4   -->
							<col style="width:3%"><!--  COl5   -->
							<col style="width:3.5%"><!--  COl6   -->
							<col style="width:15%"><!--  COl7   -->	
							<col style="width:4%"><!--  COl9   -->
					</tbody>
		</table>
					   
					    </div>
    				</div>
				</div>
				
				<!--    -->			
								
				<div id="tab2" class="tab_content">
				
					<table class="transTableMiddle">
						<tr>
							<td> <label class="namelabel">TC Code</label> </td>
							<td><input id="txtTCCode" ondblclick="funHelp('tcForSetup')" class="searchTextBox" /></td>									
							<td> <label id="lblTCName" class="namelabel"></label> </td>
							<td> <label class="namelabel">TC Description</label> </td>
							<td><input id="txtTCDesc" class="longTextBox" /></td>
							<td colspan="3"><input type="Button" value="Add" id="btnAddTC" class="smallButton" /></td>
						</tr>
					</table>
						
					<table class="transTable" id="tblTermsAndCondColumns">
						<tr>
							<td width="40%">TC Name</td>
							<td width="80%">TC Description</td>
						</tr>
					</table>
				
					<table border="1" class="myTable" style="font-size:11px;
						font-weight: bold;" id="tblTermsAndCond">
						
					</table>
				</div>			
							
							
		</div>
		</td>
		</tr>
		</table>
		
		<br>
		<p align="center">
			<input type="submit" value="Submit" id="btnSubmit" onclick="return funCallFormAction('submit',this);" class="form_button" />
		    <input type="button" value="Reset" onclick="funResetFields()" class="form_button" />
		</p>
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	<script type="text/javascript">funApplyNumberValidation();</script>
</body>
</html>