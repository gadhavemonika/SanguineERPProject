<%@ page language="java" contentType="text/html;charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="default.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TAX MASTER</title>
	
	<script type="text/javascript">
	$(document).ready(function(){
		 resetForms('taxForm');
		    $("#txtTaxDesc").focus();	
	});
	</script>
	<script type="text/javascript">
	
	//Initialize tab Index or which tab is Active
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
				
				$(document).ajaxStart(function(){
				    $("#wait").css("display","block");
				});
				$(document).ajaxComplete(function(){
				   	$("#wait").css("display","none");
				});
				
				var propCode='<%=session.getAttribute("propertyCode").toString()%>';
				var propertyName='<%=session.getAttribute("propertyName").toString()%>';
				
				$( "#lblPropName" ).text(propertyName);
				$( "#txtPropCode" ).val(propCode);
				

			});
	
	
	
		var fieldName;
		$(function() 
		{
		 	$( "#txtDtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		 	$( "#txtDtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		 	
		 	/*$("#txtTaxCode").blur(function()
			{
				if(!$("#txtTaxCode").val()=='')
				{
					funSetData($("#txtTaxCode").val());
				}
			});*/
				    
			$('a#baseUrl').click(function() 
			{
				if($("#txtTaxCode").val().trim()=="")
				{
					alert("Please Select tax Code");
					return false;
				}
 
				 window.open('attachDoc.html?transName=frmTaxMaster.jsp&formName=Tax Master&code='+$('#txtTaxCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
              });
			
			$("#btnSubmit").click(function()
			{
				
				if (!fun_isDate($("#txtDtFromDate").val())) {
					alert('Invalid Date');
					$("#txtDtFromDate").focus();
					return false;
				}
				
				if (!fun_isDate($("#txtDtToDate").val())) {
					alert('Invalid Date');
					$("#txtDtToDate").focus();
					return false;
				}
				
				var fromDate=$("#txtDtFromDate").val();
				var toDate=$("#txtDtToDate").val();
				if(funCompareDate(fromDate,toDate)==0)
				{
					alert("Invalid From and To Date");
					return false;
				}
			});
	 	});
	
		
	 	function funResetFields()
		{
			$("#txtTaxDesc").focus();
			$("#chkTaxRounded").attr('checked', false);
			$("#chkTaxOnTax").attr('checked', false);
			$("#chkTaxOnST").attr('checked', false);
			$("#chkExcisable").attr('checked',false);
			$("#txtExternalCode").val('');
	    }		
	 	
		 
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
		function funSetTax(code)
		{
				$.ajax({
						type: "GET",
				        url: getContextPath()+"/loadTaxMasterData.html?taxCode="+code,
				        dataType: "json",
				        success: function(response)
				        {
				        	funRemRows();
				        	if('Invalid Code' == response.strTaxCode){
				        		alert("Invalid tax Code");
				        		$("#txtTaxCode").val('');
				        		$("#txtTaxCode").focus();
				        	}else{
				        		$("#txtTaxCode").val(response.strTaxCode);
					        	$("#txtTaxDesc").val(response.strTaxDesc);
					        	$("#txtTaxPer").val(response.dblPercent);
					        	$("#txtTaxAmt").val(response.dblAmount);
					        	$("#cmbTaxOnSP").val(response.strTaxOnSP);
					        	$("#cmbTaxOnGD").val(response.strTaxOnGD);
					        	$("#cmbtaxType").val(response.strTaxType);
					        	$("#cmbTaxCalType").val(response.strTaxCalculation);
					        	$("#cmbTaxIndicator").val(response.strTaxIndicator);
					        	$("#cmbApplOn").val(response.strApplOn);
					        	$("#txtPropCode").val(response.strPropertyCode);
					        	$("#cmbPartyInd").val(response.strPartyIndicator);
					        	$("#txtDtFromDate").val(response.dtValidFrom);
					        	$("#txtDtToDate").val(response.dtValidTo);
					        	$("#cmbShortName").val(response.strShortName);
					        	$("#txtGSTNo").val(response.strGSTNo);
					        	$("#txtExternalCode").val(response.strExternalCode);
					        	
					        	if(response.strTaxRounded=='Y')
					        	{
					        		$("#chkTaxRounded").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkTaxRounded").attr('unchecked', false);
					        	}
					        	
					        	if(response.strTaxOnTax=='Y')
					        	{
					        		$("#chkTaxOnTax").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkTaxOnTax").attr('checked', false);
					        	}
					        	
					        	if(response.strTaxOnST=='Y')
					        	{
					        		$("#chkTaxOnST").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkTaxOnST").attr('checked', false);
					        	}
					        	
					        	if(response.strExcisable=='Y')
					        	{
					        		$("#chkExcisable").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkExcisable").attr('checked', false);
					        	}
					        	if(response.strTaxOnSubGroup=='Y')
					        	{
					        		$("#chkTaxOnSubGroup").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkTaxOnSubGroup").attr('checked', false);
					        	}
					        	
					        	if(response.strCalTaxOnMRP=='Y')
					        	{
					        		$("#chkCalTaxOnMRP").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkCalTaxOnMRP").attr('checked', false);
					        	}
					        	
					        	$("#txtAbatement").val(response.dblAbatement);
					        	
					        	if(response.strTOTOnMRPItems=='Y')
					        	{
					        		$("#chkTOTForMRPItems").attr('checked', true);
					        	}
					        	else
					        	{
					        		$("#chkTOTOnMRPItems").attr('checked', false);
					        	}
					        	
					        	if(response.strNotApplicableForComesa=='Y')
									document.getElementById("chkNotApplicableForComesa").checked=true;
						    	else
						    		document.getElementById("chkNotApplicableForComesa").checked=false;
					        	
					        	if(response.strTaxReversal=='Y')
									document.getElementById("chkTaxReversal").checked=true;
						    	else
						    		document.getElementById("chkTaxReversal").checked=false;
					        	
					        	
					        	funLoadTaxes(response.strTaxCode,response.strTaxOnTaxCode);
					        	funFillGrid(response.listTaxSGDtl);
					        	//funLoadSubGroupMaster();
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
		
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			   	case 'taxmaster':
			      	funSetTax(code);
			    	break;
			    	
			   	case 'subgroup':
			    	funSetSubGroup(code);
			        break;
			}
		}
		
		
		
		function funSetSubGroup(code)
		{
			$("#txtSubgroupCode").val(code);
			 
			$.ajax({
			        type: "GET",
			        url: getContextPath()+"/loadSubGroupMasterData.html?subGroupCode="+code,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strSGCode=='Invalid Code')
			        	{
			        		alert("Invalid SubGroup Code");
			        		$("#txtSGCode").val('');
			        		$("#txtSGCode").focus();
			        	}
			        	else
			        	{
				        	$("#lblSGName").text(response.strSGName);
				        	$("#txtSGCode").val(response.strSGCode);
		        		}
					},
			        error: function(e)
			        {
			        	alert("Invalid SubGroup Code");
		        		$("#txtSGCode").val('');
			        }
		      });
		}
		
		
		
		
		function funChangeState()
		{
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
			//alert($("#cmbtaxType").val());
			if($("#cmbtaxType").val()=='Percent')
			{
				$("#txtTaxAmt").attr("disabled", true);
				$("#txtTaxPer").attr("disabled", false);
			}
			else if($("#cmbtaxType").val()=='Fixed Amount')
			{
				$("#txtTaxAmt").attr("disabled", false);
				$("#txtTaxPer").attr("disabled", true);
			}
			$("#txtAttName").focus();
			$("#lblParentAttName").text("");			
		}
		
		
		$(function() 
		{
			$("#txtDtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtDtFromDate" ).datepicker('setDate', 'today');
			var dt = new Date();
			var currentDateTime=dt.getDay()+"-"+(dt.getMonth()+1)+"-"+(dt.getYear()+1901);
			
			$("#txtDtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtDtToDate" ).datepicker('setDate', currentDateTime);
		});
		
		
		function funLoadTaxes(code,strTaxOnTaxCode)
		{
			$.ajax({
				type: "GET",
		        url: getContextPath()+"/loadTaxesData.html?taxCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	if('Invalid Code' == response.strTaxCode){
		        		alert("Invalid tax Code");
		        		$("#txtTaxCode").val('');
		        		$("#txtTaxCode").focus();
		        	}else{
		        		$.each(response, function(i,item)
		                {
		        			funfillTaxesRow(response[i][0],response[i][1],strTaxOnTaxCode);
						});
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
		
		
		function funfillTaxesRow(TaxCode,TaxDes,strTaxOnTaxCode)
		{
			var taxesCode = strTaxOnTaxCode.split(",");
			$('#hidTaxesCodes').val(strTaxOnTaxCode);
			
			var table = document.getElementById("tblTaxes");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var cnt=0;
		    var insertRowflg='Y';
		    row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtTaxCode."+(rowCount)+"\" value='"+TaxCode+"' />";
		    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+TaxDes+"'/>";
		    for(cnt=0;cnt<taxesCode.length;cnt++)
			{
				if(TaxCode==taxesCode[cnt])
				{
					row.insertCell(2).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\"  name=\"WOthemes\" checked=\"checked\" value='Tick' onclick=\"funTaxesChkOnClick()\"/>";
					insertRowflg='N';
					break;
				}
		    }
			if(insertRowflg=='Y')
			{
				row.insertCell(2).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\"  name=\"WOthemes\" value='Tick' onclick=\"funTaxesChkOnClick()\"/>";
			}
		}
		
		
		function funTaxesChkOnClick()
		{
			var table = document.getElementById("tblTaxes");
		    var rowCount = table.rows.length;
		    var strTaxesCode="";
		    for(no=0;no<rowCount;no++)
		    {
		        if(document.all("cbGSel."+no).checked==true)
				{
		        	if(strTaxesCode.length>0)
		        	{
		        		strTaxesCode=strTaxesCode+","+document.all("txtTaxCode."+no).value;
		        	}
		        	else
		        	{
		        		strTaxesCode=document.all("txtTaxCode."+no).value;
		        	}
		        }
		    }
		    $('#hidTaxesCodes').val(strTaxesCode);
		}
		
		
		function funRemRows() {
			var table = document.getElementById("tblTaxes");
			var rowCount = table.rows.length;

			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}

		
		
		
		
		function funLoadSubGroupMaster()
		{
			$.ajax({
				type: "GET",
		        url: getContextPath()+"/loadSubGroupData.html",
		        dataType: "json",
		        success: function(response)
		        {
		        	if('Invalid Code' == response.strSGCode){
		        		alert("Invalid SubGroup Code");
		        		$("#txtTaxCode").val('');
		        		$("#txtTaxCode").focus();
		        	}else{
		        		$.each(response, function(i,item)
		                {
		        			funFillSubGroupTab(response[i][0],response[i][1]);
						});
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
		
		
		
		function funFillSubGroupTab(subGroupCode ,subGroupName)
		{
			var table = document.getElementById("tblSubGroup");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    var cnt=0;
		    var insertRowflg='Y';
		    row.insertCell(0).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"txtTaxCode."+(rowCount)+"\" value='"+TaxCode+"' />";
		    row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box\" size=\"27%\" id=\"txtTaxDesc."+(rowCount)+"\" value='"+TaxDes+"'/>";
		    for(cnt=0;cnt<taxesCode.length;cnt++)
			{
				if(TaxCode==taxesCode[cnt])
				{
					row.insertCell(2).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\"  name=\"WOthemes\" checked=\"checked\" value='Tick' onclick=\"funTaxesChkOnClick()\"/>";
					insertRowflg='N';
					break;
				}
		    }
			if(insertRowflg=='Y')
			{
				row.insertCell(2).innerHTML= "<input id=\"cbGSel."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\"  name=\"WOthemes\" value='Tick' onclick=\"funTaxesChkOnClick()\"/>";
			}
		}
		
		
		
		//Delete a All record from a grid
		function funRemoveSGRows()
		{
			var table = document.getElementById("tblTaxSGDtl");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		
		
		function funFillGrid(resListSGDtlBean)
		{
			var taxCode=$("#txtTaxCode").val();
			funRemoveSGRows();
			$.each(resListSGDtlBean, function(i,item)
			{
				$.ajax({
			        type: "GET",
			        url: getContextPath()+"/loadSubGroupMasterData.html?subGroupCode="+resListSGDtlBean[i].strSGCode,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strSGCode=='Invalid Code')
			        	{
			        		alert("Invalid SubGroup Code");
			        		$("#txtSGCode").val('');
			        		$("#txtSGCode").focus();
			        	}
			        	else
			        	{
			        		funAddDetailsRow(taxCode,resListSGDtlBean[i].strSGCode,response.strSGName);
		        		}
					},
			        error: function(e)
			        {
			        	alert("Invalid SubGroup Code");
		        		$("#txtSGCode").val('');
			        }
		      });
			});
		}
		
		
		
	// Get Sub Group Code and Name fields and pass them to function to add into detail grid
		function funFillSGGrid() 
		{
			var taxCode=$("#txtTaxCode").val();
			var subGroupCode=$("#txtSGCode").val().trim();
			var subGroupName=$("#lblSGName").text().trim();
		    funAddDetailsRow(taxCode,subGroupCode,subGroupName);
		}
	
	
	//Function to add detail grid rows	
		function funAddDetailsRow(taxCode,subGroupCode,subGroupName) 
		{
		    var table = document.getElementById("tblTaxSGDtl");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);

		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strTaxCode."+(rowCount)+"\" value='"+taxCode+"' />";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"strSGName."+(rowCount)+"\" value='"+subGroupName+"' />";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listTaxSGDtl["+(rowCount)+"].strSGCode\" id=\"strSGCode."+(rowCount)+"\" value='"+subGroupCode+"' />";
		    
		    funResetSubGroupFields();
		}
		
	
	// Reset Sub Group Fields
		function funResetSubGroupFields()
		{
			$("#txtSGCode").val('');
			$("#txtSGCode").text('');
		}
		
		
		
	</script>
</head>

<body onload="funChangeState();">
<div id="formHeading">
		<label>Tax Master</label>
	</div>
	<s:form name="taxForm" method="POST" action="saveTaxMaster.html?saddr=${urlHits}">
			<br />
		<br />
		
		
		<div id="tab_container" class="masterTable"  style="height: 535px;">
			<ul class="tabs">
				<li class="active" data-state="tab1" style="width: 25%; left: 10%">GENERAL</li>
				<li data-state="tab2" style="width: 10%; padding-left: 55px">Tax On Tax</li>
				<li data-state="tab3" style="width: 10%; padding-left: 55px">Tax On Subgroup</li>
			</ul>
		
			<div id="tab1" class="tab_content" style="height: 520px">
				<table class="masterTable">
				
					<tr>
				       	<th align="right" colspan="4"> <a id="baseUrl" href="#">Attach Documents</a>  &nbsp; &nbsp; &nbsp;&nbsp;</th>
				    </tr>
					<tr></tr>
											
				    <tr>
				        <td width="120px"><label>TaxCode</label></td>
				        <td colspan="3"><s:input id="txtTaxCode" name="txtTaxCode" path="strTaxCode" value=""  ondblclick="funHelp('taxmaster')" cssClass="searchTextBox"/></td>
				    </tr>
				    	
				    <tr>
				        <td><label>Description</label></td>
				        <td>
				        	<s:input type="text" id="txtTaxDesc" name="txtTaxDesc" cssStyle="text-transform: uppercase;" path="strTaxDesc" required="true" cssClass="BoxW124px" />
				        	<s:errors path="strTaxDesc"></s:errors>
				        </td>
				        
				        <td><label>Short Name</label></td>
				        <td>
				        	<s:select id="cmbShortName" name="cmbShortName" cssStyle="text-transform: uppercase;" path="strShortName"  cssClass="BoxW124px"> 
				        		<option selected="selected" value=" "></option>
					            <option value="CGST">CGST</option>
					            <option value="SGST">SGST</option>
					            </s:select>
				        	
				        	<s:errors path="strShortName"></s:errors>
				        </td>
				    </tr>
					    
				    <tr>
					    <td>
					    	<label>Tax On S/P</label>
					    </td>
					    <td>
					    	<s:select id="cmbTaxOnSP" path="strTaxOnSP" cssClass="BoxW124px">
					    		<s:options items="${taxOnSPList}"/>
					    	</s:select>
					    </td>
					     <td><label>GST No</label></td>
					     <td>
				        	<s:input type="text" id="txtGSTNo" name="txtGSTNo" cssStyle="text-transform: uppercase;" path="strGSTNo"  cssClass="BoxW124px" />
				        	<s:errors path="strGSTNo"></s:errors>
				        </td>
					    
					</tr>
					
					<tr>
					    <td><label>Tax Type</label></td>
					    <td>
					    	<s:select id="cmbtaxType" path="strTaxType"  onchange="funChangeState();"  cssClass="BoxW124px">
						    	<option selected="selected" value="Percent">Percent</option>
					            <option value="Fixed Amount">Fixed Amount</option>
				            </s:select>
					    </td>
					    	<td><label>External Code</label></td>
						    <td>
						    	<s:input id="txtExternalCode" name="txtExternalCode" path="strExternalCode"  autocomplete="off" cssClass="BoxW116px"/>
						    	<s:errors path="strExternalCode"></s:errors>
						    </td>
					   	
					</tr>
					    
					<tr>
				        <td><label>Percent</label></td>
				        <td>
				        	<s:input type="number" max="100" step="0.01" id="txtTaxPer" name="txtTaxPer" path="dblPercent" required="true"  cssClass="BoxW116px right-Align"/>
				        	<s:errors path="dblPercent"></s:errors>
				        </td>
				        
				        <td><label>Not Applicable For Comesa Region</label></td>
						<td><s:checkbox id="chkNotApplicableForComesa" path="strNotApplicableForComesa" value="Y"></s:checkbox></td>
									
				    </tr>
					    
					<tr>
				        <td><label>Amount</label></td>
				        <td colspan="3">
				        	<s:input type="number" step="0.01" id="txtTaxAmt" name="txtTaxAmt" path="dblAmount" cssClass="BoxW116px right-Align"/>
				        	<s:errors path="dblAmount"></s:errors>
				        </td>
				    </tr>
					    
					<tr>
					    <td><label>Tax On G/D</label></td>
					    <td colspan="3"><s:select id="cmbTaxOnGD" path="strTaxOnGD" items="${taxOnGDList}"  cssClass="BoxW124px"/></td>
					</tr>
					
					<tr>
					    <td><label>Date Valid From</label></td>
					    <td colspan="3"><s:input id="txtDtFromDate" name="txtDtFromDate" path="dtValidFrom" cssClass="calenderTextBox"/></td>
					</tr>
					
					<tr>
					    <td><label>Date Valid To</label></td>
					    <td colspan="3"><s:input id="txtDtToDate" name="txtDtToDate" path="dtValidTo"  cssClass="calenderTextBox" /></td>
					</tr>
						
					<tr>
					    <td><label>Tax Indicator</label></td>
					    <td colspan="3"><s:select id="cmbTaxIndicator" path="strTaxIndicator" items="${taxIndicatorList}" cssClass="BoxW48px" /></td>
					</tr>
					
					<tr>
					    <td><label>Party Indicator</label></td>
					    <td colspan="3"><s:select id="cmbPartyInd" path="strPartyIndicator" items="${partyIndicatorList}"  cssClass="BoxW48px"/></td>
					</tr>
						
					<tr>
			    		<td><label>Tax Rounded</label></td>
			    		<td colspan="3"><s:checkbox element="li" id="chkTaxRounded" path="strTaxRounded" value="Yes" /></td>
					</tr>
						
					<tr>
					    <td><label>Property Code</label></td>
					    <td colspan="3"><s:input id="txtPropCode" name="txtPropCode" path="strPropertyCode" cssClass="BoxW116px"/>
					    <label id="lblPropName"></label></td>
					</tr>
						
					<tr>
					    <td><label>Tax On SubTotal</label></td>
					    <td colspan="3"><s:checkbox element="li" id="chkTaxOnST" path="strTaxOnST" value=""/></td>
					</tr>
						
					<tr>
					    <td><label>Tax On Tax</label></td>
					    <td colspan="3"><s:checkbox element="li" id="chkTaxOnTax" path="strTaxOnTax" value=""/></td>
					</tr>
						
					<tr>
					    <td><label>Excisable</label></td>
					    <td colspan="3"><s:checkbox element="li" id="chkExcisable" path="strExcisable" value=""/></td>
					</tr>
						
					<tr>
					    <td><label>Applicable On</label></td>
					    <td colspan="3"><s:select id="cmbApplOn" path="strApplOn" items="${applicableOnList}"  cssClass="BoxW124px"/></td>
					</tr>
						
					<tr>
						<td><label>Tax Calculation</label></td>
					    <td colspan="3"><s:select id="cmbTaxCalType" path="strTaxCalculation" items="${taxCalList}"  cssClass="BoxW124px"/></td>
					</tr>
					
					<tr>
						<td><label>Tax On Subgroup</label></td>
					    <td colspan="3"><s:checkbox element="li" id="chkTaxOnSubGroup" path="strTaxOnSubGroup" value=""/></td>
					</tr>
					
					<tr>
						<td><label>Calculate Tax On MRP</label></td>
					    <td colspan="3"><s:checkbox element="li" id="chkCalTaxOnMRP" path="strCalTaxOnMRP" value=""/></td>
					</tr>
					
					<tr>
				        <td><label>Abatement %</label></td>
				        <td colspan="3">
				        	<s:input type="text" id="txtAbatement" name="txtAbatement" cssStyle="text-transform: uppercase;" path="dblAbatement" required="true" cssClass="BoxW124px" />
				        	<s:errors path="dblAbatement"></s:errors>
				        </td>
				    </tr>
				    
				    <tr>
						<td><label>Calculate TOT for MRP Items</label></td>
					    <td colspan="3"><s:checkbox element="li" id="chkTOTForMRPItems" path="strTOTOnMRPItems" value=""/></td>
					</tr>
					
					<tr>
					    <td><label>Tax Reversal</label></td>
					    <td colspan="3"><s:checkbox element="li" id="chkTaxReversal" path="strTaxReversal" value="Y"/></td>
					</tr>
					
				</table>
			</div> 
		
			<div id="tab2" class="tab_content" style="height: 520px">
				<table class="masterTable">
					<tr>
						<th style="border: 1px white solid;width: 10%"><label>Tax Code</label></th>
						<th style="border: 1px white solid;width: 50%"><label>Tax Desc</label></th>
						<th style="border: 1px white solid;width: 10%"><label>Select</label></th>
					</tr>
				</table>
				
				<div style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
					<table id="tblTaxes" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll" class="transTablex col5-center">
						<tbody>
							<col style="width: 5%"><!-- col1   -->
						    <col style="width: 25%"><!-- col2   -->
							<col style="width: 5%"><!-- col3   -->
						</tbody>
					</table>
				</div>
			</div>
			
			
			<div id="tab3" class="tab_content" style="height: 520px">
				<table class="transTable">
				
					<tr>
						<td><label>Sub Group</label></td>
						<td width="10%"><input id="txtSGCode" ondblclick="funHelp('subgroup');" class="searchTextBox" /></td>
						<td width="10%"><label id="lblSGName" ></label></td>
					</tr>
					
					<tr>
						<td colspan="3"><input type="Button" value="Add" onclick="return funFillSGGrid()" class="smallButton" /></td>
					</tr>
				</table>
				
				
				<div class="dynamicTableContainer" style="height: 300px;">
				<table style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
					<tr bgcolor="#72BEFC">
						<td style="width:20%;">Tax</td>
						<td style="width:20%;">Sub Group Name</td>
						<td style="width:20%;">Sub Group Code</td>
						<td style="width:5%;"></td>
					</tr>
				</table>
			
				<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblTaxSGDtl"
						style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex col8-center">
						<tbody>
							<col style="width: 210px;">
							<col style="width: 140px;">
							<col style="width: 140px;">
							<col style="display:none;">
						</tbody>
					</table>
				</div>
			</div>
		</div>
			
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
			
		<p align="center">
			<input type="submit" value="Submit" id="btnSubmit" class="form_button"/> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
			
		<s:input type="hidden" id="hidTaxesCodes" path="strTaxOnTaxCode"></s:input>
		<br><br>
	</s:form>
</body>
</html>