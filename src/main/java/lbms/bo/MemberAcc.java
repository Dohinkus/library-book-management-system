package lbms.bo;

public class MemberAcc {
    private String username;
    private int cardNum;
    private String displayName;

    public MemberAcc() {}

    public MemberAcc(String username, int cardNum, String displayName) {
        this.username = username;
        this.cardNum = cardNum;
        this.displayName = displayName;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getCardNum() { return cardNum; }
    public void setCardNum(int cardNum) { this.cardNum = cardNum; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
