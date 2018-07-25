<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<script>
	
		var fieldName,reportType,reportSql,columns,groupBy,orderBy,tableName;
		var no=0;
		var sql='';
	
		$(function ()
		{
			$("#btnExport").click(function (e)
			{
				if(reportType=='TABLE')
				{
					window.location.href=getContextPath()+"/exportReportData.html?reportQuery="+reportSql+"&columns="+columns+"&queryType=hql";
				}
				else
				{
					window.location.href=getContextPath()+"/exportReportData.html?reportQuery="+reportSql+"&columns="+columns+"&queryType=sql";				
				}
			});
			$("#btnSubmit").click(function (e)
			{
				funProcessReport();
			});			
		});
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			   case 'udreportname':
				   funSetReportName(code);
			        break;
			}
		}
		
		function funSetReportName(code)
		{
			$.ajax({
				type : "GET",
				url : getContextPath()+ "/getReportName.html?reportCode=" + code,
				dataType : "json",
				success : function(response)
				{
					$.each(response, function(i, data) 
					{
						no=no+1;
						$("#txtReportCode").val(code);
						$("#lblReportName").text(data[0]);
						var type="sql";
						reportType=data[3];
						reportSql=data[2];
						columns=data[1];
						groupBy=data[5];
						orderBy=data[6];
						tableName=data[7];
						if(data[3]=='TABLE')
						{
							type="hql";
						}
						funSetReportColumns(data[1],data[2],type,data[4]);
					});
				},
				error : function(e){
				}
			});
		}
		
		
		function funAddRow1(fieldName,searchCode,condition,criteria,condOperator)
		{
			if(condition=='>')
			{
				condition='&gt;';
			}
			else if(condition=='<')
			{
				condition='&lt;';
			}
			else if(condition=='>=')
			{
				condition='&gt;=';
			}
			else if(condition=='<=')
			{
				condition='&lt;=';
			}
			else
			{
				condition='=';
			}
			
			var table = document.getElementById("tblCriteria");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			var name='strArrCriteria['+rowCount+']';
			row.insertCell(0).innerHTML= "<input type=\"text\" id=\"txtFieldName."+(rowCount)+"\" name="+name+" style=\"width: 120px;\" value="+fieldName+">";
			row.insertCell(1).innerHTML= "<input type=\"text\" id=\"txtSearchCode."+(rowCount)+"\" name="+name+" style=\"width: 100px;\" value="+searchCode+">";
			row.insertCell(2).innerHTML= "<input type=\"text\" id=\"txtCondition."+(rowCount)+"\" name="+name+" style=\"width: 100px;\" value="+condition+">";
			row.insertCell(3).innerHTML= "<input type=\"text\" id=\"txtCriteria."+(rowCount)+"\" name="+name+" style=\"width: 120px;\" value="+criteria+">";
			row.insertCell(4).innerHTML= "<input type=\"text\" id=\"txtCondOperator."+(rowCount)+"\" name="+name+" style=\"width: 120px;\" value="+condOperator+">";
			//row.insertCell(5).innerHTML= '<input type="button" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
			
			return false;
		}
		
		
		function funSetReportColumns(columnNames,query,qType,criteria)
		{
			var table2 = document.getElementById("tblCriteria");
			var rowCount2 = table2.rows.length;
			while(rowCount2>0)
			{
				table2.deleteRow(0);
				rowCount2--;
			}
			criteria=criteria.substring(5,criteria.length).trim();
			var spCr=criteria.split('and');
			
			for(var cnt1=0;cnt1<spCr.length;cnt1++)
			{
				var fieldName="",searchCode="",condition="",cr="",condOperator="and";
				
				if(spCr[cnt1].indexOf("=")>0)
				{
					if(spCr[cnt1].indexOf(">=")>0)
					{
						var arrCr=spCr[cnt1].split('>=');
						var op="none";
						if((cnt1+1)<spCr.length)
						{
							op="and";
						}
						funAddRow1(arrCr[0],"",">=",arrCr[1],op);
					}
					else if(spCr[cnt1].indexOf("<=")>0)
					{					
						var arrCr=spCr[cnt1].split('<=');
						var op="none";
						if((cnt1+1)<spCr.length)
						{
							op="and";
						}
						funAddRow1(arrCr[0],"","<=",arrCr[1],op);
					}
					else
					{
						var arrCr=spCr[cnt1].split('=');
						var op="none";
						if((cnt1+1)<spCr.length)
						{
							op="and";
						}
						funAddRow1(arrCr[0],"","=",arrCr[1],op);
					}
				}
				else if(spCr[cnt1].indexOf(">")>0)
				{
					var arrCr=spCr[cnt1].split('>');
					var op="none";
					if((cnt1+1)<spCr.length)
					{
						op="and";
					}
					funAddRow1(arrCr[0],"",">",arrCr[1],op);
				}
				else if(spCr[cnt1].indexOf("<")>0)
				{
					var arrCr=spCr[cnt1].split('<');
					var op="none";
					if((cnt1+1)<spCr.length)
					{
						op="and";
					}
					funAddRow1(arrCr[0],"","<",arrCr[1],op);
				}
				else if(spCr[cnt1].indexOf(">=")>0)
				{
					var arrCr=spCr[cnt1].split('>=');
					var op="none";
					if((cnt1+1)<spCr.length)
					{
						op="and";
					}
					funAddRow1(arrCr[0],"",">=",arrCr[1],op);
				}
				else if(spCr[cnt1].indexOf("<=")>0)
				{					
					var arrCr=spCr[cnt1].split('<=');
					var op="none";
					if((cnt1+1)<spCr.length)
					{
						op="and";
					}
					funAddRow1(arrCr[0],"","<=",arrCr[1],op);
				}
			}
		}
		
		
		function funProcessReport()
		{
			//reportSql=reportSql.substring(0,reportSql.lastIndexOf('where')).trim();
			if(reportType=='TABLE')
				{
					reportSql="select "+columns+" from "+tableName;
				}
				else
				{
					//reportSql="select "+columns+" from ("+tableName+") a ";
					reportSql=reportSql;
				}
			
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
				reportSql+=" where "+cr;
			}
			if(groupBy.trim()!='')
			{
				reportSql+=" group by "+groupBy;
			}
			if(orderBy.trim()!='')
			{
				reportSql+=" order by "+orderBy;
			}
			
			
			var table = document.getElementById("tblReport");
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
        	
			rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			var spCol=columns.split(',');
			for(var cnt=0;cnt<spCol.length;cnt++)
			{
				row.insertCell(cnt).innerHTML= "<label>"+spCol[cnt]+"</label>";
			}
			rowCount++;
			if(reportType=='TABLE')
			{				
				funSetReportData(reportSql,'hql',spCol.length);
			}
			else
			{
				funSetReportData(reportSql,'sql',spCol.length);
			}			
		}
		
		
		function funSetReportData(query,qType,colCount)
		{
			$.ajax({
		        type: "GET",
		        url: getContextPath()+"/getReportData.html?reportQuery="+query+"&queryType="+qType,
		        dataType: "json",
		        success: function(response)
		        {
		        	var table = document.getElementById("tblReport");
					var rowCount = table.rows.length;
					var spCol=columns.split(',');
		        	$.each(response, function(i,item)
					{
		        		var row1 = table.insertRow(rowCount);
						for(var cnt=0;cnt<colCount;cnt++)
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
		
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
	    //    window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	        window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
	</script>

</head>
	<body>
	<div id="formHeading">
		<label>User Defined Report View</label>
	</div>
		<s:form id="frmUserDefinedReportView" method="POST">
			
		    <table class="masterTable" >
			    <tr>
			        <td ><label>Report Name</label></td>
			        <td><input id="txtReportCode" ondblclick="funHelp('udreportname');" class="searchTextBox"/></td>
			        <td ><label id="lblReportName"></label></td>
			        
			        <td><label>Export</label></td>						
					<td><input id="btnExport" type="button" value="EXPORT" class="form_button" onclick="funExportData();"/></td>
			    </tr>
			</table>
			
			<br>
			
			<table class="masterTable" id="criteriaTable" border="0">
				<tr>					
					<td style="width: 165px;">Field</td>
					<td style="width: 140px;">Search Code</td>
					<td style="width: 135px;">Condition</td>
					<td style="width: 166px;">Criteria</td>
					<td>AndOr</td>					
				</tr>				
			</table>
					
			<table class="masterTable" id="tblCriteria">						
			</table>
			<br><br>
			<table class="masterTable">
				<tr>
					<td><input id="btnSubmit" type="button" value="SUBMIT" class="form_button" /></td>
				</tr>
			</table>
			
			<br>
			
			<table class="transTable" id="tblReport">					
			</table>
		    
		</s:form>
	</body>
</html>