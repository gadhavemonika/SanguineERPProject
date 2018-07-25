<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName;
    
	
	function funValidateFields()
	{
		var flag=false;
		if($("#strFolioNo").val().trim().length==0)
		{
			alert("Please Select Folio No.");
		}
		else
		{
			flag=true;
			
			var fromDate=$("#dteFromDate").val();
			var toDate=$("#dteToDate").val();
			
			var fd=fromDate.split("-")[0];
			var fm=fromDate.split("-")[1];
			var fy=fromDate.split("-")[2];
			
			var td=toDate.split("-")[0];
			var tm=toDate.split("-")[1];
			var ty=toDate.split("-")[2];
			
			$("#dteFromDate").val(fy+"-"+fm+"-"+fd);
			$("#dteToDate").val(ty+"-"+tm+"-"+td);
			
			var folioNo=$("#strFolioNo").val();
			
			window.open(getContextPath()+"/rptFolioPrinting.html?fromDate="+fy+"-"+fm+"-"+fd+"&toDate="+ty+"-"+tm+"-"+td+"&folioNo="+folioNo+"");
		}
		
		
		return flag;
	}
	
	function funSetFolioNo(folioNo)
	{
		$("#strFolioNo").val(folioNo);
	}	
	
	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
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
			}
		}%>
		
		
		 var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		  var dte=pmsDate.split("-");
		  $("#txtPMSDate").val(dte[2]+"-"+dte[1]+"-"+dte[0]);
	});
	/**
		* Success Message After Saving Record
	**/
	
	/* set date values */
	function funSetDate(id,responseValue)
	{
		var id=id;
		var value=responseValue;
		var date=responseValue.split(" ")[0];
		
		var y=date.split("-")[0];
		var m=date.split("-")[1];
		var d=date.split("-")[2];
		
		$(id).val(d+"-"+m+"-"+y);
		
	}	
	
	//set date
	$(document).ready(function(){
		
		var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		$("#dteFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteFromDate").datepicker('setDate', pmsDate);	
		
		
		$("#dteToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteToDate").datepicker('setDate', pmsDate);	
	});
	
	function funSetData(code)
	{
		switch(fieldName)
		{
		case "folioNo":
			funSetFolioNo(code);
			break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Folio Printing</label>
	</div>

<br/>
<br/>

	<s:form name="FolioPrinting" method="GET" action="">

		<table class="masterTable">
		
			<tr>
				<td><label>Folio No.</label></td>
				<td colspan="3"><s:input id="strFolioNo" path="strFolioNo" readonly="true" cssClass="searchTextBox" ondblclick="funHelp('folioNo')"/></td>													
			</tr>
			<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
		</table>
					
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields()" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>						
	</s:form>
</body>
</html>
