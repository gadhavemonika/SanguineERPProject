<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<script>
	
		var transactionName,fieldName,reportName;
	
		$(function ()
		{
			funSetTransCodeHelp();
			
			$("#frmDelTrans").submit(function( event )
			{
				if($("#txtTransactionCode").val()=='')
				{
					alert("Please Select Transaction Code To Delete");
					return false;
				}
				
				if($("#txtReason").val()=='')
				{
					alert("Enter Reason For Delete");
					return false;
				}
			});
			
			
			$("#btnShow").click(function( event )
			{
				funSetDocCode($("#txtTransactionCode").val());
			});
			
			
			$("#cmbFormName").change(function() 
			{
				funSetTransCodeHelp();
			});
			
			$('a#baseUrl').click(function() 
			{
				var doc=$("#txtTransactionCode").val();
				var value=$("#cmbFormName").val();
				window.open("setReportFormName.html?docCode="+doc+","+value,"_blank");
			});
			
			
			 var message='';
			 var retval="";
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
				alert(message);
				
			<%
			}}%>
			
		});
		
		
		function funSetTransCodeHelp()
		{
			var value=$("#cmbFormName").val();
			
			switch (value) 
			{
			   case 'frmExciseManualSale':
				   reportName="loadExciseSalesMasterData.html";
				   transactionName='SalesId';
			       break;
			       
			   case 'frmExciseTransportPass':
				   reportName="loadExciseTPMasterData.html";
				   transactionName='TPCode';
			       break;
			       
			   case 'frmExciseOpeningStock':
				   reportName="loadExciseBrandOpeningMasterData.html";
				   transactionName='brandOpeningCode';
			       break;
			       
			   case 'frmExciseOpeningStock':
				   reportName="loadExciseBrandOpeningMasterData.html";
				   transactionName='brandOpeningCode';
			       break;    
			}
		}
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'transaction':
			    	funSetTrasactionCode(code);
			        break;
			

			}
		}
		
		
		function funHelp1(field)
		{
			fieldName=field;
		
		//	window.showModalDialog("searchform.html?formname="+fieldName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			window.open("searchform.html?formname="+fieldName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		
           }
		
		function funHelp()
		{
			funSetTransCodeHelp();
			fieldName='transaction';
			var licenceCode=$('#cmbLicenceCode').val();
	           if($("#cmbLicenceCode").val().trim().length == 0){
					
					alert("Please Select Licence ");
				}else{
	      //  window.showModalDialog("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
					  window.open("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	    }
		}
		function funSetTrasactionCode(code)
		{			
			$("#txtTransactionCode").val(code);
			$("#lblDocCode").text(code);
		}
		
		
		function funReset(){
			$("#txtTransactionCode").val("");
			$("#txtNarration").val("");
		}
		

		function funHelpLicence(transactionName) 
		{
			fieldName = transactionName;
			var licenceCode=$("#txtLicenceCode").val();
			if(transactionName=="TPCode")
			{
				if($("#txtLicenceCode").val().trim().length == 0){
					
					alert("Please Select Licence ");
				}else{
			//	window.showModalDialog("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
					window.open("searchform.html?formname="+transactionName+"&licenceCode="+licenceCode+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			}
		   }else{
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
				window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
		}}
</script>
</head>
	<body>
	<div id="formHeading">
		<label>Excise Delete Transaction</label>
	</div>
		<s:form id="frmDelTrans" method="POST" action="exciseDeleteTransaction.html?saddr=${urlHits}" >
		    <br><br>
		    <table class="transTable">
		    <tr><th colspan="8"></th></tr>
				<tr>	
						
					<td>Form Name</td>
					<td>
						<s:select id="cmbFormName" path="strFormName" cssClass="longTextBox" onchange="funReset();">
							<s:options items="${listFormName}"/>
						</s:select>
					</td>
					
					<td>Transaction Code</td>
					
					<td width="15%">
						<s:input type="text" name="code" id="txtTransactionCode" cssClass="searchTextBox" path="strTransCode" ondblclick="funHelp();"/>						
					</td>
					
					<td>
						 <label id="lblDocCode"></label>
					</td>
				</tr>
				    
			    <tr>
			        <td><label>Reason For Deletion</label></td>
			        <td>
			        	<s:select id="cmdReason" path="strReasonCode" cssClass="longTextBox">
							<s:option value="Wrong Entry">Wrong Entry</s:option>
							<s:option value="Stock Problem">Stock Problem</s:option>
							<s:option value="Testing">Testing</s:option>
						</s:select>
			        </td>
			        
				<td>
					<label>Licence Code</label>
				</td>

			
				<td ><s:select id="cmbLicenceCode" items="${listLicence}" path="strLicenceCode" cssClass="BoxW124px"></s:select>
				
				</td>
			        <td colspan="1"></td>
			    </tr>
			    
			    <tr>
			    	<td><label>Narration</label></td>
			        <td colspan="7">
			        	<s:input type="text" id="txtNarration" cssClass="longTextBox" path="strNarration"/>
			        </td>
			    </tr>
			    
			</table>
				<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit"	id="btnSubmit"	class="form_button" />
		 	<input type="reset" value="Reset"	id="btnReset" class="form_button" />
		</p>
<br><br>
		</s:form>
	</body>
</html>