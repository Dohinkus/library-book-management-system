package lbms.utils;

import lbms.bo.AppAccount;
import lbms.dao.AppAccountDAO;
import lbms.utils.PasswordUtils;

public class CreateDemoUsers {

    public static void main(String[] args) throws Exception {
        AppAccountDAO dao = new AppAccountDAO();

        createUser(dao,
                "admin_demo",
                "admin123",
                "admin.demo@library.com",
                "555-0101",
                "System",
                "Admin",
                "Library HQ",
                "LIBRARIAN");

        createUser(dao,
                "member_demo",
                "member123",
                "member.demo@library.com",
                "555-0102",
                "Alice",
                "Demo",
                "123 Demo St",
                "MEMBER");

        System.out.println("Demo users created.");
    }

    private static void createUser(AppAccountDAO dao,
                                   String username,
                                   String rawPassword,
                                   String email,
                                   String phone,
                                   String firstName,
                                   String lastName,
                                   String address,
                                   String role) throws Exception {

        byte[] salt = PasswordUtils.generateSalt();
        byte[] hash = PasswordUtils.hashPassword(rawPassword, salt);

        AppAccount acc = new AppAccount(
                username,
                hash,
                salt,
                email,
                phone,
                firstName,
                lastName,
                address,
                role
        );

        dao.insert(acc);
    }
}
