<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Bill" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Billing Summary - Dental Care</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=2">
</head>
<body>
<header class="app-navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">
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
        <a href="${pageContext.request.contextPath}/jsp/help.jsp" class="nav-item">Help Guide</a>
    </nav>
    <div class="nav-user">
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
    </div>
</header>

<main class="page-container">
    <div class="form-card">
        <div class="card-header-title">
            <h2>Billing Summary by Date</h2>
            <span class="badge">Report</span>
        </div>

        <form method="get" action="${pageContext.request.contextPath}/billingSummary">
            <div class="form-group">
                <label for="date">Select Date</label>
                <input type="date" id="date" name="date" value="<%= request.getAttribute("selectedDate") != null ? request.getAttribute("selectedDate") : "" %>" required>
            </div>
            <button type="submit">Show Billing</button>
        </form>

        <div class="table-container" style="margin-top:20px;">
            <table class="custom-table">
                <thead>
                <tr>
                    <th>Bill ID</th>
                    <th>Appointment #</th>
                    <th>Patient</th>
                    <th>Appointment Time</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
                    if (bills != null && !bills.isEmpty()) {
                        for (Bill b : bills) {
                %>
                <tr>
                    <td>#<%= b.getBillId() %></td>
                    <td><%= b.getAppointmentNumber() != null ? b.getAppointmentNumber() : "-" %></td>
                    <td><%= b.getPatientName() != null ? b.getPatientName() : "-" %></td>
                    <td><span class="badge"><%= b.getAppointmentDate() %></span></td>
                    <td><strong>Rs. <%= String.format("%.2f", b.getTotalAmount()) %></strong></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="5" style="text-align:center; color:var(--text-muted); padding:20px;">No billing records for selected date.</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <div class="back-section" style="margin-top:16px;">
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="back-btn">Back to Dashboard</a>
        </div>
    </div>
</main>

</body>
</html>