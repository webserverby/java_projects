package com.faenko.clientDatabase.servlets;

import com.faenko.clientDatabase.service.ClassName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет главной страницы
 *
 * @author Artem Faenko
 */
public class IndexServlet extends HttpServlet {

    public static final String PAGE_INDEX_JSP = "index.jsp";
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
            request.getRequestDispatcher("/" + PAGE_INDEX_JSP).forward(request, response);
            logger.trace("RequestDispatcher(" + PAGE_INDEX_JSP + ").forward(request, response);");
        } catch (Exception e) {
            logger.fatal("PAGE FATAL ERROR! ", e);
        }

    }


}
