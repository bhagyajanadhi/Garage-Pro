package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.db.Dbconnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.model.Customer;
import lk.ijse.util.ValidateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerManagementController {

    @FXML
    private Button UpdatePane;

    @FXML
    private Button clearPane;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Button dltPane;

    @FXML
    private Button savePane;

    @FXML
    private TableView<CustomerDto> tblCustomer;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtcustomer;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    private List<CustomerDto> customerList = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("customerContactInformation"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        getAllCustomer();

        Pattern patternId = Pattern.compile("^(C0)[0-9]{1,5}$");
        Pattern patternName = Pattern.compile("^[A-z]{3,}$");  //[0-9 a-z]{10}
        Pattern patternEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Pattern patterncontact = Pattern.compile("^(070 |071 | 072 | 076) [0-9] {7}$");

        map.put(txtcustomer, patternId);
        map.put(txtName, patternName);
        map.put(txtEmail, patternEmail);
        map.put(txtContact, patterncontact);

    }

    private void getAllCustomer() throws SQLException, ClassNotFoundException {
        customerList = Customer.getAll();
        tblCustomer.setItems(FXCollections.observableList(this.customerList));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        txtcustomer.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtaddress.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtcustomer.getText();
        try {
            boolean isDeleted = Customer.delete(customerId);
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

        String customerId = txtcustomer.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contactInformation = txtContact.getText();
        String address = txtaddress.getText();

        CustomerDto customerdto = new CustomerDto(customerId, name, contactInformation, address, email);


        boolean isSaved = false;
        try {
            isSaved = Customer.save(customerdto);
            if (isSaved) {
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

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customerId = txtcustomer.getText();
        String customerName = txtName.getText();
        String customerContactInformation = txtContact.getText();
        String customerAddress = txtaddress.getText();
        String customerEmail = txtEmail.getText();
        CustomerDto customerdto = new CustomerDto(customerId, customerName, customerContactInformation, customerAddress, customerEmail);

        try {
            boolean isUpdated = Customer.update(customerdto);
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


    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String  customerContactInformation = txtContact.getText();

        try {
            CustomerDto customerDto = Customer.searchById(customerContactInformation);

            if (customerDto != null) {
                txtcustomer.setText(customerDto.getCustomerId());
                txtName.setText(customerDto.getCustomerName());
                txtContact.setText(customerDto.getCustomerContactInformation());
                txtaddress.setText(customerDto.getCustomerAddress());
                txtEmail.setText(customerDto.getCustomerEmail());

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

    public void txtOnKeyRelesed(KeyEvent keyEvent) {
        ValidateUtil.validation(map);
    }

    public void ttxtOnKeyRelese(KeyEvent keyEvent) {
    }
}
