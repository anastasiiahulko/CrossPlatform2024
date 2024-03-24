import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє книжковий магазин
class BookStore implements Serializable {
    private String name;
    private List<Book> books;

    public BookStore(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        StringBuilder bookInfo = new StringBuilder();
        for (Book book : books) {
            bookInfo.append(book.toString()).append("\n");
        }
        return "\nКнигарня: \n" +
                "назва: '" + name + '\'' +
                "\nкниги: \n" + bookInfo.toString() +
                ' ';
    }
}