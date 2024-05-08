package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementController {

    @FXML
    private Button clearpane;

    @FXML
    private ComboBox<?> cmbJobId;

    @FXML
    private TableColumn<?, ?> colContactInformation;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJobId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private TableColumn<?, ?> colSkills;

    @FXML
    private Button dtlpane;

    @FXML
    private Button savpane;

    @FXML
    private TableView<EmployeeDto> tblEmployee;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeContactInfromation;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeSalary;

    @FXML
    private TextField txtEmployeeSkills;

    @FXML
    private Button updatepane;

    private List<EmployeeDto> employeeList = new ArrayList<>();
    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skills"));
       colContactInformation.setCellValueFactory(new PropertyValueFactory<>("contactinformation"));
        getAllEmployee();


    }
    private void getAllEmployee() throws SQLException, ClassNotFoundException {
        employeeList =Employee.getAll();
       tblEmployee.setItems(FXCollections.observableList(this.employeeList));

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtEmployeeId.setText("");
        txtEmployeeSalary.setText("");
        txtEmployeeName.setText("");
        txtEmployeeSkills.setText("");
        txtEmployeeContactInfromation.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        try {
            boolean isDeleted = Employee.delete(employeeId);
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

        String employeeId = txtEmployeeId.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());
        String name = txtEmployeeName.getText();
        String skills = txtEmployeeSkills.getText();
        String contactInfromation = txtEmployeeContactInfromation.getText();

        EmployeeDto employeedto = new EmployeeDto(employeeId,salary,name,skills,contactInfromation);

        boolean isSaved = false;
        try {
            isSaved = Employee.save(employeedto);
            if (isSaved){
                new Alert(Alert.AlertType. CONFIRMATION,"Succsessful").show();
                getAllEmployee();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        String employeeId = txtEmployeeId.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());
        String name = txtEmployeeName.getText();
        String skills = txtEmployeeSkills.getText();
        String contactInformation= txtEmployeeContactInfromation.getText();

          EmployeeDto employeedto = new EmployeeDto(employeeId,salary,name,skills,contactInformation);
        boolean isUpdated = Employee.update(employeedto);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
        }
    }

}


