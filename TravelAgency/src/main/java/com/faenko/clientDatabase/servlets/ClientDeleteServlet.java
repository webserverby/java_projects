package com.faenko.clientDatabase.servlets;

import com.faenko.clientDatabase.models.Client;
import com.faenko.clientDatabase.service.ClassName;
import com.faenko.clientDatabase.store.ClientCache;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет обработки удаления клиента
 *
 * @author Artem Faenko
 */
public class ClientDeleteServlet extends HttpServlet {
    /** Экземпляр клиента */
    private static final ClientCache CLIENT_CACHE = ClientCache.getInstance();

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
        try {
            Client client = CLIENT_CACHE.get(Integer.valueOf(request.getParameter("id")));
            /** Получаем id клиента и удаляем */
            CLIENT_CACHE.delete(client.getId());
            logger.info("DELETED CLIENT [" +
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
        } catch (Exception e) {
            logger.error("DELETE ERROR! ", e);
        }
        /** Редирект на главную страницу */
        response.sendRedirect(String.format("%s%s", request.getContextPath(), "/client/output_client"));
        logger.trace("Redirect(" + request.getContextPath() + "/client/output_client);");
    }
}