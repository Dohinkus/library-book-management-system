package lbms.bo;

public class PhysicalBook {
    private String isbn;
    private String coverType;

    public PhysicalBook() {}

    public PhysicalBook(String isbn, String coverType) {
        this.isbn = isbn;
        this.coverType = coverType;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCoverType() { return coverType; }
    public void setCoverType(String coverType) { this.coverType = coverType; }
}
