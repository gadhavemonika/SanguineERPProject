<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer History Flash</title>
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

	var activeTab="";
	var txtVal="";
	var reportType="";
	var foundChar="N";
	
	$(document).ready(function() {
		
		var POSDate="${POSDate}"
		var startDate="${POSDate}";
	  	var Date = startDate.split(" ");
		var arr = Date[0].split("-");
		Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
		$("#txtdteFromDate").datepicker({ dateFormat: 'dd-mm-yy'  });
		$("#txtdteFromDate" ).datepicker('setDate', Dat);
		$("#txtdteFromDate").datepicker();
		$("#txtdteToDate").datepicker({ dateFormat: 'dd-mm-yy'  });
		$("#txtdteToDate" ).datepicker('setDate', Dat);
		
		$(".tab_content").hide();
		$(".tab_content:first").show();
		
		
		

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			 activeTab = $(this).attr("data-state");
			
			$("#" + activeTab).fadeIn();
			
			var selectedTab;
		var reportType;
			var fromDate=$("#txtdteFromDate").val();
	    	var toDate=$("#txtdteToDate").val();
			
	    	
	    	//alert($("#txtTabName").val());
			
 	    	$("#txtTabName").val(activeTab);
 	    	
 	    	$("#txtTabName").val();
			
	    	

		});
		
		


		$("#execute").click(function(event){
			

			var fromDte=$("#txtdteFromDate").val();
			var Date = fromDte.split(" ");
			var arr = Date[0].split("-");
			Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
			fromDate = Dat;
	    	var toDte=$("#txtdteToDate").val();
	    	var Date = toDte.split(" ");
			var arr = Date[0].split("-");
			Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
			toDate = Dat;

	    	txtVal=activeTab;

				
				 if(txtVal=="tab2")
				{

					selectedTab="Top Spenders";
					reportType=$('#cmbAmount :selected').text();
					
					funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate);

				}
				else if(txtVal=="tab3")
				{
					selectedTab="Non Spenders";
					reportType="Non Spenders";

					funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate);
				}
				else
					{
					if ($("#txtCustCode").val().length == 0) 
					{
	                    alert("Please Select Customer");
	                   
	                }
					else
					{	
					selectedTab="Customer Wise";
						reportType=$('#cmbReportType :selected').text();
						
						funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate);
					}
				}
			 });
	});
	
	function funGetDate(type)
	{
		if(type=="fromDate")
		{
		
		$("#txtdteFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtdteFromDate" ).datepicker('setDate', 'today');
		$("#txtdteFromDate").datepicker();
		
		}
		else
		{
			$("#txtdteToDate").datepicker({ dateFormat: 'yy-mm-dd' });
			$("#txtdteToDate" ).datepicker('setDate', 'today');
			$("#txtdteToDate").datepicker();
		}	
	}
	
	
/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	   		  selectedTab="Customer Wise";
				reportType=$('#cmbReportType :selected').text();
	       var fromDate=$("#txtdteFromDate").val();
	    	var toDate=$("#txtdteToDate").val();
	    	
	       if (reportType=="Item Wise") 
	       {
	    	  
				funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate);
	        } 
	        else 
	        {
	           
	            funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate);
	        }
	    }	
	
	
