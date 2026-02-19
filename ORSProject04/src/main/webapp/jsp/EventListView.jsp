<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.EventBean"%>
<%@page import="in.co.rays.proj4.controller.EventListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Event List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EventBean"
	scope="request"></jsp:useBean>

<div align="center">

<h1 style="color: navy;">Event List</h1>

<!-- Success & Error -->
<div style="height: 15px; margin-bottom: 12px">
	<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
	<h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
</div>

<form action="<%=ORSView.EVENT_LIST_CTL%>" method="post">

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

/* ✅ SAFE next page */
int nextPageSize = 0;
if (request.getAttribute("nextListSize") != null) {
	nextPageSize = DataUtility.getInt(
		request.getAttribute("nextListSize").toString());
}

/* ✅ Dropdown list */
@SuppressWarnings("unchecked")
List<EventBean> eventDropdownList =
	(List<EventBean>) request.getAttribute("eventDropdownList");

/* ✅ Main list */
@SuppressWarnings("unchecked")
List<EventBean> list =
	(List<EventBean>) ServletUtility.getList(request);
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<!-- 🔍 Search -->
<table style="width: 100%">
<tr>
<td align="center">
	<label><b>Event :</b></label>

	<%= HTMLUtility.getList(
        "eventName",
        String.valueOf(bean.getEventName()),
        eventDropdownList) %>


	&nbsp;&nbsp;

	<input type="submit" name="operation"
		value="<%=EventListCtl.OP_SEARCH%>">

	<input type="submit" name="operation"
		value="<%=EventListCtl.OP_RESET%>">
</td>
</tr>
</table>

<br>

<%
if (list != null && list.size() > 0) {

	Iterator<EventBean> it = list.iterator();
%>

<!-- 📋 Table -->
<table border="1" width="100%" style="border: groove;">
<tr style="background-color: #e1e6f1e3;">
	<th width="5%"><input type="checkbox" id="selectall"></th>
	<th width="5%">S.No</th>
	<th width="25%">Event Code</th>
	<th width="25%">Event Name</th>
	<th width="30%">Location</th>
	<th width="10%">Edit</th>
</tr>

<%
while (it.hasNext()) {
	bean = it.next();
%>

<tr>
<td align="center">
	<input type="checkbox" class="case"
		name="ids" value="<%=bean.getId()%>">
</td>

<td align="center"><%=index++%></td>
<td align="center"><%=bean.getEventCode()%></td>
<td align="center"><%=bean.getEventName()%></td>
<td align="center"><%=bean.getEventLocation()%></td>

<td align="center">
	   <a href="EventCtl?id=<%=bean.getId()%>">Edit</a>
	
</td>
</tr>

<%
}
%>
</table>

<!-- 🔄 Pagination -->
<table width="100%">
<tr>

<td width="25%">
	<input type="submit" name="operation"
		value="<%=EventListCtl.OP_PREVIOUS%>"
		<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center" width="25%">
	<input type="submit" name="operation"
		value="<%=EventListCtl.OP_NEW%>">
</td>

<td align="center" width="25%">
	<input type="submit" name="operation"
		value="<%=EventListCtl.OP_DELETE%>">
</td>

<td align="right" width="25%">
	<input type="submit" name="operation"
		value="<%=EventListCtl.OP_NEXT%>"
		<%=nextPageSize != 0 ? "" : "disabled"%>>
</td>

</tr>
</table>

<%
} else {
%>

<table>
<tr>
<td align="center">
	<font color="red"><b>No Record Found</b></font><br><br>
	<input type="submit" name="operation"
		value="<%=EventListCtl.OP_BACK%>">
</td>
</tr>
</table>

<%
}
%>

</form>
</div>
</body>
</html>
