<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
<title>Property Selection</title>
<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
<link rel="stylesheet" type="text/css" media="screen" href="<spring:url value="/resources/css/design.css"/>" />
<script type="text/javascript">
  $(document).ready(function(){
       $('#submit').focus();
       
       $(document).ajaxStart(function(){
   	    $("#wait").css("display","block");
   	  });
   	  $(document).ajaxComplete(function(){
   	    $("#wait").css("display","none");
   	  });
   	  
<%--    	var PropertyError='<%=session.getAttribute("PropertyError").toString()%>'; --%>
//    	if(PropertyError!='')
//    		{
//    			alert(PropertyError);
//    		}
   	  
   	  
  });

  $(document).ready(function()
			{
		var message='';
		<%if (session.getAttribute("posLicStatus") != null) {
			            if(session.getAttribute("posLicMsg") != null){%>
			            message='<%=session.getAttribute("posLicMsg").toString()%>';
			            <%
			            session.removeAttribute("posLicMsg");
			            }
						session.removeAttribute("posLicStatus");
						%>	
			alert("\n"+message);
		<%
		}%>

	});
  
  
</script>


<script type="text/javascript">

	function funSetSessionData()
	{
		var companyName=$( "#cmbCompany option:selected" ).text();
	    var  propertyName=$( "#cmbProperty option:selected" ).text();
	    var locationName=$( "#cmbLocation option:selected" ).text();
	    var financialYear=$( "#cmbFinancialYear option:selected" ).text();   
	 	if(locationName.trim().length == 0){
	 	$.jAlert("Please Select Location");	
		return false;
	 	}else{
	    $("#strCompanyName").val(companyName);
	    $("#strPropertyName").val(propertyName);
	    $("#strLocationName").val(locationName);
	    $("#strFinancialYear").val(financialYear);
	    
	  	var  moduleName='<%=session.getAttribute("selectedModuleName").toString()%>';
	    if(moduleName=="3-WebPMS")
	    	{
	    //	window.open("frmShowTodayCheckOutRooms.html", "Today's Checkout List", "scrollbars=1,width=530,height=350");
	    //	window.open("searchform.html?formname="+transactionName+"&POCode="+POCode+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;top=500,left=500")
	    	return true;
	    	}else
		 	{
		    		 return true;		
		 	}
	    
	   
	 	}
	}
	
	function getContextPath() 
	{
	  	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	function funLoadLocation(code)
	{	
		var searchUrl=getContextPath()+"/loadLoactionComboPropertyNUserWise.html?code="+code;
		//alert(searchUrl);
		$.ajax({
	        type: "GET",
	        url: searchUrl,	        		
	        dataType: "json",
	        success: function(response)	        
	        {	      
	        	
	        
		           var html = '';   
		           var check=0;
				   $.each(response, function(key, value)
				   {
						if(key=='Invalid')
		        		{
							window.location.href=getContextPath()+"/frmStructureUpdateException_2.html"; 
		        			check=1;
		        		}else{
					 html += '<option value="' + key + '">'+ value + '</option>';
		        		}
				   });
				   if(check==0)
					   {
				   html += '</option>';
				   $('#cmbLocation').html(html);
				   //document.all['#divBtn'].style.display = 'block';
        		}
	        
			},
			error: function(jqXHR, exception) {
	            if (jqXHR.status === 0) {
	                $.jAlert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                $.jAlert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                $.jAlert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                $.jAlert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                $.jAlert('Time out error.');
	            } else if (exception === 'abort') {
	                $.jAlert('Ajax request aborted.');
	            } else {
	                $.jAlert('Uncaught Error.n' + jqXHR.responseText);
	            }		            
	        }
	  });	
	}
	
	
	function funLoadLocation1(code,loc)
	{	
		var searchUrl=getContextPath()+"/loadLoactionComboPropertyNUserWise.html?code="+code;
		
		$.ajax({
	        type: "GET",
	        url: searchUrl,
	        dataType: "json",
	        success: function(response)	        
	        {	    
	        
		           var html = '';   
		           var check=0;
				   $.each(response, function(key, value)
				   {
						if(key=='Invalid')
		        		{
							window.location.href=getContextPath()+"/frmStructureUpdateException_2.html"; 
		        			check=1;
		        		}else{
					 html += '<option value="' + key + '">'+ value + '</option>';
		        		}
				   });
				   if(check==0)
				   {
				   html += '</option>';
				   $('#cmbLocation').html(html);
				   $("#cmbLocation").val(loc);
				   }
				  // document.all["#divBtn"].style.display = 'block';
				  // $('#txtWOCode').css('visibility','visible');
	        
			},
			error: function(jqXHR, exception) {
	            if (jqXHR.status === 0) {
	                $.jAlert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                $.jAlert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                $.jAlert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                $.jAlert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                $.jAlert('Time out error.');
	            } else if (exception === 'abort') {
	                $.jAlert('Ajax request aborted.');
	            } else {
	                $.jAlert('Uncaught Error.n' + jqXHR.responseText);
	            }		            
	        }
	  });	
	}
	

	function funOnLoad()
	{	
		var prop='<%=session.getAttribute("LastProperty").toString()%>';
		var loc='<%=session.getAttribute("LastLocation").toString()%>';
		
		if(prop!='')
		{
			<%
				session.removeAttribute("LastProperty");
				session.removeAttribute("LastLocation");
			%>
			 $("#cmbProperty").val(prop);
			funLoadLocation1(prop,loc); 
		}
	}


</script>

  </head>
  
	<body>
	<div style="width: 100%;height: 200px;"><img alt="" src="../${pageContext.request.contextPath}/resources/images/${headerImage}" width="100%" height="200px" style="background-repeat: no-repeat"></div>
	<div style="background-color: inherit; top: 200px; bottom: 0;float: left;padding-left: 40px"><img alt="" src="../${pageContext.request.contextPath}/resources/images/${moduleTitleImage}" height="100px" width="466px">
	
<!-- 	<div style="float: left;padding-left: 40px;"> -->
<!-- 		<img alt="" -->
<%-- 				src="../${pageContext.request.contextPath}/resources/images/company_Logo.png" --%>
<!-- 				style="display: block; padding-left: 1px;width : 339px;high : 250px;"> -->
				
<!-- 	</div> -->
	</div>
	
	<div style="padding-top: 100px">
		<s:form name="frmPropSel"  method="GET" action="frmMainMenu.html">
		
			<div style="padding-right: 12%;">
			<div
			style="float:right;width: 339px; height: 300px; -webkit-border-radius: 29px; -moz-border-radius: 29px; border-radius: 29px; border: 2px solid  	#0595D2; background: rgba(252, 253, 255, 0.4); -webkit-box-shadow: #42B3F4 2px 2px 2px; -moz-box-shadow: #42B3F4 2px 2px 2px; box-shadow: #42B3F4 2px 2px 2px;">
			<div
				style="width: 340px; height: 62px; background-image: url(../${pageContext.request.contextPath}/resources/images/loginlogo.png);">
				<br>
				<p align="center" style="font-size: 17px;font-weight: bold;">Sanguine Softwares Solutions Pvt. Ltd.</p>
			</div>
			<img alt="" src="../prjWebStocks/resources/images/property.png"
				style="display: block; padding-left: 1px">
			
			
			<div style="width: 340px; height: 163px;">
			<table>
			
			<tr>
			<td style="height: 35px;padding-left: 10px;width: 75px"><label style="font-size: 14px;font-weight: normal;text-shadow: gray;">Company</label></td>
						<td>
							<s:select id="cmbCompany" path="strCompanyCode"  cssClass="loginInput" cssStyle="width:98%;padding-left:2px;">
				    			<s:options items="${listComapny}"/>
				    		</s:select><s:input type="hidden" path="strCompanyName" id="strCompanyName"/>
				    	</td>
			</tr>
			
			
			<tr>
			<td style="height: 35px;padding-left: 10px;"><label style="font-size: 14px;font-weight: normal;">Property</label></td>
						<td>
							<s:select id="cmbProperty" path="strPropertyCode" cssClass="loginInput" 
							cssStyle="width:98%;padding-left:2px;height:22px;" required="true"  onchange="funLoadLocation(this.value)">
				    			<s:options items="${listProperty}"/>
				    		</s:select><s:input type="hidden" path="strPropertyName" id="strPropertyName"/>
						</td>
			</tr>
			<tr><td style="height: 35px;padding-left: 10px;"><label style="font-size: 14px;font-weight: normal;">Location</label></td>
						<td>
							<s:select id="cmbLocation" path="strLocationCode"  cssClass="loginInput" cssStyle="width:98%;padding-left:2px;">
				    			<%-- <s:options  items="${listLocation}"/> --%>
				    		</s:select><s:input type="hidden" path="strLocationName" id="strLocationName"/>
						</td>
				</tr>
			<tr>
						<td style="height: 35px;padding-left: 10px;"><label style="font-size: 14px;font-weight: normal;">Fin. Year</label></td>
						<td>
						
				    		
				    		<select id="cmbFinancialYear" class="loginInput" style="padding-left:2px;" >	
				    		<c:forEach items="${listFinancialYear}" var="draw">	
				    		<option value="${draw.key}">${draw.value}</option>
				    		</c:forEach>	
				    		</select><s:input type="hidden" path="strFinancialYear" id="strFinancialYear"/>
						</td>
					</tr>
			</table>			
			</div>
			<div id="divBtn"  
				style="width: 340px; height: 36px; background-image: url(../${pageContext.request.contextPath}/resources/images/loginfoot.png);">
				<p align="right" style="padding-right: 22px; padding-top: 9px">
					<input type="submit" onclick="return funSetSessionData()" id="submit" value="" class="loginButton"   />
				</p>
			</div>
			</div>
			
			</div>
									
			<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:50%;left:45%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
			<script type="text/javascript">
			funLoadLocation($("#cmbProperty").val());
			</script>
		</s:form>
		</div>
		<div id="loginfooter">Copyright &copy; 2014 Sanguine Software Solutions</div>
	</body>
</html>