<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Management - Dental Clinic</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=4">
</head>
<body>

<header class="app-navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">
        <div class="brand-title">DentalCare <span>Staff Registry</span></div>
    </a>
    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-item">Back to Dashboard</a>
    </nav>
</header>

<main class="main-wrapper">
    <!-- Hero Banner -->
    <div class="dashboard-hero">
        <h1>Staff Directory Management</h1>
        <p>Review active dental clinic operators, supervisors, and administrative personnel profiles.</p>
    </div>

    <!-- ලස්සන කරන ලද නව Table ව්‍යුහය -->
    <div class="table-container">
        <h3 class="table-title">Active System Users</h3>
        
        <table class="custom-table">
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Access Role</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<User> staffList = (List<User>) request.getAttribute("staffList");
                    if (staffList != null && !staffList.isEmpty()) {
                        for (User u : staffList) {
                %>
                <tr>
                    <td style="color: var(--text-muted);">#<%= u.getUserId() %></td>
                    <td style="font-weight: 600;"><%= u.getUsername() %></td>
                    <td>
                        <% if ("admin".equalsIgnoreCase(u.getRole())) { %>
                            <span class="role-badge admin-role">Admin</span>
                        <% } else { %>
                            <span class="role-badge recep-role">Receptionist</span>
                        <% } %>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3" style="padding: 30px; text-align: center; color: var(--text-muted);">No system staff directory records found.</td>
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
