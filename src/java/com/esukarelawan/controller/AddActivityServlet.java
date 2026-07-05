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

@WebServlet(name = "AddActivityServlet", urlPatterns = {"/AddActivityServlet"})
public class AddActivityServlet extends HttpServlet {

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

        ActivityDAO dao = new ActivityDAO();

        ActivityBean activity = new ActivityBean();

        activity.setActivityId(dao.getNextActivityId());

        activity.setTitle(request.getParameter("title"));
        activity.setDescription(request.getParameter("description"));
        activity.setActivityDate(
                Date.valueOf(request.getParameter("activityDate"))
        );

        int hours = Integer.parseInt(
                request.getParameter("hourOffered")
        );

        if (hours <= 0 || hours > 8) {
            response.sendRedirect("addActivity.jsp?error=hours");
            return;
        }

        activity.setHourOffered(hours);

        activity.setOrganizerId(organizer.getOrganizerId());

        boolean success = dao.addActivity(activity);

        if (success) {
            response.sendRedirect("ListActivityServlet");
        } else {
            response.sendRedirect("addActivity.jsp?error=database");
        }
    }
}