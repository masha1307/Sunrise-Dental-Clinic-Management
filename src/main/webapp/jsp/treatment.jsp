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

<div class="main-wrapper">
    <div class="page-container">

        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <!-- Add / Edit Treatment Form -->
        <div class="form-card">

            <div class="card-header-title">
                <h2>${not empty editTreatment ? "Edit Treatment" : "Add Treatment"}</h2>
            </div>

            <form action="${pageContext.request.contextPath}/treatmentAdmin" method="post">
                <input type="hidden" name="action" value="${not empty editTreatment ? 'update' : 'add'}">

                <c:if test="${not empty editTreatment}">
                    <input type="hidden" name="treatmentId" value="${editTreatment.treatmentId}">
                </c:if>

                <div class="form-grid">

                    <div class="form-group">
                        <label>Treatment Name</label>
                        <input type="text" name="treatmentName"
                               value="${not empty editTreatment ? editTreatment.treatmentName : ''}"
                               placeholder="Enter treatment name" required>
                    </div>

                    <div class="form-group">
                        <label>Price</label>
                        <input type="number" step="0.01" min="0" name="price"
                               value="${not empty editTreatment ? editTreatment.price : ''}"
                               placeholder="Enter price" required>
                    </div>

                    <div class="form-group">
                        <label>Duration (minutes)</label>
                        <input type="number" step="1" min="1" name="durationMinutes"
                               value="${not empty editTreatment ? editTreatment.durationMinutes : ''}"
                               placeholder="Enter duration in minutes" required>
                    </div>

                </div>

                <div style="display:flex; gap:12px; margin-top:8px;">
                    <button type="submit" class="btn" style="width:auto; padding:13px 28px;">
                        ${not empty editTreatment ? "Update Treatment" : "Add Treatment"}
                    </button>

                    <c:if test="${not empty editTreatment}">
                        <a href="${pageContext.request.contextPath}/treatmentAdmin"
                           class="back-btn" style="text-decoration:none; display:inline-flex; align-items:center;">
                            Cancel
                        </a>
                    </c:if>
                </div>
            </form>

        </div>

        <!-- Existing Treatments Table -->
        <div class="form-card" style="margin-top:28px;">

            <div class="card-header-title">
                <h2>Existing Treatments</h2>
                <span class="badge badge-info">${treatments.size()} Total</span>
            </div>

            <div class="table-container">
                <table class="custom-table">
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
                        <c:choose>
                            <c:when test="${empty treatments}">
                                <tr>
                                    <td colspan="5" class="empty-state">No treatments added yet.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
<c:forEach var="t" items="${treatments}">
    <tr>
        <td>#${t.treatmentId}</td>
        <td>${t.treatmentName}</td>
        <td>
            <c:choose>
                <c:when test="${not empty t.price}">Rs. ${t.price}</c:when>
                <c:otherwise><span class="badge badge-danger">Not set</span></c:otherwise>
            </c:choose>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty t.durationMinutes}">${t.durationMinutes} min</c:when>
                <c:otherwise><span class="badge badge-danger">Not set</span></c:otherwise>
            </c:choose>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/treatmentAdmin?action=edit&treatmentId=${t.treatmentId}"
               class="btn btn-sm"
               style="width:auto; padding:6px 12px; font-size:12px; display:inline-block; text-decoration:none; text-align:center;">
                Edit
            </a>

            <form action="${pageContext.request.contextPath}/treatmentAdmin" method="post"
                  style="display:inline; margin-left:8px;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="treatmentId" value="${t.treatmentId}">
                <button type="submit" class="btn btn-sm"
                        style="width:auto; padding:6px 12px; font-size:12px; background:var(--danger); box-shadow:none;">
                    Delete
                </button>
            </form>
        </td>
    </tr>
</c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

        </div>

        <div class="back-section">
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">
                &larr; Back to Dashboard
            </a>
        </div>

    </div>
</div>

</body>
</html>