package com.esukarelawan.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.esukarelawan.model.MedalBean;

public class MedalDAO {

    public void awardEligibleMedals(int volunteerId, int totalHours) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT MEDAL_ID FROM MEDAL WHERE REQUIRED_HOURS <= ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, totalHours);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int medalId = rs.getInt("MEDAL_ID");

                if (!alreadyAwarded(conn, volunteerId, medalId)) {

                    int volunteerMedalId = getNextVolunteerMedalId(conn);

                    String insertSql =
                            "INSERT INTO VOLUNTEERMEDAL "
                            + "(VOLUNTEERMEDAL_ID, VOLUNTEER_ID, MEDAL_ID, DATE_EARNED) "
                            + "VALUES (?, ?, ?, CURRENT_DATE)";

                    PreparedStatement insertPs = conn.prepareStatement(insertSql);
                    insertPs.setInt(1, volunteerMedalId);
                    insertPs.setInt(2, volunteerId);
                    insertPs.setInt(3, medalId);

                    insertPs.executeUpdate();
                }
            }

        } catch (Exception e) {
            System.out.println("MEDAL ERROR:");
            e.printStackTrace();
        }
    }

    private int getNextVolunteerMedalId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(VOLUNTEERMEDAL_ID) FROM VOLUNTEERMEDAL";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        }

        return 1;
    }

    private boolean alreadyAwarded(Connection conn, int volunteerId, int medalId)
            throws SQLException {

        String sql =
                "SELECT * FROM VOLUNTEERMEDAL "
                + "WHERE VOLUNTEER_ID = ? AND MEDAL_ID = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, volunteerId);
        ps.setInt(2, medalId);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public int getMedalsEarned(int volunteerId) {
        int count = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) FROM VOLUNTEERMEDAL "
                    + "WHERE VOLUNTEER_ID = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, volunteerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    
    public List<MedalBean> getVolunteerMedals(int volunteerId) {

    List<MedalBean> medals = new ArrayList<>();

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
            "SELECT M.MEDAL_ID, M.MEDAL_NAME, M.DESCRIPTION, "
            + "M.REQUIRED_HOURS, VM.DATE_EARNED "
            + "FROM MEDAL M "
            + "LEFT JOIN VOLUNTEERMEDAL VM "
            + "ON M.MEDAL_ID = VM.MEDAL_ID "
            + "AND VM.VOLUNTEER_ID = ? "
            + "ORDER BY M.REQUIRED_HOURS";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, volunteerId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            MedalBean medal = new MedalBean();

            medal.setMedalId(rs.getInt("MEDAL_ID"));
            medal.setMedalName(rs.getString("MEDAL_NAME"));
            medal.setDescription(rs.getString("DESCRIPTION"));
            medal.setRequiredHours(rs.getInt("REQUIRED_HOURS"));
            medal.setDateEarned(rs.getDate("DATE_EARNED"));

            medals.add(medal);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return medals;
}
}