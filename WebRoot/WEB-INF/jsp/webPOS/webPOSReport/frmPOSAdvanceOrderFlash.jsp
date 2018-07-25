<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
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

.cell {
	background: inherit;
	border: 0 solid #060006;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	outline: 0 none;
	padding-left: 0;
	width: 100%
}


.header {
	border: #c0c0c0 1px solid;
	background: #78BEF9;
	
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	outline: 0 none;
	padding-left: 0;
	width: 100%;
	height:100%
}
</style>
<script type="text/javascript">


	
	$(document).ready(function() {
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

		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});

		 $(function() 
	    			{		
		 var POSDate="${POSDate}"
			    var startDate="${POSDate}";
			  	var Date = startDate.split(" ");
				var arr = Date[0].split("-");
				Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
				$("#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
				$("#txtFromDate" ).datepicker('setDate', Dat); 
				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
				$("#txtToDate" ).datepicker('setDate', Dat); 
				
	    			
				funLoadTableData();
	    			}); 
		
		$("[type='reset']").click(function(){
			location.reload(true);
		});
		
		$("form").submit(function(event){
			var table = document.getElementById("tblAdvOrderFlash");
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

		$("#btnSubmit").click(function(event) {
			var fromDate = $("#txtFromDate").val();
			var toDate = $("#txtToDate").val();

			if (fromDate.trim() == '' && fromDate.trim().length == 0) {
				alert("Please Enter From Date");
				return false;
			}
			if (toDate.trim() == '' && toDate.trim().length == 0) {
				alert("Please Enter To Date");
				return false;
			}
			if(funDeleteTableAllRows()){
				if(CalculateDateDiff(fromDate,toDate)){
					fDate=fromDate;
					tDate=toDate;
					funFetchColNames();
				}
			}
		});

	});
	
	
	function funDeleteTableAllRows()
	{
		$('#tblAdvOrderFlash tbody').empty();
		$('#tblTotal tbody').empty();
		var table = document.getElementById("tblAdvOrderFlash");
		var rowCount1 = table.rows.length;
		if(rowCount1==0){
			return true;
		}else{
			return false;
		}
	}
	
	function CalculateDateDiff(fromDate,toDate) {

		var frmDate= fromDate.split('-');
	    var fDate = new Date(frmDate[2],frmDate[1],frmDate[0]);
	    
	    var tDate= toDate.split('-');
	    var t1Date = new Date(tDate[2],tDate[1],tDate[0]);

    	var dateDiff=t1Date-fDate;
  		 if (dateDiff >= 0 ) 
  		 {
         	return true;
         }else{
        	 alert("Please Check From Date And To Date");
        	 return false
         }
	}

	function funLoadTableData()
	{
	
       	
		funFetchColNames();
		
	}
	
	function funFetchColNames() {
		fDate=  $("#txtFromDate").val();
		tDate=	$("#txtToDate").val();
		
		var strReportType=$('#cmbReportType').val();
		var posName=$('#cmbPOSName').val();
		var strUserName=$('#cmbUserName').val();
		var strDateFilter=$('#cmbDateFilter').val();
		var strCustomerCode=$('#txtCustomerCode').val();
		var operationType=$('#cmbOperationType').val();
		var strOrderMode=$('#cmbOrderMode').val();
		var strStatus=$('#cmbStatus').val();
			var gurl = getContextPath() + "/loadAdvOrderFlash.html";
		
		
		$.ajax({
			type : "GET",
			data:{ fromDate:fDate,
					toDate:tDate,
					strDateFilter:strDateFilter,
					strCustomerCode:strCustomerCode,
					operationType:operationType,
					posName:posName,
					strReportType:strReportType,
					strOrderMode:strOrderMode,
					strStatus:strStatus,
				},
			url : gurl,
			dataType : "json",
			success : function(response) {
				if (response.RowCount == 0  ) {
					alert("Data Not Found");
				} else {
					
					//Add Sub Category Headers
					funAddHeaderRow(response.ColHeader);
					
					//Add Size Names And Headers
					
					switch(operationType)
					 {
							case "Item Wise":
							   {
									 
								$.each(response.List,function(i,item){
						            	
										funFillTableWith10Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
					            	});
									
								   
							   }
							   break;
						
							case "Customer Wise":
							   {
									 
								$.each(response.List,function(i,item){
						            	
									funFillTableWith11Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10]);
					            	});
									
								   
							   }
							   break;
							case "Bill Wise":
							   {
									 
								$.each(response.List,function(i,item){
						            	
									funFillTableWith15Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10],item[11],item[12],item[13],item[14]);
					            	});
									
								   
							   }
							   break;
							   
							case "MenuHead Wise":
							   {
									 
								$.each(response.List,function(i,item){
						            	
									funFillTableWith10Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
					            	});
									
								   
							   }
							   break;
							case "Group Wise":
							   {
									 
								$.each(response.List,function(i,item){
						            	
									funFillTableWith10Col(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
					            	});
									
								   
							   }
							   break;
				
					 }		
					
					funFillTotalData(response.totalList);
					
				}//Else block Of Response
				
				
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
	
	function funFillTotalData(rowData) 
	{
		var table = document.getElementById("tblTotal");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    for(var i=0;i<rowData.length;i++)
	    	 {
	   		
	 	   			row.insertCell(i).innerHTML = "<input type=\"text\" readonly=\"readonly\" class=\"cell\" name=\"rowList["+(rowCount)+"].strCol"+(i)+"\" id=\"rowList["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
	   		
	   		 }
		
	  
	}
 	function funAddHeaderRow(rowData){
		var table = document.getElementById("tblAdvOrderFlash");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		    for(var i=0;i<rowData.length;i++){
		    	if(i==0){
	    			 row.insertCell(i).innerHTML= "<input type=\"text\"  readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
	    		 } else {
		    			row.insertCell(i).innerHTML= "<input type=\"text\"  readonly=\"readonly\" class=\"header\" name=\"colRow["+(rowCount)+"].strCol"+(i)+"\" value='"+rowData[i]+"' />";
		    			
		    		}
				}
		} 
	
	
	function funFillTableWith15Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt,strUserCreated,strUserEdited,strReasonName,item11,item12,item13,item14,item15)
	{
		var table = document.getElementById("tblAdvOrderFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		 row.insertCell(0).innerHTML= "<input  class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	     
	      row.insertCell(5).innerHTML= "<input class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strUserCreated+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strUserEdited+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strReasonName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      
	      row.insertCell(10).innerHTML= "<input class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+item11+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(11).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+item12+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(12).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+item13+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(13).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+item14+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(14).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+item15+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      
	}
	function funFillTableWith11Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt,strUserCreated,strUserEdited,strReasonName,item11)
	{
		var table = document.getElementById("tblAdvOrderFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		 row.insertCell(0).innerHTML= "<input  class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	     
	      row.insertCell(5).innerHTML= "<input class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strUserCreated+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strUserEdited+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strReasonName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+item11+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	}
	function funFillTableWith10Col(strBillNo,strBillDate,strModifiedDate,strEntryTime,strModifyTime,dblBillAmt,dblNetAmt,strUserCreated,strUserEdited,strReasonName)
	{
		var table = document.getElementById("tblAdvOrderFlash");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		 row.insertCell(0).innerHTML= "<input  class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+strBillNo+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+strBillDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strModifiedDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strEntryTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strModifyTime+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	     
	      row.insertCell(5).innerHTML= "<input class=\"Box \" size=\"10%\" id=\"txtItemName."+(rowCount)+"\" value='"+dblBillAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(6).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtDate."+(rowCount)+"\" value='"+dblNetAmt+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(7).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtCompStk."+(rowCount)+"\" value='"+strUserCreated+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(8).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+strUserEdited+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(9).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"10%\" id=\"txtVariance."+(rowCount)+"\" value='"+strReasonName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      
	}
	
	function funHelp(transactionName)
	 
	{	
 		fieldName=transactionName;
 		// window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
	
	function funSetData(code)
	{
		$("#txtCustomerCode").val(code);
		var searchurl=getContextPath()+"/loadPOSCustomerMasterData.html?POSCustomerCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strCustomerTypeMasterCode=='Invalid Code')
			        	{
			        		alert("Invalid Customer  Code");
			        		$("#txtCustomerCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtCustomerCode").val(response.strCustomerCode);
				        	$("#txtCustomerName").val(response.strCustomerName);
				        	
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
</script>


</head>

<body>
	<div id="formHeading">
		<label>POS Advanced Order Flash</label>
	</div>
	<br />
	<br />
	<s:form name="POSDayWiseSalesSummeryFlash" method="POST"
		action="rptPOSAdvOrderFlash.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin:auto;">

					<tr>
						<td width="140px">POS Name</td>
						<td><s:select id="cmbPOSName" name="cmbPOSName"
								path="strPOSName" cssClass="BoxW124px" items="${posList}">
								
								</s:select></td>
								
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:select id="cmbDateFilter" path="dteDate"
								cssClass="BoxW124px">
								<s:option value="Order Date">Order Date</s:option>
								<s:option value="Booking Date">Booking Date</s:option>
								
							</s:select></td>

							
						<td><label>From Date</label></td>
						<td><s:input id="txtFromDate" required="required"
								path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="toDate"
								pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>
 		
 						
					</tr>
					<tr>
					<td>
					<label>Customer Name</label>
				</td>
				<td colspan="1">
					<input  type="text" id="txtCustomerName" readonly="readonly" class="searchTextBox" ondblclick="funHelp('POSCustomerMaster');"/>
					<s:input  type="hidden" id="txtCustomerCode" path="strPSPCode"/>
				</td>
						
							
							<td><label>Type</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:select id="cmbOperationType" path="strOperationType"
								cssClass="BoxW124px">
								<s:option value="Bill Wise">Bill Wise</s:option>
								<s:option value="Item Wise">Item Wise</s:option>
								<s:option value="Customer Wise">Customer Wise</s:option>
								<s:option value="MenuHead Wise">MenuHead Wise</s:option>
								<s:option value="Group Wise">Group Wise</s:option>

							</s:select></td>
							
							<td><label>Trans Type</label></td>
						<td><s:select id="cmbReportType" path="strReportType"
								cssClass="BoxW124px">
								<s:option value="All">All</s:option>
								<s:option value="Dine In">Dine In</s:option>
								<s:option value="Direct Biller">Direct Biller</s:option>
								<s:option value="Home Delivery">Home Delivery</s:option>
								<s:option value="Take Away">Take Away</s:option>

							</s:select></td>
							
							<td><label>Order Type</label></td>
						<td><s:select id="cmbOrderMode" path="strViewType"
								cssClass="BoxW124px" items="${advOrderList}">
							</s:select>
							<s:select id="cmbStatus" path="strViewBy"
								cssClass="BoxW124px">
								<s:option value="Billed">Billed</s:option>
								<s:option value="Open">Open</s:option>
								<s:option value="Both">Both</s:option>
								<s:option value="Settled">Settled</s:option>
								
							</s:select></td>
								
					
					</tr>

				</table>
			</div>
			
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin:auto; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
				<table id="tblAdvOrderFlash" class="transTablex"
					style="width: 100%; text-align: center !important;">
				</table>
				
			</div>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 50px; margin:auto; overflow-x: scroll; overflow-y: scroll; width: 80%;">
				
	
				<table id="tblTotal" class="transTablex"
					style="width: 100%; text-align: center !important;">
				</table>
			</div>
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Execute" class="form_button"
				id="btnSubmit" /> <input type="submit" value="Export"
				class="form_button" id="submit" /> <input type="reset" value="Reset"
				class="form_button" id="btnReset" />

		</p>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>

	</s:form>

</body>
</html>