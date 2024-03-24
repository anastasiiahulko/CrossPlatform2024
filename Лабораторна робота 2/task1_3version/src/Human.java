import java.io.*;

class Human implements Externalizable {
    private String firstName;
    private String lastName;

    public Human() {
    }

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(firstName);
        out.writeObject(lastName);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        firstName = (String) in.readObject();
        lastName = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "\nЛюдина: \n" +
                "\nім'я='" + firstName + '\'' +
                "\nпрізвище='" + lastName + '\'' +
                ' ';
    }
}