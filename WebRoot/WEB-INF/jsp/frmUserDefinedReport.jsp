<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="<c:url value="/resources/js/jQuery.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.steps.js"/>"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/resources/css/steps.css"/>" />
<script>

		var seletedFields="",dblFields="",htmlForDblFields="",htmlForCriteriaFields="";

		$(function ()
		{
			$("#cmbGroupBy").change(function() 
			{
				if ($(this).val() != 'All') 
				{
					alert(dblFields);
				}
			});
			
			
			
			$("#wizard").steps({
			headerTag: "h2",
			bodyTag: "section",
			transitionEffect: "slide",
			onStepChanging: function (event, currentIndex, newIndex)
		    {
		        // Always allow going backward even if the current step contains invalid fields!
		       // alert("currentIndex = "+currentIndex+"\nnewIndex = "+newIndex);
		        var queryType = $('#queryType').val();
		        if(currentIndex == 0 && queryType == 'TABLE' && $('#strTable').val() == -1)
		        {
		        	alert("Please select table name.");
		       		return false;
		        }
		        	//('#Crd option:selected').text();
		        if(currentIndex == 1){
		        	var queryErrorMsg = "Incorrect Query";
		        	//Check Query length
		        	var queryStr = $("textarea#queryText").val().trim();
		        	
		        	if(queryStr.length == 0
		        			|| queryStr.substr(0,7).toUpperCase().indexOf("SELECT ") != 0 ){
		        		alert(queryErrorMsg);
		        		return false;
		        	}
		        	
		        	if(queryType == 'TABLE' && $('#strTable').val() == -1)
		            {
			        	$.ajax({
			 		        type: "GET",
			 		        url: getContextPath()+"/checkQuery.html?queryStr="+queryStr,
			 		        dataType: "json",
			 		        success: function(response)
			 		        {
			 		        	if(arguments[2].responseText == "true")
			 		        	{
			 		        		return true;
			 		        	}
			 		        	else
			 		        	{
			 		        		alert(queryErrorMsg);
			 		        		return false;
			 		        	}
			 				},
			 		        error: function(e)
			 		        {
			 		          	alert('Error in checking query: ' + e);
			 		        }
			 	      	 });
			        }
		        	else if(queryType == 'SQL' && queryStr.toUpperCase().indexOf("FROM") != -1 
		        			&& queryStr.toUpperCase().indexOf("SELECT") != -1)
		            {
		        		$("textarea#queryText").val().toUpperCase().indexOf("FROM")-4;
		        		var startIndex = queryStr.toUpperCase().indexOf("SELECT") + 6;
		        		var lastIndex = queryStr.toUpperCase().indexOf("FROM"); 
		        		var columnNames = queryStr.substr(startIndex, (lastIndex-startIndex));
		        		alert("columnNames : "+columnNames);
		        		var columns =  columnNames.split(",");
		        		var columnList;
		        		for(var count = 0; count<columns.length; count++){
							var column = columns[count];
							if(column.indexOf(".") != -1){
								column = column.split(".")[1];
							}
		        		}
		        	}
		        }
		        if (currentIndex > newIndex)
		        {
		            return true;
		        }
		        return true;
		    },
		    onStepChanged: function (event, currentIndex, priorIndex)
		    {
		    	 var tableName = $('#strTable').val();
		    	 var queryType = $('#queryType').val();
		    	 if (currentIndex == 1 && tableName != -1)
		         {
		             if(queryType == 'TABLE'){
		             	$("textarea#queryText").text("select * from "+$('#strTable option:selected').text());
		             }
		         } 
		    	 else if(currentIndex == 2)
		    	 {		        	 
		        	 if(queryType == 'TABLE')
		        	 {
			    		 $.ajax({
			 		        type: "GET",
			 		        url: getContextPath()+"/loadColumnNames.html?tableName="+tableName,
			 		        dataType: "json",
			 		        success: function(response)
			 		        {
			 		        	var html = "";
								$.each(response, function(i, column) 
								{
							        /*html +='<li><label id="lblColumn['+i+']"><input id="strSelectedFields['+i+']" type="checkbox" value="'+column+'" name="strSelectedFields['+i+']" onclick="funChk(this);"/>';
							        html +=column+"</label></li>";*/
							        
									html +='<li><input id="strSelectedFields['+i+']" type="checkbox" value="'+column+'" name="strSelectedFields['+i+']" onclick="funChk(this);"/>';
							        html +='<label id="lblColumn['+i+']">'+column+'</label></li>';
							        
							    });
								//alert(html);
							    $("#columns").html(html);
			 				},
			 		        error: function(e)
			 		        {
			 		          	alert('Error121212: ' + e);
			 		        }
			 	      	 });
		        	 }
		        	 else{
		        	 }
		         }
				 else if(currentIndex == 3)
				 {
		        	 if(queryType == 'TABLE')
		        	 {
			    		 $.ajax({
			 		        type: "GET",
			 		        url: getContextPath()+"/loadColumnNames.html?tableName="+tableName,
			 		        dataType: "json",
			 		        success: function(response)
			 		        {
			 		        	var html = '<option value="All">All</option>';
								$.each(response, function(key, value)
								{
									html += '<option value="' + value + '">'+ value + '</option>';
									
							    });
								html += '</option>';
								htmlForCriteriaFields=html;
								$('#cmbCriteriaCol').html(html);
			 				},
			 		        error: function(e)
			 		        {
			 		          	alert('Error121212: ' + e);
			 		        }
			 	      	 });
		        	 }
		         }
				 else if(currentIndex == 4)
				 {
		        	 if(queryType == 'TABLE')
		        	 {
		        		 funAddFieldsForSize();
		        		 
		        		 $.ajax({
				 		        type: "GET",
				 		        url: getContextPath()+"/loadColumnNames.html?tableName="+tableName,
				 		        dataType: "json",
				 		        success: function(response)
				 		        {
				 		        	var html = '<option value="All">All</option>';
									$.each(response, function(key, value)
									{
										html += '<option value="' + value + '">'+ value + '</option>';
										var pos=value.search("dbl");
										if(pos==0)
										{
											if(dblFields.length>0)
											{
												dblFields=dblFields+","+value;
											}
											else
											{
												dblFields=value;
											}
										}
								    });
									html += '</option>';
									$('#cmbGroupBy').html(html);
									$('#cmbSortBy').html(html);
				 				},
				 		        error: function(e)
				 		        {
				 		          	alert('Error121212: ' + e);
				 		        }
				 	      	 });
		        	 }
		         }
		    	 
		       /*  // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
		        if (currentIndex === 2 && priorIndex === 3)
		        {
		            $(this).steps("previous");
		            return;
		        }
		 
		        // Suppress (skip) "Warning" step if the user is old enough.
		        if (currentIndex === 2 && Number($("#age").val()) >= 18)
		        {
		            $(this).steps("next");
		        } */
		    },
		    onFinishing: function (event, currentIndex)
		    {
		    	return true;
		    },
		    onFinished: function (event, currentIndex)
		    {
		    	$('form[name="userDefinedReport"]').submit();
		    }
			});
			
			$("#queryType").change(function() 
			{
				var visibility =  $(this).val()=="TABLE" ? "" : "none";
				document.getElementById("tableName").style.display = visibility;
			});
			
			/*
			$("[id^=strSelectedFields]").on("click", function()
			{
				seletedFields="";
				var idd=this.id;
				var fieldName=this.value;				
				if(document.getElementById(idd).checked)
				{
					if(seletedFields.length>0)
					{
						seletedFields=seletedFields+","+fieldName;
					}
					else
					{
						seletedFields=fieldName;
					}
				}
				else
				{
					var arrDocCodes=seletedFields.split(',');
					seletedFields="";
					for(var i=0;i<arrDocCodes.length;i++)
					{
						if(fieldName==arrDocCodes[i])
						{
						}
						else
						{
							if(seletedFields.length>0)
							{
								seletedFields=seletedFields+","+arrDocCodes[i];
							}
							else
							{
								seletedFields=arrDocCodes[i];
							}
						}
					}
				}
			});*/
		});
		
		
		function funChk(obj)
		{
			var idd=document.getElementById(obj.id);
			var arrSplit1=obj.id.split('[');
			var arrSplit=arrSplit1[1].split(']');
			var fieldId="lblColumn["+arrSplit[0]+"]";
			var fieldName=document.getElementById(fieldId).innerHTML;
			//alert(idd.checked);
			//alert(document.getElementById("lblColumn[9]").innerHTML);
			
			if(idd.checked)
			{
				if(seletedFields.length>0)
				{
					seletedFields=seletedFields+","+fieldName;
				}
				else
				{
					seletedFields=fieldName;
				}
			}
			else
			{
				var arrDocCodes=seletedFields.split(',');
				seletedFields="";
				for(var i=0;i<arrDocCodes.length;i++)
				{
					if(fieldName==arrDocCodes[i])
					{
					}
					else
					{
						if(seletedFields.length>0)
						{
							seletedFields=seletedFields+","+arrDocCodes[i];
						}
						else
						{
							seletedFields=arrDocCodes[i];
						}
					}
				}
			}
		}
		
		
		function funChange(obj)
		{
			htmlForDblFields="";
			if(document.getElementById(obj.id).checked)
			{			
				var arrDblFields=dblFields.split(',');
				for(var i=0;i<arrDblFields.length;i++)
				{
					htmlForDblFields +='<li><input id="dblFields['+i+']" type="checkbox" value="'+arrDblFields[i]+'" name="dblFields['+i+']"/>';
					htmlForDblFields +='<label id="lblDblColumn['+i+']">'+arrDblFields[i]+'</label></li>';
				}
				$('#tdGroupBy').html(htmlForDblFields);
			}
			else
			{
				$('#tdGroupBy').html(htmlForDblFields);
			}
		}
		
		function funAddFieldsForSize()
		{	  		    
			var table = document.getElementById("tblFieldSize");
			var rowCount = table.rows.length;
			
			var arrDocCodes=seletedFields.split(',');
			for(var i=0;i<arrDocCodes.length;i++)
			{
				var row = table.insertRow(i);
				var name='strArrFieldName['+i+']';
				var name1='strArrFieldSize['+i+']';
				row.insertCell(0).innerHTML= "<input type=\"text\" id="+name+" name="+name+" style=\"width: 20px;\" value='"+arrDocCodes[i]+"'/>";
				row.insertCell(1).innerHTML= "<input type=\"text\" name="+name1+" style=\"width: 150px;\" value='' />";	
			}
			return false;
		}		
		
		
		function funAddRow()
		{
			var fieldName = $("#cmbCriteriaCol").val();
			//var openingBracket = $("#txtOpeningBracket").val();
			//var closingBracket = $("#txtClosingBracket").val();
			var openingBracket = '(';
			var closingBracket = ')';
			var searchCode = $("#txtSearchCode").val();
			var condition = '';
			if($("#cmbCondition").val()=='>')
			{
				condition='&gt;';
			}
			else if($("#cmbCondition").val()=='<')
			{
				condition='&lt;';
			}
			else if($("#cmbCondition").val()=='>=')
			{
				condition='&gt;=';
			}
			else if($("#cmbCondition").val()=='<=')
			{
				condition='&lt;=';
			}
			else
			{
				condition='=';
			}
			
			var criteria = $("#txtCriteria").val();
			var condOperator = $("#cmbCondOperator").val();
			  		    
			var table = document.getElementById("tblCriteria");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			var name='strArrCriteria['+rowCount+']';
			//row.insertCell(0).innerHTML= "<input type=\"text\" name="+name+" style=\"width: 20px;\" value="+openingBracket+">";
			row.insertCell(0).innerHTML= "<input type=\"text\" name="+name+" style=\"width: 150px;\" value="+fieldName+" >";
			row.insertCell(1).innerHTML= "<input type=\"text\" name="+name+" style=\"width: 150px;\" value="+searchCode+">";
			row.insertCell(2).innerHTML= "<input type=\"text\" name="+name+" style=\"width: 20px;\" value="+condition+">";
			row.insertCell(3).innerHTML= "<input type=\"text\" name="+name+" value="+criteria+">";
			//row.insertCell(5).innerHTML= "<input type=\"text\" name="+name+" style=\"width: 20px;\" value="+closingBracket+">";
			row.insertCell(4).innerHTML= "<input type=\"text\" name="+name+" value="+condOperator+">";
			row.insertCell(5).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
						    
			funResetCriteriaFields();
			return false;
		}
		
		
		function funResetCriteriaFields()
		{			
			$('#cmbCriteriaCol').html(htmlForCriteriaFields);
			$("#txtSearchCode").val('');
			$("#txtCriteria").val('');
			$("#cmbCondition").val('=');
			$("#cmbCondOperator").val('none');
		}
		
		
		function funDeleteRow(obj)
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblCriteria");
		    table.deleteRow(index);
		}

