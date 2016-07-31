<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <title>Туры</title>
    <link href="${pageContext.servletContext.contextPath}/css/outputStyle.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.12.1.min.js"></script>

            <div>
                <h1>Туры</h1>
            </div>
        <div class="height">
        <a href="${pageContext.servletContext.contextPath}/tour/add_tour" id="addBtn">
            <button class="btn btn-success">+ Добавить</button>
        </a>
        <a href="${pageContext.servletContext.contextPath}/tour/search_tour" id="searchBtn">
            <button class="btn btn-success">Поиск</button>
        </a>
            <a href="${pageContext.servletContext.contextPath}/Logging/log4j_html_info.html" id="loggingBtn">
                <button class="btn btn-info">Логи</button>
            </a>
        </div>

        <c:if test="${!tours.isEmpty()}">
        <div>
            <table border="2" class="table" >
                <tbody>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Дата начала</th>
                    <th>Дата окончания</th>
                    <th>Кол-во дней</th>
                    <th>Кол-во человек</th>
                    <th>Туроператор</th>
                    <th>Отель</th>
                    <th>Тип номера</th>
                    <th>Питание</th>
                    <th>Транспорт</th>
                    <th>Город отправления</th>
                    <th>Город прибытия</th>
                    <th>Страна прибытия</th>
                    <th>Дата отправления</th>
                    <th>Дата прибытия</th>
                    <th>Дата возврата</th>
                    <th>Стоимость</th>
                    <th>Доп. действия</th>
                </tr>
                <div class="createClient">
                    <c:forEach items="${tours}" var="tour" varStatus="status">
                        <tr>
                            <td>${tour.id}</td>
                            <td>${tour.nameTour}</td>
                            <td>${tour.dateBegin}</td>
                            <td>${tour.dateEnd}</td>
                            <td>${tour.dayNumber}</td>
                            <td>${tour.personNumber}</td>
                            <td>${tour.tourOperator}</td>
                            <td>${tour.hotel}</td>
                            <td>${tour.typeNumber}</td>
                            <td>${tour.food}</td>

                            <td>${tour.route.transportName}</td>
                            <td>${tour.route.cityDeparture}</td>
                            <td>${tour.route.cityCome}</td>
                            <td>${tour.route.countryCome}</td>
                            <td>${tour.route.dateDeparture}</td>
                            <td>${tour.route.dateCome}</td>
                            <td>${tour.route.dateReturn}</td>
                            <td>${tour.tourСost}</td>

                            <td id="linkAction">
                                <a href="${pageContext.servletContext.contextPath}/tour/edit_tour?id=${tour.id}" id="redactBtn"><button class="btn btn-warning">Редактировать</button></a>
                                <a href="${pageContext.servletContext.contextPath}/tour/delete_tour?id=${tour.id}" id="deletBtn"><button class="btn btn-danger">Удалить</button></a>
                            </td>
                        </tr>
                    </c:forEach>
                </div>
                </tbody>
            </table>
        </div>
        </c:if>