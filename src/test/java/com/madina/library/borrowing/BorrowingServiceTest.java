package com.madina.library.borrowing;

import com.madina.library.book.Book;
import com.madina.library.book.BookRepository;
import com.madina.library.student.Student;
import com.madina.library.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class BorrowingServiceTest {

    private BorrowingRepository borrowingRepository;
    private BookRepository bookRepository;
    private StudentRepository studentRepository;
    private BorrowingService borrowingService;

    @BeforeEach
    void setUp() {
        borrowingRepository = Mockito.mock(BorrowingRepository.class);
        bookRepository = Mockito.mock(BookRepository.class);
        studentRepository = Mockito.mock(StudentRepository.class);

        borrowingService = new BorrowingService(
                borrowingRepository,
                bookRepository,
                studentRepository
        );
    }

    @Test
    void borrowBookMakesBookUnavailable() {
        Book book = new Book("Clean Code", "Robert C. Martin", "Programming");
        Student student = new Student("Madina", "Sagatova", "madina@example.com");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(borrowingRepository.save(any(Borrowing.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Borrowing borrowing = borrowingService.borrowBook(1L, 1L);

        assertFalse(book.isAvailable());
        assertTrue(borrowing.isActive());
        assertEquals(book, borrowing.getBook());
        assertEquals(student, borrowing.getStudent());
    }

    @Test
    void borrowBookFailsWhenBookIsAlreadyBorrowed() {
        Book book = new Book("Clean Code", "Robert C. Martin", "Programming");
        book.setAvailable(false);

        Student student = new Student("Madina", "Sagatova", "madina@example.com");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> borrowingService.borrowBook(1L, 1L)
        );

        assertEquals("Book is already borrowed", exception.getMessage());
        verify(borrowingRepository, never()).save(any());
    }

    @Test
    void returnBookMakesBookAvailableAgain() {
        Book book = new Book("Clean Code", "Robert C. Martin", "Programming");
        book.setAvailable(false);

        Student student = new Student("Madina", "Sagatova", "madina@example.com");
        Borrowing borrowing = new Borrowing(book, student);

        when(borrowingRepository.findById(1L)).thenReturn(Optional.of(borrowing));
        when(borrowingRepository.save(any(Borrowing.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Borrowing returnedBorrowing = borrowingService.returnBook(1L);

        assertFalse(returnedBorrowing.isActive());
        assertTrue(book.isAvailable());
        assertNotNull(returnedBorrowing.getReturnedDate());
    }

    @Test
    void returnBookFailsWhenAlreadyReturned() {
        Book book = new Book("Clean Code", "Robert C. Martin", "Programming");
        Student student = new Student("Madina", "Sagatova", "madina@example.com");
        Borrowing borrowing = new Borrowing(book, student);
        borrowing.markReturned();

        when(borrowingRepository.findById(1L)).thenReturn(Optional.of(borrowing));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> borrowingService.returnBook(1L)
        );

        assertEquals("Book has already been returned", exception.getMessage());
        verify(borrowingRepository, never()).save(any());
    }
}

