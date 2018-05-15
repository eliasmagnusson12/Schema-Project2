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


        labelInstruction.setText("Login - To see your schedule you need to login, press the login button and enter \nyour social security number and your personal password. If you dont remember \nyour password go to forgot password. \n\n" +
        "Send email - To be able to sent a email to admins or your nearest supervisor, \nclick on the email icon and login using your social security number and your \npersonal password. Then simply check the box next to the name you want to \nadress and write your email in the field to the right. \n\n" +
        "Change password - To change your current password press the settings icon and \nthen press change password, login using your social security number and your \ncurrent password. Enter the new password you want two times and its done. \n\n" +
        "Fullscreen - To get the schedule view in fullscreen go to the settings icon and check \nthe box view Schedule in fullscreen.");

        labelContact.setText("Boss 1 - 07xxxxxxxx \n\n" +
                             "Boss 2 - 07xxxxxxxx\n\n" +
                             "Boss 3 - 07xxxxxxxx");


    }
}
