package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.dto.AdminDto;
import lk.ijse.model.Admin;

import java.io.IOException;

public class LoginController {


    @FXML
    private Text txtForgetPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtpassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String password = txtpassword.getText();

        if (!userName.isEmpty() && !password.isEmpty()) {
            AdminDto admin = AdminDto.getAdmin(userName);

            if (admin != null) {
                if (password.equals(admin.getPassword())) {
                    // Login successful, proceed to next step
                } else {
                    new Alert(Alert.AlertType.ERROR, "Wrong Password").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "User not found").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Fields").show();
        }
    }

    @FXML
    void signupOnAction(ActionEvent event) {
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));
            Scene scene1 = new Scene(load);
            Stage stage1 =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("SignUp Form");
            stage1.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtForgetPasswordOnAction(MouseEvent event) {
        // Implementation for forget password action
    }
}
