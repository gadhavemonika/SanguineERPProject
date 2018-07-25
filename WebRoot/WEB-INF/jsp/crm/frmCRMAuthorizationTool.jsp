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
			html+='<ul>';
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
				
				html+='<li><input type="checkbox" name="chkTransCodes['+i+']" id="chk.'+i+'" value="'+docCode+'" onClick="funStateChange(this);"/>';
				html+=docCode+',   '+res[i][1]+', ('+res[i][2]+'), '+res[i][3]+' <input type="button" id="'+docCode+'" value="View" onClick="funOpenDocument(this);">';
				html+='<ul><li>';
				html+='<input type="text" name="txtComments['+i+']" id="txtComment"/>';
				html+='</li></ul></li>';
				level=res[i][4];
			});
			html+='</ul>';
			html1+='</table>';
			
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
				case "frmInovice":
					window.open(getContextPath()+"/frmInovice.html?saddr=1&authorizationInvoiceCode="+idd);
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
		<s:form id="frmAuthorise" method="POST" action="saveAuthorizedTrans.html?saddr=${urlHits}">
			
		   	<div style="float: left;width: 200px; height: 445px;background-color:#9FCFF5;overflow-y:auto;font-family: Arial, Helvetica, sans-serif;font-size: 14px; ">
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
		     
		     
		    <table>
			    <tr>
			    	<td>
			    		<input type="submit" id="btnSubmit" class="form_button"  value="SUBMIT" onclick="return funSubmit_onClick();"/>
			    	</td>
			    	<td>
			    		<input type="button" id="btnReset1" class="form_button"  value="RESET" onclick="funChk();"/>
			    	</td>
			    </tr>
			</table>
		</s:form>
		<c:if test="${!empty StkAdjCode}">
<script type="text/javascript">
	alert('${StkAdjCode}');
</script>
</c:if> 
	</body>
</html>