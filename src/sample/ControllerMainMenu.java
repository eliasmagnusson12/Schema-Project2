package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainMenu implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label menuName;
    @FXML
    private Button schedule;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.setStyle("fx-background-color: RED");
    }

}

