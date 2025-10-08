package com.example.demo.api;

import com.example.demo.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

public class BookBackendClient {

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl;

    public BookBackendClient() {
        Properties props = new Properties();
        try {
            props.load(getClass().getResourceAsStream("/client.properties"));
            this.baseUrl = props.getProperty("backend.baseUrl", "http://localhost:8080");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Fetch all books
    public List<Book> fetchBooks() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/books"))
                .GET()
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(resp.body(), new TypeReference<>() {});
    }

    // Get book by ID
    public Book getBook(Long id) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/books/" + id))
                .GET()
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(resp.body(), Book.class);
    }

    // Create a new book
    public Book createBook(Book book) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(book);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/books"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(resp.body(), Book.class);
    }

    // Update an existing book
    public Book updateBook(Long id, Book book) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(book);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/books/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(resp.body(), Book.class);
    }

    // Delete a book
    public void deleteBook(Long id) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/books/" + id))
                .DELETE()
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 204) {
            throw new RuntimeException("Delete failed with status: " + resp.statusCode());
        }
    }
}
