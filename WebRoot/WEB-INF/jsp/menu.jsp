<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="java.util.List,com.sanguine.model.clsTreeMasterModel"%>


<html>
<head>
<style>

</style>
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/bootstrap.min.js"/>"></script>
<link rel="stylesheet"  href="<spring:url value="/resources/css/bootstrap.min.css"/>" /> --%>
<script type="text/javascript">
var cntrlIsPressed = false;


$(document).keydown(function(event){
    if(event.which=="17")
        cntrlIsPressed = true;
});

$(document).keyup(function(){
    cntrlIsPressed = false;
});

function selectMe(mouseButton)
{
	//alert(mouseButton);
	var _href = $(mouseButton).attr("href");
	
	//$(mouseButton).attr("href", _href + '?saddr=50.1234567,-50.03452');
	var split=_href.split("=");
	
	_href=split[0]+"=1" ;
	$(mouseButton).attr("href", _href);
	
    if(cntrlIsPressed)
    {
    	var split1=_href.split("=");
    
    	_href=split1[0];
    	$(mouseButton).attr("href", _href + '=2');
    	cntrlIsPressed=false;
    }
    cntrlIsPressed=false;

   
}

function onContextClick(mouseButton){
	var _href = $(mouseButton).attr("href");
	
	//$(mouseButton).attr("href", _href + '?saddr=50.1234567,-50.03452');
	var split=_href.split("=");
	
	_href=split[0]+"=1" ;
	$(mouseButton).attr("href", _href);
	
	var split1=_href.split("=");
    
	_href=split1[0];
	$(mouseButton).attr("href", _href + '=2');		
}


</script>
</head>

<body onload="">

	<div class="menu" id="test" >
		<ul id="tree" style="white-space: nowrap;">
		<c:forEach items="${treeMap}" var="draw1" varStatus="status1">
			<li>${draw1.key}
				<ul style="white-space: nowrap;">
					<c:forEach items="${draw1.value}" var="draw2" varStatus="status2">
						<c:forEach items="${draw2.value}" var="draw3" varStatus="status3">
							<c:if test="${draw3.key=='Parent'}">
								<ul style="white-space: nowrap;"> 
			 					 <li>
			 						 <a href="${draw3.value.strRequestMapping}?saddr=1" id="${draw3.value.strRequestMapping}" onclick="selectMe(this)" oncontextmenu="onContextClick(this)"
			 						title='${draw3.value.strFormDesc}'>${draw3.value.strFormDesc}</a>
			 		 			</li>
				 			 </ul>
							</c:if>
							
							<c:if test="${draw3.key !='Parent'}">
								<li>${draw3.key}
									<ul style="white-space: nowrap;">
										<c:forEach items="${draw3.value}" var="draw4" varStatus="status4">
											<ul style="white-space: nowrap;"> 
		 									 	<li>
			 									 <a href="${draw4.strRequestMapping}?saddr=1" id="${draw4.strRequestMapping}" onclick="selectMe(this)" oncontextmenu="onContextClick(this)"
			 									title='${draw4.strFormDesc}'>${draw4.strFormDesc}</a>
			 		 							</li>
				 			 				</ul>
										</c:forEach>
									</ul>
								</li>
							</c:if>
						</c:forEach>
					</c:forEach>
				</ul>
			</li>
			</c:forEach>
	</ul>			
		<script type="text/javascript">
			make_tree_menu('tree');
		</script>
		<br>
	</div>
</body>
</html>