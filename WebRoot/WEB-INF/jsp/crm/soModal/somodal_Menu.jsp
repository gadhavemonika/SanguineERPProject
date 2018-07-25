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
<script type="text/javascript">
	var cntrlIsPressed = false;

	$(document).keydown(function(event) {
		if (event.which == "17")
			cntrlIsPressed = true;
	});

	$(document).keyup(function() {
		cntrlIsPressed = false;
	});

	function selectMe(mouseButton) {
		var _href = $(mouseButton).attr("href");
		var split = _href.split("=");
		_href = split[0] + "=1";
		$(mouseButton).attr("href", _href);
		if (cntrlIsPressed) {
			var split1 = _href.split("=");
			_href = split1[0];
			$(mouseButton).attr("href", _href + '=2');
			cntrlIsPressed = false;
		}
		cntrlIsPressed = false;
	}

	function onContextClick(mouseButton) {

		var _href = $(mouseButton).attr("href");
		var split = _href.split("=");
		_href = split[0] + "=1";
		$(mouseButton).attr("href", _href);
		var split1 = _href.split("=");
		_href = split1[0];
		$(mouseButton).attr("href", _href + '=2');
	}
</script>
</head>

<body onload="">

	<div class="menu" id="test_1">
		<ul id="tree" style="white-space: nowrap;">
			<li>Material Planing
				<ul style="white-space: nowrap;">
					<li><a href="frmUnplanned_Item.html?soCode=${soCode}">UnPlanned Item</a></li>

					<li>Planned Item
						<ul style="white-space: nowrap;">
							<li><a href="frmIndend_Status.html?soCode=${soCode}">Indent Status</a></li>
							<li><a href="frmPO_Status.html?soCode=${soCode}">Purchase Order Status</a></li>
						</ul>
					</li>
				</ul>
			</li>

			<li>Job Order
				<ul style="white-space: nowrap;">
					<li><a href="frmUnplanned_JO.html?soCode=${soCode}">UnPlanned Job Order</a></li>
					<li><a href="frmPlanned_JO.html?soCode=${soCode}">Planned Job Order</a></li>
				</ul>
			</li>

			<li>Work Order
				<ul style="white-space: nowrap;">
					<li><a href="frmUnplanned_WO.html?soCode=${soCode}">UnPlanned Work Order</a></li>
					<li><a href="frmPlanned_WO.html?soCode=${soCode}">Planned Work Order</a></li>
				</ul>
			</li>

		</ul>
		<script type="text/javascript">
			make_tree_menu('tree');
		</script>
		<br>
	</div>
</body>
</html>