package lbms.bo;

public class CheckOut {
    private int orderId;
    private String username;
    private String isbn;
    private String dateCheckedOut;
    private String returnDate; // may be null

    public CheckOut() {}

    public CheckOut(int orderId, String username, String isbn, String dateCheckedOut, String returnDate) {
        this.orderId = orderId;
        this.username = username;
        this.isbn = isbn;
        this.dateCheckedOut = dateCheckedOut;
        this.returnDate = returnDate;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getDateCheckedOut() { return dateCheckedOut; }
    public void setDateCheckedOut(String dateCheckedOut) { this.dateCheckedOut = dateCheckedOut; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
}
