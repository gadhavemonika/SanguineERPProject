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
	
	
	
	$(function() 
	{
	});

	function funSetData(code){

		switch(fieldName){

			case 'settlementCode' : 
				funSetSettlementCode(code);
				break;
				
			case "accountCode":
				funSetAccountCode(code);
				break;	
		}
	}


	function funSetSettlementCode(code)
	{
		$("#txtSettlementCode").val(code);

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadSettlementCode.html?settlementCode=" + code,
			dataType : "json",
			success : function(response)
			{ 
				if(response.strService=='Invalid Code')
	        	{
	        		alert("Invalid Service Code");
	        		$("#txtSettlementCode").val('');
	        	}
	        	else
	        	{
		        	$("#txtSettlementCode").val(response.strSettlementCode);
		        	$("#txtSettlementDesc").val(response.strSettlementDesc);
		        	$("#txtSettlementType").val(response.strSettlementType);
		        	$("#cmbApplicable").val(response.strApplicable);
		        	
		        	funSetAccountCode(response.strAccountCode);
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
	
	
	/**
	* On Blur Event on settlementCode Textfield
	**/
	$('#txtSettlementCode').blur(function() 
	{
		var code = $('#txtSettlementCode').val();
		if (code.trim().length > 0 && code !="?" && code !="/")
		{
			funSetSettlementCode(code);
		}
	});
	
	
	/**
	* Success Message After Saving Record
	**/
	$(document).ready(function()
	{
		var message='';
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null){%>
			    message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
				session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test)
			{
				%>
				alert("Data Save successfully\n\n"+message);
				<%
			}
		}%>
	});
	
	
	
	 /**
		* help function for searching settlement code
		**/
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	 
	 

	 /**
		*   Attached document Link
		**/
		$(function()
		{
		
			$('a#baseUrl').click(function() 
			{
				if($("#txtSettlementCode").val().trim()=="")
				{
					alert("Please Select SettlementCode");
					return false;
				}
			   window.open('attachDoc.html?transName=frmSettlementMaster.jsp&formName=Settlement Master &code='+$('#txtSettlementCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
		});
	 
	 
		/**
		 *  Check Validation Before Saving Record
		 **/
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			
			if($('#txtSettlementDesc').val()=='')
			{
				 alert('Enter Settlement Description');
				 flg=false;
			}
			return flg;
		}
		
</script>

</head>
<body>

	<div id="formHeading">
	<label>Settlement Master</label>
	</div>

<br/>
<br/>

	<s:form name="SettlementMaster" method="GET" action="saveSettlementMaster.html">

<div id="tab_container" style="height: 405px">
				<ul class="tabs">
					<li data-state="tab1" style="width: 6%; padding-left: 2%;margin-left: 10%; " class="active" >General</li>
					<li data-state="tab2" style="width: 8%; padding-left: 1%">LinkUp</li>
				</ul>
							
				<!-- General Tab Start -->
				<div id="tab1" class="tab_content" style="height: 400px">
					<br> 
					<br>	
		<table class="masterTable">
		
		  <tr>
				<th align="right" colspan="2"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp;</th>
			</tr>
			
		
			<tr>
				<td><label>SettlementCode</label></td>
                <td><s:input id="txtSettlementCode" path="strSettlementCode" cssClass="searchTextBox" ondblclick="funHelp('settlementCode')" /></td>
			</tr>
				
			<tr>
				<td><label>SettlementDesc</label></td>
				<td><s:input id="txtSettlementDesc" path="strSettlementDesc" cssClass="longTextBox" /></td>
           	</tr>
			
			<tr>
				<td><label>SettlementType</label></td>
				<td>
					<s:select id="cmbSettlementType" path="strSettlementType" cssClass="BoxW124px">
				    	<option selected="selected" value="Cash">Cash</option>
				    	<option value="Credit Card">Credit Card</option>
				    	<option value="Credit">Credit</option>
		         	</s:select>					
				</td>				
			</tr>
			
			<tr>
				<td><label>Applicable</label></td>
				 <td > 
				 	<s:select id="cmbApplicable" path="strApplicable" cssClass="BoxW124px">
				    	<option selected="selected" value="Y">Y</option>			           
			        	<option value="N">N</option>
		         	</s:select>
				</td>	
				
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
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funCallFormAction('submit',this);" />
            <input type="reset" value="Reset" class="form_button" />
          
		</p>
	</s:form>
</body>
</html>
