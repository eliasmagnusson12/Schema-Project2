package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerResetPasswordHelp implements Initializable {

    @FXML
    private Label textLabel, headLabel;
    @FXML
    private AnchorPane anchorPane;
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

        textLabel.setText("Börja med att skriva in din email adress som du tidigare har uppgett som din kontakt \n" +
                "email i fältet för email, klicka sedan på knappen reset password och ett mail kommer att \n" +
                "skickas dig med ett nytt tillfälligt lösenord om du har angivit rätt email adress. \n" +
                "Gå sedan in under inställningar och ändra ditt lösenord till ett nytt som du själv väljer.");


    }

    @FXML
    private void handlePressBackButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleResetPassword.fxml"));
        stage.setTitle("Reset Password");
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
