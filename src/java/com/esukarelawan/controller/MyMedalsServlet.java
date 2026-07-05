package com.esukarelawan.controller;

import com.esukarelawan.dao.MedalDAO;
import com.esukarelawan.model.MedalBean;
import com.esukarelawan.model.VolunteerBean;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "MyMedalsServlet", urlPatterns = {"/MyMedalsServlet"})
public class MyMedalsServlet extends HttpServlet {

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

        MedalDAO medalDAO = new MedalDAO();

        List<MedalBean> medals =
                medalDAO.getVolunteerMedals(volunteerId);

        request.setAttribute("medals", medals);

        request.getRequestDispatcher("myMedals.jsp").forward(request, response);
    }
}