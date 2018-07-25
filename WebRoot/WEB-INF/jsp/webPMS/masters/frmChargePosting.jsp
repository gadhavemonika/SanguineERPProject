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

	$(function() 
	{
	});
	
	
//set serached data to fields
	function funSetData(code)
	{

		switch(fieldName){

			case 'service' : 
				funSetService(code);
				break;
			case 'deptCode' : 
				funSetDeptCode(code);
				break;
			case 'incomeHead' : 
				funSetIncomeHeadCode(code);
				break;
		}
	}

	
	/**
	* On Blur Event on deptCode Textfield
	**/
	$('#txtService').blur(function() 
	{
			var code = $('#txtService').val();
			if (code.trim().length > 0 && code !="?" && code !="/")
			{				
				funSetService(code);
			}
	});
	
	

	function funSetService(code)
	{
		$("#txtService").val(code);

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadService.html?serviceCode=" + code,
			dataType : "json",
			success : function(response)
			{ 
				
				if(response.strService=='Invalid Code')
	        	{
	        		alert("Invalid Service Code");
	        		$("#txtService").val('');
	        	}
	        	else
	        	{
		        	$("#txtDeptCode").val(response.strDeptCode);
		        	$("#txtIncomeHeadCode").val(response.strIncomeHeadCode);
		        
	        	}

			},
			error : function(e)
			{
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

	

	
	function funSetDeptCode(code)
	{
		$("#txtDeptCode").val(code);
		$.ajax({
			type : "GET",
	
			url : getContextPath()+ "/loadDeptMasterData.html?deptCode=" + code,
			dataType : "json",
			success : function(response)
			{ 

			},
			error : function(e)
			{

			}
		});
	}

	function funSetIncomeHeadCode(code)
	{
		$("#txtIncomeHeadCode").val(code);

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadIncomeHeadMasterData.html?incomeCode=" + code,
			dataType : "json",
			success : function(response)
			{ 

			},
			error : function(e)
			{

			}
		});
	}

	

	/**
	* Success Message After Saving Record
	**/
	 $(document).ready(function()
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
			alert("Data Save successfully\n\n"+message);
		<%
		}}%>

	});


	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		//funSetData(code)
	}
	
	

	 /**
		*   Attached document Link
		**/
		$(function()
		{
		
			$('a#baseUrl').click(function() 
			{
				if($("#txtService").val().trim()=="")
				{
					alert("Please Select ServiceCode");
					return false;
				}
			   window.open('attachDoc.html?transName=frmChargePosting.jsp&formName=Charge Posting &code='+$('#txtService').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
		});	
			
		
</script>

</head>
<body>

	<div id="formHeading">
	<label>ChargePosting</label>
	</div>

<br/>
<br/>

	<s:form name="ChargePosting" method="GET" action="saveChargePosting.html">

		<table class="masterTable">
		
		   <tr>
				<th align="right" colspan="2"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp;</th>
			</tr>
			
			<tr>
				<td>
					<label>Service</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtService" path="strService" cssClass="searchTextBox" ondblclick="funHelp('service');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>DeptCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtDeptCode" path="strDeptCode" cssClass="searchTextBox" ondblclick="funHelp('deptCode');"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>IncomeHeadCode</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtIncomeHeadCode" path="strIncomeHeadCode" cssClass="searchTextBox" ondblclick="funHelp('incomeHead');"/>
				</td>
			</tr>
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
