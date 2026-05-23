
<%@page import="in.co.rays.proj4.controller.TaxCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.TrackingCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add Tax</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.TAX_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.TaxBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
                Tax
            </h1>

            <div style="height: 15px; margin-bottom: 12px">
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
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <table>
                <tr>
                    <th align="left">code<span style="color: red">*</span></th>
                    <td><input type="text" name="code" placeholder="Enter code" value="<%=DataUtility.getStringData(bean.getCode())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("code", request)%>
                        </font>
                    </td>
                </tr>

                <tr>
                    <th align="left">type<span style="color: red">*</span></th>
                    <td><input type="text" name="type" placeholder="Enter type" value="<%=DataUtility.getStringData(bean.getType())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("type", request)%> -                        </font>
                    </td>
                </tr>
 
            
                <tr>
                    <th align="left">percentage<span style="color: red">*</span></th>
                    <td><input type="text" name="percentage" maxlength="10" placeholder="Enter  percentage" value="<%= (bean.getPercentage() == 0) ? "" : bean.getPercentage()%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("percentage", request)%>
                        </font>
                    </td>
                </tr>
                
                 <tr>
                    <th align="left">status<span style="color: red">*</span></th>
                    <td><input type="text" name="status" maxlength="10" placeholder="Enter  status" value="<%=DataUtility.getStringData(bean.getStatus())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("status", request)%>
                        </font>
                    </td>
                </tr>

                <tr>
                    <th></th>
                    <td></td>
                </tr>

                <tr>
                    <th></th>
                    <%
                        if (bean != null && bean.getId() > 0) {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=TaxCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=TaxCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=TaxCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=TaxCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>