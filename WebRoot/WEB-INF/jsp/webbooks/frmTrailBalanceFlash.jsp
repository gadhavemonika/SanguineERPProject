<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>
<script type="text/javascript" src="<spring:url value="/resources/js/pagination.js"/>"></script>
<script type="text/javascript">
var StkFlashData;
	$(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		/*$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', 'today');
		*/
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', Dat);
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtToDate" ).datepicker('setDate', 'today');
// 		var glCode = $("#txtGLCode").val();
// 		if(glCode!='')
// 		{
// 			funSetGLCode(glCode);
// 		}
		
		$("#btnExecute").click(function( event )
				{
			funCalculateTrialBalanceFlash();
				});

	});
	
			function funSetData(code){

		switch(fieldName){

			case 'creditorAccountCode' : 
				funSetGLCode(code);
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
		function funSetGLCode(code){

			$.ajax({
				type : "GET",
				url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
				dataType : "json",
				success : function(response){ 
					if(response.strVouchNo!="Invalid")
			    	{
						$("#txtGLCode").val(response.strAccountCode);
						$("#lblGLCode").text(response.strAccountName);
						$("#txtFromDebtorCode").focus();					
			    	}
			    	else
				    {
				    	alert("Invalid Receipt No");
				    	$("#txtVouchNo").val("");
				    	$("#txtVouchNo").focus();
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
		
		
		function funCalculateTrialBalanceFlash()
		{
			
			
			var fromDat=$("#txtFromDate").val();
			var toDat=$("#txtToDate").val();
			var GLCode=$("#txtGLCode").val();
			var currency=$("#cmbCurrency").val();
			var searchUrl=getContextPath()+"/rptTrialBalanceFlash.html?fromDat="+fromDat+"&toDat="+toDat+"&GLCode="+GLCode+"&currency="+currency;
			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {				    	
				    	StkFlashData=response;
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
		    $("#Pagination").pagination(StkFlashData.length, optInit);	
		    $("#divValueTotal").show();
		    
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
		    var max_elem = Math.min((page_index+1) * items_per_page, StkFlashData.length);
		    var newcontent="";
			var currValue='<%=session.getAttribute("currValue").toString()%>';
    		if(currValue==null)
    			{
    				currValue=1;
    			}	
			    	
			   	newcontent = '<table id="tblStockFlash" class="transTablex" style="width: 100%;font-size:11px;font-weight: bold;"><tr bgcolor="#75c0ff"><td id="labld1" size="10%">Account Code</td><td id="labld2">Account Name</td><td id="labld3"> Opening</td>	<td id="labld4">Debit</td>	<td id="labld5"> Credit</td><td id="labld6">Balance</td></tr>';
			   	// Iterate through a selection of the content and build an HTML string
			    for(var i=page_index*items_per_page;i<max_elem;i++)
			    {
			    	var balAmt=0.0;
			    	var opnAmt= parseFloat(StkFlashData[i].dblOpnAmt).toFixed(maxQuantityDecimalPlaceLimit);
			    	if(opnAmt<0){
			    		opnAmt="("+(opnAmt*-1)+")";
			    	}
			    	var drAmt=parseFloat(StkFlashData[i].dblDrAmt).toFixed(maxQuantityDecimalPlaceLimit);
			    	if(drAmt<0){
			    		drAmt="("+(drAmt*-1)+")";
			    	}
			    	var crAmt=parseFloat(StkFlashData[i].dblCrAmt).toFixed(maxQuantityDecimalPlaceLimit);
			    	if(crAmt<0){
			    		crAmt="("+(crAmt*-1)+")";
			    	}
			        newcontent += '<tr><td><a id="stkLedgerUrl.'+i+'" href="#" onclick="funClick(this);">'+StkFlashData[i].strAccountCode+'</a></td>';
			        newcontent += '<td>'+StkFlashData[i].strAccountName+'</td>';
			        newcontent += '<td align=right>'+opnAmt+'</td>';
			        newcontent += '<td align=right>'+drAmt+'</td>';
			        newcontent += '<td align=right>'+crAmt+'</td>';
			        
			        balAmt=parseFloat(StkFlashData[i].dblOpnAmt)+parseFloat(StkFlashData[i].dblDrAmt)-parseFloat(StkFlashData[i].dblCrAmt);
			        balAmt=parseFloat(balAmt).toFixed(maxQuantityDecimalPlaceLimit);
			        if(balAmt<0){
			        	balAmt="("+(balAmt*-1)+")";
			    	}
			        newcontent += '<td align=right>'+balAmt+'</td>';
			'</tr>';
			    }			   
		    
		
	
		    
		    newcontent += '</table>';
		    // Replace old content with new content
		   
		    $('#Searchresult').html(newcontent);
		   
		    // Prevent click eventpropagation
		    return false;
		}
		

		
	
		function funClick(obj)			
		 {
			var transactionformName="frmGeneralLedger";
			var fromDat=$("#txtFromDate").val();
			var toDat=$("#txtToDate").val();
			var glCode=document.getElementById(""+obj.id+"").innerHTML;
			var currency=$("#cmbCurrency").val();
			var debtorCode=document.getElementById(""+obj.id+"").innerHTML;

		    response=window.open("frmGeneralTrialBalanceLedger.html?formname="+transactionformName+"&glCode="+glCode+"&accountCode="+debtorCode+"&fromDat="+fromDat+"&toDat="+toDat+"&currency="+currency,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:550px;");
	       
			var timer = setInterval(function ()
				    {
					if(response.closed)
						{
							if (response.returnValue != null)
							{
								if(null!=response)
						        {
									response=response.returnValue;
						        	var count=0; 
						        }
			
							}
							clearInterval(timer);
						}
				    }, 500);
			
			}
		  
		
</script>
<body>
	<div id="formHeading">
		<label>Trial Balance  Flash</label>
	</div>

	<br />
	<br />

	<s:form name="Trial Balance Report" method="GET" action="" >
		<div>
			<table class="transTable">
<!-- 			 	<tr>	<td> -->
<!-- 					<label>GL Code</label> -->
<!-- 				</td> -->
<!-- 				<td> -->
<%-- 					<s:input type="text" id="txtGLCode" path="strAccountCode" class="searchTextBox" ondblclick="funHelp('debtorAccountCode');"/> --%>
<!-- 				</td> -->
<!-- 				<td  colspan="3"><label id="lblGLCode"></label></td></tr> -->
			    <tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
				</tr>
<!-- 				<tr> -->
<!-- 					<td><label>Currency </label></td> -->
<%-- 					<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px"> --%>
<%-- 						</s:select></td> --%>
<!-- 					<td colspan="2"></td> -->
<!-- 				</tr> -->
			</table>
			<p align="center">
				<input type="button" id="btnExecute" value="Excecute"  class="form_button" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
			
			<br/>
			<br/>
				
			<dl id="Searchresult" style="width: 95%; margin-left: 26px; overflow:auto;"></dl>
		<div id="Pagination" class="pagination" style="width: 80%;margin-left: 26px;">
		<%-- <s:input type="hidden" id="hidSubCodes" path="strCatCode"></s:input> --%>	
		</div>
		
			<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>

</body>
</html>