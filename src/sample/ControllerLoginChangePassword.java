package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLoginChangePassword implements Initializable {

    //private String hest;

    @FXML
    private TextField usernameTextfield, passwordTextField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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


        if (password.equals(dbConnect.getUser(username))) {

            dbConnect.getData(username);

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(  "sampleChangePasword.fxml"));
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
    }
    }

