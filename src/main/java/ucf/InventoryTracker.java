package ucf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryTracker extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

            Scene scene = new Scene(root);

            primaryStage.getScene(scene);
            primaryStage.setTitle("Inventory Tracker");
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
