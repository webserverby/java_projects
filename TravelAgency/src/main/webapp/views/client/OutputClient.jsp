
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <title>Туристы</title>
    <link href="${pageContext.servletContext.contextPath}/css/outputStyle.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.12.1.min.js"></script>

            <div>
                <h1>Туристы</h1>
            </div>
        <div class="height">
        <a href="${pageContext.servletContext.contextPath}/client/add_client" id="addBtn">
            <button class="btn btn-success">+ Добавить</button>
        </a>
        <a href="${pageContext.servletContext.contextPath}/client/search_client" id="searchBtn">
            <button class="btn btn-success">Поиск</button>
        </a>
        <a href="${pageContext.servletContext.contextPath}/Logging/log4j_html_info.html" id="loggingBtn">
            <button class="btn btn-info">Логи</button>
        </a>
        </div>


        <c:if test="${!clients.isEmpty()}">
        <div>
            <table border="2" class="table" >
                <tbody>
                <tr>
                    <th>ID</th>
                    <th>Фамилия</th>
                    <th>Имя</th>
                    <th>Отчество</th>
                    <th>Телефон<br>моб./дом.</th>

                    <th>Адрес</th>
                    <th>Дата рождения</th>
                    <th>Серия</th>
                    <th>Номер</th>
                    <th>Кем выдан</th>
                    <th>Когда выдан</th>
                    <th>Срок действия</th>
                    <th>Доп. действия</th>

                </tr>
                <div class="createClient">
                    <c:forEach items="${clients}" var="client" varStatus="status">
                        <tr>
                            <td>${client.id}</td>
                            <td>${client.surname}</td>
                            <td>${client.name}</td>
                            <td>${client.patronymic}</td>
                            <td>${client.phoneMobile}<br>${client.phoneHome}</td>

                            <td>${client.address}</td>
                            <td>${client.birthDate}</td>
                            <td>${client.passport.series}</td>
                            <td>${client.passport.number}</td>
                            <td>${client.passport.received}</td>
                            <td>${client.passport.issueDate}</td>
                            <td>${client.passport.expiryDate}</td>


                            <td id="linkAction">
                                <a href="${pageContext.servletContext.contextPath}/client/edit_client?id=${client.id}" id="redactBtn"><button class="btn btn-warning">Редактировать</button></a>
                                <a href="${pageContext.servletContext.contextPath}/client/delete_client?id=${client.id}" id="deletBtn"><button class="btn btn-danger">Удалить</button></a>
                            </td>
                        </tr>
                    </c:forEach>
                </div>
                </tbody>
            </table>
        </div>
        </c:if>
