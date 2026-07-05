package com.esukarelawan.dao;

import com.esukarelawan.model.ParticipationBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ParticipationDAO {

    public List<ParticipationBean> getAllParticipations() {
        List<ParticipationBean> list = new ArrayList<ParticipationBean>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT V.full_name, A.title, P.status, P.hour_earned, "
                    + "P.activity_id, P.volunteer_id "
                    + "FROM Participation P "
                    + "JOIN Volunteer V ON P.volunteer_id = V.volunteer_id "
                    + "JOIN Activity A ON P.activity_id = A.activity_id "
                    + "ORDER BY A.activity_date DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParticipationBean p = new ParticipationBean();

                p.setActivityId(rs.getInt("activity_id"));
                p.setVolunteerId(rs.getInt("volunteer_id"));
                p.setVolunteerName(rs.getString("full_name"));
                p.setActivityTitle(rs.getString("title"));
                p.setStatus(rs.getString("status"));
                p.setHourEarned(rs.getInt("hour_earned"));

                list.add(p);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ParticipationBean> getParticipationsByOrganizer(int organizerId) {
        List<ParticipationBean> list = new ArrayList<ParticipationBean>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql =
        "SELECT A.activity_id, A.title, A.hour_offered, A.activity_date, "
        + "COUNT(P.volunteer_id) AS total_participants, "
        + "MAX(P.status) AS status "
        + "FROM Activity A "
        + "LEFT JOIN Participation P "
        + "ON A.activity_id = P.activity_id "
        + "WHERE A.organizer_id = ? "
        + "GROUP BY A.activity_id, A.title, A.hour_offered, A.activity_date "
        + "ORDER BY A.activity_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParticipationBean p = new ParticipationBean();

                p.setActivityId(rs.getInt("activity_id"));
                p.setActivityTitle(rs.getString("title"));
                p.setHourEarned(rs.getInt("hour_offered"));
                p.setStatus(rs.getString("status"));
                p.setTotalParticipants(rs.getInt("total_participants"));

                list.add(p);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ParticipationBean> getParticipantsByActivity(int activityId) {
        List<ParticipationBean> list = new ArrayList<ParticipationBean>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT P.activity_id, P.volunteer_id, V.full_name, "
                    + "P.status, P.hour_earned "
                    + "FROM Participation P "
                    + "JOIN Volunteer V ON P.volunteer_id = V.volunteer_id "
                    + "WHERE P.activity_id = ? "
                    + "ORDER BY V.full_name";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, activityId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParticipationBean p = new ParticipationBean();

                p.setActivityId(rs.getInt("activity_id"));
                p.setVolunteerId(rs.getInt("volunteer_id"));
                p.setVolunteerName(rs.getString("full_name"));
                p.setStatus(rs.getString("status"));
                p.setHourEarned(rs.getInt("hour_earned"));

                list.add(p);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateAttendance(int activityId, int volunteerId,
            String status, int hourEarned) {

        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE Participation "
                    + "SET status = ?, hour_earned = ? "
                    + "WHERE activity_id = ? AND volunteer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, hourEarned);
            ps.setInt(3, activityId);
            ps.setInt(4, volunteerId);

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

    public int getTotalVolunteersByOrganizer(int organizerId) {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT COUNT(DISTINCT P.volunteer_id) AS total "
                    + "FROM Participation P "
                    + "JOIN Activity A ON P.activity_id = A.activity_id "
                    + "WHERE A.organizer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public int getTotalCommunityHoursByOrganizer(int organizerId) {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT SUM(P.hour_earned) AS total "
                    + "FROM Participation P "
                    + "JOIN Activity A ON P.activity_id = A.activity_id "
                    + "WHERE A.organizer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public int getTotalVolunteers() {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total FROM Volunteer";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public int getTotalCommunityHours() {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT SUM(hour_earned) AS total FROM Participation";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public boolean resetAttendance(int activityId) {
        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE Participation "
                    + "SET status = ?, hour_earned = ? "
                    + "WHERE activity_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "Pending");
            ps.setInt(2, 0);
            ps.setInt(3, activityId);

            int row = ps.executeUpdate();

            if (row >= 0) {
                success = true;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public List<ParticipationBean> getParticipationHistory(int volunteerId) {
        List<ParticipationBean> list = new ArrayList<ParticipationBean>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT P.activity_id, P.volunteer_id, A.title, "
                    + "P.status, P.hour_earned "
                    + "FROM Participation P "
                    + "JOIN Activity A ON P.activity_id = A.activity_id "
                    + "WHERE P.volunteer_id = ? "
                    + "ORDER BY A.activity_date DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, volunteerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParticipationBean p = new ParticipationBean();

                p.setActivityId(rs.getInt("activity_id"));
                p.setVolunteerId(rs.getInt("volunteer_id"));
                p.setActivityTitle(rs.getString("title"));
                p.setStatus(rs.getString("status"));
                p.setHourEarned(rs.getInt("hour_earned"));

                list.add(p);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getTotalHoursByVolunteer(int volunteerId) {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT SUM(hour_earned) AS total "
                    + "FROM Participation "
                    + "WHERE volunteer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, volunteerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public int getTotalActivitiesByVolunteer(int volunteerId) {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total "
                    + "FROM Participation "
                    + "WHERE volunteer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, volunteerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public boolean hasJoined(int activityId, int volunteerId) {
        boolean joined = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM Participation "
                    + "WHERE activity_id = ? AND volunteer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, activityId);
            ps.setInt(2, volunteerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                joined = true;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return joined;
    }

    public boolean joinActivity(int activityId, int volunteerId) {
        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Participation "
                    + "(activity_id, volunteer_id, status, hour_earned) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, activityId);
            ps.setInt(2, volunteerId);
            ps.setString(3, "Pending");
            ps.setInt(4, 0);

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

    public String getParticipationStatus(int activityId, int volunteerId) {
        String status = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT status FROM Participation "
                    + "WHERE activity_id = ? AND volunteer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, activityId);
            ps.setInt(2, volunteerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                status = rs.getString("status");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    
  
   
   
}