package lbms.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import lbms.bo.Book;
import lbms.dao.BookDAO;

import java.io.IOException;
import java.util.List;

public class LibrarianSearchController {

    // FXML controls (match LibrarianSearch.fxml)

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Book> booktable; // fx:id="booktable"

    @FXML
    private TableColumn<Book, String> IsbnColumn;

    @FXML
    private TableColumn<Book, Integer> publisherIDColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TableColumn<Book, Integer> quantityColumn;

    @FXML
    private TableColumn<Book, String> editionColumn;

    @FXML
    private TableColumn<Book, Integer> pagesColumn;

    @FXML
    private Button ReturnHome;

    @FXML
    private void initialize() {
        IsbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publisherIDColumn.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        editionColumn.setCellValueFactory(new PropertyValueFactory<>("edition"));
        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));

        // load all books at startup
        loadBooks("");
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        if (keyword == null) {
            keyword = "";
        }
        loadBooks(keyword.trim());
    }

    private void loadBooks(String keyword) {
        try {
            BookDAO dao = new BookDAO();
            List<Book> books = dao.search(keyword == null ? "" : keyword);
            booktable.setItems(FXCollections.observableArrayList(books));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load books");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleReturnHome() {
        switchScene("HomePageA.fxml");
    }

    private void switchScene(String fxmlName) {
        try {
            Stage stage = (Stage) ReturnHome.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load screen");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
