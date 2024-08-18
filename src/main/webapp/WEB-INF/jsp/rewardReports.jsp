<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Dashboard</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js" ></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.js" ></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/custDash.css">

</head>
<body>

<div class="">
<br>
	<h2>Reward Points Reports</h2>
	
	<form:form>
		<table class="table table-bordered">
		<tr>
			<th>Customer Id</th>
			<th>Amount</th>
			<th>Reward Points</th>
			<th>Month</th>
			<th>Year</th>
		</tr>
		 <c:forEach var="customer" items="${rewards}">
		
			<tr>
				<td>${customer.cust_id}</td>
				<td>${customer.amount}</td>
				<td>${customer.points}</td>
				<td>${customer.month}</td>
				<td>${customer.year}</td>
    			
			</tr>
		</c:forEach>
		</table>
	</form:form>
		<button type="button" class="btn-primary1"><a href="/register">Add New Customer</a></button>
		<button type="button" class="btn-primary1"><a href="/downloadRewardsReport">Download Reports</a></button>
		<button type="button" class="btn-primary1"><a href="/customerdashboard">Customer Dashboard</a></button>
		<br>

</body>
</html>