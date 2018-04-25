package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


public class ControllerMail implements Initializable {

    @FXML
    CheckBox box1, box2, box3, box4;
    @FXML
    TextArea mailTextArea;
    @FXML
    Button send;
    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField subject;
    @FXML
    Label messageSent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image sendImage = new Image("resourses/sendButton.png");
        ImageView sendImageView = new ImageView(sendImage);
        send.setGraphic(sendImageView);
        send.setStyle("-fx-background-color: TRANSPARENT");

        Image smallBackgroundImage = new Image("resourses/smallBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(smallBackgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));


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
    public void sendEmail(){

        String from = "kristianstad.gadors@hotmail.com";
        String pass = "gadors123";

        String to = "Schedule.Kristianstad@hotmail.com";

        String host = "smtp.live.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        try{


            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipients(RecipientType.TO, to);

            message.setSubject(subject.getText());

            message.setText(mailTextArea.getText() + "\nYou can not respond to this mail. ");



            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Sent message successfully....");

            subject.clear();
            mailTextArea.clear();
            subject.setPromptText("Subject...");
            mailTextArea.setPromptText("Message...");

            messageSent.setText("Your message was sent successfully");





        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
