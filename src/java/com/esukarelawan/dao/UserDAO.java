package com.esukarelawan.dao;

import com.esukarelawan.model.OrganizerBean;
import com.esukarelawan.model.VolunteerBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public OrganizerBean loginOrganizer(String username, String password) {
        OrganizerBean organizer = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM Organizer WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                organizer = new OrganizerBean();
                organizer.setOrganizerId(rs.getInt("organizer_id"));
                organizer.setUsername(rs.getString("username"));
                organizer.setPassword(rs.getString("password"));
                organizer.setEmail(rs.getString("email"));
                organizer.setFullName(rs.getString("full_name"));
                organizer.setPhoneNumber(rs.getString("phone_number"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return organizer;
    }

    public VolunteerBean loginVolunteer(String username, String password) {
        VolunteerBean volunteer = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM Volunteer WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                volunteer = new VolunteerBean();
                volunteer.setVolunteerId(rs.getInt("volunteer_id"));
                volunteer.setUsername(rs.getString("username"));
                volunteer.setPassword(rs.getString("password"));
                volunteer.setEmail(rs.getString("email"));
                volunteer.setFullName(rs.getString("full_name"));
                volunteer.setPhoneNumber(rs.getString("phone_number"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return volunteer;
    }

    public boolean isUsernameTaken(String username) {
        boolean taken = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql1 = "SELECT username FROM Organizer WHERE username = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, username);

            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                conn.close();
                return true;
            }

            String sql2 = "SELECT username FROM Volunteer WHERE username = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, username);

            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                taken = true;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return taken;
    }

    public int getNextVolunteerId() {
        int nextId = 1;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT MAX(volunteer_id) AS max_id FROM Volunteer";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nextId = rs.getInt("max_id") + 1;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextId;
    }

    public int getNextOrganizerId() {
        int nextId = 1;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT MAX(organizer_id) AS max_id FROM Organizer";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nextId = rs.getInt("max_id") + 1;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextId;
    }

    public boolean registerVolunteer(VolunteerBean volunteer) {
        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Volunteer "
                    + "(volunteer_id, username, password, email, full_name, phone_number) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, volunteer.getVolunteerId());
            ps.setString(2, volunteer.getUsername());
            ps.setString(3, volunteer.getPassword());
            ps.setString(4, volunteer.getEmail());
            ps.setString(5, volunteer.getFullName());
            ps.setString(6, volunteer.getPhoneNumber());

            int row = ps.executeUpdate();
            if (row > 0) {
                success = true;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean registerOrganizer(OrganizerBean organizer) {
        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Organizer "
                    + "(organizer_id, username, password, email, full_name, phone_number) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizer.getOrganizerId());
            ps.setString(2, organizer.getUsername());
            ps.setString(3, organizer.getPassword());
            ps.setString(4, organizer.getEmail());
            ps.setString(5, organizer.getFullName());
            ps.setString(6, organizer.getPhoneNumber());

            int row = ps.executeUpdate();
            if (row > 0) {
                success = true;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}
