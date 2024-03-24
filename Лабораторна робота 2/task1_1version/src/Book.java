import java.io.Serializable;
import java.util.List;

// Клас, що представляє книгу
class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private List<Author> authors;
    private int publicationYear;
    private long editionNumber;

    public Book(String title, List<Author> authors, int publicationYear, long editionNumber) {
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.editionNumber = editionNumber;
    }

    // Геттери та сеттери для title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Геттери та сеттери для authors
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    // Геттери та сеттери для publicationYear
    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Геттери та сеттери для editionNumber
    public long getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(long editionNumber) {
        this.editionNumber = editionNumber;
    }

    // Перевизначений метод toString() для зручного виведення інформації про книгу
    @Override
    public String toString() {
        return "Назва: " + title + "\nАвтор(и): " + getAuthorsAsString() + "\nРік публікації: " + publicationYear + "\nНомер видання: " + editionNumber;
    }

    public String getAuthorsAsString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            builder.append(author.getFullName());
            if (i < authors.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
