<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<style type="text/css">  
   	
   	
    #tblChargeProcessing td:nth-child(1),#tblChargeProcessing td:nth-child(2)
	#tblChargeProcessing td:nth-child(3),#tblChargeProcessing td:nth-child(7)
	{
		text-align: left;		
	}
	#tblChargeProcessing td:nth-child(4)
	{
		text-align: right;
	}
	#tblChargeProcessing td:nth-child(5),#tblChargeProcessing td:nth-child(6)
	{
		text-align: center;
	} 
	
	#tblChargeProcessing th:nth-child(1),#tblChargeProcessing td:nth-child(1)
	{
		width: 20px;
	}

	
	
</style>
<script type="text/javascript">
	
	/* generate charge processing splip and call report */
    function funGenerateChargeProcessingSlip()
	{
		var memberCode='';
		var fromDate='';
		var toDate='';
		var generatedOnDate='';
		
		<%
			if(session.getAttribute("memberCode")!=null)
			{
				%>memberCode='<%= session.getAttribute("memberCode").toString()%>';
				<%	
				session.removeAttribute("memberCode");
			}
			if(session.getAttribute("fromDate")!=null)
			{
				%>fromDate='<%= session.getAttribute("fromDate").toString()%>';
				<%		
				session.removeAttribute("fromDate");
			}
			if(session.getAttribute("toDate")!=null)
			{
				%>toDate='<%= session.getAttribute("toDate").toString()%>';
				<%				
				session.removeAttribute("toDate");
			}
			if(session.getAttribute("generatedOnDate")!=null)
			{
				%>generatedOnDate='<%= session.getAttribute("generatedOnDate").toString()%>';
				<%	
				session.removeAttribute("generatedOnDate");
			}
		%>			
		
		var isOk=confirm("Do You Want to Generate Slip?");
		if(isOk)
		{
			window.open(getContextPath()+"/rptChargeProcessingSlip.html?memberCode="+memberCode+"&fromDate="+fromDate+"&toDate="+toDate+"&generatedOnDate="+generatedOnDate,'_blank');
		}	
	} 
	
	/* form validation */
	function funCheckValidation()
	{
		var flag=false;
		$('#tblChargeProcessing tbody tr').each(function () 
		{
		        $('td:first', this).each(function () 
		        {	        		
		        	var element=$(this).html();			        				        	
		        	var id=$(element).attr("id");
		        	var currentCheckBox=document.getElementById(id);			        	
		        	
		        	if($(currentCheckBox).val().toUpperCase()=="Y")
		        	{
		        		flag=true;		        		
		        	}
		        })
		});	
		if(!flag)
		{
			alert("Please Select At Least One Charge.");	
		}
			
		
		return flag;
	}
	
	function funSelectAllCheckBoxClicked(currentCheckBox)
	{
	
		if($(currentCheckBox).prop('checked')==true)
		{
			$(currentCheckBox).val("Y");
			$('#tblChargeProcessing  tr').each(function () 
			{
			        $('td:first', this).each(function () 
			        {	        		
			        	var element=$(this).html();			        				        	
			        	var id=$(element).attr("id");
			        	var currentCheckBox=document.getElementById(id);			        	
			        	$(currentCheckBox).prop("checked",true);
			        	$(currentCheckBox).val("Y");				    			        	
			        })
			});	
		}
		else
	    {
			$(currentCheckBox).val("N");
			$('#tblChargeProcessing tr').each(function () 
			{
			    $('td:first', this).each(function () 
			    {	        		
			       	var element=$(this).html();			        				        	
			       	var id=$(element).attr("id");
			       	var currentCheckBox=document.getElementById(id);			        	
			       	$(currentCheckBox).prop("checked",false);
			       	$(currentCheckBox).val("N");					    			        
			    })
			});	
	    }			
	}
	
	
	/* load charges from charge master */
	function funLoadAllCharges()
	{		
		var searchurl=getContextPath()+"/loadAllChargesFromChargeMaster.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {			        	
			        	$.each(response,function(i,objModel)
			        	{
			        		 var table=document.getElementById("tblChargeProcessing");	    
			        		    			        		 
			        		 var row=table.insertRow();
			        		 
			        		 var col0=row.insertCell(0);
			        		 var col1=row.insertCell(1);
			        		 var col2=row.insertCell(2);
			        		 var col3=row.insertCell(3);
			        		 var col4=row.insertCell(4);
			        		 var col5=row.insertCell(5);
			        		 var col6=row.insertCell(6); 
			        		 var col7=row.insertCell(7); 
			        			    
			        	     col0.innerHTML = "<input type=\"checkbox\" size=\"1%\" id=\"chkBox"+i+" \" name=\"listChargeDtl["+i+"].isProcessYN\" value=\"N\" onclick=\"funSetCheckBoxValueYN(this)\" />";
			        		 col1.innerHTML = "<input class=\"Box\" size=\"9%\" style=\"padding-left: 5px\" readonly=\"readonly\" name=\"listChargeDtl["+i+"].strChargeCode\" value="+objModel.strChargeCode+" />";
			        		 col2.innerHTML = "<input class=\"Box\" size=\"20%\" style=\"padding-left: 5px\" readonly=\"readonly\" value=\" "+objModel.strChargeName+" \" name=\"listChargeDtl["+i+"].strChargeName\"  />" ;
			        		 col3.innerHTML = "<input class=\"Box\" size=\"9%\" readonly=\"readonly\" name=\"listChargeDtl["+i+"].strAccountCode\" value="+objModel.strAcctCode+" />" ;
			        		 col4.innerHTML = "<input size=\"9%\" style=\"text-align: right;\" class=\"Box\" readonly=\"readonly\" name=\"listChargeDtl["+i+"].dblAmount\" value="+objModel.dblAmt+" />";
			        		 col5.innerHTML = "<input size=\"9%\" style=\"text-align: center;\" class=\"Box\" readonly=\"readonly\" name=\"listChargeDtl["+i+"].strType\" value="+objModel.strType+" />";
			        		 col6.innerHTML = "<input size=\"9%\" style=\"text-align: center;\" class=\"Box\" readonly=\"readonly\" name=\"listChargeDtl["+i+"].strCrDr\" value="+objModel.strCrDr+" />";
			        		 col7.innerHTML = "<input size=\"40%\" class=\"Box\" readonly=\"readonly\" name=\"listChargeDtl["+i+"].strNarration\" value="+objModel.strRemark+" />";  
			        		 
			        	});
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

	/* when document is ready */
	function funWhenDocumentIsReady()
	{
		funLoadAllCharges();
	}
	
	$(document).ready(function()
	{
		funWhenDocumentIsReady();
	});
	
	/* To Set CheckBox Value To Y/N */
	function funSetCheckBoxValueYN(currentCheckBox)
	{			    
	    if($(currentCheckBox).prop("checked") == true)
	    {           
	    	$(currentCheckBox).val("Y");	
        }
	    if($(currentCheckBox).prop("checked") == false)
        {         
	    	$(currentCheckBox).val("N");	
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
			
			funGenerateChargeProcessingSlip();
			
		<%
		}}%>

		$("#txtFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtFromDate").datepicker('setDate', 'today');

		$("#txtToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtToDate").datepicker('setDate', 'today');
		
		$("#txtGeneratedOnDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtGeneratedOnDate").datepicker('setDate', 'today');
		
	});
	
	 function funSetMemberData(memberCode)
		{
		   
			var searchurl=getContextPath()+"/loadMemberData.html?memberCode="+memberCode;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strDebtorCode=='Invalid Code')
				        	{
				        		alert("Invalid Member Code");				        		
				        	}
				        	else
				        	{					        	    			        	    
				        	   $("#txtMemberCode").val(response[0]);
				        	   $("#txtMemberName").val(response[1]);
				        	   $("#txtMemberName").focus();
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
	 
	 
	 
	 
	 
	 function funSetGLCode(code){

			$.ajax({
				type : "GET",
				url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
				dataType : "json",
				success : function(response){ 
					if(response.strVouchNo!="Invalid")
			    	{
						$("#txtAccountCode").val(response.strAccountCode);
						$("#txtAccountName").val(response.strAccountName);
									
			    	}
			    	else
				    {
				    	alert("Invalid Account");
				    	$("#txtAccountCode").val("");
				    	$("#txtAccountCode").focus();
				    	return false;
				    }
				},
				error : function(e){
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
	 
	 
	 
	 
	 
	 
	 

	function funSetData(code){

		switch(fieldName)
		{		
			case "memberCode":
				funSetMemberData(code);			    
			 break;	
			 
			case 'debtorAccountCode' : 
				funSetGLCode(code);
				break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Charge Processing</label>
	</div>

<br/>
<br/>

	<s:form name="ChargeProcessing" method="POST" action="saveChargeProcessing.html"  >

		<table class="masterTable">
			<tr>
			    <td><label>From Date</label></td>	
			    <td colspan="3"><s:input type="text" id="txtFromDate" class="calenderTextBox" path="dteFromDate"  /></td>		    		  
			</tr>
			<tr>
			    <td><label>To Date</label></td>	
			    <td colspan="3"><s:input type="text" id="txtToDate" class="calenderTextBox" path="dteToDate"  /></td>		    		  
			</tr>
			<tr>
			    <td><label>Generated On</label></td>	
			    <td colspan="3"><s:input type="text" id="txtGeneratedOnDate" class="calenderTextBox" path="dteGeneratedOn" /></td>		    		  
			</tr>		
			<tr>
			    <td><label >Member</label></td>
			    <td style="width: 100px;" ><s:input id="txtMemberCode" path="strMemberCode" readonly="true" ondblclick="funHelp('memberCode')" cssClass="searchTextBox"/></td>			        			        
			    <td colspan="2"><s:input id="txtMemberName" path="strMemberName" required="true" readonly="true"  cssClass="longTextBox" cssStyle="width:68%"/></td>			    		        			   
			</tr>	
			
		
			<tr>
			    <td><label >Account</label></td>
			    <td style="width: 100px;" ><s:input id="txtAccountCode" path="strAccountCode" readonly="true" ondblclick="funHelp('debtorAccountCode')" cssClass="searchTextBox"/></td>			        			        
			    <td colspan="2"><s:input id="txtAccountName" path="strAccountName" required="true" readonly="true"  cssClass="longTextBox" cssStyle="width:68%"/></td>			    		        			   
			</tr>	
															
			
			<tr>
			    <td><label>Instant JV</label></td>	
			    <td><s:input type="checkbox" id="chkInstantJV"  path="strInstantJVYN" onclick="funSetCheckBoxValueYN(this)"/></td>
			    <td style="width: 100px;" ><label>Other Functions</label></td>	
			    <td><s:select id="cmbOtherFunctions" path="strOtherFunctions" items="${listOtherFunctions}" cssClass="BoxW124px" /></td>		    		  
			</tr>
			<tr>
			    <td style="width: 150px"><label>Annual Charge Processing</label></td>	
			    <td colspan="3"><s:input type="checkbox" id="chkAnnualChargeProcessing"  path="strAnnualChargeProcessYN" onclick="funSetCheckBoxValueYN(this)"/></td>			    		    		 
			</tr>
		</table>
         <br>
         <br>
        
  		 <div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 150px;
				    				margin: auto;overflow-x: scroll; overflow-y: scroll;width: 80%;">
				<!-- Dynamic Table Generation for tab4 (Opening Balance)				 -->
				<table id="tblChargeProcessing" class="transTablex" style="width: 100%" >					
					
					<tr>
					        <th style="text-align: left;"><input type="checkbox" id="chkSelectAll" value="N" onclick="funSelectAllCheckBoxClicked(this)"/></th>
					        <th><label>Charge Code</label></th>
					        <th><label>Charge Name</label></th>
					        <th><label>Account Code</label></th>
					        <th><label>Amount</label></th>
					        <th><label>Type</label></th>		
					        <th><label>Cr/Dr</label></th>
					        <th><label>Narration</label></th>				       
				   	</tr>
				   
					<col style="width: 1%">
					<col style="width: 9%" style="padding-left: 5px">
					<col style="width: 20%" style="padding-left: 5px">
					<col style="width: 9%">
					<col style="width: 9%">
					<col style="width: 9%">
					<col style="width: 9%">
					<col style="width: 40%">
																						   				   				   				   					   	    		
				</table>	
			</div>	
			
			
			
			<%-- <div class="dynamicTableContainer" style="height: 300px;">

			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px;
													font-weight: bold;">
				<tr bgcolor="#72BEFC" style="height: 24px;">
				
					<td width="1%"><input type="checkbox" id="chkSelectAll" value="N" onclick="funSelectAllCheckBoxClicked(this)"/></td>
					<td width="5%" style="padding-left: 5px">Charge Code</td>
					<td width="18%" style="padding-left: 5px">Charge Name</td>
					<td width="5%">Account Code</td>					
					<td width="5%" align="right">Amount</td>					
					<td width="4%">Type</td>					
					<td width="5%">Cr/Dr</td>					
					<td width="10%">Narration</td>					
					
				</tr>
			</table>

			<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">

				<table id="tblChargeProcessing"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">

					<tbody>
					<col style="width: 1%">
					<col style="width: 5%" style="padding-left: 5px">
					<col style="width: 18%" style="padding-left: 5px">
					<col style="width: 5%">
					<col style="width: 5%">
					<col style="width: 4%">
					<col style="width: 5%">
					<col style="width: 10%">
					</tbody>					
				</table>
			</div>
		</div> --%>
			
        
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funCheckValidation()"/>
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
