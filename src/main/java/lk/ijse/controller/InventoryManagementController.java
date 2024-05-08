package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.dto.InventoryDto;
import lk.ijse.model.Inventory;
import lk.ijse.model.Supplier;

import java.sql.SQLException;
import java.util.List;

public class InventoryManagementController {

    @FXML
    private ComboBox<String> cmbSupplierId;
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

    public void initialize() throws SQLException {
        getAllInventory();
        getAllSupplier();
    }

    private void getAllSupplier() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = Supplier.getIds();

            for (String supplierId : idList) {
                obList.add(supplierId);
            }
            cmbSupplierId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void getAllInventory() {

    }

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
        String supplierId = cmbSupplierId.getValue();
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
        String inventoryId = txtInventoryId.getText();
        String description = txtDescription.getText();
        String supplierId = cmbSupplierId.getValue();
        String partName = txtPartName.getText();
        int stockLevel = Integer.parseInt(txtStockLevel.getText());
        double unitePrice = Double.parseDouble(txtUnitePrice.getText());
        InventoryDto inventoryDto = new InventoryDto(inventoryId, description, supplierId, partName, stockLevel,unitePrice);
        boolean isUpdated = false;
        try {
            isUpdated = Inventory.update(inventoryDto);
            if (isUpdated) {
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

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String partName = txtPartName.getText();


        try {
            InventoryDto inventoryDto = Inventory.searchById(partName);

            if (inventoryDto != null) {
                txtInventoryId.setText(inventoryDto.getInventoryId());
                txtDescription.setText(inventoryDto.getDescription());
                cmbSupplierId.setValue(inventoryDto.getSupplierId());
                txtPartName.setText(inventoryDto.getPartName());
                txtStockLevel.setText(String.valueOf(inventoryDto.getStockLevel()));
                txtUnitePrice.setText(String.valueOf(inventoryDto.getUnitePrice()));
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
