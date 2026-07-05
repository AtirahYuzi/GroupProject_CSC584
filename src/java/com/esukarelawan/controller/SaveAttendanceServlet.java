package com.esukarelawan.controller;

import com.esukarelawan.dao.ActivityDAO;
import com.esukarelawan.dao.ParticipationDAO;
import com.esukarelawan.model.ActivityBean;
import com.esukarelawan.model.OrganizerBean;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "SaveAttendanceServlet", urlPatterns = {"/SaveAttendanceServlet"})
public class SaveAttendanceServlet extends HttpServlet {

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
            int hourOffered = Integer.parseInt(request.getParameter("hourOffered"));

            ActivityDAO activityDAO = new ActivityDAO();
            ActivityBean activity = activityDAO.getActivityByIdAndOrganizer(
                    activityId,
                    organizer.getOrganizerId()
            );

            if (activity == null) {
                response.sendRedirect("ListActivityServlet?error=unauthorized");
                return;
            }

            String[] attendedVolunteers = request.getParameterValues("attended");

            ParticipationDAO dao = new ParticipationDAO();

            dao.resetAttendance(activityId);

            if (attendedVolunteers != null) {
                for (String volunteerIdStr : attendedVolunteers) {
                    int volunteerId = Integer.parseInt(volunteerIdStr);

                    dao.updateAttendance(
                            activityId,
                            volunteerId,
                            "Completed",
                            hourOffered
                    );
                }
            }

            response.sendRedirect("ListActivityServlet?attendance=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListActivityServlet?attendance=failed");
        }
    }
}
