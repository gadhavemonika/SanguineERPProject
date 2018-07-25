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

	$(function() 
	{
		
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
				<% session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
						
			if (test)
			{%>	
				alert("Data Save successfully\n\n"+message);
			<%}
		}%>
	
	});

	function funSetData(code){

		switch(fieldName){
		

		case 'WCCommitteeMaster' :
			funSetCommitteeMaster(code);
			break;
			
		case 'WCmemProfile' :
			funMemberData(code);
			break;

		}
	}

	 function funMemberData(code)
		{
			var searchurl=getContextPath()+"/loadWebClubMemberProfileData.html?primaryCode="+code;
			//alert(searchurl);
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        success: function(response)
				        {
				        	if(response.strMemberCode=='Invalid Code')
				        	{
				        		alert("Invalid Member Code");
				        		$("#txtMemberCode").val('');
				        	}
				        	else
				        	{  
				        		funRemoveAllRows();
				        		
					        	$("#txtMemberCode").val(response[0].strMemberCode);
					        	
					        	$("#txtMemberName").val(response[0].strFullName);							        							        						        	
					        	
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
	 
	function funRemoveAllRows()
	{
		 var table = document.getElementById("tblMemDet");
		 var rowCount = table.rows.length;			   
		//alert("rowCount\t"+rowCount);
		for(var i=rowCount-1;i>=0;i--)
		{
			table.deleteRow(i);						
		} 
	 
	} 
	 
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
	
	//Delete particular row when click on delete button on product 
	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblMemDet");
	    table.deleteRow(index);
	    funGetTotal();
	}
	
	funRemoveAllRows()
	{
		
	}

	
	function btnAdd_onclick()
	{
		var memcode = $("#txtMemberCode").val();
		var memName = $("#txtMemberName").val();
		var role = $("#cmbMemberRole").val();
		
		var table = document.getElementById("tblMemDet");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		
		  row.insertCell(0).innerHTML= "<input name=\"listCommitteeMasterDtl["+(rowCount)+"].strMemberCode\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMemberCode."+(rowCount)+"\" value='"+memcode+"'>";
		  row.insertCell(1).innerHTML= "<input name=\"listCommitteeMasterDtl["+(rowCount)+"].strMemberName\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtMemberName."+(rowCount)+"\" value='"+memName+"'>";
		   
		  row.insertCell(2).innerHTML= "<input name=\"listCommitteeMasterDtl["+(rowCount)+"].strRoleName\" readonly=\"readonly\" class=\"Box \" size=\"15%\" id=\"txtRole."+(rowCount)+"\" value='"+role+"'>";
		  row.insertCell(3).innerHTML= '<input type="button" value = "Delete" class="deletebutton" onClick="Javacsript:funDeleteRow(this)">'; 
		   
		  funResetFileds();
		
	}
	
	
	function funResetFileds()
	{
		 $("#txtMemberCode").val();
		 $("#txtMemberName").val();
	}
	
	
	
	
	
	
	
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Committee Master</label>
	</div>

<br/>
<br/>

	<s:form name="WebClubCommitteeMaster" method="POST" action="saveWebClubCommitteeMaster.html">

		<table class="masterTable">
		
		<tr>
			 
			    <td width="180px"><label>Committee Code</label></td>   
				<td width="55px"><s:input id="txtCommitteeCode" path="strCommitteeCode" ondblclick="funHelp('')"
									style="width: 118px" cssClass="searchTextBox" type="text"></s:input></td>
				
				<td><s:input id="textCommitteeName" path="strCommitteeName" required=""
				            cssStyle="width:53% ;" cssClass="longTextBox" type="text" ></s:input></td>
		</tr>
		<tr>
					<td><label>Type</label></td>
					<td colspan="2"><s:select id="cmbType" name="cmbType" path="strType" cssClass="BoxW124px" >
					 <option value="Main">Main</option>
		 				 <option value="Sub">Sub</option>
				 </s:select></td>
			</tr>
			
		<tr>
				<td width="16%">Member Code</td>
				<td width="17%"><input id="txtMemberCode" Class="searchTextBox" ondblclick="funHelp('WCmemProfile')"/></td>
				<td><input type="text" id="txtMemberName" 
						name="txtMemberName" required=""
				            Style="width:53% ;" Class="longTextBox" type="text" ></input></td>
		</tr>	
		
		<tr>
					<td><label>Member Role</label></td>
					<td colspan="2"><s:select id="cmbMemberRole" items="${listRoleDesc}" path="strRoleDesc" name="cmbMemberRole"  cssClass="BoxW124px" >
				 </s:select></td>
		</tr>
			
				
		<tr>
		<td colspan="2"></td>
			<td ><input type="Button" value="Add" onclick="return btnAdd_onclick()" class="smallButton" /></td>
		</tr>
		
		</table>

			<div class="dynamicTableContainer" style="height: 250px;">
			<table style="height: 20px; border: #0F0;width: 100%;font-size:11px;
			font-weight: bold;">
				<tr bgcolor="#72BEFC">
					<td width="6%">Member Code</td>					
					<td width="22%">Member Name</td>	
					<td width="20%">Role</td>
					<td width="5%">Delete</td>
				</tr>
			</table>
			<div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblMemDet"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col11-center">
					<tbody>
					<col style="width:6%">					
					<col style="width:22%">
					<col style="width:22%">
					<col  style="width:2%">
					</tbody>
				</table>
			</div>
		</div>


		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
