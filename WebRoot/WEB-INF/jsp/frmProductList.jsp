<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    <script type="text/javascript">
    /**
	 * fill subgroup combox when user select Group
	 */
    function funFillCombo(code) {
		var searchUrl = getContextPath() + "/loadSubGroupCombo.html?code="
				+ code;
		//alert(searchUrl);
		if(code=="ALL")
			{
				var html = '<option value="ALL">ALL</option>';
				html += '</option>';
				$('#strSGCode').html(html);
			}
		else{
		$.ajax({
			type : "GET",
			url : searchUrl,
			dataType : "json",
			success : function(response) {
				var html = '<option value="ALL">ALL</option>';
				html += '</option>';
				$.each(response, function(key, value) {
					html += '<option value="' + key + '">' + value
							+ '</option>';
				});
				html += '</option>';
				$('#strSGCode').html(html);
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
	}
    </script>
  </head>
  
	<body >
	<div id="formHeading">
		<label>Product List</label>
	</div>
	<br />
	<br />
		<s:form name="prodList" method="GET" action="rptProductList.html" target="_blank">
			<table class="masterTable">
	<tr><th colspan="4"></th></tr>
				<tr>
					<td><label>Product Type</label></td>
					<td colspan="4">
						<s:select id="cmbProdType" path="strProdType" cssClass="BoxW124px" items="${typeList}">
<%-- 				    		<s:option value="ALL">ALL</s:option> --%>
				    	</s:select>
					</td>
					
				</tr>
				<tr>
					<td><label>Group</label></td>
				<td><s:select path="strGCode" items="${listgroup}"
						id="strGCode" onchange="funFillCombo(this.value);" cssClass="BoxW124px" cssStyle="width:auto"> </s:select></td>
				<td><label>SubGroup</label></td>
				<td colspan="4"> <s:select path="strSGCode" items="${listsubGroup}" cssStyle="width:auto"
						id="strSGCode" cssClass="BoxW124px">
					</s:select></td>
				</tr>	
				<tr>
					<td><label>Report Type</label></td>
					<td colspan="4">
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="CSV">CSV</s:option>
				    	</s:select>
					</td>
				</tr>
				
				<tr>
				<td colspan="4"></td>
					<!-- <td><input type="submit" value="Submit" /></td>
					<td><input type="reset" value="Reset" onclick="funResetFields()"/></td>	 -->				
				</tr>
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit"  class="form_button"/>
				 <input type="button" value="Reset" class="form_button " />
			</p>
			
		</s:form>
	</body>
</html>