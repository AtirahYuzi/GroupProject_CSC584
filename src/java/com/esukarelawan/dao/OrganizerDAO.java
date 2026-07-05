package com.esukarelawan.dao;

import com.esukarelawan.model.OrganizerBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrganizerDAO {

    public OrganizerBean getOrganizerById(int organizerId) {
        OrganizerBean organizer = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM Organizer WHERE organizer_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizerId);

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return organizer;
    }
}