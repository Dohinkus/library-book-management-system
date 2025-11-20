package lbms.dao;

import lbms.bo.PhysicalBook;
import lbms.utils.DBConnection;

import java.sql.*;

public class PhysicalBookDAO {

    public boolean insert(PhysicalBook p) throws SQLException {
        String sql = "INSERT INTO PhysicalBook (ISBN, CoverType) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getIsbn());
            stmt.setString(2, p.getCoverType());

            return stmt.executeUpdate() > 0;
        }
    }

    public PhysicalBook getByISBN(String isbn) throws SQLException {
        String sql = "SELECT * FROM PhysicalBook WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PhysicalBook(
                        rs.getString("ISBN"),
                        rs.getString("CoverType")
                );
            }
            return null;
        }
    }

    public boolean update(PhysicalBook p) throws SQLException {
        String sql = "UPDATE PhysicalBook SET CoverType = ? WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getCoverType());
            stmt.setString(2, p.getIsbn());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(String isbn) throws SQLException {
        String sql = "DELETE FROM PhysicalBook WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            return stmt.executeUpdate() > 0;
        }
    }
}
