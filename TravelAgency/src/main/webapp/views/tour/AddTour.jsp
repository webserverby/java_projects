<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <title>Добавление тура</title>
    <link href="${pageContext.servletContext.contextPath}/css/addStyle.css" rel="stylesheet">

    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.12.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#button').click(function() {
                if ($('#tourNameTour').val() == '' || $('#tourDateBegin').val() == '' || $('#tourDateEnd').val() == '' ) {
                    if ($('#tourNameTour').val() == '') {
                        $('#tourNameTour').css('box-shadow', 'rgba(102, 175, 233, 1) 0px 0px 25px inset');
                        $('#tourDateBegin').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
                    } else if($('#tourDateBegin').val() == '') {
                        $('#tourDateBegin').css('box-shadow', 'rgba(102, 175, 233, 1) 0px 0px 25px inset');
                        $('#tourDateEnd').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
                    } else {
                        $('#tourNameTour').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
                        $('#tourDateBegin').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
                        $('#tourDateEnd').css('box-shadow', 'rgba(102, 175, 233, 1) 0px 0px 25px inset');
                    }
                }
            });
        });
    </script>

            <div>
                <h1>Добавление тура</h1>
            </div>

        <div class="content">
            <form action="${pageContext.servletContext.contextPath}/tour/add_tour" method="POST">
                <p class="lead">Внесите требуемые данные:</p>
                <div class="form-group">

                    <table  class="tablePois" >
                        <tr>
                            <th>Название тура:</th>
                            <th><input type="text" name="tourNameTour" id="tourNameTour" class="form-control input-control" placeholder="Название тура" required></th>
                        </tr>
                        <tr>
                            <th>Дата начала:</th>
                            <th><input type="text" name="tourDateBegin" id="tourDateBegin" class="form-control input-control" placeholder="Дата начала" required></th>
                        </tr>
                        <tr>
                            <th>Дата окончания:</th>
                            <th><input type="text" name="tourDateEnd" id="tourDateEnd" class="form-control input-control" placeholder="Дата окончания" required></th>
                        </tr>
                        <tr>
                            <th>Кол-во дней:</th>
                            <th><input type="text" name="tourDayNumber"  class="form-control input-control" placeholder="Кол-во дней"></th>
                        </tr>
                        <tr>
                            <th>Кол-во человек:</th>
                            <th><input type="text" name="tourPersonNumber"  class="form-control input-control" placeholder="Кол-во человек"></th>
                        </tr>
                        <tr>
                            <th>Туроператор:</th>
                            <th><input type="text" name="tourTourOperator"  class="form-control input-control" placeholder="Туроператор"></th>
                        </tr>
                        <tr>
                            <th>Отель:</th>
                            <th><input type="text" name="tourHotel"  class="form-control input-control" placeholder="Отель"></th>
                        </tr>
                        <tr>
                            <th>Тип номера:</th>
                            <th><input type="text" name="tourTypeNumber"  class="form-control input-control" placeholder="Тип номера"></th>
                        </tr>
                        <tr>
                            <th>Питание:</th>
                            <th><input type="text" name="tourFood" class="form-control input-control" placeholder="Питание"></th>
                        </tr>
                        <tr>
                            <th>Вид транспорта:</th>
                            <th><select name="routeTransportName" class="form-control input-control">
                                    <option value="Авиа">Авиа перелет</option>
                                    <option value="Автобус">Автобусный тур</option>
                                </select></th>
                        </tr>
                        <tr>
                            <th>Город отправления:</th>
                            <th><input type="text" name="routeCityDeparture"  class="form-control input-control" placeholder="Город отправления"></th>
                        </tr>
                        <tr>
                            <th>Город прибытия:</th>
                            <th><input type="text" name="routeCityCome"  class="form-control input-control" placeholder="Город прибытия"></th>
                        </tr>
                        <tr>
                            <th>Страна прибытия:</th>
                            <th><input type="text" name="routeCountryCome"  class="form-control input-control" placeholder="Страна прибытия"></th>
                        </tr>
                        <tr>
                            <th>Дата отправления:</th>
                            <th><input type="text" name="routeDateDeparture"  class="form-control input-control" placeholder="Дата отправления"></th>
                        </tr>
                        <tr>
                            <th>Дата прибытия:</th>
                            <th><input type="text" name="routeDateCome"  class="form-control input-control" placeholder="Дата прибытия"></th>
                        </tr>
                        <tr>
                            <th>Дата возврата:</th>
                            <th><input type="text" name="routeDateReturn"  class="form-control input-control" placeholder="Дата возврата"></th>
                        </tr>
                        <tr>
                            <th>Стоимость:</th>
                            <th><input type="text" name="tourTourСost"  class="form-control input-control" placeholder="Стоимость"></th>
                        </tr>
                    </table>

                    <input type="submit" id="button" class="btn btn-primary input-control" value="Создать">
                </div>
            </form>
        </div>
