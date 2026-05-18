package com.madina.library.borrowing;

import com.madina.library.book.Book;
import com.madina.library.book.BookRepository;
import com.madina.library.student.Student;
import com.madina.library.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public BorrowingService(
            BorrowingRepository borrowingRepository,
            BookRepository bookRepository,
            StudentRepository studentRepository
    ) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    public Borrowing borrowBook(Long bookId, Long studentId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is already borrowed");
        }

        book.setAvailable(false);
        Borrowing borrowing = new Borrowing(book, student);

        return borrowingRepository.save(borrowing);
    }

    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing record not found"));

        if (!borrowing.isActive()) {
            throw new IllegalStateException("Book has already been returned");
        }

        borrowing.markReturned();
        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getActiveBorrowings() {
        return borrowingRepository.findByReturnedDateIsNull();
    }
}

