<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script>

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


function funHelp(transactionName)
{	     fieldName=  transactionName
 //   window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
}
	function funSetData(code){

		switch(fieldName){

			case 'VehCode' : 
				funSetVehCode(code);
				break;
				
			case 'transCode' : 
				funSetTransCode(code);
				break;
		}
	}
	
	function funSetVehCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/LoadVehicleMaster.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				
				if('Invalid Code' == response.strVehCode){
        			alert("Invalid Vehicle Code");
        			$("#txtVehCode").val('');
        			$("#txtVehCode").focus();
        		}else{
        			
        			$("#txtVehCode").val(response.strVehCode);
        			$("#lblVehNo").text(response.strVehNo);
        		
        		
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

	
	
	
	function funSetTransCode(code){
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/LoadTransporterMaster.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				
				if('Invalid Code' == response.strVehCode){
        			alert("Invalid Vehicle Code");
        			$("#txtTransCode").val('');
        			$("#txtTransCode").focus();
        		}else{
        			
        			$("#txtTransCode").val(response.strTransCode);
        			$("#txtTransName").val(response.strTransName);
        			$("#txtDesc").val(response.strDesc);
        			$.each(response.listclsTransporterModelDtl, function(i,item)
	       	       	    	 {
	       	       	    	    funfillTransporterDataRow(response.listclsTransporterModelDtl[i]);
	       	       	    	 });
        		
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
	
	function funfillTransporterDataRow(data){
		
		var strVehCode=data.strVehCode;
		var vehNo=data.strVehNo;
		var table = document.getElementById("tblVehicleDtl");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);

	    row.insertCell(0).innerHTML= "<input name=\"listclsTransporterModelDtl["+(rowCount)+"].strVehCode\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtVehCode."+(rowCount)+"\" value='"+strVehCode+"'/>";
	    row.insertCell(1).innerHTML= "<input name=\"listclsTransporterModelDtl["+(rowCount)+"].strVehNo\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"lblVehNo."+(rowCount)+"\" value='"+vehNo+"'/>";
	    row.insertCell(2).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
	
	}
	
	
    function funSaveTrasnporter(){
    
    	var transCode=$("#txtTransCode").val();
    	var transName=$("#txtTransName").val();
    	var desc= $("#txtDesc").val();
    	$.ajax({
			type : "GET",
			url : getContextPath()+ "/saveTransporter.html?transName="+transName+"&desc="+desc+"&transCode="+transCode,
			dataType : "json",
			success : function(response){ 
				alert(response.strTransCode);
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
    
    function btnAdd_onclick()
    {

    	var table = document.getElementById("tblVehicleDtl");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
    	var strVehCode=$("#txtVehCode").val();
    	var vehNo=$("#lblVehNo").text();

     	//if(funDuplicateVehicle(strVehCode))
   		// {
	    row.insertCell(0).innerHTML= "<input name=\"listclsTransporterModelDtl["+(rowCount)+"].strVehCode\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"txtVehCode."+(rowCount)+"\" value='"+strVehCode+"'/>";
	    row.insertCell(1).innerHTML= "<input name=\"listclsTransporterModelDtl["+(rowCount)+"].strVehNo\" readonly=\"readonly\" class=\"Box\" size=\"9%\" id=\"lblVehNo."+(rowCount)+"\" value='"+vehNo+"'/>";
	    row.insertCell(2).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
    	//}
     	
	    funResetDetailFields();
    }

    
    function funResetDetailFields()
    {
    	$("#txtVehCode").val('');
    	$("#lblVehNo").text('');
    }
    
    
    function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblVehicleDtl");
	    table.deleteRow(index);
	}
    

	function funDuplicateVehicle(strVehCode)
	{
	    var table = document.getElementById("tblVehicleDtl");
	    var rowCount = table.rows.length;		   
	    var flag=true;
	    if(rowCount > 0)
	    	{
			    $('#tblVehicleDtl tr').each(function()
			    {
				    if(strVehCode==$(this).find('input').val())// `this` is TR DOM element
    				{
				    	alert("Already added "+ strVehCode);
	    				flag=false;
    				}
				});
			    
	    	}
	}
	
	function funResetFields()
	{
		$("#txtTransCode").focus();
    }
</script>
</head>
<body>
<s:form name="TransPorter Master" method="GET" action="saveTransporterVehicle.html?saddr=${urlHits}">
<div id="formHeading">
<input type="hidden" value="${urlHits}" name="saddr">
		
	<label>TransPorter Master</label>
	
	</div>
	<br>
	<br>
		<table class="masterTable">
			<tr>
				<td>
					<label>Transporter Code</label>
				</td>
				
						<td><s:input  type="text" id="txtTransCode" path="strTransCode" cssClass="searchTextBox" ondblclick="funHelp('transCode');"/>	</td>
				<td colspan="2"></td>
			</tr>
				<tr>
				<td>
					<label>Transporter Name</label>
				</td>
				<td>
					<s:input  type="text" id="txtTransName" path="strTransName" cssClass="BoxW124px" />
				</td>
				<td colspan="2"></td>
			</tr>
		
			<tr>
				<td>
					<label>Description</label>
				</td>
				<td>
					<s:input type="text" id="txtDesc" path="strDesc" cssClass="BoxW124px" />
				</td>
				<td colspan="2"></td>
			
		
			
			
			<tr><td><input type="button" value="Create" class="form_button" onclick="return funSaveTrasnporter()"/></td>
			<td colspan="3"></td>
			
			</tr>
				
				<tr>
				<td>
					<label>Vehicle Code</label>
				</td>
				
				<td><s:input  type="text" id="txtVehCode" path="strVehCode" cssClass="searchTextBox" ondblclick="funHelp('VehCode');"/>	</td>
				<td ><label id ="lblVehNo"></label>	</td>
				<td><input type="button" value="Add" class="smallButton"
										onclick="return btnAdd_onclick()" /></td>
				</tr>
			
			
		
		</table>
		
		
			
			
			<div class="dynamicTableContainer"  style="width: 80%;">
			<table style="height: 28px; border: #0F0; width: 100%;font-size:11px;
	font-weight: bold;">
					<tr bgcolor="#75c0ff">
                        <tr style="background-color:#75c0ff">
                        
                           
                            <td align="left" style="width: 10%; height: 30px;">
                                Vehicle Code</td>
                             <td align="left" style="width: 16%; height: 30px;">
                                Vehicle No.</td>    
                                     <td align="left" style="width: 16%; height: 30px;">
                                Delete</td>
                           
                        
                        </tr>
                        </table>
                        
                 <div
				style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 100%;">

                        <table id="tblVehicleDtl"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">
                        <tbody>
                        	<col align="left" style="width: 10%">   
                            <col align="left" style="width: 16%">                            
                            <col align="left" style="width: 16%">                            
                           
                            
                        </tbody>
                        </table>
                   		 </div>
		</div>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit"  class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>

</body>
</html>