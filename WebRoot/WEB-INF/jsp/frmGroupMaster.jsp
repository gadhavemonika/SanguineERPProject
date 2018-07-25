<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <!-- charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> -->
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/hindiTextBox.js"/>"></script> --%>
<title>GROUP MASTER</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>
<%-- <script src="http://www.hinkhoj.com/common/js/keyboard.js"></script> --%>
 <%-- <script src="hindiTyping.js"></script> --%>
         <link rel="stylesheet" type="text/css" href="http://www.hinkhoj.com/common/css/keyboard.css" /> 
  
<script type="text/javascript">

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
    resetForms('grpForm');
    $("#txtGroupName").focus();
}); 

/**
 * AutoComplte when user Type the Name then Display Exists Group Name
 */
 $(document).ready(function()
			{
				$(function() {
					
					$("#txtGroupName").autocomplete({
					source: function(request, response)
					{
						var searchUrl=getContextPath()+"/AutoCompletGetGroupName.html";
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
			});


	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		$("#txtGroupName").focus();
    }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetData(code)
		{
			$("#txtGroupCode").val(code);
			var searchurl=getContextPath()+"/loadGroupMasterData.html?groupCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strGCode=='Invalid Code')
				        	{
				        		alert("Invalid Group Code");
				        		$("#txtGroupCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtGroupName").val(response.strGName);
					        	$("#txtGroupDesc").val(response.strGDesc);
					        	$("#marathiName").val(response.strMarathiName);
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
		
		/**
		*   Attached document Link
		**/
		$(function()
		{
		
			$('a#baseUrl').click(function() 
			{
				if($("#txtGroupCode").val().trim()=="")
				{
					alert("Please Select Group Code");
					return false;
				}
				window.open('attachDoc.html?transName=frmGroupMaster.jsp&formName=Group Master&code='+$('#txtGroupCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
			/**
			* On Blur Event on TextField
			**/
			$('#txtGroupCode').blur(function() 
			{
					var code = $('#txtGroupCode').val();
					if (code.trim().length > 0 && code !="?" && code !="/")
					{				
						funSetData(code);							
					}
			});
			
			$('#txtGroupName').blur(function () {
				 var strGName=$('#txtGroupName').val();
			      var st = strGName.replace(/\s{2,}/g, ' ');
			      $('#txtGroupName').val(st);
				});
			
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
		
		/**
		*  Check Validation Before Saving Record
		**/
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			if($('#txtGroupCode').val()=='')
			{
				//var txtToBillNo=document.getElementById("txtToBillNo").value;
				 var marathiName=document.getElementById("marathiName").value;
				/* $("#hidMarathiText").val(marathiName);  */
				
				var code = $('#txtGroupName').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkGroupName.html?groupName="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("Group Name Already Exist!");
				        			$('#txtGroupName').focus();
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
			return flg;
		}
		

		
</script>


</head>

<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Group Master</label>
	</div>
	<s:form name="grpForm" method="POST" action="saveGroupMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<th align="right" colspan="2"><a id="baseUrl"
					href="#"> Attach Documents</a>&nbsp; &nbsp; &nbsp;
						&nbsp;</th>
			</tr>
			<tr>
				<td width="140px">Group Code</td>
				<td><s:input id="txtGroupCode" path="strGCode"
						cssClass="searchTextBox" ondblclick="funHelp('group')" /></td>
			</tr>
			<tr>
				<td><label>Group Name</label></td>
				<td><s:input colspan="3" type="text" id="txtGroupName" 
						name="txtGroupName" path="strGName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path="strGName"></s:errors></td>
			</tr>
			<tr>
				<td><label>Description </label></td>
				<td><s:input colspan="3" id="txtGroupDesc" name="txtGroupDesc"
						cssStyle="text-transform: uppercase;" path="strGDesc" cssClass="longTextBox" autocomplete="off" /> </td>
			</tr>
			<%-- <tr>
				<td><label>Hindi Name</label></td>
					<td> 
					<script type="text/javascript">
	            		CreateHindiTextBox("marathiName",60);
	        		</script>
	       		 </td>
			</tr> --%>
			<tr>
			<%-- <td><s:input type="hidden" id="hidMarathiText" path="strMarathiText"></s:input> --%>
			</td>
			<td></td>
			</tr>
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"
				onclick="return funCallFormAction('submit',this);" /> <input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>