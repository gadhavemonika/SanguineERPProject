<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
var fieldName;

$(document).ready(function(){
	
	$("#txtBrandCode").focus();
	
	$(document).ajaxStart(function() {
		$("#wait").css("display", "block");
	});
	$(document).ajaxComplete(function() {
		$("#wait").css("display", "none");
	});
		
	$('#txtBrandCode').blur(function () 
	{
	 var code=$('#txtBrandCode').val();
	 if (code.trim().length > 0 && code !="?" && code !="/"){							   
		 funSetBrandData(code);
	   }
	});
		
	$('#txtIntId').blur(function () 
	{
	 var code=$('#txtIntId').val();
	 if (code.trim() > 0 && code !="?" && code !="/"){							   
		 funSetOpeningData(code);
	   }
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
		alert("Opening Data Save successfully Id:-"+message);
	<%
	}}%>
	
	
	
	var warningmessage='';
	<%if (session.getAttribute("warning") != null) {
		            if(session.getAttribute("warningMessage") != null){%>
		            warningmessage='<%=session.getAttribute("warningMessage").toString()%>';
		            <%
		            session.removeAttribute("warningMessage");
		            }
					boolean test = ((Boolean) session.getAttribute("warning")).booleanValue();
					session.removeAttribute("warning");
					if (test) {
					%>	
		alert("Opening Data already Created of this Id:-"+warningmessage);
	<%
	}}%>
	
	
	$("form").submit(function(){	
		if($("#txtBrandCode").val().trim()=='' && $("#txtBrandCode").val().trim().length==0)
		{
			alert("Please Select proper Brand");
			return false;
		} 
	});
	
	$("[type='reset']").click(function(){	
		location.reload();
	});
	
});

function funSetData(code){
		
		switch(fieldName){
			case 'BrandCode' : 
				funSetBrandData(code);
				break;
			case 'brandOpeningCode' : 
				funSetOpeningData(code);
				break;
			case 'LicenceCode':
				funSetLicenceData(code);
				break;
		}
	}


	function funSetBrandData(code){
			$("#txtBtls").focus();
			var gurl=getContextPath()+"/loadExciseBrandMasterData.html?brandCode=";
			$.ajax({
		        type: "GET",				        
		        url: gurl+code,
		        dataType: "json",
		        success: function(response)
		        {
				if(response.strBrandCode=='Invalid Code')
		        	{
		        		alert("Invalid Brand Code Please Select Again");
		        		$("#txtBrandCode").val('');
		        		$("#txtBrandCode").focus();
		        	}
		        	else
		        	{
		        		$("#txtBrandCode").val(response.strBrandCode);
		        		$("#txtBrandName").text(response.strBrandName);
		        		$("#txtSizeName").text(response.strSizeName);
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


	function funSetOpeningData(code){
		$("#txtPegSize").focus();
		var licenceCode=$("#txtLicenceCode").val();
		var gurl=getContextPath()+"/loadExciseBrandOpeningMasterData.html?intId=";
		$.ajax({
	        type: "GET",				        
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {
			if(response.intId ==0)
	        	{
	        		alert("Invalid Code Please Select Again");
	        		$("#txtIntId").val('');
	        		$("#txtIntId").focus();
	        	}
	        	else
	        	{
        			$("#txtIntId").val(response.intId);
        			$("#txtBrandCode").val(response.strBrandCode);
	        		$("#txtBrandName").text(response.strBrandName);
	        		
	        		$("#txtBtls").val(response.intOpBtls);	
	        		$("#txtPeg").val(response.intOpPeg);
	        		$("#txtML").val(response.intOpML);
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
		fieldName = transactionName;
		var licenceCode=$("#txtLicenceCode").val();
		if(transactionName=='brandOpeningCode')
		{
			if($("#txtLicenceCode").val().trim().length == 0){
				
				alert("Please Select Licence ");
			}else{
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
				window.open("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		}}else{
	   
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}}
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
	

	function funOpenExportImport() {
		var transactionformName = "ExciseOpeningStockExportImport";
	//	response = window.showModalDialog("frmExciseExcelExportImport.html?formname="+ transactionformName,"","dialogHeight:500px;dialogWidth:500px;");
		response = window.open("frmExciseExcelExportImport.html?formname="+ transactionformName,"","dialogHeight:500px;dialogWidth:500px;");
		if (null != response) {
			alert("Data import Successfully");
		}
	}

	

		
	
</script>

</head>
<body>
	<div id="formHeading">
		<label>Opening Stock</label>
	</div>

	<br />
	<br />

	<s:form name="BrandOpeningMaster" method="POST" action="saveExciseBrandOpeningMaster.html?saddr=${urlHits}" >

		<table class="masterTable">
		<tr>
			    <th align="right" colspan="6">
			    	<a onclick="funOpenExportImport()" >Export/Import</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    </th>
<!-- 			    <td colspan="5"></td> -->
			</tr><tr>
				<td>
					<label>Opening Code</label>
				</td>
				<td>
					<s:input type="text" id="txtIntId" path="intId" cssClass="searchTextBox numeric positive-integer " ondblclick="funHelp('brandOpeningCode')" />
				</td>
				<td>
					<label>Licence Code</label>
				</td>
				<td>
					<s:input id="txtLicenceCode" ondblclick="funHelp('LicenceCode')" type="text" path="strLicenceCode" cssClass="searchTextBox"  />
				<s:label id="licenceNo" path="strLicenceNo" >${LicenceNo}</s:label></td>
				<td colspan="2"></td>
					</tr>
				
		
			<tr>
				<td>
					<label>Brand Code</label>
				</td>
				<td>
					<s:input type="text" id="txtBrandCode" path="strBrandCode" cssClass="searchTextBox" ondblclick="funHelp('BrandCode')" required="true" />
				</td>
				<td>
					<label>Brand Name</label>
				</td>
				<td colspan="2"><label id="txtBrandName" ></label></td>
				<td><label id="txtSizeName"></label></td>
			</tr>
			<tr>
				<td>
					<label>Opening Btl.s</label>
				</td>
				<td>
					<s:input type="text" id="txtBtls" path="intOpBtls" cssClass="BoxW124px numeric positive-integer " />
				</td>
				<td>
					<label>Opening Peg</label>
				</td>
				<td>
					<s:input type="text" id="txtPeg" cssClass="BoxW124px numeric positive-integer " path="intOpPeg" />
				</td>
				<td>
					<label>Opening ML</label>
				</td>
				<td>
					<s:input type="text" id="txtML" path="intOpML" cssClass="BoxW124px numeric positive-integer " />
				</td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button" />
		</p>
		
		<div id="wait" style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		
	</s:form>
	<script type="text/javascript">
  	funApplyNumberValidation();
	</script>
</body>
</html>