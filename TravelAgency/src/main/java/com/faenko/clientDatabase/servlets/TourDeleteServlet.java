package com.faenko.clientDatabase.servlets;


import com.faenko.clientDatabase.models.Tour;
import com.faenko.clientDatabase.service.ClassName;
import com.faenko.clientDatabase.store.TourCache;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет обработки удаления тура
 *
 * @author Artem Faenko
 */
public class TourDeleteServlet extends HttpServlet {
    /** Ссылка на список туров из единственного экземпляра TourCache */
    private static final TourCache TOUR_CACHE = TourCache.getInstance();
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
            Tour tour = TOUR_CACHE.get(Integer.valueOf(request.getParameter("id")));
            /** Получаем id тура и удаляем */
            TOUR_CACHE.delete(tour.getId());
            logger.info("DELETED TOUR [" +
                    "ID=" +    tour.getId() + ", " +
                    "NAMETOUR='" + tour.getNameTour() + '\'' + ", " +
                    "DATEBEGIN='" + tour.getDateBegin() + '\'' + ", " +
                    "DATEEND='" + tour.getDateEnd() + '\'' + ", " +
                    tour.getPersonNumber() + ", " +
                    tour.getHotel() + ", " +
                    tour.getTypeNumber() + ", " +
                    tour.getTourСost() + ", " +
                    "ROUTE=" + tour.getRoute().getTransportName() + ", " +
                    tour.getRoute().getCityCome() +  ", " +
                    tour.getRoute().getCountryCome() + "]");

        } catch (Exception e) {
            logger.error("DELETE ERROR! ", e);
        }
        /** Редирект на главную страницу */
        response.sendRedirect(String.format("%s%s", request.getContextPath(), "/tour/output_tour"));
        logger.trace("Redirect(" + request.getContextPath() + "/tour/output_tour);");
    }
}