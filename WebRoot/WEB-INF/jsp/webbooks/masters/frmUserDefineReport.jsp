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
	
		var fieldName,gridHelpRow,searchData;

		$(function ()
		{
			$("#txtReportDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtReportDate").datepicker('setDate','today');
			$("#txtReportDate").datepicker();
		});
		
		/**
		* Success Message After Saving Record
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
				alert("Data Save successfully\n\n"+message);
			<%
			}}%>

		});
		
		function funHelp(transactionName)
		{
			fieldName=transactionName;
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		function funHelp1(transactionName,row,searchType)
		{
			gridHelpRow=row;
			fieldName=transactionName;
			searchData=searchType;
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		}
		
		function funSetData(code)
		{
			switch (fieldName) 
			{
			   case 'userDefinedReportCode':
				   funSetReportName(code);
			        break;
			        
			   case "acGroupCode":
				   funSetGroupCode(code,gridHelpRow,searchData);
			   break;  
				 
			   case "accountCode": 
				   funSetAccountCode(code,gridHelpRow,searchData);
				break;	 
			        
			}
		}
		
		function funSetReportName(code)
		{
			$.ajax({
				type : "GET",
				url : getContextPath()+ "/loadUserDefinedReportMasterData.html?userDefinedCode=" + code,
				dataType : "json",
				success : function(response)
				{
					funRemoveAllRows();
					$("#txtReportCode").val(response.strReportId);
					$("#txtReportName").val(response.strReportName);
					var userDefDate=response.dteUserDefDate.split(" ")[0].split("-");
					$("#txtReportDate").val(userDefDate[2]+"-"+userDefDate[1]+"-"+userDefDate[0]);
					$.each(response.listUserDefRptDtlModel, function(i, data) 
					{
						funAddProductRow(data.strType,data.strColumn,data.strOperator,data.strFGroup,data.strTGroup,data.strFAccount,data.strTAccount,data.strDescription,data.strConstant,data.strFormula,data.strPrint);
					});
					
				},
				error : function(e){
				}
			});
		}
		
		
		function funRemoveAllRows() 
	    {
			var table = document.getElementById("tblUserDefindDtl");
			var rowCount = table.rows.length;			   
			for(var i=rowCount-1;i>=0;i--)
			{
				table.deleteRow(i);						
			} 
	    }
		
		function funDeleteRow(obj) 
		{
		    var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("tblUserDefindDtl");
		    table.deleteRow(index);
		}
		
		
		
		function funAddProductRow(selectedType,strColumn,selectedOperator,FGroup,TGroup,FAccount,TAccount,description,constant,formula,selectedPrintYN)
		{
			var table = document.getElementById("tblUserDefindDtl");
		    var rowCount = table.rows.length;
		    var row = table.insertRow(rowCount);
 		    row.insertCell(0).innerHTML= "<input name=\"listUserDefRptDtlBean["+(rowCount)+"].intSrNo\" readonly=\"readonly\"  class=\"Box\" style=\"text-align: right;\" size=\"3%\" id=\"intSrNo."+(rowCount)+"\" value='"+(rowCount+1)+"' />";		  		   	  
		    row.insertCell(1).innerHTML= "<select name=\"listUserDefRptDtlBean["+(rowCount)+"].strType\" class=\"Box\" style=\"width: 100%;\"  id=\"strType."+(rowCount)+"\"  onchange=\"funClickOnType(this.value,"+(rowCount)+")\">   '"+funGetTypeList(selectedType)+"'";
		    row.insertCell(2).innerHTML= "<input type=\"text\"  size=\"2%\" name=\"listUserDefRptDtlBean["+(rowCount)+"].strColumn\" style=\"text-align: right;\"  id=\"strColumn."+(rowCount)+"\" value='"+strColumn+"'  />";
		    row.insertCell(3).innerHTML= "<select name=\"listUserDefRptDtlBean["+(rowCount)+"].strOperator\" class=\"Box\" style=\"width: 100%;\"  id=\"strOperator."+(rowCount)+"\"  >   '"+funGetOperatorList(selectedOperator)+"'";
		    row.insertCell(4).innerHTML= "<input name=\"listUserDefRptDtlBean["+(rowCount)+"].strFGroup\"  class=\"searchTextBox1\" size=\"7%\" id=\"strFGroup."+(rowCount)+"\" value='"+FGroup+"'  ondblclick=\"Javacsript:funHelp1('acGroupCode',"+(rowCount)+",'FGroup' )\"/>";		  		   	  
		    row.insertCell(5).innerHTML= "<input name=\"listUserDefRptDtlBean["+(rowCount)+"].strTGroup\"  class=\"searchTextBox1\" size=\"7%\" id=\"strTGroup."+(rowCount)+"\" value='"+TGroup+"'  ondblclick=\"Javacsript:funHelp1('acGroupCode',"+(rowCount)+",'TGroup')\"/>";
		    row.insertCell(6).innerHTML= "<input name=\"listUserDefRptDtlBean["+(rowCount)+"].strFAccount\"  class=\"searchTextBox\" size=\"6%\" id=\"strFAccount."+(rowCount)+"\" value='"+FAccount+"' ondblclick=\"Javacsript:funHelp1('accountCode',"+(rowCount)+",'FAccount' )\"/>";	  		   	  
		    row.insertCell(7).innerHTML= "<input name=\"listUserDefRptDtlBean["+(rowCount)+"].strTAccount\"  class=\"searchTextBox\" size=\"6%\" id=\"strTAccount."+(rowCount)+"\" value='"+TAccount+"' ondblclick=\"Javacsript:funHelp1('accountCode',"+(rowCount)+",'TAccount')\"/>";	  		   	  
		    row.insertCell(8).innerHTML= "<input type=\"text\"  size=\"18%\" name=\"listUserDefRptDtlBean["+(rowCount)+"].strDescription\" style=\"text-align: left; width:170px;\"  id=\"strDescription."+(rowCount)+"\" value='"+description+"'  />";
		    row.insertCell(9).innerHTML= "<input type=\"text\"  size=\"4.4%\" name=\"listUserDefRptDtlBean["+(rowCount)+"].strConstant\" style=\"text-align: left; width:45px;\"  id=\"strConstant."+(rowCount)+"\" value='"+constant+"'  />";
		    row.insertCell(10).innerHTML= "<input type=\"text\"  size=\"4.4%\" name=\"listUserDefRptDtlBean["+(rowCount)+"].strFormula\" style=\"text-align: left; width:48px;\"  id=\"strFormula."+(rowCount)+"\" value='"+formula+"'  />";
		    row.insertCell(11).innerHTML= "<select name=\"listUserDefRptDtlBean["+(rowCount)+"].strPrint\" class=\"Box\" style=\"width: 100%;\"  id=\"strPrint."+(rowCount)+"\"  >   '"+funGetPrintYNList(selectedPrintYN)+"'";
		    row.insertCell(12).innerHTML= '<input  class="deletebutton" value = "Delete" onClick="Javacsript:funDeleteRow(this)">';
		    funClickOnType(selectedType,rowCount);   
		    
		}
		
		function funGetTypeList(selectedType)
		{
			var type="";
			var strType=["Text","Group","GL_Code",]
			type += "<option value="+selectedType+" selected>"+selectedType+"</option>";
			for(var i=0;i<strType.length;i++)
			{
				if(strType[i]!=selectedType)
				 type += "<option value="+strType[i]+">"+strType[i]+"</option>";
			}
			return type;
		}
		
		function funGetOperatorList(selectedOperator)
		{
			var type="";
			var strType=["Between","Equal"]
			type += "<option value="+selectedOperator+" selected>"+selectedOperator+"</option>";
			for(var i=0;i<strType.length;i++)
			{
				if(strType[i]!=selectedOperator)
				 type += "<option value="+strType[i]+">"+strType[i]+"</option>";
			}
			return type;
		}
		
		function funGetPrintYNList(selectedPrintYN)
		{
			var type="";
			var strType=["N","Y"]
			type += "<option value="+selectedPrintYN+" selected>"+selectedPrintYN+"</option>";
			for(var i=0;i<strType.length;i++)
			{
				if(strType[i]!=selectedPrintYN)
				 type += "<option value="+strType[i]+">"+strType[i]+"</option>";
			}
			return type;
		}
		
		
		 function funClickOnType(item,rowCount)
		 {
		   if(item=="Text")
		 	{
			    document.getElementById("strFGroup."+rowCount).disabled = true;  
		    	document.getElementById("strTGroup."+rowCount).disabled = true;   
		    	document.getElementById("strFAccount."+rowCount).disabled = true;   
		    	document.getElementById("strTAccount."+rowCount).disabled = true;  
			}
		    else if(item=="Group")
			{
		    	document.getElementById("strFGroup."+rowCount).disabled = false;  
		    	document.getElementById("strTGroup."+rowCount).disabled = false;  
		    	document.getElementById("strFAccount."+rowCount).disabled = true;  
		    	document.getElementById("strTAccount."+rowCount).disabled = true; 
		    	
			} 
		    else if(item=="GL_Code")
			{
		    	document.getElementById("strFGroup."+rowCount).disabled = true;  
		    	document.getElementById("strTGroup."+rowCount).disabled = true;  
		    	document.getElementById("strFAccount."+rowCount).disabled = false;  
		    	document.getElementById("strTAccount."+rowCount).disabled = false; 
			}
 	     }
		 

		
		function funSetGroupCode(code,gridHelpRow,searchType)
		{
			if(searchData=="FGroup")
			{
				 document.getElementById("strFGroup."+gridHelpRow).value=code;
			}	
			else
			{
			    document.getElementById("strTGroup."+gridHelpRow).value=code;
			}	
			
		}
		function funSetAccountCode(code,gridHelpRow,searchType)
		{
			if(searchData=="FAccount")
			{
				document.getElementById("strFAccount."+gridHelpRow).value=code;
			}
			else
			{
				document.getElementById("strTAccount."+gridHelpRow).value=code;
			}	 
		}
		
		
		function funGetVaildation() 
		 {
				var flg=true;
				var table = document.getElementById("tblUserDefindDtl");
				var rowCount = table.rows.length;	

				if($("#txtReportName").val()=="")
		 		{
		 			alert('Enter User Defined Report Name!! ');
		 			return false;
		 		}
				
			  	if(rowCount<1)
				{
					alert("Please insert data in Grid");
					return false;
				}
				else
				{
					for(var i=0;i<rowCount;i++)
					{
						if(document.getElementById("strColumn."+i).value=='')
						{
							alert('Enter ColumnNo!! ');
				 			return false;
						}
						else
						{
							var regex=/^[0-9]+$/;
						    if (document.getElementById("strColumn."+i).value.match(regex))
						    {
						    	return true;
						    }
						    else
						    {
						    	alert('Enter Valid ColumnNo!! ');
					 			return false;
						    }	
						}	
		    			if(document.getElementById("strDescription."+i).value=='')
						{
		    				alert('Enter Description!! ');
				 			return false;
						}	
					}	
					return true;
				}
		  }
		
		
		/**
		* Reset The Group Name TextField
		**/
		function funResetFields()
		{
			location.reload(true); 
	    }

	</script>

