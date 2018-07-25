<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Department Master</title>


<script type="text/javascript">

		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	  
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
				if($("#txtDeptCode").val().trim()=="")
				{
					alert("Please Select Dept Code");
					return false;
				}
			   window.open('attachDoc.html?transName=frmDepartmentMaster.jsp&formName=Department Master&code='+$('#txtDeptCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			/**
			* On Blur Event on deptCode Textfield
			**/
			$('#txtDeptCode').blur(function() 
			{
					var code = $('#txtDeptCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/")
					{				
						funSetData(code);
					}
			});
			
			$('#txtDeptName').blur(function () {
				 var strDName=$('#txtDeptName').val();
			      var st = strDName.replace(/\s{2,}/g, ' ');
			      $('#txtDeptName').val(st);
				});
		});
		
		
		/**
		 *  Check Validation Before Saving Record
		 **/
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			if($('#txtDeptDesc').val()=='')
			{
				alert('Enter Department Name ');
				flg=false;								  
			}
			return flg;
		}
		
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Dept Code)
		**/
		function funSetData(code)
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
				        		$("#txtDeptCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtDeptDesc").val(response.strDeptDesc);
					        	$("#cmbOperational").val(response.strOperational);
					        	$("#cmbRevenueProducing").val(response.strRevenueProducing);
					        	$("#cmbDiscount").val(response.strDiscount);
					        	$("#cmdDeactivate").val(response.strDeactivate);
					        	$("#cmbType").val(response.strType);
					        	$("#txtDeptDesc").focus();
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
				alert("Data Save successfully \n\n"+message);
			<%
			}}%>
		});
		
		
</script>

</head>
<body>
	<div id="formHeading">
		<label>Department Master</label>
	</div>
	
	<s:form name="Dept" method="POST" action="saveDepartmentMaster.html?" >
	
		<table class="masterTable">
		
		   <tr>
				<th align="right" colspan="2"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp;</th>
			</tr>
		
			<tr>
			    <td><label>Department</label></td>
				<td><s:input id="txtDeptCode" path="strDeptCode" cssClass="searchTextBox" ondblclick="funHelp('deptCode')" /></td>				
			</tr>
			
			<tr>
			    <td><label>Department Desc</label></td>
				<td><s:input id="txtDeptDesc" path="strDeptDesc" cssClass="longTextBox" /></td>				
			</tr>
			
			<tr>
				 <td><label>Operational</label></td>
				 <td >
				 <s:select id="cmbOperational" path="strOperational" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>			           
			        <option value="N">No</option>
		         </s:select>
				</td>						
			</tr>
			
			<tr>
				 <td><label>Revenue Producing</label></td>
				 <td  >
				 <s:select id="cmbRevenueProducing" path="strRevenueProducing" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>			           
			        <option value="N">No</option>
		         </s:select>
				</td>
			</tr>
			
			<tr>
				 <td><label>Discount</label></td>
				 <td  >
				 <s:select id="cmbDiscount" path="strDiscount" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>
			        <option value="N">No</option>
		         </s:select>
				</td>
			</tr>
			
			<tr>
				 <td><label>Deactivate</label></td>
				 <td  >
				 <s:select id="cmdDeactivate" path="strDeactivate" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>
			        <option value="N">No</option>
		         </s:select>
				</td>
			</tr>
			
			<tr>
				 <td><label>Type</label></td>
				 <td  >
				 <s:select id="cmbType" path="strType" cssClass="BoxW124px">
				    <option selected="selected" value="Y">Yes</option>			           
			        <option value="N">No</option>
		         </s:select>
				</td>	
			</tr>
			
		</table>
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funCallFormAction('submit',this);" />
            <input type="reset" value="Reset" class="form_button" />           
		</p>
	</s:form>
	
</body>
</html>
