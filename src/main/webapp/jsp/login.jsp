<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Dental Clinic Login</title>

    <!-- Connect CSS file -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>


<body>


<div class="login-container">


    <div class="login-card">


        <h1>Dental Clinic</h1>

        <h2>Management System</h2>


        <!-- Display Login Error Message -->

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");

            if(errorMessage != null) {
        %>

        <div class="error-message">
            <%= errorMessage %>
        </div>

        <%
            }
        %>



        <!-- Login Form -->

        <form action="${pageContext.request.contextPath}/login"
              method="post">


            <div class="input-group">


                <label for="username">
                    Username
                </label>


                <input type="text"
                       id="username"
                       name="username"
                       placeholder="Enter username"
                       required>


            </div>



            <div class="input-group">


                <label for="password">
                    Password
                </label>


                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Enter password"
                       required>


            </div>



            <button type="submit">
                Login
            </button>


        </form>


    </div>


</div>


</body>

</html>