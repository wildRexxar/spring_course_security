<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.01.2022
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Employees</title>
</head>
<body>
<h3>Information for all employees</h3>

<br><br>

<%--тег security:authorize скрывает содержимое тега от всех ролей кроме указанных в access--%>

<security:authorize access="hasRole('HR')">
    <input type="button" value="Salary" onclick="window.location.href = 'hr_info'">
    Only for HR staff
</security:authorize>
<br><br>

<security:authorize access="hasRole('MANAGER')">
    <input type="button" value="Performance" onclick="window.location.href = 'manager_info'">
    Only for Managers
</security:authorize>

</body>
</html>
