package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.model.Employee;

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
    private TableView<?> tblEmployee;

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

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String employeeId = txtEmployeeId.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());
        String name = txtEmployeeName.getText();
        String skill = txtEmployeeSkills.getText();
        String contactInfromation = txtEmployeeContactInfromation.getText();

        Employee employee = new Employee();
        int i = employee.saveEmpolyee(new EmployeeDto(employeeId,salary,name,skill,contactInfromation));
        if (i > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "save Customer..!").show();

        } else {
            new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
        }

        }

    }


