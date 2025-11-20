package lbms.bo;

public class Book {
    private String isbn;
    private int quantity;
    private String title;
    private String publishDate; // store as String "YYYY-MM-DD" for GUI simplicity
    private Integer pages;
    private String edition;
    private String genre;
    private Integer publisherId;

    public Book() {}

    public Book(String isbn, int quantity, String title, String publishDate,
                Integer pages, String edition, String genre, Integer publisherId) {
        this.isbn = isbn;
        this.quantity = quantity;
        this.title = title;
        this.publishDate = publishDate;
        this.pages = pages;
        this.edition = edition;
        this.genre = genre;
        this.publisherId = publisherId;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    public Integer getPages() { return pages; }
    public void setPages(Integer pages) { this.pages = pages; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getPublisherId() { return publisherId; }
    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }
}
