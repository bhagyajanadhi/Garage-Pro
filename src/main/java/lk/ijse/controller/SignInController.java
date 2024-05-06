package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.model.Admin;

import java.io.IOException;

public class SignInController {
    public Pane mainPain;
    public TextField txtUserName;
    public TextField txtEmail;
    public AnchorPane mainPane;
    public Button btnsign;
    public PasswordField passwordField;
    public PasswordField passwordField1;

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        String username = txtUserName.getText();
        String email = txtEmail.getText();
        String password = passwordField1.getText();
        String confirmPassword = passwordField.getText();

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!");
            return;
        }

        Admin adminModel = new Admin();
        // Check for duplicate username
        if (adminModel.checkDuplicateUsername(username)) {
            showAlert("Error", "Username already exists!");
            return;
        }

        boolean success = adminModel.signUp(username, email, password);
        if (success) {
            showAlert("Success", "Registration successful!");

            clearFields();
        } else {
            showAlert("Error", "Registration failed!");
        }
    }

    public void logInOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginform.fxml"))));
            stage.show();
            Stage window = (Stage) mainPane.getScene().getWindow();
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtUserName.clear();
        txtEmail.clear();
        passwordField1.clear();
        passwordField.clear();
    }
}