function funOnLoad()
{

	document.all[ 'divTopSpenders' ].style.display = 'block';
	document.all[ 'divNonSpenders' ].style.display = 'block';
}
function funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate)
{
	var custCode;
	var custName;
	var posName=$('#cmbPOSName').val();
	
	if(selectedTab=="Customer Wise")
	{
	 custCode=$("#txtCustCode").val();
	 custName=$("#txtCustName").val();
	}
	else if(selectedTab=="Top Spenders")
	{
		custCode=$("#cmbAmount").val();
		custName=$("#txtAmount").val();
	}	
	else if(selectedTab=="Non Spenders")
	{
		custCode="";
		custName="";
	}
	var searchurl=getContextPath()+"/loadFunFillAllTables.html";
	 $.ajax({
		        type: "POST",
		        data:{ reportType : reportType,
	 	        	fromDate:fromDate,
	 	 			toDate:toDate,
	 	 			selectedTab : selectedTab,
	 	 			custCode : custCode,
	 	 			custName : custName,
	 	 			posName : posName,
	 			},
		        url: searchurl,
		        dataType: "json",
		        async:false,
		        success: function(response)
		        {
		        	var cmbName=response.strCmbName;
		        	var strTabName=response.strTabName;
		        	if(strTabName=="Customer Wise")
		        	{	
		        	if(cmbName=="Item Wise")
	        		{
		        		document.all[ 'divBillWise' ].style.display = 'none';
 			    		document.all[ 'divItemWise' ].style.display = 'block';
 			    		funRemoveTableRows("tblItemWise");
 			    		funRemoveTableRows("tblTotalItem");
		        	$.each(response.CustomerWiseTblData, function(i, item) 
		        	{
		        		
		        			
		        			funFillCustomerWiseItemTable(item.billNo,item.billDate,item.customerCode,item.customerName,item.itemName,item.dblQuantity,
			        				item.dblAmount);
		        	});
		        	$.each(response.TotalTblData, function(i, item) 
				        	{
				        		
				        			
				        			funFillTotalItemTable(item.Total,item.totAmt);
				        	});
		        	}
		        	else
		        	{
		        		document.all[ 'divBillWise' ].style.display = 'block';
 			    		document.all[ 'divItemWise' ].style.display = 'none';
 			    		funRemoveTableRows("tblBillWise");
 			    		funRemoveTableRows("tblTotalBillWise");
		        		$.each(response.CustomerWiseTblData, function(i, item) 
		    		    {
		        			
		        			funFillCustomerWiseTable(item.billNo,item.billDate,item.dblDateCreated,item.posName,item.settelmentDesc,item.dblSubTotal,
			        				item.dblDiscountPer,item.dblDiscountAmt,item.dblTaxAmt,item.dblSettlementAmt,item.strRemark);
		    		    });
		        		$.each(response.TotalTblData, function(i, item) 
				    	{
				        			
		        			funFillTotalBillWiseTable(item.Total,item.totalSubTotal,item.blank,item.totalDiscAmt,item.totalTaxAmt,item.totalSettleAmt,item.totalTipAmt);
				    	});
		        	}
		        	}
		        	else if(strTabName=="Non Spenders")
		        	{
		        		funRemoveTableRows("tblNonSpenders");
		        		$.each(response.NonSpendersTblData, function(i, item) 
		    		    {		
		        		funFillNonSpendersTable(item.longMobileNo,item.strCustomerName,item.dteBillDate)	;
		    		    });
		        	}	
		        	else
		        	{
		        		funRemoveTableRows("tblTopSpenders");
		        		funRemoveTableRows("tblTotalTopSpenders");
		        		$.each(response.TopSpendersTblData, function(i, item) 
		    		    {   		
		        		funFillTopSpendersTable(item.LongMobileNo,item.StrCustomerName,item.strBillNo,item.dblGrandTotal);
		    		    });
		        		$.each(response.TotalTblData, function(i, item) 
					    {
					        		
		        			funFillTotalTopSpendersTable(item.Total,item.totalSettleAmt);  			
					        			
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
function funRemoveTableRows(tableId)
{
	var table = document.getElementById(tableId);
	var rowCount = table.rows.length;
	while(rowCount>0)
	{
		table.deleteRow(0);
		rowCount--;
	}
}
function funFillCustomerWiseTable(billNo,billDate,dblDateCreated,posName,settelmentDesc,dblSubTotal,
		dblDiscountPer,dblDiscountAmt,dblTaxAmt,dblSettlementAmt,strRemark)
{
	var table = document.getElementById("tblBillWise");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].billNo\" readonly=\"readonly\" class=\"Box \" size=\"13%\" id=\"txtKOTNo."+(rowCount)+"\" value='"+billNo+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].billDate\" readonly=\"readonly\" class=\"Box \" size=\"11%\" id=\"txtTime."+(rowCount)+"\" value='"+billDate+"' >";
	row.insertCell(2).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].dblDateCreated\" readonly=\"readonly\" class=\"Box \" size=\"11%\" id=\"txtWaiterName."+(rowCount)+"\" value='"+dblDateCreated+"' >";
	row.insertCell(3).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].posName\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTableName."+(rowCount)+"\" value='"+posName+"' >";	  
	row.insertCell(4).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].settelmentDesc\" readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPaxNo."+(rowCount)+"\" value='"+settelmentDesc+"' >";
	row.insertCell(5).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].dblSubTotal\" readonly=\"readonly\" class=\"Box \" size=\"16%\" id=\"txtUserCreated."+(rowCount)+"\" value='"+dblSubTotal+"' >";
	row.insertCell(6).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].dblDiscountPer\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"txtAmount."+(rowCount)+"\" value='"+dblDiscountPer+"' >";
	row.insertCell(7).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].dblDiscountAmt\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"txtdblDiscountAmt."+(rowCount)+"\" value='"+dblDiscountAmt+"'  >";
	
	row.insertCell(8).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].dblSettlementAmt\" readonly=\"readonly\" class=\"Box \" size=\"20%\" id=\"txtdblSettlementAmt."+(rowCount)+"\" value='"+dblSettlementAmt+"' >";
	row.insertCell(9).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].strRemark\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"txtstrRemark."+(rowCount)+"\" value='"+strRemark+"' >";	  
	}
