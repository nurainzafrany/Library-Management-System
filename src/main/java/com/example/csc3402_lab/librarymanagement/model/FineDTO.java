package com.example.csc3402_lab.librarymanagement.model;

public class FineDTO {
    private Long fineId; // This is actually the Borrow ID
    private String bookTitle;
    private String bookAuthor;
    private long daysOverdue;

    public FineDTO(Long fineId, String bookTitle, String bookAuthor, long daysOverdue) {
        this.fineId = fineId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.daysOverdue = daysOverdue;
    }

    public Long getFineId() { return fineId; }
    public String getBookTitle() { return bookTitle; }
    public String getBookAuthor() { return bookAuthor; }
    public long getDaysOverdue() { return daysOverdue; }
}
