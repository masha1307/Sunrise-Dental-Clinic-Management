<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Appointment" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cancellations - DentalCare System</title>
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
            <a href="${pageContext.request.contextPath}/appointment" class="nav-item">Appointments</a>
            <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item">Billing</a>
            <a href="${pageContext.request.contextPath}/cancellations" class="nav-item active">Cancellations</a>
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
    <div class="page-container" style="max-width: 1000px;">

        <!-- Top Header with Back to Dashboard Button on the Right Side -->
        <div class="page-top-header">
            <div class="header-title-area">
                <h1>
                    <span class="card-icon-wrapper" style="width:38px; height:38px; margin-bottom:0; background: var(--danger-light); color: var(--danger);">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="10"></circle>
                            <line x1="15" y1="9" x2="9" y2="15"></line>
                            <line x1="9" y1="9" x2="15" y2="15"></line>
                        </svg>
                    </span>
                    Appointment Cancellations
                </h1>
                <p>Lookup appointments by date to cancel or reschedule patient bookings.</p>
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

            if (successMessage != null) {
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

            if (errorMessage != null) {
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

        <!-- Date Selection Form -->
        <div class="form-card">
            <div class="card-header-title">
                <h2>Select Target Date</h2>
                <span class="badge badge-info">Cancellation Filter</span>
            </div>

            <form action="${pageContext.request.contextPath}/cancellations" method="get">
                <div class="form-group">
                    <label for="date">Appointment Date</label>
                    <input type="date" id="date" name="date" required
                           value="<%= request.getAttribute("selectedDate") != null ? request.getAttribute("selectedDate") : "" %>">
                </div>

                <button type="submit" style="width:auto; padding:12px 28px;">
                    View Appointments
                </button>
            </form>
        </div>

        <!-- Appointments Table -->
        <%
            String selectedDate = (String) request.getAttribute("selectedDate");
            List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");

            if (selectedDate != null) {
        %>
        <div class="form-card" style="margin-top:28px;">
            <div class="card-header-title">
                <h2>Appointments on <%= selectedDate %></h2>
                <span class="badge badge-info"><%= appointments != null ? appointments.size() : 0 %> Found</span>
            </div>

            <div class="table-container" style="margin-top:0;">
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>Appt ID</th>
                            <th>Patient</th>
                            <th>Dentist</th>
                            <th>Treatment</th>
                            <th>Time</th>
                            <th>Status</th>
                            <th style="text-align: right;">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (appointments == null || appointments.isEmpty()) {
                        %>
                        <tr>
                            <td colspan="7" class="empty-state">No appointments found for this date.</td>
                        </tr>
                        <%
                            } else {
                                for (Appointment appt : appointments) {
                                    String status = appt.getStatus() != null ? appt.getStatus() : "scheduled";
                                    boolean isCancelled = "cancelled".equalsIgnoreCase(status);
                        %>
                        <tr>
                            <td><strong>#<%= appt.getAppointmentId() %></strong></td>
                            <td><%= appt.getPatientName() != null ? appt.getPatientName() : "Patient #" + appt.getPatientId() %></td>
                            <td><%= appt.getDentistName() != null ? appt.getDentistName() : "Dentist #" + appt.getDentistId() %></td>
                            <td><%= appt.getTreatmentName() != null ? appt.getTreatmentName() : "Treatment #" + appt.getTreatmentId() %></td>
                            <td><%= appt.getAppointmentTime() %></td>
                            <td>
                                <% if (isCancelled) { %>
                                    <span class="badge badge-danger">Cancelled</span>
                                <% } else { %>
                                    <span class="badge badge-success">Scheduled</span>
                                <% } %>
                            </td>
                            <td style="text-align: right;">
                                <% if (!isCancelled) { %>
                                <form action="${pageContext.request.contextPath}/cancellations" method="post"
                                      onsubmit="return confirm('Are you sure you want to cancel this appointment?');" style="display:inline;">
                                    <input type="hidden" name="action" value="cancel">
                                    <input type="hidden" name="appointmentId" value="<%= appt.getAppointmentId() %>">
                                    <input type="hidden" name="date" value="<%= selectedDate %>">
                                    <button type="submit" class="btn btn-sm"
                                            style="width:auto; padding:6px 14px; font-size:12px; background:var(--danger); box-shadow:none;">
                                        Cancel Booking
                                    </button>
                                </form>
                                <% } else { %>
                                    <span style="color: var(--text-muted); font-size:12px;">Cancelled</span>
                                <% } %>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
        <%
            }
        %>

    </div>
</main>

</body>
</html>