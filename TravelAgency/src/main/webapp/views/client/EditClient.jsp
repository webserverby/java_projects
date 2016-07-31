		<%@ page contentType="text/html;charset=UTF-8" language="java" %>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<title>Редактирование туриста</title>
		<link href="${pageContext.servletContext.contextPath}/css/editStyle.css" rel="stylesheet">

		<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.12.1.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#button').click(function() {
					if ($('#clientSurname').val() == '' || $('#clientName').val() == '' || $('#passportNumber').val() == '' ) {
						if ($('#clientSurname').val() == '') {
							$('#clientSurname').css('box-shadow', 'rgba(102, 175, 233, 1) 0px 0px 25px inset');
							$('#clientName').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
						} else if($('#clientName').val() == '') {
							$('#clientName').css('box-shadow', 'rgba(102, 175, 233, 1) 0px 0px 25px inset');
							$('#passportNumber').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
						} else {
							$('#clientSurname').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
							$('#clientName').css('box-shadow', 'rgba(102, 175, 233, 0) 0px 0px 25px inset');
							$('#passportNumber').css('box-shadow', 'rgba(102, 175, 233, 1) 0px 0px 25px inset');
						}
					}
				});
			});
		</script>

		<header>
			<div>
				<h1>ID:${client.id}</h1>
				<h1>Редактирование туриста</h1>
			</div>
		</header>

		<div class="content">
		<form action="${pageContext.servletContext.contextPath}/client/edit_client" method="POST">
			<p class="lead">Внесите требуемые данные:</p>
			<input type="hidden" name="id" class="form-control input-control" value="${client.id}">
			<input type="hidden" name="passport_id" class="form-control input-control" value="${client.passport.id}">
			<table  class="tablePois" >
				<tr>
					<th>Фамилия:</th>
					<th><input type="text" name="clientSurname" id="clientSurname" class="form-control input-control" value="${client.surname}" placeholder="Фамилия" required></th>
				</tr>
				<tr>
					<th>Имя:</th>
					<th><input type="text" name="clientName" id="clientName" class="form-control input-control" value="${client.name}" placeholder="Имя" required></th>
				</tr>
				<tr>
					<th>Отчество:</th>
					<th><input type="text" name="clientPatronymic"  class="form-control input-control" value="${client.patronymic}" placeholder="Отчество"></th>
				</tr>
				<tr>
					<th>Телефон мобильный:</th>
					<th><input type="text" name="clientPhoneMobile"  class="form-control input-control" value="${client.phoneMobile}" placeholder="Телефон мобильный"></th>
				</tr>
				<tr>
					<th>Телефон домашний:</th>
					<th><input type="text" name="clientPhoneHome"  class="form-control input-control" value="${client.phoneHome}" placeholder="Телефон домашний"></th>
				</tr>
				<tr>
					<th>Адрес:</th>
					<th><input type="text" name="clientAddress"  class="form-control input-control" value="${client.address}" placeholder="Адрес"></th>
				</tr>
				<tr>
					<th>Дата рождения:</th>
					<th><input type="text" name="clientBirthDate"  class="form-control input-control" value="${client.birthDate}" placeholder="Дата рождения"></th>
				</tr>
				<tr>
					<th>Серия паспорта:</th>
					<th><input type="text" name="passportSeries"  class="form-control input-control" value="${client.passport.series}" placeholder="Серия паспорта"></th>
				</tr>
				<tr>
					<th>Номер паспорта:</th>
					<th><input type="text" name="passportNumber" id="passportNumber" class="form-control input-control" value="${client.passport.number}" placeholder="Номер паспорта" required></th>
				</tr>
				<tr>
					<th>Кем выдан:</th>
					<th><input type="text" name="passportReceived"  class="form-control input-control" value="${client.passport.received}" placeholder="Кем выдан"></th>
				</tr>
				<tr>
					<th>Когда выдан:</th>
					<th><input type="text" name="passportIssueDate"  class="form-control input-control" value="${client.passport.issueDate}" placeholder="Когда выдан"></th>
				</tr>
				<tr>
					<th>Срок действия:</th>
					<th><input type="text" name="passportExpiryDate"  class="form-control input-control" value="${client.passport.expiryDate}" placeholder="Срок действия"></th>
				</tr>

			</table>

			<input type="submit" align="center" id="button" class="btn btn-primary input-control"  value="Готово">
		</form>
		</div>

