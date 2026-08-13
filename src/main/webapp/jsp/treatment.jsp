<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Treatment Management</title>


<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">


</head>


<body>


<div class="page-header">

<h1>
Treatment Management
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




<form action="${pageContext.request.contextPath}/treatment"
method="post">

<input type="hidden" name="action" value="add">



<div class="form-group">

<label>
Treatment Name
</label>


<input type="text"
name="treatmentName"
placeholder="Enter treatment name"
required>


</div>



<button type="submit"
class="btn">

Add Treatment

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
