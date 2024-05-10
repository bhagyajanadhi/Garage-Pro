package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.InventoryDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;
import lk.ijse.model.Inventory;
import lk.ijse.model.Supplier;
import lk.ijse.util.ValidateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierManagementController {

    @FXML
    private Button clearpane;
    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPaymentTerms;

    @FXML
    private TableColumn<?, ?> colSupplier;
    @FXML
    private TableView<SupplierDto> tblSupplier;


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
    private List<SupplierDto> supplierList = new ArrayList<>();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();


    public void initialize() throws SQLException, ClassNotFoundException {

        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPaymentTerms.setCellValueFactory(new PropertyValueFactory<>("paymentTerm"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact no"));
        getAllSuppliers();

        Pattern patternId = Pattern.compile("^(S0)[0-9]{1,5}$");
        Pattern patterncontact = Pattern.compile("^(070 |071 | 072 | 076) [0-9] {7}$");

        map.put(txtSupplierId, patternId);
        map.put(txtContact, patterncontact);
    }

    private void getAllSuppliers() throws SQLException, ClassNotFoundException {
        tblSupplier.setItems(FXCollections.observableList(this.supplierList));
        supplierList = Supplier.getAll();

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
                txtContact.setText(supplierDto.getContactInformation());
                txtPaymentTerms.setText(supplierDto.getPaymentTerm());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtKeyOnRelease(KeyEvent keyEvent) {
        ValidateUtil.validation(map);
    }
}

