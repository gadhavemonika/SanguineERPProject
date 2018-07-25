<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="sp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fund Flow</title>
<script type="text/javascript">
	
	var ffData;
	var rowCountList=0;
   //Set tab which have Active on form loding
	$(document).ready(function()
	{
		
		//Ajax Wait 
	 	$(document).ajaxStart(function()
	 	{
		    $("#wait").css("display","block");
	  	});
	 	
		$(document).ajaxComplete(function()
		{
		    $("#wait").css("display","none");
		});	  
		
		
		$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate").datepicker('setDate','today');
		$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtToDate").datepicker('setDate', 'today');
		
		
	});
	
</script>

	<script type="text/javascript">
	
	
		
		//Open purchase help
		function funOpenHelp()
		{
			if ($("#cmbAgainst").val() == 'Purchase Order')
			{
				var POCode=$("#cmbPODoc").val();
				//alert(POCode);
				if(POCode.trim().length>0)
				{
					fieldName = "prodforPO";
					transactionName=fieldName;
				//	window.showModalDialog("searchform.html?formname="+transactionName+"&POCode="+POCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
					window.open("searchform.html?formname="+transactionName+"&POCode="+POCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
				}
			}
			else
			{
				funHelp("productInUse");
			}
		}
		
	
		
		function funLoadFundFlow()
		{
			var fromDate = $("#txtFromDate").val();
			var toDate = $("#txtToDate").val();
			$.ajax({
				type : "GET",
				url : getContextPath()
						+ "/loadFundFlowData.html?fromDate=" + fromDate+"&toDate="+toDate,
				dataType : "json",
				success : function(response) {
				//	funRemoveProductRows();
					
					ffData = response;
					showTable();
				
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
		
		function showTable()
		{
			var optInit = getOptionsFromForm();
		    $("#Pagination").pagination(ffData.length, optInit);	
		    
		}
		
		var items_per_page = 10;
		function getOptionsFromForm()
		{
		    var opt = {callback: pageselectCallback};
			opt['items_per_page'] = items_per_page;
			opt['num_display_entries'] = 10;
			opt['num_edge_entries'] = 3;
			opt['prev_text'] = "Prev";
			opt['next_text'] = "Next";
		    return opt;
		}
		
		function pageselectCallback(page_index, jq)
		{
		    // Get number of elements per pagionation page from form
		    var max_elem = Math.min((page_index+1) * items_per_page, ffData.length);
		    var newcontent="";
		    var Bal=0;
		   		    	
			   	newcontent = '<table id="tblProductPag" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;">'
			   	+'<tr bgcolor="#75c0ff">'
			   	+'<td width="10%">Transection Type</td><td width="8%">Doc Date</td><td width="10%">Ref No</td><td width="10%">Supp/Cust Code</td>'
			   	+'<td width="22%">Supp/Cust Name</td><td width="8%">Pay Date</td><td width="6%">Amount</td><td width="6%">Balance</td>'
			   	+'</tr>';
			   	
			   	// Iterate through a selection of the content and build an HTML string
			   	if($("#cmbReportType").val()=='Both')
			   		{
				   	  for(var i=page_index*items_per_page;i<max_elem;i++)
					    {
					    	
					    	if(ffData[i].strTransectionType=='Purchase Order')
					    		{
					    			Bal=Bal-parseFloat(ffData[i].dblAmt);
					    		}else
					    		{
					    			Bal=Bal+parseFloat(ffData[i].dblAmt);
					    		}
					    	
					        newcontent += '<tr><td>'+ "<input name=\"listFF["+(i)+"].strTransectionType\" readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"txtTransectionType."+(i)+"\" value='"+ffData[i].strTransectionType+"' /></td>";
					        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dteDoc\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"dteDoc."+(i)+"\" value='"+ffData[i].dteDoc+"' /></td>";
					        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].strRefNo\"    required = \"required\" size=\"10%\" style=\"width:100%\" readonly=\"readonly\" class=\"Box\" id=\"strRefNo."+(i)+"\" /> <a href=\"#\" onclick=\"funClick('"+ffData[i].strRefNo+"','"+ffData[i].strTransectionType+"');\" >"+ffData[i].strRefNo+"</a></td>"; 
					        newcontent += '<td>'+ "<input type=\"text\" name=\"listFF["+(i)+"].strPartyCode\"  readonly=\"readonly\"  required = \"required\" size=\"8%\" style=\"width:100%\" class=\"Box\" id=\"txtPartyCode."+(i)+"\" value='&nbsp;&nbsp;"+ffData[i].strPartyCode+"'></td>";
					        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].strPartyName\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\width:100%\" size=\"22%\" class=\"Box\" id=\"txtPartyName."+(i)+"\" value='"+ffData[i].strPartyName+"' /></td>";
					        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dtePay\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\"width:100%\" size=\"6%\" class=\"Box\" id=\"txtdtePay."+(i)+"\" value='"+ffData[i].dtePay+"'  /></td>";
					        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dblAmt\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"6%\" class=\"Box\" id=\"txtdblAmt."+(i)+"\" value='&nbsp;&nbsp;"+ffData[i].dblAmt+"'></td>";
						   
					        newcontent += '<td>'+ "<input  size=\"8%\" style=\"text-align: right;\" class=\"Box\"  readonly=\"readonly\" id=\"txtBal."+(i)+"\" value="+parseFloat(Bal).toFixed(maxAmountDecimalPlaceLimit)+" /></td>";
	
					        rowCountList++;
					    }
			   		}
				if($("#cmbReportType").val()=='Purchase Order')
		   		{
					
					 for(var i=page_index*items_per_page;i<max_elem;i++)
					    {
					    	
					    	if(ffData[i].strTransectionType=='Purchase Order')
					    		{
					    			Bal=Bal+parseFloat(ffData[i].dblAmt);
					    			
					    				newcontent += '<tr><td>'+ "<input name=\"listFF["+(i)+"].strTransectionType\" readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"txtTransectionType."+(i)+"\" value='"+ffData[i].strTransectionType+"' /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dteDoc\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"dteDoc."+(i)+"\" value='"+ffData[i].dteDoc+"' /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].strRefNo\"    required = \"required\" size=\"10%\" style=\"width:100%\" readonly=\"readonly\" class=\"Box\" id=\"strRefNo."+(i)+"\" /> <a href=\"#\" onclick=\"funClick('"+ffData[i].strRefNo+"','"+ffData[i].strTransectionType+"');\" >"+ffData[i].strRefNo+"</a></td>"; 
								        newcontent += '<td>'+ "<input type=\"text\" name=\"listFF["+(i)+"].strPartyCode\"  readonly=\"readonly\" required = \"required\" size=\"8%\" style=\"width:100%\" class=\"Box\" id=\"txtPartyCode."+(i)+"\" value='&nbsp;&nbsp;"+ffData[i].strPartyCode+"'></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].strPartyName\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\width:100%\" size=\"22%\" class=\"Box\" id=\"txtPartyName."+(i)+"\" value='"+ffData[i].strPartyName+"' /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dtePay\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\"width:100%\" size=\"6%\" class=\"Box\" id=\"txtdtePay."+(i)+"\" value='"+ffData[i].dtePay+"'  /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dblAmt\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"6%\" class=\"Box\" id=\"txtdblAmt."+(i)+"\" value='&nbsp;&nbsp;"+ffData[i].dblAmt+"'></td>";
									   
								        newcontent += '<td>'+ "<input  size=\"8%\" style=\"text-align: right;\" class=\"Box\"  readonly=\"readonly\"  id=\"txtBal."+(i)+"\" value="+parseFloat(Bal).toFixed(maxAmountDecimalPlaceLimit)+" /></td>";
				
								        rowCountList++;
					    		
					    		}
					    }
		   		}
				
				if($("#cmbReportType").val()=='Sales Order')
		   		{
					
					 for(var i=page_index*items_per_page;i<max_elem;i++)
					    {
					    	
					    	if(ffData[i].strTransectionType=='Sales Order')
					    		{
					    			Bal=Bal+parseFloat(ffData[i].dblAmt);
					    			
					    				newcontent += '<tr><td>'+ "<input name=\"listFF["+(i)+"].strTransectionType\" readonly=\"readonly\" class=\"Box\" size=\"12%\" id=\"txtTransectionType."+(i)+"\" value='"+ffData[i].strTransectionType+"' /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dteDoc\" readonly=\"readonly\" class=\"Box\" size=\"8%\" id=\"dteDoc."+(i)+"\" value='"+ffData[i].dteDoc+"' /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].strRefNo\"   readonly=\"readonly\"   required = \"required\" size=\"10%\" style=\"width:100%\" readonly=\"readonly\" class=\"Box\" id=\"strRefNo."+(i)+"\" /> <a href=\"#\" onclick=\"funClick('"+ffData[i].strRefNo+"','"+ffData[i].strTransectionType+"');\" >"+ffData[i].strRefNo+"</a></td>"; 
								        newcontent += '<td>'+ "<input type=\"text\" name=\"listFF["+(i)+"].strPartyCode\"   readonly=\"readonly\" required = \"required\" size=\"8%\" style=\"width:100%\" class=\"Box\" id=\"txtPartyCode."+(i)+"\" value='&nbsp;&nbsp;"+ffData[i].strPartyCode+"'></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].strPartyName\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\width:100%\" size=\"22%\" class=\"Box\" id=\"txtPartyName."+(i)+"\" value='"+ffData[i].strPartyName+"' /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dtePay\" type=\"text\"   readonly=\"readonly\" required = \"required\" style=\"width:100%\" size=\"6%\" class=\"Box\" id=\"txtdtePay."+(i)+"\" value='"+ffData[i].dtePay+"'  /></td>";
								        newcontent += '<td>'+ "<input name=\"listFF["+(i)+"].dblAmt\" type=\"text\"  readonly=\"readonly\"  required = \"required\" style=\"text-align: right;width:100%\" size=\"6%\" class=\"Box\" id=\"txtdblAmt."+(i)+"\" value='&nbsp;&nbsp;"+ffData[i].dblAmt+"'></td>";
									   
								        newcontent += '<td>'+ "<input  size=\"8%\" style=\"text-align: right;\" class=\"Box\"  readonly=\"readonly\"  id=\"txtBal."+(i)+"\" value="+parseFloat(Bal).toFixed(maxAmountDecimalPlaceLimit)+" /></td>";
				
								        rowCountList++;
					    		
					    		}
					    }
		   		}
			   	
			   	
			   	
			  
			    document.all[ 'divValueTotal' ].style.display = 'block';
			    $("#txtTotValue").val(parseFloat(Bal).toFixed(maxAmountDecimalPlaceLimit));  
			   	
			    newcontent += '</table>';
			    $('#Searchresult').html(newcontent);
			   
		   	}
	
		
		function funClick(strDocCode,strTranstype)
		{
			var invoiceformat='<%=session.getAttribute("invoieFormat").toString()%>';
			
			if(strTranstype=='Sales Order')
				{
					
				window.open(getContextPath()+"/openRptSalesOrderSlip.html?rptSOCode="+strDocCode,'_blank');
			
				}else
					{
						window.open(getContextPath() + "/openPOSlip.html?rptPOCode=" + strDocCode,'_blank');
					}
			
		}
		
		
	</script>
</head>

<body>
<div id="formHeading">
		<label>Fund Flow</label>
	</div>
	<s:form name="fundFlow" method="POST" action="showFundFlow.html?saddr=${urlHits}">
	
		<br>
		
		<table  class="transTable">
			<tr>
			
			 <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dteFromDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteFromDate"></s:errors>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td colspan="2">
			            <s:input id="txtToDate" name="toDate" path="dteToDate" cssClass="calenderTextBox"/>
			        	<s:errors path="dteToDate"></s:errors>
			        </td>
			 <tr>       
			  <%--  <td><label id="lblSupplier">Supplier</label></td>
			        <td>
			            <s:input id="txtSuppName" name="txtSupp" path="strSuppCode" cssClass="searchTextBox" placeholder="All Suppliers" /> </td>   
			            <td><label id="lblSuppName"></label></td> --%>
			        
			        <td >Transection Type</td>
					<td colspan="3">
						<s:select path="strReportType" id="cmbReportType" cssClass="BoxW124px">
						<option value="Both">Both</option>
							<option value="Sales Order">Sales Order</option>
							<option value="Purchase Order">Purchase Order</option>
						</s:select>
					</td>
			</tr>
			
			<tr>
			<td colspan="4">
			<input id="btnExcuete" type="button" value="Execute" onclick="funLoadFundFlow();" class="smallButton"  />
			</td>
			
			</tr>
			<tr>
			</tr>
			</table>
			<table style="border: 0px solid black; width: 100%; height: 100%; margin-left: auto; margin-right: auto; background-color: #C0E4FF;">
			
				<tr>
				
				<td>
 				<dl id="Searchresult" style="width: 100%;overflow:auto;"></dl>
 				<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
		
				</div>
				
				</td>
				
				</tr>
				
				
			</table>
	
		<br><p align="center">
			
		</p><br><br>
		<div id="divValueTotal" style="display: none">
		<table id="tblTotalFlash" class="transTablex" style="width: 95%;font-size:11px;font-weight: bold;">
		<tr style="margin-left: 28px">
			<td id="labld26" width="80%" align="right">Total Value</td>
			<td id="tdTotValue" width="10%" align="right">
			<input id="txtTotValue" readonly="readonly" style="width: 100%;text-align: right;" class="Box"></input></td>
			
			</tr>
		</table>
		</div>
		
		
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
	<script type="text/javascript">
	/* funApplyNumberValidation();
	funOnChange(); */
	
	
	</script>
</body>
</html>