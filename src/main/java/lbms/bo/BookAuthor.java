package lbms.bo;

public class BookAuthor {
    private String isbn;
    private int authorId;
    private int authorNth;

    public BookAuthor() {}

    public BookAuthor(String isbn, int authorId, int authorNth) {
        this.isbn = isbn;
        this.authorId = authorId;
        this.authorNth = authorNth;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public int getAuthorNth() { return authorNth; }
    public void setAuthorNth(int authorNth) { this.authorNth = authorNth; }
}
