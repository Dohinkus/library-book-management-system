package lbms.dao;

import lbms.bo.Author;
import lbms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public int insert(Author a) throws SQLException {
        String sql = "INSERT INTO Author (FirstName, LastName, Email) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, a.getFirstName());
            stmt.setString(2, a.getLastName());
            stmt.setString(3, a.getEmail());

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public Author getById(int id) throws SQLException {
        String sql = "SELECT * FROM Author WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Author(
                        rs.getInt("Id"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email")
                );
            }
            return null;
        }
    }

    public List<Author> searchByName(String namePart) throws SQLException {
        String sql = """
            SELECT * FROM Author
            WHERE FirstName LIKE ? OR LastName LIKE ?
        """;

        List<Author> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String like = "%" + namePart + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Author(
                        rs.getInt("Id"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email")
                ));
            }
        }
        return list;
    }
}
