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

	$(document).ready(function()
			{
				var message='';
				<%if (session.getAttribute("success") != null) 
				{
					if(session.getAttribute("successMessage") != null)
					{%>
						message='<%=session.getAttribute("successMessage").toString()%>';
					    <%
					    session.removeAttribute("successMessage");
					}
					boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
					session.removeAttribute("success");
					if (test) 
					{
						%>alert("Data "+message);
						window.returnValue = "saved";
						window.close();
						<%
					}
				}%>
			});


	function funSetData(code){

		switch(fieldName){

		}
	}




	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>POSConfigSetting</label>
	</div>

<br/>
<br/>

	<s:form name="POSConfigSetting" method="POST" action="savePOSConfigSetting.html">

		<table class="masterTable">
			<tr>
				<td style="width: 10%;">
					<label>Server</label>
				</td>
				<td>
					<s:input  type="text" id="txtServer" path="strServer" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>DBName</label>
				</td>
				<td>
					<s:input  type="text" id="txtDBName" path="strDBName" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>UserID</label>
				</td>
				<td>
					<s:input  type="text" id="txtUserID" path="strUserID" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>Password</label>
				</td>
				<td>
					<s:input  type="text" id="txtPassword" path="strPassword" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;"> 
					<label>IPAddress</label>
				</td>
				<td>
					<s:input  type="text" id="txtIPAddress" path="strIPAddress" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>Port</label>
				</td>
				<td>
					<s:input  type="text" id="txtPort" path="strPort" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>BackupPath</label>
				</td>
				<td>
					<s:input  type="text" id="txtBackupPath" path="strBackupPath" cssClass="BoxW124px" style="width: 40%;" />
				</td>
				
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>ExportPath</label>
				</td>
				<td>
					<s:input  type="text" id="txtExportPath" path="strExportPath" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>ImagePath</label>
				</td>
				<td>
					<s:input  type="text" id="txtImagePath" path="strImagePath" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>HOWebServiceUrl</label>
				</td>
				<td>
					<s:input  type="text" id="txtHOWebServiceUrl" path="strHOWebServiceUrl" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>MMSWebServiceUrl</label>
				</td>
				<td>
					<s:input  type="text" id="txtMMSWebServiceUrl" path="strMMSWebServiceUrl" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>OS</label>
				</td>
				<td>
					<s:input  type="text" id="txtOS" path="strOS" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>DefaultPrinter</label>
				</td>
				<td>
					<s:input  type="text" id="txtDefaultPrinter" path="strDefaultPrinter" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>PrinterType</label>
				</td>
				<td>
					<s:input  type="text" id="txtPrinterType" path="strPrinterType" cssClass="BoxW124px" style="width: 40%;"/>
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>TouchScreenMode</label>
				</td>
				<td>
					<s:input  type="text" id="txtTouchScreenMode" path="strTouchScreenMode" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>ServerFilePath</label>
				</td>
				<td>
					<s:input  type="text" id="txtServerFilePath" path="strServerFilePath" cssClass="BoxW124px" style="width: 40%;"/>
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>SelectWaiterFromCardSwipe</label>
				</td>
				<td>
					<s:input  type="text" id="txtSelectWaiterFromCardSwipe" path="strSelectWaiterFromCardSwipe" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>MySQBackupFilePath</label>
				</td>
				<td>
					<s:input  type="text" id="txtMySQBackupFilePath" path="strMySQBackupFilePath" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>HOCommunication</label>
				</td>
				<td>
					<s:input  type="text" id="txtHOCommunication" path="strHOCommunication" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<tr>
				<td style="width: 10%;">
					<label>AdvReceiptPrinter</label>
				</td>
				<td>
					<s:input  type="text" id="txtAdvReceiptPrinter" path="strAdvReceiptPrinter" cssClass="BoxW124px" style="width: 40%;" />
				</td>
			</tr>
			<%-- <tr><td>
			<s:label path="strAdvReceiptPrinter"> </s:label>
			<s:errors path="strAdvReceiptPrinter"></s:errors>
			</td></tr> --%>
			
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
