package com.example.demo;

import com.example.demo.api.BackendClient;
import com.example.demo.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SignupController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button signupButton;

    @FXML
    private Button backButton;

    @FXML
    private Label statusLabel;

    private final BackendClient backendClient = new BackendClient();

    @FXML
    private void handleSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            statusLabel.setText("Please enter a valid email address.");
            return;
        }

        try {
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setEmail(email);

            Customer createdCustomer = backendClient.createCustomer(newCustomer);

            if (createdCustomer == null) {
                statusLabel.setText("Failed to create customer. Please try again.");
                return;
            }else {

                // Clear the form
                nameField.clear();
                emailField.clear();
                statusLabel.setText("Customer created successfully!");

                showAlert("Registration Successful", "Welcome, " + name + "!");

                goToMainView();
            }
        } catch (Exception e) {
            String errorMessage = "Failed to create customer: " + e.getMessage();

            statusLabel.setText(errorMessage);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        goToMainView();
    }


    private void goToMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/main.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
