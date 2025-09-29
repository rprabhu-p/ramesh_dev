package LibraryManagementSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryService service = new LibraryService();
        Admin admin = new Admin("admin", "1234");

        System.out.print("Enter username: ");
        String u = sc.nextLine();
        System.out.print("Enter password: ");
        String p = sc.nextLine();

        if (!admin.authenticate(u, p)) {
            System.out.println("Login failed.");
            sc.close();
            return;
        }

        int ch;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Add User");
            System.out.println("4. View Users");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Issued Records");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1:
                    System.out.print("ID: "); String id = sc.nextLine();
                    System.out.print("Title: "); String t = sc.nextLine();
                    System.out.print("Author: "); String a = sc.nextLine();
                    service.addBook(new Book(id, t, a));
                    break;

                case 2: service.viewBooks(); break;

                case 3:
                    System.out.print("User ID: "); String uid = sc.nextLine();
                    System.out.print("Name: "); String name = sc.nextLine();
                    service.addUser(new User(uid, name));
                    break;

                case 4: service.viewUsers(); break;

                case 5:
                    System.out.print("Book ID: "); String bid = sc.nextLine();
                    System.out.print("User ID: "); String uID = sc.nextLine();
                    service.issueBook(bid, uID);
                    break;

                case 6:
                    System.out.print("Book ID to return: ");
                    String rid = sc.nextLine();
                    service.returnBook(rid);
                    break;

                case 7: service.viewIssuedRecords(); break;

                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (ch != 0);

        sc.close();
    }
}
