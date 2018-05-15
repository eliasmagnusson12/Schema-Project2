package sample;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerChangePassword implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button changeButton;
    @FXML
    private Label successLabel;

    private String username;
    boolean answer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                changeButton.requestFocus();
            }
        });
    }

    @FXML
    private void handleChangePasswordButton(ActionEvent event) throws SQLException {
        String password = newPassword.getText();
        String username = Singleton.getInstance().getUser().getSsn();
        if (newPassword.getText().equals(confirmPassword.getText())) {

            DBConnect dbConnect = new DBConnect();
            answer = dbConnect.changePassword(username, password);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("New password must match confirm password");
            alert.showAndWait();

        }
        displaySuccessLabel(answer);

    }

        private void displaySuccessLabel(boolean answer) {
            if (answer) {
                successLabel.setVisible(true);
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Successfully changed password!");
            } else {
                successLabel.setVisible(true);
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Something went wrong!");
            }
            PauseTransition visiblePause = new PauseTransition(
                    Duration.seconds(3)
            );
            visiblePause.setOnFinished(
                    event -> successLabel.setVisible(false)
            );
            visiblePause.play();
        }
    }

