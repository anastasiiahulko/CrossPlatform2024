import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

class Book implements Externalizable {
    private String title;
    private List<Author> authors;

    public Book() {
        // Потрібно для Externalizable
    }

    public Book(String bookName, List<Author> authors, int year, long editionNumber) {
        this.authors = new ArrayList<>();
    }

    public Book(String title, List<Author> authors) {
        this.title = title;
        this.authors = authors;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeInt(authors.size());
        for (Author author : authors) {
            author.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        int size = in.readInt();
        authors = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Author author = new Author();
            author.readExternal(in);
            authors.add(author);
        }
    }

    @Override
    public String toString() {
        return "\nКнига:" +
                "\nназва: '" + title + '\'' +
                "\nавтор(и): " + authors +
                ' ';
    }
}