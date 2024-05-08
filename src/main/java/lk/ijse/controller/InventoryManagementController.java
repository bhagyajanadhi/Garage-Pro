package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.InventoryDto;
import lk.ijse.model.Customer;
import lk.ijse.model.Inventory;

import java.sql.SQLException;

public class InventoryManagementController {

    @FXML
    private ComboBox<?> cmbSupplierId;
    @FXML
    private Button clearpane;

    @FXML
    private Button dltpane1;

    @FXML
    private Button savpane;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtInventoryId;

    @FXML
    private TextField txtPartName;

    @FXML
    private TextField txtStockLevel;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtUnitePrice;

    @FXML
    private Button updatepane;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtInventoryId.setText("");
        txtDescription.setText("");
        cmbSupplierId.setDisable(true);
        txtPartName.setText("");
        txtStockLevel.setText("");
        txtUnitePrice.setText("");


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String inventoryId = txtInventoryId.getText();
        try {
            boolean isDeleted = Inventory.delete(inventoryId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String inventoryId = txtInventoryId.getText();
        String description = txtDescription.getText();
        String supplierId = (String) cmbSupplierId.getValue();
        String partName = txtPartName.getText();
        int stockLevel = Integer.parseInt(txtStockLevel.getText());
        double unitePrice = Double.parseDouble(txtUnitePrice.getText());

        InventoryDto inventoryDto = new InventoryDto(inventoryId, description, supplierId, partName, stockLevel,unitePrice);


        boolean isSaved = false;
        try {
            isSaved = Inventory.save(inventoryDto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Succsessful").show();
                getAllInventory();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customerId = txtcustomer.getText();
        String customerName = txtName.getText();
        String customerContactInformation = txtContact.getText();
        String customerAddress = txtaddress.getText();
        String customerEmail = txtEmail.getText();
        CustomerDto customerdto = new CustomerDto(customerId, customerName, customerContactInformation, customerAddress, customerEmail);
        boolean isUpdated = false;
        try {
            isUpdated = Customer.update(customerdto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Succsessful").show();
                getAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }
}
