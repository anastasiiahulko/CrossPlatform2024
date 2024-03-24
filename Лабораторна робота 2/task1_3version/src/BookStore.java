import java.io.*;
import java.util.ArrayList;
import java.util.List;

class BookStore implements Externalizable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Book> books;

    public BookStore() {
        // Потрібно для Externalizable
    }

    public BookStore(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = (String) in.readObject();
        this.books = (List<Book>) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.name);
        out.writeObject(this.books);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Книгарня: ").append(name).append("\n");
        for (Book book : books) {
            sb.append(book).append("\n");
        }
        return sb.toString();
    }
}