function funFillCustomerWiseItemTable(billNo,billDate,customerCode,customerName,itemName,dblQuantity,dblAmount)
{
	var table = document.getElementById("tblItemWise");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].billNo\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtKOTNo."+(rowCount)+"\" value='"+billNo+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].billDate\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTime."+(rowCount)+"\" value='"+billDate+"' >";
	row.insertCell(2).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].itemName\" readonly=\"readonly\" class=\"Box \" size=\"40%\" id=\"txtitemName."+(rowCount)+"\" value='"+itemName+"' >";
	row.insertCell(3).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].dblQuantity\" readonly=\"readonly\" class=\"Box \" size=\"7%\" id=\"txtdblQuantity."+(rowCount)+"\" value='"+dblQuantity+"' >";
	row.insertCell(4).innerHTML= "<input name=\"CustomerWiseTblData["+(rowCount)+"].dblAmount\" readonly=\"readonly\" class=\"Box \" size=\"20%\" id=\"txtdblAmount."+(rowCount)+"\" value='"+dblAmount+"' >";
}
function funFillNonSpendersTable(longMobileNo,strCustomerName,dteBillDate)
{
	var table = document.getElementById("tblNonSpenders");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML= "<input name=\"NonSpendersTblData["+(rowCount)+"].longMobileNo\" readonly=\"readonly\" style=\"text-align: center\" class=\"Box \" size=\"30%\" id=\"txtlongMobileNo."+(rowCount)+"\" value='"+longMobileNo+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"NonSpendersTblData["+(rowCount)+"].strCustomerName\" readonly=\"readonly\" class=\"Box \" size=\"40%\" id=\"txtstrCustomerName."+(rowCount)+"\" value='"+strCustomerName+"' >";
	row.insertCell(2).innerHTML= "<input name=\"NonSpendersTblData["+(rowCount)+"].dteBillDate\" style=\"text-align: center;\" readonly=\"readonly\" class=\"Box \" size=\"30%\" id=\"txtdteBillDate."+(rowCount)+"\" value='"+dteBillDate+"' >";
	}

