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
    private TextField usernameTextfield, passwordTextField;
    @FXML
    private AnchorPane pane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image background = new Image("resourses/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws SQLException, IOException {
        String username = usernameTextfield.getText();
        String password = passwordTextField.getText();

        DBConnect dbConnect = new DBConnect();

//        Button button = (Button) event.getSource();
//        if (button.getId().equals("mailButton")){
//            hest = "sampleMail";
//        }else if (button.getId().equals("settingsButton")){
//            hest = "sampleSettings";
//        }


        if ((dbConnect.isPasswordCorrect(username, password))) {

            dbConnect.setUser(username);

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(  "sampleChangePassword.fxml"));
            stage.setTitle("Change Password");
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong username or password.");
            alert.showAndWait();
        }

        //test test test
    }
    }

