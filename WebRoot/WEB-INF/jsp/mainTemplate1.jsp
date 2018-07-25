<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>
	<tiles:insertAttribute name="title" ignore="true"></tiles:insertAttribute>
</title>
</head>
<body  bgcolor="#D8EDFF">

<div>
<tiles:insertAttribute name="header"></tiles:insertAttribute>
</div>


<div>
<tiles:insertAttribute name="body"></tiles:insertAttribute>
</div>


<div>
<tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>


<%-- <table style=" border:0;  align:center; width:100%; height:100%">
    <tr height="30%">
        <td colspan="2" align="center">
        	<tiles:insertAttribute name="header"></tiles:insertAttribute>
        </td>
    </tr>
    <tr height="60%">
        <td>
        	<tiles:insertAttribute name="body"></tiles:insertAttribute>
        </td>
    </tr>
    <tr height="28px">
        <td colspan="2"  align="center" >
        	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
        </td>
    </tr>
</table> --%>
<%-- 
<div style="height: 100%;">
  <div class="pagetop"> 
  <tiles:insertAttribute name="header"></tiles:insertAttribute>
    <!-- end .header --></div>
 <!--  <div class="content"> -->

 <div id="1">
    <tiles:insertAttribute name="body"></tiles:insertAttribute>
    <!-- end .content --></div>
  <div id="loginfooter"> <!-- id="loginfooter"> -->
   <tiles:insertAttribute name="footer"></tiles:insertAttribute>
    <!-- end .footer --></div> --%>
  <!-- end .container </div>-->
</body>
</html>


<%-- <div>
<img src="WebRoot/webstock1.jpg"width="534" height="100" alt="WEB---STOCKS" />
</div>
<div>
<tiles:insertAttribute name="header"></tiles:insertAttribute>
</div>
<div id="WizardBody" style="height:400px;background-color:#c0e2ff;">
<tiles:insertAttribute name="body"></tiles:insertAttribute>
</div>
<div id="WizardBody">
	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div> --%>