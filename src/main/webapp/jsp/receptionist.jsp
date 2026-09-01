<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff & Receptionist Management - DentalCare System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=4">
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
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item">Treatment Mgmt</a>
            <a href="${pageContext.request.contextPath}/receptionist" class="nav-item active">Staff & Receptionists</a>
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
    <div class="page-container" style="max-width: 1000px;">

        <!-- Top Header with Back to Dashboard Button on the Right Side -->
        <div class="page-top-header">
            <div class="header-title-area">
                <h1>
                    <span class="card-icon-wrapper" style="width:38px; height:38px; margin-bottom:0; background: var(--accent-light); color: var(--accent);">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4z"></path>
                            <path d="M6 20v-1a4 4 0 0 1 4-4h4a4 4 0 0 1 4 4v1"></path>
                        </svg>
                    </span>
                    Staff & Receptionist Management
                </h1>
                <p>Manage separate database tables for Receptionists and System Administrators.</p>
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

        <!-- Add / Edit Staff Form -->
        <div class="form-card">
            <div class="card-header-title">
                <h2>
                    <c:choose>
                        <c:when test="${not empty editReceptionist}">
                            <span>Edit Receptionist (#${editReceptionist.receptionistId})</span>
                        </c:when>
                        <c:when test="${not empty editAdmin}">
                            <span>Edit Administrator (#${editAdmin.adminId})</span>
                        </c:when>
                        <c:otherwise>
                            <span>Add New Account</span>
                        </c:otherwise>
                    </c:choose>
                </h2>
                <span class="badge ${not empty editReceptionist or not empty editAdmin ? 'badge-info' : 'badge-success'}">
                    ${not empty editReceptionist or not empty editAdmin ? 'Editing Mode' : 'New Account'}
                </span>
            </div>

            <form action="${pageContext.request.contextPath}/receptionist" method="post">
                <input type="hidden" name="action" value="${not empty editReceptionist or not empty editAdmin ? 'update' : 'add'}">

                <c:if test="${not empty editReceptionist}">
                    <input type="hidden" name="id" value="${editReceptionist.receptionistId}">
                    <input type="hidden" name="accountType" value="receptionist">
                </c:if>
                <c:if test="${not empty editAdmin}">
                    <input type="hidden" name="id" value="${editAdmin.adminId}">
                    <input type="hidden" name="accountType" value="admin">
                </c:if>

                <div class="form-grid">
                    <c:if test="${empty editReceptionist and empty editAdmin}">
                        <div class="form-group">
                            <label for="accountTypeSelect">Account Type *</label>
                            <select id="accountTypeSelect" name="accountType" onchange="toggleAccountFields()" required>
                                <option value="receptionist" selected>Receptionist (receptionists table)</option>
                                <option value="admin">Administrator (admins table)</option>
                            </select>
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label for="username">Username *</label>
                        <div class="input-wrapper">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                                <circle cx="12" cy="7" r="4"></circle>
                            </svg>
                            <input type="text" id="username" name="username"
                                   value="${not empty editReceptionist ? editReceptionist.username : (not empty editAdmin ? editAdmin.username : '')}"
                                   placeholder="Enter login username" required autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">
                            Password ${empty editReceptionist and empty editAdmin ? '*' : '<span style="font-weight:normal; text-transform:none; color:var(--text-muted);">(Leave blank to keep unchanged)</span>'}
                        </label>
                        <div class="input-wrapper">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                                <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                            </svg>
                            <input type="password" id="password" name="password"
                                   placeholder="${not empty editReceptionist or not empty editAdmin ? 'Enter new password if changing' : 'Enter account password'}"
                                   ${empty editReceptionist and empty editAdmin ? 'required' : ''} autocomplete="new-password">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name">Full Name</label>
                        <input type="text" id="name" name="name"
                               value="${not empty editReceptionist ? editReceptionist.name : (not empty editAdmin ? editAdmin.name : '')}"
                               placeholder="e.g. Emily Davis">
                    </div>

                    <div class="form-group" id="contactGroup" style="${not empty editAdmin ? 'display:none;' : ''}">
                        <label for="contactNumber">Contact Number</label>
                        <input type="text" id="contactNumber" name="contactNumber"
                               value="${not empty editReceptionist ? editReceptionist.contactNumber : ''}"
                               placeholder="e.g. 555-0201">
                    </div>

                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email"
                               value="${not empty editReceptionist ? editReceptionist.email : (not empty editAdmin ? editAdmin.email : '')}"
                               placeholder="e.g. staff@dentalclinic.com">
                    </div>
                </div>

                <div style="display:flex; gap:12px; margin-top:20px; align-items:center;">
                    <button type="submit" class="btn" style="width:auto; padding:12px 28px;">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
                            <polyline points="17 21 17 13 7 13 7 21"></polyline>
                            <polyline points="7 3 7 8 15 8"></polyline>
                        </svg>
                        <span>${not empty editReceptionist or not empty editAdmin ? "Update Account" : "Add Account"}</span>
                    </button>

                    <c:if test="${not empty editReceptionist or not empty editAdmin}">
                        <a href="${pageContext.request.contextPath}/receptionist" class="back-btn"
                           style="text-decoration:none; display:inline-flex; align-items:center;">
                            Cancel
                        </a>
                    </c:if>
                </div>
            </form>
        </div>

        <!-- ================= SEPARATE TABLE 1: RECEPTIONISTS TABLE ================= -->
        <div class="form-card" style="margin-top:28px;">
            <div class="card-header-title">
                <h2>
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="var(--accent)" stroke-width="2">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                    </svg>
                    Receptionists Table (`receptionists`)
                </h2>
                <span class="badge badge-info" style="background: var(--accent-light); color: var(--accent);">
                    ${receptionists.size()} Receptionists
                </span>
            </div>

            <div class="table-container" style="margin-top:0;">
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Full Name</th>
                            <th>Contact</th>
                            <th>Email</th>
                            <th style="text-align: right;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty receptionists}">
                                <tr>
                                    <td colspan="6" class="empty-state">
                                        No records found in the receptionists table.
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="r" items="${receptionists}">
                                    <tr>
                                        <td><strong>#${r.receptionistId}</strong></td>
                                        <td style="font-weight: 600; color: var(--text-main);">${r.username}</td>
                                        <td>${not empty r.name ? r.name : '<span style="color:var(--text-muted);">-</span>'}</td>
                                        <td>${not empty r.contactNumber ? r.contactNumber : '<span style="color:var(--text-muted);">-</span>'}</td>
                                        <td>${not empty r.email ? r.email : '<span style="color:var(--text-muted);">-</span>'}</td>
                                        <td style="text-align: right;">
                                            <div style="display:inline-flex; align-items:center; gap:8px;">
                                                <a href="${pageContext.request.contextPath}/receptionist?action=edit&type=receptionist&id=${r.receptionistId}"
                                                   class="btn btn-sm"
                                                   style="width:auto; padding:6px 14px; font-size:12px; text-decoration:none;">
                                                    Edit
                                                </a>

                                                <form action="${pageContext.request.contextPath}/receptionist"
                                                      method="post" style="display:inline; margin:0;"
                                                      onsubmit="return confirm('Delete receptionist \'${r.username}\'?');">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="accountType" value="receptionist">
                                                    <input type="hidden" name="id" value="${r.receptionistId}">
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

        <!-- ================= SEPARATE TABLE 2: ADMINS TABLE ================= -->
        <div class="form-card" style="margin-top:28px;">
            <div class="card-header-title">
                <h2>
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#4f46e5" stroke-width="2">
                        <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                    </svg>
                    Administrators Table (`admins`)
                </h2>
                <span class="badge badge-info" style="background: #e0e7ff; color: #4338ca;">
                    ${admins.size()} Admins
                </span>
            </div>

            <div class="table-container" style="margin-top:0;">
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th style="text-align: right;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty admins}">
                                <tr>
                                    <td colspan="5" class="empty-state">
                                        No administrator records found in the admins table.
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="a" items="${admins}">
                                    <tr>
                                        <td><strong>#${a.adminId}</strong></td>
                                        <td style="font-weight: 600; color: var(--text-main);">${a.username}</td>
                                        <td>${not empty a.name ? a.name : '<span style="color:var(--text-muted);">-</span>'}</td>
                                        <td>${not empty a.email ? a.email : '<span style="color:var(--text-muted);">-</span>'}</td>
                                        <td style="text-align: right;">
                                            <div style="display:inline-flex; align-items:center; gap:8px;">
                                                <a href="${pageContext.request.contextPath}/receptionist?action=edit&type=admin&id=${a.adminId}"
                                                   class="btn btn-sm"
                                                   style="width:auto; padding:6px 14px; font-size:12px; text-decoration:none;">
                                                    Edit
                                                </a>

                                                <form action="${pageContext.request.contextPath}/receptionist"
                                                      method="post" style="display:inline; margin:0;"
                                                      onsubmit="return confirm('Delete administrator \'${a.username}\'?');">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="accountType" value="admin">
                                                    <input type="hidden" name="id" value="${a.adminId}">
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

<script>
function toggleAccountFields() {
    var select = document.getElementById('accountTypeSelect');
    var contactGroup = document.getElementById('contactGroup');
    if (!select || !contactGroup) return;

    if (select.value === 'admin') {
        contactGroup.style.display = 'none';
    } else {
        contactGroup.style.display = 'block';
    }
}
</script>

</body>
</html>
