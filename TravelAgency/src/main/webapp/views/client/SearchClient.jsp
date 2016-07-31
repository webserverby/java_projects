<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <title>Поиск туриста</title>
    <link href="${pageContext.servletContext.contextPath}/css/searchStyle.css" rel="stylesheet">

    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.12.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            if ($('#clientSurname').val() == '' && $('#clientName').val() == '' && $('#passportNumber').val() == ''
                    && $('#idClient').val() == '') {
                $('input[name="submit"]').attr('disabled', true);
            }
            if ($('#clientSurname').val() != '' || $('#clientName').val() != '' || $('#passportNumber').val() != ''
                    || $('#idClient').val() != '') {
                $('input[name="submit"]').removeAttr('disabled');
            }
        });

        function check() {
            if ($('#clientSurname').val() == '' && $('#clientName').val() == '' && $('#passportNumber').val() == ''
                    && $('#idClient').val() == '')
                $('input[name="submit"]').attr('disabled','disable');
            else
                $('input[name="submit"]').removeAttr('disabled');
        }
    </script>

        <div>
            <h1>Поиск туриста</h1>
        </div>

        <form action="${pageContext.servletContext.contextPath}/client/search_client" method="POST" name="form">
            <p class="lead">Внесите возможные данные:</p>
            <div class="form-group">
                <input type="text" name="idClient" id="idClient" onkeyup="check();" class="form-control input-control" placeholder="ID клиента">
                <input type="text" name="clientSurname" id="clientSurname" onkeyup="check();" class="form-control input-control" placeholder="Фамилия" >
                <input type="text" name="clientName" id="clientName" onkeyup="check();" class="form-control input-control" placeholder="Имя" >
                <input type="text" name="passportNumber" id="passportNumber" onkeyup="check();" class="form-control input-control" placeholder="Номер паспорта" >
                <input type="submit" name="submit" id="button" class="btn btn-primary input-control" value="Найти">
            </div>
        </form>

    <c:if test="${!found.isEmpty()}">
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
                    <c:forEach items="${found}" var="client" varStatus="status">
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

