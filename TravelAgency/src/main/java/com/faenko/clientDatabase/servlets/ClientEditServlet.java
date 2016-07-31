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
 * Сервлет обработки редактирования клиента
 *
 * @author Artem Faenko
 */
public class ClientEditServlet extends HttpServlet {
    /** Экземпляр клиента */
    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();
    /** Строковые константы */
    public static final String ATTRIBUTE_MODEL_TO_EDIT = "clients";
    public static final String PAGE_EDIT_JSP = "EditClient.jsp";

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
        request.setCharacterEncoding("UTF-8");
        try {
            Client client = CLIENT_CACHE.get(Integer.valueOf(request.getParameter("id")));
            logger.info("EDITING CLIENT [" +
                    "ID=" +    client.getId() + ", " +
                    "SURNAME='" + client.getSurname() + '\'' + ", " +
                    "NAME='" + client.getName() + '\'' + ", " +
                    "PATRONYMIC='" + client.getPatronymic() + '\'' + ", " +
                    client.getPhoneMobile() + ", " +
                    client.getPhoneHome() + ", " +
                    client.getAddress() + ", " +
                    client.getBirthDate() + ", " +
                    "PASSPORT=" + client.getPassport().getSeries() + ", " +
                    client.getPassport().getNumber() +  ", " +
                    client.getPassport().getReceived() + ", " +
                    client.getPassport().getIssueDate() + ", " +
                    client.getPassport().getExpiryDate() + "]");
            request.setAttribute("client", client);
            logger.trace("setAttribute(" + ATTRIBUTE_MODEL_TO_EDIT + ");");
            request.getRequestDispatcher("/views/client/" + PAGE_EDIT_JSP).forward(request, response);
            logger.trace("RequestDispatcher(" + PAGE_EDIT_JSP + ").forward(request, response);");
        } catch (Exception e) {
            logger.error("PAGE ERROR! " + "Redirect(" + request.getContextPath() + "/client/output_client);", e);
            response.sendRedirect(String.format("%s%s", request.getContextPath(), "/client/output_client"));
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
            int client_id = Integer.valueOf(request.getParameter("id"));
            int passport_id = Integer.valueOf(request.getParameter("passport_id"));
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
            CLIENT_CACHE.edit(new Client(client_id, clientSurname, clientName, clientPatronymic,
                    clientPhoneMobile, clientPhoneHome, clientAddress,clientBirthDate,
                    new Passport(passport_id, passportSeries, passportNumber, passportReceived, passportIssueDate, passportExpiryDate)));
            logger.info("CLIENT EDITED [" +
                    "ID=" +  client_id + ", " + "SURNAME='" + clientSurname + '\'' + ", " +
                    "NAME='" + clientName + '\'' + ", " + "PATRONYMIC='" + clientPatronymic + '\'' + ", " +
                    clientPhoneMobile + ", " + clientPhoneHome + ", " + clientAddress + ", " + clientBirthDate + ", " +
                    "PASSPORT=" +  passportSeries + ", " + '\'' +   passportNumber + '\'' + ", " + passportReceived + ", " +
                    passportIssueDate + ", " + passportExpiryDate + "]");
        } catch (Exception e) {
            logger.error("EDIT ERROR! ", e);
        }
        /** Редирект на главную страницу */
        response.sendRedirect(String.format("%s%s", request.getContextPath(), "/client/output_client"));
        logger.trace("Redirect(" + request.getContextPath() + "/client/output_client);");
    }
}