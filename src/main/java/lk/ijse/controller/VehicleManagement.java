package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.dto.VehicleDto;
import lk.ijse.model.Customer;
import lk.ijse.model.Vehicle;
import lk.ijse.util.ValidateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class VehicleManagement {

    @FXML
    private Button clearpane;


    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colLicencePlate;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private Button dtlpane;

    @FXML
    private Button savepane;

    @FXML
    private TableView<VehicleDto> tblVehicle;

    @FXML
    private TextField txtPlate;
    @FXML
    private TextField txtmodel;



    @FXML
    private TextField txtVehicleId;


    @FXML
    private Button updatepane;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    private List<VehicleDto> vehicleList = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        colLicencePlate.setCellValueFactory(new PropertyValueFactory<>("vehicleLicensePlate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cus_id"));
        getAllVehicle();
        getCustomerIds();

        Pattern patternId = Pattern.compile("^(V0)[0-9]{1,5}$");
        Pattern patternLisence = Pattern.compile("^(V1)[0-9]{1,5}$");

        map.put(txtVehicleId, patternId);
        map.put(txtPlate, patternLisence);

    }

    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = Customer.getIds();

            for (String customerId : idList) {
                obList.add(customerId);
            }
            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtVehicleId.setText("");
        txtmodel.setText("");
        txtPlate.setText("");
        cmbCustomerId.setDisable(true);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String vehicleId= txtVehicleId.getText();
        try {
            boolean isDeleted = Vehicle.delete(vehicleId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String vehicleId = txtVehicleId.getText();
        String vehicleModel = txtmodel.getText();
        String vehicleLicense = txtPlate.getText();
        String cus_id = (String) cmbCustomerId.getValue();
        VehicleDto vehicleDto = new VehicleDto(vehicleId,vehicleModel,vehicleLicense,cus_id);

        boolean isSaved = false;
        try {
            isSaved = Vehicle.save(vehicleDto);
            if (isSaved){
                new Alert(Alert.AlertType. CONFIRMATION,"Succsessful").show();
                getAllVehicle();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void getAllVehicle() throws SQLException, ClassNotFoundException {
        vehicleList = Vehicle.getAll();
        tblVehicle.setItems(FXCollections.observableList(this.vehicleList));

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String vehicleId = txtVehicleId.getText();
        String vehicleModel = txtmodel.getText();
        String vehicleLicensePlate = txtPlate.getText();
        String cus_id = (String) cmbCustomerId.getValue();
        VehicleDto vehicleDto = new VehicleDto(vehicleId,vehicleModel,vehicleLicensePlate,cus_id);

        boolean isUpdated = false;
        try {
            isUpdated = Vehicle.update(vehicleDto);
            if (isUpdated){
                new Alert(Alert.AlertType. CONFIRMATION,"Succsessful").show();
                getAllVehicle();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String plate = txtPlate.getText();

        try {
            VehicleDto vehicleDto = Vehicle.searchById(plate);
            if(vehicleDto!=null){
                txtVehicleId.setText(vehicleDto.getVehicleId());
                txtmodel.setText(vehicleDto.getVehicleModel());
                txtPlate.setText(vehicleDto.getVehicleLicensePlate());
                cmbCustomerId.setValue(vehicleDto.getCus_id());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtOnKeyRelease(KeyEvent keyEvent) {

        ValidateUtil.validation(map);
            }


}