function funFillTopSpendersTable(LongMobileNo,StrCustomerName,strBillNo,dblGrandTotal)
{
	var table = document.getElementById("tblTopSpenders");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML= "<input name=\"TopSpendersTblData["+(rowCount)+"].LongMobileNo\" readonly=\"readonly\" style=\"text-align: center\" class=\"Box \" size=\"20%\" id=\"txtLongMobileNo."+(rowCount)+"\" value='"+LongMobileNo+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"TopSpendersTblData["+(rowCount)+"].StrCustomerName\" readonly=\"readonly\" class=\"Box \" size=\"40%\" id=\"txtStrCustomerName."+(rowCount)+"\" value='"+StrCustomerName+"' >";
	row.insertCell(2).innerHTML= "<input name=\"TopSpendersTblData["+(rowCount)+"].strBillNo\" readonly=\"readonly\" style=\"text-align: right\" class=\"Box \" size=\"12%\" id=\"txtstrBillNo."+(rowCount)+"\" value='"+strBillNo+"' >";
	row.insertCell(3).innerHTML= "<input name=\"TopSpendersTblData["+(rowCount)+"].dblGrandTotal\" readonly=\"readonly\" style=\"text-align: right\" class=\"Box \" size=\"40%\" id=\"txtdblGrandTotal."+(rowCount)+"\" value='"+dblGrandTotal+"' >";	  
}




function funFillTotalItemTable(Total,totAmt)
{
	var table = document.getElementById("tblTotalItem");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].Total\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTotal."+(rowCount)+"\" value='"+Total+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].totAmt\" readonly=\"readonly\" style=\"text-align: right\" class=\"Box \" size=\"12%\" id=\"txttotAmt."+(rowCount)+"\" value='"+totAmt+"' >";
	
}

function funFillTotalTopSpendersTable(Total,totalSettleAmt)
{
	var table = document.getElementById("tblTotalTopSpenders");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].Total\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTotal."+(rowCount)+"\" value='"+Total+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].totalSettleAmt\" readonly=\"readonly\" style=\"text-align: right\" class=\"Box \" size=\"12%\" id=\"txttotalSettleAmt."+(rowCount)+"\" value='"+totalSettleAmt+"' >";
	
}

function funFillTotalBillWiseTable(Total,totalSubTotal,blank,totalDiscAmt,totalTaxAmt,totalSettleAmt,totalTipAmt)
{
	var table = document.getElementById("tblTotalBillWise");
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	row.insertCell(0).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].Total\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtTotal."+(rowCount)+"\" value='"+Total+"'  >";
	row.insertCell(1).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].totalSubTotal\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txttotalSubTotal."+(rowCount)+"\" value='"+totalSubTotal+"' >";
	row.insertCell(2).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].blank\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txtblank."+(rowCount)+"\" value='"+blank+"'  >";
	row.insertCell(3).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].totalDiscAmt\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txttotalDiscAmt."+(rowCount)+"\" value='"+totalDiscAmt+"' >";
	row.insertCell(4).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].totalTaxAmt\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txttotalTaxAmt."+(rowCount)+"\" value='"+totalTaxAmt+"'  >";
	row.insertCell(5).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].totalSettleAmt\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txttotalSettleAmt."+(rowCount)+"\" value='"+totalSettleAmt+"' >";
	row.insertCell(6).innerHTML= "<input name=\"TotalTblData["+(rowCount)+"].totalTipAmt\" readonly=\"readonly\" class=\"Box \" size=\"12%\" id=\"txttotalTipAmt."+(rowCount)+"\" value='"+totalTipAmt+"'  >";
	
}


