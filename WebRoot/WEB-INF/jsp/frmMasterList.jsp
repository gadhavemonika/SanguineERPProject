<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javascript">
	var fDate, tDate,ModuleName;
	var inPeg;
	$(document).ready(function() {
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
		$("#txtFromDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtFromDate").datepicker('setDate', Dat);

		$("#txtToDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtToDate").datepicker('setDate', 'today');

		
		$("[type='reset']").click(function(){
			location.reload(true);
		});

		$("#btnSubmit").click(function(event) {
			 fromDate = $("#txtFromDate").val();
			toDate = $("#txtToDate").val();
			 ModuleName=$("#cmbModule").val();
			if (fromDate.trim() == '' && fromDate.trim().length == 0) {
				alert("Please Enter From Date");
				return false;
			}
			if (toDate.trim() == '' && toDate.trim().length == 0) {
				alert("Please Enter To Date");
				return false;
			}

			var masterName = $("#txtMasterName").val();

			if (masterName.trim() == '' && masterName.trim().length == 0) {
				alert("Please Select Master");
				return false;
			}

		
			if (CalculateDateDiff(fromDate, toDate)) {
					fDate = fromDate;
					tDate = toDate;
					funAdd();
				}
			
		});

	});

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
	

	
</script>
<body>
	<s:form name="frmMasterList" method="GET" action="rptMasterList.html?saddr=${urlHits}">
		<div >
		
			<div id="formHeading">
		<label >Master List</label>
	</div><br/>
	<br/>
	<br/>
			<table class="masterTable">
				<tr>
					<td><label>From Date</label></td>
					<td><s:input type="text" id="txtFromDate"
							class="calenderTextBox" path="dtFromDate" required="required" />
					</td>
				</tr>
				<tr>
					<td><label>To Date</label></td>
					<td><s:input type="text" id="txtToDate"
							class="calenderTextBox" path="dtToDate" required="required" /></td>
				</tr>
				<tr>
					<td><label>Module</label></td>
					
				<td><s:select id="cmbModule" cssClass="combo1" cssStyle="width:125px;height:20px;overflow:scroll" path="strAgainst">
					<option value="tblattributemaster">Attribute Master</option>
					<option value="tbludcategory">UD Report Category Master</option>
					
					<option value="tblcharacteristics">Characteristics Master</option>
					<option value="tblgroupmaster">Group Master</option>
					<option value="tbllocationmaster">Location Master</option>
					<option value="tblpropertymaster">Property Master</option>
					<option value="tblreasonmaster">Reason Master</option>
					<option value="tblsubgroupmaster">Sub Group Master</option>
					<option value="tblprodsuppmaster">Supplier Master</option>
					<option value="tbltaxhd">Tax Master</option>
					<option value="tblprocessmaster">Process Master</option>
					<option value="tbluserhd">User Master</option>
					<option value="tbltcmaster">TC Master</option>
					<option value="tbluommaster">UOM Master</option>
					<!-- <option value=""></option> -->
				
			
					</s:select></td>			
				</tr>
			</table>
		</div>
		<br />
		<br />
		<br />
		<br />
		<br />
	
		<p align="center">
			 <input type="submit" value="Submit"
				class="form_button" id="btnSubmit" /> <input type="reset" value="Reset"
				class="form_button" />
		</p>
	</s:form>
</body>
</html>