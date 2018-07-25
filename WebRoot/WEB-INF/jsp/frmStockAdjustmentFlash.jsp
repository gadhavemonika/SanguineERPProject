<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
        <!-- Load data to paginate -->
	<link rel="stylesheet" href="<spring:url value="/resources/css/pagination.css"/>" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript">
// var ExportData;
    /**
	 * Global variable
	 */
   var StkFlashData;
 
   /**
	* Combo box On change
	*/
	
   function funChangeLocationCombo()
	{
		var propCode=$("#cmbProperty").val();
		funFillLocationCombo(propCode);
	}
   
   /**
	* filling Location Combo box
	*/
	function funFillLocationCombo(propCode) 
	{
		var searchUrl = getContextPath() + "/loadLocationForProperty.html?propCode="+ propCode;
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				var html = '<option value="ALL">ALL</option>';
				$.each(response, function(key, value) {
					html += '<option value="' + value[1] + '">'+value[0]
							+ '</option>';
				});
				html += '</option>';
				$('#cmbLocation').html(html);
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
 
	/**
	 * using Pagination
	**/
 	function showTable()
	{
		var optInit = getOptionsFromForm();
	    $("#Pagination").pagination(StkFlashData.length, optInit);
	   
	}

	var items_per_page = 10;
	function getOptionsFromForm(){
	    var opt = {callback: pageselectCallback};
		opt['items_per_page'] = items_per_page;
		opt['num_display_entries'] = 10;
		opt['num_edge_entries'] = 3;
		opt['prev_text'] = "Prev";
		opt['next_text'] = "Next";
	    return opt;
	}
	
	/**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	 */
	$(document).ready(function() 
		{
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Dat=arr[0]+"-"+arr[1]+"-"+arr[2];		
			$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtFromDate").datepicker('setDate',Dat);			
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate").datepicker('setDate', 'today');
			$("#cmbLocation").val("${locationCode}");
			$("#btnExecute").click(function( event )
			{
				var fromDate=$("#txtFromDate").val();
				var toDate=$("#txtToDate").val();
				
				if($("#cmbReportType").val()=='Detail')
				{
					funCalculateStockAdjFlashForDetail();
				}
				else
				{
					funCalculateStockAdjFlashForSummary();
				}
			});
			
			/**
			 * Ready Function for Ajax Waiting and reset form
			 */
			 $(document).ajaxStart(function(){
				    $("#wait").css("display","block");
				  });
				  $(document).ajaxComplete(function(){
				    $("#wait").css("display","none");
				  });
		});
		
		
		
		function pageselectCallback(page_index, jq){
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, StkFlashData.length);
		    var newcontent="";
		    if($("#cmbReportType").val()=='Detail')
		    	{
		    	
			    	newcontent = '<table id="tblStockAdjFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">SA No.</td><td id="labld2">Date</td><td id="labld3">Reason Name</td><td>Location Name</td><td id="labld4">Product Name</td><td id="labld5">UOM</td><td id="labld6">Qty</td><td>Rate</td><td>Price</td><td>Remark</td><td id="labld7">User Created</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	/* '<tr><td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+invoceFlashData[i].strInvCode+'</a></td>'; */
				    	newcontent += '<tr><td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+StkFlashData[i][0]+'</a></td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td>';				        
				        newcontent += '<td>'+StkFlashData[i][10]+'</td></tr>';
				       
				    }
			    	
		    	}
		    else
		    	{

			    	newcontent = '<table id="tblStockAdjFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labls1">SA No.</td><td id="labls2">Date</td><td id="labls3">Reason Name</td><td>Location Name</td><td id="labls4">Value</td><td id="labls5">Narration</td><td id="labls6">User Created</td></tr>';
					   
				    // Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				        /* newcontent += '<tr><td>'+StkFlashData[i][0]+'</td>'; */
				        newcontent += '<tr><td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+StkFlashData[i][0]+'</a></td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td> </tr>';
				       
				    }
		    	}
		    
		     newcontent += '</table>';
		    // Replace old content with new content
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
		function funClick(obj,dteObj)
		{
			var code=document.getElementById(""+obj.id+"").innerHTML;
			
			window.open(getContextPath()+"/openRptStockAdjustmentSlip.html?rptSACode="+code,'_blank');
			window.open(getContextPath()+"/openRptStockAdjustmentSlipSecondRpt.html?rptSACode="+code,'_blank');
		}
		
		
		/**
		 * Get and set Stockflash Summary Data
		 */
		function funCalculateStockAdjFlashForSummary()
		{
			var reportType=$("#cmbReportType").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var reasonCode=$("#cmbReason").val();
			var adjType=$("#cmbAdjustmentType").val();
			var param1=reportType+","+locCode+","+propCode+","+reasonCode+","+adjType;
			var searchUrl=getContextPath()+"/flshStockAdjSummaryReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {				    	
				    	StkFlashData=response;
				    	showTable();
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
		
		
		
		/**
		 * Get and set Stockflash Detail Data
		 */
		function funCalculateStockAdjFlashForDetail()
		{			
			var reportType=$("#cmbReportType").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			var reasonCode=$("#cmbReason").val();
			var adjType=$("#cmbAdjustmentType").val();
			var param1=reportType+","+locCode+","+propCode+","+reasonCode+","+adjType;		
			var searchUrl=getContextPath()+"/flshStockAdjDetailReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	StkFlashData=response;
				    	showTable();
				    	
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
		
	
		 /*  $(document).ready(function () {
			 
				$("#btnExport").click(function (e)
				{
					var reportType=$("#cmbReportType").val();
					var fromDate=$("#txtFromDate").val();
					var toDate=$("#txtToDate").val();
					var locCode=$("#cmbLocation").val();
					var propCode=$("#cmbProperty").val();
						
					var param1=reportType+","+locCode+","+propCode;
					window.location.href=getContextPath()+"/downloadExcel.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
				});
		 }); */
		 
		 /**
		  * Excel Export
		 **/
		 $(document).ready(function () {
		 $("#btnExport").click(function() {  
			  var dtltbl = $('#dvStock').html(); 
			  window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#Searchresult').html()));
			});
		 }); 
	</script>
</head>
<body>
<div id="formHeading">
		<label>Stock Adjustment Flash</label>
	</div>
	<s:form action="frmStockAdjustmentFlash.html" method="GET" name="frmStkAdjFlash">
		<br>
	
			<table class="transTable">
			<tr><th colspan="7"></th></tr>
				<tr>
					<td width="8%">Property Name</td>
					<td width="15%">
						<s:select id="cmbProperty" name="propCode" path="strPropertyCode" cssClass="longTextBox" cssStyle="width:80%" onchange="funChangeLocationCombo();">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td>
						
					<td width="5%"><label>Location</label></td>
					<td width="20%">
						<s:select id="cmbLocation" name="locCode" path="strLocationCode" cssClass="longTextBox" cssStyle="width:100%;" >
			    			<s:options items="${listLocation}"/>
			    		</s:select>
					</td>
					<td width="8%"><label>Reason Code</label></td>
					<td width="20%">
						<s:select id="cmbReason" name="resCode" path="strReasonCode" cssClass="longTextBox" cssStyle="width:100%;" >
			    			<s:options items="${listReason}"/>
			    		</s:select>
					</td>
				</tr>
					
				<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dteFromDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteFromDate"></s:errors>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td>
			            <s:input id="txtToDate" name="toDate" path="dteToDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteToDate"></s:errors>
			        </td>
			        
			        <td width="5%"><label>Adjustment Type</label></td>
			        <td width="20%">
			        <s:select id="cmbAdjustmentType" name="adjustmentType" path="strAdjustmentType" cssClass="longTextBox" cssStyle="width:100%;" >
			    		<option value="All">ALL</option>
						<option value="Physical Stock Posting">Physical Stock Posting</option>
						<option value="POS Sale Data">POS Sale Data</option>
			    	</s:select>
			        </td>
			        
			        <td>
			        </td>
				</tr>
						
				<tr>
					<td>Report Type</td>
					<td colspan="6">
						<s:select path="strReportType" id="cmbReportType" cssClass="BoxW124px">
						<option value="Summary">Summary</option>
						<option value="Detail">Detail</option>
							
						</s:select>	
						
						<input id="btnExecute" type="button" class="form_button1" value="EXECUTE"/>
					<s:select path="strExportType" id="cmbExportType"  cssClass="BoxW124px">
							<option value="Excel">Excel</option>
						</s:select>					
						<input id="btnExport" type="button" value="EXPORT"  class="form_button1"/>
					</td>
					
					
				</tr>
			</table>
			
			<br><br>
			<dl id="Searchresult" style="padding-left: 26px;overflow:auto;width: 95%"></dl>
		<div id="Pagination" class="pagination" style="padding-left: 26px;"></div>
		
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	
</body>
</html>