package lk.intelleon.springbootrestfulwebservices.javaFxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_anchorpane;

    @FXML
    private Button btn_suppliers;

    @FXML
    private Button btn_unit;

    @FXML
    private Button btn_category;

    @FXML
    private AnchorPane context;

    @FXML
    void categoryOnAction(ActionEvent event) {

    }

    @FXML
    void suppliersOnAction(ActionEvent event) {

    }

    @FXML
    void unitOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
