<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<style type="text/css">
	#divFieldSelection
	{
		 width:300px;
		 height: 300px;	
		 background-color: white;
		 overflow: scroll;		 
	}
	#divCriteriaConainer 
	{
		width: 488px; 
		height: 300px;
		background-color: white;		
	}	
	#divFieldSelection  a:link
	{
    	color: #0000FF;
    	text-decoration: none;
	}
</style>
<script type="text/javascript">
	var fieldName;
	
	
	/* To Disable spellcheck For txtCriteria textarea */
	$(document).ready(function()
	{	   
	   	var textCriteria=document.getElementById("txtCriteria");
	   	textCriteria.spellcheck = false;
	   	  
	});
	/* To Reset all fields and select default Tab */
	function funResetFields()
	{
	    $("#cmbCriteriyaType").val("Parameter Based Criteria");
	    funOnChangeCriteriaType();	  
	}
	/* To check sql syntax of criteria */
	function funCheckFormula()
	{
	    if($("#txtChargeCode").val()=="" && $("#txtChargeName").val()=="")
	    {
	    	alert("Please Select Charge.");
	    	return ;
	    }
	    else
	    {
	    	var isValidSQLSyntax="";
		    var strCriteria=$("#txtCriteria").val();
		    var sqlQuery="select * from dbwebmms.vwdebtormemberdtl where "+strCriteria;	    
		   	    
			var searchurl=getContextPath()+"/checkSQLQuerySyntax.html?sqlQuery="+sqlQuery;
			$.ajax({
		        type: "GET",
		        url: searchurl,
		        dataType: "json",
		        success: function(response)
		        {          	            
		        	if(response.result=="true")
			        {
			          alert("Your SQL Statement Syntax Is Correct.");
			        }	   
			        else
			        {
			           alert(response.result);
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
	}
	/* when Enter key is press on textArea */
	$(document).ready(function()
	{
		$("#txtCriteria").keypress(function(event)
		{ 
		    var keyCode = event.keyCode;   
		    if(keyCode==13)
		    {
		    	var strCriteria=$("#txtCriteria").val();	
		    	$("#txtCriteria").val(strCriteria+"\n");
		    }	
		});
	});
	
	/* To add selected field to text criteria */
	function funCriteriaFieldSelected(selectedField)
	{
	    var a=$(selectedField).text();	   	    
	    var strCriteria=$("#txtCriteria").val();	    
	    $("#txtCriteria").val(strCriteria+" "+a);
	}
	/* To select Tab based on criteria comboBox selected value when document loaded */
	$(document).ready(function()
	{
	    funOnChangeCriteriaType();	    	   
	});	
	/* To select Tab based on criteria comboBox value */
	function funOnChangeCriteriaType()
	{	    	   
	    $(".tab_conents").hide();
	    $("ul.tabs li").removeClass("active");
	    
	    var selectedCriteria=$("#cmbCriteriyaType").val();
	    
	    if(selectedCriteria.toString().toLowerCase()=="Parameter Based Criteria".toLowerCase() || selectedCriteria.toString().toLowerCase()=="Parameter")
	    {	        	       	        
		    $("ul.tabs li:nth-child(1)").addClass("active");
		    $("#tab1").fadeIn();
	    }
	    else if(selectedCriteria.toLowerCase()=="Formula Based Criteria".toLowerCase() || selectedCriteria.toLowerCase()=="Formula")
	    {	        	         
		    $("ul.tabs li:nth-child(2)").addClass("active");
		    $("#tab2").fadeIn();		    
	    }
	}
	
	/* To add runtime anchor tag to div divFieldSelection */
    
    /* var divFieldSelection = document.getElementById("divFieldSelection");
    var aTag = document.createElement('a');
    aTag.setAttribute('href',"#");
    aTag.setAttribute('class',"fieldCriteriaLink");
    aTag.setAttribute('ondblclick',"funCriteriaFieldSelected(this)");
    aTag.innerHTML = "This is RunTime added.";
    
    var bTag = document.createElement('a');
    bTag.setAttribute('href',"#");
    bTag.setAttribute('class',"fieldCriteriaLink");
    bTag.setAttribute('ondblclick',"funCriteriaFieldSelected(this)");
    bTag.innerHTML = "This is RunTime added 2.";
    
    var brTag=document.createElement('br');
    
    divFieldSelection.appendChild(aTag);
    divFieldSelection.appendChild(brTag);
    divFieldSelection.appendChild(bTag);	 */
    
	    	
	
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
	/*  */
	function funSetChargeMasterData(chargeCode)
	{
	    $("#txtChargeCode").val(chargeCode);
		var searchurl=getContextPath()+"/loadChargeMasterData.html?chargeCode="+chargeCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strChargeCode=='Invalid Code')
			        	{
			        		alert("Invalid Charge Code");
			        		$("#txtChargeCode").val('');
			        	}
			        	else
			        	{					        	    
				        	$("#txtChargeName").val(response.strChargeName);
				        	$("#txtChargeName").focus();
				        	$("#txtAcctCode").val(response.strAcctCode);
				        	$("#txtAccountName").val(response.strAccountName);
				        	$("#cmbDimension").val(response.strDimension);
				        	$("#cmbValue1").val(response.strDimensionValue);
				        	$("#cmbValue2").val(response.strDimensionValue2);
				        	$("#cmbType").val(response.strType);
				        	$("#txtRs").val(response.dblAmt);
				        	$("#cmbCrDr").val(response.strCrDr);
				        	$("#cmbFrequency").val(response.strFreq);
				        	$("#cmbAllowEditing").val(response.strAllowEditing);
				        	$("#cmbTAXIndicator").val(response.strTaxIndicator);
				        	$("#txtRemark").val(response.strRemark);
				        	$("#cmbActive").val(response.strActive);
				        	$("#cmbOpenCharge").val(response.strOpenCharge);
				        	
				        	$("#cmbCriteria").val(response.strCriteria);   
				        	$("#cmbCondition").val(response.strCondition);
				        	$("#txtConditionValue").val(response.dblConditionValue);
				        	
				        	var selectedCriteria=response.strCriteriaType;
				    	    
				    	    if(selectedCriteria.toString().toLowerCase()=="Parameter Based Criteria".toLowerCase() || selectedCriteria.toString().toLowerCase()=="Parameter".toLowerCase())
				    	    {	        	       	        
				    	    	$("#cmbCriteriyaType").val("Parameter Based Criteria")
				    	    }
				    	    else if(selectedCriteria.toLowerCase()=="Formula Based Criteria".toLowerCase() || selectedCriteria.toLowerCase()=="Formula".toLowerCase())
				    	    {	        	         
				    	    	$("#cmbCriteriyaType").val("Formula Based Criteria")		    
				    	    }				        					        	
				        	//show saved criteria
				        	funOnChangeCriteriaType();	 
				        	$("#txtCriteria").val(response.strSql);
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
	/*  */
	function funSetAccountData(accountCode)
	{
	    $("#txtAcctCode").val(accountCode);
		var searchurl=getContextPath()+"/loadAccontCodeAndName.html?accountCode="+accountCode;
		$.ajax({
        type: "GET",
        url: searchurl,
        dataType: "json",
        success: function(response)
        {
        	if(response.strAccountCode=='Invalid Code')
        	{
        		alert("Invalid Account Code");
        		$("#txtAcctCode").val('');
        	}
        	else
        	{					        	    
	        	$("#txtAccountName").val(response.strAccountName);						        							        	
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
 /*  */
  function funSetData(code){

		switch(fieldName)
		{		
			case "chargeCode":
			    funSetChargeMasterData(code);			    
				 break;
				 
				case "accountCode":			    
				    funSetAccountData(code);						    
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
	<label>Charge Master</label>
	</div>

<br/>
<br/>

	<s:form name="ChargeMaster" method="POST" action="saveChargeMaster.html">

		<table class="masterTable">
			<tr>
			    <td style="width: 12%"><label >Charge Code</label></td>
			    <td><s:input id="txtChargeCode" path="strChargeCode" readonly="true" ondblclick="funHelp('chargeCode')" cssClass="searchTextBox"/></td>			        			        
			    <td colspan="4"><s:input id="txtChargeName" path="strChargeName" required="true" cssClass="longTextBox" cssStyle="width:68%"/></td>			    		        			   
			</tr>
			<tr>
			    <td><label >Account Code</label></td>
			    <td><s:input id="txtAcctCode" path="strAcctCode" readonly="true" ondblclick="funHelp('accountCode')" cssClass="searchTextBox"/></td>			        			        
			    <td colspan="4"><s:input id="txtAccountName" path="strAccountName" required="true" readonly="true" cssClass="longTextBox" cssStyle="width:68%" /></td>			    	    		        			  
			</tr>
			<%-- <tr>
				<td><label>Dimension</label> </td>
				<td><s:select id="cmbDimension" path="strDimension" items="${listDimension}" cssClass="BoxW124px"/></td>		
				<td><label>Value</label> </td>
				<td><s:select id="cmbValue1" path="strDimensionValue" items="${listValue1}" cssClass="BoxW124px"/></td>
				<td><s:select id="cmbValue2" path="strDimensionValue2" items="${listValue2}" cssClass="BoxW124px"/></td>
				<td></td>			
			</tr> --%>
			<tr>
				<td><label>Type</label> </td>
				<td><s:select id="cmbType" path="strType" items="${listType}" cssClass="BoxW124px"/></td>		
				<td><label>Rs.</label></td>
				<td><s:input id="txtRs" path="dblAmt" required="true" cssClass="longTextBox" /></td>
				<td><s:select id="cmbCrDr" path="strCrDr" items="${listRs2}" cssClass="BoxW124px"/></td>
				<td></td>			
			</tr>
			<tr>
				<td><label>Frequency</label> </td>
				<td><s:select id="cmbFrequency" path="strFreq" items="${listFrequency}" cssClass="BoxW124px"/></td>		
				<td><label>Allow Editing</label> </td>
				<td><s:select id="cmbAllowEditing" path="strAllowEditing" items="${listAllowEditing}" cssClass="BoxW124px"/></td>
				<td><label>TAX Indicator</label> </td>
				<td><s:select id="cmbTAXIndicator" path="strTaxIndicator" items="${listTAXIndicator}" cssClass="BoxW124px"/></td>	
			</tr>
			<tr>
				<td><label>Remarks</label></td>
				<td colspan="6"><s:input id="txtRemark" path="strRemark" required="true" cssClass="longTextBox" cssStyle="width:98%"/></td>
			</tr>
			<tr>
				<td><label>Active</label> </td>
				<td><s:select id="cmbActive" path="strActive" items="${listActive}" cssClass="BoxW124px"/></td>		
				<td><label>Open Charge</label> </td>
				<td><s:select id="cmbOpenCharge" path="strOpenCharge" items="${listOpenCharge}" cssClass="BoxW124px"/></td>
				<td  width="162px"><label>Criteriya Type</label> </td>
				<td><s:select id="cmbCriteriyaType" path="strCriteriaType" items="${listCriteriyaType}" cssClass="BoxW124px" cssStyle="width:156px" onchange='funOnChangeCriteriaType()' /></td>	
			</tr>
			<tr>
				<td><label>Criteria</label> </td>
				<td><s:select id="cmbCriteria" path="strCriteria" items="${mapCriteria}" cssClass="BoxW124px"/></td>						
				<td><s:select id="cmbCondition" path="strCondition" items="${mapCondition}" cssClass="BoxW124px"/></td>
				<td><s:input id="txtConditionValue" path="dblConditionValue" required="true" cssClass="longTextBox" /></td>
				<td colspan="2"></td>	
			</tr>
			
			
		</table>
		<br>
		<br>
		 <!-- table which holds all tabs header -->
		<table class="masterTable">
			<tr>
				<th id="tab_container" style="height: 100%;">
					<ul class="tabs" >
						<li class="active" data-state="tab1" style="width: 23.85%; cursor: default;">Parameter Based Criteria</li>
						<li data-state="tab2" style="width: 23.85%; cursor: default;">Formula Based Criteria</li>						
					</ul>
				</th>
			</tr>
		</table>
        <br>        
        <!-- Parameter Based Criteria Tab -->
        <div id="tab1" class="tab_conents">
        	<table class="masterTable">
        		<tr>
        			<td><label>Parameter Based Criteria</label></td>
        		</tr>
        	</table>
        </div>
        <!-- Formula Based Criteria Tab -->
        <div id="tab2" class="tab_conents">
        	<table class="masterTable">        
        		<tr>
        			<td style="padding-left: 0px;  width: 300px; height: 0px;">
						<div id="divFieldSelection">
							<c:forEach var="fieldCriteria" items="${listVMemberDebtorDtlColumnNames}">
								<a href="#" class="fieldCriteriaLink" ondblclick='funCriteriaFieldSelected(this)'>${fieldCriteria}</a><br>
						    </c:forEach>
						</div>
					</td>  
					<td></td>  
					<td style="width: 488px;">
						<div id="divCriteriaConainer" >
					    	<s:textarea id="txtCriteria" path="strSql" style="width: 485px; height: 300px; resize: none;" />
						</div>
					</td>         			
        		</tr>     
        		<tr>
        			<td></td>
        			<td></td>
        			<td style="text-align: center;"><input id="btnCheckFormula" type="button" value="Check Formula" onclick="funCheckFormula()" /></td>
        		</tr>   		
        	</table>
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
