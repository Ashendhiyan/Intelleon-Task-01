package lk.intelleon.springbootrestfulwebservices.javaFxController;

import com.google.gson.Gson;
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
import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class LoginController {
    public ComboBox<String> cmbStatus;
    boolean isMatchUserName = false;
    boolean isMatchPassword = false;
    @FXML
    private AnchorPane loginAnchorPain;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;

    @FXML
        //  authentication
    void LoginOnAction(ActionEvent event) {
        if (isMatchUserName && isMatchPassword) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/user/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create SupplierDTO object
                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setUserName(txtUserName.getText());
                loginDTO.setPassword(txtPassword.getText());
//                loginDTO.setStatus(cmbStatus.getValue());


                // Convert SupplierDTO object to JSON
                Gson gson = new Gson();
                String jsonInputString = gson.toJson(loginDTO);

                // Send JSON data
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //navigate to dashboard
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

                    // Handle successful response
                    System.out.println("Login successfully!");
                    showConfirmationMessage("Login successfully!");
                } else {
                    // Handle error response
                    System.out.println("Error login: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Display an error message or handle the case where conditions are not met
            showErrorAlert("Please enter valid Username and Password.");
            clearFields();
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

    public void clearFields(){
        txtUserName.setText("");
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

}
