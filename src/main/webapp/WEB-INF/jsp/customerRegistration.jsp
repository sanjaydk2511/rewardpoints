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

<script type="text/javascript">
        function validatePasswords() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirm_password").value;
          
            if (password !== confirmPassword) {
                alert("Passwords do not match.");
                return false;
            }
            if (password.length < 8) {
                alert("Password must be at least 8 characters long.");
                return false;
            }
            return true;
        }
        
      
            function validateEmails() {
                var email = $("#email").val();
                //var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                var emailRegex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;    

                if (!emailRegex.test(email)) {
                    alert("Please enter a valid email address.");
                    return false;
                }
                return true;
            }
       
    </script>
    




</head>
<body>
	<div class="container">
		<h1>Customer Registration</h1>
		<form action="/custmerRegistrationSave" method="post" onsubmit="return validatePasswords()">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="personal-info">
				<div class="form-row">
					<label for="cust_name">Customer Name<span class="required" >*</span></label> <input type="text" 
						id="cust_name" name="cust_name" tabindex="1" required="required">
				</div>
				<div class="form-row">
					<label for="contact">Contact No.<span class="required" >*</span></label> <input type="text" maxlength="10"
						id="contact" name="contact" tabindex="1"  required="required">
				</div>
				<div class="form-row">
					<label for="email">Email-id<span class="required" >*</span></label> <input type="email" 
						id="email" name="email" tabindex="1" required="required">
				</div>
				<div class="form-row">
					<label for="user_name">User Name<span class="required" >*</span></label> <input type="text" 
						id="user_name" name="user_name" tabindex="1" required="required">
				</div>
				<div class="form-row">
					<label for="password">Enter Password<span class="required" >*</span></label> <input type="text" 
						id="password" name="password" tabindex="1" required="required">
				</div>
				<div class="form-row">
					<label for="password">Confirm Password<span class="required" >*</span></label> <input type="password" 
						id="confirm_password" name="confirm_password" tabindex="1" required="required">
				</div>

			</div>

				<div class="form-row" ><button type="submit">Register</button></div>
				
		
		</form>
	</div>
	<script src="scripts.js"></script>
</body>
</html>