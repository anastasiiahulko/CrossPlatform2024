import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє книжкове сховище
class BookStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Book> books;

    public BookStore(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    // Геттер для поля name
    public String getName() {
        return name;
    }
}

