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
	width: 60px
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

		$("#txtFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtFromDate").datepicker('setDate', 'today');

		$("#txtToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtToDate").datepicker('setDate', 'today');
		
		$("[type='reset']").click(function(){
			location.reload(true);
		});
		
		$("form").submit(function(event){
			var table = document.getElementById("tblDayWiseSales");
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
	
	
	
	var selectedRowIndex=0;
	
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

	
	function funFetchColNames() {
		
		var posName=$('#cmbPOSName').val();
		
		var gurl = getContextPath() + "/loadPhysicalStockFlash.html";
		$.ajax({
			type : "GET",
			data:{ fromDate:fDate,
					toDate:tDate,
					posName:posName,
				},
			url : gurl,
			dataType : "json",
			success : function(response) {
				
					
					//Add Sub Category Headers
					
					
					//Add Size Names And Headers
					
					$.each(response.List,function(i,item){
		            	
						funFillStockDetail(item.strPSPCode,item.dteDate,item.dblCompStk,item.dblPhyStk,item.dblVariance);
	            	});
		        	
				
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
	
	function funSetDate()
	{
		
		var searchurl=getContextPath()+"/getPOSDate.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	
			        	var date = new Date(response.POSDate);
			        var	dateTime=date.getDate()  + '-' + (date.getMonth() + 1)+ '-' +  date.getFullYear();
			        var posDate=dateTime.split(" ");
			        $("#txtFromDate").val(posDate[0]);
		        	$("#txtToDate").val(posDate[0]);
		        	
			        },
			        error: function(jqXHR, exception)
			        {
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
	function funGetSelectedRowIndex(obj)
	{
		 var index = obj.parentNode.parentNode.rowIndex;
		 var table = document.getElementById("tblPhysicalStock");
		  if(selectedRowIndex%2==0)
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#A3D0F7';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
			 }
			 else
			 {
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#C0E4FF';
				 selectedRowIndex=index;
				 row = table.rows[selectedRowIndex];
				 row.style.backgroundColor='#ffd966';
				 row.hilite = true;
	         }
		
		 
		 funOpenPhysicalStockSlip(selectedRowIndex);
		 
		
	}
	
 	function funFillStockDetail(strItemName,dteDate,dblCompStk,dblPhyStk,dblVariance)
	{
		var table = document.getElementById("tblPhysicalStock");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

	      row.insertCell(0).innerHTML= "<input type=\"hidden\" class=\"Box \" size=\"1%\" id=\"txtItemName."+(rowCount)+"\" value='"+strItemName+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(1).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"25%\" id=\"txtDate."+(rowCount)+"\" value='"+dteDate+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(2).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"25%\" id=\"txtCompStk."+(rowCount)+"\" value='"+dblCompStk+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(3).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"25%\" id=\"txtPhyStk."+(rowCount)+"\" value='"+dblPhyStk+"' onclick=\"funGetSelectedRowIndex(this)\"/>";
	      row.insertCell(4).innerHTML= "<input  readonly=\"readonly\" class=\"Box \" size=\"24%\" id=\"txtVariance."+(rowCount)+"\" value='"+dblVariance+"' onclick=\"funGetSelectedRowIndex(this)\"/>";

	}
 	

	function funDeleteTableAllRows()
	{
		$('#tblPhysicalStock tbody').empty();
	
		var table = document.getElementById("tblPhysicalStock");
		var rowCount1 = table.rows.length;
		if(rowCount1==0){
			return true;
		}else{
			return false;
		}
	}
	
 	function funOpenPhysicalStockSlip(selectedRowIndex)
 	{
 		var posName=$('#cmbPOSName').val();
 		var table = document.getElementById("tblPhysicalStock");
		var rowCount = table.rows.length;
		if(rowCount>0)
		{
			
			 var tableNo=table.rows[selectedRowIndex].cells[0].innerHTML;
			  var tableName=table.rows[selectedRowIndex].cells[1].innerHTML; 
			 
			  var codeArr = tableNo.split('value=');
				var code=codeArr[1].split('onclick=');
				var pspCode=code[0].substring(1, (code[0].length-2));
				
				var nameArr = tableName.split('value=');
				var name=nameArr[1].split('onclick=');
				var date=name[0].substring(1, (name[0].length-2));
				
				document.getElementById("txtPSPCode").value=pspCode;
				document.getElementById("txtDate").value=date;
				
				document.forms["POSPhysicalStockFlash"].submit();
		}
 	}
 	
	
</script>


</head>

<body onload="funSetDate()">
	<div id="formHeading">
		<label>POS Physical Stock Flash</label>
	</div>
	<br />
	<br />
	<s:form name="POSPhysicalStockFlash" method="POST"
		action="rptPhysicalStockFlash.html?saddr=${urlHits}"
		target="_blank">
		<div>
			<div>

				<table class="masterTable" style="margin: auto;">

					<tr>
						<td width="140px">POS Name</td>
						<td><s:select id="cmbPOSName" name="cmbPOSName"
							path="strPOSName"	cssClass="BoxW124px" items="${posList}">

							</s:select></td>
						<td><label>From Date</label></td>
						<td><s:input id="txtFromDate" required="required"
								path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}"
								cssClass="calenderTextBox" /></td>

						<td><label>To Date</label></td>
						<td><s:input id="txtToDate" required="required" path="toDate"
								pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox" /></td>
 		
 						
					</tr>
					

				</table>
			</div>
			<table border="1" class="myTable" style="width:80%;margin: auto;" >
										<thead>
										<tr>
											
											<th style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Date</th>
											<th style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Computer Stock</th>
											<th style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Physical Stock</th>
											<th style="width:25% border: #c0c0c0 1px solid; background: #78BEF9;">Variance</th>
										</tr>
										
										</thead>
			</table>
			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 500px; margin: auto; overflow-y: scroll; width: 80%;">
				
				<table id="tblPhysicalStock" class="transTablex"
					style="width: 100%; ">
				</table>
				
			</div>
			
		</div>
		<br />
		<br />
		<p align="center">
			<input type="button" value="Display" class="form_button"
				id="btnSubmit" /><input type="reset" value="Reset"
				class="form_button" id="btnReset" />

		</p>
		
		<s:input type="hidden" id="txtPSPCode" name="txtPSPCode" path="strPSPCode" />
		<s:input type="hidden" id="txtDate" name="txtDate" path="dteDate" />
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 40%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>

	</s:form>

</body>
</html>