import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Library implements Externalizable {
    private List<BookStore> bookStores;

    public Library() {
        bookStores = new ArrayList<>();
    }

    public void addBookStore(BookStore bookStore) {
        bookStores.add(bookStore);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(bookStores.size());
        for (BookStore bookStore : bookStores) {
            bookStore.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        bookStores = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            BookStore bookStore = new BookStore();
            bookStore.readExternal(in);
            bookStores.add(bookStore);
        }
    }

    @Override
    public String toString() {
        return "\nБібліотека: " +
                "\nкнигарні: " + bookStores +
                ' ';
    }
}