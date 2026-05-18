package com.madina.library.borrowing;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping("/active")
    public List<Borrowing> getActiveBorrowings() {
        return borrowingService.getActiveBorrowings();
    }

    @PostMapping
    public ResponseEntity<Borrowing> borrowBook(@Valid @RequestBody BorrowingRequest request) {
        Borrowing borrowing = borrowingService.borrowBook(request.getBookId(), request.getStudentId());
        return ResponseEntity
                .created(URI.create("/api/borrowings/" + borrowing.getId()))
                .body(borrowing);
    }

    @PostMapping("/{id}/return")
    public Borrowing returnBook(@PathVariable Long id) {
        return borrowingService.returnBook(id);
    }
}

