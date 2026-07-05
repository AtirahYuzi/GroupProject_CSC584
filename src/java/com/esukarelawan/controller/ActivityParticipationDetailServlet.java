package com.esukarelawan.controller;

import com.esukarelawan.dao.ActivityDAO;
import com.esukarelawan.dao.ParticipationDAO;
import com.esukarelawan.model.ActivityBean;
import com.esukarelawan.model.ParticipationBean;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ActivityParticipationDetailServlet",
        urlPatterns = {"/ActivityParticipationDetailServlet"})
public class ActivityParticipationDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int activityId = Integer.parseInt(request.getParameter("activityId"));

        ActivityDAO activityDAO = new ActivityDAO();
        ParticipationDAO participationDAO = new ParticipationDAO();

        ActivityBean activity = activityDAO.getActivityById(activityId);

        List<ParticipationBean> participants =
                participationDAO.getParticipantsByActivity(activityId);

        request.setAttribute("activity", activity);
        request.setAttribute("participants", participants);

        request.getRequestDispatcher("activityParticipationDetail.jsp")
                .forward(request, response);
    }
}