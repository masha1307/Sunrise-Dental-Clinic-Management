<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Patient Management</title>


<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">


</head>


<body>


<div class="page-header">

<h1>
Patient Management
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




<form action="${pageContext.request.contextPath}/patient"
method="post">

<input type="hidden" name="action" value="add">



<div class="form-group">

<label>
Patient Name
</label>


<input type="text"
name="patientName"
placeholder="Enter patient name"
required>


</div>



<div class="form-group">

<label>
Age
</label>


<input type="number"
name="age"
placeholder="Enter age"
required>


</div>



<div class="form-group">

<label>
Gender
</label>


<select name="gender" required>


<option value="">
-- Select Gender --
</option>


<option value="Male">
Male
</option>


<option value="Female">
Female
</option>


<option value="Other">
Other
</option>


</select>


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



<div class="form-group">

<label>
Address
</label>


<input type="text"
name="address"
placeholder="Enter address"
required>


</div>



<button type="submit"
class="btn">

Add Patient

</button>



</form>


</div>




<div class="back-section">


<a href="${pageContext.request.contextPath}/jsp/dashboard.jsp"
class="back-btn">

Back to Dashboard

</a>


</div>



</body>

</html>
