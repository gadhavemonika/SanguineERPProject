<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tools</title>
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
 

<script type="text/javascript">

/**
 * Global variable
 */
var member="";
var posName="";
var fromDate="";
var toDate = "";
var userName="";
var chkAllSelected="";

$(document).ready(function () {
	
	
	}); 


	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		
    }
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		
	function funOpenWindow()
	{
		
		response= window.open("frmOpenPOSConfigSetting.html","","dialogHeight:800px;dialogWidth:700px;dialogLeft:400px;")
		var timer = setInterval(function ()
			    {
				if(response.closed)
					{
						if (response.returnValue != null)
						{
									
						}
						clearInterval(timer);
					}
			    }, 500);
		
	}
	
	function funOpenWindowDBBackup()
	{
		
		response= window.open("frmOpenPOSDBBackup.html","","dialogHeight:800px;dialogWidth:700px;dialogLeft:400px;")
		var timer = setInterval(function ()
			    {
				if(response.closed)
					{
						if (response.returnValue != null)
						{
									
						}
						clearInterval(timer);
					}
			    }, 500);
		
	}
	
	
	function funPOSStructureUpdate(){
		 $.ajax({
		  type: "GET",
		  url: getContextPath()+"/posUpdateStructure.html",
		  dataType:"text",
		  async:false,
		  success: function(response){
			  alert(response);
		  },
		  error: function(){      
		   alert('Error while request..');
		  }
		 });
		}
	
	/**
	  * Open Delete Module Form and return back selected Module either Transaction or Master 
	 **/
	function funList(strType)
	{
	//	var returnVal=window.showModalDialog("frmDeleteModuleList.html?strHeadingType="+strType,"","dialogHeight:600px;dialogWidth:500px;dialogLeft:350px;dialogTop:100px");
		var returnVal=window.open("frmPOSClearMasterTransaction.html?strHeadingType="+strType,"","dialogHeight:600px;dialogWidth:500px;dialogLeft:350px;dialogTop:100px");
		
		var timer = setInterval(function ()
			    {
				if(returnVal.closed)
					{
						if (returnVal.returnValue != null)
						{
							var ret=returnVal.returnValue.members;
// 							var nm = returnVal.returnValue.posName;
							posName=returnVal.returnValue.posName;
							fromDate=returnVal.returnValue.fromDate;
							toDate = returnVal.returnValue.toDate;
							chkAllSelected = returnVal.returnValue.chkAllSelected;
							
							$.each(ret, function(i,item)
								    {
								if(ret[i].flag=="true")
						        			{
							        			if(member !=""){
						        					member=member+","+ret[i].moduleName;
						        				}else{
						        					member=ret[i].moduleName;
						        				}
						        			}
								    }); 
						
								funCheckUser(strType);		
		
						}
						clearInterval(timer);
					}
			    }, 500);
		
		
	}

	/**
	  * Clear Transaction or Clear master Form which ever user Selected then open Confirmation Login
	  */
	function funCheckUser(strType)
	{
		
	//	var ret=window.showModalDialog("frmConfirmLoginUser.html?strHeadingType="+strType,"","dialogHeight:200px;dialogWidth:500px;dialogLeft:350px;dialogTop:150px");
			var retchk=window.open("frmConfirmUserLogin.html?strHeadingType="+strType,"","dialogHeight:200px;dialogWidth:500px;dialogLeft:350px;dialogTop:150px");	
	
	
			var timer = setInterval(function ()
				    {
					if(retchk.closed)
						{
							if (retchk.returnValue != null)
							{
								userName = retchk.returnValue.strUserName;
								if(retchk.returnValue.str=="Successfull Login")
								{
									if(strType=="Transaction")
									{
										funClearTransaction();
									} else if(strType=="Master")
									{
										funClearMaster();
									}
								}
								else
									{
										return false;
									}
			
							}
							clearInterval(timer);
						}
				    }, 500);	
			
	}
	
	/**
	  * Call Clear Master 
	  */
	function funClearMaster(){
		
		var isOk=confirm("Are you sure delete Master ?");
		if(isOk)
		{
		 $.ajax({
		  type: "GET",
		  url: getContextPath()+"/POSClearMaster.html?frmName="+member,
		  dataType:"text",
		  async:false,
		  success: function(response){
			  alert(response);
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
		}
	
	/**
	  * Call Clear Transaction 
	  */
	function funClearTransaction()
	{
		
		var isOk=confirm("Are you sure delete Transaction ?");
		if(isOk)
		{
		var searchurl=getContextPath()+"/POSClearTransaction.html?frmName="+member+"&posName="+posName+"&fromDate="+fromDate+"&toDate="+toDate+"&userName="+userName+"&chkAllSelected="+chkAllSelected;
		 $.ajax({
			  type: "GET",
			  url: searchurl,
			  dataType:"text",
			  async:false,
			  success: function(response){
				  alert(response);
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
	}
/* 	function funPOSStructureUpdate()
	{
		
		var searchurl=getContextPath()+"/posUpdateStructure";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "text",
			        success: function(response)
			        {
			        	
			        		alert("Structure Update Successfully");
			        	
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
	
</script>


</head>

<body >
	<div id="formHeading">
		<label>Tools</label>
	</div>
	
	<s:form name="tools" method="POST" action="">

	<table class="transTablex">
	<tr></tr>
	<tr>
	<td><img alt="" src="../${pageContext.request.contextPath}/resources/images/imgStructureUpdate.png" onclick="funPOSStructureUpdate()"></td>
	<td><img alt="" src="../${pageContext.request.contextPath}/resources/images/imgDatabaseBackup1.png" onclick="funOpenWindowDBBackup()"></td>
	<td><img alt="" src="../${pageContext.request.contextPath}/resources/images/imgConfigSettings.png" onclick="funOpenWindow()"></td>
	</tr>
	<tr>
	<td><img alt="" src="../${pageContext.request.contextPath}/resources/images/imgCleanMaster.png" onclick="funList('Master');"></td>
	<td><img alt="" src="../${pageContext.request.contextPath}/resources/images/imgClearTransaction.png" onclick="funList('Transaction');"></td>
	<td><img alt="" src="../${pageContext.request.contextPath}/resources/images/imgClose.png"></td>
	</tr>
	
	
	</table>


		
		
	</s:form>

</body>
</html>