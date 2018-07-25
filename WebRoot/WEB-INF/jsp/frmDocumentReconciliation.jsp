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
	
		var transactionName,fieldName;
	
		$(function ()
		{			
			$( "#txtFromDate" ).datepicker();
			$( "#txtToDate" ).datepicker();
			
			funSetTransCodeHelp($("#cmbFormName").val());
			
			$("#btnSubmit").click(function( event )
			{	
				if($("#txtTransactionCode").val()=='')
				{
					alert("Please Select Transaction Code");
					return false;
				}
				
				funGenerateDocRecoTree($("#cmbFormName").val(),$("#txtTransactionCode").val(),$("#cmbType").val());
			});
			
			$("#btnReset").click(function( event )
			{
				$('#divDocReco').html("");
			});
			
			$("#cmbFormName").change(function() 
			{
				var value=$("#cmbFormName").val();
				funSetTransCodeHelp(value);
			});
			
		});
		
		
		function funOpenDocument(obj)
		{			
			var idd=obj.id;
			var spTrans=idd.split(' ');
			
			var transName=idd.substring(idd.lastIndexOf('#')+1,idd.length);
			//alert(transName+'    '+spTrans[0]);
			window.open(getContextPath()+"/setReportFormName.html?docCode="+spTrans[0]+","+transName+"");
		}		
		
		var count = 0;
		
		function funGenerateDocRecoTree(frmName,docCode,type)
		{
			var tree1='';
			var fromDate='';
			var toDate='';
			var param1=frmName+","+docCode+","+type;
			
			var searchUrl=getContextPath()+"/showDocReconc.html?param1="+param1+"&fDate="+fromDate+"&tDate="+toDate;
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(respBillPass)
				    {
				    	tree='<ul>';
				    	$.each(respBillPass, function(i,respGRN)
						{
				    		if(i!='Empty')
				    		{
				    			var spTrans=i.split('#');
				    			tree+='<li>'+spTrans[0] + '&nbsp;&nbsp;<input type="button" id="'+i+'" value="View" onClick="funOpenDocument(this);">';
				    			tree+='<ul>';
				    		}
				    		$.each(respGRN, function(g,respPO)
							{
				    			if(g!='Empty')
					    		{
				    				var spTrans=g.split('#');
					    			tree+='<li>'+spTrans[0] + '&nbsp;&nbsp;<input type="button" id="'+g+'" value="View" onClick="funOpenDocument(this);">';
					    			tree+='<ul>';
					    		}
				    			$.each(respPO, function(po,respPI)
								{
				    				if(po!='Empty')
				    				{
				    					var spTrans=po.split('#');
				    					//tree+='<li>'+po;
				    					tree+='<li>'+spTrans[0] + '&nbsp;&nbsp;<input type="button" id="'+po+'" value="View" onClick="funOpenDocument(this);">';
					    				tree+='<ul>';
				    				}
				    				$.each(respPI, function(pi,item)
									{
				    					var spTrans=pi.split('#');
				    					tree+='<li>'+spTrans[0] + '&nbsp;&nbsp;<input type="button" id="'+pi+'" value="View" onClick="funOpenDocument(this);">';
				    					tree+='</li>';
									});
				    				tree+='</ul>';
				    				tree+='</li>';
								});
				    			if(g!='Empty')
					    		{
					    			tree+='</ul>';
				    				tree+='</li>';
					    		}
							});
				    		if(i!='Empty')
				    		{
				    			tree+='</ul>';
			    				tree+='</li>';
				    		}
				    	});
				    	
				    	tree+='</ul>';
				    	//$('#divDocReco').html(tree);
				    	var div = jQuery('<div id="a'+(count++)+'"></div>').html(tree);
				    	$('#divDocReco').html("");
				    	$('#divDocReco').append(div);
				    	div.fancytree();
				    	//alert(tree);
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
		
		
		function funSetTransCodeHelp(value)
		{
			switch (value) 
			{
			   case 'frmGRN':
				   transactionName='grncode';
			       break;
			       
			   case 'frmMaterialReturn':
				   transactionName='MaterialReturn';
			       break;
			       
			   case 'frmOpeningStock':
				   transactionName='opstock';
			       break;
			   
			   case 'frmPurchaseIndent':
				   transactionName='PICode';
			       break;
			       
			   case 'frmPurchaseOrder':
				   transactionName='purchaseorder';
			       break;
			      
			   case 'frmWorkOrder':
				   transactionName='WorkOrder';
			       break;
			   
			   case 'frmStockTransfer':
				   transactionName='stktransfercode';
			       break;
			       
			   case 'frmBillPassing':
				   transactionName='BillPassing';
			       break;
			   
			   case 'frmMIS':
				   transactionName='MIS';
			       break;
			       
			   case 'frmMaterialReq':
				   transactionName='MaterialReq';
			       break;
			       
			       
			}
		}
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			    case 'reason':
			    	funSetReason(code);
			        break;
			    
			    case 'transaction':
			    	funSetTrasactionCode(code);
			        break;
			}
		}
		
		
		function funHelp1(field)
		{
			fieldName=field;
	        window.open("searchform.html?formname="+field+"&searchText=", 'window', 'width=600,height=600');
	    }
		
		function funHelp()
		{
			fieldName='transaction';
	        window.open("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
	    }
		
		function funSetTrasactionCode(code)
		{
			$("#txtTransactionCode").val(code);
		}
		
		
		
	</script>

</head>
	<body>
	<div id="formHeading">
		<label>Document Reconciliation Flash</label>
	</div>
		<s:form id="frmDocReconciliation" method="POST" action="showDocReconc.html">
		    <br><br>
		    <table class="masterTable">
		    <tr><th colspan="4"></th></tr>
				<tr>	
						
					<td width="90px">Form Name</td>
					<td>
						<s:select id="cmbFormName" path="strFormName" cssClass="longTextBox">
							<s:options items="${listFormName}"/>
						</s:select>
					</td>
					
					<td >Code</td>
					
					<td>
						<s:input type="text" name="code" id="txtTransactionCode" cssClass="searchTextBox" cssStyle="width:150px;background-position: 136px 2px;" path="strTransCode" ondblclick="funHelp();"/>						
					</td>
					
					<!-- <td>
						<input type="button" id="btnShow" />
					</td> -->
				</tr>
				
				<!-- 		    			    
			    <tr>
			        <td><label id="lblFromDate">From Date</label></td>
			        <td>
			            <s:input id="txtFromDate" name="fromDate" path="dteFromDate" cssClass="BoxW116px"/>
			        	<s:errors path="dteFromDate"></s:errors>
			        </td>
				        
			        <td><label id="lblToDate">To Date</label></td>
			        <td colspan="4">
			            <s:input id="txtToDate" name="toDate" path="dteToDate" cssClass="BoxW116px"/>
			        	<s:errors path="dteToDate"></s:errors>
			        </td>
			    </tr>
			     -->
			    
			    <tr>
			    	<td><label>Type</label></td>
			        <td>
			        	<s:select id="cmbType" path="strType" cssClass="longTextBox">
							<s:option value="Forward"/>
							<s:option value="Backward"/>
						</s:select>
					</td>
					<td></td>
					<td></td>
			    </tr>		    
			
				<tr>
					<td></td>
					<td></td>
					<td><input type="button" value="Execute" id="btnSubmit" class="form_button1" /></td>					
					<td><input type="reset" value="Reset" id="btnReset" class="form_button1" /></td>
				</tr>
			    
			</table>
			
		<br />
		<!-- 
		<p align="center">
			<input type="button" value="Execute" id="btnSubmit" class="form_button" />
			<input type="reset" value="Reset" id="btnReset" class="form_button" />
		</p>-->
		
		<div id="divDocReco">				
		</div>
		
<br><br>
		</s:form>
	</body>
</html>