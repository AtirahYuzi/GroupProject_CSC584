package com.esukarelawan.controller;

import com.esukarelawan.dao.ActivityDAO;
import com.esukarelawan.dao.ParticipationDAO;
import com.esukarelawan.model.ActivityBean;
import com.esukarelawan.model.ParticipationBean;
import com.esukarelawan.model.VolunteerBean;
import com.esukarelawan.dao.MedalDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "StudentDashboardServlet", urlPatterns = {"/StudentDashboardServlet"})
public class StudentDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("volunteer") == null) {
            response.sendRedirect("index.html?error=true");
            return;
        }

        VolunteerBean volunteer =
                (VolunteerBean) session.getAttribute("volunteer");

        int volunteerId = volunteer.getVolunteerId();
        String studentName = volunteer.getFullName();

        ActivityDAO activityDAO = new ActivityDAO();
        ParticipationDAO participationDAO = new ParticipationDAO();

        List<ActivityBean> activities = activityDAO.getAllActivities();

        List<ParticipationBean> history =
                participationDAO.getParticipationHistory(volunteerId);

        HashMap<Integer, String> joinedStatus =
                new HashMap<Integer, String>();

        for (ActivityBean activity : activities) {
            String status = participationDAO.getParticipationStatus(
                    activity.getActivityId(),
                    volunteerId
            );

            if (status != null) {
                joinedStatus.put(activity.getActivityId(), status);
            }
        }

        int totalHours =
                participationDAO.getTotalHoursByVolunteer(volunteerId);

        int totalActivities =
                participationDAO.getTotalActivitiesByVolunteer(volunteerId);
        
        MedalDAO medalDAO = new MedalDAO();

medalDAO.awardEligibleMedals(volunteerId, totalHours);

int medalsEarned = medalDAO.getMedalsEarned(volunteerId);

int nextMedalHours = 20;

if (totalHours >= 20) {
    nextMedalHours = 50;
}

if (totalHours >= 50) {
    nextMedalHours = 100;
}

if (totalHours >= 100) {
    nextMedalHours = 100;
}

        request.setAttribute("activities", activities);
        request.setAttribute("history", history);
        request.setAttribute("totalHours", totalHours);
        request.setAttribute("totalActivities", totalActivities);
        request.setAttribute("medalsEarned", medalsEarned);
        request.setAttribute("nextMedalHours", nextMedalHours);
        request.setAttribute("studentName", studentName);
        request.setAttribute("joinedStatus", joinedStatus);

        request.getRequestDispatcher("student.jsp").forward(request, response);
    }
}