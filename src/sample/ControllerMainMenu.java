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
    private Button exitButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button mailButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button infoButton;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("resourses/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image logo = new Image("resourses/logo.png");
        ImageView imageView = new ImageView(logo);
        anchorPane.getChildren().add(imageView);
        imageView.fitWidthProperty().bind(anchorPane.widthProperty().divide(1.5));
        imageView.setX(80);

        Image exitImage = new Image("resourses/exit.png");
        ImageView exitImageView = new ImageView(exitImage);
        exitButton.setGraphic(exitImageView);
        exitButton.setStyle("-fx-background-color: TRANSPARENT");

        Image settingsImage = new Image("resourses/settings.png");
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsButton.setGraphic(settingsImageView);
        settingsButton.setStyle("-fx-background-color: TRANSPARENT");

        Image mailImage = new Image("resourses/email.png");
        ImageView mailImageView = new ImageView(mailImage);
        mailButton.setGraphic(mailImageView);
        mailButton.setStyle("-fx-background-color: TRANSPARENT");

        Image loginImage = new Image("resourses/login.png");
        ImageView loginImageView = new ImageView(loginImage);
        loginButton.setGraphic(loginImageView);
        loginButton.setStyle("-fx-background-color: TRANSPARENT");

        Image helpImage = new Image("resourses/help.png");
        ImageView helpImageView = new ImageView(helpImage);
        helpButton.setGraphic(helpImageView);
        helpButton.setStyle("-fx-background-color: TRANSPARENT");

        Image infoImage = new Image("resourses/info.png");
        ImageView infoImageView = new ImageView(infoImage);
        infoButton.setGraphic(infoImageView);
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
        fxmlLoader.setLocation(getClass().getResource("sampleMail.fxml"));
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

