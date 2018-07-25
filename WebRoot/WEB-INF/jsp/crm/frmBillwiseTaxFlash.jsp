<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
		var invoceFlashData;
		var loggedInProperty="";
 		var loggedInLocation="";
 		
 		//Load Date,Login Property function on page Ready
		$(document).ready(function() 
				{		
					loggedInProperty="${LoggedInProp}";
					loggedInLocation="${LoggedInLoc}";
					$("#cmbProperty").val(loggedInProperty);
					var propCode=$("#cmbProperty").val();
				
					$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
					$("#txtFromDate").datepicker('setDate','today');
					$("#txtFromDate").datepicker();
					$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
					$("#txtToDate" ).datepicker('setDate', 'today');
					$("#txtToDate").datepicker();
		
					
					
					$(document).ajaxStart(function()
							{
							    $("#wait").css("display","block");
							});
							$(document).ajaxComplete(function()
							{
								$("#wait").css("display","none");
							});
					
					
				});
		
		function funChangeLocationCombo()
		{
			var propCode=$("#cmbProperty").val();
			funFillLocationCombo(propCode);
		}
		
		// Load Loacation Data Property wise for combo box  
		function funFillLocationCombo(propCode) 
		{
			var searchUrl = getContextPath() + "/loadLocationForProperty.html?propCode="+ propCode;
			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					var html = '';
					$.each(response, function(key, value) {
						html += '<option value="' + value[1] + '">'+value[0]
								+ '</option>';
					});
					html += '</option>';
					$('#cmbLocation').html(html);
					$("#cmbLocation").val(loggedInLocation);
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
 
		
		
		 	// btn Click Event of Export and Execute	
			$(document).ready(function() 
			{
				$("#btnExport").click(function( event )
				{
 					 funExportData();
				});
				$("#btnExecute").click(function( event )
				{
					funShowFlash();
				});
					
				$(document).ajaxStart(function()
				{
				    $("#wait").css("display","block");
				});
				$(document).ajaxComplete(function()
				{
					$("#wait").css("display","none");
				});
			});
		 	
			
		 	// Load Billwise Data for Flash Dispaly
			function funShowFlash()
			{
				funRemoveFlshRows();
				var fdate = $("#txtFromDate").val();
				var tdate = $("#txtToDate").val();
				var fdateSp= fdate.split("-");
				fdate = fdateSp[2]+"-"+fdateSp[1]+"-"+fdateSp[0];
				var tdateSp= tdate.split("-");
				tdate = tdateSp[2]+"-"+tdateSp[1]+"-"+tdateSp[0];
				
				var strLocCode = $("#cmbLocation").val();
				var strHSN = $("#cmbHSN").val();
				
				
				var searchUrl = getContextPath()+ "/showBillwisetaxDataFlash.html?fromDate="+ fdate+"&toDate="+tdate+"&strLocCode="+strLocCode+"&strHSN="+strHSN;
				$.ajax({
					type : "GET",
					url : searchUrl,
					dataType : "json",
					success : function(response) {
						
						funShowBillwiseFlash(response);
						
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
			
			
		function funExportData()
			{
			
				var fdate = $("#txtFromDate").val();
				var tdate = $("#txtToDate").val();
				var fdateSp= fdate.split("-");
				fdate = fdateSp[2]+"-"+fdateSp[1]+"-"+fdateSp[0];
				var tdateSp= tdate.split("-");
				tdate = tdateSp[2]+"-"+tdateSp[1]+"-"+tdateSp[0];
				
				var strHSN = $("#cmbHSN").val();
				var strLocCode = $("#cmbLocation").val();
				
				window.location.href = getContextPath()+ "/loadBillwisetaxData.html?fromDate="+ fdate+"&toDate="+tdate+"&strLocCode="+strLocCode+"&strHSN="+strHSN;
			
				
			}
			
			// Set Billwise Data for Flash Table Dispaly
			function funShowBillwiseFlash(response)
			{
						
				var table = document.getElementById("tblBilwiseFlash");
				var rowCount = table.rows.length;
			    var row = table.insertRow(rowCount);
			  //  funRemoveFlshRows();
				var hearderArr = response[0];
				if(hearderArr.length>0)
					{
						document.all[ 'dvBilwiseFlash' ].style.display = 'block';
						row.insertCell(0).innerHTML= '<label>InvCode</label>';
						row.insertCell(1).innerHTML= '<label >Bill Date</label>';
					  for(var i=2; i<hearderArr.length;i++)
						  {
							row.insertCell(i).innerHTML= '<label>'+hearderArr[i]+'</label>';
						  }
					  rowCount=rowCount+1;
					}
				
					var rowDataArr = response[1];
					$.each(rowDataArr, function(i,item)
							{
								var row1 = table.insertRow(rowCount);
									for(var j=0; j<item.length;j++)
									  {
											if(j==0)
												{
													 if(item[j]=='' || item[j]== 'Total')
													  {
														  row1.insertCell(j).innerHTML= "<label>"+item[j]+"</label>";
													  }else
														  {
															row1.insertCell(0).innerHTML= "<a id=\"urlDocCode\" href=\"openSlip.html?docCode="+item[j]+",Invoice\" target=\"_blank\" >"+item[j]+"</a>";
														  }
											}
											else if(j==1)
											{
												 row1.insertCell(j).innerHTML= "<label>"+item[j]+"</label>";
											}else
											{
												  if(item[j]=='' || item[j]== 'Total')
												  {
													  row1.insertCell(j).innerHTML= "<label>"+item[j]+"</label>";
												  }else
													  {
													 	var dblData=parseFloat(item[j]).toFixed(maxAmountDecimalPlaceLimit);
														row1.insertCell(j).innerHTML= "<label>"+dblData+"</label>";
													  }
											}
									  }
									rowCount=rowCount+1;
							});
			}
			
			//Remove Display Data of Table
			function funRemoveFlshRows()
			{
				var table = document.getElementById("tblBilwiseFlash");
				var rowCount = table.rows.length;
				while(rowCount>0)
				{
					table.deleteRow(0);
					rowCount--;
				}
			}
		
			
		 	</script>
</head>

<body>
	<div id="formHeading">
		<label>Billwise Tax Flash</label>
	</div>
	<s:form name="Form" method="GET" action="">
		<br />

		<table class="transTable">
			<tr>
				 <td width="10%">Property Code</td>
					<td width="20%">
						<s:select id="cmbProperty" name="propCode" path="" cssClass="longTextBox" cssStyle="width:100%" onchange="funChangeLocationCombo();">
			    			<s:options items="${listProperty}"/>
			    		</s:select>
					</td> 
					<td width="5%"><label>Location</label></td>
					<td>
						<s:select id="cmbLocation" name="locCode" path="strLocCode" cssClass="longTextBox" cssStyle="width:180px;" >
			    			<s:options items="${listLocation}"/>
			    		</s:select>
					</td>
			</tr>
			<tr>
				<td><label id="lblFromDate">From Date</label></td>
				<td><s:input id="txtFromDate" required="required" path=""
						name="fromDate" cssClass="calenderTextBox" /></td>




				<td><label id="lblToDate">To Date</label></td>
				<td colspan="3"><s:input id="txtToDate" name="toDate" path=""
						cssClass="calenderTextBox" /></td>
			</tr>
			
			<tr>
				<td><label id="lblHSN">Show HSN no.</label></td>
				<td><s:select id="cmbHSN" required="required" path=""
						name="HSN" cssClass="calenderTextBox" >
						<option value="No" selected="selected">No</option>
						<option value="Yes">Yes</option>
				</s:select>
				</td>
				<td colspan="5"></td>
			</tr>
			
			<tr>
				<td><input id="btnExecute" type="button" class="form_button1"
					value="EXECUTE" /></td>
				<td><input id="btnExport" type="button" value="EXPORT"
					class="form_button1" /></td>
				<td colspan="5"></td>

			</tr>


		</table>
		
		<br><br>
		<div id="dvBilwiseFlash" style="width: 100% ;height: 100% ;display: none;">
				<table id="tblBilwiseFlash" class="transTable col3-right col4-right col5-right col6-right col7-right col8-right col9-right" ></table>
		</div>
			
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>

	</s:form>
</body>
</html>