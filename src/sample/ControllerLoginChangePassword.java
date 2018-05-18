package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLoginChangePassword implements Initializable {


    @FXML
    private TextField usernameTextField, passwordTextField;
    @FXML
    private AnchorPane pane;

    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image background = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws SQLException, IOException {
        username = usernameTextField.getText();
        String password = passwordTextField.getText();


        if (username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter username");
            alert.showAndWait();
        } else if (password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter password");
            alert.showAndWait();

        } else if ((DBConnect.getDBCon().isPasswordCorrect(username, password))) {

            DBConnect.getDBCon().setUser(username);

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sampleChangePassword.fxml"));
            stage.setTitle("Change Password");
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong username or password.");
            alert.showAndWait();
        }
    }
}

