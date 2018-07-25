<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>USER CARDS</title>

<script type="text/javascript">
//	var fieldName;

	$(function() 
	{
	});

	
	
	
	
	$(document).ready(function () {
		  $('input#txtUserCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtSwipeCard').mlKeyboard({layout: 'en_US'});
		}); 

	
	/**
	* Get and Set data from help file and load data Based on Selection Passing Value(User Code)
	**/
	function funSetData(code){
		$("#txtUserCode").val(code);
		var searchurl=getContextPath()+"/loadPOSUserCardSwipeData.html?POSUserCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strGCode=='Invalid Code')
			        	{
			        		alert("Invalid User Code");
			        		$("#txtUserCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtUserCode").val(response.strUserCode);
				        	$("#txtSwipeCard").val(response.strDebitCardString);
				        	$("#txtSwipeCard").focus();
				        //	$("#txtSuper").val(response.strSuper);
				        	$("#txtSuperType").val(response.strSuperType);
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

		switch(fieldName){

		}
	
		
		
		/**
		* Success Message After Saving Record
		**/
		$(document).ready(function()
		{
			var message='';
			<%if (session.getAttribute("success") != null) 
			{
				if(session.getAttribute("successMessage") != null)
				{%>
					message='<%=session.getAttribute("successMessage").toString()%>';
				    <%
				    session.removeAttribute("successMessage");
				}
				boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
				session.removeAttribute("success");
				if (test) 
				{
					%>alert("Data Saved \n\n"+message);<%
				}
			}%>
		});
		

		
		
		/**
		*  Check Validation Before Saving Record
		**/
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			
			if($('#txtUserCode').val()=='')
			{
				var code = $('#txtSwipeCard').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkSwipeCard.html?SwipeCard="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("Card Number Already Exist!");
				        			$('#txtSwipeCard').focus();
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


		/**
		* Open Help
		**/
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>User Cards</label>
	</div>

<br/>
<br/>

	<s:form name="POSUserCard" method="POST" action="savePOSUserCardSwipe.html">
	
	   <br />
		<br />
		<div id="jquery-script-menu">

		</div>

		<table class="masterTable">
			<tr>
				
				<td width="140px">User Code</td>
				<td><s:input id="txtUserCode" path="strUserCode"
						cssClass="searchTextBox jQKeyboard form-control" ondblclick="funHelp('POSUserMaster')" /></td>	
						
			</tr>
			
			<tr>	
			   
			    <td ><label>Swipe Card</label></td>		
				<td><s:input colspan="3" type="text" id="txtSwipeCard" 
						name="txtDebitCardString" path="strDebitCardString" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"   /> </td>
				
			</tr>
			
			<tr>
				<td><s:input colspan="3" type="hidden"  id="txtSuperType" value="N" name="txtSuperType" path="strSuperType"/> </td>
			</tr>
			
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
