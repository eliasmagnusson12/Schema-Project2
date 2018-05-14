package sample;

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
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerMainMenu implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button exitButton, settingsButton, mailButton, loginButton, helpButton, infoButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image logo = new Image("resources/logo.png");
        ImageView imageView = new ImageView(logo);
        anchorPane.getChildren().add(imageView);
        imageView.fitWidthProperty().bind(anchorPane.widthProperty().divide(1.5));
        imageView.setX(70);

        Image exitImage = new Image("resources/exit.png");
        exitButton.setGraphic(new ImageView(exitImage));
        exitButton.setStyle("-fx-background-color: TRANSPARENT");

        Image settingsImage = new Image("resources/settings.png");
        settingsButton.setGraphic(new ImageView(settingsImage));
        settingsButton.setStyle("-fx-background-color: TRANSPARENT");

        Image mailImage = new Image("resources/email.png");
        mailButton.setGraphic(new ImageView(mailImage));
        mailButton.setStyle("-fx-background-color: TRANSPARENT");

        Image loginImage = new Image("resources/login.png");
        loginButton.setGraphic(new ImageView(loginImage));
        loginButton.setStyle("-fx-background-color: TRANSPARENT");

        Image helpImage = new Image("resources/help.png");
        helpButton.setGraphic(new ImageView(helpImage));
        helpButton.setStyle("-fx-background-color: TRANSPARENT");

        Image infoImage = new Image("resources/info.png");
        infoButton.setGraphic(new ImageView(infoImage));
        infoButton.setStyle("-fx-background-color: TRANSPARENT");

    }

    @FXML
    private void handlePressLoginButton(ActionEvent event) throws IOException {
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
    private void handlePressExitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes!");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No!");
        alert.setHeaderText("Are you sure you want to exit the application?");
        alert.setTitle("Exit");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(1);
        }
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
    private void handlePressSettingsButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleSettings.fxml"));
        stage.setTitle("Settings");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlePressMailButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleLoginEmail.fxml"));
        stage.setTitle("Mail");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlePressInfoButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleInfo.fxml"));
        stage.setTitle("Information");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlePressHelpButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleHelp.fxml"));
        stage.setTitle("Help");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}

