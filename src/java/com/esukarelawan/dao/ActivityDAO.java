package com.esukarelawan.dao;

import com.esukarelawan.model.ActivityBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {

    public List<ActivityBean> getAllActivities() {
        List<ActivityBean> list = new ArrayList<ActivityBean>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT A.*, O.full_name "
                    + "FROM Activity A "
                    + "JOIN Organizer O ON A.organizer_id = O.organizer_id "
                    + "ORDER BY A.activity_id DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ActivityBean activity = new ActivityBean();

                activity.setActivityId(rs.getInt("activity_id"));
                activity.setTitle(rs.getString("title"));
                activity.setDescription(rs.getString("description"));
                activity.setActivityDate(rs.getDate("activity_date"));
                activity.setHourOffered(rs.getInt("hour_offered"));
                activity.setOrganizerId(rs.getInt("organizer_id"));
                activity.setOrganizerName(rs.getString("full_name"));

                list.add(activity);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ActivityBean> getActivitiesByOrganizer(int organizerId) {
        List<ActivityBean> list = new ArrayList<ActivityBean>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT A.*, O.full_name "
                    + "FROM Activity A "
                    + "JOIN Organizer O ON A.organizer_id = O.organizer_id "
                    + "WHERE A.organizer_id = ? "
                    + "ORDER BY A.activity_id DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ActivityBean activity = new ActivityBean();

                activity.setActivityId(rs.getInt("activity_id"));
                activity.setTitle(rs.getString("title"));
                activity.setDescription(rs.getString("description"));
                activity.setActivityDate(rs.getDate("activity_date"));
                activity.setHourOffered(rs.getInt("hour_offered"));
                activity.setOrganizerId(rs.getInt("organizer_id"));
                activity.setOrganizerName(rs.getString("full_name"));

                list.add(activity);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getTotalActivitiesByOrganizer(int organizerId) {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total FROM Activity WHERE organizer_id = ?";
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

    public int getTotalActivities() {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total FROM Activity";
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

    public boolean addActivity(ActivityBean activity) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Activity "
                    + "(activity_id, title, description, activity_date, hour_offered, organizer_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, activity.getActivityId());
            ps.setString(2, activity.getTitle());
            ps.setString(3, activity.getDescription());
            ps.setDate(4, activity.getActivityDate());
            ps.setInt(5, activity.getHourOffered());
            ps.setInt(6, activity.getOrganizerId());

            int row = ps.executeUpdate();

            if (row > 0) {
                status = true;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public int getNextActivityId() {
        int nextId = 1;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT MAX(activity_id) AS max_id FROM Activity";
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

    public ActivityBean getActivityById(int activityId) {
        ActivityBean activity = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM Activity WHERE activity_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, activityId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                activity = new ActivityBean();

                activity.setActivityId(rs.getInt("activity_id"));
                activity.setTitle(rs.getString("title"));
                activity.setDescription(rs.getString("description"));
                activity.setActivityDate(rs.getDate("activity_date"));
                activity.setHourOffered(rs.getInt("hour_offered"));
                activity.setOrganizerId(rs.getInt("organizer_id"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return activity;
    }

    public ActivityBean getActivityByIdAndOrganizer(int activityId, int organizerId) {
        ActivityBean activity = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM Activity WHERE activity_id = ? AND organizer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, activityId);
            ps.setInt(2, organizerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                activity = new ActivityBean();

                activity.setActivityId(rs.getInt("activity_id"));
                activity.setTitle(rs.getString("title"));
                activity.setDescription(rs.getString("description"));
                activity.setActivityDate(rs.getDate("activity_date"));
                activity.setHourOffered(rs.getInt("hour_offered"));
                activity.setOrganizerId(rs.getInt("organizer_id"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return activity;
    }

    public boolean updateActivity(ActivityBean activity) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE Activity SET "
                    + "title = ?, "
                    + "description = ?, "
                    + "activity_date = ?, "
                    + "hour_offered = ? "
                    + "WHERE activity_id = ? AND organizer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, activity.getTitle());
            ps.setString(2, activity.getDescription());
            ps.setDate(3, activity.getActivityDate());
            ps.setInt(4, activity.getHourOffered());
            ps.setInt(5, activity.getActivityId());
            ps.setInt(6, activity.getOrganizerId());

            int row = ps.executeUpdate();

            if (row > 0) {
                String sql2 = "UPDATE Participation "
                        + "SET hour_earned = ? "
                        + "WHERE activity_id = ? "
                        + "AND status = 'Completed'";

                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, activity.getHourOffered());
                ps2.setInt(2, activity.getActivityId());
                ps2.executeUpdate();

                status = true;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean deleteActivity(int activityId) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql1 = "DELETE FROM Participation WHERE activity_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, activityId);
            ps1.executeUpdate();

            String sql2 = "DELETE FROM Activity WHERE activity_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, activityId);

            int row = ps2.executeUpdate();

            if (row > 0) {
                status = true;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean deleteActivity(int activityId, int organizerId) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            ActivityBean activity = getActivityByIdAndOrganizer(activityId, organizerId);
            if (activity == null) {
                conn.close();
                return false;
            }

            String sql1 = "DELETE FROM Participation WHERE activity_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, activityId);
            ps1.executeUpdate();

            String sql2 = "DELETE FROM Activity WHERE activity_id = ? AND organizer_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, activityId);
            ps2.setInt(2, organizerId);

            int row = ps2.executeUpdate();

            if (row > 0) {
                status = true;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}