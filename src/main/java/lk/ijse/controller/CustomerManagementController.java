package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.dto.customerDto;
import lk.ijse.model.Customer;

import java.sql.SQLException;

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
    private TableView<?> tblCustomer;

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

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String customerId = txtcustomer.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contactInformation =txtContact.getText();
        String address = txtaddress.getText();

        Customer customer =new Customer();
        int i =customer.save(new customerDto(customerId,name,contactInformation,address,email));



    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
