package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.util.DateTimeUtil;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBoardDetailsFormController implements Initializable {

    @FXML
    private Text lblDate;

    @FXML
    private Text lblTime;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       
        TimeNow();

        setUi("DashBoardPaneForm");
    }

    private void TimeNow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM, dd, yyyy");
            while (true) {
                try {
                    Thread.sleep(1000);

                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timeNow = sdf.format(new Date());
                String timeNow1 = sdf1.format(new Date());

                Platform.runLater(() -> {
                    lblTime.setText(timeNow);
                    lblDate.setText(timeNow1);
                });
            }
        });
        thread.start();

    }


    public Pane mainPain;

    public void btnManageDashBoarOnAction(ActionEvent actionEvent) {
        setUi("DashBoardPaneForm");
    }

    public void btnManageBookingOnAction(ActionEvent actionEvent) {
        setUi("BookingManagementController");
    }

    public void btnManageCustomerOnAction(ActionEvent actionEvent) {
        setUi("CustomerManagement");
    }

    public void btnManagePaymentOnAction(ActionEvent actionEvent) {
        setUi("PaymentManagement");
    }

    public void dtnManageVehicleOnAction(ActionEvent actionEvent) {
        setUi("VehicleManagement");
    }

    public void btnManageJobOnAction(ActionEvent actionEvent) {
        setUi("JobManagementView");
    }

    public void btnManageEmployeeOnAction(ActionEvent actionEvent) {
        setUi("EmployeeManagement");
    }

    public void btnManageSupplierOnAction(ActionEvent actionEvent) {
        setUi("SupplierManagement");
    }

    public void btnManageLoanOnAction(ActionEvent actionEvent) {
        setUi("LoanManagement");
    }
    public void btnInventoryOnAction(ActionEvent actionEvent) {
        setUi("InventoryManagement");
    }

    void setUi(String url){
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/"+url+".fxml"));
            mainPain.getChildren().setAll(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}



}
