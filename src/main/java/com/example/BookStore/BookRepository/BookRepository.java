package com.example.BookStore.BookRepository;

import com.example.BookStore.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE b.title=?1")
    Optional<Book> findBookByTitle(String title);
}
