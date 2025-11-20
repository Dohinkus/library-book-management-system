package lbms.bo;

public class DigitalBook {
    private String isbn;
    private String fileType;
    private Double fileSizeMB;

    public DigitalBook() {}

    public DigitalBook(String isbn, String fileType, Double fileSizeMB) {
        this.isbn = isbn;
        this.fileType = fileType;
        this.fileSizeMB = fileSizeMB;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Double getFileSizeMB() { return fileSizeMB; }
    public void setFileSizeMB(Double fileSizeMB) { this.fileSizeMB = fileSizeMB; }
}
