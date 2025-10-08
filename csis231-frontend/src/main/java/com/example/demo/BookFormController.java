package com.example.demo;

import com.example.demo.api.BookBackendClient;
import com.example.demo.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookFormController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField isbnField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private final BookBackendClient api = new BookBackendClient();
    private Book book;
    private boolean isEditMode = false;
    private Runnable onBookSaved;

    public void setBook(Book book) {
        this.book = book;
        this.isEditMode = book != null;

        if (isEditMode) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            yearField.setText(String.valueOf(book.getYear()));
            priceField.setText(String.valueOf(book.getPrice()));
            isbnField.setText(book.getISBN());
            saveButton.setText("Update");
        } else {
            titleField.clear();
            authorField.clear();
            yearField.clear();
            priceField.clear();
            isbnField.clear();
            saveButton.setText("Create");
        }
    }

    public void setOnBookSaved(Runnable callback) {
        this.onBookSaved = callback;
    }

    @FXML
    private void onSave() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearText = yearField.getText().trim();
        String priceText = priceField.getText().trim();
        String isbn = isbnField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty() || priceText.isEmpty() || isbn.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields").showAndWait();
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            double price = Double.parseDouble(priceText);

            if (isEditMode) {
                book.setTitle(title);
                book.setAuthor(author);
                book.setYear(year);
                book.setPrice(price);
                book.setISBN(isbn);
                api.updateBook(book.getId(), book);
            } else {
                Book newBook = new Book();
                newBook.setTitle(title);
                newBook.setAuthor(author);
                newBook.setYear(year);
                newBook.setPrice(price);
                newBook.setISBN(isbn);
                api.createBook(newBook);
            }

            if (onBookSaved != null) {
                onBookSaved.run();
            }

            closeForm();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Year and Price must be valid numbers.").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save book: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void onCancel() {
        closeForm();
    }

    private void closeForm() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
