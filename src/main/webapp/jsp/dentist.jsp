<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dentist Management - DentalCare System</title>
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
            <a href="${pageContext.request.contextPath}/dentist" class="nav-item active">Dentist Mgmt</a>
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item">Treatment Mgmt</a>
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
                            <path d="M12 2c-2.5 0-4 1.5-4 4 0 1.2.3 2 .6 3 .3 1 .6 2 .6 4 0 2-.6 4-1.2 6.5-.2.9.5 1.5 1.2 1.5.9 0 1.3-.6 1.6-1.5.4-1.2.8-2.5 1.2-2.5s.8 1.3 1.2 2.5c.3.9.7 1.5 1.6 1.5.7 0 1.4-.6 1.2-1.5C15.6 17 15 15 15 13c0-2 .3-3 .6-4 .3-1 .6-1.8.6-3 0-2.5-1.5-4-4-4z" />
                        </svg>
                    </span>
                    Dentist Management
                </h1>
                <p>Add, update, and manage clinic dental practitioners and specialist profiles.</p>
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

        <!-- Add / Edit Dentist Form -->
        <div class="form-card">
            <div class="card-header-title">
                <h2>
                    <c:choose>
                        <c:when test="${not empty editDentist}">
                            <span>Edit Dentist (#${editDentist.dentistId})</span>
                        </c:when>
                        <c:otherwise>
                            <span>Add New Dentist</span>
                        </c:otherwise>
                    </c:choose>
                </h2>
                <span class="badge ${not empty editDentist ? 'badge-info' : 'badge-success'}">
                    ${not empty editDentist ? 'Editing Mode' : 'New Doctor'}
                </span>
            </div>

            <form action="${pageContext.request.contextPath}/dentist" method="post">
                <input type="hidden" name="action" value="${not empty editDentist ? 'update' : 'add'}">

                <c:if test="${not empty editDentist}">
                    <input type="hidden" name="dentistId" value="${editDentist.dentistId}">
                </c:if>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="dentistName">Dentist Name *</label>
                        <input type="text" id="dentistName" name="dentistName"
                               value="${not empty editDentist ? editDentist.dentistName : ''}"
                               placeholder="e.g. Dr. John Smith" required>
                    </div>

                    <div class="form-group">
                        <label for="specialization">Specialization *</label>
                        <input type="text" id="specialization" name="specialization"
                               value="${not empty editDentist ? editDentist.specialization : ''}"
                               placeholder="e.g. Orthodontist, General Dentist" required>
                    </div>

                    <div class="form-group">
                        <label for="contactNumber">Contact Number *</label>
                        <input type="text" id="contactNumber" name="contactNumber"
                               value="${not empty editDentist ? editDentist.contactNumber : ''}"
                               placeholder="e.g. 555-0101" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email Address *</label>
                        <input type="email" id="email" name="email"
                               value="${not empty editDentist ? editDentist.email : ''}"
                               placeholder="e.g. doctor@clinic.com" required>
                    </div>
                </div>

                <div style="display:flex; gap:12px; margin-top:20px; align-items:center;">
                    <button type="submit" class="btn" style="width:auto; padding:12px 28px;">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
                            <polyline points="17 21 17 13 7 13 7 21"></polyline>
                            <polyline points="7 3 7 8 15 8"></polyline>
                        </svg>
                        <span>${not empty editDentist ? "Update Dentist" : "Add Dentist"}</span>
                    </button>

                    <c:if test="${not empty editDentist}">
                        <a href="${pageContext.request.contextPath}/dentist" class="back-btn"
                           style="text-decoration:none; display:inline-flex; align-items:center;">
                            Cancel
                        </a>
                    </c:if>
                </div>
            </form>
        </div>

        <!-- Existing Dentists Table -->
        <div class="form-card" style="margin-top:28px;">
            <div class="card-header-title">
                <h2>
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="var(--primary)" stroke-width="2">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                    </svg>
                    Existing Dentists
                </h2>
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
                            <th style="text-align: right;">Actions</th>
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
                                        <td><strong>#${d.dentistId}</strong></td>
                                        <td style="font-weight:600; color:var(--text-main);">${d.dentistName}</td>
                                        <td><span class="badge badge-info">${d.specialization}</span></td>
                                        <td>${d.contactNumber}</td>
                                        <td>${d.email}</td>
                                        <td style="text-align: right;">
                                            <div style="display:inline-flex; align-items:center; gap:8px;">
                                                <a href="${pageContext.request.contextPath}/dentist?action=edit&dentistId=${d.dentistId}"
                                                   class="btn btn-sm"
                                                   style="width:auto; padding:6px 14px; font-size:12px; text-decoration:none;">
                                                    Edit
                                                </a>

                                                <form action="${pageContext.request.contextPath}/dentist"
                                                      method="post" style="display:inline; margin:0;"
                                                      onsubmit="return confirm('Are you sure you want to delete dentist \'${d.dentistName}\'?');">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="dentistId" value="${d.dentistId}">
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