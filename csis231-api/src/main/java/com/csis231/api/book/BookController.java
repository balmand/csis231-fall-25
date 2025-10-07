package com.csis231.api.book;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository repo;
    public BookController(BookRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Book> all() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book c) {
        return repo.save(c);
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        existing.setAuthor(book.getAuthor());
        existing.setPrice(book.getPrice());
        existing.setTitle(book.getTitle());
        existing.setISBN(book.getISBN());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}