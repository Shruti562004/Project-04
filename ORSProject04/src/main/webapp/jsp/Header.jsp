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
	<a href="<%=ORSView.USER_CTL %>">Add User</a>
		<b>|</b>
	<a href="<%=ORSView.COLLEGE_CTL %>">Add College</a>
		<b>|</b>
	<a href="<%=ORSView.STUDENT_CTL %>">Add Student</a>
		<b>|</b>
	<a href="<%=ORSView.MARKSHEET_CTL %>">Add Marksheet</a>
		<b>|</b>
	<a href="<%=ORSView.COURSE_CTL %>">Add Course</a>
		<b>|</b>
	<a href="<%=ORSView.SUBJECT_CTL %>">Add Subject</a>
		<b>|</b>
		
	<a href="<%=ORSView.TIMETABLE_CTL %>">Add Timetable</a>
		<b>|</b>
	<a href="<%=ORSView.FACULTY_CTL %>">Add Faculty</a>
	<hr>
</body>
</html>