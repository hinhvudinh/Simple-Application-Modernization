package com.example.BookStore.BookController;

import com.example.BookStore.Book;
import com.example.BookStore.BookService.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")

public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBook() {
        return bookService.getBook();
    }

    @PostMapping
    public void registerNewBook(@RequestBody Book book){
        bookService.addNewBook(book);
    }

    @PutMapping(path="{bookId}")
    public void updateBook(@PathVariable("bookId") Integer bookId,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) String isbn){
        bookService.updateBook(bookId, title, isbn);
    }

    @DeleteMapping(path="{bookId}")
    public void deleteBook(@PathVariable("bookId") Integer bookId){
        bookService.deleteBook(bookId);
    }


}
