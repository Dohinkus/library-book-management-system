package lbms.dao;

import lbms.bo.CheckOut;
import lbms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
supports:

insert checkout record

return (set ReturnDate)

get active checkouts
*/

public class CheckOutDAO {

    public int insert(CheckOut co) throws SQLException {
        String sql = """
            INSERT INTO CheckOut
            (Username, ISBN, DateCheckedOut, ReturnDate)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, co.getUsername());
            stmt.setString(2, co.getIsbn());
            stmt.setString(3, co.getDateCheckedOut());
            stmt.setString(4, co.getReturnDate());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean returnBook(int orderId, String returnDate) throws SQLException {
        String sql = """
            UPDATE CheckOut
            SET ReturnDate = ?
            WHERE OrderId = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, returnDate);
            stmt.setInt(2, orderId);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<CheckOut> getActiveCheckouts(String username) throws SQLException {
        String sql = """
            SELECT * FROM CheckOut
            WHERE Username = ? AND ReturnDate IS NULL
        """;

        List<CheckOut> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new CheckOut(
                        rs.getInt("OrderId"),
                        rs.getString("Username"),
                        rs.getString("ISBN"),
                        rs.getString("DateCheckedOut"),
                        rs.getString("ReturnDate")
                ));
            }
        }
        return list;
    }
}
