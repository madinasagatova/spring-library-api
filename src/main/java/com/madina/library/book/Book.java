package com.madina.library.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity // Java Class becomes a database table
public class Book {

    @Id // Pimary key

    // The database generate the id automatically
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title cannot be empty 
    @NotBlank
    private String title;

    // Author cannot be empty 
    @NotBlank
    private String author;

    private String genre;

    private boolean available = true;

    protected Book() {
    }

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true;
    }

    // Getters 
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return available;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
