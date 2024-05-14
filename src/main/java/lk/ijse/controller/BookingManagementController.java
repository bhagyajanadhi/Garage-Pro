package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.dto.BookingDto;
import lk.ijse.model.Booking;
import lk.ijse.model.Customer;
import lk.ijse.model.Vehicle;
import lk.ijse.util.ValidateUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class  BookingManagementController {


    @FXML
    private Button clearpane;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbVehicleId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colBooking;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Button dtlpane;

    @FXML
    private Button savpane;

    @FXML
    private TableView<BookingDto> tblBooking;

    @FXML
    private TextField txtBooking;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();


    private List<BookingDto> bookingList = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        colBooking.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        getAllBooking();
        getCustomerIds();
        getVehicleIds();

        Pattern patternId = Pattern.compile("^(B0)[0-9]{1,5}$");

        map.put(txtBooking, patternId);
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

    private void getAllBooking() throws SQLException, ClassNotFoundException {
        bookingList = Booking.getAll();
        tblBooking.setItems(FXCollections.observableList(this.bookingList));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtBooking.setText("");
        cmbCustomerId.setValue(null);
        cmbVehicleId.setValue(null);
        dpDate.setValue(null);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookigId = txtBooking.getText();
        try {
            boolean isDeleted = Booking.delete(bookigId);
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
    void btnSaveOnAction() {
        String bookingId = txtBooking.getText();
        String customerId = (String) cmbCustomerId.getValue();
        String vehicleId = (String) cmbVehicleId.getValue();
        LocalDate date = LocalDate.parse(String.valueOf(dpDate.getValue()));




        BookingDto bookingDto = new BookingDto(bookingId,customerId,vehicleId,date);

        boolean isSaved = false;
        try {
            isSaved = Booking.save(bookingDto);
            if (isSaved){
                new Alert(Alert.AlertType. CONFIRMATION,"Succsessful").show();
                getAllBooking();
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
        String bookingId = txtBooking.getText();
        String customerId = (String) cmbCustomerId.getValue();
        String vehicleId = (String) cmbVehicleId.getValue();
        LocalDate date = dpDate.getValue();
        BookingDto bookingDto = new BookingDto(bookingId,customerId,vehicleId,date);
        boolean isUpdated = Booking.update(bookingDto);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
        }
    }

    public void txtPayIdOnAction(MouseEvent mouseEvent) {
    }

    public void txtOnKeyRelease(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object respond =  ValidateUtil.validation(map);
            if (respond instanceof TextField) {
                TextField textField = (TextField) respond;
                textField.requestFocus();
            } else {
                btnSaveOnAction();
       }}
        ValidateUtil.validation(map);
        ValidateUtil.validation(map);
        ValidateUtil.validation(map);
        ValidateUtil.validation(map);
        ValidateUtil.validation(map);
        ValidateUtil.validation(map);
        ValidateUtil.validation(map);
        ValidateUtil.validation(map);
    }
}
