<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Auth guard
    String sessionUser = (String) session.getAttribute("username");
    if (sessionUser == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    String role = (String) session.getAttribute("role");
    char initial = (sessionUser != null && !sessionUser.isEmpty())
                   ? Character.toUpperCase(sessionUser.charAt(0)) : 'U';
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Patients - DentalCare System</title>
    <meta name="description" content="Register, update, search and manage patient profiles in the Sunrise Dental Clinic system.">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=4">
    <style>
        /* ---- Search bar ---- */
        .search-bar-wrapper {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 16px;
        }
        .search-bar-wrapper .input-wrapper {
            flex: 1;
        }
        .patient-count-badge {
            font-size: 13px;
            font-weight: 600;
            color: var(--text-muted);
            white-space: nowrap;
        }
        /* ---- Gender badge ---- */
        .gender-badge {
            display: inline-flex;
            align-items: center;
            gap: 4px;
            padding: 2px 10px;
            border-radius: 999px;
            font-size: 12px;
            font-weight: 600;
        }
        .gender-male   { background: #e0f2fe; color: #0284c7; }
        .gender-female { background: #fce7f3; color: #be185d; }
        .gender-other  { background: #f3e8ff; color: #7c3aed; }
        /* ---- Empty state ---- */
        .empty-state-full {
            text-align: center;
            padding: 52px 24px;
            color: var(--text-muted);
        }
        .empty-state-full svg { opacity: 0.25; margin-bottom: 12px; }
        .empty-state-full p   { font-size: 15px; }
        /* ---- row highlight on hover ---- */
        tbody tr { transition: background 0.15s; }
        tbody tr:hover { background: var(--bg); }
        /* ---- no-results row ---- */
        #noResults { display: none; }
    </style>
</head>
<body>

<!-- ============================================================ NAVBAR -->
<header class="app-navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">
        <div class="brand-icon">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <path d="M12 2C8 2 4 5 4 10c0 4.5 2 9 4.5 12 .8.9 2 1 2.5 0l1-2 1 2c.5 1 1.7.9 2.5 0C18 19 20 14.5 20 10c0-5-4-8-8-8z"/>
            </svg>
        </div>
        <div class="brand-title">DentalCare <span>System</span></div>
    </a>

    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-item">Dashboard</a>
        <a href="${pageContext.request.contextPath}/jsp/searchAppointment.jsp" class="nav-item">Search</a>
        <% if ("receptionist".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/appointment" class="nav-item">Appointments</a>
            <a href="${pageContext.request.contextPath}/patient" class="nav-item active">Patients</a>
            <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item">Billing</a>
            <a href="${pageContext.request.contextPath}/cancellations" class="nav-item">Cancellations</a>
        <% } %>
        <% if ("admin".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/dentist" class="nav-item">Dentist Mgmt</a>
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item">Treatment Mgmt</a>
            <a href="${pageContext.request.contextPath}/receptionist" class="nav-item">Receptionist Mgmt</a>
            <a href="${pageContext.request.contextPath}/patient" class="nav-item active">Patient Mgmt</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="nav-item">Help Guide</a>
    </nav>

    <div class="nav-user">
        <div class="user-badge">
            <div class="user-avatar"><%= initial %></div>
            <span><%= sessionUser %></span>
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

<!-- ============================================================ MAIN -->
<main class="main-wrapper">
    <div class="page-container">

        <!-- Page Header -->
        <div class="page-top-header">
            <div class="header-title-area">
                <h1>
                    <span class="card-icon-wrapper" style="width:38px; height:38px; margin-bottom:0; background: var(--primary-light); color: var(--primary);">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                            <circle cx="12" cy="7" r="4"></circle>
                        </svg>
                    </span>
                    Manage Patients
                </h1>
                <p>Register new patients, update their profiles, or remove records from the system.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                <span>Back to Dashboard</span>
            </a>
        </div>

        <!-- Flash Messages -->
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

        <!-- ====================================================== ADD / EDIT FORM -->
        <div class="form-card">
            <div class="card-header-title">
                <h2>
                    <c:choose>
                        <c:when test="${not empty editPatient}">
                            <span>Edit Patient Profile (#${editPatient.patientId})</span>
                        </c:when>
                        <c:otherwise>
                            <span>Register New Patient</span>
                        </c:otherwise>
                    </c:choose>
                </h2>
                <span class="badge ${not empty editPatient ? 'badge-info' : 'badge-success'}">
                    ${not empty editPatient ? 'Editing Mode' : 'New Registration'}
                </span>
            </div>

            <form action="${pageContext.request.contextPath}/patient" method="post" id="patientForm">
                <input type="hidden" name="action" value="${not empty editPatient ? 'update' : 'add'}">
                <c:if test="${not empty editPatient}">
                    <input type="hidden" name="patientId" value="${editPatient.patientId}">
                </c:if>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="patientName">Full Name *</label>
                        <div class="input-wrapper">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                                <circle cx="12" cy="7" r="4"></circle>
                            </svg>
                            <input type="text" id="patientName" name="patientName"
                                   value="${not empty editPatient ? editPatient.patientName : ''}"
                                   placeholder="e.g. Maria Santos" required autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="age">Age *</label>
                        <div class="input-wrapper">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <circle cx="12" cy="12" r="10"></circle>
                                <polyline points="12 6 12 12 16 14"></polyline>
                            </svg>
                            <input type="number" id="age" name="age"
                                   value="${not empty editPatient ? editPatient.age : ''}"
                                   placeholder="Age" min="1" max="120" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="gender">Gender *</label>
                        <select id="gender" name="gender" required>
                            <option value="">-- Select Gender --</option>
                            <option value="Male"   ${editPatient.gender == 'Male'   ? 'selected' : ''}>Male</option>
                            <option value="Female" ${editPatient.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other"  ${editPatient.gender == 'Other'  ? 'selected' : ''}>Other</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="contactNumber">Contact Number *</label>
                        <div class="input-wrapper">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07A19.5 19.5 0 0 1 4.69 12 19.79 19.79 0 0 1 1.58 3.47 2 2 0 0 1 3.55 1.29h3a2 2 0 0 1 2 1.72c.127.96.361 1.903.7 2.81a2 2 0 0 1-.45 2.11L7.91 9a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45c.907.339 1.85.573 2.81.7A2 2 0 0 1 22 16.92z"></path>
                            </svg>
                            <input type="text" id="contactNumber" name="contactNumber"
                                   value="${not empty editPatient ? editPatient.contactNumber : ''}"
                                   placeholder="e.g. 09171234567" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email">Email Address *</label>
                        <div class="input-wrapper">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                                <polyline points="22,6 12,13 2,6"></polyline>
                            </svg>
                            <input type="email" id="email" name="email"
                                   value="${not empty editPatient ? editPatient.email : ''}"
                                   placeholder="e.g. patient@email.com" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="address">Home Address *</label>
                        <div class="input-wrapper">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                                <circle cx="12" cy="10" r="3"></circle>
                            </svg>
                            <input type="text" id="address" name="address"
                                   value="${not empty editPatient ? editPatient.address : ''}"
                                   placeholder="e.g. 123 Mahogany St, Quezon City" required>
                        </div>
                    </div>
                </div>

                <div style="display:flex; gap:12px; margin-top:22px; align-items:center; flex-wrap:wrap;">
                    <button type="submit" class="btn" id="submitBtn" style="width:auto; padding:12px 28px;">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
                            <polyline points="17 21 17 13 7 13 7 21"></polyline>
                            <polyline points="7 3 7 8 15 8"></polyline>
                        </svg>
                        <span>${not empty editPatient ? 'Update Patient' : 'Register Patient'}</span>
                    </button>

                    <c:if test="${not empty editPatient}">
                        <a href="${pageContext.request.contextPath}/patient"
                           class="back-btn" style="text-decoration:none; display:inline-flex; align-items:center;">
                            Cancel
                        </a>
                    </c:if>
                </div>
            </form>
        </div>

        <!-- ====================================================== PATIENTS TABLE -->
        <div class="form-card" style="margin-top:28px;">
            <div class="card-header-title">
                <h2>
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="var(--primary)" stroke-width="2">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                    </svg>
                    Patient Records
                </h2>
                <span class="badge badge-info" style="background: var(--primary-light); color: var(--primary);">
                    ${patients.size()} Patients
                </span>
            </div>

            <!-- Search bar -->
            <div class="search-bar-wrapper">
                <div class="input-wrapper" style="max-width:380px;">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="11" cy="11" r="8"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                    <input type="text" id="patientSearch" placeholder="Search by name, contact, or email…"
                           oninput="filterPatients()" autocomplete="off">
                </div>
                <span class="patient-count-badge" id="visibleCount">${patients.size()} shown</span>
            </div>

            <div class="table-container" style="margin-top:0; overflow-x:auto;">
                <table class="custom-table" id="patientTable" style="table-layout:fixed; width:100%; min-width:820px;">
                    <colgroup>
                        <col style="width:60px;">      <!-- ID -->
                        <col style="width:18%;">      <!-- Full Name -->
                        <col style="width:55px;">     <!-- Age -->
                        <col style="width:90px;">     <!-- Gender -->
                        <col style="width:130px;">    <!-- Contact -->
                        <col style="width:18%;">      <!-- Email -->
                        <col style="">               <!-- Address (fills remaining) -->
                        <col style="width:140px;">   <!-- Actions -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Full Name</th>
                            <th>Age</th>
                            <th>Gender</th>
                            <th>Contact</th>
                            <th>Email</th>
                            <th>Address</th>
                            <th style="text-align:right;">Actions</th>
                        </tr>
                    </thead>
                    <tbody id="patientTbody">
                        <c:choose>
                            <c:when test="${empty patients}">
                                <tr>
                                    <td colspan="8">
                                        <div class="empty-state-full">
                                            <svg width="56" height="56" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                                                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                                                <circle cx="12" cy="7" r="4"></circle>
                                            </svg>
                                            <p>No patients registered yet. Use the form above to add one.</p>
                                        </div>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="p" items="${patients}">
                                    <tr class="patient-row">
                                        <td><strong>#${p.patientId}</strong></td>
                                        <td style="font-weight:600; color:var(--text-main); white-space:nowrap; overflow:hidden; text-overflow:ellipsis;"
                                            title="${p.patientName}">${p.patientName}</td>
                                        <td>${p.age}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${p.gender == 'Male'}">
                                                    <span class="gender-badge gender-male">♂ Male</span>
                                                </c:when>
                                                <c:when test="${p.gender == 'Female'}">
                                                    <span class="gender-badge gender-female">♀ Female</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="gender-badge gender-other">⚧ Other</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="white-space:nowrap;">${not empty p.contactNumber ? p.contactNumber : '<span style="color:var(--text-muted);">—</span>'}</td>
                                        <td style="font-size:13px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;"
                                            title="${p.email}">${not empty p.email ? p.email : '<span style="color:var(--text-muted);">—</span>'}</td>
                                        <td style="font-size:13px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;"
                                            title="${p.address}">
                                            ${not empty p.address ? p.address : '<span style="color:var(--text-muted);">—</span>'}
                                        </td>
                                        <td style="text-align:right; white-space:nowrap;">
                                            <div style="display:inline-flex; align-items:center; gap:8px; justify-content:flex-end;">
                                                <!-- Edit -->
                                                <a href="${pageContext.request.contextPath}/patient?action=edit&id=${p.patientId}"
                                                   class="btn btn-sm"
                                                   style="width:auto; padding:5px 12px; font-size:12px; text-decoration:none; flex-shrink:0;">
                                                    Edit
                                                </a>
                                                <!-- Delete -->
                                                <form action="${pageContext.request.contextPath}/patient"
                                                      method="post" style="display:inline; margin:0;"
                                                      onsubmit="return confirm('Delete patient \'${p.patientName}\' (#${p.patientId})? This cannot be undone.');">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="patientId" value="${p.patientId}">
                                                    <button type="submit" class="btn btn-sm"
                                                            style="width:auto; padding:5px 12px; font-size:12px; background:var(--danger); box-shadow:none; flex-shrink:0;">
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

                <!-- No search results row -->
                <div id="noResults" style="display:none; text-align:center; padding:36px 24px; color:var(--text-muted);">
                    <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" style="opacity:.3; display:block; margin:0 auto 10px;">
                        <circle cx="11" cy="11" r="8"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                    No patients match your search. Try a different name or contact number.
                </div>
            </div>
        </div>

    </div>
</main>

<script>
function filterPatients() {
    var query = document.getElementById('patientSearch').value.toLowerCase().trim();
    var rows  = document.querySelectorAll('#patientTbody .patient-row');
    var shown = 0;

    rows.forEach(function(row) {
        var text = row.textContent.toLowerCase();
        if (!query || text.includes(query)) {
            row.style.display = '';
            shown++;
        } else {
            row.style.display = 'none';
        }
    });

    var noRes = document.getElementById('noResults');
    var countBadge = document.getElementById('visibleCount');
    if (countBadge) countBadge.textContent = shown + ' shown';

    if (noRes) noRes.style.display = (shown === 0 && rows.length > 0) ? 'block' : 'none';
}
</script>

</body>
</html>
