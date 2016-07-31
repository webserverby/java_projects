<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <title>Поиск тура</title>
    <link href="${pageContext.servletContext.contextPath}/css/searchStyle.css" rel="stylesheet">

    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.12.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            if ($('#tourNameTour').val() == '' && $('#routeCityCome').val() == '' && $('#routeCountryCome').val() == ''
                    && $('#idTour').val() == '') {
                $('input[name="submit"]').attr('disabled', true);
            }
            if ($('#tourNameTour').val() != '' || $('#routeCityCome').val() != '' || $('#routeCountryCome').val() != ''
                    || $('#idTour').val() != '') {
                $('input[name="submit"]').removeAttr('disabled');
            }
        });

        function check() {
            if ($('#tourNameTour').val() == '' && $('#routeCityCome').val() == '' && $('#routeCountryCome').val() == ''
                    && $('#idTour').val() == '')
                $('input[name="submit"]').attr('disabled','disable');
            else
                $('input[name="submit"]').removeAttr('disabled');
        }
    </script>

        <div>
            <h1>Поиск тура</h1>
        </div>

        <form action="${pageContext.servletContext.contextPath}/tour/search_tour" method="POST" name="form">
            <p class="lead">Внесите возможные данные:</p>
            <div class="form-group">
                <input type="text" name="idTour" id="idTour" onkeyup="check();" class="form-control input-control" placeholder="ID тура">
                <input type="text" name="tourNameTour" id="tourNameTour" onkeyup="check();" class="form-control input-control" placeholder="Название тура" >
                <input type="text" name="routeCityCome" id="routeCityCome" onkeyup="check();" class="form-control input-control" placeholder="Город прибытия" >
                <input type="text" name="routeCountryCome" id="routeCountryCome" onkeyup="check();" class="form-control input-control" placeholder="Страна прибытия" >
                <input type="submit" name="submit" id="button" class="btn btn-primary input-control" value="Найти">
            </div>
        </form>

    <c:if test="${!found.isEmpty()}">
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
                    <c:forEach items="${found}" var="tour" varStatus="status">
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