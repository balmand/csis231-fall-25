package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private StackPane contentPane;

    @FXML
    private VBox sidebar;

    @FXML
    private Button customersButton;

    @FXML
    private Button booksButton;

    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load default view
        showCustomers();

        // set accessible ids (optional) for styling/automation
        customersButton.setId("nav-customers");
        booksButton.setId("nav-books");
        logoutButton.setId("nav-logout");

        // attach handlers (FXML onAction also works)
        customersButton.setOnAction(e -> showCustomers());
        booksButton.setOnAction(e -> showBooks());
        logoutButton.setOnAction(e -> handleLogout());
    }

    // Load Customer view
    @FXML
    private void showCustomers() {
        loadView("/com/example/demo/fxml/hello-view.fxml");
        setActiveNav(customersButton);
    }

    // Load Book view
    @FXML
    private void showBooks() {
        loadView("/com/example/demo/fxml/hello-book.fxml");
        setActiveNav(booksButton);
    }

    // Logout - simple example (replace with real logout logic)
    @FXML
    private void handleLogout() {
        // TODO: add proper logout (token clear, scene switch, confirmation, etc.)
        System.out.println("Logout clicked — implement real logout logic here.");
        // Example: load login scene or close app
        // Platform.exit();
    }

    // Generic loader method
    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
            // In production you might show a user-friendly dialog instead
        }
    }

    // set active nav button styling
    private void setActiveNav(Button active) {
        customersButton.getStyleClass().remove("active");
        booksButton.getStyleClass().remove("active");
        // do not add active class to logout
        active.getStyleClass().add("active");
    }
}
