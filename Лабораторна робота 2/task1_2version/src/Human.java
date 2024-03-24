import java.io.Serializable;

// Абстрактний базовий клас, що представляє людину
abstract class Human implements Serializable {
    private String firstName;
    private String lastName;

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "\nЛюдина: \n" +
                "ім'я='" + firstName + '\'' +
                "\nпрізвище='" + lastName + '\'' +
                ' ';
    }
}