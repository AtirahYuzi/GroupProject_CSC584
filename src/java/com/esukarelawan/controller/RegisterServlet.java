package com.esukarelawan.controller;

import com.esukarelawan.dao.UserDAO;
import com.esukarelawan.model.OrganizerBean;
import com.esukarelawan.model.VolunteerBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");
        String fullName = request.getParameter("full_name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");

        UserDAO dao = new UserDAO();

        if (dao.isUsernameTaken(username)) {
            response.sendRedirect("register.jsp?error=username");
            return;
        }

        boolean success = false;

        if ("volunteer".equalsIgnoreCase(role)) {
            VolunteerBean volunteer = new VolunteerBean();
            volunteer.setVolunteerId(dao.getNextVolunteerId());
            volunteer.setFullName(fullName);
            volunteer.setUsername(username);
            volunteer.setPassword(password);
            volunteer.setEmail(email);
            volunteer.setPhoneNumber(phoneNumber);

            success = dao.registerVolunteer(volunteer);
        } else if ("organizer".equalsIgnoreCase(role)) {
            OrganizerBean organizer = new OrganizerBean();
            organizer.setOrganizerId(dao.getNextOrganizerId());
            organizer.setFullName(fullName);
            organizer.setUsername(username);
            organizer.setPassword(password);
            organizer.setEmail(email);
            organizer.setPhoneNumber(phoneNumber);

            success = dao.registerOrganizer(organizer);
        }

        if (success) {
            response.sendRedirect("index.html?registered=true");
        } else {
            response.sendRedirect("register.jsp?error=true");
        }
    }
}
