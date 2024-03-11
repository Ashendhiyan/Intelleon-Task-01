package lk.intelleon.springbootrestfulwebservices.javaFxController;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.intelleon.springbootrestfulwebservices.dto.UnitDTO;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static lk.intelleon.springbootrestfulwebservices.javaFxController.LoginController.authToken;

@Component
public class UnitController {
    @FXML
    public AnchorPane unitPane;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtCode;
    @FXML
    public ComboBox<String> cmbStatus;
    @FXML
    public Button btn_save;
    @FXML
    public Button btn_update;
    @FXML
    public Button btn_delete;
    @FXML
    public TableView<UnitDTO> tblUnit;
    @FXML
    public TableColumn<UnitDTO, String> colId;
    @FXML
    public TableColumn<UnitDTO, String> colCode;
    @FXML
    public TableColumn<UnitDTO, String> colName;
    @FXML
    public TableColumn<UnitDTO, String> colstatus;
    boolean isMatchUnitCode = false;
    boolean isMatchUnitName = false;
    private ObservableList<UnitDTO> unitList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize the ComboBox with "Active" and "Inactive" values
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusOptions);


        // Initialize table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set data to table
        tblUnit.setItems(unitList);

        // Load data from backend
        loadDataFromBackend();
    }

    private void loadDataFromBackend() {
        try {
            unitList.clear();
            URL url = new URL("http://localhost:8080/api/v1/unit");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // Set authToken as a header
            conn.setRequestProperty("Authorization", "Bearer " + authToken);

            // Get response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response data
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new Gson();
                    UnitDTO[] unitArray = gson.fromJson(reader, UnitDTO[].class);
                    unitList.addAll(Arrays.asList(unitArray));
                }
            } else {
                System.out.println("Error fetching units: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Check if single click
            UnitDTO selectedUnit = (UnitDTO) tblUnit.getSelectionModel().getSelectedItem();
            if (selectedUnit != null) {
                // Populate text fields with selected row data
                txtCode.setText(selectedUnit.getCode());
                txtName.setText(selectedUnit.getName());
                cmbStatus.setValue(selectedUnit.getStatus());
            }
        }
    }

    public void clearTextFields() {
        txtCode.setText("");
        txtName.setText("");
        cmbStatus.setValue("");
    }

    @FXML
    public void saveOnAction(ActionEvent actionEvent) {
        if (isMatchUnitCode && isMatchUnitName) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/unit");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                // Set authToken as a header
                conn.setRequestProperty("Authorization", "Bearer " + authToken);

                // Create SupplierDTO object
                UnitDTO unitDTO = new UnitDTO();
                unitDTO.setCode(txtCode.getText());
                unitDTO.setName(txtName.getText());
                unitDTO.setStatus(cmbStatus.getValue());
                //

                // Convert SupplierDTO object to JSON
                Gson gson = new Gson();
                String jsonInputString = gson.toJson(unitDTO);

                // Send JSON data
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Unit saved successfully!");
                    showConfirmationMessage("Unit saved successfully!");
                    loadDataFromBackend();
                    clearTextFields();
                } else {
                    // Handle error response
                    System.out.println("Error saving unit: " + responseCode);
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

    @FXML
    public void updateOnAction(ActionEvent actionEvent) {
        if (isMatchUnitCode && isMatchUnitName) {
            UnitDTO selectedUnit = tblUnit.getSelectionModel().getSelectedItem();
            if (selectedUnit != null) {
                try {
                    URL url = new URL("http://localhost:8080/api/v1/unit/" + selectedUnit.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    // Set authToken as a header
                    conn.setRequestProperty("Authorization", "Bearer " + authToken);

                    // Create SupplierDTO object with updated details
                    selectedUnit.setCode(txtCode.getText());
                    selectedUnit.setName(txtName.getText());
                    selectedUnit.setStatus(cmbStatus.getValue());

                    // Convert SupplierDTO object to JSON
                    Gson gson = new Gson();
                    String jsonInputString = gson.toJson(selectedUnit);

                    // Send JSON data
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Handle successful response
                        System.out.println("Unit updated successfully!");
                        showConfirmationMessage("Unit updated successfully!");
                        // Refresh table data after update
                        loadDataFromBackend();
                        clearTextFields();
                    } else {
                        // Handle error response
                        System.out.println("Error updating unit: " + responseCode);
                    }

                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Please select a unit to update.");
            }
        } else {
            // Display an error message or handle the case where conditions are not met
            showErrorAlert("Please ensure all fields are valid before updating.");
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        UnitDTO selectedUnit = tblUnit.getSelectionModel().getSelectedItem();
        if (selectedUnit != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/unit/" + selectedUnit.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                // Set authToken as a header
                conn.setRequestProperty("Authorization", "Bearer " + authToken);

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Remove deleted supplier from the table
                    unitList.remove(selectedUnit);
                    showConfirmationMessage("unit deleted successfully!");
                    System.out.println("unit deleted successfully!");
                    clearTextFields();
                } else {
                    System.out.println("Error deleting unit: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a unit to delete.");
        }
    }

    public void txtCodeOnAction(KeyEvent keyEvent) {
        if (Service.isValidUnitCode(txtCode.getText())) {
            txtCode.setStyle("-fx-border-color: green");
            isMatchUnitCode = true;
        } else {
            txtCode.setStyle("-fx-border-color: red");
            isMatchUnitCode = false;
        }
    }

    public void txtNameOnAction(KeyEvent keyEvent) {
        if (Service.isValidName(txtName.getText())) {
            txtName.setStyle("-fx-border-color: green");
            isMatchUnitName = true;
        } else {
            txtName.setStyle("-fx-border-color: red");
            isMatchUnitName = false;
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
}
