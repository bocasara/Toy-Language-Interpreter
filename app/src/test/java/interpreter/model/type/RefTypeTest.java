package interpreter.model.type;

import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RefTypeTest {
    @Test
    void defaultValueIsRefAtAddressZero() {
        assertEquals(new RefValue(0, new IntType()), new RefType(new IntType()).defaultValue());
    }

    @Test
    void toStringWrapsInnerType() {
        assertEquals("Ref(int)", new RefType(new IntType()).toString());
    }

    @Test
    void innerReturnsLocationType() {
        assertEquals(new IntType(), new RefType(new IntType()).inner());
    }

    @Test
    void nestedRefToString() {
        assertEquals("Ref(Ref(bool))", new RefType(new RefType(new BoolType())).toString());
    }
}
