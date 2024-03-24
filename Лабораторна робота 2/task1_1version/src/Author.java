import java.io.Serializable;

// Клас, що представляє автора книги
class Author implements Serializable {
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Геттер для firstName
    public String getFirstName() {
        return firstName;
    }

    // Сеттер для firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Геттер для lastName
    public String getLastName() {
        return lastName;
    }

    // Сеттер для lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Перевизначений метод toString() для зручного виведення інформації про автора
    @Override
    public String toString() {
        return getFullName();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
