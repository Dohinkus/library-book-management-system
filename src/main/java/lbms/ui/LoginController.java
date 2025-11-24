package lbms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lbms.dao.AppAccountDAO;
import lbms.bo.AppAccount;

public class LoginController {

    @FXML
    private TextField usernameField;   // match fx:id in FicLibraryApp.fxml

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

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
        Stage stage = (Stage) usernameField.getScene().getWindow();
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
