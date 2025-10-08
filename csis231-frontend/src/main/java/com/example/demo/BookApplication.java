package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BookApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML for the Book Management UI
        FXMLLoader fxmlLoader = new FXMLLoader(BookApplication.class.getResource("fxml/book-view.fxml"));

        // Set up the scene
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Configure the window
        stage.setTitle("Book Management System");
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
