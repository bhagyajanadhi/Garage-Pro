package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.dto.LoanDto;
import lk.ijse.model.Loan;
import lk.ijse.util.ValidateUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanManagementController {

    @FXML
    private Button clearpane;

    @FXML
    private ComboBox<?> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDueDates;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableColumn<?, ?> colPaymentStatus;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Button dtlpane;

    @FXML
    private Button savpane;

    @FXML
    private TableView<LoanDto> tblLoan;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtLoanId;

    @FXML
    private TextField txtPaymentStatus;

    @FXML
    private Button updatepane;

    private List<LoanDto> loanList = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("LoanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDueDates.setCellValueFactory(new PropertyValueFactory<>("duedate"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentstatus"));
        getAllLoan();

    }

    private void getAllLoan() throws SQLException, ClassNotFoundException {
        loanList =Loan.getAll();
        tblLoan.setItems(FXCollections.observableList(this.loanList));
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtLoanId.setText("");
        txtAmount.setText("");
        cmbSupplierId.setDisable(true);
        dpDate.setValue(null);
        txtPaymentStatus.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String LoanId = txtLoanId.getText();
        try {
            boolean isDeleted = Loan.delete(LoanId);
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

        String LoanId = txtLoanId.getText();
        String amount = txtAmount.getText();
        String supplierId = (String) cmbSupplierId.getValue();
        LocalDate duedate = LocalDate.parse(String.valueOf(dpDate.getValue()));
        String paymentstatus = txtPaymentStatus.getText();

        LoanDto loanDto = new LoanDto(LoanId, amount,supplierId,duedate , paymentstatus);


        boolean isSaved = false;
        try {
            isSaved = Loan.save(loanDto);
            if (isSaved){
                new Alert(Alert.AlertType. CONFIRMATION,"Succsessful").show();
                getAllLoan();
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
    void btnUpdateOnAction(ActionEvent event) {
        String LoanId = txtLoanId.getText();
        String amount = txtAmount.getText();
        String supplierId = (String) cmbSupplierId.getValue();
        LocalDate duedate = LocalDate.parse(String.valueOf(dpDate.getValue()));
        String paymentstatus = txtPaymentStatus.getText();

        LoanDto loanDto = new LoanDto(LoanId, amount,supplierId,duedate , paymentstatus);

        boolean isUpdated = false;
        try {
            isUpdated = Loan.update(loanDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Succsessful").show();
                getAllLoan();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtPayIdOnAction(MouseEvent event) {

    }

    public void txtOnAction(KeyEvent keyEvent) {
        ValidateUtil.validation(map);
    }
}
