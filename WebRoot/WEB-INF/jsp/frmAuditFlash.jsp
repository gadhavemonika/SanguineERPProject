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
 var StkFlashData;
 
 /**
	 * Ready Function for Initialize textField with default value
	 * And Set date in date picker 
	 * And Getting session Value
	 */
 $(document).ready(function() 
			{
				var startDate="${startDate}";
				//alert(startDate);
				var arr = startDate.split("/");
				Dat=arr[0]+"-"+arr[1]+"-"+arr[2];		
				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtFromDate").datepicker('setDate',Dat);			
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate").datepicker('setDate', 'today');
				var strPropCode='<%=session.getAttribute("propertyCode").toString()%>';
				var locationCode ='<%=session.getAttribute("locationCode").toString()%>';
				$("#cmbProperty").val(strPropCode);
				$("#cmbLocation").val(locationCode);
				$("#btnExecute").click(function( event )
				{
					funGetAuditFlashData();
				});
				/**
				 * Ready Function for Ajax Waiting
				 */
				 $(document).ajaxStart(function(){
					    $("#wait").css("display","block");
					  });
					  $(document).ajaxComplete(function(){
					    $("#wait").css("display","none");
					  });
					  
					  if($("#cmbProperty").val()=="ALL")
						 {
						  	$("#cmbLocation").val("ALL");
						 }
					
			});
			
    /**
	 * Combo box change event
	 */
	 function funChangeLocationCombo()
		{
			var propCode=$("#cmbProperty").val();
			funFillLocationCombo(propCode);
		}
	
	 /**
	  * Fill Combo box change Property code
	 **/
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
 
 
	//Pagination Data
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
	
		function pageselectCallback(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, StkFlashData.length);
		    var newcontent="";
		    var combo1 = document.getElementById("cmbReportType");
		    var rptType = combo1.options[combo1.selectedIndex].text
		    
		    var combo2 = document.getElementById("cmbTransType");
		    var TransType = combo2.options[combo2.selectedIndex].text
		  /**
		   *Checking Report type and transaction type and create and fill table
		  **/
		    if(rptType=="Edited")
			{
		    	if(TransType=="GRN(Good Receiving Note)")
		    	   {
		    				TransType=TransType.replace(/\s/g,"%20");
					    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">GRN Code</td><td id="labld2">Date</td><td id="labld5">Supplier Code</td><td id="labld6">Supplier Name</td><td>Loc Code</td><td>Location Name</td><td>Bill No.</td><td id="labld7">Pay Mode</td><td>Total Amt</td><td>User Created</td><td>User Modified</td><td>Date Created</td><td>Last Modified</td></tr>';
					    	// Iterate through a selection of the content and build an HTML string
						    for(var i=page_index*items_per_page;i<max_elem;i++)
						    {
						    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
						        newcontent += '<td align="right">'+StkFlashData[i][8]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][9]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][10]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][11]+'</td>';
						        newcontent += '<td>'+StkFlashData[i][12]+'</td></tr>';
						       
						    }
			    	
		    	}
		    if(TransType== "Opening Stock")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Opening Stock Code</td><td id="labld2">Loc Code</td><td id="labld3">Location Name</td><td id="labld4">User Created</td><td id="labld5">User Modified</td><td id="labld6">Created Date</td><td id="labld7">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td></tr>';
				       
				    }
		    	}
		    if(TransType== "Physical Stk Posting")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Physical Stock Code</td><td id="labld2">Date</td><td id="labld3">Location Code</td><td id="labld4">Location Name</td><td id="labld5">SA Code</td><td id="labld6">User Created</td><td id="labld7">User Modified</td><td id="labld8">Created Date</td><td id="labld9">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td></tr>';
				       
				    }
		    	}
		    if(TransType== "Production Order")
		    	{
		    	TransType=TransType.replace(/\s/g,"%20");
		    	}
		    if(TransType== "Purchase Indent")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">PI Code</td><td id="labld2">Date</td><td id="labld3">Location Code</td><td id="labld4">Location Name</td><td id="labld5">Narration</td><td id="labld6">Total Amt</td><td id="labld7">User Created</td><td id="labld8">User Modified</td><td id="labld9">Created Date</td><td id="labld10">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td></tr>';
				       
				    }
		    	}
		    if(TransType== "Purchase Order")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");																														    	
			    	newcontent = '<table  id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">PO Code</td><td id="labld2">Date</td><td id="labld3">Supplier Code</td><td id="labld4">Supplier Name</td><td id="labld5">Against</td><td id="labld6">Total Amt</td><td id="labld7">User Created</td><td id="labld8">User Modified</td><td id="labld9">Created Date</td><td id="labld10">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td></tr>';
				       
				    }
		    	}
		    if(TransType=="Purchase Return")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1">PR Code</td><td id="labld2">Date</td><td id="labld3">Against</td><td id="labld5">Narration</td><td id="labld6">Supp Code</td><td id="labld7">Supplier Name</td><td id="labld8">Total Amt</td><td id="labld9">User Created</td><td id="labld10">User Modified</td><td id="labld11">Date Created</td><td id="labld10">Last Modified</td></tr>';
				    // Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][10]+'</td> </tr>';
				    }
		    	}
		    if(TransType=="Stock Adjustment")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
		    	 	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1">SA Code</td><td id="labld2">Date</td><td id="labld3">Loc Code</td><td id="labld4">Location Name</td><td id="labld5">Narration</td><td id="labld6">Total Amt</td><td id="labld7">User Created</td><td id="labld8">User Modified</td><td id="labld9">Date Created</td><td id="labld10">Last Modified</td></tr>';
				    // Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td> </tr>';
				    }
		    	}
		    if(TransType=="Stock Transfer")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">ST Code</td><td id="labld2">Date</td><td id="labld3">From Location</td><td id="labld4">To Location</td><td id="labld5">Against</td><td id="labld6">Material Issue</td><td id="labld7">Narration</td><td id="labld8">User Created</td><td id="labld9">User Modified</td><td id="labld10">Created Date</td><td id="labld11">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][10]+'</td></tr>';
				       
				    }
		    	}
		    if(TransType=="Work Order")
		    	{
		    	TransType=TransType.replace(/\s/g,"%20");
		    	}
		    if(TransType=="Material Return")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Material Return Code</td><td id="labld2">Date</td><td id="labld3">From Location</td><td id="labld4">To Location</td><td id="labld5">Against</td><td id="labld6">MIS Code</td><td id="labld7">Narration</td><td id="labld8">User Created</td><td id="labld9">User Modified</td><td id="labld10">Created Date</td><td id="labld11">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
			    	for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	/* newcontent += '<tr><td>'+StkFlashData[i][0]+'</td>'; */
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][10]+'</td></tr>';
				    }
		    	}
		    if(TransType=="Material Issue Slip")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">MIS Code</td><td id="labld2">Date</td><td id="labld3">From Location</td><td id="labld4">To Location</td><td id="labld5">Against</td><td id="labld6">Req Code</td><td id="labld7">Total Amt</td><td id="labld8">Narration</td><td id="labld9">User Created</td><td id="labld10">User Modified</td><td id="labld11">Created Date</td><td id="labld12">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][10]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][11]+'</td></tr>';
				       
				    }
		    	}
		    if(TransType=="Material Requisition")
		    	{
		    		TransType=TransType.replace(/\s/g,"%20");
			    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Req Code</td><td id="labld2">Date</td><td id="labld3">From Location</td><td id="labld4">To Location</td><td id="labld5">Against</td><td id="labld6">Total Amt</td><td id="labld7">Narration</td><td id="labld8">User Created</td><td id="labld9">User Modified</td><td id="labld10">Created Date</td><td id="labld11">Last Modified</td></tr>';
			    	// Iterate through a selection of the content and build an HTML string
				    for(var i=page_index*items_per_page;i<max_elem;i++)
				    {
				    	newcontent += '<tr><td><a href='+getContextPath()+'/funOpenAuditRptSlip.html?strTransCode='+StkFlashData[i][0]+'&TransType='+TransType+'&TransMode=Edited target="_blank">'+StkFlashData[i][0]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
				        newcontent += '<td align="right">'+StkFlashData[i][5]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][9]+'</td>';
				        newcontent += '<td>'+StkFlashData[i][10]+'</td></tr>';
				       
				    }
		    	}
		    
		    if(TransType=="Invoice")
	    	{
		    	TransType=TransType.replace(/\s/g,"%20");
		    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1">Invoice Code</td><td id="labld2">Date</td><td id="labld3">Loc Code</td><td id="labld4">Location Name</td><td id="labld5">Narration</td><td id="labld6">Total Amt</td><td id="labld7">User Created</td><td id="labld8">User Modified</td><td id="labld9">Date Created</td><td id="labld10">Last Modified</td></tr>';
			    // Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {

			    	var invoiceUrl=funOpenInvoiceFormat();
			    	newcontent += '<tr><td><a href='+invoiceUrl+'\.html?rptInvCode='+StkFlashData[i][0]+'&rptInvDate='+StkFlashData[i][1]+'\ target="\_blank\"  id="\StrInvCode."+(rowCount)+"\" >'+StkFlashData[i][0]+'</a></td>';
			        newcontent += '<td>'+StkFlashData[i][1]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][2]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][3]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][4]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][5]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][6]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][7]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][8]+'</td>';
			        newcontent += '<td>'+StkFlashData[i][9]+'</td> </tr>';
			    }	
	    	}
			}
		    if(rptType=="Deleted")
			{
		    	newcontent = '<table id="tblAuditFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Transaction Code</td><td id="labld2">Date</td><td id="labld3">User</td><td id="labld4">Reason </td><td id="labld5">Narration</td></tr>';
		    	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			    	TransType=TransType.replace(/\s/g,"%20");
			    	newcontent += "<tr><td><a href="+getContextPath()+"/funOpenAuditRptSlip.html?strTransCode="+StkFlashData[i][0]+"&TransType="+TransType+"&TransMode=Deleted  target=_blank >"+StkFlashData[i][0]+"</td>";
			        newcontent += "<td>"+StkFlashData[i][1]+"</td>";
			        newcontent += "<td>"+StkFlashData[i][2]+"</td>";
			        newcontent += "<td>"+StkFlashData[i][3]+"</td>";
			        newcontent += "<td>"+StkFlashData[i][4]+"</td></tr>";
			    }
			}
		     newcontent += '</table>';
		    // Replace old content with new content
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
		
		/**
		 * Geting Audit flash Data based on filter
		**/
		function funGetAuditFlashData()
		{			
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
		    var combo1 = document.getElementById("cmbReportType");
			var rptType = combo1.options[combo1.selectedIndex].text
		    var combo2 = document.getElementById("cmbTransType");
		    var TransType = combo2.options[combo2.selectedIndex].text
			var param1=locCode+","+propCode+","+TransType+","+rptType;	
		   
			var searchUrl=getContextPath()+"/frmAuditFlashReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
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
		 * Excel Export
		**/
		 $(document).ready(function () {
		 $("#btnExport").click(function() {  
			  var dtltbl = $('#dvStock').html(); 
			  window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#Searchresult').html()));
			});
		 }); 
		 
		
		 function funOpenInvoiceFormat()
			{
				var invPath="";
					invoiceformat='<%=session.getAttribute("invoieFormat").toString()%>';
					if(invoiceformat=="Format 1")
						{
							invPath="openRptInvoiceSlip";
							
						}
					else if(invoiceformat=="Format 2")
						{
							invPath="rptInvoiceSlipFromat2";
						}
					else if(invoiceformat=="Format 5")
						{
							invPath="rptInvoiceSlipFormat5Report";
						}
					else if(invoiceformat=="RetailNonGSTA4"){
						 invPath="openRptInvoiceRetailNonGSTReport";
						}
					else if("Format 6")
						{
							invPath="rptInvoiceSlipFormat6ReportForAudit";
						}
						else
					    {
							invPath="rptInvoiceSlipFormat6ReportForAudit";
					    }
				return invPath;
			}
	</script>
</head>
<body>
<div id="formHeading">
		<label>Audit Flash</label>
	</div>
	<s:form action="frmAuditFlash.html" method="GET" name="frmAuditFlash">
		<br>
	
			<table class="transTable">
			<tr><th colspan="7"></th></tr>
				<tr>
					<td width="10%">Property Name</td>
					<td width="15%">
						<s:select id="cmbProperty" name="propCode" path="strPropertyCode" cssClass="longTextBox" cssStyle="width:80%" onchange="funChangeLocationCombo();">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td>
						
					<td width="5%"><label>Location</label></td>
					<td width="30%" colspan="2">
						<s:select id="cmbLocation" name="locCode" path="strLocationCode" cssClass="longTextBox" cssStyle="width:100%;" >
			    			<s:options items="${listLocation}"/>
			    		</s:select>
					</td>
					<td>
					</td>
					</tr>
					<tr>
					<td width="8%"><label>Report Type</label></td>
					<td width="20%">
						<s:select id="cmbReportType" path="" cssClass="longTextBox">
						  <option value="Edited">Edited</option>
						  <option value="Deleted">Deleted</option>
					</s:select>
			    		
					</td>
					<td width="8%"><label>Transaction Type</label></td>
					<td width="30%" colspan="2">
						<s:select id="cmbTransType" path="" cssClass="longTextBox">
							<s:options items="${listAuditName}"/>
						</s:select>
			    		
					</td>
					<td>
					</td>
				</tr>
					
				<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="" cssClass="calenderTextBox"/>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td>
			            <s:input id="txtToDate" name="toDate" path="" cssClass="calenderTextBox"/>
			        </td>
			        <td>
			        </td>
			        <td>
			        </td>
				</tr>
						
				<tr>
					<td colspan="1">
						
						<input id="btnExecute" type="button" class="form_button1" value="EXECUTE"/>
					</td>
					<td>
					<s:select path="strExportType" id="cmbExportType"  cssClass="BoxW124px">
							<option value="Excel">Excel</option>
						</s:select>	
						</td>
						<td colspan="4">				
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