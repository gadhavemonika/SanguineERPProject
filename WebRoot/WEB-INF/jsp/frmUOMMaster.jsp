<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	
		/**
		 * Open Help windows 
		 */
		function funHelp(transactionName)
		{	     
	      //  window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
			  window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	    }
		/**
		 * Set Data after selecting form Help windows
		 */
		function funSetData(code)
		{
			$("#txtUOM").val(code);
			$("#txtHidUom").val(code);
		}
</script>
</head>
<body>
<div id="somediv"></div>
	<div id="formHeading">
		<label>UOM Master</label>
	</div>
<s:form id="UOMMaster" name="UOMMaster" action="saveUOMMaster.html" method="POST">
<br>
<table class="masterTable">
<tr>
		    <th  align="right" colspan="2">  &nbsp; &nbsp; &nbsp;
						&nbsp;</th>
		    </tr>
<tr>
<td width="10%">UOM Name</td>
<td><s:input id="txtUOM" name="txtUOM" cssStyle="text-transform: uppercase;"  cssClass="BoxW116px" path="strUOMName" ondblclick="funHelp('UOMmaster')" required = "true"	value="" />
</td>
</tr>
</table>
<br>
		<p align="center">
		<s:input type="hidden" id="txtHidUom" path="strhidUom"></s:input>
			<input type="submit" value="Submit" id="btnSubmit" class="form_button"
				 /> 
				<input type="reset"
				value="Reset" class="form_button" />
		</p><br><br>
</s:form>
</body>
</html>