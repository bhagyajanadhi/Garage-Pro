package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dto.JobDto;
import lk.ijse.model.Employee;
import lk.ijse.model.Job;
import lk.ijse.model.Vehicle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobManagementController {

    @FXML
    private Button Updatepane;

    @FXML
    private Button clearpane;
    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private ComboBox<String> cmbVehicleId;

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
    private TableView<JobDto> tblJob;

    @FXML
    private TextField txtDescriotion;

    @FXML
    private TextField txtJobId;
    private List<JobDto> jobList = new ArrayList<JobDto>();


    public void initialize() throws SQLException, ClassNotFoundException {
        colJobId.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        getAllJob();
        getEmployeeIds();
        getVehicleIds();
    }

    private void getVehicleIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = Vehicle.getIds();

            for (String vehicleId : idList) {
                obList.add(vehicleId);
            }
            cmbVehicleId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = Employee.getIds();

            for (String employeeId : idList) {
                obList.add(employeeId);
            }
            cmbEmployeeId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllJob() throws SQLException, ClassNotFoundException {
        jobList = Job.getAll();
        tblJob.setItems(FXCollections.observableList(this.jobList));
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtJobId.setText("");
        cmbEmployeeId.setDisable(true);
        txtDescriotion.setText("");
        dpDate.setValue(null);
        cmbVehicleId.setDisable(true);

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String jobId = txtJobId.getText();
        try {
            boolean isDeleted =Job.delete(jobId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnOnUpdateAction(ActionEvent actionEvent) {
        String jobId = txtJobId.getText();
        String employeeId = (String) cmbEmployeeId.getValue();
        String description = txtDescriotion.getText();
        LocalDate date = dpDate.getValue();
        String vehicleId = (String) cmbVehicleId.getValue();
        JobDto jobDto = new JobDto(jobId, employeeId, description, date, vehicleId);
        boolean isUpdated = false;
        try {
            isUpdated = Job.update(jobDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Succsessful").show();
                getAllJob();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
        public void btnOnSaveAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String jobId = txtJobId.getText();
        String employeeId = (String) cmbEmployeeId.getValue();
        String description = txtDescriotion.getText();
        LocalDate date = LocalDate.parse(String.valueOf(dpDate.getValue()));
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



    public void txtPayIdOnAction(MouseEvent mouseEvent) {

    }
}