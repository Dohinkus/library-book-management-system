package lbms.bo;

public class Volume {
    private String isbn;
    private int seriesId;
    private int volNum;

    public Volume() {}

    public Volume(String isbn, int seriesId, int volNum) {
        this.isbn = isbn;
        this.seriesId = seriesId;
        this.volNum = volNum;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getSeriesId() { return seriesId; }
    public void setSeriesId(int seriesId) { this.seriesId = seriesId; }

    public int getVolNum() { return volNum; }
    public void setVolNum(int volNum) { this.volNum = volNum; }
}
