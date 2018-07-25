<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">

 


	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		$("#txtRecipeCode").focus();
    }
	
	function funSetData(code)
	{			
		switch (fieldName) 
		{			   
		   case 'bomcode':
		    	funSetBom(code);
		        break;
		        
		   case 'productmaster':
		    	funSetProduct(code);
		        break;
		        
		   case 'bomcodeslip':
		    	funSetBom(code);
		        break;
		}
	}
	function funSetProduct(code)
	{
		searchUrl=getContextPath()+"/loadProductData.html?prodCode="+code;
		//alert(searchUrl);
		$.ajax({
	        type: "GET",
	        url: searchUrl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strParentCode!="Invalid Product Code")
	        		{
			        
		        		$("#lblParentName").text(response.strParentName);
		        		
	        		}
	        	else
	        		{
	        			alert("Invalid Parent Product Code");
	        			
	        			return false;
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
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			
		}
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetBom(code)
		{
			$("#txtRecipeCode").val(code);
			var searchurl=getContextPath()+"/loadLossData.html?recipeCode="+code;
			//alert(searchurl);
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strBOMCode=='Invalid Code')
				        	{
				        		alert("Invalid Recipe Code");
				        		$("#txtRecipeCode").val('');
				        	}
				        	else
				        	{
				        		funSetProduct(response.strParentCode);
					        	
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
		
		
			
			/**
			* On Blur Event on TextField
			**/
			$('#txtRecipeCode').blur(function() 
			{
					var code = $('#txtRecipeCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/")
					{				
						funSetData(code);							
					}
			});
			
		
</script>
<body>
	<div id="formHeading">
		<label>Loss Calculation report </label>
	</div>

	<br />
	<br />

	<s:form name="frmLossCalculationReport" method="POST" action="rptLossCalculationReport.html?saddr=${urlHits}">
		<div>
			<table class="masterTable">

			
			<tr>
				<td width="140px">Recipe Code</td>
				<td><s:input id="txtRecipeCode" path="strDocCode"
						cssClass="searchTextBox" ondblclick="funHelp('bomcodeslip')" /></td>
				<td><label id=lblParentName> All Recipe </label>	</td>	
						
			
			</tr>
			<tr>
			
			<td><label>Report Type</label></td>
				<td ><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						<s:option value="HTML">HTML</s:option>
						<s:option value="CSV">CSV</s:option>
					</s:select></td>
			
			</tr>
		</table>
		</div>
	
	<p align="center">
		<br /> <br /> <input type="submit" value="Submit" class="form_button" id="submit" /> 
	           
	            <input type="reset" value="Reset" class="form_button" id="btnReset" />

	</p>
</s:form>

</body>
</html>