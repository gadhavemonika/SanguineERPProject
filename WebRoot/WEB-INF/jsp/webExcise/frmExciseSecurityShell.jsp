<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%-- <%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
	$(document).ready(function() {

		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
	});
</script>

<script type="text/javascript">
var fieldName;
	function funHelp(transactionName) {
		//window.open("searchform.html?formname=" + transactionName+"&searchText=", 'window','width=600,height=600');
		//Likeusermaster
		fieldName=transactionName;		
		if(transactionName=="Likeusermaster")
			{
				transactionName="usermaster";
			}
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}

	function funSetData(code)
	{
		//alert(fieldName);
		switch (fieldName)
		{
			case 'usermaster':
				
				funSetNewUser(code);
				
			break;	
			
			case 'Likeusermaster':
				
				funSetLikeUser(code);	
				
				break;
		}
		

	}
	function funSetNewUser(code)
	{
		document.getElementById("strUserCode").value = code;
		$
				.ajax({
					type : "GET",
					url : getContextPath()
							+ "/loadUserMasterData.html?userCode=" + code,
					dataType : "json",
					success : function(response) 
					{
						document.getElementById("UserName").value = response.strUserName1;
						document.securityShell.action = "security.html?userCode="+ code;
						document.securityShell.submit();
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
	
	function funSetLikeUser(code)
	{
		//alert(code);
		document.getElementById("strLikeUserCode").value = code;
		$
				.ajax({
					type : "GET",
					url : getContextPath()
							+ "/loadUserMasterData.html?userCode=" + code,
					dataType : "json",
					success : function(response) 
					{
						document.getElementById("LikeUserName").value = response.strUserName1;
						document.securityShell.action = "LikeUsersecurity.html?userCode="+ code;
						document.securityShell.submit();
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
	function funResetForm(){
		
	}
</script>
<style type="text/css">

</style>

<title>Insert title here</title>
<%-- <tab:tabConfig /> --%>
</head>
<body>

	<div id="formHeading">
		<label>Excise Security Shell</label>
	</div>
	<div>
		<s:form action="saveSecurityShell.html?saddr=${urlHits}" method="POST"
			name="securityShell">
			<input type="hidden" value="${urlHits}" name="saddr">
			<br />
			
			<table
				style="border: 0px solid black; width: 70%; margin-left: auto; margin-right: auto;background-color:#A3D0F7;font-size:11px;
			font-weight: bold;" class="transTable">
				<thead>
					<tr >
						<td width="100px"><label>User Code</label></td>
						<td width="100px"> <s:input path="strUserCode"
								cssClass="searchTextBox" id="strUserCode" readonly="true"
								ondblclick="funHelp('usermaster')" /></td>
						<td > <s:input id="UserName"
							cssClass="Box" cssStyle="width:50%;height:18px;" readonly="true" path="strUserName"/></td>
					</tr>
					<tr>
					<td width="100px"><label>Like User </label></td>
						<td width="100px"> <s:input path="strLikeUserCode" 
								cssClass="searchTextBox" id="strLikeUserCode" readonly="true"
								ondblclick="funHelp('Likeusermaster')" /></td>
					<td > <s:input id="LikeUserName"
							cssClass="Box" cssStyle="width:50%;height:18px;" readonly="true" path="strLikeUserName"/></td>
					</tr>
				</thead>
				
			</table>
			<br>

			<!-- Start of tab container -->
			<table
				style="border: 0px solid black; width: 70%; margin-left: auto; margin-right: auto;background-color:#C0E4FF;">
				<tr>
					<td>
						<div id="tab_container">
							<ul class="tabs">
								<li class="active" data-state="tab1">Masters</li>
								<li data-state="tab2">Transactions</li>
								<li data-state="tab3">Process</li>
								<li data-state="tab4">Reports</li>
								<li data-state="tab5">Tools</li>
								<li data-state="tab6">Mobile Applications</li>
							</ul>
							<br /> <br />

							<!--  Start of Masters tab-->

							<div id="tab1" class="tab_content">
								<table border="1" class="myTable">
									<thead>
										<tr>
											<th>Form Name</th>
											<th></th>
											<th>Add</th>
											<th>Edit</th>
											<th>View</th>
											<th>Print</th>
										</tr>
									</thead>									
									<c:forEach items="${treeList.listMasterForms}" var="tree"
										varStatus="status">
										<tr>
											<td><label
												id="listMasterForms[${status.index}].strFormDesc">${tree.strFormDesc}</label></td>
											<td><input type="hidden"
												name="listMasterForms[${status.index}].strFormName"
												value="${tree.strFormName}" /></td>
											<td align="center"><input type="checkbox"
												name="listMasterForms[${status.index}].strAdd"
												<c:if test="${tree.strAdd == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listMasterForms[${status.index}].strEdit"
												<c:if test="${tree.strEdit == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listMasterForms[${status.index}].strView"
												<c:if test="${tree.strView == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listMasterForms[${status.index}].strPrint"
												<c:if test="${tree.strPrint == 'true'}">checked="checked"</c:if>
												value="true" /></td>
										</tr>
									</c:forEach>


								</table>
							</div>
							<!--  End of  Masters tab-->


							<!-- Start of Transaction tab -->

							<div id="tab2" class="tab_content">
								<table border="1" class="myTable">
									<thead>
										<tr>
											<th width="40%">Form Name</th>
											<th></th>
											<th>Add</th>
											<th>Edit</th>
											<th>Delete</th>
											<th>View</th>
											<th>Print</th>
											<th>Authorise</th>
										</tr>
									</thead>
									<c:forEach items="${treeList.listTransactionForms}" var="tree"
										varStatus="status">
										<tr>
											<%-- <td><input name="listTransactionForms[${status.index}].strFormDesc" value="${tree.strFormDesc}"/></td> --%>
											<td><label
												id="listTransactionForms[${status.index}].strFormDesc">${tree.strFormDesc}</label></td>
											<td><input type="hidden"
												name="listTransactionForms[${status.index}].strFormName"
												value="${tree.strFormName}" /></td>
											<td align="center"><input type="checkbox"
												name="listTransactionForms[${status.index}].strAdd"
												<c:if test="${tree.strAdd == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listTransactionForms[${status.index}].strEdit"
												<c:if test="${tree.strEdit == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listTransactionForms[${status.index}].strDelete"
												<c:if test="${tree.strDelete == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listTransactionForms[${status.index}].strView"
												<c:if test="${tree.strView == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listTransactionForms[${status.index}].strPrint"
												<c:if test="${tree.strPrint == 'true'}">checked="checked"</c:if>
												value="true" /></td>
											<td align="center"><input type="checkbox"
												name="listTransactionForms[${status.index}].strAuthorise"
												<c:if test="${tree.strAuthorise == 'true'}">checked="checked"</c:if>
												value="true" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<!-- End of Transaction tab -->


							<!-- Start of Process Tab -->

							<div id="tab3" class="tab_content"></div>

							<!-- End  of Process Tab -->


							<!-- Start of Reports Tab -->
							<div id="tab4" class="tab_content">
								<table border="1" class="myTable">
									<thead>
										<tr>
											<th width="70%">Form Name</th>
											<th></th>
											<th>Grant</th>
										</tr>
									</thead>
									<c:forEach items="${treeList.listReportForms}" var="tree"
										varStatus="status">
										<tr>
											<td><label
												id="listReportForms[${status.index}].strFormDesc">${tree.strFormDesc}</label></td>
											<%-- <td><label id="listReportForms[${status.index}].strFormDesc" >${tree.strFormDesc}</label></td> --%>
											<td><input type="hidden"
												name="listReportForms[${status.index}].strFormName"
												value="${tree.strFormName}" /></td>
											<td align="center"><input type="checkbox"
												name="listReportForms[${status.index}].strGrant"
												<c:if test="${tree.strGrant == 'true'}">checked="checked"</c:if>
												value="true" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<!-- End  of Reports Tab -->


							<!-- Start of tools tab -->

							<div id="tab5" class="tab_content">
								<table border="1" class="myTable">
									<thead>
										<tr>
											<th width="70%">Form Name</th>
											<th></th>
											<th>Grant</th>
										</tr>
									</thead>
									<c:forEach items="${treeList.listUtilityForms}" var="tree"
										varStatus="status">
										<tr>
											<td><label
												id="listUtilityForms[${status.index}].strFormDesc">${tree.strFormDesc}</label></td>
											<td><input type="hidden"
												name="listUtilityForms[${status.index}].strFormName"
												value="${tree.strFormName}" /></td>
											<td align="center"><input type="checkbox"
												name="listUtilityForms[${status.index}].strGrant"
												<c:if test="${tree.strGrant == 'true'}">checked="checked"</c:if>
												value="true" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>

							<!-- End  of tools tab -->


							<!-- Start of Mobile Applications Tab -->
							<div id="tab4" class="tab_content">This id my Mobile
								Applications Tab</div>
							<!-- End of Mobile Applications Tab -->


						</div>
					</td>
				</tr>
			</table>
			<!-- End Of tab container -->
			
			<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			 <a STYLE="text-decoration:none"  href="frmExciseSecurityShell.html" ><input type="button" value="Reset" class="form_button" /></a><br/></p>
		
		</s:form>
	</div>
</body>
</html>