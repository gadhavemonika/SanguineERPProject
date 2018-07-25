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
	var rowNo=0;

	$(function() 
	{
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
		alert("\n\n"+message);
		<%
		}}%>

		$("#reset").click(function(){
			location.reload();
		});
		

		$("form").submit(function(event){
			var table = document.getElementById("tblPOSExciseLinkUp");
			var rowCount = table.rows.length;
					
			if (rowCount < 2 ){
					alert("Data Not Available");
					return false;
				}
		});
	
	});

	function funSetData(code){

		switch(fieldName){
		
			case 'BrandCode':
		    	funSetBrandData(code);
		        break;
		}
	}

	function funSetBrandData(code){

		$("#txtBrandNo").focus();
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
		        		document.getElementById("strBrandCode."+rowNo).value=response.strBrandCode;
				    	document.getElementById("strBrandName."+rowNo).value=response.strBrandName;
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
	
	function funHelp1(row,transName)
	{
		fieldName=transName;
		rowNo=row;
	//	window.showModalDialog("searchform.html?formname="+transName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		window.open("searchform.html?formname="+transName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	}
	
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblPOSExciseLinkUp");
	    table.deleteRow(index);
	}
	
	function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	    $(".decimal-places").numeric({ decimalPlaces: maxQuantityDecimalPlaceLimit,negative: false});
	}
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Excise POS Link Up</label>
	</div>

<br/>
<br/>

	<s:form name="POSExciseLinkUp" method="POST" action="savePOSExciseLinkUp.html?saddr=${urlHits}">
		<table id="tblPOSExciseLinkUp" style="width:80%;height:60%;border:#0F0;table-layout:fixed;overflow:scroll" class="transTable">
			<tr style="border:solid thin;">
				<td width="7%">
					<label>POS Item Code</label>
				</td>
				
				<td width="30%">
					<label>POSItemName</label>
				</td>
				
				<td width="14%">
					<label>BrandCode</label>
				</td>
				
				<td width="20%">
					<label>BrandName</label>
				</td>	
				
				<td width="10%">
					<label>Conversion Factor</label>
				</td>	
				<!-- <td width="10%">
					<label>Consumption Unit</label>
				</td>	 -->
				<td width="10%">
<!-- 					<label>Delete</label> -->
				</td>			
			</tr>
			
			<c:forEach items="${ExcisePOSLinkUpList.listExcisePOSLinkUp}" var="tc" varStatus="status">
				<tr>
					<td align="Left" width="5%">
						<input readonly="readonly" class="Box" type="text" size="5%" 
								name="listExcisePOSLinkUp[${status.index}].strPOSItemCode"
								value="${tc.strPOSItemCode}" />
					</td>

					<td align="Left" width="25%">
						<input type="text" readonly="readonly" name="listExcisePOSLinkUp[${status.index}].strPOSItemName"
								value="${tc.strPOSItemName}"  class="Box" size="40%" style="height:40%"/>
					</td>
							
					<td align="center" >
						<input type="text" name="listExcisePOSLinkUp[${status.index}].strBrandCode"
							value="${tc.strBrandCode}" class="searchTextBox" 
							name="listExcisePOSLinkUp[${status.index}].strBrandCode"
							id="strBrandCode.${status.index}" 
							onclick="funHelp1(${status.index},'BrandCode');" />
					</td>

					<td align="center" >
						<input type="text" readonly="readonly"
							name="listExcisePOSLinkUp[${status.index}].strBrandName" 
							id="strBrandName.${status.index}"  size="25%"
							value="${tc.strBrandName}" class="Box" />
					</td>
					
					<td align="center" >
						<input type="text" class=" BoxW124px integer " style="width:75%; !important"
							name="listExcisePOSLinkUp[${status.index}].intConversionFactor" 
							id="intConversionFactor.${status.index}"  
							value="${tc.intConversionFactor}" />
					</td>
								
					<td> 
<!-- 						<input type="button" class="deletebutton" value="Delete" onClick="Javacsript:funDeleteRow(this)"> -->
					</td>
				</tr>
			</c:forEach>
	</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" class="form_button" />
			<input type="button" id="reset" value="Reset" class="form_button"/>
		</p>

	</s:form>
	
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
	
</body>
</html>
