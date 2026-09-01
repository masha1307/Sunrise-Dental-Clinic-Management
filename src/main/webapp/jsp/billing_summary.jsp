<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Bill" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Billing Summary - DentalCare System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=3">
</head>
<body>
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
        <a href="${pageContext.request.contextPath}/billingSummary" class="nav-item active">Billing Summary</a>
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
    <div class="page-container">
        <!-- Top Bar with Back to Dashboard Button on the Right Side -->
        <div class="page-top-header">
            <div class="header-title-area">
                <h1>
                    <span class="card-icon-wrapper" style="width:38px; height:38px; margin-bottom:0; background: var(--success-light); color: var(--success);">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <rect x="2" y="5" width="20" height="14" rx="2"></rect>
                            <line x1="2" y1="10" x2="22" y2="10"></line>
                        </svg>
                    </span>
                    Billing Summary
                </h1>
                <p>Filter and view daily billing transaction logs and reconciliation reports.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                <span>Back to Dashboard</span>
            </a>
        </div>

        <div class="form-card">
            <div class="card-header-title">
                <h2>Select Report Date</h2>
                <span class="badge badge-info">Search Filter</span>
            </div>

            <form method="get" action="${pageContext.request.contextPath}/billingSummary">
                <div class="form-group">
                    <label for="date">Target Date</label>
                    <input type="date" id="date" name="date" value="<%= request.getAttribute("selectedDate") != null ? request.getAttribute("selectedDate") : "" %>" required>
                </div>
                <button type="submit" style="width:auto; padding:12px 28px;">Show Billing Records</button>
            </form>
        </div>

        <div class="form-card" style="margin-top:28px;">
            <div class="card-header-title">
                <h2>Billing Transaction Records</h2>
                <span class="badge badge-success">Daily Logs</span>
            </div>

            <div class="table-container" style="margin-top:0;">
                <table class="custom-table">
                    <thead>
                    <tr>
                        <th>Bill ID</th>
                        <th>Appointment #</th>
                        <th>Patient</th>
                        <th>Appointment Date</th>
                        <th style="text-align: right;">Total Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Bill> bills = (List<Bill>) request.getAttribute("bills");
                        if (bills != null && !bills.isEmpty()) {
                            for (Bill b : bills) {
                    %>
                    <tr>
                        <td><strong>#<%= b.getBillId() %></strong></td>
                        <td><%= b.getAppointmentNumber() != null ? b.getAppointmentNumber() : "-" %></td>
                        <td style="font-weight: 600; color: var(--primary);"><%= b.getPatientName() != null ? b.getPatientName() : "-" %></td>
                        <td><span class="badge badge-info"><%= b.getAppointmentDate() %></span></td>
                        <td style="text-align: right;"><strong style="color: var(--text-main);">Rs. <%= String.format("%.2f", b.getTotalAmount()) %></strong></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr><td colspan="5" class="empty-state">No billing records found for the selected date.</td></tr>
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