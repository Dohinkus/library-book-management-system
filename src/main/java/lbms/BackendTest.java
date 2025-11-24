package lbms;

import lbms.bo.Book;
import lbms.bo.CheckOut;
import lbms.dao.BookDAO;
import lbms.dao.CheckOutDAO;
import lbms.utils.DBConnection;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class BackendTest {

    public static void main(String[] args) {
        System.out.println("LBMS Backend Test Starting...");

        // 1. Test DB connection
        testConnection();

        // 2. Test BookDAO (list books and availability)
        testBooks();

        // 3. Test basic checkout logic using CheckOutDAO
        testCheckout();

        System.out.println("LBMS Backend Test Complete.");
    }

    private static void testConnection() {
        System.out.println("\n[1] Testing DB connection...");
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connection OK: " + (conn != null));
        } catch (Exception ex) {
            System.out.println("Connection FAILED:");
            ex.printStackTrace();
        }
    }

    private static void testBooks() {
        System.out.println("\n[2] Testing BookDAO...");

        BookDAO bookDAO = new BookDAO();

        try {
            // Use search("") to get all books (LIKE "%%")
            List<Book> books = bookDAO.search("");

            System.out.println("Books in DB:");
            for (Book b : books) {
                System.out.println(" - " + b.getIsbn() + " : " + b.getTitle());
            }

            // Test availability for a known ISBN from seed-data.sql
            String testIsbn = "9780451524935"; // 1984 from seed-data.sql
            int available = bookDAO.getAvailableCopies(testIsbn);
            System.out.println("Available copies of ISBN " + testIsbn + ": " + available);

        } catch (Exception ex) {
            System.out.println("BookDAO test FAILED:");
            ex.printStackTrace();
        }
    }

    private static void testCheckout() {
        System.out.println("\n[3] Testing CheckOutDAO...");

        CheckOutDAO checkOutDAO = new CheckOutDAO();
        BookDAO bookDAO = new BookDAO();

        String testUser = "mem_alice";          // from seed-data.sql
        String testIsbn = "9780451524935";      // 1984 from seed-data.sql
        String today = LocalDate.now().toString();

        try {
            // Show availability before
            int before = bookDAO.getAvailableCopies(testIsbn);
            System.out.println("Available copies BEFORE checkout: " + before);

            // Create a new checkout record
            CheckOut co = new CheckOut();
            co.setUsername(testUser);
            co.setIsbn(testIsbn);
            co.setDateCheckedOut(today);
            co.setReturnDate(null);

            int generatedId = checkOutDAO.insert(co);
            if (generatedId > 0) {
                System.out.println("Checkout inserted with OrderId: " + generatedId);
            } else {
                System.out.println("Checkout insert FAILED (OrderId not generated).");
            }

            // Show availability after
            int after = bookDAO.getAvailableCopies(testIsbn);
            System.out.println("Available copies AFTER checkout: " + after);

            // List active checkouts for the user
            System.out.println("Active checkouts for " + testUser + ":");
            for (CheckOut active : checkOutDAO.getActiveCheckouts(testUser)) {
                System.out.println(" - OrderId=" + active.getOrderId()
                        + " ISBN=" + active.getIsbn()
                        + " DateCheckedOut=" + active.getDateCheckedOut());
            }

        } catch (Exception ex) {
            System.out.println("CheckOutDAO test FAILED:");
            ex.printStackTrace();
        }
    }
}
