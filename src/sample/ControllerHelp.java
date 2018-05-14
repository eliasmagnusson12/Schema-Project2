package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
        Image image = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneContact.setBackground(new Background(backgroundImage));

        Image image1 = new Image("resources/2.jpg");
        BackgroundImage backgroundImage1 = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneInstruction.setBackground(new Background(backgroundImage1));

        tabContact.setStyle("-fx-background-color: SILVER");
        tabInstruction.setStyle("-fx-background-color: SILVER");
        tabPane.setStyle("-fx-background-color: BLACK");

        labelInstruction.setText("Sign in - To see your schedule you have to sign in with your social security number \nand your personal password. \n\n" +
                                 "Send Email - To send an email you simply click the box of whom you want to contact,\nthen write your message with the appropriate subject and then just click send. \n\n" +
                                 "Change password - To change your password, go into settings,\nclick the ''change password''-button then write your new desired password.");
        labelContact.setText("Boss 1 - 07xxxxxxxx \n\n" +
                             "Boss 2 - 07xxxxxxxx\n\n" +
                             "Boss 3 - 07xxxxxxxx");


    }
}
