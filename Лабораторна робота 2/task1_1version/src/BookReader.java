import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє читача
class BookReader implements Serializable {
    private static final long serialVersionUID = 1L;
    private String registrationNumber;
    private String firstName;
    private String lastName;
    private List<Book> borrowedBooks;

    public BookReader(String registrationNumber, String firstName, String lastName) {
        this.registrationNumber = registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.borrowedBooks = new ArrayList<>();
    }

    // Геттер для поля registrationNumber
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    // Метод для видачі книги читачеві
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Метод для повернення книги читачеві
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    // Метод для отримання списку позичених книг
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Геттер для поля firstName
    public String getFirstName() {
        return firstName;
    }

    // Геттер для поля lastName
    public String getLastName() {
        return lastName;
    }
}
