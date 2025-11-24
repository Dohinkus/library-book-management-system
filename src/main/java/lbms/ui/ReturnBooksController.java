package lbms.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

import lbms.bo.CheckOut;
import lbms.dao.CheckOutDAO;


public class ReturnBooksController {

	  @FXML
	    private TextField usernameField;

	    @FXML
	    private TextField isbnField;

	    @FXML
	    private Button returnButton;

	    @FXML
	    private Button ReturnHomeButton;

	    @FXML
	    private TableView<CheckOut> returnedTable;

	    @FXML
	    private TableColumn<CheckOut, String> usernameColumn;

	    @FXML
	    private TableColumn<CheckOut, String> isbnColumn;

	    @FXML
	    private TableColumn<CheckOut, String> dateReturnedColumn;
	  
	    
	   //runs after FXML loads
	    @FXML
	    private void initialize() {
	       
	        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
	        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
	        dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

	        returnedTable.setItems(FXCollections.observableArrayList());
	    }
	    
	    private void handleReturnBook() {
	        String username = usernameField.getText();
	        String isbn = isbnField.getText();

	        if (username == null || username.isBlank() ||
	            isbn == null || isbn.isBlank()) {
	            showError("Please enter both username and ISBN.");
	            return;
	        }

	        try {
	            CheckOutDAO dao = new CheckOutDAO();

	            // all active (ReturnDate IS NULL) for this user
	            List<CheckOut> active = dao.getActiveCheckouts(username);

	            CheckOut toReturn = null;
	            for (CheckOut co : active) {
	                if (isbn.equals(co.getIsbn())) {
	                    toReturn = co;
	                    break;
	                }
	            }

	            if (toReturn == null) {
	                showError("No active checkout found for that username and ISBN.");
	                return;
	            }

	            String today = LocalDate.now().toString();

	            boolean ok = dao.returnBook(toReturn.getOrderId(), today);

	            if (ok) {
	                // update BO so the table shows the date
	                toReturn.setReturnDate(today);

	                // add this returned item to the table (running log for this session)
	                returnedTable.getItems().add(toReturn);

	                showInfo("Book returned successfully.");
	                isbnField.clear();
	            } else {
	                showError("Return failed.");
	            }

	        } catch (Exception e) { 
	            e.printStackTrace();
	            showError("Error returning book: " + e.getMessage());
	        }
	    }

	    @FXML
	    private void handleReturnHome() {
	        try {
	            Stage stage = (Stage) ReturnHomeButton.getScene().getWindow();
	            Parent root = FXMLLoader.load(getClass().getResource("HomePageA.fxml"));
	            stage.setScene(new Scene(root));
	        } catch (Exception e) {
	            e.printStackTrace();
	            showError("Could not go back home: " + e.getMessage());
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
	        a.setTitle("Return Book");
	        a.setHeaderText(null);
	        a.setContentText(msg);
	        a.showAndWait();
	    }
}
