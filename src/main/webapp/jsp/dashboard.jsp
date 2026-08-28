<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Dental Clinic Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=2">
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
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-item active">Dashboard</a>
        <a href="${pageContext.request.contextPath}/appointment" class="nav-item">Appointments</a>
        <a href="${pageContext.request.contextPath}/jsp/searchAppointment.jsp" class="nav-item">Search</a>
        <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item">Billing</a>
        <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="nav-item">Help Guide</a>
        <% if ("admin".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/dentist" class="nav-item">Dentist Mgmt</a>
            <a href="${pageContext.request.contextPath}/treatmentAdmin" class="nav-item">Treatment Mgmt</a>
        <% } %>
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
            <span><%= (uname != null) ? uname : "Staff Admin" %></span>
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

<!-- Main Dashboard Container -->
<main class="main-wrapper">
    <!-- Hero Banner -->
    <div class="dashboard-hero">
        <h1>Welcome to Dental Clinic Management</h1>
        <p>Manage patient appointments, search medical schedules, and generate billing invoices seamlessly.</p>
    </div>

    <!-- Quick Stats Overview -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon cyan">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                    <line x1="16" y1="2" x2="16" y2="6"></line>
                    <line x1="8" y1="2" x2="8" y2="6"></line>
                    <line x1="3" y1="10" x2="21" y2="10"></line>
                </svg>
            </div>
            <div class="stat-info">
                <h4>Today's Schedule</h4>
                <div class="value">Active</div>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon teal">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                    <circle cx="9" cy="7" r="4"></circle>
                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                    <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                </svg>
            </div>
            <div class="stat-info">
                <h4>Dentist Roster</h4>
                <div class="value">Available</div>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon emerald">
               <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                    <polyline points="14 2 14 8 20 8"></polyline>
                    <line x1="16" y1="13" x2="8" y2="13"></line>
                    <line x1="16" y1="17" x2="8" y2="17"></line>
                    <polyline points="10 9 9 9 8 9"></polyline>
               </svg>

            </div>
            <div class="stat-info">
                <h4>Billing Module</h4>
                <div class="value">Ready</div>
            </div>
        </div>
    </div>

    <!-- Action Cards Grid -->
    <div class="dashboard-grid">
        <!-- Card 1 -->
        <div class="dashboard-card">
            <div class="card-body-content">
                <div class="card-icon-wrapper">
                    <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
                    </svg>
                </div>
                <h3>Appointment Management</h3>
                <p>Register new patient visits, assign certified dentists, update treatment slots, or cancel appointments.</p>
            </div>
            <a href="${pageContext.request.contextPath}/appointment" class="dashboard-btn">
                <span>Manage Appointments</span>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                    <polyline points="12 5 19 12 12 19"></polyline>
                </svg>
            </a>
        </div>

        <!-- Card 2 -->
        <div class="dashboard-card">
            <div class="card-body-content">
                <div class="card-icon-wrapper" style="background: var(--accent-light); color: var(--accent);">
                    <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="11" cy="11" r="8"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                </div>
                <h3>Search Appointments</h3>
                <p>Lookup patient history and active bookings instantly by Appointment ID, Patient ID, or treatment date.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/searchAppointment.jsp" class="dashboard-btn" style="background: var(--accent-light); color: var(--accent);">
                <span>Search Records</span>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                    <polyline points="12 5 19 12 12 19"></polyline>
                </svg>
            </a>
        </div>

        <!-- Card 3 -->
        <div class="dashboard-card">
            <div class="card-body-content">
                <div class="card-icon-wrapper" style="background: var(--success-light); color: var(--success);">
                    <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <rect x="2" y="5" width="20" height="14" rx="2"></rect>
                        <line x1="2" y1="10" x2="22" y2="10"></line>
                    </svg>
                </div>
                <h3>Billing & Invoicing</h3>
                <p>Calculate consultation fees, apply treatment discounts, and generate official patient bill receipts.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="dashboard-btn" style="background: var(--success-light); color: var(--success);">
                <span>Generate Bill</span>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                    <polyline points="12 5 19 12 12 19"></polyline>
                </svg>
            </a>
        </div>

        <!-- Card 4 -->
        <div class="dashboard-card">
            <div class="card-body-content">
                <div class="card-icon-wrapper" style="background: #e0e7ff; color: #4f46e5;">
                    <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="12" cy="12" r="10"></circle>
                        <path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"></path>
                        <line x1="12" y1="17" x2="12.01" y2="17"></line>
                    </svg>
                </div>
                <h3>Help & Guidelines</h3>
                <p>Access clinic system operation manuals, staff instructions, and step-by-step workflow procedures.</p>
            </div>
            <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="dashboard-btn" style="background: #e0e7ff; color: #4f46e5;">
                <span>View User Guide</span>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                    <polyline points="12 5 19 12 12 19"></polyline>
                </svg>
            </a>
        </div>
    </div>
</main>

</body>
</html>