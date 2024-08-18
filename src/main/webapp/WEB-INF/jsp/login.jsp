<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BC System</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">

</head>
<body>
	<div class="container">
		<h1>Customer Login</h1>
		<form:form action="/login" method="post">

			<div class="personal-info">
				<div class="form-row">
					<label for="username">User Name<span class="required" >*</span></label> <input type="text" 
						id="name" name="name" tabindex="1" required="required">
				</div>
				<div class="form-row">
					<label for="username">Password<span class="required" >*</span></label> <input type="password" 
						id="password" name="password" tabindex="1" required="required">
				</div>

			</div>

				 <p>New User?  <a href="/register">Register here</a></p>&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="submit">Login</button>
		
		</form:form>
	</div>
	<script src="scripts.js"></script>
</body>
</html>