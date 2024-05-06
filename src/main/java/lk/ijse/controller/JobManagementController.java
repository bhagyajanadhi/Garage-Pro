package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.JobDto;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;
import lk.ijse.model.Job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobManagementController {

    @FXML
    private Button Updatepane;

    @FXML
    private Button clearpane;
    @FXML
    private ComboBox<?> cmbEmployeeId;

    @FXML
    private ComboBox<?> cmbVehicleId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colJobId;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Button dtlpane;

    @FXML
    private TableView<?> tblJob;

    @FXML
    private TextField txtDescriotion;

    @FXML
    private TextField txtJobId;

    private List<JobDto> jobList = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        colJobId.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        getAllJob();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtJobId.setText("");
        cmbEmployeeId.setDisable(true);
        txtDescriotion.setText("");
        dpDate.setDisable(true);
        cmbVehicleId.setDisable(true);

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String jobId = txtJobId.getId();
        boolean isDeleted = Job.delete(jobId);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, " deleted!").show();
        }

    }

    public void btnOnUpdateAction(ActionEvent actionEvent) {
    }

    public void btnOnSaveAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String jobId = txtJobId.getText();
        String employeeId = (String) cmbEmployeeId.getValue();
        String description = txtDescriotion.getText();
        String date = String.valueOf(dpDate.getValue());
        String vehicleId = (String) cmbVehicleId.getValue();
        JobDto jobDto = new JobDto(jobId, employeeId, description, date, vehicleId);
        boolean isSaved = false;
        isSaved = Job.save(jobDto);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Succsessful").show();
            getAllJob();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error").show();
        }
    }

    private void getAllJob() {
        jobList = Job.getAll();
        tblJob.setItems(FXCollections.observableList(this.jobList));
    }


    public void txtPayIdOnAction(MouseEvent mouseEvent) {

    }
}