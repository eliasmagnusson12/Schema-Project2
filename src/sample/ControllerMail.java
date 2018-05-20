package sample;

import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;


public class ControllerMail implements Initializable {

    @FXML
    private CheckBox box1, box2, box3, box4;
    @FXML
    private TextArea mailTextArea;
    @FXML
    private Button send;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField subject;
    @FXML
    private Label messageSent;

    private String email;
    private String pw;
    private boolean answer;

    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    private final int maxNumSelected = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image smallBackgroundImage = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(smallBackgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image sendImage = new Image("resources/sendButton.png");
        send.setGraphic(new ImageView(sendImage));
        send.setStyle("-fx-background-color: TRANSPARENT");


        configureCheckBox(box1);
        configureCheckBox(box2);
        configureCheckBox(box3);
        configureCheckBox(box4);

        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (newSelectedCount.intValue() >= maxNumSelected) {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
                send.setDisable(false);
            } else {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
                send.setDisable(true);
            }
        });

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
    public void sendEmail() {

        String from = "kristianstad.gadors@hotmail.com";
        String pass = "gadors123";

        String to = email;

        String host = "smtp.live.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        try {


            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipients(RecipientType.TO, to);

            message.setSubject(subject.getText());

            message.setText(mailTextArea.getText() + "\n\nThis email was sent by " + Singleton.getInstance().getUser().getFirstName() + " "
                    + Singleton.getInstance().getUser().getLastName() + "\n\nYou can not respond to this mail. ");


            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            subject.clear();
            mailTextArea.clear();
            subject.setPromptText("Subject..");
            mailTextArea.setPromptText("Message..");

            messageSent.setTextFill(Color.GREEN);
            messageSent.setText("Your message was sent successfully");


        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void toEmail(ActionEvent event) {
        if (event.getSource() instanceof CheckBox) {
            String id = ((CheckBox) event.getSource()).getId();

            switch (id) {
                case "box1":
                    email = "Schedule.Kristianstad@hotmail.com";
                    break;
                case "box2":
                    email = "some Email";
                    break;
                case "box3":
                    email = "some other Email";
                    break;
                case "box4":
                    email = "some other other Email";
                    break;
            }
        }
    }

    private void configureCheckBox(CheckBox checkBox) {

        if (checkBox.isSelected()) {
            selectedCheckBoxes.add(checkBox);
        } else {
            unselectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                unselectedCheckBoxes.remove(checkBox);
                selectedCheckBoxes.add(checkBox);
            } else {
                selectedCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }

        });
    }

    public String sendFirstPW(Person person) throws AddressException {
        answer = true;

        String from = "kristianstad.gadors@hotmail.com";
        String pass = "gadors123";

        String to = person.getEmail();

        String host = "smtp.live.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        try {


            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(RecipientType.TO, to);

            pw = getPW();
            message.setSubject("Your password");
            message.setText("Your password is " + pw + "\n\nYou can not respond to this mail. ");


            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();


        } catch (Exception e) {
            answer = false;
        }
        return pw;
    }


    public boolean sendNewPassword(String email, String pw) throws MessagingException, SQLException {
        try {
            answer = true;
            String from = "kristianstad.gadors@hotmail.com";
            String pass = "gadors123";

            String to = email;

            String host = "smtp-mail.outlook.com";
            Properties properties = System.getProperties();

            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.user", from);
            properties.put("mail.smtp.password", pass);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(properties);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(RecipientType.TO, to);

            message.setSubject("Your new password");
            message.setText("Your new password is " + pw + "\n\nYou can not respond to this mail. ");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();


        }catch (Exception e){
            answer = false;
        }
        return answer;
    }

    public String getPW() {
        String pwChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String pw = RandomStringUtils.random(8, pwChars);

        return pw;
    }
}