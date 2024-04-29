package lk.ijse.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.model.Admin;
import lk.ijse.navigation.Navigation;
import lk.ijse.repository.AdminRepo;

public class LoginController {






    @FXML
    private Text txtForgetPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    AdminRepo adminRepo = new AdminRepo();






    public void SingInOnAction(ActionEvent actionEvent) {

    }

    public void btnSingInOnAction(MouseEvent mouseEvent) {
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        String password = txtPassword.getText();
        String userName = txtUserName.getText();

        Admin admin = new Admin();
        boolean adminCheck = admin.adminCheck(userName, password);
        if (adminCheck == true) {
            Navigation navigation = new Navigation();
            navigation.popUpNavigation("DashBoardDetailsForm.fxml");
        } else {
            new Alert(Alert.AlertType.ERROR, "Wrong Details..!").show();
        }
    }
}

