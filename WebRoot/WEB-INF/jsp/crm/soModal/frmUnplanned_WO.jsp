<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script>
     
          
        

</script>
</head>





<body onload=""  >
	<div id="formHeading">
		<label> UnPlanned WO</label> <br />
	</div>
	<br />
	<br />
	<br />
	<br />
	<s:form name="UnPlanned WO" method="POST" action="">
	 <c:choose>
    <c:when test="${tableRows != null}">
		<div class="transTable"
			style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; margin: auto;">
          	
			<table style="width: 180%; border: 1px solid black !important; table-layout: fixed;"
				class="transTable  col6-right ">

				<tr bgcolor="#72BEFC">


						<th width="12%">Product_Code</th>

					<th width="15%">Product__Name</th>
					<th width="10%">Part_No</th>
					<th width="10%">Work_Order</th>
					<th width="10%">Word_Order_Date</th>
					<th width="10%">Qty</th>
					<th width="10%">Process_Code</th>
					<th width="12%">Process_Name</th>
					
					<th width="15%">Work_Order_Status</th>
					<!-- <th width="15%">Machine_Allocation_Name</th>
					<th width="15%">Machine_Allocation_Date</th>
					<th width="15%">Allocation_Qty</th>
					<th width="12%">Staff_Name</th> -->
					<th width="10%">Production_Code</th>
					<th width="10%">Production_Date</th>
				

				</tr>


				<c:forEach items="${tableRows}" var="modelvar" varStatus="status1">
                          			
						<c:forEach items="${modelvar}" var="modelvar1" varStatus="status2">
						<tr>
							<c:forEach items="${modelvar1}" var="modelvar2"
								varStatus="status3">
							
								<td>${modelvar2}</td>
							</c:forEach>
						</tr>
						</c:forEach>
					
				</c:forEach>
			</table>
		
		</div>
		 </c:when>    
    <c:otherwise>
       No UnPlanned work order Item Found For this Sales Order 
        <br />
    </c:otherwise>
</c:choose>
	</s:form>



</body>
</html>