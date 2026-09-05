<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Appointment" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Appointments - DentalCare System</title>
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
        <a href="${pageContext.request.contextPath}/jsp/searchAppointment.jsp" class="nav-item active">Search</a>
        <% if ("receptionist".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/appointment" class="nav-item">Appointments</a>
            <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item">Billing</a>
        <% } %>
        <% if ("admin".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/dentist" class="nav-item">Dentist Mgmt</a>
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item">Treatment Mgmt</a>
            <a href="${pageContext.request.contextPath}/receptionist" class="nav-item">Receptionist Mgmt</a>
        <% } %>
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
    <div class="page-container" style="max-width: 1000px;">

        <!-- Top Header with Back to Dashboard Button on the Right Side -->
        <div class="page-top-header">
            <div class="header-title-area">
                <h1>
                    <span class="card-icon-wrapper" style="width:38px; height:38px; margin-bottom:0; background: var(--accent-light); color: var(--accent);">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="11" cy="11" r="8"></circle>
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                        </svg>
                    </span>
                    Search Appointments
                </h1>
                <p>Query scheduled patient bookings by appointment ID, patient ID, or date.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                <span>Back to Dashboard</span>
            </a>
        </div>

        <!-- Search Filter Card -->
        <div class="form-card" style="margin-bottom: 28px;">
            <div class="card-header-title">
                <h2>Search Filter</h2>
                <span class="badge badge-info" style="background: var(--accent-light); color: var(--accent);">Quick Lookup</span>
            </div>

            <form action="${pageContext.request.contextPath}/searchAppointment" method="get">
                <div class="form-group">
                    <label for="keyword">Search Keyword</label>
                    <div class="input-wrapper">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="11" cy="11" r="8"></circle>
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                        </svg>
                        <input type="text" id="keyword" name="keyword" placeholder="Enter Appointment ID, Patient ID, or Date (YYYY-MM-DD)" required>
                    </div>
                </div>

                <button type="submit" style="background: linear-gradient(135deg, #0d9488 0%, #0f766e 100%);">
                    <span>Perform Search Query</span>
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="11" cy="11" r="8"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                </button>
            </form>
        </div>

        <!-- Results Table -->
        <div class="form-card">
            <div class="card-header-title">
                <h2>Search Results</h2>
                <span class="badge badge-info">Matching Records</span>
            </div>

            <div class="table-container" style="margin-top:0;">
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>Appointment ID</th>
                            <th>Patient</th>
                            <th>Dentist</th>
                            <th>Treatment</th>
                            <th>Date</th>
                            <th style="text-align: right;">Time Slot</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
                            if(appointments != null && !appointments.isEmpty()) {
                                for(Appointment appointment : appointments) {
                        %>
                        <tr>
                            <td><span class="badge badge-info">#<%= appointment.getAppointmentId() %></span></td>
                            <td><strong style="color: var(--primary);"><%= appointment.getPatientName() != null ? appointment.getPatientName() : "Patient #" + appointment.getPatientId() %></strong></td>
                            <td><%= appointment.getDentistName() != null ? appointment.getDentistName() : "Dentist #" + appointment.getDentistId() %></td>
                            <td><%= appointment.getTreatmentName() != null ? appointment.getTreatmentName() : "General Consultation" %></td>
                            <td><%= appointment.getAppointmentDate() %></td>
                            <td style="text-align: right;"><span class="badge badge-success"><%= appointment.getAppointmentTime() %></span></td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="6" class="empty-state">
                                No appointment records matched the specified query.
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</main>

</body>
</html>