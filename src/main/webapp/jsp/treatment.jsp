<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Treatment Management - DentalCare System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=3">
</head>
<body>

<!-- Top Navigation Bar -->
<header class="app-navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">
        <div class="brand-icon">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <path d="M12 2C8 2 4 5 4 10c0 4.5 2 9 4.5 12 .8.9 2 1 2.5 0l1-2 1 2c.5 1 1.7.9 2.5 0C18 19 20 14.5 20 10c0-5-4-8-8-8z"/>
            </svg>
        </div>
        <div class="brand-title">DentalCare <span>System</span></div>
    </a>

    <%
        String role = (String) session.getAttribute("role");
    %>
    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-item">Dashboard</a>
        <a href="${pageContext.request.contextPath}/jsp/searchAppointment.jsp" class="nav-item">Search</a>
        <% if ("admin".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/dentist" class="nav-item">Dentist Mgmt</a>
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item active">Treatment Mgmt</a>
            <a href="${pageContext.request.contextPath}/receptionist" class="nav-item">Receptionist Mgmt</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="nav-item">Help Guide</a>
    </nav>

    <div class="nav-user">
        <div class="user-badge">
            <div class="user-avatar">
                <%
                    String uname = (String) session.getAttribute("username");
                    char initial = (uname != null && !uname.isEmpty()) ? Character.toUpperCase(uname.charAt(0)) : 'A';
                %>
                <%= initial %>
            </div>
            <span><%= (uname != null) ? uname : "Admin" %></span>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                <polyline points="16 17 21 12 16 7"></polyline>
                <line x1="21" y1="12" x2="9" y2="12"></line>
            </svg>
            <span>Logout</span>
        </a>
    </div>
</header>

<main class="main-wrapper">
    <div class="page-container">

        <!-- Top Header with Back to Dashboard Button on the Right Side -->
        <div class="page-top-header">
            <div class="header-title-area">
                <h1>
                    <span class="card-icon-wrapper" style="width:38px; height:38px; margin-bottom:0; background: var(--primary-light); color: var(--primary);">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                            <polyline points="14 2 14 8 20 8"></polyline>
                            <line x1="16" y1="13" x2="8" y2="13"></line>
                            <line x1="16" y1="17" x2="8" y2="17"></line>
                        </svg>
                    </span>
                    Treatment Management
                </h1>
                <p>Configure clinic dental procedures, pricing structure, and duration slots.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                <span>Back to Dashboard</span>
            </a>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="success-message">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                    <polyline points="22 4 12 14.01 9 11.01"></polyline>
                </svg>
                <span>${successMessage}</span>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="12" y1="8" x2="12" y2="12"></line>
                    <line x1="12" y1="16" x2="12.01" y2="16"></line>
                </svg>
                <span>${errorMessage}</span>
            </div>
        </c:if>

        <!-- Add / Edit Treatment Form -->
        <div class="form-card">
            <div class="card-header-title">
                <h2>
                    <c:choose>
                        <c:when test="${not empty editTreatment}">
                            <span>Edit Treatment (#${editTreatment.treatmentId})</span>
                        </c:when>
                        <c:otherwise>
                            <span>Add New Treatment</span>
                        </c:otherwise>
                    </c:choose>
                </h2>
                <span class="badge ${not empty editTreatment ? 'badge-info' : 'badge-success'}">
                    ${not empty editTreatment ? 'Editing Mode' : 'New Treatment'}
                </span>
            </div>

            <form action="${pageContext.request.contextPath}/treatmentAdmin" method="post">
                <input type="hidden" name="action" value="${not empty editTreatment ? 'update' : 'add'}">

                <c:if test="${not empty editTreatment}">
                    <input type="hidden" name="treatmentId" value="${editTreatment.treatmentId}">
                </c:if>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="treatmentName">Treatment Name *</label>
                        <input type="text" id="treatmentName" name="treatmentName"
                               value="${not empty editTreatment ? editTreatment.treatmentName : ''}"
                               placeholder="e.g. Root Canal, Teeth Cleaning" required>
                    </div>

                    <div class="form-group">
                        <label for="price">Price (Rs.) *</label>
                        <input type="number" step="0.01" min="0" id="price" name="price"
                               value="${not empty editTreatment ? editTreatment.price : ''}"
                               placeholder="e.g. 5000.00" required>
                    </div>

                    <div class="form-group">
                        <label for="durationMinutes">Duration (Minutes) *</label>
                        <input type="number" step="1" min="1" id="durationMinutes" name="durationMinutes"
                               value="${not empty editTreatment ? editTreatment.durationMinutes : ''}"
                               placeholder="e.g. 45" required>
                    </div>
                </div>

                <div style="display:flex; gap:12px; margin-top:20px; align-items:center;">
                    <button type="submit" class="btn" style="width:auto; padding:12px 28px;">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
                            <polyline points="17 21 17 13 7 13 7 21"></polyline>
                            <polyline points="7 3 7 8 15 8"></polyline>
                        </svg>
                        <span>${not empty editTreatment ? "Update Treatment" : "Add Treatment"}</span>
                    </button>

                    <c:if test="${not empty editTreatment}">
                        <a href="${pageContext.request.contextPath}/treatmentAdmin" class="back-btn"
                           style="text-decoration:none; display:inline-flex; align-items:center;">
                            Cancel
                        </a>
                    </c:if>
                </div>
            </form>
        </div>

        <!-- Existing Treatments Table -->
        <div class="form-card" style="margin-top:28px;">
            <div class="card-header-title">
                <h2>
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="var(--primary)" stroke-width="2">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                    </svg>
                    Existing Treatments
                </h2>
                <span class="badge badge-info">${treatments.size()} Total</span>
            </div>

            <div class="table-container">
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Treatment Name</th>
                            <th>Price</th>
                            <th>Duration</th>
                            <th style="text-align: right;">Actions</th>
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
                                        <td><strong>#${t.treatmentId}</strong></td>
                                        <td style="font-weight: 600; color: var(--text-main);">${t.treatmentName}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty t.price}">
                                                    <strong>Rs. ${t.price}</strong>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-danger">Not set</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty t.durationMinutes}">
                                                    <span class="badge badge-info">${t.durationMinutes} min</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-danger">Not set</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="text-align: right;">
                                            <div style="display:inline-flex; align-items:center; gap:8px;">
                                                <a href="${pageContext.request.contextPath}/treatmentAdmin?action=edit&treatmentId=${t.treatmentId}"
                                                   class="btn btn-sm"
                                                   style="width:auto; padding:6px 14px; font-size:12px; text-decoration:none;">
                                                    Edit
                                                </a>

                                                <form action="${pageContext.request.contextPath}/treatmentAdmin" method="post"
                                                      style="display:inline; margin:0;"
                                                      onsubmit="return confirm('Are you sure you want to delete treatment \'${t.treatmentName}\'?');">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="treatmentId" value="${t.treatmentId}">
                                                    <button type="submit" class="btn btn-sm"
                                                            style="width:auto; padding:6px 14px; font-size:12px; background:var(--danger); box-shadow:none;">
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

    </div>
</main>

</body>
</html>