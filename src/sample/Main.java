package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;



import java.sql.SQLException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sampleCalendar.fxml"));
        primaryStage.setTitle("Schedule 1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();




//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        primaryStage.setX(primaryScreenBounds.getMinX());
//        primaryStage.setY(primaryScreenBounds.getMinY());
//        primaryStage.setWidth(primaryScreenBounds.getWidth());
//        primaryStage.setHeight(primaryScreenBounds.getHeight());
    }


    public static void main(String[] args) throws SQLException {
        launch(args);
        DBConnect dbConnect = new DBConnect();
        dbConnect.getFirstName();



    }
}