/**
* Get and Set data from help file and load data Based on Selection Passing Value(Customer Code)
**/
function funSetData(code)
{
	$("#txtCustCode").val(code);
	var searchurl=getContextPath()+"/loadPOSCustomerMasterDtl.html?POSCustomerCode="+code;
	 $.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strCustomerTypeMasterCode=='Invalid Code')
		        	{
		        		alert("Invalid Customer  Code");
		        		$("#txtCustCode").val('');
		        	}
		        	else
		        	{
			        	$("#txtCustCode").val(response.strCustomerCode);
			        	$("#txtCustName").val(response.strCustomerName);
			        	$("#txtCustName").focus();
			        	
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



function handle(e){
	
	
    if(e.keyCode === 13){
        e.preventDefault(); // Ensure it is only this code that rusn
         
    
        selectedTab="Top Spenders";
        var fromDate=$("#txtdteFromDate").val();
    	var toDate=$("#txtdteToDate").val();
		reportType=$('#cmbAmount :selected').text();
		
		funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate);
    }
    
}


function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    	//alert("Pleasse Enter Number");
        return false;
    }
    else{
    	  if(evt.keyCode === 13){
    		  evt.preventDefault(); // Ensure it is only this code that rusn
    	         
    	     //   alert("Enter was pressed was presses");
    	        selectedTab="Top Spenders";
    	        var fromDate=$("#txtdteFromDate").val();
    	    	var toDate=$("#txtdteToDate").val();
    			reportType=$('#cmbAmount :selected').text();
    			
    			funFillCustomerWiseTables(selectedTab,reportType,fromDate,toDate);
    	    }
    	
    }
    return true;
}


</script>
<script type="text/javascript">
	 
</script>


</head>

