<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Unplanned_Item</title>

<script type="text/javascript">
	var soCode="";
	listRow=0;
	$(document).ready(function() {

		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
		<%if (session.getAttribute("soCode") != null) {%>
           	soCode='<%=session.getAttribute("soCode").toString()%>';
		<%
		}%>
		
// 		funFetchSOData(soCode);

	});

	function funSetData(code) {

		switch (fieldName) {

		case 'custMaster':
			funSetCustomerData(code);
			break;
		
		case 'salesOrder':
			funSetSOCode(code);
			break;

		case 'productionOrder':
			funSetSOCode(code);
			break;
			
		case 'serviceOrder':
			funSetSOCode(code);
			break;	
		
			
	
		}
		
		
	}

	function funFetchSOData(code) {

		gurl = getContextPath() + "/SODataForModal.html?soCode=" + code;
		$.ajax({
			type : "GET",
			url : gurl,
			dataType : "json",
			success : function(response) {
				 if ('Invalid Code' == response.strSOCode) {
					alert("Invalid Sales Order Code");

				} else {
					
					$.each(response.listSODtl, function(i, item) {
						var objDtl = response.listSODtl[i];
						funAddProductRow(objDtl.strProdCode,objDtl.strProdName,objDtl.strPartNo,objDtl.dblAvalaibleStk,objDtl.dblRequiredQty,objDtl.strStatus); 
					});
					
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
	
	function funAddProductRow(strProdCode,strProdName,strPartNo,dblAvalaibleStk,dblRequiredQty,strStatus) 
	{
		var table = document.getElementById("tblSalesOrderDtl");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    rowCount= listRow;
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box strProdCode\" size=\"20%\" id=\"strProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";		  		   	  
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box strProdName\" size=\"40%\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"'/>";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box strPartNo\" size=\"18%\" id=\"strPartNo."+(rowCount)+"\" value='"+strPartNo+"'/>";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box dblAvalaibleStk\" size=\"10%\" style=\"text-align: right;\" id=\"dblAvalaibleStk."+(rowCount)+"\" value='"+dblAvalaibleStk+"' />";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box dblRequiredQty\" size=\"10%\" style=\"text-align: right;\" id=\"dblRequiredQty."+(rowCount)+"\" value='"+dblRequiredQty+"' />";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box strStatus\" size=\"25%\" id=\"strStatus."+(rowCount)+"\"   value='"+strStatus+"'/>";
	    
	    listRow++;
	    return false;
	}

	function funHelp(transactionName) {

		fieldName = transactionName;
	//	window.showModalDialog("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname=" + transactionName+ "&searchText=", "","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}

	function funOpenStatus(code) {
	//	window.showModalDialog("frmSOStatusModal.html?formname=" + code	+"&searchText=", "","dialogHeight:700px;dialogWidth:700px;dialogLeft:100px;");
		window.open("frmSOStatusModal.html?formname=" + code	+"&searchText=", "","dialogHeight:700px;dialogWidth:700px;dialogLeft:100px;");
	}
</script>

</head>
<body>
	<div id="formHeading">
		<label>Unplanned_Item</label>
	</div>
	<s:form name="Unplanned_Item" method="POST"
		action="saveUnplanned_Item.html">
		<br />
		<br />
		
	<c:choose>
    <c:when test="${tableRows != null}">
		<div class="transTable"	style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; margin: auto;">
			<table id="tblSalesOrderDtl"
				style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">

				<tr bgcolor="#72BEFC">
					<th width="15%">Product Code</th>
					<!--  COl1   -->
					<th width="30%">Product Name</th>
					<!--  COl2   -->
					<th width="15%">Part No.</th>
					<!--  COl3   -->
					<th width="10%">Availabel Stock</th>
					<!--  COl4   -->
					<th width="10%">Required Qty</th>
					<!--  COl5   -->
					<th width="10%">Required Date</th>
					<!--  COl6   -->
					<th width="20%">Status</th>
					<!--  COl7   -->
				</tr>
					
					<c:forEach items="${listUnPlanedPI}" var="modelPI" varStatus="status1">
					
<%--  						<c:forEach items="${modelPI}" var="modelvar1" varStatus="status2">  --%>
						<tr>
								<td>${modelPI.strProdCode}</td>
								<td>${modelPI.strProdName}</td>
								<td>  </td>
								<td>0.00</td>
								<td>${modelPI.dblQty}</td>
								<td>${modelPI.dtReqDate}</td>
								<td>Pending</td>
<%-- 						</c:forEach>  --%>
				
					</tr>
				</c:forEach>
				
				
			</table>
		</div>
		
		  </c:when>    
    <c:otherwise>
       No Intend Item Found For this Sales Order 
        <br />
    </c:otherwise>
	</c:choose>
		
		<br />

		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

	<script type="text/javascript">
		// 		funApplyNumberValidation();
	</script>
</body>
</html>