package com.faenko.clientDatabase.servlets;

import com.faenko.clientDatabase.service.ClassName;
import com.faenko.clientDatabase.store.ClientCache;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет поиска клиента
 *
 * @author Artem Faenko
 */
public class ClientSearchServlet extends HttpServlet {
    /** Экземпляр клиента */
    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();
    /** Строковые константы */
    public static final String ATTRIBUTE_MODEL_TO_SEARCH = "found";
    public static final String PAGE_SEARCH_JSP = "SearchClient.jsp";

    private static final Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    /**
     * Обработка get-запросов
     * @param request Запрос
     * @param response Ответ
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            logger.info("SEARCHING CLIENTS");
            request.setAttribute(ATTRIBUTE_MODEL_TO_SEARCH, CLIENT_CACHE.valuesFound());
            logger.trace("setAttribute(" + ATTRIBUTE_MODEL_TO_SEARCH + ");");
            request.getRequestDispatcher("/views/client/" + PAGE_SEARCH_JSP).forward(request, response);
            logger.trace("RequestDispatcher(" + PAGE_SEARCH_JSP + ").forward(request, response);");
        } catch (Exception e) {
            logger.error("PAGE ERROR! " + "Redirect(" + request.getContextPath() + "/client/search_client);", e);
            response.sendRedirect(String.format("%s%s", request.getContextPath(), "/client/search_client"));
        }
    }

    /**
     * Обработка post-запросов
     * @param request Запрос
     * @param response Ответ
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String idClient = request.getParameter("idClient");
            String clientSurname = request.getParameter("clientSurname");
            String clientName = request.getParameter("clientName");
            String passportNumber = request.getParameter("passportNumber");

            CLIENT_CACHE.find(idClient, clientSurname, clientName, passportNumber);
            logger.info("SEARCH SUCCESSFULLY");
        } catch (Exception e) {
            logger.error("SEARCH ERROR! ", e);
        }
        response.sendRedirect(String.format("%s%s", request.getContextPath(), "/client/search_client"));
        logger.trace("Redirect(" + request.getContextPath() + "/client/search_client);");
    }
}