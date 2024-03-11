package lk.intelleon.springbootrestfulwebservices.javaFxController;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class SignupController {
    @FXML
    public ComboBox<String> cmbStatus;
    boolean isMatchUserName = false;
    boolean isMatchEmail = false;
    boolean isMatchPassword = false;
    @FXML
    private AnchorPane signUpAnchroPane;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;

    public void initialize(){
        // Initialize the ComboBox with"Admin" and "User") values
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Admin", "User");
        cmbStatus.setItems(statusOptions);
    }

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
        if (isMatchUserName && isMatchEmail && isMatchPassword) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/user/register");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create SupplierDTO object
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(txtUserName.getText());
                userDTO.setEmail(txtEmail.getText());
                userDTO.setPassword(txtPassword.getText());
                userDTO.setStatus(cmbStatus.getValue());
                //

                // Convert SupplierDTO object to JSON
                Gson gson = new Gson();
                String jsonInputString = gson.toJson(userDTO);

                // Send JSON data
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("User saved successfully!");
                    showConfirmationMessage("User saved successfully!");
                    clearTextFields();
                } else {
                    // Handle error response
                    System.out.println("Error saving User: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Display an error message or handle the case where conditions are not met
            showErrorAlert("Please ensure all fields are valid before saving.");
        }
    }

    private void showConfirmationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void clearTextFields() {
        txtUserName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
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

    public void txtEmailOnAction(KeyEvent keyEvent) {
        if (Service.isValidEmail(txtEmail.getText())) {
            txtEmail.setStyle("-fx-border-color: green");
            isMatchEmail = true;
        } else {
            txtEmail.setStyle("-fx-border-color: red");
            isMatchEmail = false;
        }
    }

}
