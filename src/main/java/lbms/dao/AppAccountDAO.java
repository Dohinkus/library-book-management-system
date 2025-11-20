package lbms.dao;

import lbms.bo.AppAccount;
import lbms.utils.DBConnection;
import lbms.utils.PasswordUtils;

import java.sql.*;

/*
Supports:

Creating users

Getting user by username

Validating login (uses PasswordUtils)

Checking roles
*/

public class AppAccountDAO {

    public AppAccount getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM AppAccount WHERE Username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AppAccount(
                        rs.getString("Username"),
                        rs.getBytes("Password"),
                        rs.getBytes("Salt"),
                        rs.getString("Email"),
                        rs.getString("PhoneNum"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Address"),
                        rs.getString("Role")
                );
            }
            return null;
        }
    }

    public boolean insert(AppAccount account) throws SQLException {
        String sql = """
            INSERT INTO AppAccount
            (Username, Password, Salt, Email, PhoneNum, FirstName, LastName, Address, Role)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getUsername());
            stmt.setBytes(2, account.getPassword());
            stmt.setBytes(3, account.getSalt());
            stmt.setString(4, account.getEmail());
            stmt.setString(5, account.getPhoneNum());
            stmt.setString(6, account.getFirstName());
            stmt.setString(7, account.getLastName());
            stmt.setString(8, account.getAddress());
            stmt.setString(9, account.getRole());

            return stmt.executeUpdate() > 0;
        }
    }

    /** Login validation */
    public AppAccount validateLogin(String username, String rawPassword) throws Exception {
        AppAccount acc = getByUsername(username);
        if (acc == null) return null;

        boolean ok = PasswordUtils.verifyPassword(rawPassword, acc.getSalt(), acc.getPassword());

        return ok ? acc : null;
    }
}
