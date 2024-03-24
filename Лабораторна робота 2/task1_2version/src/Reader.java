import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє читача
class Reader extends Human implements Serializable {
    private String registrationNumber;
    private List<Book> borrowedBooks;

    public Reader(String firstName, String lastName, String registrationNumber) {
        super(firstName, lastName);
        this.registrationNumber = registrationNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    @Override
    public String toString() {
        StringBuilder borrowedBookNames = new StringBuilder();
        for (Book book : borrowedBooks) {
            borrowedBookNames.append(book.toString());
        }
        return "\nЧитач: \n" +
                "реєстраційний номер: '" + registrationNumber + '\'' +
                ", позичені книги: \n" + borrowedBookNames.toString() +
                " " + super.toString();
    }
}