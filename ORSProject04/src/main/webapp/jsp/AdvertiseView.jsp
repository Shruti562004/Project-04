<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.AdvertiseCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add Advertisement</title>
    <link rel="icon" type="image/png" 
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.ADVERTISE_CTL%>" method="POST">

    <%@ include file="Header.jsp" %>

    <jsp:useBean id="bean" 
        class="in.co.rays.proj4.bean.AdvertiseBean" 
        scope="request"></jsp:useBean>

    <div align="center">

        <h1 align="center" style="color: navy">
            <%
                if (bean != null && bean.getId() > 0) {
            %>
                Update
            <%
                } else {
            %>
                Add
            <%
                }
            %>
            Advertisement
        </h1>

        <div>
            <h3 align="center">
                <font color="green">
                    <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </h3>

            <h3 align="center">
                <font color="red">
                    <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </h3>
        </div>

        <!-- Hidden Fields -->
        <input type="hidden" name="id" value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"
            value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime"
            value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <table>

            <!-- Name -->
            <tr>
                <th align="left">
                    Name <span style="color:red">*</span>
                </th>
                <td>
                    <input type="text" name="name"
                        placeholder="Enter Name"
                        value="<%=DataUtility.getStringData(bean.getName())%>">
                </td>
                <td>
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("name", request)%>
                    </font>
                </td>
            </tr>

            <!-- Budget -->
            <tr>
                <th align="left">
                    Budget <span style="color:red">*</span>
                </th>
                <td>
                    <input type="text" name="budget"
                        placeholder="Enter Budget"
                        value="<%=DataUtility.getStringData(bean.getBudget())%>">
                </td>
                <td>
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("budget", request)%>
                    </font>
                </td>
            </tr>

            <!-- Start Date -->
            <tr>
                <th align="left">
                    Start Date <span style="color:red">*</span>
                </th>
                <td>
                    <input type="date" name="startDate"
                        value="<%=DataUtility.getStringData(bean.getStartDate())%>">
                </td>
                <td>
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("startDate", request)%>
                    </font>
                </td>
            </tr>

            <!-- Buttons -->
            <tr>
                <th></th>
                <td colspan="2">
                    <%
                        if (bean != null && bean.getId() > 0) {
                    %>
                        <input type="submit" name="operation"
                            value="<%=AdvertiseCtl.OP_UPDATE%>">
                        <input type="submit" name="operation"
                            value="<%=AdvertiseCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                        <input type="submit" name="operation"
                            value="<%=AdvertiseCtl.OP_SAVE%>">
                        <input type="submit" name="operation"
                            value="<%=AdvertiseCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </td>
            </tr>

        </table>

    </div>

</form>

</body>
</html>