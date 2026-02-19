<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.InquiryCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.InquiryBean"%>

<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <%@ include file="Header.jsp"%>
 <div align="center">
 
 
 <%
InquiryBean bean = (InquiryBean) request.getAttribute("bean");
if (bean == null) {
    bean = new InquiryBean();
}
%>
        <form action="<%=ORSView.INQUIRY_CTL%>" method="post">

      


            <h1 align="center" style="margin-bottom: -15; color: navy">
               Add Inquiry
            </h1>

            <div style="height: 15px; margin-bottom: 12px">
                <H3>
                    <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
                </H3>
                <H3>
                    <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
                </H3>
            </div>


            <table>
                <tr>
                    <th align="left">Name<span style="color: red">*</span></th>
          <td>
<input type="text" name="name" placeholder="Enter name"
value="<%=DataUtility.getStringData(bean.getName())%>">
</td>
                    <td style="position: fixed;">
                        <font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
                    </td>
                </tr>
            <tr>
                    <th align="left">Email<span style="color: red">*</span></th>
                    <td>
                        <input type="email" name="email" placeholder="Enter email"  value="<%=DataUtility.getStringData(bean.getEmail())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
                    </td>
                </tr>
                <tr>
                    <th align="left">Subject<span style="color: red">*</span></th>
                    <td>
                        <input type="text" name="subject"  placeholder="Enter subject"
                            value="<%=DataUtility.getStringData(bean.getSubject())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red"><%=ServletUtility.getErrorMessage("subject", request)%></font>
                    </td>
                </tr>
                <tr>
                  <th align="left">Status<span style="color: red">*</span></th>
                  <td>
                  <%HashMap<String,String> map=new HashMap();
                  map.put("online","online");
                  map.put("offline" ,"offline");
                  String htmlList=HTMLUtility.getList("status", bean.getStatus(),map);
                  %>
                  <%=htmlList %>
                  
                  </td>
                   <td style="position: fixed;">
                        <font color="red"><%=ServletUtility.getErrorMessage("status", request) %></td>
                </tr>
                <tr>
                
                    <th align="left">Status<span style="color: red">*</span></th>
                    <td>
                        <input type="text" name="status"  placeholder="Enter status"
                            value="<%=DataUtility.getStringData(bean.getStatus())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font>
                    </td>
                </tr>
              
                <tr>
                    <th></th>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=InquiryCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=InquiryCtl.OP_RESET%>">
                    </td>
                  
                </tr>
            </table>
        </form>
    </div>

</body>
</html>