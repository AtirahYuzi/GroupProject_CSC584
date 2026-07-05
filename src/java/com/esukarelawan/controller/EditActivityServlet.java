package com.esukarelawan.controller;

import com.esukarelawan.dao.ActivityDAO;
import com.esukarelawan.model.ActivityBean;
import com.esukarelawan.model.OrganizerBean;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EditActivityServlet", urlPatterns = {"/EditActivityServlet"})
public class EditActivityServlet extends HttpServlet {

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

        int activityId = Integer.parseInt(request.getParameter("id"));

        ActivityDAO dao = new ActivityDAO();
        ActivityBean activity = dao.getActivityByIdAndOrganizer(
                activityId,
                organizer.getOrganizerId()
        );

        if (activity == null) {
            response.sendRedirect("ListActivityServlet?error=unauthorized");
            return;
        }

        request.setAttribute("activity", activity);
        request.getRequestDispatcher("editActivity.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("organizer") == null) {
            response.sendRedirect("index.html?error=true");
            return;
        }

        OrganizerBean organizer = (OrganizerBean) session.getAttribute("organizer");

        try {
            int activityId = Integer.parseInt(request.getParameter("activityId"));

            ActivityDAO dao = new ActivityDAO();
            ActivityBean existingActivity = dao.getActivityByIdAndOrganizer(
                    activityId,
                    organizer.getOrganizerId()
            );

            if (existingActivity == null) {
                response.sendRedirect("ListActivityServlet?error=unauthorized");
                return;
            }

            String title = request.getParameter("title");
            String description = request.getParameter("description");
            Date activityDate = Date.valueOf(request.getParameter("activityDate"));

            int hourOffered = Integer.parseInt(request.getParameter("hourOffered"));

            if (hourOffered < 1 || hourOffered > 8) {
                response.sendRedirect("EditActivityServlet?id="
                        + activityId + "&error=hours");
                return;
            }

            ActivityBean activity = new ActivityBean();

            activity.setActivityId(activityId);
            activity.setTitle(title);
            activity.setDescription(description);
            activity.setActivityDate(activityDate);
            activity.setHourOffered(hourOffered);
            activity.setOrganizerId(organizer.getOrganizerId());

            boolean success = dao.updateActivity(activity);

            if (success) {
                response.sendRedirect("ListActivityServlet");
            } else {
                response.sendRedirect("EditActivityServlet?id="
                        + activityId + "&error=database");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListActivityServlet");
        }
    }
}
