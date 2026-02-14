<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Hi, Guest</h3>
	<a href="<%=ORSView.ROLE_CTL%>">Add Role</a> 
		<b>|</b>
			<a href="<%=ORSView.ROLE_LIST_CTL%>"> Role List</a> 
			<b>|</b>
	<a href="<%=ORSView.USER_CTL %>">Add User</a>
		<b>|</b>
		<a href="<%=ORSView.USER_LIST_CTL%>"> User List</a> 
			<b>|</b>
	<a href="<%=ORSView.COLLEGE_CTL %>">Add College</a>
		<b>|</b>
		<a href="<%=ORSView.COLLEGE_LIST_CTL%>"> College List</a> 
			<b>|</b>
	<a href="<%=ORSView.STUDENT_CTL %>">Add Student</a>
		<b>|</b>
		<a href="<%=ORSView.STUDENT_LIST_CTL%>"> Student List</a> 
			<b>|</b>
	<a href="<%=ORSView.MARKSHEET_CTL %>">Add Marksheet</a>
		<b>|</b>
		<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"> Marksheet List</a> 
			<b>|</b>
	<a href="<%=ORSView.COURSE_CTL %>">Add Course</a>
		<b>|</b>
		<a href="<%=ORSView.COURSE_LIST_CTL%>"> Course List</a> 
			<b>|</b>
	<a href="<%=ORSView.SUBJECT_CTL %>">Add Subject</a>
		<b>|</b>
		<a href="<%=ORSView.SUBJECT_LIST_CTL%>"> Subject List</a> 
			<b>|</b>
	<a href="<%=ORSView.TIMETABLE_CTL %>">Add Timetable</a>
		<b>|</b>
		<a href="<%=ORSView.TIMETABLE_LIST_CTL%>"> Timetable List</a> 
			<b>|</b>
	<a href="<%=ORSView.FACULTY_CTL %>">Add Faculty</a>
		<b>|</b>
	<a href="<%=ORSView.FACULTY_LIST_CTL%>"> Faculty List</a> 
				<b>|</b>
	<a href="<%=ORSView.INQUIRY_CTL %>">Add Inquery</a>
		<b>|</b>
	<a href="<%=ORSView.FACULTY_LIST_CTL%>"> Faculty List</a> 
	<hr>
</body>
</html>