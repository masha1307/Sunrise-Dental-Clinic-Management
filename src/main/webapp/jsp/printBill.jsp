<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dentalclinic.model.Bill" %>
<%@ page import="com.dentalclinic.model.Appointment" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Print Bill</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=2">
</head>
<body>

<%
    Bill bill = (Bill) request.getAttribute("bill");
    Appointment appointment = (Appointment) request.getAttribute("appointment");
%>

<div class="print-page">

    <div class="print-toolbar no-print">
        <p>Press <strong>Ctrl + P</strong> (or Cmd + P on Mac) to print this bill.</p>
        <a href="${pageContext.request.contextPath}/bill" class="back-btn">
            &larr; Back to Billing
        </a>
    </div>

    <% if (bill != null) { %>
    <div class="invoice-card">
        <div class="invoice-header">
            <h3 style="font-size: 20px; font-weight: 700; color: var(--text-main);">Sunrice Dental Clinic</h3>
            <p style="font-size: 13px; color: var(--text-muted);">Official Payment Receipt</p>
            <div style="margin-top: 10px;">
                <span class="badge badge-success">Invoice #<%= bill.getBillId() %></span>
            </div>
        </div>

        <% if (appointment != null) { %>
        <div class="invoice-row">
            <span style="color: var(--text-muted);">Patient</span>
            <strong><%= appointment.getPatientName() != null ? appointment.getPatientName() : "Patient #" + appointment.getPatientId() %></strong>
        </div>
        <div class="invoice-row">
            <span style="color: var(--text-muted);">Dentist</span>
            <span><%= appointment.getDentistName() != null ? appointment.getDentistName() : "Dentist #" + appointment.getDentistId() %></span>
        </div>
        <div class="invoice-row">
            <span style="color: var(--text-muted);">Treatment</span>
            <span><%= appointment.getTreatmentName() != null ? appointment.getTreatmentName() : "Treatment #" + appointment.getTreatmentId() %></span>
        </div>
        <div class="invoice-row">
            <span style="color: var(--text-muted);">Date</span>
            <span><%= appointment.getAppointmentDate() %></span>
        </div>
        <% } %>

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
    <% } else { %>
    <div class="invoice-card" style="text-align:center; color: var(--text-muted); padding: 50px 20px;">
        <h4 style="color: var(--text-main);">No bill found</h4>
        <p style="font-size: 13px;">Please generate a bill first before printing.</p>
    </div>
    <% } %>

</div>

</body>
</html>