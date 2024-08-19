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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
 
</head>
<body>
	<div class="container">
		<h1>Add Reward Points</h1>

<form:form action="/calculateRewardPoints" method="post" modelAttribute="customer">
	 <form:input path="cust_id" type="hidden"/>

			<div class="personal-info">
				<div class="form-row">
					<label for="cust_name">Product Amount<span class="required" >*</span></label> <input type="text"
						id="amount" name="amount" tabindex="1" required="required"/>

				</div>

			</div>

				<div class="form-row" ><button type="submit">Calculate & Save</button></div>
				
		
		</form:form>
	</div>
	<script src="scripts.js"></script>
</body>
</html>