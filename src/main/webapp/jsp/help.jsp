<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Guide & Support - DentalCare System</title>
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
        <% if ("receptionist".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/appointment" class="nav-item">Appointments</a>
            <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item">Billing</a>
        <% } %>
        <% if ("admin".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/dentist" class="nav-item">Dentist Mgmt</a>
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item">Treatment Mgmt</a>
            <a href="${pageContext.request.contextPath}/receptionist" class="nav-item">Receptionist Mgmt</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="nav-item active">Help Guide</a>
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
    <div class="page-container" style="max-width: 950px;">

        <!-- Top Header with Back to Dashboard Button on the Right Side -->
        <div class="page-top-header">
            <div class="header-title-area">
                <h1>
                    <span class="card-icon-wrapper" style="width:38px; height:38px; margin-bottom:0; background: #e0e7ff; color: #4f46e5;">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="10"></circle>
                            <path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"></path>
                            <line x1="12" y1="17" x2="12.01" y2="17"></line>
                        </svg>
                    </span>
                    User Guide & Support
                </h1>
                <p>System documentation, operation guidelines, and clinic workflow manual.</p>
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
                <h2>Operations Manual</h2>
                <span class="badge" style="background: #e0e7ff; color: #4f46e5;">System Guide</span>
            </div>

            <div style="display: grid; gap: 20px;">
                <!-- Step 1 -->
                <div style="background: var(--bg); padding: 20px 24px; border-radius: var(--radius-md); border-left: 4px solid var(--primary);">
                    <h3 style="font-size: 16px; font-weight: 700; margin-bottom: 6px; color: var(--primary);">1. Authentication & Role Permissions</h3>
                    <p style="font-size: 14px; color: var(--text-muted);">
                        Clinic staff authenticate using assigned usernames and passwords. Admins manage dentist rosters, treatment catalogs, and receptionist accounts.
                    </p>
                </div>

                <!-- Step 2 -->
                <div style="background: var(--bg); padding: 20px 24px; border-radius: var(--radius-md); border-left: 4px solid var(--accent);">
                    <h3 style="font-size: 16px; font-weight: 700; margin-bottom: 6px; color: var(--accent);">2. Appointment Management Workflow</h3>
                    <p style="font-size: 14px; color: var(--text-muted);">
                        Navigate to <strong>Appointments</strong> to schedule patient bookings. Select an operation (Add, Update, Delete), enter Patient ID, choose certified Dentists and Treatments, and assign dates and times.
                    </p>
                </div>

                <!-- Step 3 -->
                <div style="background: var(--bg); padding: 20px 24px; border-radius: var(--radius-md); border-left: 4px solid var(--success);">
                    <h3 style="font-size: 16px; font-weight: 700; margin-bottom: 6px; color: var(--success);">3. Search & Booking Logs</h3>
                    <p style="font-size: 14px; color: var(--text-muted);">
                        Use the <strong>Search Directory</strong> to filter active bookings by Appointment ID, Patient ID, or Date. Results are presented in an interactive table.
                    </p>
                </div>

                <!-- Step 4 -->
                <div style="background: var(--bg); padding: 20px 24px; border-radius: var(--radius-md); border-left: 4px solid #4f46e5;">
                    <h3 style="font-size: 16px; font-weight: 700; margin-bottom: 6px; color: #4f46e5;">4. Invoicing & Billing Generation</h3>
                    <p style="font-size: 14px; color: var(--text-muted);">
                        Navigate to <strong>Billing</strong> to enter consultation fees, procedure treatment costs, and calculate net payable totals with print receipt support.
                    </p>
                </div>
            </div>
        </div>

    </div>
</main>

</body>
</html>
