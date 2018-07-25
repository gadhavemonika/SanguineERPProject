<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="default.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SUB GROUP MASTER</title>	
<script type="text/javascript">
$(document).ready(function(){
	 resetForms('subgrpForm');
	    $("#txtSubgroupName").focus();	
});
</script>
	<script type="text/javascript">
	
	 $(document).ready(function()
				{
					$(function() {
						$("#txtSubgroupName").autocomplete({
						source: function(request, response)
						{
							var searchUrl=getContextPath()+"/AutoCompletGetSubGroupName.html";
							$.ajax({
							url: searchUrl,
							type: "POST",
							data: { term: request.term },
							dataType: "json",
							 
								success: function(data) 
								{
									response($.map(data, function(v,i)
									{
										return {
											label: v,
											value: v
											};
									}));
								}
							});
						}
					});
					});
					

				    $("#chkExciseable").click(function() {
				        if (this.checked) {
				        	$("#hidExciseable").val('Y');
				        }else
				        	{
				        	$("#hidExciseable").val('N');
				        	}
				    });
					
					
					
				});


	
	
		var fieldName1;		
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
			$("#txtSubgroupName").focus();	

		});
		
		function funSetGroup(code)
		{
				 $.ajax({
					        type: "GET",
					        url: getContextPath()+"/loadGroupMasterData.html?groupCode="+code,
					        dataType: "json",
					        success: function(response)
					        {
					        	if(response.strGCode=='Invalid Code')
					        	{
					        		alert("Invalid Group Code");
					        		$("#txtGroupCode").val('');
					        		$("#lblgroupname").text('');
					        		$("#txtGroupCode").focus();
					        	}
					        	else
					        	{
					        		$("#txtGroupCode").val(response.strGCode);
						        	$("#lblgroupname").text(response.strGName);						    
						        	$("#txtGroupName").focus();
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
		
		function funSetSubGroup(code)
		{
			$("#txtSubgroupCode").val(code);
			 
			$.ajax({
			        type: "GET",
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strSGCode=='Invalid Code')
			        	{
			        		alert("Invalid SubGroup Code");
			        		$("#txtSubgroupCode").val('');
			        		$("#txtSubgroupCode").focus();
			        	}
			        	else
			        		{
				        	$("#txtSubgroupName").val(response.strSGName);
				        	$("#txtSubgroupDesc").val(response.strSGCode);
				        	$("#txtGroupCode").val(response.strGCode);
				        	$("#txtChapterNo").val(response.strExciseChapter);
				        	if(response.strExciseable=='Y')
					    	{
					    		document.getElementById("chkExciseable").checked=true;
					    		$("#hidExciseable").val('Y');
					    	}
					    	else
					    	{
					    		document.getElementById("chkExciseable").checked=false;
					    		$("#hidExciseable").val('N');
					    	}
				        	
				        	funSetGroup(response.strGCode);
				        	$("#txtSortingNo").val(response.intSortingNo);
				        	
			        		}
					},
			        error: function(e)
			        {				        	
			        	alert("Invalid SubGroup Code");
		        		$("#txtSubgroupCode").val('');
			        }
		      });
		}
	
		function funHelp(transactionName)
		{
			fieldName1=transactionName;	
			
			
			if(fieldName1=='subgroup')
			{
				gurl=getContextPath()+"/loadSubGroupMasterData.html?subGroupCode=";
			}
			else
			{
				gurl=getContextPath()+"/loadGroupMasterData.html?groupCode=";				
			}			
	       // window.open("searchform.html?formname="+transactionName+"&searchText=", 'window', 'width=600,height=600');
	     //   window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		function funSetData(code)
		{
			switch (fieldName1) 
			{
			   
			   case 'subgroup':
			    	funSetSubGroup(code);
			        break;
			   
			   case 'group':
			    	funSetGroup(code);
			        break;
			}
			
		}
		$(function()
				{
					
				    
					$('a#baseUrl').click(function() 
					{
						if($("#txtSubgroupCode").val().trim()=="")
						{
							alert("Please Select Sub Group Code");
							return false;
						}
 
						 window.open('attachDoc.html?transName=frmSubGroupMaster.jsp&formName=SubGroup Master&code='+$('#txtSubgroupCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
                   });
 					
					$('#txtSubgroupCode').blur(function(){						
						    var code=$('#txtSubgroupCode').val();
						    if (code.trim().length > 0 && code !="?" && code !="/") {
						  		funSetSubGroup(code);
						  		}
						  
					});							
					$('#txtGroupCode').blur(function(){						
						    var code=$('#txtGroupCode').val();
						    if (code.trim().length > 0 && code !="?" && code !="/") {
						  		funSetGroup(code);
						  	}						  
					});	
					
					$('#txtSubgroupName').blur(function () {
						 var strSGName=$('#txtSubgroupName').val();
					      var st = strSGName.replace(/\s{2,}/g, ' ');
					      $('#txtSubgroupName').val(st);
						});
				});
		
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			if($('#txtSubgroupCode').val()=='')
			{
				var code = $('#txtSubgroupName').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checksubGroupName.html?subgroupName="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("SubGroup Name Already Exist!");
				        			$('#txtSubgroupName').focus();
				        			flg= false;
					    		}
					    	else
					    		{
					    			flg=true;
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
			//alert("flg"+flg);
			return flg;
		}
	</script>

</head>
<body onload="funResetFields()">
<div id="formHeading">
		<label>SubGroup Master</label>
	</div>
	<s:form name="subgrpForm" method="POST" action="saveSubGroupMaster.html?saddr=${urlHits}">
		<br />
		<br />
		<table class="masterTable">
		
			<tr>
		        <th align="right" colspan="2"> <a id="baseUrl" href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp; </th>
		    </tr>
		   
		    
		    <tr>
		        <td width="120px"><s:label path="strSGCode" >Sub-Group Code</s:label></td>
		        <td><s:input id="txtSubgroupCode" name="txtSubgroupCode" path="strSGCode" ondblclick="funHelp('subgroup')"  cssClass="searchTextBox" /></td>
		    </tr>
		    	
		    <tr>
		        <td>
		        	<s:label path="strSGName">Sub-Group Name</s:label>
		        </td>
		        <td>
		        	<s:input id="txtSubgroupName" name="txtSubgroupName"  cssStyle="text-transform: uppercase;" size="80px" path="strSGName" required="true" cssClass="longTextBox"/>
		        	<s:errors path="strSGName"></s:errors>
		        </td>
		    </tr>
			    
		    <tr>
			    <td>
			    	<s:label path="strSGDesc">Description</s:label>
			    </td>
			    
			    <td>
			    	<s:input id="txtSubgroupDesc" name="txtSubgroupDesc" autocomplete="off" cssStyle="text-transform: uppercase;" cssClass="longTextBox" path="strSGDesc" />
			    </td>
			    
			  <tr> 
			    <td><s:label path="strGCode" >Group Code</s:label></td>
		        <td><s:input type="text" id="txtGroupCode" name="txtGroupCode" autocomplete="off" path="strGCode"    ondblclick="funHelp('group')" required="true" cssClass="searchTextBox" /><label id="lblgroupname"></label></td>
				
			</tr>
			   
			<tr>
			    <td><label  >Exciseable</label></td>
			    <td><input type="checkbox"  id="chkExciseable"   /></td>
									
		    </tr>
		    <tr>
			    <td>
			    	<label>Chapter No.</label>
			    </td>
			    
			    <td>
			    	<s:input id="txtChapterNo" path="strExciseChapter" name="txtChapterNo" cssStyle="text-transform: uppercase;" cssClass="longTextBox" />
			    </td>
			    </tr>
			    <tr>
			     <td><label >Sorting No.</label>  </td>
			    <td>
			    	<s:input id="txtSortingNo" path="intSortingNo" name="txtSortingNo" value=""  cssClass="longTextBox" style="width: 17%"/>
			    </td>
			  </tr> 
			  
			    <tr>
			     <td><label >Sub Group Desc Header.</label>  </td>
			    <td>
			    	<s:input id="txtSGDescHeader" path="strSGDescHeader" name="txtSGDescHeader" value=""  cssClass="longTextBox" style="width: 17%"/>
			    </td>
			  </tr> 
		    
		    
		    
		</table>
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit"  class="form_button"
				onclick="return funCallFormAction('submit',this);" /> 
				<input type="reset"
				value="Reset" class="form_button" />
		</p>
		<s:input type="hidden" id="hidExciseable" path="strExciseable"></s:input>
	</s:form>
</body>
</html>