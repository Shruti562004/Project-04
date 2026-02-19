<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.EventCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Event</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
<form action="<%=ORSView.EVENT_CTL%>" method="POST">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EventBean" scope="request" />

<div align="center">

<h1 align="center" style="margin-bottom:-15; color:navy">
<% if (bean != null && bean.getId() > 0) { %>
	Update Event
<% } else { %>
	Add Event
<% } %>
</h1>

<div style="height:15px; margin-bottom:12px">
	<h3 align="center">
		<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
	</h3>
	<h3 align="center">
		<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
	</h3>
</div>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

<table>

<tr>
	<th align="left">Event Code<span style="color:red">*</span></th>
	<td>
		<input type="text" name="eventCode" placeholder="Enter code"
			value="<%=DataUtility.getStringData(bean.getEventCode())%>">
	</td>
	<td style="position:fixed;">
		<font color="red"><%=ServletUtility.getErrorMessage("eventCode", request)%></font>
	</td>
</tr>

<tr>
	<th align="left">Event Name<span style="color:red">*</span></th>
	<td>
		<input type="text" name="eventName" placeholder="Enter Name"
			value="<%=DataUtility.getStringData(bean.getEventName())%>">
	</td>
	<td style="position:fixed;">
		<font color="red"><%=ServletUtility.getErrorMessage("eventName", request)%></font>
	</td>
</tr>

<tr>
	<th align="left">Date of Event<span style="color:red">*</span></th>
	<td>
		<input type="date" name="eventDate"
			value="<%=DataUtility.getDateString(bean.getEventDate())%>">
	</td>
	<td style="position:fixed;">
		<font color="red"><%=ServletUtility.getErrorMessage("eventDate", request)%></font>
	</td>
</tr>

<tr>
	<th align="left">Location<span style="color:red">*</span></th>
	<td>
		<%
			HashMap<String,String> map = new HashMap<>();
			map.put("Pune","Pune");
			map.put("Indore","Indore");
			map.put("Rajgarh","Rajgarh");

			String htmlList = HTMLUtility.getList(
				"eventLocation",
				bean.getEventLocation(),
				map);
		%>
		<%=htmlList%>
	</td>
	<td style="position:fixed;">
		<font color="red"><%=ServletUtility.getErrorMessage("eventLocation", request)%></font>
	</td>
</tr>

<tr>
	<th></th>

	<% if (bean != null && bean.getId() > 0) { %>

	<td align="left" colspan="2">
		<input type="submit" name="operation" value="<%=EventCtl.OP_UPDATE%>">
		<input type="submit" name="operation" value="<%=EventCtl.OP_CANCEL%>">
	</td>

	<% } else { %>

	<td align="left" colspan="2">
		<input type="submit" name="operation" value="<%=EventCtl.OP_SAVE%>">
		<input type="submit" name="operation" value="<%=EventCtl.OP_RESET%>">
	</td>

	<% } %>
</tr>

</table>
</div>
</form>
</body>
</html>
