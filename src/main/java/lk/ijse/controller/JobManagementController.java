package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.dto.InventoryDto;
import lk.ijse.dto.JobInventoryDto;
import lk.ijse.dto.VehicleDto;
import lk.ijse.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class JobManagementController implements Initializable {

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private ComboBox<String> cmbInventoryId;

    @FXML
    private ComboBox<String> cmbVehicleId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colInventory;

    @FXML
    private TableColumn<?, ?> colPartName;

    @FXML
    private TableColumn<?, ?> colStockLevel;

    @FXML
    private TableColumn<?, ?> colUnitePrice;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private TableColumn<?, ?> colTotal;


    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private TableView<JobInventoryDto> tblJob;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtInventoryId;

    @FXML
    private TextField txtJobId;

    @FXML
    private TextField txtLisenceplate;

    @FXML
    private TextField txtNetTotal;

    @FXML
    private TextField txtPartName;

    @FXML
    private TextField txtStockLevel;

    @FXML
    private TextField txtUnitePrice;


    @FXML
    private TextField txtQty;

    private ObservableList<JobInventoryDto> observableList = FXCollections.observableArrayList();
    private double fullTotal=0;
    @FXML
    void btnOnSaveAction(ActionEvent event) {
        String inventoryId = cmbInventoryId.getSelectionModel().getSelectedItem().toString();
        String description = txtDescription.getText();
        String partName = txtPartName.getText();
        int stockLevel = Integer.parseInt(txtStockLevel.getText());
        double unitePrice = Double.parseDouble(txtUnitePrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double totalPrice = unitePrice * qty ;
        fullTotal =fullTotal+(unitePrice * qty);

        JobInventoryDto jobDto = new JobInventoryDto(inventoryId,description,partName,stockLevel,unitePrice,qty,totalPrice);
        observableList.add(jobDto);
        tblJob.setItems(observableList);
        txtNetTotal.setText(String.valueOf(fullTotal));

    }
    @FXML
    void btnPlaceOrder(ActionEvent event) throws SQLException {
        String JobId = txtJobId.getText();
        LocalDate date = LocalDate.parse(String.valueOf(dpDate.getValue()));
        String vehicleId = cmbVehicleId.getSelectionModel().getSelectedItem().toString();
        String description = txtDescription.getText();
        String employeeId = cmbEmployeeId.getSelectionModel().getSelectedItem().toString();


        Job job = new Job();
        //String jobId, String employeeId, String description, LocalDate date, String vehicleId, ObservableList<JobInventoryDto> observableList, double fullTotal
        boolean b = job.saveJob(JobId, employeeId,description,date,vehicleId,observableList,fullTotal);
        if (b){
            new Alert(Alert.AlertType.CONFIRMATION,"save List..!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong..!").show();
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colInventory.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        colStockLevel.setCellValueFactory(new PropertyValueFactory<>("stockLevel"));
        colUnitePrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        getEmployeeIds();

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
    @FXML
    void txtLicenseOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String Lisenceplate = txtLisenceplate.getText();
        Vehicle vehicle = new Vehicle();
        VehicleDto vehicleDto = vehicle.searchById(Lisenceplate);
       if(vehicleDto!=null){
           System.out.println(vehicle); // null
           txtLisenceplate.setText(vehicleDto.getVehicleLicensePlate());
           cmbVehicleId.setValue(vehicleDto.getVehicleId());
       }


    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String partName = txtPartName.getText();
        Inventory inventory = new Inventory();
        InventoryDto inventoryDto = inventory.searchById(partName);
        txtStockLevel.setText(String.valueOf(inventoryDto.getQty()));
       cmbInventoryId.setValue(inventoryDto.getInventoryId());
       txtUnitePrice.setText(String.valueOf(inventoryDto.getUnitePrice()));
       txtDescription.setText(inventoryDto.getDescription());

    }

    @FXML
    void txtOnKeyRelease(KeyEvent event) {

    }

    @FXML
    void txtPayIdOnAction(MouseEvent event) {

    }




}
