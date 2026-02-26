<%@page import="in.co.rays.proj4.controller.PaymentCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>



<html>
<head>
<title>Add Payment</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.PAYMENT_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>


		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PaymentBean"
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
				Payment
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

				<!-- Transaction ID -->
				<tr>
					<th align="left">Transaction Id <span style="color: red">*</span></th>
					<td><input type="text" name="transactionId"
						placeholder="Enter transaction id"
						value="<%=DataUtility.getStringData(bean.getTransactionId())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("transactionId", request)%>
					</font></td>
				</tr>

				<!-- Payer Name -->
				<tr>
					<th align="left">Payer Name <span style="color: red">*</span></th>
					<td><input type="text" name="payerName"
						placeholder="Enter name"
						value="<%=DataUtility.getStringData(bean.getPayerName())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("payerName", request)%>
					</font></td>
				</tr>

				<!-- Amount -->
				<tr>
					<th align="left">Amount <span style="color: red">*</span></th>
					<td><input type="text" name="amount"
						placeholder="Enter amount"
					value="<%=DataUtility.getDateString(bean.getPaymentDate())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("amount", request)%>
					</font></td>
				</tr>

				<!-- Payment Date -->
				<tr>
					<th align="left">Payment Date <span style="color: red">*</span></th>
					<td><input type="date" name="paymentDate"
						value="<%=DataUtility.getDateString(bean.getPaymentDate())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("paymentDate", request)%>
					</font></td>
				</tr>

				<!-- Payment Status -->
				<tr>
					<th align="left">Payment Status <span style="color: red">*</span></th>
					<td><input type="text" name="paymentStatus" maxlength="10"
						placeholder="Enter payment status"
						value="<%=DataUtility.getStringData(bean.getPaymentStatus())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("paymentStatus", request)%>
					</font></td>
				</tr>

				<!-- Buttons -->
				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=PaymentCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=PaymentCtl.OP_CANCEL%>"></td>
					<%
					} else {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=PaymentCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=PaymentCtl.OP_RESET%>"></td>
					<%
					}
					%>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>