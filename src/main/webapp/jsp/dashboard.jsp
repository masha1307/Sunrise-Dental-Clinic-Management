<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Dashboard - Dental Clinic</title>


    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">


</head>


<body>


<!-- Header -->

<div class="dashboard-header">

    <h1>
        Dental Clinic Management System
    </h1>

    <p>
        Welcome to the Dashboard
    </p>

</div>



<!-- Navigation -->

<div class="dashboard-container">


    <div class="dashboard-card">


        <h2>
            Appointment Management
        </h2>


        <p>
            Register, update and delete patient appointments.
        </p>


        <a href="${pageContext.request.contextPath}/jsp/appointment.jsp"
           class="dashboard-btn">

            Manage Appointment

        </a>


    </div>





    <div class="dashboard-card">


        <h2>
            Search Appointment
        </h2>


        <p>
            Search patient appointments by ID, name or date.
        </p>


        <a href="${pageContext.request.contextPath}/jsp/searchAppointment.jsp"
           class="dashboard-btn">

            Search Appointment

        </a>


    </div>






    <div class="dashboard-card">


        <h2>
            Billing
        </h2>


        <p>
            Generate and view patient bills.
        </p>


        <a href="${pageContext.request.contextPath}/jsp/bill.jsp"
           class="dashboard-btn">

            Manage Bills

        </a>


    </div>






    <div class="dashboard-card">


        <h2>
            Help
        </h2>


        <p>
            View system usage instructions.
        </p>


        <a href="${pageContext.request.contextPath}/jsp/help.jsp"
           class="dashboard-btn">

            User Guide

        </a>


    </div>



</div>





<!-- Logout -->

<div class="logout-section">


    <a href="${pageContext.request.contextPath}/logout"
       class="logout-btn">

        Logout

    </a>


</div>




</body>

</html>