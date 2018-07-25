<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Characteristics</title>
<style type="text/css">

<style type="text/css">

</style>
</head>

	<script type="text/javascript">
	
	
	function funPreviousForm(value) {
		//alert(value);
		window.opener.funSetCharData(value);
		window.close();
	}
	
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	
	
	
	
	function funCloseWindow()
	{
		/*
		var text = '{ "employees" : [' +
		'{ "firstName":"John" , "lastName":"Doe" },' +
		'{ "firstName":"Anna" , "lastName":"Smith" },' +
		'{ "firstName":"Peter" , "lastName":"Jones" } ]}';
		*/
		var prodCode="";
		var i=0;
		var text =' [';
		
			$('#tblProdChar > tbody  > tr').each(function() {
				
				var charCode=$("#strCharCode"+i).val();
		    	var charName=$("#strCharName"+i).val();
		    	var prodCode=$("#strProdCode"+i).val();
		    	
		    	
		    //alert(charName+"   "+specf);
		    
		    text+='{ "charCode":"'+charCode+'","CharName":"'+charName+'","ProdCode":"'+prodCode+'"},';
		    i++;
		    
		 });
			text=text.substring(0, text.length-1);
		text+=']';
	
		funPreviousForm(text);
		window.close();
	}
	</script>


<body style="background-color: #C0E4FF">

	

			<table >
			  <tr>
			    <td>
			    <div id="formHeading" align="center"><span  style="color: #0066ff">Characteristics</span></div></td>
			  </tr>
			 
			                 
			  </table>
			  
			  <table >
			  
			  
			  </table>
			  
			  
			   <table id="tblProdChar" style="width:100%;border:#0F0;table-layout:fixed;overflow:scroll; background-color: #C0E4FF;border: 1px solid black;" class="transTablex col4-center" >
<!-- 					<tr style="border"> -->
<!-- 							<td><lable style="font-size: 125%" bold="true">Characteristics</lable></td> -->
<!-- 							<td><lable style="font-size: 125%" bold="true">Specification</lable></td>				 -->
<!-- 					</tr> -->
								<c:forEach items="${listProdCharData}" var="obj"  varStatus="loopCounter" >
 									
									<tr>
									
									<td style="width: 34%; height: 12px;"><input size="27%" class="Box" id="strCharCode${loopCounter.index}"  readonly="readonly" class="strCharCode" 
<%--  											name="listProdChar[${loopCounter.index}].strCharName"  --%>
											value="${obj.strCharCode}" /></td>
									
									
										<td style="width: 34%; height: 12px;"><input size="27%" class="text" id="strCharName${loopCounter.index}"  readonly="readonly" class="strCharNames" 
<%--  											name="listProdChar[${loopCounter.index}].strCharName"  --%>
											value="${obj.strCharName}" /></td>


										<td ><input type="hidden"  id="strProdCode${loopCounter.index}" class="strProdCode"
<%--  											name="listProdChar[${loopCounter.index}].strCharName"  --%>
											value="${obj.strProdCode}" /></td>


<!-- 										<td style="width:35%; height: 12px;"><input type="text" size="11%" class="BoxW116px"  -->
<%-- <%-- 											name="listProdChar[${loopCounter.index}].strSpecf"   --%> 
<%-- 											id="strSpecf${loopCounter.index}"  class="strSpec" --%>
											
<%-- 											value="${obj.strSpecf}" /></td> --%>


									
									</tr>
								</c:forEach> 
								
							</table>
		<br>					
		<p align="center">
			<input type="submit" value="Close" onclick="funCloseWindow();"	class="form_button" id="btnSaveC"/>&nbsp; &nbsp; &nbsp;
				 
		</p>					
			  


</body>

</html>