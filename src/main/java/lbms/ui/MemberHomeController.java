package application;

	import javafx.collections.FXCollections;
	import javafx.fxml.FXML;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.control.TextField;
	import javafx.scene.control.Alert;
	import javafx.scene.control.Alert.AlertType;
	import javafx.scene.control.cell.PropertyValueFactory;

	// backend imports
	import lbms.bo.Book;
	import lbms.dao.BookDAO;
	 
	import java.util.list;
	
	public class MemberHomeController{
		
		// FXML controls
		
		@FXML
		private TextField searchField;
		
		@FXML
		private TableView<Book> bookTable;
		
		@FXML
		private TableColumn<Book, String> isbnColumn;
		
		@FXML
		private TableColumn<Book, Integer> publisherIdColumn;
		
		@FXML
		private TableColumn<Book, String> titleColumn;
		
		@FXML
		private TableColumn<Book, String> genreColumn;
		
		@FXML
		private TableColumn<Book, Integer> quantityColumn;
		
		@FXML
		private TableColumn<Book, Integer> editionColumn;
		
		@FXML
		private TableColumn<Book, Integer> pagesColumn;
		
		@FXML
		private void initialize() {
			//ties tabel columns to Book getters
			isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
			publisherIdColumn.setcellValueFactory(new PropertyValueFactory<>("publisher"));
			titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
			genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
			quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			editionColumn.setCellValueFactory(new PropertyValueFactoru<>("edition"));
			pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));
			
			//loads all books at startup
			loadBookds("");
			
			
			//called when searchField is interacted with
			@FXML
			private void handleSearch() {
				String keyword = searchField.getText();
				loadBooks(keyword);
			}
			
			// helper to talk to the DAO and fill the table
			private void loadBooks(String keyword) {
				try {
					BookDAO dao= new BookDAO();
					List<Book> books = dao.search(keyword); // backend handles talking to sql
					
					bookTable.setItems(FXCollections.observableArrayList(books));
					
				}catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Could not load books");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
					
				}
			}
		}
		
		
	}


