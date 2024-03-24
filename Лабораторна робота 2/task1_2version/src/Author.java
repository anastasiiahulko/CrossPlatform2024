import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє автора книги
class Author extends Human implements Serializable {
    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }
}