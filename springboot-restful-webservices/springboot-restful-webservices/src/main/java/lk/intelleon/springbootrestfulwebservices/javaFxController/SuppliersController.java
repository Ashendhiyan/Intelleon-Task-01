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
import lk.intelleon.springbootrestfulwebservices.dto.SupplierDTO;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static lk.intelleon.springbootrestfulwebservices.javaFxController.LoginController.authToken;

@Component
public class SuppliersController {
    boolean isMatchSupplierCode = false;
    boolean isMatchSupplierName = false;
    boolean isMatchSupplierAddress = false;
    private ObservableList<SupplierDTO> suppliersList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane supplierPane;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;
    @FXML
    private TableView<SupplierDTO> tblSuppliers;
    @FXML
    private TableColumn<SupplierDTO, String> colId;
    @FXML
    private TableColumn<SupplierDTO, String> colCode;
    @FXML
    private TableColumn<SupplierDTO, String> colName;
    @FXML
    private TableColumn<SupplierDTO, String> colAddress;
    @FXML
    private TableColumn<SupplierDTO, String> colStatus;


    public void initialize() {
        // Initialize the ComboBox with "Active" and "Inactive" values
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusOptions);


        // Initialize table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("supplierCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set data to table
        tblSuppliers.setItems(suppliersList);

        // Load data from backend
        loadDataFromBackend();
    }

    private void loadDataFromBackend() {
        try {
            suppliersList.clear();
            URL url = new URL("http://localhost:8080/api/v1/suppliers");
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
                    SupplierDTO[] suppliersArray = gson.fromJson(reader, SupplierDTO[].class);
                    suppliersList.addAll(Arrays.asList(suppliersArray));
                }
            } else {
                System.out.println("Error fetching suppliers: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Check if single click
            SupplierDTO selectedSupplier = (SupplierDTO) tblSuppliers.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                // Populate text fields with selected row data
                txtCode.setText(selectedSupplier.getSupplierCode());
                txtName.setText(selectedSupplier.getName());
                txtAddress.setText(selectedSupplier.getAddress());
                cmbStatus.setValue(selectedSupplier.getStatus());
            }
        }
    }

    public void clearTextFields() {
        txtCode.setText("");
        txtName.setText("");
        txtAddress.setText("");
        cmbStatus.setValue("");
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        if (isMatchSupplierCode && isMatchSupplierName && isMatchSupplierAddress) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/suppliers");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                // Set authToken as a header
                conn.setRequestProperty("Authorization", "Bearer " + authToken);

                // Create SupplierDTO object
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setSupplierCode(txtCode.getText());
                supplierDTO.setName(txtName.getText());
                supplierDTO.setAddress(txtAddress.getText());
                supplierDTO.setStatus(cmbStatus.getValue());
                //

                // Convert SupplierDTO object to JSON
                Gson gson = new Gson();
                String jsonInputString = gson.toJson(supplierDTO);

                // Send JSON data
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Supplier saved successfully!");
                    showConfirmationMessage("Supplier saved successfully!");
                    loadDataFromBackend();
                    clearTextFields();
                } else {
                    // Handle error response
                    System.out.println("Error saving supplier: " + responseCode);
                    showErrorAlert("Error saving supplier: " + responseCode);
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
    void UpdateOnAction(ActionEvent event) {
        if (isMatchSupplierCode && isMatchSupplierName && isMatchSupplierAddress) {
            SupplierDTO selectedSupplier = tblSuppliers.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                try {
                    URL url = new URL("http://localhost:8080/api/v1/suppliers/" + selectedSupplier.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    // Set authToken as a header
                    conn.setRequestProperty("Authorization", "Bearer " + authToken);

                    // Create SupplierDTO object with updated details
                    selectedSupplier.setSupplierCode(txtCode.getText());
                    selectedSupplier.setName(txtName.getText());
                    selectedSupplier.setAddress(txtAddress.getText());
                    selectedSupplier.setStatus(cmbStatus.getValue());

                    // Convert SupplierDTO object to JSON
                    Gson gson = new Gson();
                    String jsonInputString = gson.toJson(selectedSupplier);

                    // Send JSON data
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Handle successful response
                        System.out.println("Supplier updated successfully!");
                        showConfirmationMessage("Supplier updated successfully!");
                        // Refresh table data after update
                        loadDataFromBackend();
                        clearTextFields();
                    } else {
                        // Handle error response
                        System.out.println("Error updating supplier: " + responseCode);
                        showErrorAlert("Error updating supplier: " + responseCode);
                    }

                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Please select a supplier to update.");
            }
        } else {
            // Display an error message or handle the case where conditions are not met
            showErrorAlert("Please ensure all fields are valid before updating.");
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        SupplierDTO selectedSupplier = tblSuppliers.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/suppliers/" + selectedSupplier.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                // Set authToken as a header
                conn.setRequestProperty("Authorization", "Bearer " + authToken);

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Remove deleted supplier from the table
                    showConfirmationMessage("Supplier deleted successfully!");
                    suppliersList.remove(selectedSupplier);
                    System.out.println("Supplier deleted successfully!");
                    clearTextFields();
                } else {
                    System.out.println("Error deleting supplier: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a supplier to delete.");
        }
    }

    public void txtCodeOnAction(KeyEvent keyEvent) {
        if (Service.isValidSupplierCode(txtCode.getText())) {
            txtCode.setStyle("-fx-border-color: green");
            isMatchSupplierCode = true;
        } else {
            txtCode.setStyle("-fx-border-color: red");
            isMatchSupplierCode = false;
        }
    }

    public void txtNameOnAction(KeyEvent keyEvent) {
        if (Service.isValidName(txtName.getText())) {
            txtName.setStyle("-fx-border-color: green");
            isMatchSupplierName = true;
        } else {
            txtName.setStyle("-fx-border-color: red");
            isMatchSupplierName = false;
        }
    }

    public void txtAddressOnAction(KeyEvent keyEvent) {
        if (Service.isValidName(txtAddress.getText())) {
            txtAddress.setStyle("-fx-border-color: green");
            isMatchSupplierAddress = true;
        } else {
            txtAddress.setStyle("-fx-border-color: red");
            isMatchSupplierAddress = false;
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
