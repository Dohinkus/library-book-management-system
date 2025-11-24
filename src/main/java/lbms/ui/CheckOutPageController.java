package lbms.ui;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

import lbms.bo.CheckOut;
import lbms.dao.BookDAO;
import lbms.dao.CheckOutDAO;
import lbms.dao.WaitListDAO;


public class CheckOutPageController {
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField isbnField;
	
	@FXML
	private Button CheckAvaliabilityButton;
	
	@FXML
	private Label isAvaliableLabel;
	
	@FXML
	private Button ConfirmCheckOutButton;
	
	@FXML
	private Button ReturnHomeButton;
	
	@FXML
	private TableView<CheckOut> CheckoutTable;
	
	@FXML
	private TableColumn<CheckOut, String> isbnColumn;
	
	@FXML
	private TableColumn<CheckOut, String> dateCheckOutColumn;
	
	@FXML
	private TableColumn<CheckOut, String> dateReturnedColumn;
	
	
	//Checking how many copies are free
	@FXML
	private void handleCheckAvaliability() {
		
		String isbn = isbnField.getText();
		
		if(isbn == null) {
			showError("Please enter an ISBN");
			return;
		}
		
		try {
			BookDAO bookDAO = new BookDAO();
			int available = bookDAO.getAvailableCopies(isbn);
			
			if(avaliabe > 0) {
				 isAvaliableLabel.setText("Avaliabe Copies:"+ avaliabe);
				 
			}else {
				isAvaliableLabel.setText(" There Are No Copies Avaliabe");
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			showError("Error While Checking Avaliablity:" + e.getMessage());
		}
	}
	
	
	//Preform the Checkout
	@FXML
	private void handleConfirmCheckOut() {
		
		String username = usernameField.getText();
		String isbn = isbnField.getText();
		
		if(username == null || username.isBlank() || isbn == null || isbn.isBlank()) {
			showError("Please enter both username and ISBN");
			return;
		}
		
		try {
			
			BookDAO bookDAO = new BookDAO();
			int available = bookDAO.getAvailableCopies(isbn);
			
			if(avaliabe <= 0) {
				
				isAvaliableLabel.setText("No copies Avaliable Adding to WaitList...");
				WaitListDAO waitListDAO = new WaitListDAO();
				boolean added = waitListDAO.addToWaitList(username, isbn);
				
				if(added) {
					showInfo("No copies avaliabe. Adding User to waitlist.");
				}else {
					showError("Couldn't add to waitlist");
				}
				return;
				
			}
			
			//builds the checkout BO
			CheckOut co = new CheckOut();
			co.setUsername(username);
			co.setIsbn(isbn);
			co.setDateCheckedOut(LocalDate.now().toString());
			
			
			CheckOutDAO dao= new CheckOutDAO();
			int orderId = dao.insert(co);
			
			if(orderId > 0) {
				showInfo("Checkout Successful. Order ID:" + orderId);
				isAvaliableLabel.setText("");
				
				isbnField.clear();
				
				//refresh table for this user
				loadCheckoutsForUser(username);
	
			}else {
				showError("Checkout failed. Order ID not generated.");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			showError("Error during checkout:" + e.getMessage());
			
		}
		
		private void showError(String msg) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText(msg);
			alert.showAndWait();
		}
		
		
		private void showInfo(String msg) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Checkout");
			alert.setHeaderText(null);
			alert.setContentText(msg);
			alert.showAndWait();
		}
		
		
	}
	
	@FXML
	private void initalize() {
		
		isbnColumn.setCellValueFactory(new PropertyValueFactory<> ("isbn"));
		dateCheckOutColumn.setCellValueFactory(new PropertyValueFactory<> ("dateCheckOut"));
		dateReturnedColumn.setCellValueFactory(new PropertyValueFactory <> ("returnDate"));
	}
	
	private void LoadCheckoutsForUser(String username) {
		
		try {
			CheckOutDAO dao = new CheckOutDAO();
			
			java.util.List<CheckOut> checkouts = dao.getActiveCheckouts(username);
			
			checkoutTable.setItems(FXColletions.observableArryList(checkouts));
		} catch (Exception e) {
			e.printStackTrace();
			showError("Could not load checkouts:" + e.getMessage());
		}
	}
	

}
