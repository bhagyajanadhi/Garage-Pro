package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.InventoryDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;
import lk.ijse.model.Inventory;
import lk.ijse.model.Supplier;

import java.sql.SQLException;

public class SupplierController {

    @FXML
    private Button clearpane;

    @FXML
    private Button dtlpane;

    @FXML
    private Button savpane;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPaymentTerms;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private Button updatepane;

    public void initialize() throws SQLException {
        getAllSuppliers();
    }

    private void getAllSuppliers() {
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSupplierId.setText("");
        txtName.setText("");
        txtPaymentTerms.setText("");
        txtContact.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String supplierID = txtSupplierId.getText();
        try {
            boolean isDeleted = Supplier.delete(supplierID);
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
    void btnSaverOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String name = txtName.getText();
        String paymentTerm = txtPaymentTerms.getText();
        String contactInformation = txtContact.getText();

        SupplierDto supplierDto = new SupplierDto(supplierId, name, paymentTerm,contactInformation);


        boolean isSaved = false;
        try {
            isSaved = Supplier.save(supplierDto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Succsessful").show();
                getAllSuppliers();
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

        String supplierId = txtSupplierId.getText();
        String name = txtName.getText();
        String contactInformation = txtContact.getText();
        String paymentTerms = txtPaymentTerms.getText();

        SupplierDto supplierDto = new SupplierDto(supplierId,name,contactInformation,paymentTerms );

        try {
            boolean isUpdated = Supplier.update(supplierDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Succsessful").show();
                getAllSuppliers();
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
        String  name = txtContact.getText();

        try {
            SupplierDto supplierDto = Supplier.searchById(name);

            if (supplierDto != null) {
                txtSupplierId.setText(supplierDto.getSupplierId());
                txtName.setText(supplierDto.getName());
                txtContact.setText(supplierDto.getContactInformaton());
                txtPaymentTerms.setText(supplierDto.getPaymentTerm());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}

