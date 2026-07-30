<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Appointment" %>


<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Search Appointment</title>


    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">


</head>


<body>


<!-- Header -->

<div class="page-header">

    <h1>
        Search Appointment
    </h1>

</div>




<div class="search-container">



    <!-- Search Form -->

    <form action="${pageContext.request.contextPath}/searchAppointment"
          method="get">


        <div class="form-group">


            <label>
                Search Appointment
            </label>


            <input type="text"
                   name="keyword"
                   placeholder="Enter Appointment ID, Patient ID or Date"
                   required>


        </div>



        <button type="submit"
                class="btn">

            Search

        </button>


    </form>


</div>






<!-- Display Search Results -->


<div class="table-container">


    <table>


        <tr>

            <th>
                Appointment ID
            </th>


            <th>
                Patient ID
            </th>


            <th>
                Dentist ID
            </th>


            <th>
                Treatment
            </th>


            <th>
                Date
            </th>


            <th>
                Time
            </th>


        </tr>




        <%

            List<Appointment> appointments =
                    (List<Appointment>) request.getAttribute("appointments");


            if(appointments != null && !appointments.isEmpty()) {


                for(Appointment appointment : appointments) {

        %>


        <tr>


            <td>
                <%= appointment.getAppointmentId() %>
            </td>


            <td>
                <%= appointment.getPatientId() %>
            </td>


            <td>
                <%= appointment.getDentistId() %>
            </td>


            <td>
                <%= appointment.getTreatment() %>
            </td>


            <td>
                <%= appointment.getAppointmentDate() %>
            </td>


            <td>
                <%= appointment.getAppointmentTime() %>
            </td>


        </tr>


        <%

            }

        }

        else {

        %>


        <tr>

            <td colspan="6">

                No appointments found.

            </td>

        </tr>


        <%

            }

        %>


    </table>


</div>





<div class="back-section">


    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp"
       class="back-btn">

        Back to Dashboard

    </a>


</div>



</body>

</html>