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

 		var DocFlashData;
 		var loggedInProperty="";
 		var loggedInLocation="";
 		$(document).ready(function() 
 				{
		  	loggedInProperty="${LoggedInProp}";
			loggedInLocation="${LoggedInLoc}";
			$("#cmbProperty").val(loggedInProperty);
			//alert(loggedInProperty);
			var propCode=$("#cmbProperty").val();
			//funFillLocationCombo(propCode);
			$("#divValueTotal").hide();
		});	
 		function funChangeLocationCombo()
		{
			var propCode=$("#cmbProperty").val();
			funFillLocationCombo(propCode);
		}
	
 		

		function funFillLocationCombo(propCode) 
		{
			var searchUrl = getContextPath() + "/loadLocationForProperty.html?propCode="+ propCode;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[1] + '">'+value[0]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbLocation').html(html);
					$("#cmbLocation").val(loggedInLocation);
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
 
		function funGetTotalValue(resData)
		{
			
			var docName= $('#cmbDoctype').val();
			if(docName =='GRN')
			{     
				// show bottom totals
				document.all[ 'trGRN' ].style.display = 'block';

				// hide unnessary bottom totals
				document.all[ 'trMR' ].style.display = 'none';
				document.all[ 'trMIS' ].style.display = 'none';
				
				var totsubData =resData[1];
				var totTax = resData[2];
				var totDis = resData[3];
				var totExtraCharge = resData[4];
				var totValue = resData[5]; 
				$("#txtTotSubTot").val(parseFloat(totsubData).toFixed(maxAmountDecimalPlaceLimit));
				$("#txtTotTax").val(parseFloat(totTax).toFixed(maxAmountDecimalPlaceLimit));
				$("#txtTotDis").val(parseFloat(totDis).toFixed(maxAmountDecimalPlaceLimit));
				$("#txtTotExtraCharge").val(parseFloat(totExtraCharge).toFixed(maxAmountDecimalPlaceLimit));
				$("#txtTotValue").val(parseFloat(totValue).toFixed(maxAmountDecimalPlaceLimit));
			}
			if(docName =='MR')
			{
				var totSubTotal =resData[1];
				// show bottom totals
				document.all[ 'trMR' ].style.display = 'block';
				
				// hide unnessary bottom totals
				document.all[ 'trGRN' ].style.display = 'none';
				document.all[ 'trMIS' ].style.display = 'none';
				
				$("#txtSubTotal").val(parseFloat(totSubTotal).toFixed(maxAmountDecimalPlaceLimit));
			}
			
			if(docName =='MIS')
			{
				var totTotalAmt =resData[1];
				// show bottom totals
				document.all[ 'trMIS' ].style.display = 'block';
				
				// hide unnessary bottom totals
				document.all[ 'trMR' ].style.display = 'none';
				document.all[ 'trGRN' ].style.display = 'none';

				
				$("#txtTotalAmt").val(parseFloat(totTotalAmt).toFixed(maxAmountDecimalPlaceLimit));
			}
			
			
			
			
			
		}
	 	function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(DocFlashData.length, optInit);	
		    $("#divValueTotal").show();
		    
		}
	
		var items_per_page = 10;
		function getOptionsFromForm()
		{
			var opt;
			var docName= $('#cmbDoctype').val();
			
			if(docName =='GRN')
				{
					opt = {callback: pageselectCallbackGRN};
				}
			if(docName =='MR')
			{
				opt = {callback: pageselectCallbackMR};
			}
			
			if(docName =='MIS')
			{
				opt = {callback: pageselectCallbackMIS};
			}
			
			
			opt['items_per_page'] = items_per_page;
			opt['num_display_entries'] = 10;
			opt['num_edge_entries'] = 3;
			opt['prev_text'] = "Prev";
			opt['next_text'] = "Next";
		    return opt;
		}
		
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
				
				funDocFlashReport();
			});
		
		
				
			$(document).ajaxStart(function()
			{
			    $("#wait").css("display","block");
			});
			$(document).ajaxComplete(function()
			{
				$("#wait").css("display","none");
			});
		});
		
		

		function pageselectCallbackGRN(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, DocFlashData.length);
		    var newcontent="";
		    		    	
			   	newcontent = ' <table id="tblDocFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"> <tr bgcolor="#75c0ff"> <td id="labld1" size="10%">GRN Code</td> <td id="labld2"> Date</td>	<td id="labld3"> Supplier Name</td> <td id="labld4"> BillNo </td> <td id="labld5"> Bill Date</td> <td id="labld6"> Sub Total</td> <td id="labld7"> Tax Amt</td> <td id="labld8"> Dis Amt</td> <td id="labld9"> Extra Charges</td> <td id="labld10"> Grand Total</td> </tr> ';
			   	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<tr><td>'+DocFlashData[i].strGRNCode+'</td>';
			        //newcontent += '<td>'+DocFlashData[i].strProdCode+'</td>';
			       // newcontent += '<td><a id="DocFlashUrl.'+i+'" href="#" onclick="funClick(this);">'+DocFlashData[i].dtGRNDate+'</a></td>';
			        newcontent += '<td>'+DocFlashData[i].dtGRNDate+'</a></td>';
			        newcontent += '<td>'+DocFlashData[i].strSuppName+'</td>';
			        newcontent += '<td>'+DocFlashData[i].strBillNo+'</td>';
			        newcontent += '<td>'+DocFlashData[i].dtBillDate+'</td>';
			            			        
			        
				        newcontent += '<td align="right">'+parseFloat(DocFlashData[i].dblSubTotal).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(DocFlashData[i].dblTaxAmt).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(DocFlashData[i].dblDisAmt).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(DocFlashData[i].dblExtra).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
				        newcontent += '<td align="right">'+parseFloat(DocFlashData[i].dblTotal).toFixed(maxQuantityDecimalPlaceLimit)+'</td>';
			    
			    }			   
		    newcontent += '</table>';
		    // Replace old content with new content
		   
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
		
		
		function pageselectCallbackMIS(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, DocFlashData.length);
		    var newcontent="";
		    		    	
			   	newcontent = ' <table id="tblDocFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"> <tr bgcolor="#75c0ff"> <td id="labld1" size="10%">MIS Code</td> <td id="labld2">MIS Date</td>	<td id="labld3"> Loc From</td> <td id="labld4"> Loc To </td> <td id="labld5"> Total Amt</td>  </tr> ';
			   	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<tr><td>'+DocFlashData[i].strMISCode+'</td>';
			        //newcontent += '<td>'+DocFlashData[i].strProdCode+'</td>';
			       // newcontent += '<td><a id="DocFlashUrl.'+i+'" href="#" onclick="funClick(this);">'+DocFlashData[i].dtGRNDate+'</a></td>';
			        newcontent += '<td>'+DocFlashData[i].dtMISDate+'</a></td>';
			        newcontent += '<td>'+DocFlashData[i].strLocFromName+'</td>';
			        newcontent += '<td>'+DocFlashData[i].strLocToName+'</td>';
			        
				    newcontent += '<td align="right">'+parseFloat(DocFlashData[i].dblTotalAmt).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				     
			    }			   
		    newcontent += '</table>';
		    // Replace old content with new content
		   
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
		
		
		function pageselectCallbackMR(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, DocFlashData.length);
		    var newcontent="";
		    		    	
			   	newcontent = ' <table id="tblDocFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"> <tr bgcolor="#75c0ff"> <td id="labld1" size="10%">Req Code</td> <td id="labld2"> Req Date</td>	<td id="labld3"> Loc By</td> <td id="labld4"> Loc ON </td> <td id="labld5"> Required Date</td> <td id="labld6"> Sub Total</td>  </tr> ';
			   	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			        newcontent += '<tr><td>'+DocFlashData[i].strReqCode+'</td>';
			        //newcontent += '<td>'+DocFlashData[i].strProdCode+'</td>';
			       // newcontent += '<td><a id="DocFlashUrl.'+i+'" href="#" onclick="funClick(this);">'+DocFlashData[i].dtGRNDate+'</a></td>';
			        newcontent += '<td>'+DocFlashData[i].dtReqDate+'</a></td>';
			        newcontent += '<td>'+DocFlashData[i].strLocByName+'</td>';
			        newcontent += '<td>'+DocFlashData[i].strLocOnName+'</td>';
			        newcontent += '<td>'+DocFlashData[i].dtReqiredDate+'</td>';
			            			        
			        
				    newcontent += '<td align="right">'+parseFloat(DocFlashData[i].dblSubTotal).toFixed(maxAmountDecimalPlaceLimit)+'</td>';
				       
			    }			   
		    newcontent += '</table>';
		    // Replace old content with new content
		   
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
		
		
				
			
		function funDocFlashReport()
		{			
			var docType=$("#cmbDoctype").val();
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			var locCode=$("#cmbLocation").val();
			var propCode=$("#cmbProperty").val();
			
			var param1=docType+","+locCode+","+propCode;
		
			var searchUrl=getContextPath()+"/frmDocumentFlashReport.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			//alert(searchUrl);
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	DocFlashData=response[0];
				    	showTable();
				    	funGetTotalValue(response);
				    	
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
		
		$(document).ready(function () 
		{			 
			$("#btnExport").click(function (e)
			{
				var docFlashType=$("#cmbDoctype").val();
				
				window.location.href=getContextPath()+"/downloadDocFlashExcel.html?docFlashType="+docFlashType ;
			});
		});
		  
	function funDoumentSelection()
		{
		var docName= $('#cmbDoctype').val();
			if(docName=="MR")
				{
				document.all[ 'lblLocName' ].style.display = 'none';
				document.all[ 'cmbLocation' ].style.display = 'none';
				}
			else
				{
				document.all[ 'lblLocName' ].style.display = 'block';
				document.all[ 'cmbLocation' ].style.display = 'block';
				}
		}
		 
		 
	</script>
</head>
<body onload="funOnLoad();">
<div id="formHeading">
		<label>Document Listing Flash</label>
	</div>
	<s:form action="frmDocumentListingFlashReport.html" method="GET" name="frmDocListingFlash">
		<br>
	
			<table class="transTable">
			<tr><th colspan="10"></th></tr>
				<tr>
				
					<td>Document Type </td>
					<td width="20%">
						<s:select id="cmbDoctype" name="DocType" path="strDocType" cssClass="longTextBox" cssStyle="width:100%" onchange="funDoumentSelection();" >
			    			<s:option value="GRN">GRN</s:option><s:options/>
			    			<s:option value="MR">Matrial Requsition</s:option><s:options/>
			    			<s:option value="MIS">Matrial Issue Slip</s:option><s:options/>
			    		</s:select>
					</td>
				
					<td width="10%">Property Code</td>
					<td width="20%">
						<s:select id="cmbProperty" name="propCode" path="strPropertyCode" cssClass="longTextBox" cssStyle="width:100%" onchange="funChangeLocationCombo();">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td>
						
					<td width="5%"><label id='lblLocName'>Location</label></td>
					<td>
						<s:select id="cmbLocation" name="locCode" path="strLocationCode" cssClass="longTextBox" cssStyle="width:180px;" >
			    			<s:options items="${listLocation}"/>
			    		</s:select>
					</td>
					
				</tr>
				
				<tr>
				    <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dtFromDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dtFromDate"></s:errors>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td colspan="3">
			            <s:input id="txtToDate" name="toDate" path="dtToDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dtToDate"></s:errors>
			        </td>
			        
					
				</tr>
				
								
				<tr>
					<td><input id="btnExecute" type="button" class="form_button1" value="EXECUTE"/></td>
					
					<td>
						<s:select path="strExportType" id="cmbExportType"  cssClass="BoxW124px">
							<option value="Excel">Excel</option>
						</s:select>
					</td>
					<td colspan="7">						
						<input id="btnExport" type="button" value="EXPORT"  class="form_button1"/>
					</td>
				</tr>
			</table>
			
			<dl id="Searchresult" style="width: 95%; margin-left: 26px; overflow:auto;"></dl>
		<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
		
		</div>
		<br>
		<br>
		<div id="divValueTotal">
		<table id="tblTotalFlash" class="transTablex" style="width: 95%;font-size:11px;font-weight: bold;">
			
			<tr id="trGRN" style="margin-left: 28px;  display:none  ">
			<td id="labld26" width="64%" align="right">Total &nbsp; &nbsp;</td>
			
			<td  style="width: 8%;" align="right"  >
			<input id="txtTotSubTot" style="width: 100%;text-align: right; " class="Box"></input></td>
			
			<td  style="width: 5%;" align="right">
			<input id="txtTotTax" style="width: 80%;text-align: right;" class="Box"></input></td>
			
			<td  style="width: 5%;" align="right">
			<input id="txtTotDis" style="width: 100%;text-align: right;" class="Box"></input></td>
			
			<td  style="width: 9%;" align="right">
			<input id="txtTotExtraCharge" style="width: 100%;text-align: right;" class="Box"></input></td>
			
			<td  style="width: 10%;  align="right">
			<input id="txtTotValue" style="width: 100%;text-align: right;" class="Box"></input></td>
			
<!-- 			<td id="tdTotalAmt" style="display:none " align="right"> -->
<!-- 			<input id="txtTotalAmt" style="width: 100%; text-align: right;" class="Box"></input></td> -->
			
<!-- 			<td id="tdSubTotal" style="display:none " align="right"> -->
<!-- 			<input id="txtSubTotal" style="width: 100%;text-align: right;" class="Box"></input></td> -->
			
			</tr>
			
		<tr id="trMIS" style="margin-left: 28px;  display:none  ">
			<td id="labld26" width="87%" align="right">Total &nbsp; &nbsp;</td>
			<td id="tdTotalAmt"  align="right">
 			<input id="txtTotalAmt" style="width: 100%; text-align: right;" class="Box"></input></td>
			</tr>	
			
		<tr id="trMR" style="margin-left: 28px;  display:none  ">
			<td id="labld26" width="88%" align="right">Total &nbsp; &nbsp;</td>
			<td id="tdSubTotal"  align="right"> 
			<input id="txtSubTotal" style="width: 100%;text-align: right;" class="Box"></input></td>
			</tr>		
			
			
			
		</table>
		</div>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	
</body>
</html>