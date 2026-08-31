<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dentist Management</title>
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

        <!-- Add / Edit Dentist Form -->
        <div class="form-card">

            <div class="card-header-title">
                <h2>
                    <span class="card-icon-wrapper" style="width:36px;height:36px;margin-bottom:0;">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 2c-2.5 0-4 1.5-4 4 0 1.2.3 2 .6 3 .3 1 .6 2 .6 4 0 2-.6 4-1.2 6.5-.2.9.5 1.5 1.2 1.5.9 0 1.3-.6 1.6-1.5.4-1.2.8-2.5 1.2-2.5s.8 1.3 1.2 2.5c.3.9.7 1.5 1.6 1.5.7 0 1.4-.6 1.2-1.5C15.6 17 15 15 15 13c0-2 .3-3 .6-4 .3-1 .6-1.8.6-3 0-2.5-1.5-4-4-4z"/>
                        </svg>
                    </span>
                    ${not empty editDentist ? "Edit Dentist" : "Add Dentist"}
                </h2>
            </div>

            <form action="${pageContext.request.contextPath}/dentist" method="post">
                <input type="hidden" name="action" value="${not empty editDentist ? 'update' : 'add'}">

                <c:if test="${not empty editDentist}">
                    <input type="hidden" name="dentistId" value="${editDentist.dentistId}">
                </c:if>

                <div class="form-grid">

                    <div class="form-group">
                        <label>Dentist Name</label>
                        <input type="text" name="dentistName"
                               value="${not empty editDentist ? editDentist.dentistName : ''}"
                               placeholder="Enter dentist name" required>
                    </div>

                    <div class="form-group">
                        <label>Specialization</label>
                        <input type="text" name="specialization"
                               value="${not empty editDentist ? editDentist.specialization : ''}"
                               placeholder="Enter specialization" required>
                    </div>

                    <div class="form-group">
                        <label>Contact Number</label>
                        <input type="text" name="contactNumber"
                               value="${not empty editDentist ? editDentist.contactNumber : ''}"
                               placeholder="Enter contact number" required>
                    </div>

                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email"
                               value="${not empty editDentist ? editDentist.email : ''}"
                               placeholder="Enter email" required>
                    </div>

                </div>

                <div style="display:flex; gap:12px; margin-top:8px;">
                    <button type="submit" class="btn" style="width:auto; padding:13px 28px;">
                        ${not empty editDentist ? "Update Dentist" : "Add Dentist"}
                    </button>

                    <c:if test="${not empty editDentist}">
                        <a href="${pageContext.request.contextPath}/dentist"
                           class="back-btn" style="text-decoration:none; display:inline-flex; align-items:center;">
                            Cancel
                        </a>
                    </c:if>
                </div>

            </form>

        </div>

        <!-- Existing Dentists Table -->
        <div class="form-card" style="margin-top:28px;">

            <div class="card-header-title">
                <h2>Existing Dentists</h2>
                <span class="badge badge-info">${dentists.size()} Total</span>
            </div>

            <div class="table-container">
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Specialization</th>
                            <th>Contact</th>
                            <th>Email</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty dentists}">
                                <tr>
                                    <td colspan="6" class="empty-state">
                                        No dentists added yet.
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="d" items="${dentists}">
                                    <tr>
                                        <td>#${d.dentistId}</td>
                                        <td>${d.dentistName}</td>
                                        <td>${d.specialization}</td>
                                        <td>${d.contactNumber}</td>
                                        <td>${d.email}</td>
                                        <td>
                                            <div style="display:flex; flex-wrap:nowrap; align-items:center; gap:8px;">
                                                <a href="${pageContext.request.contextPath}/dentist?action=edit&dentistId=${d.dentistId}"
                                                   class="btn btn-sm"
                                                   style="width:auto; padding:6px 12px; font-size:12px; white-space:nowrap; text-decoration:none; text-align:center;">
                                                    Edit
                                                </a>

                                                <form action="${pageContext.request.contextPath}/dentist" method="post"
                                                      style="display:inline; margin:0;">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="dentistId" value="${d.dentistId}">
                                                    <button type="submit" class="btn btn-sm"
                                                            style="width:auto; padding:6px 12px; font-size:12px; white-space:nowrap; background:var(--danger); box-shadow:none;">
                                                        Delete
                                                    </button>
                                                </form>
                                            </div>
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