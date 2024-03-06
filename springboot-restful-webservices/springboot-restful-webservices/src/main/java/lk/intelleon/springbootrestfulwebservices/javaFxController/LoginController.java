package lk.intelleon.springbootrestfulwebservices.javaFxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

    @FXML
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
}
