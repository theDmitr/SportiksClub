package dmitr.app.sportiksclub.util;

import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonUtilsTest {

    @Test
    void getInitials() {
        Person person = new Person(new User(), "Ivan", "Lopata", "Shigorevich", true);
        String initials = PersonUtils.getInitials(person);
        assertEquals(initials, "Lopata I. S.");
    }
}