package lk.intelleon.springbootrestfulwebservices.javaFxController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.dto.InventoryDTO;
import lk.intelleon.springbootrestfulwebservices.dto.ItemDTO;
import lk.intelleon.springbootrestfulwebservices.util.LocalDateAdapter;
import lk.intelleon.springbootrestfulwebservices.util.Service;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class InventoryController {

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
        try {
            URL url = new URL("http://localhost:8080/api/v1/inventory");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create InventoryDTO object
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setItemId(cmbItems.getValue().getId());
            inventoryDTO.setReceivedDate(datePicker.getValue());
            inventoryDTO.setReceivedQty(Integer.parseInt(txtReceived_qty.getText()));
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
                loadDataFromBackend(); // Refresh data
                clearFields(); // Clear input fields
            } else {
                // Handle error response
                System.out.println("Error saving inventory: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        InventoryDTO selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/inventory/" + selectedItem.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create updated InventoryDTO object
                InventoryDTO updatedInventoryDTO = new InventoryDTO();
                updatedInventoryDTO.setItemId(cmbItems.getValue().getId());
                updatedInventoryDTO.setReceivedDate(datePicker.getValue());
                updatedInventoryDTO.setReceivedQty(Integer.parseInt(txtReceived_qty.getText()));
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
                    loadDataFromBackend();
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
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        InventoryDTO selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/inventory/" + selectedItem.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Inventory item deleted successfully!");
                    loadDataFromBackend();
                } else {
                    // Handle error response
                    System.out.println("Error deleting inventory item: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where no item is selected
            System.out.println("Please select an inventory item to delete.");
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

        // Initialize ComboBoxes
        ObservableList<String> approvalStatus = FXCollections.observableArrayList("Pending", "Approved");
        ObservableList<String> status = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(status);
        cmbApproval.setItems(approvalStatus);

        // Initialize table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        collDate.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("receivedQty"));
        colApproval.setCellValueFactory(new PropertyValueFactory<>("approvalStatus"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set data to table
        tblInventory.setItems(inventoryList);
    }

    private void loadItemsFromBackend() {
        // Implement method to load items from backend and populate the ComboBox
        try {
            ObservableList<ItemDTO> itemsList = FXCollections.observableArrayList();

            URL url = new URL("http://localhost:8080/api/v1/item");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

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

    private void loadDataFromBackend() {
        try {
            URL url = new URL("http://localhost:8080/api/v1/inventory");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();

                    InventoryDTO[] inventoryArray = gson.fromJson(reader, InventoryDTO[].class);
                    inventoryList.clear();
                    inventoryList.addAll(Arrays.asList(inventoryArray));
                }
            } else {
                System.out.println("Error fetching inventory data: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        // Implement method to clear input fields
        txtReceived_qty.clear();
        cmbStatus.getSelectionModel().clearSelection();
        cmbItems.getSelectionModel().clearSelection();
        cmbApproval.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }

    boolean isMatchQty=false;
    public void txtReceived_qtyOnAction(KeyEvent keyEvent) {
        if (Service.receivedQty(txtReceived_qty.getText())) {
            txtReceived_qty.setStyle("-fx-border-color: green");
            isMatchQty = true;
        } else {
            txtReceived_qty.setStyle("-fx-border-color: red");
            isMatchQty = false;
        }
    }
}
