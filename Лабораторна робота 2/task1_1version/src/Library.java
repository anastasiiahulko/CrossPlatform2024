import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// Клас, що представляє бібліотеку
class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private BookStore bookStore;
    private List<BookReader> readers;

    // Конструктор з параметрами
    public Library(String name, BookStore bookStore, List<BookReader> readers) {
        this.name = name;
        this.bookStore = bookStore;
        this.readers = readers;
    }

    // Геттер для назви бібліотеки
    public String getName() {
        return name;
    }

    // Геттер для книжкового магазину
    public BookStore getBookStore() {
        return bookStore;
    }

    // Геттер для списку читачів
    public List<BookReader> getReaders() {
        return readers;
    }

    // Метод для додавання читача
    public void addReader(BookReader reader) {
        readers.add(reader);
    }

    // Метод для видалення книги за назвою
    public boolean removeBook(String bookTitle) {
        List<Book> books = bookStore.getBooks();
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle)) {
                books.remove(book);
                return true; // Книга успішно видалена
            }
        }
        return false; // Книга не знайдена
    }


    // Метод для збереження поточного стану системи у файл
    public void saveLibraryToFile() {
        String fileName = "library_data.ser";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
            System.out.println("Стан бібліотеки збережено у файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для відновлення поточного стану системи з файлу
    public static Library restoreLibraryFromFile() {
        String fileName = "library_data.ser";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл " + fileName + " не знайдено. Створюємо нову бібліотеку.");
            return new Library("Моя бібліотека", new BookStore("Книжковий магазин"), new ArrayList<>());
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            Library restoredLibrary = (Library) inputStream.readObject();
            System.out.println("Стан бібліотеки відновлено з файлу " + fileName);
            return restoredLibrary;
        } catch (IOException e) {
            System.out.println("Помилка вводу/виводу при зчитуванні файлу " + fileName + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Клас не знайдено при відновленні бібліотеки з файлу " + fileName + ": " + e.getMessage());
        }
        // У випадку, якщо сталася помилка, повертаємо нову бібліотеку
        return new Library("Моя бібліотека", new BookStore("Книжковий магазин"), new ArrayList<>());
    }


    // Метод для виведення поточної бібліотеки
    public void printLibraryInfo() {
        System.out.println("Назва бібліотеки: " + name);
        System.out.println("Назва книжкового магазину: " + bookStore.getName());
        System.out.println("Книги в книжковому магазині:");

        List<Book> books = bookStore.getBooks();
        if (books.isEmpty()) {
            System.out.println("Книжковий магазин порожній.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
        System.out.println("Читачі:");

        if (readers.isEmpty()) {
            System.out.println("Немає зареєстрованих читачів.");
        } else {
            for (BookReader reader : readers) {
                System.out.println("Читач " + reader.getRegistrationNumber());
                System.out.println("Ім'я: " + reader.getFirstName());
                System.out.println("Прізвище: " + reader.getLastName());
                System.out.println("Позичені книги:");
                List<Book> borrowedBooks = reader.getBorrowedBooks();
                if (borrowedBooks.isEmpty()) {
                    System.out.println("Цей читач не позичав жодної книги.");
                } else {
                    for (Book book : borrowedBooks) {
                        System.out.println("Назва: " + book.getTitle());
                        System.out.println("Автор(и): " + book.getAuthorsAsString());
                        System.out.println("Рік публікації: " + book.getPublicationYear());
                        System.out.println("Номер видання: " + book.getEditionNumber());
                    }
                }
            }
        }
    }

    // Метод для видалення читача за реєстраційним номером
    public boolean removeReader(String registrationNumber) {
        Iterator<BookReader> iterator = readers.iterator();
        while (iterator.hasNext()) {
            BookReader reader = iterator.next();
            if (reader.getRegistrationNumber().equals(registrationNumber)) {
                iterator.remove();
                return true; // Читача успішно видалено
            }
        }
        return false; // Читач не знайдений
    }

}
