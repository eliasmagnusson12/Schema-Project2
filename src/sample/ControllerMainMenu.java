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
    private Label menuName;
    @FXML
    private Button loginButton;
    @FXML
    private Button settings;
    @FXML
    private Button sickness;
    @FXML
    private Button availableSlots;
    @FXML
    private Button logOff;
    @FXML
    private Hyperlink help;
    @FXML
    private HBox hBox;


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
    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
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
    private void handleExitButton(ActionEvent event) {
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

}

