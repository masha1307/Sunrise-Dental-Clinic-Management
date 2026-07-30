<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="com.dentalclinic.model.Bill" %>


<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Generate Bill</title>


    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">


</head>


<body>


<!-- Header -->

<div class="page-header">

    <h1>
        Billing Management
    </h1>

</div>




<div class="bill-container">


    <!-- Display Error Message -->

    <%

        String errorMessage =
                (String) request.getAttribute("errorMessage");


        if(errorMessage != null){

    %>


    <div class="error-message">

        <%= errorMessage %>

    </div>


    <%

        }

    %>





    <!-- Bill Form -->

    <form action="${pageContext.request.contextPath}/bill"
          method="post">



        <div class="form-group">


            <label>
                Appointment ID
            </label>


            <input type="text"
                   name="appointmentId"
                   placeholder="Enter appointment ID"
                   required>


        </div>





        <div class="form-group">


            <label>
                Consultation Fee
            </label>


            <input type="text"
                   name="consultationFee"
                   placeholder="Enter consultation fee"
                   required>


        </div>





        <div class="form-group">


            <label>
                Treatment Fee
            </label>


            <input type="text"
                   name="treatmentFee"
                   placeholder="Enter treatment fee"
                   required>


        </div>






        <div class="form-group">


            <label>
                Discount
            </label>


            <input type="text"
                   name="discount"
                   placeholder="Enter discount amount">


        </div>






        <button type="submit"
                class="btn">

            Generate Bill

        </button>



    </form>


</div>







<!-- Display Bill Result -->

<%

    Bill bill = (Bill) request.getAttribute("bill");


    if(bill != null){

%>



<div class="bill-result">


    <h2>
        Bill Details
    </h2>



    <table>


        <tr>

            <th>
                Appointment ID
            </th>

            <td>
                <%= bill.getAppointmentId() %>
            </td>

        </tr>



        <tr>

            <th>
                Consultation Fee
            </th>

            <td>
                <%= bill.getConsultationFee() %>
            </td>

        </tr>




        <tr>

            <th>
                Treatment Fee
            </th>

            <td>
                <%= bill.getTreatmentFee() %>
            </td>

        </tr>





        <tr>

            <th>
                Discount
            </th>

            <td>
                <%= bill.getDiscount() %>
            </td>

        </tr>





        <tr>

            <th>
                Total Amount
            </th>

            <td>
                <%= bill.getTotalAmount() %>
            </td>

        </tr>



    </table>


</div>



<%

    }

%>





<div class="back-section">


    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp"
       class="back-btn">

        Back to Dashboard

    </a>


</div>



</body>

</html>