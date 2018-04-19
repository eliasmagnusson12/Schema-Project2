package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControllerLogin implements Initializable {

    @FXML
    AnchorPane anchorPane;
    @FXML
    Button loginButton;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.setStyle("-fx-backgorund-color: LIGHTGRAY");
    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        DBConnect dbConnect = new DBConnect();

        if (password.equals(dbConnect.getUser(username))) {

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sampleCalendar.fxml"));
            stage.setTitle("Schedule 1.0");
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }else{
            System.out.println("Something went wrong");
        }
    }
}
