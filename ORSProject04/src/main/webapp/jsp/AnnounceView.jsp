<%@page import="in.co.rays.proj4.controller.AnnounceCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.AnnounceBean"%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="java.util.List"%>


<html>
<head>
<title>Add Payment</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.ANNOUNCE_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>


		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.AnnounceBean"
			scope="request" />

		<div align="center">

			<h1 style="color: navy">
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
				Announcement
			</h1>

			<!-- Success / Error Messages -->
			<div style="height: 15px; margin-bottom: 12px">
				<h3>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<!-- Hidden Fields -->
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

		
				<tr>
					<th align="left">Code <span style="color: red">*</span></th>
					<td><input type="text" name="code"
						placeholder="Enter "
						value="<%=DataUtility.getStringData(bean.getCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("code", request)%>
					</font></td>
				</tr>

				<!-- Payer Name -->
				<tr>
					<th align="left">Title <span style="color: red">*</span></th>
					<td><input type="text" name="title"
						placeholder="Enter "
						value="<%=DataUtility.getStringData(bean.getTitle())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("title", request)%>
					</font></td>
				</tr>

				<!-- Amount -->
				<tr>
					<th align="left">Description <span style="color: red">*</span></th>
					<td><input type="text" name="description"
						placeholder="Enter "
					value="<%=DataUtility.getStringData(bean.getDescription())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%>
					</font></td>
				</tr>

				
				<tr>
					<th align="left">Publish Date <span style="color: red">*</span></th>
					<td><input type="date" name="publishDate"
						value="<%=DataUtility.getDateString(bean.getPublishDate())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("publishDate", request)%>
					</font></td>
				</tr>

				
			<tr>
					<th align="left">Status <span style="color: red">*</span></th>
					<td><input type="text" name="status"
						value="<%=DataUtility.getStringData(bean.getStatus())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
				</tr>
				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=AnnounceCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=AnnounceCtl.OP_CANCEL%>"></td>
					<%
					} else {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=AnnounceCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=AnnounceCtl.OP_RESET%>"></td>
					<%
					}
					%>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>