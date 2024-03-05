package lk.intelleon.springbootrestfulwebservices.javaFxController;

import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

@Component
public class InventoryController {

    @FXML
    private TextField txtReceived_qty;

    @FXML
    private ComboBox<?> cmbStatus;

    @FXML
    private TableView<?> tblInventory;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colItem;

    @FXML
    private TableColumn<?, ?> collDate;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colApproval;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private ComboBox<?> cmbItems;

    @FXML
    private ComboBox<?> cmbApproval;

    @FXML
    private DatePicker datePicker;

    @FXML
    void SaveOnAction(ActionEvent event) {
        .
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }


    public void handleRowClick(MouseEvent mouseEvent) {
    }
}
