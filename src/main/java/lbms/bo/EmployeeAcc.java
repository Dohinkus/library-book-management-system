package lbms.bo;

public class EmployeeAcc {
    private String username;
    private int staffId;
    private String jobTitle;

    public EmployeeAcc() {}

    public EmployeeAcc(String username, int staffId, String jobTitle) {
        this.username = username;
        this.staffId = staffId;
        this.jobTitle = jobTitle;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
}
