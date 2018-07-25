<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
		
		/**
		 * Global variable
		 */
		var member="";
		var propName="";
		var locName="";
		 /**
		  * Ready Function for Ajax Waiting and reset form
		  */
		 $(document).ready(function () {
		    $(document).ajaxStart(function(){
			    $("#wait").css("display","block");
			  });
			  $(document).ajaxComplete(function(){
			    $("#wait").css("display","none");
			  });
		}); 
	
		 /**
		  * Structure Update
		  */
		function funUpdateStructure(){
		 $.ajax({
		  type: "GET",
		  url: getContextPath()+"/ExciseUpdateStructure.html",
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
		  * Clear Transaction or Clear master Form which ever user Selected then open Confirmation Login
		  */
		function funCheckUser(strType)
		{
			
		//	var ret=window.showModalDialog("frmConfirmLoginUser.html?strHeadingType="+strType,"","dialogHeight:200px;dialogWidth:500px;dialogLeft:350px;dialogTop:150px");
			var ret=window.open("frmConfirmLoginUser.html?strHeadingType="+strType,"","dialogHeight:200px;dialogWidth:500px;dialogLeft:350px;dialogTop:150px");
			if(ret=="Successfull Login")
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

		 /**
		  * Call Clear Transaction 
		  */
		function funClearTransaction()
		{
			var isOk=confirm("Are you sure delete Transaction ?");
			if(isOk)
			{
			var searchurl=getContextPath()+"/ExciseClearTransaction.html?frmName="+member+"&propName="+propName+"&locName="+locName ;
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
			
		
		/**
		  * Call Clear Master 
		  */
		function funClearMaster(){
			var isOk=confirm("Are you sure delete Master ?");
			if(isOk)
			{
			 $.ajax({
			  type: "GET",
			  url: getContextPath()+"/ExciseClearMaster.html?frmName="+member,
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
		  * Open Delete Module Form and return back selected Module either Transaction or Master 
		 **/
		function funList(strType)
		{
		//	var returnVal=window.showModalDialog("frmExciseDeleteModuleList.html?strHeadingType="+strType,"","dialogHeight:600px;dialogWidth:500px;dialogLeft:350px;dialogTop:100px");
			var returnVal=window.open("frmExciseDeleteModuleList.html?strHeadingType="+strType,"","dialogHeight:600px;dialogWidth:500px;dialogLeft:350px;dialogTop:100px");
			var ret=returnVal.members;
			propName=returnVal.propertyName;
			locName=returnVal.locationName;
			$.each(ret, function(i,item)
				    {
		        		if(ret[i].strDelete!="false")
		        			{
			        			if(member !=""){
		        					member=member+","+ret[i].strFormDesc;
		        				}else{
		        					member=ret[i].strFormDesc;
		        				}
		        			}
				    }); 
		
				funCheckUser(strType);		
		}
	
</script>

</head>
<body>
<div id="formHeading">
		<label>Excise Structure Update</label>
	</div>
	<s:form>
	<br>
	<br>
	<br>
	<p align="center">
	<input type="button" value="" class="structureUpdate_button"  onclick="funUpdateStructure();">
	<input type="button" class="clearMaster_button" style="width: 20%;height: 200px" value="" onclick="funList('Master');">
	<input type="button" class="clearTransaction_button" style="width: 20%;height: 200px" value="" onclick="funList('Transaction');">
	
	</p>
	
	</s:form>
	<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
</body>
</html>