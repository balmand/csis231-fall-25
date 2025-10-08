package com.example.demo;

import com.example.demo.api.BookBackendClient;
import com.example.demo.model.Book;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class BookController {

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Integer> yearColumn;

    @FXML
    private TableColumn<Book, Double> priceColumn;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    private final BookBackendClient api = new BookBackendClient();
    private final ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure table columns
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));

        // Bind data to table
        bookTable.setItems(books);

        // Enable sorting
        titleColumn.setSortable(true);
        authorColumn.setSortable(true);

        // Initial data load
        reload();
    }

    @FXML
    protected void onAddBook() {
        showBookForm(null);
    }

    @FXML
    protected void onEditBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a book to edit").showAndWait();
            return;
        }
        showBookForm(selected);
    }

    @FXML
    protected void onDeleteBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a book to delete").showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Book");
        confirmAlert.setContentText("Are you sure you want to delete \"" + selected.getTitle() + "\"?");

        if (confirmAlert.showAndWait().orElse(null) == ButtonType.OK) {
            try {
                api.deleteBook(selected.getId());
                reload();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete book: " + e.getMessage()).showAndWait();
            }
        }
    }

    @FXML
    protected void onRefresh() {
        reload();
    }

    private void showBookForm(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/bookForm.fxml"));
            Scene scene = new Scene(loader.load(), 700, 600); // increased width & height
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            BookFormController controller = loader.getController();
            controller.setBook(book);
            controller.setOnBookSaved(this::reload);

            Stage stage = new Stage();
            stage.setTitle(book == null ? "Add Book" : "Edit Book");
            stage.setResizable(false); // keep as false
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open book form: " + e.getMessage()).showAndWait();
        }
    }






    private void reload() {
        books.clear();
        try {
            books.addAll(api.fetchBooks());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load books: " + e.getMessage()).showAndWait();
        }
    }
}
