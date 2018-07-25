<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="<spring:url value="/resources/js/jQuery.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>	
	<script type="text/javascript" src="<spring:url value="/resources/js/validations.js"/>"></script>
	<script>
 	
	var activeTabId="tab1";
	var seletedFields="",dblFields="",htmlForDblFields="",htmlForCriteriaFields="",criteria1="",selectedUsers="";
	var reportSql="",fieldName1;
	var query,queryColumns,queryTableName,queryWhereClause,queryGroupByClause,queryOrderByClause;
	
	$(document).ready(function() 
	{
		$(".tab_content").hide();
		$(".tab_content:first").show();
		$("ul.tabs li").click(function() 
		{
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();
			var activeTab = $(this).attr("data-state");
			$("#" + activeTab).fadeIn();
			activeTabId=activeTab;
		});
				
		$("#btnExport").click(function (e)
		{
			if($("#cmbQueryType").val()=='TABLE')
			{
				window.location.href=getContextPath()+"/exportReportData.html?reportQuery="+reportSql+"&columns="+seletedFields+"&queryType=hql";
			}
			else
			{
				window.location.href=getContextPath()+"/exportReportData.html?reportQuery="+reportSql+"&columns="+seletedFields+"&queryType=sql";				
			}
		});
		
		$("#btnNext").click(function (e)
		{
			if(activeTabId=="tab6")
			{
				$('form[name="userDefinedReport"]').submit();
			}
		});
	});
	
	
	function funHelp(transactionName)
	{
		fieldName1=transactionName;
    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
    }
	
	
	function funResetFields()
	{
		seletedFields="";
		dblFields="";
		htmlForDblFields="";
		htmlForCriteriaFields="";
		criteria1="";
		reportSql="";
		query="";
		queryColumns="";
		queryTableName="";
		queryWhereClause="";
		queryGroupByClause="";
		queryOrderByClause="";
		selectedUsers="";
	}
	
	function funSetData(code)
	{
		switch (fieldName1) 
		{
		   case 'udreportname':
			   funResetFields();
			   funSetReportData(code);
		       break;
		}
	}
	
	function funSetReportData(code)
	{
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadUserDefinedReport.html?reportCode=" + code,
			dataType : "json",
			success : function(response)
			{
				$.each(response, function(i, data) 
				{						
					funSetReportDetails(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[11]);
				});
			},
			error : function(e){
			}
		});
	}
	
	function funSetReportDetails(reportCode,reportDesc,category,operational,type,tbName,sqlQuery,selFields,criteriaForSQL,users)
	{
		selectedUsers=users;
		$("#txtReportCode").val(reportCode);
		$("#txtReportDesc").val(reportDesc);
		$("#cmbCategory").val(category);
		$("#cmbOperational").val(operational);
		$("#cmbQueryType").val(type);
		if(type=='TABLE')
		{
			var visibility =  $("#cmbTableName").val()=="TABLE" ? "" : "none";
			document.getElementById("tableName").style.display = visibility;
			//$("#cmbTableName").val(tbName);
		}
		
		if(type=='SQL Based')
		{
			funFillQueryTab(tbName,sqlQuery);
		}
		funFillFieldTab(selFields,type);
		funFillCriteriaTab(criteriaForSQL);
		if(type=='TABLE')		
		{
			funFillCriteriaForTable();
		}
		else
		{
			funFillCriteriaForSQL();
		}
	}
	
	
	function funFillQueryTab(tbName,sqlQuery)
	{
		//tbName=tbName.substring(2,tbName.length).trim();
		//$("#txtAreaQuery").val(tbName);		
		$("#txtAreaQuery").val(sqlQuery);		
	}
	
	function funFillFieldTab(selFields,type)
	{
		seletedFields=selFields;
		if(type=='TABLE')
		{
			funFillColumnsForTable();
		}			
		else
		{
			funFillColumnsForSQL();
		}		
	}
	
	
	function funFillCriteriaTab(criteriaForSQL)
	{
		funDeleteAllRows();
		criteriaForSQL=criteriaForSQL.substring(criteriaForSQL.indexOf('where')+5,criteriaForSQL.length).trim();
		
		var spCr=criteriaForSQL.split('and');
		for(var cnt1=0;cnt1<spCr.length;cnt1++)
		{
			if(spCr[cnt1].search("=")>0)
			{
				var arrCr=spCr[cnt1].split('=');
				var op="none";
				if((cnt1+1)<spCr.length)
				{
					op="and";
				}
				funAddRowForUpdate(arrCr[0],"","=",arrCr[1],op);
			}
			else if(spCr[cnt1].search(">")>0)
			{
				var arrCr=spCr[cnt1].split('>');
				var op="none";
				if((cnt1+1)<spCr.length)
				{
					op="and";
				}
				funAddRowForUpdate(arrCr[0],"",">",arrCr[1],op);
			}
			else if(spCr[cnt1].search("<")>0)
			{
				var arrCr=spCr[cnt1].split('<');
				var op="none";
				if((cnt1+1)<spCr.length)
				{
					op="and";
				}
				funAddRowForUpdate(arrCr[0],"","<",arrCr[1],op);
			}
			else if(spCr[cnt1].search(">=")>0)
			{
				var arrCr=spCr[cnt1].split('>=');
				var op="none";
				if((cnt1+1)<spCr.length)
				{
					op="and";
				}
				funAddRowForUpdate(arrCr[0],"",">=",arrCr[1],op);
			}
			else if(spCr[cnt1].search("<=")>0)
			{
				var arrCr=spCr[cnt1].split('<=');
				var op="none";
				if((cnt1+1)<spCr.length)
				{
					op="and";
				}
				funAddRowForUpdate(arrCr[0],"","<=",arrCr[1],op);
			}
		}
	}
	
	
	function funFillColumnsForTable()
	{
		$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadColumnNames.html?tableName="+$("#cmbTableName").val().trim(),
		        dataType: "json",
		        success: function(response)
		        {
		        	var html = "";
					$.each(response, function(i, column) 
					{
						html +='<li><input id="strSelectedFields['+i+']" type="checkbox" value="'+column+'" name="strSelectedFields['+i+']" onclick="funChk(this);"/>';
				        html +='<label id="lblColumn['+i+']">'+column+'</label></li>';
				    });
			    	$("#chkColumns").html(html);
				},
		        error: function(e)
		        {
		          	alert('Error121212: ' + e);
		        }
	      	 });
	}
	
	function funFillColumnsForSQL() 
	{
		queryTableName="";
		queryWhereClause="";
		queryOrderByClause="";
		queryGroupByClause="";
		
		query=$("#txtAreaQuery").val().trim().toLowerCase();
		queryColumns=query.substring(query.indexOf("select")+6,query.indexOf("from")).trim();
		var whereIndex=query.search("where");
		var tableEndIndex=query.search("where");
		var grpByIndex=query.search("group by");
		var orderByIndex=query.search("order by");
		
		if(whereIndex<0) // Query does not contain where clause
		{
			if(grpByIndex<0) // Query does not contain group by clause
			{
				if(orderByIndex<0) // Query does not contain order by clause
				{
					queryTableName=query.substring(query.indexOf("from")+4,query.trim().length).trim();
				}
				else
				{
					tableEndIndex=query.indexOf("order by");
					queryTableName=query.substring(query.indexOf("from")+4,tableEndIndex).trim();
					queryOrderByClause=query.substring(query.indexOf("order by"),query.trim().length).trim();
				}
			}
			else
			{
				tableEndIndex=query.indexOf("group by");
				queryTableName=query.substring(query.indexOf("from")+4,tableEndIndex).trim();
				
				if(orderByIndex<0) // Query does not contain order by clause
				{
					queryGroupByClause=query.substring(query.indexOf("group by"),query.trim().length).trim();
				}
				else
				{
					queryGroupByClause=query.substring(query.indexOf("group by"),orderByIndex).trim();
					queryOrderByClause=query.substring(query.indexOf("order by"),query.trim().length).trim();
				}
			}
		}
		else
		{
			whereIndex=query.indexOf("where");
			queryTableName=query.substring(query.indexOf("from")+4,tableEndIndex).trim();
			
			if(grpByIndex<0) // Query does not contain group by clause
			{
				if(orderByIndex<0) // Query does not contain order by clause
				{
					queryTableName=query.substring(query.indexOf("from")+4,tableEndIndex).trim();
					queryWhereClause=query.substring(query.indexOf("where"),query.trim().length).trim();
				}
				else
				{
					whereIndex=query.indexOf("order by");
					queryWhereClause=query.substring(query.indexOf("where"),whereIndex).trim();
					queryOrderByClause=query.substring(query.indexOf("order by"),query.trim().length).trim();
				}
			}
			else
			{
				whereIndex=query.indexOf("group by");
				
				queryWhereClause=query.substring(query.indexOf("where"),whereIndex).trim();
				if(orderByIndex<0) // Query does not contain order by clause
				{
					queryGroupByClause=query.substring(query.indexOf("group by"),query.trim().length).trim();
				}
				else
				{
					queryGroupByClause=query.substring(query.indexOf("group by"),orderByIndex).trim();
					queryOrderByClause=query.substring(query.indexOf("order by"),query.trim().length).trim();
				}
			}
		}
		
		if(queryColumns.trim()=='*')
		{
			$.ajax({
 		        type: "GET",
 		        url: getContextPath()+"/loadColumnNames1.html?tableName="+queryTableName,
 		        dataType: "json",
 		        success: function(response)
 		        {
 		        	var html = "";
					$.each(response, function(i, column) 
					{
						html +='<li><input id="strSelectedFields['+i+']" type="checkbox" value="'+column+'" name="strSelectedFields['+i+']" onclick="funChk(this);"/>';
				        html +='<label id="lblColumn['+i+']">'+column+'</label></li>';
				    });
				    $("#chkColumns").html(html);
 				},
 		        error: function(e)
 		        {
 		          	alert('Error121212: ' + e);
 		        }
 	      	 });
		}
		else
		{
			var html="";
			var tempColName="";
			var arrCol=queryColumns.trim().split(',');
			for(var cnt1=0;cnt1<arrCol.length;cnt1++)
			{
				if(cnt1==0)
				{
					//seletedFields=arrCol[cnt1];
					var arrColTempName=arrCol[cnt1].trim().split(' ');
					if(arrColTempName.length>1)
					{
						seletedFields=arrColTempName[1];
						tempColName=arrColTempName[1];
					}
					else
					{
						seletedFields=arrColTempName[0];
						tempColName=arrColTempName[0];
					}
				}
				else
				{
					//seletedFields=seletedFields+","+arrCol[cnt1];
					
					var arrColTempName=arrCol[cnt1].trim().split(' ');
					if(arrColTempName.length>1)
					{
						seletedFields=seletedFields+","+arrColTempName[1];
						tempColName=arrColTempName[1];
					}
					else
					{
						seletedFields=seletedFields+","+arrColTempName[0];
						tempColName=arrColTempName[0];
					}
				}
				//html +='<li><input id="strSelectedFields['+cnt1+']" type="checkbox" checked value="'+arrCol[cnt1]+'" name="strSelectedFields['+cnt1+']" onclick="funChk(this);"/>';
		        //html +='<label id="lblColumn['+cnt1+']">'+arrCol[cnt1]+'</label></li>';
		        
				html +='<li><input id="strSelectedFields['+cnt1+']" type="checkbox" checked value="'+tempColName+'" name="strSelectedFields['+cnt1+']" onclick="funChk(this);"/>';
		        html +='<label id="lblColumn['+cnt1+']">'+tempColName+'</label></li>';
			}
			$("#chkColumns").html(html);
		}
	}
	
	
	function funFillCriteriaForTable()
	{
		$.ajax({
		        type: "GET",
		       	url: getContextPath()+"/loadColumnNames.html?tableName="+$("#cmbTableName").val().trim(),
		        dataType: "json",
		        success: function(response)
		        {
		        	var html = '<option value="All">All</option>';
		        	var selColumns= '<option value="All">All</option>';
				$.each(response, function(key, value)
				{
					html += '<option value="' + value + '">'+ value + '</option>';
			    });
				html += '</option>';
				htmlForCriteriaFields=html;
				$('#cmbCriteriaCol').html(html);
				
				var spCol=seletedFields.split(',');
				for(var cnt=0;cnt<spCol.length;cnt++)
				{
					selColumns += '<option value="' + spCol[cnt] + '">'+ spCol[cnt] + '</option>';
				}
				selColumns += '</option>';
				$('#cmbGroupBy').html(selColumns);
				$('#cmbSortBy').html(selColumns);
				},
		        error: function(e)
		        {
		          	alert('Error121212: ' + e);
		        }
	      	 });
	}
	
	
	function funFillCriteriaForSQL()
	{
		var html = '<option value="All">All</option>';
		var selColumns= '<option value="All">All</option>';
		var q=$("#txtAreaQuery").val().trim().toLowerCase();
		var columnNames=q.substring(q.indexOf('select')+6,q.indexOf('from')).trim();
		var spColumns=columnNames.split(',');
		for(var cn=0;cn<spColumns.length;cn++)
		{
			//if(spColumns[cn].search('.')<0 || spColumns[cn].search('.')<1)
			if(spColumns[cn].trim().indexOf('.')<0)
			{
				var tempColName="";
				var arrColTempName=spColumns[cn].trim().split(' ');
				if(arrColTempName.length>1)
				{
					tempColName=arrColTempName[1];
				}
				else
				{
					tempColName=arrColTempName[0];
				}
				
				//html += '<option value="' + spColumns[cn] + '">'+ spColumns[cn] + '</option>';
				html += '<option value="' + tempColName + '">'+ tempColName + '</option>';
			}
			else
			{
				/*var sp1=spColumns[cn].split('.');
				html += '<option value="' + sp1[1] + '">'+ sp1[1] + '</option>';*/
				
				var tempColName="";
				var arrColTempName=spColumns[cn].trim().split(' ');
				if(arrColTempName.length>1)
				{
					tempColName=arrColTempName[1];
				}
				else
				{
					tempColName=arrColTempName[0];
				}
				
				html += '<option value="' + tempColName + '">'+ tempColName + '</option>';
			}
		}
		html += '</option>';
		htmlForCriteriaFields=html;
		$('#cmbCriteriaCol').html(html);
		$('#cmbGroupBy').html(html);
		$('#cmbGroupByOn').html(html);
		$('#cmbSortBy').html(html);
	}
	
	$(function ()
	{
		$("#cmbQueryType").change(function() 
		{
			var visibility =  $(this).val()=="TABLE" ? "" : "none";
			document.getElementById("tableName").style.display = visibility;
		});
		
		$("#btnNext").click(function( event )
		{
			$("#btnNext").val("Next");
			switch(activeTabId)
			{
				case "tab1":
					
					if($("#txtReportCode").val().trim().length==0)
					{
						alert('Please Enter Report Code!!!');
						return;
					}
					if($("#txtReportDesc").val().trim().length==0)
					{
						alert('Please Enter Report Description!!!');
						return;
					}
					
					if($("#cmbQueryType").val()=='TABLE')
					{
						$("#t3").trigger('click');
						aciveTabId="tab3";
					}
					else
					{
						$("#t2").trigger('click');
						aciveTabId="tab2";
					}
					break;
					
				case "tab2":
					
					if($("#cmbQueryType").val()=='SQL Based')
					{
						if($("#txtAreaQuery").val().trim()=='')
						{
							alert('Enter Query!!!');
							return;
						}	
					}
					
					$("#t3").trigger('click');
					aciveTabId="tab3";
					break;
					
				case "tab3":
					$("#t4").trigger('click');
					aciveTabId="tab4";
					break;
					
				case "tab4":
					$("#t5").trigger('click');
					aciveTabId="tab5";
					break;
					
				case "tab5":
					$("#btnNext").val("Finish");
					$("#t6").trigger('click');
					aciveTabId="tab6";
					break;
			}
			
			if(aciveTabId=="tab3") 
			{
				seletedFields="";
				if($("#cmbQueryType").val()=='TABLE')// For Table Report
				{
					funFillColumnsForTable();
				}
				else // For SQL Based Report
				{
					funFillColumnsForSQL();
				}
			}
			
			if(activeTabId=='tab4')// Criteria Tab
			{				
				var tableSelCol = document.getElementById("tblColumns");
				var rowCnt2 = tableSelCol.rows.length;
				var rows2=tableSelCol.rows;
			    for(var cnt=0;cnt<rowCnt2;cnt++)
			   	{
			     	var cells1=rows2[cnt].cells;
			     	var fieldName=cells1[0].innerHTML;
			   	}
			    
				if($("#cmbQueryType").val()=='TABLE')
	        	{
					funFillCriteriaForTable();
	        	}
				else
	        	{
					funFillCriteriaForSQL();
	        	}
			}
			
			if(activeTabId=="tab5") // Preview Tab
			{
				var sql="",sqlType="";
				if($("#cmbQueryType").val()=='TABLE')
	        	{	
					$("#txtFinalColumns").val(seletedFields);
					sql="select "+seletedFields+" from "+$("#cmbTableName").val().trim();
					sqlType="hql";
					var cr="";
					var tableCriteria = document.getElementById("tblCriteria");
					var rowCnt = tableCriteria.rows.length;
					var rows1=tableCriteria.rows;
				    for(var cnt=0;cnt<rowCnt;cnt++)
				   	{
				    	var fieldName=document.getElementById("txtFieldName."+cnt).value;
				    	var criteria=document.getElementById("txtCriteria."+cnt).value;
				    	var searchCode=document.getElementById("txtSearchCode."+cnt).value;
				    	var condition=document.getElementById("txtCondition."+cnt).value;
				    	var condOperator=document.getElementById("txtCondOperator."+cnt).value;
				    	criteria=criteria.replace('#',' ');
				    	
				    	if(fieldName.substring(0,2).trim()=='dt')
				    	{
				    		fieldName='date('+fieldName+')';
				    	}
				    	cr+=fieldName+" "+searchCode+" "+condition+" '"+criteria+"' ";
						if(condOperator!='none')
						{
							cr+=condOperator+" ";
						}
				   	}
					
					if(cr.trim()!='')
					{
						sql+=" where "+cr;
						var whClause="where "+cr;
						$("#txtFinalWhere").val(whClause);
					}
					
					var table = document.getElementById("tblGroupBy");
					var rowCount1 = table.rows.length;
				    var tableRows=table.rows;
				    var groupByColumns="",sortByColumns="";
				    for(var cnt=0;cnt<rowCount1;cnt++)
				   	{
				     	var cells1=tableRows[cnt].cells;
				    	var field=cells1[0].innerHTML;
				    	field=field.substring((field.indexOf("value")+7),field.indexOf("type")-2);
				    	groupByColumns+=","+field;
				   	}
					if(groupByColumns!='')
					{
						groupByColumns=groupByColumns.substring(1,groupByColumns.length);
						sql+=" group by "+groupByColumns;
						$("#txtFinalGroup").val(groupByColumns);
					}
					
					var table2 = document.getElementById("tblSortBy");
					var rowCount2 = table2.rows.length;
				    var tableRows2=table2.rows;
				    for(var cnt=0;cnt<rowCount2;cnt++)
				   	{
				     	var cells1=tableRows2[cnt].cells;
				    	var field=cells1[0].innerHTML;
				    	field=field.substring((field.indexOf("value")+7),field.indexOf("type")-2);
				    	sortByColumns+=","+field;
				   	}
					
					if(sortByColumns!='')
					{
						sortByColumns=sortByColumns.substring(1,sortByColumns.length);
						sql+=" order by "+sortByColumns;
						$("#txtFinalSort").val(sortByColumns);
					}
					reportSql=sql;
					$("#txtFinalQuery").val(sql);
	        	}
				else
				{
					sqlType="sql";
					
					var tblGrpByOn = document.getElementById("tblGroupByOn");
					var rowCntGrpOn = tblGrpByOn.rows.length;
				    var grpOnRows=tblGrpByOn.rows;
				    var groupByOnColumns="";
				    for(var cnt=0;cnt<rowCntGrpOn;cnt++)
				   	{
				     	var cells1=grpOnRows[cnt].cells;
				    	var field=cells1[0].innerHTML;
				    	field=field.substring((field.indexOf("value")+7),field.indexOf("type")-2);
				    	groupByOnColumns+=","+field;
				   	}
					alert(groupByOnColumns);
					
					var query=$("#txtAreaQuery").val().trim().toLowerCase();
					
					var afterfrom = query.split("from");
					var onlywhere="";
					var onlygroupby="";
					var onlyorderby="";
					var afterwhere ="";
					var afterGroupby ="";
					var afterOrderby ="";
					if(afterfrom.length>1)
						{
						  var afterwhere = afterfrom[1].split("where");
						  onlywhere=afterwhere[0];
							if(afterwhere.length>1)
								{
								     var afterGroupby =afterwhere[1].split("group by");
								     onlywhere="where "+ afterGroupby[0];
								     if(afterGroupby.length>1)
								    	 {
								    	  	var	afterOrderby =afterGroupby[1].split("order by");
								    	  	onlygroupby="group by"+  afterOrderby[0];
								    	  	onlyorderby="order by"+ afterOrderby[1];
								    	 }
								     
								}
						}
					
					$("#txtFinalWhere").val(onlywhere);
					$("#txtFinalGroup").val(onlygroupby);
					$("#txtFinalSort").val(onlyorderby);
					
					 
					
					
					
					
					
					
					
					
					
					var spSelColumns=seletedFields.split(',');
					
					var columnNames="";
					for(var cn=0;cn<spSelColumns.length;cn++)
					{
						if(spSelColumns[cn].trim().indexOf('.')<0)
						{
							//columnNames+=","+spSelColumns[cn];
							var arrColTempName=spSelColumns[cn].split(' ');
							if(arrColTempName.length>1)
							{
								var flgGrpByOn=false;
								var spGrpByOn=groupByOnColumns.split(',');
								for(var k=0;k<spGrpByOn.length;k++)
								{
									if(spGrpByOn[k]==arrColTempName[1])
									{
										flgGrpByOn=true;
										break;
									}
									else
									{
										flgGrpByOn=false;
									}
								}
								if(flgGrpByOn==true)
								{
									arrColTempName[1]='sum('+arrColTempName[1]+')';
								}
								columnNames+=","+arrColTempName[1];
							}
							else
							{
								var flgGrpByOn=false;
								var spGrpByOn=groupByOnColumns.split(',');
								for(var k=0;k<spGrpByOn.length;k++)
								{
									if(spGrpByOn[k]==arrColTempName[0])
									{
										flgGrpByOn=true;
										break;
									}
									else
									{
										flgGrpByOn=false;
									}
								}
								if(flgGrpByOn==true)
								{
									arrColTempName[0]='sum('+arrColTempName[0]+')';
								}
								columnNames+=","+arrColTempName[0];
							}
						}
						else
						{
							//var sp=spSelColumns[cn].split('.');
							var sp=spSelColumns[cn];
							//columnNames+=","+sp[1];
							//var arrColTempName=sp[1].split(' ');
							var arrColTempName=sp.split(' ');
							if(arrColTempName.length>1)
							{
								var flgGrpByOn=false;
								var spGrpByOn=groupByOnColumns.split(',');
								for(var k=0;k<spGrpByOn.length;k++)
								{
									if(spGrpByOn[k]==arrColTempName[1])
									{
										flgGrpByOn=true;
										break;
									}
									else
									{
										flgGrpByOn=false;
									}
								}
								if(flgGrpByOn==true)
								{
									arrColTempName[1]='sum('+arrColTempName[1]+')';
								}
								columnNames+=","+arrColTempName[1];
							}
							else
							{
								var flgGrpByOn=false;
								var spGrpByOn=groupByOnColumns.split(',');
								for(var k=0;k<spGrpByOn.length;k++)
								{
									if(spGrpByOn[k]==arrColTempName[0])
									{
										flgGrpByOn=true;
										break;
									}
									else
									{
										flgGrpByOn=false;
									}
								}
								if(flgGrpByOn==true)
								{
									arrColTempName[0]='sum('+arrColTempName[0]+')';
								}
								columnNames+=","+arrColTempName[0];
							}
						}
					}
					columnNames=columnNames.substring(1,columnNames.length).trim();
					//sql="select "+columnNames+" from ("+query+") a ";
					sql="select "+columnNames+" from "+afterwhere[0]  ;
					
					if(afterwhere.length>1)
					{
						sql+="where "+afterwhere[1];
						
					}	
					
					$("#txtFinalColumns").val(columnNames);
					
					var cr="";
					var tableCriteria = document.getElementById("tblCriteria");
					var rowCnt = tableCriteria.rows.length;
					var rows1=tableCriteria.rows;
				    for(var cnt=0;cnt<rowCnt;cnt++)
				   	{
				    	var fieldName=document.getElementById("txtFieldName."+cnt).value;
				    	var criteria=document.getElementById("txtCriteria."+cnt).value;
				    	var searchCode=document.getElementById("txtSearchCode."+cnt).value;
				    	var condition=document.getElementById("txtCondition."+cnt).value;
				    	var condOperator=document.getElementById("txtCondOperator."+cnt).value;
				    	//criteria=criteria.replace('#',' ');
				    	var criteria1='';
				    	var spCriteria=criteria.split('#');
				    	for(var k=0;k<spCriteria.length;k++)
				    	{
				    		criteria1+=' '+spCriteria[k];
				    	}
				    	criteria=criteria1.trim();
				    	
				    	if(fieldName.substring(0,2).trim()=='dt')
				    	{
				    		fieldName='date('+fieldName+')';
				    	}
				    	cr+=fieldName+" "+searchCode+" "+condition+" '"+criteria+"' ";
						if(condOperator!='none')
						{
							cr+=condOperator+" ";
						}
				   	}
					
					if(cr.trim()!='')
					{						
						sql+=" where "+cr;
						var whClause="where "+cr;
						$("#txtFinalWhere").val(whClause);
					}
					
					var table = document.getElementById("tblGroupBy");
					var rowCount1 = table.rows.length;
				    var tableRows=table.rows;
				    var groupByColumns="",sortByColumns="";
				    for(var cnt=0;cnt<rowCount1;cnt++)
				   	{
				     	var cells1=tableRows[cnt].cells;
				    	var field=cells1[0].innerHTML;
				    	field=field.substring((field.indexOf("value")+7),field.indexOf("type")-2);
				    	groupByColumns+=","+field;
				   	}
					if(groupByColumns!='')
					{
						groupByColumns=groupByColumns.substring(1,groupByColumns.length);
						sql+=" group by "+groupByColumns;
						$("#txtFinalGroup").val(groupByColumns);
					}
					
					var table2 = document.getElementById("tblSortBy");
					var rowCount2 = table2.rows.length;
				    var tableRows2=table2.rows;
				    for(var cnt=0;cnt<rowCount2;cnt++)
				   	{
				     	var cells1=tableRows2[cnt].cells;
				    	var field=cells1[0].innerHTML;
				    	field=field.substring((field.indexOf("value")+7),field.indexOf("type")-2);
				    	sortByColumns+=","+field;
				   	}
					
					if(sortByColumns!='')
					{
						sortByColumns=sortByColumns.substring(1,sortByColumns.length);
						sql+=" order by "+sortByColumns;
						$("#txtFinalSort").val(sortByColumns);
					}
					reportSql=sql;
					
					$("#txtFinalQuery").val(sql);
					$("#txtFinalTable").val(afterwhere[0]);
				}
				alert(sql);
				funShowReport(sql,sqlType);
	        	
				/*else
				{
					query=query.replace('*',seletedFields);
					reportSql=query;
					funShowReport(query,'sql');
				}*/
			}
			
			if(activeTabId=='tab6')
			{
				$.ajax({
	 		        type: "GET",
	 		        url: getContextPath()+"/loadUserCodes.html",
	 		        dataType: "json",
	 		        success: function(response)
	 		        {
	 		        	var spUsers=selectedUsers.split(',');
	 		        	var html = "";
						$.each(response, function(i, column) 
						{
							var flgUsers=false;
							for(var cn=0;cn<spUsers.length;cn++)
							{
								if(spUsers[cn]==column)
								{
									flgUsers=true;
								}
							}
							
							if(flgUsers)
							{
								html +='<li><input id="strUserCodes['+i+']" type="checkbox" checked value="'+column+'" name="strSelectedUsers['+i+']"/>';
							}
							else
							{
								html +='<li><input id="strUserCodes['+i+']" type="checkbox" value="'+column+'" name="strSelectedUsers['+i+']"/>';
							}
							//html +='<li><input id="strUserCodes['+i+']" type="checkbox" value="'+column+'" name="strSelectedUsers['+i+']"/>';
					        html +='<label id="lblColumn1['+i+']">'+column+'</label></li>';
					    });
						
					    $("#chkUserCode").html(html);
	 				},
	 		        error: function(e)
	 		        {
	 		          	alert('Error121212: ' + e);
	 		        }
	 	      	 });
			}
			
		});
		
		
		$("#btnPrev").click(function( event )
		{
			$("#btnNext").val("Next");
			switch(activeTabId)
			{
				case "tab2":
					$("#t1").trigger('click');
					aciveTabId="tab1";
					break;
					
				case "tab3":
					$("#t2").trigger('click');
					aciveTabId="tab2";
					break;
					
				case "tab4":
					$("#t3").trigger('click');
					aciveTabId="tab3";
					break;
					
				case "tab5":
					$("#t4").trigger('click');
					aciveTabId="tab4";
					break;
					
				case "tab6":
					$("#t5").trigger('click');
					aciveTabId="tab5";
					break;
			}			
		});
				
		function funShowReport(sql,qType)
		{
			var table = document.getElementById("tblReport");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
			$("#txtReportQuery").val(sql);
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/getReportData.html?reportQuery="+sql+"&queryType="+qType,
		        dataType: "json",
		        success: function(response)
		        {	
					rowCount = table.rows.length;
					var row = table.insertRow(rowCount);
					var spCol=seletedFields.split(',');
					for(var cnt=0;cnt<spCol.length;cnt++)
					{
						row.insertCell(cnt).innerHTML= "<label>"+spCol[cnt]+"</label>";
					}
					rowCount++;
		        	$.each(response, function(i,item)
					{
		        		var row1 = table.insertRow(rowCount);
		        		var spCol=seletedFields.split(',');
						for(var cnt=0;cnt<spCol.length;cnt++)
						{
							if(spCol[cnt].substring(0,2).trim()=='dt')
							{
								var date = new Date(item[cnt]);
								item[cnt]=date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
							}
							row1.insertCell(cnt).innerHTML= "<label>"+item[cnt]+"</label>";
						}
		        	});
				},
		        error: function(e)
		        {
		          	alert('Error in checking query: ' + e);
		        }
	      	 });
		}
			
		
		function funAddColumns()
		{
			var table = document.getElementById("tblReport");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			var spCol=seletedFields.split(',');
			for(var cnt=0;cnt<spCol.length;cnt++)
			{
				row.insertCell(cnt).innerHTML= "<label>"+spCol[cnt]+"</label>";
			}
		}
		
		$("#btnChkQuery").click(function( event )
		{
			if($("#cmbQueryType").val()=='SQL Based')
			{
				var queryStr=$("#txtAreaQuery").val();
				var queryErrorMsg = "Incorrect Query";
				
				$.ajax({
			        type: "GET",
			        url: getContextPath()+"/checkQuery.html?queryStr="+queryStr,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(arguments[2].responseText == "true")
			        	{
			        		alert('Query is Correct');
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
			});
		});
	
	
	function funChk(obj)
	{
		var idd=document.getElementById(obj.id);
		var arrSplit1=obj.id.split('[');
		var arrSplit=arrSplit1[1].split(']');
		var fieldId="lblColumn["+arrSplit[0]+"]";
		var fieldName=document.getElementById(fieldId).innerHTML;
		
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
	
	
	function funDeleteAllRows()
	{
		var table = document.getElementById("tblCriteria");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	function funAddRowForUpdate(fieldName,searchCode,condition,criteria,condOperator)
	{		
		var table = document.getElementById("tblCriteria");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var name='strArrCriteria['+rowCount+']';
		criteria1=fieldName+" "+searchCode+" "+condition+" '"+criteria+"' ";
		if(condOperator!='none')
		{
			criteria1+=condOperator;
		}
		row.insertCell(0).innerHTML= "<input type=\"text\" id=\"txtFieldName."+(rowCount)+"\" name="+name+" style=\"width: 150px;\" value="+fieldName+">";
		row.insertCell(1).innerHTML= "<input type=\"text\" id=\"txtSearchCode."+(rowCount)+"\" name="+name+" style=\"width: 150px;\" value="+searchCode+">";
		row.insertCell(2).innerHTML= "<input type=\"text\" id=\"txtCondition."+(rowCount)+"\" name="+name+" style=\"width: 20px;\" value="+condition+">";
		row.insertCell(3).innerHTML= "<input type=\"text\" id=\"txtCriteria."+(rowCount)+"\" name="+name+"  value="+criteria+">";
		row.insertCell(4).innerHTML= "<input type=\"text\" id=\"txtCondOperator."+(rowCount)+"\" name="+name+" value="+condOperator+">";
		row.insertCell(5).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
					    
		funResetCriteriaFields();
		return false;
	}
	
	
	
	function funAddRow()
	{
		var fieldName = $("#cmbCriteriaCol").val();
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
		
		var criteria1='';
		var spCriteria=criteria.split(' ');
		for(var k=0;k<spCriteria.length;k++)
		{
			criteria1+="#"+spCriteria[k];
		}
		criteria=criteria1.substring(1,criteria1.length).trim();
		
		//criteria=criteria.replace(' ','#');
		var table = document.getElementById("tblCriteria");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var name='strArrCriteria['+rowCount+']';
		criteria1=fieldName+" "+searchCode+" "+condition+" '"+criteria+"' ";
		if(condOperator!='none')
		{
			criteria1+=condOperator;
		}
		row.insertCell(0).innerHTML= "<input type=\"text\" id=\"txtFieldName."+(rowCount)+"\" name="+name+" style=\"width: 150px;\" value="+fieldName+">";
		row.insertCell(1).innerHTML= "<input type=\"text\" id=\"txtSearchCode."+(rowCount)+"\" name="+name+" style=\"width: 150px;\" value="+searchCode+">";
		row.insertCell(2).innerHTML= "<input type=\"text\" id=\"txtCondition."+(rowCount)+"\" name="+name+" style=\"width: 20px;\" value="+condition+">";
		row.insertCell(3).innerHTML= "<input type=\"text\" id=\"txtCriteria."+(rowCount)+"\" name="+name+"  value="+criteria+">";
		row.insertCell(4).innerHTML= "<input type=\"text\" id=\"txtCondOperator."+(rowCount)+"\" name="+name+" value="+condOperator+">";
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
			row.insertCell(0).innerHTML= "<input type=\"text\" id="+name+" name="+name+" style=\"width: 100px;\" value='"+arrDocCodes[i]+"'/>";
			row.insertCell(1).innerHTML= "<input type=\"text\" name="+name1+" style=\"width: 150px;\" value='' />";	
		}
		return false;
	}
	
	function funAddGroupByRow()
	{
		var table = document.getElementById("tblGroupBy");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var groupByField=$("#cmbGroupBy").val();
		row.insertCell(0).innerHTML= "<input type=\"text\" name=\"groupByColumns\" style=\"width: 150px;\" value="+groupByField+" >";		
		row.insertCell(1).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRowForGroup(this)">';
		
		return false;
	}
	
	function funAddGroupByRow1(groupByField)
	{
		var table = document.getElementById("tblGroupBy");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);		
		row.insertCell(0).innerHTML= "<input type=\"text\" name=\"groupByColumns\" style=\"width: 150px;\" value="+groupByField+" >";		
		row.insertCell(1).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRowForGroup(this)">';
		
		return false;
	}
	
	function funDeleteRowForGroup(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblGroupBy");
	    table.deleteRow(index);
	}
	
	
	function funAddGroupByOnRow()
	{
		var table = document.getElementById("tblGroupByOn");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var groupByField=$("#cmbGroupByOn").val();
		row.insertCell(0).innerHTML= "<input type=\"text\" name=\"groupByColumns1\" style=\"width: 150px;\" value="+groupByField+" >";		
		row.insertCell(1).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRowForGroup(this)">';
		
		return false;
	}
	
	function funAddGroupByOnRow1(groupByField)
	{
		var table = document.getElementById("tblGroupByOn");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);		
		row.insertCell(0).innerHTML= "<input type=\"text\" name=\"groupByColumns1\" style=\"width: 150px;\" value="+groupByField+" >";		
		row.insertCell(1).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRowForGroup(this)">';
		
		return false;
	}
	
	function funDeleteRowForGroupByOn(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblGroupByOn");
	    table.deleteRow(index);
	}
	
	
	
	
	function funAddSortByRow()
	{
		var table = document.getElementById("tblSortBy");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var sortByField=$("#cmbSortBy").val();
		row.insertCell(0).innerHTML= "<input type=\"text\" name=\"sortByColumns\" style=\"width: 150px;\" value="+sortByField+" >";		
		row.insertCell(1).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRowForSort(this)">';
		
		return false;
	}
	
	function funAddSortByRow1(sortByField)
	{
		var table = document.getElementById("tblSortBy");
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML= "<input type=\"text\" name=\"sortByColumns\" style=\"width: 150px;\" value="+sortByField+" >";		
		row.insertCell(1).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRowForSort(this)">';
		
		return false;
	}
	
	function funDeleteRowForSort(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblSortBy");
	    table.deleteRow(index);
	}
		
	</script>
</head>
<body>

	<div id="formHeading">
		<label>User Defined Reports</label>
	</div>
		<s:form action="saveUserDefinedReport.html" method="POST" name="userDefinedReport">
		<br>
	
		<div id="tab_container" style="height: 550px">
			<ul class="tabs">
				<li class="active" id="t1" data-state="tab1" style="width: 100px;padding-left: 55px">Report Details</li>
				<li data-state="tab2" id="t2" style="width: 100px;padding-left: 55px">Query</li>
				<li data-state="tab3" id="t3" style="width: 100px;padding-left: 55px">Field Selection</li>
				<li data-state="tab4" id="t4" style="width: 100px;padding-left: 55px">Criteria</li>
				<li data-state="tab5" id="t5" style="width: 100px;padding-left: 55px">Preview</li>
				<li data-state="tab6" id="t6" style="width: 100px;padding-left: 55px">Save Report</li>
			</ul>
	
		<!-- Report Details Tab -->
			<div id="tab1" class="tab_content" style="height: 450px" >
				<table class="transTable" style="width: 150px">
					<th colspan="2" align="center" >Report Details</th>
						<tr>
							<td align="left">Report Code</td>
							<td><s:input id="txtReportCode" path="strReportCode" type="text" ondblclick="funHelp('udreportname');"/></td>
						</tr>
						<tr>
							<td>Report Description</td>
							<td><s:input id="txtReportDesc" path="strReportDesc" type="text"/></td>
						</tr>
						<tr>
							<td>Category</td>
							<td>
								<s:select path="strCategory" id="cmbCategory">
									<s:options items="${category}"/>
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
							<td><s:select path="strType" id="cmbQueryType">
									<option value="SQL Based">SQL Statement</option>
									<option value="TABLE">Table</option>
							</s:select></td>
						</tr>
						
						<tr id="tableName" style="display:none">
							<td>Table</td>
							<td><s:select path="strTable" id="cmbTableName">
							   		<s:option value="-1" label="Select Table"/>
               					 	<s:options items="${tableNames}"/>
								</s:select>
							</td>
						</tr>
				</table>
			</div>
		
		<!-- Query Tab -->
			<div id="tab2" class="tab_content" style="height: 450px" >
				<table class="transTable" style="width:100px">
					<th colspan="2" align="center">Query</th>
					<tr>
						<td align="center">
							<s:textarea rows="10" cols="100" id ="txtAreaQuery" path="strQuery"></s:textarea>
						</td>						
					</tr>
				</table>
				<table class="transTable" style="width:100px">
					<tr><td align="center"><input type="button" value="Check Query" id="btnChkQuery" class="form_button"></td></tr>
				</table>				
			</div>
		
		<!-- Field Selection Tab -->
			<div id="tab3" class="tab_content" style="height: 450px" >
				<table class="masterTable">
					<tr>
						<td>Field Selection</td>
					</tr>
				</table>
			
				<table class="masterTable" id="tblColumns">
					<tr>
						<td>
							<div>
								<ul id="chkColumns"></ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
		
		<!-- Criteria Tab -->
			<div id="tab4" class="tab_content" style="height: 450px" >
				<table class="masterTable">
					<tr>
						<td>Criteria</td>
					</tr>
				</table>
					
				<table class="masterTable" id="criteriaTable">
					<tr>						
						<th>Field</th>
						<th>Search Code</th>
						<th>Condition</th>
						<th>Criteria</th>
						<th>AndOr</th>
						<th>Del</th>
					</tr>
						
					<tr>
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
							
						<td>
							<select id="cmbCondOperator" style="width: 75px;">
								<option value="none">None</option>
								<option value="and">AND</option>
								<option value="or">OR</option>
							</select>
						</td>
						
						<td width="100%"></td>
							
						<td><input type="button" value="ADD" onclick="funAddRow();" class="form_button"/></td>
					</tr>
				</table>
					
				<table id="tblCriteria" class="masterTable">						
				</table>
					
				<br><br>
				<table class="masterTable" id="tblOrder">
					<tr>
						<td><label>Grouping</label></td>
						<td>
							<s:select id="cmbGroupBy" path="strGroupBy" onchange="funChange(this);">
							</s:select>
						</td>
						<td><input id="btnAddGroupBy" type="button" value="ADD" class="form_button" onclick="funAddGroupByRow();"/></td>						
					</tr>
				</table>				
				<table id="tblGroupBy" class="masterTable">
				</table>
				
				<br><br>
				
				<table class="masterTable" id="tblGroupByOn1">
					<tr>
						<td><label>Group On</label></td>
						<td>
							<s:select id="cmbGroupByOn" path="strGroupByOn" >
							</s:select>
						</td>
						<td><input id="btnAddGroupByOn" type="button" value="ADD" class="form_button" onclick="funAddGroupByOnRow();"/></td>						
					</tr>
				</table>				
				<table id="tblGroupByOn" class="masterTable">
				</table>
				
				<br><br>
				
				<table class="masterTable" id="tblOrder1">
					<tr>
						<td><label>Sorting</label></td>
						<td>
							<s:select id="cmbSortBy" path="strSortBy">
							</s:select>
						</td>
						<td><input id="btnAddSortBy" type="button" value="ADD" class="form_button" onclick="funAddSortByRow();"/></td>
					</tr>
				</table>
				
				<table id="tblSortBy" class="transTable">
				</table>
					
			</div>
					
		<!-- Preview Tab -->
			<div id="tab5" class="tab_content" style="height: 450px" >
					
					<table class="masterTable" >
					<tr>
						<td><label>Export</label></td>						
						<td><input id="btnExport" type="button" value="EXPORT" onclick="funExportData();" class="form_button"/></td>
					</tr>
				</table>
					
					<br>
					<table class="transTable" id="tblReport">
						
					</table>
					
					<table>
						<tr>
							<td> <s:input type="hidden" id="txtFinalQuery" path="strFinalQuery"/> </td>
							<td> <s:input type="hidden" id="txtFinalTable" path="strFinalTable"/> </td>
							<td> <s:input type="hidden" id="txtFinalWhere" path="strFinalWhere"/> </td>
							<td> <s:input type="hidden" id="txtFinalGroup" path="strFinalGroup"/> </td>
							<td> <s:input type="hidden" id="txtFinalSort" path="strFinalSort"/> </td>
							<td> <s:input type="hidden" id="txtFinalColumns" path="strFinalColumns"/> </td>							
						</tr>
					</table>
			</div>
			
		<!-- Save Report -->
			<div id="tab6" class="tab_content" style="height: 450px" >
					<table class="masterTable">
						<tr>
							<td>Save Report</td>
						</tr>
					</table>
					
					<table class="masterTable">
					<tr>
						<td>
							<div>
								<ul id="chkUserCode"></ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
		
	</div>
				
		<p align="center">
			<input id="btnPrev" type="button" value="Previous" id="formsubmit" class="form_button" />
			<input id="btnNext" type="button" value="Next" id="formsubmit" class="form_button" />
			<s:input id="txtReportQuery" type="hidden" path="strReportQuery"/>
		</p>
		
		
	</s:form>
	
</body>
</html>