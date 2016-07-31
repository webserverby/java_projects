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
 * Сервлет страницы вывода клиентов
 *
 * @author Artem Faenko
 */
public class ClientOutputServlet extends HttpServlet {
    /** Ссылка на список клиентов из единственного экземпляра ClientCache */
    private final ClientCache CLIENT_CACHE = ClientCache.getInstance();
    /** Строковые константы */
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "clients";
    public static final String PAGE_INDEX_JSP = "OutputClient.jsp";
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
            /**
             * Список клиентов забираеться(CLIENT_CACHE.values) из JdbcStorage и передаеться
             * на страницу OutputClient.jsp через атрибут "clients" и выводиться в таблицу
             */
            request.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, CLIENT_CACHE.values());
            /** Вывод лога объекта */
            logger.trace("setAttribute(" + ATTRIBUTE_MODEL_TO_VIEW + ");");
            request.getRequestDispatcher("/views/client/" + PAGE_INDEX_JSP).forward(request, response);
            logger.trace("RequestDispatcher(" + PAGE_INDEX_JSP + ").forward(request, response);");
        } catch (Exception e) {
            logger.fatal("PAGE FATAL ERROR! ", e);
        }
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
