<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>User Management</title>	
<script type="text/javascript">
$(document).ready(function(){
	resetForms("userForm");
	$("#txtUserName").focus();
	funFillModuleCombo();
});

$(document).ready(function(){
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
		alert("\n"+message);
	<%
	}}%>
	
		 
});

</script>

	<script type="text/javascript">

	   	function funResetFields(){
			$("#txtUserName").focus();
			funRemoveRows();
			//location.reload();
	   	}
	   
	   	
		function funHelp(transactionName)
		{
		    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		
		function funSetData(code)
		{
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadUserMasterData.html?userCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	if('Invalid Code' == response.strUserCode1){
			        	//alert("Invalid User Code");
			        	//resetForms("userForm");
			        	//$("#txtUserName").focus();
		        	}else{
			        	$("#txtUserCode").val(response.strUserCode1);
			        	$("#txtUserName").val(response.strUserName1);
			        	$("#txtPassword").val(response.strPassword1);
			        	$("#cmbSuperUser").val(response.strSuperUser);
			        	if(response.strRetire=='Y')
			        	{
			        		$("#cmbRetire").val('Yes');
			        	}
			        	else		        	
			        	{
			        		$("#cmbRetire").val('No');
			        	}
			        	if(response.strShowDashBoard=='Y')
				    	{
				    		document.getElementById("chkShowDashBoard").checked=true;
				    	}else{
				    		document.getElementById("chkShowDashBoard").checked=false;
				    	}
			        	if(response.strReorderLevel=='Y')
				    	{
				    		document.getElementById("chkReorderLevel").checked=true;
				    	}else{
				    		document.getElementById("chkReorderLevel").checked=false;
				    	}
			        	
			        	$("#cmbProperty").val(response.strProperty);
			        	funFillLocCombo();
			        	funFillDtlGrid(response.listUserLocDtlModel);
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
		
		
		function funFillDtlGrid(resListDtlBean)
		{
			funRemoveRows();
			$.each(resListDtlBean, function(i,item)
			{
				$.ajax({
			        type: "GET",
			        url: getContextPath()+"/loadPropAndLocName.html?propCode="+resListDtlBean[i].strPropertyCode+"&locCode="+resListDtlBean[i].strLocCode,
			        dataType: "text",
			        async:false,
			        success: function(response)
			        {
			        	var propName=response.split("#")[0];
			        	var locName=response.split("#")[1];
			        	if(funDuplicateData(resListDtlBean[i].strPropertyCode,resListDtlBean[i].strLocCode,resListDtlBean[i].strModule))
		        		{ 
			        		funAddDetailsRow(resListDtlBean[i].strPropertyCode,resListDtlBean[i].strLocCode,propName,locName,resListDtlBean[i].strModule);
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
			});
		}
		
		
		//Delete a All record from a grid
		function funRemoveRows()
		{
			var table = document.getElementById("tblUserLoc");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		
		$(function()
		{
			var propCode=$("#cmbProperty").val();
			$('a#baseUrl').click(function() 
			{
				if($("#txtUserCode").val().trim()=="")
				{
					alert("Please Select User Code");
					return false;
				}
            	window.open('attachDoc.html?transName=frmUserManagement1.jsp&formName=User Management&code='+$('#txtUserCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
   			});
			
			$('#txtUserCode').keydown(function(e) {
				if (e.which == 9) {
					
					var code = $('#txtUserCode').val();
					if (code.trim().length > 0) {
						funSetData(code);
					}
				}
			});
			
			if(propCode!=null)
				{
				funLoadLocationPropertyWise(propCode);
				}
			
		});
		
		
		function funOnLoad(){
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
		}
		
		function funFillModuleCombo()
		{
						
			$.ajax({
				type : "GET",
				url : getContextPath() + "/loadModuleName.html",
				dataType : "json",
				async :false,
				success : function(response) {
					var html = '';
					$.each(response, function(i, value) {
						html += '<option value="' + value + '">'+value
								+ '</option>';
					});
					html += '</option>';
					$('#cmbModule').html(html);
// 					$("#cmbModule").val(loggedInLocation);
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
		
		function funFillLocCombo()
		{
			var propCode=$("#cmbProperty").val();
			
			$.ajax({
				type : "GET",
				url : getContextPath() + "/loadPropertyMasterData.html?Code="+ propCode,
				dataType : "json",
				async :false,
				success : function(resp) {
					// we have the response
					if('Invalid Code' == resp.propertyCode){
						alert("Invalid Property Code");
					}else{
						$("#txtPropName").val(resp.propertyName);
						
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
			
			
			var searchUrl = getContextPath() + "/loadLocationForProperty.html?propCode="+ propCode;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[1] + '">'+value[0]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbLocation').html(html);
// 					$("#cmbLocation").val(loggedInLocation);
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
		
		function funLoadLocationPropertyWise(propCode)
		{
			var searchUrl = getContextPath() + "/loadLocationForProperty.html?propCode="+ propCode;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[1] + '">'+value[0]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbLocation').html(html);
// 					$("#cmbLocation").val(loggedInLocation);
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
		
		function funSetLocName()
		{
			var locCode=$("#cmbLocation").val().trim();
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadLocationMasterData.html?locCode="+locCode,
		        dataType: "json",
		        success: function(response)
		        {
			       	if(response.strLocCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       	}
			       	else
			       	{
			       		$("#txtLocName").val(response.strLocName);
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
		
		
	// Get Property and location fields and pass them to function to add into detail grid
		function funFillPropLocGrid() 
		{
			var propCode=$("#cmbProperty").val();
			var locCode=$("#cmbLocation").val().trim();
			var module=$("#cmbModule").val().trim();
			funAddDetailsRow(propCode,locCode,'','',module);
		}
	
		function funAddDetailsRow(propCode,locCode,propName,locName,module)
		{
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadPropAndLocName.html?propCode="+propCode+"&locCode="+locCode,
		        dataType: "text",
		        success: function(response)
		        {
		        	propName=response.split("#")[0];
		        	locName=response.split("#")[1];
		        	
		        	 if(funDuplicateData(propCode,locCode,module))
		        		{ 
		        			funInsertRow(propCode,locCode,propName,locName,module);
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
	
	
		function funInsertRow(propCode,locCode,propName,locName,module)
		{
			var proplocMod=propCode+locCode+module;
			var table = document.getElementById("tblUserLoc");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
		    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"0%\" style=\"display:none;\"  id=\"strPropNLocCode."+(rowCount)+"\" value='"+proplocMod+"' />";
			row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"35%\" id=\"strPropName."+(rowCount)+"\" value='"+propName+"' />";
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"35%\" id=\"strLocName."+(rowCount)+"\" value='"+locName+"' />";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"30%\" name=\"listUserLocDtlBean["+(rowCount)+"].strModule\" id=\"strModule."+(rowCount)+"\" value='"+module+"' />";
		    row.insertCell(4).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"2%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>";
		    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"0%\" name=\"listUserLocDtlBean["+(rowCount)+"].strPropertyCode\" id=\"strPropertyCode."+(rowCount)+"\" value='"+propCode+"' />";
		    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"0%\" name=\"listUserLocDtlBean["+(rowCount)+"].strLocCode\" id=\"strLocCode."+(rowCount)+"\" value='"+locCode+"' />";
		    
		}
		
		
		//Function to Delete Selected Row From Grid
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblUserLoc");
		    table.deleteRow(index);
		}
		
		
		/* function funValidateFields()
		{
			code=$("#txtUserCode").val();
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadUserMasterData.html?userCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
		        	if('Invalid Code' == response.strUserCode1){
		        	alert("Invalid User Code");
		        	$("#txtUserCode").focus();
		        	return false;
		        	}else{
		        		return true;
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
		} */
		
		function funCallFormAction() 
		{
			var table = document.getElementById("tblUserLoc");
		    var rowCount = table.rows.length;
		    if(!(rowCount>0))
		    	{
		    		alert("Please Add Location in Grid");
					return false;
		       	}
			
			if($('#txtUserCode').val().trim()=="")
				{
					alert("Please Enter User Code");
					$('#txtUserCode').focus();
					return false;
				}
			if($('#txtUserName').val().trim()=="")
			{
				alert("Please Enter User Name");
				$('#txtUserName').focus();
				return false;
			}
			
			if($('#txtPassword').val().trim()=="")
			{
				alert("Please Enter Password");
				$('#txtPassword').focus();
				return false;
			}
			
		}	
		
		function funDuplicateData(strPropertyCode,strLocCode,module)
		{
			var propLocMod=strPropertyCode+strLocCode+module;
		    var table = document.getElementById("tblUserLoc");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblUserLoc tr').each(function()
				    {
				    	
				    	
					    if(propLocMod==$(this).find('input').val() )// `this` is TR DOM element
	    				{
					    			 alert(" Data Already Present in Grid");
							    	 flag=false;	
			    					    			
	    				}
					}); 
			
		    	}
		    return flag;
		}
		
		
	</script>
</head>

	<body onload="funOnload();">
	<div id="formHeading">
		<label>User Management</label>
	</div>
		<s:form action="saveUserMaster.html?saddr=${urlHits}" method="POST" name="userForm">
			<br />
			<br />
				<table class="masterTable" >
				   <tr>
		        		<th  align="right" colspan="5"> <a id="baseUrl" href="#"> Attach Documents</a> &nbsp; &nbsp; &nbsp;
						&nbsp;</th>
		    		</tr>
					<tr>
						<td width="100px"><label>User Code </label></td>
						<td colspan="4"><s:input path="strUserCode1" id="txtUserCode" ondblclick="funHelp('usermaster')" required="true" cssStyle="text-transform: uppercase;" cssClass="searchTextBox"/></td>
					</tr>
			
					<tr>
						<td><label>User Name</label></td>
						<td colspan="4"><s:input path="strUserName1" id="txtUserName" required="true" cssStyle="text-transform: uppercase; width: 117px;"   cssClass="longTextBox" /></td>
					</tr>
			
					<tr>
						<td><label>Password</label></td>
						<td colspan="4"><s:input type="password" path="strPassword1" id="txtPassword" required="true" cssStyle="width: 117px;"   cssClass="longTextBox"/></td>
					</tr>
			
					<tr>
						<td><label>Super User</label></td>
						<td colspan="2">
							<s:select path="strSuperUser" id="cmbSuperUser" cssClass="BoxW62px">
								<s:option value="No">No</s:option>
								<s:option value="YES">YES</s:option>
							</s:select>
						</td>
					
						<td><s:checkbox  label="  Show Dashboard" colspan="3" id="chkShowDashBoard" path="strShowDashBoard"  value="" /></td>					
						<td><s:checkbox  label="  Reorder level Notification" colspan="3" id="chkReorderLevel" path="strReorderLevel"  value="" /></td>
						
					</tr>
			
					<tr>
						<td><label>Retire</label></td>
						<td >
							<s:select path="strRetire" id="cmbRetire" cssClass="BoxW62px">
								<s:option value="No">No</s:option>
								<s:option value="YES">YES</s:option>
							</s:select>
						</td>
						
						<td><label>Module</label></td>
						<td colspan="2"><select id="cmbModule" Class="BoxW62px" Style="width:40%">
							</select>
						</td>
						
					</tr>

					<tr>
						<td><label>Properties</label></td>
						<td>
							<s:select path="strProperty" items="${propertyList}" id="cmbProperty" cssClass="BoxW62px" cssStyle="width:73%" onchange="funFillLocCombo();">
							</s:select>
						</td>
						
						<td><label>Locations</label></td>
						<td>
							<s:select path="strLocation" items="${listLocation}" id="cmbLocation" cssClass="BoxW62px" cssStyle="width:73%">
							</s:select>
						</td>
						
						<td colspan="3"><input type="Button" value="Add" onclick="return funFillPropLocGrid();" class="smallButton" /></td>
					</tr>					
			
					<tr>
			  			<td><label>Email Id</label></td>
			  			<td>
					       <input id="txtEmail" name="strEmail" class="longTextBox" placeholder="name@email.com" type="text" value="">
					     </td>
					     <td colspan="3"></td>
			  		</tr>
				</table>
				
				
				<div class="dynamicTableContainer" style="height: 300px;">
				<table style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
					<tr bgcolor="#72BEFC">
						
						<td style="width:20%;">Property</td>
						<td style="width:15%;">Location</td>
						<td style="width:15%;">Module</td>
						<td style="width:5%;">Delete</td>
					</tr>
				</table>
			
				<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblUserLoc"
						style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex col8-center">
						<tbody>
							<col style="width: 0%;">
							<col style="width: 100px;">
							<col style="width: 100px;">
							<col style="width: 70px;">
							<col style="width: 50px;">
							<col style="width: 0px;">
							<col style="width: 0px;">
							<col style="display:none;">
						</tbody>
					</table>
				</div>
			</div>
				
			<br /><br />
		
		<p align="center">
			<input type="submit" value="Submit"  class="form_button"
				onclick="return funCallFormAction()" /> 
				<input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
		</s:form>
	</body>
</html>