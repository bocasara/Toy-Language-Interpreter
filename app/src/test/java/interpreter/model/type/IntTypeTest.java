package interpreter.model.type;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntTypeTest {
    @Test
    void defaultValueIsZero() {
        assertEquals(new IntValue(0), new IntType().defaultValue());
    }

    @Test
    void toStringReturnsInt() {
        assertEquals("int", new IntType().toString());
    }
}
