<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Appointment Management</title>


    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">


</head>


<body>


<!-- Header -->

<div class="page-header">

    <h1>
        Appointment Management
    </h1>

</div>



<div class="appointment-container">


    <!-- Display Messages -->

    <%
        String successMessage =
                (String) request.getAttribute("successMessage");


        String errorMessage =
                (String) request.getAttribute("errorMessage");


        if(successMessage != null) {
    %>

    <p class="success-message">
        <%= successMessage %>
    </p>


    <%
        }


        if(errorMessage != null) {
    %>

    <p class="error-message">
        <%= errorMessage %>
    </p>


    <%
        }
    %>




    <!-- Appointment Form -->

    <form action="${pageContext.request.contextPath}/appointment"
          method="post">



        <div class="form-group">


            <label>
                Action
            </label>


            <select name="action">

                <option value="add">
                    Add Appointment
                </option>


                <option value="update">
                    Update Appointment
                </option>


                <option value="delete">
                    Delete Appointment
                </option>


            </select>


        </div>




        <div class="form-group">


            <label>
                Appointment ID
            </label>


            <input type="text"
                   name="appointmentId"
                   placeholder="Enter appointment ID">


        </div>





        <div class="form-group">


            <label>
                Patient ID
            </label>


            <input type="text"
                   name="patientId"
                   placeholder="Enter patient ID">


        </div>






        <div class="form-group">


            <label>
                Dentist ID
            </label>


            <input type="text"
                   name="dentistId"
                   placeholder="Enter dentist ID">


        </div>







        <div class="form-group">


            <label>
                Treatment
            </label>


            <textarea name="treatment"
                      placeholder="Enter treatment details"></textarea>


        </div>







        <div class="form-group">


            <label>
                Appointment Date
            </label>


            <input type="date"
                   name="appointmentDate">


        </div>







        <div class="form-group">


            <label>
                Appointment Time
            </label>


            <input type="time"
                   name="appointmentTime">


        </div>







        <button type="submit"
                class="btn">


            Save Appointment


        </button>




    </form>


</div>





<!-- Navigation -->

<div class="back-section">


    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp"
       class="back-btn">

        Back to Dashboard

    </a>


</div>



</body>

</html>