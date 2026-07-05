package com.esukarelawan.controller;

import com.esukarelawan.dao.ActivityDAO;
import com.esukarelawan.dao.OrganizerDAO;
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

@WebServlet(name = "ListActivityServlet", urlPatterns = {"/ListActivityServlet"})
public class ListActivityServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("organizer") == null) {
            response.sendRedirect("index.html?error=true");
            return;
        }

        OrganizerBean organizer = (OrganizerBean) session.getAttribute("organizer");
        int organizerId = organizer.getOrganizerId();

        OrganizerDAO organizerDAO = new OrganizerDAO();
        ActivityDAO activityDAO = new ActivityDAO();
        ParticipationDAO participationDAO = new ParticipationDAO();

        organizer = organizerDAO.getOrganizerById(organizerId);
        List<ActivityBean> activityList = activityDAO.getActivitiesByOrganizer(organizerId);
        List<ParticipationBean> participationList =
                participationDAO.getParticipationsByOrganizer(organizerId);

        int totalActivities = activityDAO.getTotalActivitiesByOrganizer(organizerId);
        int totalVolunteers = participationDAO.getTotalVolunteersByOrganizer(organizerId);
        int totalHours = participationDAO.getTotalCommunityHoursByOrganizer(organizerId);

        request.setAttribute("organizer", organizer);
        request.setAttribute("activityList", activityList);
        request.setAttribute("participationList", participationList);
        request.setAttribute("totalActivities", totalActivities);
        request.setAttribute("totalVolunteers", totalVolunteers);
        request.setAttribute("totalHours", totalHours);

        RequestDispatcher rd =
                request.getRequestDispatcher("organizer.jsp");

        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
}