package LibraryManagementSystem;
import java.time.LocalDate;

public class IssueRecord {
    private String bookId;
    private String userId;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public IssueRecord(String bookId, String userId) {
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = LocalDate.now();
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
    }

    public String toString() {
        return "BookID: " + bookId + " | UserID: " + userId + " | Issued: " + issueDate +
               " | Returned: " + (returnDate != null ? returnDate : "Not Returned");
    }

    public String getBookId() {
        return bookId;
    }

    public boolean isReturned() {
        return returnDate != null;
    }
}
