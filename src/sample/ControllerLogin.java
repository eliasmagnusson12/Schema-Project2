package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControllerLogin implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("resourses/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));
        Image logo = new Image("resourses/logo.png");
        ImageView imageView = new ImageView(logo);
        anchorPane.getChildren().add(imageView);
        imageView.fitWidthProperty().bind(anchorPane.widthProperty().divide(1.5));
        imageView.setX(80);

        Image back = new Image("resourses/arrowB.png");
        ImageView imageView1 = new ImageView(back);
        backButton.setGraphic(imageView1);




    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        DBConnect dbConnect = new DBConnect();

        if (password.equals(dbConnect.getUser(username))) {

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sampleCalendar.fxml"));
            stage.setTitle("Schedule 1.0");
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }else{
            System.out.println("Something went wrong");
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleMainMenu.fxml"));
        stage.setTitle("Schedule 1.0");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
