package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.dto.JobDto;
import lk.ijse.dto.JobInventoryDto;
import lk.ijse.dto.PaymentDto;
import lk.ijse.dto.VehicleDto;
import lk.ijse.model.*;
import lk.ijse.util.ValidateUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

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
    private TextField txtJobId;

    @FXML
    private TextField txtNetTotal;
    @FXML
    private TextField txtTotal;


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
    double fullTotal=0;
    private List<PaymentDto> paymentList = new ArrayList<>();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    public void initialize() throws SQLException, ClassNotFoundException {

        colJobId.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        getAllPayment();
        Pattern patternId = Pattern.compile("^(P0)[0-9]{1,5}$");

        map.put(txtPaymentId, patternId);


    }




    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtJobId.setText("");
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
        String jobId = txtJobId.getText();
        String paymentId = txtPaymentId.getText();
        Double amount = Double.valueOf(txtamount.getText());
        LocalDate date = dpDate.getValue();
        Double itemAmount= Double.valueOf(txtNetTotal.getText());
        double fullAmount = (amount + itemAmount);
        txtTotal.setText(String.valueOf(fullAmount));
        PaymentDto paymentDto = new PaymentDto(jobId,paymentId,amount,date,itemAmount);
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
        String jobId = txtJobId.getText();
        String paymentId = txtPaymentId.getText();
        Double amount = Double.valueOf(txtamount.getText());
        LocalDate date = dpDate.getValue();
        Double itemAmount =Double.valueOf(txtNetTotal.getText());
        PaymentDto paymentDto = new PaymentDto(jobId,paymentId,amount,date,itemAmount);

        boolean isUpdated = Payment.update(paymentDto);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
        }
    }

    @FXML
    void txtPayIdOnAction(MouseEvent event) {

    }

    @FXML
    void txtJobOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String jobId = txtJobId.getText();
        Job job = new Job();
        JobDto jobDto = job.searchById(jobId);
        System.out.println(job); // null
            txtJobId.setText(jobDto.getJobId());
            txtNetTotal.setText(String.valueOf(jobDto.getTotal()));



    }

    public void txtKeyOnRelease(KeyEvent keyEvent) {
        ValidateUtil.validation(map);
    }
}
