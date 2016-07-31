package com.faenko.clientDatabase.servlets;

import com.faenko.clientDatabase.store.ClientCache;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientAddServletTest extends Mockito {

    final ClientCache clientCache = ClientCache.getInstance();

    @Test
    public void testClientAddServlet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        ClientAddServlet clientAddServlet = new ClientAddServlet();

        when(request.getRequestDispatcher("/views/client/AddClient.jsp")).thenReturn(dispatcher);
        clientAddServlet.doGet(request, response);
        verify(dispatcher).forward(request, response);

        when(request.getParameter("clientSurname")).thenReturn("test");
        when(request.getParameter("clientName")).thenReturn("test");
        when(request.getParameter("clientPatronymic")).thenReturn(null);
        when(request.getParameter("clientPhoneMobile")).thenReturn(null);
        when(request.getParameter("clientPhoneHome")).thenReturn(null);
        when(request.getParameter("clientAddress")).thenReturn(null);
        when(request.getParameter("clientBirthDate")).thenReturn(null);
        when(request.getParameter("passportSeries")).thenReturn(null);
        when(request.getParameter("passportNumber")).thenReturn("test");
        when(request.getParameter("passportReceived")).thenReturn(null);
        when(request.getParameter("passportIssueDate")).thenReturn(null);
        when(request.getParameter("passportExpiryDate")).thenReturn(null);

        clientAddServlet.doPost(request, response);

        when(request.getParameter("clientSurname")).thenReturn("test");
        when(request.getParameter("clientName")).thenReturn("test");
        when(request.getParameter("clientPatronymic")).thenReturn("test");
        when(request.getParameter("clientPhoneMobile")).thenReturn("test");
        when(request.getParameter("clientPhoneHome")).thenReturn("test");
        when(request.getParameter("clientAddress")).thenReturn("test");
        when(request.getParameter("clientBirthDate")).thenReturn("test");
        when(request.getParameter("passportSeries")).thenReturn("test");
        when(request.getParameter("passportNumber")).thenReturn("test");
        when(request.getParameter("passportReceived")).thenReturn("test");
        when(request.getParameter("passportIssueDate")).thenReturn("test");
        when(request.getParameter("passportExpiryDate")).thenReturn("test");

        clientAddServlet.doPost(request, response);

        clientAddServlet.destroy();



    }




}