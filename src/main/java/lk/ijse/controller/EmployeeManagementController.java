package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;
import lk.ijse.util.ValidateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeManagementController {
    @FXML
    private Text txtTime;
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
    private TextField txtEmployeeContactInformation;

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

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();


    private List<EmployeeDto> employeeList = new ArrayList<>();
    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skill"));
       colContactInformation.setCellValueFactory(new PropertyValueFactory<>("contactInformation"));
        getAllEmployee();
        Pattern patternId = Pattern.compile("^(E0)[0-9]{1,5}$");
        Pattern patterncontact = Pattern.compile("^(070 |071 | 072 | 076) [0-9] {7}$");
        map.put(txtEmployeeId, patternId);
        map.put(txtEmployeeContactInformation, patterncontact);


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
        txtEmployeeContactInformation.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        try {
            boolean isDeleted = Employee.delete(employeeId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, " deleted!").show();
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
        String contactInformation = txtEmployeeContactInformation.getText();

        EmployeeDto employeedto = new EmployeeDto(employeeId,salary,name,skills,contactInformation);

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
        String contactInformation= txtEmployeeContactInformation.getText();

          EmployeeDto employeedto = new EmployeeDto(employeeId,salary,name,skills,contactInformation);
        boolean isUpdated = Employee.update(employeedto);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String  contactInformation = txtEmployeeContactInformation.getText();

        try {
            EmployeeDto employeeDto = Employee.searchById(contactInformation);

            if (employeeDto != null) {
                txtEmployeeId.setText(employeeDto.getEmployeeId());
                txtEmployeeSalary.setText(employeeDto.getEmployeeId());
                txtEmployeeName.setText(employeeDto.getName());
                txtEmployeeSkills.setText(employeeDto.getSkill());
                txtEmployeeContactInformation.setText(employeeDto.getContactInformation());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
        ValidateUtil.validation(map);
    }
}


