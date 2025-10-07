package com.example.demo;

import com.example.demo.api.BackendClient;
import com.example.demo.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML 
    private TableView<Customer> customerTable;
    
    @FXML
    private TableColumn<Customer, String> nameColumn;
    
    @FXML
    private TableColumn<Customer, String> emailColumn;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button editButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button refreshButton;

    private final BackendClient api = new BackendClient();
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        // Configure table columns with custom cell value factories
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        
        // Set table data
        customerTable.setItems(customers);
        
        // Configure column sorting
        nameColumn.setSortable(true);
        emailColumn.setSortable(true);
        
        reload();
    }

    @FXML
    protected void onAddCustomer() {
        showCustomerForm(null);
    }
    
    @FXML
    protected void onEditCustomer() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to edit").showAndWait();
            return;
        }
        showCustomerForm(selected);
    }
    
    @FXML
    protected void onDeleteCustomer() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to delete").showAndWait();
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Customer");
        confirmAlert.setContentText("Are you sure you want to delete " + selected.getName() + "?");
        
        if (confirmAlert.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {
            try {
                api.deleteCustomer(selected.getId());
                reload();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete customer: " + e.getMessage()).showAndWait();
            }
        }
    }
    
    @FXML
    protected void onRefresh() {
        reload();
    }

    private void showCustomerForm(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/customer-form.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            
            CustomerFormController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setOnCustomerSaved(this::reload);
            
            Stage stage = new Stage();
            stage.setTitle(customer == null ? "Add Customer" : "Edit Customer");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open customer form: " + e.getMessage()).showAndWait();
        }
    }

    private void reload(){
        customers.clear();
        try{
            customers.addAll(api.fetchCustomers());
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to load customers "+e.getMessage()).showAndWait();
        }
    }
}
