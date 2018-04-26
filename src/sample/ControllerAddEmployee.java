package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerAddEmployee implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addButton;
    @FXML
    private TextField firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private CheckBox checkBox1, checkBox2;

    private String department;

    private ArrayList<String> underDepartmentList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image mediumBackgroundimage = new Image("resourses/mediumBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(mediumBackgroundimage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image checkImage = new Image("resourses/check.png");
        ImageView checkImageView = new ImageView(checkImage);
        addButton.setGraphic(checkImageView);
        addButton.setStyle("-fx-background-color: TRANSPARENT");

        firstNameTextField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));
        lastNameTextField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));

    }

    @FXML
    private EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField textField = (TextField) event.getSource();
                if (textField.getText().length() >= max_Lengh) {
                    event.consume();
                }
                if (event.getCharacter().matches("[A-Za-z]")) {
                } else {
                    event.consume();
                }
            }
        };
    }

    @FXML
    private void handleInput(ActionEvent event) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
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
    private void handleCheckBoxes(ActionEvent event){
        CheckBox checkBox = (CheckBox) event.getSource();
        if (checkBox.getId().equals("checkBox1")){
            department = "Lanternan";
            checkBox2.setSelected(false);
        }else if (checkBox.getId().equals("checkBox2")){
            department = "Åhaga";
            checkBox1.setSelected(false);

        }
        setChoiseBox(department);
    }

    private void setChoiseBox(String department){
        DBConnect dbConnect = new DBConnect();
        underDepartmentList = (dbConnect.getUnderDepartments(department));

        for (int i = 0; i<underDepartmentList.size(); i++) {


        }
        choiceBox.setItems(FXCollections.observableArrayList(underDepartmentList.get(0), underDepartmentList.get(1)));
    }
}
