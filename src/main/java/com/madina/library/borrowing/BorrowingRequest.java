package com.madina.library.borrowing;

import jakarta.validation.constraints.NotNull;

public class BorrowingRequest {

    @NotNull
    private Long bookId;

    @NotNull
    private Long studentId;

    public Long getBookId() {
        return bookId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

