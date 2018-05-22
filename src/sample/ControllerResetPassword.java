package sample;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.RandomStringUtils;


import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private Label successLabel;
    @FXML
    private Button backButton;

    String error;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image back = new Image("resources/arrowBsmall.png");
        backButton.setGraphic(new ImageView(back));
        backButton.setStyle("-fx-background-color: TRANSPARENT");

    }

    @FXML
    private void handlePressHelpLink(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleResetPasswordHelp.fxml"));
        stage.setTitle("Reset Password Help");
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image("resources/s.png"));
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

    @FXML
    private void forgotPW() throws SQLException, MessagingException {
        String email = emailTextField.getText();

        String pwChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String pw = RandomStringUtils.random(8, pwChars);

        boolean answer = false;
        String socialSecurityNumber = DBConnect.getDBCon().getSocialSecurityNumber(email);

        if (socialSecurityNumber == null) {

        } else {
            answer = DBConnect.getDBCon().changePassword(socialSecurityNumber, pw);

            if (answer) {
                sendMail(email, pw, socialSecurityNumber);
            } else {
                error = "Could not change password!";
                callAlert(error);
            }
        }
    }
    private void sendMail(String email, String pw, String socialSecurityNumber) throws MessagingException, SQLException {
        ControllerMail controllerMail = new ControllerMail();
        boolean answer = controllerMail.sendNewPassword(email, pw);

        if (answer) {
            successLabel.setVisible(true);
            successLabel.setTextFill(Color.GREEN);
            successLabel.setText("Your password has been successfully changed");
        } else {
            successLabel.setVisible(true);
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Something went wrong");
        }
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished(event -> successLabel.setVisible(false));
        visiblePause.play();
    }

    private void callAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(error);
        alert.showAndWait();
    }
}