</head>
	<body>
	<div id="formHeading">
		<label>User Defined Report Master</label>
	</div>
	<br/>
    <br/>
		<s:form id="frmUserDefinedReportMaster" method="POST" action="saveUserDefinedReportMaster.html?saddr=${urlHits}">
			
		    <table class="masterTable" style="width: 95%;">
			    <tr>
			        <td ><label>Report ID</label></td>
			        <td><s:input id="txtReportCode" path="strReportId" ondblclick="funHelp('userDefinedReportCode');" class="searchTextBox"/></td>
			        <td><s:input id="txtReportName"  required="true" path="strReportName" cssClass="longTextBox"/></td>			    			    			    		    
			        <td><label>Date</label></td>
				    <td><s:input type="text" id="txtReportDate" path="dteUserDefDate" cssClass="calenderTextBox"  /></td>   
				    <td><input type="button" value="Add" class="smallButton" onclick="return funAddProductRow('Text','1','Equal','','','','','Desc','','','N')" /></td>
			    </tr>
			</table>
			<br>
			<div class="dynamicTableContainer" style="height: 300px; ">
			
			 <table class="transTablex col15-center" id="criteriaTable" style="width: 100%; border: #0F0; table-layout: fixed;">
				<tr bgcolor="#72BEFC"  style="font-family: sans-serif">
				    
				    <td width="2.5%">Sr.No</td>
					<!--  COl1   -->
					<td width="5.5%">Type</td>
					<!--  COl2   -->
					<td width="4%">Print In Col</td>
					<!--  COl3   -->
					<td width="6%">Operator</td>
					<!--  COl4   -->
					<td width="6%">Group</td>
					<!--  COl5   -->
					<td width="6%">Group</td>
					<!-- COl6   -->
					<td width="10%">Account</td>
					<!--  COl7   -->
					<td width="10.3%">Account</td>
					<!-- COl8   -->
					<td width="14.7%">Description</td>
					<!-- COl9   -->
					<td width="4.4%">Constant</td>
					<!-- COl10   -->
					<td width="4.4%">Formula</td>
					<!-- COl11   -->	
					<td width="3%">Print</td>
					<!-- COl12   -->
					<td style="width:2%;">Del</td> 
						
				</tr>					
			</table>
					
			<table id="tblUserDefindDtl"
				style="width: 100%; border: #0F0; table-layout: fixed;"
				class="transTablex col15-center">
				<tbody>
				<col style="width: 2.5%">
				<!--  COl1   -->
				<col style="width: 5.5%">
				<!--  COl2   -->
				<col style="width: 4%">
				<!--  COl3   -->
				<col style="width: 6%"> 
				<!--COl4   -->
				<col style="width: 6%"> 
				<!--COl5   -->
				<col style="width: 6%"> 
				<!-- COl6   -->
				<col style="width: 10%"> 
				<!-- COl7   -->
				<col style="width: 10.2%"> 
				<!-- COl8   -->
				<col style="width: 14.8%"> 
				<!--  COl9   -->
				<col style="width: 4.4%"> 
				<!--  COl10   -->
				<col style="width: 4.4%"> 
				<!--  COl11  -->
				<col style="width:3%">
		    	<!--COl12  -->
				<col style="width:2%">

				</tbody>

			</table>
			   
			</div>
			
			<br><br>
			<p align="center">
			<input id="btnSubmit" type="submit" value="SUBMIT" class="form_button" onclick="return funGetVaildation()" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
			</p>
			
			<table class="transTable" id="tblReport">					
			</table>
		    
		</s:form>
	</body>
</html>