package lbms.dao;

import lbms.bo.WaitListEntry;
import lbms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WaitListDAO {

    public boolean addToWaitList(String username, String isbn) throws SQLException {
        String sql = "INSERT INTO WaitList (Username, ISBN) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, isbn);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeFromWaitList(String username, String isbn) throws SQLException {
        String sql = "DELETE FROM WaitList WHERE Username = ? AND ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, isbn);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<WaitListEntry> getWaitListForBook(String isbn) throws SQLException {
        String sql = """
            SELECT * FROM WaitList
            WHERE ISBN = ?
            ORDER BY DatePlaced
        """;

        List<WaitListEntry> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new WaitListEntry(
                        rs.getString("Username"),
                        rs.getString("ISBN"),
                        rs.getString("DatePlaced")
                ));
            }
        }
        return list;
    }

    public WaitListEntry getNextInWaitList(String isbn) throws SQLException {
        String sql = """
            SELECT * FROM WaitList
            WHERE ISBN = ?
            ORDER BY DatePlaced
            LIMIT 1
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new WaitListEntry(
                        rs.getString("Username"),
                        rs.getString("ISBN"),
                        rs.getString("DatePlaced")
                );
            }
            return null;
        }
    }

    public List<WaitListEntry> getAll() throws SQLException {
    String sql = """
        SELECT Username, ISBN, DatePlaced
        FROM WaitList
        ORDER BY DatePlaced
        """;

    List<WaitListEntry> list = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            list.add(new WaitListEntry(
                    rs.getString("Username"),
                    rs.getString("ISBN"),
                    rs.getTimestamp("DatePlaced").toString()
                ));
            }
        }
        return list;
    }

}
