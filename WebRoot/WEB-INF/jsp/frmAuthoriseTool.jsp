<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<script>
	
		var transactionName,fieldName,selectedTransCodes;
		var count = 0;
	
		$(function ()
		{
			$("#tdData").fancytree();
			
			$("#frmAuthorise").submit(function( event )
			{
				if($("#txtTransactionCode").val()=='')
				{
					alert("Please Select Transaction Code To Delete");
					return false;
				}
			});			
			
			$("#cmbFormName").change(function() 
			{
				var value=$("#cmbFormName").val();
				funShowTransactionCode(value);
			});			
			
			$("[id^=form]").on("click", function() 
			{
				var arr=this.id.split('.');
				funShowTransactionCode(arr[1]);
			});
		});
		
		
		function funShowTransactionCode(transName)
		{
			var searchUrl="";
			
			searchUrl=getContextPath()+"/getTransactionCodes.html?transName="+transName;
			$.ajax
			({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
			    	$("#txtFormName").val(transName);
			    	funShowCodes(response);
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
		
		
		function funShowCodes(res)
		{
			var html="",docCode="",html1="";
			var level='';
			selectedTransCodes='';
			html+='<table>';
			html1+='<table id="tblTrans">';
			$.each(res, function(i,item)
			{
				docCode=res[i][0];
				html1+='<tr>';
				html1+='<td>';
				html1+='<input type="checkbox" name="chkTransCodes['+i+']" id="chk.'+i+'" value="'+docCode+'" onClick="funStateChange(this);"/>';
				html1+='<label id="chk.'+i+'">'+docCode+'</label>';
				html1+='<label>('+res[i][1]+') '+res[i][2]+'['+res[i][3]+']</label>';
				html1+='</td>';
				html1+='<td>';
				html1+='<input type="text" name="txtComments['+i+']" id="txt.'+i+'"/>';
				html1+='</td>';
				html1+='</tr>';
				
				html+='<tr><td><input type="checkbox" name="chkTransCodes['+i+']" id="chk.'+i+'" value="'+docCode+'" onClick="funStateChange(this);"/>';
				html+=docCode+',   '+res[i][1]+', ('+res[i][2]+'), '+res[i][3]+' <input type="button" id="'+docCode+'" value="View" onClick="funOpenDocument(this);">';
				html+='</td></tr>';
				html+='<tr><td>';
				html+='<label>Narration</label>';
				html+='<input type="text" name="txtComments['+i+']" id="txt.'+i+'" />';
				html+='</td></tr>';
				
				level=res[i][4];
			
			});
			html1+='</table>';
			html+='</table>';
			
			
			var div = jQuery('<div id="a'+(count++)+'"></div>').html(html);
	    	$('#trans').html("");
	    	$('#trans').append(div);
			//$("#trans").html(html);
			//$("#trans").fancytree();
	    	div.fancytree();
	    	$('#txtLevel').val(level);
		}
		
		function funOpenDocument(obj){
			var idd=obj.id;
			//alert(idd);
			var formName=$('#txtFormName').val();
			switch (formName) {
				case "frmGRN":
					window.open(getContextPath()+"/frmGRN.html?saddr=1&authorizationGRNCode="+idd);
					break;
					
				case "frmPurchaseOrder":
					window.open(getContextPath()+"/frmPurchaseOrder.html?saddr=1&authorizationPOCode="+idd);
					break;
								
				case "frmMIS":
					window.open(getContextPath()+"/frmMIS.html?saddr=1&authorizationMISCode="+idd);
					break;
					
				case "frmMaterialReq":
					window.open(getContextPath()+"/frmMaterialReq.html?saddr=1&authorizationReqCode="+idd);
					break;
					
				case "frmMaterialReturn":
					window.open(getContextPath()+"/frmMaterialReturn.html?saddr=1&authorizationMatRetCode="+idd);
					break;
					
				case "frmBillPassing":
					window.open(getContextPath()+"/frmBillPassing.html?saddr=1&authorizationBillPassingCode="+idd);
					break;
					
				case "frmPurchaseIndent":
					window.open(getContextPath()+"/frmPurchaseIndent.html?saddr=1&authorizationPICode="+idd);
					break;
					
				case "frmPurchaseReturn":
					window.open(getContextPath()+"/frmPurchaseReturn.html?saddr=1&authorizationPRCode="+idd);
					break;
					
				case "frmProduction":
					window.open(getContextPath()+"/frmProduction.html?saddr=1&authorizationProductionCode="+idd);
					break;
					
				case "frmProductionOrder":
					window.open(getContextPath()+"/frmProductionOrder.html?saddr=1&authorizationProdOrderCode="+idd);
					break;
					
				case "frmRateContract":
					window.open(getContextPath()+"/frmRateContract.html?saddr=1&authorizationRateContractCode="+idd);
					break;
					
				case "frmPhysicalStkPosting":
					window.open(getContextPath()+"/frmPhysicalStkPosting.html?saddr=1&authorizationPhyStkCode="+idd);
					break;
					
				case "frmStockAdjustment":
					window.open(getContextPath()+"/frmStockAdjustment.html?saddr=1&authorizationSACode="+idd);
					break;
					
				case "frmStockTransfer":
					window.open(getContextPath()+"/frmStockTransfer.html?saddr=1&authorizationSTCode="+idd);
					break;
					
				case "frmInovice":
					window.open(getContextPath()+"/frmInovice.html?saddr=1&authorizationInvoiceCode="+idd);
					break;
					
				case "frmSalesOrder":
					window.open(getContextPath()+"/frmSalesOrder.html?saddr=1&authorizationSOCode="+idd);
					break;
					
				case "frmSalesReturn":
					window.open(getContextPath()+"/frmSalesReturn.html?saddr=1&authorizationSRCode="+idd);
					break;
					
				case "frmDeliveryChallan":
					window.open(getContextPath()+"/frmDeliveryChallan.html?saddr=1&authorizationDCCode="+idd);
					break;
					
			}
		}
		
		function funStateChange(obj)
		{
			var idd=obj.id;
			var res1=obj.checked;
			var fieldName=obj.value;
			
			if(res1)
			{
				if(selectedTransCodes.length>0)
				{
					selectedTransCodes=selectedTransCodes+","+fieldName;
				}
				else
				{
					selectedTransCodes=fieldName;
				}
			}
			else
			{
				var arrDocCodes=selectedTransCodes.split(',');
				selectedTransCodes="";
				for(var i=0;i<arrDocCodes.length;i++)
				{
					if(fieldName==arrDocCodes[i])
					{
					}
					else
					{
						if(selectedTransCodes.length>0)
						{
							selectedTransCodes=selectedTransCodes+","+arrDocCodes[i];
						}
						else
						{
							selectedTransCodes=arrDocCodes[i];
						}
					}
				}
			}
			//alert(selectedTransCodes);
		}
		
		function funChk(obj)
		{
			var idd=obj.id;
			var res1=document.getElementById(idd).checked;
		}		
		
		
			function funSubmit_onClick()
			{
				if(selectedTransCodes!=null && selectedTransCodes!="")
					{
						return true;
					}
				else
					{
						alert("Please Select Code");
						return false;
					}
			}
		
		
	</script>

</head>
	<body>
	<div id="formHeading">
		<label>Authorization Tool</label>
	</div>
	<br/>
	
		<s:form id="frmAuthorise" method="POST" action="saveAuthorizedTrans.html?saddr=${urlHits}">
			
		   	<div style="float: left;width: 209px; height: 300px;background-color:#9FCFF5;overflow-y:auto;font-family: Arial, Helvetica, sans-serif;font-size: 14px; ">
		  		<ol>
		   			<c:forEach items="${listFormName}" var="draw" varStatus="status">
		   			<br>
		  				<li style="padding-left: 10px"> <a id="form.${draw.key}"> ${draw.value} </a> </li>
		  				
		   			</c:forEach>
		    	</ol>
		   	</div>
		   	 
		    <table>
				<tr>
					<td>
						<s:input type="hidden" id="txtLevel" path="intLevel"/>
					</td>
					
					<td>
						<s:input type="hidden" id="txtFormName" path="strFormName"/>
					</td>
				</tr>
				
				
				
				<%-- <tr>
					<td>
						<s:select id="cmbFormName" path="strFormName">
							<s:options items="${listFormName}"/>
						</s:select>
					</td>
				</tr>--%>
			</table>
		    
		    <table id="tblTransactionCode">
		    
		    </table>
		    
		    <div id="trans" style="margin-left: 235px;overflow-y: scroll;">
		    
		    </div>
		    
		     <br/> 
		    
		     <br/>
		     
		   		<p align="center">
			    		<input type="submit" id="btnSubmit" class="form_button"  value="SUBMIT" onclick="return funSubmit_onClick();"/>
			    		<input type="button" id="btnReset1" class="form_button"  value="RESET" onclick="funChk();"/>
			  	</p>
		</s:form>
		<c:if test="${!empty StkAdjCode}">
<script type="text/javascript">
	alert('${StkAdjCode}');
</script>
</c:if> 
	</body>
</html>