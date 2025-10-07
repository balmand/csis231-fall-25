package com.csis231.api.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book create(Book book){
        return bookRepository.save(book);
    }

    public List<Book> getAll(){
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public Book getById(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return book;
    }

    public Book update(Long id, Book book){
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setISBN(book.getISBN());
        bookToUpdate.setYear(book.getYear());
        return bookRepository.save(bookToUpdate);
    }

    public void delete(Long id){

        Book bookToBeDeleted = bookRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(bookToBeDeleted);
    }
}
