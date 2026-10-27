package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void evalIntValue() throws MyException {
        ValExp valExp = new ValExp(new IntValue(42));
        assertEquals(new IntValue(42), valExp.eval(symTable));
    }

    @Test
    void evalBoolValue() throws MyException {
        ValExp valExp = new ValExp(new interpreter.model.value.BoolValue(true));
        assertEquals(new interpreter.model.value.BoolValue(true), valExp.eval(symTable));
    }

    @Test
    void toStringTest() {
        ValExp valExp = new ValExp(new IntValue(42));
        assertEquals("42", valExp.toString());

        ValExp boolValExp = new ValExp(new interpreter.model.value.BoolValue(true));
        assertEquals("true", boolValExp.toString());
    }
}
