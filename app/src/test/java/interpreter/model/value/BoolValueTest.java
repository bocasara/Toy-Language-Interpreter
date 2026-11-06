package interpreter.model.value;

import interpreter.model.type.BoolType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoolValueTest {
    @Test
    void getTypeReturnsBoolType() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }


    @Test
    void toStringReturnsBooleanText() {
        assertEquals("true", new BoolValue(true).toString());
        assertEquals("false", new BoolValue(false).toString());
    }
}