<body onload="funOnLoad()">
	<div id="formHeading">
		<label>Customer History Flash</label>
	</div>
	<s:form name="POSCustomerWiseHistoryFlash" method="POST"
		action="rptPOSCustomerHistoryFlash.html?saddr=${urlHits}"
		target="_blank">
		<br />
		<table class="masterTable" style="width: 85%;">

			<tr>
				<td><label>POS Name</label> <s:input type="hidden"
						id="txtTabName" name="txtTabName" path="strTabVal"
						cssClass="BoxW116px" value="tab1" /> <s:select id="cmbPOSName"
						name="cmbPOSName" path="strPOSName" items="${posList}"
						cssClass="BoxW124px" /></td>

				<td><label>From Date</label> <s:input id="txtdteFromDate"
						name="txtdteFromDate" path="dteFromDate"
						cssClass="calenderTextBox" /></td>

				<td><label>To Date</label> <s:input id="txtdteToDate"
						name="txtdteToDate" path="dteToDate" cssClass="calenderTextBox" />
				</td>

				<td><input id="execute" type="button" value="Execute"
					tabindex="3" class="form_button" /></td>

				<td><input type="submit" onclick="onClickExport()"
					value="Export" class="form_button" /></td>
			</tr>

		</table>


		<table
			style="border: 0px solid black; width: 85%; height: 130%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			<tr>
				<td>
					<div id="tab_container" style="overflow: hidden; height: 600px;">
						<ul class="tabs" id="MyTab">
							<li class="active" data-state="tab1">Customer Wise</li>
							<li data-state="tab2">Top Spenders</li>
							<li data-state="tab3">Non Spenders</li>

						</ul>
						<br /> <br />

						<!--  Start of Generals tab-->

						<div id="tab1" class="tab_content">
							<table class="masterTable">
								<tr>
									<td><label>Customer Code</label> <s:input type="text"
											id="txtCustCode" path="strCustCode" cssClass="searchTextBox"
											ondblclick="funHelp('POSCustomerMaster');" /> <s:input
											colspan="3" type="text" id="txtCustName" path="strCustName"
											cssClass="BoxW116px" /></td>
									<td><label>Report Type</label> <s:select
											id="cmbReportType" name="cmbReportType" path="strReportType"
											items="${mapReportType}" cssClass="BoxW124px" /></td>
								</tr>
							</table>
							<div id="divBillWise"
								style="width: 100%; margin-bottom: 5px; display: block; overflow-x: hidden; float: right; margin-right: 4px; height: 600px; border: 0px solid">

								<table border="1" class="myTable"
									style="width: 80%; margin: auto;">
									<thead>
										<tr>

											<th style="width: 12%">Bill No</th>
											<th style="width: 11%">Bill Date</th>
											<th style="width: 11%">Bill Time</th>
											<th style="width: 12%">POS Name</th>
											<th style="width: 10%">Pay Mode</th>
											<th style="width: 16%">Sub Total</th>
											<th style="width: 7%">Disc %</th>
											<th style="width: 7%">Disc Amount</th>
											<th style="width: 20%">Sales Amount</th>
											<th style="width: 7%">Remarks</th>
											<!-- 											<input type="checkbox" id="chkALL" onclick="funfillSettlementDetail()" /></th>	 -->
										</tr>

									</thead>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block;; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%; height: 400px;">
									<table id="tblBillWise" class="transTablex col5-center"
										style="width: 100%;">
										<tbody>
										<col style="width: 14%;">
										<!--  COl1   -->
										<col style="width: 14%">
										<!--  COl2   -->
										<col style="width: 15%">
										<!--  COl3   -->
										<col style="width: 16%">
										<!--  COl4  -->
										<col style="width: 13%">
										<!--  COl5   -->
										<col style="width: 22%">
										<!--  COl6   -->
										<col style="width: 14%">
										<!--  COl7   -->
										<col style="width: 13%">
										<!--  COl8   -->
										<col style="width: 21%">
										<!--  COl9   -->
										<col style="width: 8%">
										<!--  COl10   -->
										</tbody>
									</table>


								</div>
								<table border="0" class="myTable"
									style="width: 80%; margin: auto;">
									<thead>
										<tr>

											<th style="width: 12%"></th>
											<th style="width: 12%">SubTotal</th>
											<th style="width: 12%"></th>
											<th style="width: 12%">Disc</th>
											<th style="width: 12%">Tax Total</th>
											<th style="width: 12%">Sales Amount</th>
											<th style="width: 12%">Tip Amount</th>

										</tr>

									</thead>
								</table>
								<div
									style="background-color: #a4d7ff; display: block;; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%; height: 50px;">
									<table id="tblTotalBillWise" class="transTablex col5-center"
										style="width: 100%;">
										<tbody>
										<col style="width: 12%;">
										<!--  COl1   -->
										<col style="width: 12%">
										<!--  COl2   -->
										<col style="width: 12%;">
										<!--  COl3   -->
										<col style="width: 12%">
										<!--  COl4  -->
										<col style="width: 12%;">
										<!--  COl5   -->
										<col style="width: 12%">
										<!--  COl6  -->
										<col style="width: 12%;">
										<!--  COl7   -->


										</tbody>
									</table>


								</div>


							</div>
							<div id="divItemWise"
								style="width: 100%; margin-bottom: 5px; display: none; overflow-x: hidden; float: right; margin-right: 4px; height: 600px; border: 0px solid">

								<table border="1" class="myTable"
									style="width: 80%; margin: auto;">
									<thead>
										<tr>

											<th style="width: 10%">Bill No</th>
											<th style="width: 10%">Bill Date</th>
											<th style="width: 40%">Item Name</th>
											<th style="width: 7%">Quantity</th>
											<th style="width: 20%">Amount</th>

										</tr>

									</thead>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
									<table id="tblItemWise" class="transTablex col5-center"
										style="width: 100%;">
										<tbody>
										<col style="width: 11%;">
										<!--  COl1   -->
										<col style="width: 11%">
										<!--  COl2   -->
										<col style="width: 44%">
										<!--  COl3   -->
										<col style="width: 7%">
										<!--  COl4   -->
										<col style="width: 20%">
										<!--  COl5   -->
										</tbody>
									</table>


								</div>
								<table border="1" class="myTable"
									style="width: 80%; margin: auto;">
									<thead>
										<tr>

											<th style="width: 12%"></th>
											<th style="width: 12%">Sales Amount</th>


										</tr>

									</thead>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block;; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%; height: 100px;">
									<table id="tblTotalItem" class="transTablex col5-center"
										style="width: 100%;">
										<tbody>
										<col style="width: 12%;">
										<!--  COl1   -->
										<col style="width: 12%">
										<!--  COl2   -->

										</tbody>
									</table>


								</div>
							</div>
						</div>
						<!--  End of  First  tab-->


						<!-- Start of Top Spenders tab -->

						<div id="tab2" class="tab_content">
							<table class="masterTable">
								<tr>
									<td><label>Amount</label></td>
									<td><s:select id="cmbAmount" name="cmbAmount"
											path="strAmount" items="${mapAmount}" cssClass="BoxW124px" />
										<s:input type="text" id="txtAmount" name="txtAmount"
											path="strAmt" cssClass="BoxW124px"
											onkeypress="return isNumber(event)" /> <!-- 				<input type="text"  value="" id="txtChildQty" name="txtChildQty" onkeypress="return isNumber(event)" /> -->
									</td>

								</tr>


							</table>
							<div id="divTopSpenders"
								style="width: 100%; margin-bottom: 5px; display: block; overflow-x: hidden; float: right; margin-right: 4px; height: 600px; border: 0px solid">

								<table border="1" class="myTable"
									style="width: 80%; margin: auto;">
									<thead>
										<tr>

											<th style="width: 20%">Mobile Number</th>
											<th style="width: 40%">Customer Name</th>
											<th style="width: 12%">No. Of Bills</th>
											<th style="width: 40%">Sales Amount</th>

										</tr>

									</thead>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 400px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
									<table id="tblTopSpenders" class="transTablex col5-center"
										style="width: 100%;">
										<tbody>
										<col style="width: 20%;">
										<!--  COl1   -->
										<col style="width: 41%">
										<!--  COl2   -->
										<col style="width: 12%">
										<!--  COl3   -->
										<col style="width: 40%">
										<!--  COl3   -->
										</tbody>
									</table>


								</div>

								<table border="1" class="myTable"
									style="width: 80%; margin: auto;">
									<thead>
										<tr>

											<th style="width: 12%"></th>
											<th style="width: 12%">Sales Amount</th>


										</tr>

									</thead>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block;; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%; height: 50px;">
									<table id="tblTotalTopSpenders" class="transTablex col5-center"
										style="width: 100%;">
										<tbody>
										<col style="width: 12%;">
										<!--  COl1   -->
										<col style="width: 12%">
										<!--  COl2   -->

										</tbody>
									</table>


								</div>
							</div>
						</div>



						<!-- End of Top Spenders tab -->
						<!-- Start of Non Spenders tab -->

						<div id="tab3" class="tab_content">

							<div id="divNonSpenders"
								style="width: 100%; margin-bottom: 5px; display: block; overflow-x: hidden; float: right; margin-right: 4px; height: 600px; border: 0px solid">

								<table border="1" class="myTable"
									style="width: 100%; margin: auto;">
									<thead>
										<tr>

											<th style="width: 30%">Mobile Number</th>
											<th style="width: 40%">Customer Name</th>
											<th style="width: 30%">Last Transaction Date</th>
										</tr>
									</thead>
								</table>
								<div
									style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 550px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">
									<table id="tblNonSpenders" class="transTablex col5-center"
										style="width: 100%;">
										<tbody>
										<col style="width: 30%;">
										<!--  COl1   -->
										<col style="width: 41%">
										<!--  COl2   -->
										<col style="width: 30%">
										<!--  COl3   -->
										</tbody>
									</table>


								</div>
							</div>
						</div>

						<!-- End of Non Spenders tab -->

					</div>
		</table>
		<br />
		<br />
		<br />
		<p align="center">
			<input type="reset" value="Reset" class="form_button"
				onclick="funResetFields()" /> <input type="button" value="Close"
				tabindex="3" class="form_button" />

		</p>

	</s:form>

</body>
</html>