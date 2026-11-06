package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {
    @Test
    void getTypeReturnsStringType() {
        assertEquals(new StringType(), new StringValue("hello").getType());
    }

    @Test
    void toStringReturnsString() {
        assertEquals("hello", new StringValue("hello").toString());
        assertEquals("", new StringValue("").toString());
    }

}