package lk.intelleon.springbootrestfulwebservices.javaFxController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.intelleon.springbootrestfulwebservices.dto.InventoryDTO;
import lk.intelleon.springbootrestfulwebservices.dto.ItemDTO;
import lk.intelleon.springbootrestfulwebservices.entity.InventoryEntity;
import lk.intelleon.springbootrestfulwebservices.util.LocalDateAdapter;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.stereotype.Component;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lk.intelleon.springbootrestfulwebservices.javaFxController.LoginController.authToken;
import static lk.intelleon.springbootrestfulwebservices.util.MailSenderUtil.sendEmail;

@Component
public class InventoryController {

    public TableColumn<InventoryDTO, String> colExpDate;
    public DatePicker expDatePicker;
    public Label expLable;
    boolean isMatchQty = false;
    private ObservableList<InventoryDTO> inventoryList = FXCollections.observableArrayList();
    @FXML
    private TextField txtReceived_qty;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private TableView<InventoryDTO> tblInventory;
    @FXML
    private TableColumn<InventoryDTO, Long> colId;
    @FXML
    private TableColumn<InventoryDTO, String> colItem;
    @FXML
    private TableColumn<InventoryDTO, String> collDate;
    @FXML
    private TableColumn<InventoryDTO, Integer> colQty;
    @FXML
    private TableColumn<InventoryDTO, String> colApproval;
    @FXML
    private TableColumn<InventoryDTO, String> colStatus;
    @FXML
    private ComboBox<ItemDTO> cmbItems;
    @FXML
    private ComboBox<String> cmbApproval;
    @FXML
    private DatePicker datePicker;

    @FXML
    void SaveOnAction(ActionEvent event) {
        if (isMatchQty) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/inventory");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                // Set authToken as a header
                conn.setRequestProperty("Authorization", "Bearer " + authToken);

                // Create InventoryDTO object
                InventoryDTO inventoryDTO = new InventoryDTO();
                inventoryDTO.setItemId(cmbItems.getValue().getId());
                inventoryDTO.setReceivedDate(datePicker.getValue());
                inventoryDTO.setExpireDate(expDatePicker.getValue());
                inventoryDTO.setReceivedQty(txtReceived_qty.getText());
                inventoryDTO.setApprovalStatus(cmbApproval.getValue());
                inventoryDTO.setStatus(cmbStatus.getValue());

                // Convert InventoryDTO object to JSON
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                String jsonInputString = gson.toJson(inventoryDTO);

                // Send JSON data
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Inventory saved successfully!");
                    showConfirmationMessage("Inventory saved successfully!");
                    loadDataFromBackend(); // Refresh data
                    loadDataAndSetToTable();
                    clearFields(); // Clear input fields
                } else {
                    // Handle error response
                    System.out.println("Error saving inventory: " + responseCode);
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
        if (isMatchQty) {
            InventoryDTO selectedItem = tblInventory.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    URL url = new URL("http://localhost:8080/api/v1/inventory/" + selectedItem.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    // Set authToken as a header
                    conn.setRequestProperty("Authorization", "Bearer " + authToken);

                    // Create updated InventoryDTO object
                    InventoryDTO updatedInventoryDTO = new InventoryDTO();
                    updatedInventoryDTO.setItemId(cmbItems.getValue().getId());
                    updatedInventoryDTO.setReceivedDate(datePicker.getValue());
                    updatedInventoryDTO.setExpireDate(expDatePicker.getValue());
                    updatedInventoryDTO.setReceivedQty(txtReceived_qty.getText());
                    updatedInventoryDTO.setApprovalStatus(cmbApproval.getValue());
                    updatedInventoryDTO.setStatus(cmbStatus.getValue());

                    // Convert updated InventoryDTO object to JSON
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();

                    String jsonInputString = gson.toJson(updatedInventoryDTO);

                    // Send JSON data
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Handle successful response
                        System.out.println("Inventory updated successfully!");
                        showConfirmationMessage("Inventory updated successfully!");
                        loadDataFromBackend(); // Refresh data
                        loadDataAndSetToTable();
                        clearFields();
                    } else {
                        // Handle error response
                        System.out.println("Error updating inventory: " + responseCode);
                    }

                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle case where no item is selected
                System.out.println("Please select an inventory item to update.");
            }
        } else {
            // Display an error message or handle the case where conditions are not met
            showErrorAlert("Please ensure all fields are valid before updating.");
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        InventoryDTO selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/inventory/" + selectedItem.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                // Set authToken as a header
                conn.setRequestProperty("Authorization", "Bearer " + authToken);

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Inventory item deleted successfully!");
                    showConfirmationMessage("Inventory item deleted successfully!");
                    loadDataFromBackend(); // Refresh data
                } else {
                    // Handle error response
                    System.out.println("Error deleting inventory item: " + responseCode);
                    showErrorAlert("Error deleting inventory item: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where no item is selected
            System.out.println("Please select an inventory item to delete.");
            showErrorAlert("Please select an inventory item to delete.");
        }
    }

