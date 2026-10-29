package interpreter.model.value;

import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntValueTest {
    @Test
    void getTypeReturnsIntType() {
        assertEquals(new IntType(), new IntValue(42).getType());
    }

    @Test
    void isTrueIsFalseOnlyForZero() {
        assertFalse(new IntValue(0).IsTrue());
        assertTrue(new IntValue(1).IsTrue());
        assertTrue(new IntValue(-1).IsTrue());
    }

    @Test
    void toStringReturnsNumberText() {
        assertEquals("42", new IntValue(42).toString());
        assertEquals("-7", new IntValue(-7).toString());
    }
}
