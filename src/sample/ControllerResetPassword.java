package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class ControllerResetPassword implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button resetButton;
    @FXML
    private Hyperlink helpLink;
    @FXML
    private Label labelResetPassword;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("resourses/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image back = new Image("resourses/arrowBsmall.png");
        backButton.setGraphic(new ImageView(back));
        backButton.setStyle("-fx-background-color: TRANSPARENT");

    }
    @FXML
    private void handlePressHelpLink(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(  "sampleResetPasswordHelp.fxml"));
        stage.setTitle("Reset Password Help");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void handlePressBackButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleLogin.fxml"));
        stage.setTitle("Schedule 1.0");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void handleHoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: LIGHTGRAY");
    }

    @FXML
    private void handleNoHoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: TRANSPARENT");
    }

}
