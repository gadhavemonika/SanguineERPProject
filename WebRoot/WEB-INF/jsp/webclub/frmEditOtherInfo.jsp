<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName;

	$(function() 
	{
	});

	function funSetData(code){

		switch(fieldName){

		}
	}





	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Edit Other Info</label>
	</div>

<br/>
<br/>

	<s:form name="WebClubEditOtherInfo" method="POST" action="saveWebClubEditOtherInfo.html">

<div >
	<div class="divBorder" style="float:left; width: 200px; height: 203px; background-color: #C0E4FF;border: 1px solid black; overflow: scroll;"  >
	
	
	<c:forEach var="fieldCriteria" items="${listEditOtherInfo}">
	<a href="#" class="fieldCriteriaLink" >${fieldCriteria}</a><br>
	</c:forEach>
	
	
	
	
	</div>
<div class="divBorder" style="float:right; width: 200px; height: 203px; background-color: #C0E4FF;border: 1px solid black; overflow: scroll;">
<!-- 	&nbsp;&nbsp;<input type="button" value="Add Field" class="form_button" onclick="return btnAdd_onclick()" /> -->
<!-- 	<br> -->
<!-- 	<br>&nbsp; -->
<!-- 	<input type="button" value="Add Value" class="form_button" onclick="return btnAdd_onclick()" /> -->
<!-- 	<br> -->
<!-- 	<br>&nbsp; -->
<!-- 	<input type="button" value="Update" class="form_button" onclick="return btnAdd_onclick()" /> -->
<!-- 	<br> -->
<!-- 	<br>&nbsp; -->
	<input type="button" value="Reset" class="form_button" onclick="return btnAdd_onclick()" />
	</div>
	</div>	
		<div  >
			<table class="divBorder" style="width: 613px; height: 205px; background-color: #C0E4FF;border: 1px solid black; overflow: scroll;">
			
				<tr>
					<td ><label>Member Code</label></td>
							<td width="150px"><s:input id="txtMemberCode"
									ondblclick="funHelp('WCmemProfileCustomer')" cssClass="searchTextBox"
									type="text" path="strMemberCode" ></s:input></td>
					<td colspan="4"><s:input id="txtMemberName" path=""
									cssClass="longTextBox" type="text"  style="width: 34%" ></s:input></td>
		
				</tr>
			
			<tr>
			<td></td>
			
			</tr>
			
			<tr>
			<td></td>
			
			</tr>
			
			<tr>
			<td></td>
			
			</tr>
			
			</table>
		</div>
	



	
	
	
	
		
		
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
