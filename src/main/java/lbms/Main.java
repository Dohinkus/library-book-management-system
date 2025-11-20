package lbms;

import lbms.dao.*;
import lbms.bo.*;
import lbms.utils.*;

/*
This main method:

Connects to DB

Calls DAOs

Prints test results
*/

public class Main {

    public static void main(String[] args) {
        System.out.println("LBMS Backend Test Starting...");

        try {
            // Basic connectivity test
            System.out.println("Testing DB connection...");
            var conn = DBConnection.getConnection();
            System.out.println("Connection OK: " + (conn != null));

            // Simple DAO test
            BookDAO bookDAO = new BookDAO();
            var books = bookDAO.getAll();

            System.out.println("Books in DB:");
            for (Book b : books) {
                System.out.println(" - " + b.getIsbn() + " : " + b.getTitle());
            }

            // Availability test
            System.out.println("Testing availability...");
            int avail = bookDAO.getAvailableCopies("9780451524935");
            System.out.println("Available copies of 1984: " + avail);

            // Test checkout
            System.out.println("Testing checkout...");
            var checkout = LibraryService.checkoutBook("mem_alice", "9780451524935");
            if (checkout == null) {
                System.out.println("Checkout FAILED: no available copies");
            } else {
                System.out.println("Checkout SUCCESS, OrderId = " + checkout.getOrderId());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("LBMS Backend Test Complete.");
    }
}
