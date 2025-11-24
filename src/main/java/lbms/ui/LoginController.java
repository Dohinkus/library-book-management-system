package lbms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lbms.bo.AppAccount;
import lbms.dao.AppAccountDAO;

public class LoginController {

    // IMPORTANT: names MUST match fx:id EXACTLY in FicLibraryApp.fxml
    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private void handleLogin() {
        if (UsernameField == null || PasswordField == null) {
            // This means FXML injection failed (bad fx:id or fx:controller)
            showError("Internal error: username/password fields not initialized.");
            return;
        }

        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            showError("Please enter both username and password.");
            return;
        }

        try {
            AppAccountDAO dao = new AppAccountDAO();
            AppAccount acc = dao.validateLogin(username, password);

            if (acc == null) {
                showError("Invalid username or password.");
                return;
            }

            String role = acc.getRole();
            if ("LIBRARIAN".equalsIgnoreCase(role)) {
                switchScene("/fxml/HomePageA.fxml");
            } else if ("MEMBER".equalsIgnoreCase(role)) {
                switchScene("/fxml/HomePageM.fxml");
            } else {
                showError("Unknown role: " + role);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Login error: " + e.getMessage());
        }
    }

    private void switchScene(String fxmlPath) throws Exception {
        // Use any control that you know is injected; UsernameField is safe.
        Stage stage = (Stage) UsernameField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Login Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
