<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script type="text/javascript">

function funSearchFormPopup()
{
    window.open("searchform.html?formname=characteristics", 'window', 'width=600,height=600');
}

function funSetCode(Code)
{
	document.getElementById("CharacteristicsCode").value=Code;
	
	 $.ajax({
	        type: "GET",
	        url: getContextPath()+"/loadCharacteristicsMasterData.html?Code="+Code,
	        dataType: "json",
	        success: function(resp){
	          // we have the response
	         document.getElementById("CharacteristicsCode").value=resp.strCharCode;
	         document.getElementById("CharacteristicsName").value=resp.strCharName;
	        },
	        error: function(e){
	        	document.getElementById("CharacteristicsCode").value=Code;
	          alert('Error121212: ' + e);
	        }
	      });
}

</script>

<title>Insert title here</title>
</head>
<body>
	<s:form name="characteristicsForm" method="POST"
		action="savecharacteristics.html">
		<table>
		<tr></tr>
			<tr>
		        <td align="right"> <a id="baseUrl" href="attachDoc.html"> Attatch Documents </a> </td>
		    </tr>
			<tr>
				<td><s:label path="">Characteristics Code </s:label> 
				<s:input path="strCharCode" id="CharacteristicsCode" readonly="true" ondblclick="funSearchFormPopup()"/></td>
			</tr>
			<tr>
				<td><s:label path="">Name </s:label>. <s:input
						path="strCharName" id="CharacteristicsName" /></td>
			</tr>
			<tr>
				<td><s:label path="">Type</s:label></td>
				<td><s:select path="strCharType">
					<s:option value="Text">Text</s:option>
					<s:option value="Integer">Integer</s:option>
					<s:option value="Decimal">Decimal</s:option>
				</s:select>
				</td>
			</tr>

			<tr>
				<td><s:label path="">Description</s:label> <s:input
						path="strCharDesc" /></td>

			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>

		</table>

	</s:form>
</body>
</html>