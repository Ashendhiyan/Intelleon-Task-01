package lk.intelleon.springbootrestfulwebservices.javaFxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

@Component
public class SignupController {
    @FXML
    private AnchorPane signUpAnchroPane;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    void btnLogin(ActionEvent event) {
        try {
            // Load the FXML file for the signup form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent loginForm = loader.load();

            // Create a new scene with the signup form
            Scene loginScene = new Scene(loginForm);

            // Get the stage information
            Stage stage = (Stage) signUpAnchroPane.getScene().getWindow();

            // Set the signup scene on the stage
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void signUpOnAction(ActionEvent event) {

    }
}
