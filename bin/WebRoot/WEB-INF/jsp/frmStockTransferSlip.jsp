<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    
    <script type="text/javascript">
    function funHelp(transactionName)
	{
		window.showModalDialog("searchform.html?formname="+transactionName, 'window', 'width=600,height=600');
    }
    
    function funSetData(code)
	{
    	$("#txtSTCode").val(code);
	}
	</script>
    
  </head>
  
	<body>
		<s:form name="frmStockTransferSlip" method="POST" action="stktransferslip.html">
	   		<table>
			    <tr>
			        <td><label >Stock Transfer Code:</label></td>
			        <td>
			        	<s:input id="txtSTCode" path="strSTCode" ondblclick="funHelp('stktransfercode')"/>
			        </td>
			    </tr>
			    <tr>
			        <td><label >Type:</label></td>
			        <td>
			        	<select id="cmbType">
						 	<option value="General">General</option>
							<option value="Imported">Imported</option>
						</select>
					</td>
			    </tr>
			    <tr>
			      <td colspan="2"><input type="submit" value="Submit" /></td>
			      <td colspan="2"><input type="reset" value="Reset" /></td>
		      	</tr>
			</table>
		</s:form>
	</body>
</html>