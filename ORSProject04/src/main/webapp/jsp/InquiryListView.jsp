<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%@page import="in.co.rays.proj4.controller.InquiryListCtl"%>
<%@page import="in.co.rays.proj4.bean.InquiryBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inquiry List</title>
</head>

<body>
    <%@include file="Header.jsp"%>
    

<%

InquiryBean bean = (InquiryBean) session.getAttribute("bean");
if (bean == null) {
    bean = new InquiryBean();
}

%>

<div align="center">

    <h1 style="color: navy;">Inquiry List</h1>

    <!-- Messages -->
   

    <div align="center">
        <div style="height: 15px; margin-bottom: 12px">
            <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
            <h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
        </div>

    <form action="<%=ORSView.INQUIRY_LIST_CTL%>" method="post">

<%
    int pageNo = ServletUtility.getPageNo(request);
    int pageSize = ServletUtility.getPageSize(request);
    int index = ((pageNo - 1) * pageSize) + 1;
    int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
   
    List<InquiryBean> list = (List<InquiryBean>) ServletUtility.getList(request);
    Iterator<InquiryBean> it = list.iterator();

    if (list.size() != 0) {
%>

   <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

        <!-- 🔍 Search Panel -->
        <table width="100%">
            <tr>
                <td align="center">

                    <b>Name :</b>
                    <input type="text" name="name"
                           placeholder="Enter Name"
                           value="<%=ServletUtility.getParameter("name", request)%>">

                    &nbsp;&nbsp;

                    <b>Email :</b>
                    <input type="email" name="email"
                           placeholder="Enter Email"
                           value="<%=ServletUtility.getParameter("email", request)%>">

                    &nbsp;&nbsp;

                   
                    <input type="submit" name="operation"
                           value="<%=InquiryListCtl.OP_SEARCH%>">

                    <input type="submit" name="operation"
                           value="<%=InquiryListCtl.OP_RESET%>">
                </td>
            </tr>
        </table>

        <br>




        <!-- 📋 Table -->
        <table border="1" width="100%" style="border-collapse: collapse;">
            <tr style="background-color:#e1e6f1e3;">
                <th><input type="checkbox" id="selectall"></th>
                <th>S.No</th>
                <th>Name</th>
                <th>Email</th>
                <th>Subject</th>
                <th>Status</th>
                <th>Role</th>
                <th>Edit</th>
            </tr>

<%
    while (it.hasNext()) {
    	bean=(InquiryBean)it.next();
%>

            <tr>
                <td align="center">
                    <input type="checkbox" class="case"
                           name="ids" value="<%=bean.getId()%>">
                </td>

                <td align="center"><%=index++%></td>
                <td align="center"><%=bean.getName()%></td>
                <td align="center"><%=bean.getEmail()%></td>
                <td align="center"><%=bean.getSubject()%></td>
                <td align="center"><%=bean.getStatus()%></td>
                <td align="center">
                    
                </td>
                <td align="center">
                    <a href="InquiryCtl?id=<%=bean.getId()%>">Edit</a>
                </td>
            </tr>

<% } %>

        </table>

        <!-- 🔁 Pagination -->
        <table width="100%" style="margin-top:10px;">
            <tr>
                <td width="25%">
                    <input type="submit" name="operation"
                           value="<%=InquiryListCtl.OP_PREVIOUS%>"
                           <%= (pageNo > 1) ? "" : "disabled" %>>
                </td>

                <td align="center" width="25%">
                    <input type="submit" name="operation"
                           value="<%=InquiryListCtl.OP_NEW%>">
                </td>

                <td align="center" width="25%">
                    <input type="submit" name="operation"
                           value="<%=InquiryListCtl.OP_DELETE%>">
                </td>

                <td align="right" width="25%">
                    <input type="submit" name="operation"
                           value="<%=InquiryListCtl.OP_NEXT%>"
                           <%= (nextListSize > 0) ? "" : "disabled" %>>
                </td>
            </tr>
        </table>

<%
} else {
%>

        <h3 style="color:red;">No Record Found</h3>
        <input type="submit" name="operation"
               value="<%=InquiryListCtl.OP_BACK%>">

<% } %>

    </form>
</div>

</body>
</html>


