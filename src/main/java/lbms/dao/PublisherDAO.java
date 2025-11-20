package lbms.dao;

import lbms.bo.Publisher;
import lbms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO {

    public int insert(Publisher p) throws SQLException {
        String sql = "INSERT INTO Publisher (Name, Address) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getName());
            stmt.setString(2, p.getAddress());

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        }
        return -1;
    }

    public Publisher getById(int id) throws SQLException {
        String sql = "SELECT * FROM Publisher WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Publisher(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address")
                );
            }
            return null;
        }
    }

    public Publisher getByName(String name) throws SQLException {
        String sql = "SELECT * FROM Publisher WHERE Name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Publisher(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address")
                );
            }
            return null;
        }
    }

    public List<Publisher> getAll() throws SQLException {
        String sql = "SELECT * FROM Publisher ORDER BY Name";

        List<Publisher> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Publisher(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address")
                ));
            }
        }
        return list;
    }
}
