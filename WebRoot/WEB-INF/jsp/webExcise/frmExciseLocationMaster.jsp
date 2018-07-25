<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOCATION MASTER</title>	
<script type="text/javascript">

$(document).ready(function(){
	resetForms("locationForm");
	$("#txtlocName").focus();
});
</script>
	<script type="text/javascript">
	
	$(document).ready(function() 
			{		
				$(".tab_content").hide();
				$(".tab_content:first").show();
		
				$("ul.tabs li").click(function() {
					$("ul.tabs li").removeClass("active");
					$(this).addClass("active");
					$(".tab_content").hide();
					var activeTab = $(this).attr("data-state");
					$("#" + activeTab).fadeIn();
				});
				
				$(document).ajaxStart(function(){
				    $("#wait").css("display","block");
				});
				$(document).ajaxComplete(function(){
				   	$("#wait").css("display","none");
				});
			});
	
		var fieldName;
		
		function funResetFields()
		{			
			$("#chkAvlForSale").attr('checked', false);
			$("#chkActive").attr('checked', false);
			$("#chkPickable").attr('checked', false);
			$("#chkReceivable").attr('checked', false);
			$("#txtlocName").focus();
			$("#lblPropertyName").text("");
	    }
		
		function funSetData(code)
		{			
			switch (fieldName) 
			{			   
			   case 'exciselocationmaster':
			    	funSetLocation(code);
			        break;
			        
			   case 'exciseproperty':
				   funSetPropertyData(code);
			        break;
			}
		}		
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;;dialogTop:400px;");
	    }
		
		function funSetLocation(code)
		{
		 	$("#txtlocName").focus();
			$.ajax({
			        type: "GET",
			        url: getContextPath()+"/loadExciseLocationMasterData.html?locCode="+code,
			        dataType: "json",
			        success: function(response)
			        {
				       	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#txtLocCode").val('');
				       	}
				       	else
				       	{
				       		$("#txtLocCode").val(response.strLocCode);
					       	$("#txtlocName").val(response.strLocName);
					       	$("#txtLocDesc").val(response.strLocDesc);					       	
					       	$("#txtExciseNo").val(response.strExciseNo);
					       	$("#txtExternalCode").val(response.strExternalCode);
					       	
					       	if(response.strAvlForSale=='Y')
				        	{
				        		$("#chkAvlForSale").attr('checked', true);
				        	}
					       	if(response.strActive=='Y')
				        	{
				        		$("#chkActive").attr('checked', true);
				        	}					       	
					       	if(response.strPickable=='Y')
				        	{
				        		$("#chkPickable").attr('checked', true);
				        	}					       	
					       	if(response.strReceivable=='Y')
				        	{
				        		$("#chkReceivable").attr('checked', true);
				        	}
					       	$("#cmbType").val(response.strType);	
					    	$("#txtPropertyCode").val(response.strPropertyCode);
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
		
		
		function funSetPropertyData(code)
		{
			
			$("#txtPropertyCode").val(code);
			$.ajax({
					type : "GET",
					url : getContextPath()+ "/loadPropertyMasterData.html?Code=" + code,
					dataType : "json",
					success : function(resp) 
					{
						$("#txtPropertyCode").val(resp.propertyCode);
						$("#lblPropertyName").text(resp.propertyName);
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


		function funSetReOrderLevelData(strLocCode)
		{
			var searchurl=getContextPath()+ "/loadReorderLvlMasterData.html?strLocCode=" + strLocCode;
			$.ajax({
					type : "GET",
					url : searchurl,
					dataType : "json",
					success : function(resp) 
					{
						funRemoveProductRows();
						$.each(resp, function(i,item)
						{
							funAddReorderLevelRow(resp[i][0],resp[i][1],resp[i][2],resp[i][3])
						});
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
		
		
		$(function()
		{
			$('a#baseUrl').click(function() 
			{
				if($("#txtLocCode").val().trim()=="" && $("#txtLocCode").val().trim().length==0)
				{
					alert("Please Select Location Code");
					return false;
				}
				window.open('attachDoc.html?transName=frmLocationMaster.jsp&formName=Location Master&code='+$('#txtLocCode').val(),"mywindow","directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=600,left=400px");
			});
			
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
			
			$('#txtLocCode').blur(function () {
				 var code=$('#txtLocCode').val();				   
				      if (code.trim().length > 0 && code !="?" && code !="/") {
				      funSetLocation(code);
				      }
				});
			
		});
		
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			
			if($('#txtlocName').val().trim()=="" && $('#txtlocName').val().trim().length==0)
				{
					alert("Please Enter Location Name");
					$('#txtlocName').focus();
					return false;
				}
			if($('#txtPropertyCode').val().trim()=="" && $('#txtPropertyCode').val().trim().length==0)
			{
				alert("Please Select Property Code");
				return false;
			}
			if($('#txtLocCode').val().trim()=='' && $('#txtLocCode').val().trim().length==0)
			{
				var code = $('#txtlocName').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checklocName.html?locName="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("Location Name Already Exist!");
				        			$('#txtlocName').focus();
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
		function funRemoveProductRows()
		{
			var table = document.getElementById("tblProdDet");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		function funOpenExportImport()			
		{
			var transactionformName="frmLocationMaster";
	     //   response=window.showModalDialog("frmExcelExportImport.html?formname="+transactionformName,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:550px;");
	        response=window.open("frmExcelExportImport.html?formname="+transactionformName,"","dialogHeight:500px;dialogWidth:500px;dialogLeft:550px;");
	        if(null!=response)
	        {
		        funRemoveProductRows();
		    	$.each(response, function(i,item)
				{			    		
		    		funAddReorderLevelRow(response[i][0],response[i][1],response[i][2],response[i][3]);
			  	}); 
	        }
		}
		function funAddReorderLevelRow(strProdCode,strProdName,dblReorderLvl,dblReorderQty)
		{
			 var table = document.getElementById("tblProdDet");
			    var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			    
			    row.insertCell(0).innerHTML= "<input class=\"Box\" size=\"5%\" name=\"listReorderLvl["+(rowCount)+"].strProdCode\" id=\"txtProdCode."+(rowCount)+"\" value='"+strProdCode+"'>";
			    row.insertCell(1).innerHTML= "<input class=\"Box\" size=\"80%\"  id=\"txtProdName."+(rowCount)+"\" value='"+strProdName+"' >";
			    row.insertCell(2).innerHTML= "<input type=\"text\"  required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" name=\"listReorderLvl["+(rowCount)+"].dblReOrderLevel\" id=\"txtReOrderLevel."+(rowCount)+"\" value='"+dblReorderLvl+"' >";		    
			    row.insertCell(3).innerHTML= "<input type=\"text\" required = \"required\" style=\"text-align: right;\" class=\"decimal-places inputText-Auto\" name=\"listReorderLvl["+(rowCount)+"].dblReOrderQty\"  id=\"txtReOrderQty."+(rowCount)+"\" value='"+dblReorderQty+"' >";
			    row.insertCell(4).innerHTML= '<input type="button" value = "Delete"  class="deletebutton" onClick="Javacsript:funDeleteRow(this)">';
			    return false;
		}
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblProdDet");
		    table.deleteRow(index);
		}
	</script>

</head>
<body>
<div id="formHeading">
		<label>Location Master</label>
	</div>
	<s:form name="locationForm" method="POST" action="saveExciseLocationMaster.html?saddr=${urlHits}">
	<input type="hidden" name="saddr" value="${urlHits}">
	<br />
		<br />
		<input type="hidden" name="saddr" value="${urlHits}">
		<div id="tab_container" style="height: 400px;">
						<ul class="tabs">
							<li class="active" data-state="tab1"
								style="width: 10%; padding-left: 55px">GENERAL</li>
							<li data-state="tab2" style="width: 10%; padding-left: 55px">ReOrdel Level</li>
							</ul>
						<div id="tab1" class="tab_content" style="height: 300px">
				<table class="masterTable">
		
					<tr>
				        <th align="right" colspan="2"> <a id="baseUrl" href="#"> Attach Documents</a> &nbsp; &nbsp; &nbsp;
								&nbsp;</th>
				    </tr>
				     
				    <tr>
				        <td width="120px"><label>Location Code </label></td>
				        <td><s:input id="txtLocCode" name="txtLocCode" path="strLocCode" ondblclick="funHelp('exciselocationmaster')"  cssClass="searchTextBox"/></td>
				    </tr>
				    	
				    <tr>
				        <td>
				        	<label>Location Name</label>
				        </td>
				        <td>
				        	<s:input type="text" id="txtlocName" name="txtlocName" size="80px" path="strLocName" required="true" cssClass="longTextBox"/>
				        	<s:errors path="strLocName"></s:errors>
				        </td>
				    </tr>
					    
				    <tr>
					    <td>
					    	<label>Description</label>
					    </td>
					    
					    <td>
					    	<s:input id="txtLocDesc" size="80px" name="txtLocDesc" path="strLocDesc"  cssClass="longTextBox" />
					    	<s:errors path="strLocDesc"></s:errors>
					    </td>
					 </tr>
					    
					  <tr>
					    <td><label>Property Code</label></td>
					    
					    <td>
					    	<s:input id="txtPropertyCode" name="txtPropertyCode" path="strPropertyCode" cssClass="searchTextBox"  ondblclick="funHelp('exciseproperty')" />
					    	<s:errors path="strPropertyCode"></s:errors>
					    	<label id="lblPropertyName" class="namelebel"></label>
					    </td>
					    <td><label id="lblPropertyName"></label></td>
					   </tr> 
					  
					   <tr>
					    <td>
					    	<label>Excise No</label>
					    </td>
					    
					    <td>
					    	<s:input id="txtExciseNo" name="txtExciseNo" path="strExciseNo" cssClass="BoxW116px" />
					    	<s:errors path="strExciseNo"></s:errors>
					    </td>
					   </tr> 
					  
					   
					   	<tr>
						    <td>
						    	<label>External Code</label>
						    </td>
						    
						    <td>
						    	<s:input id="txtExternalCode" name="txtExternalCode" path="strExternalCode"  cssClass="BoxW116px"/>
						    	<s:errors path="strExternalCode"></s:errors>
						    </td>
					   	</tr> 
					   
						<tr>
						    <td>
						    	<label>Available for Sale</label>
						    </td>			    
						    <td>
						    	<s:checkbox id="chkAvlForSale" name="chkAvlForSale" path="strAvlForSale" value="" />
						    	<s:errors path="strAvlForSale"></s:errors>
						    </td>
					   </tr>
					  
					   	<tr>
						    <td>
						    	<label>Active</label>
						    </td>
						    
						    <td>
						    	<s:checkbox id="chkActive" name="chkActive" path="strActive" value="" />
						    	<s:errors path="strActive"></s:errors>
						    </td>
					   </tr>
					  
						<tr>
						    <td>
						    	<label>Pickable</label>
						    </td>
						    
						    <td>
						    	<s:checkbox id="chkPickable" name="chkPickable" path="strPickable" value=""  />
						    	<s:errors path="strPickable"></s:errors>
						    </td>
					   </tr>
					   
						<tr>
						    <td>
						    	<label>Receivable</label>
						    </td>
						    
						    <td>
						    	<s:checkbox id="chkReceivable" name="chkReceivable" path="strReceivable" value="" />
						    	<s:errors path="strReceivable"></s:errors>
						    </td>
					   </tr>
					  
					  	<tr> 
						    <td><label>Type </label></td>
					        <td><s:select id="cmbType" name="cmbType" path="strType" items="${listType}" cssClass="BoxW124px"/></td>
						</tr>
						
						<tr>
						    <td ></td>
						    <td ></td>
				    	</tr>
				</table>
		</div>
					<div id="tab2" class="tab_content" style="height: 400px">
					<table class="transTable" style="width: 80%">
						<tr>
							<th align="left"><a onclick="funOpenExportImport()"
								href="javascript:void(0);">Export/Import</a>
						</tr>
					</table>
							<table class="masterTable">
								<tr>
									<th style="border: 1px white solid;width: 10%"><label>Product Code</label></th>
									<th style="border: 1px white solid;width: 50%"><label>Product Name</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Reorder Level</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Reorder Qty</label></th>
									<th style="border: 1px white solid;width: 10%"><label>Delete</label></th>									
								</tr>	
								</table>
								<div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 80%;">
								
								<table id="tblProdDet" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
								class="transTablex col5-center">
									<tbody>
									<col style="width: 5%"><!-- col1   -->
								    <col style="width: 25%"><!-- col2   -->
									<col style="width: 5%"><!-- col3   -->
									<col style="width: 5%"><!-- col4   -->
									<col style="width: 4%">	<!-- col5   -->
									
								</table>
								</div>
					</div>
		</div>
		
		<br />
		<p align="center">
			<input type="submit" value="Submit"  class="form_button"/>
<!-- 				 onclick="return funCallFormAction('submit',this);"/>  -->
				<input type="reset"
				value="Reset" class="form_button"/>
		</p>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		<br><br>
	</s:form>
</body>
</html>