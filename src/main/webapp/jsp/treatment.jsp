<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.dentalclinic.model.Treatment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Treatment Management</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-header"><h1>Treatment Management</h1></div>
<div class="appointment-container">

<%
    // debug: print how many treatments were loaded (null => servlet not used)
    Object _tObj = request.getAttribute("treatments");
    int _tCount = -1;
    if (_tObj instanceof java.util.List) { _tCount = ((java.util.List)_tObj).size(); }
%>
<%-- 
<div style="margin-bottom:8px; color: #666;">
<strong>Debug:</strong> treatments attribute present? <%= (_tObj==null)?"no":"yes" %> | count = <%= _tCount %>
</div>
--%>

<c:if test="${not empty successMessage}">
    <div class="success-message">${successMessage}</div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="error-message">${errorMessage}</div>
</c:if>

<div style="display:flex; gap:24px; align-items:flex-start;">
    <!-- Form -->
    <div style="flex:1;">
        <form id="treatmentForm" action="${pageContext.request.contextPath}/treatmentAdmin" method="post">
            <input type="hidden" name="action" id="formAction" value="add">
            <input type="hidden" name="treatmentId" id="treatmentId">
            <div class="form-group">
                <label>Treatment Name</label>
                <input type="text" id="treatmentName" name="treatmentName" placeholder="Enter treatment name" required>
            </div>
            <div class="form-group">
                <label>Price</label>
                <input type="number" step="0.01" id="price" name="price" placeholder="0.00">
            </div>
            <div class="form-group">
                <label>Duration (minutes)</label>
                <input type="number" id="duration" name="duration" placeholder="30">
            </div>
            <button type="submit" class="btn" id="submitBtn">Add Treatment</button>
            <button type="button" onclick="resetForm()" style="margin-left:8px;">Reset</button>
        </form>
    </div>

    <!-- List -->
    <div style="flex:2;">
        <h3>Existing Treatments</h3>
        <table class="data-table" style="width:100%;">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Duration</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="t" items="${treatments}">
                    <tr>
                        <td>${t.treatmentId}</td>
                        <td>${t.treatmentName}</td>
                        <td>${t.price}</td>
                        <td>${t.durationMinutes}</td>
                        <td>
                            <button type="button" onclick="editTreatment(${t.treatmentId}, '${fn:escapeXml(t.treatmentName)}', '${t.price}', '${t.durationMinutes}')">Edit</button>
                            <form action="${pageContext.request.contextPath}/treatmentAdmin" method="post" style="display:inline;" onsubmit="return confirm('Delete this treatment?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="treatmentId" value="${t.treatmentId}">
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</div>

<script>
function editTreatment(id, name, price, duration) {
    document.getElementById('formAction').value = 'update';
    document.getElementById('treatmentId').value = id;
    document.getElementById('treatmentName').value = name;
    document.getElementById('price').value = price;
    document.getElementById('duration').value = duration;
    document.getElementById('submitBtn').textContent = 'Update Treatment';
}
function resetForm(){
    document.getElementById('formAction').value='add';
    document.getElementById('treatmentId').value='';
    document.getElementById('treatmentName').value='';
    document.getElementById('price').value='';
    document.getElementById('duration').value='';
    document.getElementById('submitBtn').textContent='Add Treatment';
}
</script>

</body>
</html>


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




<form action="${pageContext.request.contextPath}/treatmentAdmin"
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
