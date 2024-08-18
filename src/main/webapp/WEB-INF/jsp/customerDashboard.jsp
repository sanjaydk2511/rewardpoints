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

<script>
        // JavaScript function to show the hidden input box
        function showInput() {
        	
            var inputBox = document.getElementById('amount');
            inputBox.style.display = 'block'; // Show the input box
            
            var inputBox = document.getElementById('rewardSave');
            inputBox.style.display = 'block'; // Show the input box
        }
    </script>

</head>
<body>

<div class="">
<br>
	<h2>Customer Dashboard</h2>
	
	<form:form>
		<table class="table table-bordered">
		<tr>
			<th>Customer Id</th>
			<th>Customer Name</th>
			<th>Contact</th>
			<th>Email</th>
			
			<th>Reward Points</th>
			<th>Edit</th>
			<th>Delete</th>
			
		</tr>
		 <c:forEach var="customer" items="${customers}">
		
			<tr>
				<td>${customer.cust_id}</td>
				<td>${customer.cust_name}</td>
				<td>${customer.contact}</td>
				<td>${customer.email}</td>
				
				<td><button type="button" class="btn btn-primary">
					<a href="/calculatePoints/${customer.cust_id}">Add Reward Points</a></button>
				</td>
				
				
				<td><button type="button" class="btn btn-primary">
					<a href="/editCustomer/${customer.cust_id}">Edit</a>
					</button>
				</td>

				<td><button type="button" class="btn btn-danger">
					<a href="/deleteCustomer/${customer.cust_id}">Delete</a>
					</button>
				</td>
				
    			
			</tr>
		</c:forEach>
		</table>
	</form:form>
		<button type="button" class="btn-primary1"><a href="/register">Add New Customer</a></button>
		<button type="button" class="btn-primary1"><a href="downloadCustomerReport">Download Customer Reports</a></button>
		<button type="button" class="btn-primary1"><a href="/allrewards">Reward Point Reports</a></button>
		

<%-- <form:form action="/calculatePoints" method="get">
	<form:input path="cust_id" type="hidden"/>
		<div class="input-container" style="float: right; margin-right: 40%">
        <input type="number" id="amount" placeholder="Enter Amount">
        <button type="submit" id="rewardSave">Save</button>
    </div>
    </form:form> --%>
</body>
</html>