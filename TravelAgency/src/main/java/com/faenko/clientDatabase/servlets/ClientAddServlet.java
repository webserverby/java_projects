package com.faenko.clientDatabase.servlets;

import com.faenko.clientDatabase.models.Client;
import com.faenko.clientDatabase.models.Passport;
import com.faenko.clientDatabase.service.ClassName;
import com.faenko.clientDatabase.store.ClientCache;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет страницы добавления клиента
 *
 * @author Artem Faenko
 */
public class ClientAddServlet extends HttpServlet {
    /** Ссылка на список клиентов из единственного экземпляра ClientCache */
    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();
    /** Строковые константы */
    public static final String PAGE_INDEX_JSP = "AddClient.jsp";
    /** Экземпляр лога */
    private static final Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    /**
     * Обработка get-запросов
     * @param request Запрос
     * @param response Ответ
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** Для корректной работы с кирилицей переданного парамметра*/
        request.setCharacterEncoding("UTF-8");
        try {
            request.getRequestDispatcher("/views/client/" + PAGE_INDEX_JSP).forward(request, response);
            logger.trace("RequestDispatcher(" + PAGE_INDEX_JSP + ").forward(request, response);");
        } catch (Exception e) {
            logger.fatal("PAGE FATAL ERROR! ", e);
        }
    }

    /**
     * Обработка post-запросов
     * @param request Запрос
     * @param response Ответ
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            /** request получает переменные отправленные через форму*/
            String clientSurname = request.getParameter("clientSurname");
            String clientName = request.getParameter("clientName");
            String clientPatronymic = request.getParameter("clientPatronymic");
            String clientPhoneMobile = request.getParameter("clientPhoneMobile");
            String clientPhoneHome = request.getParameter("clientPhoneHome");
            String clientAddress = request.getParameter("clientAddress");
            String clientBirthDate = request.getParameter("clientBirthDate");
            String passportSeries = request.getParameter("passportSeries");
            String passportNumber = request.getParameter("passportNumber");
            String passportReceived = request.getParameter("passportReceived");
            String passportIssueDate = request.getParameter("passportIssueDate");
            String passportExpiryDate = request.getParameter("passportExpiryDate");

            /** Если строка при заполнении пустая то ставиться " - " */
            if (clientPatronymic.equals("")) clientPatronymic = " - ";
            if (clientPhoneMobile.equals("")) clientPhoneMobile = " - ";
            if (clientPhoneHome.equals("")) clientPhoneHome = " - ";
            if (clientAddress.equals("")) clientAddress = " - ";
            if (clientBirthDate.equals("")) clientBirthDate = " - ";
            if (passportSeries.equals("")) passportSeries = " - ";
            if (passportReceived.equals("")) passportReceived = " - ";
            if (passportIssueDate.equals("")) passportIssueDate = " - ";
            if (passportExpiryDate.equals("")) passportExpiryDate = " - ";

            /**  Добавление объекта Клиент и Паспорт в базу данных через Jdbc */
            CLIENT_CACHE.add(new Client(CLIENT_CACHE.generateId(), clientSurname, clientName,
                    clientPatronymic, clientPhoneMobile, clientPhoneHome, clientAddress,clientBirthDate,
                    new Passport(CLIENT_CACHE.generateId(), passportSeries, passportNumber, passportReceived, passportIssueDate, passportExpiryDate )));
            /** Создание лога добавления объекта в файле log4j_html_info.html. Пример http://devcolibri.com/3413*/
            logger.info("NEW CLIENT [" + "SURNAME='" + clientSurname + '\'' + ", " + "NAME='" + clientName + '\'' + ", " +
                    "PATRONYMIC='" + clientPatronymic + '\'' + ", " + clientPhoneMobile + ", " + clientPhoneHome + ", " + clientAddress + ", " + clientBirthDate + ", " +
                    "PASSPORT=" +   passportSeries + ", " + '\'' +   passportNumber + '\'' + ", " + passportReceived + ", " + passportIssueDate + ", " + passportExpiryDate + "]");
        } catch (Exception e) {
            logger.error("ADD CLIENT ERROR! ", e);
        }
        /** Редирект на главную страницу */
        response.sendRedirect(String.format("%s%s", request.getContextPath(), "/client/output_client"));
        logger.trace("Redirect(" + request.getContextPath() + "/client/output_client);");
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void destroy() {
        super.destroy();
        CLIENT_CACHE.close();
    }
}
