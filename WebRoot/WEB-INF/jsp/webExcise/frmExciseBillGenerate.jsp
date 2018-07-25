<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">

	var fDate,tDate;
	var listRow=0;
	
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

		$("#btnSubmit").click(function(event) {
			var fromDate = $("#txtFromDate").val();
			var toDate = $("#txtToDate").val();
             var licenceCode=$("#txtLicenceCode").val();
			if (fromDate.trim() == '' && fromDate.trim().length == 0) {
				alert("Please Enter From Date");
				return false;
			}
			if (toDate.trim() == '' && toDate.trim().length == 0) {
				alert("Please Enter To Date");
				return false;
			}
			if (licenceCode.trim() == '' && licenceCode.trim().length == 0) {
				alert("Please Select Licence");
				return false;
			}
			if(funDeleteTableAllRows()){
				if(CalculateDateDiff(fromDate,toDate)){
					fDate=fromDate;
					tDate=toDate;
					funFetchCol();
				}
			}
		});
		
		
		$("form").submit(function(event) {
			
			var table = document.getElementById("tblExciseSaleData");
			var rowCount1 = table.rows.length;
			if(rowCount1 >1){
				return true;
			}else{
				alert("No Sale Data");
				return false;
			}
		});

	});
	
	function funDeleteTableAllRows()
	{

	 $("#tblExciseSaleData").find("tr:gt(0)").remove();		
		var table = document.getElementById("tblExciseSaleData");
		var rowCount1 = table.rows.length;
		if(rowCount1==1){
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

	
	function funFetchCol() {
		var licenceCode=$("#txtLicenceCode").val();
		var gurl = getContextPath() + "/loadSaleDataColumns.html";
		$.ajax({
			type : "GET",
			data:"fromDate="+fDate+"&toDate="+tDate+"&licenceCode="+licenceCode,
			url : gurl,
			dataType : "json",
			success : function(response) {
				if (response == '' || response.size > 0) {
					alert("Data Not Found");
				} else {
					
					 $.each(response, function(i,item)
					{
						funAddDataRows(response[i]);
					});
					
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
	
	function funAddDataRows(rowData) 
	{
		var table = document.getElementById("tblExciseSaleData");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML= "<input type=\"text\" class=\"Box\" readonly=\"readonly\" id=\"intId."+(rowCount)+"\" name=\"saleHdList["+(rowCount)+"].intId\" value="+rowData[0]+" />";		    
	    row.insertCell(1).innerHTML= "<input class=\"Box\" class=\"Box\" readonly=\"readonly\" id=\"dteBillDate."+(rowCount)+"\" name=\"saleHdList["+(rowCount)+"].dteBillDate\" value='"+rowData[1]+"' />";
	    row.insertCell(2).innerHTML= "<input type=\"text\" class=\"Box\" readonly=\"readonly\" id=\"strUserCreated."+(rowCount)+"\" name=\"saleHdList["+(rowCount)+"].strUserCreated\" value="+rowData[2]+" />";
	    row.insertCell(3).innerHTML= "<input type=\"text\" class=\"Box\" readonly=\"readonly\" id=\"dteDateCreated."+(rowCount)+"\" name=\"saleHdList["+(rowCount)+"].dteDateCreated\" value="+rowData[3]+" />";
	    row.insertCell(4).innerHTML= "<input type=\"text\" class=\"Box\" readonly=\"readonly\" id=\"strLicenceCode."+(rowCount)+"\" name=\"saleHdList["+(rowCount)+"].strLicenceCode\" value="+rowData[4]+" />";
	    row.insertCell(5).innerHTML= "<input type=\"text\" class=\"Box\" readonly=\"readonly\" id=\"strSourceEntry."+(rowCount)+"\" name=\"saleHdList["+(rowCount)+"].strSourceEntry\" value="+rowData[5]+" />";
	    row.insertCell(6).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
	}
	
	
	function funDeleteRow(obj) 
	{
		var index = $(obj).closest('tr').index();
		var icode = $(obj).closest("tr").find("input[type=text]").val();
	    var table = document.getElementById("tblExciseSaleData");
	    table.deleteRow(index);
	}
	function funSetData(code){
		
		switch(fieldName){
			
			case 'LicenceCode':
				$("#txtLicenceCode").val(code);
				break;
		}
	}
	
	function funSetLicenceData(code) {
		$("#txtTPCode").focus();
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
		        		$("#txtLicenceCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtLicenceCode").val(response.strLicenceCode);
		        		$("#licenceNo").text(response.strLicenceNo);
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
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}
	

	function funValidateFields() 
	{
		
		if(funGetMonthEnd(document.all("txtLicenceCode").value,document.all("txtToDate").value)!=true)
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
				+ "/checkExciseMonthEnd.html?licenceCode=" + licenceCode+"&date="+date+"&formName=Bill Generation";

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
	<label>Bill Generation</label>
	</div>

<br/>
<br/>

	<s:form name="BillGenerate" method="POST" action="exciseBillGenerate.html?saddr=${urlHits}" >
	<div>
	<table class="transTable">
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
					<label>Licence Code</label>
				</td>
				<td>
					
				<s:input id="txtLicenceCode" ondblclick="funHelp('LicenceCode')" type="text" path="strlicenceCode" cssClass="searchTextBox"  />
				</td>
								
			</tr>
		</table>		
		<br />
		<br />
		<!-- <div class="dynamicTableContainer" >
			<table id="tblExciseSaleData" style="width:100%;border:#0F0;table-layout:fixed;overflow:scroll;text-align:center" class="transTablex col7-center">
				<tr>
					<th>Sale Id</th>
					<th>Sale Date</th>
					<th>User Created</th>
					<th>Created Date</th>
					<th>Licence Code</th>
					<th>Source Entry</th>
					<th>Delete</th>
				</tr>
			</table>
		</div> -->
		
		<div class="dynamicTableContainer" style="border:#0F0;overflow:scroll;text-align:center" >
			<table id="tblExciseSaleData" style="width:100%;border:#0F0;table-layout:fixed;overflow:scroll;text-align:center" class="transTablex col7-center">
				<tr>
					<th>Sale Id</th>
					<th>Sale Date</th>
					<th>User Created</th>
					<th>Created Date</th>
					<th>Licence Code</th>
					<th>Source Entry</th>
					<th>Delete</th>
				</tr>
			</table>
		</div>
		
		<br />
		<br />
		<p align="center">
			<input type="button" value="Sale Data" class="form_button" id="btnSubmit"/>
			<input type="submit" value="Generate Bill" class="form_button" id="submit"  onclick="return funValidateFields();"/>
			<input type="reset" value="Reset" class="form_button" id="btnReset"/>
			
		</p>
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
