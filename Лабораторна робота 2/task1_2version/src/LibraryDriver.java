import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас з методом main() для організації роботи програми
public class LibraryDriver {
    public static void main(String[] args) {
        // Створення об'єктів та робота з ними

        // Серіалізація бібліотеки
        Library library = createLibrary();
        serializeLibrary(library, "library.ser");

        // Десеріалізація бібліотеки
        Library deserializedLibrary = deserializeLibrary("library.ser");
        System.out.println("Десеріалізована бібліотека: \n" + deserializedLibrary);
    }

    public static Library createLibrary() {
        // Створення об'єктів для бібліотеки
        Library library = new Library("Моя бібліотека");

        BookStore bookstore1 = new BookStore("Книгарня 1");
        bookstore1.addBook(new Book("Книга 1", List.of(new Author("Автор 1", "Автор 1")), 2022, 1));
        bookstore1.addBook(new Book("Книга 2", List.of(new Author("Автор 2", "Автор 2")), 2023, 2));
        library.addBookStore(bookstore1);

        Reader reader1 = new Reader("Іван", "Іванов", "12345");
        reader1.borrowBook(bookstore1.getBooks().get(0));
        library.addReader(reader1);

        return library;
    }

    public static void serializeLibrary(Library library, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(library);
            System.out.println("Бібліотека серіалізована успішно.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Library deserializeLibrary(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Library library = (Library) in.readObject();
            System.out.println("Бібліотека десеріалізована успішно.");
            return library;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
