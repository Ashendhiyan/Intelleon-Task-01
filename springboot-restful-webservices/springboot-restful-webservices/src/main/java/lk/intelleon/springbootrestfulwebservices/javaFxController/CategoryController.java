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
import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

@Component
public class CategoryController {
    @FXML
    public AnchorPane categoryPane;
    @FXML
    public TextField txtCode;
    @FXML
    public TextField txtName;
    @FXML
    public ComboBox<String> cmbStatus;
    @FXML
    public Button btn_save;
    @FXML
    public Button btn_update;
    @FXML
    public Button btn_delete;
    @FXML
    public TableView<CategoryDTO> tblCategory;
    @FXML
    public TableColumn<CategoryDTO, String> colId;
    @FXML
    public TableColumn<CategoryDTO, String> colCode;
    @FXML
    public TableColumn<CategoryDTO, String> colName;
    @FXML
    public TableColumn<CategoryDTO, String> colStatus;
    boolean isMatchICategoryCode = false;
    boolean isMatchCategoryName = false;
    private ObservableList<CategoryDTO> categoryList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize the ComboBox with "Active" and "Inactive" values
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusOptions);


        // Initialize table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set data to table
        tblCategory.setItems(categoryList);

        // Load data from backend
        loadDataFromBackend();
    }

    private void loadDataFromBackend() {
        try {
            categoryList.clear();
            URL url = new URL("http://localhost:8080/api/v1/category");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Get response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response data
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new Gson();
                    CategoryDTO[] categoryArray = gson.fromJson(reader, CategoryDTO[].class);
                    categoryList.addAll(Arrays.asList(categoryArray));
                }
            } else {
                System.out.println("Error fetching category: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Check if single click
            CategoryDTO selectedCategory = (CategoryDTO) tblCategory.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                // Populate text fields with selected row data
                txtCode.setText(selectedCategory.getCode());
                txtName.setText(selectedCategory.getName());
                cmbStatus.setValue(selectedCategory.getStatus());
            }
        }
    }

    public void clearTextFields() {
        txtCode.setText("");
        txtName.setText("");
        cmbStatus.setValue("");
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (isMatchICategoryCode && isMatchCategoryName) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/category");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create SupplierDTO object
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCode(txtCode.getText());
                categoryDTO.setName(txtName.getText());
                categoryDTO.setStatus(cmbStatus.getValue());
                //

                // Convert SupplierDTO object to JSON
                Gson gson = new Gson();
                String jsonInputString = gson.toJson(categoryDTO);

                // Send JSON data
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Category saved successfully!");
                    showConfirmationMessage("Category saved successfully!");
                    loadDataFromBackend();
                    clearTextFields();
                } else {
                    // Handle error response
                    System.out.println("Error saving Category: " + responseCode);
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

    public void updateOnAction(ActionEvent actionEvent) {
        if (isMatchICategoryCode && isMatchCategoryName) {
            CategoryDTO selectedCategory = tblCategory.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                try {
                    URL url = new URL("http://localhost:8080/api/v1/category/" + selectedCategory.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Create SupplierDTO object with updated details
                    selectedCategory.setCode(txtCode.getText());
                    selectedCategory.setName(txtName.getText());
                    selectedCategory.setStatus(cmbStatus.getValue());

                    // Convert SupplierDTO object to JSON
                    Gson gson = new Gson();
                    String jsonInputString = gson.toJson(selectedCategory);

                    // Send JSON data
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Handle successful response
                        System.out.println("Category updated successfully!");
                       showConfirmationMessage("Category updated successfully!");
                        // Refresh table data after update
                        loadDataFromBackend();
                        clearTextFields();
                    } else {
                        // Handle error response
                        System.out.println("Error updating Category: " + responseCode);
                    }

                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Please select a Category to update.");
            }
        } else {
            // Display an error message or handle the case where conditions are not met
            showErrorAlert("Please ensure all fields are valid before updating.");
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        CategoryDTO selectedCategory = tblCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/category/" + selectedCategory.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Remove deleted supplier from the table
                    showConfirmationMessage("Category deleted successfully!");
                    categoryList.remove(selectedCategory);
                    System.out.println("Category deleted successfully!");
                    clearTextFields();
                } else {
                    System.out.println("Error deleting Category: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a Category to delete.");
        }
    }

    public void txtCodeOnActiion(KeyEvent keyEvent) {
        if (Service.isValidCategoryCode(txtCode.getText())) {
            txtCode.setStyle("-fx-border-color: green");
            isMatchICategoryCode = true;
        } else {
            txtCode.setStyle("-fx-border-color: red");
            isMatchICategoryCode = false;
        }
    }

    public void txtNameOnAction(KeyEvent keyEvent) {
        if (Service.isValidName(txtName.getText())) {
            txtName.setStyle("-fx-border-color: green");
            isMatchCategoryName = true;
        } else {
            txtName.setStyle("-fx-border-color: red");
            isMatchCategoryName = false;
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
