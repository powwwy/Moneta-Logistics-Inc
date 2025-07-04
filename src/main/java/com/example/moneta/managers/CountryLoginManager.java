package src.main.java.com.example.moneta.managers;

import src.main.java.com.example.moneta.handler.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CountryLoginManager {

    public String getCountryIDByManagerID(String managerID) throws Exception {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT countryID FROM country WHERE managerID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, managerID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("countryID");
            } else {
                return null;
            }
        }
    }
}
