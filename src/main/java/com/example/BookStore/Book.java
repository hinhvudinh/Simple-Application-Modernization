package com.example.BookStore;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity

public class Book {
    @Id
    @SequenceGenerator(
            name = "book_id_sequence",
            sequenceName = "book_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_id_sequence"
    )
    
    private Integer id;
    private String title;
    private String ISBN;
    private String publicdate;
    private String price;

    public Book() {
    }

    public Book(String title,
                String ISBN,
                String publicdate,
                String price) {
        this.title = title;
        this.ISBN = ISBN;
        this.publicdate = publicdate;
        this.price = price;
    }

    public Book(Integer id, String title, String ISBN, String publicdate, String price) {
        this.id = id;
        this.title = title;
        this.ISBN = ISBN;
        this.publicdate = publicdate;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getpublicdate() {
        return publicdate;
    }

    public void setpublicdate(String publicdate) {
        this.publicdate = publicdate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publicdate=" + publicdate +
                ", price='" + price + '\'' +
                '}';
    }

}
