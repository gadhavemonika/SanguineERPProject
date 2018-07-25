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
	
	
	//set name
	function funSetName(masterName,searchUrl)
	{
			$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	switch(masterName)
	    		{
	    			case "DepartmentMaster":
	    				 $("#strDeptCode").val(response.name);
	    		    	 break;
	    		    	 
	    			case "IncomeHeadMaster":
	    				 $("#strIncomeHeadCode").val(response.name);
	    		    	 break;
	    		    	 
	    			case "TaxOnTaxCode":
	    				 $("#strTaxOnTaxCode").val(response.name);
	    		    	 break;
	    		    	 
	    			case "TaxGroupMaster":
	    				 $("#strTaxGroupCode").val(response.name);
	    		    	 break;
	    		}		        	
			},
			error: function(jqXHR, exception) 
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
	
	//get name
	function funGetNameAgainstCode(masterName,code)
	{
		var clientCode='';		
		clientCode='<%session.getAttribute("clientCode").toString();%>';
		var searchUrl='';
		switch(masterName)
		{
			case "DepartmentMaster":
				 searchUrl=getContextPath()+"/getMasterNameFromCode.html?masterName=DepartmentMaster&masterCode="+code+" ";
				 funSetName(masterName,searchUrl);
		    	 break;
			case "IncomeHeadMaster":
				 searchUrl=getContextPath()+"/getMasterNameFromCode.html?masterName=IncomeHeadMaster&masterCode="+code+" ";
				 funSetName(masterName,searchUrl);
		    	 break;
			case "TaxOnTaxCode":
				 searchUrl=getContextPath()+"/getMasterNameFromCode.html?masterName=TaxOnTaxCode&masterCode="+code+" ";
				 funSetName(masterName,searchUrl);
		    	 break;
			case "TaxGroupMaster":
				 searchUrl=getContextPath()+"/getMasterNameFromCode.html?masterName=TaxGroupMaster&masterCode="+code+" ";
				 funSetName(masterName,searchUrl);
		    	 break;
		}				
	}
	
	/* set date values */
	function funSetDate(id,responseValue)
	{
		var id=id;
		var value=responseValue;
		var date=responseValue.split(" ")[0];
		
		var y=date.split("-")[0];
		var m=date.split("-")[1];
		var d=date.split("-")[2];
		
		$(id).val(d+"-"+m+"-"+y);
		
	}	
	/* To check and Set CheckBox Value To Y/N */
	function funSetCheckBoxStatusAndValue(currentCheckBox,flag)
	{			    
		var value=flag;
	    if(value.toUpperCase()=="Y")
	    {  
	    	$("#"+currentCheckBox).prop("checked",true);
	    	$("#"+currentCheckBox).val("Y");
        }
	    else	    
        {         
	    	$("#"+currentCheckBox).prop("checked",false);
	    	$("#"+currentCheckBox).val("N");
        }	    		    	   
	}	
	
	function funSetTaxMasterData(taxCode)
	{
		var searchUrl=getContextPath()+"/loadPMSTaxMasterData.html?taxCode="+taxCode;
		$.ajax({
			
			url:searchUrl,
			type :"GET",
			dataType: "json",
	        success: function(response)
	        {
	        	if(response.strTaxCode=='Invalid Code')
	        	{
	        		alert("Invalid Tax Code");
	        		$("#strTaxCode").val('');
	        	}
	        	else
	        	{
	        		$("#strTaxCode").val(response.strTaxCode);
	        		$("#strTaxDesc").val(response.strTaxDesc);
	        		$("#txtDeptCode").val(response.strDeptCode);
	        		if(response.strDeptCode!="NA" || response.strDeptCode=='')
	        			{
	        				funSetDepartment(response.strDeptCode);
	        			}
	        		$("#txtIncomeHeadCode").val(response.strIncomeHeadCode);
	        		funSetIncomeHead(response.strIncomeHeadCode);
	        		$("#dblTaxValue").val(response.dblTaxValue);
	        		$("#strTaxOn").val(response.strTaxOn);
	        		$("#cmbTaxOnType").val(response.strTaxOnType);
	        		$("#strDeplomat").val(response.strDeplomat);
	        		$("#strLocalOrForeigner").val(response.strLocalOrForeigner);
	        		funSetDate(dteValidFrom,response.dteValidFrom);
	        		funSetDate(dteValidTo,response.dteValidTo);
	        		/* $("#strTaxOnTaxCode").val(response.strTaxOnTaxCode); */
	        		if(response.strTaxOnTaxCode.length>0)
	        		{
	        			funGetNameAgainstCode("TaxOnTaxCode",response.strTaxOnTaxCode);
	        			funSetCheckBoxStatusAndValue("chkTaxOnTax","Y");
	        			funTaxOnTaxStateChange();
	        		}
	        		else
	        		{
	        			funSetCheckBoxStatusAndValue("chkTaxOnTax","N");
	        			funTaxOnTaxStateChange();
	        		}
	        		$("#strTaxOnTaxable").val(response.strTaxOnTaxable);
	        		if(response.strTaxOnTaxable.length>0)
	        		{
	        			funSetCheckBoxStatusAndValue("chkTaxOnTaxable","Y");
	        			funTaxOnTaxableStateChange();
	        		}
	        		else
	        		{
	        			funSetCheckBoxStatusAndValue("chkTaxOnTaxable","N");
	        			funTaxOnTaxableStateChange();
	        		}
	        		/* $("#strTaxGroupCode").val(response.strTaxGroupCode); */
	        		funGetNameAgainstCode("TaxGroupMaster",response.strTaxGroupCode);
	        		
	        		if(response.strAccountCode!="NA" || response.strAccountCode=='')
        			{
	        			funSetAccountCode(response.strAccountCode);
        			}
	        	}
			},
			error: function(jqXHR, exception) 
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
	
	
	function funTaxOnTaxableStateChange()
	{
		var isSelected=$("#chkTaxOnTaxable").prop('checked');
		if(isSelected==true)
		{
			$("#chkTaxOnTaxable").val("Y");
			document.getElementById("strTaxOnTaxable").disabled=false;
		}
		else
		{
			$("#chkTaxOnTaxable").val("N");
			document.getElementById("strTaxOnTaxable").disabled=true;
		}
	}
	
	function funTaxOnTaxStateChange()
	{
		var isSelected=$("#chkTaxOnTax").prop('checked');
		if(isSelected==true)
		{
			$("#chkTaxOnTax").val("Y");
			document.getElementById("strTaxOnTaxCode").disabled=false;
		}
		else
		{
			$("#chkTaxOnTax").val("N");
			document.getElementById("strTaxOnTaxCode").disabled=true;
		}
	}
	
	$(document).ready(function(){
		
		var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
		
		$("#dteValidFrom").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteValidFrom").datepicker('setDate', pmsDate);

		$("#dteValidTo").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteValidTo").datepicker('setDate', pmsDate);
		
		
		
	});

	
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
	
	
	function funSetData(code)
	{
		switch(fieldName)
		{
			case "taxCode":
			    funSetTaxMasterData(code);
			 	break;
			 
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
		        		$("#txtDeptCode").val(response.strDeptCode);
		        		$("#lblDeptDesc").text(response.strDeptDesc);
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
				        		$("#txtIncomeHeadCode").text(response.strIncomeHeadCode);
					        	$("#lblIncomeHead").text(response.strIncomeHeadDesc);
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
		
		
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Tax Master</label>
	</div>

<br/>
<br/>

	<s:form name="PMSTaxMaster" method="POST" action="savePMSTaxMaster.html">

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
			    <td><label>Tax Code</label></td>
			    <td><s:input id="strTaxCode" path="strTaxCode"  ondblclick=" funHelp('taxCode') " cssClass="searchTextBox"/></td>			        			        
			    <td colspan="3"><s:input id="strTaxDesc" path="strTaxDesc" required="true" cssClass="longTextBox"  style="width: 316px"/></td>			    		        			   
			</tr>
			<tr>
				<td><label>Department</label></td>
				<td colspan="3" >
					<s:input id="txtDeptCode" path="strDeptCode"  ondblclick=" funHelp('deptCode') " cssClass="searchTextBox"/>
				</td>
				<td><label id="lblDeptDesc"></label></td>
			</tr>
			<tr>
				<td><label>Income Head</label></td>
				<td colspan="3" >
					<s:input id="txtIncomeHeadCode" path="strIncomeHeadCode"  ondblclick=" funHelp('incomeHead') " cssClass="searchTextBox"/>
				</td>
				<td><label id="lblIncomeHead"></label></td>
			</tr>
			<tr>
				<td><label>Tax Type</label></td>
				<td><s:select id="strTaxType" path="strTaxType" items="${listTaxType}" required="true" cssClass="BoxW124px"></s:select></td>
				<td colspan="2"><s:select id="" path="" items="${listTaxType2}"  cssClass="BoxW124px"></s:select></td>
			</tr>
			<tr>
				<td><label>Percentage / Amount</label></td>
				<td>
					<s:select id="cmbTaxOnType" path="strTaxOnType"  cssClass="BoxW124px">
						<option value="Room Night" selected>Room Night</option>
						<option value="Income Head">Income Head</option>
					</s:select>				
				<td colspan="2"><s:input  id="dblTaxValue" path="dblTaxValue" required="true" cssClass="decimal-places numberField" /></td>
			</tr>
			<tr>
				<td><label>Tax On</label></td>
				<td colspan="3" ><s:select id="strTaxOn" path="strTaxOn" items="${listTaxOn}" required="true" cssClass="BoxW200px"></s:select></td>				
			</tr>
			<tr>
				<td><label>Diplomat</label></td>
				<td colspan="3" ><s:select id="strDeplomat" path="strDeplomat" items="${listDiplomat}" cssClass="BoxW124px"></s:select></td>				
			</tr>
			<tr>
				<td><label>Local/Foreigner</label></td>
				<td colspan="3" ><s:select id="strLocalOrForeigner" path="strLocalOrForeigner" items="${listLocalForeigner}" cssClass="BoxW124px"></s:select></td>				
			</tr>
			<tr>
			    <td><label>Valid From</label></td>	
			    <td><s:input type="text" id="dteValidFrom" path="dteValidFrom" required="true" class="calenderTextBox" /></td>
			    <td><label>Valid To</label></td>	
			    <td><s:input type="text" id="dteValidTo" path="dteValidTo" class="calenderTextBox" /></td>		    		  
			</tr>	
			<tr>			    
			    <td><s:checkbox label="Tax On Tax" id="chkTaxOnTax" path="" value="N" onclick=' funTaxOnTaxStateChange() '/></td>			    		    		 
			    <td colspan="3" ><s:select id="strTaxOnTaxCode" path="strTaxOnTaxCode" items="${listTaxOnTax}"  disabled="true" cssClass="BoxW200px"></s:select></td>
			</tr>	
			<tr>			    
			    <td><s:checkbox label="Tax On Taxable" id="chkTaxOnTaxable" path="" value="N" onclick="funTaxOnTaxableStateChange()" /></td>			    		    		 
			    <td colspan="3" ><s:select id="strTaxOnTaxable" path="strTaxOnTaxable" items="${listTaxOnTaxable}"  disabled="true" cssClass="BoxW200px"></s:select></td>
			</tr>	
			<tr>
				<td><label>Tax Group</label></td>
				<td colspan="3" ><s:select id="strTaxGroupCode" path="strTaxGroupCode" items="${listTaxGroup}" cssClass="BoxW200px"></s:select></td>				
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
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
