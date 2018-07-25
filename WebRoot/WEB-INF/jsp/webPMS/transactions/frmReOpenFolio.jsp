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
			
			var folioNo=$("#strFolioNo").val();
			
			//window.open(getContextPath()+"/rptFolioPrinting.html?fromDate="+fy+"-"+fm+"-"+fd+"&toDate="+ty+"-"+tm+"-"+td+"&folioNo="+folioNo+"");
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
	$(document).ready(function()
	{
		var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
	});
	
	function funSetData(code)
	{
		switch(fieldName)
		{
		case "folioNoForReOpen":
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
	<label>ReOpen Folio</label>
	</div>

<br/>
<br/>

	<s:form name="ReOpeningFolio" method="POST" action="saveFolio.html">

		<table class="masterTable">
		
			<tr>
				<td><label>Folio No.</label></td>
				<td colspan="3"><s:input id="strFolioNo" path="strFolioNo" readonly="true" cssClass="searchTextBox" ondblclick="funHelp('folioNoForReOpen')"/></td>													
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
