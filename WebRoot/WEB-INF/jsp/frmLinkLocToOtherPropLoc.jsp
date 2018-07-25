<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javascript">

 		var byLocation,toLocation;
 		$(document).ready(function() 
 				{
		  	 var loggedInProperty="${logInProperty}";
		  	 byLocation="${listFromLocc}";
		  	 toLocation="${listToLocation}";
		  	 
		  	 
		  	
			$("#cmbProperty").val(loggedInProperty);
			var propCode=$("#cmbProperty").val();
			funFillLoadLinkData();
			
		});
 		
 		
 	
 		
 		function funChangeLocationCombo()
		{
			var propCode=$("#cmbProperty").val();
			funFillByLocationCombo(propCode);
			funFillToLocationCombo(propCode);
			funFillLoadLinkData();
		}
 		
 		function funFillByLocationCombo(propCode) 
		{
			
			var searchUrl = getContextPath() + "/loadByLocation.html?propCode="+ propCode;
						
			
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[0] + '">'+value[1]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbByLocation').html(html);
				//	$("#cmbLocation").val(loggedInLocation);
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
 		
 		
 		function funFillLoadLinkData() 
		{
 			var propCode=$("#cmbProperty").val();
			var searchUrl = getContextPath() + "/loadLinkLocation.html?propCode="+ propCode;
						
			
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
			
					funAddRowOfLoadedData(response)	;
							
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
 		
 		function funAddRowOfLoadedData(data)
 		{
 			$('#tblLoc tbody').empty();
 			var table = document.getElementById("tblLoc");
	

 			for(var i=0;i<data.length;i++)
 			{
 				var prop=data[i]
 			    var rowCount = table.rows.length;
 			    var row = table.insertRow(rowCount);
 			    var proplocMod=prop[0]+prop[1]+prop[2];
 			    row.insertCell(0).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" id=\"proplocMod."+(rowCount)+"\" value='"+proplocMod+"' />";
 			    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"strPropertyName."+(rowCount)+"\" value='"+prop[3]+"' />";
 			   
 			    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\"  id=\"strByLocName."+(rowCount)+"\" value='"+prop[4]+"' />";
 			    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\"  id=\"strToLoc."+(rowCount)+"\" value='"+prop[5]+"' />";
 			    row.insertCell(4).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"5%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>"; 
 		     	row.insertCell(5).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" name=\"listLinkLocationModel["+(rowCount)+"].strPropertyCode\" id=\"strPropertyCode."+(rowCount)+"\" value='"+prop[0]+"' />";
 				  
 			    row.insertCell(6).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" name=\"listLinkLocationModel["+(rowCount)+"].strLocCode\" id=\"strLocCode."+(rowCount)+"\" value='"+prop[1]+"' />";
 			    row.insertCell(7).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" name=\"listLinkLocationModel["+(rowCount)+"].strToLoc\" id=\"strToLoc."+(rowCount)+"\" value='"+prop[2]+"' />";
 			     
 		   }
 		}
		//Function to Delete Selected Row From Grid
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblLoc");
		    table.deleteRow(index);
		}
 		
 		function funFillToLocationCombo(propCode) 
		{
			
			var searchUrl = getContextPath() + "/loadToLocation.html?propCode="+ propCode;
						
			
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[0] + '">'+value[1]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbToLocation').html(html);
				//	$("#cmbLocation").val(loggedInLocation);
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
 		

		function funAddBtnClick()
		{
			var propCode=$("#cmbProperty").val();
 			var byLoc=$("#cmbByLocation").val();
 			var toLoc=$("#cmbToLocation").val();
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadPropByLocAndToLocName.html?propCode="+propCode+"&byLocCode="+byLoc+"&toLocCode="+toLoc,
		        dataType: "text",
		        success: function(response)
		        {
		        	var propName=response.split("#")[0];
		        	var byLocName=response.split("#")[1];
		        	var toLocName=response.split("#")[2];
		        	
		        	 if(funDuplicateData(propCode,byLoc,toLoc))
		        		{ 
		        		 funFillPropLocGrid(propName,byLocName,toLocName);
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
 		

 		function funDuplicateData(strPropertyCode,byLocName,toLocName)
		{
			var propLocMod=strPropertyCode+byLocName+toLocName;
		    var table = document.getElementById("tblLoc");
		    var rowCount = table.rows.length;		   
		    var flag=true;
		    if(rowCount > 0)
		    	{
				    $('#tblLoc tr').each(function()
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
 		
 		
 		
 		function funFillPropLocGrid(propName,byLocName,toLocName)
		{
 			var propCode=$("#cmbProperty").val();
 			var byLoc=$("#cmbByLocation").val();
 			
 			var toLoc=$("#cmbToLocation").val();
			var table = document.getElementById("tblLoc");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);

		    var proplocMod=propCode+byLoc+toLoc;
		    row.insertCell(0).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" id=\"proplocMod."+(rowCount)+"\" value='"+proplocMod+"' />";
		    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" id=\"strPropertyName."+(rowCount)+"\" value='"+propName+"' />";
		   
		    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\"  id=\"strByLocName."+(rowCount)+"\" value='"+byLocName+"' />";
		    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"15%\"  id=\"strToLoc."+(rowCount)+"\" value='"+toLocName+"' />";
		    row.insertCell(4).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"5%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>"; 
		    row.insertCell(5).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" name=\"listLinkLocationModel["+(rowCount)+"].strPropertyCode\" id=\"strPropertyCode."+(rowCount)+"\" value='"+propCode+"' />";
			  
		    row.insertCell(6).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" name=\"listLinkLocationModel["+(rowCount)+"].strLocCode\" id=\"strLocCode."+(rowCount)+"\" value='"+byLoc+"' />";
		    row.insertCell(7).innerHTML= "<input type=\"hidden\" class=\"Box\" size=\"0%\" name=\"listLinkLocationModel["+(rowCount)+"].strToLoc\" id=\"strToLoc."+(rowCount)+"\" value='"+toLoc+"' />";
		     
		}
 		</script>
	<body >
	<div id="formHeading">
		<label>Link Location</label>
	</div>
		<s:form action="saveLinkLocation.html?saddr=${urlHits}" method="POST" name="userForm">
			<br />
			<br />
				<table class="masterTable" >
					<tr>
						<td><label>Properties</label></td>
						<td colspan="2">
							<s:select path="strPropertyCode" items="${propertyList}" id="cmbProperty" cssClass="BoxW62px" cssStyle="width:73%" onchange="funChangeLocationCombo();">
							</s:select>
						</td>
						</tr>
						<tr>
						<td><label>By Location</label></td>
						<td>
							<s:select path="strLocCode" items="${listFromLocc}" id="cmbByLocation" cssClass="BoxW62px" cssStyle="width:73%">
							</s:select>
						</td>
						
						<td><label>To Location</label></td>
						<td>
							<s:select path="strToLoc" items="${listToLocation}" id="cmbToLocation" cssClass="BoxW62px" cssStyle="width:73%">
							</s:select>
						</td>
						
						<td colspan="3"><input type="Button" value="Add" onclick="return funAddBtnClick();" class="smallButton" /></td>
					</tr>					
			
					<tr>
			  			<td colspan="5" >
			  				
			  			</td>
			  		</tr>
				</table>
				
				<br/>
				<br/>
				<div class="dynamicTableContainer" style="height: 300px;">
				<table style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
					<tr bgcolor="#72BEFC">
						
						<td style="width:20%;">Property</td>
						<td style="width:15%;">From Location</td>
						<td style="width:15%;">To Location</td>
						<td style="width:5%;">Delete</td>
						
					</tr>
				</table>
			
				<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblLoc"
						style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex col8-center">
						<tbody>
							<col style="width: 0px;">	
							<col style="width: 37%;">
							<col style="width: 29%;">
							<col style="width: 29%;">
							<col style="width: 7%;">
							<col style="width: 0px;">
							<col style="width: 0px;">
							<col style="width: 0px;">		
							<col style="width: 0px;">	
							<col style="width: 0px;">	
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