<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.servletContext.contextPath}/css/cssmenu.css" rel="stylesheet">

</head>
<body>
<div class="container">

    <div>
        <ul id="cssmenu">
            <li><a href="${pageContext.servletContext.contextPath}/client/index" class="centr">Главная</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/client/output_client" class="centr">Туристы</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/tour/output_tour" class="centr">Туры</a></li>
            <li><a href="#" class="centr">Заказы</a></li>
            <li><a href="#" class="centr">Сотрудники</a></li>
            <li><a href="#" class="centr">Бухгалтерия</a></li>
        </ul>
    </div>
