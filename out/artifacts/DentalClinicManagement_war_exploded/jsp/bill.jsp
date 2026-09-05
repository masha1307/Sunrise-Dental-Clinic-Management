<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dentalclinic.model.Bill" %>
<%@ page import="com.dentalclinic.model.Appointment" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Billing & Invoicing - Dental Care System</title>
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
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-item">Dashboard</a>
        <% if ("receptionist".equals(role)) { %>
            <a href="${pageContext.request.contextPath}/appointment" class="nav-item">Appointments</a>
            <a href="${pageContext.request.contextPath}/jsp/bill.jsp" class="nav-item active">Billing</a>
        <% } else { %>
            <a href="${pageContext.request.contextPath}/appointmentSummary" class="nav-item">Appointments</a>
            <a href="${pageContext.request.contextPath}/billingSummary" class="nav-item active">Billing</a>
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

<main class="page-container" style="max-width: 1000px;">
    <div class="billing-layout">
        <!-- Form Section -->
        <div class="form-card">
            <div class="card-header-title">
                <h2>
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="var(--success)" stroke-width="2">
                        <rect x="2" y="5" width="20" height="14" rx="2"></rect>
                        <line x1="2" y1="10" x2="22" y2="10"></line>
                    </svg>
                    Generate Invoice
                </h2>
                <span class="badge badge-success">Billing Form</span>
            </div>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
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

            <form action="${pageContext.request.contextPath}/bill" method="post">
                <div class="form-group">
                    <label for="appointmentId">Appointment ID</label>
                    <input type="text" id="appointmentId" name="appointmentId" placeholder="Enter Appointment ID" required
                           value="<%= request.getParameter("appointmentId") != null ? request.getParameter("appointmentId") : "" %>">
                </div>

                <!-- Appointment Details Section -->
                <%
                    Appointment appointment = (Appointment) request.getAttribute("appointment");
                    if(appointment != null){
                %>
                <div style="background: #f0fdf4; border: 1px solid #22c55e; border-radius: 8px; padding: 16px; margin-bottom: 20px;">
                    <h4 style="margin: 0 0 12px 0; color: #16a34a; font-size: 14px; font-weight: 600;">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="vertical-align: text-bottom;">
                            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                            <polyline points="22 4 12 14.01 9 11.01"></polyline>
                        </svg>
                        Appointment Details
                    </h4>
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 8px; font-size: 13px;">
                        <div><strong>Patient:</strong> <%= appointment.getPatientName() != null ? appointment.getPatientName() : "Patient #" + appointment.getPatientId() %></div>
                        <div><strong>Dentist:</strong> <%= appointment.getDentistName() != null ? appointment.getDentistName() : "Dentist #" + appointment.getDentistId() %></div>
                        <div><strong>Treatment:</strong> <%= appointment.getTreatmentName() != null ? appointment.getTreatmentName() : "Treatment #" + appointment.getTreatmentId() %></div>
                        <div><strong>Date:</strong> <%= appointment.getAppointmentDate() %></div>
                        <div><strong>Time:</strong> <%= appointment.getAppointmentTime() %></div>
                    </div>
                </div>
                <%
                    }
                %>

                <div class="form-group">
                    <label for="consultationFee">Consultation Fee (Rs.)</label>
                    <input type="number" step="0.01" id="consultationFee" name="consultationFee" placeholder="e.g. 50.00" required>
                </div>

                <div class="form-group">
                    <label for="treatmentFee">Treatment Fee (Rs.)</label>
                    <input type="number" step="0.01" id="treatmentFee" name="treatmentFee" placeholder="e.g. 150.00" required>
                </div>

                <div class="form-group">
                    <label for="discount">Discount (Rs.)</label>
                    <input type="number" step="0.01" id="discount" name="discount" placeholder="e.g. 10.00" value="0">
                </div>

                <button type="submit" style="background: linear-gradient(135deg, #10b981 0%, #059669 100%);">
                    <span>Calculate & Save Bill</span>
                    <span style="font-weight : bold; margin-left : 5px;">Rs.</span>
                </button>
            </form>
        </div>

        <!-- Invoice Result Receipt -->
        <div>
            <%
                Bill bill = (Bill) request.getAttribute("bill");
                if(bill != null){
            %>
            <div class="invoice-card">
                <div class="invoice-header">
                    <h3 style="font-size: 20px; font-weight: 700; color: var(--text-main);">Sunrice Dental Clinic</h3>
                    <p style="font-size: 13px; color: var(--text-muted);">Official Payment Receipt</p>
                    <div style="margin-top: 10px;">
                        <span class="badge badge-success">Invoice #<%= bill.getAppointmentId() %></span>
                    </div>
                </div>

                <div class="invoice-row">
                    <span style="color: var(--text-muted);">Appointment ID</span>
                    <strong>#<%= bill.getAppointmentId() %></strong>
                </div>

                <div class="invoice-row">
                    <span style="color: var(--text-muted);">Consultation Fee</span>
                    <span>Rs.<%= String.format("%.2f", bill.getConsultationFee()) %></span>
                </div>

                <div class="invoice-row">
                    <span style="color: var(--text-muted);">Treatment Fee</span>
                    <span>Rs.<%= String.format("%.2f", bill.getTreatmentFee()) %></span>
                </div>

       

                <div class="invoice-row total">
                    <span>Total Paid Amount</span>
                    <span>Rs.<%= String.format("%.2f", bill.getTotalAmount()) %></span>
                </div>
            </div>
            <%
                } else {
            %>
            <div class="invoice-card" style="text-align: center; color: var(--text-muted); padding: 50px 20px;">
                <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" style="margin-bottom: 12px; display: block; margin-left: auto; margin-right: auto;">
                    <rect x="2" y="5" width="20" height="14" rx="2"></rect>
                    <line x1="2" y1="10" x2="22" y2="10"></line>
                </svg>
                <h4 style="color: var(--text-main); margin-bottom: 4px;">No Invoice Generated Yet</h4>
                <p style="font-size: 13px;">Fill in the appointment billing details on the left to calculate and preview the receipt.</p>
            </div>
            <%
                }
            %>
        </div>
    </div>

    <div class="back-section">
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="19" y1="12" x2="5" y2="12"></line>
                <polyline points="12 19 5 12 12 5"></polyline>
            </svg>
            <span>Back to Dashboard</span>
        </a>
    </div>
</main>

</body>
</html>