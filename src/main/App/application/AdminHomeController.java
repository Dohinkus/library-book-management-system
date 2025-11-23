package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdminHomeController {

	@FXML
	private Button ToSearch;
	
	@FXML
	private Button ToCheckout;
	
	@FXML
	private Button ToWaitlist;
	
	@FXML
	private Button ToReturn;
	
	// helper to swap scenes
	private void switchScene(String fxmlName) {
		Stage stage = (Stage) ToSearch.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource(fxmlName));
		Scene scene = new scene(root);
		stage.setScene(scene);
	}catch(Exception e) {
		e.printStackTrace();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Unable to load screen");
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
	
	//handles to swap scenes
	
	@FXML
	private void handleToSearch() {
		
		switchScene("LibrarianSearch.fxml");
	}
	
	
	@FXML
	private void handleToCheckout() {
		
		switchScene("CheckOutPage.fxml");
	}
	
	
	@FXML
	private void handleToWaitlist() {
		
		switchScene("WaitList.fxml");
	}
	
	@FXML
	private void handleToReturn() {
		
		switchScene("ReturnBooks.fxml");
	}
}
