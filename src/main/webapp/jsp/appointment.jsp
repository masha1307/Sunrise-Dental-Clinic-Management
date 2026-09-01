<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Appointment Management - DentalCare System</title>
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
        <% if ("receptionist".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/appointment" class="nav-item active">Appointments</a>
            <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item">Billing</a>
            <a href="${pageContext.request.contextPath}/cancellations" class="nav-item">Cancellations</a>
        <% } else { %>
            <a href="${pageContext.request.contextPath}/appointmentSummary" class="nav-item active">Appointments</a>
        <% } %>
        <% if ("admin".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/dentist" class="nav-item">Dentist Mgmt</a>
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item">Treatment Mgmt</a>
            <a href="${pageContext.request.contextPath}/receptionist" class="nav-item">Receptionist Mgmt</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/jsp/searchAppointment.jsp" class="nav-item">Search</a>
        <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="nav-item">Help Guide</a>
    </nav>

    <div class="nav-user">
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
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                        </svg>
                    </span>
                    Appointment Management
                </h1>
                <p>Schedule new patient visits, assign doctors and procedures, or modify existing bookings.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                <span>Back to Dashboard</span>
            </a>
        </div>

        <%
            String successMessage = (String) request.getAttribute("successMessage");
            String errorMessage = (String) request.getAttribute("errorMessage");

            if(successMessage != null){
        %>
        <div class="success-message">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                <polyline points="22 4 12 14.01 9 11.01"></polyline>
            </svg>
            <span><%= successMessage %></span>
        </div>
        <%
            }
            if(errorMessage != null){
        %>
        <div class="error-message">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="12" y1="8" x2="12" y2="12"></line>
                <line x1="12" y1="16" x2="12.01" y2="16"></line>
            </svg>
            <span><%= errorMessage %></span>
        </div>
        <%
            }
        %>

        <div class="form-card">
            <div class="card-header-title">
                <h2>Booking Details</h2>
                <span class="badge badge-info">Booking Form</span>
            </div>

            <form action="${pageContext.request.contextPath}/appointment" method="post">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="actionSelect">Operation Action</label>
                        <select id="actionSelect" name="action" required>
                            <option value="add">Add New Appointment</option>
                            <option value="update">Update Appointment</option>
                            <option value="delete">Delete Appointment</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="appointmentId">Appointment ID (For Update/Delete)</label>
                        <input type="text" id="appointmentId" name="appointmentId" placeholder="e.g. 101">
                    </div>

                    <div class="form-group">
                        <label for="patientSelect">Patient</label>
                        <select id="patientSelect" name="patientId" required onchange="togglePatientForm()">
                            <option value="">-- Select Patient --</option>
                            <c:forEach var="patient" items="${patients}">
                                <option value="${patient.patientId}">${patient.patientName} (ID: ${patient.patientId})</option>
                            </c:forEach>
                            <option value="new">+ Add New Patient</option>
                        </select>
                    </div>

                    <!-- New Patient Form (Hidden by default) -->
                    <div id="newPatientForm" style="display: none; grid-column: 1 / -1; margin-top: 10px; padding: 20px; background: #f8fafc; border-radius: 8px; border: 1px solid #e2e8f0;">
                        <h3 style="margin: 0 0 15px 0; color: var(--primary); font-size: 16px;">New Patient Details</h3>
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="newPatientName">Patient Name *</label>
                                <input type="text" id="newPatientName" name="newPatientName" placeholder="Enter full name">
                            </div>
                            <div class="form-group">
                                <label for="newPatientAge">Age *</label>
                                <input type="number" id="newPatientAge" name="newPatientAge" placeholder="Enter age" min="1" max="120">
                            </div>
                            <div class="form-group">
                                <label for="newPatientGender">Gender *</label>
                                <select id="newPatientGender" name="newPatientGender">
                                    <option value="">-- Select Gender --</option>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="newPatientContact">Contact Number *</label>
                                <input type="tel" id="newPatientContact" name="newPatientContact" placeholder="Enter contact number">
                            </div>
                            <div class="form-group">
                                <label for="newPatientEmail">Email</label>
                                <input type="email" id="newPatientEmail" name="newPatientEmail" placeholder="Enter email (optional)">
                            </div>
                            <div class="form-group">
                                <label for="newPatientAddress">Address</label>
                                <input type="text" id="newPatientAddress" name="newPatientAddress" placeholder="Enter address (optional)">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="dentistId">Assigned Dentist</label>
                        <select id="dentistId" name="dentistId" required>
                            <option value="">-- Select Dentist --</option>
                            <c:forEach var="dentist" items="${dentists}">
                                <option value="${dentist.dentistId}">${dentist.dentistName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="treatmentId">Treatment Type</label>
                        <select id="treatmentId" name="treatmentId" required>
                            <option value="">-- Select Treatment --</option>
                            <c:forEach var="treatment" items="${treatments}">
                                <option value="${treatment.treatmentId}">${treatment.treatmentName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="appointmentDate">Appointment Date</label>
                        <input type="date" id="appointmentDate" name="appointmentDate" required>
                    </div>

                    <div class="form-group">
                        <label for="appointmentTime">Appointment Time</label>
                        <input type="time" id="appointmentTime" name="appointmentTime" required>
                    </div>
                </div>

                <button type="submit" style="margin-top: 24px; width: auto; padding: 13px 32px;">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
                        <polyline points="17 21 17 13 7 13 7 21"></polyline>
                        <polyline points="7 3 7 8 15 8"></polyline>
                    </svg>
                    <span>Submit Appointment Record</span>
                </button>
            </form>
        </div>

    </div>
</main>

<script>
function togglePatientForm() {
    var patientSelect = document.getElementById("patientSelect");
    var newPatientForm = document.getElementById("newPatientForm");
    
    if (patientSelect.value === "new") {
        newPatientForm.style.display = "block";
        document.getElementById("newPatientName").required = true;
        document.getElementById("newPatientAge").required = true;
        document.getElementById("newPatientGender").required = true;
        document.getElementById("newPatientContact").required = true;
    } else {
        newPatientForm.style.display = "none";
        document.getElementById("newPatientName").required = false;
        document.getElementById("newPatientAge").required = false;
        document.getElementById("newPatientGender").required = false;
        document.getElementById("newPatientContact").required = false;
    }
}
</script>

</body>
</html>