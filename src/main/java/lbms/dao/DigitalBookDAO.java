package lbms.dao;

import lbms.bo.DigitalBook;
import lbms.utils.DBConnection;

import java.sql.*;

public class DigitalBookDAO {

    public boolean insert(DigitalBook d) throws SQLException {
        String sql = "INSERT INTO DigitalBook (ISBN, FileType, FileSizeMB) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getIsbn());
            stmt.setString(2, d.getFileType());
            if (d.getFileSizeMB() == null) {
                stmt.setNull(3, Types.DECIMAL);
            } else {
                stmt.setDouble(3, d.getFileSizeMB());
            }

            return stmt.executeUpdate() > 0;
        }
    }

    public DigitalBook getByISBN(String isbn) throws SQLException {
        String sql = "SELECT * FROM DigitalBook WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Double size = rs.getObject("FileSizeMB") == null
                        ? null
                        : rs.getDouble("FileSizeMB");

                return new DigitalBook(
                        rs.getString("ISBN"),
                        rs.getString("FileType"),
                        size
                );
            }
            return null;
        }
    }

    public boolean update(DigitalBook d) throws SQLException {
        String sql = "UPDATE DigitalBook SET FileType = ?, FileSizeMB = ? WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getFileType());
            if (d.getFileSizeMB() == null) {
                stmt.setNull(2, Types.DECIMAL);
            } else {
                stmt.setDouble(2, d.getFileSizeMB());
            }
            stmt.setString(3, d.getIsbn());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(String isbn) throws SQLException {
        String sql = "DELETE FROM DigitalBook WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            return stmt.executeUpdate() > 0;
        }
    }
}
