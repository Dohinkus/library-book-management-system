
package lbms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import lbms.dao.AppAccountDAO;
import lbms.bo.AppAccount;

public class LoginController {

    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private void handleLogin() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        AppAccount account;

        try {
            AppAccountDAO dao = new AppAccountDAO();
            // uses the backend to validate username and password
            account = dao.validateLogin(username, password);
        } catch (Exception e) {   // DAO can throw SQLException/Exception
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Error");
            alert.setContentText("There was a problem talking to the database.");
            alert.showAndWait();
            return;
        }

        // if no account is returned, the login failed
        if (account == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
            return;
        }

        // gets the role
        String role = account.getRole();

        try {
            Stage stage = (Stage) UsernameField.getScene().getWindow();
            FXMLLoader loader;

            // checks which user is logingin
            if ("Employee".equalsIgnoreCase(role)) {
                loader = new FXMLLoader(getClass().getResource("HomePageA.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("HomePageM.fxml"));
            }

            Parent root = loader.load();
            Scene newScene = new Scene(root);
            stage.setScene(newScene);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load next scene");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
