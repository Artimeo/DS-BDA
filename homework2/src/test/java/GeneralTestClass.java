import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GeneralTestClass {
    @Test
    public void testPerson() throws IOException {
        Person person = new Person(12345, 1, 50000, 24, 1);
        assertEquals(person.getCategory(), "yong");
    }
}
