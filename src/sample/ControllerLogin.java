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
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerLogin implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button loginButton, backButton;
    @FXML
    private TextField usernameTextField, passwordTextField;
    @FXML
    private CheckBox checkBoxUsername, checkBoxPassword;
    @FXML
    private Hyperlink forgotLink;

    private String saveUsername, savePassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image background = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image logo = new Image("resources/logo.png");
        ImageView logoImageView = new ImageView(logo);
        anchorPane.getChildren().add(logoImageView);
        logoImageView.fitWidthProperty().bind(anchorPane.widthProperty().divide(1.5));
        logoImageView.setX(80);

        Image back = new Image("resources/arrowBsmall.png");
        ImageView imageView1 = new ImageView(back);
        backButton.setGraphic(imageView1);
        backButton.setStyle("-fx-background-color: TRANSPARENT");

        Image signInImage = new Image("resources/signIn.png");
        ImageView signInImageView = new ImageView(signInImage);
        loginButton.setGraphic(signInImageView);
        loginButton.setStyle("-fx-background-color: TRANSPARENT");



        try {
            checkForSavedData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        DBConnect dbConnect = new DBConnect();

        saveLoginData();

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

        }else if (dbConnect.isPasswordCorrect(username, password)) {

            dbConnect.setUser(username);
            dbConnect.getAllEmployees();
            dbConnect.getAllUnderDepartments();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sampleCalendar.fxml"));
            stage.setTitle("Schedule 1.0");
            if (Singleton.getInstance().isFullScreenSetting()){
                stage.setFullScreen(true);
            }
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

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleMainMenu.fxml"));
        stage.setTitle("Schedule 1.0");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void handleCheckBoxUsername(ActionEvent event) {
        if (event.getSource() instanceof CheckBox) {
            checkBoxUsername = (CheckBox) event.getSource();
            if (checkBoxUsername.isSelected()) {
                saveUsername = "true";
            } else {
                saveUsername = "false";
            }
        }
    }

    @FXML
    private void handleCheckBoxPassword(ActionEvent event) {
        if (event.getSource() instanceof CheckBox) {
            checkBoxPassword = (CheckBox) event.getSource();
            if (checkBoxPassword.isSelected()) {
                savePassword = "true";
            } else {
                savePassword = "false";
            }
        }
    }

    private void saveLoginData() throws IOException {
        File file = new File("save.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        Path path = Paths.get("save.txt");


        ArrayList saveLoginData = new ArrayList();
        saveLoginData.add(saveUsername);
        saveLoginData.add(usernameTextField.getText());
        saveLoginData.add(savePassword);
        saveLoginData.add(passwordTextField.getText());
        Files.write(path, saveLoginData, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);


    }

    private void checkForSavedData() throws IOException {
        File file = new File("save.txt");
        if (file.exists()) {

            BufferedReader bufferedReader = new BufferedReader(new FileReader("save.txt"));

            if (bufferedReader.readLine().equals("true")) {
                checkBoxUsername.fire();
                String username = new String(bufferedReader.readLine());
                usernameTextField.setText(username);

            }
            if (bufferedReader.readLine().equals("true")) {
                checkBoxPassword.fire();
                String password = new String(bufferedReader.readLine());
                passwordTextField.setText(password);
            }
            bufferedReader.close();
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
    private void handleForgotPasswordLink(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(  "sampleResetPassword.fxml"));
        stage.setTitle("Reset Password");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
