package com.faenko.clientDatabase.servlets;

import com.faenko.clientDatabase.models.Route;
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
 * Сервлет страницы добавления тура
 *
 * @author Artem Faenko
 */
public class TourAddServlet extends HttpServlet {
    /** Ссылка на список туров из единственного экземпляра TourCache */
    private final TourCache TOUR_CACHE = TourCache.getInstance();
    /** Строковые константы */
    public static final String PAGE_INDEX_JSP = "AddTour.jsp";
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
        /** Для корректной работы с кирилицей */
        request.setCharacterEncoding("UTF-8");
        try {
            request.getRequestDispatcher("/views/tour/" + PAGE_INDEX_JSP).forward(request, response);
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
            String tourNameTour = request.getParameter("tourNameTour");
            String tourDateBegin = request.getParameter("tourDateBegin");
            String tourDateEnd = request.getParameter("tourDateEnd");
            String tourDayNumber = request.getParameter("tourDayNumber");
            String tourPersonNumber = request.getParameter("tourPersonNumber");
            String tourTourOperator = request.getParameter("tourTourOperator");
            String tourHotel = request.getParameter("tourHotel");
            String tourTypeNumber = request.getParameter("tourTypeNumber");
            String tourFood = request.getParameter("tourFood");
            String tourTourСost = request.getParameter("tourTourСost");
            String routeTransportName = request.getParameter("routeTransportName");
            String routeCityDeparture = request.getParameter("routeCityDeparture");
            String routeCityCome = request.getParameter("routeCityCome");
            String routeCountryCome = request.getParameter("routeCountryCome");
            String routeDateDeparture = request.getParameter("routeDateDeparture");
            String routeDateCome = request.getParameter("routeDateCome");
            String routeDateReturn = request.getParameter("routeDateReturn");

            /** Если строка при заполнении пустая то ставиться " - " */
            if (tourDayNumber.equals("")) tourDayNumber = " - ";
            if (tourPersonNumber.equals("")) tourPersonNumber = " - ";
            if (tourTourOperator.equals("")) tourTourOperator = " - ";
            if (tourHotel.equals("")) tourHotel = " - ";
            if (tourTypeNumber.equals("")) tourTypeNumber = " - ";
            if (tourFood.equals("")) tourFood = " - ";
            if (tourTourСost.equals("")) tourTourСost = " - ";
            if (routeTransportName.equals("")) routeTransportName = " - ";
            if (routeCityDeparture.equals("")) routeCityDeparture = " - ";
            if (routeCityCome.equals("")) routeCityCome = " - ";
            if (routeCountryCome.equals("")) routeCountryCome = " - ";
            if (routeDateDeparture.equals("")) routeDateDeparture = " - ";
            if (routeDateCome.equals("")) routeDateCome = " - ";
            if (routeDateReturn.equals("")) routeDateReturn = " - ";

            /**  Добавление объекта Тур и Маршрут в базу данных через Jdbc */
            TOUR_CACHE.add(new Tour(TOUR_CACHE.generateId(), tourNameTour, tourDateBegin, tourDateEnd, tourDayNumber,
                    tourPersonNumber, tourTourOperator, tourHotel, tourTypeNumber, tourFood, tourTourСost,
                    new Route(TOUR_CACHE.generateId(), routeTransportName, routeCityDeparture, routeCityCome, routeCountryCome, routeDateDeparture, routeDateCome, routeDateReturn )));
            /** Создание лога добавления объекта в файле log4j_html_info.html. Пример http://devcolibri.com/3413*/
            logger.info("NEW TOUR [" + "NAMETOUR='" + tourNameTour + '\'' + ", " + "DATEBEGIN='" + tourDateBegin + '\'' + ", " +
                    "DATEEND='" + tourDateEnd + '\'' + ", " + tourPersonNumber + ", " + tourHotel + ", " + tourTypeNumber + ", " + tourTourСost + ", " +
                    "ROUTE=" +   routeTransportName + ", " + '\'' +  routeCityCome + '\'' + ", " +routeCountryCome + "]");
        } catch (Exception e) {
            logger.error("ADD TOUR ERROR! ", e);
        }
        /** Редирект на главную страницу */
        response.sendRedirect(String.format("%s%s", request.getContextPath(), "/tour/output_tour"));
        logger.trace("Redirect(" + request.getContextPath() + "/tour/output_tour);");
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void destroy() {
        super.destroy();
        TOUR_CACHE.close();
    }
}
