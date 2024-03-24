import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє книгу
class Book implements Serializable {
    private String name;
    private List<Author> authors;
    private int year;
    private int editionNumber;

    public Book(String name, List<Author> authors, int year, int editionNumber) {
        this.name = name;
        this.authors = authors;
        this.year = year;
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        StringBuilder authorNames = new StringBuilder();
        for (Author author : authors) {
            authorNames.append(author.getFirstName()).append(" ").append(author.getLastName()).append(", ");
        }
        if (authorNames.length() > 0) {
            authorNames.setLength(authorNames.length() - 2);
        }
        return "\nКнига: \n" +
                "назва: '" + name + '\'' +
                "\nавтор(и): " + authorNames.toString() +
                "\nрік: " + year +
                "\nномер видання: " + editionNumber +
                ' ';
    }
}