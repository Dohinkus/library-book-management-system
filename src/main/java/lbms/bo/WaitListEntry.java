package lbms.bo;

public class WaitListEntry {
    private String username;
    private String isbn;
    private String datePlaced;

    public WaitListEntry() {}

    public WaitListEntry(String username, String isbn, String datePlaced) {
        this.username = username;
        this.isbn = isbn;
        this.datePlaced = datePlaced;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getDatePlaced() { return datePlaced; }
    public void setDatePlaced(String datePlaced) { this.datePlaced = datePlaced; }
}
