package sample;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAddEmployee implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addButton;
    @FXML
    private TextField firstNameTextField, lastNameTextfield, emailTextField, phoneNumberTextfield;
    @FXML
    private Label department1Label, department2Label, department3Label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image mediumBackgroundimage = new Image("resourses/mediumBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(mediumBackgroundimage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image checkImage = new Image("resourses/check.png");
        ImageView checkImageView = new ImageView(checkImage);
        addButton.setGraphic(checkImageView);
        addButton.setStyle("-fx-background-color: TRANSPARENT");

        firstNameTextField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));
        lastNameTextfield.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));
    }

    @FXML
    private EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField textField = (TextField) event.getSource();
                if (textField.getText().length() >= max_Lengh) {
                    event.consume();
                }
                if (event.getCharacter().matches("[A-Za-z]")) {
                }else {
                    event.consume();
                }
            }
        };
    }
    @FXML
    private void handleInput(ActionEvent event){
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextfield.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextfield.getText();
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
