import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє бібліотеку
class Library implements Serializable {
    private String name;
    private List<BookStore> bookStores;
    private List<Reader> readers;

    public Library(String name) {
        this.name = name;
        this.bookStores = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public void addBookStore(BookStore bookStore) {
        bookStores.add(bookStore);
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    @Override
    public String toString() {
        StringBuilder bookStoreInfo = new StringBuilder();
        for (BookStore bookStore : bookStores) {
            bookStoreInfo.append(bookStore.toString()).append("\n");
        }
        StringBuilder readerInfo = new StringBuilder();
        for (Reader reader : readers) {
            readerInfo.append(reader.toString()).append("\n");
        }
        return "\nБібліотека: \n" +
                "назва: '" + name + '\'' +
                "\nкнигарні: " + bookStoreInfo.toString() +
                "\nчитачі: " + readerInfo.toString() +
                ' ';
    }
}
