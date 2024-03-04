package lk.intelleon.springbootrestfulwebservices.javaFxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DashboardController {
    @FXML
    public AnchorPane side_anchorpane;
    @FXML
    public AnchorPane root;
    @FXML
    public AnchorPane context;
    @FXML
    public Button Suppliers;
    @FXML
    public Button Unit;
    @FXML
    public Button Category;

    @FXML
    public void btnSuppliersOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the supplier form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/suppliers.fxml"));
            Parent supplierForm = loader.load();

            // Access the controller for the supplier form
            SuppliersController suppliersController = loader.getController();

            // Set the supplier form as the content of the context AnchorPane
            context.getChildren().setAll(supplierForm);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnUnitOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the unit form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/unit.fxml"));
            Parent supplierForm = loader.load();

            // Access the controller for the unit form
            UnitController unitController = loader.getController();

            // Set the unit form as the content of the context AnchorPane
            context.getChildren().setAll(supplierForm);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnCategoryOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the category form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/category.fxml"));
            Parent supplierForm = loader.load();

            // Access the controller for the category form
            CategoryController categoryController = loader.getController();

            // Set the category form as the content of the context AnchorPane
            context.getChildren().setAll(supplierForm);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnItemOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the item form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/item.fxml"));
            Parent supplierForm = loader.load();

            // Access the controller for the item form
            ItemController itemController = loader.getController();

            // Set the item form as the content of the context AnchorPane
            context.getChildren().setAll(supplierForm);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
