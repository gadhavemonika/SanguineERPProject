<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
  <script type="text/javascript">
  $(document).ready(function(){
       $('#clientCode').focus();
  });
  
  function funApplyNumberValidation(){
		$(".numeric").numeric();
		$(".integer").numeric(false, function() { alert("Integers only"); this.value = ""; this.focus(); });
		$(".positive").numeric({ negative: false }, function() { alert("No negative values"); this.value = ""; this.focus(); });
		$(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Positive integers only"); this.value = ""; this.focus(); });
	}
</script>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Client Login</title>
  </head>
  
<body>
	<div>
	<div style="float: left;padding-left: 40px">
		<img alt=""src="../${pageContext.request.contextPath}/resources/images/Sanguine_ERP_Logo_1.jpg" height="100px" width="466px">
	</div>
      <div style="padding-top: 100px">
		<s:form name="login" method="POST" action="validateClient.html"  >
			<div style="padding-right: 12%; padding-bottom: 0px;">
				<div
					style="float: right; width: 339px; height: 250px; -webkit-border-radius: 29px; -moz-border-radius: 29px; border-radius: 29px; border: 2px solid #0595D2; background: rgba(252, 253, 255, 0.4); -webkit-box-shadow: #42B3F4 2px 2px 2px; -moz-box-shadow: #42B3F4 2px 2px 2px; box-shadow: #42B3F4 2px 2px 2px;">
					<div
						style="width: 340px; height: 62px; background-image: url(../${pageContext.request.contextPath}/resources/images/loginlogo.png);">
						<br>
						<p align="center" style="font-size: 17px; font-weight: bold;">
							Sanguine Softwares Solutions Pvt. Ltd.</p>
					</div>

					<img alt=""
						src="../${pageContext.request.contextPath}/resources/images/login.png"
						style="display: block; padding-left: 1px">
					<div style="width: 340px; height: 115px;">
						<table>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td width="100px" style="padding-left: 30px">
									<s:label path="strClientCode" cssStyle="font-size: 15px;font-weight: normal;">Client Code </s:label></td>
								<td><s:input cssClass="loginInput numeric" name="clientCode" placeholder="Client Code"
									path="strClientCode" autocomplete="off" id="clientCode" />
									<s:errors path="strClientCode"></s:errors>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td style="padding-left: 30px"><s:label path="strPassword"
										cssStyle="font-size: 15px;font-weight: normal;">Password </s:label></td>
								<td><s:input type="password" placeholder="Password"
										required="true" cssClass="loginInput" name="password"
										path="strPassword" /> <s:errors path="strPassword"></s:errors></td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div
						style="width: 340px; height: 36px; background-image: url(../${pageContext.request.contextPath}/resources/images/loginfoot.png);">
						<p align="right" style="padding-right: 22px; padding-top: 9px">
							<input type="submit" value="" class="loginButton" />
						</p>
					</div>
				</div>
			</div>
		
		</s:form>
		</div>
	</div>
	
	<c:if test="${!empty invalid}">
<script type="text/javascript">
	alert("Invalid Login");
</script>
</c:if> 
<c:if test="${!empty LicenceExpired}">
<script type="text/javascript">
	alert("Licence is Expired \n Please Contact Technical Support");
</script>
</c:if> 
	<script type="text/javascript">
		funApplyNumberValidation();
	</script>
</body>
</html>