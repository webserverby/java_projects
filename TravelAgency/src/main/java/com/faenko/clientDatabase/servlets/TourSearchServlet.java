package com.faenko.clientDatabase.servlets;

import com.faenko.clientDatabase.service.ClassName;
import com.faenko.clientDatabase.store.TourCache;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет поиска тура
 *
 * @author Artem Faenko
 */
public class TourSearchServlet extends HttpServlet {
    /** Экземпляр тура */
    private final TourCache TOUR_CACHE = TourCache.getInstance();
    /** Строковые константы */
    public static final String ATTRIBUTE_MODEL_TO_SEARCH = "found";
    public static final String PAGE_SEARCH_JSP = "SearchTour.jsp";

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
            request.setAttribute(ATTRIBUTE_MODEL_TO_SEARCH, TOUR_CACHE.valuesFound());
            logger.trace("setAttribute(" + ATTRIBUTE_MODEL_TO_SEARCH + ");");
            request.getRequestDispatcher("/views/tour/" + PAGE_SEARCH_JSP).forward(request, response);
            logger.trace("RequestDispatcher(" + PAGE_SEARCH_JSP + ").forward(request, response);");
        } catch (Exception e) {
            logger.error("PAGE ERROR! " + "Redirect(" + request.getContextPath() + "/tour/search_tour);", e);
            response.sendRedirect(String.format("%s%s", request.getContextPath(), "/tour/search_tour"));
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
            String idTour = request.getParameter("idTour");
            String tourNameTour = request.getParameter("tourNameTour");
            String routeCityCome = request.getParameter("routeCityCome");
            String routeCountryCome = request.getParameter("routeCountryCome");

            TOUR_CACHE.find(idTour, tourNameTour, routeCityCome, routeCountryCome);
            logger.info("SEARCH SUCCESSFULLY");
        } catch (Exception e) {
            logger.error("SEARCH ERROR! ", e);
        }
        response.sendRedirect(String.format("%s%s", request.getContextPath(), "/tour/search_tour"));
        logger.trace("Redirect(" + request.getContextPath() + "/tour/search_tour);");
    }
}

