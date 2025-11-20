package lbms.dao;

import lbms.bo.Book;
import lbms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
Supports:

insert

update

delete

search

get availability

list all

getByISBN
*/

public class BookDAO {

    public boolean insert(Book b) throws SQLException {
        String sql = """
            INSERT INTO Book
            (ISBN, Quantity, Title, PublishDate, Pages, Edition, Genre, pId)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, b.getIsbn());
            stmt.setInt(2, b.getQuantity());
            stmt.setString(3, b.getTitle());
            stmt.setString(4, b.getPublishDate());
            stmt.setObject(5, b.getPages());
            stmt.setString(6, b.getEdition());
            stmt.setString(7, b.getGenre());
            stmt.setObject(8, b.getPublisherId());

            return stmt.executeUpdate() > 0;
        }
    }

    public Book getByISBN(String isbn) throws SQLException {
        String sql = "SELECT * FROM Book WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getString("ISBN"),
                        rs.getInt("Quantity"),
                        rs.getString("Title"),
                        rs.getString("PublishDate"),
                        (Integer) rs.getObject("Pages"),
                        rs.getString("Edition"),
                        rs.getString("Genre"),
                        (Integer) rs.getObject("pId")
                );
            }
            return null;
        }
    }

    public boolean update(Book b) throws SQLException {
        String sql = """
            UPDATE Book
            SET Quantity = ?, Title = ?, PublishDate = ?, Pages = ?, Edition = ?, Genre = ?, pId = ?
            WHERE ISBN = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getQuantity());
            stmt.setString(2, b.getTitle());
            stmt.setString(3, b.getPublishDate());
            stmt.setObject(4, b.getPages());
            stmt.setString(5, b.getEdition());
            stmt.setString(6, b.getGenre());
            stmt.setObject(7, b.getPublisherId());
            stmt.setString(8, b.getIsbn());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(String isbn) throws SQLException {
        String sql = "DELETE FROM Book WHERE ISBN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Book> search(String keyword) throws SQLException {
        String sql = """
            SELECT * FROM Book
            WHERE Title LIKE ? OR ISBN LIKE ? OR Genre LIKE ?
        """;

        List<Book> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getString("ISBN"),
                        rs.getInt("Quantity"),
                        rs.getString("Title"),
                        rs.getString("PublishDate"),
                        (Integer) rs.getObject("Pages"),
                        rs.getString("Edition"),
                        rs.getString("Genre"),
                        (Integer) rs.getObject("pId")
                ));
            }
        }
        return list;
    }

    /** Returns available copies (computed dynamically) */
    public int getAvailableCopies(String isbn) throws SQLException {
        String sql = """
            SELECT b.Quantity - COUNT(c.ISBN) AS Available
            FROM Book b
            LEFT JOIN CheckOut c
                ON b.ISBN = c.ISBN AND c.ReturnDate IS NULL
            WHERE b.ISBN = ?
            GROUP BY b.ISBN
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("Available");
            return 0;
        }
    }
}
