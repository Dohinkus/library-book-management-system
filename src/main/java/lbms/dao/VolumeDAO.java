package lbms.dao;

import lbms.bo.Volume;
import lbms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolumeDAO {

    public boolean insert(Volume v) throws SQLException {
        String sql = "INSERT INTO Volume (ISBN, SeriesId, VolNum) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getIsbn());
            stmt.setInt(2, v.getSeriesId());
            stmt.setInt(3, v.getVolNum());

            return stmt.executeUpdate() > 0;
        }
    }

    public Volume getByISBN(String isbn) throws SQLException {
        String sql = "SELECT * FROM Volume WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Volume(
                        rs.getString("ISBN"),
                        rs.getInt("SeriesId"),
                        rs.getInt("VolNum")
                );
            }
            return null;
        }
    }

    public List<Volume> getBySeriesId(int seriesId) throws SQLException {
        String sql = "SELECT * FROM Volume WHERE SeriesId = ? ORDER BY VolNum";
        List<Volume> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, seriesId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Volume(
                        rs.getString("ISBN"),
                        rs.getInt("SeriesId"),
                        rs.getInt("VolNum")
                ));
            }
        }
        return list;
    }
}
