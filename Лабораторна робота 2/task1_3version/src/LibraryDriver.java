import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Створення бібліотеки
        System.out.println("Введіть назву бібліотеки:");
        String libraryName = scanner.nextLine();
        Library library = new Library();

        // Додавання книг користувачем
        addBooksManually(library, scanner);

        // Серіалізація об'єктів у файл
        saveLibraryToFile(library);

        // Десеріалізація об'єктів з файлу
        Library deserializedLibrary = restoreLibraryFromFile();
        if (deserializedLibrary != null) {
            System.out.println("Об'єкти бібліотеки відновлено з файлу 'library.ser'");
            System.out.println(deserializedLibrary);
        }
    }

    private static void addBooksManually(Library library, Scanner scanner) {
        do {
            // Створення автора книги
            System.out.println("Введіть ім'я автора:");
            String authorFirstName = scanner.nextLine().trim();
            System.out.println("Введіть прізвище автора:");
            String authorLastName = scanner.nextLine().trim();
            Author author = new Author(authorFirstName, authorLastName);
            List<Author> authors = new ArrayList<>();
            authors.add(author);

            // Створення книги
            System.out.println("Введіть назву книги:");
            String bookName = scanner.nextLine();
            int year;
            while (true) {
                try {
                    System.out.println("Введіть рік публікації:");
                    year = Integer.parseInt(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Некоректний ввід. Будь ласка, введіть ціле число для року публікації.");
                }
            }

            System.out.println("Введіть номер видання (13 цифр):");
            long editionNumber;
            while (true) {
                try {
                    editionNumber = Long.parseLong(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Некоректний ввід. Будь ласка, введіть число:");
                }
            }

            // Створення книгарні
            System.out.println("Введіть назву книгарні:");
            String storeName = scanner.nextLine();
            BookStore bookStore = new BookStore(storeName);

            Book book = new Book(bookName, authors);
            bookStore.addBook(book);
            // Додавання книгарні до бібліотеки
            library.addBookStore(bookStore);

            System.out.println("Чи бажаєте додати ще одну книгу? (Так/Ні):");
        } while (scanner.nextLine().trim().equalsIgnoreCase("Так"));
    }

    private static void saveLibraryToFile(Library library) {
        String fileName = "library.ser";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            library.writeExternal(outputStream);
            System.out.println("Об'єкти бібліотеки серіалізовано у файл '" + fileName + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Library restoreLibraryFromFile() {
        String fileName = "library.ser";
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            Library restoredLibrary = new Library();
            restoredLibrary.readExternal(inputStream);
            return restoredLibrary;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}