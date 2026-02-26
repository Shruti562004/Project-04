<%@page import="in.co.rays.proj4.bean.AnnounceBean"%>
<%@page import="in.co.rays.proj4.controller.AnnounceListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.PaymentListCtl"%>

<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Announcement List</title>
    <link rel="icon" type="image/png"
          href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
<%@include file="Header.jsp"%>

<jsp:useBean id="bean"
             class="in.co.rays.proj4.bean.AnnounceBean"
             scope="request" />

<div align="center">

    <h1 style="color: navy;">Payment List</h1>

    <h3><font color="red">
        <%=ServletUtility.getErrorMessage(request)%>
    </font></h3>

    <h3><font color="green">
        <%=ServletUtility.getSuccessMessage(request)%>
    </font></h3>

    <form action="<%=ORSView.ANNOUNCE_LIST_CTL%>" method="post">

<%
    int pageNo = ServletUtility.getPageNo(request);
    int pageSize = ServletUtility.getPageSize(request);
    int index = ((pageNo - 1) * pageSize) + 1;

    int nextPageSize = 0;
    if (request.getAttribute("nextListSize") != null) {
        nextPageSize = DataUtility.getInt(
                request.getAttribute("nextListSize").toString());
    }

    List list = ServletUtility.getList(request);
    Iterator it = list.iterator();
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<!-- 🔍 Search Area -->
<table width="100%">
<tr>
<td align="center">
    <label><b>code :</b></label>
    <input type="text" name="code"
           value="<%=DataUtility.getStringData(bean.getCode())%>">

    &nbsp;

    <input type="submit" name="operation"
           value="<%=AnnounceListCtl.OP_SEARCH%>">

    <input type="submit" name="operation"
           value="<%=AnnounceListCtl.OP_RESET%>">
</td>
</tr>
</table>

<br>

<!-- 📋 Table -->
<table border="1" width="100%" style="border: groove;">
<tr style="background-color:#e1e6f1e3;">
    <th width="5%"><input type="checkbox" id="selectall"></th>
    <th width="5%">S.No</th>
    <th width="25%">Code</th>
    <th width="25%">Title</th>
    <th width="20%">Description</th>
       <th width="20%">PublishDate</th>
           <th width="20%">Status</th>
    <th width="20%">Edit</th>
</tr>

<%
while (it.hasNext()) {
    bean = (AnnounceBean) it.next();
%>

<tr>
    <td align="center">
        <input type="checkbox" class="case"
               name="ids" value="<%=bean.getId()%>">
    </td>

    <td align="center"><%=index++%></td>
    <td align="center"><%=bean.getCode()%></td>
    <td align="center"><%=bean.getTitle()%></td>

        <td align="center"><%=bean.getDescription()%></td>
            <td align="center"><%=bean.getPublishDate()%></td>
                <td align="center"><%=bean.getStatus()%></td>

    <td align="center">
        <a href="AnnounceCtl?id=<%=bean.getId()%>">Edit</a>
    </td>
</tr>

<% } %>
</table>

<br>

<!-- 🔄 Pagination -->
<table width="100%">
<tr>

<td width="25%">
<input type="submit" name="operation"
       value="<%=AnnounceListCtl.OP_PREVIOUS%>"
       <%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td width="25%" align="center">
<input type="submit" name="operation"
       value="<%=AnnounceListCtl.OP_NEW%>">
</td>

<td width="25%" align="center">
<input type="submit" name="operation"
       value="<%=AnnounceListCtl.OP_DELETE%>">
</td>

<td width="25%" align="right">
<input type="submit" name="operation"
       value="<%=AnnounceListCtl.OP_NEXT%>"
       <%=nextPageSize != 0 ? "" : "disabled"%>>
</td>

</tr>
</table>

</form>
</div>
</body>
</html>