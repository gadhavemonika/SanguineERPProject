<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	
/**
* Success/faild Message After Connect Record
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
		alert("Connection \n\n"+message);
	<%
	}}%>

});
	
	 	
	function funCheckTable()
	{
		if(flgSACode==true)
		{
			alert("Data Already Saved!!!!");
			return false;
		}
	}
	
	
	function funFillTableName()
	{
		
		var searchUrl="";
		var ipAdd=$("#txtIPAddress").val();
		var port=$("#txtPortNo").val();
		var dbName=$("#txtDBName").val();
		var userName=$("#txtUserName").val();
		var pass=$("#txtPass").val();
		var flgCon ="F";
		
		
		
		var param=ipAdd+","+port+","+dbName+","+userName+","+pass ;
		searchUrl=getContextPath()+"/loadDatabase.html?param="+param;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			
			    success: function(response)
			    {
			    	$('#cmbTableName').val('');
			    	document.all["lblTable"].style.display = 'block';
					document.all["cmbTableName"].style.display = 'block';
			    	$.each(response, function(i,item)
					{
			    		$('#cmbTableName').append(new Option(item, i));
			    		flgCon="T";
					}); 
			    	if(flgCon=="T")
					{
			    		alert("Connection \n\n"+"Established");
					}else
						{
						alert("Connection \n\n"+"Failed");
				        document.all["lblTable"].style.display = 'none';
						document.all["cmbTableName"].style.display = 'none';
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
	function funSetComboData()
	{
		var cmbval = $('#cmbTableName').find('option:selected').text();
		//var cmbval = $('#cmbTableName').text();
		//$('#cmbTableName').text(cmbval);
		$('#hidTableName').val(cmbval);
		//alert($("#cmbTableName").prop('selectedIndex'));
		//alert($('#cmbTableName')[2].selectedIndex);
		//alert(cmbval);
	}

</script>

</head>
<body>

	<div id="formHeading">
	<label>Database Data Import</label>
	</div>

<br/>
<br/>

	<s:form name="POSItemMasterImport" method="POST" action="saveImportData.html?saddr=${urlHits}">

		<table class="transTable">
				
				<tr>
				
				<td><label>IP Address</label></td>
				<td><s:input type="text" id="txtIPAddress" 
						name="txtIPAddress" path="strIPAddress" required="true"
						 cssClass="longTextBox"  /> </td>
						
				<td><label>Port No</label></td>		
				<td><s:input type="text" id="txtPortNo" 
						name="txtPortNo" path="strPortNo" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>

				<td><label>DataBase Name</label></td>			
				<td><s:input type="text" id="txtDBName" 
						name="txtDBName" path="strDBName" required="true"
						 cssClass="longTextBox"  /> </td>
				
				</tr>
				<tr>
						<td><label>User Name</label></td>	
						<td><s:input type="text" id="txtUserName" 
						name="txtUserName" path="strUserName" required="true"
						 cssClass="longTextBox"  /> </td>
						
						<td><label>Password</label></td>	
						<td><s:input type="text" id="txtPass" 
						name="txtPass" path="strPass" required="true"
						 cssClass="longTextBox"  /> </td>
						
						<td><label id="lblTable" style="display:none">Tables</label></td>
						<td><select id="cmbTableName"  Class="BoxW124px" style="display:none" onchange="funSetComboData()"></select></td>
						
						
				</tr>
			
					
		</table>
<!-- 	<div>	 -->
<!-- 			<div> -->
<!-- 			<table class="transTable"> -->
<!-- 			<tr> -->
			
<!-- 				<td style="padding: 0 !important;"> -->
<!-- 							<div -->
<!-- 								style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;"> -->
<!-- 								<table id="" class="display" -->
<!-- 									style="width: 100%; border-collapse: separate;"> -->
<!-- 									<tbody> -->
<!-- 										<tr bgcolor="#72BEFC"> -->
<!-- 											<td width="15%"><input type="checkbox" id="chkGALL" -->
<!-- 												checked="checked" onclick="funCheckUncheck()" />Select</td> -->
<!-- 											<td width="20%">Column</td> -->
											
	
<!-- 										</tr> -->
<!-- 									</tbody> -->
<!-- 								</table> -->
<!-- 								<table id="tblMySql" class="masterTable" -->
<!-- 									style="width: 100%; border-collapse: separate;"> -->
<!-- 									<tbody> -->
<!-- 										<tr bgcolor="#72BEFC"> -->
<!-- 											<td width="15%"></td> -->
<!-- 											<td width="20%"></td> -->
											
	
<!-- 										</tr> -->
<!-- 									</tbody> -->
<!-- 								</table> -->
<!-- 							</div> -->
<!-- 							</td> -->
			
			
<!-- 			</tr> -->
			
<!-- 			</table> -->
			
<!-- 			</div> -->
			
<!-- 			<div> -->
<!-- 			<table class="transTable"> -->
<!-- 			<tr> -->
			
<!-- 				<td style="padding: 0 !important;"> -->
<!-- 							<div -->
<!-- 								style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 150px; overflow-x: hidden; overflow-y: scroll;"> -->
<!-- 								<table id="" class="display" -->
<!-- 									style="width: 100%; border-collapse: separate;"> -->
<!-- 									<tbody> -->
<!-- 										<tr bgcolor="#72BEFC"> -->
<!-- 											<td width="15%"><input type="checkbox" id="chkGALL" -->
<!-- 												checked="checked" onclick="funCheckUncheck()" />Select</td> -->
<!-- 											<td width="20%">Column</td> -->
											
	
<!-- 										</tr> -->
<!-- 									</tbody> -->
<!-- 								</table> -->
<!-- 								<table id="tblMsSql" class="masterTable" -->
<!-- 									style="width: 100%; border-collapse: separate;"> -->
<!-- 									<tbody> -->
<!-- 										<tr bgcolor="#72BEFC"> -->
<!-- 											<td width="15%"></td> -->
<!-- 											<td width="20%"></td> -->
											
	
<!-- 										</tr> -->
<!-- 									</tbody> -->
<!-- 								</table> -->
<!-- 							</div> -->
<!-- 							</td> -->
			
			
<!-- 			</tr> -->
			
			
<!-- 			</table> -->
<!-- 			</div> -->
<!-- 	</div>	 -->


<!-- <table class="transTable"> -->
		
<!-- 			<tr></tr> -->
<!-- 			<tr> -->
<!-- 				<td style="padding: 0 !important;"> -->
<!-- 						<div -->
<!-- 							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; overflow-x: hidden; overflow-y: scroll;"> -->
<!-- 							<table id="" class="display" -->
<!-- 								style="width: 100%; border-collapse: separate;"> -->
<!-- 								<tbody> -->
<!-- 									<tr bgcolor="#72BEFC"> -->
<!-- 										<td width="15%"><input type="checkbox" id="chkGALL" -->
<!-- 											checked="checked" onclick="funCheckUncheck()" />Select</td> -->
<!-- 										<td width="20%">Column</td> -->
									

<!-- 									</tr> -->
<!-- 								</tbody> -->
<!-- 							</table> -->
<!-- 							<table id="tblMySql" class="masterTable" -->
<!-- 								style="width: 100%; border-collapse: separate;"> -->
<!-- 								<tbody> -->
<!-- 									<tr bgcolor="#72BEFC"> -->
<!-- 										<td width="15%"></td> -->
<!-- 										<td width="20%"></td> -->
									

<!-- 									</tr> -->
<!-- 								</tbody> -->
<!-- 							</table> -->
<!-- 						</div> -->
<!-- 						</td> -->
<!-- 						<td style="padding: 0 !important;"> -->
<!-- 						<div -->
<!-- 							style="background-color: #a4d7ff; border: 1px solid #ccc; display: block; height: 250px; overflow-x: hidden; overflow-y: scroll;"> -->

<!-- 							<table id="" class="masterTable" -->
<!-- 								style="width: 100%; border-collapse: separate;"> -->
<!-- 								<tbody> -->
<!-- 									<tr bgcolor="#72BEFC"> -->
<!-- 										<td width="15%"><input type="checkbox" id="chkSGALL" -->
<!-- 											checked="checked" onclick="funCheckUncheckSubGroup()" />Select</td> -->
<!-- 										<td width="25%">Column</td> -->
										

<!-- 									</tr> -->
<!-- 								</tbody> -->
<!-- 							</table> -->
<!-- 							<table id="tblMsSql" class="masterTable" -->
<!-- 								style="width: 100%; border-collapse: separate;"> -->
<!-- 								<tbody> -->
<!-- 									<tr bgcolor="#72BEFC"> -->
<!-- 										<td width="15%"></td> -->
<!-- 										<td width="25%"></td> -->
										

<!-- 									</tr> -->
<!-- 								</tbody> -->
<!-- 							</table> -->
<!-- 						</div> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->

				
		<br />
		<table class="transTable">
		<tr>
					<td>	<p>
						<input type="button" value="Connect" tabindex="3" class="form_button" id="btnSubmit" onclick="funFillTableName()"  />
						<input type="submit" value="Excute" tabindex="3" class="form_button"  />
						<input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>
						<s:input type="hidden" id="hidTableName" path="strSGName"></s:input>
					</p>  </td>
								
		</tr>
		</table>
		
	</s:form>
</body>
</html>
