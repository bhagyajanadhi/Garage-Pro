package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dto.PaymentDto;
import lk.ijse.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentManagementController {

    @FXML
    private Button clearPane;

    @FXML
    private ComboBox<String> cmdJobId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colJobId;

    @FXML
    private TableColumn<?, ?> colPaymentId;
    @FXML
    private DatePicker dpDate;

    @FXML
    private Button dtlPane;

    @FXML
    private Button savePane;

    @FXML
    private TableView<PaymentDto> tblPayment;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtamount;

    @FXML
    private Button updatePane;
    private List<PaymentDto> paymentList = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {

        colJobId.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        getAllPayment();
        getJobIds();

    }

    private void getJobIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = Job.getIds();

            for (String jobId : idList) {
                obList.add(jobId);
            }
            cmdJobId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmdJobId.getSelectionModel().clearSelection();
        txtPaymentId.setText("");
        txtamount.setText("");
        dpDate.setValue(null);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        try {
            boolean isDeleted = Payment.delete(paymentId);
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
        String jobId = (String) cmdJobId.getValue();
        String paymentId = txtPaymentId.getText();
        String amount = txtamount.getText();
        LocalDate date = dpDate.getValue();
        PaymentDto paymentDto = new PaymentDto(jobId,paymentId,amount,date);

        boolean isSaved = false;
        try {
            isSaved = Payment.save(paymentDto);
            if (isSaved){
                new Alert(Alert.AlertType. CONFIRMATION,"Succsessful").show();
                getAllPayment();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void getAllPayment() throws SQLException, ClassNotFoundException {
        paymentList = Payment.getAll();
        tblPayment.setItems(FXCollections.observableList(this.paymentList));

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String jobId = (String) cmdJobId.getValue();
        String paymentId = txtPaymentId.getText();
        String amount = txtamount.getText();
        LocalDate date = dpDate.getValue();
        PaymentDto paymentDto = new PaymentDto(jobId,paymentId,amount,date);

        boolean isUpdated = Payment.update(paymentDto);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
        }
    }

    @FXML
    void txtPayIdOnAction(MouseEvent event) {

    }

}
