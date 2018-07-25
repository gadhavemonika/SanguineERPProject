<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Income Head Master</title>

<script type="text/javascript">

	var fieldName;
	
	
	
	
	//Initialize tab Index or which tab is Active
	$(document).ready(function() 
	{		
		$(".tab_content").hide();
		$(".tab_content:first").show();

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();
			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
		});
			
		$(document).ajaxStart(function(){
		    $("#wait").css("display","block");
		});
		$(document).ajaxComplete(function(){
		   	$("#wait").css("display","none");
		});
	});
	
	
	
	/**
	* Open Help
	**/
	function funHelp(transactionName)
	{
		fieldName=transactionName;
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
				if($("#txtIncomeHeadCode").val().trim()=="")
				{
					alert("Please Select IncomeHead Code");
					return false;
				}
			   	window.open('attachDoc.html?transName=frmIncomehead.jsp&formName=IncomeHead Master&code='+$('#txtIncomeHeadCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			/**
			* On Blur Event on 	incomeHead Code Textfield
			**/
			$('#txtIncomeHeadCode').blur(function() 
			{
				var code = $('#txtIncomeHeadCode').val();
				if (code.trim().length > 0 && code !="?" && code !="/")
				{
					funSetIncomeHead(code);
				}
			});
			
			$('#txtIncomeHeadDesc').blur(function () {
				 var strIncomeHeadDesc=$('#txtIncomeHeadDesc').val();
			      var st = strIncomeHeadDesc.replace(/\s{2,}/g, ' ');
			      $('#txtIncomeHeadDesc').val(st);
			});
		});
		
		
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Dept Code)
		**/
		function funSetDepartment(code)
		{
			$("#txtDeptCode").val(code);
			var searchurl=getContextPath()+"/loadDeptMasterData.html?deptCode="+code;
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {
		        	if(response.strDeptCode=='Invalid Code')
		        	{
		        		alert("Invalid Dept Code");
		        		$("#txtDepartment").val('');
		        	}
		        	else
		        	{
		        		$("#txtDepartment").val(response.strDeptCode);
		        		$("#lblDepartment").text(response.strDeptDesc);
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
		* Get and Set data from help file and load data Based on Selection Passing Value(Income Head Code)
		**/
		function funSetIncomeHead(code)
		{
			$("#txtIncomeHeadCode").val(code);
			var searchurl=getContextPath()+"/loadIncomeHeadMasterData.html?incomeCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strIncomeHeadCode=='Invalid Code')
				        	{
				        		alert("Invalid Income Head Code");
				        		$("#txtIncomeHeadCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtIncomeHeadDesc").val(response.strIncomeHeadDesc);
					        	$("#cmbDeptType").val(response.strDeptCode);
					        	

				        		funSetAccountCode(response.strAccountCode)
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
		linked account code
		**/
		function funSetAccountCode(code){

			$.ajax({
				type : "GET",
				url : getContextPath()+ "/getAccountMasterDtl.html?accountCode=" + code,
				dataType : "json",			
				success : function(response)
				{
					if(response.strAccountCode=='Invalid Code')
		        	{
		        		alert("Invalid Account Code");
		        		$("#txtAccountCode").val('');
		        	}
		        	else
		        	{
		        		$("#txtAccountCode").val(response.strAccountCode);
		        		$("#txtAccountName").val(response.strAccountName);
		        	}
				},
				error : function(jqXHR, exception)
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
		
		
		
		function funSetData(code)
		{
			switch(fieldName)
			{
				case "incomeHead":
					funSetIncomeHead(code);
					break;
					
				case "deptCode":
					funSetDepartment(code);
					break;
					
				case "accountCode":
					funSetAccountCode(code);
					break;
			}
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
				}
			}%>
		});
		

			/**
			 *  Check Validation Before Saving Record
			 **/
			function funCallFormAction(actionName,object) 
			{
				var flg=true;
				
				if($('#txtDepartment').val()=='')
				{
					 alert('Select Department');
					 flg=false;
				}
				else if($('#txtIncomeHeadDesc').val()=='')
				{
					 alert('Enter Income Head Description ');
					 flg=false;
				}				
				
				return flg;
			}
</script>


</head>
<body>
	<div id="formHeading">
		<label>Income Head Master</label>
	</div>
	
	<s:form name="Plan" method="GET" action="saveIncomeHeadMaster.html?" >
		<br> 
		<br>
		<div id="tab_container" style="height: 405px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 6%; padding-left: 2%;margin-left: 10%; " class="active" >General</li>
					<li data-state="tab2" style="width: 8%; padding-left: 1%">LinkUp</li>
				</ul>
							
				<!-- General Tab Start -->
				<div id="tab1" class="tab_content" style="height: 400px">
							
		<table class="masterTable">
         	<tr>
				<th align="right" colspan="4"><a id="baseUrl" href="#"> Attach Documents </a>&nbsp; &nbsp; &nbsp;&nbsp;</th>
			</tr>
			
			<tr>
			    <td width="20%"><label>Income Head</label></td>
				<td width="40%"><s:input id="txtIncomeHeadCode" path="strIncomeHeadCode" cssClass="searchTextBox" ondblclick="funHelp('incomeHead')" /></td>				
			<td colspan="1"></td>
			</tr>
			
			<tr>
			    <td width="20%"><label>Department</label></td>
			    <td width="40%"><s:input id="txtDepartment" path="strDeptCode" cssClass="searchTextBox" ondblclick="funHelp('deptCode')" /></td>			     
		        <td width="20%"><label id="lblDepartment"></label></td>
			</tr>
			
			<tr>
			    <td width="20%"><label>Income Head Desc</label></td>
				<td width="40%"><s:input id="txtIncomeHeadDesc" path="strIncomeHeadDesc" cssClass="longTextBox" /></td>				
			    <td colspan="1"></td>
			</tr>
		</table>
		</div>
						<!--General Tab End  -->
						
						
			<!-- Linkedup Details Tab Start -->
			<div id="tab2" class="tab_content" style="height: 400px">
			<br> 
			<br>			
				<table class="masterTable">
						<tr>
						    <td><label>Account Code</label></td>
						    <td><s:input id="txtAccountCode" path="strAccountCode" readonly="true" ondblclick="funHelp('accountCode')" cssClass="searchTextBox"/></td>
						    <td colspan="2"><s:input id="txtAccountName" path="" readonly="true" cssClass="longTextBox"  style="width: 316px"/></td>			        			        						    			    		        			  
						</tr>
				</table>
			</div>
			
		</div>
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"  onclick="return funCallFormAction('submit',this);" />
            <input type="reset" value="Reset" class="form_button" />
        </p>
	</s:form>
	
</body>
</html>