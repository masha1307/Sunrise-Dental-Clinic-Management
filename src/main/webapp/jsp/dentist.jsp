<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Dentist Management</title>


<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">


</head>


<body>


<div class="page-header">

<h1>
Dentist Management
</h1>

</div>



<div class="appointment-container">


<%
String successMessage =
(String)request.getAttribute("successMessage");

String errorMessage =
(String)request.getAttribute("errorMessage");


if(successMessage != null){
%>

<p class="success-message">
<%=successMessage%>
</p>

<%
}


if(errorMessage != null){
%>


<p class="error-message">
<%=errorMessage%>
</p>


<%
}
%>




<form action="${pageContext.request.contextPath}/dentist"
method="post">

<input type="hidden" name="action" value="add">



<div class="form-group">

<label>
Dentist Name
</label>


<input type="text"
name="dentistName"
placeholder="Enter dentist name"
required>


</div>



<div class="form-group">

<label>
Specialization
</label>


<input type="text"
name="specialization"
placeholder="Enter specialization"
required>


</div>



<div class="form-group">

<label>
Contact Number
</label>


<input type="text"
name="contactNumber"
placeholder="Enter contact number"
required>


</div>



<div class="form-group">

<label>
Email
</label>


<input type="email"
name="email"
placeholder="Enter email"
required>


</div>



<button type="submit"
class="btn">

Add Dentist

</button>



</form>


</div>

<div style="margin-top:12px;">
    <h3>Existing Dentists</h3>
    <table class="custom-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Contact</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="d" items="${dentists}">
                <tr>
                    <td>#${d.dentistId}</td>
                    <td>${d.dentistName}</td>
                    <td>${d.specialization}</td>
                    <td>${d.contactNumber}</td>
                    <td>${d.email}</td>
                </tr>
            </c:forEach>
        </tbody>
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
