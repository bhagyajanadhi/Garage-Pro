package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.model.Admin;
import lk.ijse.navigation.Navigation;
import java.io.IOException;


public class LoginController {
    public Text txtForgetPassword;
    public TextField txtUserName;
    public AnchorPane mainPane;
    public PasswordField passwordField;


    public void btnSignInOnAction(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"))));
            stage.show();
            Stage window = (Stage) mainPane.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnLogInOnAction(ActionEvent event) {

        String password = passwordField.getText();
        String userName = txtUserName.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter both username and password.").show();
            return;
        }


            Admin adminModel = new Admin();
            boolean value = adminModel.adminCheck(userName, password);
            if (value==true){
                Navigation navigation = new Navigation();
                navigation.popUpNavigation("DashBoardDetailsForm.fxml");
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Wrong Details..!").show();
            }

        }
    }

