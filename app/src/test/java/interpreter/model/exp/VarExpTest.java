package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VarExpTest {
    private IDictionary<String, IValue> symTable;
    private IHeap heap;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
        heap = new MyHeap();
    }

    @Test
    void evalDefinedVariable() throws MyException {
        symTable.put("x", new IntValue(10));
        VarExp varExp = new VarExp("x");
        assertEquals(new IntValue(10), varExp.eval(symTable, heap));
    }

    @Test
    void evalUndefinedVariable() {
        VarExp varExp = new VarExp("y");
        try {
            varExp.eval(symTable, heap);
        } catch (Exception e) {
            assertEquals("Variable y is not defined.", e.getMessage());
        }
    }

    @Test
    void toStringTest() {
        VarExp varExp = new VarExp("z");
        assertEquals("z", varExp.toString());
    }
}