    @FXML
    public void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Check if it's a one-click event
            InventoryDTO selectedItem = tblInventory.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Populate the input fields with the selected item's data
                txtReceived_qty.setText(String.valueOf(selectedItem.getReceivedQty()));
                cmbStatus.setValue(selectedItem.getStatus());
                cmbApproval.setValue(selectedItem.getApprovalStatus());
                // Set the date picker value
                datePicker.setValue(selectedItem.getReceivedDate());
                expDatePicker.setValue(selectedItem.getExpireDate());
                // Select the corresponding item in the ComboBox
                for (ItemDTO item : cmbItems.getItems()) {
                    if (item.getId().equals(selectedItem.getItemId())) {
                        cmbItems.getSelectionModel().select(item);
                        break;
                    }
                }
            }
        }
    }

    public void initialize() {
        loadItemsFromBackend();
        loadDataFromBackend();
        delayLoadDataAndSetToTable(); // Call the delayed method

        // Initialize ComboBoxes
        ObservableList<String> approvalStatus = FXCollections.observableArrayList("Pending", "Approved");
        ObservableList<String> status = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(status);
        cmbApproval.setItems(approvalStatus);

        // Initialize table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        collDate.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
        colExpDate.setCellValueFactory(new PropertyValueFactory<>("expireDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("receivedQty"));
        colApproval.setCellValueFactory(new PropertyValueFactory<>("approvalStatus"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set data to table
        tblInventory.setItems(inventoryList);
    }

    private void delayLoadDataAndSetToTable() {
        // Create a PauseTransition with a duration of 5 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));

        // Set the action to be performed after the delay
        delay.setOnFinished(event -> loadDataAndSetToTable());

        // Start the delay
        delay.play();
    }

    private void loadItemsFromBackend() {
        // Implement method to load items from backend and populate the ComboBox
        try {
            ObservableList<ItemDTO> itemsList = FXCollections.observableArrayList();

            URL url = new URL("http://localhost:8080/api/v1/item");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // Set authToken as a header
            conn.setRequestProperty("Authorization", "Bearer " + authToken);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new Gson();
                    ItemDTO[] itemsArray = gson.fromJson(reader, ItemDTO[].class);
                    itemsList.addAll(Arrays.asList(itemsArray));
                }
            } else {
                System.out.println("Error fetching items: " + responseCode);
            }

            cmbItems.setItems(itemsList);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<InventoryDTO> loadDataFromBackend() {
        List<InventoryDTO> itemList = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:8080/api/v1/inventory");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // Set authToken as a header
            conn.setRequestProperty("Authorization", "Bearer " + authToken);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();

                    InventoryDTO[] inventoryArray = gson.fromJson(reader, InventoryDTO[].class);
                    inventoryList.clear();
                    inventoryList.addAll(Arrays.asList(inventoryArray));
                    itemList.addAll(Arrays.asList(inventoryArray));
                }
            } else {
                System.out.println("Error fetching inventory data: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }


    private void loadDataAndSetToTable() {
        List<InventoryDTO> itemList = loadDataFromBackend();

        for (InventoryDTO item : itemList) {
            LocalDate currentDate = LocalDate.now();
            LocalDate expireDate = item.getExpireDate();

            if (expireDate != null && expireDate.isBefore(currentDate.plusDays(10))) {
                showAlert(null, "Warning", "Inventory id " + item.getId() + " will expire in " + item.getExpireDate());
                sendEmail("dyan13516@gmail.com", "expire date", String.valueOf(item));
                continue;
            }
        }
        loadDataFromBackend();
//        updateTableView(itemList);
    }

    //    update table
    private void updateTableView(List<InventoryDTO> itemList) {
        ObservableList<InventoryDTO> observableList = FXCollections.observableArrayList(itemList);
        tblInventory.setItems(observableList);
    }
    private void clearFields() {
        // Implement method to clear input fields
        txtReceived_qty.clear();
        cmbStatus.getSelectionModel().clearSelection();
        cmbItems.getSelectionModel().clearSelection();
        cmbApproval.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        expDatePicker.setValue(null);
    }

    public void txtReceived_qtyOnAction(KeyEvent keyEvent) {
        if (Service.receivedQty(txtReceived_qty.getText())) {
            txtReceived_qty.setStyle("-fx-border-color: green");
            isMatchQty = true;
        } else {
            txtReceived_qty.setStyle("-fx-border-color: red");
            isMatchQty = false;
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
    private void showAlert(HttpResponse<String> response, String title, String contentText) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.setContentText(contentText);
            alert.showAndWait();
        });
    }
}
