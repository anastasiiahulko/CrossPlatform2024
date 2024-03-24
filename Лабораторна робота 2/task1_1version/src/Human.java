import java.io.Serializable;

// Абстрактний базовий клас, що представляє людину
abstract class Human implements Serializable {
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;

    // Конструктор класу
    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Геттер для поля firstName
    public String getFirstName() {
        return firstName;
    }

    // Сеттер для поля firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Геттер для поля lastName
    public String getLastName() {
        return lastName;
    }

    // Сеттер для поля lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
