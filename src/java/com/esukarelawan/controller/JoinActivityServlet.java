package com.esukarelawan.controller;

import com.esukarelawan.dao.ParticipationDAO;
import com.esukarelawan.model.VolunteerBean;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "JoinActivityServlet", urlPatterns = {"/JoinActivityServlet"})
public class JoinActivityServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int activityId = Integer.parseInt(request.getParameter("activityId"));

            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("volunteer") == null) {
                response.sendRedirect("index.html?error=true");
                return;
            }

            VolunteerBean volunteer = (VolunteerBean) session.getAttribute("volunteer");
            int volunteerId = volunteer.getVolunteerId();

            ParticipationDAO dao = new ParticipationDAO();

            if (dao.hasJoined(activityId, volunteerId)) {
                response.sendRedirect("StudentDashboardServlet?join=exists");
                return;
            }

            boolean success = dao.joinActivity(activityId, volunteerId);

            if (success) {
                response.sendRedirect("StudentDashboardServlet?join=success");
            } else {
                response.sendRedirect("StudentDashboardServlet?join=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("StudentDashboardServlet?join=failed");
        }
    }
}