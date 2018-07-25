<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Plan Master</title>


<script type="text/javascript">

		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	  
			window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		    //window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		

		/**
		*   Attached document Link
		**/
		$(function()
		{
		
			$('a#baseUrl').click(function() 
			{
				if($("#txtPlanCode").val().trim()=="")
				{
					alert("Please Select Plan Code");
					return false;
				}
			   window.open('attachDoc.html?transName=frmPlanMaster.jsp&formName=Plan Master&code='+$('#txtPlanCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			/**
			* On Blur Event on Plan Code Textfield
			**/
			$('#txtPlanCode').blur(function() 
			{
					var code = $('#txtPlanCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/")
					{				
						funSetData(code);						
					}
			});
			
			$('#txtPlanDesc').blur(function () {
				 var strPlanDesc=$('#txtPlanDesc').val();
			      var st = strPlanDesc.replace(/\s{2,}/g, ' ');
			      $('#txtPlanDesc').val(st);
				});
			
		});
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Plan Code)
		**/
		
		function funSetData(code)
		{
			$("#txtPlanCode").val(code);
			var searchurl=getContextPath()+"/loadPlanMasterData.html?planCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strPlanCode=='Invalid Code')
				        	{
				        		alert("Invalid Plan Code");
				        		$("#txtPlanCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtPlanDesc").val(response.strPlanDesc);
					        	
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
		
		
		 /**
			 *  Check Validation Before Saving Record
			 **/
					function funCallFormAction(actionName,object) 
					{
						var flg=true;
						if($('#txtPlanDesc').val()=='')
						{
							 alert('Enter Plan Name ');
							 flg=false;
							  
						}
						
						
						
						return flg;
					}
		
		
	
</script>


</head>
<body>
	<div id="formHeading">
		<label>Plan Master</label>
	</div>
	
	<s:form name="Plan" method="GET" action="savePlanMaster.html?" >
	
		<table class="masterTable">
          
          <tr>
				<th align="right" colspan="2"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp;</th>
			</tr>
			
			<tr>
			    <td><label>Plan </label></td>
				<td><s:input id="txtPlanCode" path="strPlanCode" cssClass="searchTextBox" ondblclick="funHelp('plan')" /></td>				
			</tr>
			
			<tr>
			    <td><label>Plan Desc</label></td>
				<td><s:input id="txtPlanDesc" path="strPlanDesc" cssClass="longTextBox" /></td>				
			</tr>
			
			
			
		</table>
		
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funCallFormAction('submit',this);" />
            <input type="reset" value="Reset" class="form_button" />
          
            
		</p>
	</s:form>
	
</body>
</html>