package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHelp implements Initializable {

    @FXML
    AnchorPane paneInstruction, paneContact;
    @FXML
    Label labelInstruction, labelContact;
    @FXML
    Tab tabInstruction, tabContact;
    @FXML
    TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("resourses/smallBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneContact.setBackground(new Background(backgroundImage));

        Image image1 = new Image("resourses/smallBackground.jpg");
        BackgroundImage backgroundImage1 = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneInstruction.setBackground(new Background(backgroundImage1));

        tabContact.setStyle("-fx-background-color: SILVER");
        tabInstruction.setStyle("-fx-background-color: SILVER");
        tabPane.setStyle("-fx-background-color: BLACK");

        labelInstruction.setText("Skriv instruktioner till programmet här");
        labelContact.setText("Skriv kontaktuppgifter till olika chefer/avdelningar här");

    }
}
