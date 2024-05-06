package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardDetailsFormController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUi("DashBoardPaneForm");
    }

    public Pane mainPain;

    public void btnManageDashBoarOnAction(ActionEvent actionEvent) {
    setUi("DashBoardPaneForm");
    }

    public void btnManageCustomerOnAction(ActionEvent actionEvent) {
   setUi("CustomerManagement");
    }

    public void btnManagePaymentOnAction(ActionEvent actionEvent) {setUi("PaymentManagement");}

    public void dtnManageVehicleOnAction(ActionEvent actionEvent) {setUi("VehicleManagement");}

    public void btnManageJobOnAction(ActionEvent actionEvent) {setUi("JobManagementView");}

    public void btnManageEmployeeOnAction(ActionEvent actionEvent) { setUi("EmployeeManagement");}

    public void btnManageSupplierOnAction(ActionEvent actionEvent) {setUi("SupplierManagement");}

    public void btnManageLoanOnAction(ActionEvent actionEvent) {setUi("LoanManagement");}

    void setUi(String url){
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/"+url+".fxml"));
            mainPain.getChildren().setAll(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
