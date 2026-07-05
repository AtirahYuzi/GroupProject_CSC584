package com.esukarelawan.controller;

import com.esukarelawan.dao.ActivityDAO;
import com.esukarelawan.dao.ParticipationDAO;
import com.esukarelawan.model.ActivityBean;
import com.esukarelawan.model.OrganizerBean;
import com.esukarelawan.model.ParticipationBean;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AttendanceServlet", urlPatterns = {"/AttendanceServlet"})
public class AttendanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("organizer") == null) {
            response.sendRedirect("index.html?error=true");
            return;
        }

        OrganizerBean organizer = (OrganizerBean) session.getAttribute("organizer");

        try {
            int activityId = Integer.parseInt(request.getParameter("id"));

            ActivityDAO activityDAO = new ActivityDAO();
            ParticipationDAO participationDAO = new ParticipationDAO();

            ActivityBean activity = activityDAO.getActivityByIdAndOrganizer(
                    activityId,
                    organizer.getOrganizerId()
            );

            if (activity == null) {
                response.sendRedirect("ListActivityServlet?error=unauthorized");
                return;
            }

            List<ParticipationBean> participantList =
                    participationDAO.getParticipantsByActivity(activityId);

            request.setAttribute("activity", activity);
            request.setAttribute("participantList", participantList);

            RequestDispatcher rd = request.getRequestDispatcher("attendance.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListActivityServlet");
        }
    }
}
