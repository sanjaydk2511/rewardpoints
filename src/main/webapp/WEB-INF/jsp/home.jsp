<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home Page</title>

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
	<h2>Welcome to Home : Quick Links</h2><br>
	
		<button type="button" class="btn-primary1"><a href="/register">Add New Customer</a></button>
		<button type="button" class="btn-primary1"><a href="/customerdashboard">Customer Dashboard</a></button>
		<button type="button" class="btn-primary1"><a href="/allrewards">Reward Point Reports</a></button>
		<button type="button" class="btn-primary1"><a href="/downloadRewardsReport">Download Reward Reports</a></button>
		<button type="button" class="btn-primary1"><a href="/downloadCustomerReport">Download Customer Reports</a></button>
		
</body>
</html>