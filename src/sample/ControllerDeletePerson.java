package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDeletePerson implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button okDeleteButton;
    @FXML
    private TextField ssnTextField;
    @FXML
    private Label okLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image mediumBackgroundImage = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(mediumBackgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image checkImage = new Image("resources/check.png");
        okDeleteButton.setGraphic(new ImageView(checkImage));
        okDeleteButton.setStyle("-fx-background-color: TRANSPARENT");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                okDeleteButton.requestFocus();
            }
        });
    }

    @FXML
    private void handleOkDeleteButton(ActionEvent event){
        String username = ssnTextField.getText();

        DBConnect dbConnect = new DBConnect();
        dbConnect.removePerson(username);

        okLabel.setText("Done!");

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
