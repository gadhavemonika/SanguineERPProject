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

	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}

	function funSetData(code){

		switch(fieldName){

			case 'SizeCode' : 
				funSetSizeData(code);
				break;
		}
	}

	function funSetSizeData(code){
		$("#txtSizeName").focus();
		var gurl=getContextPath()+"/loadExciseSizeMasterData.html?sizeCode=";
		$.ajax({
	        type: "GET",				        
	        url: gurl+code,
	        dataType: "json",
	        success: function(response){
				if(response.strSizeCode=='Invalid Size Code')
	        	{
	        		alert("Invalid Size Code Please Select Again");
	        		$("#txtSizeCode").val('');
	        		$("#txtSizeName").focus();
	        	}
	        	else
	        	{
	        		$("#txtSizeCode").val(response.strSizeCode);
	        		$("#txtSizeName").val(response.strSizeName);
	        		$("#txtQty").val(response.intQty);
	        		$("#txtUOM").val(response.strUOM);
	        		$("#txtNarration").val(response.strNarration);
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

	
	function funValidateUOM() {
	            var selValue = $("#txtUOM").val();
	            if (selValue.length > 0) {
	            	 return true; 
	            } else{
	            	alert("Please select Proper UOM");
	            	return false;
	            }         
	}
	
	$(function(){
		
		$("#txtSizeName").focus();
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
			alert("Size Data Save successfully\n\n"+message);
		<%
		}}%>
		
		$('#txtSizeCode').blur(function () 
				{
				 var code=$('#txtSizeCode').val();
				 if (code.trim().length > 0 && code !="?" && code !="/"){							   
					 funSetSizeData(code);
				   }
				});
	});
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit, negative: false });
	}
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Size Master</label>
	</div>

<br/>
<br/>

	<s:form name="SizeMaster" method="POST" action="saveExciseSizeMaster.html?saddr=${urlHits}" onsubmit="return funValidateUOM()">

		<table class="masterTable">
			<tr>
				<td width="120px">
					<label>Size Code</label>
				</td>
				<td width="120px" colspan="3">
					<s:input type="text" id="txtSizeCode" path="strSizeCode" cssClass="searchTextBox" ondblclick="funHelp('SizeCode');"/>
				</td>
				
			</tr>
			<tr>
				<td width="120px">
					<label>Size Name</label>
				</td>
				<td colspan="3">
					<s:input type="text" id="txtSizeName" path="strSizeName" cssClass="longTextBox" required="true" cssStyle="width: 50%;" />
				</td>
			</tr>
			<tr>
				<td width="120px">
					<label>Qty</label>
				</td>
				<td width="120px">
					<s:input type="text" id="txtQty" path="intQty" cssClass="BoxW124px numeric positive-integer " required="true"/>
				</td>
				<td width="100px">
					<label>UOM</label>
				</td>
				<td>
					<s:select id="txtUOM" path="strUOM" cssClass="BoxW124px" >
<%-- 					 <s:option value="" label="Select UOM" /> --%>
					 <s:options items="${uomList}"/>
					 </s:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>Narrator</label>
				</td>
				<td colspan="3">
					<s:input type="text" id="txtNarration" path="strNarration" cssClass="longTextBox" cssStyle="width: 80%;" />
				</td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="reset" value="Reset" class="form_button" />
		</p>

	</s:form>
	<script type="text/javascript">
  		funApplyNumberValidation();
	</script>
</body>
</html>
