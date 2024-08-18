<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Registration</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration.css">

</head>
<body>
	<div class="container">
		<h1>Update Customer</h1>

<form:form action="/editSaveCustomer" method="post" modelAttribute="customer" onsubmit="return validatePasswords()">
	<form:input path="cust_id" type="hidden"/>
			<div class="personal-info">
				<div class="form-row">
					<label for="cust_name">Customer Name<span class="required" >*</span></label> <form:input type="text" path="cust_name"
						id="cust_name" name="cust_name" tabindex="1" required="required"/>

				</div>
				<div class="form-row">
					<label for="contact">Contact No.<span class="required" >*</span></label> <form:input type="text" maxlength="10" path="contact"
						id="contact" name="contact" tabindex="1"  required="required"/>
				</div>
				<div class="form-row">
					<label for="email">Email-id<span class="required" >*</span></label> <form:input type="email" path="email"
						id="email" name="email" tabindex="1" required="required"/>
				</div>

			</div>

				<div class="form-row" ><button type="submit">Update</button></div>
				
		
		</form:form>
	</div>
	<script src="scripts.js"></script>
</body>
</html>