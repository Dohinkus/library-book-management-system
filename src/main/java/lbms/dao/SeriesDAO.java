package lbms.dao;

import lbms.bo.Series;
import lbms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesDAO {

    public int insert(Series s) throws SQLException {
        String sql = "INSERT INTO Series (Name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, s.getName());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        }
        return -1;
    }

    public Series getById(int id) throws SQLException {
        String sql = "SELECT * FROM Series WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Series(
                        rs.getInt("Id"),
                        rs.getString("Name")
                );
            }
            return null;
        }
    }

    public List<Series> getAll() throws SQLException {
        String sql = "SELECT * FROM Series ORDER BY Name";
        List<Series> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Series(
                        rs.getInt("Id"),
                        rs.getString("Name")
                ));
            }
        }
        return list;
    }
}
