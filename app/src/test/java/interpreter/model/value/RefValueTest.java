package interpreter.model.value;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RefValueTest {
    @Test
    void getTypeReturnsRefOfLocationType() {
        assertEquals(new RefType(new IntType()), new RefValue(1, new IntType()).getType());
    }

    @Test
    void toStringShowsAddressAndLocationType() {
        assertEquals("(5, int)", new RefValue(5, new IntType()).toString());
    }

    @Test
    void equalByAddressAndLocationType() {
        assertEquals(new RefValue(2, new IntType()), new RefValue(2, new IntType()));
    }

    @Test
    void differentAddressNotEqual() {
        assertNotEquals(new RefValue(1, new IntType()), new RefValue(2, new IntType()));
    }
}
