package com.madina.library.borrowing;

import com.madina.library.book.Book;
import com.madina.library.student.Student;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Student student;

    private LocalDate borrowedDate;

    private LocalDate returnedDate;

    protected Borrowing() {
    }

    public Borrowing(Book book, Student student) {
        this.book = book;
        this.student = student;
        this.borrowedDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public boolean isActive() {
        return returnedDate == null;
    }

    public void markReturned() {
        this.returnedDate = LocalDate.now();
        this.book.setAvailable(true);
    }
}

