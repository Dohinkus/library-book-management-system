package lbms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lbms.bo.AppAccount;
import lbms.dao.AppAccountDAO;
import lbms.utils.PasswordUtils;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ensure demo users exist BEFORE showing login screen
        try {
            ensureDemoUsers();
        } catch (Exception e) {
            // Log, but don't crash the UI
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FicLibraryApp.fxml"));
        primaryStage.setTitle("Library Book Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void ensureDemoUsers() throws Exception {
        AppAccountDAO dao = new AppAccountDAO();

        ensureUser(dao,
                "admin_demo",
                "admin123",
                "admin.demo@library.com",
                "555-0101",
                "System",
                "Admin",
                "Library HQ",
                "LIBRARIAN");

        ensureUser(dao,
                "member_demo",
                "member123",
                "member.demo@library.com",
                "555-0102",
                "Alice",
                "Demo",
                "123 Demo St",
                "MEMBER");
    }

    private void ensureUser(AppAccountDAO dao,
                            String username,
                            String rawPassword,
                            String email,
                            String phone,
                            String firstName,
                            String lastName,
                            String address,
                            String role) throws Exception {

        // If user already exists, do nothing
        if (dao.getByUsername(username) != null) {
            return;
        }

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

    public static void main(String[] args) {
        launch(args);
    }
}
