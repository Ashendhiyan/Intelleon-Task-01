package lk.intelleon.springbootrestfulwebservices.javaFxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

@Component
public class LoginController {
    @FXML
    private AnchorPane loginAnchorPain;

    @FXML
    private TextField txtUserName;

    @FXML
    private ComboBox<?> cmbRank;

    @FXML
    private PasswordField txtPassword;

    boolean isMatchUserName = false;
    boolean isMatchPassword = false;



    @FXML       //  authentication
    void LoginOnAction(ActionEvent event) {
        try {
            // Load the FXML file for the dashboard form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent dashboardForm = loader.load();

            // Create a new scene with the dashboard form
            Scene dashboardScene = new Scene(dashboardForm);

            // Get the stage information
            Stage stage = (Stage) loginAnchorPain.getScene().getWindow();

            // Set the dashboard scene on the stage
            stage.setScene(dashboardScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSignUp(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the signup form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
            Parent signupForm = loader.load();

            // Create a new scene with the signup form
            Scene signupScene = new Scene(signupForm);

            // Get the stage information
            Stage stage = (Stage) loginAnchorPain.getScene().getWindow();

            // Set the signup scene on the stage
            stage.setScene(signupScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtUserNameOnAction(KeyEvent keyEvent) {
        if (Service.isValidName(txtUserName.getText())) {
            txtUserName.setStyle("-fx-border-color: green");
            isMatchUserName = true;
        } else {
            txtUserName.setStyle("-fx-border-color: red");
            isMatchUserName = false;
        }
    }

    public void txtPasswordOnAction(KeyEvent keyEvent) {
        if (Service.isValidPassword(txtPassword.getText())) {
            txtPassword.setStyle("-fx-border-color: green");
            isMatchPassword = true;
        } else {
            txtPassword.setStyle("-fx-border-color: red");
            isMatchPassword = false;
        }
    }
}
