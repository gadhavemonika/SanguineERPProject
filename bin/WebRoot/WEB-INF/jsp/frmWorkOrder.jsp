<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<tab:tabConfig />
<script type="text/javascript">
	var fieldName;

	$(function() {
		$("#dtWODate").datepicker();

	});

	function funHelp(transactionName) {
		fieldName = transactionName;

		window.showModalDialog("searchform.html?formname=" + transactionName,
				'window', 'width=600,height=600');

	}
</script>

</head>
<body>
	<s:form>
		<!--Start Of container  -->
		<tab:tabContainer id="workordertab">

			<!-- Start of SOWise -->
			<tab:tabPane id="SOWise" tabTitle="SO Wise">

				<!-- End Of SO Wise -->
			</tab:tabPane>
			
			
<!--================================================-->



			<!-- Start of Direct WO -->
			<tab:tabPane id="directWO" tabTitle="Direct">
				<table>
					<tr>
						<td><label>WO Code</label></td>
						<td><s:input path="strWOCode" id="strWOCode" /></td>
						<td><label>Date </label></td>
						<td><s:input path="dtWODate" id="dtWODate" /></td>
					</tr>



					<tr>
						<td><label>Product </label></td>
						<td><s:input path="strProdCode" id="strProdCode"
								readonly="true" ondblclick="funHe" /></td>
						<td><label>Quantity</label></td>
						<td><s:input path="dblQty" /></td>
					</tr>


					<tr>
						<td><label>Status</label></td>
						<td><s:label id="strStatus" path="strStatus"></s:label></td>
					</tr>
				</table>

				<table width="100%">
					<tr bgcolor="#75c0ff">
						<td style="width: 16%; height: 16px;" align="left">Process
							Code</td>
						<td style="width: 16%; height: 16px;" align="left">Process
							Name</td>
						<td style="width: 16%; height: 16px;" align="right">Status</td>
						<td style="width: 16%; height: 16px;" align="left">Pending
							Qty</td>
						<td style="width: 16%; height: 16px;" align="left">Delete</td>
					</tr>
				</table>

				<div id="divProduct"
					style="width: 100%; bgcolor: #d8edff; overflow: scroll;">
					<table width="100%" bgcolor="#d8edff" id="tblProdDet"
						style="overflow: scroll;">
						<tr>
							<td style="width: 16%" align="left"></td>
							<td style="width: 16%" align="left"></td>
							<td style="width: 16%" align="left"></td>
							<td style="width: 16%" align="right"></td>
							<td style="width: 16%" align="left"></td>
						</tr>

						<c:forEach items="${command.listclsWorkOrderDtlModel}" var="wo"
							varStatus="status">
							<tr>
								<td><input
									name="listclsWorkOrderDtlModel[${status.index}].strProcessCode"
									value="${wo.strProdCode}" /></td>

								<td><input
									name="listclsWorkOrderDtlModel[${status.index}].strStatus"
									value="${wo.dblOrdQty}" /></td>

								<td><input type="Button" value="Delete" /></td>
							</tr>
						</c:forEach>


					</table>
				</div>

				<!-- End  of Direct WO -->
			</tab:tabPane>



			<!--End  Of container  -->
		</tab:tabContainer>


		<input type="submit" value="Submit" />
		<input type="reset" value="Reset" />
	</s:form>
</body>
</html>