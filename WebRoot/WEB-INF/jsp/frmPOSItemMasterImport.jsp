<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName,flgSACode;

	$(document).ready(function(){
		
	
	$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		
<%if (session.getAttribute("fail") != null) 
		{%>
			alert("Data Already Present\n\n");
			session.removeAttribute("fail");
		<%}%>
		
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
	
		function funLoadBusyAnimation()
    		{
    	var tablename='';
    			$('#btnSubmit').keyup(function()
    			{
    				tablename='#tblPOSLinkUp';
    				searchTable($(this).val(),tablename);
    			});
    			
    		}
		
		

		 $(document).ready(function () 
					{	
					
					$("#chkLocALL").click(function () {
					    $(".ProdCheckBoxClass").prop('checked', $(this).prop('checked'));
					    funOnClickAllChecked();
					});
					});	
		 
		 
		 
		 function funOnClickAllChecked()
			{
				var table = document.getElementById("tblPOSLinkUp");
			    var rowCount = table.rows.length;  
			    var strProdCodes="";
			    for(no=0;no<rowCount;no++)
			    {
			        if(document.all("cbItemCodeSel."+no).checked==true)
			        	{
			        	
			        		if(strProdCodes.length>0)
			        			{
			        			 strProdCodes=strProdCodes+","+document.all("txtPOSItemCode."+no).value;
			        			}
			        		else
			        			{
			        			strProdCodes=document.all("txtPOSItemCode."+no).value;
			        			}
			        	}
			    }
			   // alert(strPropCodes);
			  //  funGetLocData(strPropCodes);
			   
			}
		 
		 
	
	function searchTable(inputVal,tablename)
	{
		var table = $(tablename);
		table.find('tr').each(function(index, row)
		{
			var allCells = $(row).find('td');
			if(allCells.length > 0)
			{
				var found = false;
				allCells.each(function(index, td)
				{
					var regExp = new RegExp(inputVal, 'i');
					if(regExp.test($(td).find('input').val()))
					{
						found = true;
						return false;
					}
				});
				if(found == true)$(row).show();else $(row).hide();
			}
		});
	}
	
	 	
	function funCheckTable()
	{
		if(flgSACode==true)
		{
			alert("Data Already Saved!!!!");
			return false;
		}
	}


	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funSetData(code)
	{
		switch (fieldName) 
		{
		    case '':
		    	
		        break;
		}
	}
	
	
	function funFillPOSTable()
	{
		flgSACode=false;
		var searchUrl="";
		var ipAdd=$("#txtIPAddress").val();
		var port=$("#txtPortNo").val();
		var dbName=$("#txtDBName").val();
		var userName=$("#txtUserName").val();
		var pass=$("#txtPass").val();
		var subGroupName=$("#cmbSGName").val();
		subGroupName=subGroupName.replace('%', '%25');
		
		var locName=$("#cmbLocName").val();
		var param=ipAdd+","+port+","+dbName+","+userName+","+pass+","+subGroupName+","+locName;
		
		searchUrl=getContextPath()+"/loadPOSData.html?param="+param;
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			
			    success: function(response)
			    {
    	    		funDeleteTableAllRows();
			    	$.each(response, function(i,item)
					{
			    		funAddRow(response[i]);
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
	
	function funAddRow(rowData)
	{
		//alert(qty);
		var table = document.getElementById("tblPOSLinkUp");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    
	    var strPosItemCode = rowData.strPosItemCode;
    	var strPOSItemName = rowData.strPOSItemName;
   		var strProdCode = rowData.strProdCode;
		var strProdName = rowData.strProdName;
		var strPartNo = rowData.strPartNo;
		var strProdType = rowData.strProdType;
		
		if(strPosItemCode == null){
			strPosItemCode="";
		}
		
		if(strProdCode == null){
			strProdCode="";
		}
	    
		if(strPOSItemName == null){
			strPOSItemName="";
		}
		
		if(strProdCode == null){
			strProdCode="";
		}
		
		if(strProdName == null){
			strProdName="";
		}
		
		if(strPartNo == null){
			strPartNo="";
		}
		
		if(strProdType == null){
			strProdType="";
		}
		
		
	    row.insertCell(0).innerHTML= "<input id=\"cbItemCodeSel."+(rowCount)+"\" type=\"checkbox\" class=\"ProdCheckBoxClass Box\" name=\"listPOSRecipe["+(rowCount)+"].strSelectedPOSItem\" size=\"8%\" value=\"Tick\" />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSRecipe["+(rowCount)+"].strPosItemCode\" id=\"txtPOSItemCode."+(rowCount)+"\" value='"+strPosItemCode+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listPOSRecipe["+(rowCount)+"].strPOSItemName\" id=\"txtPOSItemName."+(rowCount)+"\" value='"+strPOSItemName+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSRecipe["+(rowCount)+"].strProdCode\" id=\"strProdCode."+(rowCount)+"\" value='"+strProdCode+"' />";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"50%\" name=\"listPOSRecipe["+(rowCount)+"].strProdName\" id=\"strProdName."+(rowCount)+"\" value='"+strProdName+"' />";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSRecipe["+(rowCount)+"].strPartNo\" id=\"strPartNo."+(rowCount)+"\" value='"+strPartNo+"' />";
	    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listPOSRecipe["+(rowCount)+"].strProdType\" id=\"strProdType."+(rowCount)+"\" value='"+strProdType+"' />";
	   // row.insertCell(7).innerHTML= '<input type="button" class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';	    
	 
	   
	}
	
	
	function funDeleteRow(obj) 
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblPOSLinkUp");
	    table.deleteRow(index);
	}
	
	
	function funDeleteTableAllRows()
	{
		$("#tblPOSLinkUp tr").remove();
		/* var table = document.getElementById("tblPOSLinkUp");
		var rowCount = table.rows.length;
		for(var i=0;i<rowCount;i++)
		{
			table.deleteRow(i);
		} */
	}
	function funResetFields()
	{
		location.reload(true); 
	}
	
	/**
	 * After Selected POS Item 
	**/	
	function funChkOnClick()
	{
		var column_num;
		var row_num;
	    $("#tblPOSLinkUp td").click(function() {     
	    	 
	       //column_num = parseInt( $(this).index() ) ;
	       row_num = parseInt( $(this).parent().index() );    
	 
	     /*  alert("clo=="+column_num);
	        alert("row=="+row_num); */
	        
	        var table = document.getElementById("tblPOSLinkUp");
		    //var rowCount = table.rows.
	       /*  alert(document.getElementById("tblPOSLinkUp").rows[row_num].cells[1]); */
	       
	       
	    /*     $('td').click(function(){
	        	   var colIndex = $(this).parent().children().index($(this));
	        	   var rowIndex = $(this).parent().parent().children().index($(this).parent());
	        	   alert(document.getElementById("tblPOSLinkUp").rows[rowIndex].cells[colIndex].innerHTML);
	        	});
 */
	       
	       
	    });
	   
	    
	  
	}
	
	function funSetDataOnChkBoxClick(stritemCodes)
	{
		strCodes = strGCodes.split(",");
		var count=0;
		
		
	}
	
	function funValidate()
	{
	
		var table = document.getElementById("tblPOSLinkUp");
	    var rowCount = table.rows.length;
				
			if(rowCount<1)
			{
				alert("Please Load Item in Grid");
				return false;
			}
	}
	
	function funFillLinkData(){
		flgSACode=false;
		var searchUrl="";
		var ipAdd=$("#txtIPAddress").val();
		var port=$("#txtPortNo").val();
		var dbName=$("#txtDBName").val();
		var userName=$("#txtUserName").val();
		var pass=$("#txtPass").val();
		var subGroupName=$("#cmbSGName").val();
		var locName=$("#cmbLocName").val();
		var param=ipAdd+","+port+","+dbName+","+userName+","+pass+","+subGroupName+","+locName;
		
		/* var param='{"ipAdd":' + ipAdd + ',"port":"'
		+ port + '","dbName":"'
		+ dbName + '","userName":'
		+ userName + ',"pass":"'
		+ pass + ',"subGroupName":"'
		+ subGroupName + ',"locName":"'
		+ locName + '"}';
		
		var jsonManuF = $.parseJSON('[' + obj + ']'); */
		
		
		searchUrl=getContextPath()+"/loadLinkProdPOSData.html?param="+param;
		$("#hidstrShowLinked").val("Y");
		//alert(searchUrl);
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
    	    		funDeleteTableAllRows();
			    	$.each(response, function(i,item)
					{
			    		funAddRow(response[i]);
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
	
	
	function funTestDBConnection(){
		flgSACode=false;
		var searchUrl="";
		var ipAdd=$("#txtIPAddress").val();
		var port=$("#txtPortNo").val();
		var dbName=$("#txtDBName").val();
		var userName=$("#txtUserName").val();
		var pass=$("#txtPass").val();
		var subGroupName=$("#cmbSGName").val();
		var locName=$("#cmbLocName").val();
		var param=ipAdd+","+port+","+dbName+","+userName+","+pass;
		searchUrl=getContextPath()+"/testDBConnection.html?param="+param;
		
		$.ajax({
		        type: "GET",
		        url: searchUrl,
			    dataType: "json",
			    success: function(response)
			    {
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
	
	
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>POS Item Master Import</label>
	</div>

<br/>
<br/>

	<s:form name="POSItemMasterImport" method="POST" action="savePOSItemMasterImport.html?saddr=${urlHits}">

		<table class="transTable">
				
				<tr>
				
				<td><label>IP Address</label></td>
				<td><s:input type="text" id="txtIPAddress" 
						name="txtIPAddress" path="strIPAddress" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>
						
				<td><label>Port No</label></td>		
				<td><s:input type="text" id="txtPortNo" 
						name="txtPortNo" path="strPortNo" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>

				<td><label>DataBase Name</label></td>			
				<td><s:input type="text" id="txtDBName" 
						name="txtDBName" path="strDBName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>
				
				</tr>
				<tr>
						<td><label>User Name</label></td>	
						<td><s:input type="text" id="txtUserName" 
						name="txtUserName" path="strUserName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>
						
						<td><label>Password</label></td>	
						<td><s:input type="text" id="txtPass" 
						name="txtPass" path="strPass" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> </td>
						
						<td><label>Subgroup</label></td>
						<td><s:select id="cmbSGName"  path="strSGName" items="${listType}" cssClass="BoxW124px"/></td>
						
				
				</tr>
				<tr>
				<td ><label>Location</label></td>
					<td colspan="4"><s:select id="cmbLocName"  path="strLocName" items="${listTypeLoc}" cssClass="BoxW124px"/></td>
						<td>	<p>
						<input type="button" value="TestCon." tabindex="3" class="form_button" id="btnTest" onclick="funTestDBConnection()"  />
						<input type="button" value="ShowLinkProduct" tabindex="3" class="form_button" id="btnShow" onclick="funFillLinkData()"  />
						<input type="button" value="Excute" tabindex="3" class="form_button" id="btnSubmit" onclick="funFillPOSTable()"  />
						<input type="submit" value="Create/Update" tabindex="3" class="form_button" onclick="return funValidate();" />
						<input type="button" value="Reset" class="form_button" onclick="funResetFields()"/>
					</p>  </td>
						
				</tr>
				<tr>
							
				
				</tr>
				
			
			
		</table>
		
		<div class="dynamicTableContainer" style="height: 300px;">
			<table
				style="height: 28px; border: #0F0; width: 100%; font-size: 11px; font-weight: bold;">
				<tr bgcolor="#72BEFC">
				<td width="5%"><input type="checkbox" id="chkLocALL" />Select</td>
				<td style="width:10%;">POS Item Code</td>
				<td style="width:20%;">POS Item Name</td>
				<td style="width:9%;">WS Item Code</td>
				<td style="width:25%;">WS Item Name</td>
				<td style="width:10%;">PartNo</td>
				<td style="width:10%;">Type</td>	
							
			</tr>
		</table>
		<div
				style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 250px; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblPOSLinkUp"
					style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="transTablex col8-center">
					<tbody>
					<col style="width=15%">
					<col style="width:7.5%">					
					<col style="width:26%">
					<col style="width:9.5%">
					<col style="width:26%">
					<col style="width:11%">
					<col style="width:10%">
					
					</tbody>
				</table>
			</div>
		</div>
		<br />
		<s:input type="hidden" id="hidstrShowLinked" value="N" name="hidstrShowLinked"  path="strShowLinked" ></s:input>	
		<br><br>
		<div id="wait" style="display:none;width:60px;height:60px;border:0px solid black;position:absolute;top:60%;left:55%;padding:2px;">
				<img src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif" width="60px" height="60px" />
			</div>
	</s:form>
</body>
</html>
