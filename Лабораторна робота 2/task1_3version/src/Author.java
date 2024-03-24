import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


class Author extends Human {
    public Author() {
    }

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
