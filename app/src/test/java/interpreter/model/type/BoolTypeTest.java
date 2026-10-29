package interpreter.model.type;

import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoolTypeTest {
    @Test
    void defaultValueIsFalse() {
        assertEquals(new BoolValue(false), new BoolType().defaultValue());
    }

    @Test
    void toStringReturnsBool() {
        assertEquals("bool", new BoolType().toString());
    }
}
