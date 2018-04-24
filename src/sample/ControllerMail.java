package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMail implements Initializable {

    @FXML
    CheckBox box1, box2, box3, box4;
    @FXML
    TextArea mailTextArea;
    @FXML
    Button send;
    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image sendImage = new Image("resourses/sendButton.png");
        ImageView sendImageView = new ImageView(sendImage);
        send.setGraphic(sendImageView);
        send.setStyle("-fx-background-color: TRANSPARENT");

        Image smallBackgroundImage = new Image("resourses/smallBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(smallBackgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));


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
