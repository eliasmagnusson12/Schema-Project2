package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



import java.sql.SQLException;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sampleMainMenu.fxml"));
        primaryStage.setTitle("Schedule 1.0");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("resources/s.png"));
        primaryStage.show();


    }


    public static void main(String[] args) throws SQLException {
        DBConnect.getDBCon();
        launch(args);


    }
}

