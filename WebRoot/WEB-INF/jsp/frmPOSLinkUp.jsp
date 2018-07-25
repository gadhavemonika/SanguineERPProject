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
	var rowNo;

	$(function() 
	{
	});

	function funSetData(code){

		switch(fieldName){
				
			case 'WSItemCode' : 
				funSetWSItemCode(code);
				break;
				
			case 'productmaster':
		    	funSetProduct(code);
		        break;
		}
	}


	function funSetWSItemCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadWSItemCode.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 

			},
			error : function(e){

			}
		});
	}

	
	function funSetProduct(code)
	{
		var searchUrl="";
		searchUrl=getContextPath()+"/loadProductMasterData.html?prodCode="+code;
		$.ajax
		({
	        type: "GET",
	        url: searchUrl,
		    dataType: "json",
		    success: function(response)
		    {
		    	document.getElementById("strWSItemCode."+rowNo).value=response.strProdCode;
		    	document.getElementById("strWSItemName."+rowNo).value=response.strProdName;
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
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funHelp1(row,transName)
	{
		fieldName=transName;
		rowNo=row;
	//	window.showModalDialog("searchform.html?formname="+transName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>POS Link Up</label>
	</div>

<br/>
<br/>

	<s:form name="POSLinkUp" method="POST" action="savePOSLinkUp.html">

		<table class="masterTable">
			<tr bgcolor="#72BEFC">
				<td>
					<label>POS Item Code</label>
				</td>
				
				<td>
					<label>POS Item Name</label>
				</td>
				
				<td>
					<label>WS Item Code</label>
				</td>
				
				<td>
					<label>WS Item Name</label>
				</td>				
			</tr>
		</table>

		<table class="masterTable" style="height: 100%; text-align: center; border: 1px solid black; font-size: 11px; font-weight: bold;">
			<tr>
				<td>
					<table  id="tblTermsAndCond">
						<c:forEach items="${POSLinkUpList.listPOSLinkUp}" var="tc"
							varStatus="status">
							<tr>
								<td align="left" width="10%"><input
									readonly="readonly" class="Box" type="text" size="20%"
									name="listPOSLinkUp[${status.index}].strPOSItemCode"
									value="${tc.strPOSItemCode}"></input></td>

								<td align="left" width="40%"><input type="text" size="50%"
									name="listPOSLinkUp[${status.index}].strPOSItemName"
									value="${tc.strPOSItemName}" class="longTextBox"></input></td>
								
								<td align="left" width="10%">
									<input type="text" size="20%" name="listPOSLinkUp[${status.index}].strWSItemCode"
									value="${tc.strWSItemCode}" class="searchTextBox" 
									name="listPOSLinkUp[${status.index}].strWSItemCode"
									id="strWSItemCode.${status.index}" 
									onclick="funHelp1(${status.index},'productmaster');" ></input></td>

								<td align="left" width="60%"><input type="text" size="40%"
									name="listPOSLinkUp[${status.index}].strWSItemName" 
									id="strWSItemName.${status.index}"  
									value="${tc.strWSItemName}" class="longTextBox"></input></td>
									
								<td><input type="button" class="deletebutton"
									value="Delete" onClick="Javacsript:funDeleteTCRow(this)"></td>
							</tr>
						</c:forEach>

					</table>
				</td>
			</tr>
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		<br />
		<br />

	</s:form>
</body>
</html>
