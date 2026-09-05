<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dental Clinic - Staff Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=2">
</head>
<body>

<div class="login-container">
    <div class="login-card">
        <div class="login-header">
            <div class="logo-badge">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M12 2C8 2 4 5 4 10c0 4.5 2 9 4.5 12 .8.9 2 1 2.5 0l1-2 1 2c.5 1 1.7.9 2.5 0C18 19 20 14.5 20 10c0-5-4-8-8-8z"/>
                </svg>
            </div>
            <h1>SUNRISE DENTAL CLINIC</h1>
            <p>Management & Patient Administration</p>
        </div>

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if(errorMessage != null) {
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

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="input-group">
                <label for="username">Username</label>
                <div class="input-wrapper">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                    </svg>
                    <input type="text" id="username" name="username" placeholder="Enter staff username" required autocomplete="username">
                </div>
            </div>

            <div class="input-group">
                <label for="password">Password</label>
                <div class="input-wrapper">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                        <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                    </svg>
                    <input type="password" id="password" name="password" placeholder="Enter password" required autocomplete="current-password">
                </div>
            </div>

            <button type="submit" style="margin-top: 10px;">
                <span>Sign In to Dashboard</span>
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                    <polyline points="12 5 19 12 12 19"></polyline>
                </svg>
            </button>
        </form>
    </div>
</div>

</body>
</html>