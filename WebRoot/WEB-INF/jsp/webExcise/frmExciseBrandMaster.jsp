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
var subCategoryPegSize;

$(document).ready(function(){
	
	$(document).ajaxStart(function() {
		$("#wait").css("display", "block");
	});
	$(document).ajaxComplete(function() {
		$("#wait").css("display", "none");
	});
	
	$('#txtBrandName').focus();
	$('#txtBrandCode').blur(function () 
	{
	 var code=$('#txtBrandCode').val();
	 if (code.trim().length > 0 && code !="?" && code !="/"){							   
		 funSetBrandData(code);
	   }
	});
		
	$('#txtSubCategoryCode').blur(function () 
	{
	 var code=$('#txtSubCategoryCode').val();
	 if (code.trim().length > 0 && code !="?" && code !="/"){							   
		 funSetSubCategoryCode(code);
	   }
	});
	
	$('#txtSizeCode').blur(function () 
	{
	 var code=$('#txtSizeCode').val();
	 if (code.trim().length > 0 && code !="?" && code !="/"){							   
		 funSetSizeCode(code);
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
		alert("Brand Data Save successfully\n\n"+message);
	<%
	}}%>

	
	$("form").submit(function(){	
		if($("#txtSizeCode").val().trim()=='' && $("#txtSizeCode").val().trim().length==0)
		{
			alert("Please Select proper Size Code");
			return false;
		} 
		
		var pegSize=0;
			pegSize = parseInt($("#txtPegSize").val());
		var brandSize= 0;
			brandSize = parseInt($("#intbrandSize").val());
		if(brandSize<pegSize){
			alert("Peg Size Not Greater Than Brand Size");
			return false;
		}
		
		var MRP= $("#txtRate").val();
		var PegPrice= $("#txtPegPrice").val();
		if(MRP<PegPrice){
			alert("Peg Price Greater Than MRP");
			return false;
		}
		
	});
	
});

function funSetData(code){
		
		switch(fieldName){
			case 'BrandCode' : 
				funSetBrandData(code);
				break;
			case 'SubCategoryCode' : 
				funSetSubCategoryCode(code);
				break;
			case 'SizeCode' : 
				funSetSizeCode(code);
				break;
		}
	}


	function funSetBrandData(code){

		$("#txtBrandName").focus();
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
		        		$("#txtBrandName").val(response.strBrandName);
		        		$("#txtShortName").val(response.strShortName);
		        		$("#txtBrandNo").val(response.strBrandNo);
		        		$("#txtCategoryCode").val(response.strCategoryCode);
		        		$("#txtSubCategoryCode").val(response.strSubCategoryCode);
		        		$("#txtSizeCode").val(response.strSizeCode);
		        		$("#txtStrength").val(response.dblStrength);	
		        		$("#txtPegSize").val(response.intPegSize);
		        		$("#txtRate").val(response.dblRate);
// 		        		$("#txtPegPrice").val(response.dblPegPrice);
		        		$("#txtSizeName").text(response.strSizeName);
		        		$("#intbrandSize").val(response.intSizeQty);
		        		$("#txtCategoryName").text(response.strCategoryName);
		        		$("#txtSubCategoryName").text(response.strSubCategoryName);
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


	function funSetSubCategoryCode(code){
		$("#txtSizeCode").focus();
		var gurl=getContextPath()+"/loadExciseSubCategoryMasterData.html?subcategoryCode=";
		$.ajax({
	        type: "GET",				        
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {
			if(response.strSubCategoryCode=='Invalid Code')
	        	{
	        		alert("Invalid Sub Category Code Please Select Again");
	        		$("#txtSubCategoryCode").val('');
	        		$("#txtSubCategoryCode").focus();
	        	}
	        	else
	        	{
	        		$("#txtSubCategoryCode").val(response.strSubCategoryCode);
	        		$("#txtSubCategoryName").text(response.strSubCategoryName);
	        		$("#txtCategoryName").text(response.strCategoryName);	
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
	
	function funSetSizeCode(code){
		$("#txtPegSize").focus();
		var gurl=getContextPath()+"/loadExciseSizeMasterData.html?sizeCode=";
		$.ajax({
	        type: "GET",				        
	        url: gurl+code,
	        dataType: "json",
	        success: function(response)
	        {
			if(response.setStrSizeCode=='Invalid Code')
	        	{
	        		alert("Invalid SizeCode Please Select Again");
	        		$("#txtSizeCode").val('');
	        		$("#txtSizeCode").focus();
	        	}
	        	else
	        	{
	        		$("#txtSizeCode").val(response.strSizeCode);
	        		$("#txtSizeName").text(response.strSizeName);
	        		$("#intbrandSize").val(response.intQty);
	        		
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


	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}

	function funOpenExport() {
		var transactionformName = "ExciseBrandPriceExcelList";
	//	response = window.showModalDialog("frmExciseExcelExportImport.html?formname="+ transactionformName,"","dialogHeight:500px;dialogWidth:500px;");
		response = window.open("frmExciseExcelExportImport.html?formname="+ transactionformName,"","dialogHeight:500px;dialogWidth:500px;");
	}
	
</script>

</head>
<body>
	<div id="formHeading">
		<label>Brand Master</label>
	</div>

	<br />
	<br />

	<s:form name="BrandMaster" method="POST" action="saveExciseBrandMaster.html?saddr=${urlHits}" >

		<table class="masterTable">
			<tr>
			    <th align="right" colspan="5">
			    	<a onclick="funOpenExport()" >Brand-Price List</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    </th>
			</tr>
			<tr>
				<td><label>Brand Code</label></td>
				<td><s:input type="text" id="txtBrandCode" path="strBrandCode" cssClass="searchTextBox" ondblclick="funHelp('BrandCode')" /></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><label>Brand Name</label></td>
				<td colspan="2"><s:input type="text" id="txtBrandName"
						path="strBrandName" cssClass="longTextBox" required="true" /></td>
				<td>
					<label>Short Name</label>
				</td>
				<td>
					<s:input type="text" maxlength="20" id="txtShortName" path="strShortName" cssClass="BoxW124px" />
				</td>
			</tr>
			<tr>
				<td><label>Sub Category Code</label></td>
				<td><s:input type="text" id="txtSubCategoryCode" path="strSubCategoryCode" cssClass="searchTextBox"	ondblclick="funHelp('SubCategoryCode');" required="true" /></td>
				<td><label id="txtSubCategoryName"></label></td>
					<td><label>Category Name</label></td>
				<td><label id="txtCategoryName" ></label></td>
							
			</tr>
			<tr>
				<td><label>Size</label></td>
				<td>
				<s:input type="text" id="txtSizeCode" path="strSizeCode" cssClass="searchTextBox" ondblclick="funHelp('SizeCode');" required="true" />
				</td>
				<td colspan="2"><label id="txtSizeName"></label></td>
				<td><input type="hidden" id="intbrandSize"></td>
			</tr>
			 <tr>
			 	<td>
					<label>Peg Size in ML</label>
				</td>
				<td>
					<s:input type="text" id="txtPegSize" cssClass="BoxW124px numeric positive-integer " path="intPegSize" required="true"/>
				</td>
				
				<td>
					<label>Strength</label>
				</td>
				<td>
					<s:input type="text" id="txtStrength" path="dblStrength" cssClass="BoxW124px numeric decimal-places" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>Rate Of Peg / Bottle</label>
				</td>
				<td>
					<s:input type="text" id="txtRate" path="dblRate"	cssClass="BoxW124px numeric decimal-places" />
				</td>
				<td>
<!-- 					<label>Peg Price</label> -->
				</td>
				<td>
<%-- 					<s:input type="text" id="txtPegPrice" path="dblPegPrice" cssClass="BoxW124px numeric decimal-places" /> --%>
				</td>
				<td></td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" id ="submit" class="form_button" />
			<input type="reset" value="Reset" id="reset" class="form_button" />
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