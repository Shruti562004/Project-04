<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.FollowupCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.FollowupBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Followup</title>
</head>

<body>

<%
FollowupBean bean = (FollowupBean) request.getAttribute("bean");
if (bean == null) {
    bean = new FollowupBean();
}
%>

<%@include file="Header.jsp" %>

<div align="center">
    <h3 style="color:red">
        <%=ServletUtility.getErrorMessage(request)%>
    </h3>
    <h3 style="color:green">
        <%=ServletUtility.getSuccessMessage(request)%>
    </h3>
</div>

<div align="center">
<h1>Add Followup</h1>

<form action="<%=ORSView.FOLLOWUP_CTL%>" method="post">

<table>

<tr>
    <th>Patient <span style="color:red">*</span></th>
    <td>
        <input type="text" name="patient" placeholder="Enter Patient"
        value="<%=DataUtility.getStringData(bean.getPatient())%>">
    </td>
    <td style="color:red">
        <%=ServletUtility.getErrorMessage("patient", request)%>
    </td>
</tr>

<tr>
    <th>Doctor <span style="color:red">*</span></th>
    <td>
        <input type="text" name="doctor" placeholder="Enter Doctor"
        value="<%=DataUtility.getStringData(bean.getDoctor())%>">
    </td>
    <td style="color:red">
        <%=ServletUtility.getErrorMessage("doctor", request)%>
    </td>
</tr>

<tr>
    <th>DOB <span style="color:red">*</span></th>
    <td>
        <input type="date" name="dob"
        value="<%=DataUtility.getDateString(bean.getDob())%>">
    </td>
    <td style="color:red">
        <%=ServletUtility.getErrorMessage("dob", request)%>
    </td>
</tr>

<tr>
    <th>Fees <span style="color:red">*</span></th>
    <td>
        <input type="text" name="fees" placeholder="Enter Fees"
        value="<%=DataUtility.getStringData(bean.getFees())%>">
    </td>
    <td style="color:red">
        <%=ServletUtility.getErrorMessage("fees", request)%>
    </td>
</tr>

<tr>
    <th></th>
    <td>
        <input type="submit" name="operation"
        value="<%=FollowupCtl.OP_SAVE%>">
    </td>
    <td>
        <input type="submit" name="operation"
        value="<%=FollowupCtl.OP_RESET%>">
    </td>
</tr>

</table>

</form>
</div>

</body>
</html>
