package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class LoginController {

	@FXML
	private TextField UsernameField;
	
	@FXML
	private PasswordField PasswordField;
	
	@FXML
	private void handleLogin() {
		String username = UsernameField.getText();
		String password = PasswordField.getText();
		
		//Temp till Data Base
		//Admin Account : user= Admin, Pass = 1234, role = admin
		// MemberAccount : user= member, pass = 1234, role = member
		String role = null;
		if(username.equals("Admin") && password.equals("1234")) {
			role = "Admin";
		}else if (username.equals("Member") && password.equals("1234")) {
			role = "Member";
		}
		
		if(role==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("LoginFailed");
			alert.setHeaderText(null);
			alert.setContentText("Invalid Username or Password");
			alert.showAndWait();
			return;
		}
		
		try {
			Stage stage = (Stage) UsernameField.getScene().getWindow();
			
			FXMLLoader loader;
			
			if("Admin".equals(role)) {
				loader = new FXMLLoader(getClass().getResource("HomePageA.fxml"));
				
			}else {
				loader = new FXMLLoader(getClass().getResource("HomePageM.fxml"));
			}
			
			Parent root = loader.load();
			
			Scene newScene = new Scene(root);
			stage.setScene(newScene);
			
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Unable to Load next Scene");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
