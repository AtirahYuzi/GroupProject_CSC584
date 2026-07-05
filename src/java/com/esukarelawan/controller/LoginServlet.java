package com.esukarelawan.controller;

import com.esukarelawan.dao.UserDAO;
import com.esukarelawan.model.OrganizerBean;
import com.esukarelawan.model.VolunteerBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        OrganizerBean organizer = dao.loginOrganizer(username, password);
        if (organizer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("role", "organizer");
            session.setAttribute("organizer", organizer);
            session.setAttribute("username", organizer.getUsername());
            response.sendRedirect("ListActivityServlet");
            return;
        }

        VolunteerBean volunteer = dao.loginVolunteer(username, password);
        if (volunteer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("role", "volunteer");
            session.setAttribute("volunteer", volunteer);
            session.setAttribute("username", volunteer.getUsername());
            response.sendRedirect("StudentDashboardServlet");
            return;
        }

        response.sendRedirect("index.html?error=true");
    }
}
