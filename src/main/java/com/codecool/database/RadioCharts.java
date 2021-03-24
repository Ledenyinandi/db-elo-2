package com.codecool.database;

import java.sql.*;

public class RadioCharts {

    private final String DB_URL;
    private final String USERNAME;
    private final String PASSWORD;

    public RadioCharts(String DB_URL, String USERNAME, String PASSWORD) {
        this.DB_URL = DB_URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public String getMostPlayedSong() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT song\n" +
                    "FROM music_broadcast\n" +
                    "GROUP BY song, times_aired\n" +
                    "ORDER BY times_aired DESC\n" +
                    "LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return "";
            }
            return rs.getString("song");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMostActiveArtist() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT artist\n" +
                    "FROM music_broadcast\n" +
                    "GROUP BY artist\n" +
                    "ORDER BY COUNT(DISTINCT song) DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return "";
            }
            return rs.getString("artist");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
