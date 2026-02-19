<%@page import="in.co.rays.proj4.bean.InventoryBean"%>
<%@page import="in.co.rays.proj4.controller.InventoryCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Inventory</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<%@ include file="Header.jsp"%>
<form action="<%=ORSView.INVENTORY_CTL%>" method="post">




<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InventoryBean"
	scope="request"></jsp:useBean>

<div align="center">

<h1 align="center" style="margin-bottom: -15; color: navy">
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
Inventory
</h1>

<div style="height: 15px; margin-bottom: 12px">
	<h3 align="center">
		<font color="red">
			<%=ServletUtility.getErrorMessage(request)%>
		</font>
	</h3>

	<h3 align="center">
		<font color="green">
			<%=ServletUtility.getSuccessMessage(request)%>
		</font>
	</h3>
</div>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

<table>

<tr>
	<th align="left">Supplier Name<span style="color:red">*</span></th>
	<td>
		<input type="text" name="supplierName"
			placeholder="Enter Supplier Name"
			value="<%=DataUtility.getStringData(bean.getSupplierName())%>">
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("supplierName", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Date of Birth<span style="color:red">*</span></th>
	<td>
		<input type="date" name="dob"
			value="<%=DataUtility.getDateString(bean.getDob())%>">
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("dob", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Quantity<span style="color:red">*</span></th>
	<td>
		<input type="number" name="quantity"
			placeholder="Enter Quantity"
			value="<%=DataUtility.getStringData(bean.getQuantity())%>">
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("quantity", request)%>
		</font>
	</td>
</tr>

<tr>
	<th align="left">Product<span style="color:red">*</span></th>
	<td>
		<input type="text" name="product"
			placeholder="Enter Product"
			value="<%=DataUtility.getStringData(bean.getProduct())%>">
	</td>
	<td style="position: fixed;">
		<font color="red">
			<%=ServletUtility.getErrorMessage("product", request)%>
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
	<input type="submit" name="operation" value="<%=InventoryCtl.OP_UPDATE%>">
	<input type="submit" name="operation" value="<%=InventoryCtl.OP_CANCEL%>">
</td>

<%
	} else {
%>

<td align="left" colspan="2">
	<input type="submit" name="operation" value="<%=InventoryCtl.OP_SAVE%>">
	<input type="submit" name="operation" value="<%=InventoryCtl.OP_RESET%>">
</td>

<%
	}
%>

</tr>
</table>
</div>
</form>
</body>
</html>
