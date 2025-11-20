package lbms.bo;

public class AppAccount {
    private String username;
    private byte[] password;
    private byte[] salt;
    private String email;
    private String phoneNum;
    private String firstName;
    private String lastName;
    private String address;
    private String role;

    public AppAccount() {}

    public AppAccount(String username, byte[] password, byte[] salt, String email,
                      String phoneNum, String firstName, String lastName,
                      String address, String role) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.phoneNum = phoneNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public byte[] getPassword() { return password; }
    public void setPassword(byte[] password) { this.password = password; }

    public byte[] getSalt() { return salt; }
    public void setSalt(byte[] salt) { this.salt = salt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