</script>
</head>

<body>
	<s:form action="saveUserDefinedReport.html" method="POST" name="userDefinedReport">
		<div id="wizard">
			<h2>Report Details</h2>
			<section>
				<div align="center">
					<table>
					<tr>
						<th colspan="2" align="center">Report Details</th>
						</tr>
						<tr>
							<td align="left">Report Code</td>
							<td><s:input path="strReportCode" type="text"/></td>
						</tr>
						<tr>
							<td>Report Description</td>
							<td><s:input path="strReportDesc" type="text"/></td>
						</tr>
						<tr>
							<td>Category</td>
							<td>
								<s:select path="strCategory" id="cmbCategory">
								</s:select>
							</td>
						</tr>
						<tr>
							<td>Operational</td>
							<td>
								<s:select path="strOperational" id="cmbOperational">
									<option value="Y">Yes</option>
									<option value="N">No</option>
								</s:select>
							</td>
						</tr>
						<tr>
							<td>Type</td>
							<td><s:select path="strType" id="queryType">
									<option value="SQL">SQL Statement</option>
									<option value="TABLE">Table</option>
							</s:select></td>
						</tr>
						<tr id="tableName" style="display:none">
							<td>Table Name</td>
							<td><s:select path="strTable">
							   		<s:option value="-1" label="Select Table"/>
               					 	<s:options items="${tableNames}"/>
								</s:select>
							</td>
						</tr>
					</table>
				</div>
			</section>
			
			<h2>Query</h2>
			<section>
				<div>
					<table>
						<tr>
							<td>Query</td>
						</tr>
						<tr>
							<td><s:textarea rows="10" cols="50" id ="queryText" path="strQuery"></s:textarea></td>
						</tr>
						<tr>
							<td><input type="button" value="Check Query"></td>
						</tr>
					</table>
				</div>
			</section>
			
			<h2>Field Selection</h2>
			<section>
				<div>
					<ul id="columns"></ul>
				</div>
			</section>
			
			<h2>Criteria</h2>
			
			<section>			
				<div>
					<table id="criteriaTable" border="1" style="display:inline">
						<tr>
							<th>Del</th>
							<!-- <th>(</th> -->
							<th>Field</th>
							<th>Search Code</th>
							<th>Condition</th>
							<th>Criteria</th>
							<!-- <th>)</th>-->
							<th>AndOr</th>
						</tr>
						
						<tr>
							<td></td>
							<!-- <td><input style="width: 20px;" type="text" id="txtOpeningBracket"></td> -->
							<td>
								<select id="cmbCriteriaCol" style="width: 150px;">
								</select>
							</td>
							
							<td>
								<input id="txtSearchCode" style="width: 150px;" type="text">
							</td>
							
							<td style="width:20px;">
								<select id="cmbCondition" style="width:50px;">								
									<option value="=">=</option>
									<option value="<">&lt;</option>
									<option value=">">&gt;</option>									
									<option value=">=">&gt;=</option>
									<option value="<=">&lt;=</option>
								</select>
							</td>
							
							<td><input type="text" id="txtCriteria"></td>
							
							<!-- <td><input style="width: 20px;" type="text" id="txtClosingBracket"></td>  -->
							<td>
								<select id="cmbCondOperator" style="width: 75px;">
									<option value="none">None</option>
									<option value="and">AND</option>
									<option value="or">OR</option>
								</select>
							</td>
							
							<td><input type="button" value="ADD" onclick="funAddRow();"/></td>
						</tr>
					</table>
					
					<table id="tblCriteria">
						
					</table>
				</div>
				
			</section>
			
		
			<h2>Page Setting</h2>
			<section>
				<div>
					<table id="tblPageSetting" border="1" style="display:inline">
						<tr>
							<td><label>Layout</label></td>
							<td></td>
							<td><label>Grouping</label></td>
						</tr>
												
						<tr>
							<td><label>Page Layout</label></td>
							
							<td style="width:20px;">
								<s:select id="cmbPageLayout" path="strLayout" style="width:50px;">
									<s:option value="Potrait">Potrait</s:option>
									<s:option value="Landscape">Landscape</s:option>
									<s:option value="Autosize">Autosize</s:option>
								</s:select>
							</td>
							
							<td><label>Group By</label></td>
							<td>
								<s:select id="cmbGroupBy" path="strGroupBy" style="width:50px;" onchange="funChange(this);">									
								</s:select>
							</td>
						</tr>
						
						<tr>
							<td id="tdGroupBy">
							</td>
						</tr>

						<tr>
							<td><label>Field Size</label></td>
							<td>
								<table>
									<tr>
										<td><label>Field Name</label></td>
										<td><label>Size(Inches)</label></td>
									</tr>
								</table>
							
								<table id="tblFieldSize">
								</table>
							</td>
							
							<td><label>Sort By</label></td>
							<td>
								<s:select id="cmbSortBy" path="strSortBy" style="width:50px;">									
								</s:select>
							</td>
							<td id="tdSortBy">
							</td>
						</tr>
					</table>
					
					<table id="tblPageHeader">
						<tr>
							<td><label>Page Header</label></td>
						</tr>
						
						<tr>
							<td><label>Line1</label></td>
							<td><s:input id="txtHeaderLine1" path="strHeadLine1"/></td>
						</tr>
						
						<tr>
							<td><label>Line2</label></td>
							<td><s:input id="txtHeaderLine2" path="strHeadLine2"/></td>
						</tr>
					</table>
					
					
					<table id="tblPageFooter">
						<tr>
							<td><label>Page Footer</label></td>
						</tr>
						
						<tr>
							<td><label>Line1</label></td>
							<td><s:input id="txtFooterLine1" path="strFootLine1"/></td>
						</tr>
						
						<tr>
							<td><label>Line2</label></td>
							<td><s:input id="txtFooterLine2" path="strFootLine2"/></td>
						</tr>
					</table>
					
				</div>
			</section>
			
			
 		</div>
	</s:form>

</body>
</html>