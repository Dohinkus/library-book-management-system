package lbms;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println(getClass().getResource("/fxml/FicLibraryApp.fxml"));
			
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/FicLibraryApp.fxml")); //gets file info
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Fic Library Directory");
			primaryStage.show();
	  }catch (Exception e) {
		  e.printStackTrace();
	  }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
