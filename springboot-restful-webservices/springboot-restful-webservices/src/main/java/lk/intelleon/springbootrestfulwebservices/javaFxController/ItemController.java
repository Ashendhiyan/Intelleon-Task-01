package lk.intelleon.springbootrestfulwebservices.javaFxController;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.dto.ItemDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UnitDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

@Component
@Service
public class ItemController {

    private ObservableList<ItemDTO> itemList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane itemPane;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private TableView<ItemDTO> tblItems;

    @FXML
    private TableColumn<ItemDTO, String> colId;

    @FXML
    private TableColumn<ItemDTO, String> colCode;

    @FXML
    private TableColumn<ItemDTO, String> colName;

    public TableColumn<ItemDTO,String> colCategoryid;
    public TableColumn<ItemDTO,String>  colUnitId;

    @FXML
    private TableColumn<ItemDTO, String> colStatus;

    @FXML
    private ComboBox<CategoryDTO> cmbCategory;

    @FXML
    private ComboBox<UnitDTO> cmbUnit;

    public void initialize() {
        loadCmbDataFromBackend();
        loadDataFromBackend();
        // Initialize the ComboBox with "Active" and "Inactive" values
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusOptions);

        // Initialize table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategoryid.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colUnitId.setCellValueFactory(new PropertyValueFactory<>("unitId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set data to table
        tblItems.setItems(itemList);
    }

    // Method to load data from backend for both categories and units
    private void loadCmbDataFromBackend() {
        loadCategoryData();
        loadUnitData();
    }

    // Method to load category data from backend
    private void loadCategoryData() {
        try {
            ObservableList<CategoryDTO> categoryList = FXCollections.observableArrayList();

            URL url = new URL("http://localhost:8080/api/v1/category");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new Gson();
                    CategoryDTO[] categoryArray = gson.fromJson(reader, CategoryDTO[].class);
                    categoryList.addAll(Arrays.asList(categoryArray));
                }
            } else {
                System.out.println("Error fetching categories: " + responseCode);
            }

            cmbCategory.setItems(categoryList);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load unit data from backend
    private void loadUnitData() {
        try {
            ObservableList<UnitDTO> unitList = FXCollections.observableArrayList();

            URL url = new URL("http://localhost:8080/api/v1/unit");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new Gson();
                    UnitDTO[] unitArray = gson.fromJson(reader, UnitDTO[].class);
                    unitList.addAll(Arrays.asList(unitArray));
                }
            } else {
                System.out.println("Error fetching units: " + responseCode);
            }

            cmbUnit.setItems(unitList);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void SaveOnAction(ActionEvent event) {
        try {
            URL url = new URL("http://localhost:8080/api/v1/item");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create ItemDTO object
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(txtCode.getText());
            itemDTO.setName(txtName.getText());
            itemDTO.setCategoryId(cmbCategory.getValue().getId());
            itemDTO.setUnitId(cmbUnit.getValue().getId());
            itemDTO.setStatus(cmbStatus.getValue());

            // Convert ItemDTO object to JSON
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(itemDTO);

            // Send JSON data
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Handle successful response
                System.out.println("Item saved successfully!");
                loadDataFromBackend(); // Refresh data
                clearTextFields(); // Clear input fields
            } else {
                // Handle error response
                System.out.println("Error saving item: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        ItemDTO selectedItem = tblItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/item/" + selectedItem.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create updated ItemDTO object
                ItemDTO updatedItemDTO = new ItemDTO();
                updatedItemDTO.setCode(txtCode.getText());
                updatedItemDTO.setName(txtName.getText());
                updatedItemDTO.setCategoryId(cmbCategory.getValue().getId());
                updatedItemDTO.setUnitId(cmbUnit.getValue().getId());
                updatedItemDTO.setStatus(cmbStatus.getValue());

                // Convert updated ItemDTO object to JSON
                Gson gson = new Gson();
                String jsonInputString = gson.toJson(updatedItemDTO);

                // Send JSON data
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Item updated successfully!");
                    loadDataFromBackend();
                    clearTextFields();
                } else {
                    // Handle error response
                    System.out.println("Error updating item: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where no item is selected
            System.out.println("Please select an item to update.");
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        ItemDTO selectedItem = tblItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                URL url = new URL("http://localhost:8080/api/v1/item/" + selectedItem.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");

                // Get response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Handle successful response
                    System.out.println("Item deleted successfully!");
                    loadDataFromBackend();
                } else {
                    // Handle error response
                    System.out.println("Error deleting item: " + responseCode);
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where no item is selected
            System.out.println("Please select an item to delete.");
        }
    }

    @FXML
    void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Check if it's a one-click event
            ItemDTO selectedItem = tblItems.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Populate the input fields with the selected item's data
                txtCode.setText(selectedItem.getCode());
                txtName.setText(selectedItem.getName());
                cmbStatus.setValue(selectedItem.getStatus());

                // Select the corresponding category and unit in the ComboBoxes
                for (CategoryDTO category : cmbCategory.getItems()) {
                    if (category.getId().equals(selectedItem.getCategoryId())) {
                        cmbCategory.getSelectionModel().select(category);
                        break;
                    }
                }

                for (UnitDTO unit : cmbUnit.getItems()) {
                    if (unit.getId().equals(selectedItem.getUnitId())) {
                        cmbUnit.getSelectionModel().select(unit);
                        break;
                    }
                }
            }
        }
    }

    private void loadDataFromBackend() {
        try {
            URL url = new URL("http://localhost:8080/api/v1/item");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                    Gson gson = new Gson();
                    ItemDTO[] itemArray = gson.fromJson(reader, ItemDTO[].class);
                    itemList.clear();
                    itemList.addAll(Arrays.asList(itemArray));
                }
            } else {
                System.out.println("Error fetching items: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields() {
        txtCode.clear();
        txtName.clear();
        cmbStatus.getSelectionModel().clearSelection();
        cmbCategory.getSelectionModel().clearSelection();
        cmbUnit.getSelectionModel().clearSelection();
    }

}
