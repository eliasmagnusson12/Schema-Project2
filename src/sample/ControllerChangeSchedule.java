package sample;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerChangeSchedule implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ChoiceBox<String> personChoiceBox;
    @FXML
    private MenuButton menuButton;
    @FXML
    private TextField startTextField, endTextField;
    @FXML
    private Button addButton, toolTipButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label successLabel;

    private ArrayList<String> listOfNames = new ArrayList<>();
    private ObservableList<CheckMenuItem> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image mediumBackgroundImage = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(mediumBackgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image checkImage = new Image("resources/check.png");
        addButton.setGraphic(new ImageView(checkImage));
        addButton.setStyle("-fx-background-color: TRANSPARENT");

        Image toolTipImage = new Image("resources/toolTip.png");
        toolTipButton.setGraphic(new ImageView(toolTipImage));
        toolTipButton.setStyle("-fx-background-color: TRANSPARENT");
        toolTipButton.setTooltip(new Tooltip("Enter Time in format XXXX. Ex: 0700"));

        showUnderDepartments();

        menuButton.getItems().forEach((MenuItem menuItem) -> menuItem.setOnAction(ev -> {
            final List<String> selectedItems = menuButton.getItems().stream()
                    .filter(item -> CheckMenuItem.class.isInstance(item) && CheckMenuItem.class.cast(item).isSelected())
                    .map(MenuItem::getText)
                    .collect(Collectors.toList());
            addNamesToList(selectedItems);
        }));

    }

    private void showUnderDepartments() {
        ArrayList<String> listOfUnderDepartments = Singleton.getInstance().getListOfUnderDepartments();

        menuButton.getItems().clear();

        for (int i = 0; i < listOfUnderDepartments.size(); i++) {

            CheckMenuItem menuItem = new CheckMenuItem();
            menuItem.setText(listOfUnderDepartments.get(i));
            list.addAll(menuItem);
        }
        menuButton.getItems().addAll(list);
    }


    private void addNamesToList(List<String> selectedList) {
        ArrayList<Person> listOfEmployees = Singleton.getInstance().getListOfEmployees();

        for (int i = 0; i < listOfEmployees.size(); i++) {
            Person person = listOfEmployees.get(i);
            String departmentName = person.getDepartment();
            for (int x = 0; x < selectedList.size(); x++) {
                if (departmentName.equals(selectedList.get(x))) {
                    String name = person.getFirstName() + " " + person.getLastName();
                    if (!listOfNames.contains(name)) {
                        listOfNames.add(name);
                    }
                }
            }
        }
        addNamesToChoiceBox();
    }

    private void addNamesToChoiceBox() {
        ObservableList<String> files = FXCollections.observableArrayList();
        files.addAll(listOfNames);
        personChoiceBox.setItems(files);
    }

    @FXML
    private void handleAddButton(ActionEvent event) throws SQLException, InterruptedException {
        if (personChoiceBox.getValue() == null) {
            String error = "Must add employee!";
            callAlert(error);
        } else if (startTextField.getText().length() < 4 || endTextField.getText().length() < 4) {
            String error = "Invalid time format!";
            callAlert(error);
        } else {
            String startTime = startTextField.getText();
            String endTime = endTextField.getText();
            String name = personChoiceBox.getValue();
            String[] splitName = name.split(" ");
            String firstName = splitName[0];
            String lastName = splitName[1];
            LocalDate localDate = handleDatePicker();

            DBConnect dbConnect = new DBConnect();
            if (dbConnect.addToSchedule(startTime, endTime, firstName, lastName, localDate)) {
                setSuccessLabel(true);
            } else {
                setSuccessLabel(false);
            }
        }
    }

    @FXML
    private void handleStartTextField() {
        startTextField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(4));
        endTextField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(4));
        startTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    startTextField.setText(oldValue);
                }
            }
        });
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
            }
        };
    }

    private void callAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(error);
        alert.showAndWait();
    }

    @FXML
    private LocalDate handleDatePicker() {

        LocalDate date = datePicker.getValue();
        return date;
    }

    private void setSuccessLabel(boolean answer) {
        if (answer) {
            successLabel.setVisible(true);
            successLabel.setTextFill(Color.GREEN);
            successLabel.setText("Successfully added new time to schedule!");
        } else {
            successLabel.setVisible(true);
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Something went wrong!");
        }
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(3)
        );
        visiblePause.setOnFinished(
                event -> successLabel.setVisible(false)
        );
        visiblePause.play();
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
}

