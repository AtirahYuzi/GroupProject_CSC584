package com.esukarelawan.controller;

import com.esukarelawan.dao.ActivityDAO;
import com.esukarelawan.model.ActivityBean;
import com.esukarelawan.model.OrganizerBean;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteActivityServlet", urlPatterns = {"/DeleteActivityServlet"})
public class DeleteActivityServlet extends HttpServlet {

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

            ActivityDAO dao = new ActivityDAO();
            ActivityBean activity = dao.getActivityByIdAndOrganizer(
                    activityId,
                    organizer.getOrganizerId()
            );

            if (activity == null) {
                response.sendRedirect("ListActivityServlet?delete=notfound");
                return;
            }

            request.setAttribute("activity", activity);
            request.getRequestDispatcher("deleteActivity.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListActivityServlet?delete=failed");
        }
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
            boolean success = dao.deleteActivity(activityId, organizer.getOrganizerId());

            if (success) {
                response.sendRedirect("ListActivityServlet?delete=success");
            } else {
                response.sendRedirect("ListActivityServlet?delete=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListActivityServlet?delete=failed");
        }
    }
}
