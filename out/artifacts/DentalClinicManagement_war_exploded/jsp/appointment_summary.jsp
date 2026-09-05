<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Appointment" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Appointment Summary - Dental Care</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=6">
</head>
<body>

<%
    // Session එකෙන් පරිශීලකයාගේ භූමිකාව (Role) ලබා ගැනීම
    String role = (String) session.getAttribute("role");
    
    String adminClass = "hide-card";
    String recepClass = "hide-card";
    
    if (role != null) {
        role = role.trim().toLowerCase(); 
        if ("admin".equals(role)) {
            adminClass = "show-card";
        } else if ("receptionist".equals(role)) {
            recepClass = "show-card";
        }
    }
%>

<!-- Top Navigation Bar -->
<header class="app-navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">
        <div class="brand-title">DentalCare <span>System</span></div>
    </a>
    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-item">Dashboard</a>
        <a href="${pageContext.request.contextPath}/appointmentSummary" class="nav-item active">Appointment Summary</a>
        
        <!-- රෙසෙප්ෂනිස්ටර්ට පමණක් පෙනෙන මෙනු ලින්ක්ස් -->
        <a href="${pageContext.request.contextPath}/appointment" class="nav-item <%= recepClass %>">Appointments</a>
        <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item <%= recepClass %>">Billing</a>
        
        <!-- ඇඩ්මින්ට පමණක් පෙනෙන මෙනු ලින්ක්ස් -->
        <a href="${pageContext.request.contextPath}/dentist" class="nav-item <%= adminClass %>">Dentist Mgmt</a>
        <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item <%= adminClass %>">Treatment Mgmt</a>
        
        <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="nav-item">Help Guide</a>
    </nav>
    <div class="nav-user">
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
    </div>
</header>

<main class="main-wrapper">
    <!-- Hero Banner -->
    <div class="dashboard-hero">
        <h1>Appointment Summary Logs</h1>
        <p>Filter, review, and overview dental checkup timelines and operational doctor schedules by date.</p>
    </div>

    <!-- Filter Form Section Card -->
    <div class="dashboard-card" style="padding: 30px; box-shadow: var(--shadow-sm); max-width: 500px; margin-bottom: 32px;">
        <h3 style="font-size: 20px; font-weight: 700; margin-bottom: 20px;">Select Log Target Date</h3>
        <form method="get" action="${pageContext.request.contextPath}/appointmentSummary">
            <div class="form-group">
                <label for="date">Target Date</label>
                <input type="date" id="date" name="date" value="<%= request.getAttribute("selectedDate") != null ? request.getAttribute("selectedDate") : "" %>" required>
            </div>
            <button type="submit" class="btn" style="margin-top: 12px;">Fetch Appointment Logs</button>
        </form>
    </div>

    <!-- Live Data View Table Container -->
    <div class="table-container" style="background: var(--card-bg); padding: 30px; border-radius: var(--radius-md); box-shadow: var(--shadow-sm); border: 1px solid var(--border); overflow-x: auto;">
        <h3 style="font-size: 20px; font-weight: 700; margin-bottom: 24px;">Daily Schedule Records Matrix</h3>
        
        <table class="custom-table" style="width: 100%; border-collapse: collapse; text-align: left; font-size: 14px;">
            <thead>
                <tr style="background: var(--bg); border-bottom: 2px solid var(--border);">
                    <th style="padding: 12px 16px; font-weight: 600;">ID</th>
                    <th style="padding: 12px 16px; font-weight: 600;">Patient</th>
                    <th style="padding: 12px 16px; font-weight: 600;">Dentist</th>
                    <th style="padding: 12px 16px; font-weight: 600;">Treatment</th>
                    <th style="padding: 12px 16px; font-weight: 600; text-align: center;">Scheduled Time</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
                if (appointments != null && !appointments.isEmpty()) {
                    for (Appointment a : appointments) {
            %>
                <tr style="border-bottom: 1px solid var(--border);">
                    <td style="padding: 14px 16px; color: var(--text-muted);">#<%= a.getAppointmentId() %></td>
                    <td style="padding: 14px 16px; font-weight: 600; color: var(--primary);"><%= a.getPatientName() != null ? a.getPatientName() : ("#"+a.getPatientId()) %></td>
                    <td style="padding: 14px 16px;"><%= a.getDentistName() %></td>
                    <td style="padding: 14px 16px; font-weight: 500;"><%= a.getTreatmentName() %></td>
                    <td style="padding: 14px 16px; text-align: center;">
                        <span style="padding: 6px 14px; border-radius: 50px; font-size: 12px; font-weight: 700; background: var(--primary-light); color: var(--primary);">
                            <%= a.getAppointmentTime() %>
                        </span>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="5" style="text-align:center; color:var(--text-muted); padding:30px;">No active dental appointments found for the selected timeline.</td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>

</body>
</html>
