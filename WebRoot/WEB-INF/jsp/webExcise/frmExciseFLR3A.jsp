<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
 <style type="text/css">
 .cell{
 	background: inherit;
    border: 0 solid #060006;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: bold;
    outline: 0 none;
    padding-left: 0;
    width: 40px
 }
 
  .brandname{
 	background: inherit;
    border: 0 solid #060006;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: bold;
    outline: 0 none;
    padding-left: 0;
    width: 120px
 }
 
  .tpcode{
 	background: inherit;
    border: 0 solid #060006;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: bold;
    outline: 0 none;
    padding-left: 0;
    width: 50px
 }
 
 </style>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var totalTableCol=0;
	var colspan=0;
	var inPeg;

	var companyName="";
	var licenceNo="";
	var address="";
	var fDate="";
	var tDate="";
	var sizeCodeRowIndex=0;
	var licenceCode;
	
	$(function() 
	{
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$( "#txtFromDate" ).datepicker('setDate','today');
		
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
		$( "#txtToDate" ).datepicker('setDate','today');
		
		$("[type='reset']").click(function(){
			location.reload(true);
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
		
		$("#btnSubmit").click(function(event)
		{
			var fromDate=$("#txtFromDate").val();
			var toDate=$("#txtToDate").val();
			inPeg=$('#txtInPeg').val();
			
			
			if(fromDate.trim()=='' && fromDate.trim().length==0)
			{
				alert("Please Enter From Date");
				return false;
			}
			if(toDate.trim()=='' && toDate.trim().length==0)
			{
				alert("Please Enter To Date");
				return false;
			}
			if(funDeleteTableAllRows()){
				if(CalculateDateDiff(fromDate,toDate)){
					funFetchRowAndCol(fromDate,toDate);
				}
			}
		});
});

	function CalculateDateDiff(fromDate, toDate) {

		var frmDate = fromDate.split('-');
		var fDate = new Date(frmDate[2], frmDate[1], frmDate[0]);

		var tDate = toDate.split('-');
		var t1Date = new Date(tDate[2], tDate[1], tDate[0]);

		var dateDiff = t1Date - fDate;
		if (dateDiff >= 0) {
			return true;
		} else {
			alert("Please Check From Date And To Date");
			return false
		}
	}

	function funFetchRowAndCol(fromDate, toDate) {
		var searchUrl = "";
		licenceCode=$('#cmbLicenceCode').val();
		searchUrl = getContextPath() + "/fetchExciseReportColumn.html?licenceCode="+licenceCode;
		$.ajax({
					type : "GET",
					url : searchUrl,
					dataType : "json",
					async:false,
					success : function(response) {
						var totalSizeLength=response.totalSizeLength;
						totalTableCol= (totalSizeLength*4)+3;
						funAddHeders(totalSizeLength);
	        			$.each(response.SubCategory, function(i,item)
						{
	        					funFetchRowData(response.SubCategory[i],response.SubCategoryName[i],fromDate,toDate);
						});
			    	
			<%if(session.getAttribute("companyName") != null) {%>
				companyName='<%=session.getAttribute("companyName").toString()%>';
	            <%
	            }%>
	            
	            <%if(session.getAttribute("LicenceNo") != null) {%>
	            	licenceNo='<%=session.getAttribute("LicenceNo").toString()%>';
		            <%
	            }%>
		            
	            <%if(session.getAttribute("address") != null) {%>
	           	 	address='<%=session.getAttribute("address").toString()%>';
		            <%
	            }%>
	            
		            fDate=fromDate;
		            tDate=toDate;
		            
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
	
	function funAddHeders(colspan){
		var $row = $('<tr id="headerRow">'+
					'<th></th>'+
					'<th></th>'+
					'<th></th>'+
			      '<th colspan="'+colspan+'"><h4>OPENING STOCK</h4></th>'+
			      '<th colspan="'+(colspan)+'"><h4>RECEIVED</h4></th>'+
			      '<th colspan="'+(colspan)+'"><h4>SALES</h4></th>'+
			      '<th colspan="'+(colspan)+'"><h4>CLOSING STOCK</h4></th>'+
			      '</tr>');    
			
			$('#tblExciseReport').prepend($row);
	}
	
	function funFetchRowData(subCategory,subCategoryName,fromDate,toDate)
	{
		var searchUrl="";
		searchUrl=getContextPath()+"/fetchExciseRowData.html?";
		$.ajax({
		        type: "GET",
		        url: searchUrl,
		        async:false,
		        data:"fromDate="+fromDate+"&toDate="+toDate+"&subCategory="+subCategory
		        +"&subCategoryName="+subCategoryName+"&inPeg="+inPeg+"&licenceCode="+licenceCode,
			    success: function(response)
			    {
					funAddFullRow(response.SizeQty);
			    	$.each(response.Data, function(i,item)
					{
			    		funAddFullRow(response.Data[i]);
					});
			    	if(response.Total.length>0){
			    		funAddUnderLine(response.Total);
			    	}
			    	if(response.Total.length>0){
			    		funAddFullRow(response.Total);
			    		funAddUnderLine(response.Total);
			    		funAddFullBlankRow(response.Total);
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
	
 	function funAddFullRow(rowData)
	{
		if(rowData !=null){
			var table = document.getElementById("tblExciseReport");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
			    for(var i=0;i<rowData.length;i++){
			    	if(i==0){
			    		row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" value='"+rowData[i]+"' />";	
			    	} else if(i==1){
			    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"brandname\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
			    	}else if(i==2){
			    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"tpcode\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
			    	}else{
			    			row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+rowData[i]+"' />";
		   			 }
				}
			}
    }
	
	
	function funAddUnderLine(rowData)
	{
			var table = document.getElementById("tblExciseReport");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
			    for(var i=0;i<rowData.length;i++){
			    	if(i==0){
			    		row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" value='"+rowData[i]+"' />";	
			    	} else if(i==1){
			    		row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"brandname\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+'`'+"' />";
			    	}else if(i==2){
			    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"tpcode\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+' '+"' />";
			    	}else{
			    		if(rowData[i].trim().length >0 && rowData[i].trim() !=0){
				    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+'-----'+"' />";
			    		}else{
				    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+' '+"' />";
			    		}
		   			 }
				}
// 			    $('#tblExciseReport tr:eq('+rowCount+')').hide();
    }
	
	function funAddFullBlankRow(rowData)
	{
			var table = document.getElementById("tblExciseReport");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
			    for(var i=0;i<rowData.length;i++){
			    	if(i==0){
			    		row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"cell\" value='"+' '+"' />";	
			    	} else if(i==1){
			    		row.insertCell(i).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" class=\"brandname\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+'`'+"' />";
			    	}else if(i==2){
			    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"tpcode\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+' '+"' />";
			    	}else{
				    		row.insertCell(i).innerHTML= "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"FLRList["+ (rowCount)+"].strCol"+i+"\" value='"+' '+"' />";
		   			 }
				}
// 			    $('#tblExciseReport tr:eq('+rowCount+')').hide();
    }
    
	
	function funDeleteTableAllRows()
	{
		$('#tblExciseReport tbody').empty()
		
		var table = document.getElementById("tblExciseReport");
		var rowCount1 = table.rows.length;
		if(rowCount1==0){
			return true;
		}else{
			return false;
		}
	}

	$(function() 
	{
		$("form").submit(function(event)
		{
			var table = document.getElementById("tblExciseReport");
			var rowCount = table.rows.length;
			
			if (rowCount > 2){
				$("#txtFromDate").val(fDate);
				$("#txtToDate").val(tDate);
				return true;
			} else {
				alert("Data Not Available");
				return false;
			}
		});
	});
	

	

	 function funSetLicenceNo() {
			var code=$("#cmbLicenceCode").val();
	
			var gurl=getContextPath()+"/loadExciseLicenceMasterData.html?licenceCode=";
				$.ajax({
			        type: "GET",				        
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {	
			        	if(response.strLicenceNo=='Invalid Code')
			        	{
			        		alert("Invalid Licence Code Please Select Again");
			        	
			        	}
			        	else
			        	{ 
			        	
			        		if(response.strLicenceNo==null)
			        			{
			        			$("#licenceNo").text('');
			        			}else{
			        		//$("#txtLicenceCode").val(response.strLicenceCode);
			        		$("#licenceNo").text(response.strLicenceNo);
						}}
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
	<label>Excise FLR-3A Report</label>
	</div>

<br/>
<br/>

	<s:form name="FLR3AReport" method="POST" action="saveFLRData.html?saddr=${urlHits}" target="_blank">
	<div>
		<table class="masterTable">
			<tr>
				<td>
					<label>From Date</label>
				</td>
				<td>
					<s:input type="text" id="txtFromDate" class="calenderTextBox" path="strFromDate" required="required" />
				</td>
				
				<td>
					<label>To Date</label>
				</td>
				<td>
					<s:input type="text" id="txtToDate" class="calenderTextBox" path="strToDate" required="required"/>
				</td>
				<td>
					<label>Unit Type</label>
				</td>
				<td>
				<s:select id="txtInPeg" path="strInPeg" cssClass="BoxW124px">
					<s:option value="Peg">PegWise</s:option>
					<s:option value="ML">MLWise</s:option>
				</s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>Export Format</label>
				</td>
				<td>
					<s:select id="txtExportType" path="strExportType" cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="EXCEL">EXCEL</s:option>
					</s:select>
				</td>
				<td>
					<label>Licence Number</label>
				</td>
<!-- 				<td> -->
<%-- 					<s:input id="txtLicenceCode" ondblclick="funHelp('LicenceCode')" type="text" path="strLicenceCode" cssClass="searchTextBox" value="${LicenceCode}" /> --%>
<!-- 				</td> -->
				<td ><s:select id="cmbLicenceCode" items="${listLicence}" path="strLicenceCode" cssClass="BoxW124px" onchange="funSetLicenceNo()">
			
					</s:select></td>
					
				<td><s:label id="licenceNo" path="strLicenceNo" >${LicenceNo}</s:label></td> 
			<td> </td>
			</tr>
		</table>	
	<br>
	<br>
		<div style="width:100%;overflow-x:auto !important ; overflow-y:auto !important;">
			<table id="tblExciseReport" class="transTablex" style="width:100%; text-align:center !important;">
			</table>
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Display" class="form_button" id="btnSubmit"/>
			<input type="submit" value="Export" class="form_button"/>
<!-- 			<input type="button" value="Export" class="form_button" id="btnExport"/> -->
			<input type="reset" value="Reset" class="form_button"/>
		</p>
		<br/><br/><br/>
	</div>
	
	<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	
	</s:form>
</body>
</html>
