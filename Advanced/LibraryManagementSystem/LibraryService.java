package LibraryManagementSystem;

import java.util.*;

public class LibraryService {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<IssueRecord> records = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added.");
    }

    public void viewBooks() {
        for (Book b : books) System.out.println(b);
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added.");
    }

    public void viewUsers() {
        for (User u : users) System.out.println(u);
    }

    public void issueBook(String bookId, String userId) {
        Book book = findBook(bookId);
        if (book == null || book.isIssued()) {
            System.out.println("Book not found or already issued.");
            return;
        }

        if (findUser(userId) == null) {
            System.out.println("User not found.");
            return;
        }

        book.issue();
        records.add(new IssueRecord(bookId, userId));
        System.out.println("Book issued.");
    }

    public void returnBook(String bookId) {
        Book book = findBook(bookId);
        if (book == null || !book.isIssued()) {
            System.out.println("Book not issued.");
            return;
        }

        for (IssueRecord r : records) {
            if (r.getBookId().equals(bookId) && !r.isReturned()) {
                r.returnBook();
                book.returnBook();
                System.out.println("Book returned.");
                return;
            }
        }

        System.out.println("Issue record not found.");
    }

    public void viewIssuedRecords() {
        for (IssueRecord r : records) System.out.println(r);
    }

    private Book findBook(String id) {
        for (Book b : books) {
            if (b.getId().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }

    private User findUser(String id) {
        for (User u : users) if (u.getUserId().equalsIgnoreCase(id)) return u;
        return null;
    }
}
