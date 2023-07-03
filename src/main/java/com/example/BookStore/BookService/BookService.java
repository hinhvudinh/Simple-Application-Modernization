package com.example.BookStore.BookService;

import com.example.BookStore.Book;
import com.example.BookStore.BookRepository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBook() {
        return bookRepository.findAll();
    }

    public void addNewBook(Book book) {
        Optional<Book> bookByName =
                bookRepository.findBookByTitle(book.getTitle());

        if (bookByName.isPresent()){
            throw new IllegalStateException("This book is already available!");
        }

        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Integer bookId, String title, String isbn) {
        // Verify the book with id exists
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalStateException("Book with ID: " +
                        bookId + " does not exists!")
        );

        // update title of the book
        if (title != null &&
                title.length() > 0 &&
                !Objects.equals(book.getTitle(), title)){
            book.setTitle(title);
        }

        // update isbn of the book
        if (isbn != null &&
                isbn.length() > 0 &&
                !Objects.equals(book.getISBN(), isbn)){
            book.setISBN(isbn);
        }
    }


    public void deleteBook(Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalStateException("Book with ID: " +
                        bookId + " does not exists!")
        );

        bookRepository.deleteById(bookId);
    }
}
