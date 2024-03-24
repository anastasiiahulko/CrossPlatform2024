import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryConsoleApp {
    private static Library library;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Вітаємо у вашій бібліотеці!");

        // Відновлення бібліотеки з файлу при запуску програми
        library = Library.restoreLibraryFromFile();

        // Показ меню користувачу
        int choice;
        do {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Додати книгу");
            System.out.println("2. Видалити книгу");
            System.out.println("3. Додати читача");
            System.out.println("4. Видалити читача");
            System.out.println("5. Додати позичання книги");
            System.out.println("6. Додати повернення книги");
            System.out.println("7. Показати інформацію про бібліотеку");
            System.out.println("8. Вийти");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addBook(library, scanner);
                    break;
                case 2:
                    // Додайте видалення книги
                    System.out.println("Введіть назву книги, яку потрібно видалити:");
                    scanner.nextLine(); // очищення буфера введення
                    String bookTitle = scanner.nextLine().trim();
                    removeBook(bookTitle);
                    break;
                case 3:
                    addReader(library, scanner);
                    break;
                case 4:
                    removeReader(library, scanner);
                    break;
                case 5:
                    borrowBook(library, scanner);
                    break;
                case 6:
                    returnBook(library, scanner);
                    break;
                case 7:
                    library.printLibraryInfo();
                    break;
                case 8:
                    saveAndExit();
                    System.out.println("До побачення!");
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        } while (choice != 8);

        scanner.close();
    }

    // Метод для додавання читача
    private static void addReader(Library library, Scanner scanner) {
        System.out.println("Введіть реєстраційний номер читача:");
        String registrationNumber = scanner.next();
        System.out.println("Введіть ім'я читача:");
        String firstName = scanner.next();
        System.out.println("Введіть прізвище читача:");
        String lastName = scanner.next();

        BookReader reader = new BookReader(registrationNumber, firstName, lastName);
        library.addReader(reader);

        System.out.println("Читача додано до бібліотеки.");
    }

    // Метод для видалення книги
    private static void removeBook(String bookTitle) {
        boolean removed = library.removeBook(bookTitle);
        if (removed) {
            System.out.println("Книга '" + bookTitle + "' успішно видалена з книжкового магазину.");
        } else {
            System.out.println("Книга з назвою '" + bookTitle + "' не знайдена в книжковому магазині.");
        }
    }

    // Метод для видалення читача
    private static void removeReader(Library library, Scanner scanner) {
        System.out.println("Введіть реєстраційний номер читача, якого потрібно видалити:");
        String registrationNumber = scanner.next();

        boolean removed = library.removeReader(registrationNumber);
        if (removed) {
            System.out.println("Читача успішно видалено з бібліотеки.");
        } else {
            System.out.println("Читача з таким реєстраційним номером не знайдено.");
        }
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.println("Введіть назву книги:");
        scanner.nextLine(); // очищення буфера введення
        String title = scanner.nextLine().trim();

        if (isBookExists(library, title)) {
            System.out.println("Книга з такою назвою вже існує у бібліотеці.");
            return;
        }

        int publicationYear;
        while (true) {
            try {
                System.out.println("Введіть рік публікації:");
                publicationYear = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некоректний ввід. Будь ласка, введіть ціле число для року публікації.");
            }
        }

        // Створення автора книги
        System.out.println("Введіть ім'я автора:");
        String authorFirstName = scanner.nextLine().trim();
        System.out.println("Введіть прізвище автора:");
        String authorLastName = scanner.nextLine().trim();
        Author author = new Author(authorFirstName, authorLastName);
        List<Author> authors = new ArrayList<>();
        authors.add(author);

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

        Book book = new Book(title, authors, publicationYear, editionNumber);
        library.getBookStore().addBook(book);
        System.out.println("Книга додана успішно!");
    }



    // Метод для перевірки наявності книги у бібліотеці
    private static boolean isBookExists(Library library, String title) {
        for (Book book : library.getBookStore().getBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }


    // Метод для позичення книги
    private static void borrowBook(Library library, Scanner scanner) {
        System.out.println("Введіть реєстраційний номер читача:");
        String registrationNumber = scanner.next(); // Зчитуємо номер читача

        BookReader reader = findReader(library, registrationNumber);
        if (reader == null) {
            System.out.println("Читача з таким реєстраційним номером не знайдено.");
            return;
        }

        System.out.println("Введіть назву книги для позичення:");
        scanner.nextLine(); // очищення буфера введення
        String title = scanner.nextLine().trim(); // Зчитуємо назву книги

        // Перевірка, чи книга вже позичена
        if (isBookBorrowed(library, title)) {
            System.out.println("Ця книга вже позичена. Оберіть іншу книгу.");
            return;
        }

        Book book = findBook(library, title);
        if (book == null) {
            System.out.println("Книги з такою назвою не знайдено.");
            return;
        }

        reader.borrowBook(book);
        System.out.println("Книгу \"" + title + "\" позичено читачу " + registrationNumber);
    }

    // Метод для перевірки, чи книга вже позичена
    private static boolean isBookBorrowed(Library library, String title) {
        for (BookReader reader : library.getReaders()) {
            for (Book borrowedBook : reader.getBorrowedBooks()) {
                if (borrowedBook.getTitle().equalsIgnoreCase(title)) {
                    return true;
                }
            }
        }
        return false;
    }


    // Метод для пошуку читача за реєстраційним номером
    private static BookReader findReader(Library library, String registrationNumber) {
        for (BookReader reader : library.getReaders()) {
            if (reader.getRegistrationNumber().equals(registrationNumber)) {
                return reader;
            }
        }
        return null;
    }

    // Метод для пошуку книги за назвою
    private static Book findBook(Library library, String title) {
        for (Book book : library.getBookStore().getBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Метод для повернення книги
    private static void returnBook(Library library, Scanner scanner) {
        System.out.println("Введіть реєстраційний номер читача:");
        String registrationNumber = scanner.next();
        scanner.nextLine(); // Споживаємо символ нового рядка

        BookReader reader = findReader(library, registrationNumber);
        if (reader == null) {
            System.out.println("Читача з таким реєстраційним номером не знайдено.");
            return;
        }

        List<Book> borrowedBooks = reader.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("Цей читач не позичав жодної книги.");
            return;
        }

        System.out.println("Список позичених книг:");
        for (int i = 0; i < borrowedBooks.size(); i++) {
            System.out.println((i + 1) + ". " + borrowedBooks.get(i).getTitle());
        }

        System.out.println("Введіть номер книги, яку хочете повернути:");
        int bookIndex;
        try {
            bookIndex = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Некоректний номер книги.");
            return;
        }

        if (bookIndex < 1 || bookIndex > borrowedBooks.size()) {
            System.out.println("Невірний номер книги.");
            return;
        }

        Book book = borrowedBooks.get(bookIndex - 1);
        reader.returnBook(book);
        System.out.println("Книгу \"" + book.getTitle() + "\" повернено.");
    }

    // Метод для збереження і виходу
    private static void saveAndExit() {
        library.saveLibraryToFile();
    }
}
