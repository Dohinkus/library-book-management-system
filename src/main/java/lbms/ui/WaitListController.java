package lbms.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import lbms.dao.WaitListDAO;
import lbms.bo.WaitListEntry;   

import java.util.List;


public class WaitListController {
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField isbnField;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button ReturnHomeButton;
	
	@FXML
	private TableView<WaitListEntry> waitlistTable;
	
	@FXML
	private TableColumn<WaitListEntry, String> usernameColumn;
	
	@FXML
	private TableColumn<WaitListEntry, String> isbnColumn;
	
	@FXML
	private TableColumn<WaitListEntry, String> datePlacedColumn;
	
	
	@FXML
	private void initalize() {
		
		usernameColumn.setCellValueFactory(new PropertyValueFactory<> ("username"));
		isbnColumn.setCellValueFactory(new PropertyValueFactory<> ("isbn"));
		datePlacedColumn.setCellValueFactory(new PropertyValueFactory<> ("datePlaced"));
		
		//starts with an empty list(no isbn to check yet)
		waitlistTable.setItems(FXCollections.observableArrayList());
	
	}
	
	@FXML
	private void handleaddButton() {
		String username = usernameField.getText();
		String isbn = isbnField.getText();
		
		if(username == null || username.isBlank() || isbn == null || isbn.isBlank()) {
			showError("Please enter both username and ISBN");
			return;
		}
		
		try {
			WaitListDAO dao = new WaitListDAO();
			boolean added = dao.addToWaitList(username, isbn);
			
			if(added) {
				showInfo("User Added to Waitlist");
				
				//refresh table for ISBN
				loadWaitlistForIsbn(isbn);
				isbnField.clear();
			}else {
				showError("Error adding to waitlist" + e.getMessage());
			}
		}catch (Exception e) {
			e.printStackTrace();
			showError("Could not load Waitlist: " + e.getMessage());
		}
		
		@FXML
		private void handleReturnHome() {
			try {
				Stage stage = (Stage) returnHomeButton.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("HomePageA.fxml"));
				stage.setScene(new Scene(root));
			}catch (Exception e) {
				e.printStackTrace();
				showError("Could not return Home:" + e.getMessage())
			}
		}
		
		  private void showError(String msg) {
		        Alert a = new Alert(Alert.AlertType.ERROR);
		        a.setTitle("Error");
		        a.setHeaderText(null);
		        a.setContentText(msg);
		        a.showAndWait();
		    }

		    private void showInfo(String msg) {
		        Alert a = new Alert(Alert.AlertType.INFORMATION);
		        a.setTitle("Waitlist");
		        a.setHeaderText(null);
		        a.setContentText(msg);
		        a.showAndWait();
		    }
		
		
	}

}
